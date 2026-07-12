package com.onyx.android.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.onyx.android.sdk.extension.AnyKt;
import com.onyx.android.sdk.extension.BitmapKt;
import java.io.FileInputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ImageRender.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0008\u0002\n\u0002\u0010\u000e\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0002\u0008\u0004\n\u0002\u0010\u0002\n\u0002\u0008\u0005\u0018\u00002\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00082\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\"\u0010\u000c\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u000e\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019J(\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\u00082\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u0018\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u00082\u0006\u0010\u001c\u001a\u00020\nH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004H\u0002J\u001e\u0010 \u001a\u00020\u001e2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004\u00a8\u0006#"}, d2 = {"Lcom/onyx/android/sdk/utils/ImageRender;", "", "()V", "decodeSubImageFromStream", "Landroid/graphics/Bitmap;", "inputStream", "Ljava/io/InputStream;", "scale", "", "displayRectF", "Landroid/graphics/RectF;", "viewport", "decodeSubImageFromStreamImpl", "options", "Landroid/graphics/BitmapFactory$Options;", "regionToDecode", "Landroid/graphics/Rect;", "drawFullImageToTargetBitmap", "", "targetBitmap", "path", "", "displayRect", "drawImage", "renderArgs", "Lcom/onyx/android/sdk/utils/ImageRender$RenderArgs;", "drawSubImageToTargetBitmap", "getOriginRegion", "visibleRect", "renderSubImageToTargetBitmap", "", "regionBitmap", "renderToTargetBitmap", "decodeBitmap", "RenderArgs", "onyxsdk-base_release"})
public final class ImageRender {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ImageRender$RenderArgs.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lcom/onyx/android/sdk/utils/ImageRender$RenderArgs;", TTFFont.UNKNOWN_FONT_NAME, "path", TTFFont.UNKNOWN_FONT_NAME, "viewportBitmap", "Landroid/graphics/Bitmap;", "displayRect", "Landroid/graphics/RectF;", "scale", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/String;Landroid/graphics/Bitmap;Landroid/graphics/RectF;F)V", "getDisplayRect", "()Landroid/graphics/RectF;", "getPath", "()Ljava/lang/String;", "getScale", "()F", "getViewportBitmap", "()Landroid/graphics/Bitmap;", "onyxsdk-base_release"})
    public static final class RenderArgs {

        @NotNull
        private final String a;

        @NotNull
        private final Bitmap b;

        @NotNull
        private final RectF c;
        private final float d;

        public RenderArgs(@NotNull String path, @NotNull Bitmap viewportBitmap, @NotNull RectF displayRect, float scale) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(viewportBitmap, "viewportBitmap");
            Intrinsics.checkNotNullParameter(displayRect, "displayRect");
            this.a = path;
            this.b = viewportBitmap;
            this.c = displayRect;
            this.d = scale;
        }

        @NotNull
        public final String getPath() {
            return this.a;
        }

        @NotNull
        public final Bitmap getViewportBitmap() {
            return this.b;
        }

        @NotNull
        public final RectF getDisplayRect() {
            return this.c;
        }

        public final float getScale() {
            return this.d;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ImageRender$a.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "decoder", "Landroid/graphics/BitmapRegionDecoder;", "invoke"})
    static final class a implements Function1<BitmapRegionDecoder, Bitmap> {
        final /* synthetic */ Rect a;
        final /* synthetic */ BitmapFactory.Options b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Rect $regionToDecode, BitmapFactory.Options $options) {
            this.a = $regionToDecode;
            this.b = $options;
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Bitmap invoke(@NotNull BitmapRegionDecoder decoder) {
            Intrinsics.checkNotNullParameter(decoder, "decoder");
            return decoder.decodeRegion(this.a, this.b);
        }
    }

    private final boolean a(Bitmap targetBitmap, String path, RectF displayRect) {
        try {
            Bitmap bitmapDecodeBitmap = BitmapUtils.decodeBitmap(path, (int) displayRect.width(), (int) displayRect.height());
            if (bitmapDecodeBitmap == null) {
                return true;
            }
            renderToTargetBitmap(displayRect, targetBitmap, bitmapDecodeBitmap);
            return true;
        } catch (Throwable th) {
            Log.w(AnyKt.toSimpleNameString(this), th);
            return false;
        }
    }

    public final boolean drawImage(@NotNull RenderArgs renderArgs) {
        Intrinsics.checkNotNullParameter(renderArgs, "renderArgs");
        String path = renderArgs.getPath();
        Bitmap viewportBitmap = renderArgs.getViewportBitmap();
        RectF displayRect = renderArgs.getDisplayRect();
        return !RectUtils.contains(BitmapKt.boundingRectF(viewportBitmap), displayRect) ? a(viewportBitmap, path, renderArgs.getScale(), displayRect) : a(viewportBitmap, path, displayRect);
    }

    public final void renderToTargetBitmap(@NotNull RectF displayRect, @NotNull Bitmap targetBitmap, @NotNull Bitmap decodeBitmap) {
        Intrinsics.checkNotNullParameter(displayRect, "displayRect");
        Intrinsics.checkNotNullParameter(targetBitmap, "targetBitmap");
        Intrinsics.checkNotNullParameter(decodeBitmap, "decodeBitmap");
        Canvas canvas = new Canvas(targetBitmap);
        Paint paintCreateBitmapPaint = BitmapUtils.createBitmapPaint();
        Intrinsics.checkNotNullExpressionValue(paintCreateBitmapPaint, "createBitmapPaint()");
        Matrix matrix = new Matrix();
        matrix.postScale(displayRect.width() / decodeBitmap.getWidth(), displayRect.height() / decodeBitmap.getHeight());
        matrix.postTranslate((int) displayRect.left, (int) displayRect.top);
        canvas.drawBitmap(decodeBitmap, matrix, paintCreateBitmapPaint);
    }

    private final boolean a(Bitmap targetBitmap, String path, float scale, RectF displayRect) {
        Boolean bool;
        try {
            try (FileInputStream fileInputStream = new FileInputStream(path)) {
                Bitmap bitmapA = a(fileInputStream, scale, displayRect, BitmapKt.boundingRectF(targetBitmap));
                if (bitmapA == null) {
                    bool = null;
                } else {
                    a(targetBitmap, bitmapA);
                    bool = Boolean.TRUE;
                }
                return bool != null && bool.booleanValue();
            }
        } catch (Throwable th3) {
            Log.w(AnyKt.toSimpleNameString(this), th3);
            return false;
        }
    }

    private final void a(Bitmap targetBitmap, Bitmap regionBitmap) {
        Canvas canvas = new Canvas(targetBitmap);
        Paint paintCreateBitmapPaint = BitmapUtils.createBitmapPaint();
        Intrinsics.checkNotNullExpressionValue(paintCreateBitmapPaint, "createBitmapPaint()");
        canvas.drawBitmap(regionBitmap, (Rect) null, new Rect(0, 0, targetBitmap.getWidth(), targetBitmap.getHeight()), paintCreateBitmapPaint);
    }

    private final Rect a(float scale, RectF visibleRect) {
        return new Rect((int) (visibleRect.left / scale), (int) (visibleRect.top / scale), (int) (visibleRect.right / scale), (int) (visibleRect.bottom / scale));
    }

    private final Bitmap a(InputStream inputStream, float scale, RectF displayRectF, RectF viewport) {
        RectF rectF = new RectF(a(scale, displayRectF));
        int i = -((int) rectF.left);
        int i2 = -((int) rectF.top);
        Rect rect = new Rect(i, i2, (int) (i + (viewport.width() / scale)), (int) (i2 + (viewport.height() / scale)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = rect.height();
        options.outWidth = rect.width();
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, (int) viewport.width(), (int) viewport.height());
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;
        Debug.d(AnyKt.toSimpleNameString(this), Intrinsics.stringPlus("regionInOrigin = ", rect), new Object[0]);
        Bitmap bitmapA = a(inputStream, options, rect);
        Debug.d(AnyKt.toSimpleNameString(this), "bitmap = " + (bitmapA == null ? null : Integer.valueOf(bitmapA.getWidth())) + " * " + (bitmapA == null ? null : Integer.valueOf(bitmapA.getHeight())), new Object[0]);
        return bitmapA;
    }

    private final Bitmap a(InputStream inputStream, BitmapFactory.Options options, Rect regionToDecode) {
        try {
            return (Bitmap) BitmapKt.use(BitmapRegionDecoder.newInstance(inputStream, false),
                    new a(regionToDecode, options));
        } catch (java.io.IOException exception) {
            Debug.e(exception);
            return null;
        }
    }
}
