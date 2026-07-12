/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.NeoPenUtils;
import com.onyx.android.sdk.pen.PenUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.List;

public class NeoMarkerPenWrapper {
    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        void var2_2;
        List<TouchPoint> list;
        return NeoMarkerPenWrapper.computeStrokePoints(strokeWidth, list, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(float strokeWidth, List<TouchPoint> points, float maxTouchPressure) {
        void var2_2;
        float f;
        void var1_1;
        return NeoPenUtils.computeStrokePoints(3, (List<TouchPoint>)var1_1, f, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> list, float strokeWidth, boolean erase) {
        void var3_4;
        Rect rect;
        void var1_2;
        void var4_5;
        Canvas canvas2;
        Canvas canvas3;
        if (CollectionUtils.isNullOrEmpty(list)) {
            return;
        }
        Canvas canvas4 = canvas3;
        int n = canvas4.getWidth();
        int n2 = canvas4.getHeight();
        Bitmap bitmap = PenUtils.ensurePenBitmapCreated(new Rect(0, 0, n, n2));
        Canvas canvas5 = canvas2;
        canvas2 = new Canvas(bitmap);
        if (var4_5 != false) {
            canvas5 = canvas3;
        }
        PenUtils.drawStrokeByPointSize(canvas5, (Paint)var1_2, (List<TouchPoint>)rect, (boolean)var4_5);
        if (var4_5 != false) {
            return;
        }
        void v2 = rect;
        rect = null;
        for (TouchPoint touchPoint : v2) {
            if (rect == null) {
                Rect rect2;
                rect = rect2;
                TouchPoint touchPoint2 = touchPoint;
                float f = touchPoint2.x;
                int n3 = (int)f;
                float f2 = touchPoint2.y;
                int n4 = (int)f2;
                int n5 = (int)f;
                int n6 = (int)f2;
                rect2 = new Rect(n3, n4, n5, n6);
                continue;
            }
            rect.union((int)touchPoint.x, (int)touchPoint.y);
        }
        void v6 = var1_2;
        int n7 = -((int)(var3_4 / 2.0f));
        rect.inset(n7, n7);
        int n8 = v6.getAlpha();
        v6.setAlpha(128);
        Rect rect3 = rect;
        canvas3.drawBitmap(bitmap, rect3, rect3, (Paint)var1_2);
        var1_2.setAlpha(n8);
    }
}

