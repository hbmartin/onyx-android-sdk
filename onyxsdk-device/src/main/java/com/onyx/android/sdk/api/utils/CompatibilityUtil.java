package com.onyx.android.sdk.api.utils;

import android.os.Build;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/utils/CompatibilityUtil.class */
public class CompatibilityUtil {
    public static final int VERSION_CODE_P = 28;
    public static final int VERSION_CODE_Q = 29;
    public static final int VERSION_CODE_R = 30;
    public static final int VERSION_CODE_S = 31;

    public static boolean isApiLevelSatisfied(int requireAPILevel) {
        return Build.VERSION.SDK_INT >= requireAPILevel;
    }

    public static boolean isApiLevelAbove(int requireAPILevel) {
        return Build.VERSION.SDK_INT > requireAPILevel;
    }
}
