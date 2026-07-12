/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  com.onyx.android.sdk.data.PenConstant
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 */
package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.NeoPenUtils;
import com.onyx.android.sdk.pen.PenUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class NeoFountainPenWrapper {
    public static final float MIN_FOUNTAIN_PEN_WIDTH = 1.0f;
    private static final int a = 10;

    /*
     * WARNING - void declaration
     */
    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> points, float displayScale, float strokeWidth, float maxTouchPressure, boolean erase) {
        void var6_6;
        void var1_1;
        Canvas canvas2;
        void var5_5;
        void var4_4;
        void var3_3;
        List<TouchPoint> list = NeoFountainPenWrapper.computeStrokePoints(points, (float)var3_3, (float)var4_4, (float)var5_5);
        if (list == null) {
            return;
        }
        PenUtils.drawStrokeByPointSize(canvas2, (Paint)var1_1, list, (boolean)var6_6);
    }

    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float displayScale, float strokeWidth, float maxTouchPressure) {
        void var1_1;
        List<TouchPoint> list;
        if (NeoFountainPenWrapper.hasPressure(points)) {
            void var3_3;
            void var2_2;
            return NeoFountainPenWrapper.computeStrokePoints(list, (float)var2_2, (float)var3_3);
        }
        return NeoFountainPenWrapper.a(list, (float)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    private static List<TouchPoint> a(List<TouchPoint> points, float scale) {
        ArrayList<TouchPoint> arrayList;
        ArrayList<TouchPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<TouchPoint>();
        for (TouchPoint touchPoint : points) {
            void var1_1;
            new TouchPoint(touchPoint).size = PenConstant.checkPenWidth((float)(touchPoint.size * var1_1));
            arrayList2.add(new TouchPoint(touchPoint));
        }
        return arrayList2;
    }

    public static boolean hasPressure(List<TouchPoint> points) {
        List<TouchPoint> list;
        if (CollectionUtils.isNullOrEmpty(points)) {
            return false;
        }
        return list.stream().limit(10L).anyMatch(o -> o.getPressure() > 0.0f);
    }

    /*
     * WARNING - void declaration
     */
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        void var2_2;
        void var1_1;
        List<TouchPoint> list;
        return NeoPenUtils.computeStrokePoints(2, list, (float)var1_1, (float)var2_2);
    }
}

