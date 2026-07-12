package com.onyx.android.sdk.api.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().length() <= 0;
    }

    public static boolean isNotBlank(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static boolean safelyEquals(String firstStr, String secondStr) {
        if (firstStr == null && secondStr == null) {
            return true;
        }
        if (firstStr == null || secondStr == null) {
            return false;
        }
        return firstStr.equals(secondStr);
    }

    public static String join(Iterable<?> elements, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : elements) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(obj);
        }
        return sb.toString();
    }
}
