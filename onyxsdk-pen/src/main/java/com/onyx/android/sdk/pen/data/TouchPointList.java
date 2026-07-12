package com.onyx.android.sdk.pen.data;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.data.point.PointDocumentUtils;
import com.onyx.android.sdk.data.point.TinyPoint;
import com.onyx.android.sdk.pen.utils.PenUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.MatrixUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.nustaq.serialization.annotations.Flat;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/data/TouchPointList.class */
@Flat
public class TouchPointList implements Serializable, Cloneable {

    @Flat
    private List<TouchPoint> a;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/data/TouchPointList$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        static {
            int[] iArr = new int[MirrorType.values().length];
            a = iArr;
            try {
                iArr[MirrorType.XAxisMirror.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[MirrorType.YAxisMirror.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPointList() {
        this.a = new ArrayList();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static RectF getBoundingRect(List<TouchPoint> list) {
        RectF rectF = new RectF();
        if (CollectionUtils.isNullOrEmpty(list)) {
            return rectF;
        }
        TouchPoint touchPoint = (TouchPoint) CollectionUtils.getFirst(list);
        rectF.set(touchPoint.getX(), touchPoint.getY(), touchPoint.getX(), touchPoint.getY());
        for (TouchPoint touchPoint2 : list) {
            rectF.union(touchPoint2.getX(), touchPoint2.getY());
        }
        return rectF;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List<TouchPoint> getPoints() {
        return this.a;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Move duplicate insns, count: 1 to block B:7:0x0021 */
    @JSONField(serialize = false, deserialize = false)
    public final List<TouchPoint> getRenderPoints() {
        int size = size();
        if (size <= 20000) {
            return this.a;
        }
        int pointCountBreakValue = PointDocumentUtils.getPointCountBreakValue(size);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= size) {
                Debug.w(getClass(), "large touch point count:" + size + ", render count: " + CollectionUtils.getSize(arrayList), new Object[0]);
                return arrayList;
            }
            arrayList.add(this.a.get(i2));
            i = i2 + pointCountBreakValue;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPointList setPoints(List<TouchPoint> list) {
        this.a = list;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint first() {
        return (TouchPoint) CollectionUtils.getFirst(this.a);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint last() {
        return (TouchPoint) CollectionUtils.getLast(this.a);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int size() {
        return this.a.size();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint get(int i) {
        return this.a.get(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void add(TouchPoint touchPoint) {
        this.a.add(touchPoint);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void addAll(TouchPointList other) {
        CollectionUtils.safeAddAll(this.a, other.getPoints());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public List<TinyPoint> detachPointList() {
        List<TinyPoint> tinyPointList = toTinyPointList();
        this.a.clear();
        return tinyPointList;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public List<TinyPoint> toTinyPointList() {
        ArrayList arrayList = new ArrayList();
        if (CollectionUtils.isNullOrEmpty(this.a)) {
            return arrayList;
        }
        long timestamp = this.a.get(0).getTimestamp();
        Iterator<TouchPoint> it = this.a.iterator();
        while (it.hasNext()) {
            arrayList.add(TouchPoint.toTinyPoint(it.next(), timestamp));
        }
        return arrayList;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public Iterator<TouchPoint> iterator() {
        return this.a.iterator();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPointList applyMatrix(Matrix matrix) {
        if (MatrixUtils.isEmptyMatrix(matrix)) {
            return this;
        }
        for (TouchPoint touchPoint : this.a) {
            float[] fArr = {touchPoint.x, touchPoint.y};
            matrix.mapPoints(fArr);
            touchPoint.x = fArr[0];
            touchPoint.y = fArr[1];
        }
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPointList cloneMatrixPoints(Matrix matrix) {
        TouchPointList touchPointListClone = clone();
        touchPointListClone.applyMatrix(matrix);
        return touchPointListClone;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void scaleAllPoints(float scaleValue) {
        for (TouchPoint touchPoint : this.a) {
            touchPoint.x *= scaleValue;
            touchPoint.y *= scaleValue;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void translateAllPoints(float dx, float dy) {
        for (TouchPoint touchPoint : this.a) {
            touchPoint.x += dx;
            touchPoint.y += dy;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void rotateAllPoints(float rotateAngle, PointF originPoint) {
        Matrix matrix = new Matrix();
        matrix.setRotate(rotateAngle, originPoint.x, originPoint.y);
        for (TouchPoint touchPoint : this.a) {
            float[] fArr = {touchPoint.x, touchPoint.y};
            matrix.mapPoints(fArr);
            touchPoint.x = fArr[0];
            touchPoint.y = fArr[1];
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void mirrorAllPoints(MirrorType type, float translateDistance) {
        Matrix matrix = new Matrix();
        if (type == MirrorType.XAxisMirror) {
            matrix.setScale(-1.0f, 1.0f);
            matrix.postTranslate(translateDistance, PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY);
        } else if (type == MirrorType.YAxisMirror) {
            matrix.setScale(1.0f, -1.0f);
            matrix.postTranslate(PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, translateDistance);
        }
        for (TouchPoint touchPoint : this.a) {
            float[] fArr = {touchPoint.x, touchPoint.y};
            matrix.mapPoints(fArr);
            touchPoint.x = fArr[0];
            touchPoint.y = fArr[1];
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isEmpty() {
        return CollectionUtils.isNullOrEmpty(this.a);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void clear() {
        this.a = new ArrayList();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public void add(int index, TouchPoint touchPoint) {
        this.a.add(index, touchPoint);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public void addAll(int index, List<TouchPoint> pointList) {
        this.a.addAll(index, pointList);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    @NonNull
    public TouchPointList clone() {
        TouchPointList touchPointList = new TouchPointList();
        if (this.a.isEmpty()) {
            return touchPointList;
        }
        for (TouchPoint touchPoint : this.a) {
            if (touchPoint != null) {
                touchPointList.add(touchPoint.clone());
            }
        }
        return touchPointList;
    }

    public TouchPointList(int size) {
        this.a = new ArrayList(size);
    }

    public static TouchPointList detachPointList(TouchPointList touchPointList) {
        TouchPointList touchPointList2 = new TouchPointList(touchPointList);
        touchPointList.clear();
        return touchPointList2;
    }

    public TouchPointList addAll(List<TinyPoint> pointList) {
        if (CollectionUtils.isNullOrEmpty(pointList)) {
            return this;
        }
        Iterator<TinyPoint> it = pointList.iterator();
        while (it.hasNext()) {
            this.a.add(TouchPoint.fromTinyPoint(it.next()));
        }
        return this;
    }

    public void scaleAllPoints(float sx, float sy) {
        for (TouchPoint touchPoint : this.a) {
            touchPoint.x *= Math.abs(sx);
            touchPoint.y *= Math.abs(sy);
        }
    }

    public TouchPointList(TouchPointList other) {
        this.a = new ArrayList();
        addAll(other);
    }

    public TouchPointList(List<TouchPoint> list) {
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        CollectionUtils.safeAddAll(arrayList, list);
    }
}
