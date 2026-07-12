package com.onyx.android.sdk.common.request;

import android.os.Handler;

public abstract class BaseCallback {

    public static class ProgressInfo {
        public long soFarBytes;
        public long totalBytes;
        public double progress;

        public static ProgressInfo onComplete() {
            ProgressInfo progressInfo = new ProgressInfo();
            progressInfo.soFarBytes = 100L;
            progressInfo.totalBytes = 100L;
            return progressInfo;
        }

        public String toString() {
            return "ProgressInfo{soFarBytes=" + this.soFarBytes + ", totalBytes=" + this.totalBytes + ", progress=" + this.progress + '}';
        }

        public boolean isComplete() {
            return this.progress >= 100.0d;
        }
    }

    class a implements Runnable {
        final /* synthetic */ BaseRequest b;
        final /* synthetic */ ProgressInfo c;

        a(BaseRequest baseRequest, ProgressInfo progressInfo) {
            this.b = baseRequest;
            this.c = progressInfo;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseCallback.this.progress(this.b, this.c);
        }
    }

    public static void invokeProgress(BaseCallback callback, BaseRequest request, ProgressInfo progressInfo) {
        if (callback != null) {
            callback.progress(request, progressInfo);
        }
    }

    public static void invokeStart(BaseCallback callback, BaseRequest request) {
        if (callback != null) {
            callback.start(request);
        }
    }

    public static void invoke(BaseCallback callback, BaseRequest request, Throwable e) {
        if (callback != null) {
            callback.done(request, e);
        }
    }

    public static void invokeBeforeDone(BaseCallback callback, BaseRequest request, Throwable e) {
        if (callback != null) {
            callback.beforeDone(request, e);
        }
    }

    public void start(BaseRequest request) {
    }

    public void progress(BaseRequest request, ProgressInfo info) {
    }

    public void beforeDone(BaseRequest request, Throwable e) {
    }

    public abstract void done(BaseRequest baseRequest, Throwable th);

    public static void invokeProgress(Handler handler, BaseCallback callback, BaseRequest request, ProgressInfo progressInfo) {
        if (callback == null || handler == null) {
            return;
        }
        handler.post(callback.new a(request, progressInfo));
    }
}
