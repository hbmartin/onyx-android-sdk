package com.onyx.android.sdk.pen.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.RawInputListenerV2;
import java.util.List;

public interface TouchRender {
    void bindHostView(View view, RawInputCallback rawInputCallback);

    default void setRawInputListenerV2(RawInputListenerV2 listener) {
    }

    View getHostView();

    default boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    void setStrokeStyle(int i);

    void setStrokeColor(int i);

    void setStrokeWidth(float f);

    void debugLog(boolean z);

    void setLimitRect(Rect rect, List<Rect> list);

    void setLimitRect(List<Rect> list, List<Rect> list2);

    void setLimitRect(List<Rect> list);

    void setExcludeRect(List<Rect> list);

    void openDrawing();

    void closeDrawing();

    void setDrawingRenderEnabled(boolean z);

    void setBrushRawDrawingEnabled(boolean z);

    void setEraserRawDrawingEnabled(boolean z, int i);

    void setInputReaderEnable(boolean z);

    void setSingleRegionMode();

    void setMultiRegionMode();

    void setPenUpRefreshTimeMs(int i);

    void setPenUpRefreshEnabled(boolean z);

    void setFilterRepeatMovePoint(boolean z);

    void setPostInputEvent(boolean z);

    void enableSideBtnErase(boolean z);

    void enableFingerTouch(boolean z);

    void onlyEnableFingerTouch(boolean z);

    void setTouchListenerEnabled(boolean z);

    void setHostViewScrollListenerEnabled(boolean z);

    void enableFingerTouchPressure(boolean z);

    void setFingerTouchPressure(float f);

    void printTouchInfo();
}
