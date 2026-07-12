package com.onyx.android.sdk.data;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.StringUtils;

public enum BookFilter {
    ALL,
    NEW,
    READING,
    FINISHED,
    RECENT_ADD,
    TAG,
    SEARCH,
    EXTRA_ATTRIBUTES,
    LOCAL_SOURCE,
    CLOUD_SOURCE;

    @Nullable
    public static BookFilter safelyValueOf(String name) {
        try {
            return StringUtils.isNullOrEmpty(name) ? null : valueOf(name);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
