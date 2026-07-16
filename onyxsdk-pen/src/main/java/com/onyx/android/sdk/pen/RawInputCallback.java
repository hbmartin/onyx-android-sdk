package com.onyx.android.sdk.pen;

import android.graphics.RectF;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;

/**
 * Legacy raw-input callback retained for source and binary compatibility.
 *
 * <p>Callbacks run on the selected input source's thread and do not overlap for one
 * {@link TouchHelper}. Move points delivered individually are also present in the later batch
 * callback, so applications must consume either the individual stream or the batches rather than
 * combining both. {@link TouchPoint} and {@link TouchPointList} are legacy mutable values and must
 * be copied before retaining them after the callback returns.</p>
 *
 * @deprecated Prefer {@link RawInputListenerV2}, whose events are immutable, normalized, and
 * unified across pen and eraser tools.
 */
@Deprecated
public abstract class RawInputCallback {
    public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint touchPoint) {
    }

    public void onEndRawDrawing(boolean outsideLimitRegion, TouchPoint touchPoint) {
    }

    public void onRawDrawingTouchPointMoveReceived(TouchPoint touchPoint) {
    }

    public void onRawDrawingTouchPointListReceived(TouchPointList touchPointList) {
    }

    public void onBeginRawErasing(boolean shortcutErasing, TouchPoint touchPoint) {
    }

    public void onEndRawErasing(boolean outsideLimitRegion, TouchPoint touchPoint) {
    }

    public void onRawErasingTouchPointMoveReceived(TouchPoint touchPoint) {
    }

    public void onRawErasingTouchPointListReceived(TouchPointList touchPointList) {
    }

    public void onPenActive(TouchPoint point) {
    }

    public void onPenUpRefresh(RectF refreshRect) {
    }
}
