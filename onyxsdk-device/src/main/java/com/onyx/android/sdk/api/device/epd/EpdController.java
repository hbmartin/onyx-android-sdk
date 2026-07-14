package com.onyx.android.sdk.api.device.epd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.graphics.RectF;
import android.view.View;
import android.webkit.WebView;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.Debug;

public abstract class EpdController {
    private static String TAG = "EpdController";
    public static final int STROKE_STYLE_PENCIL = 0;
    public static final int STROKE_STYLE_BRUSH = 1;
    public static final int STROKE_STYLE_MARKER = 2;
    public static final int STROKE_STYLE_NEO_BRUSH = 3;
    public static final int STROKE_STYLE_CHARCOAL = 4;
    public static int SCHEME_START = 1;
    public static int SCHEME_NORMAL = 1;
    public static int SCHEME_KEYBOARD = 2;
    public static int SCHEME_SCRIBBLE = 3;
    public static int SCHEME_APPLICATION_ANIMATION = 4;
    public static int SCHEME_SYSTEM_ANIMATION = 5;
    public static int SCHEME_END = 5;
    public static final String ENABLE_SYSTEM_CTP_ACTION = "action.enable.all.tp.region";
    public static final String RESET_SYSTEM_CTP_ACTION = "action.disable.tp.exclude.bar.region";
    public static final String CTP_STATUS_CHANGE_ACTION = "ctp.status.change";
    private static boolean isRegalEnabled = true;
    private static final StrokeTransport FRAMEWORK_STROKE_TRANSPORT = new FrameworkStrokeTransport();
    private static volatile StrokeTransport strokeTransport = FRAMEWORK_STROKE_TRANSPORT;
    public static final float MAX_TOUCH_PRESSURE = getMaxTouchPressure();

    private EpdController() {
    }

    public static void invalidate(View view, UpdateMode mode) {
        Device.currentDevice().invalidate(view, mode);
    }

    public static void postInvalidate(View view, UpdateMode mode) {
        Device.currentDevice().postInvalidate(view, mode);
    }

    public static boolean enableScreenUpdate(View view, boolean enable) {
        return Device.currentDevice().enableScreenUpdate(view, enable);
    }

    public static boolean setViewDefaultUpdateMode(View view, UpdateMode mode) {
        Debug.d(TAG, "setViewDefaultUpdateMode, mode = " + mode, new Object[0]);
        Device.currentDevice().setViewDefaultUpdateMode(view, mode);
        return true;
    }

    public static UpdateMode getViewDefaultUpdateMode(View view) {
        return Device.currentDevice().getViewDefaultUpdateMode(view);
    }

    public static boolean isDeepGcMode(View view) {
        return getViewDefaultUpdateMode(view) == UpdateMode.DEEP_GC;
    }

    public static void useFastScheme() {
        setDisplayScheme(SCHEME_SCRIBBLE);
    }

    public static void resetUpdateMode(View view) {
        Debug.d(TAG, "resetUpdateMode", new Object[0]);
        resetViewUpdateMode(view);
        useFastScheme();
    }

    public static boolean resetViewUpdateMode(View view) {
        Device.currentDevice().resetViewUpdateMode(view);
        return true;
    }

    public static UpdateMode getSystemDefaultUpdateMode() {
        return Device.currentDevice().getSystemDefaultUpdateMode();
    }

    public static boolean setSystemDefaultUpdateMode(UpdateMode mode) {
        Device.currentDevice().setSystemDefaultUpdateMode(mode);
        return false;
    }

    public static boolean applyAppScopeUpdate(String application, boolean enable, boolean clear, UpdateMode repeatMode, int repeatLimit) {
        Debug.d(TAG, "applyAppScopeUpdate, application = " + application + ", clear = " + clear + ", mode = " + repeatMode, new Object[0]);
        Device.currentDevice().applyAppScopeUpdate(application, enable, clear, repeatMode, repeatLimit);
        return true;
    }

    public static boolean clearAppScopeUpdate() {
        Debug.d(TAG, "clearAppScopeUpdate", new Object[0]);
        Device.currentDevice().clearAppScopeUpdate();
        return true;
    }

    public static boolean applyTransientUpdate(UpdateMode updateMode) {
        Debug.d(TAG, "applyTransientUpdate = " + updateMode, new Object[0]);
        return Device.currentDevice().applyTransientUpdate(updateMode);
    }

