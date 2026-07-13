package com.onyx.android.sdk.ktx

import android.graphics.RectF
import com.onyx.android.sdk.data.note.TouchPoint
import com.onyx.android.sdk.pen.RawInputCallback
import com.onyx.android.sdk.pen.RawInputManager
import com.onyx.android.sdk.pen.data.TouchPointList
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.rx2.asFlow as asCoroutineFlow

sealed class RawInputEvent {
    data class DrawingStarted(val shortcut: Boolean, val point: TouchPoint) : RawInputEvent()
    data class DrawingEnded(val outsideLimitRegion: Boolean, val point: TouchPoint) : RawInputEvent()
    data class DrawingPoint(val point: TouchPoint) : RawInputEvent()
    data class DrawingPoints(val points: TouchPointList) : RawInputEvent()
    data class ErasingStarted(val shortcut: Boolean, val point: TouchPoint) : RawInputEvent()
    data class ErasingEnded(val outsideLimitRegion: Boolean, val point: TouchPoint) : RawInputEvent()
    data class ErasingPoint(val point: TouchPoint) : RawInputEvent()
    data class ErasingPoints(val points: TouchPointList) : RawInputEvent()
    data class PenActive(val point: TouchPoint) : RawInputEvent()
    data class PenUpRefresh(val refreshRect: RectF) : RawInputEvent()
}

/**
 * Exposes the manager's single callback slot as a cold flow. Collection owns
 * that callback slot until cancellation, when the callback is cleared.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun RawInputManager.rawInputEvents(): Flow<RawInputEvent> = callbackFlow {
    val callback = rawInputCallback { event -> trySend(event) }
    setRawInputCallback(callback)
    awaitClose { setRawInputCallback(null) }
}

/** Converts the SDK's RxJava 2 stream into a cancellation-aware Kotlin flow. */
fun <T : Any> Observable<T>.toKotlinFlow(): Flow<T> = asCoroutineFlow()

private fun rawInputCallback(emit: (RawInputEvent) -> Unit): RawInputCallback =
    object : RawInputCallback() {
        override fun onBeginRawDrawing(shortcut: Boolean, point: TouchPoint) {
            emit(RawInputEvent.DrawingStarted(shortcut, point))
        }

        override fun onEndRawDrawing(outsideLimitRegion: Boolean, point: TouchPoint) {
            emit(RawInputEvent.DrawingEnded(outsideLimitRegion, point))
        }

        override fun onRawDrawingTouchPointMoveReceived(point: TouchPoint) {
            emit(RawInputEvent.DrawingPoint(point))
        }

        override fun onRawDrawingTouchPointListReceived(points: TouchPointList) {
            emit(RawInputEvent.DrawingPoints(points))
        }

        override fun onBeginRawErasing(shortcut: Boolean, point: TouchPoint) {
            emit(RawInputEvent.ErasingStarted(shortcut, point))
        }

        override fun onEndRawErasing(outsideLimitRegion: Boolean, point: TouchPoint) {
            emit(RawInputEvent.ErasingEnded(outsideLimitRegion, point))
        }

        override fun onRawErasingTouchPointMoveReceived(point: TouchPoint) {
            emit(RawInputEvent.ErasingPoint(point))
        }

        override fun onRawErasingTouchPointListReceived(points: TouchPointList) {
            emit(RawInputEvent.ErasingPoints(points))
        }

        override fun onPenActive(point: TouchPoint) {
            emit(RawInputEvent.PenActive(point))
        }

        override fun onPenUpRefresh(refreshRect: RectF) {
            emit(RawInputEvent.PenUpRefresh(refreshRect))
        }
    }
