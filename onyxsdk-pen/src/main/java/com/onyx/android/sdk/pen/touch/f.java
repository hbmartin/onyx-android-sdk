package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.pen.data.TouchPointList;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/f.class */
public final /* synthetic */ class f implements Runnable {
    public final SFTouchRender.b a;
    public final TouchPointList b;

    public /* synthetic */ f(SFTouchRender.b bVar, TouchPointList touchPointList) {
        this.a = bVar;
        this.b = touchPointList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onRawErasingTouchPointListReceived(this.b);
    }
}
