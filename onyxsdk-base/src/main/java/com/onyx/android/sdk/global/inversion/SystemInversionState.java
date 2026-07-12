package com.onyx.android.sdk.global.inversion;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/global/inversion/SystemInversionState.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0005\"\u0004\b\u0006\u0010\u0004¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/global/inversion/SystemInversionState;", TTFFont.UNKNOWN_FONT_NAME, "isInversion", TTFFont.UNKNOWN_FONT_NAME, "(Z)V", "()Z", "setInversion", "onyxsdk-base_release"})
public final class SystemInversionState {
    private boolean a;

    public SystemInversionState(boolean isInversion) {
        this.a = isInversion;
    }

    public SystemInversionState() {
        this(false, 1, null);
    }

    public final boolean isInversion() {
        return this.a;
    }

    public final void setInversion(boolean z) {
        this.a = z;
    }

    public /* synthetic */ SystemInversionState(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }
}
