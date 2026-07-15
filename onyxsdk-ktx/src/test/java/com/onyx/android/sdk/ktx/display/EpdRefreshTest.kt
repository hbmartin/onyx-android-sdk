package com.onyx.android.sdk.ktx.display

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class EpdRefreshTest {
    @Test
    fun rejectedFirmwareWaitSubmissionReturnsNull() {
        val executor = Executors.newSingleThreadExecutor()
        executor.shutdownNow()

        val future = submitFirmwareWaitOrNull(executor)

        assertNull(future)
    }

    @Test
    fun timedOutWaitRecoversAfterFirmwareCallReturns() = runBlocking {
        val executor = Executors.newSingleThreadExecutor()
        val release = CountDownLatch(1)
        val calls = AtomicInteger()
        val coordinator = FirmwareWaitCoordinator(executor) {
            if (calls.incrementAndGet() == 1) {
                while (true) {
                    try {
                        release.await()
                        break
                    } catch (_: InterruptedException) {
                        // Model a Binder call that ignores thread interruption.
                    }
                }
            }
        }
        try {
            assertEquals(
                FirmwareWaitAttempt.TimedOut::class,
                coordinator.await(20.milliseconds)::class,
            )
            assertSame(FirmwareWaitAttempt.Recovering, coordinator.await(20.milliseconds))

            release.countDown()
            check(release.await(1, TimeUnit.SECONDS))
            var recovered: FirmwareWaitAttempt = FirmwareWaitAttempt.Recovering
            repeat(100) {
                if (recovered == FirmwareWaitAttempt.Recovering) {
                    Thread.sleep(5)
                    recovered = coordinator.await(200.milliseconds)
                }
            }

            assertSame(FirmwareWaitAttempt.Returned, recovered)
            assertEquals(2, calls.get())
        } finally {
            release.countDown()
            executor.shutdownNow()
        }
    }

    @Test
    fun concurrentWaitsReceiveIndependentExecutionBudgets() = runBlocking {
        val executor = Executors.newSingleThreadExecutor()
        val calls = AtomicInteger()
        val coordinator = FirmwareWaitCoordinator(executor) {
            calls.incrementAndGet()
            Thread.sleep(40)
        }
        try {
            val first = async(Dispatchers.Default) { coordinator.await(250.milliseconds) }
            val second = async(Dispatchers.Default) { coordinator.await(250.milliseconds) }

            assertSame(FirmwareWaitAttempt.Returned, first.await())
            assertSame(FirmwareWaitAttempt.Returned, second.await())
            assertEquals(2, calls.get())
        } finally {
            executor.shutdownNow()
        }
    }
}
