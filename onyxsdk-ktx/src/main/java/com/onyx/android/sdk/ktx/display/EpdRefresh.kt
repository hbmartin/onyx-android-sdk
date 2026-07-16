@file:Suppress(
    // Firmware waits use explicit early-return circuit-breaker paths; legacy update modes are
    // type-erased from KTX bytecode and cast only at the internal dispatch boundary. The
    // process-owned waiter deliberately uses Dispatchers.IO around Future.get; callers cannot
    // inject a dispatcher into this singleton API.
    "DontForceCast",
    "InjectDispatcher",
    "LongMethod",
    "ReturnCount",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx.display

import android.graphics.Rect
import android.view.View
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.api.device.epd.UpdateMode
import com.onyx.android.sdk.ktx.capabilities.toLegacyUpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticStatus
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.RefreshScope
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runInterruptible
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

enum class RefreshCompletionEvidence {
    FIRMWARE_WAIT_RETURNED,
    FIRMWARE_DISPATCHED,
    ESTIMATED_DELAY,
}

enum class RefreshWarning { WAIT_BACKEND_UNAVAILABLE, WAIT_BACKEND_TIMED_OUT }

data class RefreshReceipt(
    val evidence: RefreshCompletionEvidence,
    val elapsedNanos: Long,
    val warnings: Set<RefreshWarning> = emptySet(),
)

internal fun interface FirmwareWaitOperation {
    fun run()
}

@JvmSynthetic
internal fun submitFirmwareWaitOrNull(
    executor: ExecutorService,
    operation: FirmwareWaitOperation = FirmwareWaitOperation(
        EpdController::waitForUpdateFinishedOrThrow,
    ),
): Future<*>? = try {
    executor.submit(operation::run)
} catch (_: RejectedExecutionException) {
    null
}

internal sealed interface FirmwareWaitAttempt {
    data object Returned : FirmwareWaitAttempt
    data object Recovering : FirmwareWaitAttempt
    data object Unavailable : FirmwareWaitAttempt
    data class TimedOut(val failure: TimeoutException) : FirmwareWaitAttempt
    data class Failed(val failure: Throwable) : FirmwareWaitAttempt
}

/** Serializes waits and lets a timed-out Binder call recover instead of poisoning the process. */
internal class FirmwareWaitCoordinator(
    private val executor: ExecutorService,
    private val operation: FirmwareWaitOperation,
) {
    private class RunningWait(val running: AtomicBoolean = AtomicBoolean())

    private val mutex = Mutex()
    private val recovering = AtomicReference<RunningWait?>()

    suspend fun await(timeout: Duration): FirmwareWaitAttempt {
        mutex.lock()
        try {
            recovering.get()?.let { previous ->
                if (previous.running.get()) return FirmwareWaitAttempt.Recovering
                recovering.set(null)
            }
            val running = RunningWait()
            val future = submitFirmwareWaitOrNull(executor) {
                running.running.set(true)
                try {
                    operation.run()
                } finally {
                    running.running.set(false)
                }
            } ?: return FirmwareWaitAttempt.Unavailable
            return try {
                runInterruptible(Dispatchers.IO) {
                    future.get(timeout.inWholeMilliseconds.coerceAtLeast(1), TimeUnit.MILLISECONDS)
                }
                FirmwareWaitAttempt.Returned
            } catch (cancelled: CancellationException) {
                future.cancel(true)
                if (running.running.get()) recovering.set(running)
                throw cancelled
            } catch (timeoutFailure: TimeoutException) {
                future.cancel(true)
                if (running.running.get()) recovering.set(running)
                FirmwareWaitAttempt.TimedOut(timeoutFailure)
            } catch (failure: Throwable) {
                FirmwareWaitAttempt.Failed(failure.cause ?: failure)
            }
        } finally {
            mutex.unlock()
        }
    }
}

private object FirmwareWaiter {
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "onyx-epd-wait").apply { isDaemon = true }
    }
    private val coordinator = FirmwareWaitCoordinator(
        executor,
        EpdController::waitForUpdateFinishedOrThrow,
    )

    suspend fun await(timeout: Duration): Result<RefreshReceipt> {
        val started = System.nanoTime()
        if (!EpdController.getFirmwareBackendInfo()
                .transactionCodes.containsKey("WAIT_FOR_UPDATE_FINISHED")
        ) {
            return Result.success(
                RefreshReceipt(
                    RefreshCompletionEvidence.ESTIMATED_DELAY,
                    System.nanoTime() - started,
                    setOf(RefreshWarning.WAIT_BACKEND_UNAVAILABLE),
                ),
            )
        }
        return when (val attempt = coordinator.await(timeout)) {
            FirmwareWaitAttempt.Returned -> {
                OnyxDiagnostics.record(
                    "epd.wait",
                    FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                    FirmwareDiagnosticPhase.REPLY,
                    FirmwareDiagnosticStatus.SUCCEEDED,
                    started,
                )
                Result.success(
                    RefreshReceipt(
                        RefreshCompletionEvidence.FIRMWARE_WAIT_RETURNED,
                        System.nanoTime() - started,
                    ),
                )
            }
            FirmwareWaitAttempt.Recovering -> Result.success(
                RefreshReceipt(
                    RefreshCompletionEvidence.ESTIMATED_DELAY,
                    System.nanoTime() - started,
                    setOf(RefreshWarning.WAIT_BACKEND_TIMED_OUT),
                ),
            )
            FirmwareWaitAttempt.Unavailable -> Result.success(
                RefreshReceipt(
                    RefreshCompletionEvidence.ESTIMATED_DELAY,
                    System.nanoTime() - started,
                    setOf(RefreshWarning.WAIT_BACKEND_UNAVAILABLE),
                ),
            )
            is FirmwareWaitAttempt.TimedOut -> {
                val diagnostic = OnyxDiagnostics.record(
                    "epd.wait",
                    FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                    FirmwareDiagnosticPhase.REPLY,
                    FirmwareDiagnosticStatus.TIMED_OUT,
                    started,
                    attempt.failure,
                )
                Result.failure(
                    OnyxFailure.TimedOut(
                        "epd.wait",
                        diagnostic.id,
                        "Firmware wait exceeded $timeout; precise waits resume when it returns",
                        attempt.failure,
                    ),
                )
            }
            is FirmwareWaitAttempt.Failed -> {
                val diagnostic = OnyxDiagnostics.record(
                    "epd.wait",
                    FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                    FirmwareDiagnosticPhase.REPLY,
                    FirmwareDiagnosticStatus.FAILED,
                    started,
                    attempt.failure,
                )
                Result.failure(
                    OnyxFailure.FirmwareRejected(
                        "epd.wait",
                        diagnostic.id,
                        attempt.failure.message ?: "Firmware wait failed",
                        attempt.failure,
                    ),
                )
            }
        }
    }
}

