// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import kotlin.collections.CollectionsKt;
import com.onyx.android.sdk.utils.Debug;
import io.reactivex.ObservableSource;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.functions.Function1;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.Observable;
import android.view.View;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import io.reactivex.Observer;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u000e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a)\u0010\u0007\u001a\u00020\u0001\"\b\b\u0000\u0010\b*\u00020\t*\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\n2\u0006\u0010\u000b\u001a\u0002H\b¢\u0006\u0002\u0010\f\u001a(\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u000f0\u000e\"\u0004\b\u0000\u0010\b*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u000f0\u000e\u001a_\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00130\u00122)\u0010\u0014\u001a%\u0012\u0013\u0012\u0011H\b¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e0\u0012\u001aK\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u00192)\u0010\u0014\u001a%\u0012\u0013\u0012\u0011H\b¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e0\u0012\u001a4\u0010\u001a\u001a&\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\t0\t \u001b*\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\t0\t\u0018\u00010\u000e0\u000e*\b\u0012\u0004\u0012\u00020\t0\u000e\u001aB\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\b0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\b0\n\u001a$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e*\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\n\u001a$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e*\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\n\u001a'\u0010 \u001a\u00020\u0001\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0!2\u0006\u0010\u000b\u001a\u0002H\b¢\u0006\u0002\u0010\"\u001aI\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2'\u0010\u0014\u001a#\u0012\u0013\u0012\u0011H\b¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u000e0\u0012\u001aP\u0010$\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00130\u00122\u001a\u0010%\u001a\u0016\u0012\u0004\u0012\u0002H\b\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e0\u0012\u001al\u0010$\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00130\u00122\u001a\u0010&\u001a\u0016\u0012\u0004\u0012\u0002H\b\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e0\u00122\u001a\u0010'\u001a\u0016\u0012\u0004\u0012\u0002H\b\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e0\u0012\u001aD\u0010$\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00130\u00122\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e\u001a&\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\u000e*\b\u0012\u0004\u0012\u00020\u00130\u000e2\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e\u001a&\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0\u000e*\b\u0012\u0004\u0012\u00020\u00130\u000e2\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u000e\u001a\f\u0010*\u001a\u00020\u0013*\u0004\u0018\u00010+\u001aK\u0010,\u001a&\u0012\f\u0012\n \u001b*\u0004\u0018\u0001H\bH\b \u001b*\u0012\u0012\f\u0012\n \u001b*\u0004\u0018\u0001H\bH\b\u0018\u00010\u000e0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010-\u001a\u0002H\b¢\u0006\u0002\u0010.\u001a\u0018\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u000e*\u0002002\u0006\u00101\u001a\u000202\u001a'\u00103\u001a\u00020\u0013\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b042\u0006\u0010\u000b\u001a\u0002H\b¢\u0006\u0002\u00105\u001aD\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u000f0\u000e\"\b\b\u0000\u0010\b*\u00020\t*\b\u0012\u0004\u0012\u0002H\b0\u000e2\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u0002022\b\b\u0002\u0010\u0004\u001a\u00020\u0005¨\u0006:" }, d2 = { "run", "", "runnable", "Ljava/lang/Runnable;", "scheduler", "Lio/reactivex/Scheduler;", "runInUI", "acceptSafety", "T", "", "Lio/reactivex/functions/Consumer;", "item", "(Lio/reactivex/functions/Consumer;Ljava/lang/Object;)V", "collectAndFlatten", "Lio/reactivex/Observable;", "", "concatMapIf", "predicate", "Lkotlin/Function1;", "", "observable", "Lkotlin/ParameterName;", "name", "r", "concatMapIfLast", "Lio/reactivex/subjects/BehaviorSubject;", "continueOnFail", "kotlin.jvm.PlatformType", "doOnNextIf", "onNext", "doOnNextIfFalse", "doOnNextIfTrue", "done", "Lio/reactivex/Observer;", "(Lio/reactivex/Observer;Ljava/lang/Object;)V", "flatMapFilter", "flatMapIf", "nextObservable", "trueObservable", "falseObservable", "flatMapIfFalse", "flatMapIfTrue", "isDisposed", "Lio/reactivex/disposables/Disposable;", "onErrorReturnItemWithLog", "t", "(Lio/reactivex/Observable;Ljava/lang/Object;)Lio/reactivex/Observable;", "postDelayObservable", "Landroid/view/View;", "delayTimeMs", "", "testSafety", "Lio/reactivex/functions/Predicate;", "(Lio/reactivex/functions/Predicate;Ljava/lang/Object;)Z", "touchBuffer", "timeSpan", "", "count", "onyxsdk-base_release" })
public final class RxjavaKt
{
    public static final <T> void done(@NotNull final Observer<T> $this$done, @NotNull final T item) {
        Intrinsics.checkNotNullParameter((Object)$this$done, "<this>");
        Intrinsics.checkNotNullParameter((Object)item, "item");
        $this$done.onNext(item);
        $this$done.onComplete();
    }
    
