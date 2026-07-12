// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import kotlin.jvm.internal.Intrinsics;
import java.util.LinkedHashMap;
import org.jetbrains.annotations.NotNull;
import com.onyx.android.sdk.data.Size;
import java.util.Map;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0005H\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0005J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0006H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/utils/ImageSizeManager;", "", "()V", "sizeCache", "", "", "Lcom/onyx/android/sdk/data/Size;", "clear", "", "loadBitmapSizeFromCache", "path", "loadImageSize", "saveSizeToCache", "size", "onyxsdk-base_release" })
public final class ImageSizeManager
{
    @NotNull
    private final Map<String, Size> a;
    
    public ImageSizeManager() {
        this.a = new LinkedHashMap<String, Size>();
    }
    
    private final void a(final String path, final Size size) {
        this.a.put(path, size);
    }
    
    private final Size a(final String path) {
        Size size;
        if ((size = this.a.get(path)) == null) {
            size = new Size();
        }
        return size;
    }
    
    public final void clear() {
        this.a.clear();
    }
    
    @NotNull
    public final Size loadImageSize(@NotNull final String path) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        if (!this.a.containsKey(path)) {
            final Size size;
            if (!BitmapUtils.decodeBitmapSizeSupportExif(path, size = new Size())) {
                return size;
            }
            this.a(path, size);
        }
        return this.a(path);
    }
}
