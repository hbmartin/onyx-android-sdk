package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
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
    private static final float RECOVERED_DEFAULT_STROKE_WIDTH = 3.0f;
    private final Object configurationLock = new Object();
    private RawDrawingConfigurationSnapshot observedConfiguration =
            RawDrawingConfigurationSnapshot.defaults();
    private volatile boolean a;
    private volatile boolean b;
    private volatile boolean c;
    private List<TouchRender> d;

    private TouchHelper(View view, RawInputCallback callback) {
        this(view, DeviceFeatureUtil.hasStylus(view.getContext()) ? 2 : 1, callback, true);
    }

    public static TouchHelper create(View hostView, RawInputCallback callback) {
        if (hostView != null) {
            return new TouchHelper(hostView, callback);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    private void a() {
        this.a = false;
        this.b = false;
        this.c = false;
    }

    public static void register(Object subscriber) {
        getEventBusHolder().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        getEventBusHolder().unregister(subscriber);
    }

    public static EventBusHolder getEventBusHolder() {
        return TouchEventBus.getInstance().getEventBusHolder();
    }

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

    public View getHostView() {
        for (TouchRender touchRender : this.d) {
            if (touchRender.getHostView() != null) {
                return touchRender.getHostView();
            }
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean zOnTouchEvent = false;
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            zOnTouchEvent = it.next().onTouchEvent(event);
        }
        return zOnTouchEvent;
    }

    public TouchHelper setStrokeStyle(int style) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeStyle(style);
        }
        observe(style, null, null, null, null, null, null, null, null, null);
        return this;
    }

    public TouchHelper setStrokeColor(int color) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeColor(color);
        }
        observe(null, color, null, null, null, null, null, null, null, null);
        return this;
    }

    public TouchHelper setStrokeWidth(float w) {
        float sanitized = sanitizeStrokeWidth(w);
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setStrokeWidth(sanitized);
        }
        observe(null, null, sanitized, null, null, null, null, null, null, null);
        return this;
    }

    static float sanitizeStrokeWidth(float width) {
        return Float.isFinite(width) && width > 0.0f
                ? width
                : RECOVERED_DEFAULT_STROKE_WIDTH;
    }

    public TouchHelper debugLog(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().debugLog(enable);
        }
        return this;
    }

    public TouchHelper setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setLimitRect(limitRect, excludeRectList);
        }
        observe(null, null, null, null, null, null, null, null,
                limitRect == null ? new ArrayList<>() : java.util.Collections.singletonList(limitRect),
                excludeRectList);
        return this;
    }

    public TouchHelper setExcludeRect(List<Rect> excludeRectList) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setExcludeRect(excludeRectList);
        }
        observe(null, null, null, null, null, null, null, null, null, excludeRectList);
        return this;
    }

    @MainThread
    public TouchHelper openRawDrawing() {
        a();
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().openDrawing();
        }
        this.a = true;
        return this;
    }

    @MainThread
    public void closeRawDrawing() {
        this.a = false;
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().closeDrawing();
        }
    }

    @MainThread
    public TouchHelper setRawDrawingEnabled(boolean enabled) {
        return setRawDrawingEnabled(enabled, RawDrawingConfigurationPolicy.RESET_TO_FIRMWARE_DEFAULTS);
    }

    @MainThread
    public TouchHelper setRawDrawingEnabled(
            boolean enabled,
            RawDrawingConfigurationPolicy configurationPolicy) {
        if (!this.a) {
            return this;
        }
        setRawDrawingRenderEnabled(enabled);
        setRawInputReaderEnable(enabled);
        if (configurationPolicy == RawDrawingConfigurationPolicy.RESET_TO_FIRMWARE_DEFAULTS) {
            resetPenDefaultRawDrawing();
        }
        return this;
    }

    @AnyThread
    public TouchHelper setRawInputListenerV2(RawInputListenerV2 listener) {
        boolean hasNativeReader = false;
        for (TouchRender touchRender : this.d) {
            if (touchRender instanceof SFTouchRender) {
                hasNativeReader = true;
                break;
            }
        }
        for (TouchRender touchRender : this.d) {
            touchRender.setRawInputListenerV2(
                    hasNativeReader && !(touchRender instanceof SFTouchRender) ? null : listener);
        }
        return this;
    }

    public boolean isRawDrawingInputEnabled() {
        return this.c;
    }

    public boolean isRawDrawingRenderEnabled() {
        return this.b;
    }

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

    public boolean isRawDrawingCreated() {
        return this.a;
    }

    public TouchHelper setSingleRegionMode() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setSingleRegionMode();
        }
        observe(null, null, null, null, null, null, null, true, null, null);
        return this;
    }

    public TouchHelper setMultiRegionMode() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setMultiRegionMode();
        }
        observe(null, null, null, null, null, null, null, false, null, null);
        return this;
    }

    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPenUpRefreshTimeMs(penUpRefreshTimeMs);
        }
    }

    @Deprecated
    public void setPenUpRefreshEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPenUpRefreshEnabled(enable);
        }
    }

    public void setFilterRepeatMovePoint(boolean filter) {
        dispatchFilterRepeatMovePoint(this.d, filter);
    }

    static void dispatchFilterRepeatMovePoint(
            Iterable<TouchRender> renderers, boolean filter) {
        for (TouchRender renderer : renderers) {
            renderer.setFilterRepeatMovePoint(filter);
        }
    }

    public void setPostInputEvent(boolean post) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setPostInputEvent(post);
        }
    }

    public void setTouchListenerEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setTouchListenerEnabled(enable);
        }
    }

    public void enableSideBtnErase(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableSideBtnErase(enable);
        }
        observe(null, null, null, null, null, null, enable, null, null, null);
    }

    public void enableFingerTouch(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableFingerTouch(enable);
        }
    }

    public void onlyEnableFingerTouch(boolean only) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().onlyEnableFingerTouch(only);
        }
    }

    public void enableFingerTouchPressure(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().enableFingerTouchPressure(enable);
        }
    }

    public void setFingerTouchPressure(float pressure) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setFingerTouchPressure(pressure);
        }
    }

    public void printTouchInfo() {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().printTouchInfo();
        }
    }

    public TouchHelper setBrushRawDrawingEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setBrushRawDrawingEnabled(enable);
        }
        observe(null, null, null, enable, null, null, null, null, null, null);
        return this;
    }

    public TouchHelper setEraserRawDrawingEnabled(boolean drawing, int eraserStyle) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setEraserRawDrawingEnabled(drawing, eraserStyle);
        }
        observe(null, null, null, null, drawing, eraserStyle, null, null, null, null);
        return this;
    }

    public TouchHelper setHostViewScrollListenerEnabled(boolean enable) {
        Iterator<TouchRender> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().setHostViewScrollListenerEnabled(enable);
        }
        return this;
    }

    public void resetPenDefaultRawDrawing() {
        Device.currentDevice().setBrushRawDrawingEnabled(true);
        Device.currentDevice().setEraserRawDrawingEnabled(false, 5);
        observe(null, null, null, true, false, 5, null, null, null, null);
    }

    /** Captures the last complete configuration observed through TouchHelper in this process. */
    @AnyThread
    public RawDrawingConfigurationSnapshot captureRawDrawingConfiguration() {
        synchronized (configurationLock) {
            RawDrawingConfigurationSnapshot value = observedConfiguration;
            return new RawDrawingConfigurationSnapshot(
                    value.getStrokeStyle(),
                    value.getStrokeColor(),
                    value.getStrokeWidth(),
                    value.isBrushEnabled(),
                    value.isEraserEnabled(),
                    value.getEraserStyle(),
                    value.isSideButtonEraserEnabled(),
                    value.isSingleRegionMode(),
                    value.getLimitRegions(),
                    value.getExcludeRegions(),
                    value.getEvidence());
        }
    }

    /** Applies every field of an SDK-observed snapshot; no partial reset is performed. */
    @MainThread
    public TouchHelper applyRawDrawingConfiguration(RawDrawingConfigurationSnapshot snapshot) {
        requireMainThread("applyRawDrawingConfiguration");
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot must not be null");
        }
        setStrokeStyle(snapshot.getStrokeStyle());
        setStrokeColor(snapshot.getStrokeColor());
        setStrokeWidth(snapshot.getStrokeWidth());
        setBrushRawDrawingEnabled(snapshot.isBrushEnabled());
        setEraserRawDrawingEnabled(snapshot.isEraserEnabled(), snapshot.getEraserStyle());
        enableSideBtnErase(snapshot.isSideButtonEraserEnabled());
        if (snapshot.isSingleRegionMode()) setSingleRegionMode(); else setMultiRegionMode();
        setLimitRect(snapshot.getLimitRegions());
        setExcludeRect(snapshot.getExcludeRegions());
        return this;
    }

    public void restartRawDrawing() {
        closeRawDrawing();
        openRawDrawing();
    }

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
        observe(null, null, null, null, null, null, null, null,
                limitRectList, excludeRectList);
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
        observe(null, null, null, null, null, null, null, null, limitRectList, null);
        return this;
    }

    public static TouchHelper create(View hostView, int feature, RawInputCallback callback, boolean enableTouchListener) {
        if (hostView != null) {
            return new TouchHelper(hostView, feature, callback, enableTouchListener);
        }
        throw new IllegalArgumentException("hostView should not be null!");
    }

    private void observe(
            Integer style,
            Integer color,
            Float width,
            Boolean brush,
            Boolean eraser,
            Integer eraserStyle,
            Boolean sideButton,
            Boolean singleRegion,
            List<Rect> limits,
            List<Rect> excludes) {
        synchronized (configurationLock) {
            observedConfiguration = observedConfiguration.observed(
                    style, color, width, brush, eraser, eraserStyle, sideButton,
                    singleRegion, limits, excludes);
        }
    }

    private static void requireMainThread(String operation) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(operation + " must run on the Android main thread");
        }
    }
}
