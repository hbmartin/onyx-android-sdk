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
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/multiview/LimitViewInfo.class */
public class LimitViewInfo {
    private View a;
    private RawInputCallback b;
    private List<Rect> c = new ArrayList();
    private List<Rect> d = new ArrayList();
    private int e;
    private int f;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LimitViewInfo(@NonNull View view, @Nullable RawInputCallback callback) {
        this.a = view;
        this.b = callback;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private void a(List<Rect> limitRectList) {
        Iterator<Rect> it = limitRectList.iterator();
        while (it.hasNext()) {
            this.d.add(a(it.next()));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private Rect b(Rect containerRect) {
        Rect rect = new Rect(containerRect);
        rect.offset(-this.e, -this.f);
        return rect;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public View getView() {
        return this.a;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public RawInputCallback getCallback() {
        return this.b;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LimitViewInfo setCallback(RawInputCallback callback) {
        this.b = callback;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public List<Rect> getLimitRectList() {
        return this.c;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LimitViewInfo setLimitRectList(List<Rect> limitRectList) {
        this.c.clear();
        this.d.clear();
        this.c.addAll(limitRectList);
        a(limitRectList);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public List<Rect> getContainerLimitRectList() {
        return this.d;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LimitViewInfo initLimitRect() {
        ArrayList arrayList = new ArrayList();
        Rect rect = new Rect();
        getView().getLocalVisibleRect(rect);
        arrayList.add(rect);
        setLimitRectList(arrayList);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LimitViewInfo setContainerViewScreenXY(int screenX, int screenY) {
        int[] iArrA = a();
        this.e = iArrA[0] - screenX;
        this.f = iArrA[1] - screenY;
        initLimitRect();
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint offSetXY(TouchPoint point) {
        point.offset(-this.e, -this.f);
        return point;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean contains(Rect rect, TouchPoint point) {
        int i;
        int i2;
        int x = (int) point.getX();
        int y = (int) point.getY();
        int i3 = rect.left;
        int i4 = rect.right;
        return i3 < i4 && (i = rect.top) < (i2 = rect.bottom) && x >= i3 && x <= i4 && y >= i && y <= i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean intersect(Rect r) {
        Iterator<Rect> it = getContainerLimitRectList().iterator();
        while (it.hasNext()) {
            if (RectUtils.intersect(new Rect(it.next()), new Rect(r))) {
                return true;
            }
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public Rect getRefreshRectInView(Rect containerRefreshRect) {
        Rect viewInContainerRect = getViewInContainerRect();
        viewInContainerRect.intersect(containerRefreshRect);
        return b(viewInContainerRect);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public Rect getViewInContainerRect() {
        Rect rect = new Rect();
        getView().getLocalVisibleRect(rect);
        return a(rect);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public TouchPointList offSetXY(TouchPointList touchPointList) {
        if (CollectionUtils.isNullOrEmpty(touchPointList.getPoints())) {
            return touchPointList;
        }
        TouchPointList touchPointList2 = new TouchPointList();
        Iterator<TouchPoint> it = touchPointList.getPoints().iterator();
        while (it.hasNext()) {
            touchPointList2.add(offSetXY(it.next()));
        }
        return touchPointList2;
    }

    private Rect a(Rect rectInView) {
        Rect rect = new Rect(rectInView);
        rect.offset(this.e, this.f);
        return rect;
    }

    public boolean contains(TouchPoint point) {
        Iterator<Rect> it = getContainerLimitRectList().iterator();
        while (it.hasNext()) {
            if (contains(it.next(), point)) {
                return true;
            }
        }
        return false;
    }

    private int[] a() {
        Rect rectGlobalVisibleRect = ViewUtils.globalVisibleRect(getView());
        return new int[]{rectGlobalVisibleRect.left, rectGlobalVisibleRect.top};
    }
}

