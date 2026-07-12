/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.ColorUtils
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.NeoRenderPoint;
import com.onyx.android.sdk.utils.ColorUtils;
import java.util.ArrayList;
import java.util.List;

public class PenUtils {
    public static final float ERASE_EXTRA_STROKE_WIDTH = 1.0f;
    private static Bitmap a;

    public static Bitmap ensurePenBitmapCreated(Rect drawRect) {
        Rect rect;
        Bitmap bitmap = a;
        if (bitmap == null || bitmap.getWidth() != rect.width() || a.getHeight() != rect.height()) {
            a = Bitmap.createBitmap((int)rect.width(), (int)rect.height(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        }
        a.eraseColor(0);
        return a;
    }

    public static int getColorSafely(int shapeColor) {
        int n;
        if (!ColorUtils.isNeutralColor((int)shapeColor)) {
            n = -16777216;
        }
        return n;
    }

    /*
     * WARNING - void declaration
     */
    public static void drawStrokeByPointSize(Canvas canvas, Paint paint, List<TouchPoint> points, boolean erase) {
        void var1_1;
        void var2_2;
        Paint paint2 = paint;
        int n = paint2.getColor();
        Paint.Style style = paint2.getStyle();
        Paint.Cap cap = paint2.getStrokeCap();
        Paint.Join join = paint2.getStrokeJoin();
        boolean bl = paint2.isAntiAlias();
        float f = paint2.getStrokeWidth();
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setAntiAlias(true);
        int n2 = 0;
        while (n2 < var2_2.size() - 1) {
            Canvas canvas2;
            void var3_3;
            int n3 = n2 + 1;
            float f2 = ((TouchPoint)var2_2.get((int)n3)).size;
            if (var3_3 != false) {
                f2 += 1.0f;
            }
            var1_1.setStrokeWidth(f2);
            float f3 = ((TouchPoint)var2_2.get((int)n2)).x;
            f2 = ((TouchPoint)var2_2.get((int)n2)).y;
            float f4 = ((TouchPoint)var2_2.get((int)n3)).x;
            float f5 = ((TouchPoint)var2_2.get((int)n3)).y;
            canvas2.drawLine(f3, f2, f4, f5, (Paint)var1_1);
            n2 = n3;
        }
        void v1 = var1_1;
        v1.setColor(n);
        v1.setStyle(style);
        v1.setStrokeCap(cap);
        v1.setStrokeJoin(join);
        v1.setAntiAlias(bl);
        v1.setStrokeWidth(f);
    }

    public static ArrayList<TouchPoint> toTouchPoints(NeoRenderPoint[] points) {
        ArrayList<TouchPoint> arrayList;
        ArrayList<TouchPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<TouchPoint>();
        int n = points.length;
        for (int i = 0; i < n; ++i) {
            TouchPoint touchPoint;
            NeoRenderPoint[] neoRenderPointArray;
            NeoRenderPoint neoRenderPoint = neoRenderPointArray[i];
            TouchPoint touchPoint2 = touchPoint;
            NeoRenderPoint neoRenderPoint2 = neoRenderPoint;
            float f = neoRenderPoint2.x;
            float f2 = neoRenderPoint2.y;
            float f3 = neoRenderPoint2.size;
            touchPoint = new TouchPoint(f, f2, 0.0f, f3, 0L);
            arrayList2.add(touchPoint2);
        }
        return arrayList2;
    }

    /*
     * WARNING - void declaration
     */
    public static float[] getPointArray(List<TouchPoint> points, float maxTouchPressure) {
        List<TouchPoint> list;
        float[] fArray = new float[points.size() * 5];
        for (int i = 0; i < list.size(); ++i) {
            void var1_1;
            int n = i;
            int n2 = n * 5;
            fArray[n2] = list.get((int)n).x;
            int n3 = n2 + 1;
            fArray[n3] = list.get((int)i).y;
            n3 = n2 + 2;
            fArray[n3] = list.get((int)i).pressure / var1_1;
            n3 = n2 + 3;
            fArray[n3] = list.get((int)i).size;
            fArray[n2 += 4] = list.get((int)i).timestamp;
        }
        return fArray;
    }

    /*
     * WARNING - void declaration
     */
    public static double[] getPointDoubleArray(TouchPoint point, float maxTouchPressure) {
        void var1_2;
        double d;
        TouchPoint touchPoint;
        double d2;
        double[] dArray = new double[5];
        double[] dArray2 = dArray;
        dArray2[0] = d2 = (double)touchPoint.x;
        dArray2[1] = d2 = (double)touchPoint.y;
        dArray2[2] = d = (double)(touchPoint.pressure / var1_2);
        dArray2[3] = d = (double)touchPoint.size;
        dArray2[4] = d = (double)touchPoint.tiltX;
        dArray2[5] = d = (double)touchPoint.tiltY;
        dArray[6] = d = (double)touchPoint.timestamp;
        return dArray;
    }

    /*
     * WARNING - void declaration
     */
    public static double[] getPointDoubleArray(List<TouchPoint> points, float maxTouchPressure) {
        List<TouchPoint> list;
        double[] dArray = new double[points.size() * 7];
        for (int i = 0; i < list.size(); ++i) {
            void var1_1;
            double d;
            double d2;
            int n = i;
            int n2 = n * 7;
            dArray[n2] = d2 = (double)list.get((int)n).x;
            int n3 = n2 + 1;
            dArray[n3] = d = (double)list.get((int)i).y;
            n3 = n2 + 2;
            float f = list.get((int)i).pressure / var1_1;
            dArray[n3] = Math.min(1.0f, f);
            n3 = n2 + 3;
            dArray[n3] = d = (double)list.get((int)i).size;
            n3 = n2 + 4;
            dArray[n3] = d = (double)list.get((int)i).tiltX;
            n3 = n2 + 5;
            dArray[n3] = d = (double)list.get((int)i).tiltY;
            dArray[n2 += 6] = d2 = (double)list.get((int)i).timestamp;
        }
        return dArray;
    }
}

