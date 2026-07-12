package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
public final /* synthetic */ class m implements Runnable {
    public final SFTouchRender.b a;
    public final TouchPoint b;

    public /* synthetic */ m(SFTouchRender.b bVar, TouchPoint touchPoint) {
        this.a = bVar;
        this.b = touchPoint;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onRawDrawingTouchPointMoveReceived(this.b);
    }
}
