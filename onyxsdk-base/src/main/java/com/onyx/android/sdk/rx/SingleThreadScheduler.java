package com.onyx.android.sdk.rx;

import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleThreadScheduler {
    private static final int a = 1;
    private static final int b = 1;
    public static final long DEFAULT_KEEP_ALIVE_TIME_IN_SECOND = 5;
    public static final long LONG_KEEP_ALIVE_TIME_IN_SECOND = TimeUnit.MINUTES.toSeconds(10);
    private static Scheduler c;

    static class a implements ThreadFactory {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            if (StringUtils.isNotBlank(this.a)) {
                thread.setName(this.a);
            }
            thread.setPriority(10);
            return thread;
        }
    }

    public static Scheduler scheduler() {
        if (c == null) {
            c = newScheduler();
        }
        return c;
    }

    public static Scheduler newScheduler() {
        return newScheduler(TTFFont.UNKNOWN_FONT_NAME);
    }

    public static ExecutorService newSingleThreadExecutor() {
        return newSingleThreadExecutor(TTFFont.UNKNOWN_FONT_NAME);
    }

    @NonNull
    public static ThreadFactory newThreadFactory(String name) {
        return new a(name);
    }

    public static Scheduler newScheduler(String name) {
        return Schedulers.from(newSingleThreadExecutor(name));
    }

    @NonNull
    public static ExecutorService newSingleThreadExecutor(String name) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), newThreadFactory(name));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    @NonNull
    public static ExecutorService newSingleThreadExecutor(String name, long keepAliveTime) {
        return new ThreadPoolExecutor(1, 1, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(), newThreadFactory(name));
    }
}
