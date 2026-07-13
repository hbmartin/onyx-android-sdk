package com.onyx.android.sdk.ktx

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SizeExtensionsTest {
    @Test
    fun immutableSizeFitsWithinBoundsWithoutEnlarging() {
        assertEquals(PixelSize(437, 500), PixelSize(700, 800).fitWithin(PixelSize(1_000, 500)))
        assertEquals(PixelSize(320, 200), PixelSize(320, 200).fitWithin(PixelSize(1_000, 1_000)))
    }

    @Test
    fun invalidImmutableDimensionsFailClearly() {
        assertThrows(IllegalArgumentException::class.java) { PixelSize(-1, 10) }
        assertThrows(IllegalArgumentException::class.java) {
            PixelSize.Zero.fitWithin(PixelSize(10, 10))
        }
    }
}
