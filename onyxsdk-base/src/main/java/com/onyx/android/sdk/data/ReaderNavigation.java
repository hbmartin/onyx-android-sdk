package com.onyx.android.sdk.data;

import android.graphics.PointF;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ReaderNavigation.class */
public class ReaderNavigation {
    private static final float e = 30.0f;
    private static final float f = 10.0f;
    private PointF a = new PointF();
    private String b = "-1";
    private float c = e;
    private RectF d = new RectF();

    public void setCenterPoint(float x, float y) {
        PointF pointF = this.a;
        pointF.x = x;
        pointF.y = y;
    }

    public void setContentRect(float left, float top, float right, float bottom) {
        this.d.set(left, top, right, bottom);
    }

    public RectF getRectF() {
        return this.d;
    }

    public void setOrderHint(String orderHint) {
        this.b = orderHint;
    }

    public void setRadius(float radius) {
        this.c = radius;
    }

    public PointF getCenterPoint() {
        return this.a;
    }

    public String getOrderHint() {
        return this.b;
    }

    public float getRadius() {
        return this.c;
    }
}
