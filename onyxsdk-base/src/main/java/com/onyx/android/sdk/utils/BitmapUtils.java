// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorMatrix;
import android.util.Base64;
import android.graphics.Xfermode;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.opengl.GLES10;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.rx.RxCallback;
import java.io.IOException;
import android.media.ThumbnailUtils;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import android.graphics.Color;
import android.annotation.SuppressLint;
import android.os.Build;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import android.media.ExifInterface;
import java.io.Closeable;
import java.io.FileInputStream;
import android.util.Log;
import com.onyx.android.sdk.data.Size;
import java.io.InputStream;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.content.res.Resources;
import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import com.onyx.android.sdk.data.file.StreamProvider;
import android.graphics.Canvas;
import android.view.View;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Paint;

public class BitmapUtils
{
    private static final String a = "BitmapUtils";
    public static Paint paint;
    private static final int b = 255;
    public static final ColorFilter INVERTED_COLOR_FILTER;
    private static boolean c;
    
    public static boolean isAntiAlias() {
        return BitmapUtils.c;
    }
    
    public static void setAntiAlias(final boolean antiAlias) {
        BitmapUtils.c = antiAlias;
    }
    
    public static Bitmap loadBitmapFromFile(final String path) {
        final BitmapFactory.Options options;
        (options = new BitmapFactory.Options()).inPreferredConfig = Bitmap.Config.ARGB_8888;
        if (!FileUtils.fileExist(path)) {
            return null;
        }
        return BitmapFactory.decodeFile(path, options);
    }
    
