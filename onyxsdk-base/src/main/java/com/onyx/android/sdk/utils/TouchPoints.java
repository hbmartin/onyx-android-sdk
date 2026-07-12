// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.Nullable;
import android.graphics.Matrix;
import android.graphics.PathMeasure;
import java.util.ArrayList;
import android.graphics.Path;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.RectF;
import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u0014\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u000f\u001a\u00020\u0010J'\u0010\u0011\u001a\u00020\u0006*\u00020\u00062\u0016\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0013\"\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u0004*\u0004\u0018\u00010\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0006¨\u0006\u0018" }, d2 = { "Lcom/onyx/android/sdk/utils/TouchPoints;", "", "()V", "calculateMinIntersectionAngle", "", "line1P1", "Lcom/onyx/android/sdk/data/note/TouchPoint;", "line1P2", "line2P1", "line2P2", "getBoundingRect", "Landroid/graphics/RectF;", "list", "", "parseTouchPointsFromPath", "path", "Landroid/graphics/Path;", "concatTransform", "matrixs", "", "Landroid/graphics/Matrix;", "(Lcom/onyx/android/sdk/data/note/TouchPoint;[Landroid/graphics/Matrix;)Lcom/onyx/android/sdk/data/note/TouchPoint;", "distance", "other", "onyxsdk-base_release" })
public final class TouchPoints
{
    @NotNull
    public static final TouchPoints INSTANCE;
    
    private TouchPoints() {
    }
    
    static {
        INSTANCE = new TouchPoints();
    }
    
    @NotNull
    public final RectF getBoundingRect(@NotNull final List<? extends TouchPoint> list) {
        Intrinsics.checkNotNullParameter((Object)list, "list");
        final RectF rectF = new RectF();
        if (list.isEmpty()) {
            return rectF;
        }
        final RectF rectF2 = rectF;
        final TouchPoint touchPoint = (TouchPoint)CollectionsKt.first((List)list);
        final float x = touchPoint.x;
        final float y = touchPoint.y;
        rectF2.set(x, y, x, y);
        final Iterator<? extends TouchPoint> iterator = list.iterator();
        while (iterator.hasNext()) {
            final RectF rectF3 = rectF;
            final TouchPoint touchPoint2 = (TouchPoint)iterator.next();
            rectF3.union(touchPoint2.x, touchPoint2.y);
        }
        return rectF;
    }
    
    @NotNull
    public final List<TouchPoint> parseTouchPointsFromPath(@NotNull final Path path) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        final ArrayList list = new ArrayList();
        final float[] array = new float[2];
        final int n = 2;
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        do {
            final float length = pathMeasure.getLength();
            float n2 = 0.0f;
            while (length > 0.0f && n2 <= length) {
                if (pathMeasure.getPosTan(n2, array, (float[])null)) {
                    final ArrayList list2 = list;
                    final TouchPoint touchPoint = new TouchPoint(array[0], array[1], 1.0f, 0.0f, 0L);
                    list2.add(touchPoint);
                }
                final float n4;
                final float n3;
                if ((n3 = n2 + (n4 = (float)n)) > length && n3 != length + n4) {
                    n2 = length;
                }
                else {
                    n2 = n3;
                }
            }
            if (pathMeasure.getPosTan(length, array, (float[])null)) {
                final ArrayList list3 = list;
                final TouchPoint o = new TouchPoint(array[0], array[1]);
                final TouchPoint touchPoint2;
                if ((touchPoint2 = (TouchPoint)CollectionsKt.lastOrNull((List)list3)) != null && touchPoint2.equalXY(o)) {
                    continue;
                }
                list.add(o);
            }
        } while (pathMeasure.nextContour());
        return list;
    }
    
    @NotNull
    public final TouchPoint concatTransform(@NotNull final TouchPoint $this$concatTransform, @NotNull final Matrix... matrixs) {
        Intrinsics.checkNotNullParameter((Object)$this$concatTransform, "<this>");
        Intrinsics.checkNotNullParameter((Object)matrixs, "matrixs");
        final Matrix matrix = new Matrix();
        for (int i = 0; i < matrixs.length; ++i) {
            Matrix matrix2;
            if ((matrix2 = matrixs[i]) == null) {
                matrix2 = new Matrix();
            }
            matrix.postConcat(matrix2);
        }
        final TouchPoint transform = $this$concatTransform.transform(matrix);
        Intrinsics.checkNotNullExpressionValue((Object)transform, "transform(matrix)");
        return transform;
    }
    
    public final float distance(@Nullable final TouchPoint $this$distance, @Nullable final TouchPoint other) {
        if ($this$distance == null) {
            return 0.0f;
        }
        if (other == null) {
            return 0.0f;
        }
        return TouchPoint.getPointDistance($this$distance.x, $this$distance.y, other.x, other.y);
    }
    
    public final float calculateMinIntersectionAngle(@NotNull final TouchPoint line1P1, @NotNull final TouchPoint line1P2, @NotNull final TouchPoint line2P1, @NotNull final TouchPoint line2P2) {
        Intrinsics.checkNotNullParameter((Object)line1P1, "line1P1");
        Intrinsics.checkNotNullParameter((Object)line1P2, "line1P2");
        Intrinsics.checkNotNullParameter((Object)line2P1, "line2P1");
        Intrinsics.checkNotNullParameter((Object)line2P2, "line2P2");
        final float n2;
        final float n = n2 = line1P2.x - line1P1.x;
        final float n3 = line1P2.y - line1P1.y;
        final float n4 = line2P2.x - line2P1.x;
        final float n6;
        final float n5;
        if (Math.abs(n5 = n * (n6 = line2P2.y - line2P1.y) - n3 * n4) < 1.0E-9f) {
            return (n2 * n4 + n3 * n6 >= 0.0f) ? 0.0f : 180.0f;
        }
        return (float)(Math.toDegrees(Math.acos(RangesKt.coerceIn((n2 * n4 + n3 * n6) / (Math.hypot(n2, n3) * Math.hypot(n4, n6)), -1.0, 1.0))) * Math.signum((double)n5));
    }
}
