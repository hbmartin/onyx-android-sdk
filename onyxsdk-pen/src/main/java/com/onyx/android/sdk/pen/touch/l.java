package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/l.class */
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
