package com.onyx.android.sdk.ktx

import com.onyx.android.sdk.data.note.TouchPoint
import com.onyx.android.sdk.pen.RawInputCallback
import com.onyx.android.sdk.pen.RawInputManager
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FlowExtensionsTest {
    @Test
    fun rxObservableConvertsToFlow() = runBlocking {
        assertEquals(listOf(1, 2, 3), Observable.just(1, 2, 3).toKotlinFlow().toList())
    }

    @Test
    fun rawInputCallbackIsInstalledAndClearedWithCollection() = runBlocking {
        val manager = RawInputManager()
        val events = mutableListOf<RawInputEvent>()
        val collection = launch(start = CoroutineStart.UNDISPATCHED) {
            manager.rawInputEvents().take(1).toList(events)
        }
        yield()
        val callbackField = RawInputManager::class.java.getDeclaredField("a").apply {
            isAccessible = true
        }
        val callback = callbackField.get(manager) as RawInputCallback

        callback.onPenActive(TouchPoint(3.0f, 4.0f))
        collection.join()

        assertEquals(1, events.size)
        assertEquals(RawInputEvent.PenActive(TouchPoint(3.0f, 4.0f)), events.single())
        assertNull(callbackField.get(manager))
    }
}
