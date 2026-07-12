package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.pen.data.TouchPointList;

/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
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
