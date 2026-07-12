/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  androidx.annotation.NonNull
 *  com.alibaba.fastjson2.annotation.JSONField
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.data.point.PointDocumentUtils
 *  com.onyx.android.sdk.data.point.TinyPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.Debug
 *  com.onyx.android.sdk.utils.MatrixUtils
 *  org.nustaq.serialization.annotations.Flat
 */
package com.onyx.android.sdk.pen.data;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.data.point.PointDocumentUtils;
import com.onyx.android.sdk.data.point.TinyPoint;
import com.onyx.android.sdk.pen.data.MirrorType;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.MatrixUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.nustaq.serialization.annotations.Flat;

@Flat
public class TouchPointList
implements Serializable,
Cloneable {
    @Flat
    private List<TouchPoint> a;

    public TouchPointList() {
        ArrayList arrayList;
        Cloneable this_ = arrayList;
        arrayList = new ArrayList();
        v1.a = this_;
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList(int size) {
        void var1_1;
        ArrayList arrayList;
        Cloneable this_ = arrayList;
        arrayList = new ArrayList((int)var1_1);
        v1.a = this_;
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList(TouchPointList other) {
        void var1_1;
        ArrayList arrayList;
        TouchPointList touchPointList = this_;
        Cloneable this_ = arrayList;
        arrayList = new ArrayList();
        touchPointList.a = this_;
        touchPointList.addAll((TouchPointList)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList(List<TouchPoint> list) {
        void var1_1;
        ArrayList<TouchPoint> arrayList;
        ArrayList<TouchPoint> arrayList2 = arrayList;
        arrayList2();
        this.a = arrayList2;
        CollectionUtils.safeAddAll(arrayList, (Collection)var1_1);
    }

    public static TouchPointList detachPointList(TouchPointList touchPointList) {
        TouchPointList touchPointList2;
        TouchPointList touchPointList3 = new TouchPointList(touchPointList2);
        touchPointList2.clear();
        return touchPointList3;
    }

    public static RectF getBoundingRect(List<TouchPoint> list) {
        List<TouchPoint> list2;
        RectF rectF;
        RectF rectF2 = rectF;
        rectF = new RectF();
        if (CollectionUtils.isNullOrEmpty(list)) {
            return rectF2;
        }
        TouchPoint touchPoint = (TouchPoint)CollectionUtils.getFirst(list2);
        float f = touchPoint.getX();
        float f2 = touchPoint.getY();
        float f3 = touchPoint.getX();
        float f4 = touchPoint.getY();
        rectF2.set(f, f2, f3, f4);
        for (TouchPoint touchPoint2 : list2) {
            f2 = touchPoint2.getX();
            rectF2.union(f2, touchPoint2.getY());
        }
        return rectF2;
    }

    public final List<TouchPoint> getPoints() {
        return this.a;
    }

    @JSONField(serialize=false, deserialize=false)
    public final List<TouchPoint> getRenderPoints() {
        ArrayList<TouchPoint> arrayList;
        int n = this.size();
        if (n <= 20000) {
            return this.a;
        }
        int n2 = PointDocumentUtils.getPointCountBreakValue((int)n);
        ArrayList<TouchPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<TouchPoint>();
        for (int i = 0; i < n; i += n2) {
            arrayList2.add(this.a.get(i));
        }
        Debug.w(this.getClass(), (String)("large touch point count:" + n + ", render count: " + CollectionUtils.getSize(arrayList2)), (Object[])new Object[0]);
        return arrayList2;
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList setPoints(List<TouchPoint> list) {
        void var1_1;
        this.a = var1_1;
        return this;
    }

    public TouchPoint first() {
        return (TouchPoint)CollectionUtils.getFirst(this.a);
    }

    public TouchPoint last() {
        return (TouchPoint)CollectionUtils.getLast(this.a);
    }

    public int size() {
        return this.a.size();
    }

    /*
     * WARNING - void declaration
     */
    public TouchPoint get(int i) {
        void var1_1;
        return this.a.get((int)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void add(TouchPoint touchPoint) {
        void var1_1;
        this.a.add((TouchPoint)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void add(int index, TouchPoint touchPoint) {
        void var2_2;
        void var1_1;
        this.a.add((int)var1_1, (TouchPoint)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public void addAll(TouchPointList other) {
        void var1_1;
        CollectionUtils.safeAddAll(this.a, var1_1.getPoints());
    }

    /*
     * WARNING - void declaration
     */
    public void addAll(int index, List<TouchPoint> pointList) {
        void var2_2;
        void var1_1;
        this.a.addAll((int)var1_1, (Collection<TouchPoint>)var2_2);
    }

    public TouchPointList addAll(List<TinyPoint> pointList) {
        Iterator iterator;
        if (CollectionUtils.isNullOrEmpty(pointList)) {
            return this;
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            TinyPoint tinyPoint = (TinyPoint)iterator.next();
            this.a.add(TouchPoint.fromTinyPoint((TinyPoint)tinyPoint));
        }
        return this;
    }

    public List<TinyPoint> detachPointList() {
        List<TinyPoint> list = this.toTinyPointList();
        this.a.clear();
        return list;
    }

    public List<TinyPoint> toTinyPointList() {
        ArrayList<TinyPoint> arrayList;
        ArrayList<TinyPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<TinyPoint>();
        if (CollectionUtils.isNullOrEmpty(((TouchPointList)((Object)this_)).a)) {
            return arrayList2;
        }
        TouchPointList touchPointList = this_;
        long l = touchPointList.a.get(0).getTimestamp();
        Iterator<TouchPoint> this_ = touchPointList.a.iterator();
        while (this_.hasNext()) {
            arrayList2.add(TouchPoint.toTinyPoint((TouchPoint)((TouchPoint)this_.next()), (long)l));
        }
        return arrayList2;
    }

    public Iterator<TouchPoint> iterator() {
        return this.a.iterator();
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList applyMatrix(Matrix matrix) {
        if (MatrixUtils.isEmptyMatrix((Matrix)matrix)) {
            return this;
        }
        for (TouchPoint touchPoint : this.a) {
            void var1_1;
            float[] fArray = new float[]{touchPoint.x, touchPoint.y};
            var1_1.mapPoints(fArray);
            touchPoint.x = fArray[0];
            var2_2.next().y = fArray[1];
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public TouchPointList cloneMatrixPoints(Matrix matrix) {
        void var1_1;
        TouchPointList touchPointList = this.clone();
        touchPointList.applyMatrix((Matrix)var1_1);
        return touchPointList;
    }

    /*
     * WARNING - void declaration
     */
    public void scaleAllPoints(float scaleValue) {
        for (TouchPoint touchPoint : ((TouchPointList)this).a) {
            void var1_1;
            touchPoint.x *= var1_1;
            touchPoint.y *= var1_1;
        }
    }

    /*
     * WARNING - void declaration
     */
    public void scaleAllPoints(float sx, float sy) {
        for (TouchPoint touchPoint : ((TouchPointList)this).a) {
            void var2_2;
            void var1_1;
            touchPoint.x *= Math.abs((float)var1_1);
            touchPoint.y *= Math.abs((float)var2_2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void translateAllPoints(float dx, float dy) {
        for (TouchPoint touchPoint : ((TouchPointList)this).a) {
            void var2_2;
            void var1_1;
            touchPoint.x += var1_1;
            touchPoint.y += var2_2;
        }
    }

    public void rotateAllPoints(float rotateAngle, PointF originPoint) {
        TouchPoint touchPoint2;
        float f;
        Matrix matrix;
        TouchPointList touchPointList = this_;
        TouchPointList this_ = matrix;
        void v2 = f;
        void v3 = touchPoint2;
        this_();
        f = v3.x;
        matrix.setRotate((float)v2, f, v3.y);
        for (TouchPoint touchPoint2 : touchPointList.a) {
            float[] fArray = new float[]{touchPoint2.x, touchPoint2.y};
            this_.mapPoints(fArray);
            touchPoint2.x = fArray[0];
            var1_2.next().y = fArray[1];
        }
    }

    /*
     * WARNING - void declaration
     */
    public void mirrorAllPoints(MirrorType type, float translateDistance) {
        void var2_4;
        void var1_1;
        Matrix matrix;
        Matrix matrix2 = matrix;
        matrix = new Matrix();
        int n = com.onyx.android.sdk.pen.data.TouchPointList$a.a[var1_1.ordinal()];
        if (n != 1) {
            if (n == 2) {
                Matrix matrix3 = matrix2;
                matrix3.setScale(1.0f, -1.0f);
                matrix3.postTranslate(0.0f, (float)var2_4);
            }
        } else {
            Matrix matrix4 = matrix2;
            matrix4.setScale(-1.0f, 1.0f);
            matrix4.postTranslate((float)var2_4, 0.0f);
        }
        for (TouchPoint touchPoint : ((TouchPointList)this).a) {
            float[] fArray = new float[]{touchPoint.x, touchPoint.y};
            matrix2.mapPoints(fArray);
            touchPoint.x = fArray[0];
            ((TouchPoint)this.next()).y = fArray[1];
        }
    }

    @NonNull
    public TouchPointList clone() {
        TouchPointList touchPointList;
        TouchPointList touchPointList2 = touchPointList;
        touchPointList = new TouchPointList();
        if (((TouchPointList)this).a.isEmpty()) {
            return touchPointList2;
        }
        for (TouchPoint touchPoint : ((TouchPointList)this).a) {
            if (touchPoint == null) continue;
            touchPointList2.add(touchPoint.clone());
        }
        return touchPointList2;
    }

    public boolean isEmpty() {
        return CollectionUtils.isNullOrEmpty(this.a);
    }

    public void clear() {
        ArrayList arrayList;
        TouchPointList touchPointList = this_;
        Cloneable this_ = arrayList;
        arrayList = new ArrayList();
        touchPointList.a = this_;
    }
}