    public static boolean clearTransientUpdate(View view, boolean clear) {
        Debug.d(TAG, "clearTransientUpdate = " + clear, new Object[0]);
        Device.currentDevice().clearTransientUpdate(clear);
        if (view == null) {
            return true;
        }
        view.invalidate();
        return true;
    }

    public static boolean setDisplayScheme(int scheme) {
        Device.currentDevice().setDisplayScheme(scheme);
        return true;
    }

    public static boolean applySystemFastMode(boolean enable) {
        Device.currentDevice().applySystemFastMode(enable);
        return true;
    }

    public static UpdateOption getAppScopeRefreshMode() {
        return Device.currentDevice().getAppScopeRefreshMode();
    }

    public static boolean setAppScopeRefreshMode(UpdateOption updateOption) {
        Device.currentDevice().setAppScopeRefreshMode(updateOption);
        return true;
    }

    public static void waitForUpdateFinished() {
        Device.currentDevice().waitForUpdateFinished();
    }

    /**
     * Waits using the verified synchronous SurfaceFlinger transaction and
     * throws when the operation cannot be dispatched. Unlike the legacy
     * method this never converts a missing method or rejected transaction into
     * apparent success.
     */
    @WorkerThread
    public static void waitForUpdateFinishedOrThrow() {
        requireWorkerThread("WAIT_FOR_UPDATE_FINISHED");
        FirmwareBinderBackend.get().waitForUpdateFinished();
    }

    /** Returns a fresh diagnostic snapshot of the strict firmware backend. */
    public static FirmwareBackendInfo getFirmwareBackendInfo() {
        return FirmwareBinderBackend.get().info();
    }

    /** Retries strict backend discovery after an application-provided exemption hook. */
    public static FirmwareBackendInfo retryFirmwareBackendDiscovery() {
        FirmwareBinderBackend.get().resetForExternalExemption();
        return FirmwareBinderBackend.get().info();
    }

    @MainThread
    public static void setViewDefaultUpdateModeOrThrow(View view, UpdateMode mode) {
        requireMainThread("SET_VIEW_UPDATE_MODE");
        if (view == null || mode == null) {
            throw new IllegalArgumentException("view and mode must not be null");
        }
        Device.currentDevice().setViewDefaultUpdateMode(view, mode);
        UpdateMode actual = Device.currentDevice().getViewDefaultUpdateMode(view);
        if (actual != mode) {
            throw new FirmwareOperationException(
                    "SET_VIEW_UPDATE_MODE", "framework-reflection",
                    "Firmware reported " + actual + " after setting " + mode);
        }
    }

    public static void refreshScreen(View view, UpdateMode mode) {
        Device.currentDevice().refreshScreen(view, mode);
    }

    public static void refreshScreenRegion(View view, int left, int top, int width, int height, UpdateMode mode) {
        Device.currentDevice().refreshScreenRegion(view, left, top, width, height, mode);
    }

    public static boolean supportRegal() {
        return Device.currentDevice().supportRegal() && isRegalEnabled;
    }

    public static boolean isRegalEnabled() {
        return isRegalEnabled;
    }

    public static void holdDisplay(boolean hold, UpdateMode updateMode, int ignoreFrame) {
        Device.currentDevice().holdDisplay(hold, updateMode, ignoreFrame);
    }

    public static void byPass(int count) {
        Device.currentDevice().byPass(count);
    }

    public static void setStrokeWidth(float width) {
        Device.currentDevice().setStrokeWidth(width);
    }

    public static void setStrokeStyle(int style) {
        Device.currentDevice().setStrokeStyle(style);
    }

    public static void setStrokeColor(int color) {
        Device.currentDevice().setStrokeColor(color);
    }

    public static void setScreenHandWritingPenState(View view, int penState) {
        Device.currentDevice().setScreenHandWritingPenState(view, penState);
    }

    public static boolean isValidPenState() {
        return Device.currentDevice().isValidPenState();
    }

    public static int getPenState() {
        return Device.currentDevice().getPenState();
    }

    public static void setScreenHandWritingRegionLimit(View view, int left, int top, int right, int bottom) {
        Device.currentDevice().setScreenHandWritingRegionLimit(view, left, top, right, bottom);
    }

