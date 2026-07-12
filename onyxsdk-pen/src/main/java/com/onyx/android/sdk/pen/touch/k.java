package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/k.class */
/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * was a synthetic lambda bridge that decompilation cannot recover, so its
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
public final /* synthetic */ class k implements Runnable {
    public final SFTouchRender.b a;
    public final TouchPoint b;

    public /* synthetic */ k(SFTouchRender.b bVar, TouchPoint touchPoint) {
        this.a = bVar;
        this.b = touchPoint;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onRawErasingTouchPointMoveReceived(this.b);
    }
}
