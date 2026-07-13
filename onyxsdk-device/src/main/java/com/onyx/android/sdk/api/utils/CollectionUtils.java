package com.onyx.android.sdk.api.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    public static boolean isNullOrEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
