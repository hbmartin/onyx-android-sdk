package com.onyx.android.sdk.base.lite.extension;

import android.graphics.PointF;
import com.onyx.android.sdk.base.data.TouchPoint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0004\u001a\u00020\u0005*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0006\u001a\u00020\u0005*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\n\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\u0002¨\u0006\r"}, d2 = {"obtainPressure", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "obtainSize", "obtainTiltX", "", "obtainTiltY", "obtainTimestamp", "", "obtainX", "obtainY", "toPointF", "Landroid/graphics/PointF;", "sdk-baselite_release"})
public final class TouchPointsKt {
    public static final float obtainX(@Nullable TouchPoint $this$obtainX) {
        if ($this$obtainX == null) {
            return 0.0f;
        }
        return $this$obtainX.x;
    }

    public static final float obtainY(@Nullable TouchPoint $this$obtainY) {
        if ($this$obtainY == null) {
            return 0.0f;
        }
        return $this$obtainY.y;
    }

    public static final float obtainPressure(@Nullable TouchPoint $this$obtainPressure) {
        if ($this$obtainPressure == null) {
            return 0.0f;
        }
        return $this$obtainPressure.pressure;
    }

    public static final float obtainSize(@Nullable TouchPoint $this$obtainSize) {
        if ($this$obtainSize == null) {
            return 0.0f;
        }
        return $this$obtainSize.size;
    }

    public static final int obtainTiltX(@Nullable TouchPoint $this$obtainTiltX) {
        if ($this$obtainTiltX == null) {
            return 0;
        }
        return $this$obtainTiltX.tiltX;
    }

    public static final int obtainTiltY(@Nullable TouchPoint $this$obtainTiltY) {
        if ($this$obtainTiltY == null) {
            return 0;
        }
        return $this$obtainTiltY.tiltY;
    }

    public static final long obtainTimestamp(@Nullable TouchPoint $this$obtainTimestamp) {
        if ($this$obtainTimestamp == null) {
            return 0L;
        }
        return $this$obtainTimestamp.timestamp;
    }

    @NotNull
    public static final PointF toPointF(@NotNull TouchPoint $this$toPointF) {
        Intrinsics.checkNotNullParameter($this$toPointF, "<this>");
        return new PointF($this$toPointF.x, $this$toPointF.y);
    }
}
