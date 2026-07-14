package com.onyx.android.sdk.pennative;

import android.graphics.Bitmap;

/** Test-only declarations for black-box calls into a separately supplied Notable library. */
public final class NeoPenNative {
    public static final NeoPenNative INSTANCE = new NeoPenNative();

    private NeoPenNative() {}

    private native long nativeCreateLogger();
    private native void nativeRegisterLogger(long logger);
    private native void nativeSetLogLevel(int level);
    private native long nativeCreatePen(int penType, PenConfig config);
    private native void nativeDestroyPen(long pen);
    private native PenInkResult nativeOnPenDown(long pen, double[] point, boolean repaint);
    private native PenInkResult nativeOnPenMove(
            long pen, double[] points, double[] prediction, boolean repaint);
    private native PenInkResult nativeOnPenUp(long pen, double[] point, boolean repaint);
    private native boolean nativeSetBitmapColor(Bitmap bitmap);

    public long createLogger() {
        long logger = nativeCreateLogger();
        setLogLevel(2);
        return logger;
    }

    public void registerLogger(long logger) {
        nativeRegisterLogger(logger);
    }

    public void setLogLevel(int level) {
        nativeSetLogLevel(level);
    }

    public long createPen(int penType, PenConfig config) {
        return nativeCreatePen(penType, config);
    }

    public void destroyPen(long pen) {
        nativeDestroyPen(pen);
    }

    public PenInkResult onPenDown(long pen, double[] point, boolean repaint) {
        return nativeOnPenDown(pen, point, repaint);
    }

    public PenInkResult onPenMove(
            long pen, double[] points, double[] prediction, boolean repaint) {
        return nativeOnPenMove(pen, points, prediction, repaint);
    }

    public PenInkResult onPenUp(long pen, double[] point, boolean repaint) {
        return nativeOnPenUp(pen, point, repaint);
    }

    public boolean setBitmapColor(Bitmap bitmap) {
        return nativeSetBitmapColor(bitmap);
    }
}
