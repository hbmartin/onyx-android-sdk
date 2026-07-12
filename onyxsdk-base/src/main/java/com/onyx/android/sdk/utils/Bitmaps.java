// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import android.graphics.BitmapFactory;
import kotlin.math.MathKt;
import com.onyx.android.sdk.extension.BitmapKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import androidx.core.graphics.drawable.DrawableCompat;
import android.os.Build;
import androidx.core.content.ContextCompat;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.RectF;
import android.content.Context;
import com.onyx.android.sdk.data.Size;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ.\u0010\n\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011J@\u0010\n\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0004J\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u0006J \u0010\u001d\u001a\u00020\u001e*\u0004\u0018\u00010\u00062\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u001e0 R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!" }, d2 = { "Lcom/onyx/android/sdk/utils/Bitmaps;", "", "()V", "ENSURE_BITMAP_NOT_NULL_SIZE", "Lcom/onyx/android/sdk/data/Size;", "createBitmapOrNull", "Landroid/graphics/Bitmap;", "rectF", "Landroid/graphics/RectF;", "ensureBitmapNotNull", "getBitmapFromVectorDrawableBySize", "context", "Landroid/content/Context;", "drawableId", "", "displayRectF", "colorFilter", "Landroid/graphics/ColorFilter;", "originRectF", "matrix", "Landroid/graphics/Matrix;", "getPathThumbSize", "path", "", "defaultThumbSize", "loadBitmapFromFile", "options", "Landroid/graphics/BitmapFactory$Options;", "copy", "ifValid", "", "block", "Lkotlin/Function1;", "onyxsdk-base_release" })
public final class Bitmaps
{
    @NotNull
    public static final Bitmaps INSTANCE;
    @NotNull
    private static final Size a;
    
    private Bitmaps() {
    }
    
    static {
        INSTANCE = new Bitmaps();
        a = new Size(10, 10);
    }
    
