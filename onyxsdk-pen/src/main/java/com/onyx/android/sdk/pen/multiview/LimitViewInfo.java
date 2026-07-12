/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  androidx.annotation.NonNull
 *  androidx.annotation.Nullable
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.RectUtils
 *  com.onyx.android.sdk.utils.ViewUtils
 */
package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.ViewUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LimitViewInfo {
    private View a;
    private RawInputCallback b;
    private List<Rect> c;
    private List<Rect> d;
    private int e;
    private int f;

    /*
     * WARNING - void declaration
     */
    public LimitViewInfo(@NonNull View view, @Nullable RawInputCallback callback) {
        void var2_2;
        void var1_1;
        ArrayList arrayList;
        ArrayList arrayList2;
        Object this_ = arrayList2;
        arrayList2 = new ArrayList();
        v1.c = this_;
        this_ = arrayList;
        arrayList = new ArrayList();
        v1.d = this_;
        v1.a = var1_1;
        v1.b = var2_2;
    }

    private void a(List<Rect> limitRectList) {
        for (Rect rect : limitRectList) {
            this.d.add(this.a(rect));
        }
    }

    /*
     * WARNING - void declaration
     */
    private Rect a(Rect rectInView) {
        void var1_2;
        Rect rect;
        Rect rect2 = rect;
        LimitViewInfo limitViewInfo = this;
        rect2((Rect)var1_2);
        int n = limitViewInfo.e;
        rect.offset(n, limitViewInfo.f);
        return rect;
    }

    /*
     * WARNING - void declaration
     */
    private Rect b(Rect containerRect) {
        void var1_1;
        Rect rect = new Rect((Rect)var1_1);
        rect.offset(-this.e, -this.f);
        return rect;
    }

    private int[] a() {
        int[] nArray = new int[2];
        int[] nArray2 = nArray;
        Rect rect = ViewUtils.globalVisibleRect((View)this.getView());
        nArray2[0] = rect.left;
        nArray[1] = rect.top;
        return nArray;
    }

    public View getView() {
        return this.a;
    }

    public RawInputCallback getCallback() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public LimitViewInfo setCallback(RawInputCallback callback) {
        void var1_1;
        this.b = var1_1;
        return this;
    }

    public List<Rect> getLimitRectList() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    public LimitViewInfo setLimitRectList(List<Rect> limitRectList) {
        void var1_1;
        LimitViewInfo limitViewInfo = this;
        LimitViewInfo limitViewInfo2 = this;
        limitViewInfo2.c.clear();
        limitViewInfo2.d.clear();
        limitViewInfo2.c.addAll((Collection<Rect>)var1_1);
        limitViewInfo.a((List<Rect>)var1_1);
        return limitViewInfo;
    }

    public List<Rect> getContainerLimitRectList() {
        return this.d;
    }

    public LimitViewInfo initLimitRect() {
        Rect rect;
        LimitViewInfo limitViewInfo = this;
        ArrayList<Rect> arrayList = new ArrayList<Rect>();
        Rect rect2 = rect;
        rect2();
        this.getView().getLocalVisibleRect(rect2);
        arrayList.add(rect);
        limitViewInfo.setLimitRectList(arrayList);
        return limitViewInfo;
    }

    /*
     * WARNING - void declaration
     */
    public LimitViewInfo setContainerViewScreenXY(int screenX, int screenY) {
        void var2_2;
        void var1_1;
        LimitViewInfo limitViewInfo = this;
        int[] nArray = limitViewInfo.a();
        this.e = nArray[0] - var1_1;
        limitViewInfo.f = nArray[1] - var2_2;
        limitViewInfo.initLimitRect();
        return limitViewInfo;
    }

    public TouchPoint offSetXY(TouchPoint point) {
        TouchPoint touchPoint = point;
        touchPoint.offset(-this.e, -this.f);
        return touchPoint;
    }

    public TouchPointList offSetXY(TouchPointList touchPointList) {
        TouchPointList touchPointList2;
        TouchPointList touchPointList3;
        if (CollectionUtils.isNullOrEmpty(touchPointList.getPoints())) {
            return touchPointList3;
        }
        void v0 = touchPointList3;
        touchPointList3 = touchPointList2;
        touchPointList2 = new TouchPointList();
        Iterator<TouchPoint> iterator = v0.getPoints().iterator();
        while (iterator.hasNext()) {
            touchPointList3.add(this.offSetXY(iterator.next()));
        }
        return touchPointList3;
    }

    /*
     * WARNING - void declaration
     */
    public boolean contains(Rect rect, TouchPoint point) {
        int n;
        int n2;
        void var1_2;
        void var2_4;
        void v0 = var2_4;
        int n3 = (int)v0.getX();
        int n4 = (int)v0.getY();
        int n5 = rect.left;
        int n6 = var1_2.right;
        return n5 < n6 && (n2 = var1_2.top) < (n = var1_2.bottom) && n3 >= n5 && n3 <= n6 && n4 >= n2 && n4 <= n;
    }

    /*
     * WARNING - void declaration
     */
    public boolean contains(TouchPoint point) {
        Iterator<Rect> iterator = this.getContainerLimitRectList().iterator();
        while (iterator.hasNext()) {
            void var1_1;
            if (!this.contains(iterator.next(), (TouchPoint)var1_1)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public boolean intersect(Rect r) {
        for (Rect rect : ((LimitViewInfo)this).getContainerLimitRectList()) {
            void var1_1;
            if (!RectUtils.intersect((Rect)new Rect(rect), (Rect)new Rect((Rect)var1_1))) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public Rect getRefreshRectInView(Rect containerRefreshRect) {
        void var1_1;
        LimitViewInfo limitViewInfo = this;
        Rect rect = limitViewInfo.getViewInContainerRect();
        rect.intersect((Rect)var1_1);
        return limitViewInfo.b(rect);
    }

    public Rect getViewInContainerRect() {
        Rect rect;
        Rect rect2 = rect;
        rect2();
        this.getView().getLocalVisibleRect(rect2);
        return this.a(rect);
    }
}

