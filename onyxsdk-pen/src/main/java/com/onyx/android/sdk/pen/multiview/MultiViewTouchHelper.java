/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 *  androidx.annotation.NonNull
 *  androidx.annotation.Nullable
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.Debug
 *  com.onyx.android.sdk.utils.RectUtils
 *  com.onyx.android.sdk.utils.ViewUtils
 */
package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.TouchHelper;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.pen.multiview.BaseViewWatcher;
import com.onyx.android.sdk.pen.multiview.LimitViewInfo;
import com.onyx.android.sdk.pen.multiview.ViewWatcher;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.ViewUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MultiViewTouchHelper {
    private static boolean g = false;
    private TouchHelper a;
    private List<LimitViewInfo> b;
    private LimitViewInfo c;
    private BaseViewWatcher d;
    private int e;
    private int f;

    /*
     * WARNING - void declaration
     */
    public MultiViewTouchHelper(@NonNull View containerView, int feature) {
        void var2_2;
        void var1_1;
        MultiViewTouchHelper multiViewTouchHelper = this;
        this.b = new ArrayList<LimitViewInfo>();
        this.d = new ViewWatcher();
        multiViewTouchHelper.a((View)var1_1);
        this.a = TouchHelper.create((View)var1_1, (int)var2_2, multiViewTouchHelper.b()).setSingleRegionMode();
    }

    private void a(@NonNull View containerView) {
        int n;
        Rect rect;
        rect = ViewUtils.globalVisibleRect((View)rect);
        this.e = n = rect.left;
        this.f = rect.top;
        this.d.setContainerViewScreenX(n).setContainerViewScreenY(this.f);
    }

    private RawInputCallback b() {
        return new RawInputCallback(this){
            final /* synthetic */ MultiViewTouchHelper a;
            {
                void var1_1;
                this.a = var1_1;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint point) {
                TouchPoint touchPoint;
                a a2 = this;
                LimitViewInfo limitViewInfo = MultiViewTouchHelper.a(a2.a, touchPoint);
                if (!MultiViewTouchHelper.a(a2.a, limitViewInfo)) {
                    return;
                }
                LimitViewInfo limitViewInfo2 = limitViewInfo;
                MultiViewTouchHelper.b(this.a, limitViewInfo);
                touchPoint = limitViewInfo2.offSetXY(touchPoint);
                try {
                    void var1_1;
                    limitViewInfo2.getCallback().onBeginRawDrawing((boolean)var1_1, touchPoint);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onBeginRawDrawing " + limitViewInfo.toString() + " touchPoint:" + touchPoint.toString()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
                TouchPoint touchPoint;
                void var1_1;
                a a2 = this;
                LimitViewInfo limitViewInfo = a2.a.c;
                a2.a.clearActiveLimitViewInfo((var1_1 ^ 1) != 0);
                if (!MultiViewTouchHelper.a(a2.a, limitViewInfo)) {
                    return;
                }
                LimitViewInfo limitViewInfo2 = limitViewInfo;
                touchPoint = limitViewInfo2.offSetXY(touchPoint);
                try {
                    limitViewInfo2.getCallback().onEndRawDrawing((boolean)var1_1, touchPoint);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onEndRawDrawing " + limitViewInfo.toString() + " touchPoint:" + touchPoint.toString()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
                void var1_1;
                a a2 = this_;
                Object this_ = a2.a.c;
                if (!MultiViewTouchHelper.a(a2.a, (LimitViewInfo)this_)) {
                    return;
                }
                Object object = this_;
                this_ = ((LimitViewInfo)object).offSetXY((TouchPoint)var1_1);
                try {
                    ((LimitViewInfo)object).getCallback().onRawDrawingTouchPointMoveReceived((TouchPoint)this_);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
                void var1_1;
                if (!this.a.c()) {
                    return;
                }
                try {
                    this.a.c.getCallback().onRawDrawingTouchPointListReceived((TouchPointList)var1_1);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onRawDrawingTouchPointListReceived " + this.a.c + ", point list size = " + var1_1.size()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
                TouchPoint touchPoint;
                a a2 = this;
                LimitViewInfo limitViewInfo = MultiViewTouchHelper.a(a2.a, touchPoint);
                if (!MultiViewTouchHelper.a(a2.a, limitViewInfo)) {
                    return;
                }
                LimitViewInfo limitViewInfo2 = limitViewInfo;
                MultiViewTouchHelper.b(this.a, limitViewInfo);
                touchPoint = limitViewInfo2.offSetXY(touchPoint);
                try {
                    void var1_1;
                    limitViewInfo2.getCallback().onBeginRawErasing((boolean)var1_1, touchPoint);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onBeginRawErasing " + limitViewInfo.toString() + " point:" + touchPoint.toString()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
                TouchPoint touchPoint;
                void var1_1;
                a a2 = this;
                LimitViewInfo limitViewInfo = a2.a.c;
                a2.a.clearActiveLimitViewInfo((var1_1 ^ 1) != 0);
                if (!MultiViewTouchHelper.a(a2.a, limitViewInfo)) {
                    return;
                }
                LimitViewInfo limitViewInfo2 = limitViewInfo;
                touchPoint = limitViewInfo2.offSetXY(touchPoint);
                try {
                    limitViewInfo2.getCallback().onEndRawErasing((boolean)var1_1, touchPoint);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onEndRawErasing " + limitViewInfo.toString() + " point:" + touchPoint.toString()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
                void var1_1;
                a a2 = this_;
                Object this_ = a2.a.c;
                if (!MultiViewTouchHelper.a(a2.a, (LimitViewInfo)this_)) {
                    return;
                }
                Object object = this_;
                this_ = ((LimitViewInfo)object).offSetXY((TouchPoint)var1_1);
                try {
                    ((LimitViewInfo)object).getCallback().onRawErasingTouchPointMoveReceived((TouchPoint)this_);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
                void var1_1;
                if (!this.a.c()) {
                    return;
                }
                try {
                    this.a.c.getCallback().onRawErasingTouchPointListReceived((TouchPointList)var1_1);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
                if (g) {
                    Debug.d((Class)this.a.getTag(), (String)("onRawErasingTouchPointListReceived" + this.a.c + ", point list size = " + var1_1.size()), (Object[])new Object[0]);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onPenActive(TouchPoint point) {
                void var1_1;
                a a2 = this_;
                super.onPenActive((TouchPoint)var1_1);
                Object this_ = MultiViewTouchHelper.a(a2.a, (TouchPoint)var1_1);
                if (!MultiViewTouchHelper.a(a2.a, (LimitViewInfo)this_)) {
                    return;
                }
                Object object = this_;
                this_ = ((LimitViewInfo)object).offSetXY((TouchPoint)var1_1);
                try {
                    ((LimitViewInfo)object).getCallback().onPenActive((TouchPoint)this_);
                }
                catch (Exception exception) {
                    Debug.e((Throwable)exception);
                }
            }

            @Override
            public void onPenUpRefresh(RectF refreshRect) {
                Rect rect;
                super.onPenUpRefresh((RectF)rect);
                rect = RectUtils.toRect((RectF)rect);
                Object object = MultiViewTouchHelper.a(this.a, rect);
                if (CollectionUtils.isNullOrEmpty((Collection)object)) {
                    Debug.d((Class)this.a.getTag(), (String)"onPenUpRefresh do nothing, do not find limitViewInfo", (Object[])new Object[0]);
                    return;
                }
                object = object.iterator();
                while (object.hasNext()) {
                    LimitViewInfo limitViewInfo = (LimitViewInfo)object.next();
                    MultiViewTouchHelper.a(this.a, limitViewInfo, rect);
                }
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    private void a(LimitViewInfo limitViewInfo, Rect refreshRect) {
        Rect rect;
        void var1_1;
        if (!this.a((LimitViewInfo)var1_1)) {
            return;
        }
        void v0 = var1_1;
        void v1 = v0;
        rect = v0.getRefreshRectInView(rect);
        try {
            v1.getCallback().onPenUpRefresh(RectUtils.toRectF((Rect)rect));
        }
        catch (Exception exception) {
            Debug.e((Throwable)exception);
        }
        Debug.d((Class)this.getTag(), (String)("handlePenUpRefresh:" + rect.toString()), (Object[])new Object[0]);
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(LimitViewInfo limitViewInfo) {
        void var1_1;
        if (g && var1_1 == null) {
            Debug.printStackTraceDebug((String)"limitViewInfo is null");
        }
        return var1_1 != null && var1_1.getCallback() != null;
    }

    private boolean c() {
        MultiViewTouchHelper multiViewTouchHelper = this;
        return multiViewTouchHelper.a(multiViewTouchHelper.c);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private LimitViewInfo a(TouchPoint point) {
        void var1_1;
        for (LimitViewInfo limitViewInfo : ((MultiViewTouchHelper)this).b) {
            if (!limitViewInfo.contains((TouchPoint)var1_1)) continue;
            return limitViewInfo;
        }
        Debug.printStackTraceDebug((String)("getLimitViewInfo is null, the point is " + var1_1.toString()));
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private List<LimitViewInfo> a(Rect rect) {
        ArrayList arrayList;
        MultiViewTouchHelper multiViewTouchHelper = this_;
        ArrayList this_ = arrayList;
        arrayList = new ArrayList();
        for (LimitViewInfo limitViewInfo : multiViewTouchHelper.b) {
            void var1_1;
            if (!limitViewInfo.intersect((Rect)var1_1)) continue;
            this_.add(limitViewInfo);
        }
        return this_;
    }

    /*
     * WARNING - void declaration
     */
    private MultiViewTouchHelper b(LimitViewInfo activeLimitViewInfo) {
        void var1_1;
        this.c = var1_1;
        return this;
    }

    public static void setDebug(boolean debug) {
        g = debug;
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ LimitViewInfo a(MultiViewTouchHelper x0, TouchPoint x1) {
        void var1_1;
        return x0.a((TouchPoint)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ boolean a(MultiViewTouchHelper x0, LimitViewInfo x1) {
        void var1_1;
        return x0.a((LimitViewInfo)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ MultiViewTouchHelper b(MultiViewTouchHelper x0, LimitViewInfo x1) {
        void var1_1;
        return x0.b((LimitViewInfo)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ List a(MultiViewTouchHelper x0, Rect x1) {
        void var1_1;
        return x0.a((Rect)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void a(MultiViewTouchHelper x0, LimitViewInfo x1, Rect x2) {
        void var2_2;
        void var1_1;
        x0.a((LimitViewInfo)var1_1, (Rect)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public MultiViewTouchHelper bindContainerView(View containerView) {
        void var1_1;
        MultiViewTouchHelper multiViewTouchHelper = this;
        multiViewTouchHelper.a((View)var1_1);
        multiViewTouchHelper.a.bindHostView((View)var1_1, this.b());
        return multiViewTouchHelper;
    }

    public Class getTag() {
        return this.getClass();
    }

    public void clearActiveLimitViewInfo(boolean isActionUp) {
        if (!isActionUp) {
            return;
        }
        this.c = null;
    }

    public void refreshLimitRect() {
        ArrayList<Rect> arrayList;
        ArrayList<Rect> arrayList2 = arrayList;
        arrayList = new ArrayList<Rect>();
        Iterator<LimitViewInfo> iterator = ((MultiViewTouchHelper)((Object)this_)).b.iterator();
        while (iterator.hasNext()) {
            arrayList2.addAll(iterator.next().getContainerLimitRectList());
        }
        MultiViewTouchHelper multiViewTouchHelper = this_;
        List<Rect> this_ = multiViewTouchHelper.d.getRects();
        Debug.i((Class)multiViewTouchHelper.getTag(), (String)("Limit Rect : " + ((Object)arrayList2).toString()), (Object[])new Object[0]);
        Debug.i((Class)multiViewTouchHelper.getTag(), (String)("Exclude Rect : " + this_.toString()), (Object[])new Object[0]);
        multiViewTouchHelper.a.setLimitRect(arrayList2);
        multiViewTouchHelper.a.setExcludeRect(this_);
    }

    /*
     * WARNING - void declaration
     */
    public void add(LimitViewInfo limitViewInfo) {
        void var1_2;
        if (!this.b.contains(var1_2)) {
            MultiViewTouchHelper multiViewTouchHelper = this;
            int n = multiViewTouchHelper.e;
            var1_2.setContainerViewScreenXY(n, multiViewTouchHelper.f);
            this.b.add((LimitViewInfo)var1_2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void remove(LimitViewInfo limitViewInfo) {
        void var1_1;
        this.b.remove(var1_1);
    }

    public void clear() {
        this.b.clear();
    }

    public TouchHelper getTouchHelper() {
        return this.a;
    }

    /*
     * WARNING - void declaration
     */
    public void addExcludeView(List<View> viewList) {
        void var1_1;
        this.d.add(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void addExcludeView(View view) {
        void var1_1;
        this.d.add(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void removeExcludeView(View view) {
        void var1_1;
        this.d.remove(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void removeExcludeView(List<View> viewList) {
        void var1_1;
        this.d.remove(var1_1);
    }
}

