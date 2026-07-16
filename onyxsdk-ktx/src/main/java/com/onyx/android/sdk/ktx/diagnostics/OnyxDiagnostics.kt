@file:Suppress(
    // The public diagnostics level is intentionally configurable, and the bounded synchronized
    // history requires a mutable deque. Result capture is the one boundary that must classify
    // arbitrary firmware/reflection failures.
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "LongParameterList",
    "NoCallbacksInFunctions",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx.diagnostics

import android.os.Build
import com.onyx.android.sdk.api.device.epd.FirmwareOperationException
import com.onyx.android.sdk.ktx.model.OnyxFailure
import java.util.ArrayDeque
import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/** Firmware integration boundary that handled an operation. */
enum class FirmwareBackendKind { SURFACE_FLINGER_BINDER, FRAMEWORK_REFLECTION, NATIVE_INPUT, NATIVE_RENDERER, FALLBACK }

/** Stage at which a firmware diagnostic was recorded. */
enum class FirmwareDiagnosticPhase { RESOLUTION, DISPATCH, REPLY, CLEANUP, VALIDATION }

/** Outcome recorded for a firmware operation. */
enum class FirmwareDiagnosticStatus { SUCCEEDED, REJECTED, UNAVAILABLE, TIMED_OUT, FAILED }

/** Controls how much information [OnyxDiagnostics] emits. */
enum class DiagnosticsLevel { FAILURES, VERBOSE }

/**
 * Immutable diagnostic record for one firmware operation.
 *
 * @property id Process-local monotonically increasing identifier.
 * @property operation Stable name of the firmware operation.
 * @property backend Integration boundary that handled the operation.
 * @property phase Stage at which the result was recorded.
 * @property status Recorded outcome.
 * @property durationNanos Elapsed monotonic time for the operation.
 * @property threadName Name of the thread that recorded the result.
 * @property firmwareFingerprint Android build fingerprint at the time of the operation.
 * @property firmwareDisplay Android display build identifier.
 * @property board Android board identifier.
 * @property causeClass Fully qualified failure class, or `null` on success.
 * @property causeMessage Failure message, or `null` when unavailable.
 */
data class FirmwareDiagnostic(
    val id: Long,
    val operation: String,
    val backend: FirmwareBackendKind,
    val phase: FirmwareDiagnosticPhase,
    val status: FirmwareDiagnosticStatus,
    val durationNanos: Long,
    val threadName: String,
    val firmwareFingerprint: String,
    val firmwareDisplay: String,
    val board: String,
    val causeClass: String? = null,
    val causeMessage: String? = null,
)

/** Process-local, bounded firmware diagnostic stream. */
object OnyxDiagnostics {
    private const val CAPACITY = 256
    private val ids = AtomicLong()
    private val history = ArrayDeque<FirmwareDiagnostic>(CAPACITY)
    private val mutableEvents = MutableSharedFlow<FirmwareDiagnostic>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    /** Amount of diagnostic information retained and emitted. */
    @Volatile
    var level: DiagnosticsLevel = DiagnosticsLevel.FAILURES

    /** Live stream of diagnostics produced after collection begins. */
    val events: SharedFlow<FirmwareDiagnostic> = mutableEvents.asSharedFlow()

    /** Returns a point-in-time copy of the bounded diagnostic history. */
    @Synchronized
    fun snapshot(): List<FirmwareDiagnostic> = history.toList()

    internal fun record(
        operation: String,
        backend: FirmwareBackendKind,
        phase: FirmwareDiagnosticPhase,
        status: FirmwareDiagnosticStatus,
        startedNanos: Long,
        cause: Throwable? = null,
    ): FirmwareDiagnostic {
        val diagnostic = FirmwareDiagnostic(
            id = ids.incrementAndGet(),
            operation = operation,
            backend = backend,
            phase = phase,
            status = status,
            durationNanos = (System.nanoTime() - startedNanos).coerceAtLeast(0),
            threadName = Thread.currentThread().name,
            firmwareFingerprint = Build.FINGERPRINT ?: "unknown",
            firmwareDisplay = Build.DISPLAY ?: "unknown",
            board = Build.BOARD ?: "unknown",
            causeClass = cause?.javaClass?.name,
            causeMessage = cause?.message,
        )
        synchronized(this) {
            while (history.size >= CAPACITY) history.removeFirst()
            history.addLast(diagnostic)
        }
        if (level == DiagnosticsLevel.VERBOSE || status != FirmwareDiagnosticStatus.SUCCEEDED) {
            mutableEvents.tryEmit(diagnostic)
        }
        return diagnostic
    }
}

internal inline fun <T> onyxResult(
    operation: String,
    backend: FirmwareBackendKind,
    phase: FirmwareDiagnosticPhase = FirmwareDiagnosticPhase.DISPATCH,
    block: () -> T,
): Result<T> {
    val started = System.nanoTime()
    return try {
        val value = block()
        OnyxDiagnostics.record(
            operation,
            backend,
            phase,
            FirmwareDiagnosticStatus.SUCCEEDED,
            started,
        )
        Result.success(value)
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (failure: Throwable) {
        val status = when (failure) {
            is FirmwareOperationException -> FirmwareDiagnosticStatus.REJECTED
            else -> FirmwareDiagnosticStatus.FAILED
        }
        val diagnostic = OnyxDiagnostics.record(
            operation,
            backend,
            phase,
            status,
            started,
            failure,
        )
        Result.failure(
            when (failure) {
                is OnyxFailure -> failure
                is FirmwareOperationException -> OnyxFailure.FirmwareRejected(
                    operation,
                    diagnostic.id,
                    failure.message ?: "Firmware rejected the operation",
                    failure,
                )
                is IllegalArgumentException -> OnyxFailure.InvalidArgument(
                    operation,
                    failure.message ?: "Invalid argument",
                )
                else -> OnyxFailure.InvalidState(
                    operation,
                    diagnostic.id,
                    failure.message ?: failure.javaClass.simpleName,
                )
            },
        )
    }
}
