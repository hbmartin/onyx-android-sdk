// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.extension.BitmapKt;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0008\n\u0002\u0008\u0002\n\u0002\u0010\u0002\n\u0002\u0008\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0008\u0002\n\u0002\u0010\u000e\n\u0002\u0008\u0002\u0018\u00002\u0008\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0008\u001a\u00020\u0002H\u0014J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\u0008\u001a\u00020\u0002H\u0014J \u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u000c\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0008\u001a\u00020\u0002H\u0014J\u0012\u0010\u0011\u001a\u00020\u00122\u0008\u0010\u0008\u001a\u0004\u0018\u00010\u0002H\u0014J\u001a\u0010\u0013\u001a\u00020\u00042\u0008\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0002H\u0014\u00a8\u0006\u0017" }, d2 = { "Lcom/onyx/android/sdk/utils/SimpleBitmapReferenceLruCache;", "Lcom/onyx/android/sdk/utils/BaseBitmapReferenceLruCache;", "Lcom/onyx/android/sdk/utils/SimpleBitmapReference;", "maxSize", "", "(I)V", "clearBitmap", "", "t", "cloneBitmapReference", "create", "width", "height", "config", "Landroid/graphics/Bitmap$Config;", "getBitmap", "Landroid/graphics/Bitmap;", "isValid", "", "sizeOf", "key", "", "value", "onyxsdk-base_release" })
public final class SimpleBitmapReferenceLruCache extends BaseBitmapReferenceLruCache<SimpleBitmapReference>
{
    public SimpleBitmapReferenceLruCache(final int maxSize) {
        super(maxSize);
    }
    
    public int sizeOf(@Nullable final String key, @NotNull final SimpleBitmapReference value) {
        Intrinsics.checkNotNullParameter((Object)value, "value");
        return value.isValid() ? value.getBitmap().getByteCount() : 0;
    }
    
    @Nullable
    @Override
    public Bitmap getBitmap(@NotNull final SimpleBitmapReference t) {
        Intrinsics.checkNotNullParameter((Object)t, "t");
        return t.getBitmap();
    }
    
    @Override
    public boolean isValid(@Nullable final SimpleBitmapReference t) {
        return t != null && t.isValid();
    }
    
    @NotNull
    @Override
    public SimpleBitmapReference create(final int width, final int height, @NotNull final Bitmap.Config config) {
        Intrinsics.checkNotNullParameter((Object)config, "config");
        final SimpleBitmapReference simpleBitmapReference = new SimpleBitmapReference();
        final Bitmap bitmap;
        final Bitmap $this$clearBitmap = bitmap = Bitmap.createBitmap(width, height, config);
        BitmapKt.clearBitmap($this$clearBitmap);
        Intrinsics.checkNotNullExpressionValue((Object)$this$clearBitmap, "bitmap");
        simpleBitmapReference.setBitmap(bitmap);
        return simpleBitmapReference;
    }
    
    @Override
    public void clearBitmap(@NotNull final SimpleBitmapReference t) {
        Intrinsics.checkNotNullParameter((Object)t, "t");
        t.clearBitmap();
    }
    
    @NotNull
    @Override
    public SimpleBitmapReference cloneBitmapReference(@NotNull final SimpleBitmapReference t) {
        Intrinsics.checkNotNullParameter((Object)t, "t");
        return t.cloneBitmapReference();
    }
}
