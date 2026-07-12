/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  com.onyx.android.sdk.base.data.TouchPoint
 *  com.onyx.android.sdk.extension.CollectionKt
 *  com.onyx.android.sdk.geometry.data.TouchData
 *  com.onyx.android.sdk.pen.NeoPen
 *  com.onyx.android.sdk.pen.PenPathResult
 *  com.onyx.android.sdk.pen.PenResult
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.extension.CollectionKt;
import com.onyx.android.sdk.geometry.data.TouchData;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.PenPathResult;
import com.onyx.android.sdk.pen.PenResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 82\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0017\u001a\u00020\u00182\u0016\u0010\u0019\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014J\b\u0010\u001a\u001a\u00020\u0018H\u0004J\u0006\u0010\u001b\u001a\u00020\u0018J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"J*\u0010#\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J*\u0010(\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J<\u0010)\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020%0+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020'H\u0016J\u0014\u0010-\u001a\u00020\u00182\f\u0010.\u001a\b\u0012\u0004\u0012\u00020%0+J\u0018\u0010/\u001a\u00020\u00182\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0016J&\u0010/\u001a\u00020\u00182\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\f\u0010.\u001a\b\u0012\u0004\u0012\u00020%0+H\u0016J\"\u00104\u001a\u00020\u00182\b\u00105\u001a\u0004\u0018\u00010\n2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0004J\b\u00106\u001a\u00020\u0018H\u0016J\b\u00107\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006RA\u0010\u0007\u001a2\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t0\bj\u0018\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t`\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013\u00a8\u00069"}, d2={"Lcom/onyx/android/sdk/pen/NeoPenRender;", "", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPen;", "(Lcom/onyx/android/sdk/pen/NeoPen;)V", "getNeoPen", "()Lcom/onyx/android/sdk/pen/NeoPen;", "penResults", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "Lkotlin/collections/ArrayList;", "getPenResults", "()Ljava/util/ArrayList;", "pointCount", "", "getPointCount", "()I", "setPointCount", "(I)V", "pointCountThreshold", "getPointCountThreshold", "setPointCountThreshold", "addPenResult", "", "pair", "clearPenResults", "destroyPen", "loadPenPointArrays", "", "loadPenPointSizeArrays", "", "onTouchData", "touchData", "Lcom/onyx/android/sdk/geometry/data/TouchData;", "onTouchDone", "realPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "repaint", "", "onTouchDown", "onTouchMove", "realPointList", "", "predictPoint", "onTouchPointList", "points", "render", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "renderResult", "penResult", "reset", "resetPredict", "Companion", "onyxsdk-pen_release"})
public class NeoPenRender {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int POINT_LIST_BATCH_LIMIT = 1000;
    public static final int DEFAULT_POINT_COUNT_THRESHOLD = 100;
    @NotNull
    private final NeoPen a;
    private int b;
    private int c;
    @NotNull
    private final ArrayList<Pair<PenResult, PenResult>> d;

