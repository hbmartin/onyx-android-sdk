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

    public PenRenderArgs setCanvas(Canvas canvas) {
        this.a = canvas;
        return this;
    }

    public PenRenderArgs setContentRect(RectF contentRect) {
        this.k = contentRect;
        return this;
    }

    public RectF getContentRect() {
        return this.k;
    }

    public PenRenderArgs setTiltEnabled(boolean tiltEnabled) {
        this.l = tiltEnabled;
        return this;
    }

    public boolean isTiltEnabled() {
        return this.l;
    }

    public Paint getPaint() {
        return this.b;
    }

    public PenRenderArgs setPaint(Paint paint) {
        this.b = paint;
        return this;
    }

    public List<TouchPoint> getPoints() {
        return this.c;
    }

    public PenRenderArgs setPoints(List<TouchPoint> points) {
        this.c = points;
        return this;
    }

    public PenRenderArgs setPenType(int penType) {
        this.d = penType;
        return this;
    }

    public int getPenType() {
        return this.d;
    }

    public int getColor() {
        return this.e;
    }

    public PenRenderArgs setColor(int color) {
        this.e = color;
        return this;
    }

    public float getStrokeWidth() {
        return this.f;
    }

    public PenRenderArgs setStrokeWidth(float strokeWidth) {
        this.f = strokeWidth;
        return this;
    }

    public ShapeCreateArgs getCreateArgs() {
        return this.g;
    }

    public PenRenderArgs setCreateArgs(ShapeCreateArgs createArgs) {
        this.g = createArgs;
        return this;
    }

    public Matrix getScreenMatrix() {
        return this.i;
    }

    public PenRenderArgs setScreenMatrix(Matrix screenMatrix) {
        this.i = screenMatrix;
        return this;
    }

    public PenRenderArgs setRenderMatrix(Matrix renderMatrix) {
        this.h = renderMatrix;
        return this;
    }

    public Matrix getRenderMatrix() {
        return this.h;
    }

    public boolean isErase() {
        return this.j;
    }

    public PenRenderArgs setErase(boolean erase) {
        this.j = erase;
        return this;
    }
}

