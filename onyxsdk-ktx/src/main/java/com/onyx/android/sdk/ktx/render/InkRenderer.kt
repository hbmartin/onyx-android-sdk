@file:Suppress(
    // Legacy renderer objects are deliberately type-erased at the bytecode boundary so KTX
    // never exports their descriptors. Casts are confined to this adapter and state is locked.
    "AvoidVarsExceptWithDelegate",
    "DontForceCast",
    "MagicNumber",
    "NoCallbacksInFunctions",
    "NoVarsInConstructor",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx.render

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.onyx.android.sdk.base.data.TouchPoint
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticStatus
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.model.BrushConfiguration
import com.onyx.android.sdk.ktx.model.ExperimentalOnyxRendererApi
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenKind
import com.onyx.android.sdk.pen.NeoBallpointInkPen
import com.onyx.android.sdk.pen.NeoBrushPen
import com.onyx.android.sdk.pen.NeoCharcoalPen
import com.onyx.android.sdk.pen.NeoCharcoalPenV2
import com.onyx.android.sdk.pen.NeoFountainPen
import com.onyx.android.sdk.pen.NeoFountainPenV2
import com.onyx.android.sdk.pen.NeoMarkerPen
import com.onyx.android.sdk.pen.NeoPen
import com.onyx.android.sdk.pen.NeoPenConfig
import com.onyx.android.sdk.pen.NeoPencilPen
import com.onyx.android.sdk.pen.NeoSquarePen
import com.onyx.android.sdk.pen.PenResult
import java.io.Closeable

/** Immutable render output. The legacy pen implementation remains private to KTX. */
class RenderLayer internal constructor(
    private val nativeResults: List<Any>,
    bounds: RectF,
    paint: Paint,
) {
    private val immutableBounds = RectF(bounds)
    private val immutablePaint = Paint(paint)

    val bounds: RectF
        get() = RectF(immutableBounds)

    fun draw(canvas: Canvas) {
        val drawingPaint = Paint(immutablePaint)
        nativeResults.forEach { (it as PenResult).draw(canvas, drawingPaint) }
    }
}

class RenderFrame internal constructor(
    val committed: RenderLayer?,
    val predicted: RenderLayer?,
) {
    val bounds: RectF
        get() = when {
            committed == null -> predicted?.bounds ?: RectF()
            predicted == null -> committed.bounds
            else -> committed.bounds.apply { union(predicted.bounds) }
        }

    fun draw(canvas: Canvas, includePrediction: Boolean = true) {
        committed?.draw(canvas)
        if (includePrediction) predicted?.draw(canvas)
    }
}

/**
 * Deterministically owned native renderer with one checked state machine for both live and
 * offline rendering. Input is copied and pressure is always passed to native code normalized.
 */
class InkRenderer private constructor(
    private val kind: PenKind,
    private val configuration: BrushConfiguration,
    // Erased so Kotlin's synthetic private-constructor and helper bridges do not publish a
    // legacy SDK descriptor from the KTX bytecode surface.
    private var nativePen: Any,
) : Closeable {
    private sealed interface State {
        data object Idle : State
        data object Drawing : State
        data object Closed : State
    }

    private val lock = Any()
    private var state: State = State.Idle

    fun begin(point: InkPoint): Result<RenderFrame> = rendererResult("renderer.begin") {
        synchronized(lock) {
            requireState(State.Idle, "begin")
            val frame = (nativePen as NeoPen)
                .onPenDown(point.toNativePoint() as TouchPoint, false)
                .toFrame(configuration)
            state = State.Drawing
            frame
        }
    }

    fun append(
        points: List<InkPoint>,
        prediction: InkPoint? = null,
    ): Result<RenderFrame> = rendererResult("renderer.append") {
        require(points.isNotEmpty()) { "points must not be empty" }
        synchronized(lock) {
            requireState(State.Drawing, "append")
            val chunks = points.chunked(NATIVE_POINT_CHUNK)
            chunks.mapIndexed { index, chunk ->
                (nativePen as NeoPen).onPenMove(
                    chunk.map { it.toNativePoint() as TouchPoint },
                    prediction?.takeIf { index == chunks.lastIndex }
                        ?.toNativePoint() as? TouchPoint,
                    false,
                )
            }.toFrame(configuration)
        }
    }

    fun end(point: InkPoint): Result<RenderFrame> = rendererResult("renderer.end") {
        synchronized(lock) {
            requireState(State.Drawing, "end")
            val frame = (nativePen as NeoPen)
                .onPenUp(point.toNativePoint() as TouchPoint, false)
                .toFrame(configuration)
            state = State.Idle
            frame
        }
    }

    /** Runs the same down/move/up sequence on an isolated native handle. */
    fun render(points: List<InkPoint>): Result<RenderFrame> = rendererResult("renderer.render") {
        require(points.isNotEmpty()) { "points must not be empty" }
        synchronized(lock) {
            requireState(State.Idle, "render")
            val offline = checkNotNull(createNativePen(kind, configuration) as? NeoPen) {
                "Native renderer creation returned null"
            }
            try {
                val outputs = ArrayList<Pair<PenResult?, PenResult?>>(3)
                outputs += offline.onPenDown(points.first().toNativePoint() as TouchPoint, false)
                if (points.size > 2) {
                    points.subList(1, points.lastIndex).chunked(NATIVE_POINT_CHUNK).forEach { chunk ->
                        outputs += offline.onPenMove(
                            chunk.map { it.toNativePoint() as TouchPoint },
                            null,
                            false,
                        )
                    }
                }
                outputs += offline.onPenUp(points.last().toNativePoint() as TouchPoint, false)
                outputs.toFrame(configuration)
            } finally {
                offline.destroy()
            }
        }
    }

    override fun close() {
        synchronized(lock) {
            if (state == State.Closed) return
            (nativePen as NeoPen).destroy()
            state = State.Closed
        }
    }

    private fun requireState(expected: State, operation: String) {
        check(state == expected) { "$operation requires $expected, actual state is $state" }
    }

    companion object {
        private const val NATIVE_POINT_CHUNK = 1_024

        fun create(
            kind: PenKind,
            configuration: BrushConfiguration = BrushConfiguration(),
        ): Result<InkRenderer> = rendererResult("renderer.create") {
            val pen = checkNotNull(createNativePen(kind, configuration)) {
                "Native renderer creation returned null for $kind"
            }
            InkRenderer(kind, configuration, pen)
        }
    }
}

private inline fun <T> rendererResult(operation: String, block: () -> T): Result<T> {
    val started = System.nanoTime()
    return try {
        val result = block()
        OnyxDiagnostics.record(
            operation,
            FirmwareBackendKind.NATIVE_RENDERER,
            FirmwareDiagnosticPhase.DISPATCH,
            FirmwareDiagnosticStatus.SUCCEEDED,
            started,
        )
        Result.success(result)
    } catch (failure: Throwable) {
        val diagnostic = OnyxDiagnostics.record(
            operation,
            FirmwareBackendKind.NATIVE_RENDERER,
            FirmwareDiagnosticPhase.DISPATCH,
            FirmwareDiagnosticStatus.FAILED,
            started,
            failure,
        )
        Result.failure(
            when (failure) {
                is IllegalArgumentException -> OnyxFailure.InvalidArgument(
                    operation,
                    failure.message ?: "Invalid renderer argument",
                )
                is OnyxFailure -> failure
                else -> OnyxFailure.NativeRendererFailure(
                    operation,
                    diagnostic.id,
                    failure.message ?: failure.javaClass.simpleName,
                    failure,
                )
            },
        )
    }
}

/** Return type is erased to keep this compiler-generated bridge out of the public descriptor. */
private fun InkPoint.toNativePoint(): Any = TouchPoint(
    xPx,
    yPx,
    normalizedPressure,
    0f,
    tiltX,
    tiltY,
    eventTimeNanos,
)

private fun Pair<PenResult?, PenResult?>.toFrame(
    configuration: BrushConfiguration,
): RenderFrame = listOf(this).toFrame(configuration)

private fun List<Pair<PenResult?, PenResult?>>.toFrame(
    configuration: BrushConfiguration,
): RenderFrame {
    val committed = mapNotNull { it.first }
    val prediction = lastOrNull { it.second != null }?.second
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = configuration.color
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeWidth = configuration.widthPx
    }
    return RenderFrame(
        committed = committed.toLayer(paint),
        predicted = listOfNotNull(prediction).toLayer(paint),
    )
}

