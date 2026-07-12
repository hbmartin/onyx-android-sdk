/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  com.onyx.android.sdk.base.data.TouchPoint
 *  com.onyx.android.sdk.base.lite.extension.CollectionKt
 *  com.onyx.android.sdk.extension.CollectionKt
 *  com.onyx.android.sdk.pen.NeoPen
 *  com.onyx.android.sdk.pen.NeoPencilPen
 *  com.onyx.android.sdk.pen.PenBrushResult
 *  com.onyx.android.sdk.pen.PenResult
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.lite.extension.CollectionKt;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.NeoPenRender;
import com.onyx.android.sdk.pen.NeoPencilPen;
import com.onyx.android.sdk.pen.PenBrushResult;
import com.onyx.android.sdk.pen.PenResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J(\u0010\u0012\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J8\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015H\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0016J\u0012\u0010\u001f\u001a\u00020\u00182\b\u0010 \u001a\u0004\u0018\u00010\rH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006!"}, d2={"Lcom/onyx/android/sdk/pen/PencilNeoPenRender;", "Lcom/onyx/android/sdk/pen/NeoPenRender;", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPencilPen;", "(Lcom/onyx/android/sdk/pen/NeoPencilPen;)V", "brushPointCount", "", "getBrushPointCount", "()I", "setBrushPointCount", "(I)V", "onTouchDone", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "realPoint", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "repaint", "", "onTouchDown", "onTouchMove", "realPointList", "", "predictPoint", "render", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "points", "reset", "updateBrushPointsCount", "penResult", "onyxsdk-pen_release"})
public final class PencilNeoPenRender
extends NeoPenRender {
    private int e;

    /*
     * WARNING - void declaration
     */
    public PencilNeoPenRender(@NotNull NeoPencilPen neoPen) {
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"neoPen");
        super((NeoPen)var1_1);
    }

    private final void a(PenResult penResult) {
        PenBrushResult penBrushResult;
        penBrushResult = penResult instanceof PenBrushResult ? (PenBrushResult)penBrushResult : null;
        if (penBrushResult == null) {
            return;
        }
        this.e += PenBrushResult.Companion.getPointsCount(penBrushResult);
    }

    public final int getBrushPointCount() {
        return this.e;
    }

    /*
     * WARNING - void declaration
     */
    public final void setBrushPointCount(int n) {
        void var1_1;
        this.e = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void render(@NotNull Canvas canvas, @NotNull Paint paint, @NotNull List<? extends TouchPoint> points) {
        ArrayList arrayList;
        Object object;
        void var2_2;
        void var1_1;
        List<? extends TouchPoint> list = points;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"canvas");
        Intrinsics.checkNotNullParameter((Object)var2_2, (String)"paint");
        Intrinsics.checkNotNullParameter(list, (String)"points");
        Object object2 = (TouchPoint)CollectionsKt.firstOrNull(list);
        if (object2 == null) {
            return;
        }
        TouchPoint touchPoint = (TouchPoint)CollectionsKt.lastOrNull((List)object);
        if (touchPoint == null) {
            return;
        }
        void v1 = object;
        List list2 = CollectionKt.safelySubList((List)v1, (int)1, (int)(v1.size() - 1));
        PencilNeoPenRender pencilNeoPenRender = this;
        pencilNeoPenRender.renderResult((PenResult)pencilNeoPenRender.getNeoPen().onPenDown((TouchPoint)object2, true).getFirst(), (Canvas)var1_1, (Paint)var2_2);
        object = arrayList;
        arrayList = new ArrayList();
        for (Object e : com.onyx.android.sdk.extension.CollectionKt.split((List)list2, (int)1000)) {
            if (!(((List)e).isEmpty() ^ true)) continue;
            object.add(e);
        }
        object = object.iterator();
        while (object.hasNext()) {
            PencilNeoPenRender pencilNeoPenRender2 = this;
            object2 = (List)object.next();
            pencilNeoPenRender2.renderResult((PenResult)pencilNeoPenRender2.getNeoPen().onPenMove((List)object2, null, true).getFirst(), (Canvas)var1_1, (Paint)var2_2);
        }
        PencilNeoPenRender pencilNeoPenRender3 = this;
        pencilNeoPenRender3.renderResult((PenResult)pencilNeoPenRender3.getNeoPen().onPenUp(touchPoint, true).getFirst(), (Canvas)var1_1, (Paint)var2_2);
        pencilNeoPenRender3.reset();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Pair<PenResult, PenResult> onTouchDown(@NotNull TouchPoint realPoint, boolean repaint) {
        void var2_2;
        void var1_1;
        Intrinsics.checkNotNullParameter((Object)var1_1, (String)"realPoint");
        this.e = 0;
        return super.onTouchDown((TouchPoint)var1_1, (boolean)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Pair<PenResult, PenResult> onTouchMove(@NotNull List<? extends TouchPoint> realPointList, @Nullable TouchPoint predictPoint, boolean repaint) {
        void var3_3;
        void var2_2;
        void var1_1;
        void v0 = var1_1;
        Intrinsics.checkNotNullParameter((Object)v0, (String)"realPointList");
        Pair<PenResult, PenResult> pair = super.onTouchMove((List<? extends TouchPoint>)v0, (TouchPoint)var2_2, (boolean)var3_3);
        this.a((PenResult)pair.getFirst());
        return pair;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Pair<PenResult, PenResult> onTouchDone(@NotNull TouchPoint realPoint, boolean repaint) {
        void var2_2;
        void var1_1;
        void v0 = var1_1;
        Intrinsics.checkNotNullParameter((Object)v0, (String)"realPoint");
        Pair<PenResult, PenResult> pair = super.onTouchDone((TouchPoint)v0, (boolean)var2_2);
        this.a((PenResult)pair.getFirst());
        return pair;
    }

    @Override
    public void reset() {
        super.reset();
        this.e = 0;
    }
}

