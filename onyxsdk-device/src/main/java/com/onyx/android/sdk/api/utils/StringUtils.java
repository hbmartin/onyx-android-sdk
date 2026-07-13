package com.onyx.android.sdk.api.utils;

import java.util.Objects;

public class StringUtils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().length() <= 0;
    }

    public static boolean isNotBlank(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static boolean safelyEquals(String firstStr, String secondStr) {
        return Objects.equals(firstStr, secondStr);
    }

    /**
     * Joins elements while intentionally eliding leading elements whose string value is empty.
     */
    public static String join(Iterable<?> elements, String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (Object element : elements) {
            if (builder.length() > 0) {
                builder.append(delimiter);
            }
            builder.append(element);
        }
        return builder.toString();
    }
}