    @Nullable
    public final Bitmap getBitmapFromVectorDrawableBySize(@Nullable final Context context, int drawableId, @NotNull final RectF displayRectF, @Nullable final ColorFilter colorFilter) {
        Intrinsics.checkNotNullParameter((Object)displayRectF, "displayRectF");
        if (drawableId <= 0) {
            return null;
        }
        final int n = drawableId;
        Bitmap bitmap = null;
        try {
            Intrinsics.checkNotNull((Object)context);
            Drawable drawable = ContextCompat.getDrawable(context, n);
            try {
                if (Build.VERSION.SDK_INT < 21) {
                    final Drawable drawable2 = drawable;
                    Intrinsics.checkNotNull((Object)drawable2);
                    drawable = DrawableCompat.wrap(drawable2).mutate();
                }
                if (drawable == null) {
                    return null;
                }
                final Drawable drawable3 = drawable;
                final int n2 = (int)displayRectF.width();
                try {
                    final int n3 = (int)displayRectF.height();
                    try {
                        bitmap = Bitmap.createBitmap(n2, n3, Bitmap.Config.ARGB_8888);
                        try {
                            final Canvas canvas = new Canvas(bitmap);
                            drawable3.setColorFilter(colorFilter);
                            final int n4 = 0;
                            drawableId = 0;
                            drawable3.setBounds(n4, drawableId, (int)displayRectF.width(), (int)displayRectF.height());
                            drawable3.draw(canvas);
                        }
                        catch (final Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    catch (final Exception ex2) {}
                }
                catch (final Exception ex3) {}
            }
            catch (final Exception ex4) {}
        }
        catch (final Exception ex5) {}
        return bitmap;
    }

    public static /* synthetic */ Bitmap getBitmapFromVectorDrawableBySize$default(final Bitmaps bitmaps, final Context context, final int drawableId, final RectF displayRectF, ColorFilter colorFilter, final int i, final Object obj) {
        if ((i & 8) != 0) {
            colorFilter = null;
        }
        return bitmaps.getBitmapFromVectorDrawableBySize(context, drawableId, displayRectF, colorFilter);
    }

    @Nullable
    public final Bitmap getBitmapFromVectorDrawableBySize(@Nullable final Context context, int drawableId, @NotNull final RectF originRectF, @NotNull final RectF displayRectF, @Nullable final Matrix matrix, @Nullable final ColorFilter colorFilter) {
        Intrinsics.checkNotNullParameter((Object)originRectF, "originRectF");
        Intrinsics.checkNotNullParameter((Object)displayRectF, "displayRectF");
        if (drawableId <= 0) {
            return null;
        }
        final int n = drawableId;
        Bitmap bitmap = null;
        try {
            Intrinsics.checkNotNull((Object)context);
            Drawable drawable = ContextCompat.getDrawable(context, n);
            try {
                if (Build.VERSION.SDK_INT < 21) {
                    final Drawable drawable2 = drawable;
                    Intrinsics.checkNotNull((Object)drawable2);
                    drawable = DrawableCompat.wrap(drawable2).mutate();
                }
                if (drawable == null) {
                    return null;
                }
                final Drawable drawable3 = drawable;
                final int n2 = (int)displayRectF.width();
                try {
                    final int n3 = (int)displayRectF.height();
                    try {
                        bitmap = Bitmap.createBitmap(n2, n3, Bitmap.Config.ARGB_8888);
                        try {
                            final Canvas canvas = new android.graphics.Canvas();
                            final Canvas canvas2 = canvas;
                            new Canvas(bitmap);
                            canvas.setMatrix(matrix);
                            drawable3.setColorFilter(colorFilter);
                            final int n4 = 0;
                            drawableId = 0;
                            drawable3.setBounds(n4, drawableId, (int)originRectF.width(), (int)originRectF.height());
                            drawable3.draw(canvas2);
                        }
                        catch (final Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    catch (final Exception ex2) {}
                }
                catch (final Exception ex3) {}
            }
            catch (final Exception ex4) {}
        }
        catch (final Exception ex5) {}
        return bitmap;
    }

    public static /* synthetic */ Bitmap getBitmapFromVectorDrawableBySize$default(final Bitmaps bitmaps, final Context context, final int drawableId, final RectF originRectF, final RectF displayRectF, final Matrix matrix, ColorFilter colorFilter, final int i, final Object obj) {
        if ((i & 32) != 0) {
            colorFilter = null;
        }
        return bitmaps.getBitmapFromVectorDrawableBySize(context, drawableId, originRectF, displayRectF, matrix, colorFilter);
    }

    @Nullable
    public final Bitmap createBitmapOrNull(@NotNull final RectF rectF) {
        Intrinsics.checkNotNullParameter((Object)rectF, "rectF");
        if (rectF.isEmpty()) {
            return null;
        }
        return Bitmap.createBitmap((int)rectF.width(), (int)rectF.height(), Bitmap.Config.ARGB_8888);
    }
    
    @NotNull
    public final Size getPathThumbSize(@NotNull final String path, @NotNull final Size defaultThumbSize) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        Intrinsics.checkNotNullParameter((Object)defaultThumbSize, "defaultThumbSize");
        final Size size;
        if (!BitmapUtils.decodeBitmapSizeSupportExif(path, size = new Size())) {
            return defaultThumbSize;
        }
        final Boolean vertical = size.isVertical();
        Intrinsics.checkNotNullExpressionValue((Object)vertical, "isVertical");
        int n;
        int n2;
        if (vertical) {
            n = defaultThumbSize.width;
            n2 = defaultThumbSize.height;
        }
        else {
            n = defaultThumbSize.height;
            n2 = defaultThumbSize.width;
        }
        final float max = Math.max(size.width / (float)n, size.height / (float)n2);
        return new Size((int)(size.width / max), (int)(size.height / max));
    }
    
    public final void ifValid(@Nullable final Bitmap $this$ifValid, @NotNull final Function1<? super Bitmap, Unit> block) {
        Intrinsics.checkNotNullParameter((Object)block, "block");
        if (BitmapKt.isValid($this$ifValid)) {
            block.invoke($this$ifValid);
        }
    }
    
    @Nullable
    public final Bitmap copy(@Nullable final Bitmap $this$copy) {
        if (!BitmapKt.isValid($this$copy)) {
            return null;
        }
        return Bitmap.createBitmap($this$copy);
    }
    
    @NotNull
    public final Bitmap ensureBitmapNotNull(@NotNull final RectF rectF) {
        Intrinsics.checkNotNullParameter((Object)rectF, "rectF");
        float width = rectF.width();
        float height = rectF.height();
        if (rectF.isEmpty() || (Float.isInfinite(width) || Float.isNaN(width)) || Float.isInfinite(height) || Float.isNaN(height)) {
            final Size a = Bitmaps.a;
            width = (float)a.width;
            height = (float)a.height;
        }
        final Bitmap bitmap = Bitmap.createBitmap(MathKt.roundToInt(width), MathKt.roundToInt(height), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue((Object)bitmap, "createBitmap(width.round\u2026 Bitmap.Config.ARGB_8888)");
        return bitmap;
    }
    
    @Nullable
    public final Bitmap loadBitmapFromFile(@Nullable final String path, @NotNull final BitmapFactory.Options options) {
        Intrinsics.checkNotNullParameter((Object)options, "options");
        if (!FileUtils.fileExist(path)) {
            return null;
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static /* synthetic */ Bitmap loadBitmapFromFile$default(final Bitmaps bitmaps, final String path, BitmapFactory.Options options, final int i, final Object obj) {
        if ((i & 2) != 0) {
            options = new BitmapFactory.Options();
        }
        return bitmaps.loadBitmapFromFile(path, options);
    }
}
