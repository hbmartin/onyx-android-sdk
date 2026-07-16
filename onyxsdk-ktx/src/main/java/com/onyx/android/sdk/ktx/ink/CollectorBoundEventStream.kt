package com.onyx.android.sdk.ktx.ink

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** A single-collector stream that drops events outside an active collection window. */
internal class CollectorBoundEventStream<T>(
    private val concurrentCollectorMessage: String = "Only one collector is supported at a time",
) {
    private val lock = Any()
    private val collecting = AtomicBoolean()
    private val channel = Channel<T>(Channel.UNLIMITED)

    val flow: Flow<T> = flow {
        synchronized(lock) {
            check(collecting.compareAndSet(false, true)) {
                concurrentCollectorMessage
            }
            drainLocked()
        }
        try {
            for (value in channel) emit(value)
        } finally {
            synchronized(lock) {
                collecting.set(false)
                drainLocked()
            }
        }
    }

    fun tryEmit(value: T): Boolean = synchronized(lock) {
        collecting.get() && channel.trySend(value).isSuccess
    }

    fun close(cause: Throwable? = null) {
        synchronized(lock) {
            channel.close(cause)
        }
    }

    private fun drainLocked() {
        while (channel.tryReceive().isSuccess) {
            // Events from an ended collection window must not reach the next collector.
        }
    }
}
