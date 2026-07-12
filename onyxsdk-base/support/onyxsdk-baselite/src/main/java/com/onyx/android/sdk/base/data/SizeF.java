package com.onyx.android.sdk.base.data;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/onyx/android/sdk/base/data/SizeF;", "", "width", "", "height", "(FF)V", "getHeight", "()F", "setHeight", "(F)V", "getWidth", "setWidth", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "sdk-baselite_release"})
public final /* data */ class SizeF {
    private float width;
    private float height;

    public final float component1() {
        return this.width;
    }

    public final float component2() {
        return this.height;
    }

    @NotNull
    public final SizeF copy(float width, float height) {
        return new SizeF(width, height);
    }

    public static /* synthetic */ SizeF copy$default(SizeF sizeF, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = sizeF.width;
        }
        if ((i & 2) != 0) {
            f2 = sizeF.height;
        }
        return sizeF.copy(f, f2);
    }

    @NotNull
    public String toString() {
        return "SizeF(width=" + this.width + ", height=" + this.height + ')';
    }

    public int hashCode() {
        int result = Float.hashCode(this.width);
        return (result * 31) + Float.hashCode(this.height);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SizeF)) {
            return false;
        }
        SizeF sizeF = (SizeF) other;
        return Intrinsics.areEqual(Float.valueOf(this.width), Float.valueOf(sizeF.width)) && Intrinsics.areEqual(Float.valueOf(this.height), Float.valueOf(sizeF.height));
    }

    public SizeF() {
        this(0.0f, 0.0f, 3, null);
    }

    public SizeF(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public /* synthetic */ SizeF(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
    }

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getHeight() {
        return this.height;
    }

    public final void setHeight(float f) {
        this.height = f;
    }
}
