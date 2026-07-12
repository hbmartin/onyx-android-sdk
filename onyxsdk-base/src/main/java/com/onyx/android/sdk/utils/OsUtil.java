package com.onyx.android.sdk.utils;

import android.os.Build;
import android.system.Os;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/OsUtil.class */
public class OsUtil {
    public static void setenv(String name, String value, boolean overwrite) throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            Os.setenv(name, value, overwrite);
            return;
        }
        Class clsClassForName = ReflectUtil.classForName("libcore.io.Libcore");
        if (clsClassForName == null) {
            Debug.w(OsUtil.class, "Can not find libcore.io.Libcore", new Object[0]);
            return;
        }
        Object staticFieldSafely = ReflectUtil.getStaticFieldSafely(clsClassForName, "os");
        if (staticFieldSafely == null) {
            Debug.w(OsUtil.class, "Can not find Libcore.os", new Object[0]);
            return;
        }
        Method methodSafely = ReflectUtil.getMethodSafely(staticFieldSafely.getClass(), "setenv", new Class[]{String.class, String.class, Boolean.TYPE});
        if (methodSafely == null) {
            Debug.w(OsUtil.class, "Can not find Os.setenv()", new Object[0]);
        } else {
            ReflectUtil.invokeMethodSafely(methodSafely, staticFieldSafely, new Object[]{name, value, Boolean.valueOf(overwrite)});
        }
    }

    public static String getenv(String name) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Os.getenv(name);
        }
        Class clsClassForName = ReflectUtil.classForName("libcore.io.Libcore");
        if (clsClassForName == null) {
            Debug.w(OsUtil.class, "Can not find libcore.io.Libcore", new Object[0]);
            return null;
        }
        Object staticFieldSafely = ReflectUtil.getStaticFieldSafely(clsClassForName, "os");
        if (staticFieldSafely == null) {
            Debug.w(OsUtil.class, "Can not find Libcore.os", new Object[0]);
            return null;
        }
        Method methodSafely = ReflectUtil.getMethodSafely(staticFieldSafely.getClass(), "getenv", new Class[]{String.class});
        if (methodSafely == null) {
            Debug.w(OsUtil.class, "Can not find Os.getenv()", new Object[0]);
            return null;
        }
        AtomicReference atomicReference = new AtomicReference();
        if (ReflectUtil.invokeMethodSafely(atomicReference, methodSafely, staticFieldSafely, new Object[]{name}) && (atomicReference.get() instanceof String)) {
            return (String) atomicReference.get();
        }
        return null;
    }
}
