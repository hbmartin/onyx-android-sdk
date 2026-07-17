package com.onyx.android.sdk.ktx.ink

import android.os.Looper
import android.view.SurfaceView
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.RawInkConfiguration
import com.onyx.android.sdk.pen.RawInputEventV2
import com.onyx.android.sdk.pen.RawInputListenerV2
import com.onyx.android.sdk.pen.RawInputPhase
import com.onyx.android.sdk.pen.RawInputTool
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [35])
class RawInkSessionActorTest {
    @Test
    fun cancellationIsTerminalReleasesLeaseAndDrainsRequests() = runBlocking {
        val uncaughtActorFailure = AtomicReference<Throwable?>()
        val executor: ExecutorService = Executors.newSingleThreadExecutor { runnable ->
            Thread(runnable, "raw-ink-session-cancellation-test").apply {
                uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, failure ->
                    uncaughtActorFailure.compareAndSet(null, failure)
                }
            }
        }
        val dispatcher = executor.asCoroutineDispatcher()
        try {
            val releases = AtomicInteger()
            val session = createSession(dispatcher) { releases.incrementAndGet() }
            val state = RawInkSession::class.java.getDeclaredField("mutableState").run {
                isAccessible = true
                @Suppress("UNCHECKED_CAST")
                get(session) as kotlinx.coroutines.flow.MutableStateFlow<RawInkSessionState>
            }
            state.value = RawInkSessionState.Active(1)
            val actorScope = RawInkSession::class.java.getDeclaredField("scope").run {
                isAccessible = true
                get(session) as CoroutineScope
            }
            val manuallyPaused = RawInkSession::class.java.getDeclaredField("manuallyPaused").apply {
                isAccessible = true
            }

            val current = async(start = CoroutineStart.UNDISPATCHED) { session.pause() }
            withTimeout(5_000) {
                while (!manuallyPaused.getBoolean(session)) delay(10)
            }
            val queued = async(start = CoroutineStart.UNDISPATCHED) { session.resume() }
            actorScope.cancel(CancellationException("Cancel actor during request processing"))

            val failed = withTimeout(5_000) {
                var terminal: RawInkSessionState.Failed? = null
                while (terminal == null) {
                    shadowOf(Looper.getMainLooper()).idle()
                    terminal = session.state.value as? RawInkSessionState.Failed
                    if (terminal == null) delay(10)
                }
                terminal
            }
            val currentFailure = withTimeout(5_000) { current.await().exceptionOrNull() }
            val queuedFailure = withTimeout(5_000) { queued.await().exceptionOrNull() }

            assertTrue(failed.failure is OnyxFailure.FirmwareRejected)
            assertSame(failed.failure, currentFailure)
            assertSame(failed.failure, queuedFailure)
            assertSame(failed.failure, session.closeAndAwait().exceptionOrNull())
            assertEquals(1, releases.get())
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS))
            assertNull(uncaughtActorFailure.get())
        } finally {
            dispatcher.close()
        }
    }

    @Test
    fun overflowIsTerminalReleasesLeaseAndClosesCollectorsWithCause() = runBlocking {
        val uncaughtActorFailure = AtomicReference<Throwable?>()
        val executor: ExecutorService = Executors.newSingleThreadExecutor { runnable ->
            Thread(runnable, "raw-ink-session-actor-test").apply {
                uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, failure ->
                    uncaughtActorFailure.compareAndSet(null, failure)
                }
            }
        }
        val dispatcher = executor.asCoroutineDispatcher()
        try {
            val releases = AtomicInteger()
            val session = createSession(dispatcher) { releases.incrementAndGet() }
            val state = RawInkSession::class.java.getDeclaredField("mutableState").run {
                isAccessible = true
                @Suppress("UNCHECKED_CAST")
                get(session) as kotlinx.coroutines.flow.MutableStateFlow<RawInkSessionState>
            }
            state.value = RawInkSessionState.Active(1)
            val listener = RawInkSession::class.java.getDeclaredField("rawListener").run {
                isAccessible = true
                get(session) as RawInputListenerV2
            }
            val strokeTerminal = async(start = CoroutineStart.UNDISPATCHED) {
                runCatching { session.completedStrokes.collect { } }.exceptionOrNull()
            }
            val eventTerminal = async(start = CoroutineStart.UNDISPATCHED) {
                runCatching { session.events.collect { } }.exceptionOrNull()
            }

            listener.onRawInputEvent(event(RawInputPhase.DOWN, 1))
            listener.onRawInputEvent(event(RawInputPhase.MOVE, 2))

            val failed = withTimeout(5_000) {
                var terminal: RawInkSessionState.Failed? = null
                while (terminal == null) {
                    shadowOf(Looper.getMainLooper()).idle()
                    terminal = session.state.value as? RawInkSessionState.Failed
                    if (terminal == null) delay(10)
                }
                terminal
            }
            val strokeFailure = withTimeout(5_000) { strokeTerminal.await() }
            val eventFailure = withTimeout(5_000) { eventTerminal.await() }

            assertTrue(failed.failure is OnyxFailure.EventOverflow)
            assertSame(failed.failure, strokeFailure)
            assertSame(failed.failure, eventFailure)
            assertEquals(1, releases.get())
            assertNull(session.preview.value)
            assertSame(failed.failure, session.pause().exceptionOrNull())
            assertNotNull(session.resume().exceptionOrNull())
            assertSame(failed.failure, session.closeAndAwait().exceptionOrNull())
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS))
            assertNull(uncaughtActorFailure.get())
        } finally {
            dispatcher.close()
        }
    }

    private fun createSession(
        dispatcher: CoroutineDispatcher,
        release: () -> Unit,
    ): RawInkSession {
        val constructor = RawInkSession::class.java.declaredConstructors.single {
            it.parameterCount == 4
        }
        constructor.isAccessible = true
        return constructor.newInstance(
            SurfaceView(RuntimeEnvironment.getApplication()),
            RawInkConfiguration(maxPointsPerStroke = 1),
            dispatcher,
            release,
        ) as RawInkSession
    }

    private fun event(phase: RawInputPhase, sequence: Long) = RawInputEventV2(
        phase,
        RawInputTool.PEN,
        sequence.toFloat(),
        sequence.toFloat(),
        100,
        0.5f,
        200,
        0,
        0,
        sequence,
        sequence,
        false,
        false,
    )
}
