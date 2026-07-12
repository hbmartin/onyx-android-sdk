package com.onyx.android.sdk.data;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ReaderPointMatrix {
    private int rows;
    private int b;
    private ArrayList<ArrayList<PointF>> c;
    private ArrayList<ArrayList<ReaderNavigation>> d;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[NavigationMode.values().length];
            a = iArr;
            try {
                iArr[NavigationMode.ROWS_LEFT_TO_RIGHT_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[NavigationMode.ROWS_RIGHT_TO_LEFT_MODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[NavigationMode.COLUMNS_LEFT_TO_RIGHT_MODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[NavigationMode.COLUMNS_RIGHT_TO_LEFT_MODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public ReaderPointMatrix() {
        resize(1, 1);
    }

    public void setData(ArrayList<ArrayList<PointF>> d) {
        this.c = d;
    }

    public PointF get(int row, int col) {
        if (row < 0 || row >= this.c.size()) {
            return null;
        }
        ArrayList<PointF> arrayList = this.c.get(row);
        if (col < 0 || col >= arrayList.size()) {
            return null;
        }
        return arrayList.get(col);
    }

    public float safeGetX(int row, int col) {
        PointF pointF = get(row, col);
        return pointF != null ? pointF.x : ReaderTextStyle.FONT_EMBOLDEN_NORMAL;
    }

    public float safeGetY(int row, int col) {
        PointF pointF = get(row, col);
        return pointF != null ? pointF.y : ReaderTextStyle.FONT_EMBOLDEN_NORMAL;
    }

    public boolean set(int row, int col, float x, float y) {
        return set(row, col, new PointF(x, y));
    }

    public int rows() {
        return this.c.size();
    }

    public int cols() {
        if (rows() <= 0) {
            return 0;
        }
        return this.c.get(0).size();
    }

    public void setReaderNavigationsData(ArrayList<ArrayList<ReaderNavigation>> readerNavigationsData) {
        this.d = readerNavigationsData;
    }

    public ArrayList<ArrayList<ReaderNavigation>> getReaderNavigationsData() {
        return this.d;
    }

    public void resize(int newRows, int newCols) {
        this.b = newCols;
        this.rows = newRows;
        if (newRows > 1) {
            newRows--;
        }
        if (newCols > 1) {
            newCols--;
        }
        this.c = new ArrayList<>(newRows);
        for (int i = 0; i < newRows; i++) {
            ArrayList<PointF> arrayList = new ArrayList<>();
            for (int i2 = 0; i2 < newCols; i2++) {
                arrayList.add(new PointF());
            }
            this.c.add(arrayList);
        }
    }

    public ArrayList<ArrayList<PointF>> getData() {
        return this.c;
    }

    public void distribute(int rows, int cols, float left, float top, float right, float bottom) {
        resize(rows, cols);
        for (int i = 0; i < rows(); i++) {
            int i2 = 0;
            while (i2 < cols()) {
                PointF pointF = get(i, i2);
                int i3 = i2 + 1;
                i2 = i3;
                pointF.set(left + (((right - left) * i3) / (cols() + 1)), top + (((bottom - top) * (i + 1)) / (rows() + 1)));
            }
        }
    }

    public void updateOrderHint(ArrayList<ArrayList<ReaderNavigation>> readerNavigationsData, NavigationMode navigationMode) {
        int i = 1;
        int size = readerNavigationsData.size();
        switch (a.a[navigationMode.ordinal()]) {
            case 1:
                Iterator<ArrayList<ReaderNavigation>> it = readerNavigationsData.iterator();
                while (it.hasNext()) {
                    Iterator<ReaderNavigation> it2 = it.next().iterator();
                    while (it2.hasNext()) {
                        it2.next().setOrderHint(String.valueOf(i));
                        i++;
                    }
                }
                break;
            case 2:
                for (int i2 = 0; i2 < size; i2++) {
                    ArrayList<ReaderNavigation> arrayList = readerNavigationsData.get(i2);
                    for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                        arrayList.get(size2).setOrderHint(String.valueOf(i));
                        i++;
                    }
                }
                break;
            case 3:
                int i3 = 0;
                while (i3 < size) {
                    ArrayList<ReaderNavigation> arrayList2 = readerNavigationsData.get(i3);
                    int size3 = arrayList2.size();
                    i3++;
                    int i4 = i3;
                    for (int i5 = 0; i5 < size3; i5++) {
                        arrayList2.get(i5).setOrderHint(String.valueOf(i4));
                        i4 += size;
                    }
                }
                break;
            case 4:
                int i6 = 0;
                while (i6 < size) {
                    ArrayList<ReaderNavigation> arrayList3 = readerNavigationsData.get(i6);
                    i6++;
                    int i7 = i6;
                    for (int size4 = arrayList3.size() - 1; size4 >= 0; size4--) {
                        arrayList3.get(size4).setOrderHint(String.valueOf(i7));
                        i7 += size;
                    }
                }
                break;
        }
    }

    public boolean hitTest(float x, float y, float hysteresis, AtomicInteger hitRow, AtomicInteger hitCol, AtomicReference<PointF> point) {
        for (int i = 0; i < this.c.size(); i++) {
            ArrayList<PointF> arrayList = this.c.get(i);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                PointF pointF = arrayList.get(i2);
                float f = hysteresis * 2.0f;
                if (Math.abs(pointF.x - x) < f && Math.abs(pointF.y - y) < f) {
                    hitRow.set(i);
                    hitCol.set(i2);
                    point.set(pointF);
                    return true;
                }
            }
        }
        return false;
    }

    public void offset(float dx, float dy) {
        for (int i = 0; i < this.c.size(); i++) {
            ArrayList<PointF> arrayList = this.c.get(i);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                arrayList.get(i2).offset(dx, dy);
            }
        }
    }

    public int getOriginRows() {
        return this.rows;
    }

    public int getOriginCols() {
        return this.b;
    }

    public void setOriginRows(int originRows) {
        this.rows = originRows;
    }

    public void setOriginCols(int originCols) {
        this.b = originCols;
    }

    public boolean set(int row, int col, PointF point) {
        if (row < 0 || row >= this.c.size()) {
            return false;
        }
        ArrayList<PointF> arrayList = this.c.get(row);
        if (col < 0 || col >= arrayList.size()) {
            return false;
        }
        arrayList.set(col, point);
        return true;
    }

    public ReaderPointMatrix(int r, int c) {
        resize(r, c);
    }

    public void distribute(int rows, int cols, NavigationMode navigationMode, float left, float top, float right, float bottom) {
        resize(rows, cols);
        for (int i = 0; i < rows(); i++) {
            int i2 = 0;
            while (i2 < cols()) {
                PointF pointF = get(i, i2);
                int i3 = i2 + 1;
                i2 = i3;
                pointF.set(left + (((right - left) * i3) / (cols() + 1)), top + (((bottom - top) * (i + 1)) / (rows() + 1)));
            }
        }
        this.d = new ArrayList<>();
        float f = (right - left) / this.b;
        float f2 = (bottom - top) / this.rows;
        for (int i4 = 0; i4 < this.rows; i4++) {
            ArrayList<ReaderNavigation> arrayList = new ArrayList<>();
            int i5 = 0;
            while (i5 < this.b) {
                float f3 = i5 * f;
                float f4 = left + (f / 2.0f) + f3;
                float f5 = i4 * f2;
                ReaderNavigation readerNavigation = new ReaderNavigation();
                readerNavigation.setCenterPoint(f4, top + (f2 / 2.0f) + f5);
                int i6 = i5 + 1;
                i5 = i6;
                readerNavigation.setContentRect(left + f3, top + f5, left + (i6 * f), top + ((i4 + 1) * f2));
                arrayList.add(readerNavigation);
            }
            this.d.add(arrayList);
        }
        updateOrderHint(this.d, navigationMode);
    }
}