    public static final <T> boolean testSafety(@NotNull final Predicate<T> $this$testSafety, @NotNull final T item) {
        Intrinsics.checkNotNullParameter((Object)$this$testSafety, "<this>");
        Intrinsics.checkNotNullParameter((Object)item, "item");
        boolean test;
        try {
            test = $this$testSafety.test(item);
        }
        catch (final Exception ex) {
            test = false;
        }
        return test;
    }
    
    public static final <T> void acceptSafety(@Nullable final Consumer<T> $this$acceptSafety, @NotNull final T item) {
        Intrinsics.checkNotNullParameter((Object)item, "item");
        if ($this$acceptSafety != null) {
            try {
                $this$acceptSafety.accept(item);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static final boolean isDisposed(@Nullable final Disposable $this$isDisposed) {
        return $this$isDisposed == null || $this$isDisposed.isDisposed();
    }
    
    @NotNull
    public static final Observable<View> postDelayObservable(@NotNull final View $this$postDelayObservable, final int delayTimeMs) {
        Intrinsics.checkNotNullParameter((Object)$this$postDelayObservable, "<this>");
        final BehaviorSubject<View> subject = BehaviorSubject.create();
        $this$postDelayObservable.postDelayed(() -> done(subject, $this$postDelayObservable), (long)delayTimeMs);
        return subject;
    }
    
    public static final void runInUI(@NotNull final Runnable runnable) {
        Intrinsics.checkNotNullParameter((Object)runnable, "runnable");
        final Scheduler mainThread;
        Intrinsics.checkNotNullExpressionValue((Object)(mainThread = AndroidSchedulers.mainThread()), "mainThread()");
        run(runnable, mainThread);
    }
    
    public static final void run(@NotNull final Runnable runnable, @NotNull final Scheduler scheduler) {
        Intrinsics.checkNotNullParameter((Object)runnable, "runnable");
        Intrinsics.checkNotNullParameter((Object)scheduler, "scheduler");
        Observable.fromCallable(new RxjavaKt$run$1(runnable)).subscribeOn(scheduler).subscribe();
    }
    
    @NotNull
    public static final <T> Observable<List<T>> touchBuffer(@NotNull final Observable<T> $this$touchBuffer, final long timeSpan, final int count, @NotNull final Scheduler scheduler) {
        Intrinsics.checkNotNullParameter((Object)$this$touchBuffer, "<this>");
        Intrinsics.checkNotNullParameter((Object)scheduler, "scheduler");
        final Observable buffer = $this$touchBuffer.buffer(timeSpan, TimeUnit.MILLISECONDS, scheduler, count);
        Intrinsics.checkNotNullExpressionValue((Object)buffer, "buffer(timeSpan, TimeUni\u2026ECONDS, scheduler, count)");
        return (Observable<List<T>>)buffer;
    }
    
    @NotNull
    public static final Observable<Boolean> doOnNextIfTrue(@NotNull final Observable<Boolean> $this$doOnNextIfTrue, @NotNull final Consumer<Boolean> onNext) {
        Intrinsics.checkNotNullParameter((Object)$this$doOnNextIfTrue, "<this>");
        Intrinsics.checkNotNullParameter((Object)onNext, "onNext");
        return doOnNextIf($this$doOnNextIfTrue, (kotlin.jvm.functions.Function1<? super Boolean, Boolean>)RxjavaKt$c.a, onNext);
    }
    
    @NotNull
    public static final Observable<Boolean> doOnNextIfFalse(@NotNull final Observable<Boolean> $this$doOnNextIfFalse, @NotNull final Consumer<Boolean> onNext) {
        Intrinsics.checkNotNullParameter((Object)$this$doOnNextIfFalse, "<this>");
        Intrinsics.checkNotNullParameter((Object)onNext, "onNext");
        return doOnNextIf($this$doOnNextIfFalse, (kotlin.jvm.functions.Function1<? super Boolean, Boolean>)RxjavaKt$b.a, onNext);
    }
    
    @NotNull
    public static final <T> Observable<T> doOnNextIf(@NotNull final Observable<T> $this$doOnNextIf, @NotNull final Function1<? super T, Boolean> predicate, @NotNull final Consumer<T> onNext) {
        Intrinsics.checkNotNullParameter((Object)$this$doOnNextIf, "<this>");
        Intrinsics.checkNotNullParameter((Object)predicate, "predicate");
        Intrinsics.checkNotNullParameter((Object)onNext, "onNext");
        final Observable<T> doOnNext = $this$doOnNextIf.doOnNext(it -> {
            if (Boolean.TRUE.equals(predicate.invoke(it))) {
                onNext.accept(it);
            }
        });
        Intrinsics.checkNotNullExpressionValue((Object)doOnNext, "doOnNext {\n        if (p\u2026ccept(it)\n        }\n    }");
        return doOnNext;
    }
    
    @NotNull
    public static final Observable<Object> flatMapIfTrue(@NotNull final Observable<Boolean> $this$flatMapIfTrue, @NotNull final Observable<?> nextObservable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapIfTrue, "<this>");
        Intrinsics.checkNotNullParameter((Object)nextObservable, "nextObservable");
        return flatMapIf($this$flatMapIfTrue, RxjavaKt$g.a, nextObservable);
    }
    
    @NotNull
    public static final Observable<Object> flatMapIfFalse(@NotNull final Observable<Boolean> $this$flatMapIfFalse, @NotNull final Observable<?> nextObservable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapIfFalse, "<this>");
        Intrinsics.checkNotNullParameter((Object)nextObservable, "nextObservable");
        return flatMapIf($this$flatMapIfFalse, RxjavaKt$e.a, new RxjavaKt$f(nextObservable));
    }
    
    @NotNull
    public static final <T> Observable<Object> flatMapIf(@NotNull final Observable<T> $this$flatMapIf, @NotNull final Function1<? super T, Boolean> predicate, @NotNull final Observable<?> nextObservable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapIf, "<this>");
        Intrinsics.checkNotNullParameter((Object)predicate, "predicate");
        Intrinsics.checkNotNullParameter((Object)nextObservable, "nextObservable");
        return flatMapIf($this$flatMapIf, predicate, new RxjavaKt$d<T>(nextObservable));
    }
    
