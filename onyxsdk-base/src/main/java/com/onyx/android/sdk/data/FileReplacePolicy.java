package com.onyx.android.sdk.data;

public enum FileReplacePolicy {
    Ask,
    Replace,
    ReplaceAll,
    Skip,
    SkipAll;

    public boolean isSkipPolicy() {
        return this == Skip || this == SkipAll;
    }

    public boolean isReplacePolicy() {
        return this == Replace || this == ReplaceAll;
    }
}
