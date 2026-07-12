package com.onyx.android.sdk.common.request;

import android.content.Context;
import com.onyx.android.sdk.utils.Benchmark;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/common/request/BaseRequest.class */
public class BaseRequest {
    private static volatile int j;
    private static boolean k = true;
    private volatile boolean b;
    private volatile boolean c;
    private boolean e;
    private BaseCallback f;
    private Context g;
    private Benchmark h;
    private Throwable i;
    private boolean d = true;
    private int a = generateRequestSequence();

    public static int generateRequestSequence() {
        j++;
        return j;
    }

    public BaseRequest() {
        this.b = false;
        this.c = false;
        this.e = true;
        this.b = false;
        this.c = false;
        this.e = true;
    }

    public static boolean isEnableBenchmarkDebug() {
        return k;
    }

    public void setContext(Context c) {
        this.g = c;
    }

    public void setCallback(BaseCallback c) {
        this.f = c;
    }

    public final BaseCallback getCallback() {
        return this.f;
    }

    public int getRequestSequence() {
        return this.a;
    }

    public final Context getContext() {
        return this.g;
    }

    public void setAbort() {
        this.b = true;
    }

    public boolean isAbort() {
        return this.b;
    }

    public void setAbortPendingTasks(boolean abort) {
        this.c = abort;
    }

    public boolean isAbortPendingTasks() {
        return this.c;
    }

    public void setUseWakeLock(boolean use) {
        this.d = use;
    }

    public boolean isUseWakeLock() {
        return this.d;
    }

    public void benchmarkStart() {
        if (isEnableBenchmarkDebug()) {
            this.h = new Benchmark();
        }
    }

    public long benchmarkEnd() {
        Benchmark benchmark;
        if (!isEnableBenchmarkDebug() || (benchmark = this.h) == null) {
            return 0L;
        }
        return benchmark.duration();
    }

    public void setRunInBackground(boolean b) {
        this.e = b;
    }

    public boolean isRunInBackground() {
        return this.e;
    }

    public void setException(Throwable e) {
        this.i = e;
    }

    public Throwable getException() {
        return this.i;
    }

    public boolean hasException() {
        return this.i != null;
    }

    public String getIdentifier() {
        return null;
    }
}
