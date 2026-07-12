package com.onyx.android.sdk.common.request;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/common/request/RequestManager.class */
public class RequestManager {
    private static final String f = "RequestManager";
    private ExecutorContext c;
    private volatile WakeLockHolder a = new WakeLockHolder();
    private boolean b = false;
    private ConcurrentHashMap<String, ExecutorContext> d = new ConcurrentHashMap<>();
    private Handler e = new Handler(Looper.getMainLooper());

    public RequestManager() {
        a(5);
    }

    private void a(int priority) {
        this.c = new ExecutorContext(priority);
    }

    private boolean b(ExecutorContext executorContext, BaseRequest request, Runnable runnable) {
        if (request.isRunInBackground()) {
            executorContext.submitToSingleThreadPool(runnable);
            return true;
        }
        runnable.run();
        return true;
    }

    public void acquireWakeLock(Context context, String tag) {
        this.a.acquireWakeLock(context, tag);
    }

    public void releaseWakeLock() {
        this.a.releaseWakeLock();
    }

    public void dumpWakelocks() {
        if (this.b) {
            this.a.dumpWakelocks(f);
        }
    }

    public void removeRequest(BaseRequest request) {
        removeRequest(request, request.getIdentifier());
    }

    public void dumpRequestList() {
        Log.e(f, "Dump built-in executor");
        this.c.dump();
        for (Map.Entry<String, ExecutorContext> entry : this.d.entrySet()) {
            Log.e(f, "Executor name:  " + entry.getKey());
            entry.getValue().dump();
        }
    }

    public Handler getLooperHandler() {
        return this.e;
    }

    public boolean submitRequest(Context context, BaseRequest request, Runnable runnable, BaseCallback callback) {
        return submitRequest(context, request.getIdentifier(), request, runnable, callback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.onyx.android.sdk.common.request.ExecutorContext, java.lang.Object] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public final ExecutorContext getExecutorByIdentifier(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return this.c;
        }
        return this.d.computeIfAbsent(str, ignored -> new ExecutorContext());
    }

    public boolean submitRequestToMultiThreadPool(Context context, BaseRequest request, Runnable runnable, BaseCallback callback) {
        return submitRequestToMultiThreadPool(context, request.getIdentifier(), request, runnable, callback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAllQueueEmpty() {
        if (!a().isRequestQueueEmpty()) {
            return false;
        }
        synchronized (this.d) {
            if (CollectionUtils.isNullOrEmpty(this.d)) {
                return true;
            }
            Iterator<ExecutorContext> it = this.d.values().iterator();
            while (it.hasNext()) {
                if (!it.next().isRequestQueueEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }

    private final ExecutorContext a() {
        return this.c;
    }

    public void removeRequest(BaseRequest request, String identifier) {
        getExecutorByIdentifier(identifier).removeRequest(request);
    }

    public boolean submitRequest(Context context, String identifier, BaseRequest request, Runnable runnable, BaseCallback callback) {
        ExecutorContext executorByIdentifier = getExecutorByIdentifier(identifier);
        if (a(context, executorByIdentifier, request, callback)) {
            return b(executorByIdentifier, request, runnable);
        }
        return false;
    }

    public boolean submitRequestToMultiThreadPool(Context context, String identifier, BaseRequest request, Runnable runnable, BaseCallback callback) {
        ExecutorContext executorByIdentifier = getExecutorByIdentifier(identifier);
        if (a(context, executorByIdentifier, request, callback)) {
            return a(executorByIdentifier, request, runnable);
        }
        return false;
    }

    private boolean a(Context context, ExecutorContext executorContext, BaseRequest request, BaseCallback callback) {
        if (request == null) {
            BaseCallback.invoke(callback, null, null);
            return false;
        }
        request.setContext(context);
        request.setCallback(callback);
        if (request.isAbortPendingTasks()) {
            executorContext.abortAllRequests();
        }
        executorContext.addRequest(request);
        return true;
    }

    public RequestManager(int priority) {
        a(priority);
    }

    private boolean a(ExecutorContext executorContext, BaseRequest request, Runnable runnable) {
        if (request.isRunInBackground()) {
            executorContext.submitToMultiThreadPool(runnable);
            return true;
        }
        runnable.run();
        return true;
    }
}
