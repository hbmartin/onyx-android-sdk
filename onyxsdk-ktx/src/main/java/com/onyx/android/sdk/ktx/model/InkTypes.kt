@file:Suppress(
    // Numeric bounds mirror the native renderer configuration contract.
    "MagicNumber",
)

package com.onyx.android.sdk.ktx.model

import android.graphics.Rect
import android.graphics.RectF
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/** Typed E Ink waveform/update mode accepted by KTX refresh APIs. */
enum class EpdUpdateMode {
    AUTO,
    DU,
    DU4,
    GU,
    GU_FAST,
    GC4,
    GC16,
    DEEP_GC,
    REGAL,
    REGAL_D,
    REGAL_PLUS,
    A2,
    HANDWRITING_REPAINT,
}

/** Portion of a view to refresh. */
sealed interface RefreshScope {
    /** Refresh the entire view. */
    data object FullView : RefreshScope

    /**
     * Refresh a rectangular portion of the view.
     *
     * @property bounds Region expressed in view-local physical pixels.
     */
    data class Region(val bounds: Rect) : RefreshScope
}

/** Policy used when another session owns the process-wide raw-ink lease. */
enum class LeasePolicy { FAIL_FAST, WAIT }

/** Firmware interpretation of raw-ink limit and exclusion regions. */
enum class RegionMode { MULTI_REGION, SINGLE_REGION }

/** Physical pen tool associated with an input point or stroke. */
enum class InkTool { PEN, SIDE_ERASER, TAIL_ERASER }

/** Firmware stroke style used for overlay rendering. */
enum class StrokeStyle(internal val firmwareValue: Int) {
    PENCIL(0),
    FOUNTAIN(1),
    MARKER(2),
    BRUSH(3),
    CHARCOAL(4),
    DASH(5),
    CHARCOAL_V2(6),
    SQUARE(7),
}
/** Shape used by the native renderer to stamp brush samples. */
enum class BrushShape { CIRCLE, ELLIPSE, RECTANGLE }

/** Coordinate system used by raw input values. */
enum class CoordinateSpace { VIEW_LOCAL_PHYSICAL_PIXELS }

/** Clock used by raw input timestamps. */
enum class TimestampClock { MONOTONIC_INPUT_NANOSECONDS }

/** Unit represented by raw tilt values. */
enum class TiltUnit { RAW_INPUT_AXIS }

/** Lifecycle phase of a raw pen event. */
enum class PenPhase { DOWN, MOVE, UP, PROXIMITY }

/** Firmware handwriting state associated with a view. */
enum class PenState(internal val firmwareValue: Int) {
    STOPPED(0),
    STARTED(1),
    DRAWING(2),
    PAUSED(3),
    ERASING(4),
}
/** Whether firmware turbo rendering is enabled. */
enum class TurboMode(internal val firmwareValue: Int) { DISABLED(0), ENABLED(1) }

/**
 * Firmware eraser-style identifier.
 *
 * @property firmwareValue Non-negative value passed to the firmware.
 */
@JvmInline
value class EraserStyle private constructor(val firmwareValue: Int) {
    /** Factory and default values for [EraserStyle]. */
    companion object {
        /** Default eraser style used by the SDK. */
        val DEFAULT = EraserStyle(5)

        /** Creates a style for a non-negative firmware [value]. */
        fun firmware(value: Int): EraserStyle {
            require(value >= 0) { "eraser style must be non-negative" }
            return EraserStyle(value)
        }
    }
}

/**
 * Typed dash geometry retained without exposing the firmware float array.
 *
 * @property onLengthPx Length of each painted segment in physical pixels.
 * @property offLengthPx Length of each gap in physical pixels.
 * @property phasePx Non-negative offset into the repeating dash pattern.
 */
data class DashPattern(
    val onLengthPx: Float,
    val offLengthPx: Float,
    val phasePx: Float = 0f,
) {
    init {
        require(onLengthPx.isFinite() && onLengthPx > 0f) { "onLengthPx must be positive" }
        require(offLengthPx.isFinite() && offLengthPx > 0f) { "offLengthPx must be positive" }
        require(phasePx.isFinite() && phasePx >= 0f) { "phasePx must be non-negative" }
    }
}

/** Marks renderer features that require explicit opt-in and validation on target firmware. */
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This renderer is unstable on known BOOX firmware and requires device validation.",
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class ExperimentalOnyxRendererApi

