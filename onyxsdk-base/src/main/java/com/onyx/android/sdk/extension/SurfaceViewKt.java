package com.onyx.android.sdk.extension;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceView;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a&\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a$\u0010\f\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0016\u0010\u0011\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0012\u001a\u0004\u0018\u00010\r\u001aH\u0010\u0013\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u0015¢\u0006\u0002\b\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0003 \u0001¨\u0006\u0017"}, d2 = {"clean", TTFFont.UNKNOWN_FONT_NAME, "Landroid/view/SurfaceView;", "color", TTFFont.UNKNOWN_FONT_NAME, "drawPathWithRect", "path", "Landroid/graphics/Path;", "paint", "Landroid/graphics/Paint;", "rect", "Landroid/graphics/Rect;", "lock", "Landroid/graphics/Canvas;", "dirty", "useHardware", TTFFont.UNKNOWN_FONT_NAME, "unlockCanvasAndPost", "canvas", "use", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "onyxsdk-base_release"})
public final class SurfaceViewKt {
    private SurfaceViewKt() {
    }

    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/graphics/Canvas;", "invoke"})
    static final class a implements Function1<Canvas, Unit> {
        final /* synthetic */ int a;
        a(int $color) {
            this.a = $color;
        }

        public final Unit invoke(@NotNull Canvas $this$use) {
            Intrinsics.checkNotNullParameter($this$use, "$this$use");
            $this$use.drawColor(this.a);
            return Unit.INSTANCE;
        }
    }
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "Landroid/graphics/Canvas;", "invoke"})
    static final class b implements Function1<Canvas, Unit> {
        final /* synthetic */ Path a;
        final /* synthetic */ Paint b;
        b(Path $path, Paint $paint) {
            this.a = $path;
            this.b = $paint;
        }

        public final Unit invoke(@NotNull Canvas $this$use) {
            Intrinsics.checkNotNullParameter($this$use, "$this$use");
            $this$use.drawPath(this.a, this.b);
            return Unit.INSTANCE;
        }
    }

    @Nullable
    public static final Canvas lock(@Nullable SurfaceView $this$lock, @Nullable Rect dirty, boolean useHardware) {
        if ($this$lock == null) {
            return null;
        }
        return (CompatibilityUtil.isApiLevelAbove(26) && useHardware) ? $this$lock.getHolder().lockHardwareCanvas() : $this$lock.getHolder().lockCanvas(dirty);
    }

    public static /* synthetic */ Canvas lock$default(SurfaceView surfaceView, Rect rect, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            rect = null;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return lock(surfaceView, rect, z);
    }

    public static final void unlockCanvasAndPost(@Nullable SurfaceView $this$unlockCanvasAndPost, @Nullable Canvas canvas) {
        if ($this$unlockCanvasAndPost == null || canvas == null) {
            return;
        }
        $this$unlockCanvasAndPost.getHolder().unlockCanvasAndPost(canvas);
    }
    public static final void use(@Nullable SurfaceView surfaceView, @Nullable Rect dirty, boolean useHardware, @NotNull Function1<? super Canvas, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "block");
        if (surfaceView == null) {
            return;
        }
        Canvas canvas = null;
        try {
            canvas = lock(surfaceView, dirty, useHardware);
            if (canvas != null) {
                function1.invoke(canvas);
            }
        } finally {
            unlockCanvasAndPost(surfaceView, canvas);
        }
    }

    public static /* synthetic */ void use$default(SurfaceView surfaceView, Rect rect, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            rect = null;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        use(surfaceView, rect, z, function1);
    }

    public static final void drawPathWithRect(@NotNull SurfaceView $this$drawPathWithRect, @NotNull Path path, @NotNull Paint paint, @Nullable Rect rect) {
        Intrinsics.checkNotNullParameter($this$drawPathWithRect, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(paint, "paint");
        use$default($this$drawPathWithRect, rect, false, new b(path, paint), 2, null);
    }

    public static /* synthetic */ void drawPathWithRect$default(SurfaceView surfaceView, Path path, Paint paint, Rect rect, int i, Object obj) {
        if ((i & 4) != 0) {
            rect = null;
        }
        drawPathWithRect(surfaceView, path, paint, rect);
    }

    public static final void clean(@NotNull SurfaceView $this$clean, int color) {
        Intrinsics.checkNotNullParameter($this$clean, "<this>");
        use$default($this$clean, null, false, new a(color), 3, null);
    }

    public static /* synthetic */ void clean$default(SurfaceView surfaceView, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        clean(surfaceView, i);
    }
}
