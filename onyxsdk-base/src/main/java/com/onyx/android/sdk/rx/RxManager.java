package com.onyx.android.sdk.rx;

import android.content.Context;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.common.request.WakeLockHolder;
import com.onyx.android.sdk.utils.Benchmark;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TTFFont;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager.class */
public final class RxManager {
    private static final String h = "onyx_";
    private static final String i = h + RxManager.class.getSimpleName();
    private static boolean j = false;
    private static int k;
    private Benchmark b;
    private volatile Context c;
    private Scheduler e;
    private Scheduler f;
    private WakeLockHolder a = new WakeLockHolder();
    private boolean d = true;
    private List<Observable<? extends RxRequest>> g = new ArrayList();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager$Builder.class */
    public static final class Builder {
        private static Context c;
        private Scheduler a;
        private Scheduler b;

        public static void initAppContext(Context context) {
            c = context;
        }

        public static RxManager fromScheduler(Scheduler scheduler) {
            return new Builder().subscribeOn(scheduler).observeOn(scheduler).build();
        }

        public static RxManager sharedSingleThreadManager() {
            Scheduler scheduler = SingleThreadScheduler.scheduler();
            return new Builder().subscribeOn(scheduler).observeOn(scheduler).build();
        }

        public static RxManager sharedMultiThreadManager() {
            Scheduler scheduler = MultiThreadScheduler.scheduler();
            return new Builder().subscribeOn(scheduler).observeOn(scheduler).build();
        }

        public static RxManager newSingleThreadManager() {
            return newSingleThreadManager(TTFFont.UNKNOWN_FONT_NAME);
        }

        public static RxManager newMultiThreadManager() {
            Scheduler schedulerNewScheduler = MultiThreadScheduler.newScheduler();
            return new Builder().subscribeOn(schedulerNewScheduler).observeOn(schedulerNewScheduler).build();
        }

        public Builder subscribeOn(@NonNull Scheduler subscribeOn) {
            this.a = subscribeOn;
            return this;
        }

        public Builder observeOn(@NonNull Scheduler observeOn) {
            this.b = observeOn;
            return this;
        }

        public RxManager build() {
            if (this.a == null) {
                throw new IllegalStateException("subscribeOn required.");
            }
            if (this.b == null) {
                this.b = AndroidSchedulers.mainThread();
            }
            if (c == null) {
                Debug.w(Builder.class, "Please call initAppContext first!", new Object[0]);
                initAppContext(ResManager.getAppContext());
            }
            return new RxManager(c, this.a, this.b);
        }

