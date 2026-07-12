// 
// 

package com.onyx.android.sdk.rx;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executor;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.JvmOverloads;
import org.jetbrains.annotations.Nullable;
import kotlin.jvm.JvmStatic;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u000e\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\u0012" }, d2 = { "Lcom/onyx/android/sdk/rx/RxScheduler;", "", "()V", "observeOn", "Lio/reactivex/Scheduler;", "getObserveOn", "()Lio/reactivex/Scheduler;", "setObserveOn", "(Lio/reactivex/Scheduler;)V", "subscribeOn", "getSubscribeOn", "setSubscribeOn", "isWorkThread", "", "scheduler", "shutdown", "", "Companion", "onyxsdk-base_release" })
public final class RxScheduler
{
    @NotNull
    public static final Companion Companion;
    public Scheduler subscribeOn;
    public Scheduler observeOn;
    
    private final boolean a(final Scheduler scheduler) {
        return scheduler != AndroidSchedulers.mainThread();
    }
    
    @JvmStatic
    @NotNull
    public static final RxScheduler sharedSingleThreadManager() {
        return RxScheduler.Companion.sharedSingleThreadManager();
    }
    
    @JvmStatic
    @NotNull
    public static final RxScheduler sharedMultiThreadManager() {
        return RxScheduler.Companion.sharedMultiThreadManager();
    }
    
    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final RxScheduler newSingleThreadManager(@Nullable final String name) {
        return RxScheduler.Companion.newSingleThreadManager(name);
    }
    
    @JvmStatic
    @NotNull
    public static final RxScheduler newMultiThreadManager() {
        return RxScheduler.Companion.newMultiThreadManager();
    }
    
    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final RxScheduler newSingleThreadManager() {
        return RxScheduler.Companion.newSingleThreadManager();
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final Scheduler getSubscribeOn() {
        final Scheduler subscribeOn;
        if ((subscribeOn = this.subscribeOn) != null) {
            return subscribeOn;
        }
        Intrinsics.throwUninitializedPropertyAccessException("subscribeOn");
        return null;
    }
    
    public final void setSubscribeOn(@NotNull final Scheduler value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.subscribeOn = value;
    }
    
    @NotNull
    public final Scheduler getObserveOn() {
        final Scheduler observeOn;
        if ((observeOn = this.observeOn) != null) {
            return observeOn;
        }
        Intrinsics.throwUninitializedPropertyAccessException("observeOn");
        return null;
    }
    
    public final void setObserveOn(@NotNull final Scheduler value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.observeOn = value;
    }
    
    @NotNull
    public final RxScheduler subscribeOn(@NotNull final Scheduler subscribeOn) {
        Intrinsics.checkNotNullParameter((Object)subscribeOn, "subscribeOn");
        this.setSubscribeOn(subscribeOn);
        return this;
    }
    
    @NotNull
    public final RxScheduler observeOn(@NotNull final Scheduler observeOn) {
        Intrinsics.checkNotNullParameter((Object)observeOn, "observeOn");
        this.setObserveOn(observeOn);
        return this;
    }
    
    public final void shutdown() {
        if (this.getObserveOn() != null && this.a(this.getObserveOn())) {
            this.getObserveOn().shutdown();
        }
        if (this.getSubscribeOn() != null && this.a(this.getSubscribeOn())) {
            this.getSubscribeOn().shutdown();
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\b\u0010\u0007\u001a\u00020\u0004H\u0007J\u001c\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bJ\u0014\u0010\f\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0007J\u0018\u0010\f\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\r\u001a\u00020\u0004H\u0007J\b\u0010\u000e\u001a\u00020\u0004H\u0007¨\u0006\u000f" }, d2 = { "Lcom/onyx/android/sdk/rx/RxScheduler$Companion;", "", "()V", "createScheduler", "Lcom/onyx/android/sdk/rx/RxScheduler;", "scheduler", "Lio/reactivex/Scheduler;", "newMultiThreadManager", "name", "", "keepAliveTime", "", "newSingleThreadManager", "sharedMultiThreadManager", "sharedSingleThreadManager", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        public static /* synthetic */ RxScheduler newSingleThreadManager$default(final Companion companion, String name, final int n, final Object o) {
            if ((n & 0x1) != 0x0) {
                name = "";
            }
            return companion.newSingleThreadManager(name);
        }
        
        @JvmStatic
        @NotNull
        public final RxScheduler sharedSingleThreadManager() {
            final Scheduler scheduler = SingleThreadScheduler.scheduler();
            Intrinsics.checkNotNullExpressionValue((Object)scheduler, "scheduler()");
            return this.createScheduler(scheduler);
        }
        
        @JvmStatic
        @NotNull
        public final RxScheduler sharedMultiThreadManager() {
            return this.createScheduler(MultiThreadScheduler.scheduler());
        }
        
        @JvmStatic
        @JvmOverloads
        @NotNull
        public final RxScheduler newSingleThreadManager(@Nullable final String name) {
            final Scheduler from = Schedulers.from((Executor)SingleThreadScheduler.newSingleThreadExecutor(name));
            Intrinsics.checkNotNullExpressionValue((Object)from, "from(\n                  \u2026      )\n                )");
            return this.createScheduler(from);
        }
        
        @NotNull
        public final RxScheduler newSingleThreadManager(@Nullable final String name, final long keepAliveTime) {
            final Scheduler from = Schedulers.from((Executor)SingleThreadScheduler.newSingleThreadExecutor(name, keepAliveTime));
            Intrinsics.checkNotNullExpressionValue((Object)from, "from(\n                  \u2026      )\n                )");
            return this.createScheduler(from);
        }
        
        @JvmStatic
        @NotNull
        public final RxScheduler newMultiThreadManager() {
            return this.createScheduler(MultiThreadScheduler.newScheduler());
        }
        
        @NotNull
        public final RxScheduler createScheduler(@NotNull final Scheduler scheduler) {
            Intrinsics.checkNotNullParameter((Object)scheduler, "scheduler");
            return new RxScheduler().subscribeOn(scheduler).observeOn(scheduler);
        }
        
        @NotNull
        public final RxScheduler newMultiThreadManager(@Nullable final String name, final long keepAliveTime) {
            return this.createScheduler(MultiThreadScheduler.INSTANCE.newScheduler(name, keepAliveTime, TimeUnit.SECONDS));
        }

        public static /* synthetic */ RxScheduler newMultiThreadManager$default(final Companion companion, String name, long keepAliveTime, final int n, final Object o) {
            if ((n & 0x1) != 0x0) {
                name = null;
            }
            if ((n & 0x2) != 0x0) {
                keepAliveTime = 5L;
            }
            return companion.newMultiThreadManager(name, keepAliveTime);
        }
        
        @JvmStatic
        @JvmOverloads
        @NotNull
        public final RxScheduler newSingleThreadManager() {
            return newSingleThreadManager$default(this, null, 1, null);
        }
    }
}
