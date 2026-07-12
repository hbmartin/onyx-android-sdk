package com.onyx.android.sdk.pen.touch;

import android.graphics.RectF;

/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
public final /* synthetic */ class g implements Runnable {
    public final SFTouchRender.b a;
    public final RectF b;

    public /* synthetic */ g(SFTouchRender.b bVar, RectF rectF) {
        this.a = bVar;
        this.b = rectF;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onPenUpRefresh(this.b);
    }
}
