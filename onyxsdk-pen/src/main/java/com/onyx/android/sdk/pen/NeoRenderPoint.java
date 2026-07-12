package com.onyx.android.sdk.pen;

public class NeoRenderPoint {
    public float x;
    public float y;
    public float size;
    public int bitmapIndex;

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

