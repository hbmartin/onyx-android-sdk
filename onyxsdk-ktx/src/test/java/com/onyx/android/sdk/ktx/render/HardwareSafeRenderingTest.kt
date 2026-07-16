package com.onyx.android.sdk.ktx.render

import com.onyx.android.sdk.ktx.capabilities.DisplayColorMode
import com.onyx.android.sdk.ktx.capabilities.NativeAlphaSupport
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HardwareSafeRenderingTest {
    @Test
    fun translucentColorIsCompositedAndConvertedForMonochromeHardware() {
        val result = 0x8000ff00.toInt().toHardwareSafeColor(
            HardwareRenderingProfile(
                colorMode = DisplayColorMode.MONOCHROME,
                colorDepthBits = 4,
                nativeAlpha = NativeAlphaSupport.OPAQUE_ONLY,
                maximumHardwareStrokeWidthPx = 20f,
            ),
        )

        assertEquals(0xff, result.color ushr 24)
        assertEquals(RenderingPath.HARDWARE_OVERLAY, result.path)
        assertTrue(RenderingWarning.ALPHA_COMPOSITED in result.warnings)
        assertTrue(RenderingWarning.COLOR_CONVERTED_TO_GRAYSCALE in result.warnings)
    }

    @Test
    fun monochromeSoftwareFallbackPreservesAuthoredAlpha() {
        val result = 0x4000ff00.toInt().toHardwareSafeColor(
            HardwareRenderingProfile(
                colorMode = DisplayColorMode.MONOCHROME,
                colorDepthBits = 4,
                nativeAlpha = NativeAlphaSupport.OPAQUE_ONLY,
                maximumHardwareStrokeWidthPx = 20f,
            ),
            alphaPolicy = AlphaPolicy.PRESERVE_USING_SOFTWARE,
        )

        assertEquals(0x40, result.color ushr 24)
        assertEquals(RenderingPath.SOFTWARE_CANVAS, result.path)
        assertTrue(RenderingWarning.ALPHA_REQUIRES_SOFTWARE in result.warnings)
    }

    @Test
    fun unknownWidthLimitChoosesLosslessSoftwareFallback() {
        val result = 200f.toHardwareSafeStrokeWidth(
            HardwareRenderingProfile(
                colorMode = DisplayColorMode.COLOR,
                colorDepthBits = null,
                nativeAlpha = NativeAlphaSupport.UNVERIFIED,
                maximumHardwareStrokeWidthPx = null,
            ),
        )

        assertEquals(RenderingPath.SOFTWARE_CANVAS, result.path)
        assertEquals(null, result.hardwareWidthPx)
    }
}
