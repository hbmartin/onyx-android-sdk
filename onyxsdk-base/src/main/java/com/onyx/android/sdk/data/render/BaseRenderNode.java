package com.onyx.android.sdk.data.render;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\fH&J\b\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\nH&J\b\u0010\u0011\u001a\u00020\u0012H&J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\fH\u0016J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\fH\u0016¨\u0006\u001a"}, d2 = {"Lcom/onyx/android/sdk/data/render/BaseRenderNode;", TTFFont.UNKNOWN_FONT_NAME, "beginRecording", "Landroid/graphics/Canvas;", "discardDisplayList", TTFFont.UNKNOWN_FONT_NAME, "drawRenderNode", "canvas", "endRecording", "getHeight", TTFFont.UNKNOWN_FONT_NAME, "getTranslationX", TTFFont.UNKNOWN_FONT_NAME, "getTranslationY", "getUniqueId", TTFFont.UNKNOWN_FONT_NAME, "getWidth", "hasDisplayList", TTFFont.UNKNOWN_FONT_NAME, "setPosition", "rect", "Landroid/graphics/Rect;", "setTranslationX", "x", "setTranslationY", "y", "onyxsdk-base_release"})
public interface BaseRenderNode {

    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        private DefaultImpls() {
        }

        public static void setTranslationX(@NotNull BaseRenderNode baseRenderNode, float x) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
        }

        public static void setTranslationY(@NotNull BaseRenderNode baseRenderNode, float y) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
        }

        public static void discardDisplayList(@NotNull BaseRenderNode baseRenderNode) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
        }

        public static void drawRenderNode(@NotNull BaseRenderNode baseRenderNode, @NotNull Canvas canvas) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
            Intrinsics.checkNotNullParameter(canvas, "canvas");
        }

        public static void setPosition(@NotNull BaseRenderNode baseRenderNode, @NotNull Rect rect) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
            Intrinsics.checkNotNullParameter(rect, "rect");
        }

        public static void endRecording(@NotNull BaseRenderNode baseRenderNode, @NotNull Canvas canvas) {
            Intrinsics.checkNotNullParameter(baseRenderNode, "this");
            Intrinsics.checkNotNullParameter(canvas, "canvas");
        }
    }

    boolean hasDisplayList();

    long getUniqueId();

    int getWidth();

    int getHeight();

    float getTranslationX();

    float getTranslationY();

    void setTranslationX(float x);

    void setTranslationY(float y);

    void discardDisplayList();

    void drawRenderNode(@NotNull Canvas canvas);

    void setPosition(@NotNull Rect rect);

    @Nullable
    Canvas beginRecording();

    void endRecording(@NotNull Canvas canvas);
}