/** Native pen renderer selected for a stroke. */
enum class PenKind {
    BALLPOINT,
    FOUNTAIN,
    FOUNTAIN_V2,
    PENCIL,
    CHARCOAL,
    SQUARE,

    @ExperimentalOnyxRendererApi
    BRUSH,

    @ExperimentalOnyxRendererApi
    MARKER,

    @ExperimentalOnyxRendererApi
    CHARCOAL_V2,

    @ExperimentalOnyxRendererApi
    BRUSH_SIGN,
}

/**
 * Complete brush configuration shared by raw overlay and native renderer APIs.
 *
 * @property style Firmware overlay stroke style.
 * @property color Packed ARGB stroke color.
 * @property widthPx Nominal stroke width in physical pixels.
 * @property tiltEnabled Whether tilt affects native-rendered stroke geometry.
 * @property fastMode Whether the native renderer favors its fast path.
 * @property minWidthPx Minimum native-rendered stroke width in physical pixels.
 * @property rotateAngleDegrees Rotation applied by the native renderer.
 * @property tiltScale Native renderer multiplier for input tilt.
 * @property directionEnabled Whether stroke direction affects native rendering.
 * @property maxTouchPressure Pressure ceiling expected by the native renderer.
 * @property dpi Display density passed to the native renderer.
 * @property displayScaleX Horizontal display scale passed to the native renderer.
 * @property displayScaleY Vertical display scale passed to the native renderer.
 * @property scalePrecision Native coordinate-scale precision.
 * @property brushSpacing Spacing between native brush samples, in the range `0.1..1`.
 * @property brushShape Shape used to stamp native brush samples.
 * @property brushRatio Aspect ratio of non-circular brush shapes.
 * @property brushAngleDegrees Brush-shape rotation in degrees.
 * @property pressureSensitivity Native pressure response, in the range `0..1`.
 * @property velocitySensitivity Native velocity response, in the range `0..1`.
 * @property velocityAmplifier Native velocity amplification, in the range `0..1`.
 * @property velocityIgnoreThreshold Velocity below which variation is ignored.
 * @property velocityLowerBound Lower velocity bound used for width modulation.
 * @property velocityUpperBound Upper velocity bound used for width modulation.
 * @property smoothLevel Native smoothing strength, in the range `0..1`.
 * @property startPointLimit Initial point limit used by native tapering.
 * @property startLengthLimit Initial length limit used by native tapering.
 * @property endVelocitySensitivity End-of-stroke velocity response, in the range `0..1`.
 * @property endThinningRate End-of-stroke thinning strength, in the range `0..1`.
 * @property ignorePressure Amount of pressure variation ignored, in the range `0..1`.
 * @property dashPattern Dash geometry required when [style] is [StrokeStyle.DASH].
 */
