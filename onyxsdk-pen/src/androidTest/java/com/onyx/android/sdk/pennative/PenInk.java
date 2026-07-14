package com.onyx.android.sdk.pennative;

import android.graphics.Bitmap;

/** Test-only return object constructed by the installed Notable JNI library. */
public final class PenInk {
    private final float[] points;
    private final int[] pointSizeArray;
    private final Bitmap[] bitmaps;

    public PenInk(float[] points, int[] pointSizeArray, Bitmap[] bitmaps) {
        this.points = points;
        this.pointSizeArray = pointSizeArray;
        this.bitmaps = bitmaps;
    }

    public static PenInk create(float[] points, int[] pointSizeArray, Bitmap[] bitmaps) {
        return new PenInk(points, pointSizeArray, bitmaps);
    }

    public float[] getPoints() { return points; }
    public int[] getPointSizeArray() { return pointSizeArray; }
    public Bitmap[] getBitmaps() { return bitmaps; }
}
