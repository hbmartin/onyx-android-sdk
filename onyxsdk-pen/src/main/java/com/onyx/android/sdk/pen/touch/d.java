package com.onyx.android.sdk.pen.touch;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/d.class */
/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * was a synthetic lambda bridge that decompilation cannot recover, so its
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
public final /* synthetic */ class d implements ObservableOnSubscribe {
    public final AppTouchRender a;

    public d(AppTouchRender render) {
        this.a = render;
    }

    public final void subscribe(ObservableEmitter observableEmitter) {
        this.a.m(observableEmitter);
    }
}
