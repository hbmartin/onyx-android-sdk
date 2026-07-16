package com.onyx.android.sdk.ktx

import com.onyx.android.sdk.ktx.model.BrushConfiguration
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkTool
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class InkContractsTest {
    @Test
    fun immutablePointRetainsRawAndNormalizedPressureEvidence() {
        val point = InkPoint(
            xPx = 10f,
            yPx = 20f,
            rawPressure = 2048,
            normalizedPressure = 0.5f,
            maxPressure = 4096,
            tiltX = -12,
            tiltY = 8,
            eventTimeNanos = 123_000_000,
            sequence = 9,
            tool = InkTool.SIDE_ERASER,
        )

        assertEquals(2048, point.rawPressure)
        assertEquals(0.5f, point.normalizedPressure)
        assertEquals(InkTool.SIDE_ERASER, point.tool)
    }

    @Test
    fun invalidPressureAndCoordinatesFailAtTheBoundary() {
        assertThrows(IllegalArgumentException::class.java) {
            point(normalizedPressure = 1.1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            point(x = Float.NaN)
        }
        assertThrows(IllegalArgumentException::class.java) {
            point(maxPressure = 0)
        }
    }

    @Test
    fun normalizedPressureAcceptsDocumentedBoundaries() {
        assertEquals(0f, point(normalizedPressure = 0f).normalizedPressure)
        assertEquals(1f, point(normalizedPressure = 1f).normalizedPressure)
    }

    @Test
    fun brushConfigurationRejectsReversedVelocityBounds() {
        assertThrows(IllegalArgumentException::class.java) {
            BrushConfiguration(
                velocityLowerBound = 20f,
                velocityUpperBound = 2f,
            )
        }
        assertEquals(
            2f,
            BrushConfiguration(
                velocityLowerBound = 2f,
                velocityUpperBound = 20f,
            ).velocityLowerBound,
        )
    }

    private fun point(
        x: Float = 0f,
        normalizedPressure: Float = 0.5f,
        maxPressure: Int = 4096,
    ) = InkPoint(
        xPx = x,
        yPx = 0f,
        rawPressure = 1,
        normalizedPressure = normalizedPressure,
        maxPressure = maxPressure,
        tiltX = 0,
        tiltY = 0,
        eventTimeNanos = 1,
        sequence = 1,
        tool = InkTool.PEN,
    )
}
