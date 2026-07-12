/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  com.onyx.android.sdk.data.note.TouchPoint
 */
package com.onyx.android.sdk.pen;

import android.graphics.RectF;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;

public abstract class RawInputCallback {
    public abstract void onBeginRawDrawing(boolean var1, TouchPoint var2);

    public abstract void onEndRawDrawing(boolean var1, TouchPoint var2);

    public abstract void onRawDrawingTouchPointMoveReceived(TouchPoint var1);

    public abstract void onRawDrawingTouchPointListReceived(TouchPointList var1);

    public abstract void onBeginRawErasing(boolean var1, TouchPoint var2);

    public abstract void onEndRawErasing(boolean var1, TouchPoint var2);

    public abstract void onRawErasingTouchPointMoveReceived(TouchPoint var1);

    public abstract void onRawErasingTouchPointListReceived(TouchPointList var1);

    public void onPenActive(TouchPoint point) {
    }

    public void onPenUpRefresh(RectF refreshRect) {
    }
}

