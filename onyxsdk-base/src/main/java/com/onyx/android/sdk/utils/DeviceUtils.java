// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.io.File;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import java.nio.charset.StandardCharsets;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioManager;
import java.lang.reflect.Method;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.view.View;
import android.os.Process;
import android.view.Window;
import android.app.Dialog;
import android.app.Activity;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import android.telephony.TelephonyManager;
import android.provider.Settings;
import android.content.Context;
import android.os.Build;
import com.onyx.android.sdk.device.Device;

public class DeviceUtils
{
    public static final String TAG = "DeviceUtils";
    public static final String STATUS_BAR = "statusbar";
    public static final String STATUS_BAR_MANAGER = "android.app.StatusBarManager";
    public static final String SHOW_STATUS_BAR_ACTION = "show_status_bar";
    public static final String HIDE_STATUS_BAR_ACTION = "hide_status_bar";
    private static final String a = "/dev/input/event1";
    public static final int NEVER_SLEEP = Integer.MAX_VALUE;
    private static final String b = "ro.board.platform";
    private static final String c = "/onyxconfig";
    private static final String[] d;
    private static final int e = 65536;
    private static final int f = 1;
    private static final int g = 0;
    
    private static String a() {
        return getSystemProperty("ro.board.platform");
    }
    
    public static boolean isSDMDevice() {
        return Device.currentDeviceIndex() == Device.DeviceIndex.SDM;
    }
    
    public static boolean isRk32xxDevice() {
        return Device.currentDeviceIndex() == Device.DeviceIndex.Rk32xx;
    }
    
    public static boolean isRk31xxDevice() {
        return Device.currentDeviceIndex() == Device.DeviceIndex.Rk31xx;
    }
    
    public static boolean isRK3026() {
        return Device.currentDeviceIndex() == Device.DeviceIndex.Rk3026;
    }
    
    public static boolean isIMX6() {
        return Device.currentDeviceIndex() == Device.DeviceIndex.imx6;
    }
    
    public static boolean isRkDevice() {
        return isRk32xxDevice() || isRk31xxDevice() || isRK3026();
    }
    
    @Deprecated
    public static boolean isImx6Device() {
        return Build.HARDWARE.startsWith("freescale");
    }
    
    public static String getDeviceSerial(final Context context) {
        final String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        UUID randomUUID;
        try {
            String deviceId;
            randomUUID = ("9774d56d682e549c".equals(string) ? (((deviceId = ((TelephonyManager)context.getSystemService("phone")).getDeviceId()) != null) ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID()) : UUID.nameUUIDFromBytes(string.getBytes("utf8")));
        }
        catch (final UnsupportedEncodingException ex) {
            randomUUID = UUID.randomUUID();
            ex.printStackTrace();
        }
        return randomUUID.toString();
    }
    
    public static float getDensityDPI(final Context context) {
        return (float)context.getResources().getDisplayMetrics().densityDpi;
    }
    
