/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.extension.RectKt
 *  kotlin.Metadata
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package com.onyx.android.sdk.pen.extension;

import android.graphics.RectF;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.extension.RectKt;
import com.onyx.android.sdk.pen.data.TouchPointList;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\b\u00a8\u0006\t"}, d2={"calculateBoundingRect", "Landroid/graphics/RectF;", "", "Lcom/onyx/android/sdk/data/note/TouchPoint;", "drawRadius", "", "filterByGridSpacing", "getBoundingRect", "Lcom/onyx/android/sdk/pen/data/TouchPointList;", "onyxsdk-pen_release"})
public final class TouchPointListKt {
    @NotNull
    public static final RectF getBoundingRect(@NotNull TouchPointList $this$getBoundingRect) {
        TouchPointList touchPointList;
        RectF rectF;
        TouchPointList touchPointList2 = $this$getBoundingRect;
        Intrinsics.checkNotNullParameter((Object)touchPointList2, (String)"<this>");
        RectF rectF2 = rectF;
        rectF = new RectF();
        if (touchPointList2.getPoints().isEmpty()) {
            return rectF2;
        }
        List<TouchPoint> list = touchPointList.getPoints();
        Intrinsics.checkNotNullExpressionValue(list, (String)"points");
        TouchPoint touchPoint = (TouchPoint)CollectionsKt.first(list);
        float f = touchPoint.x;
        float f2 = touchPoint.y;
        rectF2.set(f, f2, f, f2);
        for (TouchPoint touchPoint2 : touchPointList.getPoints()) {
            f2 = touchPoint2.x;
            rectF2.union(f2, touchPoint2.y);
        }
        return rectF2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<TouchPoint> filterByGridSpacing(@NotNull List<? extends TouchPoint> $this$filterByGridSpacing, float drawRadius) {
        ArrayList<TouchPoint> arrayList;
        LinkedHashMap linkedHashMap;
        LinkedHashMap linkedHashMap2;
        void var1_1;
        List<? extends TouchPoint> list = $this$filterByGridSpacing;
        void v1 = var1_1;
        Intrinsics.checkNotNullParameter(linkedHashMap2, (String)"<this>");
        linkedHashMap2 = linkedHashMap;
        linkedHashMap = new LinkedHashMap();
        double d = (double)v1 / Math.sqrt(2.0);
        var1_1 = v1 * v1;
        int n = list.size() - 1;
        ArrayList<TouchPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<TouchPoint>();
        int n2 = 0;
        for (Object t : list) {
            boolean bl;
            int n3;
            block9: {
                n3 = n2 + 1;
                if (n2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                TouchPoint touchPoint = (TouchPoint)t;
                if (n == n2) {
                    bl = true;
                } else {
                    TouchPoint touchPoint2 = touchPoint;
                    n2 = (int)((double)touchPoint2.x / d);
                    int n4 = (int)((double)touchPoint2.y / d);
                    int n5 = -1;
                    while (n5 < 2) {
                        int n6 = n5 + 1;
                        int n7 = -1;
                        while (n7 < 2) {
                            int n8 = n7 + 1;
                            TouchPoint touchPoint3 = (TouchPoint)linkedHashMap2.get(TuplesKt.to((Object)(n2 + n5), (Object)(n4 + n7)));
                            if (touchPoint3 != null) {
                                float f;
                                float f2 = touchPoint3.x - touchPoint.x;
                                float f3 = f = touchPoint3.y - touchPoint.y;
                                if (f2 * f2 + f3 * f3 <= var1_1) {
                                    bl = false;
                                    break block9;
                                }
                            }
                            n7 = n8;
                        }
                        n5 = n6;
                    }
                    linkedHashMap2.put(TuplesKt.to((Object)n2, (Object)n4), touchPoint);
                    bl = true;
                }
            }
            if (bl) {
                arrayList2.add((TouchPoint)t);
            }
            n2 = n3;
        }
        return arrayList2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final RectF calculateBoundingRect(@NotNull List<? extends TouchPoint> $this$calculateBoundingRect, float drawRadius) {
        void var1_3;
        RectF rectF;
        List<? extends TouchPoint> list = $this$calculateBoundingRect;
        Intrinsics.checkNotNullParameter(list, (String)"<this>");
        TouchPoint touchPoint = (TouchPoint)CollectionsKt.first(list);
        RectF rectF2 = rectF;
        TouchPoint touchPoint2 = touchPoint;
        rectF2();
        float f = touchPoint2.x;
        float f2 = touchPoint2.y;
        rectF.set(f, f2, f, f2);
        for (TouchPoint touchPoint3 : list) {
            f2 = touchPoint3.x;
            rectF2.union(f2, touchPoint3.y);
        }
        return RectKt.expand((RectF)rectF2, (float)var1_3);
    }
}

