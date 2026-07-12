/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.pen.NeoPenConfig
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.NeoPenConfig;
import com.onyx.android.sdk.pen.NeoRenderPoint;
import com.onyx.android.sdk.pen.PenUtils;
import java.util.List;

public class NeoPenWrapper {
    private static NeoPenConfig a;

    private static native boolean nativeInitPen(NeoPenConfig var0);

    private static native void nativeDestroyPen();

    private static native NeoRenderPoint[] nativeOnPenDown(double[] var0);

    private static native NeoRenderPoint[] nativeOnPenMove(double[] var0);

    private static native NeoRenderPoint[] nativeOnPenUp(double[] var0);

    private static native NeoRenderPoint[] nativeComputeRenderPoints(double[] var0);

    private static native Bitmap[] nativeGetRenderedBitmaps();

    public static boolean initPen(NeoPenConfig config) {
        a = config;
        return NeoPenWrapper.nativeInitPen(a);
    }

    public static void destroyPen() {
        NeoPenWrapper.nativeDestroyPen();
    }

    public static NeoRenderPoint[] onPenDown(TouchPoint point) {
        return NeoPenWrapper.nativeOnPenDown(PenUtils.getPointDoubleArray(point, NeoPenWrapper.a.maxTouchPressure));
    }

    public static NeoRenderPoint[] onPenMove(TouchPoint point) {
        return NeoPenWrapper.nativeOnPenMove(PenUtils.getPointDoubleArray(point, NeoPenWrapper.a.maxTouchPressure));
    }

    public static NeoRenderPoint[] onPenUp(TouchPoint point) {
        return NeoPenWrapper.nativeOnPenUp(PenUtils.getPointDoubleArray(point, NeoPenWrapper.a.maxTouchPressure));
    }

    public static NeoRenderPoint[] computeRenderPoints(List<TouchPoint> points) {
        return NeoPenWrapper.nativeComputeRenderPoints(PenUtils.getPointDoubleArray(points, NeoPenWrapper.a.maxTouchPressure));
    }

    public static Bitmap[] getRenderedBitmaps() {
        return NeoPenWrapper.nativeGetRenderedBitmaps();
    }

    static {
        System.loadLibrary("neo_pen");
    }
}

