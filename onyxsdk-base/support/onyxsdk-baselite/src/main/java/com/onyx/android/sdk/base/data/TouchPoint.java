package com.onyx.android.sdk.base.data;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.onyx.android.sdk.base.lite.utils.MathUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u001c\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0016\u0018\u0000 F2\u00020\u00012\u00020\u0002:\u0001FB\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006B/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB\u000f\b\u0016\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eB\u000f\b\u0016\u0012\u0006\u0010\u000f\u001a\u00020\u0000¢\u0006\u0002\u0010\u0010BK\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0016\u001a\u00020\n¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\b\u0010\u001c\u001a\u00020\u0000H\u0016J\b\u0010\u001d\u001a\u00020\u0000H\u0016J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0000J\u0018\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010#\u001a\u00020$J\u0013\u0010%\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010&H\u0096\u0002J\u0006\u0010'\u001a\u00020\u0004J\u0006\u0010(\u001a\u00020\u0004J\u0006\u0010)\u001a\u00020\u0014J\u0006\u0010*\u001a\u00020\u0014J\u0006\u0010+\u001a\u00020\nJ\u0006\u0010,\u001a\u00020\u0004J\u0006\u0010-\u001a\u00020\u0004J\b\u0010.\u001a\u00020\u0014H\u0016J\u0010\u0010/\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u0006\u00100\u001a\u00020\u001fJ\u0010\u00101\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u0016\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020\u00042\u0006\u00104\u001a\u00020\u0004J\u0016\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u00020\u0014J\u0010\u00105\u001a\u0004\u0018\u00010\u00002\u0006\u00106\u001a\u00020\u0004J\u0016\u00105\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u0004J\u000e\u00109\u001a\u00020\u00192\u0006\u0010:\u001a\u00020\u0000J\u000e\u0010;\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014J\u000e\u0010?\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010@\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010A\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004J\b\u0010B\u001a\u00020CH\u0016J\u0012\u0010D\u001a\u00020\u00002\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0016\u0010E\u001a\u00020\u00192\u0006\u00103\u001a\u00020\u00042\u0006\u00104\u001a\u00020\u0004R\u0012\u0010\u0011\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lcom/onyx/android/sdk/base/data/TouchPoint;", "Ljava/io/Serializable;", "", "x", "", "y", "(FF)V", "p", "s", "t", "", "(FFFFJ)V", "motionEvent", "Landroid/view/MotionEvent;", "(Landroid/view/MotionEvent;)V", "source", "(Lcom/onyx/android/sdk/base/data/TouchPoint;)V", "pressure", "size", "tiltX", "", "tiltY", "timestamp", "(FFFFIIJ)V", "applyMatrix", "", "matrix", "Landroid/graphics/Matrix;", "clone", "copy", "equalXY", "", "o", "equalXYApproximately", "other", "threshold", "", "equals", "", "getPressure", "getSize", "getTiltX", "getTiltY", "getTimestamp", "getX", "getY", "hashCode", "invertMatrix", "isEmpty", "mapMatrix", "offset", "dx", "dy", "scale", "scaleValue", "scaleX", "scaleY", "set", "point", "setPressure", "setSize", "setTiltX", "setTiltY", "setTimestamp", "setX", "setY", "toString", "", "transform", "translate", "Companion", "sdk-baselite_release"})
public class TouchPoint implements Serializable, Cloneable {

    @JvmField
    public float x;

    @JvmField
    public float y;

    @JvmField
    public float pressure;

    @JvmField
    public float size;

    @JvmField
    public int tiltX;

    @JvmField
    public int tiltY;

