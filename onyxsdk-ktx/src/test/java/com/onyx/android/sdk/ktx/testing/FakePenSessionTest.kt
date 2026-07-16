package com.onyx.android.sdk.ktx.testing

import com.onyx.android.sdk.ktx.ink.RawInkSessionState
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkTool
import com.onyx.android.sdk.ktx.model.PenEvent
import com.onyx.android.sdk.ktx.model.PenPhase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakePenSessionTest {
    @Test
    fun emitsImmutableEventsAndModelsLifecycle() = runBlocking {
        val session = FakePenSession()
        val received = async { session.events.first() }
        val event = PenEvent(PenPhase.DOWN, point())

        session.emit(event)

        assertEquals(event, received.await())
        session.pause().getOrThrow()
        assertTrue(session.state.value is RawInkSessionState.Suspended)
        session.resume().getOrThrow()
        assertTrue(session.state.value is RawInkSessionState.Active)
        session.closeAndAwait().getOrThrow()
        assertEquals(RawInkSessionState.Closed, session.state.value)
    }

    private fun point() = InkPoint(
        xPx = 1f,
        yPx = 2f,
        rawPressure = 100,
        normalizedPressure = 0.5f,
        maxPressure = 200,
        tiltX = 0,
        tiltY = 0,
        eventTimeNanos = 3,
        sequence = 4,
        tool = InkTool.PEN,
    )
}
