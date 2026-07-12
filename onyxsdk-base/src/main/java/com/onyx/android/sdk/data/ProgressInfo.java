package com.onyx.android.sdk.data;

import com.alibaba.fastjson2.annotation.JSONCreator;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ProgressInfo.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0007\b\u0017¢\u0006\u0002\u0010\u0002B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u000f\b\u0016\u0012\u0006\u0010\t\u001a\u00020\u0000¢\u0006\u0002\u0010\nJ\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015¨\u0006\u001e"}, d2 = {"Lcom/onyx/android/sdk/data/ProgressInfo;", TTFFont.UNKNOWN_FONT_NAME, "()V", "current", TTFFont.UNKNOWN_FONT_NAME, "total", "(II)V", TTFFont.UNKNOWN_FONT_NAME, "(JJ)V", "progressInfo", "(Lcom/onyx/android/sdk/data/ProgressInfo;)V", "progress", TTFFont.UNKNOWN_FONT_NAME, "getProgress", "()D", "setProgress", "(D)V", "soFarBytes", "getSoFarBytes", "()J", "setSoFarBytes", "(J)V", "totalBytes", "getTotalBytes", "setTotalBytes", "isCompete", TTFFont.UNKNOWN_FONT_NAME, "toString", TTFFont.UNKNOWN_FONT_NAME, "Companion", "onyxsdk-base_release"})
public final class ProgressInfo {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private long a;

    /* JADX INFO: renamed from: b, reason: from toString */
    private long totalBytes;

    /* JADX INFO: renamed from: c, reason: from toString */
    private double progress;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ProgressInfo$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/data/ProgressInfo$Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "onComplete", "Lcom/onyx/android/sdk/data/ProgressInfo;", "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final ProgressInfo onComplete() {
            return new ProgressInfo(100, 100);
        }
    }

    @JSONCreator
    public ProgressInfo() {
    }

    public final long getSoFarBytes() {
        return this.a;
    }

    public final void setSoFarBytes(long j) {
        this.a = j;
    }

    public final long getTotalBytes() {
        return this.totalBytes;
    }

    public final void setTotalBytes(long j) {
        this.totalBytes = j;
    }

    public final double getProgress() {
        return this.progress;
    }

    public final void setProgress(double d) {
        this.progress = d;
    }

    public final boolean isCompete() {
        return this.progress >= 100.0d;
    }

    @NotNull
    public String toString() {
        return "ProgressInfo{soFarBytes=" + this.a + ", totalBytes=" + this.totalBytes + ", progress=" + this.progress + '}';
    }

    public ProgressInfo(int current, int total) {
        this((long) current, (long) total);
    }

    public ProgressInfo(long current, long total) {
        this.a = current;
        this.totalBytes = total;
        this.progress = (current * ((double) 100)) / total;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ProgressInfo(@NotNull ProgressInfo progressInfo) {
        this(progressInfo.a, progressInfo.totalBytes);
        Intrinsics.checkNotNullParameter(progressInfo, "progressInfo");
    }
}
