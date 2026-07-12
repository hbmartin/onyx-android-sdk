/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  com.onyx.android.sdk.data.note.ShapeCreateArgs
 *  com.onyx.android.sdk.data.note.TouchPoint
 */
package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import com.onyx.android.sdk.data.note.ShapeCreateArgs;
import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.List;

public class PenRenderArgs {
    private Canvas a;
    private Paint b;
    private List<TouchPoint> c;
    private int d;
    private int e;
    private float f;
    private ShapeCreateArgs g;
    private Matrix h;
    private Matrix i;
    private boolean j;
    private RectF k;
    private boolean l;

    public Canvas getCanvas() {
        return this.a;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setCanvas(Canvas canvas) {
        void var1_1;
        this.a = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setContentRect(RectF contentRect) {
        void var1_1;
        this.k = var1_1;
        return this;
    }

    public RectF getContentRect() {
        return this.k;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setTiltEnabled(boolean tiltEnabled) {
        void var1_1;
        this.l = var1_1;
        return this;
    }

    public boolean isTiltEnabled() {
        return this.l;
    }

    public Paint getPaint() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setPaint(Paint paint) {
        void var1_1;
        this.b = var1_1;
        return this;
    }

    public List<TouchPoint> getPoints() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setPoints(List<TouchPoint> points) {
        void var1_1;
        this.c = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setPenType(int penType) {
        void var1_1;
        this.d = var1_1;
        return this;
    }

    public int getPenType() {
        return this.d;
    }

    public int getColor() {
        return this.e;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setColor(int color) {
        void var1_1;
        this.e = var1_1;
        return this;
    }

    public float getStrokeWidth() {
        return this.f;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setStrokeWidth(float strokeWidth) {
        void var1_1;
        this.f = var1_1;
        return this;
    }

    public ShapeCreateArgs getCreateArgs() {
        return this.g;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setCreateArgs(ShapeCreateArgs createArgs) {
        void var1_1;
        this.g = var1_1;
        return this;
    }

    public Matrix getScreenMatrix() {
        return this.i;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setScreenMatrix(Matrix screenMatrix) {
        void var1_1;
        this.i = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setRenderMatrix(Matrix renderMatrix) {
        void var1_1;
        this.h = var1_1;
        return this;
    }

    public Matrix getRenderMatrix() {
        return this.h;
    }

    public boolean isErase() {
        return this.j;
    }

    /*
     * WARNING - void declaration
     */
    public PenRenderArgs setErase(boolean erase) {
        void var1_1;
        this.j = var1_1;
        return this;
    }
}

