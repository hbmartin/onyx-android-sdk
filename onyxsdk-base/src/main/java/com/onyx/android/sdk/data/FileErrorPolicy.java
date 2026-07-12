package com.onyx.android.sdk.data;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FileErrorPolicy.class */
public enum FileErrorPolicy {
    Retry,
    Skip,
    SkipAll;

    public boolean isSkipPolicy() {
        return this == Skip || this == SkipAll;
    }
}
