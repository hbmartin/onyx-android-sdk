package com.onyx.android.sdk.utils;

import android.os.Build;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import com.onyx.android.sdk.api.utils.StringUtils;
import com.onyx.android.sdk.device.Device;

public class SystemPropertiesUtil {
    private static final String a = "ro.vendor.build.onyxfp";
    private static final String b = "ro.vendor.build.onyxid";
    private static final String c = "vendor.onyx.tablet";
    private static final String d = "vendor.onyx.phone";
    private static final String e = "vendor.onyx.htcon";
    private static final String f;
    private static final String g;
    private static final String h = "vendor.onyx.fp_enroll_times";
    private static final String i = "3";
    private static final String j = "14";

    public static String get(String key) {
        return Device.currentDevice().getSystemProperties(key);
    }

    public static void set(String key, String value) {
        Device.currentDevice().setSystemProperties(key, value);
    }

    public static String getFingerprint() {
        String str = get(a);
        String str2 = str;
        if (StringUtils.isNullOrEmpty(str)) {
            str2 = Build.FINGERPRINT;
        }
        return str2;
    }

    public static String getBuildId() {
        String str = get(b);
        String str2 = str;
        if (StringUtils.isNullOrEmpty(str)) {
            str2 = Build.ID;
        }
        return str2;
    }

    public static boolean isTablet() {
        return Boolean.parseBoolean(get(c));
    }

    public static boolean isPhone() {
        return Boolean.parseBoolean(get(d));
    }

    public static boolean hasHTCon() {
        return Boolean.parseBoolean(get(e));
    }

    public static String getCurrentFont() {
        return get(f);
    }

    @Deprecated
    public static String getCurrentEnFont() {
        return get(g);
    }

    public static void setFpEnrollTemporaryTimes() {
        set(h, i);
    }

    public static void setFpEnrollDefaultTimes() {
        set(h, j);
    }

    static {
        f = CompatibilityUtil.isApiLevelSatisfied(24) ? "persist.sys.font.zh.regular.config" : "persist.sys.font.zh.regular";
        g = CompatibilityUtil.isApiLevelSatisfied(24) ? "persist.sys.font.en.regular.config" : "persist.sys.font.en.regular";
    }
}