        public static RxManager newSingleThreadManager(String name) {
            Scheduler schedulerNewScheduler = SingleThreadScheduler.newScheduler(name);
            return new Builder().subscribeOn(schedulerNewScheduler).observeOn(schedulerNewScheduler).build();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager$ThreadPoolIdentifier.class */
    public static class ThreadPoolIdentifier {
        public static final String DEFAULT = "default";
        public static final String DB = "db";
        public static final String DATA = "data";
        public static final String EXTRACT = "extract";
        public static final String FS = "fs";
        public static final String CLOUD = "cloud";
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager$a.class */
    class a<T extends RxRequest> implements Callable<T> {
        final /* synthetic */ T a;

        a(T rxRequest) {
            this.a = rxRequest;
        }

        /* JADX WARN: Incorrect return type in method signature: ()TT; */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public T call() throws Exception {
            RxManager.this.b();
            try {
                this.a.execute();
                RxManager.this.a(this.a);
                return this.a;
            } catch (Throwable th) {
                Debug.e(th);
                return RxManager.sneakyThrow(th);
            }
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager$b.class */
    class b<T> implements ObservableTransformer<T, T> {
        final /* synthetic */ RxCallback a;
        final /* synthetic */ RxManager owner = RxManager.this;

        b(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        public ObservableSource<T> apply(Observable<T> upstream) {
            return upstream.subscribeOn(RxManager.this.e).observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new RxManager$b$b(this))
                    .doFinally(new RxManager$b$a(this));
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxManager$c.class */
    class c<T> extends RxCallback<T> {
        final /* synthetic */ RxCallback a;

        c(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.onyx.android.sdk.rx.RxCallback, java.lang.Throwable] */
        @Override // com.onyx.android.sdk.rx.RxCallback
        public void onNext(@NonNull T t) {
            try {
                RxCallback.onNext(this.a, t);
            } catch (Throwable failure) {
                Debug.e(failure);
            }
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.onyx.android.sdk.rx.RxCallback, java.lang.Throwable] */
        @Override // com.onyx.android.sdk.rx.RxCallback
        public void onError(@NonNull Throwable e) {
            try {
                RxCallback.onError(this.a, e);
            } catch (Throwable failure) {
                Debug.e(failure);
            }
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.onyx.android.sdk.rx.RxCallback, java.lang.Throwable] */
        @Override // com.onyx.android.sdk.rx.RxCallback
        public void onComplete() {
            try {
                RxCallback.onComplete(this.a);
            } catch (Throwable failure) {
                Debug.e(failure);
            }
        }
    }

    RxManager(Context context, Scheduler subscribeOn, Scheduler observeOn) {
        this.c = context;
        this.e = subscribeOn;
        this.f = observeOn;
    }

    public static void setEnableBenchmarkDebug(boolean enableBenchmarkDebug) {
        j = enableBenchmarkDebug;
    }

    public static void setReportTime(int reportTime) {
        k = reportTime;
    }

    /* JADX INFO: Access modifiers changed from: private */
    void d() {
        if (this.d) {
            this.a.releaseWakeLock();
        }
    }

    public Scheduler getObserveOn() {
        return this.f;
    }

    public Scheduler getSubscribeOn() {
        return this.e;
    }

    public Context getAppContext() {
        return this.c;
    }

    public void setUseWakelock(boolean useWakelock) {
        this.d = useWakelock;
    }

    public final Scheduler getObserveScheduler() {
        return this.f;
    }

    public <T extends RxRequest> void enqueue(T request, RxCallback<T> callback) {
        create(request).compose(a((RxCallback) callback)).subscribe(b(callback));
    }

    public <T extends RxRequest> void enqueueList(RxCallback<T> callback) {
        ArrayList arrayList = new ArrayList();
        if (!CollectionUtils.isNullOrEmpty(c())) {
            arrayList.addAll(c());
        }
        Observable.concat(arrayList).compose(a((RxCallback) callback)).subscribe(b(callback));
        c().clear();
    }

    public <T extends RxRequest> RxManager append(T request) {
        c().add(create(request));
        return this;
    }

    public void shutdown() {
        Scheduler scheduler = this.f;
        if (scheduler != null && a(scheduler)) {
            this.f.shutdown();
        }
        Scheduler scheduler2 = this.e;
        if (scheduler2 == null || !a(scheduler2)) {
            return;
        }
        this.e.shutdown();
    }

    public <T extends RxRequest> void concat(List<T> requests, RxCallback<T> callback) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = requests.iterator();
        while (it.hasNext()) {
            arrayList.add(create(it.next()));
        }
        Observable.concat(arrayList).compose(a((RxCallback) callback)).subscribe(b(callback));
    }

    public <T1 extends RxRequest, T2 extends RxRequest, T3 extends RxRequest, T4> void zip3(T1 r1, T2 r2, T3 r3, Function3<T1, T2, T3, T4> function3, RxCallback<T4> callback) {
        Observable.zip(create(r1), create(r2), create(r3), function3).compose(a((RxCallback) callback)).subscribe(b(callback));
    }

    public <T extends RxRequest, R> void zip(List<T> rqList, Function<Object[], R> fun, RxCallback<T> callback) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = rqList.iterator();
        while (it.hasNext()) {
            arrayList.add(create(it.next()));
        }
        Observable.zip(arrayList, fun).compose(a((RxCallback) callback)).subscribe(b(callback));
    }

    public <T extends RxRequest> Observable<T> create(T request) {
        request.setContext(getAppContext());
        return Observable.fromCallable(new a(request));
    }

    public boolean isEnableBenchmarkDebug() {
        return j;
    }

    private List<Observable<? extends RxRequest>> c() {
        return this.g;
    }

    private <T> RxCallback<T> b(RxCallback callback) {
        return new c(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    private void b() {
        if (isEnableBenchmarkDebug()) {
            this.b = new Benchmark();
        }
    }

    private boolean a(Scheduler scheduler) {
        return scheduler != AndroidSchedulers.mainThread();
    }

    private <T> ObservableTransformer<T, T> a(RxCallback callback) {
        return new b(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    private void a(Object o) {
        Benchmark benchmark;
        if (!isEnableBenchmarkDebug() || (benchmark = this.b) == null || o == null || benchmark.duration() < k) {
            return;
        }
        this.b.report(RxManager.class.getSimpleName() + ": " + o.getClass().getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    void a(String tag) {
        if (this.d) {
            this.a.acquireWakeLock(this.c, tag);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, E extends Throwable> T sneakyThrow(Throwable failure) throws E {
        throw (E) failure;
    }
}

final class RxManager$b$a implements Action {
    private final RxManager.b<?> owner;

    RxManager$b$a(RxManager.b<?> owner) {
        this.owner = owner;
    }

    @Override
    public void run() throws Exception {
        owner.owner.d();
        RxCallback.onFinally(owner.a);
    }
}

final class RxManager$b$b implements Consumer<Disposable> {
    private final RxManager.b<?> owner;

    RxManager$b$b(RxManager.b<?> owner) {
        this.owner = owner;
    }

    @Override
    public void accept(Disposable disposable) throws Exception {
        owner.owner.a("onyx_" + RxManager.class.getSimpleName());
        RxCallback.onSubscribe(owner.a, disposable);
    }
}
