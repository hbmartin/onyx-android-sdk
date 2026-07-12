package com.onyx.android.sdk.utils;

import android.view.View;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/MagnifierUtils.class */
public class MagnifierUtils {
    public static boolean closeViewMagnifier(View view) {
        boolean z = ReflectUtil.invokeMethodSafely(ReflectUtil.getMethodSafely(view.getClass(), "setMagnifierEnable", Boolean.TYPE), view, Boolean.FALSE) != null;
        boolean z2 = z;
        Debug.d(view.getClass() + " closeViewMagnifier: " + z);
        return z2;
    }
}
