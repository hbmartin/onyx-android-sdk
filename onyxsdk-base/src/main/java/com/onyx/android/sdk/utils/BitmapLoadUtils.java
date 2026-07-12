package com.onyx.android.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.Size;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BitmapLoadUtils.class */
public class BitmapLoadUtils {
    private static final String TAG = "BitmapLoadUtils";

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BitmapLoadUtils$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            a = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static Bitmap decodeBitmapToLimitSize(String srcPath, Size srcSize, double byteCountLimit) {
        return BitmapUtils.decodeBitmap(srcPath, srcSize, a(srcSize, byteCountLimit), BitmapUtils.decodeBitmapSizeByExif(srcPath, srcSize), true);
    }

    public static Bitmap decodeBitmapInAppropriateSize(Size srcSize, double byteCountLimit, String srcPath) {
        Size sizeA = a(srcSize, byteCountLimit);
        return BitmapUtils.decodeBitmap(srcPath, sizeA.width, sizeA.height, true);
    }

    private static Size a(Size srcSize, double byteCountLimit) {
        int i = srcSize.width;
        int i2 = srcSize.height;
        double dMin = Math.min(Math.sqrt(byteCountLimit / ((double) (i * i2))), 1.0d);
        double d = dMin * ((double) i);
        double d2 = dMin * ((double) i2);
        Debug.i(TAG, "calculateSizeByByteLimit src=(" + i + ", " + i2 + "), new=(" + d + ", " + d2 + "), ratio=" + dMin, new Object[0]);
        return new Size((int) d, (int) d2);
    }

    public static Bitmap scaleBitmapToDstSize(Bitmap src, ImageView.ScaleType scaleType, int dstWidth, int dstHeight) {
        if (!BitmapUtils.isValid(src)) {
            Debug.i("scaleBitmapToDstSize with invalid resource bitmap !");
            return src;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Debug.i(TAG, "scaleBitmapToScreenSize srcWidth=%d, srcHeight=%d, dstWidth=%d, dstHeight=%d", new Object[]{Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(dstWidth), Integer.valueOf(dstHeight)});
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(-1);
        new Canvas(bitmapCreateBitmap).drawBitmap(src, b(width, height, dstWidth, dstHeight, scaleType), a(width, height, dstWidth, dstHeight, scaleType), new Paint(1));
        return bitmapCreateBitmap;
    }

    private static Rect b(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ImageView.ScaleType scaleType) {
        Rect rect = new Rect();
        if (a.a[scaleType.ordinal()] != 1) {
            float f = dstWidth;
            float f2 = srcWidth;
            float f3 = dstHeight;
            float f4 = srcHeight;
            float fMax = Math.max(f / f2, f3 / f4);
            float f5 = ((f2 * fMax) - f) / fMax;
            float f6 = ((f4 * fMax) - f3) / fMax;
            rect.set(((int) f5) / 2, ((int) f6) / 2, (int) (f2 - (f5 / 2.0f)), (int) (f4 - (f6 / 2.0f)));
        } else {
            rect.set(0, 0, srcWidth, srcHeight);
        }
        Debug.i(TAG, "getSrcRect srcWidth=%d, srcHeight=%d, dstWidth=%d, dstHeight=%d, scaleType=%s, srcRect=%s", new Object[]{Integer.valueOf(srcWidth), Integer.valueOf(srcHeight), Integer.valueOf(dstWidth), Integer.valueOf(dstHeight), scaleType.name(), rect.toString()});
        return rect;
    }

    private static RectF a(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ImageView.ScaleType scaleType) {
        RectF rectF = new RectF();
        if (a.a[scaleType.ordinal()] != 1) {
            rectF.set(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, dstWidth, dstHeight);
        } else {
            float f = dstWidth;
            float f2 = srcWidth;
            float f3 = dstHeight;
            float f4 = srcHeight;
            float fMin = Math.min(f / f2, f3 / f4);
            float f5 = f - (f2 * fMin);
            float f6 = f3 - (f4 * fMin);
            float f7 = f5 / 2.0f;
            float f8 = f6 / 2.0f;
            rectF.set(f7, f8, f - f7, f3 - f8);
        }
        Debug.i(TAG, "getDstRectF srcWidth=%d, srcHeight=%d, dstWidth=%d, dstHeight=%d, scaleType=%s, dstRect=%s", new Object[]{Integer.valueOf(srcWidth), Integer.valueOf(srcHeight), Integer.valueOf(dstWidth), Integer.valueOf(dstHeight), scaleType.name(), rectF.toString()});
        return rectF;
    }
}
