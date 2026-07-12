package com.onyx.android.sdk.extension;

import java.util.concurrent.Callable;

public final class RxjavaKt$run$1 implements Callable<Object> {
    final Runnable a;

    RxjavaKt$run$1(Runnable runnable) {
        this.a = runnable;
    }

    @Override
    public Object call() {
        a.run();
        return this;
    }
}
