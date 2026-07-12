// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import android.graphics.Matrix;
import androidx.annotation.NonNull;
import java.util.Map;
import java.util.LinkedHashMap;
import com.onyx.android.sdk.data.RectSortResultInfo;
import java.util.HashMap;
import android.util.Pair;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import android.graphics.PointF;
import java.util.ArrayList;
import android.graphics.RectF;
import java.util.Iterator;
import android.graphics.Rect;
import java.util.List;

public class RectUtils
{
    public static final String TAG = "RectUtils";
    private static boolean a = false;
    public static final int DEFAULT_RECT_CENTER_DISTANCE_ERROR_RANGE = 2;
    static final int b = -3;
    static final int c = 2;
    
    public static boolean isDebug() {
        return RectUtils.a;
    }
    
    public static String toString(final List<Rect> list) {
        if (list != null && !list.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            boolean b = true;
            final Iterator<Rect> iterator = list.iterator();
            while (iterator.hasNext()) {
                final boolean b2 = b;
                final Rect rect = iterator.next();
                if (!b2) {
                    sb.append(", ");
                }
                sb.append(rect.toString());
                b = false;
            }
            return sb.toString();
        }
        return "";
    }
    
    public static Rect toRect(final RectF source) {
        if (source == null) {
            return null;
        }
        return new Rect((int)source.left, (int)source.top, (int)source.right, (int)source.bottom);
    }
    
    public static Rect toRectByCeil(final RectF source) {
        if (source == null) {
            return null;
        }
        return new Rect((int)Math.ceil(source.left), (int)Math.ceil(source.top), (int)Math.ceil(source.right), (int)Math.ceil(source.bottom));
    }
    
    public static Rect toRectByFloor(final RectF source) {
        if (source == null) {
            return null;
        }
        return new Rect((int)Math.floor(source.left), (int)Math.floor(source.top), (int)Math.floor(source.right), (int)Math.floor(source.bottom));
    }
    
    public static List<Rect> toRectList(final List<RectF> source) {
        if (source == null) {
            return null;
        }
        final ArrayList<Rect> list = new ArrayList<Rect>();
        final Iterator<RectF> iterator = source.iterator();
        while (iterator.hasNext()) {
            list.add(toRect(iterator.next()));
        }
        return list;
    }
    
    public static RectF toRectF(final Rect source) {
        if (source == null) {
            return null;
        }
        return new RectF((float)source.left, (float)source.top, (float)source.right, (float)source.bottom);
    }
    
    public static RectF remove(final RectF parent, float childTop, float childHeight, float spacing) {
        if ((childTop = childTop + childHeight + spacing) >= parent.bottom) {
            return null;
        }
        final float left = parent.left;
        childHeight = parent.right;
        spacing = parent.bottom;
        return new RectF(left, childTop, childHeight, spacing);
    }
    
    public static RectF rectangle(final double[] result) {
        return new RectF((float)result[0], (float)result[1], (float)result[2], (float)result[3]);
    }
    
    public static PointF getBeginRightTop(final List<RectF> list) {
        final RectF rectF = list.get(0);
        return new PointF(rectF.right, rectF.top);
    }
    
    public static PointF getBeginRightBottom(final List<RectF> list) {
        final RectF rectF = CollectionUtils.getFirst(list);
        return new PointF(rectF.right, rectF.bottom);
    }
    
    public static PointF getBeginLeftTop(final List<RectF> list) {
        final RectF rectF = list.get(0);
        return new PointF(rectF.left, rectF.top);
    }
    
    public static PointF getBeginLeftBottom(final List<RectF> list) {
        final RectF rectF = list.get(0);
        return new PointF(rectF.left, rectF.bottom);
    }
    
    public static PointF getEndRightBottom(final List<RectF> list) {
        final RectF rectF = list.get(list.size() - 1);
        return new PointF(rectF.right, rectF.bottom);
    }
    
    public static PointF getEndRightTop(final List<RectF> list) {
        final RectF rectF = list.get(list.size() - 1);
        return new PointF(rectF.right, rectF.top);
    }
    
    public static PointF getEndLeftBottom(final List<RectF> list) {
        final RectF rectF = list.get(list.size() - 1);
        return new PointF(rectF.left, rectF.bottom);
    }
    
    public static PointF getEndLeftTop(final List<RectF> list) {
        final RectF rectF = CollectionUtils.getLast(list);
        return new PointF(rectF.left, rectF.top);
    }
    
    public static float getDistance(final RectF a, final RectF b) {
        final float n = a.centerX() - b.centerX();
        final float n2 = a.centerY() - b.centerY();
        final float n3 = n * n;
        final float n4 = n2;
        return (float)Math.sqrt(n3 + n4 * n4);
    }
    
    public static boolean isSameCenterWithinErrorRange(final Rect a, final Rect b) {
        return isSameCenterWithinErrorRange(a, b, 2);
    }
    
