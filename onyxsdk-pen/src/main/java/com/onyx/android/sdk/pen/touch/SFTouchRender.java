package com.onyx.android.sdk.pen.touch;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.pen.EpdPenManager;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.RawInputManager;
import com.onyx.android.sdk.pen.RawInputReader;
import com.onyx.android.sdk.pen.data.TouchPointList;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/touch/SFTouchRender.class */
public class SFTouchRender implements TouchRender {
    static class a {
    }
    private WeakReference<View> a;
    private RawInputCallback b;
    private EpdPenManager c;
    private RawInputManager d;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/touch/SFTouchRender$b.class */
    class b extends RawInputCallback {
        RawInputCallback a;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onBeginRawDrawing(shortcutDrawing, point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onEndRawDrawing(outLimitRegion, point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawDrawingTouchPointMoveReceived(point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawDrawingTouchPointListReceived(pointList);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onBeginRawErasing(shortcutErasing, point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onEndRawErasing(outLimitRegion, point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawErasingTouchPointMoveReceived(point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawErasingTouchPointListReceived(pointList);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenActive(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onPenActive(point);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenUpRefresh(RectF refreshRect) {
            if (this.a == null) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onPenUpRefresh(refreshRect);
            });
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        b(RawInputCallback callback) {
            this.a = callback;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public SFTouchRender(View view, RawInputCallback callback) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static TouchRender create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            return new SFTouchRender(hostView, callback);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private EpdPenManager d() {
        if (this.c == null) {
            this.c = new EpdPenManager();
        }
        return this.c;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private RawInputManager e() {
        if (this.d == null) {
            this.d = new RawInputManager();
        }
        return this.d;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void i() {
        setStrokeStyle(0);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void b() {
        e().startRawInputReader();
        d().startDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void c() {
        e().quitRawInputReader();
        d().quitDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void f() {
        EpdController.leaveScribbleMode(e().getHostView());
        e().pauseRawInputReader();
        d().pauseDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void g() {
        EpdController.leaveScribbleMode(e().getHostView());
        d().pauseDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void j() {
        d().resumeDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void k() {
        e().resumeRawInputReader();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void h() {
        e().pauseRawInputReader();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void bindHostView(View view, RawInputCallback callback) {
        this.a = new WeakReference<>(view);
        b bVar = new b(callback);
        this.b = bVar;
        a(view, bVar);
        a(view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public View getHostView() {
        WeakReference<View> weakReference = this.a;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeStyle(int style) {
        d().setStrokeStyle(style);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeColor(int color) {
        EpdPenManager.setStrokeColor(color);
        e().setStrokeColor(color);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeWidth(float w) {
        e().setStrokeWidth(w);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void debugLog(boolean enable) {
        RawInputReader.debugLog(enable);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        e().setLimitRect(limitRect, excludeRectList);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setExcludeRect(List<Rect> excludeRectList) {
        e().setExcludeRect(excludeRectList);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void openDrawing() {
        i();
        b();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void closeDrawing() {
        i();
        f();
        c();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setDrawingRenderEnabled(boolean enabled) {
        if (enabled) {
            j();
        } else {
            g();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setBrushRawDrawingEnabled(boolean enable) {
        Device.currentDevice().setBrushRawDrawingEnabled(enable);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setEraserRawDrawingEnabled(boolean enable, int eraserStyle) {
        Device.currentDevice().setEraserRawDrawingEnabled(enable, eraserStyle);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setInputReaderEnable(boolean enabled) {
        if (enabled) {
            k();
        } else {
            h();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setSingleRegionMode() {
        e().setSingleRegionMode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setMultiRegionMode() {
        e().setMultiRegionMode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        e().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshEnabled(boolean enable) {
        e().setPenUpRefreshEnabled(enable);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFilterRepeatMovePoint(boolean filter) {
        e().setFilterRepeatMovePoint(filter);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPostInputEvent(boolean post) {
        e().setPostInputEvent(post);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableSideBtnErase(boolean enable) {
        e().enableSideBtnErase(enable);
        Device.currentDevice().setEnablePenSideButton(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableFingerTouch(boolean enable) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void onlyEnableFingerTouch(boolean only) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setHostViewScrollListenerEnabled(boolean enable) {
        e().setHostViewScrollListenerEnabled(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFingerTouchPressure(float pressure) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void printTouchInfo() {
        RawInputManager rawInputManager = this.d;
        if (rawInputManager != null) {
            rawInputManager.printTouchInfo();
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableFingerTouchPressure(boolean enable) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setTouchListenerEnabled(boolean enable) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX INFO: Access modifiers changed from: private */
    private boolean a() {
        return this.b == null || getHostView() == null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        e().setLimitRect(limitRectList, excludeRectList);
    }

    private void a(View view) {
        d().setHostView(view);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(List<Rect> limitRectList) {
        e().setLimitRect(limitRectList);
    }

    private void a(View view, RawInputCallback callback) {
        e().setHostView(view);
        e().setRawInputCallback(callback);
    }
}
