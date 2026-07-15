@file:Suppress(
    // Numeric bounds mirror the native renderer configuration contract.
    "MagicNumber",
)

package com.onyx.android.sdk.ktx.model

import android.graphics.Rect
import android.graphics.RectF
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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

sealed interface RefreshScope {
    data object FullView : RefreshScope
    data class Region(val bounds: Rect) : RefreshScope
}

enum class LeasePolicy { FAIL_FAST, WAIT }
enum class RegionMode { MULTI_REGION, SINGLE_REGION }
enum class InkTool { PEN, SIDE_ERASER, TAIL_ERASER }
enum class StrokeStyle { PENCIL, FOUNTAIN, MARKER, BRUSH, CHARCOAL, DASH, CHARCOAL_V2, SQUARE }
enum class BrushShape { CIRCLE, ELLIPSE, RECTANGLE }

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This renderer is unstable on known BOOX firmware and requires device validation.",
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class ExperimentalOnyxRendererApi

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
    }
}

data class EraserConfiguration(
    val widthPx: Float = 20f,
    val sideButtonEnabled: Boolean = true,
) {
    init {
        require(widthPx.isFinite() && widthPx > 0f) { "widthPx must be finite and positive" }
    }
}

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
    init {
        require(xPx.isFinite() && yPx.isFinite()) { "Coordinates must be finite" }
        require(maxPressure > 0) { "maxPressure must be positive" }
        require(normalizedPressure in 0f..1f) { "normalizedPressure must be in 0..1" }
    }
}

data class InkStroke(
    val tool: InkTool,
    val points: List<InkPoint>,
    val endedOutsideLimitRegion: Boolean,
    val bounds: RectF,
)

data class InkPreview(
    val point: InkPoint,
    val phase: Phase,
) {
    enum class Phase { DOWN, MOVE, UP }
}

sealed interface ActiveStrokePolicy {
    data object Reject : ActiveStrokePolicy
    data class WaitForPenUp(val timeout: Duration = 2.seconds) : ActiveStrokePolicy
}

data class CommitOptions(
    val activeStrokePolicy: ActiveStrokePolicy = ActiveStrokePolicy.WaitForPenUp(),
    val updateMode: EpdUpdateMode = EpdUpdateMode.HANDWRITING_REPAINT,
    val refreshTimeout: Duration = 2.seconds,
)
