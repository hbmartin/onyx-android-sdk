package com.onyx.android.sdk.extension;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

final class RxjavaKt$a<T> implements Function1<T, Boolean> {
    final BehaviorSubject<T> a;

    RxjavaKt$a(BehaviorSubject<T> subject) {
        this.a = subject;
    }

    public final Boolean a(T value) {
        return Intrinsics.areEqual(value, a.getValue());
    }

    @Override
    public Boolean invoke(T value) {
        return a(value);
    }
}

final class RxjavaKt$b implements Function1<Boolean, Boolean> {
    public static final RxjavaKt$b a = new RxjavaKt$b();
    private RxjavaKt$b() {}
    public final Boolean a(boolean value) { return !value; }
    @Override public Boolean invoke(Boolean value) { return a(value); }
}

final class RxjavaKt$c implements Function1<Boolean, Boolean> {
    public static final RxjavaKt$c a = new RxjavaKt$c();
    private RxjavaKt$c() {}
    public final Boolean a(boolean value) { return value; }
    @Override public Boolean invoke(Boolean value) { return a(value); }
}

final class RxjavaKt$d<T> implements Function1<T, Observable<? extends Object>> {
    final Observable<? extends Object> a;
    RxjavaKt$d(Observable<?> observable) { this.a = observable; }
    public final Observable<? extends Object> a(T value) {
        Intrinsics.checkNotNullParameter(value, "it");
        return a;
    }
    @Override public Observable<? extends Object> invoke(T value) { return a(value); }
}

final class RxjavaKt$e implements Function1<Boolean, Boolean> {
    public static final RxjavaKt$e a = new RxjavaKt$e();
    private RxjavaKt$e() {}
    public final Boolean a(boolean value) { return !value; }
    @Override public Boolean invoke(Boolean value) { return a(value); }
}

final class RxjavaKt$f implements Function1<Boolean, Observable<? extends Object>> {
    final Observable<? extends Object> a;
    RxjavaKt$f(Observable<?> observable) { this.a = observable; }
    public final Observable<? extends Object> a(boolean value) { return a; }
    @Override public Observable<? extends Object> invoke(Boolean value) { return a(value); }
}

final class RxjavaKt$g implements Function1<Boolean, Boolean> {
    public static final RxjavaKt$g a = new RxjavaKt$g();
    private RxjavaKt$g() {}
    public final Boolean a(boolean value) { return value; }
    @Override public Boolean invoke(Boolean value) { return a(value); }
}
