package com.onyx.android.sdk.base.data;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001b"}, d2 = {"Lcom/onyx/android/sdk/base/data/PathMeasurePointResult;", "", "()V", "currentLength", "", "getCurrentLength", "()F", "setCurrentLength", "(F)V", "direction", "getDirection", "setDirection", "point", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "getPoint", "()Lcom/onyx/android/sdk/base/data/TouchPoint;", "setPoint", "(Lcom/onyx/android/sdk/base/data/TouchPoint;)V", "segmentIndex", "", "getSegmentIndex", "()I", "setSegmentIndex", "(I)V", "totalLength", "getTotalLength", "setTotalLength", "sdk-baselite_release"})
public final class PathMeasurePointResult {

    @NotNull
    private TouchPoint point = TouchPoint.INSTANCE.getEmpty();
    private float direction;
    private float currentLength;
    private float totalLength;
    private int segmentIndex;

    @NotNull
    public final TouchPoint getPoint() {
        return this.point;
    }

    public final void setPoint(@NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "<set-?>");
        this.point = touchPoint;
    }

    public final float getDirection() {
        return this.direction;
    }

    public final void setDirection(float f) {
        this.direction = f;
    }

    public final float getCurrentLength() {
        return this.currentLength;
    }

    public final void setCurrentLength(float f) {
        this.currentLength = f;
    }

    public final float getTotalLength() {
        return this.totalLength;
    }

    public final void setTotalLength(float f) {
        this.totalLength = f;
    }

    public final int getSegmentIndex() {
        return this.segmentIndex;
    }

    public final void setSegmentIndex(int i) {
        this.segmentIndex = i;
    }
}
