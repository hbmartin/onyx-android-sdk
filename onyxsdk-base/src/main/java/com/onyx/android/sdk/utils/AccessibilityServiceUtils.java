package com.onyx.android.sdk.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Method;

public class AccessibilityServiceUtils {
    private static final String a = "AccessibilityServiceUtils";
    private static final String b = "onyx.android.intent.send.gesture";
    private static final String c = "gesture_start_event_x";
    private static final String d = "gesture_start_event_y";
    private static final String e = "gesture_end_event_x";
    private static final String f = "gesture_end_event_y";
    private static final Class<?> g;
    private static final Method h;
    private static final Method i;

    public static void setAccessibilityServiceState(Context context, ComponentName toggledService, boolean enabled) {
        ReflectUtil.invokeMethodSafely(h, (Object) null, new Object[]{context, toggledService, Boolean.valueOf(enabled)});
    }

    public static boolean ensureAccessibilityServiceEnabled(@NonNull Context context, @NonNull Class serviceClass) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager == null) {
            return false;
        }
        if (accessibilityManager.isEnabled()) {
            return true;
        }
        Debug.d(AccessibilityServiceUtils.class, "enable %s now", new Object[]{serviceClass.getName()});
        setAccessibilityServiceState(context, new ComponentName(context.getPackageName(), serviceClass.getName()), true);
        return true;
    }

    @SuppressLint({"NewApi"})
    public static void scrollScreen(Context context, AccessibilityService as, Point from, Point to, long duration, @Nullable AccessibilityService.GestureResultCallback callback, @Nullable Handler handler) {
        if (as == null || from == null || to == null || duration <= 0) {
            return;
        }
        if (!CompatibilityUtil.apiLevelCheck(24)) {
            a(context, from.x, from.y, to.x, to.y);
            return;
        }
        if (!ensureAccessibilityServiceEnabled(context, as.getClass())) {
            Debug.d(AccessibilityServiceUtils.class, "%s is not enabled", new Object[]{as.getClass().getName()});
            return;
        }
        Path path = new Path();
        path.moveTo(from.x, from.y);
        path.lineTo(to.x, to.y);
        Debug.d(AccessibilityServiceUtils.class, "from (" + from.x + ", " + from.y + ") to (" + to.x + ", " + to.y + ")", new Object[0]);
        as.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, duration)).build(), callback, handler);
    }

    private static void a(Context context, int startX, int startY, int endX, int endY) {
        Intent intent = new Intent(b);
        intent.putExtra(c, startX);
        intent.putExtra(d, startY);
        intent.putExtra(e, endX);
        intent.putExtra(f, endY);
        BroadcastHelper.sendBroadcast(context, intent);
    }

    static {
        Class<?> clsClassForName = ReflectUtil.classForName("android.onyx.AndroidSettingsHelper");
        g = clsClassForName;
        Class cls = Boolean.TYPE;
        h = ReflectUtil.getMethodSafely(clsClassForName, "setAccessibilityServiceState", new Class[]{Context.class, ComponentName.class, cls});
        i = ReflectUtil.getMethodSafely(clsClassForName, "setAccessibilityServiceState", new Class[]{Context.class, ComponentName.class, cls, Integer.TYPE});
    }

    public static void setAccessibilityServiceState(Context context, ComponentName toggledService, boolean enabled, int userId) {
        ReflectUtil.invokeMethodSafely(i, (Object) null, new Object[]{context, toggledService, Boolean.valueOf(enabled), Integer.valueOf(userId)});
    }
}
