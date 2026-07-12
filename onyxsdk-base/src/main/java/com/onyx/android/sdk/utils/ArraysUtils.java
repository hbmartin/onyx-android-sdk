package com.onyx.android.sdk.utils;

import android.graphics.Point;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ArraysUtils.class */
public class ArraysUtils {
    public static int[] toIntArray(Integer[] inArray) {
        int length = inArray.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = inArray[i].intValue();
        }
        return iArr;
    }

    @Nullable
    public static Point findFreeSpace(boolean[][] booleanArrays) {
        for (int i = 0; i < booleanArrays.length; i++) {
            for (int i2 = 0; i2 < booleanArrays[0].length; i2++) {
                if (!booleanArrays[i][i2]) {
                    return new Point(i, i2);
                }
            }
        }
        return null;
    }

    public static boolean isTargetFreeSpace(boolean[][] booleanArrays, Point start, int spanX, int spanY) {
        int i = start.y;
        if (i + spanY > booleanArrays[0].length) {
            return false;
        }
        int i2 = start.x;
        return (i2 + spanX > booleanArrays.length || booleanArrays[i2][i] || checkOccupied(booleanArrays, start, spanX, spanY)) ? false : true;
    }

    @Nullable
    public static Point checkAvailableFreeSpace(boolean[][] occupied, int col, int row) {
        for (int i = 0; i < occupied.length; i++) {
            for (int i2 = 0; i2 < occupied[0].length; i2++) {
                if (!occupied[i][i2] && !checkOccupied(occupied, new Point(i, i2), col, row)) {
                    setOccupied(occupied, true, i, i2, col, row);
                    return new Point(i, i2);
                }
            }
        }
        return null;
    }

    public static boolean checkOccupied(boolean[][] booleanArrays, Point start, int col, int row) {
        int i = start.x;
        if (i + col > booleanArrays.length || start.y + row > booleanArrays[0].length) {
            return true;
        }
        for (int i2 = i; i2 < start.x + col; i2++) {
            for (int i3 = start.y; i3 < start.y + row; i3++) {
                if (booleanArrays[i2][i3]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setOccupied(boolean[][] booleanArrays, boolean b, int x, int y, int col, int row) {
        for (int i = x; i < x + col; i++) {
            for (int i2 = y; i2 < y + row; i2++) {
                booleanArrays[i][i2] = b;
            }
        }
    }

    public static boolean[][] clone(boolean[][] array) {
        boolean[][] zArr = new boolean[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, zArr[i], 0, array[0].length);
        }
        return zArr;
    }

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isOutOfValue(long[] array, long value) {
        return isNullOrEmpty(array) || value < array[0] || value > array[array.length - 1];
    }

    public static <T> boolean isOutOfRange(T[] array, int index) {
        return isNullOrEmpty(array) || index < 0 || index >= array.length;
    }

    public static <T> int getSize(T[] array) {
        if (isNullOrEmpty(array)) {
            return 0;
        }
        return array.length;
    }

    public static boolean startsWith(byte[] data, int from, byte[] prefix) {
        if (isNullOrEmpty(prefix) || isOutOfRange(data, (from + prefix.length) - 1)) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (prefix[i] != data[from + i]) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static <T> T getLast(T[] array) {
        if (isNullOrEmpty(array)) {
            return null;
        }
        return array[array.length - 1];
    }

    public static <T> void foreach(T[] array, Consumer<T> consumer) {
        if (isNullOrEmpty(array)) {
            return;
        }
        for (T t : array) {
            RxUtils.acceptItemSafety(consumer, t);
        }
    }

    public static <T> boolean equalsAny(T[] array, T item) {
        if (isNullOrEmpty(array)) {
            return false;
        }
        for (T t : array) {
            if (t.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    @Nullable
    public static Point findFreeSpace(boolean[][] booleanArrays, int spanX, int spanY) {
        Point point = new Point(0, 0);
        for (int i = 0; i < booleanArrays[0].length; i++) {
            for (int i2 = 0; i2 < booleanArrays.length; i2++) {
                if (!booleanArrays[i2][i]) {
                    point.set(i2, i);
                    if (!checkOccupied(booleanArrays, point, spanX, spanY)) {
                        return point;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isNullOrEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isOutOfRange(byte[] array, int index) {
        return isNullOrEmpty(array) || index < 0 || index >= array.length;
    }

    public static boolean isNullOrEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isOutOfRange(int[] array, int index) {
        return isNullOrEmpty(array) || index < 0 || index >= array.length;
    }

    @Nullable
    public static Point findFreeSpace(boolean[][] booleanArrays, Point start, int spanX, int spanY, Point endPoint) {
        int i = start.x;
        int iMin = Math.min(booleanArrays.length, endPoint.x);
        int iMin2 = Math.min(booleanArrays[0].length, endPoint.y);
        for (int i2 = start.y; i2 < iMin2; i2++) {
            while (i < iMin) {
                if (!booleanArrays[i][i2] && !checkOccupied(booleanArrays, new Point(i, i2), spanX, spanY)) {
                    return new Point(i, i2);
                }
                i++;
            }
            i = 0;
        }
        return null;
    }

    @Nullable
    public static Point findFreeSpace(boolean[][] booleanArrays, Point start, int spanX, int spanY) {
        return findFreeSpace(booleanArrays, start, spanX, spanY, new Point(booleanArrays.length, booleanArrays[0].length));
    }
}
