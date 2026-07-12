package com.onyx.android.sdk.rx;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxScrollerInfo.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0000R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0005\"\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/onyx/android/sdk/rx/RxScrollerInfo;", TTFFont.UNKNOWN_FONT_NAME, "()V", "isXFinished", TTFFont.UNKNOWN_FONT_NAME, "()Z", "setXFinished", "(Z)V", "isYFinished", "setYFinished", "newX", TTFFont.UNKNOWN_FONT_NAME, "getNewX", "()F", "setNewX", "(F)V", "newY", "getNewY", "setNewY", "isFinished", "set", TTFFont.UNKNOWN_FONT_NAME, "info", "onyxsdk-base_release"})
public final class RxScrollerInfo {
    private boolean a;
    private boolean b;
    private float c;
    private float d;

    public final boolean isXFinished() {
        return this.a;
    }

    public final void setXFinished(boolean z) {
        this.a = z;
    }

    public final boolean isYFinished() {
        return this.b;
    }

    public final void setYFinished(boolean z) {
        this.b = z;
    }

    public final float getNewX() {
        return this.c;
    }

    public final void setNewX(float f) {
        this.c = f;
    }

    public final float getNewY() {
        return this.d;
    }

    public final void setNewY(float f) {
        this.d = f;
    }

    public final void set(@NotNull RxScrollerInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        this.a = info.a;
        this.b = info.b;
        this.c = info.c;
        this.d = info.d;
    }

    public final boolean isFinished() {
        return this.a && this.b;
    }
}