    public static void setScreenHandWritingRegionMode(View view, int mode) {
        Device.currentDevice().setScreenHandWritingRegionMode(view, mode);
    }

    public static void setScreenHandWritingRegionExclude(View view, Rect[] regions) {
        Device.currentDevice().setScreenHandWritingRegionExclude(view, regions);
    }

    public static float startStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return strokeTransport.startStroke(baseWidth, x, y, pressure, size, time);
    }

    public static float addStrokePoint(float baseWidth, float x, float y, float pressure, float size, float time) {
        return strokeTransport.addStrokePoint(baseWidth, x, y, pressure, size, time);
    }

    public static float finishStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return strokeTransport.finishStroke(baseWidth, x, y, pressure, size, time);
    }

    public static void setEnablePenSideButton(boolean enabled) {
        Device.currentDevice().setEnablePenSideButton(enabled);
    }

    public static void setBrushRawDrawingEnabled(boolean enabled) {
        Device.currentDevice().setBrushRawDrawingEnabled(enabled);
    }

    public static void setEraserRawDrawingEnabled(boolean enabled, int eraserStyle) {
        Device.currentDevice().setEraserRawDrawingEnabled(enabled, eraserStyle);
    }

    public static float[] getStrokeParameters(int strokeStyle) {
        return Device.currentDevice().getStrokeParameters(strokeStyle);
    }

    public static void setStrokeParameters(int strokeStyle, float[] parameters) {
        Device.currentDevice().setStrokeParameters(strokeStyle, parameters);
    }

    /** Applies the same color/style/width/extra-parameter group used by EAC. */
    public static void applyStrokeConfiguration(StrokeConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }
        setStrokeColor(configuration.getColor());
        setStrokeStyle(configuration.getStyle());
        setStrokeWidth(configuration.getWidth());
        setStrokeParameters(configuration.getStyle(), configuration.getExtraParameters());
    }

    public static boolean supportsStrokeStyleConfiguration() {
        return Device.currentDevice().supportsStrokeStyleConfiguration();
    }

    public static boolean supportsStrokeDataTransport() {
        return Device.currentDevice().supportsStrokeDataTransport();
    }

    public static boolean supportsAdvancedStrokeConfiguration() {
        return Device.currentDevice().supportsAdvancedStrokeConfiguration();
    }

    /** Restores the normal BOOX framework/reflection stroke path. */
    public static void useFrameworkStrokeTransport() {
        strokeTransport = FRAMEWORK_STROKE_TRANSPORT;
    }

    /**
     * Enables direct Binder transport only when the configured service exists.
     * A rejected start falls back to the framework path for that whole stroke.
     * If Binder fails after a successful start, the stroke is aborted rather
     * than sending an unmatched add/finish sequence through another transport;
     * Binder resolution is retried at the next stroke boundary.
     */
    public static boolean useSurfaceFlingerStrokeTransport(StrokeTransportConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        StrokeTransport candidate = new SurfaceFlingerStrokeTransport(
                config, FRAMEWORK_STROKE_TRANSPORT);
        if (!candidate.isAvailable()) {
            return false;
        }
        strokeTransport = candidate;
        return true;
    }

    public static String getStrokeTransportName() {
        return strokeTransport.getName();
    }

    public static void enterScribbleMode(View view) {
        Device.currentDevice().enterScribbleMode(view);
    }

    public static void leaveScribbleMode(View view) {
        Device.currentDevice().leaveScribbleMode(view);
    }

    public static void enablePost(View view, int enable) {
        Debug.d(TAG, "enablePost = " + enable, new Object[0]);
        Device.currentDevice().enablePost(view, enable);
    }

    public static void resetEpdPost() {
        Device.currentDevice().resetEpdPost();
    }

    public static void setPainterStyle(boolean antiAlias, Paint.Style strokeStyle, Paint.Join joinStyle, Paint.Cap capStyle) {
        Device.currentDevice().setPainterStyle(antiAlias, strokeStyle, joinStyle, capStyle);
    }

    public static void moveTo(float x, float y, float width) {
        Device.currentDevice().moveTo(x, y, width);
    }

    public static void lineTo(float x, float y, UpdateMode mode) {
        Device.currentDevice().lineTo(x, y, mode);
    }

    public static void quadTo(float x, float y, UpdateMode mode) {
        Device.currentDevice().quadTo(x, y, mode);
    }

    public static void penUp() {
        Device.currentDevice().penUp();
    }

    public static void disableA2ForSpecificView(View view) {
        Device.currentDevice().disableA2ForSpecificView(view);
    }

    public static void enableA2ForSpecificView(View view) {
        Device.currentDevice().enableA2ForSpecificView(view);
    }

    public static void setWebViewContrastOptimize(WebView view, boolean enabled) {
        Device.currentDevice().setWebViewContrastOptimize(view, enabled);
    }

    @Nullable
    public static Matrix getRawTouchPointToScreenMatrix() {
        return Device.currentDevice().getRawTouchPointToScreenMatrix();
    }

    public static void mapToView(View view, float[] src, float[] dst) {
        Device.currentDevice().mapToView(view, src, dst);
    }

    public static void mapToEpd(View view, float[] src, float[] dst) {
        Device.currentDevice().mapToEpd(view, src, dst);
    }

    public static void mapFromRawTouchPoint(View view, float[] src, float[] dst) {
        Device.currentDevice().mapFromRawTouchPoint(view, src, dst);
    }

    public static void mapToRawTouchPoint(View view, float[] src, float[] dst) {
        Device.currentDevice().mapToRawTouchPoint(view, src, dst);
    }

    public static float getTouchWidth() {
        return Device.currentDevice().getTouchWidth();
    }

    public static float getTouchHeight() {
        return Device.currentDevice().getTouchHeight();
    }

    public static float getMaxTouchPressure() {
        return Device.currentDevice().getMaxTouchPressure();
    }

    public static float getEpdWidth() {
        return Device.currentDevice().getEpdWidth();
    }

    public static float getEpdHeight() {
        return Device.currentDevice().getEpdHeight();
    }

    public static void enableRegal() {
        Debug.d(TAG, "enableRegal", new Object[0]);
        Device.currentDevice().enableRegal(true);
    }

    public static void disableRegal() {
        Device.currentDevice().enableRegal(false);
    }

    public static void enableColorCU() {
        Debug.d(TAG, "enableColorCU", new Object[0]);
        Device.currentDevice().enableColorCU(true);
    }

    public static void disableColorCU() {
        Debug.d(TAG, "disableColorCU", new Object[0]);
        Device.currentDevice().enableColorCU(false);
    }

    public static void enableColorAdjust() {
        Debug.d(TAG, "enableColorAdjust", new Object[0]);
        Device.currentDevice.enableColorAdjust(true);
    }

    public static void disableColorAdjust() {
        Debug.d(TAG, "disableColorAdjust", new Object[0]);
        Device.currentDevice.enableColorAdjust(false);
    }

    public static void enableNightMode() {
        enableNightMode(false);
    }

    public static void disableNightMode() {
        disableNightMode(false);
    }

    public static boolean isSupportNightMode() {
        return Device.currentDevice().isSupportNightMode();
    }

    public static void setUpdListSize(int size) {
        Device.currentDevice().setUpdListSize(size);
    }

    public static void resetUpdListSize() {
        Device.currentDevice().setUpdListSize(-1);
    }

    @Deprecated
    public static boolean inSystemFastMode() {
        return Device.currentDevice().inSystemFastMode();
    }

    public static boolean isInFastMode() {
        return Device.currentDevice().isInFastMode();
    }

    public static void setAppCTPDisableRegion(Context context, int[] disableRegionArray) {
        setAppCTPDisableRegion(context, disableRegionArray, (int[]) null);
    }

    public static boolean isCTPDisableRegion(Context context) {
        return Device.currentDevice().isCTPDisableRegion(context);
    }

    public static void appResetCTPDisableRegion(Context context) {
        Device.currentDevice().appResetCTPDisableRegion(context);
    }

    public static void setSystemCTPDisableRegion(Context context) {
        context.sendBroadcast(new Intent(ENABLE_SYSTEM_CTP_ACTION));
    }

    public static void systemResetCTPDisableRegion(Context context) {
        context.sendBroadcast(new Intent(RESET_SYSTEM_CTP_ACTION));
    }

    public static void dumpCTPInfo(Context context) {
        Device.currentDevice().dumpCTPInfo(context);
    }

    public static boolean isCTPPowerOn() {
        return Device.currentDevice().isCTPPowerOn();
    }

    public static boolean isEMTPPowerOn() {
        return Device.currentDevice().isEMTPPowerOn();
    }

    public static void powerCTP(boolean enable) {
        Device.currentDevice().powerCTP(enable);
    }

    public static void powerEMTP(boolean enable) {
        Device.currentDevice().powerEMTP(enable);
    }

    public static void switchToA2Mode() {
        Device.currentDevice().switchToA2Mode();
    }

    public static void applyGammaCorrection(boolean apply, int value) {
        Device.currentDevice.applyGammaCorrection(apply, value);
    }

    public static void applyMonoLevel(int value) {
        Device.currentDevice.applyMonoLevel(value);
    }

    public static void applyColorFilter(int value) {
        Device.currentDevice().applyColorFilter(value);
    }

    public static void applyGCOnce() {
        Debug.d(TAG, "applyGCOnce", new Object[0]);
        Device.currentDevice().applyGCOnce();
    }

    public static void setTrigger(int count) {
        Device.currentDevice().setTrigger(count);
    }

    public static void repaintEveryThing() {
        Debug.d(TAG, "repaintEveryThing", new Object[0]);
        Device.currentDevice().repaintEveryThing();
    }

    public static void fillWhiteOnWakeup(boolean enable, UpdateMode repaintMode) {
        Device.currentDevice().fillWhiteOnWakeup(enable, repaintMode);
    }

    public static void useGCForNewSurface(boolean apply) {
        Debug.d(TAG, "useGCForNewSurface = " + apply, new Object[0]);
        Device.currentDevice().useGCForNewSurface(apply);
    }

    public static void setAutoSyncBufEnable(boolean enable) {
        Debug.d(TAG, "setAutoSyncBufEnable = " + enable, new Object[0]);
        Device.currentDevice().setAutoSyncBufEnable(enable);
    }

    public static void handwritingRepaint(View view, int left, int top, int right, int bottom) {
        Device.currentDevice().handwritingRepaint(view, left, top, right, bottom);
    }

    /** Strict Binder-backed handwriting overlay reconciliation. */
    @MainThread
    public static void handwritingRepaintOrThrow(
            View view, int left, int top, int right, int bottom) {
        requireMainThread("HANDWRITING_REPAINT");
        FirmwareBinderBackend.get().handwritingRepaint(view, left, top, right, bottom);
    }

    /**
     * Experimental firmware primitive. It is not used automatically without
     * an exact, validated firmware profile.
     */
    @WorkerThread
    public static void savePenAttachedFramebufferOrThrow() {
        requireWorkerThread("SAVE_PEN_ATTACHED_FB");
        FirmwareBinderBackend.get().savePenAttachedFramebuffer();
    }

    private static void requireMainThread(String operation) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new FirmwareOperationException(
                    operation, "thread-contract", "Operation must run on the main thread");
        }
    }

    private static void requireWorkerThread(String operation) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new FirmwareOperationException(
                    operation, "thread-contract", "Operation must not run on the main thread");
        }
    }

    public static int getStrictMaxTouchPressureOrThrow() {
        // This transaction returns a Parcel float on current BOOX firmware, just like the
        // display-dimension queries. Reading it as an int exposes the IEEE-754 bit pattern
        // (for example 1166012416 instead of 4096) and fabricates an impossible axis range.
        return Math.round(FirmwareBinderBackend.get().queryFloat("GET_MAX_TOUCH_PRESSURE"));
    }

    public static int getStrictEpdWidthOrThrow() {
        return Math.round(FirmwareBinderBackend.get().queryFloat("GET_EPD_WIDTH"));
    }

    public static int getStrictEpdHeightOrThrow() {
        return Math.round(FirmwareBinderBackend.get().queryFloat("GET_EPD_HEIGHT"));
    }

    public static int getStrictColorTypeOrThrow() {
        return FirmwareBinderBackend.get().queryInt("GET_COLOR_TYPE");
    }

    public static void setEpdTurbo(int value) {
        Debug.d(TAG, "setEpdTurbo: " + value, new Object[0]);
        Device.currentDevice().setEpdTurbo(value);
    }

    public static void resetEpd(Context context) {
        Device.currentDevice().byPass(0);
        resetEpdPost();
        clearTransientUpdate(false);
        appResetCTPDisableRegion(context);
    }

    public static void invalidate(View view, int left, int top, int right, int bottom, UpdateMode mode) {
        Device.currentDevice().invalidate(view, left, top, right, bottom, mode);
    }

    public static void setScreenHandWritingRegionLimit(View view) {
        Device.currentDevice().setScreenHandWritingRegionLimit(view);
    }

    public static void enterScribbleMode() {
        Device.currentDevice().enterScribbleMode();
    }

    public static void leaveScribbleMode() {
        Device.currentDevice().leaveScribbleMode();
    }

    public static void moveTo(View view, float x, float y, float width) {
        Device.currentDevice().moveTo(view, x, y, width);
    }

    public static void lineTo(View view, float x, float y, UpdateMode mode) {
        Device.currentDevice().lineTo(view, x, y, mode);
    }

    public static void quadTo(View view, float x, float y, UpdateMode mode) {
        Device.currentDevice().quadTo(view, x, y, mode);
    }

    public static Rect mapToEpd(View view, Rect src) {
        return Device.currentDevice().mapToEpd(view, src);
    }

    public static RectF mapToRawTouchPoint(View view, RectF rect) {
        return Device.currentDevice().mapToRawTouchPoint(view, rect);
    }

    public static void enableNightMode(boolean clear) {
        Debug.d(TAG, "enableNightMode, clear = " + clear, new Object[0]);
        Device.currentDevice().enableNightMode(true, clear);
    }

    public static void disableNightMode(boolean clear) {
        Debug.d(TAG, "disableNightMode, clear = " + clear, new Object[0]);
        Device.currentDevice().enableNightMode(false, clear);
    }

    public static void setAppCTPDisableRegion(Context context, Rect[] disableRegions) {
        setAppCTPDisableRegion(context, disableRegions, (Rect[]) null);
    }

    public static void handwritingRepaint(View view, Rect rect) {
        Device.currentDevice().handwritingRepaint(view, rect.left, rect.top, rect.right, rect.bottom);
        Debug.d("handwritingRepaint rect = " + rect);
    }

    public static boolean clearAppScopeUpdate(boolean clear) {
        Debug.d(TAG, "clearAppScopeUpdate, clear = " + clear, new Object[0]);
        Device.currentDevice().clearAppScopeUpdate(clear);
        return true;
    }

    public static void setScreenHandWritingRegionLimit(View view, int[] array) {
        Device.currentDevice().setScreenHandWritingRegionLimit(view, array);
    }

    public static void enablePost(int enable) {
        Debug.d(TAG, "enablePost = " + enable, new Object[0]);
        Device.currentDevice().enablePost(enable);
    }

    public static void moveTo(View view, float x, float y, float width, float pressure) {
        Device.currentDevice().moveTo(view, x, y, width, pressure);
    }

    public static void lineTo(View view, float x, float y, UpdateMode mode, float pressure) {
        Device.currentDevice().lineTo(view, x, y, mode, pressure);
    }

    public static void quadTo(View view, float x, float y, UpdateMode mode, float pressure) {
        Device.currentDevice().quadTo(view, x, y, mode, pressure);
    }

    public static void setAppCTPDisableRegion(Context context, int[] disableRegionArray, int[] excludeRegionArray) {
        Device.currentDevice().setAppCTPDisableRegion(context, disableRegionArray, excludeRegionArray);
    }

    public static void repaintEveryThing(UpdateMode mode) {
        Debug.d(TAG, "repaintEveryThing = " + mode, new Object[0]);
        Device.currentDevice().repaintEveryThing(mode);
    }

    public static void setScreenHandWritingRegionLimit(View view, Rect[] regions) {
        Device.currentDevice().setScreenHandWritingRegionLimit(view, regions);
    }

    public static void setAppCTPDisableRegion(Context context, Rect[] disableRegions, Rect[] excludeRegions) {
        Device.currentDevice().setAppCTPDisableRegion(context, disableRegions, excludeRegions);
    }

    public static boolean clearTransientUpdate(boolean clear) {
        return clearTransientUpdate(null, clear);
    }
}