    @NotNull
    public static final <T> Observable<Object> flatMapIf(@NotNull final Observable<T> $this$flatMapIf, @NotNull final Function1<? super T, Boolean> predicate, @NotNull final Function1<? super T, ? extends Observable<?>> trueObservable, @NotNull final Function1<? super T, ? extends Observable<?>> falseObservable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapIf, "<this>");
        Intrinsics.checkNotNullParameter((Object)predicate, "predicate");
        Intrinsics.checkNotNullParameter((Object)trueObservable, "trueObservable");
        Intrinsics.checkNotNullParameter((Object)falseObservable, "falseObservable");
        final Observable<Object> flatMap = $this$flatMapIf.flatMap(it ->
                (ObservableSource<Object>) (Boolean.TRUE.equals(predicate.invoke(it))
                        ? trueObservable.invoke(it) : falseObservable.invoke(it)));
        Intrinsics.checkNotNullExpressionValue((Object)flatMap, "flatMap {\n        if (pr\u2026vable(it)\n        }\n    }");
        return flatMap;
    }
    
    @NotNull
    public static final <T> Observable<Object> flatMapIf(@NotNull final Observable<T> $this$flatMapIf, @NotNull final Function1<? super T, Boolean> predicate, @NotNull final Function1<? super T, ? extends Observable<?>> nextObservable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapIf, "<this>");
        Intrinsics.checkNotNullParameter((Object)predicate, "predicate");
        Intrinsics.checkNotNullParameter((Object)nextObservable, "nextObservable");
        final Observable<Object> flatMap = $this$flatMapIf.flatMap(it ->
                Boolean.TRUE.equals(predicate.invoke(it))
                        ? (ObservableSource<Object>) nextObservable.invoke(it)
                        : Observable.just((Object) it));
        Intrinsics.checkNotNullExpressionValue((Object)flatMap, "flatMap {\n        if (pr\u2026.just(it)\n        }\n    }");
        return flatMap;
    }
    
    public static final Observable<Object> continueOnFail(@NotNull final Observable<Object> $this$continueOnFail) {
        Intrinsics.checkNotNullParameter((Object)$this$continueOnFail, "<this>");
        return $this$continueOnFail.onErrorReturn(error -> Boolean.FALSE);
    }
    
    public static final <T> Observable<T> onErrorReturnItemWithLog(@NotNull final Observable<T> $this$onErrorReturnItemWithLog, @NotNull final T t) {
        Intrinsics.checkNotNullParameter((Object)$this$onErrorReturnItemWithLog, "<this>");
        Intrinsics.checkNotNullParameter((Object)t, "t");
        return $this$onErrorReturnItemWithLog.doOnError(error ->
                Debug.e($this$onErrorReturnItemWithLog.getClass(), error)).onErrorReturnItem(t);
    }
    
    @NotNull
    public static final <T> Observable<Object> concatMapIfLast(@NotNull final BehaviorSubject<T> $this$concatMapIfLast, @NotNull final Function1<? super T, ? extends Observable<?>> observable) {
        Intrinsics.checkNotNullParameter((Object)$this$concatMapIfLast, "<this>");
        Intrinsics.checkNotNullParameter((Object)observable, "observable");
        return concatMapIf($this$concatMapIfLast, new RxjavaKt$a<T>($this$concatMapIfLast), observable);
    }
    
    @NotNull
    public static final <T> Observable<Object> concatMapIf(@NotNull final Observable<T> $this$concatMapIf, @NotNull final Function1<? super T, Boolean> predicate, @NotNull final Function1<? super T, ? extends Observable<?>> observable) {
        Intrinsics.checkNotNullParameter((Object)$this$concatMapIf, "<this>");
        Intrinsics.checkNotNullParameter((Object)predicate, "predicate");
        Intrinsics.checkNotNullParameter((Object)observable, "observable");
        final Observable<Object> concatMap = $this$concatMapIf.concatMap(it ->
                Boolean.TRUE.equals(predicate.invoke(it))
                        ? (ObservableSource<Object>) observable.invoke(it)
                        : Observable.just((Object) it));
        Intrinsics.checkNotNullExpressionValue((Object)concatMap, "concatMap {\n        if (\u2026.just(it)\n        }\n    }");
        return concatMap;
    }
    
    @NotNull
    public static final <T> Observable<Boolean> flatMapFilter(@NotNull final Observable<T> $this$flatMapFilter, @NotNull final Function1<? super T, ? extends Observable<Boolean>> observable) {
        Intrinsics.checkNotNullParameter((Object)$this$flatMapFilter, "<this>");
        Intrinsics.checkNotNullParameter((Object)observable, "observable");
        final Observable<Boolean> filter = $this$flatMapFilter
                .flatMap(it -> observable.invoke(it))
                .filter(Boolean::booleanValue);
        Intrinsics.checkNotNullExpressionValue((Object)filter, "flatMap { observable(it) }\n        .filter { it }");
        return filter;
    }
    
    @NotNull
    public static final <T> Observable<List<T>> collectAndFlatten(@NotNull final Observable<List<T>> $this$collectAndFlatten) {
        Intrinsics.checkNotNullParameter((Object)$this$collectAndFlatten, "<this>");
        final Observable<List<T>> observable = $this$collectAndFlatten.toList()
                .map(items -> (List<T>) CollectionsKt.flatten(items)).toObservable();
        Intrinsics.checkNotNullExpressionValue((Object)observable, "this\n        .toList()\n \u2026}\n        .toObservable()");
        return observable;
    }
    
    private static final void a(final BehaviorSubject $subject, final View $this_postDelayObservable) {
        Intrinsics.checkNotNullParameter((Object)$subject, "$subject");
        Intrinsics.checkNotNullParameter((Object)$this_postDelayObservable, "$this_postDelayObservable");
        done((io.reactivex.Observer<View>)$subject, $this_postDelayObservable);
    }
    
    private static final void a(final Function1 $predicate, final Consumer $onNext, final Object it) throws Exception {
        Intrinsics.checkNotNullParameter((Object)$predicate, "$predicate");
        Intrinsics.checkNotNullParameter((Object)$onNext, "$onNext");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        if (Boolean.TRUE.equals($predicate.invoke(it))) {
            $onNext.accept(it);
        }
    }
    
    private static final ObservableSource a(final Function1 $predicate, final Function1 $trueObservable, final Function1 $falseObservable, final Object it) {
        Intrinsics.checkNotNullParameter((Object)$predicate, "$predicate");
        Intrinsics.checkNotNullParameter((Object)$trueObservable, "$trueObservable");
        Intrinsics.checkNotNullParameter((Object)$falseObservable, "$falseObservable");
        Intrinsics.checkNotNullParameter(it, "it");
        return (ObservableSource)(Boolean.TRUE.equals($predicate.invoke(it)) ? $trueObservable.invoke(it) : $falseObservable.invoke(it));
    }
    
    private static final ObservableSource b(final Function1 $predicate, final Function1 $nextObservable, final Object it) {
        Intrinsics.checkNotNullParameter((Object)$predicate, "$predicate");
        Intrinsics.checkNotNullParameter((Object)$nextObservable, "$nextObservable");
        Intrinsics.checkNotNullParameter(it, "it");
        return (ObservableSource)(Boolean.TRUE.equals($predicate.invoke(it)) ? $nextObservable.invoke(it) : Observable.just(it));
    }
    
    private static final Object a(final Throwable it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return Boolean.FALSE;
    }
    
    private static final void a(final Observable $this_onErrorReturnItemWithLog, final Throwable it) {
        Intrinsics.checkNotNullParameter((Object)$this_onErrorReturnItemWithLog, "$this_onErrorReturnItemWithLog");
        Debug.e((Class)$this_onErrorReturnItemWithLog.getClass(), it);
    }
    
    private static final ObservableSource a(final Function1 $predicate, final Function1 $observable, final Object it) {
        Intrinsics.checkNotNullParameter((Object)$predicate, "$predicate");
        Intrinsics.checkNotNullParameter((Object)$observable, "$observable");
        Intrinsics.checkNotNullParameter(it, "it");
        return (ObservableSource)(Boolean.TRUE.equals($predicate.invoke(it)) ? $observable.invoke(it) : Observable.just(it));
    }
    
    private static final ObservableSource a(final Function1 $observable, final Object it) {
        Intrinsics.checkNotNullParameter((Object)$observable, "$observable");
        Intrinsics.checkNotNullParameter(it, "it");
        return (ObservableSource)$observable.invoke(it);
    }
    
    private static final boolean a(final Boolean it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return it;
    }
    
    private static final List a(final List it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return CollectionsKt.flatten((Iterable)it);
    }
}
