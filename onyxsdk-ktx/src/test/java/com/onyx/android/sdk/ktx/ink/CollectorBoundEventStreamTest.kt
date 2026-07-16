package com.onyx.android.sdk.ktx.ink

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CollectorBoundEventStreamTest {
    @Test
    fun cancelledCollectorDoesNotLeakBufferedEventsToNextCollector() = runBlocking {
        val stream = CollectorBoundEventStream<Int>()
        assertFalse(stream.tryEmit(0))

        val firstValue = CompletableDeferred<Int>()
        val firstCollector = launch(start = CoroutineStart.UNDISPATCHED) {
            stream.flow.collect { value ->
                firstValue.complete(value)
                awaitCancellation()
            }
        }

        assertTrue(stream.tryEmit(1))
        assertEquals(1, firstValue.await())
        assertTrue(stream.tryEmit(2))
        firstCollector.cancelAndJoin()

        val secondValue = async(start = CoroutineStart.UNDISPATCHED) {
            stream.flow.first()
        }
        assertTrue(stream.tryEmit(3))

        assertEquals(3, secondValue.await())
    }
}
