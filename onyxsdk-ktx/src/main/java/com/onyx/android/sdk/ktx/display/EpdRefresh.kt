package com.onyx.android.sdk.ktx.display

import android.graphics.Rect
import android.view.View
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.ktx.capabilities.toLegacyUpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticStatus
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.RefreshScope
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

private object FirmwareWaiter {
    private val poisoned = AtomicBoolean(false)
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "onyx-epd-wait").apply { isDaemon = true }
    }

    suspend fun await(timeout: Duration): Result<RefreshReceipt> {
        val started = System.nanoTime()
        if (poisoned.get() || !EpdController.getFirmwareBackendInfo()
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
        val future = executor.submit { EpdController.waitForUpdateFinishedOrThrow() }
        return try {
            withContext(Dispatchers.IO) {
                future.get(timeout.inWholeMilliseconds.coerceAtLeast(1), TimeUnit.MILLISECONDS)
            }
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
        } catch (cancelled: CancellationException) {
            future.cancel(true)
            throw cancelled
        } catch (timeoutFailure: TimeoutException) {
            poisoned.set(true)
            future.cancel(true)
            executor.shutdownNow()
            val diagnostic = OnyxDiagnostics.record(
                "epd.wait",
                FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                FirmwareDiagnosticPhase.REPLY,
                FirmwareDiagnosticStatus.TIMED_OUT,
                started,
                timeoutFailure,
            )
            Result.failure(
                OnyxFailure.TimedOut(
                    "epd.wait",
                    diagnostic.id,
                    "Firmware wait exceeded $timeout; backend disabled for this process",
                    timeoutFailure,
                ),
            )
        } catch (failure: Throwable) {
            val cause = failure.cause ?: failure
            val diagnostic = OnyxDiagnostics.record(
                "epd.wait",
                FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                FirmwareDiagnosticPhase.REPLY,
                FirmwareDiagnosticStatus.FAILED,
                started,
                cause,
            )
            Result.failure(
                OnyxFailure.FirmwareRejected(
                    "epd.wait",
                    diagnostic.id,
                    cause.message ?: "Firmware wait failed",
                    cause,
                ),
            )
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
                RefreshScope.FullView -> EpdController.refreshScreen(this@refreshAndAwait, mode.toLegacyUpdateMode())
                is RefreshScope.Region -> {
                    val region = Rect(scope.bounds)
                    require(!region.isEmpty) { "Refresh region must not be empty" }
                    EpdController.refreshScreenRegion(
                        this@refreshAndAwait,
                        region.left,
                        region.top,
                        region.width(),
                        region.height(),
                        mode.toLegacyUpdateMode(),
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
