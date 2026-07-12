package com.onyx.android.sdk.rx;

import android.os.SystemClock;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RxFirstDebounceWithReset<T> implements Disposable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final long d = 1L;

    @NotNull private final PublishSubject<T> a;
    @Nullable private Disposable b;
    @NotNull private final a<T> c;

    public RxFirstDebounceWithReset() {
        a = PublishSubject.create();
        c = new a<>();
    }

    private Observable<RxFirstDebounceWithReset<T>> a(
            long timeout, TimeUnit unit,
            Function1<? super T, ? extends Observable<?>> processor) {
        return a.concatMap(event -> {
            Observable<?> work = c.d()
                    ? a(event, timeout, unit, processor)
                    : b(event, timeout, unit, processor);
            return work.map(ignored -> this).onErrorReturn(ignored -> this);
        });
    }

    private Observable<?> a(T event, long timeout, TimeUnit unit,
                            Function1<? super T, ? extends Observable<?>> processor) {
        c.c(event);
        Observable<?> work = processor.invoke(event);
        return work.delay(timeout, unit).doOnNext(ignored -> c.a(event));
    }

    private Observable<Object> b(T event, long timeout, TimeUnit unit,
                                 Function1<? super T, ? extends Observable<?>> processor) {
        c.c(event);
        Pair<Long, TimeUnit> delay = c.a(timeout, unit);
        return Observable.just(event)
                .delay(delay.getFirst(), delay.getSecond())
                .flatMap(latest -> {
                    if (!c.b(latest)) return Observable.empty();
                    Observable<?> work = processor.invoke(latest);
                    return work.map(value -> (Object) value);
                })
                .doOnNext(ignored -> c.a(event));
    }

    private static RxFirstDebounceWithReset a(
            RxFirstDebounceWithReset owner, Object ignored) {
        return owner;
    }

    private static void a(RxFirstDebounceWithReset owner, Object event, Object ignored) {
        owner.c.a(event);
    }

    private static void b(RxFirstDebounceWithReset owner, Object event, Object ignored) {
        owner.c.a(event);
    }

    public static final RxFirstDebounceWithReset<?>.a<?> access$getStateMachine$p(
            RxFirstDebounceWithReset<?> owner) {
        return owner.c;
    }

    public static final Observable access$processImmediately(
            RxFirstDebounceWithReset owner, Object event, long timeout, TimeUnit unit,
            Function1 processor) {
        return owner.a(event, timeout, unit, processor);
    }

    public static final Observable access$processWithDebounce(
            RxFirstDebounceWithReset owner, Object event, long timeout, TimeUnit unit,
            Function1 processor) {
        return owner.b(event, timeout, unit, processor);
    }

    public final void subscribeForUI(long timeout, @NotNull TimeUnit unit,
                                     @NotNull Function1<? super T, Unit> onNext) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        Intrinsics.checkNotNullParameter(onNext, "onNext");
        subscribe(timeout, unit, event -> Observable.just(event)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ignored -> onNext.invoke(event)));
    }

    public final void subscribe(long timeout, @NotNull TimeUnit unit,
                                @NotNull Function1<? super T, ? extends Observable<?>> processor) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        Intrinsics.checkNotNullParameter(processor, "processor");
        dispose();
        b = a(timeout, unit, processor).subscribe(
                ignored -> {}, ignored -> {});
    }

    public final void sendEvent(@NotNull T event) {
        Intrinsics.checkNotNullParameter(event, "event");
        c.d(event);
        a.onNext(event);
    }

    @Override
    public void dispose() {
        if (b != null) {
            b.dispose();
            b = null;
        }
    }

    @Override
    public boolean isDisposed() {
        return b == null || b.isDisposed();
    }

    private final class a<E> {
        private final AtomicReference<E> a = new AtomicReference<>();
        private final AtomicReference<E> b = new AtomicReference<>();
        private AtomicLong c = new AtomicLong();

        public AtomicReference<E> a() { return a; }
        public AtomicLong b() { return c; }
        public AtomicReference<E> c() { return b; }
        public void a(AtomicLong value) { c = value; }

        public void d(E event) {
            a.set(event);
            c.set(SystemClock.elapsedRealtime());
        }

        public void c(E event) {
            b.set(event);
        }

        public void a(E event) {
            if (a.compareAndSet(event, null)) {
                b.set(null);
                c.set(0L);
            }
        }

        public Pair<Long, TimeUnit> a(long timeout, TimeUnit unit) {
            long elapsed = Math.max(SystemClock.elapsedRealtime() - c.get(), 0L);
            long remaining = Math.max(unit.toMillis(timeout) - elapsed, d);
            return new Pair<>(remaining, TimeUnit.MILLISECONDS);
        }

        public boolean d() {
            return b.get() == null;
        }

        public boolean b(E event) {
            return event == a.get();
        }
    }

    public static final class Companion {
        private Companion() {}
        public Companion(DefaultConstructorMarker marker) { this(); }
    }

    static final class b { private b() {} }
    static final class c { private c() {} }
    static final class d { private d() {} }
    static final class e { private e() {} }
    static final class f { private f() {} }
}
