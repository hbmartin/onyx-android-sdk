package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenPointResult.class */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B%\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\bJ\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u00012\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/onyx/android/sdk/pen/PenPointResult;", "Lcom/onyx/android/sdk/pen/PenResult;", "points", "", "Lcom/onyx/android/sdk/pen/PenPointInk;", "rect", "Landroid/graphics/RectF;", "lastResult", "(Ljava/util/List;Landroid/graphics/RectF;Lcom/onyx/android/sdk/pen/PenPointResult;)V", "getPoints", "()Ljava/util/List;", "append", "add", "draw", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenPointResult extends PenResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final List<PenPointInk> b;

    @Nullable
    private final PenPointResult c;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenPointResult$Companion.class */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004¨\u0006\b"}, d2 = {"Lcom/onyx/android/sdk/pen/PenPointResult$Companion;", "", "()V", "buildFromInkArray", "Lcom/onyx/android/sdk/pen/PenPointResult;", "ink", "Lcom/onyx/android/sdk/pen/PenInk;", "lastResult", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.PenPointResult.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final PenPointResult buildFromInkArray(@Nullable PenInk ink, @Nullable PenPointResult lastResult) {
            RectF rectF;
            if (ink == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, ink.getPoints().length), 3);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step <= 0 || first > last) && (step >= 0 || last > first)) {
                rectF = null;
            } else {
                rectF = null;
                while (true) {
                    float f = ink.getPoints()[first];
                    float f2 = ink.getPoints()[first + 1];
                    arrayList.add(new PenPointInk(f, f2, ink.getPoints()[first + 2]));
                    if (rectF == null) {
                        rectF = new RectF(f, f2, f, f2);
                    } else {
                        rectF.union(f, f2);
                    }
                    if (first == last) {
                        break;
                    }
                    first += step;
                }
            }
            if (rectF == null) {
                return null;
            }
            return new PenPointResult(arrayList, rectF, lastResult);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PenPointResult(@NotNull List<PenPointInk> list, @NotNull RectF rectF, @Nullable PenPointResult penPointResult) {
        super(rectF);
        Intrinsics.checkNotNullParameter(list, "points");
        Intrinsics.checkNotNullParameter(rectF, "rect");
        this.b = list;
        this.c = penPointResult;
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    @Nullable
    public PenResult append(@Nullable PenResult add) {
        if (add == null) {
            return this;
        }
        PenPointResult penPointResult = (PenPointResult) add;
        ArrayList arrayList = new ArrayList(this.b);
        arrayList.addAll(penPointResult.b);
        RectF rectF = new RectF(getRect());
        rectF.union(penPointResult.getRect());
        return new PenPointResult(arrayList, rectF, this.c);
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    public void draw(@NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (this.c != null && (!this.b.isEmpty())) {
            paint.setStrokeWidth(((PenPointInk) CollectionsKt.first(this.b)).getSize());
            canvas.drawLine(((PenPointInk) CollectionsKt.last(this.c.b)).getX(), ((PenPointInk) CollectionsKt.last(this.c.b)).getY(), ((PenPointInk) CollectionsKt.first(this.b)).getX(), ((PenPointInk) CollectionsKt.first(this.b)).getY(), paint);
        }
        int i = 0;
        int size = this.b.size();
        while (i < size - 1) {
            int i2 = i + 1;
            paint.setStrokeWidth(this.b.get(i2).getSize());
            canvas.drawLine(this.b.get(i).getX(), this.b.get(i).getY(), this.b.get(i2).getX(), this.b.get(i2).getY(), paint);
            i = i2;
        }
    }

    @NotNull
    public final List<PenPointInk> getPoints() {
        return this.b;
    }
}


