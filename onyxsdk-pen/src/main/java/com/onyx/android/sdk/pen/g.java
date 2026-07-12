package com.onyx.android.sdk.pen;

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