suspend fun awaitEpdIdle(
    timeout: Duration = 2.seconds,
    fallbackDelay: Duration = 500.milliseconds,
): Result<RefreshReceipt> {
    if (!timeout.isPositive() || fallbackDelay.isNegative()) {
        return Result.failure(
            OnyxFailure.InvalidArgument(
                "epd.wait",
                "timeout must be positive and fallbackDelay must not be negative",
            ),
        )
    }
    val started = System.nanoTime()
    val firmware = FirmwareWaiter.await(timeout)
    if (firmware.isSuccess) {
        val receipt = firmware.getOrThrow()
        if (receipt.evidence != RefreshCompletionEvidence.ESTIMATED_DELAY) return firmware
        delay(fallbackDelay.inWholeMilliseconds)
        return Result.success(receipt.copy(elapsedNanos = System.nanoTime() - started))
    }
    val failure = firmware.exceptionOrNull()
    if (failure is OnyxFailure.TimedOut) {
        delay(fallbackDelay.inWholeMilliseconds)
        return Result.success(
            RefreshReceipt(
                RefreshCompletionEvidence.ESTIMATED_DELAY,
                System.nanoTime() - started,
                setOf(RefreshWarning.WAIT_BACKEND_TIMED_OUT),
            ),
        )
    }
    return firmware
}

suspend fun View.refreshAndAwait(
    mode: EpdUpdateMode,
    scope: RefreshScope = RefreshScope.FullView,
    timeout: Duration = 2.seconds,
): Result<RefreshReceipt> {
    if (!timeout.isPositive()) {
        return Result.failure(OnyxFailure.InvalidArgument("epd.refresh", "timeout must be positive"))
    }
    val dispatch = withContext(Dispatchers.Main.immediate) {
        onyxResult(
            operation = "epd.refresh",
            backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
        ) {
            check(isAttachedToWindow) { "View must be attached before refresh" }
            when (scope) {
                RefreshScope.FullView -> EpdController.refreshScreen(
                    this@refreshAndAwait,
                    mode.toLegacyUpdateMode() as UpdateMode,
                )
                is RefreshScope.Region -> {
                    val region = Rect(scope.bounds)
                    require(!region.isEmpty) { "Refresh region must not be empty" }
                    EpdController.refreshScreenRegion(
                        this@refreshAndAwait,
                        region.left,
                        region.top,
                        region.width(),
                        region.height(),
                        mode.toLegacyUpdateMode() as UpdateMode,
                    )
                }
            }
        }
    }
    if (dispatch.isFailure) return Result.failure(requireNotNull(dispatch.exceptionOrNull()))
    return awaitEpdIdle(timeout, fallbackDelayFor(mode))
}

private fun fallbackDelayFor(mode: EpdUpdateMode): Duration = when (mode) {
    EpdUpdateMode.DU,
    EpdUpdateMode.DU4,
    EpdUpdateMode.GU,
    EpdUpdateMode.GU_FAST,
    EpdUpdateMode.A2,
    -> 150.milliseconds
    else -> 500.milliseconds
}
