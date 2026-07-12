package com.onyx.android.sdk.base.data;

import com.onyx.android.sdk.base.lite.utils.GeometryUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\f\u001a\u00020\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0003J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0014\u0010\u0017\u001a\u00020\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0019J\u0014\u0010\u001a\u001a\u00020\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0019J\u000e\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0000J\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u0003J\u0006\u0010\u001f\u001a\u00020\u0000J\t\u0010 \u001a\u00020!HÖ\u0001J\u0016\u0010\"\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010#\u001a\u00020\u0011J\u0016\u0010$\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010%\u001a\u00020&J\u000e\u0010'\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0003J\t\u0010(\u001a\u00020)HÖ\u0001J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006+"}, d2 = {"Lcom/onyx/android/sdk/base/data/TouchPointPair;", "", "p1", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "p2", "(Lcom/onyx/android/sdk/base/data/TouchPoint;Lcom/onyx/android/sdk/base/data/TouchPoint;)V", "getP1", "()Lcom/onyx/android/sdk/base/data/TouchPoint;", "setP1", "(Lcom/onyx/android/sdk/base/data/TouchPoint;)V", "getP2", "setP2", "center", "component1", "component2", "copy", "direction", "", "distance", "p", "equals", "", "other", "farthestPoint", "points", "", "farthestPoints", "findCrossPoint", "outPoint", "findPointByDistance", "crossPoint", "getCentralVerticalLine", "hashCode", "", "isSameSide", "length", "rotate", "rad", "", "signedDistance", "toString", "", "translate", "sdk-baselite_release"})
public final /* data */ class TouchPointPair {

    @NotNull
    private TouchPoint p1;

    @NotNull
    private TouchPoint p2;

    @NotNull
    public final TouchPoint component1() {
        return this.p1;
    }

    @NotNull
    public final TouchPoint component2() {
        return this.p2;
    }

    @NotNull
    public final TouchPointPair copy(@NotNull TouchPoint p1, @NotNull TouchPoint p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return new TouchPointPair(p1, p2);
    }

    public static /* synthetic */ TouchPointPair copy$default(TouchPointPair touchPointPair, TouchPoint touchPoint, TouchPoint touchPoint2, int i, Object obj) {
        if ((i & 1) != 0) {
            touchPoint = touchPointPair.p1;
        }
        if ((i & 2) != 0) {
            touchPoint2 = touchPointPair.p2;
        }
        return touchPointPair.copy(touchPoint, touchPoint2);
    }

    @NotNull
    public String toString() {
        return "TouchPointPair(p1=" + this.p1 + ", p2=" + this.p2 + ')';
    }

    public int hashCode() {
        int result = this.p1.hashCode();
        return (result * 31) + this.p2.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TouchPointPair)) {
            return false;
        }
        TouchPointPair touchPointPair = (TouchPointPair) other;
        return Intrinsics.areEqual(this.p1, touchPointPair.p1) && Intrinsics.areEqual(this.p2, touchPointPair.p2);
    }

    public TouchPointPair(@NotNull TouchPoint p1, @NotNull TouchPoint p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        this.p1 = p1;
        this.p2 = p2;
    }

    @NotNull
    public final TouchPoint getP1() {
        return this.p1;
    }

    public final void setP1(@NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "<set-?>");
        this.p1 = touchPoint;
    }

    @NotNull
    public final TouchPoint getP2() {
        return this.p2;
    }

    public final void setP2(@NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "<set-?>");
        this.p2 = touchPoint;
    }

    public final float length() {
        return GeometryUtils.INSTANCE.distance(this.p1, this.p2);
    }

    public final float direction() {
        if (this.p2.y == this.p1.y) {
            if (this.p2.x > this.p1.x) {
                return 0.0f;
            }
            if (this.p2.x < this.p1.x) {
                return 3.1415927f;
            }
        }
        float rad = (float) Math.atan(GeometryUtils.INSTANCE.slope(this.p1, this.p2));
        if (this.p2.y > this.p1.y && rad < 0.0f) {
            rad += 3.1415927f;
        }
        if (this.p2.y < this.p1.y && rad > 0.0f) {
            rad -= 3.1415927f;
        }
        return rad;
    }

    @NotNull
    public final TouchPoint center() {
        return GeometryUtils.INSTANCE.center(this.p1, this.p2);
    }

    public final float distance(@NotNull TouchPoint p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return Math.abs(signedDistance(p));
    }

    public final float signedDistance(@NotNull TouchPoint p) {
        Intrinsics.checkNotNullParameter(p, "p");
        TouchPointPair pLine = new TouchPointPair(this.p1, p);
        float theta1 = direction();
        float theta2 = pLine.direction();
        float theta3 = theta2 - theta1;
        float distance = pLine.length() * ((float) Math.sin(theta3));
        return distance;
    }

    @NotNull
    public final TouchPoint farthestPoint(@NotNull List<? extends TouchPoint> points) {
        Intrinsics.checkNotNullParameter(points, "points");
        TouchPoint touchPoint = this.p1;
        float max = 0.0f;
        List<? extends TouchPoint> $this$forEach$iv = points;
        for (Object element$iv : $this$forEach$iv) {
            TouchPoint p = (TouchPoint) element$iv;
            float distance = distance(p);
            if (distance > max) {
                max = distance;
                touchPoint = p;
            }
        }
        return touchPoint;
    }

    @NotNull
    public final TouchPointPair farthestPoints(@NotNull List<? extends TouchPoint> points) {
        Intrinsics.checkNotNullParameter(points, "points");
        TouchPoint empty = TouchPoint.INSTANCE.getEmpty();
        TouchPoint empty2 = TouchPoint.INSTANCE.getEmpty();
        float positiveMax = 0.0f;
        float negativeMax = 0.0f;
        List<? extends TouchPoint> $this$forEach$iv = points;
        for (Object element$iv : $this$forEach$iv) {
            TouchPoint p = (TouchPoint) element$iv;
            float distance = signedDistance(p);
            if (distance < 0.0f && distance < negativeMax) {
                negativeMax = distance;
                empty2 = p;
            }
            if (distance > 0.0f && distance > positiveMax) {
                positiveMax = distance;
                empty = p;
            }
        }
        return new TouchPointPair(empty2, empty);
    }

    @NotNull
    public final TouchPoint findPointByDistance(float distance, @NotNull TouchPoint crossPoint) {
        Intrinsics.checkNotNullParameter(crossPoint, "crossPoint");
        float theta = direction();
        TouchPoint $this$findPointByDistance_u24lambda_u2d2 = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        $this$findPointByDistance_u24lambda_u2d2.x = crossPoint.x - (distance * ((float) Math.sin(theta)));
        $this$findPointByDistance_u24lambda_u2d2.y = crossPoint.y + (distance * ((float) Math.cos(theta)));
        return $this$findPointByDistance_u24lambda_u2d2;
    }

    @NotNull
    public final TouchPoint findCrossPoint(@NotNull TouchPoint outPoint) {
        Intrinsics.checkNotNullParameter(outPoint, "outPoint");
        TouchPointPair line = new TouchPointPair(this.p1, outPoint);
        float theta = line.direction();
        float theta1 = direction();
        float theta2 = theta - theta1;
        float c = line.length();
        float b = c * ((float) Math.cos(theta2));
        float x = this.p1.x + (b * ((float) Math.cos(theta1)));
        float y = this.p1.y + (b * ((float) Math.sin(theta1)));
        TouchPoint $this$findCrossPoint_u24lambda_u2d3 = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        $this$findCrossPoint_u24lambda_u2d3.x = x;
        $this$findCrossPoint_u24lambda_u2d3.y = y;
        return $this$findCrossPoint_u24lambda_u2d3;
    }

    @NotNull
    public final TouchPoint findCrossPoint(@NotNull TouchPointPair other) {
        Intrinsics.checkNotNullParameter(other, "other");
        TouchPoint p3 = other.p1;
        TouchPoint p4 = other.p2;
        float k1 = (this.p2.y - this.p1.y) / (this.p2.x - this.p1.x);
        float b1 = this.p1.y - (k1 * this.p1.x);
        float k2 = (p4.y - p3.y) / (p4.x - p3.x);
        float b2 = p3.y - (k2 * p3.x);
        float x = (b2 - b1) / (k1 - k2);
        float y = (k1 * x) + b1;
        TouchPoint $this$findCrossPoint_u24lambda_u2d4 = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        $this$findCrossPoint_u24lambda_u2d4.x = x;
        $this$findCrossPoint_u24lambda_u2d4.y = y;
        return $this$findCrossPoint_u24lambda_u2d4;
    }

    public final boolean isSameSide(@NotNull TouchPoint p1, @NotNull TouchPoint p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        float distance1 = signedDistance(p1);
        float distance2 = signedDistance(p2);
        return distance1 * distance2 >= 0.0f;
    }

    @NotNull
    public final TouchPointPair translate(float distance) {
        TouchPoint p1 = findPointByDistance(distance, this.p1);
        TouchPoint p2 = findPointByDistance(distance, this.p2);
        return new TouchPointPair(p1, p2);
    }

    @NotNull
    public final TouchPointPair rotate(@NotNull TouchPoint center, @NotNull Number rad) {
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(rad, "rad");
        TouchPoint p3 = GeometryUtils.INSTANCE.rotationTransform(this.p1, center, rad.floatValue());
        TouchPoint p4 = GeometryUtils.INSTANCE.rotationTransform(this.p2, center, rad.floatValue());
        return new TouchPointPair(p3, p4);
    }

    @NotNull
    public final TouchPointPair getCentralVerticalLine() {
        return rotate(center(), Double.valueOf(1.5707963267948966d));
    }
}