    @JvmField
    public long timestamp;
    public static final int OBJECT_BYTE_COUNT = 32;

    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final TouchPoint Empty = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, 127, null);

    public TouchPoint() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
    }

    public TouchPoint(float x, float y, float pressure, float size, int tiltX, int tiltY, long timestamp) {
        this.x = x;
        this.y = y;
        this.pressure = pressure;
        this.size = size;
        this.tiltX = tiltX;
        this.tiltY = tiltY;
        this.timestamp = timestamp;
    }

    public /* synthetic */ TouchPoint(float f, float f2, float f3, float f4, int i, int i2, long j, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0.0f : f, (i3 & 2) != 0 ? 0.0f : f2, (i3 & 4) != 0 ? 0.0f : f3, (i3 & 8) != 0 ? 0.0f : f4, (i3 & 16) != 0 ? 0 : i, (i3 & 32) != 0 ? 0 : i2, (i3 & 64) != 0 ? 0L : j);
    }

    public TouchPoint(float x, float y) {
        this(x, y, 0.0f, 0.0f, 0, 0, System.currentTimeMillis(), 60, null);
    }

    public TouchPoint(float x, float y, float p, float s, long t) {
        this(x, y, p, s, 0, 0, t, 48, null);
    }

    public TouchPoint(@NotNull MotionEvent motionEvent) {
        this(motionEvent.getX(), motionEvent.getY(), motionEvent.getPressure(), motionEvent.getSize(), 0, 0, motionEvent.getEventTime(), 48, null);
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        Pair<Integer, Integer> pairComputeAndroidTiltXY = INSTANCE.computeAndroidTiltXY(motionEvent.getAxisValue(25), motionEvent.getAxisValue(8));
        int tx = ((Number) pairComputeAndroidTiltXY.component1()).intValue();
        int ty = ((Number) pairComputeAndroidTiltXY.component2()).intValue();
        this.tiltX = tx;
        this.tiltY = ty;
    }

    public TouchPoint(@NotNull TouchPoint source) {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        Intrinsics.checkNotNullParameter(source, "source");
        set(source);
    }

    public final float getX() {
        return this.x;
    }

    @NotNull
    public final TouchPoint setX(float x) {
        this.x = x;
        return this;
    }

    public final float getY() {
        return this.y;
    }

    @NotNull
    public final TouchPoint setY(float y) {
        this.y = y;
        return this;
    }

    public final float getPressure() {
        return this.pressure;
    }

    @NotNull
    public final TouchPoint setPressure(float p) {
        this.pressure = p;
        return this;
    }

    public final float getSize() {
        return this.size;
    }

    @NotNull
    public final TouchPoint setSize(float s) {
        this.size = s;
        return this;
    }

    public final int getTiltX() {
        return this.tiltX;
    }

    @NotNull
    public final TouchPoint setTiltX(int t) {
        this.tiltX = t;
        return this;
    }

    public final int getTiltY() {
        return this.tiltY;
    }

    @NotNull
    public final TouchPoint setTiltY(int t) {
        this.tiltY = t;
        return this;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    @NotNull
    public final TouchPoint setTimestamp(long t) {
        this.timestamp = t;
        return this;
    }

    public final void set(@NotNull TouchPoint point) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.x = point.x;
        this.y = point.y;
        this.pressure = point.pressure;
        this.size = point.size;
        this.tiltX = point.tiltX;
        this.tiltY = point.tiltY;
        this.timestamp = point.timestamp;
    }

    public final void offset(int dx, int dy) {
        offset(dx, dy);
    }

    public final void offset(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public final void invertMatrix(@Nullable Matrix matrix) {
        if (matrix == null || MathUtils.INSTANCE.isEmptyMatrix(matrix)) {
            return;
        }
        Matrix invertMatrix = new Matrix();
        if (!matrix.invert(invertMatrix)) {
            return;
        }
        applyMatrix(invertMatrix);
    }

    public final void applyMatrix(@Nullable Matrix matrix) {
        if (matrix == null || MathUtils.INSTANCE.isEmptyMatrix(matrix)) {
            return;
        }
        float[] pts = {this.x, this.y};
        matrix.mapPoints(pts);
        this.x = pts[0];
        this.y = pts[1];
    }

    public final void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    @Nullable
    public final TouchPoint scale(float scaleValue) {
        this.x *= scaleValue;
        this.y *= scaleValue;
        return this;
    }

    @NotNull
    public final TouchPoint scale(float scaleX, float scaleY) {
        this.x *= scaleX;
        this.y *= scaleY;
        return this;
    }

    public final void mapMatrix(@Nullable Matrix matrix) {
        if (matrix == null) {
            return;
        }
        float[] pts = {this.x, this.y};
        matrix.mapPoints(pts);
        this.x = pts[0];
        this.y = pts[1];
    }

    @NotNull
    public TouchPoint copy() {
        return new TouchPoint(this);
    }

    @NotNull
    public TouchPoint clone() {
        return new TouchPoint(this);
    }

    @NotNull
    public String toString() {
        return "x:" + this.x + " y:" + this.y + " pressure:" + this.pressure + " size:" + this.size;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Intrinsics.areEqual(getClass(), o.getClass())) {
            return false;
        }
        TouchPoint point = (TouchPoint) o;
        return Float.compare(point.x, this.x) == 0 && Float.compare(point.y, this.y) == 0 && Float.compare(point.pressure, this.pressure) == 0 && Float.compare(point.size, this.size) == 0 && this.timestamp == point.timestamp;
    }

    public final boolean equalXY(@NotNull TouchPoint o) {
        Intrinsics.checkNotNullParameter(o, "o");
        if (this.x == o.x) {
            if (this.y == o.y) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean equalXYApproximately$default(TouchPoint touchPoint, TouchPoint touchPoint2, Number number, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: equalXYApproximately");
        }
        if ((i & 2) != 0) {
            number = Float.valueOf(1.0f);
        }
        return touchPoint.equalXYApproximately(touchPoint2, number);
    }

    public final boolean equalXYApproximately(@NotNull TouchPoint other, @NotNull Number threshold) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(threshold, "threshold");
        return INSTANCE.distance(this, other) < ((double) threshold.floatValue());
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.pressure), Float.valueOf(this.size), Long.valueOf(this.timestamp));
    }

    public final boolean isEmpty() {
        return Intrinsics.areEqual(this, Empty);
    }

    @NotNull
    public TouchPoint transform(@Nullable Matrix matrix) {
        TouchPoint transformed = copy();
        transformed.applyMatrix(matrix);
        return transformed;
    }

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000e\u001a\u00020\b2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0010J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fH\u0002J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\bJ\u0018\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\fJ\u0016\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004J&\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u0016\u0010'\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004J&\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\fJ\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010.\u001a\u00020/J\u001e\u00100\u001a\u00020\b2\u0006\u00101\u001a\u00020\u00042\u0006\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00020\bJ\u001a\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020/2\b\u00107\u001a\u0004\u0018\u000108J\u0014\u00109\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010:\u001a\u00020;J\u0016\u0010<\u001a\u0004\u0018\u0001052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010J\u001e\u0010<\u001a\u0004\u0018\u0001052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010>\u001a\u00020\fJ$\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\b\u00107\u001a\u0004\u0018\u0001082\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010J\u0016\u0010?\u001a\u0004\u0018\u0001052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010J\u001c\u0010?\u001a\u0002052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010>\u001a\u00020\fJ\"\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010>\u001a\u00020\fJ\u0016\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u001d2\u0006\u0010E\u001a\u00020\u0004J\u0016\u0010F\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00042\u0006\u0010E\u001a\u00020\u001dJ \u0010G\u001a\u00020\u001d2\u0006\u0010H\u001a\u00020\u00042\u0006\u0010I\u001a\u00020J2\b\b\u0002\u0010\u001e\u001a\u00020\fJ\u0012\u0010K\u001a\u00020L*\u00020\u00042\u0006\u00101\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lcom/onyx/android/sdk/base/data/TouchPoint$Companion;", "", "()V", "Empty", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "getEmpty", "()Lcom/onyx/android/sdk/base/data/TouchPoint;", "OBJECT_BYTE_COUNT", "", "computeAndroidTiltXY", "Lkotlin/Pair;", "tilt", "", "orientation", "computePointByteSize", "touchPointList", "", "floatEquals", "", "lhs", "rhs", "tolerance", "fromHistorical", "event", "Landroid/view/MotionEvent;", "motionEvent", "i", "fromTinyPoint", "tinyPoint", "Lcom/onyx/android/sdk/base/data/TinyPoint;", "maxPressure", "getHorizontalAngle", "start", "end", "getIntersection", "a", "b", "c", "d", "getPointAngle", "getPointDistance", "x1", "y1", "x2", "y2", "getRectPoints", "rectF", "Landroid/graphics/RectF;", "getTouchPointCoordinatesHashCode", "touchPoint", "maxXY", "blockSize", "getTransformRectPoints", "", "originRect", "matrix", "Landroid/graphics/Matrix;", "parseTouchPointsFromPath", "path", "Landroid/graphics/Path;", "realPointArray", "points", "pointScale", "renderPointArray", "touchPoints", "scalePointList", "size2Tilt", "", "srcPoint", "dstPoint", "tilt2Size", "toTinyPoint", "point", "compareTime", "", "distance", "", "sdk-baselite_release"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final TouchPoint getEmpty() {
            return TouchPoint.Empty;
        }

        @NotNull
        public final List<TouchPoint> fromHistorical(@NotNull MotionEvent event) {
            Intrinsics.checkNotNullParameter(event, "event");
            int size = event.getHistorySize();
            List list = new ArrayList();
            int i = 0;
            while (i < size) {
                int i2 = i;
                i++;
                list.add(fromHistorical(event, i2));
            }
            list.add(new TouchPoint(event));
            return list;
        }

        @NotNull
        public final TouchPoint fromHistorical(@NotNull MotionEvent motionEvent, int i) {
            Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
            return new TouchPoint(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), motionEvent.getHistoricalPressure(i), motionEvent.getHistoricalSize(i), motionEvent.getHistoricalEventTime(i));
        }

        @NotNull
        public final List<TouchPoint> renderPointArray(@Nullable Matrix matrix, @NotNull List<? extends TouchPoint> touchPoints) {
            Intrinsics.checkNotNullParameter(touchPoints, "touchPoints");
            if (matrix == null || matrix.isIdentity()) {
                return (List<TouchPoint>) (List<?>) touchPoints;
            }
            float[] src = new float[2];
            float[] dst = new float[2];
            List result = new ArrayList();
            int i = 0;
            int size = touchPoints.size();
            while (i < size) {
                int i2 = i;
                i++;
                TouchPoint iterator = (TouchPoint) touchPoints.get(i2);
                src[0] = iterator.x;
                dst[0] = src[0];
                src[1] = iterator.y;
                dst[1] = src[1];
                matrix.mapPoints(dst, src);
                TouchPoint touchPoint = new TouchPoint(iterator);
                touchPoint.x = dst[0];
                touchPoint.y = dst[1];
                result.add(touchPoint);
            }
            return result;
        }

        public final float getPointAngle(@NotNull TouchPoint start, @NotNull TouchPoint end) {
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(end, "end");
            double tan = Math.atan2(end.x - start.x, start.y - end.y);
            return (float) ((((double) 180) * tan) / 3.141592653589793d);
        }

        public final float getHorizontalAngle(@NotNull TouchPoint start, @NotNull TouchPoint end) {
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(end, "end");
            return 0.0f;
        }

        public final float getPointDistance(float x1, float y1, float x2, float y2) {
            return (float) Math.sqrt(Math.pow(x1 - x2, 2.0d) + Math.pow(y1 - y2, 2.0d));
        }

        @Nullable
        public final float[] getTransformRectPoints(@NotNull RectF originRect, @Nullable Matrix matrix) {
            Intrinsics.checkNotNullParameter(originRect, "originRect");
            float[] src = new float[8];
            float[] dst = new float[8];
            float[] lst = new float[8];
            float[] result = new float[8];
            int index = 0 + 1;
            src[0] = originRect.left;
            int index2 = index + 1;
            src[index] = originRect.top;
            int index3 = index2 + 1;
            src[index2] = originRect.right;
            int index4 = index3 + 1;
            src[index3] = originRect.top;
            int index5 = index4 + 1;
            src[index4] = originRect.right;
            int index6 = index5 + 1;
            src[index5] = originRect.bottom;
            int index7 = index6 + 1;
            src[index6] = originRect.left;
            int i = index7 + 1;
            src[index7] = originRect.bottom;
            if (matrix == null) {
                return src;
            }
            matrix.mapPoints(dst, src);
            matrix.mapRect(originRect);
            Matrix myMatrix = new Matrix();
            myMatrix.postRotate(90.0f, dst[0], dst[1]);
            myMatrix.mapPoints(lst, dst);
            TouchPoint intersectLeftBottom = getIntersection(new TouchPoint(dst[0], dst[1]), new TouchPoint(lst[2], lst[3]), new TouchPoint(dst[4], dst[5]), new TouchPoint(dst[6], dst[7]));
            Matrix myMatrix2 = new Matrix();
            float[] lst2 = new float[8];
            myMatrix2.postRotate(90.0f, dst[4], dst[5]);
            myMatrix2.mapPoints(lst2, dst);
            TouchPoint intersectRightTopPoint = getIntersection(new TouchPoint(dst[4], dst[5]), new TouchPoint(lst2[6], lst2[7]), new TouchPoint(dst[0], dst[1]), new TouchPoint(dst[2], dst[3]));
            if (!originRect.contains(intersectLeftBottom.x, intersectLeftBottom.y) && !originRect.contains(intersectRightTopPoint.x, intersectRightTopPoint.y)) {
                result[0] = dst[0];
                result[1] = dst[1];
                result[2] = intersectRightTopPoint.x;
                result[3] = intersectRightTopPoint.y;
                result[4] = dst[4];
                result[5] = dst[5];
                result[6] = intersectLeftBottom.x;
                result[7] = intersectLeftBottom.y;
                return result;
            }
            Matrix myMatrix3 = new Matrix();
            float[] lst3 = new float[8];
            myMatrix3.postRotate(90.0f, dst[2], dst[3]);
            myMatrix3.mapPoints(lst3, dst);
            TouchPoint intersectLeftTopPoint = getIntersection(new TouchPoint(lst3[4], lst3[5]), new TouchPoint(dst[2], dst[3]), new TouchPoint(dst[6], dst[7]), new TouchPoint(dst[0], dst[1]));
            Matrix myMatrix4 = new Matrix();
            float[] lst4 = new float[8];
            myMatrix4.postRotate(90.0f, dst[6], dst[7]);
            myMatrix4.mapPoints(lst4, dst);
            TouchPoint intersectRightBottomPoint = getIntersection(new TouchPoint(dst[6], dst[7]), new TouchPoint(lst4[0], lst4[1]), new TouchPoint(dst[2], dst[3]), new TouchPoint(dst[4], dst[5]));
            result[0] = intersectLeftTopPoint.x;
            result[1] = intersectLeftTopPoint.y;
            result[2] = dst[2];
            result[3] = dst[3];
            result[4] = intersectRightBottomPoint.x;
            result[5] = intersectRightBottomPoint.y;
            result[6] = dst[6];
            result[7] = dst[7];
            return result;
        }

        @NotNull
        public final TouchPoint getIntersection(@NotNull TouchPoint a, @NotNull TouchPoint b, @NotNull TouchPoint c, @NotNull TouchPoint d) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            Intrinsics.checkNotNullParameter(c, "c");
            Intrinsics.checkNotNullParameter(d, "d");
            TouchPoint intersection = new TouchPoint(a);
            if (((Math.abs(b.y - a.y) + Math.abs(b.x - a.x)) + Math.abs(d.y - c.y)) + Math.abs(d.x - c.x) == 0.0f) {
                return intersection;
            }
            if (Math.abs(b.y - a.y) + Math.abs(b.x - a.x) == 0.0f) {
                return intersection;
            }
            if (Math.abs(d.y - c.y) + Math.abs(d.x - c.x) == 0.0f) {
                return intersection;
            }
            if (((b.y - a.y) * (c.x - d.x)) - ((b.x - a.x) * (c.y - d.y)) == 0.0f) {
                return intersection;
            }
            intersection.x = (((((b.x - a.x) * (c.x - d.x)) * (c.y - a.y)) - ((c.x * (b.x - a.x)) * (c.y - d.y))) + ((a.x * (b.y - a.y)) * (c.x - d.x))) / (((b.y - a.y) * (c.x - d.x)) - ((b.x - a.x) * (c.y - d.y)));
            intersection.y = (((((b.y - a.y) * (c.y - d.y)) * (c.x - a.x)) - ((c.y * (b.y - a.y)) * (c.x - d.x))) + ((a.y * (b.x - a.x)) * (c.y - d.y))) / (((b.x - a.x) * (c.y - d.y)) - ((b.y - a.y) * (c.x - d.x)));
            return intersection;
        }

        @Nullable
        public final float[] renderPointArray(@NotNull List<? extends TouchPoint> points) {
            Intrinsics.checkNotNullParameter(points, "points");
            return renderPointArray(points, 1.0f);
        }

        @NotNull
        public final float[] renderPointArray(@NotNull List<? extends TouchPoint> points, float pointScale) {
            Intrinsics.checkNotNullParameter(points, "points");
            float[] array = new float[points.size() * 3];
            int i = 0;
            int size = points.size();
            while (i < size) {
                int i2 = i;
                i++;
                int index = 3 * i2;
                TouchPoint p = points.get(i2);
                array[index] = p.x * pointScale;
                array[index + 1] = p.y * pointScale;
                array[index + 2] = p.size;
            }
            return array;
        }

        @Nullable
        public final float[] realPointArray(@NotNull List<? extends TouchPoint> points) {
            Intrinsics.checkNotNullParameter(points, "points");
            return realPointArray(points, 1.0f);
        }

        @Nullable
        public final float[] realPointArray(@NotNull List<? extends TouchPoint> points, float pointScale) {
            Intrinsics.checkNotNullParameter(points, "points");
            if (points.isEmpty()) {
                return new float[0];
            }
            long firstPointTime = points.get(0).timestamp;
            float[] pointArray = new float[points.size() * 5];
            int i = 0;
            int size = points.size();
            while (i < size) {
                int i2 = i;
                i++;
                int index = 5 * i2;
                TouchPoint p = points.get(i2);
                pointArray[index] = p.x * pointScale;
                pointArray[index + 1] = p.y * pointScale;
                pointArray[index + 2] = p.size;
                pointArray[index + 3] = p.pressure;
                pointArray[index + 4] = p.timestamp - firstPointTime;
            }
            return pointArray;
        }

        @NotNull
        public final List<TouchPoint> scalePointList(@NotNull List<? extends TouchPoint> points, float pointScale) {
            Intrinsics.checkNotNullParameter(points, "points");
            List<? extends TouchPoint> $this$map$iv = points;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                TouchPoint it = (TouchPoint) item$iv$iv;
                destination$iv$iv.add(new TouchPoint(it.x * pointScale, it.y * pointScale, it.pressure, it.size, it.timestamp));
            }
            return CollectionsKt.toList((List) destination$iv$iv);
        }

        public static /* synthetic */ TouchPoint fromTinyPoint$default(Companion companion, TinyPoint tinyPoint, float f, int i, Object obj) {
            if ((i & 2) != 0) {
                f = 1.0f;
            }
            return companion.fromTinyPoint(tinyPoint, f);
        }

        @NotNull
        public final TouchPoint fromTinyPoint(@NotNull TinyPoint tinyPoint, float maxPressure) {
            Intrinsics.checkNotNullParameter(tinyPoint, "tinyPoint");
            TouchPoint touchPoint = new TouchPoint(tinyPoint.getX(), tinyPoint.getY(), tinyPoint.getPressure() / maxPressure, tinyPoint.getSize(), tinyPoint.getTime());
            size2Tilt(tinyPoint, touchPoint);
            return touchPoint;
        }

        public static /* synthetic */ TinyPoint toTinyPoint$default(Companion companion, TouchPoint touchPoint, long j, float f, int i, Object obj) {
            if ((i & 4) != 0) {
                f = 1.0f;
            }
            return companion.toTinyPoint(touchPoint, j, f);
        }

        @NotNull
        public final TinyPoint toTinyPoint(@NotNull TouchPoint point, long compareTime, float maxPressure) {
            Intrinsics.checkNotNullParameter(point, "point");
            TinyPoint tinyPoint = new TinyPoint(point.x, point.y, (short) (point.pressure * maxPressure), (short) point.size, (int) (point.timestamp - compareTime));
            tilt2Size(point, tinyPoint);
            return tinyPoint;
        }

        public final void size2Tilt(@NotNull TinyPoint srcPoint, @NotNull TouchPoint dstPoint) {
            Intrinsics.checkNotNullParameter(srcPoint, "srcPoint");
            Intrinsics.checkNotNullParameter(dstPoint, "dstPoint");
            dstPoint.tiltX = (byte) srcPoint.getSize();
            dstPoint.tiltY = (byte) (srcPoint.getSize() >> 8);
        }

        public final void tilt2Size(@NotNull TouchPoint srcPoint, @NotNull TinyPoint dstPoint) {
            Intrinsics.checkNotNullParameter(srcPoint, "srcPoint");
            Intrinsics.checkNotNullParameter(dstPoint, "dstPoint");
            int size = (srcPoint.tiltY << 8) | (srcPoint.tiltX & 255);
            dstPoint.setSize((short) size);
        }

        public final int getTouchPointCoordinatesHashCode(@NotNull TouchPoint touchPoint, int maxXY, int blockSize) {
            Intrinsics.checkNotNullParameter(touchPoint, "touchPoint");
            int result = (int) (touchPoint.x / blockSize);
            return (int) ((maxXY * result) + (touchPoint.y / blockSize));
        }

        public final int computePointByteSize(@NotNull List<? extends TouchPoint> touchPointList) {
            Intrinsics.checkNotNullParameter(touchPointList, "touchPointList");
            if (touchPointList.isEmpty()) {
                return 0;
            }
            return 32 * touchPointList.size();
        }

        @NotNull
        public final List<TouchPoint> getRectPoints(@NotNull RectF rectF) {
            Intrinsics.checkNotNullParameter(rectF, "rectF");
            List list = new ArrayList();
            list.add(new TouchPoint(rectF.left, rectF.top));
            list.add(new TouchPoint(rectF.right, rectF.top));
            list.add(new TouchPoint(rectF.right, rectF.bottom));
            list.add(new TouchPoint(rectF.left, rectF.bottom));
            return list;
        }

        private final boolean floatEquals(float lhs, float rhs, float tolerance) {
            return Math.abs(lhs - rhs) < tolerance;
        }

        @NotNull
        public final Pair<Integer, Integer> computeAndroidTiltXY(float tilt, float orientation) {
            if (floatEquals(orientation, 0.0f, 0.001f)) {
                return new Pair<>(0, Integer.valueOf((int) Math.rint(Math.toDegrees((float) Math.acos((float) Math.cos(tilt))))));
            }
            if (floatEquals(orientation, 1.5707964f, 0.001f)) {
                return new Pair<>(Integer.valueOf((int) Math.rint(-Math.toDegrees((float) Math.acos((float) Math.cos(tilt))))), 0);
            }
            if (floatEquals(orientation, -1.5707964f, 0.001f)) {
                return new Pair<>(Integer.valueOf((int) Math.rint(Math.toDegrees((float) Math.acos((float) Math.cos(tilt))))), 0);
            }
            if (floatEquals(orientation, -3.1415927f, 0.001f)) {
                return new Pair<>(0, Integer.valueOf((int) Math.rint(-Math.toDegrees((float) Math.acos((float) Math.cos(tilt))))));
            }
            float cosT = (float) Math.cos(tilt);
            float tanO = (float) Math.tan(orientation);
            float tt = cosT * cosT;
            float oo = tanO * tanO;
            float b = 1 - oo;
            float bb = b * b;
            float c = -tt;
            float x = ((-b) + ((float) Math.sqrt(bb - ((4 * oo) * c)))) / (2 * oo);
            float sqrtX = (float) Math.sqrt(x);
            float tyRadian = (float) Math.acos(sqrtX);
            float txRadian = (float) Math.acos(cosT / ((float) Math.cos(tyRadian)));
            int tiltYDegrees = (int) Math.rint(Math.toDegrees(tyRadian));
            int tiltXDegrees = (int) Math.rint(Math.toDegrees(txRadian));
            if (-3.141592653589793d <= orientation && orientation <= -1.5707963267948966d) {
                tiltYDegrees = -tiltYDegrees;
            } else if (-1.5707963267948966d >= orientation || orientation > 0.0f) {
                if (0.0f < orientation && orientation <= 1.5707963267948966d) {
                    tiltXDegrees = -tiltXDegrees;
                } else if (1.5707963267948966d < orientation && orientation <= 3.141592653589793d) {
                    tiltXDegrees = -tiltXDegrees;
                    tiltYDegrees = -tiltYDegrees;
                }
            }
            return new Pair<>(Integer.valueOf(tiltXDegrees), Integer.valueOf(tiltYDegrees));
        }

        @NotNull
        public final List<TouchPoint> parseTouchPointsFromPath(@NotNull Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            List pointsList = new ArrayList();
            float[] pos = new float[2];
            PathMeasure pathMeasure = new PathMeasure(path, false);
            do {
                float length = pathMeasure.getLength();
                float i = 0.0f;
                while (length > 0.0f && i <= length) {
                    if (pathMeasure.getPosTan(i, pos, null)) {
                        pointsList.add(new TouchPoint(pos[0], pos[1], 1.0f, 0.0f, 0, 0, 0L, 120, null));
                    }
                    i += 2;
                    if (i > length) {
                        if (!(i == length + ((float) 2))) {
                            i = length;
                        }
                    }
                }
                boolean ret = pathMeasure.getPosTan(length, pos, null);
                if (ret) {
                    TouchPoint originLast = new TouchPoint(pos[0], pos[1]);
                    TouchPoint currentLast = (TouchPoint) CollectionsKt.lastOrNull(pointsList);
                    if (currentLast != null && !currentLast.equalXY(originLast)) {
                        pointsList.add(originLast);
                    }
                }
            } while (pathMeasure.nextContour());
            return pointsList;
        }

        public final double distance(@NotNull TouchPoint $this$distance, @NotNull TouchPoint touchPoint) {
            Intrinsics.checkNotNullParameter($this$distance, "<this>");
            Intrinsics.checkNotNullParameter(touchPoint, "touchPoint");
            return MathUtils.INSTANCE.distance($this$distance.x, $this$distance.y, touchPoint.x, touchPoint.y);
        }
    }
}
