package com.onyx.android.sdk.ktx.render

import android.graphics.Bitmap
import android.graphics.Color
import com.onyx.android.sdk.ktx.PixelSize
import com.onyx.android.sdk.utils.BitmapUtils
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [35])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class BitmapRenderingTest {
    @Test
    fun omittedAndFalseFlagsMatchLegacyRendering() {
        val source = solidBitmap(17, 17)
        val legacy = BitmapUtils.roundCornerBitmap(source, 17, 17, 5f)
        val default = source.roundedCorners(radiusPx = 5f)
        val explicitFalse = source.roundedCorners(radiusPx = 5f, antiAlias = false)
        try {
            assertArrayEquals(legacy.pixels(), default.pixels())
            assertArrayEquals(legacy.pixels(), explicitFalse.pixels())
        } finally {
            source.recycle()
            legacy.recycle()
            default.recycle()
            explicitFalse.recycle()
        }
    }

    @Test
    fun trueFlagProducesSmoothCornerOutput() {
        val source = solidBitmap(17, 17)
        val smoothed = source.roundedCorners(radiusPx = 5f, antiAlias = true)
        try {
            val smoothedPixels = smoothed.pixels()
            assertTrue(smoothedPixels.any { Color.alpha(it) in 1..254 })
            assertEquals(Color.BLUE, smoothed.getPixel(8, 8))
        } finally {
            source.recycle()
            smoothed.recycle()
        }
    }

    @Test
    fun targetSizeIsAppliedWithoutRecyclingTheInput() {
        val source = solidBitmap(5, 5)
        val rounded = source.roundedCorners(
            radiusPx = 2f,
            targetSize = PixelSize(9, 7),
            antiAlias = true,
        )
        try {
            assertEquals(9, rounded.width)
            assertEquals(7, rounded.height)
            assertFalse(source.isRecycled)
        } finally {
            source.recycle()
            rounded.recycle()
        }
    }

    @Test
    fun invalidArgumentsFailClearly() {
        val source = solidBitmap(5, 5)
        try {
            assertThrows(IllegalArgumentException::class.java) {
                source.roundedCorners(radiusPx = -1f)
            }
            assertThrows(IllegalArgumentException::class.java) {
                source.roundedCorners(radiusPx = Float.NaN)
            }
            assertThrows(IllegalArgumentException::class.java) {
                source.roundedCorners(radiusPx = 1f, targetSize = PixelSize.Zero)
            }
        } finally {
            source.recycle()
        }
    }

    private fun solidBitmap(width: Int, height: Int): Bitmap =
        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.BLUE)
        }

    private fun Bitmap.pixels(): IntArray = IntArray(width * height).also { pixels ->
        getPixels(pixels, 0, width, 0, 0, width, height)
    }
}
