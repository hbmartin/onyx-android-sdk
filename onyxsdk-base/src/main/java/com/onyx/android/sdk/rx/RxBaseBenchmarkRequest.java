// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.rx;

import com.onyx.android.sdk.utils.Debug;
import kotlin.jvm.internal.Intrinsics;
import com.onyx.android.sdk.extension.AnyKt;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.onyx.android.sdk.utils.Benchmark;
import kotlin.Metadata;
import kotlin.Unit;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\b\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u001a\u001a\u00020\u0002H\u0002J\b\u0010\u001b\u001a\u00020\u0002H\u0002J\b\u0010\u001c\u001a\u00020\u0002H\u0014J\u0006\u0010\u001d\u001a\u00020\u0002J\u0006\u0010\u001e\u001a\u00020\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0014J\u0006\u0010\"\u001a\u00020\tJ\u0012\u0010#\u001a\u00020\u00022\b\b\u0002\u0010!\u001a\u00020 H\u0002J\u0010\u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020 H\u0014J\b\u0010&\u001a\u00020\u0002H\u0002J\b\u0010'\u001a\u00020\u0002H&R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\n\u001a\n\u0018\u00010\u000bj\u0004\u0018\u0001`\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006(" }, d2 = { "Lcom/onyx/android/sdk/rx/RxBaseBenchmarkRequest;", "Lcom/onyx/android/sdk/rx/RxBaseRequest;", "", "()V", "benchmark", "Lcom/onyx/android/sdk/utils/Benchmark;", "getBenchmark", "()Lcom/onyx/android/sdk/utils/Benchmark;", "debugRequest", "", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", "getException", "()Ljava/lang/Exception;", "setException", "(Ljava/lang/Exception;)V", "running", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getRunning", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "throwException", "getThrowException", "()Z", "setThrowException", "(Z)V", "afterExecute", "beforeExecute", "closeResources", "disableThrowException", "execute", "getMsg", "", "msg", "hasException", "reportDuration", "reportInfoImpl", "formatMsg", "safelyExecute", "safelyExecuteImpl", "onyxsdk-base_release" })
public abstract class RxBaseBenchmarkRequest extends RxBaseRequest<Unit>
{
    @NotNull
    private final Benchmark c;
    @Nullable
    private Exception d;
    private boolean e;
    @NotNull
    private final AtomicBoolean f;
    private boolean g;
    
    public RxBaseBenchmarkRequest() {
        this.c = new Benchmark();
        this.e = true;
        this.f = new AtomicBoolean(false);
    }
    
    private final void b() {
        this.c.restart();
        if (this.g) {
            this.c.report("Request: " + AnyKt.toSimpleNameString(this) + ", beforeExecute, threadName = " + (Object)Thread.currentThread().getName() + ", threadId = " + Thread.currentThread().getId());
        }
    }
    
    private final void c() {
        this.f.set(true);
        try {
            if (!this.isAbort()) this.safelyExecuteImpl();
        } catch (Exception exception) {
            this.d = exception;
        } finally {
            this.closeResources();
            this.f.set(false);
        }
    }
    
    private final void a() throws Exception {
        a(this, null, 1, null);
        final Exception d;
        if (this.e && (d = this.d) != null) {
            throw d;
        }
    }
    
    private final void a(String msg) {
        if (this.isAbort()) {
            Debug.w(Intrinsics.stringPlus(AnyKt.toSimpleNameString(this), (Object)"---> is abort"));
        }
        final Exception d;
        if ((d = this.d) == null) {
            this.reportInfoImpl(this.getMsg(msg));
        }
        else {
            String message;
            if ((message = d.getMessage()) == null) {
                message = "";
            }
            msg = Intrinsics.stringPlus(msg, (Object)message);
            this.c.reportError(this.getMsg(msg));
        }
    }
    
    static /* synthetic */ void a(final RxBaseBenchmarkRequest rxBaseBenchmarkRequest, String msg, final int n, final Object o) {
        if (o == null) {
            if ((n & 0x1) != 0x0) {
                msg = "";
            }
            rxBaseBenchmarkRequest.a(msg);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reportDuration");
    }
    
    @NotNull
    public final Benchmark getBenchmark() {
        return this.c;
    }
    
    @Nullable
    public final Exception getException() {
        return this.d;
    }
    
    public final void setException(@Nullable final Exception value) {
        this.d = value;
    }
    
    public final boolean getThrowException() {
        return this.e;
    }
    
    public final void setThrowException(final boolean value) {
        this.e = value;
    }
    
    @NotNull
    public final AtomicBoolean getRunning() {
        return this.f;
    }
    
    public final Unit execute() throws Exception {
        this.b();
        this.c();
        this.a();
        return Unit.INSTANCE;
    }
    
    protected void closeResources() {
    }
    
    public final void disableThrowException() {
        this.e = false;
    }
    
    public final boolean hasException() {
        return this.d != null;
    }
    
    protected void reportInfoImpl(@NotNull final String formatMsg) {
        Intrinsics.checkNotNullParameter((Object)formatMsg, "formatMsg");
        if (this.g) {
            this.c.report(formatMsg);
        }
    }
    
    @NotNull
    protected String getMsg(@NotNull final String msg) {
        Intrinsics.checkNotNullParameter((Object)msg, "msg");
        return AnyKt.toSimpleNameString(this) + ':' + msg;
    }
    
    public abstract void safelyExecuteImpl();
}
