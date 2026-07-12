package com.onyx.android.sdk.base.data;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TinyPoint.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/data/TinyPoint.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J;\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020\tHÖ\u0001J\t\u0010%\u001a\u00020&HÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018¨\u0006'"}, d2 = {"Lcom/onyx/android/sdk/base/data/TinyPoint;", "", "x", "", "y", "pressure", "", "size", "time", "", "(FFSSI)V", "getPressure", "()S", "setPressure", "(S)V", "getSize", "setSize", "getTime", "()I", "setTime", "(I)V", "getX", "()F", "setX", "(F)V", "getY", "setY", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "sdk-baselite_release"})
public final /* data */ class TinyPoint {
    private float x;
    private float y;
    private short pressure;
    private short size;
    private int time;

    public final float component1() {
        return this.x;
    }

    public final float component2() {
        return this.y;
    }

    public final short component3() {
        return this.pressure;
    }

    public final short component4() {
        return this.size;
    }

    public final int component5() {
        return this.time;
    }

    @NotNull
    public final TinyPoint copy(float x, float y, short pressure, short size, int time) {
        return new TinyPoint(x, y, pressure, size, time);
    }

    public static /* synthetic */ TinyPoint copy$default(TinyPoint tinyPoint, float f, float f2, short s, short s2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f = tinyPoint.x;
        }
        if ((i2 & 2) != 0) {
            f2 = tinyPoint.y;
        }
        if ((i2 & 4) != 0) {
            s = tinyPoint.pressure;
        }
        if ((i2 & 8) != 0) {
            s2 = tinyPoint.size;
        }
        if ((i2 & 16) != 0) {
            i = tinyPoint.time;
        }
        return tinyPoint.copy(f, f2, s, s2, i);
    }

    @NotNull
    public String toString() {
        return "TinyPoint(x=" + this.x + ", y=" + this.y + ", pressure=" + ((int) this.pressure) + ", size=" + ((int) this.size) + ", time=" + this.time + ')';
    }

    public int hashCode() {
        int result = Float.hashCode(this.x);
        return (((((((result * 31) + Float.hashCode(this.y)) * 31) + Short.hashCode(this.pressure)) * 31) + Short.hashCode(this.size)) * 31) + Integer.hashCode(this.time);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TinyPoint)) {
            return false;
        }
        TinyPoint tinyPoint = (TinyPoint) other;
        return Intrinsics.areEqual(Float.valueOf(this.x), Float.valueOf(tinyPoint.x)) && Intrinsics.areEqual(Float.valueOf(this.y), Float.valueOf(tinyPoint.y)) && this.pressure == tinyPoint.pressure && this.size == tinyPoint.size && this.time == tinyPoint.time;
    }

    public TinyPoint() {
        this(0.0f, 0.0f, (short) 0, (short) 0, 0, 31, null);
    }

    public TinyPoint(float x, float y, short pressure, short size, int time) {
        this.x = x;
        this.y = y;
        this.pressure = pressure;
        this.size = size;
        this.time = time;
    }

    public /* synthetic */ TinyPoint(float f, float f2, short s, short s2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0.0f : f, (i2 & 2) != 0 ? 0.0f : f2, (i2 & 4) != 0 ? (short) 0 : s, (i2 & 8) != 0 ? (short) 0 : s2, (i2 & 16) != 0 ? 0 : i);
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final short getPressure() {
        return this.pressure;
    }

    public final void setPressure(short s) {
        this.pressure = s;
    }

    public final short getSize() {
        return this.size;
    }

    public final void setSize(short s) {
        this.size = s;
    }

    public final int getTime() {
        return this.time;
    }

    public final void setTime(int i) {
        this.time = i;
    }
}
