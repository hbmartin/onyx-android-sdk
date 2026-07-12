package com.onyx.android.sdk.base.lite.extension;

import android.graphics.Path;
import android.graphics.PathMeasure;
import com.onyx.android.sdk.base.data.PathMeasurePointResult;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.lite.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Path.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/lite/extension/PathKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0006\u001a,\u0010\u0007\u001a\u00020\b*\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\b0\fH\u0086\bø\u0001\u0000\u001a \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0011"}, d2 = {"getQuadToEndPos", "", "current", "next", "factor", "copy", "Landroid/graphics/Path;", "forEachPoint", "", "step", "", "consumer", "Lkotlin/Function1;", "Lcom/onyx/android/sdk/base/data/PathMeasurePointResult;", "resampleMultiSegmentPath", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "sdk-baselite_release"})
public final class PathKt {
    @NotNull
    public static final Path copy(@NotNull Path $this$copy) {
        Intrinsics.checkNotNullParameter($this$copy, "<this>");
        Path path = new Path();
        path.addPath($this$copy);
        return path;
    }

    public static /* synthetic */ float getQuadToEndPos$default(float f, float f2, float f3, int i, Object obj) {
        if ((i & 4) != 0) {
            f3 = 0.7f;
        }
        return getQuadToEndPos(f, f2, f3);
    }

    public static final float getQuadToEndPos(float current, float next, float factor) {
        return current + ((next - current) * factor);
    }

    public static /* synthetic */ List resampleMultiSegmentPath$default(Path path, Number number, int i, Object obj) {
        if ((i & 1) != 0) {
            number = Float.valueOf(2.0f);
        }
        return resampleMultiSegmentPath(path, number);
    }

