/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.View
 *  com.onyx.android.sdk.api.device.epd.EpdController
 *  com.onyx.android.sdk.api.device.epd.UpdateMode
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.rx.SingleThreadScheduler
 *  com.onyx.android.sdk.utils.TouchUtils
 *  io.reactivex.Observable
 *  io.reactivex.ObservableEmitter
 *  io.reactivex.disposables.Disposable
 */
package com.onyx.android.sdk.pen.touch;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.pen.touch.AppInputCallback;
import com.onyx.android.sdk.pen.touch.AppTouchInputReader;
import com.onyx.android.sdk.pen.touch.TouchRender;
import com.onyx.android.sdk.rx.SingleThreadScheduler;
import com.onyx.android.sdk.utils.TouchUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AppTouchRender
implements TouchRender {
    private AppTouchInputReader a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e = true;
    private WeakReference<View> f;
    private Disposable g;
    private ObservableEmitter<TouchPoint> h;

    public AppTouchRender(View view, RawInputCallback callback) {
    }

    /*
     * WARNING - void declaration
     */
    private AppInputCallback a(RawInputCallback callback) {
        void var1_1;
        return new AppInputCallback(this, (RawInputCallback)var1_1){
            final /* synthetic */ RawInputCallback a;
            final /* synthetic */ AppTouchRender b;
            {
                void var1_1;
                this.b = var1_1;
                this.a = rawInputCallback;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onBeginRawDrawing(MotionEvent event, boolean shortcutDrawing, TouchPoint point) {
                void var3_3;
                void var1_1;
                a a2 = this;
                AppTouchRender.a(a2.b, (MotionEvent)var1_1, (TouchPoint)var3_3);
                if (a2.b.e) {
                    void var2_2;
                    this.a.onBeginRawDrawing((boolean)var2_2, (TouchPoint)var3_3);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onEndRawDrawing(MotionEvent event, boolean outLimitRegion, TouchPoint point) {
                void var3_3;
                TouchPoint touchPoint;
                TouchPoint touchPoint2;
                a a2 = this;
                a2.b.a();
                void v1 = touchPoint2;
                touchPoint2 = touchPoint;
                touchPoint = new TouchPoint((TouchPoint)var3_3);
                AppTouchRender.b(a2.b, (MotionEvent)v1, touchPoint2);
                if (a2.b.e) {
                    void var2_2;
                    this.a.onEndRawDrawing((boolean)var2_2, (TouchPoint)var3_3);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
                void var1_1;
                if (this.b.h != null) {
                    this.b.h.onNext((Object)new TouchPoint((TouchPoint)var1_1));
                }
                if (this.b.e) {
                    this.a.onRawDrawingTouchPointMoveReceived((TouchPoint)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointListReceived(MotionEvent event, TouchPointList pointList) {
                a a2 = this;
                a2.b.a();
                if (a2.b.e) {
                    void var2_2;
                    this.a.onRawDrawingTouchPointListReceived((TouchPointList)var2_2);
                }
            }
        };
    }

    private void d() {
        this.setStrokeStyle(0);
    }

    private boolean b() {
        return this.b;
    }

    private boolean c() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    private void a(MotionEvent event, TouchPoint downPoint) {
        void var1_1;
        if (!this.c()) {
            return;
        }
        if (!this.onInterceptTouchEvent((MotionEvent)var1_1)) {
            void var2_2;
            this.b((TouchPoint)var2_2);
        }
        this.g = Observable.create(e -> {
            void var1_1;
            this.h = var1_1;
        }).buffer(1).observeOn(SingleThreadScheduler.scheduler()).subscribeOn(SingleThreadScheduler.scheduler()).subscribe(arg_0 -> this.a((MotionEvent)var1_1, arg_0));
    }

    /*
     * WARNING - void declaration
     */
    private void b(MotionEvent downEvent, List<TouchPoint> touchPointList) {
        void var2_2;
        Iterator iterator;
        if (!this.c()) {
            return;
        }
        if (this.onInterceptTouchEvent((MotionEvent)iterator)) {
            return;
        }
        iterator = var2_2.iterator();
        while (iterator.hasNext()) {
            this.c((TouchPoint)iterator.next());
        }
    }

    /*
     * WARNING - void declaration
     */
    @SuppressLint(value={"CheckResult"})
    private void b(MotionEvent event, TouchPoint touchPoint) {
        void var2_2;
        void var1_1;
        if (!this.c()) {
            return;
        }
        if (this.onInterceptTouchEvent((MotionEvent)var1_1)) {
            return;
        }
        Observable.just((Object)var2_2).observeOn(SingleThreadScheduler.scheduler()).subscribeOn(SingleThreadScheduler.scheduler()).subscribe(point -> {
            void var1_1;
            this.c((TouchPoint)var1_1);
            EpdController.penUp();
        });
    }

    /*
     * WARNING - void declaration
     */
    private void b(TouchPoint downPoint) {
        void var1_2;
        if (this.d) {
            void v0 = var1_2;
            float f = v0.getX();
            float f2 = v0.getY();
            float f3 = this.a.getStrokeWidth();
            float f4 = var1_2.pressure;
            EpdController.moveTo((View)this.getHostView(), (float)f, (float)f2, (float)f3, (float)f4);
        } else {
            void v1 = var1_2;
            float f = v1.getX();
            float f5 = v1.getY();
            EpdController.moveTo((View)this.getHostView(), (float)f, (float)f5, (float)this.a.getStrokeWidth());
        }
    }

    /*
     * WARNING - void declaration
     */
    private void c(TouchPoint touchPoint) {
        void var1_3;
        if (this.d) {
            void v0 = var1_3;
            float f = v0.getX();
            float f2 = v0.getY();
            UpdateMode updateMode = UpdateMode.DU;
            float f3 = v0.pressure;
            EpdController.quadTo((View)this.getHostView(), (float)f, (float)f2, (UpdateMode)updateMode, (float)f3);
        } else {
            void v1 = var1_3;
            float f = v1.getX();
            float f4 = v1.getY();
            EpdController.quadTo((View)this.getHostView(), (float)f, (float)f4, (UpdateMode)UpdateMode.DU);
        }
    }

    private void a() {
        AppTouchRender this_ = this_.g;
        if (this_ != null) {
            this_.dispose();
        }
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void a(MotionEvent event, List touchPoints) throws Exception {
        void var2_2;
        void var1_1;
        this.b((MotionEvent)var1_1, (List<TouchPoint>)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void a(AppTouchRender x0, MotionEvent x1, TouchPoint x2) {
        void var2_2;
        void var1_1;
        x0.a((MotionEvent)var1_1, (TouchPoint)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void b(AppTouchRender x0, MotionEvent x1, TouchPoint x2) {
        void var2_2;
        void var1_1;
        x0.b((MotionEvent)var1_1, (TouchPoint)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void bindHostView(View view, RawInputCallback callback) {
        void var1_1;
        void var2_2;
        this.a = new AppTouchInputReader(this.a((RawInputCallback)var2_2));
        this.f = new WeakReference<void>(var1_1);
        if (this.e) {
            var1_1.setOnTouchListener((v, event) -> {
                void var2_2;
                return this.onTouchEvent((MotionEvent)var2_2);
            });
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        void var1_1;
        if (!this.b()) {
            return false;
        }
        return this.a.processMotionEvent((MotionEvent)var1_1);
    }

    @Override
    public View getHostView() {
        WeakReference<View> this_ = ((AppTouchRender)((Object)this_)).f;
        return this_ == null ? null : (View)this_.get();
    }

    @Override
    public void setStrokeStyle(int style) {
        EpdController.setStrokeStyle((int)style);
    }

    @Override
    public void setStrokeColor(int color) {
        EpdController.setStrokeColor((int)color);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setInputReaderEnable(boolean enabled) {
        void var1_1;
        this.b = var1_1;
    }

    @Override
    public void setSingleRegionMode() {
    }

    @Override
    public void setMultiRegionMode() {
    }

    @Override
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
    }

    @Override
    public void setPenUpRefreshEnabled(boolean enable) {
    }

    @Override
    public void setFilterRepeatMovePoint(boolean filter) {
    }

    @Override
    public void setPostInputEvent(boolean post) {
    }

    @Override
    public void enableSideBtnErase(boolean enable) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(List<Rect> limitRectList) {
        void var1_1;
        this.a.setLimitRectList(this.getHostView(), (List<Rect>)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        void var2_2;
        View view;
        AppTouchRender appTouchRender = this;
        void v1 = view;
        view = this.getHostView();
        appTouchRender.a.setLimitRectList(view, Collections.singletonList(v1));
        appTouchRender.a.setExcludeRectList(this.getHostView(), (List<Rect>)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        void var2_2;
        void var1_1;
        AppTouchRender appTouchRender = this;
        appTouchRender.a.setLimitRectList(this.getHostView(), (List<Rect>)var1_1);
        appTouchRender.a.setExcludeRectList(this.getHostView(), (List<Rect>)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setExcludeRect(List<Rect> excludeRectList) {
        void var1_1;
        this.a.setExcludeRectList(this.getHostView(), (List<Rect>)var1_1);
    }

    @Override
    public void openDrawing() {
        AppTouchRender appTouchRender = this;
        appTouchRender.d();
        EpdController.enablePost((View)appTouchRender.getHostView(), (int)0);
        EpdController.setScreenHandWritingPenState((View)appTouchRender.getHostView(), (int)1);
    }

    @Override
    public void closeDrawing() {
        AppTouchRender appTouchRender = this;
        appTouchRender.d();
        EpdController.enablePost((View)appTouchRender.getHostView(), (int)1);
        EpdController.setScreenHandWritingPenState((View)appTouchRender.getHostView(), (int)0);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setDrawingRenderEnabled(boolean enabled) {
        void var1_1;
        this.c = var1_1;
        if (!enabled) {
            EpdController.enablePost((View)this.getHostView(), (int)1);
        }
    }

    @Override
    public void setBrushRawDrawingEnabled(boolean draw) {
    }

    @Override
    public void setEraserRawDrawingEnabled(boolean draw, int eraserStyle) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setStrokeWidth(float w) {
        void var1_1;
        this.a.setStrokeWidth((float)var1_1);
        EpdController.setStrokeWidth((float)w);
    }

    @Override
    public void debugLog(boolean enable) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void enableFingerTouch(boolean enable) {
        void var1_1;
        this.a.setEnableFingerTouch((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onlyEnableFingerTouch(boolean only) {
        void var1_1;
        this.a.setOnlyEnableFingerTouch((boolean)var1_1);
    }

    @Override
    public void setHostViewScrollListenerEnabled(boolean enable) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void enableFingerTouchPressure(boolean enable) {
        void var1_1;
        this.d = var1_1;
        this.a.setEnableFingerTouchPressure((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setFingerTouchPressure(float pressure) {
        void var1_1;
        this.a.setFingerTouchPressure((float)var1_1);
    }

    @Override
    public void printTouchInfo() {
    }

    protected boolean onInterceptTouchEvent(MotionEvent event) {
        return TouchUtils.isPenTouchType((MotionEvent)event);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setTouchListenerEnabled(boolean enable) {
        void var1_1;
        this.e = var1_1;
    }
}

