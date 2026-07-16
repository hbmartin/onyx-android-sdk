package com.onyx.android.sdk.pen;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import androidx.annotation.AnyThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
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
import java.util.concurrent.atomic.AtomicLong;

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
    private static final long I = 60;
    private static final ExecutorService J;
    private static final AtomicLong READER_SESSIONS = new AtomicLong();
    private final Object rawInputListenerLock = new Object();
    private final AtomicLong eventSequences = new AtomicLong();
    private volatile RawInputListenerV2 rawInputListenerV2;
    private volatile RawInputDeviceInfo rawInputDeviceInfo;
    private volatile int rawPressureMaximum = 4096;
    private volatile Matrix h;
    private volatile TouchPointList i;
    private RawInputCallback l;
    private volatile WeakReference<View> m;
    private volatile boolean p;
    private volatile RectF r;
    private volatile RectF s;
    private RxTimerUtil.TimerObserver t;
    private RxTimerUtil.TimerObserver u;
    private boolean v;
    private volatile long readerSession;
    private volatile boolean a = false;
    private volatile boolean b = false;
    private volatile boolean c = false;
    private volatile boolean d = false;
    private volatile float[] e = new float[2];
    private volatile float[] f = new float[2];
    private volatile int[] g = new int[2];
    private volatile float j = PenConstant.DEFAULT_STROKE_WIDTH;
    private volatile int k = -16777216;
    private int n = 500;
    private volatile boolean o = true;
    private volatile boolean q = true;

    class a implements Runnable {
        private final long session;

        a(long session) {
            this.session = session;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (F) {
                    RawInputReader.this.a("reader enter session=" + session + " thread="
                            + Thread.currentThread().getName() + "-"
                            + Thread.currentThread().getId());
                }
                RawInputReader.this.nativeRawReader(session);
            } catch (Exception error) {
                Log.e(w, "reader failed session=" + session, error);
            } catch (Throwable th) {
                Log.e(w, "reader crashed session=" + session, th);
                throw th;
            } finally {
                if (F) {
                    RawInputReader.this.a(
                            "reader exit session=" + session + " fdValid=" + isFdValid());
                }
                RawInputReader.this.c(session);
            }
        }
    }

    class b extends RxTimerUtil.TimerObserver {
        final /* synthetic */ TouchPoint a;

        b(TouchPoint touchPoint) {
            this.a = touchPoint;
        }

        public void onNext(Long aLong) {
            if (RawInputReader.this.g()) {
                RawInputReader.this.a(false);
                RawInputReader.this.a(new PenDeactivateEvent(this.a));
            }
        }
    }

    class c extends RxTimerUtil.TimerObserver {
        c() {
        }

        public void onNext(Long aLong) {
            RawInputReader rawInputReader = RawInputReader.this;
            rawInputReader.a(rawInputReader.a());
        }
    }

    private native void nativeRawReader(long session);

    private native void nativeRawClose(long session);

    private native boolean nativeIsValid();

    private static native void nativeDebug(boolean z2);

    private native void nativeSetStrokeWidth(float f);

    private native void nativeSetLimitRegion(float[] fArr);

    private native void nativeSetExcludeRegion(float[] fArr);

    private native void nativeSetPenState(int i);

    private native void nativePausePen();

    private native void nativeSetRegionMode(int i);

    private native void nativeEnableSideBtnErase(boolean z2);

    public static void debugLog(boolean enable) {
        F = enable;
        nativeDebug(enable);
    }

    private void f() {
        if (getHostView() == null) {
            return;
        }
        getHostView().post(() -> {
            getHostView().getLocationOnScreen(this.g);
        });
    }

    // Recovered solely so touch/g.java compiles: g is the only caller, and the
    // original synthetic bridge that paired them is not recoverable.
    void B() {
        View hostView = getHostView();
        if (hostView != null) {
            hostView.getLocationOnScreen(this.g);
        }
    }

    private void m() {
        this.r = null;
        this.s = null;
    }

    private boolean i() {
        return this.v;
    }

    private boolean h() {
        return this.p;
    }

    private ExecutorService e() {
        return J;
    }

    private void p() {
        long session = READER_SESSIONS.incrementAndGet();
        this.readerSession = session;
        a("queue reader session=" + session);
        e().submit(new a(session));
    }

    private boolean g() {
        return this.c;
    }

    private void q() {
        this.h = EpdController.getRawTouchPointToScreenMatrix();
    }

    private boolean j() {
        return this.d;
    }

    private void l() {
        if (isPenUpRefreshEnabled()) {
            o();
            RxTimerUtil.timer(this.n, TimeUnit.MILLISECONDS, d());
        }
    }

    private void o() {
        RxTimerUtil.cancel(this.t);
        this.t = null;
    }

    private void n() {
        this.i = null;
    }

    static {
        System.loadLibrary("onyx_pen_touch_reader");
        w = RawInputReader.class.getSimpleName();
        G = true;
        J = SingleThreadScheduler.newSingleThreadExecutor(H, I);
    }

    public void setRawInputCallback(RawInputCallback callback) {
        this.l = callback;
    }

    @AnyThread
    public void setRawInputListenerV2(RawInputListenerV2 listener) {
        synchronized (rawInputListenerLock) {
            this.rawInputListenerV2 = listener;
            RawInputDeviceInfo info = this.rawInputDeviceInfo;
            if (listener != null && info != null) {
                notifyRawInputDeviceInfoLocked(listener, info);
            }
        }
    }

    @AnyThread
    public RawInputDeviceInfo getRawInputDeviceInfo() {
        return this.rawInputDeviceInfo;
    }

    public void setHostView(View view) {
        this.m = new WeakReference<>(view);
        f();
    }

    public View getHostView() {
        if (this.m == null) {
            return null;
        }
        return this.m.get();
    }

    @AnyThread
    public void start() {
        if (F) {
            a("start requested previousSession=" + this.readerSession
                    + " fdValid=" + isFdValid());
        }
        f();
        c();
        this.b = false;
        this.d = false;
        p();
        a("start");
    }

    @AnyThread
    public void resume() {
        if (F) {
            a("resume session=" + this.readerSession + " fdValid=" + isFdValid());
        }
        f();
        nativeSetPenState(4);
        this.d = true;
        a("resume");
    }

    // Faithful reference behavior: the native pause flush fires only when the
    // pen state is below 2, and this class only ever sets state 4, so a stroke
    // in progress continues across pause()/resume() without a forced release.
    @AnyThread
    public void pause() {
        if (F) {
            a("pause session=" + this.readerSession + " fdValid=" + isFdValid());
        }
        nativePausePen();
        this.d = false;
        a("pause");
    }

    @AnyThread
    public void quit() {
        if (F) {
            a("quit requested session=" + this.readerSession + " fdValid=" + isFdValid());
        }
        o();
        nativeSetPenState(4);
        this.l = null;
        m();
        c();
        this.d = false;
        this.j = PenConstant.DEFAULT_STROKE_WIDTH;
        this.k = -16777216;
        this.b = true;
        this.c = false;
        a("quit");
        if (F) {
            a("quit completed session=" + this.readerSession + " fdValid=" + isFdValid());
        }
    }

    public boolean isFdValid() {
        return nativeIsValid();
    }

    public void setStrokeWidth(float w2) {
        this.j = w2;
        nativeSetStrokeWidth(w2);
        EpdController.setStrokeWidth(w2);
        a("setStrokeWidth:" + w2);
    }

    public void setStrokeColor(int color) {
        this.k = color;
    }

    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        this.n = penUpRefreshTimeMs;
    }

    public void setPostInputEvent(boolean post) {
        this.v = post;
    }

    public void setPenUpRefreshEnabled(boolean enable) {
        this.o = enable;
    }

    public boolean isPenUpRefreshEnabled() {
        return this.o;
    }

    public void setFilterRepeatMovePoint(boolean filter) {
        this.q = filter;
    }

    public boolean isFilterRepeatMovePoint() {
        return this.q;
    }

    public void setSingleRegionMode() {
        nativeSetRegionMode(1);
        EpdController.setScreenHandWritingRegionMode(getHostView(), 1);
        a("setSingleRegionMode");
    }

    public void setMultiRegionMode() {
        nativeSetRegionMode(0);
        EpdController.setScreenHandWritingRegionMode(getHostView(), 0);
        a("setMultiRegionMode");
    }

    public void enableSideBtnErase(boolean enable) {
        nativeEnableSideBtnErase(enable);
    }

    public void setLimitRect(Rect rect) {
        if (rect != null) {
            nativeSetLimitRegion(a(rect));
            EpdController.setScreenHandWritingRegionLimit(getHostView(), new Rect[]{rect});
            a("setLimitRect");
        }
    }

    public void setExcludeRect(List<Rect> rectList) {
        if (rectList == null || rectList.isEmpty()) {
            return;
        }
        nativeSetExcludeRegion(a(rectList));
        EpdController.setScreenHandWritingRegionExclude(
                getHostView(), (Rect[]) rectList.toArray(new Rect[0]));
        a("setExcludeRect");
    }

    @WorkerThread
    public void onTouchPointReceived(float x2, float y2, int pressure, int tx, int ty, boolean isErasing, boolean shortcutDrawing, boolean shortcutErasing, int state, long timestampNanos) {
        emitRawInputEventV2(
                x2, y2, pressure, tx, ty, isErasing, shortcutErasing, state, timestampNanos);
        long ts = timestampNanos / 1_000_000L;
        if (state == 5) {
            a(x2, y2, pressure, 0, tx, ty, ts);
        }
        if (state == 6 || j()) {
            if (state == 0) {
                this.a = isErasing;
                q();
                a(x2, y2, pressure, 0, tx, ty, ts, this.a, shortcutDrawing, shortcutErasing);
                return;
            }
            if (state == 1) {
                this.a = isErasing;
                if (h()) {
                    a(x2, y2, pressure, 0, tx, ty, ts, this.a);
                    return;
                } else {
                    q();
                    a(x2, y2, pressure, tx, ty, shortcutDrawing, shortcutErasing, ts);
                    return;
                }
            }
            if (state == 2 || state == 3) {
                this.a = isErasing;
                b(x2, y2, pressure, 0, tx, ty, ts, this.a, state == 3, false);
                this.a = false;
            } else if (state == 6) {
                Log.d(w, "EpdShapeHandler pen force release");
                this.a = isErasing;
                b(x2, y2, pressure, 0, tx, ty, ts, this.a, false, true);
                this.a = false;
            }
        }
    }

    /** Called only by the native reader after an input device has been opened. */
    @WorkerThread
    public void onRawInputDeviceInfo(
            int xMin, int xMax, int xFuzz, int xFlat, int xResolution,
            int yMin, int yMax, int yFuzz, int yFlat, int yResolution,
            int pressureMin, int pressureMax, int pressureFuzz, int pressureFlat,
            int pressureResolution,
            int tiltXMin, int tiltXMax, int tiltXFuzz, int tiltXFlat, int tiltXResolution,
            int tiltYMin, int tiltYMax, int tiltYFuzz, int tiltYFlat, int tiltYResolution,
            boolean kernelMonotonicClock) {
        RawInputAxisRange pressure = axisOrNull(
                pressureMin, pressureMax, pressureFuzz, pressureFlat, pressureResolution);
        if (pressure != null && pressure.getMaximum() > 0) {
            this.rawPressureMaximum = pressure.getMaximum();
        }
        RawInputDeviceInfo info = new RawInputDeviceInfo(
                axisOrNull(xMin, xMax, xFuzz, xFlat, xResolution),
                axisOrNull(yMin, yMax, yFuzz, yFlat, yResolution),
                pressure,
                axisOrNull(tiltXMin, tiltXMax, tiltXFuzz, tiltXFlat, tiltXResolution),
                axisOrNull(tiltYMin, tiltYMax, tiltYFuzz, tiltYFlat, tiltYResolution),
                kernelMonotonicClock);
        synchronized (rawInputListenerLock) {
            this.rawInputDeviceInfo = info;
            RawInputListenerV2 listener = this.rawInputListenerV2;
            if (listener != null) {
                notifyRawInputDeviceInfoLocked(listener, info);
            }
        }
    }

    private static RawInputAxisRange axisOrNull(
            int minimum, int maximum, int fuzz, int flat, int resolution) {
        return maximum > minimum
                ? new RawInputAxisRange(minimum, maximum, fuzz, flat, resolution)
                : null;
    }

    private void emitRawInputEventV2(
            float rawX,
            float rawY,
            int pressure,
            int tiltX,
            int tiltY,
            boolean erasing,
            boolean shortcutErasing,
            int state,
            long timestampNanos) {
        RawInputPhase phase;
        switch (state) {
            case 0:
                phase = RawInputPhase.DOWN;
                break;
            case 1:
                phase = RawInputPhase.MOVE;
                break;
            case 2:
            case 3:
            case 6:
                phase = RawInputPhase.UP;
                break;
            case 5:
                phase = RawInputPhase.PROXIMITY;
                break;
            default:
                return;
        }
        synchronized (rawInputListenerLock) {
            RawInputListenerV2 listener = this.rawInputListenerV2;
            if (listener == null) {
                return;
            }
            TouchPoint mapped = new TouchPoint(rawX, rawY, pressure, 0, tiltX, tiltY, 0L);
            d(mapped);
            int maximum = Math.max(1, this.rawPressureMaximum);
            float normalized = Math.max(0.0f, Math.min(1.0f, ((float) pressure) / maximum));
            RawInputTool tool = !erasing
                    ? RawInputTool.PEN
                    : (shortcutErasing ? RawInputTool.SIDE_ERASER : RawInputTool.TAIL_ERASER);
            notifyRawInputEventLocked(listener, new RawInputEventV2(
                    phase,
                    tool,
                    mapped.x,
                    mapped.y,
                    pressure,
                    normalized,
                    maximum,
                    tiltX,
                    tiltY,
                    timestampNanos,
                    this.eventSequences.incrementAndGet(),
                    state == 3,
                    state == 6));
        }
    }

    private void notifyRawInputDeviceInfoLocked(
            RawInputListenerV2 listener, RawInputDeviceInfo info) {
        try {
            listener.onRawInputDeviceInfo(info);
        } catch (ThreadDeath | VirtualMachineError fatal) {
            throw fatal;
        } catch (Throwable failure) {
            detachFailedRawInputListener(listener, failure);
        }
    }

    private void notifyRawInputEventLocked(
            RawInputListenerV2 listener, RawInputEventV2 event) {
        try {
            listener.onRawInputEvent(event);
        } catch (ThreadDeath | VirtualMachineError fatal) {
            throw fatal;
        } catch (Throwable failure) {
            detachFailedRawInputListener(listener, failure);
        }
    }

    private void detachFailedRawInputListener(
            RawInputListenerV2 listener, Throwable failure) {
        Log.e(w, "RawInputListenerV2 callback failed; listener detached", failure);
        if (this.rawInputListenerV2 == listener) {
            this.rawInputListenerV2 = null;
        }
    }

    public EventBusHolder getEventBusHolder() {
        return TouchEventBus.getInstance().getEventBusHolder();
    }

    public TouchPointList detachTouchPointList() {
        TouchPointList touchPointList = this.i;
        n();
        return touchPointList;
    }

    public boolean isErasing() {
        return this.a;
    }

    public void printTouchInfo() {
        Debug.i(getClass(), "erasing: " + this.a + ", active: " + this.c + ", curPenRect: " + this.r + ", oldPenRect: " + this.s + ", downPointReported: " + this.p + ", filterRepeatMovePoint: " + this.q + ", penUpRefreshTimeMs: " + this.n + ", pointMatrix: " + MatrixUtils.matrixString(this.h) + ", reportData: " + this.d, new Object[0]);
    }

    private void c() {
        c(this.readerSession);
    }

    private void c(long session) {
        a("close native reader session=" + session);
        nativeRawClose(session);
    }

    private void b(boolean reported) {
        this.p = reported;
    }

    private void e(TouchPoint touchPoint) {
        if (i()) {
            if (!g()) {
                a(true);
                a(new PenActiveEvent(touchPoint));
            }
            b();
            RxTimerUtil.timerOnCurScheduler(100L, TimeUnit.MILLISECONDS, b(touchPoint));
        }
    }

    private TouchPoint d(TouchPoint touchPoint) {
        this.e[0] = touchPoint.x;
        this.e[1] = touchPoint.y;
        if (this.h == null) {
            q();
        }
        if (this.h != null) {
            this.h.mapPoints(this.f, this.e);
        } else {
            EpdController.mapToView((View) null, this.e, this.f);
        }
        float[] fArr = this.f;
        fArr[0] = fArr[0] - this.g[0];
        float[] fArr2 = this.f;
        fArr2[1] = fArr2[1] - this.g[1];
        touchPoint.x = this.f[0];
        touchPoint.y = this.f[1];
        return touchPoint;
    }

    private void b() {
        RxTimerUtil.cancel(this.u);
        this.u = null;
    }

    private void c(TouchPoint touchPoint) {
        if (this.l == null || !j()) {
            return;
        }
        this.l.onPenActive(touchPoint);
    }

    public void setLimitRect(List<Rect> rectList) {
        if (rectList == null || rectList.isEmpty()) {
            return;
        }
        nativeSetLimitRegion(a(rectList));
        EpdController.setScreenHandWritingRegionLimit(
                getHostView(), (Rect[]) rectList.toArray(new Rect[0]));
        a("setLimitRect");
    }

    private void a(String message) {
        if (F) {
            Log.d(w, message);
        }
    }

    private RxTimerUtil.TimerObserver b(TouchPoint touchPoint) {
        if (this.u == null) {
            this.u = new b(touchPoint);
        }
        return this.u;
    }

    private void f(TouchPoint touchPoint) {
        if (this.r == null) {
            float f = touchPoint.x;
            float f2 = touchPoint.y;
            this.r = new RectF(f, f2, f, f2);
            return;
        }
        this.r.union(touchPoint.x, touchPoint.y);
    }

    private float[] a(Rect rect) {
        RectF rectFMapToRawTouchPoint = EpdController.mapToRawTouchPoint(getHostView(), RectUtils.toRectF(rect));
        if (rect.width() > 0 && rect.height() > 0 && (rectFMapToRawTouchPoint == null || rectFMapToRawTouchPoint.width() <= com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY || rectFMapToRawTouchPoint.height() <= com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY)) {
            Log.e(w, "Empty region detected when mapping!!!!!");
        }
        return new float[]{rectFMapToRawTouchPoint.left, rectFMapToRawTouchPoint.top, rectFMapToRawTouchPoint.right, rectFMapToRawTouchPoint.bottom};
    }

    private float[] a(List<Rect> rectList) {
        float[] fArr = new float[rectList.size() * 4];
        for (int i = 0; i < rectList.size(); i++) {
            Rect rect = rectList.get(i);
            RectF rectFMapToRawTouchPoint = EpdController.mapToRawTouchPoint(getHostView(), RectUtils.toRectF(rect));
            if (rect.width() > 0 && rect.height() > 0 && (rectFMapToRawTouchPoint == null || rectFMapToRawTouchPoint.width() <= com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY || rectFMapToRawTouchPoint.height() <= com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY)) {
                Log.e(w, "Empty region detected!!!!!");
            }
            int i2 = i * 4;
            fArr[i2] = rectFMapToRawTouchPoint.left;
            fArr[i2 + 1] = rectFMapToRawTouchPoint.top;
            fArr[i2 + 2] = rectFMapToRawTouchPoint.right;
            fArr[i2 + 3] = rectFMapToRawTouchPoint.bottom;
        }
        return fArr;
    }

    private void b(float x2, float y2, int pressure, int size, int tx, int ty, long ts, boolean erasing, boolean releaseOutLimitRegion, boolean forceReport) {
        TouchPoint touchPoint = new TouchPoint(x2, y2, pressure, size, tx, ty, ts);
        d(touchPoint);
        if (!erasing) {
            f(touchPoint);
        }
        if (this.i != null && this.i.size() > 0) {
            a(this.i, erasing, forceReport);
        }
        n();
        b(touchPoint, erasing, releaseOutLimitRegion, forceReport);
        b(false);
        if (erasing) {
            return;
        }
        l();
    }

    private RxTimerUtil.TimerObserver d() {
        if (this.t == null) {
            this.t = new c();
        }
        return this.t;
    }

    private void a(float x2, float y2, int pressure, int tx, int ty, boolean shortcutDrawing, boolean shortcutErasing, long ts) {
        if (G) {
            if (F) {
                Debug.d(getClass(), "down point missing, use first move point as down point.", new Object[0]);
            }
            a(new PenDownPointLostEvent());
            a(x2, y2, pressure, 0, tx, ty, ts, this.a, shortcutDrawing, shortcutErasing);
        }
    }

    private void b(TouchPoint touchPoint, boolean erasing, boolean releaseOutLimitRegion, boolean forceReport) {
        if (this.l != null) {
            if ((forceReport || j() || erasing) && h()) {
                if (erasing) {
                    this.l.onEndRawErasing(releaseOutLimitRegion, touchPoint);
                } else {
                    this.l.onEndRawDrawing(releaseOutLimitRegion, touchPoint);
                }
            }
        }
    }

    private void a(float x2, float y2, int pressure, int size, int tx, int ty, long ts) {
        TouchPoint touchPoint = new TouchPoint(x2, y2, pressure, size, tx, ty, ts);
        d(touchPoint);
        e(touchPoint);
        c(touchPoint);
    }

    private void b(TouchPoint touchPoint, boolean erasing) {
        if (this.l != null) {
            if ((j() || erasing) && h()) {
                if (erasing) {
                    this.l.onRawErasingTouchPointMoveReceived(touchPoint);
                } else {
                    this.l.onRawDrawingTouchPointMoveReceived(touchPoint);
                }
            }
        }
    }

    private void a(boolean active) {
        this.c = active;
    }

    private boolean a(TouchPoint touchPoint, boolean create) {
        if (this.i == null) {
            if (!create) {
                return false;
            }
            this.i = new TouchPointList(600);
        }
        if (touchPoint == null || this.i == null) {
            return true;
        }
        this.i.add(touchPoint);
        return true;
    }

    private void a(Object event) {
        if (i()) {
            getEventBusHolder().post(event);
        }
    }

    private void a(float x2, float y2, int pressure, int size, int tx, int ty, long ts, boolean erasing, boolean shortcutDrawing, boolean shortcutErasing) {
        n();
        TouchPoint touchPoint = new TouchPoint(x2, y2, pressure, size, tx, ty, ts);
        d(touchPoint);
        a("pressReceived :" + touchPoint);
        if (!erasing) {
            o();
            f(touchPoint);
        }
        if (a(touchPoint, true)) {
            a(touchPoint, erasing, shortcutDrawing, shortcutErasing);
        }
    }

    private void a(float x2, float y2, int pressure, int size, int tx, int ty, long ts, boolean erasing) {
        TouchPoint touchPoint = new TouchPoint(x2, y2, pressure, size, tx, ty, ts);
        d(touchPoint);
        if (a(touchPoint)) {
            return;
        }
        a(touchPoint, true);
        if (!erasing) {
            o();
            f(touchPoint);
        }
        b(touchPoint, erasing);
    }

    private boolean a(TouchPoint curMovePoint) {
        TouchPoint touchPoint;
        if (!isFilterRepeatMovePoint() || this.i == null || (touchPoint = (TouchPoint) CollectionUtils.getLast(this.i.getPoints())) == null) {
            return false;
        }
        long j = curMovePoint.timestamp - touchPoint.timestamp;
        if (j <= 0) {
            return false;
        }
        return (Math.abs(curMovePoint.x - touchPoint.x) + Math.abs(curMovePoint.y - touchPoint.y)) / ((float) j) < 0.005f && Math.abs(curMovePoint.pressure - touchPoint.pressure) <= 2.0f;
    }

    @Nullable
    private RectF a() {
        if (this.r == null) {
            return null;
        }
        RectF rectF = new RectF(this.r);
        RectUtils.expand(rectF, this.j);
        if (this.s != null) {
            rectF.union(this.s);
        }
        this.s = new RectF(this.r);
        this.r = null;
        return rectF;
    }

    private void a(TouchPoint point, boolean erasing, boolean shortcutDrawing, boolean shortcutErasing) {
        if (this.l != null) {
            if (j() || erasing) {
                b(true);
                if (erasing) {
                    this.l.onBeginRawErasing(shortcutErasing, point);
                } else {
                    this.l.onBeginRawDrawing(shortcutDrawing, point);
                }
            }
        }
    }

    private void a(TouchPointList touchPointList, boolean erasing, boolean forceReport) {
        if (this.l == null || touchPointList == null) {
            return;
        }
        if ((forceReport || j() || erasing) && h()) {
            if (erasing) {
                this.l.onRawErasingTouchPointListReceived(touchPointList);
            } else {
                this.l.onRawDrawingTouchPointListReceived(touchPointList);
            }
        }
    }

    private void a(RectF refreshRect) {
        if (this.l == null || !j() || refreshRect == null) {
            return;
        }
        Debug.d(getClass(), "invokePenUpRefresh: " + refreshRect.toString(), new Object[0]);
        this.l.onPenUpRefresh(refreshRect);
    }
}
