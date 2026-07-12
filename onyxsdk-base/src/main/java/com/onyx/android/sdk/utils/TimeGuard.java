package com.onyx.android.sdk.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/onyx/android/sdk/utils/TimeGuard;", TTFFont.UNKNOWN_FONT_NAME, "threshold", TTFFont.UNKNOWN_FONT_NAME, "(J)V", "lastTime", "getLastTime", "()J", "setLastTime", "accept", TTFFont.UNKNOWN_FONT_NAME, "reset", TTFFont.UNKNOWN_FONT_NAME, "update", "onyxsdk-base_release"})
public final class TimeGuard {
    private final long a;
    private long b;

    public TimeGuard(long threshold) {
        this.a = threshold;
        this.b = -1L;
    }

    public TimeGuard() {
        this(0L, 1, null);
    }

    public final long getLastTime() {
        return this.b;
    }

    public final void setLastTime(long j) {
        this.b = j;
    }

    public final boolean accept() {
        return this.b > 0 && System.currentTimeMillis() - this.b > this.a;
    }

    public final void update() {
        this.b = System.currentTimeMillis();
    }

    public final void reset() {
        this.b = -1L;
    }

    public /* synthetic */ TimeGuard(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j);
    }
}
