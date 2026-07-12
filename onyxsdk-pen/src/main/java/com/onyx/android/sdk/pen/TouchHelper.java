/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.View
 *  com.onyx.android.sdk.device.Device
 *  com.onyx.android.sdk.utils.DeviceFeatureUtil
 *  com.onyx.android.sdk.utils.EventBusHolder
 */
package com.onyx.android.sdk.pen;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.TouchEventBus;
import com.onyx.android.sdk.pen.touch.AppPenTouchRender;
import com.onyx.android.sdk.pen.touch.AppTouchRender;
import com.onyx.android.sdk.pen.touch.SFTouchRender;
import com.onyx.android.sdk.pen.touch.TouchRender;
import com.onyx.android.sdk.utils.DeviceFeatureUtil;
import com.onyx.android.sdk.utils.EventBusHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TouchHelper {
    public static final int STROKE_STYLE_PENCIL = 0;
    public static final int STROKE_STYLE_FOUNTAIN = 1;
    public static final int STROKE_STYLE_MARKER = 2;
    public static final int STROKE_STYLE_NEO_BRUSH = 3;
    public static final int STROKE_STYLE_CHARCOAL = 4;
    public static final int STROKE_STYLE_DASH = 5;
    public static final int STROKE_STYLE_CHARCOAL_V2 = 6;
    public static final int STROKE_STYLE_SQUARE_PEN = 7;
    public static final int FEATURE_APP_TOUCH_RENDER = 1;
    public static final int FEATURE_SF_TOUCH_RENDER = 2;
    public static final int FEATURE_APP_PEN_TOUCH_RENDER = 4;
    public static final int FEATURE_ALL_TOUCH_RENDER = 3;
    private volatile boolean a;
    private volatile boolean b;
    private volatile boolean c;
    private List<TouchRender> d;

    /*
     * WARNING - void declaration
     */
    private TouchHelper(View view, RawInputCallback callback) {
        void var2_2;
        void var1_1;
        int n = DeviceFeatureUtil.hasStylus((Context)view.getContext()) ? 2 : 1;
        this((View)var1_1, n, (RawInputCallback)var2_2, true);
    }

    /*
     * WARNING - void declaration
     */
    private TouchHelper(View view, int feature, RawInputCallback callback, boolean enableTouchListener) {
        void var4_4;
        void var2_2;
        void var3_3;
        void var1_1;
        ArrayList<TouchRender> arrayList;
        this.a = false;
        this.b = false;
        this.c = false;
        Object object = arrayList;
        this.d = new ArrayList<TouchRender>();
        if ((feature & 2) == 2) {
            SFTouchRender sFTouchRender;
            ArrayList<TouchRender> arrayList2 = object;
            object = sFTouchRender;
            sFTouchRender = new SFTouchRender((View)var1_1, (RawInputCallback)var3_3);
            arrayList2.add((TouchRender)object);
        }
        if ((var2_2 & 1) == 1) {
            this.d.add(new AppTouchRender((View)var1_1, (RawInputCallback)var3_3));
        }
        if ((var2_2 & 4) == 4) {
            this.d.add(new AppPenTouchRender((View)var1_1, (RawInputCallback)var3_3));
        }
        TouchHelper touchHelper = this;
        touchHelper.setTouchListenerEnabled((boolean)var4_4);
        touchHelper.bindHostView((View)var1_1, (RawInputCallback)var3_3);
    }

    /*
     * WARNING - void declaration
     */
    public static TouchHelper create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            void var1_1;
            View view;
            return new TouchHelper(view, (RawInputCallback)var1_1);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    /*
     * WARNING - void declaration
     */
    public static TouchHelper create(View hostView, boolean stylus, RawInputCallback callback) {
        void var2_2;
        View view;
        int n = stylus ? 2 : 1;
        return TouchHelper.create(view, n, (RawInputCallback)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static TouchHelper create(View hostView, int feature, RawInputCallback callback) {
        if (hostView != null) {
            void var2_2;
            void var1_1;
            View view;
            return new TouchHelper(view, (int)var1_1, (RawInputCallback)var2_2, true);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    /*
     * WARNING - void declaration
     */
    public static TouchHelper create(View hostView, int feature, RawInputCallback callback, boolean enableTouchListener) {
        if (hostView != null) {
            void var3_3;
            void var2_2;
            void var1_1;
            View view;
            return new TouchHelper(view, (int)var1_1, (RawInputCallback)var2_2, (boolean)var3_3);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    private void a() {
        TouchHelper touchHelper = this;
        touchHelper.a = false;
        touchHelper.b = false;
        touchHelper.c = false;
    }

    public static void register(Object subscriber) {
        Object object;
        TouchHelper.getEventBusHolder().register(object);
    }

    public static void unregister(Object subscriber) {
        Object object;
        TouchHelper.getEventBusHolder().unregister(object);
    }

    public static EventBusHolder getEventBusHolder() {
        return TouchEventBus.getInstance().getEventBusHolder();
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper bindHostView(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            Iterator<TouchRender> iterator = this.d.iterator();
            while (iterator.hasNext()) {
                void var2_2;
                void var1_1;
                iterator.next().bindHostView((View)var1_1, (RawInputCallback)var2_2);
            }
            return this;
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    public View getHostView() {
        for (TouchRender touchRender : ((TouchHelper)this).d) {
            if (touchRender.getHostView() == null) continue;
            return touchRender.getHostView();
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public boolean onTouchEvent(MotionEvent event) {
        boolean bl = false;
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_2;
            bl = iterator.next().onTouchEvent((MotionEvent)var1_2);
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setStrokeStyle(int style) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setStrokeStyle((int)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setStrokeColor(int color) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setStrokeColor((int)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setStrokeWidth(float w) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setStrokeWidth((float)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper debugLog(boolean enable) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().debugLog((boolean)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var2_2;
            void var1_1;
            iterator.next().setLimitRect((Rect)var1_1, (List<Rect>)var2_2);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var2_2;
            void var1_1;
            iterator.next().setLimitRect((List<Rect>)var1_1, (List<Rect>)var2_2);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setLimitRect(List<Rect> limitRectList) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setLimitRect((List<Rect>)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setExcludeRect(List<Rect> excludeRectList) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setExcludeRect((List<Rect>)var1_1);
        }
        return this;
    }

    public TouchHelper openRawDrawing() {
        TouchHelper touchHelper = this;
        touchHelper.a();
        Iterator<TouchRender> iterator = touchHelper.d.iterator();
        while (iterator.hasNext()) {
            iterator.next().openDrawing();
        }
        this.a = true;
        return this;
    }

    public void closeRawDrawing() {
        ((TouchHelper)((Object)this_)).a = false;
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            ((TouchRender)this_.next()).closeDrawing();
        }
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setRawDrawingEnabled(boolean enabled) {
        void var1_1;
        if (!this.a) {
            return this;
        }
        TouchHelper touchHelper = this;
        touchHelper.setRawDrawingRenderEnabled((boolean)var1_1);
        touchHelper.setRawInputReaderEnable((boolean)var1_1);
        touchHelper.resetPenDefaultRawDrawing();
        return touchHelper;
    }

    public boolean isRawDrawingInputEnabled() {
        return this.c;
    }

    public boolean isRawDrawingRenderEnabled() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setRawDrawingRenderEnabled(boolean enabled) {
        void var1_1;
        if (!this.a) {
            return this;
        }
        if (this.b == var1_1) {
            return this;
        }
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            iterator.next().setDrawingRenderEnabled((boolean)var1_1);
        }
        this.b = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper forceSetRawDrawingEnabled(boolean enabled) {
        void var1_1;
        if (!this.a) {
            return this;
        }
        for (TouchRender touchRender : this.d) {
            touchRender.setDrawingRenderEnabled((boolean)var1_1);
            touchRender.setInputReaderEnable((boolean)var1_1);
        }
        this.b = var1_1;
        this.c = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setRawInputReaderEnable(boolean enabled) {
        void var1_1;
        if (!this.a) {
            return this;
        }
        if (this.c == var1_1) {
            return this;
        }
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            iterator.next().setInputReaderEnable((boolean)var1_1);
        }
        this.c = var1_1;
        return this;
    }

    public boolean isRawDrawingCreated() {
        return this.a;
    }

    public TouchHelper setSingleRegionMode() {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            iterator.next().setSingleRegionMode();
        }
        return this;
    }

    public TouchHelper setMultiRegionMode() {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            iterator.next().setMultiRegionMode();
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setPenUpRefreshTimeMs((int)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Deprecated
    public void setPenUpRefreshEnabled(boolean enable) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setPenUpRefreshEnabled((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setFilterRepeatMovePoint(boolean filter) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setPenUpRefreshEnabled((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setPostInputEvent(boolean post) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setPostInputEvent((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setTouchListenerEnabled(boolean enable) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setTouchListenerEnabled((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void enableSideBtnErase(boolean enable) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).enableSideBtnErase((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void enableFingerTouch(boolean enable) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).enableFingerTouch((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void onlyEnableFingerTouch(boolean only) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).onlyEnableFingerTouch((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void enableFingerTouchPressure(boolean enable) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).enableFingerTouchPressure((boolean)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setFingerTouchPressure(float pressure) {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            void var1_1;
            ((TouchRender)this_.next()).setFingerTouchPressure((float)var1_1);
        }
    }

    public void printTouchInfo() {
        Iterator<TouchRender> this_ = ((TouchHelper)((Object)this_)).d.iterator();
        while (this_.hasNext()) {
            ((TouchRender)this_.next()).printTouchInfo();
        }
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setBrushRawDrawingEnabled(boolean enable) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setBrushRawDrawingEnabled((boolean)var1_1);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setEraserRawDrawingEnabled(boolean drawing, int eraserStyle) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var2_2;
            void var1_1;
            iterator.next().setEraserRawDrawingEnabled((boolean)var1_1, (int)var2_2);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchHelper setHostViewScrollListenerEnabled(boolean enable) {
        Iterator<TouchRender> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            iterator.next().setHostViewScrollListenerEnabled((boolean)var1_1);
        }
        return this;
    }

    public void resetPenDefaultRawDrawing() {
        Device.currentDevice().setBrushRawDrawingEnabled(true);
        Device.currentDevice().setEraserRawDrawingEnabled(false, 5);
    }

    public void restartRawDrawing() {
        TouchHelper touchHelper = this;
        touchHelper.closeRawDrawing();
        touchHelper.openRawDrawing();
    }
}

