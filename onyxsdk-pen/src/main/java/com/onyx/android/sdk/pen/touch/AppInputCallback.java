package com.onyx.android.sdk.pen.touch;

import android.view.MotionEvent;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/touch/AppInputCallback.class */
public abstract class AppInputCallback {
    public abstract void onBeginRawDrawing(MotionEvent motionEvent, boolean z, TouchPoint touchPoint);

    public abstract void onEndRawDrawing(MotionEvent motionEvent, boolean z, TouchPoint touchPoint);

    public abstract void onRawDrawingTouchPointMoveReceived(TouchPoint touchPoint);

    public abstract void onRawDrawingTouchPointListReceived(MotionEvent motionEvent, TouchPointList touchPointList);

    public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
    }

    public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
    }

    public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
    }

    public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
    }

    public void onPenActive(TouchPoint point) {
    }
}

