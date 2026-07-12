// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import kotlin.Unit;
import com.onyx.android.sdk.extension.BitmapKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import android.graphics.Bitmap;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\r\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\f\u001a\u00020\u0000J\u0006\u0010\r\u001a\u00020\u000eR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0010" }, d2 = { "Lcom/onyx/android/sdk/utils/SimpleBitmapReference;", "", "()V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "clearBitmap", "", "()Lkotlin/Unit;", "cloneBitmapReference", "isValid", "", "Companion", "onyxsdk-base_release" })
public final class SimpleBitmapReference
{
    @NotNull
    public static final Companion Companion;
    public Bitmap bitmap;
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final Bitmap getBitmap() {
        final Bitmap bitmap;
        if ((bitmap = this.bitmap) != null) {
            return bitmap;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bitmap");
        return null;
    }
    
    public final void setBitmap(@NotNull final Bitmap value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.bitmap = value;
    }
    
    public final boolean isValid() {
        return BitmapKt.isValid(this.getBitmap());
    }
    
    @NotNull
    public final SimpleBitmapReference cloneBitmapReference() {
        final SimpleBitmapReference simpleBitmapReference = new SimpleBitmapReference();
        simpleBitmapReference.setBitmap(this.getBitmap());
        return simpleBitmapReference;
    }
    
    @Nullable
    public final Unit clearBitmap() {
        return BitmapKt.clearBitmap(this.getBitmap());
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/utils/SimpleBitmapReference$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/utils/SimpleBitmapReference;", "bitmap", "Landroid/graphics/Bitmap;", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        private Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @NotNull
        public final SimpleBitmapReference create(@NotNull final Bitmap bitmap) {
            Intrinsics.checkNotNullParameter((Object)bitmap, "bitmap");
            final SimpleBitmapReference simpleBitmapReference = new SimpleBitmapReference();
            simpleBitmapReference.setBitmap(bitmap);
            return simpleBitmapReference;
        }
    }
}
