package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/l.class */
/*
 * Orphaned synthetic bridge recovered from the reference JAR. Nothing in
 * the recovered sources instantiates it: the compiler-generated call site
 * was a synthetic lambda bridge that decompilation cannot recover, so its
 * pairing with this exact callback is conjecture. Kept verbatim for
 * class-surface completeness.
 */
public final /* synthetic */ class l implements Runnable {
    public final SFTouchRender.b a;
    public final boolean b;
    public final TouchPoint c;

    public /* synthetic */ l(SFTouchRender.b bVar, boolean z, TouchPoint touchPoint) {
        this.a = bVar;
        this.b = z;
        this.c = touchPoint;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onEndRawDrawing(this.b, this.c);
    }
}
