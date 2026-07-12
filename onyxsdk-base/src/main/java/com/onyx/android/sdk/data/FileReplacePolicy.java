package com.onyx.android.sdk.data;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FileReplacePolicy.class */
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
