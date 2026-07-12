package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;
import java.util.List;

public class RawInputManager {
    private RawInputCallback a;
    private RawInputReader b = null;
    private boolean c = true;

    class a extends RawInputCallback {
        a() {
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawDrawing(boolean shortcut, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onBeginRawDrawing(shortcut, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onEndRawDrawing(outLimitRegion, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawDrawingTouchPointMoveReceived(point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawDrawingTouchPointListReceived(pointList);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawErasing(boolean shortcut, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onBeginRawErasing(shortcut, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onEndRawErasing(outLimitRegion, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawErasingTouchPointMoveReceived(point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawErasingTouchPointListReceived(pointList);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenActive(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onPenActive(point);
            }
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenUpRefresh(RectF refreshRect) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onPenUpRefresh(refreshRect);
            }
        }
    }

    public void setRawInputCallback(RawInputCallback callback) {
        this.a = callback;
    }

    public void startRawInputReader() {
        if (isUseRawInput()) {
            a().setRawInputCallback(new a());
            a().start();
        }
    }

    public void resumeRawInputReader() {
        if (isUseRawInput()) {
            a().resume();
        }
    }

    public void pauseRawInputReader() {
        if (isUseRawInput()) {
            a().pause();
        }
    }

    public void quitRawInputReader() {
        if (isUseRawInput()) {
            a().quit();
        }
    }

    public boolean isUseRawInput() {
        return this.c;
    }

    public RawInputManager setUseRawInput(boolean use) {
        this.c = use;
        return this;
    }

    public View getHostView() {
        return a().getHostView();
    }

    public RawInputManager setHostView(View view) {
        a().setHostView(view);
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        a().setLimitRect(rect);
        return this;
    }

    public RawInputManager setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        a().setLimitRect(limitRect);
        a().setExcludeRect(excludeRectList);
        return this;
    }

    public RawInputManager setExcludeRect(List<Rect> excludeRectList) {
        a().setExcludeRect(excludeRectList);
        return this;
    }

    public void setStrokeWidth(float w) {
        a().setStrokeWidth(w);
    }

    public void setStrokeColor(int color) {
        a().setStrokeColor(color);
    }

    public void setSingleRegionMode() {
        a().setSingleRegionMode();
    }

    public void setMultiRegionMode() {
        a().setMultiRegionMode();
    }

    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        a().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
    }

    public void setPenUpRefreshEnabled(boolean enable) {
        a().setPenUpRefreshEnabled(enable);
    }

    public void setFilterRepeatMovePoint(boolean filter) {
        a().setFilterRepeatMovePoint(filter);
    }

    public void setPostInputEvent(boolean post) {
        a().setPostInputEvent(post);
    }

    public void enableSideBtnErase(boolean enable) {
        a().enableSideBtnErase(enable);
    }

    public void setHostViewScrollListenerEnabled(boolean enable) {
    }

    public void printTouchInfo() {
        RawInputReader rawInputReader = this.b;
        if (rawInputReader != null) {
            rawInputReader.printTouchInfo();
        }
    }

    private RawInputReader a() {
        if (this.b == null) {
            this.b = new RawInputReader();
        }
        return this.b;
    }

    public RawInputManager setLimitRect(List<Rect> limitRect, List<Rect> excludeRectList) {
        a().setLimitRect(limitRect);
        a().setExcludeRect(excludeRectList);
        return this;
    }

    public RawInputManager setLimitRect(List<Rect> limitRectList) {
        a().setLimitRect(limitRectList);
        return this;
    }
}

