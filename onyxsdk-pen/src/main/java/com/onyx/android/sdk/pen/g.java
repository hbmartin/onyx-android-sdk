package com.onyx.android.sdk.pen;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/g.class */
public final /* synthetic */ class g implements Runnable {
    public final RawInputReader a;

    public g(RawInputReader reader) {
        this.a = reader;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.B();
    }
}
