package com.onyx.android.sdk.pen.touch;

import android.graphics.RectF;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/g.class */
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
