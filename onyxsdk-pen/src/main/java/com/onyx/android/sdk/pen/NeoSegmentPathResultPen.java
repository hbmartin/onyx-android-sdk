package com.onyx.android.sdk.pen;

import android.graphics.Path;
import android.graphics.RectF;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.lite.extension.PathKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoSegmentPathResultPen.class */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\t2\b\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0002J$\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0006H\u0002J*\u0010\u000f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\u0006\u0010\f\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006H\u0016J(\u0010\u000f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J0\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006H\u0016J8\u0010\u0014\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\b\u0010\u0011\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J*\u0010\u0017\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\u0006\u0010\f\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006H\u0016J(\u0010\u0017\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00100\t2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoSegmentPathResultPen;", "Lcom/onyx/android/sdk/pen/NeoPen;", "penHandle", "", "(J)V", "endPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "lastPoint", "composePredictPath", "Lkotlin/Pair;", "Landroid/graphics/Path;", "Landroid/graphics/RectF;", "point", "composeRealPath", "path", "onPenDown", "Lcom/onyx/android/sdk/pen/PenResult;", "prediction", "repaint", "", "onPenMove", "points", "", "onPenUp", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoSegmentPathResultPen extends NeoPen {

    @NotNull
    private TouchPoint b;

    @NotNull
    private TouchPoint c;

    public NeoSegmentPathResultPen() {
        this(0L, 1, null);
    }

    public NeoSegmentPathResultPen(long j) {
        super(j);
        this.b = new TouchPoint(com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 0, 0, 0L, 127, (DefaultConstructorMarker) null);
        this.c = new TouchPoint(com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 0, 0, 0L, 127, (DefaultConstructorMarker) null);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x000a: CONSTRUCTOR 
      (wrap long:?: TERNARY null = ((wrap int:0x0002: ARITH (r7v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (0 long) : (r5v0 long))
     A[MD:(long):void (m)] call: com.onyx.android.sdk.pen.NeoSegmentPathResultPen.<init>(long):void type: THIS */
    public /* synthetic */ NeoSegmentPathResultPen(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0L : j);
    }

    private final Pair<Path, RectF> a(TouchPoint touchPoint) {
        if (touchPoint == null) {
            return null;
        }
        Path path = new Path();
        TouchPoint touchPoint2 = this.c;
        path.moveTo(touchPoint2.x, touchPoint2.y);
        TouchPoint touchPoint3 = this.b;
        path.quadTo(touchPoint3.x, touchPoint3.y, touchPoint.x, touchPoint.y);
        TouchPoint touchPoint4 = this.c;
        float f = touchPoint4.x;
        float f2 = touchPoint4.y;
        RectF rectF = new RectF(f, f2, f, f2);
        rectF.union(touchPoint.x, touchPoint.y);
        return new Pair<>(path, rectF);
    }

    private final Pair<Path, RectF> b(Path path, TouchPoint touchPoint) {
        float quadToEndPos$default = PathKt.getQuadToEndPos$default(this.b.x, touchPoint.x, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 4, (Object) null);
        float quadToEndPos$default2 = PathKt.getQuadToEndPos$default(this.b.y, touchPoint.y, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 4, (Object) null);
        TouchPoint touchPoint2 = this.b;
        path.quadTo(touchPoint2.x, touchPoint2.y, quadToEndPos$default, quadToEndPos$default2);
        TouchPoint touchPoint3 = this.c;
        float f = touchPoint3.x;
        float f2 = touchPoint3.y;
        RectF rectF = new RectF(f, f2, f, f2);
        rectF.union(quadToEndPos$default, quadToEndPos$default2);
        TouchPoint touchPoint4 = this.c;
        touchPoint4.x = quadToEndPos$default;
        touchPoint4.y = quadToEndPos$default2;
        this.b.set(touchPoint);
        return new Pair<>(path, rectF);
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
        this.b.set(point);
        this.c.set(point);
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
        Path path = new Path();
        TouchPoint touchPoint = this.c;
        path.moveTo(touchPoint.x, touchPoint.y);
        Iterator<? extends TouchPoint> it = points.iterator();
        RectF rectF = null;
        while (it.hasNext()) {
            Pair<Path, RectF> pairB = b(path, it.next());
            if (rectF == null) {
                rectF = (RectF) pairB.getSecond();
            } else {
                rectF.union((RectF) pairB.getSecond());
            }
        }
        PenPathResult penPathResult = rectF == null ? null : new PenPathResult(path, rectF);
        Pair<Path, RectF> pairA = a(prediction);
        return new Pair<>(penPathResult, pairA == null ? null : new PenPathResult((Path) pairA.getFirst(), (RectF) pairA.getSecond()));
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
        Path path = new Path();
        TouchPoint touchPoint = this.c;
        path.moveTo(touchPoint.x, touchPoint.y);
        Pair<Path, RectF> pairB = b(path, point);
        return new Pair<>(new PenPathResult((Path) pairB.getFirst(), (RectF) pairB.getSecond()), null);
    }
}
