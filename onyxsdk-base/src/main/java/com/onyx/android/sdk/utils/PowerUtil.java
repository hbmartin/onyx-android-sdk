package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/PowerUtil.class */
public class PowerUtil {
    private static final String a = "com.android.internal.os.PowerProfile";

    public static double getBatteryCapacity(Context context) {
        Class clsClassForName = ReflectUtil.classForName(a);
        Object objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(clsClassForName, "getBatteryCapacity", new Class[0]), ReflectUtil.newInstance(ReflectUtil.getConstructorSafely(clsClassForName, new Class[]{Context.class}), new Object[]{context}), new Object[0]);
        if (objInvokeMethodSafely == null || !(objInvokeMethodSafely instanceof Double)) {
            return 0.0d;
        }
        return ((Double) objInvokeMethodSafely).doubleValue();
    }

    @SuppressLint({"NewApi"})
    public static boolean isBatteryCharging(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), BroadcastHelper.ReceiverFlags.RECEIVER_EXPORTED);
        int intExtra = 1;
        if (intentRegisterReceiver != null) {
            intExtra = intentRegisterReceiver.getIntExtra("status", 1);
        }
        return intExtra == 2;
    }

    public static long convertUsToMs(long timeUs) {
        return timeUs / 1000;
    }
}
