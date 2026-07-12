package com.onyx.android.sdk.rx;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.onyx.android.sdk.common.request.WakeLockHolder;
import com.onyx.android.sdk.utils.ActivityUtil;
import com.onyx.android.sdk.utils.Benchmark;
import com.onyx.android.sdk.utils.Debug;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class RxBaseAction<T> {
    private static final String f = "onyx_";
    private static boolean g = false;
    private static int h = 2000;
    private static Context i;
    private WakeLockHolder a;
    private WeakReference<Context> b;
    private Benchmark d;
    protected boolean useWakelock = true;
    private final AtomicBoolean c = new AtomicBoolean(false);
    private final CompositeDisposable e = new CompositeDisposable();

    public static void init(Context context) {
        i = context.getApplicationContext();
    }

    public static void setEnableBenchmark(boolean enable) {
        g = enable;
    }

    public static void setReportTimeMs(int time) {
        h = time;
    }

    private void c() {
        b();
    }

    private void b() {
        releaseActionWakeLock();
        a();
    }

    private void a(Throwable throwable) {
        Debug.e(getClass(), throwable);
        b();
    }

    public static Context getAppContext() {
        return i;
    }

    public void setUseWakelock(boolean useWakelock) {
        this.useWakelock = useWakelock;
    }

    public Observable<T> build() {
        return create().observeOn(getMainUIScheduler()).doOnSubscribe(this::onSubscribe).subscribeOn(trampolineMainThread()).doFinally(this::c).doOnError(this::a);
    }

    public Scheduler trampolineMainThread() {
        return Looper.myLooper() != Looper.getMainLooper() ? AndroidSchedulers.mainThread() : Schedulers.trampoline();
    }

    public void onSubscribe(Disposable disposable) {
        benchmarkStart();
        addDisposable(disposable);
        acquireActionWakeLock();
    }

    public boolean isDisposed() {
        return this.e.isDisposed();
    }

    public void addDisposable(Disposable disposable) {
        this.e.add(disposable);
    }

    public void dispose() {
        if (this.e.size() <= 0 && !this.e.isDisposed()) {
            Debug.w(getClass(), "${toSimpleNameString()} dispose fail, as compositeDisposable is empty", new Object[0]);
        }
        this.e.dispose();
    }

    public void benchmarkStart() {
        if (g) {
            if (this.d == null) {
                this.d = new Benchmark();
            }
            this.d.restart();
        }
    }

    protected void acquireActionWakeLock() {
        if (this.useWakelock) {
            WakeLockHolder wakeLockHolder = new WakeLockHolder();
            this.a = wakeLockHolder;
            wakeLockHolder.acquireWakeLock(getAppContext(), getWakeLockFlags(), getWakeLockTag());
        }
    }

    protected int getWakeLockFlags() {
        return 26;
    }

    protected String getWakeLockTag() {
        return f + getClass().getName();
    }

    protected void releaseActionWakeLock() {
        WakeLockHolder wakeLockHolder = this.a;
        if (wakeLockHolder != null) {
            wakeLockHolder.releaseWakeLock();
        }
    }

    public boolean isWakeLockHeld() {
        WakeLockHolder wakeLockHolder = this.a;
        return wakeLockHolder != null && wakeLockHolder.isHeld();
    }

    protected abstract Observable<T> create();

    public Disposable execute() {
        Disposable disposableSubscribe = build().subscribe();
        addDisposable(disposableSubscribe);
        return disposableSubscribe;
    }

    public Observable<T> buildFast() {
        return create().doOnSubscribe(this::onSubscribe).doOnError(this::a).doFinally(this::c);
    }

    public RxBaseAction<T> setActivityContext(Context context) {
        this.b = new WeakReference<>(context);
        return this;
    }

    @Nullable
    public Context getActivityContext() {
        WeakReference<Context> weakReference = this.b;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Nullable
    public Activity getActivity() {
        Context activityContext = getActivityContext();
        if (activityContext == null) {
            return null;
        }
        return ActivityUtil.getActivitySafety(activityContext);
    }

    public void setAbort() {
        this.c.set(true);
    }

    public boolean isAbort() {
        return this.c.get();
    }

    public AtomicBoolean getAbort() {
        return this.c;
    }

    public Scheduler getMainUIScheduler() {
        return AndroidSchedulers.mainThread();
    }

    private void a() {
        Benchmark benchmark;
        if (!g || (benchmark = this.d) == null || benchmark.duration() < h) {
            return;
        }
        this.d.report(RxBaseAction.class.getSimpleName() + ": " + getClass().getName());
    }
}
