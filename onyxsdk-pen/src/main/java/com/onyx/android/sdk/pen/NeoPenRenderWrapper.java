package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\t\u001a\u00020\n2\u0016\u0010\u000b\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\fH\u0014J\n\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0014J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenRenderWrapper;", "Lcom/onyx/android/sdk/pen/NeoPenRender;", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPen;", "segment", "", "(Lcom/onyx/android/sdk/pen/NeoPen;Z)V", "getSegment", "()Z", "addPenResult", "", "pair", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "appendPredictPenResult", "render", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "onyxsdk-pen_release"})
public class NeoPenRenderWrapper extends NeoPenRender {
    private final boolean e;

    public NeoPenRenderWrapper(@NotNull NeoPen neoPen, boolean segment) {
        super(neoPen);
        Intrinsics.checkNotNullParameter(neoPen, "neoPen");
        this.e = segment;
        if (segment) {
            return;
        }
        setPointCountThreshold(Integer.MAX_VALUE);
    }

    protected final boolean getSegment() {
        return this.e;
    }

    @Override // com.onyx.android.sdk.pen.NeoPenRender
    public void render(@NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (this.e) {
            super.render(canvas, paint);
        } else {
            renderResult(appendPredictPenResult(), canvas, paint);
        }
    }

    @Nullable
    protected PenResult appendPredictPenResult() {
        Pair pair = (Pair) CollectionsKt.lastOrNull(getPenResults());
        if (pair == null) {
            return null;
        }
        PenResult penResult = (PenResult) pair.getFirst();
        PenResult penResultAppend = penResult;
        if (penResult == null) {
            return null;
        }
        PenResult penResult2 = (PenResult) pair.getSecond();
        if (penResult2 != null) {
            penResultAppend = penResultAppend.append(penResult2);
        }
        return penResultAppend;
    }

    @Override // com.onyx.android.sdk.pen.NeoPenRender
    protected void addPenResult(@NotNull Pair<? extends PenResult, ? extends PenResult> pair) {
        Pair<? extends PenResult, ? extends PenResult> pair2;
        PenResult penResultAppend;
        Intrinsics.checkNotNullParameter(pair, "pair");
        if (this.e) {
            pair2 = pair;
        } else {
            Pair pair3 = (Pair) CollectionsKt.lastOrNull(getPenResults());
            if ((pair3 == null ? null : (PenResult) pair3.getFirst()) != null) {
                Pair pair4 = (Pair) CollectionsKt.lastOrNull(getPenResults());
                PenResult penResult = pair4 == null ? null : (PenResult) pair4.getFirst();
                Intrinsics.checkNotNull(penResult);
                penResultAppend = penResult.append((PenResult) pair.getFirst());
            } else {
                penResultAppend = (PenResult) pair.getFirst();
            }
            pair2 = new Pair<>(penResultAppend, pair.getSecond());
            clearPenResults();
        }
        super.addPenResult(pair2);
    }
}
