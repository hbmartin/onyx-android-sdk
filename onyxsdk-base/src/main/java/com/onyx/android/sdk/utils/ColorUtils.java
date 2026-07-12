package com.onyx.android.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.IntRange;
import java.util.Set;
import java.util.TreeSet;

public class ColorUtils {
    private static final int a = 2;
    private static final int b = 64;
    private static final int c = 1;
    public static String HEX_COLOR_START = "#";
    public static String HEX_CSS_COLOR_FORMAT = "#%06X";
    public static String RGBA_COLOR_START = "rgba";
    public static String RGB_COLOR_START = "RGB";

    public static class ParseResultCode {
        public static final int SUCCESS = 0;
        public static final int ILLEGAL_ARGUMENT = -1;
    }

    public static int getRedAisle(int color) {
        return (color & 16711680) >> 16;
    }

    public static int getGreenAisle(int color) {
        return (color & 65280) >> 8;
    }

    public static int getBlueAisle(int color) {
        return color & 255;
    }

    public static boolean isNeutralColor(int color) {
        return getRedAisle(color) == getGreenAisle(color) && getGreenAisle(color) == getBlueAisle(color);
    }

    public static boolean isGrayColor(int color) {
        return isNeutralColor(color) && getRedAisle(color) > 0 && getRedAisle(color) < 255;
    }

    public static int getColorWithAlpha(float alpha, int baseColor) {
        return (Math.min(255, Math.max(0, (int) (alpha * 255.0f))) << 24) + (baseColor & 16777215);
    }

