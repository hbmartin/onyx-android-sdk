package com.onyx.android.sdk.pen

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PenBrushInkTest {
    @Test
    fun equalValuesHaveEqualHashes() {
        val first = PenBrushInk(1.5f, -0.0f, 255u, 35u, 128u)
        val second = PenBrushInk(1.5f, 0.0f, 255u, 35u, 128u)

        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
        assertNotEquals(first, PenBrushInk(1.5f, 0.0f, 254u, 35u, 128u))
    }

    @Test
    fun equalityRemainsReflexiveForNanCoordinates() {
        val ink = PenBrushInk(Float.NaN, 0.0f, 1u, 2u, 3u)

        assertEquals(ink, ink)
    }

    @Test
    fun javaFriendlyConstructorAndAccessorsPreserveUnsignedValues() {
        val ink = PenBrushInk(10.0f, 20.0f, 255, 35, 200)

        assertEquals(255, ink.getSizeAsInt())
        assertEquals(35, ink.getAngle36AsInt())
        assertEquals(200, ink.getAlphaAsInt())
    }

    @Test
    fun javaFriendlyConstructorRejectsOutOfRangeValues() {
        assertThrows(IllegalArgumentException::class.java) {
            PenBrushInk(0.0f, 0.0f, 256, 0, 0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            PenBrushInk(0.0f, 0.0f, 0, -1, 0)
        }
    }
}
