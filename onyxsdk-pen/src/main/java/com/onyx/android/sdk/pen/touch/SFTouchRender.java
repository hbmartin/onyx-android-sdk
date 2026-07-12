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

public class SFTouchRender implements TouchRender {
    static class a {
    }
    private WeakReference<View> a;
    private RawInputCallback b;
    private EpdPenManager c;
    private RawInputManager d;

    class b extends RawInputCallback {
        RawInputCallback a;

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onBeginRawDrawing(shortcutDrawing, point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onEndRawDrawing(outLimitRegion, point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawDrawingTouchPointMoveReceived(point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawDrawingTouchPointListReceived(pointList);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onBeginRawErasing(shortcutErasing, point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onEndRawErasing(outLimitRegion, point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawErasingTouchPointMoveReceived(point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onRawErasingTouchPointListReceived(pointList);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenActive(TouchPoint point) {
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onPenActive(point);
            });
        }

        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenUpRefresh(RectF refreshRect) {
            if (this.a == null) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> {
                this.a.onPenUpRefresh(refreshRect);
            });
        }

        b(RawInputCallback callback) {
            this.a = callback;
        }
    }

    public SFTouchRender(View view, RawInputCallback callback) {
    }

    public static TouchRender create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            return new SFTouchRender(hostView, callback);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    private EpdPenManager d() {
        if (this.c == null) {
            this.c = new EpdPenManager();
        }
        return this.c;
    }

    private RawInputManager e() {
        if (this.d == null) {
            this.d = new RawInputManager();
        }
        return this.d;
    }

    private void i() {
        setStrokeStyle(0);
    }

    private void b() {
        e().startRawInputReader();
        d().startDrawing();
    }

    private void c() {
        e().quitRawInputReader();
        d().quitDrawing();
    }

    private void f() {
        EpdController.leaveScribbleMode(e().getHostView());
        e().pauseRawInputReader();
        d().pauseDrawing();
    }

    private void g() {
        EpdController.leaveScribbleMode(e().getHostView());
        d().pauseDrawing();
    }

    private void j() {
        d().resumeDrawing();
    }

    private void k() {
        e().resumeRawInputReader();
    }

    private void h() {
        e().pauseRawInputReader();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void bindHostView(View view, RawInputCallback callback) {
        this.a = new WeakReference<>(view);
        b bVar = new b(callback);
        this.b = bVar;
        a(view, bVar);
        a(view);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public View getHostView() {
        WeakReference<View> weakReference = this.a;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeStyle(int style) {
        d().setStrokeStyle(style);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeColor(int color) {
        EpdPenManager.setStrokeColor(color);
        e().setStrokeColor(color);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeWidth(float w) {
        e().setStrokeWidth(w);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void debugLog(boolean enable) {
        RawInputReader.debugLog(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        e().setLimitRect(limitRect, excludeRectList);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setExcludeRect(List<Rect> excludeRectList) {
        e().setExcludeRect(excludeRectList);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void openDrawing() {
        i();
        b();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void closeDrawing() {
        i();
        f();
        c();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setDrawingRenderEnabled(boolean enabled) {
        if (enabled) {
            j();
        } else {
            g();
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setBrushRawDrawingEnabled(boolean enable) {
        Device.currentDevice().setBrushRawDrawingEnabled(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setEraserRawDrawingEnabled(boolean enable, int eraserStyle) {
        Device.currentDevice().setEraserRawDrawingEnabled(enable, eraserStyle);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setInputReaderEnable(boolean enabled) {
        if (enabled) {
            k();
        } else {
            h();
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setSingleRegionMode() {
        e().setSingleRegionMode();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setMultiRegionMode() {
        e().setMultiRegionMode();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        e().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshEnabled(boolean enable) {
        e().setPenUpRefreshEnabled(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFilterRepeatMovePoint(boolean filter) {
        e().setFilterRepeatMovePoint(filter);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPostInputEvent(boolean post) {
        e().setPostInputEvent(post);
    }

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

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setHostViewScrollListenerEnabled(boolean enable) {
        e().setHostViewScrollListenerEnabled(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFingerTouchPressure(float pressure) {
    }

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

    private boolean a() {
        return this.b == null || getHostView() == null;
    }

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
