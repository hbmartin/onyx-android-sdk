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
}

data class BrushConfiguration(
    val style: StrokeStyle = StrokeStyle.FOUNTAIN,
    val color: Int = 0xff000000.toInt(),
    val widthPx: Float = 3f,
    val tiltEnabled: Boolean = true,
    val fastMode: Boolean = false,
) {
    init {
        require(widthPx.isFinite() && widthPx > 0f) { "widthPx must be finite and positive" }
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
