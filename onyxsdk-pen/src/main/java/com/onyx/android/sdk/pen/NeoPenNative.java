package com.onyx.android.sdk.pen;

import android.util.Log;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.base.utils.Debug;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0013\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006J\u0012\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\u001b\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0082 J\u0011\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0082 J#\u0010\u0017\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0004H\u0082 J-\u0010\u001b\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001c\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0004H\u0082 J#\u0010\u001d\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0004H\u0082 J \u0010\u001e\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u0004J0\u0010 \u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\"2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001a\u001a\u00020\u0004J \u0010#\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenNative;", "", "()V", "DUMP_POINTS", "", "createPen", "", "penType", "", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "destroyPen", "", "pen", "dumpResult", "result", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "dumpResultPrettify", "title", "", "message", "nativeCreatePen", "nativeDestroyPen", "nativeOnPenDown", "point", "", "repaint", "nativeOnPenMove", "prediction", "nativeOnPenUp", "onPenDown", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "onPenMove", "points", "", "onPenUp", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoPenNative {

    @NotNull
    public static final NeoPenNative INSTANCE = new NeoPenNative();
    private static final String TRACE_TAG = "OnyxNeoPen";
    // Cached at class load; set log.tag.OnyxNeoPen before loading this library.
    private static final boolean a = Log.isLoggable(TRACE_TAG, Log.DEBUG);

    static {
        System.loadLibrary("neo_pen");
    }

    private NeoPenNative() {
    }

    private final void a(NeoPenResult neoPenResult) {
        b("real ink", String.valueOf(neoPenResult == null ? null : neoPenResult.getRealInk()));
        b("prediction ink", String.valueOf(neoPenResult == null ? null : neoPenResult.getPredictionInk()));
    }

    private final void b(String str, String str2) {
        while (str2.length() > 80) {
            CharSequence charSequenceSubSequence = str2.subSequence(0, 80);
            Debug.INSTANCE.i(NeoPenNative.class, str + ": " + ((Object) charSequenceSubSequence), new Object[0]);
            str2 = str2.substring(80);
            Intrinsics.checkNotNullExpressionValue(str2, "this as java.lang.String).substring(startIndex)");
        }
        Debug.INSTANCE.i(NeoPenNative.class, str + ": " + str2, new Object[0]);
    }

    private final native long nativeCreatePen(int penType, NeoPenConfig config);

    private final native void nativeDestroyPen(long pen);

    private final native NeoPenResult nativeOnPenDown(long pen, double[] point, boolean repaint);

    private final native NeoPenResult nativeOnPenMove(long pen, double[] point, double[] prediction, boolean repaint);

    private final native NeoPenResult nativeOnPenUp(long pen, double[] point, boolean repaint);

    public final long createPen(int penType, @NotNull NeoPenConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        long handle = nativeCreatePen(penType, config);
        if (a) {
            Log.d(TRACE_TAG, "create handle=" + handle + " type=" + penType
                    + " width=" + config.width + " minWidth=" + config.minWidth
                    + " fast=" + config.fastMode + " color=0x"
                    + Integer.toHexString(config.color));
        }
        return handle;
    }

    public final void destroyPen(long pen) {
        if (a) Log.d(TRACE_TAG, "destroy handle=" + pen);
        nativeDestroyPen(pen);
    }

    @Nullable
    public final NeoPenResult onPenDown(long pen, @NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        NeoPenResult neoPenResultNativeOnPenDown = nativeOnPenDown(pen, com.onyx.android.sdk.pen.utils.PenUtils.getPointDoubleArray$default(com.onyx.android.sdk.pen.utils.PenUtils.INSTANCE, point, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 2, (Object) null), repaint);
        if (a) {
            Log.d(TRACE_TAG, "down handle=" + pen + " repaint=" + repaint + " point=" + point);
            a(neoPenResultNativeOnPenDown);
        }
        return neoPenResultNativeOnPenDown;
    }

    @Nullable
    public final NeoPenResult onPenMove(long pen, @NotNull List<? extends TouchPoint> points, @Nullable TouchPoint prediction, boolean repaint) {
        Intrinsics.checkNotNullParameter(points, "points");
        com.onyx.android.sdk.pen.utils.PenUtils penUtils = com.onyx.android.sdk.pen.utils.PenUtils.INSTANCE;
        NeoPenResult neoPenResultNativeOnPenMove = nativeOnPenMove(pen, com.onyx.android.sdk.pen.utils.PenUtils.getPointDoubleArray$default(penUtils, points, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 2, (Object) null), com.onyx.android.sdk.pen.utils.PenUtils.getPointDoubleArrayNullable$default(penUtils, prediction, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 2, null), repaint);
        if (a) {
            Log.d(TRACE_TAG, "move handle=" + pen + " repaint=" + repaint
                    + " count=" + points.size() + " first="
                    + (points.isEmpty() ? null : points.get(0)) + " prediction=" + prediction);
            a(neoPenResultNativeOnPenMove);
        }
        return neoPenResultNativeOnPenMove;
    }

    @Nullable
    public final NeoPenResult onPenUp(long pen, @NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        NeoPenResult neoPenResultNativeOnPenUp = nativeOnPenUp(pen, com.onyx.android.sdk.pen.utils.PenUtils.getPointDoubleArray$default(com.onyx.android.sdk.pen.utils.PenUtils.INSTANCE, point, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, 2, (Object) null), repaint);
        if (a) {
            Log.d(TRACE_TAG, "up handle=" + pen + " repaint=" + repaint + " point=" + point);
            a(neoPenResultNativeOnPenUp);
        }
        return neoPenResultNativeOnPenUp;
    }
}
