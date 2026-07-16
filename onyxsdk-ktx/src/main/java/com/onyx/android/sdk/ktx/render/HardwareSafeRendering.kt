package com.onyx.android.sdk.ktx.render

import android.graphics.Matrix
import com.onyx.android.sdk.ktx.capabilities.DisplayColorMode
import com.onyx.android.sdk.ktx.capabilities.NativeAlphaSupport
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.model.InkPoint
import kotlin.math.roundToInt

enum class RenderingPath { HARDWARE_OVERLAY, SOFTWARE_CANVAS }
enum class AlphaPolicy { COMPOSITE_FOR_HARDWARE, PRESERVE_USING_SOFTWARE }
enum class RenderingWarning {
    COLOR_CAPABILITY_UNVERIFIED,
    COLOR_CONVERTED_TO_GRAYSCALE,
    ALPHA_COMPOSITED,
    ALPHA_REQUIRES_SOFTWARE,
    STROKE_WIDTH_LIMIT_UNVERIFIED,
    STROKE_WIDTH_REQUIRES_SOFTWARE,
}

data class HardwareRenderingProfile(
    val colorMode: DisplayColorMode,
    val colorDepthBits: Int?,
    val nativeAlpha: NativeAlphaSupport,
    val maximumHardwareStrokeWidthPx: Float?,
)

data class SafeColorResult(
    val color: Int,
    val path: RenderingPath,
    val warnings: Set<RenderingWarning>,
)

data class SafeStrokeWidthResult(
    val requestedWidthPx: Float,
    val hardwareWidthPx: Float?,
    val path: RenderingPath,
    val warnings: Set<RenderingWarning>,
)

fun OnyxDeviceCapabilities.hardwareRenderingProfile(): HardwareRenderingProfile =
    HardwareRenderingProfile(
        colorMode = rendering.colorMode.value ?: DisplayColorMode.UNKNOWN,
        colorDepthBits = rendering.colorDepthBits.value,
        nativeAlpha = rendering.nativeAlpha.value ?: NativeAlphaSupport.UNVERIFIED,
        maximumHardwareStrokeWidthPx = rendering.maximumHardwareStrokeWidthPx.value,
    )

/** Converts an authored color for the hardware overlay without changing the stored source color. */
fun Int.toHardwareSafeColor(
    profile: HardwareRenderingProfile,
    alphaPolicy: AlphaPolicy = AlphaPolicy.COMPOSITE_FOR_HARDWARE,
    backgroundColor: Int = OPAQUE_WHITE,
): SafeColorResult {
    val alpha = channel(this, ALPHA_SHIFT)
    val alphaSafe = if (
        alpha == CHANNEL_MAX || profile.nativeAlpha == NativeAlphaSupport.SUPPORTED
    ) {
        SafeColorResult(this, RenderingPath.HARDWARE_OVERLAY, emptySet())
    } else {
        when (alphaPolicy) {
            AlphaPolicy.PRESERVE_USING_SOFTWARE -> SafeColorResult(
                this,
                RenderingPath.SOFTWARE_CANVAS,
                setOf(RenderingWarning.ALPHA_REQUIRES_SOFTWARE),
            )
            AlphaPolicy.COMPOSITE_FOR_HARDWARE -> SafeColorResult(
                compositeOpaque(this, backgroundColor),
                RenderingPath.HARDWARE_OVERLAY,
                setOf(RenderingWarning.ALPHA_COMPOSITED),
            )
        }
    }
    return when (profile.colorMode) {
        DisplayColorMode.MONOCHROME -> {
            val gray = luminance(alphaSafe.color)
            alphaSafe.copy(
                color = argb(channel(alphaSafe.color, ALPHA_SHIFT), gray, gray, gray),
                warnings = alphaSafe.warnings + RenderingWarning.COLOR_CONVERTED_TO_GRAYSCALE,
            )
        }
        DisplayColorMode.COLOR -> alphaSafe
        DisplayColorMode.UNKNOWN -> alphaSafe.copy(
            path = RenderingPath.SOFTWARE_CANVAS,
            warnings = alphaSafe.warnings + RenderingWarning.COLOR_CAPABILITY_UNVERIFIED,
        )
    }
}

/** Selects hardware only when the requested width is inside an evidence-backed device limit. */
fun Float.toHardwareSafeStrokeWidth(
    profile: HardwareRenderingProfile,
): SafeStrokeWidthResult {
    require(isFinite() && this > 0f) { "stroke width must be finite and positive" }
    val maximum = profile.maximumHardwareStrokeWidthPx
        ?: return SafeStrokeWidthResult(
            requestedWidthPx = this,
            hardwareWidthPx = null,
            path = RenderingPath.SOFTWARE_CANVAS,
            warnings = setOf(RenderingWarning.STROKE_WIDTH_LIMIT_UNVERIFIED),
        )
    return if (this <= maximum) {
        SafeStrokeWidthResult(this, this, RenderingPath.HARDWARE_OVERLAY, emptySet())
    } else {
        SafeStrokeWidthResult(
            requestedWidthPx = this,
            hardwareWidthPx = maximum,
            path = RenderingPath.SOFTWARE_CANVAS,
            warnings = setOf(RenderingWarning.STROKE_WIDTH_REQUIRES_SOFTWARE),
        )
    }
}

/** Returns a copy transformed into another pixel coordinate space while retaining input metadata. */
fun InkPoint.transformed(matrix: Matrix): InkPoint {
    val values = floatArrayOf(xPx, yPx)
    matrix.mapPoints(values)
    return copy(xPx = values[0], yPx = values[1])
}

private fun compositeOpaque(foreground: Int, background: Int): Int {
    val alpha = channel(foreground, ALPHA_SHIFT) / CHANNEL_MAX.toFloat()
    fun composite(shift: Int): Int = (
        channel(foreground, shift) * alpha + channel(background, shift) * (1f - alpha)
        ).roundToInt().coerceIn(0, CHANNEL_MAX)
    return argb(CHANNEL_MAX, composite(RED_SHIFT), composite(GREEN_SHIFT), composite(BLUE_SHIFT))
}

private fun luminance(color: Int): Int = (
    channel(color, RED_SHIFT) * LUMINANCE_RED +
        channel(color, GREEN_SHIFT) * LUMINANCE_GREEN +
        channel(color, BLUE_SHIFT) * LUMINANCE_BLUE
    ).roundToInt().coerceIn(0, CHANNEL_MAX)

private fun channel(color: Int, shift: Int): Int = color ushr shift and CHANNEL_MAX
private fun argb(alpha: Int, red: Int, green: Int, blue: Int): Int =
    (alpha shl ALPHA_SHIFT) or
        (red shl RED_SHIFT) or
        (green shl GREEN_SHIFT) or
        (blue shl BLUE_SHIFT)

private const val CHANNEL_MAX = 255
private const val ALPHA_SHIFT = 24
private const val RED_SHIFT = 16
private const val GREEN_SHIFT = 8
private const val BLUE_SHIFT = 0
private const val OPAQUE_WHITE = -1
private const val LUMINANCE_RED = 0.2126f
private const val LUMINANCE_GREEN = 0.7152f
private const val LUMINANCE_BLUE = 0.0722f
