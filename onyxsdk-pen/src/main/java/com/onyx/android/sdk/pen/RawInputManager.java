package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/RawInputManager.class */
public class RawInputManager {
    private RawInputCallback a;
    private RawInputReader b = null;
    private boolean c = true;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/RawInputManager$a.class */
    class a extends RawInputCallback {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        a() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawDrawing(boolean shortcut, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onBeginRawDrawing(shortcut, point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onEndRawDrawing(outLimitRegion, point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawDrawingTouchPointMoveReceived(point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawDrawingTouchPointListReceived(pointList);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawErasing(boolean shortcut, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onBeginRawErasing(shortcut, point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onEndRawErasing(outLimitRegion, point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawErasingTouchPointMoveReceived(point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onRawErasingTouchPointListReceived(pointList);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenActive(TouchPoint point) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onPenActive(point);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenUpRefresh(RectF refreshRect) {
            if (RawInputManager.this.isUseRawInput() && RawInputManager.this.a != null) {
                RawInputManager.this.a.onPenUpRefresh(refreshRect);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setRawInputCallback(RawInputCallback callback) {
        this.a = callback;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void startRawInputReader() {
        if (isUseRawInput()) {
            a().setRawInputCallback(new a());
            a().start();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void resumeRawInputReader() {
        if (isUseRawInput()) {
            a().resume();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void pauseRawInputReader() {
        if (isUseRawInput()) {
            a().pause();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void quitRawInputReader() {
        if (isUseRawInput()) {
            a().quit();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isUseRawInput() {
        return this.c;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public RawInputManager setUseRawInput(boolean use) {
        this.c = use;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public View getHostView() {
        return a().getHostView();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public RawInputManager setHostView(View view) {
        a().setHostView(view);
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        a().setLimitRect(rect);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public RawInputManager setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        a().setLimitRect(limitRect);
        a().setExcludeRect(excludeRectList);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public RawInputManager setExcludeRect(List<Rect> excludeRectList) {
        a().setExcludeRect(excludeRectList);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setStrokeWidth(float w) {
        a().setStrokeWidth(w);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setStrokeColor(int color) {
        a().setStrokeColor(color);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setSingleRegionMode() {
        a().setSingleRegionMode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setMultiRegionMode() {
        a().setMultiRegionMode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        a().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setPenUpRefreshEnabled(boolean enable) {
        a().setPenUpRefreshEnabled(enable);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setFilterRepeatMovePoint(boolean filter) {
        a().setFilterRepeatMovePoint(filter);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setPostInputEvent(boolean post) {
        a().setPostInputEvent(post);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void enableSideBtnErase(boolean enable) {
        a().enableSideBtnErase(enable);
    }

    public void setHostViewScrollListenerEnabled(boolean enable) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void printTouchInfo() {
        RawInputReader rawInputReader = this.b;
        if (rawInputReader != null) {
            rawInputReader.printTouchInfo();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
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

