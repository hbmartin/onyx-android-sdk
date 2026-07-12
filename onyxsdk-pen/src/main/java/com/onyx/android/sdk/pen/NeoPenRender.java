package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.extension.CollectionKt;
import com.onyx.android.sdk.geometry.data.TouchData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 82\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0017\u001a\u00020\u00182\u0016\u0010\u0019\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014J\b\u0010\u001a\u001a\u00020\u0018H\u0004J\u0006\u0010\u001b\u001a\u00020\u0018J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"J*\u0010#\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J*\u0010(\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J<\u0010)\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020%0+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J\u0014\u0010-\u001a\u00020\u00182\f\u0010.\u001a\b\u0012\u0004\u0012\u00020%0+J\u0018\u0010/\u001a\u00020\u00182\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0016J&\u0010/\u001a\u00020\u00182\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\f\u0010.\u001a\b\u0012\u0004\u0012\u00020%0+H\u0016J\"\u00104\u001a\u00020\u00182\b\u00105\u001a\u0004\u0018\u00010\n2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0004J\b\u00106\u001a\u00020\u0018H\u0016J\b\u00107\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006RA\u0010\u0007\u001a2\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t0\bj\u0018\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t`\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013¨\u00069"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenRender;", "", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPen;", "(Lcom/onyx/android/sdk/pen/NeoPen;)V", "getNeoPen", "()Lcom/onyx/android/sdk/pen/NeoPen;", "penResults", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "Lkotlin/collections/ArrayList;", "getPenResults", "()Ljava/util/ArrayList;", "pointCount", "", "getPointCount", "()I", "setPointCount", "(I)V", "pointCountThreshold", "getPointCountThreshold", "setPointCountThreshold", "addPenResult", "", "pair", "clearPenResults", "destroyPen", "loadPenPointArrays", "", "loadPenPointSizeArrays", "", "onTouchData", "touchData", "Lcom/onyx/android/sdk/geometry/data/TouchData;", "onTouchDone", "realPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "repaint", "", "onTouchDown", "onTouchMove", "realPointList", "", "predictPoint", "onTouchPointList", "points", "render", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "renderResult", "penResult", "reset", "resetPredict", "Companion", "onyxsdk-pen_release"})
public class NeoPenRender {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int POINT_LIST_BATCH_LIMIT = 1000;
    public static final int DEFAULT_POINT_COUNT_THRESHOLD = 100;

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\u0008\n\u0002\u0008\u0002\u0008\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenRender$Companion;", "", "()V", "DEFAULT_POINT_COUNT_THRESHOLD", "", "POINT_LIST_BATCH_LIMIT", "onyxsdk-pen_release"})
    public static final class Companion {
        private Companion() {
        }

