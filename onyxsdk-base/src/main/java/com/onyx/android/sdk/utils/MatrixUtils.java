package com.onyx.android.sdk.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.note.MatrixValues;
import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/MatrixUtils.class */
public class MatrixUtils {
    private static final Matrix a = new Matrix();

    public static boolean isEmptyMatrix(Matrix matrix) {
        return matrix == null || matrix.equals(a);
    }

    public static float[] getMatrixValue(@Nullable Matrix matrix) {
        float[] fArr = new float[9];
        if (matrix == null) {
            matrix = a;
        }
        matrix.getValues(fArr);
        return fArr;
    }

    public static Matrix getMatrixByValues(float[] values) {
        Matrix matrix = new Matrix();
        if (values != null) {
            matrix.setValues(values);
        }
        return matrix;
    }

    public static MatrixValues toMatrixValues(Matrix matrix) {
        return new MatrixValues(getMatrixValue(matrix));
    }

    public static Matrix invertMatrix(@Nullable Matrix matrix) {
        if (matrix == null) {
            return new Matrix();
        }
        Matrix matrix2 = new Matrix();
        matrix.invert(matrix2);
        return matrix2;
    }

    public static float[] getDefaultMatrixValue() {
        return getMatrixValue(a);
    }

    public static boolean safelyEquals(Matrix m1, Matrix m2) {
        if (m1 == null && m2 == null) {
            return true;
        }
        if (m1 == null || m2 == null) {
            return false;
        }
        return safelyEquals(getMatrixValue(m1), getMatrixValue(m2));
    }

    public static float getMatrixRotateAngel(Matrix matrix) {
        return getMatrixRotateAngel(getMatrixValue(matrix));
    }

    @FloatRange(from = -180.0d, to = 180.0d)
    public static float getMatrixFloatRotate(Matrix matrix) {
        return getMatrixFloatRotate(getMatrixValue(matrix));
    }

    @FloatRange(from = -90.0d, to = 90.0d)
    public static float getHorizontalAngle(Matrix matrix) {
        return getHorizontalAngle(getMatrixValue(matrix));
    }

    public static Rect mapRect(Matrix matrix, Rect rect) {
        if (matrix == null || rect == null) {
            return rect;
        }
        RectF rectF = RectUtils.toRectF(rect);
        matrix.mapRect(rectF);
        return RectUtils.toRect(rectF);
    }

    public static float getScaleX(float[] values) {
        float f = values[0];
        float f2 = values[3];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static float getScaleY(float[] values) {
        float f = values[4];
        float f2 = values[1];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static float getMatrixMaxScale(Matrix matrix) {
        if (matrix == null) {
            return 1.0f;
        }
        float[] matrixValue = getMatrixValue(matrix);
        return Math.max(getScaleX(matrixValue), getScaleY(matrixValue));
    }

    public static float getMatrixMinScale(Matrix matrix) {
        if (matrix == null) {
            return 1.0f;
        }
        return getMatrixMinScale(getMatrixValue(matrix));
    }

    public static float getMatrixYScale(float[] values) {
        if (ArraysUtils.isNullOrEmpty(values)) {
            return 1.0f;
        }
        return getScaleY(values);
    }

    public static float getMatrixXScale(float[] values) {
        if (ArraysUtils.isNullOrEmpty(values)) {
            return 1.0f;
        }
        return getScaleX(values);
    }

    public static float getAverageScale(float[] values) {
        if (ArraysUtils.isNullOrEmpty(values)) {
            return 1.0f;
        }
        return (getScaleX(values) + getScaleY(values)) / 2.0f;
    }

    public static String matrixString(@Nullable Matrix matrix) {
        return matrix == null ? "null matrix" : matrix.toString();
    }

    public static float getTranslateX(Matrix matrix) {
        return matrix == null ? ReaderTextStyle.FONT_EMBOLDEN_NORMAL : getMatrixValue(matrix)[2];
    }

    public static float getTranslateY(Matrix matrix) {
        return matrix == null ? ReaderTextStyle.FONT_EMBOLDEN_NORMAL : getMatrixValue(matrix)[5];
    }

    public static boolean isEmptyMatrix(float[] matrixValue) {
        return getMatrixByValues(matrixValue).equals(a);
    }

    public static boolean safelyEquals(float[] m1, float[] m2) {
        if (m1 == null && m2 == null) {
            return true;
        }
        if (m1 == null || m2 == null || m1.length != m2.length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            if (Float.compare(m1[i], m2[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @FloatRange(from = -180.0d, to = 180.0d)
    public static float getMatrixFloatRotate(float[] matrixValue) {
        return (float) (Math.atan2(matrixValue[1], matrixValue[0]) * 57.29577951308232d);
    }

    @FloatRange(from = -90.0d, to = 90.0d)
    public static float getHorizontalAngle(float[] matrixValue) {
        float f = -getMatrixFloatRotate(matrixValue);
        if (Float.compare(f, ReaderTextStyle.FONT_EMBOLDEN_NORMAL) == 0) {
            return ReaderTextStyle.FONT_EMBOLDEN_NORMAL;
        }
        if (90.0f >= f || f > 180.0f) {
            return (-180.0f > f || f >= -90.0f) ? f : f + 180.0f;
        }
        return f - 180.0f;
    }

    public static float getMatrixRotateAngel(float[] matrixValue) {
        return Math.round(Math.atan2(matrixValue[1], matrixValue[0]) * 57.29577951308232d);
    }

    public static float getMatrixMinScale(float[] values) {
        if (ArraysUtils.isNullOrEmpty(values)) {
            return 1.0f;
        }
        return Math.min(getScaleX(values), getScaleY(values));
    }

    public static Matrix getMatrixByValues(MatrixValues matrixValues) {
        float[] fArr;
        Matrix matrix = new Matrix();
        if (matrixValues != null && (fArr = matrixValues.values) != null) {
            matrix.setValues(fArr);
        }
        return matrix;
    }

    public static float[] getMatrixValue(@Nullable MatrixValues matrixValues) {
        if (matrixValues == null) {
            return getMatrixValue(a);
        }
        return matrixValues.values;
    }

    public static float getMatrixRotateAngel(RectF rectF, float[] matrixValue) {
        return getMatrixRotateAngel(rectF, getMatrixByValues(matrixValue));
    }

    public static float getMatrixRotateAngel(RectF rectF, @Nullable Matrix matrix) {
        float f = rectF.top;
        float[] fArr = {rectF.left, f};
        float[] fArr2 = {rectF.right, f};
        if (matrix != null) {
            matrix.mapPoints(fArr);
            matrix.mapPoints(fArr2);
        }
        return -MathUtils.calculateAngle(fArr[0], fArr[1], fArr2[0], fArr2[1]);
    }

    public static float getMatrixValue(Matrix matrix, float value) {
        float[] fArr = {ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, value};
        matrix.mapPoints(fArr);
        return TouchPoint.getPointDistance(fArr[0], fArr[1], fArr[2], fArr[3]);
    }
}
