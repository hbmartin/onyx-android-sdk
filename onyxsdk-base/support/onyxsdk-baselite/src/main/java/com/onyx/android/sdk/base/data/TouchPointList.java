package com.onyx.android.sdk.base.data;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 62\u00020\u00012\u00020\u0002:\u00016B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\u0000¢\u0006\u0002\u0010\bB\u0017\b\u0016\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n¢\u0006\u0002\u0010\fJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000bJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000bJ\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u0000J\u001c\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u001e\u0010\u0013\u001a\u00020\u00002\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00002\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0006\u0010\u001b\u001a\u00020\u0010J\b\u0010\u001c\u001a\u00020\u0000H\u0016J\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\nJ\u0011\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u0005H\u0086\u0002J\u0006\u0010 \u001a\u00020!J\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0006\u0010$\u001a\u00020%J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0'H\u0086\u0002J\u0014\u0010(\u001a\u00020\u00102\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0016\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020\u0017J\u0016\u0010-\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u00172\u0006\u00100\u001a\u00020\u0017J\u0016\u00101\u001a\u00020\u00002\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000eJ\u0006\u0010\u0004\u001a\u00020\u0005J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020\u00150\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017J\u0016\u00103\u001a\u00020\u00102\u0006\u00104\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u0017R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lcom/onyx/android/sdk/base/data/TouchPointList;", "Ljava/io/Serializable;", "", "()V", "size", "", "(I)V", "other", "(Lcom/onyx/android/sdk/base/data/TouchPointList;)V", "list", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "(Ljava/util/List;)V", "points", "", "add", "", "touchPoint", "index", "addAll", "pointList", "Lcom/onyx/android/sdk/base/data/TinyPoint;", "maxPressure", "", "applyMatrix", "matrix", "Landroid/graphics/Matrix;", "clear", "clone", "detachPointList", "get", "i", "getBoundingRect", "Landroid/graphics/RectF;", "getPoints", "getRenderPoints", "isEmpty", "", "iterator", "", "removeAll", "rotateAllPoints", "rotateAngle", "originPoint", "Landroid/graphics/PointF;", "scaleAllPoints", "scaleValue", "sx", "sy", "setPoints", "toTinyPointList", "translateAllPoints", "dx", "dy", "Companion", "sdk-baselite_release"})
public final class TouchPointList implements Serializable, Cloneable {

    @NotNull
    private List<TouchPoint> points;

    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Matrix EMPTY = new Matrix();

    public TouchPointList() {
        this.points = new ArrayList();
    }

    public TouchPointList(int size) {
        this.points = new ArrayList(size);
    }

    public TouchPointList(@NotNull TouchPointList other) {
        Intrinsics.checkNotNullParameter(other, "other");
        this.points = new ArrayList();
        addAll(other);
    }

    public TouchPointList(@Nullable List<? extends TouchPoint> list) {
        this.points = new ArrayList();
        if (list != null) {
            List<? extends TouchPoint> $this$forEach$iv = list;
            for (Object element$iv : $this$forEach$iv) {
                TouchPoint it = (TouchPoint) element$iv;
                this.points.add(it);
            }
        }
    }

    @NotNull
    public final List<TouchPoint> getPoints() {
        return this.points;
    }

    @NotNull
    public final TouchPointList setPoints(@Nullable List<TouchPoint> list) {
        if (list != null) {
            this.points = list;
        }
        return this;
    }

    @NotNull
    public final List<TouchPoint> getRenderPoints() {
        return this.points;
    }

    public final int size() {
        return this.points.size();
    }

    @NotNull
    public final TouchPoint get(int i) {
        return this.points.get(i);
    }

