// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.rx;

import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.extension.AnyKt;
import kotlin.jvm.internal.Intrinsics;
import java.util.concurrent.atomic.AtomicBoolean;
import io.reactivex.subjects.PublishSubject;
import android.view.Choreographer.FrameCallback;
import android.view.Choreographer;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0008\u0003\n\u0002\u0010\u0002\n\u0002\u0008\u0007\u0008\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0008\u0010\u0015\u001a\u00020\u0012H\u0002J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0006\u0010\u0017\u001a\u00020\u0012J\u0006\u0010\u0018\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0008\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u000c\u001a\u0010\u0012\u000c\u0012\n \u0007*\u0004\u0018\u00010\u000e0\u000e0\r\u00a2\u0006\u0008\n\u0000\u001a\u0004\u0008\u000f\u0010\u0010\u00a8\u0006\u0019" }, d2 = { "Lcom/onyx/android/sdk/rx/RxFrameHandler;", "", "()V", "DEBUG_LOG", "", "choreographer", "Landroid/view/Choreographer;", "kotlin.jvm.PlatformType", "choreographerCallback", "Landroid/view/Choreographer$FrameCallback;", "isRunning", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publishSubject", "Lio/reactivex/subjects/PublishSubject;", "", "getPublishSubject", "()Lio/reactivex/subjects/PublishSubject;", "checkLeakOnDestroy", "", "dispatchFrameCallback", "frameTimeNanos", "postFrameCallback", "printLog", "start", "stop", "onyxsdk-base_release" })
public final class RxFrameHandler
{
    @NotNull
    public static final RxFrameHandler INSTANCE;
    private static final boolean a = false;
    private static final Choreographer b;
    @NotNull
    private static Choreographer.FrameCallback c;
    @NotNull
    private static final PublishSubject<Long> d;
    @NotNull
    private static AtomicBoolean e;
    
    private RxFrameHandler() {
    }
    
    private final void a(final long frameTimeNanos) {
        this.b(frameTimeNanos);
        RxFrameHandler.d.onNext(frameTimeNanos);
        if (RxFrameHandler.e.get()) {
            this.a();
        }
    }
    
    private final void a() {
        RxFrameHandler.b.postFrameCallback(RxFrameHandler.c);
    }
    
    private final void b(final long frameTimeNanos) {
    }
    
    static {
        final RxFrameHandler rxFrameHandler = INSTANCE = new RxFrameHandler();
        b = Choreographer.getInstance();
        RxFrameHandler.c = rxFrameHandler::a;
        final PublishSubject create = PublishSubject.create();
        Intrinsics.checkNotNullExpressionValue((Object)create, "create<Long>()");
        d = create;
        RxFrameHandler.e = new AtomicBoolean(false);
    }
    
    @NotNull
    public final PublishSubject<Long> getPublishSubject() {
        return RxFrameHandler.d;
    }
    
    public final void start() {
        if (RxFrameHandler.e.get()) {
            return;
        }
        RxFrameHandler.e.set(true);
        this.a();
    }
    
    public final void stop() {
        RxFrameHandler.e.set(false);
    }
    
    public final void checkLeakOnDestroy() {
        final boolean hasObservers;
        if ((hasObservers = RxFrameHandler.d.hasObservers()) || RxFrameHandler.e.get()) {
            Debug.w(AnyKt.toSimpleNameString(this), "checkLeakOnDestroy, hasObservers = " + hasObservers + " , isRunning = " + RxFrameHandler.e.get(), new Object[0]);
        }
    }
}
