/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.onyx.android.sdk.pen.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.pen.RawInputCallback;
import java.util.List;

public interface TouchRender {
    public void bindHostView(View var1, RawInputCallback var2);

    public View getHostView();

    default public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void setStrokeStyle(int var1);

    public void setStrokeColor(int var1);

    public void setStrokeWidth(float var1);

    public void debugLog(boolean var1);

    public void setLimitRect(Rect var1, List<Rect> var2);

    public void setLimitRect(List<Rect> var1, List<Rect> var2);

    public void setLimitRect(List<Rect> var1);

    public void setExcludeRect(List<Rect> var1);

    public void openDrawing();

    public void closeDrawing();

    public void setDrawingRenderEnabled(boolean var1);

    public void setBrushRawDrawingEnabled(boolean var1);

    public void setEraserRawDrawingEnabled(boolean var1, int var2);

    public void setInputReaderEnable(boolean var1);

    public void setSingleRegionMode();

    public void setMultiRegionMode();

    public void setPenUpRefreshTimeMs(int var1);

    public void setPenUpRefreshEnabled(boolean var1);

    public void setFilterRepeatMovePoint(boolean var1);

    public void setPostInputEvent(boolean var1);

    public void enableSideBtnErase(boolean var1);

    public void enableFingerTouch(boolean var1);

    public void onlyEnableFingerTouch(boolean var1);

    public void setTouchListenerEnabled(boolean var1);

    public void setHostViewScrollListenerEnabled(boolean var1);

    public void enableFingerTouchPressure(boolean var1);

    public void setFingerTouchPressure(float var1);

    public void printTouchInfo();
}

