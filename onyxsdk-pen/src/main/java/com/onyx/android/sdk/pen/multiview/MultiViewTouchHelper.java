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
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.ViewUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/multiview/MultiViewTouchHelper.class */
public class MultiViewTouchHelper {
    private static boolean g = false;
    private TouchHelper a;
    private LimitViewInfo c;
    private int e;
    private int f;
    private List<LimitViewInfo> b = new ArrayList();
    private BaseViewWatcher d = new ViewWatcher();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/multiview/MultiViewTouchHelper$a.class */
    class a extends RawInputCallback {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        a() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x0025 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v9, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawDrawing(boolean shortcutDrawing, TouchPoint point) {
            LimitViewInfo limitViewInfoA = MultiViewTouchHelper.this.a(point);
            if (MultiViewTouchHelper.this.a(limitViewInfoA)) {
                MultiViewTouchHelper.this.b(limitViewInfoA);
                TouchPoint touchPointOffSetXY = limitViewInfoA.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfoA.getCallback();
                    callback.onBeginRawDrawing(shortcutDrawing, touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
                if (MultiViewTouchHelper.g) {
                    Debug.d(MultiViewTouchHelper.this.getTag(), "onBeginRawDrawing " + limitViewInfoA.toString() + " touchPoint:" + touchPointOffSetXY.toString(), new Object[0]);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x0025 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v9, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
            LimitViewInfo limitViewInfo = MultiViewTouchHelper.this.c;
            MultiViewTouchHelper.this.clearActiveLimitViewInfo(!outLimitRegion);
            if (MultiViewTouchHelper.this.a(limitViewInfo)) {
                TouchPoint touchPointOffSetXY = limitViewInfo.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfo.getCallback();
                    callback.onEndRawDrawing(outLimitRegion, touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
                if (MultiViewTouchHelper.g) {
                    Debug.d(MultiViewTouchHelper.this.getTag(), "onEndRawDrawing " + limitViewInfo.toString() + " touchPoint:" + touchPointOffSetXY.toString(), new Object[0]);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:9:0x001b */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v5, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
            LimitViewInfo limitViewInfo = MultiViewTouchHelper.this.c;
            if (MultiViewTouchHelper.this.a(limitViewInfo)) {
                TouchPoint touchPointOffSetXY = limitViewInfo.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfo.getCallback();
                    callback.onRawDrawingTouchPointMoveReceived(touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:11:0x000b */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        /* JADX WARN: Type inference failed for: r0v2, types: [boolean] */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
            if (!MultiViewTouchHelper.this.c()) {
                return;
            }
            try {
                MultiViewTouchHelper.this.c.getCallback().onRawDrawingTouchPointListReceived(pointList);
            } catch (Exception exception) {
                Debug.e(exception);
            }
            if (MultiViewTouchHelper.g) {
                Debug.d(MultiViewTouchHelper.this.getTag(), "onRawDrawingTouchPointListReceived " + MultiViewTouchHelper.this.c + ", point list size = " + pointList.size(), new Object[0]);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x0025 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v9, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onBeginRawErasing(boolean shortcutErasing, TouchPoint point) {
            LimitViewInfo limitViewInfoA = MultiViewTouchHelper.this.a(point);
            if (MultiViewTouchHelper.this.a(limitViewInfoA)) {
                MultiViewTouchHelper.this.b(limitViewInfoA);
                TouchPoint touchPointOffSetXY = limitViewInfoA.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfoA.getCallback();
                    callback.onBeginRawErasing(shortcutErasing, touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
                if (MultiViewTouchHelper.g) {
                    Debug.d(MultiViewTouchHelper.this.getTag(), "onBeginRawErasing " + limitViewInfoA.toString() + " point:" + touchPointOffSetXY.toString(), new Object[0]);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x0025 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v9, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
            LimitViewInfo limitViewInfo = MultiViewTouchHelper.this.c;
            MultiViewTouchHelper.this.clearActiveLimitViewInfo(!outLimitRegion);
            if (MultiViewTouchHelper.this.a(limitViewInfo)) {
                TouchPoint touchPointOffSetXY = limitViewInfo.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfo.getCallback();
                    callback.onEndRawErasing(outLimitRegion, touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
                if (MultiViewTouchHelper.g) {
                    Debug.d(MultiViewTouchHelper.this.getTag(), "onEndRawErasing " + limitViewInfo.toString() + " point:" + touchPointOffSetXY.toString(), new Object[0]);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:9:0x001b */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v5, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
            LimitViewInfo limitViewInfo = MultiViewTouchHelper.this.c;
            if (MultiViewTouchHelper.this.a(limitViewInfo)) {
                TouchPoint touchPointOffSetXY = limitViewInfo.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfo.getCallback();
                    callback.onRawErasingTouchPointMoveReceived(touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:11:0x000b */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        /* JADX WARN: Type inference failed for: r0v2, types: [boolean] */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
            if (!MultiViewTouchHelper.this.c()) {
                return;
            }
            try {
                MultiViewTouchHelper.this.c.getCallback().onRawErasingTouchPointListReceived(pointList);
            } catch (Exception exception) {
                Debug.e(exception);
            }
            if (MultiViewTouchHelper.g) {
                Debug.d(MultiViewTouchHelper.this.getTag(), "onRawErasingTouchPointListReceived" + MultiViewTouchHelper.this.c + ", point list size = " + pointList.size(), new Object[0]);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:9:0x0021 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [com.onyx.android.sdk.pen.multiview.LimitViewInfo] */
        /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v5, types: [com.onyx.android.sdk.pen.RawInputCallback] */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenActive(TouchPoint point) {
            super.onPenActive(point);
            LimitViewInfo limitViewInfoA = MultiViewTouchHelper.this.a(point);
            if (MultiViewTouchHelper.this.a(limitViewInfoA)) {
                TouchPoint touchPointOffSetXY = limitViewInfoA.offSetXY(point);
                try {
                    RawInputCallback callback = limitViewInfoA.getCallback();
                    callback.onPenActive(touchPointOffSetXY);
                } catch (Exception exception) {
                    Debug.e(exception);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.onyx.android.sdk.pen.RawInputCallback
        public void onPenUpRefresh(RectF refreshRect) {
            super.onPenUpRefresh(refreshRect);
            Rect rect = RectUtils.toRect(refreshRect);
            List listA = MultiViewTouchHelper.this.a(rect);
            if (CollectionUtils.isNullOrEmpty(listA)) {
                Debug.d(MultiViewTouchHelper.this.getTag(), "onPenUpRefresh do nothing, do not find limitViewInfo", new Object[0]);
                return;
            }
            Iterator it = listA.iterator();
            while (it.hasNext()) {
                MultiViewTouchHelper.this.a((LimitViewInfo) it.next(), rect);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public MultiViewTouchHelper(@NonNull View containerView, int feature) {
        a(containerView);
        this.a = TouchHelper.create(containerView, feature, b()).setSingleRegionMode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    private boolean c() {
        return a(this.c);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setDebug(boolean debug) {
        g = debug;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public MultiViewTouchHelper bindContainerView(View containerView) {
        a(containerView);
        this.a.bindHostView(containerView, b());
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public Class getTag() {
        return getClass();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void clearActiveLimitViewInfo(boolean isActionUp) {
        if (isActionUp) {
            this.c = null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void refreshLimitRect() {
        ArrayList arrayList = new ArrayList();
        Iterator<LimitViewInfo> it = this.b.iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getContainerLimitRectList());
        }
        List<Rect> rects = this.d.getRects();
        Debug.i(getTag(), "Limit Rect : " + arrayList.toString(), new Object[0]);
        Debug.i(getTag(), "Exclude Rect : " + rects.toString(), new Object[0]);
        this.a.setLimitRect(arrayList);
        this.a.setExcludeRect(rects);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void add(LimitViewInfo limitViewInfo) {
        if (this.b.contains(limitViewInfo)) {
            return;
        }
        limitViewInfo.setContainerViewScreenXY(this.e, this.f);
        this.b.add(limitViewInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void remove(LimitViewInfo limitViewInfo) {
        this.b.remove(limitViewInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void clear() {
        this.b.clear();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchHelper getTouchHelper() {
        return this.a;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void addExcludeView(List<View> viewList) {
        this.d.add((List) viewList);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void removeExcludeView(View view) {
        this.d.remove(view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public void addExcludeView(View view) {
        this.d.add(view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public void removeExcludeView(List<View> viewList) {
        this.d.remove((List) viewList);
    }

    private RawInputCallback b() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    private MultiViewTouchHelper b(LimitViewInfo activeLimitViewInfo) {
        this.c = activeLimitViewInfo;
        return this;
    }

    private void a(@NonNull View containerView) {
        Rect rectGlobalVisibleRect = ViewUtils.globalVisibleRect(containerView);
        int i = rectGlobalVisibleRect.left;
        this.e = i;
        this.f = rectGlobalVisibleRect.top;
        this.d.setContainerViewScreenX(i).setContainerViewScreenY(this.f);
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:10:0x0009 */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.onyx.android.sdk.pen.RawInputCallback] */
    private void a(LimitViewInfo limitViewInfo, Rect refreshRect) {
        if (!a(limitViewInfo)) {
            return;
        }
        try {
            refreshRect = limitViewInfo.getRefreshRectInView(refreshRect);
            limitViewInfo.getCallback().onPenUpRefresh(RectUtils.toRectF(refreshRect));
        } catch (Exception exception) {
            Debug.e(exception);
        }
        Debug.d(getTag(), "handlePenUpRefresh:" + refreshRect.toString(), new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    private boolean a(LimitViewInfo limitViewInfo) {
        if (g && limitViewInfo == null) {
            Debug.printStackTraceDebug("limitViewInfo is null");
        }
        return (limitViewInfo == null || limitViewInfo.getCallback() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    private LimitViewInfo a(TouchPoint point) {
        for (LimitViewInfo limitViewInfo : this.b) {
            if (limitViewInfo.contains(point)) {
                return limitViewInfo;
            }
        }
        Debug.printStackTraceDebug("getLimitViewInfo is null, the point is " + point.toString());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    private List<LimitViewInfo> a(Rect rect) {
        ArrayList arrayList = new ArrayList();
        for (LimitViewInfo limitViewInfo : this.b) {
            if (limitViewInfo.intersect(rect)) {
                arrayList.add(limitViewInfo);
            }
        }
        return arrayList;
    }
}
