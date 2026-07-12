/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen;

public class NeoRenderPoint {
    public float x;
    public float y;
    public float size;
    public int bitmapIndex;

    /*
     * WARNING - void declaration
     */
    public static NeoRenderPoint create(float x, float y, float size, int bitmapIndex) {
        void var3_3;
        void var2_2;
        void var1_1;
        float f;
        new NeoRenderPoint().x = f;
        new NeoRenderPoint().y = var1_1;
        new NeoRenderPoint().size = var2_2;
        new NeoRenderPoint().bitmapIndex = var3_3;
        return new NeoRenderPoint();
    }

    public static NeoRenderPoint create(NeoRenderPoint p) {
        NeoRenderPoint neoRenderPoint;
        NeoRenderPoint neoRenderPoint2 = neoRenderPoint;
        float f = neoRenderPoint2.y;
        float f2 = neoRenderPoint2.size;
        int n = neoRenderPoint2.bitmapIndex;
        return NeoRenderPoint.create(p.x, f, f2, n);
    }
}

