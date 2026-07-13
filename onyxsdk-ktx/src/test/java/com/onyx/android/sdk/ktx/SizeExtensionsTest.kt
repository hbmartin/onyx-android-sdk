package com.onyx.android.sdk.ktx

import com.onyx.android.sdk.base.data.Size
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertThrows
import org.junit.Test

class SizeExtensionsTest {
    @Test
    fun immutableSizeFitsWithinBoundsWithoutEnlarging() {
        assertEquals(PixelSize(437, 500), PixelSize(700, 800).fitWithin(PixelSize(1_000, 500)))
        assertEquals(PixelSize(320, 200), PixelSize(320, 200).fitWithin(PixelSize(1_000, 1_000)))
    }

    @Test
    fun recoveredSizeCanUseImmutableFitOperation() {
        assertEquals(PixelSize(500, 250), Size(1_000, 500).fitWithin(Size(500, 500)))
    }

    @Test
    fun invalidImmutableDimensionsFailClearly() {
        assertThrows(IllegalArgumentException::class.java) { PixelSize(-1, 10) }
        assertThrows(IllegalArgumentException::class.java) {
            PixelSize.Zero.fitWithin(PixelSize(10, 10))
        }
    }

    @Test
    fun emptyFactoryNeverSharesMutableState() {
        val first = emptyOnyxSize()
        val second = emptyOnyxSize()

        assertNotSame(first, second)
        first.width = 10
        assertEquals(0, second.width)
    }
}
