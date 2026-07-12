package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/i.class */
public final /* synthetic */ class i implements Runnable {
    public final SFTouchRender.b a;
    public final TouchPoint b;

    public /* synthetic */ i(SFTouchRender.b bVar, TouchPoint touchPoint) {
        this.a = bVar;
        this.b = touchPoint;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onPenActive(this.b);
    }
}