    public static Bitmap grayBrush(int color, float strokeWidth) {
        int blueAisle = getBlueAisle(color) / b;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(-16777216);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-1);
        paint.setStrokeWidth(strokeWidth);
        int i = 0;
        for (int i2 = 0; i2 < 2 && i < blueAisle; i2++) {
            int i3 = 0;
            while (i3 < 2) {
                int i4 = i3 + 1;
                i3 = i4;
                canvas.drawRect(new RectF(i2, i3, i2 + 1, i4), paint);
                int i5 = i + 1;
                i = i5;
                if (i5 >= blueAisle) {
                    break;
                }
            }
        }
        return bitmapCreateBitmap;
    }

    public static String toHexColor(int color) {
        return HEX_COLOR_START + Integer.toHexString(color);
    }

    public static String toCSSHexColor(int color) {
        return String.format(HEX_CSS_COLOR_FORMAT, Integer.valueOf(color & 16777215));
    }

    public static String toCSSHexColorRGBA(int color) {
        return HEX_COLOR_START + String.format("%02X", Integer.valueOf(Color.red(color) & 255)) + String.format("%02X", Integer.valueOf(Color.green(color) & 255)) + String.format("%02X", Integer.valueOf(Color.blue(color) & 255)) + String.format("%02X", Integer.valueOf(Color.alpha(color) & 255));
    }

    public static int parseColor(String color) {
        if (StringUtils.isNullOrEmpty(color)) {
            Debug.e(ColorUtils.class, "empty color", new Object[0]);
            return -16777216;
        }
        if (!color.startsWith(RGBA_COLOR_START)) {
            return StringUtils.startsWithIgnoreCase(color, RGB_COLOR_START) ? parseRGBColor(color) : Color.parseColor(color);
        }
        String strReplace = color.replace(RGBA_COLOR_START, TTFFont.UNKNOWN_FONT_NAME).replace("(", TTFFont.UNKNOWN_FONT_NAME).replace(")", TTFFont.UNKNOWN_FONT_NAME);
        String[] strArrSplit = strReplace.split(LogUtils.ATTRIBUTE_SEPARATOR);
        if (strArrSplit.length != 4) {
            Debug.e(ColorUtils.class, "error color: " + strReplace, new Object[0]);
        }
        return parseColor(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2]), Float.parseFloat(strArrSplit[3]));
    }

    public static int safelyParseColor(String colorString, int defaultColor) {
        try {
            return Color.parseColor(colorString);
        } catch (IllegalArgumentException unused) {
            Debug.e(ColorUtils.class, "parse color error, colorString: " + colorString + ", defaultColor: " + defaultColor, new Object[0]);
            return defaultColor;
        }
    }

    public static int parseRGBColor(String color) {
        if (StringUtils.isNullOrEmpty(color)) {
            Debug.e(ColorUtils.class, "empty color", new Object[0]);
            return -16777216;
        }
        if (!StringUtils.startsWithIgnoreCase(color, RGB_COLOR_START)) {
            if (color.startsWith(HEX_COLOR_START)) {
                return Color.parseColor(color);
            }
            return -16777216;
        }
        String strReplace = color.replace(RGB_COLOR_START, TTFFont.UNKNOWN_FONT_NAME).replace("rgb", TTFFont.UNKNOWN_FONT_NAME).replace("(", TTFFont.UNKNOWN_FONT_NAME).replace(")", TTFFont.UNKNOWN_FONT_NAME);
        String[] strArrSplit = strReplace.split(LogUtils.ATTRIBUTE_SEPARATOR);
        if (strArrSplit.length != 3) {
            Debug.e(ColorUtils.class, "error color: " + strReplace, new Object[0]);
        }
        return parseRGBColor(NumberUtils.parseInt(strArrSplit[0].trim()), NumberUtils.parseInt(strArrSplit[1].trim()), NumberUtils.parseInt(strArrSplit[2].trim()));
    }

    public static int parseRGBColor(int red, int green, int blue) {
        return (red << 16) | (-16777216) | (green << 8) | blue;
    }

    public static int parseColor(float alpha, float red, float green, float blue) {
        return (((int) ((alpha * 255.0f) + 0.5f)) << 24) | (((int) ((red * 255.0f) + 0.5f)) << 16) | (((int) ((green * 255.0f) + 0.5f)) << 8) | ((int) ((blue * 255.0f) + 0.5f));
    }

    public static int convertToGray(int color) {
        return (int) ((((double) Color.red(color)) * 0.299d) + (((double) Color.green(color)) * 0.587d) + (((double) Color.blue(color)) * 0.114d));
    }

    public static int grayToRgb32(int gray) {
        return (gray << 16) | (-16777216) | (gray << 8) | gray;
    }

    public static int convertColorByGrayscale(int color, @IntRange(from = 1, to = 8) int quant_bits) {
        int iQuantizeGrayscale = quantizeGrayscale(convertToGray(color), quant_bits);
        int iIntValue = iQuantizeGrayscale;
        if (iQuantizeGrayscale < 128) {
            iIntValue = ((Integer) MathUtils.clamp(Integer.valueOf(iIntValue - 32), 0, 127)).intValue();
        }
        return grayToRgb32(iIntValue);
    }

    public static int quantizeGrayscale(int oldGray, @IntRange(from = 1, to = 8) int quant_bits) {
        int i = (1 << quant_bits) - 1;
        int i2 = 255 / i;
        int i3 = oldGray >> (8 - quant_bits);
        if (i3 == i) {
            return 255;
        }
        return i3 * i2;
    }

    public static Set<Integer> computeGrayscaleTable(@IntRange(from = 1, to = 8) int quant_bits) {
        TreeSet treeSet = new TreeSet();
        for (int i = 0; i < 255; i++) {
            treeSet.add(Integer.valueOf(quantizeGrayscale(i, quant_bits)));
        }
        return treeSet;
    }

    public static int[] loadFontEnhancementColorTableV3(int threshold) {
        int[] iArrLoadFontEnhancementColorTable = loadFontEnhancementColorTable(threshold);
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            iArr[i] = 255 - iArrLoadFontEnhancementColorTable[255 - i];
        }
        return iArr;
    }

    public static int[] loadFontEnhancementColorTable(int threshold) {
        Debug.v(ColorUtils.class, "loadColorTable, threshold = " + threshold, new Object[0]);
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            if (i < threshold) {
                iArr[i] = 0;
            } else {
                iArr[i] = quantizeGrayscale(i, 4);
            }
        }
        return iArr;
    }

    public static int limitMinSaturation(int color) {
        if (isNeutralColor(color)) {
            return color;
        }
        float[] fArr = new float[3];
        Color.colorToHSV(color, fArr);
        fArr[1] = Math.max(fArr[1], 0.55f);
        return Color.HSVToColor(fArr);
    }

    public static int applyAlphaToColor(int alphaColor, int sourceColor) {
        Color colorValueOf = Color.valueOf(alphaColor);
        Color colorValueOf2 = Color.valueOf(sourceColor);
        return Color.argb(colorValueOf.alpha(), colorValueOf2.red(), colorValueOf2.green(), colorValueOf2.blue());
    }
}
