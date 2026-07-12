/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.util.Log
 *  android.view.View
 *  androidx.annotation.Nullable
 *  com.onyx.android.sdk.api.device.epd.EpdController
 *  com.onyx.android.sdk.data.PenConstant
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.rx.SingleThreadScheduler
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.Debug
 *  com.onyx.android.sdk.utils.EventBusHolder
 *  com.onyx.android.sdk.utils.MatrixUtils
 *  com.onyx.android.sdk.utils.RectUtils
 *  com.onyx.android.sdk.utils.RxTimerUtil
 *  com.onyx.android.sdk.utils.RxTimerUtil$TimerObserver
 */
package com.onyx.android.sdk.pen;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.TouchEventBus;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.pen.event.PenActiveEvent;
import com.onyx.android.sdk.pen.event.PenDeactivateEvent;
import com.onyx.android.sdk.pen.event.PenDownPointLostEvent;
import com.onyx.android.sdk.rx.SingleThreadScheduler;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.EventBusHolder;
import com.onyx.android.sdk.utils.MatrixUtils;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.RxTimerUtil;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class RawInputReader {
    private static final String w;
    private static final int x = 0;
    private static final int y = 0;
    private static final int z = 1;
    private static final int A = 2;
    private static final int B = 3;
    private static final int C = 4;
    private static final int D = 5;
    private static final int E = 6;
    private static boolean F;
    private static boolean G;
    private static final String H = "raw_input";
    private static final long I = 60L;
    private static final ExecutorService J;
    private volatile boolean a = false;
    private volatile boolean b = false;
    private volatile boolean c = false;
    private volatile boolean d = false;
    private volatile float[] e = new float[2];
    private volatile float[] f = new float[2];
    private volatile int[] g = new int[2];
    private volatile Matrix h;
    private volatile TouchPointList i;
    private volatile float j = PenConstant.DEFAULT_STROKE_WIDTH;
    private volatile int k = -16777216;
    private RawInputCallback l;
    private volatile WeakReference<View> m;
    private int n = 500;
    private volatile boolean o = true;
    private volatile boolean p;
    private volatile boolean q = true;
    private volatile RectF r;
    private volatile RectF s;
    private RxTimerUtil.TimerObserver t;
    private RxTimerUtil.TimerObserver u;
    private boolean v;

    private native void nativeRawReader();

    private native void nativeRawClose();

    private native boolean nativeIsValid();

    private static native void nativeDebug(boolean var0);

    private native void nativeSetStrokeWidth(float var1);

    private native void nativeSetLimitRegion(float[] var1);

    private native void nativeSetExcludeRegion(float[] var1);

    private native void nativeSetPenState(int var1);

    private native void nativePausePen();

    private native void nativeSetRegionMode(int var1);

    private native void nativeEnableSideBtnErase(boolean var1);

    private void c() {
        this.nativeRawClose();
    }

    public static void debugLog(boolean enable) {
        F = enable;
        RawInputReader.nativeDebug(F);
    }

    private void f() {
        if (this.getHostView() == null) {
            return;
        }
        this.getHostView().post(() -> this.getHostView().getLocationOnScreen(this.g));
    }

    private void m() {
        RawInputReader rawInputReader = this;
        rawInputReader.r = null;
        rawInputReader.s = null;
    }

    private boolean i() {
        return this.v;
    }

    /*
     * WARNING - void declaration
     */
    private void b(boolean reported) {
        void var1_1;
        this.p = var1_1;
    }

    private boolean h() {
        return this.p;
    }

    /*
     * WARNING - void declaration
     */
    private void a(String message) {
        void var1_1;
        if (!F) {
            return;
        }
        Log.d((String)w, (String)var1_1);
    }

    private float[] a(Rect rect) {
        float[] fArray;
        RawInputReader rawInputReader = EpdController.mapToRawTouchPoint((View)rawInputReader.getHostView(), (RectF)RectUtils.toRectF((Rect)fArray));
        if (rect.width() > 0 && fArray.height() > 0 && (rawInputReader == null || rawInputReader.width() <= 0.0f || rawInputReader.height() <= 0.0f)) {
            Log.e((String)w, (String)"Empty region detected when mapping!!!!!");
        }
        float[] fArray2 = new float[4];
        fArray = fArray2;
        fArray[0] = ((RectF)rawInputReader).left;
        fArray[1] = ((RectF)rawInputReader).top;
        fArray[2] = ((RectF)rawInputReader).right;
        fArray2[3] = ((RectF)rawInputReader).bottom;
        return fArray2;
    }

    /*
     * WARNING - void declaration
     */
    private float[] a(List<Rect> rectList) {
        void var1_1;
        float[] fArray = new float[rectList.size() * 4];
        for (int i = 0; i < var1_1.size(); ++i) {
            Rect rect = (Rect)var1_1.get(i);
            RectF rectF = EpdController.mapToRawTouchPoint((View)this.getHostView(), (RectF)RectUtils.toRectF((Rect)rect));
            if (rect.width() > 0 && rect.height() > 0 && (rectF == null || rectF.width() <= 0.0f || rectF.height() <= 0.0f)) {
                Log.e((String)w, (String)"Empty region detected!!!!!");
            }
            int n = i * 4;
            fArray[n] = rectF.left;
            int n2 = n + 1;
            fArray[n2] = rectF.top;
            n2 = n + 2;
            fArray[n2] = rectF.right;
            fArray[n += 3] = rectF.bottom;
        }
        return fArray;
    }

    private ExecutorService e() {
        return J;
    }

    private void p() {
        this.e().submit(new Runnable(this){
            final /* synthetic */ RawInputReader a;
            {
                void var1_1;
                this.a = var1_1;
            }

            @Override
            public void run() {
                try {
                    a a2 = this;
                    Debug.i(a2.getClass(), (String)("submitJob nativeRawReader " + Thread.currentThread().getName() + "-" + Thread.currentThread().getId()), (Object[])new Object[0]);
                    a2.a.nativeRawReader();
                    Debug.i(a2.getClass(), (String)("submitJob finally closeRawInput " + Thread.currentThread().getName() + "-" + Thread.currentThread().getId()), (Object[])new Object[0]);
                    a2.a.c();
                }
                catch (Throwable throwable) {
                    a a3 = this;
                    Debug.i(a3.getClass(), (String)("submitJob finally closeRawInput " + Thread.currentThread().getName() + "-" + Thread.currentThread().getId()), (Object[])new Object[0]);
                    a3.a.c();
                    throw throwable;
                }
                catch (Exception exception) {
                    a a4 = this;
                    Debug.i(a4.getClass(), (String)("submitJob finally closeRawInput " + Thread.currentThread().getName() + "-" + Thread.currentThread().getId()), (Object[])new Object[0]);
                    a4.a.c();
                }
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private void a(float x, float y, int pressure, int tx, int ty, boolean shortcutDrawing, boolean shortcutErasing, long ts) {
        void var7_8;
        void var6_7;
        void var8_9;
        void var5_6;
        void var4_5;
        void var3_4;
        void var2_3;
        void var1_2;
        PenDownPointLostEvent penDownPointLostEvent;
        if (!G) {
            return;
        }
        if (F) {
            Debug.d(penDownPointLostEvent2.getClass(), (String)"down point missing, use first move point as down point.", (Object[])new Object[0]);
        }
        RawInputReader rawInputReader = penDownPointLostEvent2;
        PenDownPointLostEvent penDownPointLostEvent2 = penDownPointLostEvent;
        penDownPointLostEvent = new PenDownPointLostEvent();
        rawInputReader.a(penDownPointLostEvent2);
        boolean bl = rawInputReader.a;
        rawInputReader.a((float)var1_2, (float)var2_3, (int)var3_4, 0, (int)var4_5, (int)var5_6, (long)var8_9, bl, (boolean)var6_7, (boolean)var7_8);
    }

    /*
     * WARNING - void declaration
     */
    private void a(float x, float y, int pressure, int size, int tx, int ty, long ts) {
        void var7_9;
        void var6_8;
        void var5_7;
        void var2_2;
        void var1_1;
        void var4_5;
        void var3_3;
        TouchPoint touchPoint;
        RawInputReader rawInputReader = this_;
        RawInputReader this_ = touchPoint;
        float f = (float)var3_3;
        float f2 = (float)var4_5;
        touchPoint = new TouchPoint((float)var1_1, (float)var2_2, f, f2, (int)var5_7, (int)var6_8, (long)var7_9);
        rawInputReader.d((TouchPoint)this_);
        rawInputReader.e((TouchPoint)this_);
        rawInputReader.c((TouchPoint)this_);
    }

    private boolean g() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    private void a(boolean active) {
        void var1_1;
        this.c = var1_1;
    }

    private void e(TouchPoint touchPoint) {
        RxTimerUtil.TimerObserver timerObserver;
        if (!this_.i()) {
            return;
        }
        if (!this_.g()) {
            PenActiveEvent penActiveEvent;
            RawInputReader rawInputReader = this_;
            rawInputReader.a(true);
            PenActiveEvent penActiveEvent2 = penActiveEvent;
            penActiveEvent = new PenActiveEvent((TouchPoint)timerObserver);
            rawInputReader.a(penActiveEvent2);
        }
        RawInputReader rawInputReader = this_;
        rawInputReader.b();
        Object this_ = TimeUnit.MILLISECONDS;
        timerObserver = rawInputReader.b((TouchPoint)timerObserver);
        RxTimerUtil.timerOnCurScheduler((long)100L, (TimeUnit)((Object)this_), (RxTimerUtil.TimerObserver)timerObserver);
    }

    private void b() {
        RxTimerUtil.cancel((RxTimerUtil.TimerObserver)this.u);
        this.u = null;
    }

    /*
     * WARNING - void declaration
     */
    private RxTimerUtil.TimerObserver b(TouchPoint touchPoint) {
        if (this.u == null) {
            void var1_1;
            RxTimerUtil.TimerObserver timerObserver;
            RxTimerUtil.TimerObserver timerObserver2 = timerObserver;
            timerObserver = new RxTimerUtil.TimerObserver(this, (TouchPoint)var1_1){
                final /* synthetic */ TouchPoint a;
                final /* synthetic */ RawInputReader b;
                {
                    void var1_1;
                    this.b = var1_1;
                    this.a = touchPoint;
                }

                public void a(Long aLong) {
                    if (this.b.g()) {
                        b b2 = this;
                        RawInputReader.a(b2.b, false);
                        RawInputReader.a(b2.b, new PenDeactivateEvent(this.a));
                    }
                }
            };
            this.u = timerObserver2;
        }
        return this.u;
    }

    private void q() {
        this.h = EpdController.getRawTouchPointToScreenMatrix();
    }

    /*
     * WARNING - void declaration
     */
    private TouchPoint d(TouchPoint touchPoint) {
        void var1_1;
        RawInputReader rawInputReader = this;
        rawInputReader.e[0] = var1_1.x;
        rawInputReader.e[1] = var1_1.y;
        if (rawInputReader.h == null) {
            this.q();
        }
        if (this.h != null) {
            RawInputReader rawInputReader2 = this;
            float[] fArray = rawInputReader2.f;
            this.h.mapPoints(fArray, rawInputReader2.e);
        } else {
            RawInputReader rawInputReader3 = this;
            float[] fArray = rawInputReader3.e;
            float[] fArray2 = rawInputReader3.f;
            EpdController.mapToView(null, (float[])fArray, (float[])fArray2);
        }
        RawInputReader rawInputReader4 = this;
        rawInputReader4.f[0] = rawInputReader4.f[0] - (float)this.g[0];
        rawInputReader4.f[1] = rawInputReader4.f[1] - (float)this.g[1];
        var1_1.x = rawInputReader4.f[0];
        var1_1.y = this.f[1];
        return var1_1;
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(TouchPoint touchPoint, boolean create) {
        void var1_1;
        if (this.i == null) {
            TouchPointList touchPointList;
            void var2_2;
            if (var2_2 == false) {
                return false;
            }
            TouchPointList touchPointList2 = touchPointList;
            touchPointList = new TouchPointList(600);
            this.i = touchPointList2;
        }
        if (var1_1 != null && this.i != null) {
            this.i.add((TouchPoint)var1_1);
        }
        return true;
    }

    private boolean j() {
        return this.d;
    }

    /*
     * WARNING - void declaration
     */
    private void a(Object event) {
        void var1_1;
        if (!this.i()) {
            return;
        }
        this.getEventBusHolder().post((Object)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    private void a(float x, float y, int pressure, int size, int tx, int ty, long ts, boolean erasing, boolean shortcutDrawing, boolean shortcutErasing) {
        void var7_8;
        void var6_7;
        void var5_6;
        void var2_2;
        void var4_5;
        void var3_3;
        float f;
        TouchPoint touchPoint;
        RawInputReader rawInputReader = this;
        rawInputReader.n();
        TouchPoint touchPoint2 = touchPoint;
        void v2 = f;
        f = (float)var3_3;
        float f2 = (float)var4_5;
        rawInputReader.d(new TouchPoint((float)v2, (float)var2_2, f, f2, (int)var5_6, (int)var6_7, (long)var7_8));
        rawInputReader.a("pressReceived :" + touchPoint2);
        if (!erasing) {
            RawInputReader rawInputReader2 = this;
            rawInputReader2.o();
            rawInputReader2.f(touchPoint2);
        }
        if (this.a(touchPoint2, true)) {
            void var11_11;
            void var10_10;
            void var9_9;
            this.a(touchPoint2, (boolean)var9_9, (boolean)var10_10, (boolean)var11_11);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void a(float x, float y, int pressure, int size, int tx, int ty, long ts, boolean erasing) {
        void var9_9;
        void var7_8;
        void var6_7;
        void var5_6;
        void var2_2;
        void var4_5;
        void var3_3;
        float f;
        TouchPoint touchPoint;
        RawInputReader rawInputReader = this;
        TouchPoint touchPoint2 = touchPoint;
        void v2 = f;
        f = (float)var3_3;
        float f2 = (float)var4_5;
        rawInputReader.d(new TouchPoint((float)v2, (float)var2_2, f, f2, (int)var5_6, (int)var6_7, (long)var7_8));
        if (rawInputReader.a(touchPoint2)) {
            return;
        }
        this.a(touchPoint2, true);
        if (var9_9 == false) {
            RawInputReader rawInputReader2 = this;
            rawInputReader2.o();
            rawInputReader2.f(touchPoint2);
        }
        this.b(touchPoint2, (boolean)var9_9);
    }

    /*
     * WARNING - void declaration
     */
    private boolean a(TouchPoint curMovePoint) {
        if (this_.isFilterRepeatMovePoint() && this_.i != null) {
            RawInputReader this_ = (TouchPoint)CollectionUtils.getLast(this_.i.getPoints());
            if (this_ != null) {
                void var1_2;
                long l = var1_2.timestamp - ((TouchPoint)this_).timestamp;
                if (l <= 0L) {
                    return false;
                }
                float f = Math.abs(var1_2.pressure - ((TouchPoint)this_).pressure);
                return (Math.abs(var1_2.x - ((TouchPoint)this_).x) + Math.abs(var1_2.y - ((TouchPoint)this_).y)) / (float)l < 0.005f && f <= 2.0f;
            }
            return false;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private void b(float x, float y, int pressure, int size, int tx, int ty, long ts, boolean erasing, boolean releaseOutLimitRegion, boolean forceReport) {
        void var10_11;
        void var11_12;
        void var9_10;
        void var7_9;
        void var6_8;
        void var5_7;
        void var2_2;
        void var1_1;
        void var4_5;
        void var3_3;
        TouchPoint touchPoint;
        TouchPoint touchPoint2 = touchPoint;
        float f = (float)var3_3;
        float f2 = (float)var4_5;
        touchPoint = new TouchPoint((float)var1_1, (float)var2_2, f, f2, (int)var5_7, (int)var6_8, (long)var7_9);
        this.d(touchPoint2);
        if (!erasing) {
            this.f(touchPoint2);
        }
        if (this.i != null && this.i.size() > 0) {
            RawInputReader rawInputReader = this;
            rawInputReader.a(rawInputReader.i, (boolean)var9_10, (boolean)var11_12);
        }
        RawInputReader rawInputReader = this;
        rawInputReader.n();
        rawInputReader.b(touchPoint2, (boolean)var9_10, (boolean)var10_11, (boolean)var11_12);
        rawInputReader.b(false);
        if (var9_10 == false) {
            this.l();
        }
    }

    /*
     * WARNING - void declaration
     */
    private void f(TouchPoint touchPoint) {
        void var1_2;
        if (this_.r == null) {
            RectF rectF;
            RawInputReader rawInputReader = this_;
            RawInputReader this_ = rectF;
            void v2 = var1_2;
            float f = v2.x;
            float f2 = v2.y;
            rectF = new RectF(f, f2, f, f2);
            rawInputReader.r = this_;
        } else {
            void v3 = var1_2;
            float f = v3.x;
            this_.r.union(f, v3.y);
        }
    }

    private void l() {
        if (!this_.isPenUpRefreshEnabled()) {
            return;
        }
        RawInputReader rawInputReader = this_;
        rawInputReader.o();
        RawInputReader rawInputReader2 = this_;
        Object this_ = TimeUnit.MILLISECONDS;
        RxTimerUtil.timer((long)rawInputReader.n, (TimeUnit)((Object)this_), (RxTimerUtil.TimerObserver)rawInputReader2.d());
    }

    @Nullable
    private RectF a() {
        RectF rectF;
        if (this.r == null) {
            return null;
        }
        RectF rectF2 = rectF;
        rectF2(this.r);
        RectUtils.expand((RectF)rectF, (float)this.j);
        if (this.s != null) {
            rectF2.union(this.s);
        }
        RawInputReader rawInputReader = this;
        rawInputReader.s = new RectF(this.r);
        rawInputReader.r = null;
        return rectF2;
    }

    private void o() {
        RxTimerUtil.cancel((RxTimerUtil.TimerObserver)this.t);
        this.t = null;
    }

    private RxTimerUtil.TimerObserver d() {
        if (this.t == null) {
            RxTimerUtil.TimerObserver timerObserver;
            RxTimerUtil.TimerObserver timerObserver2 = timerObserver;
            timerObserver = new RxTimerUtil.TimerObserver(this){
                final /* synthetic */ RawInputReader a;
                {
                    void var1_1;
                    this.a = var1_1;
                }

                public void a(Long aLong) {
                    RawInputReader rawInputReader = this.a;
                    RawInputReader.a(rawInputReader, rawInputReader.a());
                }
            };
            this.t = timerObserver2;
        }
        return this.t;
    }

    private void n() {
        this.i = null;
    }

    /*
     * WARNING - void declaration
     */
    private void a(TouchPoint point, boolean erasing, boolean shortcutDrawing, boolean shortcutErasing) {
        void var2_2;
        if (this.l != null && (this.j() || var2_2 != false)) {
            void var1_1;
            this.b(true);
            if (var2_2 != false) {
                void var4_4;
                this.l.onBeginRawErasing((boolean)var4_4, (TouchPoint)var1_1);
            } else {
                void var3_3;
                this.l.onBeginRawDrawing((boolean)var3_3, (TouchPoint)var1_1);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void b(TouchPoint touchPoint, boolean erasing, boolean releaseOutLimitRegion, boolean forceReport) {
        void var2_2;
        void var4_4;
        if (this.l != null && (var4_4 != false || this.j() || var2_2 != false) && this.h()) {
            void var1_1;
            void var3_3;
            if (var2_2 != false) {
                this.l.onEndRawErasing((boolean)var3_3, (TouchPoint)var1_1);
            } else {
                this.l.onEndRawDrawing((boolean)var3_3, (TouchPoint)var1_1);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void b(TouchPoint touchPoint, boolean erasing) {
        void var2_2;
        if (this.l != null && (this.j() || var2_2 != false) && this.h()) {
            void var1_1;
            if (var2_2 != false) {
                this.l.onRawErasingTouchPointMoveReceived((TouchPoint)var1_1);
            } else {
                this.l.onRawDrawingTouchPointMoveReceived((TouchPoint)var1_1);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void a(TouchPointList touchPointList, boolean erasing, boolean forceReport) {
        void var2_2;
        void var3_3;
        void var1_1;
        if (this.l != null && var1_1 != null && (var3_3 != false || this.j() || var2_2 != false) && this.h()) {
            if (var2_2 != false) {
                this.l.onRawErasingTouchPointListReceived((TouchPointList)var1_1);
            } else {
                this.l.onRawDrawingTouchPointListReceived((TouchPointList)var1_1);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void c(TouchPoint touchPoint) {
        if (this.l != null && this.j()) {
            void var1_1;
            this.l.onPenActive((TouchPoint)var1_1);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void a(RectF refreshRect) {
        void var1_1;
        if (this.l != null && this.j() && var1_1 != null) {
            RawInputReader rawInputReader = this;
            Debug.d(rawInputReader.getClass(), (String)("invokePenUpRefresh: " + var1_1.toString()), (Object[])new Object[0]);
            rawInputReader.l.onPenUpRefresh((RectF)var1_1);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void a(RawInputReader x0, boolean x1) {
        void var1_1;
        x0.a((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void a(RawInputReader x0, Object x1) {
        void var1_1;
        x0.a(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    static /* synthetic */ void a(RawInputReader x0, RectF x1) {
        void var1_1;
        x0.a((RectF)var1_1);
    }

    static {
        System.loadLibrary("onyx_pen_touch_reader");
        w = RawInputReader.class.getSimpleName();
        G = true;
        J = SingleThreadScheduler.newSingleThreadExecutor((String)H, (long)60L);
    }

    /*
     * WARNING - void declaration
     */
    public void setRawInputCallback(RawInputCallback callback) {
        void var1_1;
        this.l = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setHostView(View view) {
        void var1_1;
        WeakReference<void> weakReference;
        RawInputReader rawInputReader = this_;
        WeakReference<void> this_ = weakReference;
        weakReference = new WeakReference<void>(var1_1);
        rawInputReader.m = this_;
        rawInputReader.f();
    }

    public View getHostView() {
        return this.m == null ? null : (View)this.m.get();
    }

    public void start() {
        RawInputReader rawInputReader = this;
        rawInputReader.f();
        rawInputReader.c();
        rawInputReader.b = false;
        rawInputReader.d = false;
        rawInputReader.p();
        rawInputReader.a("start");
    }

    public void resume() {
        RawInputReader rawInputReader = this;
        rawInputReader.f();
        rawInputReader.nativeSetPenState(4);
        rawInputReader.d = true;
        rawInputReader.a("resume");
    }

    public void pause() {
        RawInputReader rawInputReader = this;
        rawInputReader.nativePausePen();
        rawInputReader.d = false;
        rawInputReader.a("pause");
    }

    public void quit() {
        RawInputReader rawInputReader = this;
        rawInputReader.o();
        rawInputReader.nativeSetPenState(4);
        rawInputReader.l = null;
        rawInputReader.m();
        rawInputReader.c();
        rawInputReader.d = false;
        rawInputReader.j = PenConstant.DEFAULT_STROKE_WIDTH;
        rawInputReader.k = -16777216;
        rawInputReader.b = true;
        rawInputReader.c = false;
        rawInputReader.a("quit");
    }

    public boolean isFdValid() {
        return this.nativeIsValid();
    }

    /*
     * WARNING - void declaration
     */
    public void setStrokeWidth(float w) {
        void var1_1;
        this.j = var1_1;
        this.nativeSetStrokeWidth((float)var1_1);
        EpdController.setStrokeWidth((float)var1_1);
        this.a("setStrokeWidth:" + (float)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setStrokeColor(int color) {
        void var1_1;
        this.k = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        void var1_1;
        this.n = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setPostInputEvent(boolean post) {
        void var1_1;
        this.v = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void setPenUpRefreshEnabled(boolean enable) {
        void var1_1;
        this.o = var1_1;
    }

    public boolean isPenUpRefreshEnabled() {
        return this.o;
    }

    /*
     * WARNING - void declaration
     */
    public void setFilterRepeatMovePoint(boolean filter) {
        void var1_1;
        this.q = var1_1;
    }

    public boolean isFilterRepeatMovePoint() {
        return this.q;
    }

    public void setSingleRegionMode() {
        RawInputReader rawInputReader = this;
        rawInputReader.nativeSetRegionMode(1);
        EpdController.setScreenHandWritingRegionMode((View)rawInputReader.getHostView(), (int)1);
        rawInputReader.a("setSingleRegionMode");
    }

    public void setMultiRegionMode() {
        RawInputReader rawInputReader = this;
        rawInputReader.nativeSetRegionMode(0);
        EpdController.setScreenHandWritingRegionMode((View)rawInputReader.getHostView(), (int)0);
        rawInputReader.a("setMultiRegionMode");
    }

    /*
     * WARNING - void declaration
     */
    public void enableSideBtnErase(boolean enable) {
        void var1_1;
        this.nativeEnableSideBtnErase((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setLimitRect(Rect rect) {
        if (rect != null) {
            void var1_1;
            RawInputReader rawInputReader = this;
            rawInputReader.nativeSetLimitRegion(rawInputReader.a((Rect)var1_1));
            EpdController.setScreenHandWritingRegionLimit((View)rawInputReader.getHostView(), (Rect[])new Rect[]{var1_1});
            rawInputReader.a("setLimitRect");
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setLimitRect(List<Rect> rectList) {
        void var1_1;
        if (rectList != null && var1_1.size() > 0) {
            RawInputReader rawInputReader = this;
            rawInputReader.nativeSetLimitRegion(rawInputReader.a((List<Rect>)var1_1));
            EpdController.setScreenHandWritingRegionLimit((View)rawInputReader.getHostView(), (Rect[])var1_1.toArray(new Rect[0]));
            rawInputReader.a("setLimitRect");
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setExcludeRect(List<Rect> rectList) {
        void var1_1;
        if (rectList != null && var1_1.size() > 0) {
            RawInputReader rawInputReader = this;
            rawInputReader.nativeSetExcludeRegion(rawInputReader.a((List<Rect>)var1_1));
            EpdController.setScreenHandWritingRegionExclude((View)rawInputReader.getHostView(), (Rect[])var1_1.toArray(new Rect[0]));
            rawInputReader.a("setExcludeRect");
        }
    }

    /*
     * WARNING - void declaration
     */
    public void onTouchPointReceived(float x, float y, int pressure, int tx, int ty, boolean isErasing, boolean shortcutDrawing, boolean shortcutErasing, int state, long ts) {
        boolean bl;
        boolean bl2;
        int n;
        void var9_12;
        void var10_13;
        void var5_8;
        void var4_7;
        void var3_6;
        void var2_5;
        void var1_4;
        if (state == 5) {
            this.a((float)var1_4, (float)var2_5, (int)var3_6, 0, (int)var4_7, (int)var5_8, (long)var10_13);
        }
        if (var9_12 != 6 && !this.j()) {
            return;
        }
        if (var9_12 == false) {
            RawInputReader rawInputReader = this;
            rawInputReader.a = n;
            rawInputReader.q();
            boolean bl3 = rawInputReader.a;
            rawInputReader.a((float)var1_4, (float)var2_5, (int)var3_6, 0, (int)var4_7, (int)var5_8, (long)var10_13, bl3, bl2, bl);
        } else if (var9_12 == true) {
            this.a = n;
            if (this.h()) {
                RawInputReader rawInputReader = this;
                boolean bl4 = rawInputReader.a;
                rawInputReader.a((float)var1_4, (float)var2_5, (int)var3_6, 0, (int)var4_7, (int)var5_8, (long)var10_13, bl4);
            } else {
                RawInputReader rawInputReader = this;
                rawInputReader.q();
                rawInputReader.a((float)var1_4, (float)var2_5, (int)var3_6, (int)var4_7, (int)var5_8, bl2, bl, (long)var10_13);
            }
        } else if (var9_12 != 2 && var9_12 != 3) {
            if (var9_12 == 6) {
                Log.d((String)w, (String)"EpdShapeHandler pen force release");
                boolean bl5 = this.a = n;
                this.b((float)var1_4, (float)var2_5, (int)var3_6, 0, (int)var4_7, (int)var5_8, (long)var10_13, bl5, false, true);
                this.a = false;
            }
        } else {
            this.a = n;
            n = 0;
            bl2 = this.a;
            bl = var9_12 == 3;
            this.b((float)var1_4, (float)var2_5, (int)var3_6, n, (int)var4_7, (int)var5_8, (long)var10_13, bl2, bl, false);
            this.a = false;
        }
    }

    public EventBusHolder getEventBusHolder() {
        return TouchEventBus.getInstance().getEventBusHolder();
    }

    public TouchPointList detachTouchPointList() {
        this.n();
        return this.i;
    }

    public boolean isErasing() {
        return this.a;
    }

    public void printTouchInfo() {
        Debug.i(this.getClass(), (String)("erasing: " + this.a + ", active: " + this.c + ", curPenRect: " + this.r + ", oldPenRect: " + this.s + ", downPointReported: " + this.p + ", filterRepeatMovePoint: " + this.q + ", penUpRefreshTimeMs: " + this.n + ", pointMatrix: " + MatrixUtils.matrixString((Matrix)this.h) + ", reportData: " + this.d), (Object[])new Object[0]);
    }
}

