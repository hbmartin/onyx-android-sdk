// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import android.graphics.Matrix;
import android.graphics.Paint.FontMetrics;
import org.jetbrains.annotations.Nullable;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.RectF;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Rect;
import org.jetbrains.annotations.NotNull;
import android.graphics.Canvas;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002\u001a.\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a.\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a*\u0010\u0010\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014\u001a-\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u0017\u0010\u0019\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u001a¢\u0006\u0002\b\u001b¨\u0006\u001c" }, d2 = { "boundingRect", "Landroid/graphics/Rect;", "Landroid/graphics/Canvas;", "boundingRectF", "Landroid/graphics/RectF;", "drawBitmapFloorLeftTop", "", "bitmap", "Landroid/graphics/Bitmap;", "src", "dst", "paint", "Landroid/graphics/Paint;", "left", "", "top", "drawTextFromCenter", "text", "", "centerX", "", "centerY", "withMatrix", "matrix", "Landroid/graphics/Matrix;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "onyxsdk-base_release" })
public final class CanvasKt
{
    @NotNull
    public static final Rect boundingRect(@NotNull final Canvas $this$boundingRect) {
        Intrinsics.checkNotNullParameter((Object)$this$boundingRect, "<this>");
        return new Rect(0, 0, $this$boundingRect.getWidth(), $this$boundingRect.getHeight());
    }
    
    @NotNull
    public static final RectF boundingRectF(@NotNull final Canvas $this$boundingRectF) {
        Intrinsics.checkNotNullParameter((Object)$this$boundingRectF, "<this>");
        return new RectF(0.0f, 0.0f, (float)$this$boundingRectF.getWidth(), (float)$this$boundingRectF.getHeight());
    }
    
    public static final void drawBitmapFloorLeftTop(@NotNull final Canvas $this$drawBitmapFloorLeftTop, @NotNull final Bitmap bitmap, @NotNull final Rect src, @NotNull final RectF dst, @Nullable final Paint paint) {
        Intrinsics.checkNotNullParameter((Object)$this$drawBitmapFloorLeftTop, "<this>");
        Intrinsics.checkNotNullParameter((Object)bitmap, "bitmap");
        Intrinsics.checkNotNullParameter((Object)src, "src");
        Intrinsics.checkNotNullParameter((Object)dst, "dst");
        final RectF rectF2;
        final RectF rectF = rectF2 = new RectF(dst);
        rectF.offsetTo(FloatKt.floor(rectF.left), FloatKt.floor(rectF2.top));
        $this$drawBitmapFloorLeftTop.drawBitmap(bitmap, src, rectF2, paint);
    }
    
    public static final void drawBitmapFloorLeftTop(@NotNull final Canvas $this$drawBitmapFloorLeftTop, @NotNull final Bitmap bitmap, float left, final float top, @Nullable final Paint paint) {
        final float $this$floor = left;
        Intrinsics.checkNotNullParameter((Object)$this$drawBitmapFloorLeftTop, "<this>");
        Intrinsics.checkNotNullParameter((Object)bitmap, "bitmap");
        final float floor = FloatKt.floor($this$floor);
        left = FloatKt.floor(top);
        $this$drawBitmapFloorLeftTop.drawBitmap(bitmap, floor, left, paint);
    }
    
    public static final void drawTextFromCenter(@NotNull final Canvas $this$drawTextFromCenter, @NotNull final Paint paint, @NotNull final String text, @NotNull final Number centerX, @NotNull final Number centerY) {
        Intrinsics.checkNotNullParameter((Object)$this$drawTextFromCenter, "<this>");
        Intrinsics.checkNotNullParameter((Object)paint, "paint");
        Intrinsics.checkNotNullParameter((Object)text, "text");
        Intrinsics.checkNotNullParameter((Object)centerX, "centerX");
        Intrinsics.checkNotNullParameter((Object)centerY, "centerY");
        final float measureText = paint.measureText(text);
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        final int length = text.length();
        final float floatValue = centerX.floatValue();
        final float n = measureText;
        final float n2 = 2;
        $this$drawTextFromCenter.drawText(text, 0, length, floatValue - n / n2, centerY.floatValue() - (fontMetrics.ascent + fontMetrics.descent) / n2, paint);
    }
    
    public static final void withMatrix(@NotNull final Canvas $this$withMatrix, @NotNull Matrix matrix, @NotNull final Function1<? super Canvas, Unit> block) {
        Intrinsics.checkNotNullParameter((Object)$this$withMatrix, "<this>");
        Intrinsics.checkNotNullParameter((Object)matrix, "matrix");
        Intrinsics.checkNotNullParameter((Object)block, "block");
        int saveCount = $this$withMatrix.save();
        $this$withMatrix.concat(matrix);
        try {
            block.invoke($this$withMatrix);
        }
        finally {
            $this$withMatrix.restoreToCount(saveCount);
        }
    }
}
