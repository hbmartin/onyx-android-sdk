package com.onyx.android.sdk.pen.touch;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.RawInputAxisRange;
import com.onyx.android.sdk.pen.RawInputDeviceInfo;
import com.onyx.android.sdk.pen.RawInputEventV2;
import com.onyx.android.sdk.pen.RawInputListenerV2;
import com.onyx.android.sdk.pen.RawInputPhase;
import com.onyx.android.sdk.pen.RawInputTool;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.rx.SingleThreadScheduler;
import com.onyx.android.sdk.utils.TouchUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class AppTouchRender implements TouchRender {
    private AppTouchInputReader a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e = true;
    private WeakReference<View> f;
    private Disposable g;
    private ObservableEmitter<TouchPoint> h;
    private final AtomicLong rawSequence = new AtomicLong();
    private volatile RawInputListenerV2 rawInputListenerV2;
    private RawInputTool rawInputTool = RawInputTool.PEN;

    class a extends AppInputCallback {
        final /* synthetic */ RawInputCallback a;

        a(RawInputCallback rawInputCallback) {
            this.a = rawInputCallback;
        }

        @Override // com.onyx.android.sdk.pen.touch.AppInputCallback
        public void onBeginRawDrawing(MotionEvent event, boolean shortcutDrawing, TouchPoint point) {
            AppTouchRender.this.a(event, point);
            AppTouchRender.this.rawInputTool = RawInputTool.PEN;
            AppTouchRender.this.emitRawInput(RawInputPhase.DOWN, point, false, false);
            if (AppTouchRender.this.e) {
                this.a.onBeginRawDrawing(shortcutDrawing, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.touch.AppInputCallback
        public void onEndRawDrawing(MotionEvent event, boolean outLimitRegion, TouchPoint point) {
            AppTouchRender.this.a();
            AppTouchRender.this.b(event, new TouchPoint(point));
            AppTouchRender.this.emitRawInput(
                    RawInputPhase.UP, point, outLimitRegion, false);
            if (AppTouchRender.this.e) {
                this.a.onEndRawDrawing(outLimitRegion, point);
            }
        }

        @Override // com.onyx.android.sdk.pen.touch.AppInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            if (AppTouchRender.this.h != null) {
                AppTouchRender.this.h.onNext(new TouchPoint(point));
            }
            AppTouchRender.this.emitRawInput(RawInputPhase.MOVE, point, false, false);
            if (AppTouchRender.this.e) {
                this.a.onRawDrawingTouchPointMoveReceived(point);
            }
        }

        @Override // com.onyx.android.sdk.pen.touch.AppInputCallback
        public void onRawDrawingTouchPointListReceived(MotionEvent event, TouchPointList pointList) {
            AppTouchRender.this.a();
            if (AppTouchRender.this.e) {
                this.a.onRawDrawingTouchPointListReceived(pointList);
            }
        }

        @Override
        public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
            AppTouchRender.this.rawInputTool = rawInputToolForErasing(shortcutErasing);
            AppTouchRender.this.emitRawInput(RawInputPhase.DOWN, point, false, false);
            if (AppTouchRender.this.e) {
                this.a.onBeginRawErasing(shortcutErasing, point);
            }
        }

        @Override
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            AppTouchRender.this.emitRawInput(
                    RawInputPhase.UP, point, outLimitRegion, false);
            if (AppTouchRender.this.e) {
                this.a.onEndRawErasing(outLimitRegion, point);
            }
        }

        @Override
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            AppTouchRender.this.emitRawInput(RawInputPhase.MOVE, point, false, false);
            if (AppTouchRender.this.e) {
                this.a.onRawErasingTouchPointMoveReceived(point);
            }
        }

        @Override
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (AppTouchRender.this.e) {
                this.a.onRawErasingTouchPointListReceived(pointList);
            }
        }
    }

    public AppTouchRender(View view, RawInputCallback callback) {
    }

    static RawInputTool rawInputToolForErasing(boolean shortcutErasing) {
        return shortcutErasing ? RawInputTool.SIDE_ERASER : RawInputTool.TAIL_ERASER;
    }

    private void d() {
        setStrokeStyle(0);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void bindHostView(View view, RawInputCallback callback) {
        this.a = new AppTouchInputReader(a(callback));
        this.f = new WeakReference<>(view);
        if (this.e) {
            view.setOnTouchListener((v, event) -> {
                return onTouchEvent(event);
            });
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public boolean onTouchEvent(MotionEvent event) {
        if (b()) {
            return this.a.processMotionEvent(event);
        }
        return false;
    }

    @Override
    public void setRawInputListenerV2(RawInputListenerV2 listener) {
        this.rawInputListenerV2 = listener;
        if (listener == null) {
            return;
        }
        View host = getHostView();
        RawInputAxisRange x = host == null
                ? null
                : new RawInputAxisRange(0, Math.max(1, host.getWidth()), 0, 0, 0);
        RawInputAxisRange y = host == null
                ? null
                : new RawInputAxisRange(0, Math.max(1, host.getHeight()), 0, 0, 0);
        listener.onRawInputDeviceInfo(new RawInputDeviceInfo(
                x, y, new RawInputAxisRange(0, 4096, 0, 0, 0), null, null, true));
    }

    private void emitRawInput(
            RawInputPhase phase,
            TouchPoint point,
            boolean outsideLimitRegion,
            boolean forced) {
        RawInputListenerV2 listener = rawInputListenerV2;
        if (listener == null) {
            return;
        }
        float normalizedPressure = Math.max(0.0f, Math.min(1.0f, point.getPressure()));
        listener.onRawInputEvent(new RawInputEventV2(
                phase,
                rawInputTool,
                point.getX(),
                point.getY(),
                Math.round(normalizedPressure * 4096.0f),
                normalizedPressure,
                4096,
                point.getTiltX(),
                point.getTiltY(),
                point.getTimestamp() * 1_000_000L,
                rawSequence.incrementAndGet(),
                outsideLimitRegion,
                forced));
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public View getHostView() {
        WeakReference<View> weakReference = this.f;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeStyle(int style) {
        EpdController.setStrokeStyle(style);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeColor(int color) {
        EpdController.setStrokeColor(color);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setInputReaderEnable(boolean enabled) {
        this.b = enabled;
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setSingleRegionMode() {
        this.a.setSingleRegionMode();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setMultiRegionMode() {
        this.a.setMultiRegionMode();
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPenUpRefreshEnabled(boolean enable) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFilterRepeatMovePoint(boolean filter) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setPostInputEvent(boolean post) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableSideBtnErase(boolean enable) {
        this.a.enableSideBtnErase(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(List<Rect> limitRectList) {
        this.a.setLimitRectList(getHostView(), limitRectList);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setExcludeRect(List<Rect> excludeRectList) {
        this.a.setExcludeRectList(getHostView(), excludeRectList);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void openDrawing() {
        d();
        EpdController.enablePost(getHostView(), 0);
        EpdController.setScreenHandWritingPenState(getHostView(), 1);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void closeDrawing() {
        d();
        EpdController.enablePost(getHostView(), 1);
        EpdController.setScreenHandWritingPenState(getHostView(), 0);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setDrawingRenderEnabled(boolean enabled) {
        this.c = enabled;
        if (enabled) {
            return;
        }
        EpdController.enablePost(getHostView(), 1);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setBrushRawDrawingEnabled(boolean draw) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setEraserRawDrawingEnabled(boolean draw, int eraserStyle) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setStrokeWidth(float w) {
        this.a.setStrokeWidth(w);
        EpdController.setStrokeWidth(w);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void debugLog(boolean enable) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableFingerTouch(boolean enable) {
        this.a.setEnableFingerTouch(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void onlyEnableFingerTouch(boolean only) {
        this.a.setOnlyEnableFingerTouch(only);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setHostViewScrollListenerEnabled(boolean enable) {
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void enableFingerTouchPressure(boolean enable) {
        this.d = enable;
        this.a.setEnableFingerTouchPressure(enable);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setFingerTouchPressure(float pressure) {
        this.a.setFingerTouchPressure(pressure);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void printTouchInfo() {
    }

    protected boolean onInterceptTouchEvent(MotionEvent event) {
        return TouchUtils.isPenTouchType(event);
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setTouchListenerEnabled(boolean enable) {
        this.e = enable;
    }

    private boolean c() {
        return this.c;
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        this.a.setLimitRectList(getHostView(), Collections.singletonList(limitRect));
        this.a.setExcludeRectList(getHostView(), excludeRectList);
    }

    private boolean b() {
        return this.b;
    }

    private void c(TouchPoint touchPoint) {
        if (this.d) {
            EpdController.quadTo(getHostView(), touchPoint.getX(), touchPoint.getY(), UpdateMode.DU, touchPoint.pressure);
        } else {
            EpdController.quadTo(getHostView(), touchPoint.getX(), touchPoint.getY(), UpdateMode.DU);
        }
    }

    private AppInputCallback a(RawInputCallback callback) {
        return new a(callback);
    }

    private void b(MotionEvent downEvent, List<TouchPoint> touchPointList) {
        if (c() && !onInterceptTouchEvent(downEvent)) {
            Iterator<TouchPoint> it = touchPointList.iterator();
            while (it.hasNext()) {
                c(it.next());
            }
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.TouchRender
    public void setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        this.a.setLimitRectList(getHostView(), limitRectList);
        this.a.setExcludeRectList(getHostView(), excludeRectList);
    }

    private void a(MotionEvent event, TouchPoint downPoint) {
        if (c()) {
            if (!onInterceptTouchEvent(event)) {
                b(downPoint);
            }
            this.g = Observable.<TouchPoint>create(e -> {
                this.h = e;
            }).buffer(1).observeOn(SingleThreadScheduler.scheduler()).subscribeOn(SingleThreadScheduler.scheduler()).subscribe(touchPoints -> {
                b(event, touchPoints);
            });
        }
    }

    @SuppressLint({"CheckResult"})
    private void b(MotionEvent event, TouchPoint touchPoint) {
        if (c() && !onInterceptTouchEvent(event)) {
            Observable.just(touchPoint).observeOn(SingleThreadScheduler.scheduler()).subscribeOn(SingleThreadScheduler.scheduler()).subscribe(point -> {
                c(point);
                EpdController.penUp();
            });
        }
    }

    private void a() {
        Disposable disposable = this.g;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void b(TouchPoint downPoint) {
        if (this.d) {
            EpdController.moveTo(getHostView(), downPoint.getX(), downPoint.getY(), this.a.getStrokeWidth(), downPoint.pressure);
        } else {
            EpdController.moveTo(getHostView(), downPoint.getX(), downPoint.getY(), this.a.getStrokeWidth());
        }
    }

    void o(MotionEvent event, List<TouchPoint> points) {
        b(event, points);
    }

    void q(TouchPoint point) {
        c(point);
        EpdController.penUp();
    }

    boolean k(View view, MotionEvent event) {
        return onTouchEvent(event);
    }

    void m(ObservableEmitter<TouchPoint> emitter) {
        this.h = emitter;
    }
}
