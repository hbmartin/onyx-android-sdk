package com.onyx.android.sdk.pen;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/NeoRenderPoint.class */
public class NeoRenderPoint {
    public float x;
    public float y;
    public float size;
    public int bitmapIndex;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static NeoRenderPoint create(float x, float y, float size, int bitmapIndex) {
        NeoRenderPoint neoRenderPoint = new NeoRenderPoint();
        neoRenderPoint.x = x;
        neoRenderPoint.y = y;
        neoRenderPoint.size = size;
        neoRenderPoint.bitmapIndex = bitmapIndex;
        return neoRenderPoint;
    }

    public static NeoRenderPoint create(NeoRenderPoint p) {
        return create(p.x, p.y, p.size, p.bitmapIndex);
    }
}

