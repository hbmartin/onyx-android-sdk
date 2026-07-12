package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.pen.touch.AppPenTouchRender;
import com.onyx.android.sdk.pen.touch.AppTouchRender;
import com.onyx.android.sdk.pen.touch.SFTouchRender;
import com.onyx.android.sdk.pen.touch.TouchRender;
import com.onyx.android.sdk.utils.DeviceFeatureUtil;
import com.onyx.android.sdk.utils.EventBusHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/TouchHelper.class */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private TouchHelper(View view, RawInputCallback callback) {
        this(view, DeviceFeatureUtil.hasStylus(view.getContext()) ? 2 : 1, callback, true);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static TouchHelper create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            return new TouchHelper(hostView, callback);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void a() {
        this.a = false;
        this.b = false;
        this.c = false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void register(Object subscriber) {
        getEventBusHolder().register(subscriber);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void unregister(Object subscriber) {
        getEventBusHolder().unregister(subscriber);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static EventBusHolder getEventBusHolder() {
        return TouchEventBus.getInstance().getEventBusHolder();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper bindHostView(View hostView, RawInputCallback callback) {
        if (hostView == null) {
            throw new IllegalArgumentException("hostView should not be null!");
        }
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().bindHostView(hostView, callback);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public View getHostView() {
        for (TouchRender touchRender : this.d) {
            if (touchRender.getHostView() != null) {
                return touchRender.getHostView();
            }
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean onTouchEvent(MotionEvent event) {
        boolean zOnTouchEvent = false;
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            zOnTouchEvent = it.next().onTouchEvent(event);
        }
        return zOnTouchEvent;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setStrokeStyle(int style) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeStyle(style);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setStrokeColor(int color) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeColor(color);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setStrokeWidth(float w) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeWidth(w);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper debugLog(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().debugLog(enable);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setLimitRect(limitRect, excludeRectList);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setExcludeRect(List<Rect> excludeRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setExcludeRect(excludeRectList);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper openRawDrawing() {
        a();
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().openDrawing();
        }
        this.a = true;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void closeRawDrawing() {
        this.a = false;
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().closeDrawing();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setRawDrawingEnabled(boolean enabled) {
        if (!this.a) {
            return this;
        }
        setRawDrawingRenderEnabled(enabled);
        setRawInputReaderEnable(enabled);
        resetPenDefaultRawDrawing();
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isRawDrawingInputEnabled() {
        return this.c;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isRawDrawingRenderEnabled() {
        return this.b;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setRawDrawingRenderEnabled(boolean enabled) {
        if (this.a && this.b != enabled) {
            Iterator<TouchRender> it = this.d.iterator();
            while (it.hasNext()) {
                it.next().setDrawingRenderEnabled(enabled);
            }
            this.b = enabled;
            return this;
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper forceSetRawDrawingEnabled(boolean enabled) {
        if (!this.a) {
            return this;
        }
        for (TouchRender touchRender : this.d) {
            touchRender.setDrawingRenderEnabled(enabled);
            touchRender.setInputReaderEnable(enabled);
        }
        this.b = enabled;
        this.c = enabled;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setRawInputReaderEnable(boolean enabled) {
        if (this.a && this.c != enabled) {
            Iterator<TouchRender> it = this.d.iterator();
            while (it.hasNext()) {
                it.next().setInputReaderEnable(enabled);
            }
            this.c = enabled;
            return this;
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isRawDrawingCreated() {
        return this.a;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setSingleRegionMode() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setSingleRegionMode();
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setMultiRegionMode() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setMultiRegionMode();
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Deprecated
    public void setPenUpRefreshEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPenUpRefreshEnabled(enable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setFilterRepeatMovePoint(boolean filter) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPenUpRefreshEnabled(filter);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setPostInputEvent(boolean post) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPostInputEvent(post);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setTouchListenerEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setTouchListenerEnabled(enable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void enableSideBtnErase(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableSideBtnErase(enable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void enableFingerTouch(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableFingerTouch(enable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void onlyEnableFingerTouch(boolean only) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().onlyEnableFingerTouch(only);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void enableFingerTouchPressure(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableFingerTouchPressure(enable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setFingerTouchPressure(float pressure) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setFingerTouchPressure(pressure);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void printTouchInfo() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().printTouchInfo();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setBrushRawDrawingEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setBrushRawDrawingEnabled(enable);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setEraserRawDrawingEnabled(boolean drawing, int eraserStyle) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setEraserRawDrawingEnabled(drawing, eraserStyle);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper setHostViewScrollListenerEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setHostViewScrollListenerEnabled(enable);
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void resetPenDefaultRawDrawing() {
        Device.currentDevice().setBrushRawDrawingEnabled(true);
        Device.currentDevice().setEraserRawDrawingEnabled(false, 5);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void restartRawDrawing() {
        closeRawDrawing();
        openRawDrawing();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private TouchHelper(View view, int feature, RawInputCallback callback, boolean enableTouchListener) {
        this.a = false;
        this.b = false;
        this.c = false;
        ArrayList arrayList = new ArrayList();
        this.d = arrayList;
        if ((feature & 2) == 2) {
            arrayList.add(new SFTouchRender(view, callback));
        }
        if ((feature & 1) == 1) {
            this.d.add(new AppTouchRender(view, callback));
        }
        if ((feature & 4) == 4) {
            this.d.add(new AppPenTouchRender(view, callback));
        }
        setTouchListenerEnabled(enableTouchListener);
        bindHostView(view, callback);
    }

    public static TouchHelper create(View hostView, boolean stylus, RawInputCallback callback) {
        return create(hostView, stylus ? 2 : 1, callback);
    }

    public TouchHelper setLimitRect(List<Rect> limitRectList, List<Rect> excludeRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setLimitRect(limitRectList, excludeRectList);
        }
        return this;
    }

    public static TouchHelper create(View hostView, int feature, RawInputCallback callback) {
        if (hostView != null) {
            return new TouchHelper(hostView, feature, callback, true);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    public TouchHelper setLimitRect(List<Rect> limitRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setLimitRect(limitRectList);
        }
        return this;
    }

    public static TouchHelper create(View hostView, int feature, RawInputCallback callback, boolean enableTouchListener) {
        if (hostView != null) {
            return new TouchHelper(hostView, feature, callback, enableTouchListener);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }
}

