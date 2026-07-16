@file:Suppress(
    // Renderer lifecycle and native record decoding are intentionally stateful and iterative.
    "AvoidFirstOrLastOnList",
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "MagicNumber",
    "NestedBlockDepth",
    "NoCallbacksInFunctions",
    "NoVarsInConstructor",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticStatus
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.model.BrushConfiguration
import com.onyx.android.sdk.ktx.model.BrushShape
import com.onyx.android.sdk.ktx.model.ExperimentalOnyxRendererApi
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenKind
import com.onyx.android.sdk.pennative.NeoPenNative
import com.onyx.android.sdk.pennative.PenConfig
import com.onyx.android.sdk.pennative.PenInk
import com.onyx.android.sdk.pennative.PenInkResult
import java.io.Closeable
import kotlin.math.max

private sealed interface NativeDrawCommand {
    val bounds: RectF
    fun draw(canvas: Canvas, paint: Paint)
}

private class NativePathCommand(
    private val path: Path,
    override val bounds: RectF,
) : NativeDrawCommand {
    override fun draw(canvas: Canvas, paint: Paint) = canvas.drawPath(path, paint)
}

private class NativePointCommand(
    private val x: Float,
    private val y: Float,
    private val radius: Float,
    private val alpha: Int? = null,
) : NativeDrawCommand {
    override val bounds = RectF(x - radius, y - radius, x + radius, y + radius)

    override fun draw(canvas: Canvas, paint: Paint) {
        if (alpha == null) {
            canvas.drawCircle(x, y, radius, paint)
        } else {
            canvas.drawCircle(x, y, radius, Paint(paint).apply { this.alpha = alpha })
        }
    }
}

private class NativeBitmapCommand(
    private val bitmap: Bitmap,
    private val x: Float,
    private val y: Float,
) : NativeDrawCommand {
    override val bounds = RectF(x, y, x + bitmap.width, y + bitmap.height)

    override fun draw(canvas: Canvas, paint: Paint) = canvas.drawBitmap(bitmap, x, y, paint)
}

/** Immutable render output backed only by the modern pennative result format. */
class RenderLayer internal constructor(
    private val drawCommands: List<(Canvas, Paint) -> Unit>,
    bounds: RectF,
    paint: Paint,
) {
    private val immutableBounds = RectF(bounds)
    private val immutablePaint = Paint(paint)

    /** Defensive copy of the layer's document-space bounds. */
    val bounds: RectF
        get() = RectF(immutableBounds)

    /** Draws the immutable native-renderer output onto [canvas]. */
    fun draw(canvas: Canvas) {
        drawCommands.forEach { it(canvas, immutablePaint) }
    }
}

/**
 * Output of one native-renderer operation.
 *
 * @property committed Stable output that can be retained in the document.
 * @property predicted Speculative output that should be replaced by the next frame.
 */
class RenderFrame internal constructor(
    val committed: RenderLayer?,
    val predicted: RenderLayer?,
) {
    /** Union of committed and predicted layer bounds. */
    val bounds: RectF
        get() = when {
            committed == null -> predicted?.bounds ?: RectF()
            predicted == null -> committed.bounds
            else -> committed.bounds.apply { union(predicted.bounds) }
        }

    /** Draws committed output and, when requested, predicted output onto [canvas]. */
    fun draw(canvas: Canvas, includePrediction: Boolean = true) {
        committed?.draw(canvas)
        if (includePrediction) predicted?.draw(canvas)
    }

    /** Draws using an explicit document-to-view transform without mutating input points. */
    fun draw(
        canvas: Canvas,
        transform: Matrix,
        includePrediction: Boolean = true,
    ) {
        val checkpoint = canvas.save()
        try {
            canvas.concat(transform)
            draw(canvas, includePrediction)
        } finally {
            canvas.restoreToCount(checkpoint)
        }
    }
}

/**
 * Deterministically owned renderer backed exclusively by `libneopen_jni.so` and the
 * `com.onyx.android.sdk.pennative` JNI contract.
 */
class InkRenderer private constructor(
    private val kind: PenKind,
    private val configuration: BrushConfiguration,
    private var nativeHandle: Long,
) : Closeable {
    private sealed interface State {
        data object Idle : State
        data object Drawing : State
        data object Closed : State
    }

    private val lock = Any()
    private var state: State = State.Idle

    /** Begins a stroke with [point] and returns the first native-renderer frame. */
    fun begin(point: InkPoint): Result<RenderFrame> = rendererResult("renderer.begin") {
        synchronized(lock) {
            requireState(State.Idle, "begin")
            val frame = NeoPenNative.onPenDown(
                nativeHandle,
                point.toNativePoint(),
                false,
            ).toFrame(configuration)
            state = State.Drawing
            frame
        }
    }

    /** Appends ordered [points], optionally including a speculative [prediction]. */
    fun append(
        points: List<InkPoint>,
        prediction: InkPoint? = null,
    ): Result<RenderFrame> = rendererResult("renderer.append") {
        require(points.isNotEmpty()) { "points must not be empty" }
        synchronized(lock) {
            requireState(State.Drawing, "append")
            val chunks = points.chunked(NATIVE_POINT_CHUNK)
            chunks.mapIndexed { index, chunk ->
                NeoPenNative.onPenMove(
                    nativeHandle,
                    chunk.toNativePoints(),
                    prediction?.takeIf { index == chunks.lastIndex }?.toNativePoint(),
                    false,
                )
            }.toFrame(configuration)
        }
    }

    /** Ends the active stroke at [point] and returns its final frame. */
    fun end(point: InkPoint): Result<RenderFrame> = rendererResult("renderer.end") {
        synchronized(lock) {
            requireState(State.Drawing, "end")
            val frame = NeoPenNative.onPenUp(
                nativeHandle,
                point.toNativePoint(),
                false,
            ).toFrame(configuration)
            state = State.Idle
            frame
        }
    }

    /** Runs the same down/move/up sequence on an isolated modern native handle. */
    fun render(points: List<InkPoint>): Result<RenderFrame> = rendererResult("renderer.render") {
        require(points.isNotEmpty()) { "points must not be empty" }
        synchronized(lock) {
            requireState(State.Idle, "render")
            val offline = createNativeHandle(kind, configuration)
            try {
                val outputs = ArrayList<PenInkResult?>(3)
                outputs += NeoPenNative.onPenDown(offline, points.first().toNativePoint(), false)
                if (points.size > 2) {
                    points.subList(1, points.lastIndex).chunked(NATIVE_POINT_CHUNK).forEach { chunk ->
                        outputs += NeoPenNative.onPenMove(
                            offline,
                            chunk.toNativePoints(),
                            null,
                            false,
                        )
                    }
                }
                outputs += NeoPenNative.onPenUp(offline, points.last().toNativePoint(), false)
                outputs.toFrame(configuration)
            } finally {
                NeoPenNative.destroyPen(offline)
            }
        }
    }

    /** Releases the native renderer handle; repeated calls are safe. */
    override fun close() {
        synchronized(lock) {
            if (state == State.Closed) return
            NeoPenNative.destroyPen(nativeHandle)
            nativeHandle = 0
            state = State.Closed
        }
    }

    private fun requireState(expected: State, operation: String) {
        check(state == expected) { "$operation requires $expected, actual state is $state" }
    }

    /** Creates independently owned native renderer instances. */
    companion object {
        private const val NATIVE_POINT_CHUNK = 1_024

        /** Creates an independently owned renderer for [kind] and [configuration]. */
        fun create(
            kind: PenKind,
            configuration: BrushConfiguration = BrushConfiguration(),
        ): Result<InkRenderer> = rendererResult("renderer.create") {
            ModernNativeRuntime.ensureLoggerRegistered()
            InkRenderer(kind, configuration, createNativeHandle(kind, configuration))
        }
    }
}

private object ModernNativeRuntime {
    @Volatile
    private var loggerRegistered = false

    fun ensureLoggerRegistered() {
        if (loggerRegistered) return
        synchronized(this) {
            if (loggerRegistered) return
            val logger = NeoPenNative.createLogger()
            check(logger != 0L) { "Native logger creation returned zero" }
            NeoPenNative.registerLogger(logger)
            loggerRegistered = true
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
                is IllegalStateException -> OnyxFailure.InvalidState(
                    operation,
                    diagnostic.id,
                    failure.message ?: "Invalid renderer state",
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

private fun InkPoint.toNativePoint(): DoubleArray = doubleArrayOf(
    xPx.toDouble(),
    yPx.toDouble(),
    normalizedPressure.toDouble(),
    0.0,
    tiltX.toDouble(),
    tiltY.toDouble(),
    eventTimeNanos.toDouble(),
)

private fun List<InkPoint>.toNativePoints(): DoubleArray {
    val output = DoubleArray(size * NATIVE_VALUES_PER_POINT)
    var offset = 0
    forEach { point ->
        output[offset] = point.xPx.toDouble()
        output[offset + 1] = point.yPx.toDouble()
        output[offset + 2] = point.normalizedPressure.toDouble()
        output[offset + 3] = 0.0
        output[offset + 4] = point.tiltX.toDouble()
        output[offset + 5] = point.tiltY.toDouble()
        output[offset + 6] = point.eventTimeNanos.toDouble()
        offset += NATIVE_VALUES_PER_POINT
    }
    return output
}

private fun PenInkResult?.toFrame(
    configuration: BrushConfiguration,
): RenderFrame = listOf(this).toFrame(configuration)

private fun List<PenInkResult?>.toFrame(
    configuration: BrushConfiguration,
): RenderFrame {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = configuration.color
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeWidth = configuration.widthPx
    }
    val committed = mapNotNull { it?.realInk }.toLayer(paint)
    val prediction = lastOrNull { it?.predictionInk?.isEmpty() == false }?.predictionInk
    return RenderFrame(
        committed = committed,
        predicted = listOfNotNull(prediction).toLayer(paint),
    )
}

private fun PenInk.isEmpty(): Boolean =
    points.isEmpty() && pointSizeArray.isEmpty() && bitmaps.isEmpty()

private fun List<PenInk>.toLayer(paint: Paint): RenderLayer? {
    val commands = flatMap { it.toDrawCommands() }
    if (commands.isEmpty()) return null
    val bounds = RectF(commands.first().bounds)
    commands.drop(1).forEach { bounds.union(it.bounds) }
    return RenderLayer(
        commands.map { command -> { canvas, drawingPaint -> command.draw(canvas, drawingPaint) } },
        bounds,
        paint,
    )
}

private fun PenInk.toDrawCommands(): List<NativeDrawCommand> {
    if (bitmaps.isNotEmpty()) {
        return bitmaps.mapIndexedNotNull { index, bitmap ->
            val offset = index * 2
            if (offset + 1 >= points.size) null
            else NativeBitmapCommand(bitmap, points[offset], points[offset + 1])
        }
    }

    val commands = ArrayList<NativeDrawCommand>(pointSizeArray.size)
    var offset = 0
    pointSizeArray.forEach { recordSize ->
        val end = offset + recordSize
        if (recordSize <= 0 || end > points.size) return@forEach
        when {
            recordSize == 3 -> {
                commands += NativePointCommand(
                    points[offset],
                    points[offset + 1],
                    max(points[offset + 2] * 0.5f, 0.25f),
                )
            }
            recordSize == 5 -> {
                commands += NativePointCommand(
                    points[offset],
                    points[offset + 1],
                    max(points[offset + 2] * 0.5f, 0.25f),
                    (points[offset + 4].coerceIn(0f, 1f) * 255).toInt(),
                )
            }
            recordSize >= 4 && recordSize % 2 == 0 -> {
                val path = Path().apply {
                    moveTo(points[offset], points[offset + 1])
                    var pointOffset = offset + 2
                    while (pointOffset + 1 < end) {
                        lineTo(points[pointOffset], points[pointOffset + 1])
                        pointOffset += 2
                    }
                    close()
                }
                val bounds = RectF()
                path.computeBounds(bounds, true)
                commands += NativePathCommand(path, bounds)
            }
        }
        offset = end
    }
    return commands
}

@OptIn(ExperimentalOnyxRendererApi::class)
private fun createNativeHandle(kind: PenKind, brush: BrushConfiguration): Long {
    val config = PenConfig().apply {
        type = kind.nativeType
        fastMode = brush.fastMode
        color = brush.color
        width = brush.widthPx
        minWidth = brush.minWidthPx
        rotateAngle = brush.rotateAngleDegrees
        tiltEnabled = brush.tiltEnabled
        tiltScale = brush.tiltScale
        directionEnabled = brush.directionEnabled
        maxTouchPressure = brush.maxTouchPressure
        dpi = brush.dpi
        displayScaleX = brush.displayScaleX
        displayScaleY = brush.displayScaleY
        scalePrecision = brush.scalePrecision
        brushSpacing = brush.brushSpacing
        brushShape = brush.brushShape.nativeType
        brushRatio = brush.brushRatio
        brushAngle = brush.brushAngleDegrees
        pressureSensitivity = brush.pressureSensitivity
        velocitySensitivity = brush.velocitySensitivity
        velocityAmplifier = brush.velocityAmplifier
        velocityIgnoreThreshold = brush.velocityIgnoreThreshold
        velocityLowerBound = brush.velocityLowerBound
        velocityUpperBound = brush.velocityUpperBound
        smoothLevel = brush.smoothLevel
        startPointLimit = brush.startPointLimit
        startLengthLimit = brush.startLengthLimit
        endVelocitySensitivity = brush.endVelocitySensitivity
        endThinningRate = brush.endThinningRate
        ignorePressure = brush.ignorePressure
    }
    return NeoPenNative.createPen(kind.nativeType, config).also { handle ->
        check(handle != 0L) { "Modern native renderer creation returned zero for $kind" }
    }
}

@OptIn(ExperimentalOnyxRendererApi::class)
private val PenKind.nativeType: Int
    get() = when (this) {
        PenKind.BRUSH -> PenConfig.NEOPEN_PEN_TYPE_BRUSH
        PenKind.FOUNTAIN -> PenConfig.NEOPEN_PEN_TYPE_FOUNTAIN
        PenKind.MARKER -> PenConfig.NEOPEN_PEN_TYPE_MARKER
        PenKind.CHARCOAL -> PenConfig.NEOPEN_PEN_TYPE_CHARCOAL
        PenKind.CHARCOAL_V2 -> PenConfig.NEOPEN_PEN_TYPE_CHARCOAL_V2
        PenKind.FOUNTAIN_V2 -> PenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2
        PenKind.PENCIL -> PenConfig.NEOPEN_PEN_TYPE_PENCIL
        PenKind.BALLPOINT -> PenConfig.NEOPEN_PEN_TYPE_BALLPOINT
        PenKind.SQUARE -> PenConfig.NEOPEN_PEN_TYPE_SQUARE
        PenKind.BRUSH_SIGN -> PenConfig.NEOPEN_PEN_TYPE_BRUSH_SIGN
    }

private val BrushShape.nativeType: Int
    get() = when (this) {
        BrushShape.CIRCLE -> PenConfig.NEOPEN_BRUSH_SHAPE_CIRCLE
        BrushShape.ELLIPSE -> PenConfig.NEOPEN_BRUSH_SHAPE_ELLIPSE
        BrushShape.RECTANGLE -> PenConfig.NEOPEN_BRUSH_SHAPE_RECTANGLE
    }

private const val NATIVE_VALUES_PER_POINT = 7
