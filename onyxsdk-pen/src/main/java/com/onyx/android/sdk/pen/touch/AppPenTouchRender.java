package com.onyx.android.sdk.pen.touch;

import android.view.MotionEvent;
import android.view.View;
import com.onyx.android.sdk.pen.EpdPenManager;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.utils.TouchUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\r\u001a\u00020\u000e2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\u0010\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0011H\u0016R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lcom/onyx/android/sdk/pen/touch/AppPenTouchRender;", "Lcom/onyx/android/sdk/pen/touch/AppTouchRender;", "view", "Landroid/view/View;", "callback", "Lcom/onyx/android/sdk/pen/RawInputCallback;", "(Landroid/view/View;Lcom/onyx/android/sdk/pen/RawInputCallback;)V", "epdPenManager", "Lcom/onyx/android/sdk/pen/EpdPenManager;", "getEpdPenManager", "()Lcom/onyx/android/sdk/pen/EpdPenManager;", "epdPenManager$delegate", "Lkotlin/Lazy;", "bindHostView", "", "closeDrawing", "onInterceptTouchEvent", "", "event", "Landroid/view/MotionEvent;", "pauseDrawing", "resumeDrawing", "setDrawingRenderEnabled", "enabled", "onyxsdk-pen_release"})
public final class AppPenTouchRender extends AppTouchRender {

    @NotNull
    private final Lazy i;

    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/onyx/android/sdk/pen/EpdPenManager;", "invoke"})
    static final class a implements Function0<EpdPenManager> {
        public static final a a = new a();

        a() {
        }

        @NotNull
        public final EpdPenManager invoke() {
            return new EpdPenManager();
        }
    }

    public AppPenTouchRender(@NotNull View view, @NotNull RawInputCallback callback) {
        super(view, callback);
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.i = LazyKt.lazy(a.a);
    }

    private final EpdPenManager e() {
        return (EpdPenManager) this.i.getValue();
    }

    private final void g() {
        e().resumeDrawing();
    }

    private final void f() {
        e().pauseDrawing();
    }

    @Override // com.onyx.android.sdk.pen.touch.AppTouchRender, com.onyx.android.sdk.pen.touch.TouchRender
    public void bindHostView(@Nullable View view, @Nullable RawInputCallback callback) {
        super.bindHostView(view, callback);
        e().setHostView(view);
    }

    @Override // com.onyx.android.sdk.pen.touch.AppTouchRender
    protected boolean onInterceptTouchEvent(@Nullable MotionEvent event) {
        return !TouchUtils.isPenTouchType(event);
    }

    @Override // com.onyx.android.sdk.pen.touch.AppTouchRender, com.onyx.android.sdk.pen.touch.TouchRender
    public void setDrawingRenderEnabled(boolean enabled) {
        super.setDrawingRenderEnabled(enabled);
        if (enabled) {
            g();
        } else {
            f();
        }
    }

    @Override // com.onyx.android.sdk.pen.touch.AppTouchRender, com.onyx.android.sdk.pen.touch.TouchRender
    public void closeDrawing() {
        f();
        super.closeDrawing();
    }
}
