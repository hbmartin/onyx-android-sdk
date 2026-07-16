package com.onyx.android.sdk.ktx.ink

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** A single-collector stream that drops events outside an active collection window. */
@Suppress("AvoidVarsExceptWithDelegate")
internal class CollectorBoundEventStream<T>(
    private val concurrentCollectorMessage: String = "Only one collector is supported at a time",
) {
    private val lock = Any()
    private var activeChannel: Channel<T>? = null
    private var closed = false
    private var closeCause: Throwable? = null

    val flow: Flow<T> = flow {
        val collectionChannel = synchronized(lock) {
            check(activeChannel == null) {
                concurrentCollectorMessage
            }
            Channel<T>(Channel.UNLIMITED).also { channel ->
                activeChannel = channel
                if (closed) channel.close(closeCause)
            }
        }
        try {
            for (value in collectionChannel) emit(value)
        } finally {
            synchronized(lock) {
                if (activeChannel === collectionChannel) activeChannel = null
                collectionChannel.close()
            }
        }
    }

    fun tryEmit(value: T): Boolean = synchronized(lock) {
        activeChannel?.trySend(value)?.isSuccess == true
    }

    fun close(cause: Throwable? = null) {
        synchronized(lock) {
            if (!closed) {
                closed = true
                closeCause = cause
                activeChannel?.close(cause)
            }
        }
    }
}
