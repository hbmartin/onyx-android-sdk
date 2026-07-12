// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.common.request;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import android.util.Log;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ExecutorContext
{
    private static final int e = 1;
    private static final int f = 1;
    private static final int g = 3;
    private static final int h = 3;
    private static final long i = 5L;
    private ExecutorService a;
    private ExecutorService b;
    private List<BaseRequest> c;
    private int d;
    
    public ExecutorContext() {
        this.a = null;
        this.b = null;
        this.d = 10;
        this.a();
    }
    
    public ExecutorContext(final int priority) {
        this.a = null;
        this.b = null;
        this.d = 10;
        this.d = priority;
        this.a();
    }
    
    private void a() {
        this.c = Collections.synchronizedList(new ArrayList<BaseRequest>());
    }
    
    public void removeRequest(final BaseRequest request) {
        final List<BaseRequest> c = this.c;
        synchronized (c) {
            this.c.remove(request);
        }
    }
    
    public void addRequest(final BaseRequest request) {
        final List<BaseRequest> c = this.c;
        synchronized (c) {
            this.c.add(request);
        }
    }
    
    public void abortAllRequests() {
        synchronized (this.c) {
            final Iterator<BaseRequest> iterator = this.c.iterator();
            while (iterator.hasNext()) {
                iterator.next().setAbort();
            }
        }
    }
    
    public void dump() {
        Log.e(ExecutorContext.class.getSimpleName(), "Request size list:" + this.c.size());
    }
    
    public boolean hasPendingRequests() {
        synchronized (this.c) {
            return !this.c.isEmpty();
        }
    }
    
    public boolean isRequestQueueEmpty() {
        synchronized (this.c) {
            return this.c.isEmpty();
        }
    }
    
    public ExecutorService getSingleThreadPool() {
        if (this.a == null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(final Runnable r) {
                    final Thread thread = new Thread(r);
                    thread.setPriority(ExecutorContext.this.d);
                    return thread;
                }
            };
            final ThreadPoolExecutor a = new ThreadPoolExecutor(1, 1, 5L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
            a.allowCoreThreadTimeOut(true);
            this.a = a;
        }
        return this.a;
    }
    
    public void submitToSingleThreadPool(final Runnable runnable) {
        this.getSingleThreadPool().submit(runnable);
    }
    
    public void submitToMultiThreadPool(final Runnable runnable) {
        this.getMultiThreadPool().submit(runnable);
    }
    
    public ExecutorService getMultiThreadPool() {
        if (this.b == null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(final Runnable r) {
                    final Thread thread = new Thread(r);
                    thread.setPriority(ExecutorContext.this.d);
                    return thread;
                }
            };
            final ThreadPoolExecutor b = new ThreadPoolExecutor(3, 3, 5L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
            b.allowCoreThreadTimeOut(true);
            this.b = b;
        }
        return this.b;
    }
    
    public int getRequestQueueSize() {
        synchronized (this.c) {
            return this.c.size();
        }
    }
}
