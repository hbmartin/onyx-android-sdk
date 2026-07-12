package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/NeoPenWrapper.class */
public class NeoPenWrapper {
    private static NeoPenConfig a;

    private static native boolean nativeInitPen(NeoPenConfig neoPenConfig);

    private static native void nativeDestroyPen();

    private static native NeoRenderPoint[] nativeOnPenDown(double[] dArr);

    private static native NeoRenderPoint[] nativeOnPenMove(double[] dArr);

    private static native NeoRenderPoint[] nativeOnPenUp(double[] dArr);

    private static native NeoRenderPoint[] nativeComputeRenderPoints(double[] dArr);

    private static native Bitmap[] nativeGetRenderedBitmaps();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static boolean initPen(NeoPenConfig config) {
        a = config;
        return nativeInitPen(config);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void destroyPen() {
        nativeDestroyPen();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static NeoRenderPoint[] onPenDown(TouchPoint point) {
        return nativeOnPenDown(PenUtils.getPointDoubleArray(point, a.maxTouchPressure));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static NeoRenderPoint[] onPenMove(TouchPoint point) {
        return nativeOnPenMove(PenUtils.getPointDoubleArray(point, a.maxTouchPressure));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static NeoRenderPoint[] onPenUp(TouchPoint point) {
        return nativeOnPenUp(PenUtils.getPointDoubleArray(point, a.maxTouchPressure));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static NeoRenderPoint[] computeRenderPoints(List<TouchPoint> points) {
        return nativeComputeRenderPoints(PenUtils.getPointDoubleArray(points, a.maxTouchPressure));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static Bitmap[] getRenderedBitmaps() {
        return nativeGetRenderedBitmaps();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        System.loadLibrary("neo_pen");
    }
}

