/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 *  com.onyx.android.sdk.api.device.epd.EpdController
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.device.Device
 */
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
import com.onyx.android.sdk.pen.touch.TouchRender;
import java.lang.ref.WeakReference;
import java.util.List;

public class SFTouchRender
implements TouchRender {
    private WeakReference<View> a;
    private RawInputCallback b;
    private EpdPenManager c;
    private RawInputManager d;

    public SFTouchRender(View view, RawInputCallback callback) {
    }

    /*
     * WARNING - void declaration
     */
    public static TouchRender create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            void var1_1;
            View view;
            return new SFTouchRender(view, (RawInputCallback)var1_1);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    private boolean a() {
        return this.b == null || this.getHostView() == null;
    }

    private EpdPenManager d() {
        if (this.c == null) {
            EpdPenManager epdPenManager;
            EpdPenManager epdPenManager2 = epdPenManager;
            epdPenManager = new EpdPenManager();
            this.c = epdPenManager2;
        }
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    private void a(View view) {
        void var1_1;
        this.d().setHostView((View)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    private void a(View view, RawInputCallback callback) {
        void var2_2;
        void var1_1;
        SFTouchRender sFTouchRender = this;
        sFTouchRender.e().setHostView((View)var1_1);
        sFTouchRender.e().setRawInputCallback((RawInputCallback)var2_2);
    }

    private RawInputManager e() {
        if (this.d == null) {
            RawInputManager rawInputManager;
            RawInputManager rawInputManager2 = rawInputManager;
            rawInputManager = new RawInputManager();
            this.d = rawInputManager2;
        }
        return this.d;
    }

    private void i() {
        this.setStrokeStyle(0);
    }

    private void b() {
        SFTouchRender sFTouchRender = this;
        sFTouchRender.e().startRawInputReader();
        sFTouchRender.d().startDrawing();
    }

    private void c() {
        SFTouchRender sFTouchRender = this;
        sFTouchRender.e().quitRawInputReader();
        sFTouchRender.d().quitDrawing();
    }

    private void f() {
        SFTouchRender sFTouchRender = this;
        EpdController.leaveScribbleMode((View)sFTouchRender.e().getHostView());
        sFTouchRender.e().pauseRawInputReader();
        sFTouchRender.d().pauseDrawing();
    }

    private void g() {
        SFTouchRender sFTouchRender = this;
        EpdController.leaveScribbleMode((View)sFTouchRender.e().getHostView());
        sFTouchRender.d().pauseDrawing();
    }

    private void j() {
        this.d().resumeDrawing();
    }

    private void k() {
        this.e().resumeRawInputReader();
    }

    private void h() {
        this.e().pauseRawInputReader();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void bindHostView(View view, RawInputCallback callback) {
        void var2_2;
        b b2;
        void var1_1;
        WeakReference<void> weakReference;
        SFTouchRender sFTouchRender = this;
        Object object = weakReference;
        weakReference = new WeakReference<void>(var1_1);
        sFTouchRender.a = object;
        object = b2;
        b2 = new b((RawInputCallback)var2_2);
        sFTouchRender.b = object;
        sFTouchRender.a((View)var1_1, (RawInputCallback)object);
        sFTouchRender.a((View)var1_1);
    }

    @Override
    public View getHostView() {
        WeakReference<View> this_ = ((SFTouchRender)((Object)this_)).a;
        return this_ == null ? null : (View)this_.get();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setStrokeStyle(int style) {
        void var1_1;
        this.d().setStrokeStyle((int)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setStrokeColor(int color) {
        void var1_1;
        EpdPenManager.setStrokeColor((int)var1_1);
        this.e().setStrokeColor((int)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setStrokeWidth(float w) {
        void var1_1;
        this.e().setStrokeWidth((float)var1_1);
    }

    @Override
    public void debugLog(boolean enable) {
        RawInputReader.debugLog(enable);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        void var2_2;
        void var1_1;
        this.e().setLimitRect((Rect)var1_1, (List<Rect>)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        void var2_2;
        void var1_1;
        this.e().setLimitRect((List<Rect>)var1_1, (List<Rect>)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setLimitRect(List<Rect> limitRectList) {
        void var1_1;
        this.e().setLimitRect((List<Rect>)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setExcludeRect(List<Rect> excludeRectList) {
        void var1_1;
        this.e().setExcludeRect((List<Rect>)var1_1);
    }

    @Override
    public void openDrawing() {
        SFTouchRender sFTouchRender = this;
        sFTouchRender.i();
        sFTouchRender.b();
    }

    @Override
    public void closeDrawing() {
        SFTouchRender sFTouchRender = this;
        sFTouchRender.i();
        sFTouchRender.f();
        sFTouchRender.c();
    }

    @Override
    public void setDrawingRenderEnabled(boolean enabled) {
        if (enabled) {
            this.j();
        } else {
            this.g();
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setBrushRawDrawingEnabled(boolean enable) {
        void var1_1;
        Device.currentDevice().setBrushRawDrawingEnabled((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setEraserRawDrawingEnabled(boolean enable, int eraserStyle) {
        void var2_2;
        void var1_1;
        Device.currentDevice().setEraserRawDrawingEnabled((boolean)var1_1, (int)var2_2);
    }

    @Override
    public void setInputReaderEnable(boolean enabled) {
        if (enabled) {
            this.k();
        } else {
            this.h();
        }
    }

    @Override
    public void setSingleRegionMode() {
        this.e().setSingleRegionMode();
    }

    @Override
    public void setMultiRegionMode() {
        this.e().setMultiRegionMode();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        void var1_1;
        this.e().setPenUpRefreshTimeMs((int)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setPenUpRefreshEnabled(boolean enable) {
        void var1_1;
        this.e().setPenUpRefreshEnabled((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setFilterRepeatMovePoint(boolean filter) {
        void var1_1;
        this.e().setFilterRepeatMovePoint((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setPostInputEvent(boolean post) {
        void var1_1;
        this.e().setPostInputEvent((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void enableSideBtnErase(boolean enable) {
        void var1_1;
        this.e().enableSideBtnErase((boolean)var1_1);
        Device.currentDevice().setEnablePenSideButton((boolean)var1_1);
    }

    @Override
    public void enableFingerTouch(boolean enable) {
    }

    @Override
    public void onlyEnableFingerTouch(boolean only) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setHostViewScrollListenerEnabled(boolean enable) {
        void var1_1;
        this.e().setHostViewScrollListenerEnabled((boolean)var1_1);
    }

    @Override
    public void setFingerTouchPressure(float pressure) {
    }

    @Override
    public void printTouchInfo() {
        RawInputManager this_ = ((SFTouchRender)((Object)this_)).d;
        if (this_ != null) {
            this_.printTouchInfo();
        }
    }

    @Override
    public void enableFingerTouchPressure(boolean enable) {
    }

    @Override
    public void setTouchListenerEnabled(boolean enable) {
    }

    private class b
    extends RawInputCallback {
        RawInputCallback a;

        /*
         * WARNING - void declaration
         */
        private b(RawInputCallback callback) {
            void var2_2;
            this.a = var2_2;
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void a(RectF refreshRect) {
            void var1_1;
            this.a.onPenUpRefresh((RectF)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void a(TouchPoint point) {
            void var1_1;
            this.a.onPenActive((TouchPoint)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void b(TouchPointList pointList) {
            void var1_1;
            this.a.onRawErasingTouchPointListReceived((TouchPointList)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void c(TouchPoint point) {
            void var1_1;
            this.a.onRawErasingTouchPointMoveReceived((TouchPoint)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void d(boolean outLimitRegion, TouchPoint point) {
            void var2_2;
            void var1_1;
            this.a.onEndRawErasing((boolean)var1_1, (TouchPoint)var2_2);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void b(boolean shortcutErasing, TouchPoint point) {
            void var2_2;
            void var1_1;
            this.a.onBeginRawErasing((boolean)var1_1, (TouchPoint)var2_2);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void a(TouchPointList pointList) {
            void var1_1;
            this.a.onRawDrawingTouchPointListReceived((TouchPointList)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void b(TouchPoint point) {
            void var1_1;
            this.a.onRawDrawingTouchPointMoveReceived((TouchPoint)var1_1);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void c(boolean outLimitRegion, TouchPoint point) {
            void var2_2;
            void var1_1;
            this.a.onEndRawDrawing((boolean)var1_1, (TouchPoint)var2_2);
        }

        /*
         * WARNING - void declaration
         */
        private /* synthetic */ void a(boolean shortcutDrawing, TouchPoint point) {
            void var2_2;
            void var1_1;
            this.a.onBeginRawDrawing((boolean)var1_1, (TouchPoint)var2_2);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint point) {
            void var2_2;
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.a((boolean)var1_1, (TouchPoint)var2_2));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            void var2_2;
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.c((boolean)var1_1, (TouchPoint)var2_2));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.b((TouchPoint)var1_1));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.a((TouchPointList)var1_1));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
            void var2_2;
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.b((boolean)var1_1, (TouchPoint)var2_2));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            void var2_2;
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.d((boolean)var1_1, (TouchPoint)var2_2));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.c((TouchPoint)var1_1));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.b((TouchPointList)var1_1));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onPenActive(TouchPoint point) {
            void var1_1;
            if (SFTouchRender.this.a()) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.a((TouchPoint)var1_1));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public void onPenUpRefresh(RectF refreshRect) {
            void var1_1;
            if (this.a == null) {
                return;
            }
            SFTouchRender.this.getHostView().post(() -> this.a((RectF)var1_1));
        }
    }
}

