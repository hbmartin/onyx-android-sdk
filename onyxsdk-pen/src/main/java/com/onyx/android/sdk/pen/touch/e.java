package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;

public final /* synthetic */ class e implements Runnable {
    public final SFTouchRender.b a;
    public final boolean b;
    public final TouchPoint c;

    public /* synthetic */ e(SFTouchRender.b bVar, boolean z, TouchPoint touchPoint) {
        this.a = bVar;
        this.b = z;
        this.c = touchPoint;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.onBeginRawDrawing(this.b, this.c);
    }
}