data class BrushConfiguration(
    val style: StrokeStyle = StrokeStyle.FOUNTAIN,
    val color: Int = 0xff000000.toInt(),
    val widthPx: Float = 3f,
    val tiltEnabled: Boolean = true,
    val fastMode: Boolean = false,
    val minWidthPx: Float = 0f,
    val rotateAngleDegrees: Int = 0,
    val tiltScale: Float = 0f,
    val directionEnabled: Boolean = false,
    val maxTouchPressure: Float = 1f,
    val dpi: Float = 320f,
    val displayScaleX: Float = 1f,
    val displayScaleY: Float = 1f,
    val scalePrecision: Float = 1f,
    val brushSpacing: Float = 0.25f,
    val brushShape: BrushShape = BrushShape.CIRCLE,
    val brushRatio: Float = 5f,
    val brushAngleDegrees: Float = 0f,
    val pressureSensitivity: Float = 0f,
    val velocitySensitivity: Float = 0f,
    val velocityAmplifier: Float = 0f,
    val velocityIgnoreThreshold: Float = 0f,
    val velocityLowerBound: Float = 0f,
    val velocityUpperBound: Float = 0f,
    val smoothLevel: Float = 0.2f,
    val startPointLimit: Float = 0f,
    val startLengthLimit: Float = 0f,
    val endVelocitySensitivity: Float = 0f,
    val endThinningRate: Float = 0f,
    val ignorePressure: Float = 0f,
    val dashPattern: DashPattern? = null,
) {
    init {
        require(widthPx.isFinite() && widthPx > 0f) { "widthPx must be finite and positive" }
        require(minWidthPx.isFinite() && minWidthPx >= 0f) {
            "minWidthPx must be finite and non-negative"
        }
        require(tiltScale.isFinite()) { "tiltScale must be finite" }
        require(maxTouchPressure.isFinite() && maxTouchPressure > 0f) {
            "maxTouchPressure must be finite and positive"
        }
        require(dpi.isFinite() && dpi > 0f) { "dpi must be finite and positive" }
        require(displayScaleX.isFinite() && displayScaleX > 0f) {
            "displayScaleX must be finite and positive"
        }
        require(displayScaleY.isFinite() && displayScaleY > 0f) {
            "displayScaleY must be finite and positive"
        }
        require(scalePrecision.isFinite() && scalePrecision > 0f) {
            "scalePrecision must be finite and positive"
        }
        require(brushSpacing in 0.1f..1f) { "brushSpacing must be in 0.1..1" }
        require(brushRatio.isFinite() && brushRatio > 0f) {
            "brushRatio must be finite and positive"
        }
        require(brushAngleDegrees.isFinite()) { "brushAngleDegrees must be finite" }
        require(pressureSensitivity in 0f..1f) { "pressureSensitivity must be in 0..1" }
        require(velocitySensitivity in 0f..1f) { "velocitySensitivity must be in 0..1" }
        require(velocityAmplifier in 0f..1f) { "velocityAmplifier must be in 0..1" }
        require(velocityIgnoreThreshold in 0f..50f) {
            "velocityIgnoreThreshold must be in 0..50"
        }
        require(velocityLowerBound in 0f..50f) {
            "velocityLowerBound must be in 0..50"
        }
        require(velocityUpperBound in 0f..50f) {
            "velocityUpperBound must be in 0..50"
        }
        require(velocityLowerBound <= velocityUpperBound) {
            "velocityLowerBound must be less than or equal to velocityUpperBound"
        }
        require(smoothLevel in 0f..1f) { "smoothLevel must be in 0..1" }
        require(startPointLimit.isFinite() && startPointLimit >= 0f) {
            "startPointLimit must be finite and non-negative"
        }
        require(startLengthLimit.isFinite() && startLengthLimit >= 0f) {
            "startLengthLimit must be finite and non-negative"
        }
        require(endVelocitySensitivity in 0f..1f) {
            "endVelocitySensitivity must be in 0..1"
        }
        require(endThinningRate in 0f..1f) { "endThinningRate must be in 0..1" }
        require(ignorePressure in 0f..1f) { "ignorePressure must be in 0..1" }
        require(dashPattern == null || style == StrokeStyle.DASH) {
            "dashPattern requires StrokeStyle.DASH"
        }
    }
}

/**
 * Eraser behavior for a raw-ink session.
 *
 * @property widthPx Eraser width in physical pixels.
 * @property sideButtonEnabled Whether the pen side button activates erasing.
 * @property style Firmware eraser-style identifier.
 */
data class EraserConfiguration(
    val widthPx: Float = 20f,
    val sideButtonEnabled: Boolean = true,
    val style: EraserStyle = EraserStyle.DEFAULT,
) {
    init {
        require(widthPx.isFinite() && widthPx > 0f) { "widthPx must be finite and positive" }
    }
}

/**
 * Immutable configuration applied whenever a raw-ink session starts or resumes.
 *
 * @property brush Brush behavior for pen input.
 * @property eraser Eraser behavior for eraser input.
 * @property limitRegions Regions in which raw ink is accepted.
 * @property excludeRegions Regions excluded from raw-ink input.
 * @property regionMode Firmware interpretation of the supplied regions.
 * @property maxPointsPerStroke Safety limit for retained points in one stroke.
 */
data class RawInkConfiguration(
    val brush: BrushConfiguration = BrushConfiguration(),
    val eraser: EraserConfiguration = EraserConfiguration(),
    val limitRegions: List<Rect> = emptyList(),
    val excludeRegions: List<Rect> = emptyList(),
    val regionMode: RegionMode = RegionMode.MULTI_REGION,
    val maxPointsPerStroke: Int = 100_000,
) {
    init {
        require(maxPointsPerStroke > 0) { "maxPointsPerStroke must be positive" }
    }
}

