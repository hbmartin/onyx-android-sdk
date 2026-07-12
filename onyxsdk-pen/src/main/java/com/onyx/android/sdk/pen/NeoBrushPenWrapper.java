/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  com.onyx.android.sdk.data.note.TouchPoint
 */
package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.NeoPenUtils;
import com.onyx.android.sdk.pen.PenUtils;
import java.util.List;

public class NeoBrushPenWrapper {
    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        void var2_2;
        List<TouchPoint> list;
        return NeoBrushPenWrapper.computeStrokePoints(strokeWidth, list, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(float strokeWidth, List<TouchPoint> points, float maxTouchPressure) {
        void var2_2;
        float f;
        void var1_1;
        return NeoPenUtils.computeStrokePoints(1, (List<TouchPoint>)var1_1, f, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> points, float strokeWidth, float maxTouchPressure, boolean erase) {
        void var5_5;
        void var1_1;
        Canvas canvas2;
        void var4_4;
        void var3_3;
        List<TouchPoint> list = NeoBrushPenWrapper.computeStrokePoints(points, (float)var3_3, (float)var4_4);
        if (list == null) {
            return;
        }
        PenUtils.drawStrokeByPointSize(canvas2, (Paint)var1_1, list, (boolean)var5_5);
    }
}

