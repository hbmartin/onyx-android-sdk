package com.onyx.android.sdk.pen;

import androidx.annotation.AnyThread;
import com.onyx.android.sdk.base.data.TouchPoint;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ*\u0010\t\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH'J(\u0010\t\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J0\u0010\u0011\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\rH'J8\u0010\u0011\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH'J(\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPen;", "", "penHandle", "", "(J)V", "getPenHandle$sdk_pen_release", "()J", "destroy", "", "onPenDown", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "point", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "prediction", "repaint", "", "onPenMove", "points", "", "onPenUp", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class NeoPen {
    private final long a;

    public NeoPen(long j) {
        this.a = j;
    }

    @AnyThread
    public final void destroy() {
        NeoPenNative.INSTANCE.destroyPen(this.a);
    }

    public final long getPenHandle$sdk_pen_release() {
        return this.a;
    }

    @Deprecated(message = "")
    @NotNull
    @AnyThread
    public abstract Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, @Nullable TouchPoint prediction);

    @NotNull
    @AnyThread
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new Pair<>(null, null);
    }

    @Deprecated(message = "")
    @NotNull
    @AnyThread
    public abstract Pair<PenResult, PenResult> onPenMove(@NotNull List<? extends TouchPoint> points, @Nullable TouchPoint prediction);

    @NotNull
    @AnyThread
    public Pair<PenResult, PenResult> onPenMove(@NotNull List<? extends TouchPoint> points, @Nullable TouchPoint prediction, boolean repaint) {
        Intrinsics.checkNotNullParameter(points, "points");
        return new Pair<>(null, null);
    }

    @Deprecated(message = "")
    @NotNull
    @AnyThread
    public abstract Pair<PenResult, PenResult> onPenUp(@NotNull TouchPoint point, @Nullable TouchPoint prediction);

    @NotNull
    @AnyThread
    public Pair<PenResult, PenResult> onPenUp(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new Pair<>(null, null);
    }
}