private fun List<PenResult>.toLayer(paint: Paint): RenderLayer? {
    if (isEmpty()) return null
    val bounds = RectF(first().rect)
    drop(1).forEach { bounds.union(it.rect) }
    return RenderLayer(map { it as Any }, bounds, paint)
}

@OptIn(ExperimentalOnyxRendererApi::class)
private fun createNativePen(kind: PenKind, brush: BrushConfiguration): Any? {
    val config = NeoPenConfig().apply {
        type = kind.nativeType
        color = brush.color
        width = brush.widthPx
        maxTouchPressure = 1f
        tiltEnabled = brush.tiltEnabled
        fastMode = brush.fastMode
    }
    return when (kind) {
        PenKind.BALLPOINT -> NeoBallpointInkPen.Companion.create(config)
        PenKind.FOUNTAIN -> NeoFountainPen.Companion.create(config)
        PenKind.FOUNTAIN_V2 -> NeoFountainPenV2.Companion.create(config)
        PenKind.PENCIL -> NeoPencilPen.Companion.create(config)
        PenKind.CHARCOAL -> NeoCharcoalPen.Companion.create(config)
        PenKind.SQUARE -> NeoSquarePen.Companion.create(config)
        PenKind.BRUSH -> NeoBrushPen.Companion.create(config)
        PenKind.MARKER -> NeoMarkerPen.Companion.create(config)
        PenKind.CHARCOAL_V2 -> NeoCharcoalPenV2.Companion.create(config)
    }
}

@OptIn(ExperimentalOnyxRendererApi::class)
private val PenKind.nativeType: Int
    get() = when (this) {
        PenKind.BALLPOINT -> NeoPenConfig.NEOPEN_PEN_TYPE_BALLPOINT
        PenKind.FOUNTAIN -> NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN
        PenKind.FOUNTAIN_V2 -> NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2
        PenKind.PENCIL -> NeoPenConfig.NEOPEN_PEN_TYPE_PENCIL
        PenKind.CHARCOAL -> NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL
        PenKind.SQUARE -> NeoPenConfig.NEOPEN_PEN_TYPE_SQUARE
        PenKind.BRUSH -> NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH
        PenKind.MARKER -> NeoPenConfig.NEOPEN_PEN_TYPE_MARKER
        PenKind.CHARCOAL_V2 -> NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL_V2
    }