    public final void add(@NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "touchPoint");
        this.points.add(touchPoint);
    }

    public final void add(int index, @NotNull TouchPoint touchPoint) {
        Intrinsics.checkNotNullParameter(touchPoint, "touchPoint");
        this.points.add(index, touchPoint);
    }

    public final void addAll(@NotNull TouchPointList other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Iterable $this$forEach$iv = other.getPoints();
        for (Object element$iv : $this$forEach$iv) {
            TouchPoint it = (TouchPoint) element$iv;
            this.points.add(it);
        }
    }

    public final void addAll(int index, @NotNull List<? extends TouchPoint> pointList) {
        Intrinsics.checkNotNullParameter(pointList, "pointList");
        this.points.addAll(index, pointList);
    }

    public static /* synthetic */ TouchPointList addAll$default(TouchPointList touchPointList, List list, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 1.0f;
        }
        return touchPointList.addAll((List<TinyPoint>) list, f);
    }

    @NotNull
    public final TouchPointList addAll(@NotNull List<TinyPoint> pointList, float maxPressure) {
        Intrinsics.checkNotNullParameter(pointList, "pointList");
        if (pointList.isEmpty()) {
            return this;
        }
        for (TinyPoint tinyPoint : pointList) {
            this.points.add(TouchPoint.INSTANCE.fromTinyPoint(tinyPoint, maxPressure));
        }
        return this;
    }

    public final void removeAll(@NotNull List<? extends TouchPoint> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.points.removeAll(list);
    }

    @NotNull
    public final List<TinyPoint> detachPointList() {
        List<TinyPoint> tinyPointList$default = toTinyPointList$default(this, 0.0f, 1, null);
        this.points.clear();
        return tinyPointList$default;
    }

    public static /* synthetic */ List toTinyPointList$default(TouchPointList touchPointList, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        return touchPointList.toTinyPointList(f);
    }

    @NotNull
    public final List<TinyPoint> toTinyPointList(float maxPressure) {
        List tinyPointList = new ArrayList();
        if (this.points.isEmpty()) {
            return tinyPointList;
        }
        long compareTime = this.points.get(0).timestamp;
        for (TouchPoint point : this.points) {
            tinyPointList.add(TouchPoint.INSTANCE.toTinyPoint(point, compareTime, maxPressure));
        }
        return tinyPointList;
    }

    @NotNull
    public final Iterator<TouchPoint> iterator() {
        return this.points.iterator();
    }

    @NotNull
    public final TouchPointList applyMatrix(@Nullable Matrix matrix) {
        if (matrix == null) {
            return this;
        }
        if (INSTANCE.isEmptyMatrix(matrix)) {
            return this;
        }
        for (TouchPoint point : this.points) {
            float[] pts = {point.x, point.y};
            matrix.mapPoints(pts);
            point.x = pts[0];
            point.y = pts[1];
        }
        return this;
    }

    public final void scaleAllPoints(float scaleValue) {
        for (TouchPoint point : this.points) {
            point.x *= scaleValue;
            point.y *= scaleValue;
        }
    }

    public final void scaleAllPoints(float sx, float sy) {
        for (TouchPoint point : this.points) {
            point.x *= Math.abs(sx);
            point.y *= Math.abs(sy);
        }
    }

    public final void translateAllPoints(float dx, float dy) {
        for (TouchPoint point : this.points) {
            point.x += dx;
            point.y += dy;
        }
    }

    public final void rotateAllPoints(float rotateAngle, @NotNull PointF originPoint) {
        Intrinsics.checkNotNullParameter(originPoint, "originPoint");
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.setRotate(rotateAngle, originPoint.x, originPoint.y);
        for (TouchPoint point : this.points) {
            float[] pts = {point.x, point.y};
            rotateMatrix.mapPoints(pts);
            point.x = pts[0];
            point.y = pts[1];
        }
    }

    @NotNull
    public TouchPointList clone() {
        TouchPointList list = new TouchPointList();
        if (this.points.isEmpty()) {
            return list;
        }
        for (TouchPoint point : this.points) {
            if (point != null) {
                list.add(point.clone());
            }
        }
        return list;
    }

    public final boolean isEmpty() {
        return this.points == null || this.points.isEmpty();
    }

    public final void clear() {
        this.points = new ArrayList();
    }

    @NotNull
    public final RectF getBoundingRect() {
        return INSTANCE.getBoundingRect(getPoints());
    }

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/onyx/android/sdk/base/data/TouchPointList$Companion;", "", "()V", "EMPTY", "Landroid/graphics/Matrix;", "getBoundingRect", "Landroid/graphics/RectF;", "list", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "isEmptyMatrix", "", "matrix", "pointsFromByteArray", "Lcom/onyx/android/sdk/base/data/TouchPointList;", "blob", "", "pointsToByteArray", "touchPointList", "sdk-baselite_release"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final boolean isEmptyMatrix(@Nullable Matrix matrix) {
            return matrix == null || Intrinsics.areEqual(matrix, TouchPointList.EMPTY);
        }

        @Nullable
        public final byte[] pointsToByteArray(@Nullable TouchPointList touchPointList) {
            if (touchPointList == null || touchPointList.isEmpty()) {
                return new byte[0];
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try (DataOutputStream data = new DataOutputStream(output)) {
                for (TouchPoint point : touchPointList.getPoints()) {
                    data.writeFloat(point.x);
                    data.writeFloat(point.y);
                    data.writeFloat(point.pressure);
                    data.writeFloat(point.size);
                    data.writeInt(point.tiltX);
                    data.writeInt(point.tiltY);
                    data.writeLong(point.timestamp);
                }
                return output.toByteArray();
            } catch (IOException exception) {
                return sneakyThrow(exception);
            }
        }

        @NotNull
        public final TouchPointList pointsFromByteArray(@Nullable byte[] blob) {
            TouchPointList points = new TouchPointList(100);
            if (blob == null) {
                return points;
            }
            try (DataInputStream data = new DataInputStream(new ByteArrayInputStream(blob))) {
                for (int index = 0; index < blob.length / 32; index++) {
                    float x = data.readFloat();
                    float y = data.readFloat();
                    float pressure = data.readFloat();
                    float size = data.readFloat();
                    int tiltX = data.readInt();
                    int tiltY = data.readInt();
                    long eventTime = data.readLong();
                    TouchPoint point = new TouchPoint(x, y, pressure, size, eventTime);
                    point.tiltX = tiltX;
                    point.tiltY = tiltY;
                    points.add(point);
                }
                return points;
            } catch (IOException exception) {
                return sneakyThrow(exception);
            }
        }

        @SuppressWarnings("unchecked")
        private static <T, E extends Throwable> T sneakyThrow(Throwable failure) throws E {
            throw (E) failure;
        }

        @NotNull
        public final RectF getBoundingRect(@NotNull List<? extends TouchPoint> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            RectF boundingRect = new RectF();
            if (list.isEmpty()) {
                return boundingRect;
            }
            TouchPoint first = (TouchPoint) CollectionsKt.first(list);
            boundingRect.set(first.x, first.y, first.x, first.y);
            for (TouchPoint touchPoint : list) {
                boundingRect.union(touchPoint.x, touchPoint.y);
            }
            return boundingRect;
        }
    }
}
