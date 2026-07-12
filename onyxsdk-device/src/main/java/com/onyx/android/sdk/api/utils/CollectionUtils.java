package com.onyx.android.sdk.api.utils;

import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/utils/CollectionUtils.class */
public class CollectionUtils {
    public static boolean isNullOrEmpty(Collection list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() <= 0;
    }
}
