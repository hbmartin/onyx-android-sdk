@file:Suppress(
    // Process-global ownership is inherently stateful and the scoped callback is the public API.
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "NoCallbacksInFunctions",
    "ReturnCount",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx.display

import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.api.device.epd.UpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.model.OnyxFailure
import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext

enum class TransientUpdateMode(internal val legacy: UpdateMode) {
    ANIMATION(UpdateMode.ANIMATION),
    ANIMATION_QUALITY(UpdateMode.ANIMATION_QUALITY),
}

enum class RefreshModeScope { PROCESS }

data class RefreshModeReceipt(
    val mode: TransientUpdateMode,
    val scope: RefreshModeScope,
    val nestingDepth: Int,
)

/** Nesting-safe ownership token for the process-global firmware transient mode. */
class RefreshModeToken internal constructor(
    val receipt: RefreshModeReceipt,
    private val tokenId: Long,
    private val owner: ScopedRefreshModeOwner,
) : Closeable {
    private val releaseStarted = AtomicBoolean()
    private val released = CompletableDeferred<Result<Unit>>()

    suspend fun closeAndAwait(): Result<Unit> {
        if (releaseStarted.compareAndSet(false, true)) {
            val outcome = withContext(NonCancellable) { owner.release(tokenId) }
            released.complete(outcome)
        }
        return released.await()
    }

    override fun close() {
        if (releaseStarted.compareAndSet(false, true)) {
            owner.releaseAsync(tokenId, released)
        }
    }
}

object RefreshModeController {
    private val owner = ScopedRefreshModeOwner(
        applyMode = { mode ->
            withContext(Dispatchers.Main.immediate) {
                onyxResult(
                    operation = "epd.transient.apply",
                    backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
                ) {
                    if (!EpdController.applyTransientUpdate(mode.legacy)) {
                        throw OnyxFailure.UnsupportedCapability(
                            "epd.transient.apply",
                            null,
                            "Firmware rejected ${mode.name}",
                        )
                    }
                }
            }
        },
        clearMode = {
            withContext(Dispatchers.Main.immediate) {
                onyxResult(
                    operation = "epd.transient.clear",
                    backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
                ) {
                    EpdController.clearTransientUpdate(true)
                    Unit
                }
            }
        },
    )

    suspend fun acquire(
        mode: TransientUpdateMode = TransientUpdateMode.ANIMATION_QUALITY,
    ): Result<RefreshModeToken> = owner.acquire(mode)
}

suspend fun <T> withRefreshMode(
    mode: TransientUpdateMode = TransientUpdateMode.ANIMATION_QUALITY,
    block: suspend () -> T,
): Result<T> {
    val token = RefreshModeController.acquire(mode).getOrElse { return Result.failure(it) }
    var outcome: Result<T>? = null
    try {
        outcome = Result.success(block())
    } catch (cancellation: kotlinx.coroutines.CancellationException) {
        throw cancellation
    } catch (failure: Throwable) {
        outcome = Result.failure(failure)
    } finally {
        val released = withContext(NonCancellable) { token.closeAndAwait() }
        if (outcome?.isSuccess == true && released.isFailure) {
            outcome = Result.failure(requireNotNull(released.exceptionOrNull()))
        }
    }
    return requireNotNull(outcome)
}

internal class ScopedRefreshModeOwner(
    private val applyMode: suspend (TransientUpdateMode) -> Result<Unit>,
    private val clearMode: suspend () -> Result<Unit>,
    private val asynchronousScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
) {
    private val mutex = Mutex()
    private val ids = AtomicLong()
    private val activeTokens = linkedSetOf<Long>()
    private var activeMode: TransientUpdateMode? = null

    suspend fun acquire(mode: TransientUpdateMode): Result<RefreshModeToken> {
        mutex.lock()
        try {
            val current = activeMode
            if (current != null && current != mode) {
                return Result.failure(
                    OnyxFailure.InvalidState(
                        "epd.transient.acquire",
                        null,
                        "${current.name} is already active; nested scopes must use the same mode",
                    ),
                )
            }
            if (activeTokens.isEmpty()) {
                val applied = applyMode(mode)
                if (applied.isFailure) return Result.failure(requireNotNull(applied.exceptionOrNull()))
                activeMode = mode
            }
            val id = ids.incrementAndGet()
            activeTokens += id
            return Result.success(
                RefreshModeToken(
                    RefreshModeReceipt(mode, RefreshModeScope.PROCESS, activeTokens.size),
                    id,
                    this,
                ),
            )
        } finally {
            mutex.unlock()
        }
    }

    suspend fun release(tokenId: Long): Result<Unit> {
        mutex.lock()
        try {
            if (!activeTokens.remove(tokenId)) return Result.success(Unit)
            if (activeTokens.isNotEmpty()) return Result.success(Unit)
            val cleared = clearMode()
            if (cleared.isSuccess) activeMode = null
            return cleared
        } finally {
            mutex.unlock()
        }
    }

    fun releaseAsync(tokenId: Long, completion: CompletableDeferred<Result<Unit>>) {
        asynchronousScope.launch {
            completion.complete(withContext(NonCancellable) { release(tokenId) })
        }
    }
}
