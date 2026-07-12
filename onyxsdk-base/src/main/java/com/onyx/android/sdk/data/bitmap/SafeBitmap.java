package com.onyx.android.sdk.data.bitmap;

import android.graphics.Bitmap;
import com.onyx.android.sdk.extension.BitmapKt;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/bitmap/SafeBitmap.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0000J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0000J\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lcom/onyx/android/sdk/data/bitmap/SafeBitmap;", TTFFont.UNKNOWN_FONT_NAME, "()V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "copyBitmap", "equal", TTFFont.UNKNOWN_FONT_NAME, "another", "isValid", "removeBitmap", TTFFont.UNKNOWN_FONT_NAME, "Companion", "onyxsdk-base_release"})
public final class SafeBitmap {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private Bitmap a;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/bitmap/SafeBitmap.Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/data/bitmap/SafeBitmap.Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "create", "Lcom/onyx/android/sdk/data/bitmap/SafeBitmap;", "bitmap", "Landroid/graphics/Bitmap;", "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final SafeBitmap create(@Nullable Bitmap bitmap) {
            SafeBitmap safeBitmap = new SafeBitmap();
            safeBitmap.setBitmap(bitmap);
            return safeBitmap;
        }
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.a;
    }

    public final void setBitmap(@Nullable Bitmap bitmap) {
        this.a = bitmap;
    }

    public final boolean isValid() {
        return BitmapKt.isValid(this.a);
    }

    @NotNull
    public final SafeBitmap copyBitmap() {
        SafeBitmap safeBitmap = new SafeBitmap();
        safeBitmap.a = BitmapKt.copy(this.a);
        return safeBitmap;
    }

    public final void removeBitmap() {
        this.a = null;
    }

    public final boolean equal(@NotNull SafeBitmap another) {
        Intrinsics.checkNotNullParameter(another, "another");
        return Intrinsics.areEqual(this.a, another.a);
    }
}
