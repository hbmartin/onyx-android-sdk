@file:Suppress(
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "DontForceCast",
    "NoCallbacksInFunctions",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.recognition.internal

import com.onyx.android.sdk.recognition.RecognitionFailure
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration

internal class ProcessRecognitionLane {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val channel = Channel<Work>(Channel.UNLIMITED)
    private val queueLock = Any()
    private var normalPending = 0
    private var controlPending = 0

    init {
        scope.launch {
            for (work in channel) execute(work)
        }
    }

    suspend fun <T> submit(
        operation: String,
        timeout: Duration?,
        block: suspend () -> Result<T>,
    ): Result<T> = enqueue(operation, timeout, control = false, block)

    suspend fun <T> submitControl(
        operation: String,
        block: suspend () -> Result<T>,
    ): Result<T> = enqueue(operation, timeout = null, control = true, block)

    private suspend fun <T> enqueue(
        operation: String,
        timeout: Duration?,
        control: Boolean,
        block: suspend () -> Result<T>,
    ): Result<T> {
        require(timeout == null || timeout.isPositive()) { "timeout must be positive" }
        val deferred = CompletableDeferred<Result<Any?>>()
        val work = Work(
            operation = operation,
            control = control,
            block = { block() as Result<Any?> },
            deferred = deferred,
        )
        synchronized(queueLock) {
            if (control) {
                if (controlPending >= CONTROL_PENDING_LIMIT) {
                    return Result.failure(RecognitionFailure.QueueFull())
                }
                controlPending += 1
            } else {
                if (normalPending >= NORMAL_PENDING_LIMIT) {
                    return Result.failure(RecognitionFailure.QueueFull())
                }
                normalPending += 1
            }
            check(channel.trySend(work).isSuccess) { "Recognition process lane is closed" }
        }
        return try {
            val completed = if (timeout == null) {
                deferred.await()
            } else {
                withTimeoutOrNull(timeout) { deferred.await() }
            }
            if (completed == null) {
                work.abandoned.set(true)
                work.cancelledBeforeStart.set(true)
                Result.failure(RecognitionFailure.TimedOut(operation))
            } else {
                val typed = completed as Result<T>
                val failure = typed.exceptionOrNull()
                if (failure is CancellationException) throw failure
                typed
            }
        } catch (cancelled: CancellationException) {
            work.abandoned.set(true)
            work.cancelledBeforeStart.set(true)
            throw cancelled
        }
    }

    private suspend fun execute(work: Work) {
        synchronized(queueLock) {
            if (work.control) controlPending -= 1 else normalPending -= 1
        }
        if (work.cancelledBeforeStart.get()) return
        work.started.set(true)
        val result = try {
            work.block()
        } catch (cancelled: CancellationException) {
            Result.failure(cancelled)
        } catch (failure: Throwable) {
            Result.failure(failure)
        }
        if (!work.abandoned.get()) work.deferred.complete(result)
    }

    private data class Work(
        val operation: String,
        val control: Boolean,
        val block: suspend () -> Result<Any?>,
        val deferred: CompletableDeferred<Result<Any?>>,
        val started: AtomicBoolean = AtomicBoolean(),
        val abandoned: AtomicBoolean = AtomicBoolean(),
        val cancelledBeforeStart: AtomicBoolean = AtomicBoolean(),
    )

    private companion object {
        const val NORMAL_PENDING_LIMIT = 8
        const val CONTROL_PENDING_LIMIT = 1
    }
}

internal object SharedRecognitionLanes {
    val hwrAndOcr: ProcessRecognitionLane = ProcessRecognitionLane()
}