/**
 * Lossless raw input sample normalized for application use.
 *
 * @property xPx Horizontal view-local coordinate in physical pixels.
 * @property yPx Vertical view-local coordinate in physical pixels.
 * @property rawPressure Unmodified pressure-axis value.
 * @property normalizedPressure Pressure normalized to `0..1`.
 * @property maxPressure Maximum reported value of the pressure axis.
 * @property tiltX Unmodified horizontal tilt-axis value.
 * @property tiltY Unmodified vertical tilt-axis value.
 * @property eventTimeNanos Monotonic input-event timestamp in nanoseconds.
 * @property sequence Monotonically increasing event sequence number.
 * @property tool Physical tool that produced the sample.
 */
data class InkPoint(
    val xPx: Float,
    val yPx: Float,
    val rawPressure: Int,
    val normalizedPressure: Float,
    val maxPressure: Int,
    val tiltX: Int,
    val tiltY: Int,
    val eventTimeNanos: Long,
    val sequence: Long,
    val tool: InkTool,
) {
    /** All v2/KTX points use physical pixels relative to the bound host view. */
    val coordinateSpace: CoordinateSpace
        get() = CoordinateSpace.VIEW_LOCAL_PHYSICAL_PIXELS

    /** Clock used by [eventTimeNanos]. */
    val timestampClock: TimestampClock
        get() = TimestampClock.MONOTONIC_INPUT_NANOSECONDS

    /** Unit represented by [tiltX] and [tiltY]. */
    val tiltUnit: TiltUnit
        get() = TiltUnit.RAW_INPUT_AXIS

    init {
        require(xPx.isFinite() && yPx.isFinite()) { "Coordinates must be finite" }
        require(maxPressure > 0) { "maxPressure must be positive" }
        require(normalizedPressure in 0f..1f) { "normalizedPressure must be in 0..1" }
    }
}

/**
 * Immutable, lossless raw event; instances may be retained after collection.
 *
 * @property phase Lifecycle phase represented by this event.
 * @property point Raw input sample associated with the event.
 * @property outsideLimitRegion Whether the event fell outside configured limit regions.
 * @property forced Whether firmware marked the event as forced.
 */
data class PenEvent(
    val phase: PenPhase,
    val point: InkPoint,
    val outsideLimitRegion: Boolean = false,
    val forced: Boolean = false,
) {
    /** Coordinate system used by [point]. */
    val coordinateSpace: CoordinateSpace
        get() = point.coordinateSpace
}

/**
 * Completed raw stroke.
 *
 * @property tool Physical tool that produced the stroke.
 * @property points Ordered, lossless input samples.
 * @property endedOutsideLimitRegion Whether the final point was outside configured limits.
 * @property bounds View-local physical-pixel bounds of all [points].
 */
data class InkStroke(
    val tool: InkTool,
    val points: List<InkPoint>,
    val endedOutsideLimitRegion: Boolean,
    val bounds: RectF,
)

/**
 * Latest preview sample for an in-progress or just-completed stroke.
 *
 * @property point Raw input sample represented by the preview.
 * @property phase Preview lifecycle phase.
 */
data class InkPreview(
    val point: InkPoint,
    val phase: Phase,
) {
    /** Lifecycle phase represented by an [InkPreview]. */
    enum class Phase { DOWN, MOVE, UP }
}

/** Behavior requested when committing while the pen is down. */
sealed interface ActiveStrokePolicy {
    /** Reject the commit immediately while a stroke is active. */
    data object Reject : ActiveStrokePolicy

    /**
     * Wait for the active stroke to end.
     *
     * @property timeout Maximum duration to wait for pen-up.
     */
    data class WaitForPenUp(val timeout: Duration = 2.seconds) : ActiveStrokePolicy
}

/**
 * Controls bitmap commit coordination and refresh behavior.
 *
 * @property activeStrokePolicy Behavior when the pen is down at commit time.
 * @property updateMode E Ink update mode used for the committed region.
 * @property refreshTimeout Maximum duration of the precise firmware refresh wait.
 */
data class CommitOptions(
    val activeStrokePolicy: ActiveStrokePolicy = ActiveStrokePolicy.WaitForPenUp(),
    val updateMode: EpdUpdateMode = EpdUpdateMode.HANDWRITING_REPAINT,
    val refreshTimeout: Duration = 2.seconds,
)
