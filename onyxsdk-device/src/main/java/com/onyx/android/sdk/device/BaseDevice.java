package com.onyx.android.sdk.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.onyx.android.sdk.api.device.epd.EPDMode;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdateOption;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import com.onyx.android.sdk.api.device.tp.UpgradeConfig;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import com.onyx.android.sdk.api.utils.StringUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/device/BaseDevice.class */
public class BaseDevice {
    private static final String b = "BaseDevice";
    private static final String c = "show_status_bar";
    private static final String d = "hide_status_bar";
    private static final String e = "enable_wifi_connect_status_detection_status";
    private static final String f = "onyx.settings.action.cancel.wifi.detect";
    private static final String g = "com.onyx.action.SHOW_ROTATION_LOCK_DIALOG_ACTION";
    private static final String h = "args_wifi_connect_detection_flag";
    private static final String i = "args_wifi_connect_detection_latency_flag";
    private static final String j = "switch_to_page_key";
    private static final String k = "switch_to_volume_key";
    private static final String l = "switch_to_home_back_key";
    private static final String m = "switch_key";
    private static final String n = "eng";
    private static final String o = "userdebug";
    public static final String VOLUME_TYPE_NTFS = "NTFS";
    public static final String SELINUX_RESTORECON_RECURSIVE = "selinux.restorecon_recursive";
    public static final int LIGHT_TYPE_NONE = 0;
    public static final int LIGHT_TYPE_FL = 1;
    public static final int LIGHT_TYPE_CTM_WARM = 2;
    public static final int LIGHT_TYPE_CTM_COLD = 3;
    public static final int LIGHT_TYPE_CTM_ALL = 4;
    public static final int LIGHT_TYPE_CTM = 5;
    public static final int LIGHT_TYPE_CTM_TEMPERATURE = 6;
    public static final int LIGHT_TYPE_CTM_BRIGHTNESS = 7;
    public static int UPDATE_MODE_DEFAULT = 0;
    public static int UPDATE_MODE_DU = 1;
    public static int UPDATE_MODE_A2 = 2;
    public static int UPDATE_MODE_REGAL = 3;
    public static int UPDATE_MODE_X = 4;
    public static final int MAX_CONTRAST_VALUE = 100;
    public static final int DITHER_NORMAL = 128;
    public static final int DITHER_HIGH_CONTRAST = 255;
    public static final int COLOR_DEVICE_DITHER_CONTRAST = 160;
    public static final int SINGLE_SCREEN_MODE = 0;
    public static final int LIMITED_MULTI_SCREEN_MODE = 1;
    public static final int FULL_FUNCTION_MULTI_SCREEN_MODE = 2;
    public static final int DEFAULT_WIFI_DETECT_LATENCY = 6000;
    protected static final Map<String, String> volumePathUUIDMap = new HashMap();
    private Method updateLocaleMethod;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/device/BaseDevice$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[UpdateOption.values().length];
            a = iArr;
            try {
                iArr[UpdateOption.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[UpdateOption.FAST_QUALITY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[UpdateOption.REGAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[UpdateOption.FAST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[UpdateOption.FAST_X.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public BaseDevice() {
        this.updateLocaleMethod = null;
        this.updateLocaleMethod = ReflectUtil.getMethodSafely(ReflectUtil.classForName("com.android.internal.app.LocalePicker"), "updateLocale", Locale.class);
    }

    public File getStorageRootDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    @Nullable
    @Deprecated
    public File getRemovableSDCardDirectory() {
        return getRemovableSDCardDirs().get(0);
    }

    public List<File> getRemovableSDCardDirs() {
        File externalStorageDirectory = getExternalStorageDirectory();
        File file = new File(externalStorageDirectory, "extsd");
        return file.exists() ? Collections.singletonList(file) : Collections.singletonList(externalStorageDirectory);
    }

    public boolean isPrimaryStorageRemovable(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((StorageManager) context.getSystemService("storage")).getPrimaryStorageVolume().isRemovable();
        }
        return false;
    }

    public File getBluetoothRootDirectory() {
        return new File(getExternalStorageDirectory().getPath() + File.separator + "bluetooth");
    }

    public boolean isFileOnRemovableSDCard(File file) {
        return file.getAbsolutePath().startsWith(getRemovableSDCardDirectory().getAbsolutePath());
    }

    public PowerManager.WakeLock newWakeLock(Context context, String tag) {
        return ((PowerManager) context.getApplicationContext().getSystemService("power")).newWakeLock(26, tag);
    }

    public PowerManager.WakeLock newWakeLockWithFlags(Context context, int flags, String tag) {
        return ((PowerManager) context.getApplicationContext().getSystemService("power")).newWakeLock(flags, tag);
    }

    int a(UpdateOption updateOption) {
        int i2 = UPDATE_MODE_DEFAULT;
        switch (a.a[updateOption.ordinal()]) {
            case 1:
                i2 = UPDATE_MODE_DEFAULT;
                break;
            case 2:
                i2 = UPDATE_MODE_DU;
                break;
            case 3:
                i2 = UPDATE_MODE_REGAL;
                break;
            case 4:
                i2 = UPDATE_MODE_A2;
                break;
            case 5:
                i2 = UPDATE_MODE_X;
                break;
        }
        return i2;
    }

    public void useBigPen(boolean use) {
    }

    public void stopTpd() {
    }

    public void startTpd() {
    }

    public void enableTpd(boolean enable) {
    }

    public float getTouchWidth() {
        return 0.0f;
    }

    public float getTouchHeight() {
        return 0.0f;
    }

    public float getMaxTouchPressure() {
        return 1024.0f;
    }

    public float getEpdWidth() {
        return 0.0f;
    }

    public float getEpdHeight() {
        return 0.0f;
    }

    public void mapToView(View view, float[] src, float[] dst) {
    }

    public void mapToEpd(View view, float[] src, float[] dst) {
    }

    public Rect mapToEpd(View view, Rect srcRect) {
        float[] fArr = new float[2];
        fArr[0] = srcRect.left;
        fArr[1] = srcRect.top;
        float[] fArr2 = new float[2];
        mapToEpd(view, fArr, fArr2);
        float[] fArr3 = {srcRect.right, srcRect.bottom};
        mapToEpd(view, fArr3, fArr2);
        return new Rect((int) Math.min(fArr[0], fArr2[0]), (int) Math.min(fArr[1], fArr2[1]), (int) Math.max(fArr[0], fArr2[0]), (int) Math.max(fArr[1], fArr2[1]));
    }

    public void mapFromRawTouchPoint(View view, float[] src, float[] dst) {
    }

    public void mapToRawTouchPoint(View view, float[] src, float[] dst) {
    }

    public RectF mapToRawTouchPoint(View view, RectF rect) {
        float[] fArr = new float[2];
        fArr[0] = rect.left;
        fArr[1] = rect.top;
        float[] fArr2 = new float[2];
        mapToRawTouchPoint(view, fArr, fArr2);
        float[] fArr3 = {rect.right, rect.bottom};
        mapToRawTouchPoint(view, fArr3, fArr2);
        return new RectF(Math.min(fArr[0], fArr2[0]), Math.min(fArr[1], fArr2[1]), Math.max(fArr[0], fArr2[0]), Math.max(fArr[1], fArr2[1]));
    }

    public int getFrontLightBrightnessMinimum(Context context) {
        return 0;
    }

    public int getFrontLightBrightnessMaximum(Context context) {
        return 0;
    }

    public int getFrontLightBrightnessDefault(Context context) {
        return 0;
    }

    public boolean openFrontLight(Context context) {
        return false;
    }

    public boolean closeFrontLight(Context context) {
        return false;
    }

    public boolean openFrontLight(int type) {
        return false;
    }

    public boolean closeFrontLight(int type) {
        return false;
    }

    public boolean setLedColor(String ledColor, int on) {
        return false;
    }

    public int getFrontLightDeviceValue(Context context) {
        return 0;
    }

    public List<Integer> getFrontLightValueList(Context context) {
        return new ArrayList();
    }

    public boolean setFrontLightDeviceValue(Context context, int value) {
        return false;
    }

    public boolean setNaturalLightConfigValue(Context context, int value) {
        return false;
    }

    public int getFrontLightConfigValue(Context context) {
        return 0;
    }

    public boolean setFrontLightConfigValue(Context context, int value) {
        return false;
    }

    public void setTouchpadEnable(Context context, boolean enable) {
    }

    public boolean isTouchpadEnable() {
        return false;
    }

    public EPDMode getEpdMode() {
        return EPDMode.AUTO;
    }

    public boolean setEpdMode(Context context, EPDMode mode) {
        return false;
    }

    public boolean setEpdMode(View view, EPDMode mode) {
        return false;
    }

    public UpdateMode getViewDefaultUpdateMode(View view) {
        return UpdateMode.GU;
    }

    public boolean setViewDefaultUpdateMode(View view, UpdateMode mode) {
        return false;
    }

    public void resetViewUpdateMode(View view) {
    }

    public UpdateMode getSystemDefaultUpdateMode() {
        return UpdateMode.GU;
    }

    public boolean setSystemDefaultUpdateMode(UpdateMode mode) {
        return false;
    }

    public boolean applyAppScopeUpdate(String application, boolean enable, boolean clear) {
        return false;
    }

    public boolean applyAppScopeUpdate(String application, boolean enable, boolean clear, UpdateMode repeatMode, int repeatLimit) {
        return false;
    }

    public boolean clearAppScopeUpdate() {
        return false;
    }

    public boolean clearAppScopeUpdate(boolean clear) {
        return false;
    }

    public boolean applyTransientUpdate(UpdateMode updateMode) {
        return false;
    }

    public boolean clearTransientUpdate(boolean clear) {
        return false;
    }

    public boolean setDisplayScheme(int scheme) {
        return false;
    }

    public void waitForUpdateFinished() {
    }

    public void invalidate(View view, UpdateMode mode) {
        view.invalidate();
    }

    public void invalidate(View view, int left, int top, int right, int bottom, UpdateMode mode) {
    }

    public boolean enableScreenUpdate(View view, boolean enable) {
        return false;
    }

    public void refreshScreen(View view, UpdateMode mode) {
    }

    public void refreshScreenRegion(View view, int left, int top, int width, int height, UpdateMode mode) {
    }

    public void screenshot(View view, int r, String path) {
    }

    public boolean supportDFB() {
        return false;
    }

    public boolean supportRegal() {
        return false;
    }

    public void holdDisplay(boolean hold, UpdateMode updateMode, int ignoreFrame) {
    }

    public void byPass(int count) {
    }

    public void setStrokeColor(int color) {
    }

    public void setStrokeStyle(int style) {
    }

    public void setStrokeWidth(float width) {
    }

    public void setPainterStyle(boolean antiAlias, Paint.Style strokeStyle, Paint.Join joinStyle, Paint.Cap capStyle) {
    }

    public void moveTo(float x, float y, float width) {
    }

    public void moveTo(View view, float x, float y, float width) {
    }

    public void moveTo(View view, float x, float y, float width, float pressure) {
    }

    public void lineTo(float x, float y, UpdateMode mode) {
    }

    public void lineTo(View view, float x, float y, UpdateMode mode) {
    }

    public void lineTo(View view, float x, float y, UpdateMode mode, float pressure) {
    }

    public void quadTo(float x, float y, UpdateMode mode) {
    }

    public void quadTo(View view, float x, float y, UpdateMode mode) {
    }

    public void quadTo(View view, float x, float y, UpdateMode mode, float pressure) {
    }

    public void penUp() {
    }

    public float startStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return baseWidth;
    }

    public float addStrokePoint(float baseWidth, float x, float y, float pressure, float size, float time) {
        return baseWidth;
    }

    public float finishStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return baseWidth;
    }

    @Deprecated
    public void enterScribbleMode(View view) {
    }

    @Deprecated
    public void leaveScribbleMode(View view) {
    }

    @Deprecated
    public void enablePost(View view, int enable) {
    }

    public void enablePost(int enable) {
    }

    public void enterScribbleMode() {
    }

    public void leaveScribbleMode() {
    }

    public void resetEpdPost() {
    }

    public boolean isValidPenState() {
        return true;
    }

    public int getPenState() {
        return 0;
    }

    public void setScreenHandWritingPenState(View view, int penState) {
    }

    public void setScreenHandWritingRegionMode(View view, int mode) {
    }

    public void setScreenHandWritingRegionLimit(View view) {
    }

    public void setScreenHandWritingRegionLimit(View view, int left, int top, int right, int bottom) {
    }

    public void setScreenHandWritingRegionLimit(View view, int[] array) {
    }

    public void setScreenHandWritingRegionLimit(View view, Rect[] regions) {
    }

    public void setScreenHandWritingRegionExclude(View view, int[] array) {
    }

    public void setScreenHandWritingRegionExclude(View view, Rect[] regions) {
    }

    public void postInvalidate(View view, UpdateMode mode) {
        view.postInvalidate();
    }

    public boolean applySysScopeUpdate(UpdateMode mode, UpdateScheme scheme, int count) {
        return false;
    }

    public boolean clearSysScopeUpdate() {
        return false;
    }

    public void wifiLock(Context context, String className) {
    }

    public void wifiUnlock(Context context, String className) {
    }

    public void wifiLockClear(Context context) {
    }

    public String getFixedWifiMacAddress(Context context) {
        return null;
    }

    public Map<String, Integer> getWifiLockMap(Context context) {
        return null;
    }

    public void setWifiLockTimeout(Context context, long ms) {
    }

    public String getEncryptedDeviceID() {
        return null;
    }

    public void led(Context context, boolean on) {
    }

    public void setVCom(Context context, int mv, String path) {
    }

    public void updateWaveform(Context context, String path, String target) {
    }

    public int getVCom(Context context, String path) {
        return 0;
    }

    public String readSystemConfig(Context context, String key) {
        return "";
    }

    public boolean saveSystemConfig(Context context, String key, String mv) {
        return false;
    }

    public boolean deleteSystemConfig(Context context, String key) {
        return false;
    }

    public void updateMetadataDB(Context context, String path, String target) {
    }

    public Point getWindowWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        point.x = windowManager.getDefaultDisplay().getWidth();
        point.y = windowManager.getDefaultDisplay().getHeight();
        return point;
    }

    public void hideSystemStatusBar(Context context) {
        a(context, d);
        if (Build.VERSION.SDK_INT < 26) {
            Debug.printStackTraceDebug("hideSystemStatusBar");
        }
    }

    public void showSystemStatusBar(Context context) {
        a(context, c);
        if (Build.VERSION.SDK_INT < 26) {
            Debug.printStackTraceDebug("showSystemStatusBar");
        }
    }

    public void showRotationLockDialog(Context context) {
        context.sendBroadcast(new Intent(g));
    }

    public void setSystemStatusBarVisible(Context context, boolean visible) {
        if (visible) {
            showSystemStatusBar(context);
        } else {
            hideSystemStatusBar(context);
        }
    }

    public void mapSideKeyToVolumeKey(Context context) {
        a(context, k);
    }

    public void mapSideKeyToHomeBackKey(Context context) {
        a(context, l);
    }

    public void resetKeyMapping(Context context) {
        a(context, j);
    }

    public int getCurrentSideKeyMapping(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), m);
        } catch (Exception e2) {
            if (e2 instanceof Settings.SettingNotFoundException) {
                return -1;
            }
            e2.printStackTrace();
            return -1;
        }
    }

    public void enableWifiDetect(Context context) {
        enableWifiDetect(context, true);
    }

    public void cancelPendingWifiDetect(Context context) {
        context.sendBroadcast(new Intent(f));
    }

    public void stopBootAnimation() {
    }

    public void disableA2ForSpecificView(View view) {
    }

    public void enableA2ForSpecificView(View view) {
    }

    public void setWebViewContrastOptimize(WebView view, boolean enabled) {
    }

    public boolean isLegalSystem(Context context) {
        return true;
    }

    public boolean isTouchable(Context context) {
        return true;
    }

    public void gotoSleep(Context context) {
    }

    public void enableRegal(boolean enable) {
    }

    public boolean hasWifi(Context context) {
        return true;
    }

    public void setQRShowConfig(int orientation, int startX, int startY) {
    }

    public void setInfoShowConfig(int orientation, int startX, int startY) {
    }

    public void setUpdListSize(int size) {
    }

    @Deprecated
    public boolean inSystemFastMode() {
        return false;
    }

    public String getUpgradePackageName() {
        return "update.zip";
    }

    public boolean shouldVerifyUpdateModel() {
        return true;
    }

    public void powerCTP(boolean on) {
    }

    public void powerEMTP(boolean on) {
    }

    public boolean isCTPPowerOn() {
        return true;
    }

    public boolean isEMTPPowerOn() {
        return true;
    }

    public void setAppCTPDisableRegion(Context context, int[] disableRegionArray, @Nullable int[] excludeRegionArray) {
    }

    public void setAppCTPDisableRegion(Context context, Rect[] disableRegions, @Nullable Rect[] excludeRegions) {
    }

    public boolean isCTPDisableRegion(Context context) {
        return false;
    }

    public void appResetCTPDisableRegion(Context context) {
    }

    public void updateMtpDb(Context context, String filePath) {
    }

    public void updateMtpDb(Context context, File file) {
    }

    @Deprecated
    public void removeAppConfig(String jsonString) {
    }

    @Deprecated
    public void setEACServiceConfig(boolean enable, boolean debug) {
    }

    @Deprecated
    public void setEACAppConfig(String pkgName, String jsonString) {
    }

    public void switchToA2Mode() {
    }

    public void toggleA2Mode() {
    }

    public void applyGammaCorrection(boolean apply, int value) {
    }

    public void applyMonoLevel(int value) {
    }

    public void applyColorFilter(int value) {
    }

    public void applyGCOnce() {
    }

    public String getCTPInfo() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/android_touch/vendor"));
    }

    public String getTPDetailsInfo() {
        return "";
    }

    public boolean upgradeTP(UpgradeConfig config) {
        return false;
    }

    public boolean hasAudio(Context context) {
        return true;
    }

    public boolean hasFLBrightness(Context context) {
        return false;
    }

    public boolean hasCTMBrightness(Context context) {
        return false;
    }

    public boolean hasBluetooth(Context context) {
        return false;
    }

    public boolean supportExternalSD(Context context) {
        return true;
    }

    public void setTrigger(int count) {
    }

    public void freezeApplication(Context context, String pkgName, int userId) {
    }

    public void unfreezeApplication(Context context, String pkgName, int userId) {
    }

    public void forceStopApplication(Context context, String pkgName) {
    }

    public void freezeGooglePlay(Context context) {
    }

    public void unfreezeGooglePlay(Context context) {
    }

    public boolean isGooglePlayEnabled(Context context) {
        return false;
    }

    public boolean isGoogleAppsExists(Context context) {
        return false;
    }

    @Nullable
    protected int[] convertRectArrayToIntArray(@Nullable Rect[] regions) {
        if (regions == null) {
            return null;
        }
        int[] iArr = new int[regions.length * 4];
        for (int i2 = 0; i2 < regions.length; i2++) {
            Rect rect = regions[i2];
            int iMin = Math.min(rect.left, rect.right);
            int iMin2 = Math.min(rect.top, rect.bottom);
            int iMax = Math.max(rect.left, rect.right);
            int iMax2 = Math.max(rect.top, rect.bottom);
            int i3 = i2 * 4;
            iArr[i3] = iMin;
            iArr[i3 + 1] = iMin2;
            iArr[i3 + 2] = iMax;
            iArr[i3 + 3] = iMax2;
        }
        return iArr;
    }

    @SuppressLint({"HardwareIds"})
    public String getBluetoothAddress() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return "";
        }
        String address = defaultAdapter.isEnabled() ? defaultAdapter.getAddress() : null;
        return !TextUtils.isEmpty(address) ? address.toLowerCase() : "";
    }

    @Nullable
    public String getIPAddress(Context context) {
        return null;
    }

    public String getOpenSourceCodeLicensePath() {
        return "/system/etc/NOTICE.html.gz";
    }

    public boolean isPowerSavedMode(Context context) {
        return false;
    }

    public void enablePowerSavedMode(Context context, boolean enable) {
    }

    public boolean isHallControlEnable(Context context) {
        return false;
    }

    public void enableHallControl(Context context, boolean enable) {
    }

    public long getBootUpTime() {
        return SystemClock.elapsedRealtime();
    }

    public void repaintEveryThing() {
    }

    public void repaintEveryThing(UpdateMode mode) {
    }

    public void fillWhiteOnWakeup(boolean enable, UpdateMode repaintMode) {
    }

    public void useGCForNewSurface(boolean apply) {
    }

    public void setAutoSyncBufEnable(boolean enable) {
    }

    public void handwritingRepaint(View view, int left, int top, int right, int bottom) {
    }

    public void applySystemFastMode(boolean enable) {
    }

    public void setCTMBrightnessValue(int type, int value) {
    }

    public String[] loadCACertificate() {
        return new String[0];
    }

    public String[] loadUserCertificate() {
        return new String[0];
    }

    public void applySFDebug(boolean enableDebug) {
    }

    public boolean isEMTPDisabled(Context context) {
        return false;
    }

    public boolean isKeyboardDisabled(Context context) {
        return false;
    }

    public void setEMTPDisabled(Context context, boolean disabled) {
    }

    public void setKeyboardDisabled(Context context, boolean disabled) {
    }

    public Integer[] getWarmLightValues(Context context) {
        return new Integer[0];
    }

    public Integer[] getColdLightValues(Context context) {
        return new Integer[0];
    }

    public boolean checkCTM() {
        return false;
    }

    public Integer getLightValues(int tpye) {
        return 0;
    }

    public Integer getMaxLightValues(int tpye) {
        return 10;
    }

    public Integer getBrDefaultValue() {
        return 0;
    }

    public Integer getCtDefaultValue() {
        return 0;
    }

    public boolean setLightValue(int type, int value) {
        return false;
    }

    public Integer[][] getNaturalLightValues(Context context) {
        return new Integer[0][];
    }

    public Integer[] getFLBrightnessValues(Context context) {
        return new Integer[0];
    }

    public int getWarmLightConfigValue(Context context) {
        return 0;
    }

    public int getColdLightConfigValue(Context context) {
        return 0;
    }

    public boolean setWarmLightDeviceValue(Context context, int value) {
        return false;
    }

    public boolean setColdLightDeviceValue(Context context, int value) {
        return false;
    }

    @Nullable
    public ComponentName getCurrentTopComponent(Context context) {
        return null;
    }

    public List<ActivityManager.RunningTaskInfo> getRunningTasksWithoutPermissionCheck(Context context, int maxNum) {
        return Collections.emptyList();
    }

    @Nullable
    public String getEACAppConfigByPkgName(String pkgName) {
        return null;
    }

    public boolean increaseBrightness(Context context, int colorTemp) {
        return false;
    }

    public boolean decreaseBrightness(Context context, int colorTemp) {
        return false;
    }

    @Deprecated
    public boolean isBrightnessOn(Context context) {
        return false;
    }

    public boolean isLightOn(Context context) {
        return false;
    }

    public boolean isLightOn(Context context, int type) {
        return false;
    }

    public boolean isEngVersion() {
        return n.equalsIgnoreCase(Build.TYPE);
    }

    public boolean isUserDebugVersion() {
        return o.equalsIgnoreCase(Build.TYPE);
    }

    public void setPwm1Output(int value) {
    }

    public void configLightStatusBar(Window window) {
    }

    public void configStatusBar(Window window, int backgroundColor, boolean useLight) {
    }

    public void setRotationLockAtAngle(Context context, boolean enabled, int rotation) {
    }

    public boolean isInMultiWindowMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= 24) {
            return activity.isInMultiWindowMode();
        }
        return false;
    }

    public int getFrontLightTypeCTMWarm() {
        return 2;
    }

    public int getFrontLightTypeCTMCold() {
        return 3;
    }

    public Rect getTaskBounds(Context context, int taskId) {
        return new Rect();
    }

    public boolean isPrimaryTaskInMultiWindowMode(Context context, int taskId) {
        return false;
    }

    public int getTaskWindowingMode(Context context, int taskId) {
        return 0;
    }

    public boolean isSystemInMultiWindowMode(Context context) {
        return false;
    }

    public boolean isEnabledStartActivityInMultiWindowMode(Context context) {
        return true;
    }

    public boolean isOriginMultiWindow() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public boolean isShowStatusBarInMultiWindowMode() {
        return false;
    }

    public String getSystemConfigPrefix(Context context) {
        return "/vendor/";
    }

    public int getGlobalContrast() {
        return -1;
    }

    public void setGlobalContrast(int contrast) {
    }

    public int getDitherThreshold() {
        return -1;
    }

    public void setDitherThreshold(int value) {
    }

    public void setSystemProperties(String key, String value) {
    }

    public String getSystemProperties(String key) {
        return null;
    }

    public UpdateOption getAppScopeRefreshMode() {
        return UpdateOption.NORMAL;
    }

    public void setAppScopeRefreshMode(UpdateOption updateOption) {
    }

    @Deprecated
    public UpdateOption getSystemRefreshMode() {
        return getAppScopeRefreshMode();
    }

    @Deprecated
    public void setSystemRefreshMode(UpdateOption updateOption) {
        setAppScopeRefreshMode(updateOption);
    }

    public void dumpCTPInfo(Context context) {
    }

    public boolean isInAppScopeRefreshModeDefault() {
        return false;
    }

    public boolean isInAppScopeRefreshModeX() {
        return false;
    }

    public int getStandbyTimeout() {
        return 0;
    }

    public void setStandbyTimeout(int ms) {
    }

    public int getPowerOffTimeout() {
        return 0;
    }

    public void setPowerOffTimeout(int ms) {
    }

    public void setNotificationEnabled(String pkgName, int uid, boolean enable) {
    }

    public void setSystemFontSize(Context context, float scale) {
        Settings.System.putFloat(context.getContentResolver(), "font_scale", scale);
    }

    public int getColorType() {
        return 0;
    }

    public void enableColorCU(boolean enable) {
    }

    public void enableNightMode(boolean enable) {
    }

    public void enableNightMode(boolean enable, boolean clear) {
    }

    public boolean isSupportNightMode() {
        return false;
    }

    public void setEpdTurbo(int value) {
    }

    public void setEnablePenSideButton(boolean enable) {
    }

    public void setBrushRawDrawingEnabled(boolean enable) {
    }

    public void setEraserRawDrawingEnabled(boolean enable, int eraserStyle) {
    }

    public float[] getStrokeParameters(int strokeStyle) {
        return new float[0];
    }

    public void setStrokeParameters(int strokeStyle, float[] params) {
    }

    /** Returns whether the vendor stroke color/style/width methods resolved. */
    public final boolean supportsStrokeStyleConfiguration() {
        return hasStrokeStyleConfigurationCapability();
    }

    /** Returns whether start/add/finish stroke methods resolved. */
    public final boolean supportsStrokeDataTransport() {
        return hasStrokeDataTransportCapability();
    }

    /** Returns whether extra stroke parameters and raw-drawing controls resolved. */
    public final boolean supportsAdvancedStrokeConfiguration() {
        return hasAdvancedStrokeConfigurationCapability();
    }

    boolean hasStrokeStyleConfigurationCapability() {
        return false;
    }

    boolean hasStrokeDataTransportCapability() {
        return false;
    }

    boolean hasAdvancedStrokeConfigurationCapability() {
        return false;
    }

    @Nullable
    public Matrix getRawTouchPointToScreenMatrix() {
        Matrix matrix = new Matrix();
        float[] epdToViewMatrixHelper = getEpdToViewMatrixHelper();
        if (epdToViewMatrixHelper == null || epdToViewMatrixHelper.length < 11) {
            return null;
        }
        matrix.setScale(epdToViewMatrixHelper[9], epdToViewMatrixHelper[10]);
        Matrix matrix2 = new Matrix();
        matrix2.setValues(epdToViewMatrixHelper);
        matrix.postConcat(matrix2);
        return matrix;
    }

    public Matrix getEpdToViewMatrix() {
        Matrix matrix = new Matrix();
        float[] epdToViewMatrixHelper = getEpdToViewMatrixHelper();
        if (epdToViewMatrixHelper == null || epdToViewMatrixHelper.length < 9) {
            return matrix;
        }
        matrix.setValues(epdToViewMatrixHelper);
        return matrix;
    }

    protected float[] getEpdToViewMatrixHelper() {
        return null;
    }

    public void setLowWorkWifiTimeout(Context context, long timeout) {
    }

    public void setLowWorkBluetoothTimeout(Context context, long timeout) {
    }

    public void setLowWorkAudioTimeout(Context context, long timeout) {
    }

    public int getCurrentMultiScreenMode(Context context) {
        return 0;
    }

    public boolean isLimitedMultiScreenMode(Context context) {
        return false;
    }

    public boolean isFullFunctionMultiScreenMode(Context context) {
        return false;
    }

    public void enableStandbyByPressPowerButton(Context context, boolean enable) {
    }

    public Rect getCurrentFocusTaskBound(Context context) {
        return new Rect();
    }

    public String getCpuId() {
        return com.onyx.android.sdk.device.a.a(new File("/sys/devices/soc0/serial_number"));
    }

    public void setTextShowPassword(Context context, boolean isShow) {
    }

    public boolean isTextShowPasswordOn() {
        return true;
    }

    public int getMinPasswordLength(Context context) {
        return context.getResources().getInteger(R.integer.pin_code_min_count);
    }

    public int getMaxPasswordLength(Context context) {
        return context.getResources().getInteger(R.integer.pin_code_max_count);
    }

    public boolean isInFastMode() {
        return false;
    }

    public boolean isInAppFastMode() {
        return false;
    }

    public boolean isInSystemFastMode() {
        return inSystemFastMode();
    }

    public void updateLocale(Context context, Locale locale) {
        ReflectUtil.invokeMethodSafely(this.updateLocaleMethod, null, locale);
    }

    public boolean isSupportWidecg(Context context) {
        return false;
    }

    public void setScrollingRefreshMode(int mode) {
    }

    public int convertRotation(int rotation) {
        return rotation;
    }

    public String getCalibrationFilePath() {
        return "";
    }

    public String getFsTypeByVolumeId(String volId) {
        return "";
    }

    public String getVolumeIdByFile(Context context, File file) {
        StorageManager storageManager;
        StorageVolume storageVolume;
        Object objInvokeMethodSafely;
        return (file == null || context == null || (storageManager = (StorageManager) context.getSystemService(StorageManager.class)) == null || (storageVolume = storageManager.getStorageVolume(file)) == null || (objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(StorageVolume.class, "getId", new Class[0]), storageVolume, new Object[0])) == null || objInvokeMethodSafely.equals("")) ? "" : objInvokeMethodSafely.toString();
    }

    public String getDiskIdById(Context context, String id) {
        StorageManager storageManager;
        Class<?> clsClassForName;
        Object objInvokeMethodSafely;
        Object objInvokeMethodSafely2;
        return (id == null || context == null || (storageManager = (StorageManager) context.getSystemService(StorageManager.class)) == null || (clsClassForName = ReflectUtil.classForName("android.os.storage.VolumeInfo")) == null || (objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(StorageManager.class, "findVolumeById", String.class), storageManager, id)) == null || (objInvokeMethodSafely2 = ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(clsClassForName, "getDiskId", new Class[0]), objInvokeMethodSafely, new Object[0])) == null || objInvokeMethodSafely2.equals("")) ? "" : objInvokeMethodSafely2.toString();
    }

    @Nullable
    @RequiresApi(api = 24)
    public String getVolumeUUID(Context context, String filePath) {
        StorageVolume storageVolume;
        int iIndexOf;
        if (StringUtils.isNullOrEmpty(filePath) || context == null) {
            return null;
        }
        for (Map.Entry<String, String> entry : volumePathUUIDMap.entrySet()) {
            if (filePath.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        StorageManager storageManager = (StorageManager) context.getSystemService(StorageManager.class);
        if (storageManager == null || (storageVolume = storageManager.getStorageVolume(new File(filePath))) == null) {
            return null;
        }
        String uuid = storageVolume.getUuid();
        String removableSDCardCid = uuid;
        if (StringUtils.isNotBlank(uuid) && (iIndexOf = filePath.indexOf(removableSDCardCid)) >= 0) {
            int length = iIndexOf + removableSDCardCid.length();
            if (isTFCard(filePath)) {
                removableSDCardCid = EnvironmentUtil.getRemovableSDCardCid();
            }
            volumePathUUIDMap.put(filePath.substring(0, length), removableSDCardCid);
        }
        return removableSDCardCid;
    }

    public boolean isTFCard(String filePath) {
        return filePath.startsWith("/storage") && !filePath.startsWith(getExternalStorageDirectory().getAbsolutePath());
    }

    public boolean isUSBStorage(String filePath) {
        return filePath.startsWith("/mnt/media_rw");
    }

    @Deprecated
    public boolean isVolumeReadOnly(Context context, File file) {
        return false;
    }

    public boolean useCustomRotationChangedBroadcast() {
        return false;
    }

    public void rebootFlashDeviceSn(String sn) {
    }

    public boolean isResetPasswordSupported() {
        return Build.VERSION.SDK_INT <= 29;
    }

    public void restoreconFile(String filePath) {
        setSystemProperties(SELINUX_RESTORECON_RECURSIVE, filePath);
    }

    public boolean isSystemCTPEnable() {
        return false;
    }

    public Map<String, String> loadSystemFamilyPathMap() {
        HashMap map = new HashMap();
        map.put("serif", "/system/fonts/NotoSerif-Regular.ttf");
        map.put("sans-serif", "/system/fonts/Roboto-Regular.ttf");
        map.put("cursive", "/system/fonts/DancingScript-Regular.ttf");
        map.put("fantasy", "/system/fonts/NotoSerif-Regular.ttf");
        map.put("monospace", "/system/fonts/DroidSansMono.ttf");
        map.put("system-ui", "/system/fonts/NotoSerif-Regular.ttf");
        return map;
    }

    public void setRingerModeInternal(Context context, int ringerMode) {
    }

    public boolean isWeaklyValidatedHostname(String hostname) {
        return true;
    }

    public void setSystemConfigFilePermission(Context context, String key, boolean externalReadable, boolean externalWriteable) {
    }

    public boolean supportFontHotReload() {
        return CompatibilityUtil.isApiLevelAbove(23);
    }

    public boolean enableChargingControl(boolean enable) {
        return false;
    }

    public boolean isSupportChargingControl() {
        return false;
    }

    public boolean supportActivePen() {
        return false;
    }

    public int getActivePenBatteryLevel() {
        return 0;
    }

    public int getActivePenHapticType() {
        return 0;
    }

    public void setActivePenHapticType(int type) {
    }

    public int getActivePenHapticStrength() {
        return 0;
    }

    public void setActivePenHapticStrength(int strength) {
    }

    public boolean getActivePenEnable() {
        return false;
    }

    public void setActivePenEnable(boolean enable) {
    }

    public String getActivePenMacAddress() {
        return "00:00:00:00:00:00";
    }

    public int getWirelessChargingBatteryLevel() {
        return -1;
    }

    public int getWirelessChargingState() {
        return 0;
    }

    public String getWirelessChargingChipId() {
        return "0x0";
    }

    public String getWirelessChargingChipVersion() {
        return "0x0";
    }

    public boolean supportWirelessCharging() {
        return false;
    }

    public boolean isPenUIVisibilityEnable() {
        return false;
    }

    public boolean isPenHapticEnabled() {
        return false;
    }

    public void penHapticEnabled(boolean enable) {
    }

    public void setDefaultDataSubId(SubscriptionManager subscriptionManager, int subId) {
    }

    public void shutdown(PowerManager powerManager) {
    }

    public String getValidMacAddressRegex() {
        return "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}";
    }

    public void enableColorAdjust(boolean enable) {
    }

    public void applySaturationFactor(float value) {
    }

    public void applyNoiseStrength(float value) {
    }

    public void applyDitherFilterTolerance(float value) {
    }

    public void enableWifiDetect(Context context, boolean enableDetect) {
        enableWifiDetect(context, enableDetect, DEFAULT_WIFI_DETECT_LATENCY);
    }

    public void enableWifiDetect(Context context, boolean enableDetect, int detectLatency) {
        Intent intent = new Intent(e);
        intent.putExtra(h, enableDetect);
        intent.putExtra(i, detectLatency);
        context.sendBroadcast(intent);
    }

    UpdateOption a(int refreshMode) {
        if (refreshMode == UPDATE_MODE_DEFAULT) {
            return UpdateOption.NORMAL;
        }
        if (refreshMode == UPDATE_MODE_DU) {
            return UpdateOption.FAST_QUALITY;
        }
        if (refreshMode == UPDATE_MODE_A2) {
            return UpdateOption.FAST;
        }
        if (refreshMode == UPDATE_MODE_X) {
            return UpdateOption.FAST_X;
        }
        if (refreshMode == UPDATE_MODE_REGAL) {
            return UpdateOption.REGAL;
        }
        return UpdateOption.NORMAL;
    }

    private void a(Context context, String action) {
        context.sendBroadcast(new Intent(action));
    }
}
