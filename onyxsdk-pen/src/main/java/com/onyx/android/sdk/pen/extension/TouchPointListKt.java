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

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\b¨\u0006\t"}, d2 = {"calculateBoundingRect", "Landroid/graphics/RectF;", "", "Lcom/onyx/android/sdk/data/note/TouchPoint;", "drawRadius", "", "filterByGridSpacing", "getBoundingRect", "Lcom/onyx/android/sdk/pen/data/TouchPointList;", "onyxsdk-pen_release"})
public final class TouchPointListKt {
    private TouchPointListKt() {
    }

    @NotNull
    public static final RectF getBoundingRect(@NotNull TouchPointList $this$getBoundingRect) {
        Intrinsics.checkNotNullParameter($this$getBoundingRect, "<this>");
        RectF rectF = new RectF();
        if ($this$getBoundingRect.getPoints().isEmpty()) {
            return rectF;
        }
        List<TouchPoint> points = $this$getBoundingRect.getPoints();
        Intrinsics.checkNotNullExpressionValue(points, "points");
        TouchPoint touchPoint = (TouchPoint) CollectionsKt.first(points);
        float f = touchPoint.x;
        float f2 = touchPoint.y;
        rectF.set(f, f2, f, f2);
        for (TouchPoint touchPoint2 : $this$getBoundingRect.getPoints()) {
            rectF.union(touchPoint2.x, touchPoint2.y);
        }
        return rectF;
    }

    @NotNull
    public static final List<TouchPoint> filterByGridSpacing(@NotNull List<? extends TouchPoint> list, float drawRadius) {
        boolean z;
        Intrinsics.checkNotNullParameter(list, "<this>");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        double dSqrt = ((double) drawRadius) / Math.sqrt(2.0d);
        float f = drawRadius * drawRadius;
        int size = list.size() - 1;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : list) {
            int i2 = i;
            int i3 = i + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TouchPoint touchPoint = (TouchPoint) obj;
            if (size != i) {
                int i4 = (int) (((double) touchPoint.x) / dSqrt);
                int i5 = (int) (((double) touchPoint.y) / dSqrt);
                int i6 = -1;
                scan: while (true) {
                    int i7 = i6;
                    if (i7 >= 2) {
                        linkedHashMap.put(TuplesKt.to(Integer.valueOf(i4), Integer.valueOf(i5)), touchPoint);
                        z = true;
                        break;
                    }
                    int i8 = i7 + 1;
                    int i9 = -1;
                    while (true) {
                        int i10 = i9;
                        if (i10 >= 2) {
                            break;
                        }
                        int i11 = i10 + 1;
                        TouchPoint touchPoint2 = (TouchPoint) linkedHashMap.get(TuplesKt.to(Integer.valueOf(i4 + i7), Integer.valueOf(i5 + i10)));
                        if (touchPoint2 != null) {
                            float f2 = touchPoint2.x - touchPoint.x;
                            float f3 = touchPoint2.y - touchPoint.y;
                            if ((f2 * f2) + (f3 * f3) <= f) {
                                z = false;
                                break scan;
                            }
                        }
                        i9 = i11;
                    }
                    i6 = i8;
                }
            } else {
                z = true;
            }
            if (z) {
                arrayList.add(obj);
            }
            i = i3;
        }
        return arrayList;
    }

    @NotNull
    public static final RectF calculateBoundingRect(@NotNull List<? extends TouchPoint> list, float drawRadius) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        TouchPoint touchPoint = (TouchPoint) CollectionsKt.first(list);
        RectF rectF = new RectF();
        float f = touchPoint.x;
        float f2 = touchPoint.y;
        rectF.set(f, f2, f, f2);
        for (TouchPoint touchPoint2 : list) {
            rectF.union(touchPoint2.x, touchPoint2.y);
        }
        return RectKt.expand(rectF, drawRadius);
    }
}
