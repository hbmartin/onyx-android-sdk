package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/Orientation.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lcom/onyx/android/sdk/data/Orientation;", TTFFont.UNKNOWN_FONT_NAME, "value", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/String;II)V", "getValue", "()I", "isPortrait", TTFFont.UNKNOWN_FONT_NAME, "LANDSCAPE", "PORTRAIT", "Companion", "onyxsdk-base_release"})
public enum Orientation {
    LANDSCAPE(0),
    PORTRAIT(1);


    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int a;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/Orientation$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/data/Orientation$Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "isPortrait", TTFFont.UNKNOWN_FONT_NAME, "orientation", TTFFont.UNKNOWN_FONT_NAME, "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean isPortrait(int orientation) {
            return Orientation.PORTRAIT.getValue() == orientation;
        }
    }

    Orientation(int value) {
        this.a = value;
    }

    public final int getValue() {
        return this.a;
    }

    public final boolean isPortrait() {
        return this == PORTRAIT;
    }
}
