package com.onyx.android.sdk.pen;

import android.graphics.Path;
import android.graphics.RectF;
import com.onyx.android.sdk.base.data.TouchPoint;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0014\u001a\u00020\u0006H\u0002J\u0016\u0010\u0015\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u0017H\u0002J*\u0010\u0018\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u0016J(\u0010\u0018\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J0\u0010\u001f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u0016J8\u0010\u001f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J*\u0010 \u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u0016J(\u0010 \u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006!"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoSinglePathResultPen;", "Lcom/onyx/android/sdk/pen/NeoPen;", "penHandle", "", "(J)V", "firstPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "getFirstPoint", "()Lcom/onyx/android/sdk/base/data/TouchPoint;", "setFirstPoint", "(Lcom/onyx/android/sdk/base/data/TouchPoint;)V", "path", "Landroid/graphics/Path;", "getPath", "()Landroid/graphics/Path;", "setPath", "(Landroid/graphics/Path;)V", "composePredictPenResult", "Lcom/onyx/android/sdk/pen/PenPathResult;", "predictPoint", "lastPoint", "composeRealPenResult", "points", "", "onPenDown", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "point", "prediction", "repaint", "", "onPenMove", "onPenUp", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class NeoSinglePathResultPen extends NeoPen {

    @NotNull
    private TouchPoint b;

    @NotNull
    private Path c;

    public NeoSinglePathResultPen() {
        this(0L, 1, null);
    }

    public NeoSinglePathResultPen(long j) {
        super(j);
        this.b = new TouchPoint(com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 0, 0, 0L, 127, (DefaultConstructorMarker) null);
        this.c = new Path();
    }

      (wrap long:?: TERNARY null = ((wrap int:0x0002: ARITH (r7v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (0 long) : (r5v0 long))
     A[MD:(long):void (m)] call: com.onyx.android.sdk.pen.NeoSinglePathResultPen.<init>(long):void type: THIS */
    public /* synthetic */ NeoSinglePathResultPen(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j);
    }

    private final PenPathResult a(TouchPoint touchPoint, TouchPoint touchPoint2) {
        if (touchPoint == null) {
            return null;
        }
        Path path = new Path();
        path.moveTo(touchPoint2.x, touchPoint2.y);
        path.lineTo(touchPoint.x, touchPoint.y);
        return new PenPathResult(path, new RectF(touchPoint2.x, touchPoint2.y, touchPoint.x, touchPoint.y));
    }

    private final PenPathResult b(List<? extends TouchPoint> list) {
        TouchPoint touchPoint = this.b;
        float f = touchPoint.x;
        float f2 = touchPoint.y;
        RectF rectF = new RectF(f, f2, f, f2);
        for (TouchPoint touchPoint2 : list) {
            this.c.lineTo(touchPoint2.x, touchPoint2.y);
            rectF.union(touchPoint2.x, touchPoint2.y);
        }
        return new PenPathResult(this.c, rectF);
    }

    @NotNull
    protected final TouchPoint getFirstPoint() {
        return this.b;
    }

    @NotNull
    protected final Path getPath() {
        return this.c;
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, @Nullable TouchPoint prediction) {
        Intrinsics.checkNotNullParameter(point, "point");
        return onPenDown(point, false);
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.b = point;
        Path path = new Path();
        this.c = path;
        path.moveTo(point.x, point.y);
        return new Pair<>(null, null);
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenMove(@NotNull List<? extends TouchPoint> points, @Nullable TouchPoint prediction) {
        Intrinsics.checkNotNullParameter(points, "points");
        return onPenMove(points, prediction, false);
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenMove(@NotNull List<? extends TouchPoint> points, @Nullable TouchPoint prediction, boolean repaint) {
        Intrinsics.checkNotNullParameter(points, "points");
        if (points.isEmpty()) {
            return new Pair<>(null, null);
        }
        PenPathResult penPathResultB = b(points);
        PenPathResult penPathResultA = a(prediction, (TouchPoint) CollectionsKt.last(points));
        Path path = new Path();
        path.addPath(penPathResultB.getPath$sdk_pen_release());
        if (penPathResultA != null) {
            path.addPath(penPathResultA.getPath$sdk_pen_release());
            penPathResultB.getRect().union(penPathResultA.getRect());
        }
        return new Pair<>(new PenPathResult(path, penPathResultB.getRect()), penPathResultA);
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenUp(@NotNull TouchPoint point, @Nullable TouchPoint prediction) {
        Intrinsics.checkNotNullParameter(point, "point");
        return onPenUp(point, false);
    }

    @Override // com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenUp(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new Pair<>(b(CollectionsKt.listOf(point)), null);
    }

    protected final void setFirstPoint(@NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "<set-?>");
        this.b = touchPoint;
    }

    protected final void setPath(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "<set-?>");
        this.c = path;
    }
}