        public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
            this();
        }
    }

    @NotNull
    private final NeoPen a;
    private int b;
    private int c;

    @NotNull
    private final ArrayList<Pair<PenResult, PenResult>> d;

    public NeoPenRender(@NotNull NeoPen neoPen) {
        Intrinsics.checkNotNullParameter(neoPen, "neoPen");
        this.a = neoPen;
        this.c = 100;
        this.d = new ArrayList<>();
    }

    public static /* synthetic */ Pair onTouchDown$default(NeoPenRender neoPenRender, TouchPoint touchPoint, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchDown");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return neoPenRender.onTouchDown(touchPoint, z);
    }

    public static /* synthetic */ Pair onTouchMove$default(NeoPenRender neoPenRender, List list, TouchPoint touchPoint, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchMove");
        }
        if ((i & 2) != 0) {
            touchPoint = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return neoPenRender.onTouchMove(list, touchPoint, z);
    }

    public static /* synthetic */ Pair onTouchDone$default(NeoPenRender neoPenRender, TouchPoint touchPoint, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchDone");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return neoPenRender.onTouchDone(touchPoint, z);
    }

    @NotNull
    public final NeoPen getNeoPen() {
        return this.a;
    }

    public final int getPointCount() {
        return this.b;
    }

    public final void setPointCount(int i) {
        this.b = i;
    }

    public final int getPointCountThreshold() {
        return this.c;
    }

    public final void setPointCountThreshold(int i) {
        this.c = i;
    }

    @NotNull
    public final ArrayList<Pair<PenResult, PenResult>> getPenResults() {
        return this.d;
    }

    public final void onTouchPointList(@NotNull List<? extends TouchPoint> points) {
        TouchPoint touchPoint;
        TouchPoint touchPoint2;
        Intrinsics.checkNotNullParameter(points, "points");
        clearPenResults();
        if (points.size() < 2 || (touchPoint = (TouchPoint) CollectionsKt.firstOrNull(points)) == null || (touchPoint2 = (TouchPoint) CollectionsKt.lastOrNull(points)) == null) {
            return;
        }
        List listSafelySubList = CollectionKt.safelySubList(points, 1, points.size() - 1);
        onTouchDown(touchPoint, true);
        List listSplit = CollectionKt.split(listSafelySubList, POINT_LIST_BATCH_LIMIT);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit) {
            if (!((List) obj).isEmpty()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            onTouchMove$default(this, (List) it.next(), null, true, 2, null);
        }
        onTouchDone(touchPoint2, true);
    }

    @NotNull
    public final float[] loadPenPointArrays() {
        ArrayList<Pair<PenResult, PenResult>> arrayList = this.d;
        ArrayList arrayList2 = new ArrayList();
        Iterator<?> it = arrayList.iterator();
        while (it.hasNext()) {
            Object first = ((Pair) it.next()).getFirst();
            PenPathResult penPathResult = first instanceof PenPathResult ? (PenPathResult) first : null;
            if (penPathResult != null) {
                arrayList2.add(penPathResult);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(ArraysKt.toList(((PenPathResult) it2.next()).getPoints()));
        }
        return CollectionsKt.toFloatArray(CollectionsKt.flatten(arrayList3));
    }

    @NotNull
    public final int[] loadPenPointSizeArrays() {
        ArrayList<Pair<PenResult, PenResult>> arrayList = this.d;
        ArrayList arrayList2 = new ArrayList();
        Iterator<?> it = arrayList.iterator();
        while (it.hasNext()) {
            Object first = ((Pair) it.next()).getFirst();
            PenPathResult penPathResult = first instanceof PenPathResult ? (PenPathResult) first : null;
            if (penPathResult != null) {
                arrayList2.add(penPathResult);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(ArraysKt.toList(((PenPathResult) it2.next()).getPointSizeArray()));
        }
        return CollectionsKt.toIntArray(CollectionsKt.flatten(arrayList3));
    }

    public void render(@NotNull Canvas canvas, @NotNull Paint paint, @NotNull List<? extends TouchPoint> points) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(points, "points");
        onTouchPointList(points);
        render(canvas, paint);
        reset();
    }

    protected final void renderResult(@Nullable PenResult penResult, @NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (penResult == null) {
            return;
        }
        penResult.draw(canvas, paint);
    }

    @NotNull
    public Pair<PenResult, PenResult> onTouchDown(@NotNull TouchPoint realPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPoint, "realPoint");
        this.b = 0;
        Pair<PenResult, PenResult> pairOnPenDown = this.a.onPenDown(realPoint, repaint);
        addPenResult(pairOnPenDown);
        return pairOnPenDown;
    }

    @NotNull
    public Pair<PenResult, PenResult> onTouchMove(@NotNull List<? extends TouchPoint> realPointList, @Nullable TouchPoint predictPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPointList, "realPointList");
        this.b += realPointList.size();
        Pair<PenResult, PenResult> pairOnPenMove = this.a.onPenMove(realPointList, predictPoint, repaint);
        addPenResult(pairOnPenMove);
        return pairOnPenMove;
    }

    @NotNull
    public Pair<PenResult, PenResult> onTouchDone(@NotNull TouchPoint realPoint, boolean repaint) {
        Intrinsics.checkNotNullParameter(realPoint, "realPoint");
        this.b = this.c;
        Pair<PenResult, PenResult> pairOnPenUp = this.a.onPenUp(realPoint, repaint);
        addPenResult(pairOnPenUp);
        return pairOnPenUp;
    }

    public final void onTouchData(@NotNull TouchData touchData) {
        Intrinsics.checkNotNullParameter(touchData, "touchData");
        int action = touchData.getAction();
        if (action == 0) {
            onTouchDown$default(this, touchData.getTouchPoint(), false, 2, null);
            return;
        }
        if (action == 1) {
            onTouchDone$default(this, touchData.getTouchPoint(), false, 2, null);
            resetPredict();
        } else {
            if (action != 2) {
                return;
            }
            onTouchMove$default(this, CollectionsKt.listOf(touchData.getTouchPoint()), touchData.getPredictPoint(), false, 4, null);
        }
    }

    protected void addPenResult(@NotNull Pair<? extends PenResult, ? extends PenResult> pair) {
        Intrinsics.checkNotNullParameter(pair, "pair");
        this.d.add(new Pair<>((PenResult) pair.getFirst(), (PenResult) pair.getSecond()));
    }

    protected final void clearPenResults() {
        this.d.clear();
    }

    public final void destroyPen() {
        this.a.destroy();
    }

    public void reset() {
        this.b = 0;
        clearPenResults();
    }

    public void resetPredict() {
        Pair pair = (Pair) CollectionsKt.lastOrNull(this.d);
        if (pair == null) {
            return;
        }
        this.d.remove(pair);
        this.d.add(new Pair<>((PenResult) pair.getFirst(), null));
    }

    public void render(@NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Iterator<?> it = this.d.iterator();
        while (it.hasNext()) {
            renderResult((PenResult) ((Pair) it.next()).getFirst(), canvas, paint);
        }
        Pair pair = (Pair) CollectionsKt.lastOrNull(this.d);
        renderResult(pair == null ? null : (PenResult) pair.getSecond(), canvas, paint);
    }
}
