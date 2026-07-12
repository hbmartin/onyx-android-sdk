package com.onyx.android.sdk.pen.touch;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/d.class */
public final /* synthetic */ class d implements ObservableOnSubscribe {
    public final AppTouchRender a;

    public d(AppTouchRender render) {
        this.a = render;
    }

    public final void subscribe(ObservableEmitter observableEmitter) {
        this.a.m(observableEmitter);
    }
}
