package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.lite.extension.CollectionKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/PencilNeoPenRender.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J(\u0010\u0012\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J8\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015H\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0016J\u0012\u0010\u001f\u001a\u00020\u00182\b\u0010 \u001a\u0004\u0018\u00010\rH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006!"}, d2 = {"Lcom/onyx/android/sdk/pen/PencilNeoPenRender;", "Lcom/onyx/android/sdk/pen/NeoPenRender;", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPencilPen;", "(Lcom/onyx/android/sdk/pen/NeoPencilPen;)V", "brushPointCount", "", "getBrushPointCount", "()I", "setBrushPointCount", "(I)V", "onTouchDone", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "realPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "repaint", "", "onTouchDown", "onTouchMove", "realPointList", "", "predictPoint", "render", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "points", "reset", "updateBrushPointsCount", "penResult", "onyxsdk-pen_release"})
public final class PencilNeoPenRender extends NeoPenRender {
    private int e;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PencilNeoPenRender(@NotNull NeoPencilPen neoPen) {
        super(neoPen);
        Intrinsics.checkNotNullParameter(neoPen, "neoPen");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void a(PenResult penResult) {
        PenBrushResult penBrushResult = penResult instanceof PenBrushResult ? (PenBrushResult) penResult : null;
        if (penBrushResult == null) {
            return;
        }
        this.e += PenBrushResult.Companion.getPointsCount(penBrushResult);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int getBrushPointCount() {
        return this.e;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setBrushPointCount(int i) {
        this.e = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.NeoPenRender
    public void render(@NotNull Canvas canvas, @NotNull Paint paint, @NotNull List<? extends TouchPoint> points) {
        TouchPoint touchPoint;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(points, "points");
        TouchPoint touchPoint2 = (TouchPoint) CollectionsKt.firstOrNull(points);
        if (touchPoint2 == null || (touchPoint = (TouchPoint) CollectionsKt.lastOrNull(points)) == null) {
            return;
        }
        List listSafelySubList = CollectionKt.safelySubList(points, 1, points.size() - 1);
        renderResult((PenResult) getNeoPen().onPenDown(touchPoint2, true).getFirst(), canvas, paint);
        List listSplit = com.onyx.android.sdk.extension.CollectionKt.split(listSafelySubList, NeoPenRender.POINT_LIST_BATCH_LIMIT);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit) {
            if (!((List) obj).isEmpty()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            renderResult((PenResult) getNeoPen().onPenMove((List) it.next(), null, true).getFirst(), canvas, paint);
        }
        renderResult((PenResult) getNeoPen().onPenUp(touchPoint, true).getFirst(), canvas, paint);
        reset();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.NeoPenRender
    @NotNull
    public Pair<PenResult, PenResult> onTouchDown(@NotNull TouchPoint realPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPoint, "realPoint");
        this.e = 0;
        return super.onTouchDown(realPoint, repaint);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.NeoPenRender
    @NotNull
    public Pair<PenResult, PenResult> onTouchMove(@NotNull List<? extends TouchPoint> realPointList, @Nullable TouchPoint predictPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPointList, "realPointList");
        Pair<PenResult, PenResult> pairOnTouchMove = super.onTouchMove(realPointList, predictPoint, repaint);
        a((PenResult) pairOnTouchMove.getFirst());
        return pairOnTouchMove;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.NeoPenRender
    @NotNull
    public Pair<PenResult, PenResult> onTouchDone(@NotNull TouchPoint realPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPoint, "realPoint");
        Pair<PenResult, PenResult> pairOnTouchDone = super.onTouchDone(realPoint, repaint);
        a((PenResult) pairOnTouchDone.getFirst());
        return pairOnTouchDone;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.NeoPenRender
    public void reset() {
        super.reset();
        this.e = 0;
    }
}