    /*
     * WARNING - void declaration
     */
    public NeoPenRender(@NotNull NeoPen neoPen) {
        ArrayList arrayList;
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"neoPen");
        ((NeoPenRender)((Object)this_)).a = var1_1;
        ((NeoPenRender)((Object)this_)).c = 100;
        ArrayList this_ = arrayList;
        arrayList = new ArrayList();
        v1.d = this_;
    }

    public static /* synthetic */ Pair onTouchDown$default(NeoPenRender neoPenRender, TouchPoint touchPoint, boolean bl, int n, Object object) {
        if (object == null) {
            if ((n & 2) != 0) {
                bl = false;
            }
            return neoPenRender.onTouchDown(touchPoint, bl);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchDown");
    }

    public static /* synthetic */ Pair onTouchMove$default(NeoPenRender neoPenRender, List list, TouchPoint touchPoint, boolean bl, int n, Object object) {
        if (object == null) {
            if ((n & 2) != 0) {
                touchPoint = null;
            }
            if ((n & 4) != 0) {
                bl = false;
            }
            return neoPenRender.onTouchMove(list, touchPoint, bl);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchMove");
    }

    public static /* synthetic */ Pair onTouchDone$default(NeoPenRender neoPenRender, TouchPoint touchPoint, boolean bl, int n, Object object) {
        if (object == null) {
            if ((n & 2) != 0) {
                bl = false;
            }
            return neoPenRender.onTouchDone(touchPoint, bl);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTouchDone");
    }

    @NotNull
    public final NeoPen getNeoPen() {
        return this.a;
    }

    public final int getPointCount() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public final void setPointCount(int n) {
        void var1_1;
        this.b = var1_1;
    }

    public final int getPointCountThreshold() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    public final void setPointCountThreshold(int n) {
        void var1_1;
        this.c = var1_1;
    }

    @NotNull
    public final ArrayList<Pair<PenResult, PenResult>> getPenResults() {
        return this.d;
    }

    public final void onTouchPointList(@NotNull List<? extends TouchPoint> points) {
        ArrayList arrayList;
        Object object;
        Intrinsics.checkNotNullParameter((Object)object, (String)"points");
        this.clearPenResults();
        if (points.size() < 2) {
            return;
        }
        TouchPoint touchPoint = (TouchPoint)CollectionsKt.firstOrNull((List)object);
        if (touchPoint == null) {
            return;
        }
        TouchPoint touchPoint2 = (TouchPoint)CollectionsKt.lastOrNull((List)object);
        if (touchPoint2 == null) {
            return;
        }
        void v0 = object;
        List list = CollectionKt.safelySubList((List)v0, (int)1, (int)(v0.size() - 1));
        this.onTouchDown(touchPoint, true);
        object = arrayList;
        arrayList = new ArrayList();
        for (Object e : CollectionKt.split((List)list, (int)1000)) {
            if (!(((List)e).isEmpty() ^ true)) continue;
            object.add(e);
        }
        object = object.iterator();
        while (object.hasNext()) {
            NeoPenRender.onTouchMove$default(this, (List)object.next(), null, true, 2, null);
        }
        this.onTouchDone(touchPoint2, true);
    }

    @NotNull
    public final float[] loadPenPointArrays() {
        ArrayList arrayList;
        ArrayList arrayList2;
        Iterator this_ = arrayList2;
        arrayList2 = new ArrayList();
        Object object = ((NeoPenRender)((Object)this_)).d.iterator();
        while (object.hasNext()) {
            Object object2 = ((Pair)object.next()).getFirst();
            object2 = object2 instanceof PenPathResult ? (PenPathResult)object2 : null;
            if (object2 == null) continue;
            this_.add((Object)object2);
        }
        object = arrayList;
        arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)((Object)this_), (int)10));
        this_ = this_.iterator();
        while (this_.hasNext()) {
            object.add(ArraysKt.toList((float[])((PenPathResult)this_.next()).getPoints()));
        }
        return CollectionsKt.toFloatArray((Collection)CollectionsKt.flatten((Iterable)object));
    }

    @NotNull
    public final int[] loadPenPointSizeArrays() {
        ArrayList arrayList;
        ArrayList arrayList2;
        Iterator this_ = arrayList2;
        arrayList2 = new ArrayList();
        Object object = ((NeoPenRender)((Object)this_)).d.iterator();
        while (object.hasNext()) {
            Object object2 = ((Pair)object.next()).getFirst();
            object2 = object2 instanceof PenPathResult ? (PenPathResult)object2 : null;
            if (object2 == null) continue;
            this_.add((Object)object2);
        }
        object = arrayList;
        arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)((Object)this_), (int)10));
        this_ = this_.iterator();
        while (this_.hasNext()) {
            object.add(ArraysKt.toList((int[])((PenPathResult)this_.next()).getPointSizeArray()));
        }
        return CollectionsKt.toIntArray((Collection)CollectionsKt.flatten((Iterable)object));
    }

    /*
     * WARNING - void declaration
     */
    public void render(@NotNull Canvas canvas, @NotNull Paint paint, @NotNull List<? extends TouchPoint> points) {
        void var3_3;
        void var2_2;
        void var1_1;
        NeoPenRender neoPenRender = this;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"canvas");
        Intrinsics.checkNotNullParameter((Object)var2_2, (String)"paint");
        Intrinsics.checkNotNullParameter((Object)var3_3, (String)"points");
        neoPenRender.onTouchPointList((List<? extends TouchPoint>)var3_3);
        neoPenRender.render((Canvas)var1_1, (Paint)var2_2);
        neoPenRender.reset();
    }

    /*
     * WARNING - void declaration
     */
    public void render(@NotNull Canvas canvas, @NotNull Paint paint) {
        void var2_2;
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"canvas");
        Intrinsics.checkNotNullParameter((Object)var2_2, (String)"paint");
        Object object = this.d.iterator();
        while (object.hasNext()) {
            this.renderResult((PenResult)((Pair)object.next()).getFirst(), (Canvas)var1_1, (Paint)var2_2);
        }
        object = (Pair)CollectionsKt.lastOrNull(this.d);
        object = object == null ? null : (PenResult)object.getSecond();
        this.renderResult((PenResult)object, (Canvas)var1_1, (Paint)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    protected final void renderResult(@Nullable PenResult penResult, @NotNull Canvas canvas, @NotNull Paint paint) {
        void var1_1;
        void var3_3;
        void var2_2;
        Intrinsics.checkNotNullParameter((Object)var2_2, (String)"canvas");
        Intrinsics.checkNotNullParameter((Object)var3_3, (String)"paint");
        if (penResult == null) {
            return;
        }
        var1_1.draw((Canvas)var2_2, (Paint)var3_3);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Pair<PenResult, PenResult> onTouchDown(@NotNull TouchPoint realPoint, boolean repaint) {
        void var2_2;
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"realPoint");
        this.b = 0;
        Pair pair = this.a.onPenDown((TouchPoint)var1_1, (boolean)var2_2);
        this.addPenResult((Pair<? extends PenResult, ? extends PenResult>)pair);
        return pair;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Pair<PenResult, PenResult> onTouchMove(@NotNull List<? extends TouchPoint> realPointList, @Nullable TouchPoint predictPoint, boolean repaint) {
        void var3_3;
        void var2_2;
        void var1_1;
        NeoPenRender neoPenRender = this;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"realPointList");
        neoPenRender.b += var1_1.size();
        Pair pair = neoPenRender.a.onPenMove((List)var1_1, (TouchPoint)var2_2, (boolean)var3_3);
        this.addPenResult((Pair<? extends PenResult, ? extends PenResult>)pair);
        return pair;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Pair<PenResult, PenResult> onTouchDone(@NotNull TouchPoint realPoint, boolean repaint) {
        void var2_2;
        void var1_1;
        NeoPenRender neoPenRender = this;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"realPoint");
        neoPenRender.b = neoPenRender.c;
        Pair pair = neoPenRender.a.onPenUp((TouchPoint)var1_1, (boolean)var2_2);
        this.addPenResult((Pair<? extends PenResult, ? extends PenResult>)pair);
        return pair;
    }

    /*
     * WARNING - void declaration
     */
    public final void onTouchData(@NotNull TouchData touchData) {
        void var1_1;
        TouchData touchData2 = touchData;
        Intrinsics.checkNotNullParameter((Object)touchData2, (String)"touchData");
        int n = touchData2.getAction();
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    NeoPenRender neoPenRender = list;
                    void v2 = var1_1;
                    List list = CollectionsKt.listOf((Object)v2.getTouchPoint());
                    NeoPenRender.onTouchMove$default(neoPenRender, list, (TouchPoint)v2.getPredictPoint(), false, 4, null);
                }
            } else {
                NeoPenRender neoPenRender = list;
                NeoPenRender.onTouchDone$default(neoPenRender, (TouchPoint)var1_1.getTouchPoint(), false, 2, null);
                neoPenRender.resetPredict();
            }
        } else {
            NeoPenRender.onTouchDown$default((NeoPenRender)((Object)list), (TouchPoint)var1_1.getTouchPoint(), false, 2, null);
        }
    }

    /*
     * WARNING - void declaration
     */
    protected void addPenResult(@NotNull Pair<? extends PenResult, ? extends PenResult> pair) {
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"pair");
        this.d.add((Pair<PenResult, PenResult>)var1_1);
    }

    protected final void clearPenResults() {
        this.d.clear();
    }

    public final void destroyPen() {
        this.a.destroy();
    }

    public void reset() {
        this.b = 0;
        this.clearPenResults();
    }

    public void resetPredict() {
        Pair pair = (Pair)CollectionsKt.lastOrNull(this.d);
        if (pair == null) {
            return;
        }
        NeoPenRender neoPenRender = this;
        neoPenRender.d.remove(pair);
        neoPenRender.d.add((Pair<PenResult, PenResult>)new Pair(pair.getFirst(), null));
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lcom/onyx/android/sdk/pen/NeoPenRender$Companion;", "", "()V", "DEFAULT_POINT_COUNT_THRESHOLD", "", "POINT_LIST_BATCH_LIMIT", "onyxsdk-pen_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

