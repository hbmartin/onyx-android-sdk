package com.onyx.android.sdk.base.data

import android.graphics.Matrix
import com.onyx.android.sdk.base.lite.utils.MathUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24])
class SizeAndMathUtilsTest {
    @Test
    fun ratioPageSizeUsesFloatingPointAndBothBounds() {
        val result = Size(700, 800).ratioPageSize(Size(1_000, 500))

        assertEquals(437.5f, result.width(), 0.001f)
        assertEquals(500.0f, result.height(), 0.001f)
    }

    @Test
    fun ratioPageSizeNeverEnlargesAnImageThatAlreadyFits() {
        val result = Size(320, 200).ratioPageSize(Size(1_000, 1_000))

        assertEquals(320.0f, result.width(), 0.001f)
        assertEquals(200.0f, result.height(), 0.001f)
    }

    @Test
    fun ratioPageSizeRejectsInvalidDimensions() {
        assertThrows(IllegalArgumentException::class.java) {
            Size(0, 200).ratioPageSize(Size(1_000, 1_000))
        }
        assertThrows(IllegalArgumentException::class.java) {
            Size(200, 200).ratioPageSize(Size(0, 1_000))
        }
    }

    @Test
    fun mathUtilitiesHandleBoundaryValues() {
        assertEquals(9u.toUByte(), MathUtils.normalizeAngleTo36(450.0))
        assertEquals(27u.toUByte(), MathUtils.normalizeAngleTo36(-90.0))
        assertEquals(0u.toUByte(), MathUtils.normalizeAngleTo36(360.0))
        assertEquals(17.5f, MathUtils.parseFloat("17.5", -1.0f), 0.0f)
        assertEquals(-1.0f, MathUtils.parseFloat("not-a-number", -1.0f), 0.0f)
        assertEquals(5.0, MathUtils.distance(0.0f, 0.0f, -3.0f, -4.0f), 0.0)
    }

    @Test
    fun mathUtilitiesRejectNonFiniteAnglesAndEmptyPaddedRanges() {
        assertThrows(IllegalArgumentException::class.java) {
            MathUtils.normalizeAngleTo36(Double.NaN)
        }
        assertThrows(IllegalArgumentException::class.java) {
            with(MathUtils) {
                5.0f.limitIn(start = 0.0f, end = 10.0f, startPadding = 6.0f, endPadding = 5.0f)
            }
        }
    }

    @Test
    fun identityMatrixIsEmptyButTranslatedMatrixIsNot() {
        assertTrue(MathUtils.isEmptyMatrix(null))
        val matrix = Matrix()
        assertTrue(MathUtils.isEmptyMatrix(matrix))
        matrix.setTranslate(1.0f, 0.0f)
        assertFalse(MathUtils.isEmptyMatrix(matrix))
    }

    @Test
    fun integerOffsetDelegatesToFloatingPointOffset() {
        val point = TouchPoint(2.5f, 4.5f)

        point.offset(3, -2)

        assertEquals(5.5f, point.x, 0.0f)
        assertEquals(2.5f, point.y, 0.0f)
    }
}
