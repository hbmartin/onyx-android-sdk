package com.onyx.android.sdk.rx;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.Debug;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils.class */
public class RxUtils {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils$Consumer2.class */
    public interface Consumer2<T, R> {
        void accept(T t, R r) throws Exception;
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils$a.class */
    static class a implements Callable<Object> {
        final /* synthetic */ Runnable a;

        a(Runnable runnable) {
            this.a = runnable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            Runnable runnable = this.a;
            if (runnable != null) {
                runnable.run();
            }
            return this;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils$b.class */
    static class b implements Callable<Object> {
        final /* synthetic */ Runnable a;

        b(Runnable runnable) {
            this.a = runnable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            Runnable runnable = this.a;
            if (runnable != null) {
                runnable.run();
            }
            return this;
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils$c.class */
    static class c implements Action {
        final /* synthetic */ Action a;

        c(Action action) {
            this.a = action;
        }

        public void run() throws Exception {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                RxUtils.switchToUIThreadDispose(this.a);
                return;
            }
            try {
                this.a.run();
            } catch (Exception e) {
                Debug.e(RxUtils.class, "disposeInUiThread", e);
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxUtils$d.class */
    static class d implements Runnable {
        final /* synthetic */ Action a;
        final /* synthetic */ Scheduler.Worker b;

        d(Action action, Scheduler.Worker worker) {
            this.a = action;
            this.b = worker;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.a.run();
            } catch (Exception error) {
                Debug.e(RxUtils.class, "switchToUIThreadDispose", error);
            } finally {
                this.b.dispose();
            }
        }
    }

    public static void runInIO(Runnable runnable) {
        run(runnable, Schedulers.io());
    }

    public static void runInComputation(Runnable runnable) {
        run(runnable, Schedulers.computation());
    }

    public static void runInUI(Runnable runnable) {
        run(runnable, AndroidSchedulers.mainThread());
    }

    public static void postRunInUISafely(Handler handler, Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        } else {
            runInUI(runnable);
        }
    }

    public static void run(Runnable runnable, Scheduler scheduler) {
        Observable.fromCallable(new a(runnable)).subscribeOn(scheduler).subscribe();
    }

    public static <T> void runWith(Callable<T> callable, Consumer<T> consumer, Scheduler scheduler) {
        Observable.fromCallable(callable).subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public static <T> void runWithInComputation(Callable<T> callable, Consumer<T> consumer) {
        Observable.fromCallable(callable).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public static void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static boolean isDisposed(Disposable disposable) {
        if (disposable != null) {
            return disposable.isDisposed();
        }
        return true;
    }

    public static Disposable disposeInUiThread(Action action) {
        return Disposables.fromAction(new c(action));
    }

    public static void switchToUIThreadDispose(Action action) {
        Scheduler.Worker workerCreateWorker = AndroidSchedulers.mainThread().createWorker();
        workerCreateWorker.schedule(new d(action, workerCreateWorker));
    }

    public static <T> void acceptItemSafety(Consumer<T> consumer, T item) {
        Consumer<T> consumer2 = consumer;
        if (consumer2 != null) {
            try {
                consumer2 = consumer;
                consumer2.accept(item);
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

    public static <T, R> R applyItemSafety(@NonNull Function<T, R> function, T t) {
        try {
            return (R) function.apply(t);
        } catch (Exception unused) {
            return null;
        }
    }

    public static <T> boolean testSafety(@NonNull Predicate<T> predicate, T item) {
        try {
            return predicate.test(item);
        } catch (Exception unused) {
            return false;
        }
    }

    public static <T> void done(Observer<T> observer, T item) {
        observer.onNext(item);
        observer.onComplete();
    }

    public static Observable<View> postDelayObservable(View view, int delayTimeMs) {
        BehaviorSubject behaviorSubjectCreate = BehaviorSubject.create();
        view.postDelayed(() -> {
            done(behaviorSubjectCreate, view);
        }, delayTimeMs);
        return behaviorSubjectCreate;
    }

    public static <T, R> Observable<R> applyItemObservable(@NonNull T item, Scheduler scheduler, Function<T, R> func) {
        return Observable.just(item).observeOn(scheduler).map(func);
    }

    public static <T> Observable<List<T>> emptyListObservable() {
        return Observable.just(new ArrayList());
    }

    public static <T> Consumer<T> emptyConsumer() {
        return t -> {
        };
    }

    public static void runInIO(Runnable runnable, Consumer<Object> onNext) {
        run(runnable, Schedulers.io(), onNext);
    }

    public static Disposable runInComputation(Runnable runnable, Consumer<Object> onNext) {
        return run(runnable, Schedulers.computation(), onNext);
    }

    public static <T, R> void acceptItemSafety(Consumer2<T, R> consumer, T item1, R item2) {
        if (consumer != null) {
            try {
                consumer.accept(item1, item2);
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

    public static Disposable run(Runnable runnable, Scheduler scheduler, Consumer<Object> onNext) {
        return Observable.fromCallable(new b(runnable)).subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }
}
