package com.onyx.android.sdk.pen.utils;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/utils/ColorUtils.class */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/utils/ColorUtils;", "", "()V", "replaceAlphaWithBitwise", "", "colorInt", "alpha", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ColorUtils {

    @NotNull
    public static final ColorUtils INSTANCE = new ColorUtils();

    private ColorUtils() {
    }

    public final int replaceAlphaWithBitwise(int colorInt, int alpha) {
        return (colorInt & 16777215) | (RangesKt.coerceIn(alpha, 0, 255) << 24);
    }
}

