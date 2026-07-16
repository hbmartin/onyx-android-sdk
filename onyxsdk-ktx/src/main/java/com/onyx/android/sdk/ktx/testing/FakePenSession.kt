package com.onyx.android.sdk.ktx.testing

import android.graphics.Bitmap
import android.graphics.Rect
import com.onyx.android.sdk.ktx.ink.CommitReceipt
import com.onyx.android.sdk.ktx.ink.PenSession
import com.onyx.android.sdk.ktx.ink.RawInkSessionState
import com.onyx.android.sdk.ktx.ink.SuspensionReason
import com.onyx.android.sdk.ktx.model.CommitOptions
import com.onyx.android.sdk.ktx.model.InkPreview
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenEvent
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/** Deterministic consumer-facing test double; it performs no Android firmware calls. */
class FakePenSession(
    private val commitHandler: suspend (
        bitmap: Bitmap,
        destination: Rect,
        dirtyRegion: Rect,
        options: CommitOptions,
    ) -> Result<CommitReceipt> = { _, _, _, _ ->
        Result.failure(
            OnyxFailure.UnsupportedCapability(
                "fake.commit",
                null,
                "No commit result was configured for FakePenSession",
            ),
        )
    },
) : PenSession {
    private val mutableState = MutableStateFlow<RawInkSessionState>(RawInkSessionState.Active(1))
    private val mutablePreview = MutableStateFlow<InkPreview?>(null)
    private val eventChannel = Channel<PenEvent>(Channel.UNLIMITED)
    private val strokeChannel = Channel<InkStroke>(Channel.UNLIMITED)
    private val closed = AtomicBoolean()

    override val state: StateFlow<RawInkSessionState> = mutableState.asStateFlow()
    override val events: Flow<PenEvent> = eventChannel.receiveAsFlow()
    override val preview: StateFlow<InkPreview?> = mutablePreview.asStateFlow()
    override val completedStrokes: Flow<InkStroke> = strokeChannel.receiveAsFlow()

    suspend fun emit(event: PenEvent) {
        check(!closed.get()) { "FakePenSession is closed" }
        eventChannel.send(event)
        mutablePreview.value = event.toPreview()
    }

    suspend fun emit(stroke: InkStroke) {
        check(!closed.get()) { "FakePenSession is closed" }
        strokeChannel.send(stroke)
    }

    override suspend fun pause(): Result<Unit> {
        if (!closed.get()) {
            mutableState.value = RawInkSessionState.Suspended(1, SuspensionReason.REQUESTED)
            mutablePreview.value = null
        }
        return Result.success(Unit)
    }

    override suspend fun resume(): Result<Unit> = if (closed.get()) {
        Result.failure(OnyxFailure.InvalidState("fake.resume", null, "FakePenSession is closed"))
    } else {
        mutableState.value = RawInkSessionState.Active(1)
        Result.success(Unit)
    }

    override suspend fun commit(
        bitmap: Bitmap,
        destination: Rect,
        dirtyRegion: Rect,
        options: CommitOptions,
    ): Result<CommitReceipt> = commitHandler(bitmap, destination, dirtyRegion, options)

    override suspend fun closeAndAwait(): Result<Unit> {
        close()
        return Result.success(Unit)
    }

    override fun close() {
        if (closed.compareAndSet(false, true)) {
            mutableState.value = RawInkSessionState.Closed
            mutablePreview.value = null
            eventChannel.close()
            strokeChannel.close()
        }
    }
}

private fun PenEvent.toPreview(): InkPreview? = when (phase) {
    com.onyx.android.sdk.ktx.model.PenPhase.DOWN -> InkPreview(point, InkPreview.Phase.DOWN)
    com.onyx.android.sdk.ktx.model.PenPhase.MOVE -> InkPreview(point, InkPreview.Phase.MOVE)
    com.onyx.android.sdk.ktx.model.PenPhase.UP -> InkPreview(point, InkPreview.Phase.UP)
    com.onyx.android.sdk.ktx.model.PenPhase.PROXIMITY -> null
}
