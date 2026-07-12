// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.Paint;
import com.onyx.android.sdk.utils.BitmapUtils;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Bitmap;
import org.jetbrains.annotations.NotNull;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\f\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u0002\u001a\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\n\u001a\u001e\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n¨\u0006\r" }, d2 = { "animationStart", "", "Landroid/graphics/drawable/Drawable;", "animationStop", "scale", "scaleFactor", "", "toBitmap", "Landroid/graphics/Bitmap;", "color", "", "width", "height", "onyxsdk-base_release" })
public final class DrawableKt
{
    private DrawableKt() {
    }

    @Nullable
    public static final Bitmap toBitmap(@NotNull final Drawable $this$toBitmap) {
        Intrinsics.checkNotNullParameter((Object)$this$toBitmap, "<this>");
        return toBitmap($this$toBitmap, 0);
    }
    
    @Nullable
    public static final Bitmap toBitmap(@NotNull final Drawable $this$toBitmap, final int color) {
        Intrinsics.checkNotNullParameter((Object)$this$toBitmap, "<this>");
        int n = $this$toBitmap.getIntrinsicWidth();
        int n2 = $this$toBitmap.getIntrinsicHeight();
        final boolean b;
        if (b = ($this$toBitmap instanceof BitmapDrawable)) {
            final BitmapDrawable bitmapDrawable;
            if ((bitmapDrawable = (BitmapDrawable)$this$toBitmap).getBitmap() != null) {
                final BitmapDrawable bitmapDrawable2 = bitmapDrawable;
                n = bitmapDrawable2.getBitmap().getWidth();
                n2 = bitmapDrawable2.getBitmap().getHeight();
            }
        }
        Bitmap.Config bitmap$Config;
        if ($this$toBitmap.getOpacity() != -1) {
            bitmap$Config = Bitmap.Config.ARGB_8888;
        }
        else {
            bitmap$Config = Bitmap.Config.RGB_565;
        }
        final boolean b2 = b;
        final Bitmap bitmap = Bitmap.createBitmap(n, n2, bitmap$Config);
        final Canvas canvas;
        (canvas = new Canvas(bitmap)).drawColor(color);
        if (b2) {
            final Canvas canvas2 = canvas;
            final Rect rect = new Rect(0, 0, n, n2);
            final Paint bitmapPaint = BitmapUtils.createBitmapPaint();
            final Bitmap bitmap2 = bitmap;
            final Rect rect2 = rect;
            BitmapUtils.safelyDrawBitmap(canvas2, bitmap2, rect2, rect2, bitmapPaint);
        }
        else {
            $this$toBitmap.setBounds(0, 0, n, n2);
            $this$toBitmap.draw(canvas);
        }
        return bitmap;
    }
    
    @NotNull
    public static final Bitmap toBitmap(@NotNull final Drawable $this$toBitmap, int width, int height) {
        Intrinsics.checkNotNullParameter((Object)$this$toBitmap, "<this>");
        if (width <= 0) {
            width = $this$toBitmap.getIntrinsicWidth();
        }
        if (height <= 0) {
            height = $this$toBitmap.getIntrinsicHeight();
        }
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas2;
        final Canvas canvas = canvas2 = new Canvas(bitmap);
        width = canvas.getWidth();
        height = canvas.getHeight();
        $this$toBitmap.setBounds(0, 0, width, height);
        $this$toBitmap.draw(canvas2);
        Intrinsics.checkNotNullExpressionValue((Object)bitmap, "bitmap");
        return bitmap;
    }

    public static /* synthetic */ Bitmap toBitmap$default(final Drawable drawable, int width, int height, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            width = -1;
        }
        if ((n & 0x2) != 0x0) {
            height = -1;
        }
        return toBitmap(drawable, width, height);
    }

    public static final void animationStart(@Nullable final Drawable $this$animationStart) {
        AnimationDrawable animationDrawable;
        if ($this$animationStart instanceof AnimationDrawable) {
            animationDrawable = (AnimationDrawable)$this$animationStart;
        }
        else {
            animationDrawable = null;
        }
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }
    
    public static final void animationStop(@Nullable final Drawable $this$animationStop) {
        AnimationDrawable animationDrawable;
        if ($this$animationStop instanceof AnimationDrawable) {
            animationDrawable = (AnimationDrawable)$this$animationStop;
        }
        else {
            animationDrawable = null;
        }
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }
    
    @NotNull
    public static final Drawable scale(@NotNull final Drawable $this$scale, final float scaleFactor) {
        Intrinsics.checkNotNullParameter((Object)$this$scale, "<this>");
        final LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { $this$scale });
        layerDrawable.setLayerSize(0, (int)(layerDrawable.getIntrinsicWidth() * scaleFactor), (int)(layerDrawable.getIntrinsicHeight() * scaleFactor));
        layerDrawable.setLayerGravity(0, 17);
        return (Drawable)layerDrawable;
    }
}
