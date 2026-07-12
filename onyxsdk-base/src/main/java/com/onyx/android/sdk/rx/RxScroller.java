package com.onyx.android.sdk.rx;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import com.onyx.android.sdk.extension.RangeKt;
import com.onyx.android.sdk.extension.RectKt;
import com.onyx.android.sdk.utils.TTFFont;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxScroller.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013H\u0002J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\fH\u0002J\u0006\u0010\u001a\u001a\u00020\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/onyx/android/sdk/rx/RxScroller;", TTFFont.UNKNOWN_FONT_NAME, "()V", "animatorX", "Landroidx/dynamicanimation/animation/FlingAnimation;", "animatorY", "publishSubject", "Lio/reactivex/subjects/PublishSubject;", "Lcom/onyx/android/sdk/rx/RxScrollerInfo;", "kotlin.jvm.PlatformType", "scrollerInfo", "createScrollerX", TTFFont.UNKNOWN_FONT_NAME, "args", "Lcom/onyx/android/sdk/rx/RxScrollerArgs;", "createScrollerY", "fling", "Lio/reactivex/Observable;", "frictionValue", TTFFont.UNKNOWN_FONT_NAME, "friction", "frictionFactor", "log", "msg", TTFFont.UNKNOWN_FONT_NAME, "publish", "stop", "Companion", "onyxsdk-base_release"})
public final class RxScroller {

    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_LOG = false;

    @Nullable
    private FlingAnimation a;

    @Nullable
    private FlingAnimation b;

    @NotNull
    private RxScrollerInfo c = new RxScrollerInfo();

    @NotNull
    private PublishSubject<RxScrollerInfo> d;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/RxScroller$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/rx/RxScroller$Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "DEBUG_LOG", TTFFont.UNKNOWN_FONT_NAME, "onyxsdk-base_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public RxScroller() {
        PublishSubject<RxScrollerInfo> publishSubjectCreate = PublishSubject.create();
        Intrinsics.checkNotNullExpressionValue(publishSubjectCreate, "create<RxScrollerInfo>()");
        this.d = publishSubjectCreate;
    }

    private final void a() {
        this.d.onNext(this.c);
        if (this.c.isFinished()) {
            this.d.onComplete();
        }
    }

    private final void b(RxScrollerArgs args) {
        float velocityY = args.getVelocityY();
        Range<Float> rangeY = RectKt.toRangeY(args.getLimitRect());
        Intrinsics.checkNotNullExpressionValue(rangeY, "range");
        Float f = (Float) RangeKt.clamp(rangeY, Float.valueOf(args.getStartY()));
        Intrinsics.checkNotNullExpressionValue(f, "startValue");
        FlingAnimation flingAnimation = new FlingAnimation(new FloatValueHolder(f.floatValue()));
        flingAnimation.addUpdateListener((v1, v2, v3) -> {
            b(this, v1, v2, v3);
        });
        flingAnimation.addEndListener((v1, v2, v3, v4) -> {
            b(this, v1, v2, v3, v4);
        });
        flingAnimation.setStartVelocity(velocityY);
        Object minimum = rangeY.getMinimum();
        Intrinsics.checkNotNullExpressionValue(minimum, "range.minimum");
        flingAnimation.setMinValue(((Number) minimum).floatValue());
        Object maximum = rangeY.getMaximum();
        Intrinsics.checkNotNullExpressionValue(maximum, "range.maximum");
        flingAnimation.setMaxValue(((Number) maximum).floatValue());
        flingAnimation.setFriction(a(args.getFrictionY(), args.getFrictionFactor()));
        flingAnimation.setMinimumVisibleChange(args.getMinimumVisibleChange());
        flingAnimation.start();
        this.b = flingAnimation;
    }

    private final void a(String msg) {
    }

    @NotNull
    public final Observable<RxScrollerInfo> fling(@NotNull RxScrollerArgs args) {
        Intrinsics.checkNotNullParameter(args, "args");
        RxScrollerInfo rxScrollerInfo = new RxScrollerInfo();
        this.c = rxScrollerInfo;
        rxScrollerInfo.setNewX(args.getStartX());
        this.c.setNewY(args.getStartY());
        a(args);
        b(args);
        PublishSubject<RxScrollerInfo> publishSubjectCreate = PublishSubject.create();
        Intrinsics.checkNotNullExpressionValue(publishSubjectCreate, "create()");
        this.d = publishSubjectCreate;
        return publishSubjectCreate;
    }

    public final void stop() {
        FlingAnimation flingAnimation = this.a;
        if (flingAnimation != null) {
            flingAnimation.cancel();
        }
        this.a = null;
        FlingAnimation flingAnimation2 = this.b;
        if (flingAnimation2 != null) {
            flingAnimation2.cancel();
        }
        this.b = null;
    }

    private final void a(RxScrollerArgs args) {
        float velocityX = args.getVelocityX();
        Range<Float> rangeX = RectKt.toRangeX(args.getLimitRect());
        Intrinsics.checkNotNullExpressionValue(rangeX, "range");
        Float f = (Float) RangeKt.clamp(rangeX, Float.valueOf(args.getStartX()));
        Intrinsics.checkNotNullExpressionValue(f, "startValue");
        FlingAnimation flingAnimation = new FlingAnimation(new FloatValueHolder(f.floatValue()));
        flingAnimation.addUpdateListener((v1, v2, v3) -> {
            a(this, v1, v2, v3);
        });
        flingAnimation.addEndListener((v1, v2, v3, v4) -> {
            a(this, v1, v2, v3, v4);
        });
        flingAnimation.setStartVelocity(velocityX);
        Object minimum = rangeX.getMinimum();
        Intrinsics.checkNotNullExpressionValue(minimum, "range.minimum");
        flingAnimation.setMinValue(((Number) minimum).floatValue());
        Object maximum = rangeX.getMaximum();
        Intrinsics.checkNotNullExpressionValue(maximum, "range.maximum");
        flingAnimation.setMaxValue(((Number) maximum).floatValue());
        flingAnimation.setFriction(a(args.getFrictionX(), args.getFrictionFactor()));
        flingAnimation.setMinimumVisibleChange(args.getMinimumVisibleChange());
        flingAnimation.start();
        this.a = flingAnimation;
    }

    private static final void b(RxScroller this$0, DynamicAnimation animation, float value, float velocity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.a("y, update value " + value + "  vel: " + velocity + " running: " + animation.isRunning());
        this$0.c.setNewY(value);
        this$0.a();
    }

    private static final void a(RxScroller this$0, DynamicAnimation animation, float value, float velocity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.a("x, update value " + value + "  vel: " + velocity + " running: " + animation.isRunning());
        this$0.c.setNewX(value);
        this$0.a();
    }

    private static final void b(RxScroller this$0, DynamicAnimation animation, boolean canceled, float value, float velocity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.a("y, end value " + value + "  vel: " + velocity + " finished, canceled: " + canceled);
        this$0.c.setNewY(value);
        this$0.c.setYFinished(true);
        this$0.a();
    }

    private static final void a(RxScroller this$0, DynamicAnimation animation, boolean canceled, float value, float velocity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.a("x, end value " + value + "  vel: " + velocity + " finished, canceled: " + canceled);
        this$0.c.setNewX(value);
        this$0.c.setXFinished(true);
        this$0.a();
    }

    private final float a(float friction, float frictionFactor) {
        if (friction < Float.MAX_VALUE) {
            float f = friction * frictionFactor;
            friction = f;
            if (Float.isInfinite(f)) {
                friction = Float.MAX_VALUE;
            }
        }
        return friction;
    }
}
