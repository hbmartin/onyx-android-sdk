package com.onyx.android.sdk.pen;

import android.graphics.RectF;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;

public abstract class RawInputCallback {
    public abstract void onBeginRawDrawing(boolean z, TouchPoint touchPoint);

    public abstract void onEndRawDrawing(boolean z, TouchPoint touchPoint);

    public abstract void onRawDrawingTouchPointMoveReceived(TouchPoint touchPoint);

    public abstract void onRawDrawingTouchPointListReceived(TouchPointList touchPointList);

    public abstract void onBeginRawErasing(boolean z, TouchPoint touchPoint);

    public abstract void onEndRawErasing(boolean z, TouchPoint touchPoint);

    public abstract void onRawErasingTouchPointMoveReceived(TouchPoint touchPoint);

    public abstract void onRawErasingTouchPointListReceived(TouchPointList touchPointList);

    public void onPenActive(TouchPoint point) {
    }

    public void onPenUpRefresh(RectF refreshRect) {
    }
}

