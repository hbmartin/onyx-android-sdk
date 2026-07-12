package com.onyx.android.sdk.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/KeyguardUtils.class */
public class KeyguardUtils {
    private static final String a = "KeyguardUtils";
    private static final String b = "android.settings.FINGERPRINT_SETTINGS";
    private static Class<?> c;
    private static Method d;
    private static Method e;
    private static Method f;
    private static Method g;
    private static Method h;

    public static void saveLockPassword(Context context, String password, String savedPassword) {
        ReflectUtil.invokeMethodSafely(d, (Object) null, new Object[]{context, password, savedPassword});
    }

    public static boolean checkPassword(Context context, String password) {
        Boolean bool = (Boolean) ReflectUtil.invokeMethodSafely(e, (Object) null, new Object[]{context, password});
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static boolean hasPassword(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (Build.VERSION.SDK_INT < 23 || keyguardManager == null) {
            return false;
        }
        Log.d(a, "keyguardManager.isDeviceSecure():" + keyguardManager.isDeviceSecure());
        return keyguardManager.isDeviceSecure();
    }

    public static void resetPassword(Context context) {
        ReflectUtil.invokeMethodSafely(f, (Object) null, new Object[]{context});
    }

    public static void startFingerprintManagement(Context context) {
        if (!CompatibilityUtil.apiLevelCheck(26) || !DeviceFeatureUtil.hasFingerprint(context)) {
            Log.i(a, "Current Device did not support finger print");
            return;
        }
        Intent intent = new Intent(b);
        intent.setPackage(BaseConstant.ANDROID_SETTING_PACKAGE_NAME);
        ActivityUtil.startActivitySafely(context, intent);
    }

    public static boolean isDeviceLocked(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (Build.VERSION.SDK_INT < 23 || keyguardManager == null) {
            return false;
        }
        return keyguardManager.isDeviceLocked();
    }

    public static boolean isInKeyguardInputMode(Context context) {
        return ((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public static boolean isScreenOn(Context context) {
        return ((PowerManager) context.getSystemService("power")).isScreenOn();
    }

    public static boolean modifyPassword(Context context, String newPassword, String oldPassword) {
        Method method = h;
        if (method == null) {
            return false;
        }
        ReflectUtil.invokeMethodSafely(method, (Object) null, new Object[]{context, newPassword, oldPassword});
        return h != null;
    }

    static {
        Class<?> clsClassForName = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
        c = clsClassForName;
        d = ReflectUtil.getMethodSafely(clsClassForName, "saveLockPassword", new Class[]{Context.class, String.class, String.class});
        e = ReflectUtil.getMethodSafely(c, "checkPassword", new Class[]{Context.class, String.class});
        f = ReflectUtil.getMethodSafely(c, "resetPassword", new Class[]{Context.class});
        g = ReflectUtil.getMethodSafely(c, "resetPassword", new Class[]{Context.class, String.class});
        h = ReflectUtil.getMethodSafely(c, "modifyPassword", new Class[]{Context.class, String.class, String.class});
    }

    public static void resetPassword(Context context, String savedCredential) {
        ReflectUtil.invokeMethodSafely(g, (Object) null, new Object[]{context, savedCredential});
    }
}
