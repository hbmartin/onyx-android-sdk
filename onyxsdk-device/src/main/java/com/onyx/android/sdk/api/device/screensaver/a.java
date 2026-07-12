// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.api.device.screensaver;

import java.io.IOException;
import android.media.ThumbnailUtils;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

class a
{
    private static final String a = "a";
    
    public static Bitmap a(final String path) {
        final BitmapFactory.Options options;
        (options = new BitmapFactory.Options()).inPreferredConfig = Bitmap.Config.ARGB_8888;
        if (!b.a(path)) {
            return null;
        }
        return BitmapFactory.decodeFile(path, options);
    }
    
    public static boolean a(final Bitmap bitmap, final String path) {
        return a(bitmap, path, false);
    }
    
    public static boolean a(final Bitmap bitmap, final String path, final boolean overrideFilePermission) {
        try {
            final FileOutputStream fileOutputStream2;
            final FileOutputStream fileOutputStream = fileOutputStream2 = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream2);
            try {
                fileOutputStream.close();
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
    
    public static Bitmap a(final Bitmap origin, final int newWidth, final int newHeight) {
        final Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        final float n2;
        final float n = (n2 = (float)newWidth) / origin.getWidth();
        final float n3 = (float)newHeight;
        final float n4 = n2;
        final float n5 = n3 / origin.getHeight();
        final float n6 = n4 / 2.0f;
        final float n7 = n3 / 2.0f;
        final Matrix matrix2;
        final Matrix matrix = matrix2 = new Matrix();
        final float n8 = n;
        final float n9 = n5;
        matrix.setScale(n8, n9, n6, n7);
        final Canvas canvas2;
        final Canvas canvas = canvas2 = new Canvas(bitmap);
        final float n10 = n7;
        final float n11 = n6;
        final Canvas canvas3 = canvas2;
        final Matrix matrix3 = matrix2;
        canvas3.setMatrix(matrix3);
        canvas.drawBitmap(origin, n11 - origin.getWidth() / 2, n10 - origin.getHeight() / 2, new Paint(2));
        return bitmap;
    }
    
    public static void a(final Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.eraseColor(-1);
        }
    }
    
    public static void d(final Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
    
    public static boolean c(final Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
    }
    
    public static Bitmap b(final Bitmap bmp, int degrees) {
        final Matrix matrix2;
        final Matrix matrix = matrix2 = new Matrix();
        final int n = degrees;
        matrix.postRotate((float)n);
        degrees = bmp.getWidth();
        return Bitmap.createBitmap(bmp, 0, 0, degrees, bmp.getHeight(), matrix2, true);
    }
    
    public static Bitmap b(final Bitmap bmp) {
        final int width;
        final int height;
        final int[] array;
        bmp.getPixels(array = new int[(width = bmp.getWidth()) * (height = bmp.getHeight())], 0, width, 0, 0, width, height);
        final int n = -16777216;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int[] array2 = array;
                final int n2 = -16777216;
                final int n4;
                final int n3 = array[n4 = width * i + j];
                final int n5;
                array2[n4] = (0xFF000000 | (n5 = (int)(((n3 & 0xFF0000) >> 16) * 0.3 + ((n3 & 0xFF00) >> 8) * 0.59 + (n3 & 0xFF) * 0.11)) << 16 | n5 << 8 | n5);
            }
        }
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        final int n6 = width;
        final int n7 = height;
        bitmap.setPixels(array, 0, width, 0, 0, width, height);
        return ThumbnailUtils.extractThumbnail(bitmap, n6, n7);
    }
    
    public static boolean b(final Bitmap bitmap, final String dirName, final String pngFileName, final boolean isNeedOverrideDirPermission) {
        if (bitmap == null) {
            return false;
        }
        final File directory = new File(dirName);
        final File outputFile = new File(pngFileName);
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            if (isNeedOverrideDirPermission) {
                Runtime.getRuntime().exec("chmod 777 " + directory.getAbsolutePath());
                Runtime.getRuntime().exec("chmod 777 " + outputFile.getAbsolutePath());
            }
            try (final FileOutputStream output = new FileOutputStream(pngFileName)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)output);
                output.flush();
            }
            return true;
        }
        catch (final IOException error) {
            error.printStackTrace();
            return false;
        }
        finally {
            bitmap.recycle();
        }
    }
    
    public static boolean a(final Bitmap bitmap, final String dirName, final String bmpFileName, final boolean isNeedOverrideDirPermission) {
        if (bitmap == null) {
            return false;
        }
        final File directory = new File(dirName);
        final File outputFile = new File(bmpFileName);
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            if (isNeedOverrideDirPermission) {
                Runtime.getRuntime().exec("chmod 777 " + directory.getAbsolutePath());
                Runtime.getRuntime().exec("chmod 777 " + outputFile.getAbsolutePath());
            }
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();
            final int rowBytes = width * 3;
            final int padding = (4 - rowBytes % 4) % 4;
            final int imageSize = (rowBytes + padding) * height;
            final int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            try (final FileOutputStream output = new FileOutputStream(outputFile)) {
                output.write(66);
                output.write(77);
                a(output, 54L + imageSize);
                a(output, 0L);
                a(output, 54L);
                a(output, 40L);
                a(output, width);
                a(output, height);
                a(output, 1);
                a(output, 24);
                a(output, 0L);
                a(output, imageSize);
                a(output, 0L);
                a(output, 0L);
                a(output, 0L);
                a(output, 0L);
                final byte[] pad = new byte[padding];
                for (int y = height - 1; y >= 0; --y) {
                    final int rowOffset = y * width;
                    for (int x = 0; x < width; ++x) {
                        final int color = pixels[rowOffset + x];
                        output.write(color & 0xFF);
                        output.write(color >> 8 & 0xFF);
                        output.write(color >> 16 & 0xFF);
                    }
                    output.write(pad);
                }
                output.flush();
            }
            return true;
        }
        catch (final IOException error) {
            error.printStackTrace();
            return false;
        }
        finally {
            bitmap.recycle();
        }
    }
    
    public static void a(final FileOutputStream stream, final int value) throws IOException {
        stream.write(new byte[] { (byte)(value & 0xFF), (byte)(value >> 8 & 0xFF) });
    }
    
    public static void a(final FileOutputStream stream, final long value) throws IOException {
        final byte[] b = new byte[4];
        final byte[] array2;
        final byte[] array;
        (array = (array2 = b))[0] = (byte)(value & 0xFFL);
        array[1] = (byte)(value >> 8 & 0xFFL);
        array2[2] = (byte)(value >> 16 & 0xFFL);
        b[3] = (byte)(value >> 24 & 0xFFL);
        stream.write(b);
    }
    
    public static void b(final FileOutputStream stream, final long value) throws IOException {
        final byte[] b = new byte[4];
        final byte[] array2;
        final byte[] array;
        (array = (array2 = b))[0] = (byte)(value & 0xFFL);
        array[1] = (byte)(value >> 8 & 0xFFL);
        array2[2] = (byte)(value >> 16 & 0xFFL);
        b[3] = (byte)(value >> 24 & 0xFFL);
        stream.write(b);
    }
    
    public static Bitmap a(final Bitmap targetBitmap, final int color) {
        final Bitmap bitmap = Bitmap.createBitmap(targetBitmap.getWidth(), targetBitmap.getHeight(), Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        canvas.drawBitmap(targetBitmap, 0.0f, 0.0f, (Paint)null);
        canvas.save();
        return bitmap;
    }
}

