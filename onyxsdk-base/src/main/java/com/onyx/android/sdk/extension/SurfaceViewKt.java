package com.onyx.android.sdk.extension;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceView;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a&\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a$\u0010\f\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0016\u0010\u0011\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0012\u001a\u0004\u0018\u00010\r\u001aH\u0010\u0013\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u0015¢\u0006\u0002\b\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0003 \u0001¨\u0006\u0017"}, d2 = {"clean", "", "Landroid/view/SurfaceView;", "color", "", "drawPathWithRect", "path", "Landroid/graphics/Path;", "paint", "Landroid/graphics/Paint;", "rect", "Landroid/graphics/Rect;", "lock", "Landroid/graphics/Canvas;", "dirty", "useHardware", "", "unlockCanvasAndPost", "canvas", "use", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "onyxsdk-base_release"})
public final class SurfaceViewKt {
    private SurfaceViewKt() {
    }

    @Nullable
    public static final Canvas lock(@Nullable SurfaceView surfaceView, @Nullable Rect dirty, boolean useHardware) {
        if (surfaceView == null) {
            return null;
        }
        return CompatibilityUtil.isApiLevelAbove(26) && useHardware
                ? surfaceView.getHolder().lockHardwareCanvas()
                : surfaceView.getHolder().lockCanvas(dirty);
    }

    public static /* synthetic */ Canvas lock$default(SurfaceView surfaceView, Rect dirty, boolean useHardware, int mask, Object unused) {
        if ((mask & 1) != 0) {
            dirty = null;
        }
        if ((mask & 2) != 0) {
            useHardware = true;
        }
        return lock(surfaceView, dirty, useHardware);
    }

    public static final void unlockCanvasAndPost(@Nullable SurfaceView surfaceView, @Nullable Canvas canvas) {
        if (surfaceView == null || canvas == null) {
            return;
        }
        surfaceView.getHolder().unlockCanvasAndPost(canvas);
    }

    public static final void use(@Nullable SurfaceView surfaceView, @Nullable Rect dirty,
            boolean useHardware, @NotNull Function1<? super Canvas, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (surfaceView == null) {
            return;
        }
        Canvas canvas = null;
        try {
            canvas = lock(surfaceView, dirty, useHardware);
            if (canvas != null) {
                block.invoke(canvas);
            }
        } finally {
            unlockCanvasAndPost(surfaceView, canvas);
        }
    }

    public static /* synthetic */ void use$default(SurfaceView surfaceView, Rect dirty,
            boolean useHardware, Function1 block, int mask, Object unused) {
        if ((mask & 1) != 0) {
            dirty = null;
        }
        if ((mask & 2) != 0) {
            useHardware = true;
        }
        use(surfaceView, dirty, useHardware, block);
    }

    public static final void drawPathWithRect(@NotNull SurfaceView surfaceView,
            @NotNull Path path, @NotNull Paint paint, @Nullable Rect rect) {
        Intrinsics.checkNotNullParameter(surfaceView, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(paint, "paint");
        use(surfaceView, rect, true, canvas -> {
            canvas.drawPath(path, paint);
            return Unit.INSTANCE;
        });
    }

    public static /* synthetic */ void drawPathWithRect$default(SurfaceView surfaceView,
            Path path, Paint paint, Rect rect, int mask, Object unused) {
        if ((mask & 4) != 0) {
            rect = null;
        }
        drawPathWithRect(surfaceView, path, paint, rect);
    }

    public static final void clean(@NotNull SurfaceView surfaceView, int color) {
        Intrinsics.checkNotNullParameter(surfaceView, "<this>");
        use(surfaceView, null, true, canvas -> {
            canvas.drawColor(color);
            return Unit.INSTANCE;
        });
    }

    public static /* synthetic */ void clean$default(SurfaceView surfaceView, int color,
            int mask, Object unused) {
        if ((mask & 1) != 0) {
            color = -1;
        }
        clean(surfaceView, color);
    }
}