    @NotNull
    public static final List<List<TouchPoint>> resampleMultiSegmentPath(@NotNull Path $this$resampleMultiSegmentPath, @NotNull Number step) {
        float totalLength$iv;
        Object obj;
        int distance$iv;
        Intrinsics.checkNotNullParameter($this$resampleMultiSegmentPath, "<this>");
        Intrinsics.checkNotNullParameter(step, "step");
        List results = new ArrayList();
        PathMeasure measure$iv = new PathMeasure($this$resampleMultiSegmentPath, false);
        float currentLength$iv = 0.0f;
        float length = 0.0f;
        while (true) {
            totalLength$iv = length;
            if (!measure$iv.nextContour()) {
                break;
            }
            length = totalLength$iv + measure$iv.getLength();
        }
        PathMeasure measure$iv2 = new PathMeasure($this$resampleMultiSegmentPath, false);
        float[] tempPoint$iv = new float[2];
        float[] tempTan$iv = new float[2];
        int segmentIndex$iv = 0;
        do {
            float max$iv = measure$iv2.getLength() + step.floatValue();
            int i = (int) max$iv;
            int iIntValue = MathUtils.INSTANCE.atLeast(Integer.valueOf(step.intValue()), Integer.valueOf(1)).intValue();
            if (iIntValue <= 0) {
                throw new IllegalArgumentException("Step must be positive, was: " + iIntValue + '.');
            }
            int i2 = 0;
            int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, i, iIntValue);
            if (0 <= progressionLastElement) {
                do {
                    distance$iv = i2;
                    i2 += iIntValue;
                    float length$iv = ((Number) MathUtils.INSTANCE.atMost(Float.valueOf(distance$iv), Float.valueOf(measure$iv2.getLength()))).floatValue();
                    currentLength$iv += length$iv;
                    boolean ret$iv = measure$iv2.getPosTan(length$iv, tempPoint$iv, tempTan$iv);
                    if (ret$iv) {
                        PathMeasurePointResult result$iv = new PathMeasurePointResult();
                        result$iv.setPoint(new TouchPoint(tempPoint$iv[0], tempPoint$iv[1]));
                        result$iv.setDirection((float) Math.atan2(tempTan$iv[1], tempTan$iv[0]));
                        result$iv.setCurrentLength(currentLength$iv);
                        result$iv.setTotalLength(totalLength$iv);
                        result$iv.setSegmentIndex(segmentIndex$iv);
                        results.add(result$iv);
                    }
                } while (distance$iv != progressionLastElement);
            }
            segmentIndex$iv++;
        } while (measure$iv2.nextContour());
        List $this$groupBy$iv = results;
        Map destination$iv$iv = new LinkedHashMap();
        for (Object element$iv$iv : $this$groupBy$iv) {
            PathMeasurePointResult it = (PathMeasurePointResult) element$iv$iv;
            Integer numValueOf = Integer.valueOf(it.getSegmentIndex());
            Object value$iv$iv$iv = destination$iv$iv.get(numValueOf);
            if (value$iv$iv$iv == null) {
                ArrayList arrayList = new ArrayList();
                destination$iv$iv.put(numValueOf, arrayList);
                obj = arrayList;
            } else {
                obj = value$iv$iv$iv;
            }
            List list$iv$iv = (List) obj;
            list$iv$iv.add(element$iv$iv);
        }
        Collection destination$iv$iv2 = new ArrayList(destination$iv$iv.size());
        for (Object itemObject$iv$iv : destination$iv$iv.entrySet()) {
            Map.Entry item$iv$iv = (Map.Entry) itemObject$iv$iv;
            Iterable $this$map$iv = (Iterable) item$iv$iv.getValue();
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv2 : $this$map$iv) {
                PathMeasurePointResult it2 = (PathMeasurePointResult) item$iv$iv2;
                destination$iv$iv3.add(it2.getPoint());
            }
            destination$iv$iv2.add((List) destination$iv$iv3);
        }
        List groupedPoints = (List) destination$iv$iv2;
        return groupedPoints;
    }

    public static final void forEachPoint(@NotNull Path $this$forEachPoint, @NotNull Number step, @NotNull Function1<? super PathMeasurePointResult, Unit> function1) {
        float totalLength;
        int distance;
        Intrinsics.checkNotNullParameter($this$forEachPoint, "<this>");
        Intrinsics.checkNotNullParameter(step, "step");
        Intrinsics.checkNotNullParameter(function1, "consumer");
        PathMeasure measure = new PathMeasure($this$forEachPoint, false);
        float currentLength = 0.0f;
        float length = 0.0f;
        while (true) {
            totalLength = length;
            if (!measure.nextContour()) {
                break;
            } else {
                length = totalLength + measure.getLength();
            }
        }
        PathMeasure measure2 = new PathMeasure($this$forEachPoint, false);
        float[] tempPoint = new float[2];
        float[] tempTan = new float[2];
        int segmentIndex = 0;
        do {
            float max = measure2.getLength() + step.floatValue();
            int i = (int) max;
            int iIntValue = MathUtils.INSTANCE.atLeast(Integer.valueOf(step.intValue()), Integer.valueOf(1)).intValue();
            if (iIntValue <= 0) {
                throw new IllegalArgumentException("Step must be positive, was: " + iIntValue + '.');
            }
            int i2 = 0;
            int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, i, iIntValue);
            if (0 <= progressionLastElement) {
                do {
                    distance = i2;
                    i2 += iIntValue;
                    float length2 = ((Number) MathUtils.INSTANCE.atMost(Float.valueOf(distance), Float.valueOf(measure2.getLength()))).floatValue();
                    currentLength += length2;
                    boolean ret = measure2.getPosTan(length2, tempPoint, tempTan);
                    if (ret) {
                        PathMeasurePointResult result = new PathMeasurePointResult();
                        result.setPoint(new TouchPoint(tempPoint[0], tempPoint[1]));
                        result.setDirection((float) Math.atan2(tempTan[1], tempTan[0]));
                        result.setCurrentLength(currentLength);
                        result.setTotalLength(totalLength);
                        result.setSegmentIndex(segmentIndex);
                        function1.invoke(result);
                    }
                } while (distance != progressionLastElement);
            }
            segmentIndex++;
        } while (measure2.nextContour());
    }
}
