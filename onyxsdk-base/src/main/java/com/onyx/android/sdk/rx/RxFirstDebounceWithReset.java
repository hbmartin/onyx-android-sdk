package com.onyx.android.sdk.rx;

import android.os.SystemClock;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0008\u000b\u0018\u0000 \"*\u0008\u0008\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002\"#B\u0005\u00a2\u0006\u0002\u0010\u0004JK\u0010\u000b\u001a\u000e\u0012\n\u0012\u0008\u0012\u0004\u0012\u00028\u00000\u00000\u000c2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102%\u0010\u0011\u001a!\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\u000c\u0008\u0013\u0012\u0008\u0008\u0014\u0012\u0004\u0008\u0008(\u0015\u0012\u0008\u0012\u0006\u0012\u0002\u0008\u00030\u000c0\u0012H\u0002J\u0008\u0010\u0016\u001a\u00020\u0017H\u0016J\u0008\u0010\u0018\u001a\u00020\u0019H\u0016JE\u0010\u001a\u001a\n\u0012\u0006\u0008\u0001\u0012\u00020\u00020\u000c2\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0008\u0012\u0006\u0012\u0002\u0008\u00030\u000c0\u0012H\u0002\u00a2\u0006\u0002\u0010\u001bJC\u0010\u001c\u001a\u0008\u0012\u0004\u0012\u00020\u00020\u000c2\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0008\u0012\u0006\u0012\u0002\u0008\u00030\u000c0\u0012H\u0002\u00a2\u0006\u0002\u0010\u001bJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001eJ=\u0010\u001f\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102%\u0010\u0011\u001a!\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\u000c\u0008\u0013\u0012\u0008\u0008\u0014\u0012\u0004\u0008\u0008(\u0015\u0012\u0008\u0012\u0006\u0012\u0002\u0008\u00030\u000c0\u0012J9\u0010 \u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102!\u0010!\u001a\u001d\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\u000c\u0008\u0013\u0012\u0008\u0008\u0014\u0012\u0004\u0008\u0008(\u0015\u0012\u0004\u0012\u00020\u00170\u0012R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0007R\u0008\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0008\u001a\u0010\u0012\u000c\u0012\n \n*\u0004\u0018\u00018\u00008\u00000\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/onyx/android/sdk/rx/RxFirstDebounceWithReset;", "T", "", "Lio/reactivex/disposables/Disposable;", "()V", "disposable", "stateMachine", "Lcom/onyx/android/sdk/rx/RxFirstDebounceWithReset$DebounceStateMachine;", "subject", "Lio/reactivex/subjects/PublishSubject;", "kotlin.jvm.PlatformType", "createProcessChain", "Lio/reactivex/Observable;", "timeout", "", "unit", "Ljava/util/concurrent/TimeUnit;", "processor", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "event", "dispose", "", "isDisposed", "", "processImmediately", "(Ljava/lang/Object;JLjava/util/concurrent/TimeUnit;Lkotlin/jvm/functions/Function1;)Lio/reactivex/Observable;", "processWithDebounce", "sendEvent", "(Ljava/lang/Object;)V", "subscribe", "subscribeForUI", "onNext", "Companion", "DebounceStateMachine", "onyxsdk-base_release"})
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

    public static final RxFirstDebounceWithReset.a access$getStateMachine$p(
            RxFirstDebounceWithReset owner) {
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

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\t\n\u0000\u0008\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/rx/RxFirstDebounceWithReset$Companion;", "", "()V", "MIN_DELAY_MS", "", "onyxsdk-base_release"})
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
