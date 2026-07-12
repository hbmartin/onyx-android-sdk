package com.onyx.android.sdk.pen.utils;

import com.onyx.android.sdk.pen.NeoFountainPenV2;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.NeoPenConfig;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JS\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/onyx/android/sdk/pen/utils/FountainShapes;", "", "()V", "FOUNTAIN_PEN_V1_COMPENSATION", "", "createNeoPenV2", "Lcom/onyx/android/sdk/pen/NeoPen;", "width", "minWidth", "displayScaleX", "displayScaleY", "scalePrecision", "createScale", "pressureSensitivity", "fastMode", "", "(FFFFFFLjava/lang/Float;Z)Lcom/onyx/android/sdk/pen/NeoPen;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FountainShapes {

    @NotNull
    public static final FountainShapes INSTANCE = new FountainShapes();
    private static final float a = 3.0f;

    private FountainShapes() {
    }

    public static /* synthetic */ NeoPen createNeoPenV2$default(FountainShapes fountainShapes, float f, float f2, float f3, float f4, float f5, float f6, Float f7, boolean z, int i, Object obj) {
        if ((i & 64) != 0) {
            f7 = null;
        }
        if ((i & 128) != 0) {
            z = true;
        }
        return fountainShapes.createNeoPenV2(f, f2, f3, f4, f5, f6, f7, z);
    }

    @Nullable
    public final NeoPen createNeoPenV2(float width, float minWidth, float displayScaleX, float displayScaleY, float scalePrecision, float createScale, @Nullable Float pressureSensitivity, boolean fastMode) {
        NeoPenConfig neoPenConfig = new NeoPenConfig();
        neoPenConfig.width = width + (a / createScale);
        neoPenConfig.minWidth = minWidth / createScale;
        neoPenConfig.pressureSensitivity = pressureSensitivity == null ? 0.3f : pressureSensitivity.floatValue();
        neoPenConfig.scalePrecision = scalePrecision;
        neoPenConfig.tiltEnabled = false;
        neoPenConfig.fastMode = fastMode;
        neoPenConfig.displayScaleX = displayScaleX;
        neoPenConfig.displayScaleY = displayScaleY;
        return NeoFountainPenV2.Companion.create(neoPenConfig);
    }
}