    public static boolean isSameCenterWithinErrorRange(final Rect a, final Rect b, final int errorRange) {
        final float distance;
        final boolean b2 = (distance = getDistance(toRectF(a), toRectF(b))) <= errorRange;
        if (!b2) {
            Debug.d("isSameCenterWithinErrorRange, two rect center distance = " + distance);
        }
        return b2;
    }
    
    public static ArrayList<RectF> mergeAdjacentRectangles(final List<RectF> list) {
        ArrayList<RectF> mergeAdjacentRectangles = new ArrayList<RectF>();
        if (CollectionUtils.isNullOrEmpty(list)) {
            return mergeAdjacentRectangles;
        }
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            final ArrayList<RectF> list2 = mergeAdjacentRectangles;
            final RectF a = iterator.next();
            boolean b = false;
            final Iterator<RectF> iterator2 = list2.iterator();
            while (iterator2.hasNext()) {
                final RectF rectF;
                if (canMerge(a, rectF = iterator2.next())) {
                    final RectF rectF2 = rectF;
                    b = true;
                    rectF2.union(a);
                    break;
                }
            }
            if (!b) {
                mergeAdjacentRectangles.add(new RectF(a));
            }
            else {
                mergeAdjacentRectangles = mergeAdjacentRectangles(mergeAdjacentRectangles);
            }
        }
        return mergeAdjacentRectangles;
    }
    
    public static boolean canMerge(final RectF a, final RectF b) {
        final float left;
        final float left2;
        return (left = a.left) <= b.right && (left2 = b.left) <= a.right && a.top <= b.bottom && b.top <= a.bottom && ((Float.compare(left, left2) == 0 && Float.compare(a.right, b.right) == 0) || (Float.compare(a.top, b.top) == 0 && Float.compare(a.bottom, b.bottom) == 0));
    }
    
    public static List<RectF> cutRectByExcludingRegions(RectF source, final List<RectF> excluding) {
        ArrayList<RectF> regions = new ArrayList<>();
        if (source == null) return regions;
        regions.add(normalizedOf(source));
        if (excluding == null) return regions;
        for (RectF excluded : excluding) {
            if (excluded == null || excluded.isEmpty()) continue;
            RectF cut = normalizedOf(excluded);
            ArrayList<RectF> next = new ArrayList<>();
            for (RectF region : regions) {
                RectF intersection = new RectF(region);
                if (!intersection.intersect(cut)) {
                    next.add(region);
                    continue;
                }
                if (region.top < intersection.top) next.add(new RectF(region.left, region.top, region.right, intersection.top));
                if (intersection.bottom < region.bottom) next.add(new RectF(region.left, intersection.bottom, region.right, region.bottom));
                if (region.left < intersection.left) next.add(new RectF(region.left, intersection.top, intersection.left, intersection.bottom));
                if (intersection.right < region.right) next.add(new RectF(intersection.right, intersection.top, region.right, intersection.bottom));
            }
            regions = next;
        }
        return regions;
    }

    private static void a(final List<RectF> list, final RectF rect) {
        boolean b = false;
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            final RectF rectF;
            if ((rectF = iterator.next()).left == rect.left && rectF.top == rect.top && rectF.right == rect.right && rectF.bottom == rect.bottom) {
                b = true;
                break;
            }
        }
        if (!b) {
            list.add(rect);
        }
    }
    
    public static void expand(final Rect rectF, int value) {
        if (rectF == null) {
            return;
        }
        final int n = rectF.left - value;
        final int n2 = rectF.top - value;
        final int n3 = rectF.right + value;
        value += rectF.bottom;
        rectF.set(n, n2, n3, value);
    }
    
    public static void expand(final RectF rectF, float value) {
        if (rectF == null) {
            return;
        }
        final float n = rectF.left - value;
        final float n2 = rectF.top - value;
        final float n3 = rectF.right + value;
        value += rectF.bottom;
        rectF.set(n, n2, n3, value);
    }
    
    public static void expand(final RectF rectF, float paddingLeftRight, float paddingTopBottom) {
        if (rectF == null) {
            return;
        }
        final float n = rectF.left - paddingLeftRight;
        final float n2 = rectF.top - paddingTopBottom;
        paddingLeftRight += rectF.right;
        paddingTopBottom += rectF.bottom;
        rectF.set(n, n2, paddingLeftRight, paddingTopBottom);
    }
    
    public static void expand(final RectF rectF, float paddingLeft, float paddingTop, final float paddingRight, final float paddingBottom) {
        if (rectF == null) {
            return;
        }
        final float n = rectF.left - paddingLeft;
        final float n2 = rectF.top - paddingTop;
        paddingLeft = rectF.right + paddingRight;
        paddingTop = rectF.bottom + paddingBottom;
        rectF.set(n, n2, paddingLeft, paddingTop);
    }
    
    public static float[] clampExpandArray(final RectF child, final RectF parent, final int expand) {
        final float a = (float)expand;
        final float a3;
        final float a2;
        return new float[] { Math.min(a2 = (a3 = a), child.left - parent.left), Math.min(a2, child.top - parent.top), Math.min(a3, parent.right - child.right), Math.min(a, parent.bottom - child.bottom) };
    }
    
    public static float square(final List<RectF> list) {
        float n = 0.0f;
        if (list == null || list.size() <= 0) {
            return 0.0f;
        }
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            Debug.e((Class)RectUtils.class, "square rect -> " + iterator.next().toString(), new Object[0]);
        }
        if (list.size() == 1) {
            final float f = list.get(0).width() * list.get(0).height();
            Debug.e((Class)RectUtils.class, "square result -> " + f, new Object[0]);
            return f;
        }
        final HashMap hashMap = new HashMap();
        int n6;
        for (int i = 0; i < list.size(); i = n6) {
            int j;
            int n2;
            for (n2 = (j = i + 1); j < list.size(); ++j) {
                final RectF rectF;
                if ((rectF = new RectF((RectF)list.get(i))).intersect(new RectF((RectF)list.get(j)))) {
                    final HashMap hashMap2 = hashMap;
                    final int k = i;
                    final Object[] array2;
                    final Object[] array = array2 = new Object[3];
                    final RectF rectF2 = rectF;
                    final Object[] array3 = array2;
                    final int l = j;
                    array2[0] = i;
                    array3[1] = l;
                    array[2] = rectF2.toString();
                    Debug.e((Class)RectUtils.class, "intersects: %d, %d -> %s", array2);
                    if (hashMap2.get(k) == null) {
                        hashMap.put(i, new ArrayList());
                    }
                    final HashMap hashMap3 = hashMap;
                    final int m = j;
                    a((List<RectF>)hashMap.get(i), rectF);
                    if (hashMap3.get(m) == null) {
                        hashMap.put(j, new ArrayList());
                    }
                    a((List<RectF>)hashMap.get(j), rectF);
                }
            }
            final float n3 = n;
            final RectF rectF3;
            final float n4 = (rectF3 = list.get(i)).width() * rectF3.height();
            final float square;
            final float n5 = n3 + (n4 - (square = square((List<RectF>)hashMap.get(i))));
            final Object[] array5;
            final Object[] array4 = array5 = new Object[4];
            final float f2 = n5;
            final Object[] array6 = array5;
            final float f3 = square;
            final Object[] array7 = array5;
            final float f4 = n4;
            array5[0] = rectF3.toString();
            array7[1] = f4;
            array6[2] = f3;
            array4[3] = f2;
            Debug.e((Class)RectUtils.class, "%s -> %f, intersections -> %f, square -> %f", array5);
            n6 = n2;
            n = n5;
        }
        final HashMap hashMap4 = hashMap;
        final ArrayList<RectF> list2 = new ArrayList<RectF>();
        final Iterator iterator2 = hashMap4.values().iterator();
        while (iterator2.hasNext()) {
            final Iterator iterator3 = ((List)iterator2.next()).iterator();
            while (iterator3.hasNext()) {
                a(list2, (RectF)iterator3.next());
            }
        }
        final float n7 = n;
        final float square2 = square(list2);
        Debug.e((Class)RectUtils.class, "intersections square -> " + square2, new Object[0]);
        final float f5 = n7 + square2;
        Debug.e((Class)RectUtils.class, "square result -> " + f5, new Object[0]);
        return f5;
    }
    
    public static void translate(final RectF rectF, float dx, float dy) {
        if (rectF == null) {
            return;
        }
        final float n = rectF.left + dx;
        final float n2 = rectF.top + dy;
        dx += rectF.right;
        dy += rectF.bottom;
        rectF.set(n, n2, dx, dy);
    }
    
    public static void translate(final Rect rect, int dx, int dy) {
        if (rect == null) {
            return;
        }
        final int n = rect.left + dx;
        final int n2 = rect.top + dy;
        dx += rect.right;
        dy += rect.bottom;
        rect.set(n, n2, dx, dy);
    }
    
    public static void directionScale(final RectF rectF, float sx, float sy, final RectF relativelyRectF) {
        if (rectF == null) {
            return;
        }
        final float sy2 = sy;
        final float distanceXAfterScale = getDistanceXAfterScale(relativelyRectF, sx);
        final float distanceYAfterScale = getDistanceYAfterScale(relativelyRectF, sy2);
        final float n = distanceXAfterScale;
        final float n2 = rectF.left * Math.abs(sx);
        final float n3 = rectF.top * Math.abs(sy);
        sx = rectF.right * Math.abs(sx);
        sy = rectF.bottom * Math.abs(sy);
        rectF.set(n2, n3, sx, sy);
        translate(rectF, -n, -distanceYAfterScale);
    }
    
    public static void scale(final RectF rectF, float sx, float sy) {
        if (rectF == null) {
            return;
        }
        final float n = rectF.left * sx;
        final float n2 = rectF.top * sy;
        sx *= rectF.right;
        sy *= rectF.bottom;
        rectF.set(n, n2, sx, sy);
    }
    
    public static void scale(final Rect rect, final float sx, final float sy) {
        if (rect == null) {
            return;
        }
        rect.set((int)(rect.left * sx), (int)(rect.top * sy), (int)(rect.right * sx), (int)(rect.bottom * sy));
    }
    
    public static RectF createByScale(final RectF origin, final float sx, final float sy) {
        final RectF rectF = new RectF(origin);
        scale(rectF, sx, sy);
        return rectF;
    }
    
    public static float getDistanceXAfterScale(final RectF rectF, final float sx) {
        return (sx > 0.0f) ? (rectF.left * Math.abs(sx) - rectF.left) : (rectF.right * Math.abs(sx) - rectF.right);
    }
    
    public static float getDistanceYAfterScale(final RectF rectF, final float sy) {
        return (sy > 0.0f) ? (rectF.top * Math.abs(sy) - rectF.top) : (rectF.bottom * Math.abs(sy) - rectF.bottom);
    }
    
    public static void normalize(final RectF region) {
        region.set(Math.min(region.left, region.right), Math.min(region.top, region.bottom), Math.max(region.left, region.right), Math.max(region.top, region.bottom));
    }
    
    public static RectF normalizedOf(final RectF region) {
        return new RectF(Math.min(region.left, region.right), Math.min(region.top, region.bottom), Math.max(region.left, region.right), Math.max(region.top, region.bottom));
    }
    
    public static RectF boundingRect(final List<RectF> list) {
        final RectF rectF = new RectF();
        if (list != null && list.size() > 0) {
            final Iterator<RectF> iterator = list.iterator();
            while (iterator.hasNext()) {
                rectF.union((RectF)iterator.next());
            }
            return rectF;
        }
        return rectF;
    }
    
    public static boolean intersect(final RectF o1, final RectF o2) {
        return o1 != null && o2 != null && o1.intersect(o2);
    }
    
    public static boolean intersects(final RectF o1, final RectF o2) {
        return o1 != null && o2 != null && RectF.intersects(o1, o2);
    }
    
    public static boolean intersect(final Rect o1, final Rect o2) {
        return o1 != null && o2 != null && o1.intersect(o2);
    }
    
    public static boolean isNullOrEmpty(final Rect rect) {
        return rect == null || rect.isEmpty();
    }
    
    public static boolean isNullOrEmpty(final RectF rect) {
        return rect == null || rect.isEmpty();
    }
    
    public static boolean isNotBlank(final RectF rect) {
        return isNullOrEmpty(rect) ^ true;
    }
    
    public static boolean contains(final Rect container, final Rect subset) {
        return container != null && subset != null && container.left <= subset.left && container.top <= subset.top && container.right >= subset.right && container.bottom >= subset.bottom;
    }
    
    public static boolean contains(final RectF container, final RectF subset) {
        return container != null && subset != null && container.left <= subset.left && container.top <= subset.top && container.right >= subset.right && container.bottom >= subset.bottom;
    }
    
    public static boolean hasEdgesOverlap(final Rect rect1, final Rect rect2) {
        return rect1 != null && rect2 != null && (rect1.left == rect2.left || rect1.top == rect2.top || rect1.right == rect2.right || rect1.bottom == rect2.bottom);
    }
    
    public static RectF computeRelativeRect(final RectF compareParentRect, final RectF compareRect, final RectF parentRect) {
        final float n = parentRect.width() * compareRect.left / compareParentRect.width();
        final float n2 = parentRect.height() * compareRect.top / compareParentRect.height();
        return new RectF(n, n2, n + parentRect.width() * compareRect.width() / compareParentRect.width(), n2 + parentRect.height() * compareRect.height() / compareParentRect.height());
    }
    
    public static boolean isSameSize(final RectF rect1, final RectF rect2) {
        return rect1 != null && rect2 != null && rect1.width() == rect2.width() && rect2.height() == rect2.height();
    }
    
    public static List<RectF> mergeRectanglesByDistance(final List<RectF> list, final int distanceThreshold) {
        if (CollectionUtils.isNullOrEmpty(list)) return list;
        int originalSize = list.size();
        int comparisons = 0;
        Benchmark benchmark = new Benchmark();
        a(list);
        long sortDuration = benchmark.duration();
        benchmark.restart();
        boolean merged;
        do {
            merged = false;
            outer:
            for (int i = 0; i < list.size(); i++) {
                RectF first = list.get(i);
                for (int j = i + 1; j < list.size(); j++) {
                    comparisons++;
                    RectF second = list.get(j);
                    if (!first.equals(second) && isDistanceInThreshold(first, second, distanceThreshold)) {
                        first.union(second);
                        list.remove(j);
                        merged = true;
                        break outer;
                    }
                }
            }
        } while (merged);
        if (Debug.getDebug()) {
            Debug.d("RectUtils", "mergeRectanglesByDistance compare count = " + comparisons
                    + ", raw input size = " + originalSize + ", output size = " + list.size()
                    + ", sort time = " + sortDuration + " ms, merge time = "
                    + benchmark.duration() + " ms", new Object[0]);
        }
        return list;
    }

    private static void a(final List<RectF> list) {
        Collections.sort(list, new Comparator<RectF>() {
            public int compare(final RectF o1, final RectF o2) {
                final float left = o1.left;
                final float left2;
                if (left - (left2 = o2.left) > 0.0f) {
                    return 1;
                }
                if (left - left2 < 0.0f) {
                    return -1;
                }
                final float top = o1.top;
                final float top2;
                if (top - (top2 = o2.top) > 0.0f) {
                    return 1;
                }
                if (top - top2 < 0.0f) {
                    return -1;
                }
                final float right = o1.right;
                final float right2;
                if (right - (right2 = o2.right) > 0.0f) {
                    return 1;
                }
                if (right - right2 < 0.0f) {
                    return -1;
                }
                final float bottom = o1.bottom;
                final float bottom2;
                if (bottom - (bottom2 = o2.bottom) > 0.0f) {
                    return 1;
                }
                if (bottom - bottom2 < 0.0f) {
                    return -1;
                }
                return 0;
            }
        });
    }
    
    public static RectSortResultInfo sortTBLR(final List<RectF> inputRectList) {
        final RectSortResultInfo rectSortResultInfo = new RectSortResultInfo();
        final ArrayList<RectF> sortResultList;
        final ArrayList<RectF> list = sortResultList = new ArrayList<>(inputRectList);
        b(list);
        final LinkedHashMap<Integer, List<RectF>> columnMap = new LinkedHashMap<>();
        final LinkedHashMap<Integer, RectF> columnBoundingRectMap = new LinkedHashMap<>();
        a(list, columnMap, columnBoundingRectMap);
        final ArrayList<RectF> boundingRectList = new ArrayList<RectF>();
        list.clear();
        final Iterator<Map.Entry<Integer, List<RectF>>> iterator = columnMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final ArrayList list2 = sortResultList;
            final ArrayList<RectF> list3 = boundingRectList;
            final Map.Entry<Integer, List<RectF>> entry = iterator.next();
            d(entry.getValue());
            list3.add(columnBoundingRectMap.get(entry.getKey()));
            list2.addAll(entry.getValue());
        }
        final RectSortResultInfo rectSortResultInfo2 = rectSortResultInfo;
        rectSortResultInfo2.setBoundingRectList(boundingRectList);
        rectSortResultInfo2.setSortResultList((List<RectF>)sortResultList);
        return rectSortResultInfo2;
    }
    
    public static RectSortResultInfo sortTBRL(final List<RectF> inputRectList) {
        final RectSortResultInfo rectSortResultInfo = new RectSortResultInfo();
        final ArrayList<RectF> sortResultList;
        final ArrayList<RectF> list = sortResultList = new ArrayList<>(inputRectList);
        c(list);
        final LinkedHashMap<Integer, List<RectF>> columnMap = new LinkedHashMap<>();
        final LinkedHashMap<Integer, RectF> columnBoundingRectMap = new LinkedHashMap<>();
        b(list, columnMap, columnBoundingRectMap);
        final ArrayList<RectF> boundingRectList = new ArrayList<RectF>();
        list.clear();
        final Iterator<Map.Entry<Integer, List<RectF>>> iterator = columnMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final ArrayList list2 = sortResultList;
            final ArrayList<RectF> list3 = boundingRectList;
            final Map.Entry<Integer, List<RectF>> entry = iterator.next();
            d(entry.getValue());
            list3.add(columnBoundingRectMap.get(entry.getKey()));
            list2.addAll(entry.getValue());
        }
        final RectSortResultInfo rectSortResultInfo2 = rectSortResultInfo;
        rectSortResultInfo2.setBoundingRectList(boundingRectList);
        rectSortResultInfo2.setSortResultList((List<RectF>)sortResultList);
        return rectSortResultInfo2;
    }
    
    private static void c(final List<RectF> list) {
        Collections.sort(list, new Comparator<RectF>() {
            public int compare(final RectF o1, final RectF o2) {
                final float right = o1.right;
                final float right2;
                if (right - (right2 = o2.right) > 0.0f) {
                    return -1;
                }
                if (right - right2 < 0.0f) {
                    return 1;
                }
                final float left = o1.left;
                final float left2;
                if (left - (left2 = o2.left) > 0.0f) {
                    return -1;
                }
                if (left - left2 < 0.0f) {
                    return 1;
                }
                final float top = o1.top;
                final float top2;
                if (top - (top2 = o2.top) > 0.0f) {
                    return 1;
                }
                if (top - top2 < 0.0f) {
                    return -1;
                }
                final float bottom = o1.bottom;
                final float bottom2;
                if (bottom - (bottom2 = o2.bottom) > 0.0f) {
                    return 1;
                }
                if (bottom - bottom2 < 0.0f) {
                    return -1;
                }
                return 0;
            }
        });
    }
    
    private static void b(final List<RectF> list) {
        Collections.sort(list, new Comparator<RectF>() {
            public int compare(final RectF o1, final RectF o2) {
                final float left = o1.left;
                final float left2;
                if (left - (left2 = o2.left) < 0.0f) {
                    return -1;
                }
                if (left - left2 > 0.0f) {
                    return 1;
                }
                final float right = o1.right;
                final float right2;
                if (right - (right2 = o2.right) < 0.0f) {
                    return -1;
                }
                if (right - right2 > 0.0f) {
                    return 1;
                }
                final float top = o1.top;
                final float top2;
                if (top - (top2 = o2.top) > 0.0f) {
                    return 1;
                }
                if (top - top2 < 0.0f) {
                    return -1;
                }
                final float bottom = o1.bottom;
                final float bottom2;
                if (bottom - (bottom2 = o2.bottom) > 0.0f) {
                    return 1;
                }
                if (bottom - bottom2 < 0.0f) {
                    return -1;
                }
                return 0;
            }
        });
    }
    
    private static void d(final List<RectF> list) {
        Collections.sort(list, new Comparator<RectF>() {
            public int compare(final RectF o1, final RectF o2) {
                final float top = o1.top;
                final float top2;
                if (top - (top2 = o2.top) > 0.0f) {
                    return 1;
                }
                if (top - top2 < 0.0f) {
                    return -1;
                }
                final float bottom = o1.bottom;
                final float bottom2;
                if (bottom - (bottom2 = o2.bottom) > 0.0f) {
                    return 1;
                }
                if (bottom - bottom2 < 0.0f) {
                    return -1;
                }
                final float right = o1.right;
                final float right2;
                if (right - (right2 = o2.right) > 0.0f) {
                    return -1;
                }
                if (right - right2 < 0.0f) {
                    return 1;
                }
                final float left = o1.left;
                final float left2;
                if (left - (left2 = o2.left) > 0.0f) {
                    return -1;
                }
                if (left - left2 < 0.0f) {
                    return 1;
                }
                return 0;
            }
        });
    }
    
    private static void b(final List<RectF> list, final Map<Integer, List<RectF>> columnMap, final Map<Integer, RectF> columnBoundingRectMap) {
        int n = -1;
        RectF rectF = null;
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            final RectF rectF2 = rectF;
            final RectF rectF3 = iterator.next();
            if (rectF2 != null && rectF.left <= rectF3.right) {
                (rectF = columnBoundingRectMap.get(n)).union(rectF3);
            }
            else {
                final int i = ++n;
                rectF = new RectF(rectF3);
                columnBoundingRectMap.put(i, rectF);
            }
            CollectionUtils.ensureList(columnMap, n).add(rectF3);
        }
    }
    
    private static void a(final List<RectF> list, final Map<Integer, List<RectF>> columnMap, final Map<Integer, RectF> columnBoundingRectMap) {
        int n = -1;
        RectF rectF = null;
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            final RectF rectF2 = rectF;
            final RectF rectF3 = iterator.next();
            if (rectF2 != null && rectF.right >= rectF3.left) {
                (rectF = columnBoundingRectMap.get(n)).union(rectF3);
            }
            else {
                final int i = ++n;
                rectF = new RectF(rectF3);
                columnBoundingRectMap.put(i, rectF);
            }
            CollectionUtils.ensureList(columnMap, n).add(rectF3);
        }
    }
    
    public static boolean isDistanceInThreshold(final RectF o1, final RectF o2, final int threshold) {
        if (o1 != null && o2 != null) {
            final float distanceByQuadrant;
            if ((distanceByQuadrant = getDistanceByQuadrant(o1, o2)) < 0.0f) {
                Debug.e("RectUtils", "getDistanceByQuadrant = " + distanceByQuadrant + ", o1 = " + o1 + ", o2 = " + o2, new Object[0]);
            }
            return distanceByQuadrant <= threshold;
        }
        return false;
    }
    
    public static float getDistanceByQuadrant(final RectF rect1, final RectF rect2) {
        if (isIntersect(rect1, rect2)) {
            return 0.0f;
        }
        float n;
        float n2;
        float n3;
        float n7;
        float n8;
        if ((n = rect1.bottom) < (n2 = rect2.bottom)) {
            float n5;
            if ((n3 = rect1.left) > rect2.left) {
                final float n4 = n;
                n5 = rect2.right;
                if (n4 > (n2 = rect2.top)) {
                    return n3 - n5;
                }
                if (n3 < n5) {
                    return n2 - n;
                }
            }
            else {
                n5 = rect2.right;
                if ((n = rect1.top) > n2) {
                    return n5 - n3;
                }
                if (n3 > n5) {
                    return n2 - n;
                }
            }
            final float n6 = n5;
            n7 = n;
            n8 = n6;
        }
        else if ((n3 = rect1.left) < (n8 = rect2.left)) {
            n3 = rect1.right;
            if ((n7 = rect1.top) < n2) {
                return n8 - n3;
            }
            if (n3 > n8) {
                return n7 - n2;
            }
        }
        else {
            n8 = rect2.right;
            if ((n7 = rect1.top) < n2) {
                return n3 - n8;
            }
            if (n3 < n8) {
                return n7 - n2;
            }
        }
        final float n9 = n3 - n8;
        final float n10 = n7 - n2;
        final float n11 = n9 * n9;
        final float n12 = n10;
        return (float)Math.sqrt(n11 + n12 * n12);
    }
    
    public static boolean isIntersect(final RectF o1, final RectF o2) {
        return o1.left < o2.right && o2.left < o1.right && o1.top < o2.bottom && o2.top < o1.bottom;
    }
    
    public static boolean isIntersectOnX(final RectF o1, final RectF o2) {
        return o1.left < o2.right && o2.left < o1.right;
    }
    
    public static boolean isIntersectOnY(final RectF o1, final RectF o2) {
        return o1.top < o2.bottom && o2.top < o1.bottom;
    }
    
    public static List<RectF> cleanEmptyRect(final List<RectF> list) {
        final ArrayList list2 = new ArrayList();
        final Iterator<RectF> iterator = list.iterator();
        while (iterator.hasNext()) {
            final RectF rectF;
            if (!(rectF = iterator.next()).isEmpty()) {
                list2.add(rectF);
            }
        }
        return list2;
    }
    
    @NonNull
    public static List<RectF> loadEqualRectList(final List<RectF> rectList1, final List<RectF> rectList2) {
        final ArrayList list = new ArrayList();
    Label_0015:
        for (final RectF rectF : rectList1) {
            final RectF rectF2 = null;
            final Iterator<RectF> iterator2 = rectList2.iterator();
            while (true) {
                while (iterator2.hasNext()) {
                    final RectF rectF3;
                    if (rectF.equals((Object)(rectF3 = iterator2.next()))) {
                        list.add(rectF3);
                        if (rectF3 != null) {
                            rectList2.remove(rectF3);
                            continue Label_0015;
                        }
                        continue Label_0015;
                    }
                }
                RectF rectF3 = rectF2;
                continue;
            }
        }
        return list;
    }
    
    public static void unionDiffRectList(final RectF output, final List<RectF> rectList1, final List<RectF> rectList2) {
        final List<RectF> loadEqualRectList;
        rectList2.removeAll(loadEqualRectList = loadEqualRectList(rectList2, rectList1));
        rectList1.removeAll(loadEqualRectList);
        union(output, rectList2);
        union(output, rectList1);
    }
    
    public static void union(final RectF output, final List<RectF> inputList) {
        if (CollectionUtils.isNullOrEmpty(inputList)) {
            return;
        }
        final Iterator<RectF> iterator = inputList.iterator();
        while (iterator.hasNext()) {
            output.union((RectF)iterator.next());
        }
    }
    
    public static void union(final RectF output, final RectF input) {
        if (output != null && input != null) {
            output.union(input);
        }
    }
    
    public static RectF union(final List<RectF> inputList) {
        final RectF output = new RectF();
        union(output, inputList);
        return output;
    }
    
    public static float getRectFSize(final RectF rectF) {
        return rectF.width() * rectF.height();
    }
    
    public static void roundRect(final RectF rectF) {
        rectF.left = (float)Math.round(rectF.left);
        rectF.top = (float)Math.round(rectF.top);
        rectF.right = (float)Math.round(rectF.right);
        rectF.bottom = (float)Math.round(rectF.bottom);
    }
    
    public static List<PointF> mapPoints(final Matrix matrix, final List<PointF> pointList) {
        if (matrix != null && !CollectionUtils.isNullOrEmpty(pointList)) {
            final ArrayList<PointF> list = new ArrayList<PointF>();
            final Iterator<PointF> iterator = pointList.iterator();
            while (iterator.hasNext()) {
                final ArrayList<PointF> list2 = list;
                final PointF pointF = iterator.next();
                list2.add(mapPoints(matrix, pointF.x, pointF.y));
            }
            return list;
        }
        return pointList;
    }
    
    public static PointF mapPoints(final Matrix matrix, final float x, final float y) {
        final float[] array2;
        final float[] array = array2 = new float[2];
        array[0] = x;
        array[1] = y;
        if (matrix != null) {
            matrix.mapPoints(array2);
        }
        final float[] array3 = array2;
        return new PointF(array3[0], array3[1]);
    }
    
    public static void mapPoints(final Matrix matrix, final PointF pointF) {
        if (pointF == null) {
            return;
        }
        final float[] array2;
        final float[] array = array2 = new float[2];
        array2[0] = pointF.x;
        array[1] = pointF.y;
        if (matrix != null) {
            matrix.mapPoints(array2);
        }
        final float[] array3 = array2;
        pointF.x = array2[0];
        pointF.y = array3[1];
    }
    
    public static RectF roundExpandRectF(final RectF rectF) {
        if (rectF == null) {
            return null;
        }
        return new RectF((float)Math.floor(rectF.left), (float)Math.floor(rectF.top), (float)Math.ceil(rectF.right), (float)Math.ceil(rectF.bottom));
    }
    
    public static List<Rect> addNonEmptyRect(final List<Rect> rectList, final Rect... rects) {
        if (rects != null && rects.length > 0) {
            for (int length = rects.length, i = 0; i < length; ++i) {
                final Rect rect;
                if (!isNullOrEmpty(rect = rects[i])) {
                    rectList.add(rect);
                }
            }
            return rectList;
        }
        return rectList;
    }
    
    public static RectF offset(final RectF rectF, final float x, final float y) {
        rectF.offset(x, y);
        return rectF;
    }
    
    public static Rect offset(final Rect rect, final int x, final int y) {
        rect.offset(x, y);
        return rect;
    }
    
    public static boolean inVerticalRect(final RectF rectF, final float y) {
        return y > rectF.top && y < rectF.bottom;
    }
    
    public static boolean inHorizontalRect(final RectF rectF, final float x) {
        return x > rectF.left && x < rectF.right;
    }
    
    public static PointF limitOffsetInLimitRect(final RectF limitRect, float oldX, final float oldY, final int width, final int height, final PointF offsetXY) {
        final float n = oldX;
        final float x = offsetXY.x;
        final float y = offsetXY.y;
        final int n2 = (int)(n + x);
        final int n3 = (int)(oldY + y);
        final int n4 = n2;
        final int n5 = n3;
        final float n6 = (float)n2;
        final float n7 = (float)n5;
        final float n8 = (float)(n4 + width);
        final float n9 = (float)(n3 + height);
        if (limitRect.contains(n6, n7, n8, n9)) {
            return offsetXY;
        }
        final float left;
        if ((left = limitRect.left) >= n6 && x < 0.0f) {
            if (left < oldX) {
                offsetXY.x = (float)(int)(left - oldX);
            }
            else {
                offsetXY.x = 0.0f;
            }
        }
        final float right;
        if ((right = limitRect.right) <= n8 && x > 0.0f) {
            final float n10;
            if (right > oldX + (n10 = (float)width)) {
                offsetXY.x = (float)(int)(right - oldX - n10);
            }
            else {
                offsetXY.x = 0.0f;
            }
        }
        if ((oldX = limitRect.top) >= n7 && y < 0.0f) {
            if (oldX < oldY) {
                offsetXY.y = (float)(int)(oldX - oldY);
            }
            else {
                offsetXY.y = 0.0f;
            }
        }
        final float bottom;
        if ((bottom = limitRect.bottom) <= n9 && y > 0.0f) {
            if (bottom > oldY + (oldX = (float)height)) {
                offsetXY.y = (float)(int)(bottom - oldY - oldX);
            }
            else {
                offsetXY.y = 0.0f;
            }
        }
        return offsetXY;
    }
    
    public static void ensureRectSize(final RectF srcRectF, final RectF dstRectF, float minWidth, final float minHeight) {
        if (dstRectF.width() < minWidth) {
            final float left;
            if (srcRectF.left != (left = dstRectF.left)) {
                dstRectF.left = dstRectF.right - minWidth;
            }
            else if (srcRectF.right != dstRectF.right) {
                dstRectF.right = left + minWidth;
            }
        }
        if (dstRectF.height() < minHeight) {
            if (srcRectF.top != (minWidth = dstRectF.top)) {
                dstRectF.top = dstRectF.bottom - minHeight;
            }
            else if (srcRectF.bottom != dstRectF.bottom) {
                dstRectF.bottom = minWidth + minHeight;
            }
        }
    }
    
    public static void limitRect(final RectF limitRect, final RectF rect) {
        final float left;
        final float left2;
        if ((left = rect.left) < (left2 = limitRect.left)) {
            final float n;
            rect.left = left + (n = left2 - left);
            final float n2 = rect.right + n;
            float right;
            if (n2 <= (right = limitRect.right)) {
                right = n2;
            }
            rect.right = right;
        }
        else {
            final float right2 = rect.right;
            final float right3;
            if (right2 > (right3 = limitRect.right)) {
                final float n3 = left;
                final float n4 = right2;
                final float n5;
                rect.right = n4 - (n5 = n4 - right3);
                float left3;
                if ((left3 = n3 - n5) < left2) {
                    left3 = left2;
                }
                rect.left = left3;
            }
        }
        final float top;
        final float top2;
        if ((top = rect.top) < (top2 = limitRect.top)) {
            final float n6;
            rect.top = top + (n6 = top2 - top);
            final float n7 = rect.bottom + n6;
            float bottom;
            if (n7 <= (bottom = limitRect.bottom)) {
                bottom = n7;
            }
            rect.bottom = bottom;
        }
        else {
            final float bottom2 = rect.bottom;
            final float bottom3;
            if (bottom2 > (bottom3 = limitRect.bottom)) {
                final float n8 = top;
                final float n9 = bottom2;
                final float n10;
                rect.bottom = n9 - (n10 = n9 - bottom3);
                float top3;
                if ((top3 = n8 - n10) < top2) {
                    top3 = top2;
                }
                rect.top = top3;
            }
        }
    }
}
