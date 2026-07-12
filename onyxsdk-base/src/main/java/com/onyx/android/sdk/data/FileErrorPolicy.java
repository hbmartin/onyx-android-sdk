package com.onyx.android.sdk.data;

public enum FileErrorPolicy {
    Retry,
    Skip,
    SkipAll;

    public boolean isSkipPolicy() {
        return this == Skip || this == SkipAll;
    }
}
