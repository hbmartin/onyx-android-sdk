package com.onyx.android.sdk.data.note;

import android.graphics.RectF;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/DisplayArgs.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e¨\u0006\u0016"}, d2 = {"Lcom/onyx/android/sdk/data/note/DisplayArgs;", TTFFont.UNKNOWN_FONT_NAME, "()V", "dirtySliceRectF", "Landroid/graphics/RectF;", "getDirtySliceRectF", "()Landroid/graphics/RectF;", "setDirtySliceRectF", "(Landroid/graphics/RectF;)V", "enablePost", TTFFont.UNKNOWN_FONT_NAME, "getEnablePost", "()Z", "setEnablePost", "(Z)V", "enableRegalModeOnce", "getEnableRegalModeOnce", "setEnableRegalModeOnce", "invalidate", "getInvalidate", "setInvalidate", "Companion", "onyxsdk-base_release"})
public final class DisplayArgs {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @Nullable
    private RectF a;
    private boolean b;
    private boolean c = true;
    private boolean d;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/DisplayArgs$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/data/note/DisplayArgs$Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "enableRegalDisplayArgs", "Lcom/onyx/android/sdk/data/note/DisplayArgs;", "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final DisplayArgs enableRegalDisplayArgs() {
            DisplayArgs displayArgs = new DisplayArgs();
            displayArgs.setEnableRegalModeOnce(true);
            return displayArgs;
        }
    }

    @Nullable
    public final RectF getDirtySliceRectF() {
        return this.a;
    }

    public final void setDirtySliceRectF(@Nullable RectF rectF) {
        this.a = rectF;
    }

    public final boolean getEnablePost() {
        return this.b;
    }

    public final void setEnablePost(boolean z) {
        this.b = z;
    }

    public final boolean getInvalidate() {
        return this.c;
    }

    public final void setInvalidate(boolean z) {
        this.c = z;
    }

    public final boolean getEnableRegalModeOnce() {
        return this.d;
    }

    public final void setEnableRegalModeOnce(boolean z) {
        this.d = z;
    }
}
