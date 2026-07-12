// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import io.reactivex.internal.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import io.reactivex.Observable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001\u001aF\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00050\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a>\u0010\f\u001a&\u0012\f\u0012\n \r*\u0004\u0018\u0001H\u0005H\u0005 \r*\u0012\u0012\f\u0012\n \r*\u0004\u0018\u0001H\u0005H\u0005\u0018\u00010\u00070\u0007\"\b\b\u0000\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00050\u0007¨\u0006\u000e" }, d2 = { "defaultOnError", "Lio/reactivex/functions/Consumer;", "", "subscribeWithDefault", "Lio/reactivex/disposables/Disposable;", "T", "", "Lio/reactivex/Observable;", "onNext", "onError", "onComplete", "Lio/reactivex/functions/Action;", "takeLastOne", "kotlin.jvm.PlatformType", "onyxsdk-base_release" })
public final class ObservableKt
{
    private ObservableKt() {
    }

    @NotNull
    public static final <T> Disposable subscribeWithDefault(@NotNull final Observable<T> $this$subscribeWithDefault, @NotNull final Consumer<T> onNext, @NotNull final Consumer<? super Throwable> onError, @NotNull final Action onComplete) {
        Intrinsics.checkNotNullParameter((Object)$this$subscribeWithDefault, "<this>");
        Intrinsics.checkNotNullParameter((Object)onNext, "onNext");
        Intrinsics.checkNotNullParameter((Object)onError, "onError");
        Intrinsics.checkNotNullParameter((Object)onComplete, "onComplete");
        final Disposable subscribe = $this$subscribeWithDefault.subscribe((Consumer)onNext, (Consumer)onError, onComplete);
        Intrinsics.checkNotNullExpressionValue((Object)subscribe, "subscribe(onNext, onError, onComplete)");
        return subscribe;
    }

    public static /* synthetic */ Disposable subscribeWithDefault$default(final Observable observable, Consumer onNext, Consumer onError, Action onComplete, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            onNext = Functions.emptyConsumer();
            Intrinsics.checkNotNullExpressionValue((Object)onNext, "emptyConsumer<T>()");
        }
        if ((n & 0x2) != 0x0) {
            onError = defaultOnError();
        }
        if ((n & 0x4) != 0x0) {
            onComplete = Functions.EMPTY_ACTION;
            Intrinsics.checkNotNullExpressionValue((Object)onComplete, "EMPTY_ACTION");
        }
        return subscribeWithDefault(observable, onNext, onError, onComplete);
    }

    @NotNull
    public static final Consumer<? super Throwable> defaultOnError() {
        return (Consumer<? super Throwable>)ObservableKt::a;
    }
    
    public static final <T> Observable<T> takeLastOne(@NotNull final Observable<T> $this$takeLastOne) {
        Intrinsics.checkNotNullParameter((Object)$this$takeLastOne, "<this>");
        return (Observable<T>)$this$takeLastOne.takeLast(1);
    }
    
    private static final void a(final Throwable t) {
        t.printStackTrace();
    }
}
