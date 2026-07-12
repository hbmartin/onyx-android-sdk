package com.onyx.android.sdk.utils;

import android.text.TextUtils;

public class ClassUtils {
    public static String getSimpleName(Class<?> cls) {
        if (cls == null) {
            return "null cls";
        }
        String simpleName = cls.getSimpleName();
        return TextUtils.isEmpty(simpleName) ? fallbackSimpleName(cls) : simpleName;
    }

    private static String fallbackSimpleName(Class<?> cls) {
        String name = cls.getName();
        try {
            int separator = name.lastIndexOf('.');
            if (separator > 0) {
                name = name.substring(separator + 1);
            }
        } catch (Exception error) {
            Debug.e(error);
        }
        return name;
    }
}
