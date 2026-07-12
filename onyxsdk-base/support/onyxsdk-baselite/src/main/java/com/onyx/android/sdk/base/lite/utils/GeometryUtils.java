package com.onyx.android.sdk.base.lite.utils;

import com.onyx.android.sdk.base.data.TouchPoint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001a\u0010\f\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\b¨\u0006\u000e"}, d2 = {"Lcom/onyx/android/sdk/base/lite/utils/GeometryUtils;", "", "()V", "center", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "p1", "p2", "distance", "", "slope", "startPoint", "endPoint", "rotationTransform", "rotationInRad", "sdk-baselite_release"})
public final class GeometryUtils {

    @NotNull
    public static final GeometryUtils INSTANCE = new GeometryUtils();

    private GeometryUtils() {
    }

    @NotNull
    public final TouchPoint rotationTransform(@NotNull TouchPoint $this$rotationTransform, @NotNull TouchPoint center, float rotationInRad) {
        Intrinsics.checkNotNullParameter($this$rotationTransform, "<this>");
        Intrinsics.checkNotNullParameter(center, "center");
        TouchPoint rotated = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        rotated.x = ((($this$rotationTransform.x - center.x) * ((float) Math.cos(rotationInRad))) - (($this$rotationTransform.y - center.y) * ((float) Math.sin(rotationInRad)))) + center.x;
        rotated.y = (($this$rotationTransform.y - center.y) * ((float) Math.cos(rotationInRad))) + (($this$rotationTransform.x - center.x) * ((float) Math.sin(rotationInRad))) + center.y;
        rotated.timestamp = $this$rotationTransform.timestamp;
        rotated.pressure = $this$rotationTransform.pressure;
        return rotated;
    }

    @NotNull
    public final TouchPoint center(@NotNull TouchPoint p1, @NotNull TouchPoint p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        TouchPoint $this$center_u24lambda_u2d0 = new TouchPoint(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0L, 127, null);
        $this$center_u24lambda_u2d0.x = (p1.x + p2.x) / 2;
        $this$center_u24lambda_u2d0.y = (p1.y + p2.y) / 2;
        return $this$center_u24lambda_u2d0;
    }

    public final float distance(@NotNull TouchPoint p1, @NotNull TouchPoint p2) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return (float) Math.sqrt(((float) Math.pow(p1.x - p2.x, 2)) + ((float) Math.pow(p1.y - p2.y, 2)));
    }

    public final float slope(@NotNull TouchPoint startPoint, @NotNull TouchPoint endPoint) {
        Intrinsics.checkNotNullParameter(startPoint, "startPoint");
        Intrinsics.checkNotNullParameter(endPoint, "endPoint");
        float dx = endPoint.x - startPoint.x;
        float dy = endPoint.y - startPoint.y;
        if (dx == 0.0f) {
            if (dy > 0.0f) {
                return Float.MAX_VALUE;
            }
            if (dy < 0.0f) {
                return -3.4028235E38f;
            }
        }
        return dy / dx;
    }
}