    public static String getApplicationFingerprint(final Context context) {
        try {
            android.content.pm.PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return info.versionName + " (" + info.versionCode + ")";
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static int getScreenOrientation(final Context context) {
        final WindowManager windowManager;
        final int rotation;
        final int n = rotation = (windowManager = (WindowManager)context.getSystemService("window")).getDefaultDisplay().getRotation();
        final DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        final DisplayMetrics displayMetrics2 = displayMetrics;
        final WindowManager windowManager2 = windowManager;
        new DisplayMetrics();
        windowManager2.getDefaultDisplay().getMetrics(displayMetrics2);
        final int widthPixels = displayMetrics.widthPixels;
        final int heightPixels = displayMetrics.heightPixels;
        if (((n != 0 && rotation != 2) || heightPixels <= widthPixels) && ((rotation != 1 && rotation != 3) || widthPixels <= heightPixels)) {
            switch (rotation) {
                default: {
                    Log.e(DeviceUtils.TAG, "Unknown screen orientation. Defaulting to landscape.");
                    return 0;
                }
                case 2: {
                    break;
                }
                case 3: {
                    return 9;
                }
                case 0: {
                    return 0;
                }
                case 1: {
                    return 1;
                }
            }
        }
        else {
            switch (rotation) {
                default: {
                    Log.e(DeviceUtils.TAG, "Unknown screen orientation. Defaulting to portrait.");
                    return 1;
                }
                case 3: {
                    break;
                }
                case 2: {
                    return 9;
                }
                case 1: {
                    return 0;
                }
                case 0: {
                    return 1;
                }
            }
        }
        return 8;
    }
    
    public static boolean isDeviceInLandscapeOrientation(final Context context) {
        final int screenOrientation;
        return (screenOrientation = getScreenOrientation(context)) == 0 || screenOrientation == 8;
    }
    
    public static int detectTouchDeviceCount() {
        int n = 0;
        for (int i = 1; i < 3; ++i) {
            if (FileUtils.fileExist(String.format("/dev/input/event%d", i))) {
                ++n;
            }
        }
        return n;
    }
    
    // Faithful reference behavior, including its defect: on devices where the
    // app cannot read /proc/bus/input/devices this falls back to event1 even
    // when the digitizer lives elsewhere (a NoteAir4C stylus is event5). The
    // reference SDK returns the same wrong path; the pen module's native
    // reader performs its own event0-15 discovery and is unaffected.
    public static String detectInputDevicePath() {
        String format = "/dev/input/event1";
        final String detectInputDevicePath;
        if (StringUtils.isNotBlank(detectInputDevicePath = DetectInputDeviceUtil.detectInputDevicePath())) {
            format = String.format("/dev/input/event%s", detectInputDevicePath);
        }
        return format;
    }
    
    @SuppressLint({ "NewApi" })
    public static int getBatteryPercentLevel(final Context context) {
        final Intent registerReceiver;
        final int intExtra = (registerReceiver = context.registerReceiver((BroadcastReceiver)null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), BroadcastHelper.ReceiverFlags.RECEIVER_EXPORTED)).getIntExtra("level", -1);
        final int intExtra2 = registerReceiver.getIntExtra("scale", -1);
        final float f = intExtra * 100 / (float)intExtra2;
        Debug.i(DeviceUtils.TAG, "getBatteryPercentLevel level=" + intExtra + ", scale=" + intExtra2 + ", batteryPercent=" + f, new Object[0]);
        return (int)f;
    }
    
    public static boolean isEngVersion() {
        final ArrayList source = new ArrayList();
        source.add(Build.TYPE);
        source.add(SystemPropertiesUtil.getBuildId());
        source.add(SystemPropertiesUtil.getFingerprint());
        return CollectionUtils.contains(source, Arrays.asList("eng", "userdebug"));
    }
    
    public static void setFullScreenForSoftInputModeWork(final Activity activity) {
        if (activity == null) {
            return;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(2310);
    }
    
    public static void setFullScreenOnResume(final Activity activity, final boolean fullScreen) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            a(activity, fullScreen);
            return;
        }
        Intent intent;
        if (fullScreen) {
            intent = new Intent("hide_status_bar");
        }
        else {
            intent = new Intent("show_status_bar");
        }
        activity.sendBroadcast(intent);
    }
    
    static void a(final Activity activity, final boolean fullScreen) {
        adjustFullScreenStatus(activity.getWindow(), fullScreen);
    }
    
    public static void adjustDialogFullScreenStatusForAPIAbove19(final Dialog dialog, final boolean fullScreen) {
        if (Build.VERSION.SDK_INT >= 19) {
            adjustFullScreenStatus(dialog.getWindow(), fullScreen);
        }
    }
    
    public static void adjustFullScreenStatus(final Window window, final boolean fullScreen) {
        if (window == null) {
            return;
        }
        int n;
        int n2;
        int b;
        if (fullScreen) {
            n = 2048;
            n2 = 1024;
            b = b();
        }
        else {
            n = 1024;
            n2 = 2048;
            b = 8192;
        }
        final int n3 = n2;
        window.clearFlags(n);
        window.setFlags(n3, n2);
        window.getDecorView().setSystemUiVisibility(b);
    }
    