    public static Bitmap loadBitmapFromView(final View view) {
        if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {
            final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(bitmap));
            return bitmap;
        }
        Debug.w((Class)BitmapUtils.class, "loadBitmapFromView: view is null or view's width/height <= 0.", new Object[0]);
        return null;
    }
    
    public static Bitmap loadBitmapFromDrawingCache(final View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        return view.getDrawingCache(true);
    }
    
    public static boolean saveBitmap(final Bitmap bitmap, final String path) {
        return saveBitmap(bitmap, path, false);
    }
    
    public static boolean saveBitmap(final Bitmap bitmap, final String path, final boolean overrideFilePermission) {
        try {
            final FileOutputStream outputStream = StreamProvider.getOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)outputStream);
            try {
                outputStream.flush();
                outputStream.close();
                if (!overrideFilePermission) {
                    return true;
                }
                final File file = new File(path);
                file.setReadable(true, false);
                try {
                    file.setWritable(true, false);
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
            catch (final Exception ex2) {}
        }
        catch (final Exception ex3) {}
        return true;
    }
    
    public static Bitmap decodeResourceSafely(final Resources res, final int id) {
        if (id <= 0) {
            return null;
        }
        try {
            return BitmapFactory.decodeResource(res, id);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Bitmap scaleBitmapWithinLimits(final Bitmap src, int limitWidth, int limitHeight) {
        if (isRecycled(src)) {
            return null;
        }
        final int n = limitWidth;
        final RectF rectF = new RectF(0.0f, 0.0f, (float)src.getWidth(), (float)src.getHeight());
        final float min;
        if ((min = Math.min(n / rectF.width(), limitHeight / rectF.height())) < 1.0f) {
            final RectF rectF2 = rectF;
            final float n2 = min;
            RectUtils.scale(rectF2, n2, n2);
        }
        final Bitmap bitmap2;
        final Bitmap bitmap = bitmap2 = Bitmap.createBitmap((int)rectF.width(), (int)rectF.height(), Bitmap.Config.ARGB_8888);
        final Bitmap dst = bitmap2;
        final int width = src.getWidth();
        limitHeight = src.getHeight();
        final Rect srcRegion = new Rect(0, 0, width, limitHeight);
        final Rect dstRegion = new Rect(0, 0, limitWidth, limitHeight);
        final Bitmap bitmap3 = bitmap2;
        limitWidth = bitmap3.getWidth();
        limitHeight = bitmap3.getHeight();
        scaleBitmap(src, srcRegion, dst, dstRegion);
        return bitmap;
    }
    
    public static void scaleBitmap(final Bitmap src, final Rect srcRegion, final Bitmap dst, final Rect dstRegion) {
        if (!isRecycled(src) && !isRecycled(dst)) {
            final Canvas canvas = new Canvas(dst);
            BitmapUtils.paint.setFilterBitmap(true);
            canvas.drawBitmap(src, srcRegion, dstRegion, BitmapUtils.paint);
        }
    }
    
    public static void scaleToFitCenter(final Bitmap src, final Bitmap dst) {
        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());
        scaleBitmap(src, rect, dst, getScaleFitCenterRect(rect, new Rect(0, 0, dst.getWidth(), dst.getHeight())));
    }
    
    public static Rect getScaleFitCenterRect(final Rect srcRect, final Rect dstRect) {
        final float n;
        Rect rect;
        if ((n = srcRect.width() / (float)srcRect.height()) >= dstRect.width() / (float)dstRect.height()) {
            final int round = Math.round(dstRect.width() / n);
            final int abs = Math.abs((dstRect.height() - round) / 2);
            rect = new Rect(0, abs, dstRect.width(), round + abs);
        }
        else {
            final int round2 = Math.round(dstRect.height() * n);
            final int abs2 = Math.abs((dstRect.width() - round2) / 2);
            rect = new Rect(abs2, 0, round2 + abs2, dstRect.height());
        }
        return rect;
    }
    
    public static Bitmap createScaledBitmap(final Bitmap origin, final int newWidth, final int newHeight) {
        return createScaledBitmap(origin, newWidth, newHeight, false);
    }
    
    public static Bitmap createScaledBitmap(final Bitmap origin, final int newWidth, final int newHeight, final boolean isWhiteBG) {
        final Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        if (!isValid(origin)) {
            final Bitmap bitmap2 = bitmap;
            Debug.e((Throwable)new IllegalArgumentException("invalid bitmap"));
            return bitmap2;
        }
        if (isWhiteBG) {
            bitmap.eraseColor(-1);
        }
        final Bitmap bitmap3 = bitmap;
        final float n2;
        final float n = (n2 = (float)newWidth) / origin.getWidth();
        final float n3 = (float)newHeight;
        final float n4 = n2;
        final float n5 = n3 / origin.getHeight();
        final float n6 = n4 / 2.0f;
        final float n7 = n3 / 2.0f;
        final Matrix matrix = new android.graphics.Matrix();
        final Matrix matrix2 = matrix;
        final float n8 = n;
        new Matrix();
        matrix.setScale(n8, n5, n6, n7);
        final Canvas canvas = new android.graphics.Canvas();
        final Canvas canvas2 = canvas;
        final float n9 = n7;
        final float n10 = n6;
        final Canvas canvas3 = canvas2;
        final Matrix matrix3 = matrix2;
        new Canvas(bitmap);
        canvas3.setMatrix(matrix3);
        canvas.drawBitmap(origin, n10 - origin.getWidth() / 2, n9 - origin.getHeight() / 2, new Paint(2));
        return bitmap3;
    }
    
    public static boolean decodeBitmapSize(final InputStream stream, final Size size) {
        final BitmapFactory.Options options;
        (options = new BitmapFactory.Options()).inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(stream, (Rect)null, options);
            size.width = options.outWidth;
            size.height = options.outHeight;
            return true;
        }
        catch (Throwable t) {
            Log.w(BitmapUtils.a, t);
            return false;
        }
    }
    
    public static boolean decodeBitmapSize(final String path, final Size size) {
        try (FileInputStream stream = new FileInputStream(path)) {
            return decodeBitmapSize(stream, size);
        } catch (Exception failure) {
            Log.w(BitmapUtils.a, failure);
            return false;
        }
    }
    
    public static boolean decodeBitmapSizeFromPath(final String path, final Size size) {
        final BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        final BitmapFactory.Options options3;
        final BitmapFactory.Options options2 = options3 = options;
        new BitmapFactory.Options();
        options3.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options3);
            final int outWidth = options.outWidth;
            final BitmapFactory.Options options4 = options2;
            size.width = outWidth;
            final int n = size.height = options4.outHeight;
            return outWidth > 0 && n > 0;
        }
        catch (Throwable t) {
            Log.w(BitmapUtils.a, t);
            return false;
        }
    }
    
    public static boolean decodeBitmapSizeSupportExif(final String path, final Size size) {
        final boolean decodeBitmapSizeFromPath = decodeBitmapSizeFromPath(path, size);
        decodeBitmapSizeByExif(path, size);
        return decodeBitmapSizeFromPath;
    }
    
    public static int decodeBitmapSizeByExif(final String path, final Size size) {
        int imageOrientation = 1;
        if (isSupportExifInterface(path)) {
            if ((imageOrientation = getImageOrientation(path)) == 6 || imageOrientation == 8) {
                final int width = size.width;
                size.width = size.height;
                size.height = width;
            }
        }
        return imageOrientation;
    }
    
    public static boolean isSupportExifInterface(String filePath) {
        final String s = filePath = FileUtils.getFileExtension(filePath).toLowerCase();
        s.hashCode();
        int n = -1;
        switch (s) {
            case "webp": {
                n = 5;
                break;
            }
            case "jpeg": {
                n = 4;
                break;
            }
            case "heif": {
                n = 3;
                break;
            }
            case "heic": {
                n = 2;
                break;
            }
            case "jpg": {
                n = 1;
                break;
            }
            case "dng": {
                n = 0;
                break;
            }
            default:
                break;
        }
        switch (n) {
            default: {
                return false;
            }
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                return true;
            }
        }
    }
    
    public static int getImageOrientation(final String path) {
        try {
            return new ExifInterface(path).getAttributeInt("Orientation", 1);
        }
        catch (Throwable t) {
            Log.w(BitmapUtils.a, t);
            return 1;
        }
    }
    
    public static void drawRectOnBitmap(final Bitmap bmp, final RectF rect) {
        final Canvas canvas = new Canvas(bmp);
        final Paint paint2;
        final Paint paint = paint2 = new Paint();
        paint.setColor(-16777216);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        canvas.drawRect(rect, paint2);
    }
    
    public static void clear(final Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.eraseColor(-1);
        }
    }
    
    public static void recycle(final Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
    
    public static void recycle(final List<Bitmap> bitmaps) {
        if (CollectionUtils.isNullOrEmpty(bitmaps)) {
            return;
        }
        final Iterator<Bitmap> iterator = bitmaps.iterator();
        while (iterator.hasNext()) {
            recycle(iterator.next());
        }
    }
    
    @SuppressLint({ "NewApi" })
    public static int getSizeInBytes(final Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT > 19) {
            try {
                return bitmap.getAllocationByteCount();
            }
            catch (final NullPointerException ex) {}
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getHeight() * bitmap.getRowBytes();
    }
    
    public static boolean isValid(final Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
    }
    
    public static boolean isRecycled(final Bitmap bitmap) {
        return bitmap == null || bitmap.isRecycled();
    }
    
    public static Bitmap fromGrayscale(final byte[] gray, final int width, final int height, final int stride) {
        final int[] array = new int[width * height];
        int n = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int[] array2 = array;
                final int n2 = n;
                final int n4;
                final int n3 = n4 = (gray[i * stride + j] & 0xFF);
                ++n;
                array2[n2] = (n3 << 16 | 0xFF000000 | n4 << 8 | n4);
            }
        }
        return Bitmap.createBitmap(array, width, height, Bitmap.Config.ARGB_8888);
    }
    
    public static byte[] toGrayScale(final Bitmap bitmapInArgb) {
        final byte[] array = new byte[bitmapInArgb.getWidth() * bitmapInArgb.getHeight()];
        for (int i = 0; i < bitmapInArgb.getHeight(); ++i) {
            for (int j = 0; j < bitmapInArgb.getWidth(); ++j) {
                array[i * bitmapInArgb.getWidth() + j] = (byte)ColorUtils.convertToGray(bitmapInArgb.getPixel(j, i));
            }
        }
        return array;
    }
    
    public static byte[] cfa(final Bitmap bitmapInArgb, final Rect rect) {
        final byte[] array = new byte[rect.width() * rect.height() * 4];
        for (int i = rect.top; i < rect.bottom; ++i) {
            for (int j = rect.left; j < rect.right; ++j) {
                final byte[] array2 = array;
                final int n = i;
                final int pixel = bitmapInArgb.getPixel(j, i);
                final byte b = (byte)Color.red(pixel);
                Color.green(pixel);
                Color.blue(pixel);
                array2[n * rect.width() + j] = b;
            }
        }
        return array;
    }
    
    public static byte[] bitmapToBytes(final Bitmap bitmap) {
        return bitmapToBytes(bitmap, Bitmap.CompressFormat.PNG);
    }
    
    public static byte[] bitmapToBytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, (OutputStream)byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    public static Rect getScaleInSideAndCenterRect(int renderTargetHeight, int renderTargetWidth, int sourceHeight, final int sourceWidth, final boolean zoomToWidth) {
        Rect rect;
        if (zoomToWidth) {
            final int n = sourceHeight;
            renderTargetWidth = (int)((renderTargetHeight - 1) * ((renderTargetHeight = renderTargetWidth - 1) / (float)(sourceWidth - 1)));
            sourceHeight = (n - 1 - renderTargetWidth) / 2;
            rect = new Rect(0, sourceHeight, renderTargetHeight, renderTargetWidth);
            renderTargetWidth += sourceHeight;
        }
        else {
            renderTargetWidth = (int)((renderTargetWidth - 1) * (--renderTargetHeight / (float)(sourceHeight - 1)));
            sourceHeight = (sourceWidth - 1 - renderTargetWidth) / 2;
            rect = new Rect(sourceHeight, 0, renderTargetWidth + sourceHeight, renderTargetHeight);
        }
        return rect;
    }
    
    public static Bitmap rotateBmp(final Bitmap bmp, int degrees) {
        final Matrix matrix = new android.graphics.Matrix();
        final Matrix matrix2 = matrix;
        final int n = degrees;
        new Matrix();
        matrix.postRotate((float)n);
        degrees = bmp.getWidth();
        return Bitmap.createBitmap(bmp, 0, 0, degrees, bmp.getHeight(), matrix2, true);
    }
    
    public static Bitmap horizontalFlip(final Bitmap bmp) {
        final Matrix matrix;
        (matrix = new Matrix()).postScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }
    
    public static Bitmap buildBitmapFromText(final String targetString, final int height, int textSize, final boolean boldText, final boolean saveToDisk, final boolean overrideFilePermission, final boolean needRotation, final int rotationAngle, @Nullable final String path) {
        final Paint paint = new android.graphics.Paint();
        final Paint paint4;
        final Paint paint3;
        final Paint paint2 = paint3 = (paint4 = paint);
        final int n = textSize;
        final Paint paint5 = paint2;
        new Paint(1);
        paint5.setStrokeWidth(3.0f);
        paint3.setTextSize((float)n);
        paint3.setColor(-16777216);
        paint4.setFakeBoldText(boldText);
        Bitmap bitmap = Bitmap.createBitmap(textSize = StringUtils.getTextWidth(paint, targetString), height, Bitmap.Config.ARGB_8888);
        final Rect rect = new Rect(0, 0, textSize, height);
        final Canvas canvas = new android.graphics.Canvas();
        final Canvas canvas2 = canvas;
        final Paint paint6 = paint2;
        new Canvas(bitmap);
        final Paint.FontMetricsInt fontMetricsInt = paint6.getFontMetricsInt();
        final int n2 = (height - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        final Rect rect2 = rect;
        paint2.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(targetString, (float)rect2.centerX(), (float)n2, paint2);
        if (needRotation) {
            bitmap = rotateBmp(bitmap, rotationAngle);
        }
        if (saveToDisk) {
            saveBitmap(bitmap, path);
            if (overrideFilePermission) {
                ShellUtils.execCommand("busybox chmod 644 " + path, false);
            }
        }
        return bitmap;
    }
    
    public static Bitmap convertToBlackWhite(final Bitmap bmp) {
        final int width;
        final int height;
        final int[] array;
        bmp.getPixels(array = new int[(width = bmp.getWidth()) * (height = bmp.getHeight())], 0, width, 0, 0, width, height);
        final int n = -16777216;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int[] array2 = array;
                final int n2 = n;
                final int n4;
                final int n3 = array[n4 = width * i + j];
                final int n5;
                array2[n4] = (n2 | (n5 = (int)(((n3 & 0xFF0000) >> 16) * 0.3 + ((n3 & 0xFF00) >> 8) * 0.59 + (n3 & 0xFF) * 0.11)) << 16 | n5 << 8 | n5);
            }
        }
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        final int n6 = width;
        final int n7 = height;
        bitmap.setPixels(array, 0, width, 0, 0, width, height);
        return ThumbnailUtils.extractThumbnail(bitmap, n6, n7);
    }
    
    public static boolean savePngToFile(final Bitmap bitmap, final String dirName, final String pngFileName, final boolean isNeedOverrideDirPermission) {
        if (bitmap == null) {
            return false;
        }
        try {
            FileUtils.createAndOverridePermission(
                    dirName, pngFileName, isNeedOverrideDirPermission);
            try (FileOutputStream outputStream = StreamProvider.getOutputStream(pngFileName)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
            }
            return true;
        } catch (final IOException error) {
            error.printStackTrace();
            return false;
        } finally {
            bitmap.recycle();
        }
    }
    
    public static boolean savePngToFileUsingFileApiStream(final Bitmap bitmap, final String dirName, final String pngFileName, final boolean isNeedOverrideDirPermission) {
        if (bitmap == null) {
            return false;
        }
        try {
            FileUtils.createAndOverridePermission(
                    dirName, pngFileName, isNeedOverrideDirPermission);
            try (FileOutputStream outputStream = new FileOutputStream(pngFileName)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
            }
            return true;
        } catch (final IOException error) {
            error.printStackTrace();
            return false;
        } finally {
            bitmap.recycle();
        }
    }
    
    public static boolean saveBitmapToFile(final Bitmap bitmap, final String dirName, final String bmpFileName, final boolean isNeedOverrideDirPermission) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: dup            
        //     8: invokevirtual   android/graphics/Bitmap.getWidth:()I
        //    11: istore          4
        //    13: invokevirtual   android/graphics/Bitmap.getHeight:()I
        //    16: dup            
        //    17: istore          5
        //    19: iload           4
        //    21: iconst_3       
        //    22: imul           
        //    23: dup            
        //    24: istore          6
        //    26: iload           4
        //    28: iload           5
        //    30: iload           6
        //    32: iload           4
        //    34: iconst_4       
        //    35: irem           
        //    36: iadd           
        //    37: imul           
        //    38: dup            
        //    39: istore          6
        //    41: aload_1        
        //    42: aload_2        
        //    43: iload_3        
        //    44: invokestatic    com/onyx/android/sdk/utils/FileUtils.createAndOverridePermission:(Ljava/lang/String;Ljava/lang/String;Z)V
        //    47: new             Ljava/io/FileOutputStream;
        //    50: dup            
        //    51: dup2           
        //    52: dup2           
        //    53: dup2           
        //    54: dup2           
        //    55: astore_1       
        //    56: iload           5
        //    58: aload_1        
        //    59: iload           4
        //    61: aload_1        
        //    62: dup            
        //    63: dup2           
        //    64: dup2           
        //    65: iload           6
        //    67: aload_1        
        //    68: aload_2        
        //    69: invokespecial   java/io/FileOutputStream.<init>:(Ljava/lang/String;)V
        //    72: bipush          54
        //    74: iadd           
        //    75: i2l            
        //    76: lstore_2       
        //    77: iconst_0       
        //    78: istore          6
        //    80: iconst_0       
        //    81: istore          7
        //    83: ldc2_w          54
        //    86: lstore          8
        //    88: sipush          19778
        //    91: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeWord:(Ljava/io/FileOutputStream;I)V
        //    94: lload_2        
        //    95: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //    98: iload           6
        //   100: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeWord:(Ljava/io/FileOutputStream;I)V
        //   103: iload           7
        //   105: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeWord:(Ljava/io/FileOutputStream;I)V
        //   108: lload           8
        //   110: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   113: iconst_1       
        //   114: istore_2       
        //   115: bipush          24
        //   117: istore_3       
        //   118: lconst_0       
        //   119: lstore          6
        //   121: lconst_0       
        //   122: lstore          8
        //   124: lconst_0       
        //   125: lstore          10
        //   127: lconst_0       
        //   128: lstore          12
        //   130: lconst_0       
        //   131: lstore          14
        //   133: lconst_0       
        //   134: lstore          16
        //   136: ldc2_w          40
        //   139: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   142: i2l            
        //   143: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeLong:(Ljava/io/FileOutputStream;J)V
        //   146: i2l            
        //   147: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeLong:(Ljava/io/FileOutputStream;J)V
        //   150: iload_2        
        //   151: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeWord:(Ljava/io/FileOutputStream;I)V
        //   154: iload_3        
        //   155: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeWord:(Ljava/io/FileOutputStream;I)V
        //   158: lload           6
        //   160: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   163: lload           8
        //   165: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   168: lload           10
        //   170: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeLong:(Ljava/io/FileOutputStream;J)V
        //   173: lload           12
        //   175: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeLong:(Ljava/io/FileOutputStream;J)V
        //   178: lload           14
        //   180: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   183: lload           16
        //   185: invokestatic    com/onyx/android/sdk/utils/BitmapUtils.writeDword:(Ljava/io/FileOutputStream;J)V
        //   188: newarray        B
        //   190: astore_2       
        //   191: iconst_4       
        //   192: irem           
        //   193: iadd           
        //   194: istore_3       
        //   195: iconst_0       
        //   196: istore          6
        //   198: iconst_1       
        //   199: isub           
        //   200: istore          7
        //   202: iload           6
        //   204: iload           5
        //   206: if_icmpge       292
        //   209: iconst_0       
        //   210: istore          8
        //   212: iconst_0       
        //   213: istore          9
        //   215: iload           8
        //   217: iload           4
        //   219: if_icmpge       283
        //   222: aload_2        
        //   223: dup            
        //   224: dup            
        //   225: iload           7
        //   227: iload_3        
        //   228: aload_0        
        //   229: iload           8
        //   231: iload           6
        //   233: invokevirtual   android/graphics/Bitmap.getPixel:(II)I
        //   236: istore          10
        //   238: imul           
        //   239: iload           9
        //   241: iadd           
        //   242: dup            
        //   243: istore          11
        //   245: iload           10
        //   247: invokestatic    android/graphics/Color.blue:(I)I
        //   250: i2b            
        //   251: bastore        
        //   252: iload           11
        //   254: iconst_1       
        //   255: iadd           
        //   256: iload           10
        //   258: invokestatic    android/graphics/Color.green:(I)I
        //   261: i2b            
        //   262: bastore        
        //   263: iload           11
        //   265: iconst_2       
        //   266: iadd           
        //   267: iload           10
        //   269: invokestatic    android/graphics/Color.red:(I)I
        //   272: i2b            
        //   273: bastore        
        //   274: iinc            8, 1
        //   277: iinc            9, 3
        //   280: goto            215
        //   283: iinc            6, 1
        //   286: iinc            7, -1
        //   289: goto            202
        //   292: aload_0        
        //   293: aload_1        
        //   294: dup            
        //   295: dup            
        //   296: aload_2        
        //   297: invokevirtual   java/io/FileOutputStream.write:([B)V
        //   300: invokevirtual   java/io/FileOutputStream.flush:()V
        //   303: invokevirtual   java/io/FileOutputStream.close:()V
        //   306: invokevirtual   android/graphics/Bitmap.recycle:()V
        //   309: iconst_1       
        //   310: ireturn        
        //   311: goto            323
        //   314: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   317: aload_0        
        //   318: invokevirtual   android/graphics/Bitmap.recycle:()V
        //   321: iconst_0       
        //   322: ireturn        
        //   323: aload_0        
        //   324: invokevirtual   android/graphics/Bitmap.recycle:()V
        //   327: athrow         
        //    StackMapTable: 00 08 06 FF 00 C3 00 08 07 00 18 07 02 AB 07 01 FF 01 01 01 01 01 00 00 FD 00 0C 01 01 F9 00 43 FF 00 08 00 03 07 00 18 07 02 AB 07 01 FF 00 00 FF 00 12 00 01 07 00 18 00 01 07 01 57 42 07 00 C0 48 07 01 57
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  41     50     314    323    Ljava/lang/Exception;
        //  41     50     311    314    Any
        //  56     72     314    323    Ljava/lang/Exception;
        //  56     72     311    314    Any
        //  88     113    314    323    Ljava/lang/Exception;
        //  88     113    311    314    Any
        //  136    142    314    323    Ljava/lang/Exception;
        //  136    142    311    314    Any
        //  143    146    314    323    Ljava/lang/Exception;
        //  143    146    311    314    Any
        //  147    190    314    323    Ljava/lang/Exception;
        //  147    190    311    314    Any
        //  191    193    314    323    Ljava/lang/Exception;
        //  191    193    311    314    Any
        //  222    236    314    323    Ljava/lang/Exception;
        //  222    236    311    314    Any
        //  245    250    314    323    Ljava/lang/Exception;
        //  245    250    311    314    Any
        //  251    252    314    323    Ljava/lang/Exception;
        //  251    252    311    314    Any
        //  256    261    314    323    Ljava/lang/Exception;
        //  256    261    311    314    Any
        //  262    263    314    323    Ljava/lang/Exception;
        //  262    263    311    314    Any
        //  267    272    314    323    Ljava/lang/Exception;
        //  267    272    311    314    Any
        //  273    274    314    323    Ljava/lang/Exception;
        //  273    274    311    314    Any
        //  292    306    314    323    Ljava/lang/Exception;
        //  292    306    311    314    Any
        //  314    317    311    314    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0202:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2604)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
        if (bitmap == null) {
            return false;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rowStride = width * 3 + width % 4;
        int imageSize = height * rowStride;
        try {
            FileUtils.createAndOverridePermission(dirName, bmpFileName, isNeedOverrideDirPermission);
            try (FileOutputStream output = new FileOutputStream(bmpFileName)) {
                writeWord(output, 0x4D42);
                writeDword(output, imageSize + 54L);
                writeWord(output, 0);
                writeWord(output, 0);
                writeDword(output, 54L);

                writeDword(output, 40L);
                writeLong(output, width);
                writeLong(output, height);
                writeWord(output, 1);
                writeWord(output, 24);
                writeDword(output, 0L);
                writeDword(output, 0L);
                writeLong(output, 0L);
                writeLong(output, 0L);
                writeDword(output, 0L);
                writeDword(output, 0L);

                byte[] pixels = new byte[imageSize];
                for (int y = 0, sourceY = height - 1; y < height; y++, sourceY--) {
                    int rgbOffset = 0;
                    for (int x = 0; x < width; x++, rgbOffset += 3) {
                        int color = bitmap.getPixel(x, y);
                        int offset = sourceY * rowStride + rgbOffset;
                        pixels[offset] = (byte)Color.blue(color);
                        pixels[offset + 1] = (byte)Color.green(color);
                        pixels[offset + 2] = (byte)Color.red(color);
                    }
                }
                output.write(pixels);
                output.flush();
            }
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        finally {
            bitmap.recycle();
        }
    }
    
    public static void writeWord(final FileOutputStream stream, final int value) throws IOException {
        stream.write(new byte[] { (byte)(value & 0xFF), (byte)(value >> 8 & 0xFF) });
    }
    
    public static void writeDword(final FileOutputStream stream, final long value) throws IOException {
        final byte[] b = new byte[4];
        final byte[] array2;
        final byte[] array;
        (array = (array2 = b))[0] = (byte)(value & 0xFFL);
        array[1] = (byte)(value >> 8 & 0xFFL);
        array2[2] = (byte)(value >> 16 & 0xFFL);
        b[3] = (byte)(value >> 24 & 0xFFL);
        stream.write(b);
    }
    
    public static void writeLong(final FileOutputStream stream, final long value) throws IOException {
        final byte[] b = new byte[4];
        final byte[] array2;
        final byte[] array;
        (array = (array2 = b))[0] = (byte)(value & 0xFFL);
        array[1] = (byte)(value >> 8 & 0xFFL);
        array2[2] = (byte)(value >> 16 & 0xFFL);
        b[3] = (byte)(value >> 24 & 0xFFL);
        stream.write(b);
    }
    
    public static int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        return calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
    }
    
    public static int calculateInSampleSize(int width, int height, final int reqWidth, final int reqHeight) {
        int n = 1;
        if (height > reqHeight || width > reqWidth) {
            final int n2 = width;
            for (width = height / 2, height = n2 / 2; width / n > reqHeight && height / n > reqWidth; n *= 2) {}
        }
        return n;
    }
    
    public static int calculateInSampleSize(int width, final int reqWidth) {
        int n = 1;
        if (width > reqWidth) {
            for (width /= 2; width / n > reqWidth; n *= 2) {}
        }
        return n;
    }
    
    public static int calculateInSampleSizeUnderRequest(int width, int height, final int reqWidth, final int reqHeight) {
        int n = 1;
        if (height > reqHeight || width > reqWidth) {
            final int n2 = width;
            for (width = height / 2, height = n2 / 2; width / n > reqHeight || height / n > reqWidth; n *= 2) {}
        }
        return n;
    }
    
    @Nullable
    public static Bitmap decodeBitmap(final String path, final int targetWidth, final int targetHeight) {
        return decodeBitmap(path, targetWidth, targetHeight, false);
    }
    
    @Nullable
    public static Bitmap decodeBitmap(final String path, final int targetWidth, final int targetHeight, final boolean isWhiteBG) {
        final Bitmap a;
        if (!isValid(a = a(path, getOptionsCallback(targetWidth, targetHeight)))) {
            return null;
        }
        return createScaledBitmap(a, targetWidth, targetHeight, isWhiteBG);
    }
    
    @Nullable
    public static Bitmap decodeBitmap(final String path, final Size srcSize, final Size targetSize, final int orientation, final boolean isWhiteBG) {
        final Bitmap a;
        if (!isValid(a = a(path, srcSize, orientation, getOptionsCallback(targetSize.width, targetSize.height)))) {
            return null;
        }
        return createScaledBitmap(a, targetSize.width, targetSize.height, isWhiteBG);
    }
    
    public static RxCallback<BitmapFactory.Options> getOptionsCallback(final int targetWidth, final int targetHeight) {
        RxCallback<BitmapFactory.Options> rxCallback;
        if (targetWidth > 0 && targetHeight > 0) {
            rxCallback = new RxCallback<BitmapFactory.Options>() {
                public void onNext(@NonNull final BitmapFactory.Options options) {
                    options.inSampleSize = BitmapUtils.calculateInSampleSize(options, targetWidth, targetHeight);
                }
            };
        }
        else {
            rxCallback = null;
        }
        return rxCallback;
    }
    
    @Nullable
    public static Bitmap decodeBitmapUnderRequest(final String path, final int reqWidth, final int reqHeight) {
        if (reqWidth > 0 && reqHeight > 0) {
            return a(path, Bitmap.Config.RGB_565, new RxCallback<BitmapFactory.Options>() {
                public void onNext(@NonNull final BitmapFactory.Options options) {
                    options.inSampleSize = BitmapUtils.calculateInSampleSizeUnderRequest(options.outWidth, options.outHeight, reqWidth, reqHeight);
                }
            });
        }
        return loadBitmapFromFile(path);
    }
    
    @Nullable
    public static Bitmap decodeWidthRatioBitmap(final String path, final int targetWidth) {
        if (targetWidth <= 0) {
            return loadBitmapFromFile(path);
        }
        return a(path, new RxCallback<BitmapFactory.Options>() {
            public void onNext(@NonNull final BitmapFactory.Options options) {
                options.inSampleSize = BitmapUtils.calculateInSampleSize(options.outWidth, targetWidth);
            }
        });
    }
    
    @Nullable
    public static Bitmap scaleToWidthRatioBitmap(final String path, final int targetWidth) {
        if (targetWidth <= 0) {
            return loadBitmapFromFile(path);
        }
        return scaleToWidthRatioBitmap(a(path, new RxCallback<BitmapFactory.Options>() {
            public void onNext(@NonNull final BitmapFactory.Options options) {
                options.inSampleSize = BitmapUtils.calculateInSampleSize(options.outWidth, targetWidth);
            }
        }), targetWidth);
    }
    
    @Nullable
    public static Bitmap scaleToWidthRatioBitmap(final Bitmap bitmap, final int targetWidth) {
        if (targetWidth > 0 && isValid(bitmap)) {
            return createScaledBitmap(bitmap, targetWidth, bitmap.getHeight() * targetWidth / bitmap.getWidth());
        }
        return bitmap;
    }
    
    private static Bitmap a(final String path, final Size size, final int orientation, final RxCallback<BitmapFactory.Options> optionsCallback) {
        final BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        final BitmapFactory.Options options2;
        final BitmapFactory.Options outOptions = options2 = options;
        new BitmapFactory.Options();
        options2.outWidth = size.width;
        options.outHeight = size.height;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if (optionsCallback != null) {
            optionsCallback.onNext(outOptions);
        }
        return a(path, orientation, outOptions);
    }
    
    private static Bitmap a(final String path, final RxCallback<BitmapFactory.Options> optionsCallback) {
        return a(path, Bitmap.Config.ARGB_8888, optionsCallback);
    }
    
    private static Bitmap a(final String path, final Bitmap.Config inPreferredConfig, final RxCallback<BitmapFactory.Options> optionsCallback) {
        final BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        final BitmapFactory.Options options2;
        final BitmapFactory.Options outOptions = options2 = options;
        new BitmapFactory.Options();
        final Size size;
        decodeBitmapSizeSupportExif(path, size = new Size());
        options2.outWidth = size.width;
        options2.outHeight = size.height;
        options2.inJustDecodeBounds = false;
        options.inPreferredConfig = inPreferredConfig;
        if (optionsCallback != null) {
            optionsCallback.onNext(outOptions);
        }
        return a(path, outOptions);
    }
    
    private static Bitmap a(final String path, final BitmapFactory.Options outOptions) {
        return a(BitmapFactory.decodeFile(path, outOptions), getImageOrientation(path));
    }
    
    private static Bitmap a(final String path, final int orientation, final BitmapFactory.Options outOptions) {
        return a(BitmapFactory.decodeFile(path, outOptions), orientation);
    }
    
    private static Bitmap a(Bitmap bitmap, final int orientation) {
        if (orientation != 3) {
            if (orientation != 6) {
                if (orientation == 8) {
                    bitmap = rotateBmp(bitmap, 270);
                }
            }
            else {
                bitmap = rotateBmp(bitmap, 90);
            }
        }
        else {
            bitmap = rotateBmp(bitmap, 180);
        }
        return bitmap;
    }
    
    public static Bitmap drawableToBitmap(final Drawable drawable) {
        return drawableToBitmap(drawable, 0);
    }
    
    public static Bitmap drawableToBitmap(final Drawable drawable, final int color) {
        int n = drawable.getIntrinsicWidth();
        int n2 = drawable.getIntrinsicHeight();
        final boolean b;
        final Bitmap bitmap;
        if ((b = (drawable instanceof BitmapDrawable)) && (bitmap = ((BitmapDrawable)drawable).getBitmap()) != null) {
            final Bitmap bitmap2 = bitmap;
            n = bitmap2.getWidth();
            n2 = bitmap2.getHeight();
        }
        Bitmap.Config config;
        if (drawable.getOpacity() != -1) {
            config = Bitmap.Config.ARGB_8888;
        }
        else {
            config = Bitmap.Config.RGB_565;
        }
        final boolean b2 = b;
        final Bitmap bitmap3 = Bitmap.createBitmap(n, n2, config);
        final Canvas canvas;
        (canvas = new Canvas(bitmap3)).drawColor(color);
        if (b2) {
            final Canvas canvas2 = canvas;
            final Rect rect = new Rect(0, 0, n, n2);
            safelyDrawBitmap(canvas2, ((BitmapDrawable)drawable).getBitmap(), rect, rect, createBitmapPaint());
        }
        else {
            drawable.setBounds(0, 0, n, n2);
            drawable.draw(canvas);
        }
        return bitmap3;
    }
    
    public static boolean isValidDrawable(final Drawable drawable) {
        if (drawable == null) {
            return false;
        }
        int n = drawable.getIntrinsicWidth();
        int n2 = drawable.getIntrinsicHeight();
        final Bitmap bitmap;
        if (drawable instanceof BitmapDrawable && (bitmap = ((BitmapDrawable)drawable).getBitmap()) != null) {
            final Bitmap bitmap2 = bitmap;
            n = bitmap2.getWidth();
            n2 = bitmap2.getHeight();
        }
        return n > 0 && n2 > 0;
    }
    
    public static int calculateBitmapSampleSize(final Bitmap bitmap, int defaultValue, int limitValue) {
        for (defaultValue = getMaxImageSize(defaultValue, limitValue), limitValue = 1; bitmap.getHeight() / limitValue > defaultValue || bitmap.getWidth() / limitValue > defaultValue; limitValue <<= 1) {}
        return limitValue;
    }
    
    public static int getMaxImageSize(final int defaultValue, final int limitValue) {
        final int maxTextureSize;
        if ((maxTextureSize = getMaxTextureSize()) == 0) {
            return defaultValue;
        }
        return Math.min(maxTextureSize, limitValue);
    }
    
    public static int getMaxTextureSize() {
        final int[] array = { 0 };
        GLES10.glGetIntegerv(3379, array, 0);
        return array[0];
    }
    
    public static Bitmap fillColorAsBackground(final Bitmap targetBitmap, final int color) {
        final Bitmap bitmap = Bitmap.createBitmap(targetBitmap.getWidth(), targetBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        canvas.drawBitmap(targetBitmap, 0.0f, 0.0f, (Paint)null);
        canvas.save();
        return bitmap;
    }
    
    @Nullable
    public static Bitmap getBitmapFromVectorDrawable(Context context, final int drawableId) {
        if (drawableId <= 0) {
            return null;
        }
        try {
            Drawable drawable = ContextCompat.getDrawable(context, drawableId);
            if (Build.VERSION.SDK_INT < 21 && drawable != null) drawable = DrawableCompat.wrap(drawable).mutate();
            if (drawable == null) return null;
            Bitmap result = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return result;
        } catch (Exception failure) {
            failure.printStackTrace();
            return null;
        }
    }
    
    public static Bitmap getDynamicRoundedCornerBitmap(final Bitmap bitmap, final int color, final float cornerRatio, final int borderPix) {
        return getRoundedCornerBitmap(bitmap, color, (int)(Math.min(bitmap.getWidth(), bitmap.getHeight()) * cornerRatio) + borderPix * 2, borderPix);
    }
    
    public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final int color, final int cornerPix, int borderPix) {
        int padding = borderPix;
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() + padding * 2,
                bitmap.getHeight() + padding * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(-1);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        float radius = cornerPix;
        canvas.drawRoundRect(new RectF(0, 0, result.getWidth(), result.getHeight()),
                radius, radius, paint);
        paint.setColor(-1);
        float insetRadius = Math.max(0, cornerPix - padding * 2);
        canvas.drawRoundRect(new RectF(padding, padding,
                        result.getWidth() - padding, result.getHeight() - padding),
                insetRadius, insetRadius, paint);
        canvas.drawBitmap(roundCornerBitmap(bitmap, radius), padding, padding, null);
        return result;
    }
    
    public static void safelyDrawBitmap(final Canvas canvas, final Bitmap bitmap, final Rect src, final Rect dst, final Paint paint) {
        if (!isValid(bitmap)) {
            Debug.e((Throwable)new IllegalArgumentException("invalid bitmap"));
            return;
        }
        if (dst == null) {
            Debug.e((Throwable)new IllegalArgumentException("null dst"));
            return;
        }
        canvas.drawBitmap(bitmap, src, dst, paint);
    }
    
    public static Bitmap dynamicRoundedCorners(final Bitmap bitmap, final float cornerRadiusRatio) {
        return dynamicRoundedCorners(bitmap, bitmap.getWidth(), bitmap.getHeight(), cornerRadiusRatio);
    }
    
    public static Bitmap dynamicRoundedCorners(final Bitmap bitmap, final int targetWidth, final int targetHeight, final float cornerRadiusRatio) {
        return roundCornerBitmap(bitmap, targetWidth, targetHeight, Math.min(targetWidth, targetHeight) * cornerRadiusRatio);
    }
    
    public static Bitmap roundCornerBitmap(final Bitmap bitmap, final float radius) {
        return roundCornerBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), radius);
    }
    
    public static Bitmap roundCornerBitmap(Bitmap bitmap, final int targetWidth, final int targetHeight, final float radius) {
        if (bitmap.getWidth() != targetWidth || bitmap.getHeight() != targetHeight) {
            bitmap = createScaledBitmap(bitmap, targetWidth, targetHeight);
        }
        final Bitmap bitmap2 = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new android.graphics.Canvas();
        final Canvas canvas2 = canvas;
        final Bitmap bitmap3 = bitmap;
        new Canvas(bitmap2);
        final Paint paint = new android.graphics.Paint();
        final Paint paint2 = paint;
        final Canvas canvas3 = canvas2;
        new Paint();
        final Rect rect = new Rect(0, 0, targetWidth, targetHeight);
        canvas3.drawRoundRect(new RectF(rect), radius, radius, paint2);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        final Rect rect2 = rect;
        canvas.drawBitmap(bitmap3, rect2, rect2, paint2);
        return bitmap2;
    }
    
    public static void drawCenterBitmap(final Canvas canvas, final Bitmap bitmap, final Rect rect, final Paint paint) {
        canvas.drawBitmap(bitmap, rect.centerX() - bitmap.getWidth() / 2.0f, rect.centerY() - bitmap.getHeight() / 2.0f, paint);
    }
    
    public static void invert(final Drawable drawable) {
        drawable.setColorFilter(BitmapUtils.INVERTED_COLOR_FILTER);
    }
    
    @Nullable
    public static Bitmap getBitmapFromVectorDrawable(final Context context, final int drawableId, final int width, final int height) {
        return getBitmapFromVectorDrawable(context, drawableId, width, height, null);
    }
    
    @Nullable
    public static Bitmap getBitmapFromVectorDrawable(Context context, final int drawableId, final int width, final int height, final Integer tintColor) {
        if (drawableId <= 0) {
            return null;
        }
        try {
            final Drawable drawable = getTintDrawable(drawableId, tintColor);
            if (drawable == null) return null;
            Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return result;
        }
        catch (final Exception failure) {
            failure.printStackTrace();
            return null;
        }
    }
    
    @Nullable
    public static Bitmap createBitmap(final byte[] data, final int imageWidth, final int imageHeight, final Bitmap.Config config) {
        if (data != null && data.length != 0) {
            return Bitmap.createBitmap(my_bb_to_int_le(data), imageWidth, imageHeight, config);
        }
        return null;
    }
    
    public static int[] my_bb_to_int_le(final byte[] b) {
        final int n;
        final int[] array = new int[n = b.length / 4];
        for (int i = 0; i < n; ++i) {
            final int n2;
            array[i] = (b[n2 = i * 4] & 0xFF) + ((b[n2 + 1] & 0xFF) << 8) + ((b[n2 + 2] & 0xFF) << 16) + ((b[n2 + 3] & 0xFF) << 24);
        }
        return array;
    }
    
    public static boolean copyBitmap(final Bitmap src, final Bitmap dst) {
        if (isValid(src) && isValid(dst)) {
            dst.eraseColor(0);
            new Canvas(dst).drawBitmap(src, 0.0f, 0.0f, (Paint)null);
            return true;
        }
        return false;
    }
    
    @Nullable
    public static Bitmap loadBitmapFromResources(final Context context, final int drawableId) {
        return getBitmapFromVectorDrawable(context, drawableId);
    }
    
    @Nullable
    public static String encodeToBase64(Bitmap bitmap) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            return Base64.encodeToString(output.toByteArray(), 2);
        } catch (final IOException error) {
            error.printStackTrace();
            return null;
        }
    }
    
    @Nullable
    public static String compressAndEncodeToBase64(Bitmap bitmap, final int quality) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output);
            output.flush();
            return Base64.encodeToString(output.toByteArray(), 2);
        } catch (final IOException error) {
            error.printStackTrace();
            return "";
        }
    }
    
    @Nullable
    public static Bitmap decodeBase64ToBitmap(final String base64Image) {
        final String[] split;
        final String[] array = split = base64Image.split(",");
        String s = array[0];
        if (array.length > 1) {
            s = split[1];
        }
        final byte[] decode = Base64.decode(s, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }
    
    public static boolean isBase64Image(final String value) {
        return StringUtils.safelyGetStr(value).startsWith("data:image/");
    }
    
    public static Drawable getTintDrawable(final int redId, final Integer tintColor) {
        return getTintDrawable(redId, tintColor, null);
    }
    
    public static Drawable getTintDrawable(final int redId, final Integer tintColor, final PorterDuff.Mode mode) {
        return getTintDrawable(ResManager.getDrawable(redId), tintColor, mode);
    }
    
    public static Drawable getTintDrawable(final Drawable drawable, final Integer tintColor) {
        return getTintDrawable(drawable, tintColor, null);
    }
    
    public static Drawable getTintDrawable(Drawable drawable, final Integer tintColor, final PorterDuff.Mode mode) {
        if (drawable != null && tintColor != null) {
            drawable = drawable.mutate();
            if (mode != null && drawable != null) {
                DrawableCompat.setTintMode(drawable, mode);
            }
            DrawableCompat.setTint(drawable, (int)tintColor);
        }
        return drawable;
    }
    
    public static void updateBitmapPaint(final Paint paint) {
        paint.setAntiAlias(isAntiAlias());
        paint.setFilterBitmap(true);
        paint.setDither(true);
    }
    
    public static Paint createBitmapPaint() {
        final Paint paint = new Paint();
        updateBitmapPaint(paint);
        return paint;
    }
    
    public static Paint createDarkenBitmapPaint() {
        final Paint bitmapPaint = createBitmapPaint();
        bitmapPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        return bitmapPaint;
    }
    
    public static int[] getBitmapPixels(final Bitmap bitmap) {
        final int[] array = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(array, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return array;
    }
    
    public static int getPixel(final int[] pixels, final int x, final int y, final int width) {
        return pixels[x + y * width];
    }
    
    public static void setPixel(final int[] pixels, final int x, final int y, final int color, final int width) {
        pixels[x + y * width] = color;
    }
    
    static {
        BitmapUtils.paint = new Paint();
        final float[] array = new float[20];
        final float[] array2 = array;
        array2[0] = -1.0f;
        array2[1] = 0.0f;
        array2[3] = (array2[2] = 0.0f);
        array2[4] = 255.0f;
        array2[5] = 0.0f;
        array2[6] = -1.0f;
        array2[8] = (array2[7] = 0.0f);
        array2[9] = 255.0f;
        array2[11] = (array2[10] = 0.0f);
        array2[12] = -1.0f;
        array2[13] = 0.0f;
        array2[14] = 255.0f;
        array2[15] = 0.0f;
        array2[17] = (array2[16] = 0.0f);
        array2[18] = 1.0f;
        array2[19] = 0.0f;
        final ColorMatrix colorMatrix = new ColorMatrix(array);
        INVERTED_COLOR_FILTER = (ColorFilter)new ColorMatrixColorFilter(colorMatrix);
        BitmapUtils.c = true;
    }
}
