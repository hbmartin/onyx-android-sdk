package com.onyx.android.sdk.common.request;

import android.content.Context;
import com.onyx.android.sdk.utils.RxTimerUtil;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/common/request/WakelockTimer.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002*\u0001\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/onyx/android/sdk/common/request/WakelockTimer;", TTFFont.UNKNOWN_FONT_NAME, "flag", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/String;)V", "getFlag", "()Ljava/lang/String;", "releaseTimerObserver", "com/onyx/android/sdk/common/request/WakelockTimer$releaseTimerObserver$1", "Lcom/onyx/android/sdk/common/request/WakelockTimer$releaseTimerObserver$1;", "wakeLockHolder", "Lcom/onyx/android/sdk/common/request/WakeLockHolder;", "cancelScheduledRelease", TTFFont.UNKNOWN_FONT_NAME, "ensureReleaseWakeLock", "exitWakeLock", "isHeld", TTFFont.UNKNOWN_FONT_NAME, "startWakeLock", "context", "Landroid/content/Context;", "timeMs", TTFFont.UNKNOWN_FONT_NAME, "Companion", "onyxsdk-base_release"})
public final class WakelockTimer {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final String d = "onyx-wakelock-timer";

    @NotNull
    private final String a;

    @NotNull
    private final WakeLockHolder b;

    @NotNull
    private final WakelockTimer$releaseTimerObserver$1 c;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.onyx.android.sdk.common.request.WakelockTimer$releaseTimerObserver$1] */
    public WakelockTimer(@NotNull String flag) {
        Intrinsics.checkNotNullParameter(flag, "flag");
        this.a = flag;
        this.b = new WakeLockHolder(false);
        this.c = new WakelockTimer$releaseTimerObserver$1(this);
    }

    public WakelockTimer() {
        this(null, 1, null);
    }

    @NotNull
    public final String getFlag() {
        return this.a;
    }

    public final void startWakeLock(@NotNull Context context, long timeMs) {
        Intrinsics.checkNotNullParameter(context, "context");
        cancelScheduledRelease();
        this.b.releaseWakeLock();
        this.b.acquireWakeLock(context, 26, this.a);
        if (timeMs > 0) {
            RxTimerUtil.timer(timeMs, this.c);
        }
    }

    public final void exitWakeLock() {
        cancelScheduledRelease();
        this.b.releaseWakeLock();
    }

    public final void cancelScheduledRelease() {
        RxTimerUtil.cancel(this.c);
    }

    public final void ensureReleaseWakeLock() {
        if (isHeld()) {
            this.b.releaseWakeLock();
        }
    }

    public final boolean isHeld() {
        return this.b.isHeld();
    }

    void releaseFromTimer() {
        this.b.releaseWakeLock();
    }

    public /* synthetic */ WakelockTimer(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? d : str);
    }

    public static final WakeLockHolder access$getWakeLockHolder$p(WakelockTimer wakelockTimer) {
        return wakelockTimer.b;
    }

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\u000e\n\u0000\u0008\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/common/request/WakelockTimer$Companion;", "", "()V", "DEFAULT_TAG", "", "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
    }
}

final class WakelockTimer$releaseTimerObserver$1 extends RxTimerUtil.TimerObserver {
    private final WakelockTimer owner;

    WakelockTimer$releaseTimerObserver$1(WakelockTimer owner) {
        this.owner = owner;
    }

    @Override
    public void onNext(Long ignored) {
        owner.releaseFromTimer();
    }
}
