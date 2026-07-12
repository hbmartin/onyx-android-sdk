package com.onyx.android.sdk.extension;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.util.Size;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.utils.BitmapUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.TTFFont;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/BitmapKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001e\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a)\u0010\u0004\u001a\u00020\u0005*\u00020\u00052\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000\u001a\f\u0010\u000b\u001a\u00020\f*\u0004\u0018\u00010\u0005\u001a\f\u0010\r\u001a\u00020\u000e*\u0004\u0018\u00010\u0005\u001a\u001a\u0010\u000f\u001a\u00020\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010\u001a\u0016\u0010\u0013\u001a\u00020\t*\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u0010\u001a\u0013\u0010\u0015\u001a\u0004\u0018\u00010\t*\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0016\u001a\u000e\u0010\u0017\u001a\u0004\u0018\u00010\u0005*\u0004\u0018\u00010\u0005\u001a\u0016\u0010\u0018\u001a\u00020\u0019*\u0004\u0018\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u0005\u001a\u0012\u0010\u001b\u001a\n \u001c*\u0004\u0018\u00010\u00050\u0005*\u00020\u000e\u001a\u001a\u0010\u001d\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010\u001a\"\u0010\u001d\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0019\u001a4\u0010!\u001a\u00020\t*\u0004\u0018\u00010\u00052\b\u0010\"\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u00102\b\b\u0002\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020&\u001a\u0016\u0010'\u001a\u0004\u0018\u00010(*\u00020\u00052\b\b\u0002\u0010)\u001a\u00020\u0010\u001a\u0012\u0010*\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0010\u001a\n\u0010+\u001a\u00020\u000e*\u00020\u0005\u001a\n\u0010,\u001a\u00020\u0001*\u00020\u0005\u001a\f\u0010-\u001a\u0004\u0018\u00010\u0005*\u00020\u0005\u001a\u001d\u0010.\u001a\u00020\u0019*\u0004\u0018\u00010\u0005\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001c\u0010/\u001a\u00020\u0019*\u0004\u0018\u00010\u00052\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u0010\u001a\u001d\u00102\u001a\u00020\u0019*\u0004\u0018\u00010\u0005\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a\f\u00103\u001a\u00020\t*\u0004\u0018\u00010\u0005\u001a\u0012\u00104\u001a\u00020\u0005*\u00020\u00052\u0006\u00105\u001a\u00020\u0010\u001a\u0012\u00106\u001a\u00020\u0005*\u00020\u00052\u0006\u00107\u001a\u00020&\u001a\"\u00106\u001a\u00020\u0005*\u00020\u00052\u0006\u00108\u001a\u00020\u00102\u0006\u00109\u001a\u00020\u00102\u0006\u00107\u001a\u00020&\u001a$\u0010:\u001a\u0004\u0018\u00010\u0005*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010;\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\u0010\u001a\f\u0010=\u001a\u00020\u0010*\u0004\u0018\u00010\u0005\u001a\u0014\u0010>\u001a\u00020\u0019*\u00020\u00052\b\u0010?\u001a\u0004\u0018\u00010(\u001a*\u0010@\u001a\u00020\t*\u0004\u0018\u00010\u00052\b\u0010A\u001a\u0004\u0018\u00010\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00052\b\u0010B\u001a\u0004\u0018\u00010\f\u001a\u0012\u0010C\u001a\u00020\t*\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005\u001a\u0012\u0010D\u001a\u00020\u0005*\u00020\u00052\u0006\u00108\u001a\u00020\u0010\u001a\f\u0010E\u001a\u0004\u0018\u00010\u0005*\u00020\u0005\u001a \u0010F\u001a\u0004\u0018\u00010G*\u00020\u00052\b\b\u0002\u0010H\u001a\u00020I2\b\b\u0002\u0010)\u001a\u00020\u0010\u001a\n\u0010J\u001a\u00020K*\u00020\u0005\u001a-\u0010L\u001a\u0004\u0018\u0001HM\"\u0004\b\u0000\u0010M*\u0004\u0018\u00010N2\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020N\u0012\u0004\u0012\u0002HM0\u0007¢\u0006\u0002\u0010O\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006P"}, d2 = {"BITMAP_SAFE_SIZE", "Landroid/util/Size;", "getBITMAP_SAFE_SIZE", "()Landroid/util/Size;", "applyCanvas", "Landroid/graphics/Bitmap;", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", TTFFont.UNKNOWN_FONT_NAME, "Lkotlin/ExtensionFunctionType;", "boundingRect", "Landroid/graphics/Rect;", "boundingRectF", "Landroid/graphics/RectF;", "calculateSampleSize", TTFFont.UNKNOWN_FONT_NAME, "defaultValue", "limitValue", "clear", "color", "clearBitmap", "(Landroid/graphics/Bitmap;)Lkotlin/Unit;", "copy", "copyTo", TTFFont.UNKNOWN_FONT_NAME, "dst", "createBitmap", "kotlin.jvm.PlatformType", "createScaledBitmap", "newWidth", "newHeight", "isWhiteBG", "drawRect", "rect", "paintStyle", "Landroid/graphics/Paint$Style;", "strokeWidth", TTFFont.UNKNOWN_FONT_NAME, "encodeToBase64", TTFFont.UNKNOWN_FONT_NAME, "quality", "fillBackgroundColor", "getRectF", "getSize", "horizontalFlip", "isRecycled", "isSizeChanged", "width", "height", "isValid", "recycle", "rotate", "degrees", "roundCorner", "radius", "targetWidth", "targetHeight", "roundedCorner", "cornerPix", "borderPix", "safelyByteCount", "save", "path", "scaleBitmap", "srcRegion", "dstRegion", "scaleToFitCenter", "scaleToWidthRatioBitmap", "toBlackWhiteBitmap", "toBytes", TTFFont.UNKNOWN_FONT_NAME, "format", "Landroid/graphics/Bitmap$CompressFormat;", "toPixels", TTFFont.UNKNOWN_FONT_NAME, "use", "R", "Landroid/graphics/BitmapRegionDecoder;", "(Landroid/graphics/BitmapRegionDecoder;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onyxsdk-base_release"})
public final class BitmapKt {
    private BitmapKt() {
    }


    @NotNull
    private static final Size a = new Size(5000, 5000);

    @NotNull
    public static final Size getBITMAP_SAFE_SIZE() {
        return a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.Closeable, java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.graphics.Bitmap] */
    public static final boolean save(@NotNull Bitmap bitmap, @Nullable String path) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        try (FileOutputStream output = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            return true;
        } catch (Exception failure) {
            failure.printStackTrace();
            return false;
        }
    }

    public static final boolean isValid(@Nullable Bitmap $this$isValid) {
        return $this$isValid != null && !$this$isValid.isRecycled() && $this$isValid.getWidth() > 0 && $this$isValid.getHeight() > 0;
    }

    public static final boolean isSizeChanged(@Nullable Bitmap $this$isSizeChanged, int width, int height) {
        return (($this$isSizeChanged != null && $this$isSizeChanged.getWidth() == width) && $this$isSizeChanged.getHeight() == height) ? false : true;
    }

    public static final boolean isRecycled(@Nullable Bitmap $this$isRecycled) {
        if ($this$isRecycled == null) {
            return true;
        }
        return $this$isRecycled.isRecycled();
    }

    public static final void clear(@Nullable Bitmap $this$clear, int color) {
        if ($this$clear == null) {
            return;
        }
        $this$clear.eraseColor(color);
    }

    public static /* synthetic */ void clear$default(Bitmap bitmap, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        clear(bitmap, i);
    }

    public static final void recycle(@Nullable Bitmap $this$recycle) {
        if (isRecycled($this$recycle)) {
            return;
        }
        $this$recycle.recycle();
    }

    public static final void scaleToFitCenter(@NotNull Bitmap $this$scaleToFitCenter, @NotNull Bitmap dst) {
        Intrinsics.checkNotNullParameter($this$scaleToFitCenter, "<this>");
        Intrinsics.checkNotNullParameter(dst, "dst");
        Rect rect = new Rect(0, 0, $this$scaleToFitCenter.getWidth(), $this$scaleToFitCenter.getHeight());
        scaleBitmap($this$scaleToFitCenter, rect, dst, BitmapUtils.getScaleFitCenterRect(rect, new Rect(0, 0, dst.getWidth(), dst.getHeight())));
    }

    public static final void scaleBitmap(@Nullable Bitmap $this$scaleBitmap, @Nullable Rect srcRegion, @Nullable Bitmap dst, @Nullable Rect dstRegion) {
        if (isRecycled($this$scaleBitmap) || isRecycled(dst)) {
            return;
        }
        Canvas canvas = new Canvas(dst);
        BitmapUtils.paint.setFilterBitmap(true);
        Intrinsics.checkNotNull(dstRegion);
        canvas.drawBitmap($this$scaleBitmap, srcRegion, dstRegion, BitmapUtils.paint);
    }

    @NotNull
    public static final Bitmap scaleToWidthRatioBitmap(@NotNull Bitmap $this$scaleToWidthRatioBitmap, int targetWidth) {
        Intrinsics.checkNotNullParameter($this$scaleToWidthRatioBitmap, "<this>");
        if (targetWidth > 0 && BitmapUtils.isValid($this$scaleToWidthRatioBitmap)) {
            $this$scaleToWidthRatioBitmap = createScaledBitmap($this$scaleToWidthRatioBitmap, targetWidth, ($this$scaleToWidthRatioBitmap.getHeight() * targetWidth) / $this$scaleToWidthRatioBitmap.getWidth());
        }
        return $this$scaleToWidthRatioBitmap;
    }

    @NotNull
    public static final Bitmap createScaledBitmap(@NotNull Bitmap $this$createScaledBitmap, int newWidth, int newHeight) {
        Intrinsics.checkNotNullParameter($this$createScaledBitmap, "<this>");
        return createScaledBitmap($this$createScaledBitmap, newWidth, newHeight, false);
    }

    @Nullable
    public static final byte[] toBytes(@NotNull Bitmap $this$toBytes, @NotNull Bitmap.CompressFormat format, int quality) {
        Intrinsics.checkNotNullParameter($this$toBytes, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            $this$toBytes.compress(format, quality, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException failure) {
            failure.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ byte[] toBytes$default(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            compressFormat = Bitmap.CompressFormat.PNG;
        }
        if ((i2 & 2) != 0) {
            i = 100;
        }
        return toBytes(bitmap, compressFormat, i);
    }

    @Nullable
    public static final Bitmap toBlackWhiteBitmap(@NotNull Bitmap $this$toBlackWhiteBitmap) {
        Intrinsics.checkNotNullParameter($this$toBlackWhiteBitmap, "<this>");
        int width = $this$toBlackWhiteBitmap.getWidth();
        int height = $this$toBlackWhiteBitmap.getHeight();
        int[] pixels = toPixels($this$toBlackWhiteBitmap);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= height) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                bitmapCreateBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                return ThumbnailUtils.extractThumbnail(bitmapCreateBitmap, width, height);
            }
            for (int i5 = 0; i5 < width; i5++) {
                    int i6 = (width * i2) + i5;
                    int i7 = pixels[i6];
                    int i8 = (int) ((((double) ((i7 & 16711680) >> 16)) * 0.3d) + (((double) ((i7 & 65280) >> 8)) * 0.59d) + (((double) (i7 & 255)) * 0.11d));
                    pixels[i6] = (-16777216) | (i8 << 16) | (i8 << 8) | i8;
            }
            i = i2 + 1;
        }
    }

    @NotNull
    public static final Bitmap rotate(@NotNull Bitmap $this$rotate, int degrees) {
        Intrinsics.checkNotNullParameter($this$rotate, "<this>");
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap($this$rotate, 0, 0, $this$rotate.getWidth(), $this$rotate.getHeight(), matrix, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "Matrix().apply {\n       …, height, it, true)\n    }");
        return bitmapCreateBitmap;
    }

    @Nullable
    public static final Bitmap horizontalFlip(@NotNull Bitmap $this$horizontalFlip) {
        Intrinsics.checkNotNullParameter($this$horizontalFlip, "<this>");
        Matrix matrix = new Matrix();
        matrix.postScale(-1.0f, 1.0f);
        return Bitmap.createBitmap($this$horizontalFlip, 0, 0, $this$horizontalFlip.getWidth(), $this$horizontalFlip.getHeight(), matrix, true);
    }

    @NotNull
    public static final Bitmap roundCorner(@NotNull Bitmap $this$roundCorner, float radius) {
        Intrinsics.checkNotNullParameter($this$roundCorner, "<this>");
        return roundCorner($this$roundCorner, $this$roundCorner.getWidth(), $this$roundCorner.getHeight(), radius);
    }

    @Nullable
    public static final Bitmap roundedCorner(@NotNull Bitmap $this$roundedCorner, int color, int cornerPix, int borderPix) {
        Intrinsics.checkNotNullParameter($this$roundedCorner, "<this>");
        int i = borderPix * 2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap($this$roundedCorner.getWidth() + i, $this$roundedCorner.getHeight() + i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawColor(-1);
        float f = borderPix;
        canvas.drawBitmap($this$roundedCorner, f, f, (Paint) null);
        Paint paint = new Paint(1);
        paint.setStrokeWidth(f);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        float f2 = cornerPix;
        canvas.drawRoundRect(boundingRectF(bitmapCreateBitmap), f2, f2, paint);
        return bitmapCreateBitmap;
    }

    public static final boolean copyTo(@Nullable Bitmap $this$copyTo, @Nullable Bitmap dst) {
        if (!isValid($this$copyTo) || !isValid(dst)) {
            return false;
        }
        dst.eraseColor(0);
        new Canvas(dst).drawBitmap($this$copyTo, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, (Paint) null);
        return true;
    }

    @Nullable
    public static final Bitmap copy(@Nullable Bitmap $this$copy) {
        if (isValid($this$copy)) {
            return Bitmap.createBitmap($this$copy);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.ByteArrayOutputStream, java.io.Closeable, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.graphics.Bitmap] */
    @Nullable
    public static final String encodeToBase64(@NotNull Bitmap bitmap, int quality) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, output);
            output.flush();
            return Base64.encodeToString(output.toByteArray(), Base64.NO_WRAP);
        } catch (IOException failure) {
            failure.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ String encodeToBase64$default(Bitmap bitmap, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 100;
        }
        return encodeToBase64(bitmap, i);
    }

    @NotNull
    public static final int[] toPixels(@NotNull Bitmap $this$toPixels) {
        Intrinsics.checkNotNullParameter($this$toPixels, "<this>");
        int[] iArr = new int[$this$toPixels.getWidth() * $this$toPixels.getHeight()];
        $this$toPixels.getPixels(iArr, 0, $this$toPixels.getWidth(), 0, 0, $this$toPixels.getWidth(), $this$toPixels.getHeight());
        return iArr;
    }

    @NotNull
    public static final Bitmap fillBackgroundColor(@NotNull Bitmap $this$fillBackgroundColor, int color) {
        Intrinsics.checkNotNullParameter($this$fillBackgroundColor, "<this>");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap($this$fillBackgroundColor.getWidth(), $this$fillBackgroundColor.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawColor(color);
        canvas.drawBitmap($this$fillBackgroundColor, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, (Paint) null);
        canvas.save();
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "result");
        return bitmapCreateBitmap;
    }

    public static final int calculateSampleSize(@NotNull Bitmap $this$calculateSampleSize, int defaultValue, int limitValue) {
        Intrinsics.checkNotNullParameter($this$calculateSampleSize, "<this>");
        int maxImageSize = BitmapUtils.getMaxImageSize(defaultValue, limitValue);
        int i = 1;
        while (true) {
            int i2 = i;
            if ($this$calculateSampleSize.getHeight() / i2 <= maxImageSize && $this$calculateSampleSize.getWidth() / i2 <= maxImageSize) {
                return i2;
            }
            i = i2 << 1;
        }
    }

    public static final void drawRect(@Nullable Bitmap $this$drawRect, @Nullable RectF rect, int color, @NotNull Paint.Style paintStyle, float strokeWidth) {
        Intrinsics.checkNotNullParameter(paintStyle, "paintStyle");
        if ($this$drawRect == null || rect == null) {
            return;
        }
        Canvas canvas = new Canvas($this$drawRect);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(paintStyle);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawRect(rect, paint);
    }

    public static /* synthetic */ void drawRect$default(Bitmap bitmap, RectF rectF, int i, Paint.Style style, float f, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -16777216;
        }
        if ((i2 & 4) != 0) {
            style = Paint.Style.STROKE;
        }
        if ((i2 & 8) != 0) {
            f = 3.0f;
        }
        drawRect(bitmap, rectF, i, style, f);
    }

    @Nullable
    public static final <R> R use(@Nullable BitmapRegionDecoder bitmapRegionDecoder, @NotNull Function1<? super BitmapRegionDecoder, ? extends R> function1) {
        Object objInvoke;
        Intrinsics.checkNotNullParameter(function1, "block");
        if (bitmapRegionDecoder == null) {
            return null;
        }
        try {
            objInvoke = function1.invoke(bitmapRegionDecoder);
            bitmapRegionDecoder.recycle();
        } catch (Throwable unused) {
            objInvoke = null;
            bitmapRegionDecoder.recycle();
        }
        return (R) objInvoke;
    }

    @NotNull
    public static final Bitmap applyCanvas(@NotNull Bitmap $this$applyCanvas, @NotNull Function1<? super Canvas, Unit> function1) {
        Intrinsics.checkNotNullParameter($this$applyCanvas, "<this>");
        Intrinsics.checkNotNullParameter(function1, "block");
        function1.invoke(new Canvas($this$applyCanvas));
        return $this$applyCanvas;
    }

    @NotNull
    public static final RectF boundingRectF(@Nullable Bitmap $this$boundingRectF) {
        return $this$boundingRectF == null ? new RectF() :
                new RectF(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL,
                        $this$boundingRectF.getWidth(), $this$boundingRectF.getHeight());
    }

    @NotNull
    public static final Rect boundingRect(@Nullable Bitmap $this$boundingRect) {
        return $this$boundingRect == null ? new Rect() :
                new Rect(0, 0, $this$boundingRect.getWidth(), $this$boundingRect.getHeight());
    }

    public static final Bitmap createBitmap(@NotNull RectF $this$createBitmap) {
        Intrinsics.checkNotNullParameter($this$createBitmap, "<this>");
        return Bitmap.createBitmap((int) $this$createBitmap.width(), (int) $this$createBitmap.height(), Bitmap.Config.ARGB_8888);
    }

    public static final int safelyByteCount(@Nullable Bitmap $this$safelyByteCount) {
        if ($this$safelyByteCount == null) {
            return 0;
        }
        return $this$safelyByteCount.getByteCount();
    }

    @Nullable
    public static final Unit clearBitmap(@Nullable Bitmap $this$clearBitmap) {
        if ($this$clearBitmap == null) {
            return null;
        }
        $this$clearBitmap.eraseColor(-1);
        return Unit.INSTANCE;
    }

    @NotNull
    public static final Size getSize(@NotNull Bitmap $this$getSize) {
        Intrinsics.checkNotNullParameter($this$getSize, "<this>");
        return new Size($this$getSize.getWidth(), $this$getSize.getHeight());
    }

    @NotNull
    public static final RectF getRectF(@NotNull Bitmap $this$getRectF) {
        Intrinsics.checkNotNullParameter($this$getRectF, "<this>");
        return SizeKt.toRectF(getSize($this$getRectF));
    }

    @NotNull
    public static final Bitmap createScaledBitmap(@NotNull Bitmap $this$createScaledBitmap, int newWidth, int newHeight, boolean isWhiteBG) {
        Intrinsics.checkNotNullParameter($this$createScaledBitmap, "<this>");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        if (!BitmapUtils.isValid($this$createScaledBitmap)) {
            Debug.e(new IllegalArgumentException("invalid bitmap"));
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "scaledBitmap");
            return bitmapCreateBitmap;
        }
        if (isWhiteBG) {
            bitmapCreateBitmap.eraseColor(-1);
        }
        float f = newWidth;
        float width = f / $this$createScaledBitmap.getWidth();
        float f2 = newHeight;
        float height = f2 / $this$createScaledBitmap.getHeight();
        float f3 = f / 2.0f;
        float f4 = f2 / 2.0f;
        Matrix matrix = new Matrix();
        matrix.setScale(width, height, f3, f4);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.setMatrix(matrix);
        canvas.drawBitmap($this$createScaledBitmap, f3 - ($this$createScaledBitmap.getWidth() / 2), f4 - ($this$createScaledBitmap.getHeight() / 2), new Paint(2));
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "scaledBitmap");
        return bitmapCreateBitmap;
    }

    @NotNull
    public static final Bitmap roundCorner(@NotNull Bitmap $this$roundCorner, int targetWidth, int targetHeight, float radius) {
        Intrinsics.checkNotNullParameter($this$roundCorner, "<this>");
        if ($this$roundCorner.getWidth() != targetWidth || $this$roundCorner.getHeight() != targetHeight) {
            $this$roundCorner = createScaledBitmap($this$roundCorner, targetWidth, targetHeight);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, targetWidth, targetHeight);
        canvas.drawRoundRect(new RectF(rect), radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap($this$roundCorner, rect, rect, paint);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "roundCornerBitmap");
        return bitmapCreateBitmap;
    }
}