    private static int b() {
        return 13590;
    }
    
    public static int getWindowFullScreenImmersiveOption() {
        return 4102;
    }
    
    public static void avoidWindowJitterWhenStartActivity(final Activity activity) {
        activity.getWindow().setFlags(2048, 2048);
    }
    
    public static void setFullScreenOnCreate(final Activity activity, final boolean fullScreen) {
        if (fullScreen) {
            try {
                activity.requestWindowFeature(1);
                try {
                    activity.getWindow().addFlags(1024);
                }
                catch (final Exception ex) {
                    Debug.e((Throwable)ex);
                }
            }
            catch (final Exception ex2) {}
        }
    }
    
    public static void exit() {
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
    
    public static boolean isReverseOrientation(final int current, final int target) {
        return (current == 1 && target == 9) || (current == 9 && target == 1) || (current == 0 && target == 8) || (current == 8 && target == 0);
    }
    
    public static boolean isFullScreen(final Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            return (activity.getWindow().getAttributes().flags & 0x400) == 0x400;
        }
        final int[] array = new int[2];
        activity.getWindow().getDecorView().getLocationOnScreen(array);
        return array[1] <= 0;
    }
    
    public static boolean isFullScreen(final View topLeftView) {
        final int[] array2;
        final int[] array = array2 = new int[2];
        topLeftView.getLocationOnScreen(array2);
        return array[0] == 0 && array2[1] == 0;
    }
    
    public static boolean isDeviceInteractive(final Context context) {
        final PowerManager powerManager = (PowerManager)context.getSystemService("power");
        return (Build.VERSION.SDK_INT >= 20) ? powerManager.isInteractive() : powerManager.isScreenOn();
    }
    
    public static boolean isChinese(final Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }
    
    public static void turnOffSystemPMSettings(final Context context) {
        Settings.System.putInt(context.getContentResolver(), "screen_off_timeout", Integer.MAX_VALUE);
        Settings.System.putInt(context.getContentResolver(), "auto_poweroff_timeout", Integer.MAX_VALUE);
    }
    
    public static String getPackageName(final Context context) {
        return context.getPackageName();
    }
    
    public static String getPackageVersionName(final Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(getPackageName(context), 0).versionName;
    }
    
