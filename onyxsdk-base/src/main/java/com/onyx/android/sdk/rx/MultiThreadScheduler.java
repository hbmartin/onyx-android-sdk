// 
// 

package com.onyx.android.sdk.rx;

import java.util.concurrent.Executor;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\tH\u0007J&\u0010\n\u001a\u00020\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ\b\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010" }, d2 = { "Lcom/onyx/android/sdk/rx/MultiThreadScheduler;", "", "()V", "CORE_POOL_SIZE", "", "DEFAULT_KEEP_ALIVE_TIME_IN_SECOND", "", "MAXIMUM_POOL_SIZE", "scheduler", "Lio/reactivex/Scheduler;", "newScheduler", "name", "", "keepAliveTime", "unit", "Ljava/util/concurrent/TimeUnit;", "onyxsdk-base_release" })
public final class MultiThreadScheduler
{
    @NotNull
    public static final MultiThreadScheduler INSTANCE;
    private static final int a = 5;
    private static final int b = 5;
    private static final long c = 5L;
    @Nullable
    private static Scheduler d;
    
    private MultiThreadScheduler() {
    }
    
    @JvmStatic
    @NotNull
    public static final Scheduler scheduler() {
        if (MultiThreadScheduler.d == null) {
            MultiThreadScheduler.d = newScheduler();
        }
        final Scheduler d = MultiThreadScheduler.d;
        Intrinsics.checkNotNull((Object)d);
        return d;
    }
    
    @JvmStatic
    @NotNull
    public static final Scheduler newScheduler() {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        final Scheduler from = Schedulers.from((Executor)threadPoolExecutor);
        Intrinsics.checkNotNullExpressionValue((Object)from, "from(pool)");
        return from;
    }
    
    static {
        INSTANCE = new MultiThreadScheduler();
    }
    
    @NotNull
    public final Scheduler newScheduler(@Nullable final String name, final long keepAliveTime, @NotNull final TimeUnit unit) {
        Intrinsics.checkNotNullParameter((Object)unit, "unit");
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), SingleThreadScheduler.newThreadFactory(name));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        final Scheduler from = Schedulers.from((Executor)threadPoolExecutor);
        Intrinsics.checkNotNullExpressionValue((Object)from, "from(pool)");
        return from;
    }

    public static /* synthetic */ Scheduler newScheduler$default(final MultiThreadScheduler multiThreadScheduler, String name, long keepAliveTime, TimeUnit unit, final int i, final Object obj) {
        if ((i & 1) != 0) {
            name = null;
        }
        if ((i & 2) != 0) {
            keepAliveTime = 5L;
        }
        if ((i & 4) != 0) {
            unit = TimeUnit.SECONDS;
        }
        return multiThreadScheduler.newScheduler(name, keepAliveTime, unit);
    }
}
