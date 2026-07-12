// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import android.graphics.RectF;
import com.onyx.android.sdk.data.Size;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Bitmap;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import android.graphics.Bitmap.Config;
import kotlin.Metadata;
import android.util.LruCache;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0015\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0010\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0013J%\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH$¢\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u0013\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u001c\u001a\u00020\u001d¢\u0006\u0002\u0010\u001eJ\u001b\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005¢\u0006\u0002\u0010\u001fJ\u0017\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\u0010\u001a\u00028\u0000H$¢\u0006\u0002\u0010\"J\u001f\u0010#\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010\u001fJ'\u0010#\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0002¢\u0006\u0002\u0010\u0017J\u0017\u0010$\u001a\u00020%2\b\u0010\u0010\u001a\u0004\u0018\u00018\u0000H$¢\u0006\u0002\u0010&J*\u0010'\u001a\u00020%2\b\u0010(\u001a\u0004\u0018\u00010!2\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006)" }, d2 = { "Lcom/onyx/android/sdk/utils/BaseBitmapReferenceLruCache;", "T", "Landroid/util/LruCache;", "", "maxSize", "", "(I)V", "config", "Landroid/graphics/Bitmap.Config;", "getConfig", "()Landroid/graphics/Bitmap.Config;", "setConfig", "(Landroid/graphics/Bitmap.Config;)V", "clear", "", "clearBitmap", "t", "(Ljava/lang/Object;)V", "cloneBitmapReference", "(Ljava/lang/Object;)Ljava/lang/Object;", "create", "width", "height", "(IILandroid/graphics/Bitmap.Config;)Ljava/lang/Object;", "ensureBitmapReference", "rectF", "Landroid/graphics/RectF;", "(Landroid/graphics/RectF;)Ljava/lang/Object;", "size", "Lcom/onyx/android/sdk/data/Size;", "(Lcom/onyx/android/sdk/data/Size;)Ljava/lang/Object;", "(II)Ljava/lang/Object;", "getBitmap", "Landroid/graphics/Bitmap;", "(Ljava/lang/Object;)Landroid/graphics/Bitmap;", "getFreeBitmap", "isValid", "", "(Ljava/lang/Object;)Z", "match", "bitmap", "onyxsdk-base_release" })
public abstract class BaseBitmapReferenceLruCache<T> extends LruCache<String, T>
{
    @NotNull
    private Bitmap.Config a;
    
    public BaseBitmapReferenceLruCache(final int maxSize) {
        super(maxSize);
        this.a = Bitmap.Config.ARGB_8888;
    }
    
    private final T a(final int width, final int height, final Bitmap.Config config) {
        if (this.size() < this.maxSize()) {
            return null;
        }
        final Iterator<Map.Entry<String, T>> iterator;
        if (!(iterator = this.snapshot().entrySet().iterator()).hasNext()) {
            return null;
        }
        final Map.Entry<String, T> entry = iterator.next();
        final String s = entry.getKey();
        final T value = entry.getValue();
        if (!this.isValid(value)) {
            this.remove(s);
            return null;
        }
        if (!this.a(this.getBitmap(value), width, height, config)) {
            return null;
        }
        final T cloneBitmapReference = this.cloneBitmapReference(value);
        this.remove(s);
        this.clearBitmap(cloneBitmapReference);
        return cloneBitmapReference;
    }
    
    private final T a(final int width, final int height) {
        return this.a(width, height, this.a);
    }
    
    private final boolean a(final Bitmap bitmap, final int width, final int height, final Bitmap.Config config) {
        return bitmap != null && !bitmap.isRecycled() && bitmap.isMutable() && bitmap.getWidth() == width && bitmap.getHeight() == height && bitmap.getConfig() == config;
    }
    
    @NotNull
    public final Bitmap.Config getConfig() {
        return this.a;
    }
    
    public final void setConfig(@NotNull final Bitmap.Config value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.a = value;
    }
    
    public final void clear() {
        Debug.d((Class)this.getClass(), "clear", new Object[0]);
        this.evictAll();
    }
    
    public final T ensureBitmapReference(final int width, final int height) {
        T o;
        if (!this.isValid(o = this.a(width, height))) {
            o = this.create(width, height, this.a);
        }
        final T o2 = o;
        Intrinsics.checkNotNull(o2);
        return o2;
    }
    
    public final T ensureBitmapReference(@NotNull final Size size) {
        Intrinsics.checkNotNullParameter((Object)size, "size");
        return this.ensureBitmapReference(size.width, size.height);
    }
    
    public final T ensureBitmapReference(@NotNull final RectF rectF) {
        Intrinsics.checkNotNullParameter((Object)rectF, "rectF");
        return this.ensureBitmapReference((int)rectF.width(), (int)rectF.height());
    }
    
    @Nullable
    protected abstract Bitmap getBitmap(final T p0);
    
    protected abstract boolean isValid(@Nullable final T p0);
    
    protected abstract T create(final int p0, final int p1, @NotNull final Bitmap.Config p2);
    
    protected abstract T cloneBitmapReference(final T p0);
    
    protected abstract void clearBitmap(final T p0);
}
