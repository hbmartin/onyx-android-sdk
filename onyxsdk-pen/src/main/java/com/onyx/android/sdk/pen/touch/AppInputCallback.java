/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  com.onyx.android.sdk.data.note.TouchPoint
 */
package com.onyx.android.sdk.pen.touch;

import android.view.MotionEvent;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;

public abstract class AppInputCallback {
    public abstract void onBeginRawDrawing(MotionEvent var1, boolean var2, TouchPoint var3);

    public abstract void onEndRawDrawing(MotionEvent var1, boolean var2, TouchPoint var3);

    public abstract void onRawDrawingTouchPointMoveReceived(TouchPoint var1);

    public abstract void onRawDrawingTouchPointListReceived(MotionEvent var1, TouchPointList var2);

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

