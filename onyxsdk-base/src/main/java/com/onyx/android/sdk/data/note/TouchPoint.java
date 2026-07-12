package com.onyx.android.sdk.data.note;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.MotionEvent;
import androidx.annotation.FloatRange;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.point.TinyPoint;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.LineUtils;
import com.onyx.android.sdk.utils.TiltUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/TouchPoint.class */
public class TouchPoint extends com.onyx.android.sdk.base.data.TouchPoint {
    public static final int OBJECT_BYTE_COUNT = 32;

    public TouchPoint() {
    }

    public static TouchPoint create(MotionEvent motionEvent) {
        return new TouchPoint(motionEvent);
    }

    public static TouchPoint fromHistorical(MotionEvent motionEvent, int i) {
        return new TouchPoint(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), motionEvent.getHistoricalPressure(i), motionEvent.getHistoricalSize(i), motionEvent.getHistoricalEventTime(i));
    }

    public static List<TouchPoint> renderPointArray(Matrix matrix, List<TouchPoint> touchPoints) {
        ArrayList arrayList = new ArrayList();
        Iterator<TouchPoint> it = touchPoints.iterator();
        while (it.hasNext()) {
            TouchPoint touchPointCopy = it.next().copy();
            touchPointCopy.applyMatrix(matrix);
            arrayList.add(touchPointCopy);
        }
        return arrayList;
    }

    public static float getPointAngle(TouchPoint start, TouchPoint end) {
        return (float) ((Math.atan2(((com.onyx.android.sdk.base.data.TouchPoint) end).x - ((com.onyx.android.sdk.base.data.TouchPoint) start).x, ((com.onyx.android.sdk.base.data.TouchPoint) start).y - ((com.onyx.android.sdk.base.data.TouchPoint) end).y) * 180.0d) / 3.141592653589793d);
    }

    @FloatRange(from = 0.0d, to = 90.0d)
    public static float getHorizontalAngle(TouchPoint start, TouchPoint end) {
        return LineUtils.getHorizontalAngle(((com.onyx.android.sdk.base.data.TouchPoint) start).x, ((com.onyx.android.sdk.base.data.TouchPoint) start).y, ((com.onyx.android.sdk.base.data.TouchPoint) end).x, ((com.onyx.android.sdk.base.data.TouchPoint) end).y);
    }

    public static float getPointDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2.0d) + Math.pow(y1 - y2, 2.0d));
    }

    public static float[] getTransformRectPoints(RectF originRect, Matrix matrix) {
        float[] fArr = new float[8];
        float[] fArr2 = new float[8];
        float[] fArr3 = new float[8];
        float f = originRect.left;
        float f2 = originRect.top;
        float f3 = originRect.right;
        float f4 = originRect.bottom;
        float[] fArr4 = {f, f2, f3, f2, f3, f4, f, f4};
        if (matrix == null) {
            return fArr4;
        }
        matrix.mapPoints(fArr, fArr4);
        matrix.mapRect(originRect);
        Matrix matrix2 = new Matrix();
        matrix2.postRotate(90.0f, fArr[0], fArr[1]);
        matrix2.mapPoints(fArr2, fArr);
        TouchPoint intersection = getIntersection(new TouchPoint(fArr[0], fArr[1]), new TouchPoint(fArr2[2], fArr2[3]), new TouchPoint(fArr[4], fArr[5]), new TouchPoint(fArr[6], fArr[7]));
        Matrix matrix3 = new Matrix();
        float[] fArr5 = new float[8];
        matrix3.postRotate(90.0f, fArr[4], fArr[5]);
        matrix3.mapPoints(fArr5, fArr);
        TouchPoint intersection2 = getIntersection(new TouchPoint(fArr[4], fArr[5]), new TouchPoint(fArr5[6], fArr5[7]), new TouchPoint(fArr[0], fArr[1]), new TouchPoint(fArr[2], fArr[3]));
        if (intersection != null && !originRect.contains(((com.onyx.android.sdk.base.data.TouchPoint) intersection).x, ((com.onyx.android.sdk.base.data.TouchPoint) intersection).y) && intersection2 != null && !originRect.contains(((com.onyx.android.sdk.base.data.TouchPoint) intersection2).x, ((com.onyx.android.sdk.base.data.TouchPoint) intersection2).y)) {
            fArr3[0] = fArr[0];
            fArr3[1] = fArr[1];
            fArr3[2] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection2).x;
            fArr3[3] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection2).y;
            fArr3[4] = fArr[4];
            fArr3[5] = fArr[5];
            fArr3[6] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection).x;
            fArr3[7] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection).y;
            return fArr3;
        }
        Matrix matrix4 = new Matrix();
        float[] fArr6 = new float[8];
        matrix4.postRotate(90.0f, fArr[2], fArr[3]);
        matrix4.mapPoints(fArr6, fArr);
        TouchPoint intersection3 = getIntersection(new TouchPoint(fArr6[4], fArr6[5]), new TouchPoint(fArr[2], fArr[3]), new TouchPoint(fArr[6], fArr[7]), new TouchPoint(fArr[0], fArr[1]));
        Matrix matrix5 = new Matrix();
        float[] fArr7 = new float[8];
        matrix5.postRotate(90.0f, fArr[6], fArr[7]);
        matrix5.mapPoints(fArr7, fArr);
        TouchPoint intersection4 = getIntersection(new TouchPoint(fArr[6], fArr[7]), new TouchPoint(fArr7[0], fArr7[1]), new TouchPoint(fArr[2], fArr[3]), new TouchPoint(fArr[4], fArr[5]));
        fArr3[0] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection3).x;
        fArr3[1] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection3).y;
        fArr3[2] = fArr[2];
        fArr3[3] = fArr[3];
        fArr3[4] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection4).x;
        fArr3[5] = ((com.onyx.android.sdk.base.data.TouchPoint) intersection4).y;
        fArr3[6] = fArr[6];
        fArr3[7] = fArr[7];
        return fArr3;
    }

    public static TouchPoint getIntersection(TouchPoint a, TouchPoint b, TouchPoint c, TouchPoint d) {
        TouchPoint touchPoint = new TouchPoint(a);
        if (Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) b).y - ((com.onyx.android.sdk.base.data.TouchPoint) a).y) + Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) b).x - ((com.onyx.android.sdk.base.data.TouchPoint) a).x) + Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) d).y - ((com.onyx.android.sdk.base.data.TouchPoint) c).y) + Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) d).x - ((com.onyx.android.sdk.base.data.TouchPoint) c).x) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
            if ((((com.onyx.android.sdk.base.data.TouchPoint) c).x - ((com.onyx.android.sdk.base.data.TouchPoint) a).x) + (((com.onyx.android.sdk.base.data.TouchPoint) c).y - ((com.onyx.android.sdk.base.data.TouchPoint) a).y) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
                Debug.d("ABCD is the same point!");
            } else {
                Debug.d("AB is a point, CD is a point, and AC is different!");
            }
            return touchPoint;
        }
        if (Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) b).y - ((com.onyx.android.sdk.base.data.TouchPoint) a).y) + Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) b).x - ((com.onyx.android.sdk.base.data.TouchPoint) a).x) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
            float f = ((com.onyx.android.sdk.base.data.TouchPoint) a).x;
            float f2 = ((com.onyx.android.sdk.base.data.TouchPoint) d).x;
            float f3 = f - f2;
            float f4 = ((com.onyx.android.sdk.base.data.TouchPoint) c).y;
            float f5 = ((com.onyx.android.sdk.base.data.TouchPoint) d).y;
            if ((f3 * (f4 - f5)) - ((((com.onyx.android.sdk.base.data.TouchPoint) a).y - f5) * (((com.onyx.android.sdk.base.data.TouchPoint) c).x - f2)) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
                Debug.d("A, B is a point, and on the CD line segment!");
            } else {
                Debug.d("A, B is a point, and not on the CD line segment!");
            }
            return touchPoint;
        }
        if (Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) d).y - ((com.onyx.android.sdk.base.data.TouchPoint) c).y) + Math.abs(((com.onyx.android.sdk.base.data.TouchPoint) d).x - ((com.onyx.android.sdk.base.data.TouchPoint) c).x) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
            float f6 = ((com.onyx.android.sdk.base.data.TouchPoint) d).x;
            float f7 = ((com.onyx.android.sdk.base.data.TouchPoint) b).x;
            float f8 = f6 - f7;
            float f9 = ((com.onyx.android.sdk.base.data.TouchPoint) a).y;
            float f10 = ((com.onyx.android.sdk.base.data.TouchPoint) b).y;
            if ((f8 * (f9 - f10)) - ((((com.onyx.android.sdk.base.data.TouchPoint) d).y - f10) * (((com.onyx.android.sdk.base.data.TouchPoint) a).x - f7)) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
                Debug.d("C, D is a point, and on the AB line segment!");
            } else {
                Debug.d("C, D is a point, and not on the AB line segment!");
            }
            return touchPoint;
        }
        float f11 = ((com.onyx.android.sdk.base.data.TouchPoint) b).y;
        float f12 = ((com.onyx.android.sdk.base.data.TouchPoint) a).y;
        float f13 = f11 - f12;
        float f14 = ((com.onyx.android.sdk.base.data.TouchPoint) c).x;
        float f15 = ((com.onyx.android.sdk.base.data.TouchPoint) d).x;
        float f16 = f13 * (f14 - f15);
        float f17 = ((com.onyx.android.sdk.base.data.TouchPoint) b).x;
        float f18 = ((com.onyx.android.sdk.base.data.TouchPoint) a).x;
        float f19 = f17 - f18;
        float f20 = ((com.onyx.android.sdk.base.data.TouchPoint) c).y;
        float f21 = ((com.onyx.android.sdk.base.data.TouchPoint) d).y;
        if (f16 - (f19 * (f20 - f21)) == ReaderTextStyle.FONT_EMBOLDEN_NORMAL) {
            Debug.d("Line segments are parallel, no intersections!");
            return touchPoint;
        }
        ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).x = (((((f17 - f18) * (f14 - f15)) * (f20 - f12)) - ((f14 * (f17 - f18)) * (f20 - f21))) + ((f18 * (f11 - f12)) * (f14 - f15))) / (((f11 - f12) * (f14 - f15)) - ((f17 - f18) * (f20 - f21)));
        float f22 = (f11 - f12) * (f20 - f21);
        float f23 = ((com.onyx.android.sdk.base.data.TouchPoint) c).x;
        float f24 = ((com.onyx.android.sdk.base.data.TouchPoint) a).x;
        float f25 = ((com.onyx.android.sdk.base.data.TouchPoint) d).x;
        float f26 = (f22 * (f23 - f24)) - ((f20 * (f11 - f12)) * (f23 - f25));
        float f27 = ((com.onyx.android.sdk.base.data.TouchPoint) b).x;
        ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).y = (f26 + ((f12 * (f27 - f24)) * (f20 - f21))) / (((f27 - f24) * (f20 - f21)) - ((f11 - f12) * (f23 - f25)));
        return touchPoint;
    }

    public static float[] realPointArray(List<TouchPoint> points) {
        return realPointArray(points, 1.0f);
    }

    public static TouchPoint fromTinyPoint(TinyPoint tinyPoint) {
        TouchPoint touchPoint = new TouchPoint(tinyPoint.getX(), tinyPoint.getY(), tinyPoint.getPressure(), tinyPoint.getSize(), tinyPoint.getTime());
        size2Tilt(tinyPoint, touchPoint);
        return touchPoint;
    }

    public static TinyPoint toTinyPoint(TouchPoint point, long compareTime) {
        TinyPoint tinyPoint = new TinyPoint(((com.onyx.android.sdk.base.data.TouchPoint) point).x, ((com.onyx.android.sdk.base.data.TouchPoint) point).y, (short) ((com.onyx.android.sdk.base.data.TouchPoint) point).pressure, (short) ((com.onyx.android.sdk.base.data.TouchPoint) point).size, (int) (point.getTimestamp() - compareTime));
        tilt2Size(point, tinyPoint);
        return tinyPoint;
    }

    public static void size2Tilt(TinyPoint srcPoint, TouchPoint dstPoint) {
        ((com.onyx.android.sdk.base.data.TouchPoint) dstPoint).tiltX = (byte) (srcPoint.getSize() & 255);
        ((com.onyx.android.sdk.base.data.TouchPoint) dstPoint).tiltY = (byte) ((srcPoint.getSize() >> 8) & 255);
    }

    public static void tilt2Size(TouchPoint srcPoint, TinyPoint dstPoint) {
        dstPoint.setSize((short) ((((com.onyx.android.sdk.base.data.TouchPoint) srcPoint).tiltY << 8) | (((com.onyx.android.sdk.base.data.TouchPoint) srcPoint).tiltX & 255)));
    }

    public static int getTouchPointCoordinatesHashCode(TouchPoint touchPoint, int maxXY, int blockSize) {
        float f = blockSize;
        return (int) ((maxXY * ((int) (((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).x / f))) + (((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).y / f));
    }

    public static int computePointByteSize(List<TouchPoint> touchPointList) {
        if (CollectionUtils.isNullOrEmpty(touchPointList)) {
            return 0;
        }
        return touchPointList.size() * 32;
    }

    public static List<TouchPoint> getRectPoints(RectF rectF) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TouchPoint(rectF.left, rectF.top));
        arrayList.add(new TouchPoint(rectF.right, rectF.top));
        arrayList.add(new TouchPoint(rectF.right, rectF.bottom));
        arrayList.add(new TouchPoint(rectF.left, rectF.bottom));
        return arrayList;
    }

    public static RectF getPointRectF(List<TouchPoint> list) {
        RectF rectF = new RectF();
        if (list.isEmpty()) {
            return rectF;
        }
        TouchPoint touchPoint = list.get(0);
        float f = ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).x;
        float f2 = ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).y;
        rectF.set(f, f2, f, f2);
        for (TouchPoint touchPoint2 : list) {
            rectF.union(((com.onyx.android.sdk.base.data.TouchPoint) touchPoint2).x, ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint2).y);
        }
        return rectF;
    }

    public static List<TouchPoint> copyList(List<TouchPoint> list) {
        ArrayList arrayList = new ArrayList();
        if (CollectionUtils.isNullOrEmpty(list)) {
            return arrayList;
        }
        Iterator<TouchPoint> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().copy());
        }
        return arrayList;
    }

    public void set(TouchPoint point) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = ((com.onyx.android.sdk.base.data.TouchPoint) point).x;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = ((com.onyx.android.sdk.base.data.TouchPoint) point).y;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure = ((com.onyx.android.sdk.base.data.TouchPoint) point).pressure;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).size = ((com.onyx.android.sdk.base.data.TouchPoint) point).size;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltX = ((com.onyx.android.sdk.base.data.TouchPoint) point).tiltX;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltY = ((com.onyx.android.sdk.base.data.TouchPoint) point).tiltY;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp = ((com.onyx.android.sdk.base.data.TouchPoint) point).timestamp;
    }

    public void normalize(com.onyx.android.sdk.data.PageInfo pageInfo) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = (((com.onyx.android.sdk.base.data.TouchPoint) this).x - pageInfo.getDisplayRect().left) / pageInfo.getActualScale();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = (((com.onyx.android.sdk.base.data.TouchPoint) this).y - pageInfo.getDisplayRect().top) / pageInfo.getActualScale();
    }

    public void origin(com.onyx.android.sdk.data.PageInfo pageInfo) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = (((com.onyx.android.sdk.base.data.TouchPoint) this).x * pageInfo.getActualScale()) + pageInfo.getDisplayRect().left;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = (((com.onyx.android.sdk.base.data.TouchPoint) this).y * pageInfo.getActualScale()) + pageInfo.getDisplayRect().top;
    }

    public String toString() {
        return "x:" + ((com.onyx.android.sdk.base.data.TouchPoint) this).x + " y:" + ((com.onyx.android.sdk.base.data.TouchPoint) this).y + "pressure:" + ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure + " size:" + ((com.onyx.android.sdk.base.data.TouchPoint) this).size;
    }

    public boolean equalXY(TouchPoint o) {
        return ((com.onyx.android.sdk.base.data.TouchPoint) this).x == ((com.onyx.android.sdk.base.data.TouchPoint) o).x && ((com.onyx.android.sdk.base.data.TouchPoint) this).y == ((com.onyx.android.sdk.base.data.TouchPoint) o).y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TouchPoint touchPoint = (TouchPoint) o;
        return Float.compare(((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).x, ((com.onyx.android.sdk.base.data.TouchPoint) this).x) == 0 && Float.compare(((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).y, ((com.onyx.android.sdk.base.data.TouchPoint) this).y) == 0 && Float.compare(((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).pressure, ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure) == 0 && Float.compare(((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).size, ((com.onyx.android.sdk.base.data.TouchPoint) this).size) == 0 && ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp == ((com.onyx.android.sdk.base.data.TouchPoint) touchPoint).timestamp;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(((com.onyx.android.sdk.base.data.TouchPoint) this).x), Float.valueOf(((com.onyx.android.sdk.base.data.TouchPoint) this).y), Float.valueOf(((com.onyx.android.sdk.base.data.TouchPoint) this).pressure), Float.valueOf(((com.onyx.android.sdk.base.data.TouchPoint) this).size), Long.valueOf(((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp));
    }

    public TouchPoint(float x, float y) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = x;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = y;
    }

    public static float[] realPointArray(List<TouchPoint> points, float pointScale) {
        if (CollectionUtils.isNullOrEmpty(points)) {
            return new float[0];
        }
        long j = ((com.onyx.android.sdk.base.data.TouchPoint) points.get(0)).timestamp;
        float[] fArr = new float[points.size() * 5];
        for (int i = 0; i < points.size(); i++) {
            int i2 = i;
            int i3 = i2 * 5;
            TouchPoint touchPoint = points.get(i2);
            TiltUtils.updateSizeFromTilt(touchPoint);
            fArr[i3] = touchPoint.getX() * pointScale;
            fArr[i3 + 1] = touchPoint.getY() * pointScale;
            fArr[i3 + 2] = touchPoint.getSize();
            fArr[i3 + 3] = touchPoint.getPressure();
            fArr[i3 + 4] = touchPoint.getTimestamp() - j;
        }
        return fArr;
    }

    public TouchPoint transform(Matrix matrix) {
        TouchPoint touchPoint = new TouchPoint(this);
        touchPoint.applyMatrix(matrix);
        return touchPoint;
    }

    public TouchPoint copy() {
        return new TouchPoint(this);
    }

    public static int[] size2Tilt(int size) {
        return new int[]{(byte) (size & 255), (byte) ((size >> 8) & 255)};
    }

    public TouchPoint clone() {
        return (TouchPoint) super.clone();
    }

    public static int tilt2Size(int tiltX, int tiltY) {
        return (Math.max(0, Math.min(tiltY, 255)) << 8) | (Math.max(0, Math.min(tiltX, 255)) & 255);
    }

    public TouchPoint(float px, float py, float p, float s, long ts) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = px;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = py;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure = p;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).size = s;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp = ts;
    }

    public static float[] renderPointArray(List<TouchPoint> points) {
        return renderPointArray(points, 1.0f);
    }

    public static float[] renderPointArray(List<TouchPoint> points, float pointScale) {
        float[] fArr = new float[points.size() * 3];
        for (int i = 0; i < points.size(); i++) {
            int i2 = i;
            int i3 = i2 * 3;
            TouchPoint touchPoint = points.get(i2);
            fArr[i3] = touchPoint.getX() * pointScale;
            fArr[i3 + 1] = touchPoint.getY() * pointScale;
            fArr[i3 + 2] = touchPoint.getSize();
        }
        return fArr;
    }

    public TouchPoint(float px, float py, float p, float s, int tx, int ty, long ts) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = px;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = py;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure = p;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).size = s;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltX = tx;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltY = ty;
        ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp = ts;
    }

    public TouchPoint(MotionEvent motionEvent) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = motionEvent.getX();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = motionEvent.getY();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure = motionEvent.getPressure();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).size = motionEvent.getSize();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp = motionEvent.getEventTime();
    }

    public TouchPoint(TouchPoint source) {
        ((com.onyx.android.sdk.base.data.TouchPoint) this).x = source.getX();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).y = source.getY();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).pressure = source.getPressure();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).size = source.getSize();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltX = source.getTiltX();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).tiltY = source.getTiltY();
        ((com.onyx.android.sdk.base.data.TouchPoint) this).timestamp = source.getTimestamp();
    }
}
