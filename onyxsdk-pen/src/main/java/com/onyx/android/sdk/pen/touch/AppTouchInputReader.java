/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.View
 *  androidx.annotation.NonNull
 *  com.onyx.android.sdk.api.device.epd.EpdController
 *  com.onyx.android.sdk.data.PenConstant
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.TouchUtils
 */
package com.onyx.android.sdk.pen.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.pen.touch.AppInputCallback;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.TouchUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppTouchInputReader {
    private static final int k = 0;
    private static final int l = 1;
    private static final int m = 2;
    private static final int n = 3;
    private static final int o = 5;
    private static final int p = 0;
    private static final int q = 1;
    private static final int r = 2;
    private List<Rect> a = new ArrayList<Rect>();
    private List<Rect> b = new ArrayList<Rect>();
    private TouchPointList c;
    private float d = PenConstant.DEFAULT_STROKE_WIDTH;
    private int e = 0;
    private AppInputCallback f;
    private boolean g = true;
    private boolean h = false;
    private boolean i;
    private float j = 1500.0f;

    /*
     * WARNING - void declaration
     */
    public AppTouchInputReader(@NonNull AppInputCallback callback) {
        void var1_1;
        this.f = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    private boolean c(float px, float py) {
        void var2_2;
        void var1_1;
        return this.b((float)var1_1, (float)var2_2) && !this.a((float)var1_1, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    private boolean b(float x, float y) {
        void var2_2;
        void var1_1;
        if (CollectionUtils.isNullOrEmpty(this.a)) {
            return true;
        }
        AppTouchInputReader appTouchInputReader = this;
        return appTouchInputReader.a(appTouchInputReader.a, (float)var1_1, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(float x, float y) {
        void var2_2;
        void var1_1;
        if (CollectionUtils.isNullOrEmpty(this.b)) {
            return false;
        }
        AppTouchInputReader appTouchInputReader = this;
        return appTouchInputReader.a(appTouchInputReader.b, (float)var1_1, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(List<Rect> rectList, float x, float y) {
        float f = this.d / 2.0f;
        for (Rect rect : rectList) {
            void var3_4;
            void var2_3;
            Rect rect2;
            if (!((float)rect2.left <= var2_3 - f) || !(var2_3 + f <= (float)rect.right) || !((float)rect.top <= var3_4 - f) || !(var3_4 + f <= (float)rect.bottom)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private boolean e(MotionEvent event) {
        void var1_1;
        void v0 = var1_1;
        float f = v0.getX();
        if (!this.c(f, v0.getY())) {
            return false;
        }
        if (this.a((MotionEvent)var1_1)) {
            return false;
        }
        this.c((MotionEvent)var1_1);
        return true;
    }

    /*
     * WARNING - void declaration
     */
    private void f(MotionEvent event) {
        void var1_1;
        if (this.a((MotionEvent)var1_1)) {
            return;
        }
        if (this.e == 0) {
            return;
        }
        this.a((MotionEvent)var1_1, 2);
        this.e = 0;
    }

    /*
     * WARNING - void declaration
     */
    private void c(MotionEvent event) {
        void var1_1;
        if (this.a((MotionEvent)var1_1)) {
            return;
        }
        void v0 = var1_1;
        float f = v0.getX();
        int n = this.c(f, v0.getY()) ? 1 : 2;
        int n2 = this.e;
        if (n2 == 0 && n == 1 || n2 == 2 && n == 1) {
            this.a((MotionEvent)var1_1, 0);
        } else if (n2 == 1) {
            if (n == 2) {
                this.a((MotionEvent)var1_1, 3);
            } else {
                this.a((MotionEvent)var1_1, 1);
            }
        }
        this.e = n;
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(MotionEvent event) {
        void var1_1;
        if (this.g) {
            return this.h && TouchUtils.isPenTouchType((MotionEvent)var1_1);
        }
        return TouchUtils.isPenTouchType((MotionEvent)var1_1) ^ true;
    }

    /*
     * WARNING - void declaration
     */
    private void a(MotionEvent event, int state) {
        int n;
        void var1_1;
        if (state == 0) {
            this.g((MotionEvent)var1_1);
        } else if (n == 1) {
            this.b((MotionEvent)var1_1);
        } else if (n != 2 && n != 3) {
            if (n == 5) {
                this.d((MotionEvent)var1_1);
            }
        } else {
            n = n == 3 ? 1 : 0;
            this.a((MotionEvent)var1_1, n != 0);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void d(MotionEvent event) {
        void var1_1;
        AppTouchInputReader appTouchInputReader = this_;
        AppTouchInputReader this_ = TouchPoint.create((MotionEvent)var1_1);
        appTouchInputReader.b((TouchPoint)this_);
        appTouchInputReader.f.onPenActive((TouchPoint)this_);
    }

    /*
     * WARNING - void declaration
     */
    private void a(MotionEvent event, boolean releaseOutLimitRegion) {
        void var2_2;
        void var1_1;
        AppTouchInputReader appTouchInputReader = this;
        void v1 = var1_1;
        boolean bl = TouchUtils.isEraserTouchType((MotionEvent)v1);
        TouchPoint touchPoint = TouchPoint.create((MotionEvent)v1);
        appTouchInputReader.b(touchPoint);
        TouchPointList touchPointList = appTouchInputReader.c;
        if (touchPointList != null && touchPointList.size() > 0) {
            if (bl) {
                this.f.onRawErasingTouchPointListReceived(this.c);
            } else {
                this.f.onRawDrawingTouchPointListReceived((MotionEvent)var1_1, this.c);
            }
        }
        this.a();
        if (bl) {
            this.f.onEndRawErasing((boolean)var2_2, touchPoint);
        } else {
            this.f.onEndRawDrawing((MotionEvent)var1_1, (boolean)var2_2, touchPoint);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void b(MotionEvent event) {
        void var1_1;
        int n = event.getHistorySize();
        for (int i = 0; i < n; ++i) {
            TouchPoint touchPoint;
            AppTouchInputReader appTouchInputReader = appTouchInputReader3;
            TouchPoint touchPoint2 = touchPoint;
            void v2 = var1_1;
            float f = v2.getHistoricalX(i);
            float f2 = v2.getHistoricalY(i);
            float f3 = v2.getHistoricalPressure(i);
            float f4 = v2.getHistoricalSize(i);
            long l = v2.getHistoricalEventTime(i);
            touchPoint = new TouchPoint(f, f2, f3, f4, l);
            appTouchInputReader.b(touchPoint2);
            appTouchInputReader.a(touchPoint2);
            appTouchInputReader3.a(touchPoint2, TouchUtils.isEraserTouchType((MotionEvent)var1_1));
        }
        AppTouchInputReader appTouchInputReader = appTouchInputReader3;
        AppTouchInputReader appTouchInputReader2 = appTouchInputReader3;
        AppTouchInputReader appTouchInputReader3 = TouchPoint.create((MotionEvent)var1_1);
        appTouchInputReader2.b((TouchPoint)appTouchInputReader3);
        appTouchInputReader2.a((TouchPoint)appTouchInputReader3);
        appTouchInputReader.a((TouchPoint)appTouchInputReader3, TouchUtils.isEraserTouchType((MotionEvent)var1_1));
    }

    /*
     * WARNING - void declaration
     */
    private void a(TouchPoint touchPoint, boolean erasing) {
        void var1_1;
        if (erasing) {
            this.f.onRawErasingTouchPointMoveReceived((TouchPoint)var1_1);
        } else {
            this.f.onRawDrawingTouchPointMoveReceived((TouchPoint)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void g(MotionEvent event) {
        void var1_1;
        AppTouchInputReader appTouchInputReader = this;
        this.a();
        TouchPoint touchPoint = TouchPoint.create((MotionEvent)var1_1);
        appTouchInputReader.b(touchPoint);
        if (appTouchInputReader.a(touchPoint)) {
            if (TouchUtils.isEraserTouchType((MotionEvent)var1_1)) {
                this.f.onBeginRawErasing(false, touchPoint);
            } else {
                this.f.onBeginRawDrawing((MotionEvent)var1_1, false, touchPoint);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void b(TouchPoint touchPoint) {
        if (this.i) {
            void var1_1;
            var1_1.setPressure(this.j);
        }
    }

    private void a() {
        this.c = null;
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(TouchPoint touchPoint) {
        void var1_1;
        if (this.c == null) {
            TouchPointList touchPointList;
            TouchPointList touchPointList2 = touchPointList;
            touchPointList = new TouchPointList(600);
            this.c = touchPointList2;
        }
        if (var1_1 != null) {
            this.c.add((TouchPoint)var1_1);
        }
        return true;
    }

    /*
     * WARNING - void declaration
     */
    public AppTouchInputReader setStrokeWidth(float width) {
        void var1_1;
        this.d = var1_1;
        return this;
    }

    public float getStrokeWidth() {
        return this.d;
    }

    /*
     * WARNING - void declaration
     */
    public void setLimitRectList(View hostView, List<Rect> limitRectList) {
        void var2_2;
        CollectionUtils.safeAddAll(this.a, (Collection)var2_2, (boolean)true);
        EpdController.setScreenHandWritingRegionLimit((View)hostView, (Rect[])var2_2.toArray(new Rect[0]));
    }

    /*
     * WARNING - void declaration
     */
    public void setExcludeRectList(View hostView, List<Rect> excludeRectList) {
        void var1_1;
        void var2_2;
        if (excludeRectList == null) {
            return;
        }
        CollectionUtils.safeAddAll(this.b, (Collection)var2_2, (boolean)true);
        EpdController.setScreenHandWritingRegionExclude((View)var1_1, (Rect[])var2_2.toArray(new Rect[0]));
    }

    /*
     * WARNING - void declaration
     */
    public void setEnableFingerTouch(boolean enableFingerTouch) {
        void var1_1;
        this.g = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setOnlyEnableFingerTouch(boolean onlyEnableFingerTouch) {
        void var1_1;
        this.h = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setFingerTouchPressure(float fingerTouchPressure) {
        void var1_1;
        this.j = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setEnableFingerTouchPressure(boolean enableFingerTouchPressure) {
        void var1_1;
        this.i = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public boolean processMotionEvent(MotionEvent event) {
        switch (event.getAction()) {
            default: {
                break;
            }
            case 2: {
                void var1_1;
                this.c((MotionEvent)var1_1);
                break;
            }
            case 1: 
            case 3: 
            case 6: {
                void var1_1;
                this.f((MotionEvent)var1_1);
                break;
            }
            case 0: {
                void var1_1;
                return this.e((MotionEvent)var1_1);
            }
        }
        return true;
    }
}