    public static String getPackageVersionName(final Context context, final String packageName) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
    }
    
    public static int getPackageVersionCode(final Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(getPackageName(context), 0).versionCode;
    }
    
    public static String getApplicationName(final Context context) throws PackageManager.NameNotFoundException {
        final PackageManager packageManager = context.getPackageManager();
        return packageManager.getApplicationLabel(packageManager.getApplicationInfo(getPackageName(context), 0)).toString();
    }
    
    public static String getSystemProperty(final String key) {
        String s = "";
        try {
            final Class<?> forName = Class.forName("android.os.SystemProperties");
            final String name = "get";
            try {
                final Method method = forName.getMethod(name, String.class);
                final Object obj = null;
                try {
                    s = (String)method.invoke(obj, key);
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
            catch (final Exception ex2) {}
        }
        catch (final Exception ex3) {}
        return s;
    }
    
    public static int getStatusBarHeight(final Context context) {
        int dimensionPixelSize = 0;
        final int identifier;
        if ((identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android")) > 0) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
        }
        return dimensionPixelSize;
    }
    
    public static boolean gotoOnyxSoundSettings(final Context context) {
        return ActivityUtil.startActivitySafely(context, new Intent("onyx.settings.action.SoundSettings"), true);
    }
    
    public static void showSystemVolumeAdjustUI(final Context context) {
        if (CompatibilityUtil.apiLevelCheck(28)) {
            final Intent intent;
            (intent = new Intent("android.settings.SOUND_SETTINGS")).setFlags(268435456);
            context.startActivity(intent);
        }
        else {
            final AudioManager audioManager;
            if ((audioManager = (AudioManager)context.getSystemService("audio")) != null) {
                audioManager.adjustSuggestedStreamVolume(0, 3, 1);
            }
        }
    }
    
    public static void disableStatusBar(final Context context) {
        final Method methodSafely = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.app.StatusBarManager"), "disable", new Class[] { Integer.TYPE });
        final Object systemService = context.getApplicationContext().getSystemService("statusbar");
        final Object[] array = { null };
        final int n = 0;
        final Integer value = (Build.VERSION.SDK_INT >= 17) ? 65536 : 1;
        final Method method = methodSafely;
        array[n] = value;
        ReflectUtil.invokeMethodSafely(method, systemService, array);
    }
    
    public static void enableStatusBar(final Context context) {
        ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.app.StatusBarManager"), "disable", new Class[] { Integer.TYPE }), context.getApplicationContext().getSystemService("statusbar"), new Object[] { 0 });
    }
    
    public static boolean isSupportFingerpoint(final Context context) {
        final FingerprintManager fingerprintManager;
        return Build.VERSION.SDK_INT >= 23 && (fingerprintManager = (FingerprintManager)context.getSystemService("fingerprint")) != null && fingerprintManager.isHardwareDetected();
    }
    
    public static String getCpuId() {
        final String cpuId;
        String s;
        if (StringUtils.isNotBlank(cpuId = Device.currentDevice().getCpuId())) {
            s = encodeHex(cpuId.getBytes(StandardCharsets.UTF_8));
        }
        else {
            s = cpuId;
        }
        return s;
    }

    private static String encodeHex(final byte[] data) {
        final char[] digits = "0123456789abcdef".toCharArray();
        final char[] encoded = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            final int value = data[i] & 255;
            encoded[i * 2] = digits[value >>> 4];
            encoded[i * 2 + 1] = digits[value & 15];
        }
        return new String(encoded);
    }
    
    public static String getDeviceUniqueId(final Context context) {
        final String cpuId;
        if (StringUtils.isNotBlank(cpuId = getCpuId())) {
            return cpuId;
        }
        return NetworkUtil.getMacAddress(context);
    }
    
    public static boolean isAutoRotationEnable(Context context) {
        boolean enabled = false;
        try {
            enabled = Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation") == 1;
        }
        catch (final Settings.SettingNotFoundException ex) {
            ex.printStackTrace();
        }
        return enabled;
    }
    
    public static boolean isSystemInMultiWindowMode(final Context context) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT > 29 && context instanceof Activity) {
            return ((Activity)context).isInMultiWindowMode();
        }
        return Device.currentDevice().isSystemInMultiWindowMode(context);
    }
    
    public static long getLastMasterClearTime() {
        long n = 0L;
        String[] d;
        long lastChangeTime;
        for (int length = (d = DeviceUtils.d).length, i = 0; i < length; ++i, n = lastChangeTime) {
            if ((lastChangeTime = FileUtils.getLastChangeTime(new File("/onyxconfig" + File.separator + d[i]))) <= n) {
                lastChangeTime = n;
            }
        }
        return n;
    }
    
    public static int getFullScreenUiVisibility() {
        return 11014;
    }
    
    static {
        d = new String[] { "com.onyx.android.production.test", "com.onyx.test", "com.onyx.android.screenmanager" };
    }
    
    public enum FontType
    {
        CJK, 
        ENGLISH, 
        ALL, 
        CUSTOMIZE, 
        SYSTEM;
        
        public static boolean isEnglishCharSet(final String charSet) {
            try {
                return valueOf(charSet) == FontType.ENGLISH;
            }
            catch (final IllegalArgumentException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        
        public static FontType getFontType(final int fontTypeIndex) {
            if (fontTypeIndex == 1) {
                return FontType.CJK;
            }
            if (fontTypeIndex != 2) {
                return FontType.ALL;
            }
            return FontType.ENGLISH;
        }
        
        public static boolean isCustom(final FontType fontType) {
            return fontType == FontType.CUSTOMIZE;
        }
    }
}
