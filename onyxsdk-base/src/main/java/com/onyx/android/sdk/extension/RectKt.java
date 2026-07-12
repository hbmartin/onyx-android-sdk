package com.onyx.android.sdk.extension;

import android.graphics.Rect;
import android.graphics.RectF;
import com.onyx.android.sdk.base.lite.extension.RectFKt;
import com.onyx.android.sdk.data.Orientation;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/RectKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0001\u001a\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007*\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001\u001a\u001a\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b\u001a\u0012\u0010\r\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0001\u001a\u001c\u0010\u0013\u001a\u00020\u0014*\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u0005\u001a\u0012\u0010\u0017\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u0001\u001a\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0014*\u00020\u0001\u001a\n\u0010\u001d\u001a\u00020\u0014*\u00020\u0001\u001a\u001d\u0010\u001e\u001a\u00020\u0014*\u0004\u0018\u00010\u001f\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001d\u0010\u001e\u001a\u00020\u0014*\u0004\u0018\u00010\u0001\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u0012\u0010 \u001a\u00020\u0014*\u00020\u00012\u0006\u0010!\u001a\u00020\u0001\u001a\n\u0010\"\u001a\u00020\u0014*\u00020\u0001\u001a\n\u0010#\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010$\u001a\u00020\u0001*\u00020\u0001\u001a\f\u0010%\u001a\u00020\u0001*\u0004\u0018\u00010\u0001\u001a\n\u0010&\u001a\u00020'*\u00020\u0001\u001a\n\u0010(\u001a\u00020'*\u00020\u0001\u001a\n\u0010)\u001a\u00020'*\u00020\u0001\u001a\n\u0010*\u001a\u00020\u0005*\u00020\u0001\u001a\u0010\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0007*\u00020\u001f\u001a\u0010\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0007*\u00020\u001f\u001a.\u0010-\u001a&\u0012\f\u0012\n /*\u0004\u0018\u00010\u00050\u0005 /*\u0012\u0012\f\u0012\n /*\u0004\u0018\u00010\u00050\u0005\u0018\u00010.0.*\u00020\u0001\u001a.\u00100\u001a&\u0012\f\u0012\n /*\u0004\u0018\u00010\u00050\u0005 /*\u0012\u0012\f\u0012\n /*\u0004\u0018\u00010\u00050\u0005\u0018\u00010.0.*\u00020\u0001\u001a\u000e\u00101\u001a\u0004\u0018\u00010\u001f*\u0004\u0018\u00010\u0001\u001a\u000e\u00102\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u001f\u001a\n\u00103\u001a\u00020\u001f*\u00020\u0001\u001a\n\u00104\u001a\u00020\u0001*\u00020\u0001\u001a\u001c\u00105\u001a\u00020'*\u00020\u00012\u0006\u00106\u001a\u00020\u00012\b\b\u0002\u0010\u0016\u001a\u00020\u0005¨\u00067"}, d2 = {"cropRectToMatchAspect", "Landroid/graphics/RectF;", "inputRect", "boundingRect", "area", TTFFont.UNKNOWN_FONT_NAME, "createRectanglesWithHole", TTFFont.UNKNOWN_FONT_NAME, "smallRectF", "ensureMinimumSize", "minWidth", TTFFont.UNKNOWN_FONT_NAME, "minHeight", "expand", "value", "paddingLeftRight", "paddingTopBottom", "getOrientation", "Lcom/onyx/android/sdk/data/Orientation;", "hasVerticalOverlapWith", TTFFont.UNKNOWN_FONT_NAME, "b", "gap", "inset", TTFFont.UNKNOWN_FONT_NAME, "rectF", "intersectRectF", "r2", "isLandscape", "isNaN", "isNullOrEmpty", "Landroid/graphics/Rect;", "isOutside", "other", "isPortrait", "longerSize", "offsetToOrigin", "orEmpty", "roundArea", TTFFont.UNKNOWN_FONT_NAME, "roundHeight", "roundWidth", "shorterSize", "splitHorizontally", "splitVertically", "toRangeX", "Lorg/apache/commons/lang3/Range;", "kotlin.jvm.PlatformType", "toRangeY", "toRect", "toRectF", "toRectNonNull", "toZeroOriginRoundRectF", "verCheck", "another", "onyxsdk-base_release"})
public final class RectKt {
    private RectKt() {
    }

    public static final boolean isNullOrEmpty(@Nullable Rect $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
    }

    @Nullable
    public static final Rect toRect(@Nullable RectF $this$toRect) {
        return $this$toRect == null
                ? null
                : new Rect((int) $this$toRect.left, (int) $this$toRect.top,
                        (int) $this$toRect.right, (int) $this$toRect.bottom);
    }

    @NotNull
    public static final Rect toRectNonNull(@NotNull RectF $this$toRectNonNull) {
        Intrinsics.checkNotNullParameter($this$toRectNonNull, "<this>");
        return new Rect((int) $this$toRectNonNull.left, (int) $this$toRectNonNull.top, (int) $this$toRectNonNull.right, (int) $this$toRectNonNull.bottom);
    }

    @NotNull
    public static final RectF offsetToOrigin(@NotNull RectF $this$offsetToOrigin) {
        Intrinsics.checkNotNullParameter($this$offsetToOrigin, "<this>");
        $this$offsetToOrigin.offsetTo(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL);
        return $this$offsetToOrigin;
    }

    @Nullable
    public static final RectF toRectF(@Nullable Rect $this$toRectF) {
        return $this$toRectF == null ? null : new RectF($this$toRectF);
    }

    @Nullable
    public static final RectF intersectRectF(@NotNull RectF $this$intersectRectF, @NotNull RectF r2) {
        Intrinsics.checkNotNullParameter($this$intersectRectF, "<this>");
        Intrinsics.checkNotNullParameter(r2, "r2");
        RectF rectF = new RectF();
        if (rectF.setIntersect($this$intersectRectF, r2)) {
            return rectF;
        }
        return null;
    }

    @NotNull
    public static final RectF ensureMinimumSize(@NotNull RectF $this$ensureMinimumSize, @NotNull Number minWidth, @NotNull Number minHeight) {
        Intrinsics.checkNotNullParameter($this$ensureMinimumSize, "<this>");
        Intrinsics.checkNotNullParameter(minWidth, "minWidth");
        Intrinsics.checkNotNullParameter(minHeight, "minHeight");
        if ($this$ensureMinimumSize.width() >= minWidth.floatValue() && $this$ensureMinimumSize.height() >= minHeight.floatValue()) {
            return $this$ensureMinimumSize;
        }
        RectF rectFCopy = RectFKt.copy($this$ensureMinimumSize);
        float fCenterX = $this$ensureMinimumSize.centerX();
        float fCenterY = $this$ensureMinimumSize.centerY();
        if ($this$ensureMinimumSize.width() < minWidth.floatValue()) {
            float f = 2;
            rectFCopy.left = fCenterX - (minWidth.floatValue() / f);
            rectFCopy.right = fCenterX + (minWidth.floatValue() / f);
        }
        if ($this$ensureMinimumSize.height() < minHeight.floatValue()) {
            float f2 = 2;
            rectFCopy.top = fCenterY - (minHeight.floatValue() / f2);
            rectFCopy.bottom = fCenterY + (minHeight.floatValue() / f2);
        }
        return rectFCopy;
    }

    @NotNull
    public static final List<Rect> splitHorizontally(@NotNull Rect $this$splitHorizontally) {
        Intrinsics.checkNotNullParameter($this$splitHorizontally, "<this>");
        int i = $this$splitHorizontally.left;
        int i2 = i + (($this$splitHorizontally.right - i) / 2);
        return CollectionsKt.listOf(new Rect[]{new Rect($this$splitHorizontally.left, $this$splitHorizontally.top, i2, $this$splitHorizontally.bottom), new Rect(i2, $this$splitHorizontally.top, $this$splitHorizontally.right, $this$splitHorizontally.bottom)});
    }

    @NotNull
    public static final List<Rect> splitVertically(@NotNull Rect $this$splitVertically) {
        Intrinsics.checkNotNullParameter($this$splitVertically, "<this>");
        int i = $this$splitVertically.top;
        int i2 = i + (($this$splitVertically.bottom - i) / 2);
        return CollectionsKt.listOf(new Rect[]{new Rect($this$splitVertically.left, $this$splitVertically.top, $this$splitVertically.right, i2), new Rect($this$splitVertically.left, i2, $this$splitVertically.right, $this$splitVertically.bottom)});
    }

    public static final boolean isLandscape(@NotNull RectF $this$isLandscape) {
        Intrinsics.checkNotNullParameter($this$isLandscape, "<this>");
        return $this$isLandscape.width() > $this$isLandscape.height();
    }

    public static final boolean isPortrait(@NotNull RectF $this$isPortrait) {
        Intrinsics.checkNotNullParameter($this$isPortrait, "<this>");
        return $this$isPortrait.width() < $this$isPortrait.height();
    }

    @NotNull
    public static final Orientation getOrientation(@NotNull RectF $this$getOrientation) {
        Intrinsics.checkNotNullParameter($this$getOrientation, "<this>");
        return isPortrait($this$getOrientation) ? Orientation.PORTRAIT : Orientation.LANDSCAPE;
    }

    public static final float area(@NotNull RectF $this$area) {
        Intrinsics.checkNotNullParameter($this$area, "<this>");
        return $this$area.width() * $this$area.height();
    }

    public static final int roundArea(@NotNull RectF $this$roundArea) {
        Intrinsics.checkNotNullParameter($this$roundArea, "<this>");
        return (int) ($this$roundArea.width() * $this$roundArea.height());
    }

    public static final int verCheck(@NotNull RectF $this$verCheck, @NotNull RectF another, float gap) {
        Intrinsics.checkNotNullParameter($this$verCheck, "<this>");
        Intrinsics.checkNotNullParameter(another, "another");
        if (hasVerticalOverlapWith($this$verCheck, another, gap)) {
            return 0;
        }
        return $this$verCheck.top > another.bottom ? 1 : -1;
    }

    public static /* synthetic */ int verCheck$default(RectF rectF, RectF rectF2, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.0f;
        }
        return verCheck(rectF, rectF2, f);
    }

    public static final boolean hasVerticalOverlapWith(@NotNull RectF $this$hasVerticalOverlapWith, @NotNull RectF b, float gap) {
        Intrinsics.checkNotNullParameter($this$hasVerticalOverlapWith, "<this>");
        Intrinsics.checkNotNullParameter(b, "b");
        return $this$hasVerticalOverlapWith.top < b.bottom && b.top < $this$hasVerticalOverlapWith.bottom + gap;
    }

    public static /* synthetic */ boolean hasVerticalOverlapWith$default(RectF rectF, RectF rectF2, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.0f;
        }
        return hasVerticalOverlapWith(rectF, rectF2, f);
    }

    public static final int roundWidth(@NotNull RectF $this$roundWidth) {
        Intrinsics.checkNotNullParameter($this$roundWidth, "<this>");
        return MathKt.roundToInt($this$roundWidth.width());
    }

    public static final int roundHeight(@NotNull RectF $this$roundHeight) {
        Intrinsics.checkNotNullParameter($this$roundHeight, "<this>");
        return MathKt.roundToInt($this$roundHeight.height());
    }

    public static final Range<Float> toRangeX(@NotNull RectF $this$toRangeX) {
        Intrinsics.checkNotNullParameter($this$toRangeX, "<this>");
        return Range.between(Float.valueOf($this$toRangeX.left), Float.valueOf($this$toRangeX.right));
    }

    public static final Range<Float> toRangeY(@NotNull RectF $this$toRangeY) {
        Intrinsics.checkNotNullParameter($this$toRangeY, "<this>");
        return Range.between(Float.valueOf($this$toRangeY.top), Float.valueOf($this$toRangeY.bottom));
    }

    public static final float longerSize(@NotNull RectF $this$longerSize) {
        Intrinsics.checkNotNullParameter($this$longerSize, "<this>");
        return Float.max($this$longerSize.width(), $this$longerSize.height());
    }

    public static final float shorterSize(@NotNull RectF $this$shorterSize) {
        Intrinsics.checkNotNullParameter($this$shorterSize, "<this>");
        return Float.min($this$shorterSize.width(), $this$shorterSize.height());
    }

    @NotNull
    public static final RectF expand(@NotNull RectF $this$expand, float value) {
        Intrinsics.checkNotNullParameter($this$expand, "<this>");
        return expand($this$expand, value, value);
    }

    @NotNull
    public static final RectF orEmpty(@Nullable RectF $this$orEmpty) {
        if ($this$orEmpty == null) {
            $this$orEmpty = new RectF();
        }
        return $this$orEmpty;
    }

    @NotNull
    public static final List<RectF> createRectanglesWithHole(@NotNull RectF $this$createRectanglesWithHole, @NotNull RectF smallRectF) {
        Intrinsics.checkNotNullParameter($this$createRectanglesWithHole, "<this>");
        Intrinsics.checkNotNullParameter(smallRectF, "smallRectF");
        ArrayList arrayList = new ArrayList();
        if (MathKt.roundToInt(smallRectF.left) >= MathKt.roundToInt($this$createRectanglesWithHole.left) && MathKt.roundToInt(smallRectF.top) >= MathKt.roundToInt($this$createRectanglesWithHole.top) && MathKt.roundToInt(smallRectF.right) <= MathKt.roundToInt($this$createRectanglesWithHole.right) && MathKt.roundToInt(smallRectF.bottom) <= MathKt.roundToInt($this$createRectanglesWithHole.bottom)) {
            arrayList.add(new RectF($this$createRectanglesWithHole.left, $this$createRectanglesWithHole.top, smallRectF.left, smallRectF.top));
            arrayList.add(new RectF(smallRectF.left, $this$createRectanglesWithHole.top, smallRectF.right, smallRectF.top));
            arrayList.add(new RectF(smallRectF.right, $this$createRectanglesWithHole.top, $this$createRectanglesWithHole.right, smallRectF.top));
            arrayList.add(new RectF($this$createRectanglesWithHole.left, smallRectF.top, smallRectF.left, smallRectF.bottom));
            arrayList.add(new RectF(smallRectF.right, smallRectF.top, $this$createRectanglesWithHole.right, smallRectF.bottom));
            arrayList.add(new RectF($this$createRectanglesWithHole.left, smallRectF.bottom, smallRectF.left, $this$createRectanglesWithHole.bottom));
            arrayList.add(new RectF(smallRectF.left, smallRectF.bottom, smallRectF.right, $this$createRectanglesWithHole.bottom));
            arrayList.add(new RectF(smallRectF.right, smallRectF.bottom, $this$createRectanglesWithHole.right, $this$createRectanglesWithHole.bottom));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!((RectF) obj).isEmpty()) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    @NotNull
    public static final RectF toZeroOriginRoundRectF(@NotNull RectF $this$toZeroOriginRoundRectF) {
        Intrinsics.checkNotNullParameter($this$toZeroOriginRoundRectF, "<this>");
        return new RectF(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, FloatKt.roundToIntFloat($this$toZeroOriginRoundRectF.width()), FloatKt.roundToIntFloat($this$toZeroOriginRoundRectF.height()));
    }

    public static final boolean isNaN(@NotNull RectF $this$isNaN) {
        Intrinsics.checkNotNullParameter($this$isNaN, "<this>");
        return Float.isNaN($this$isNaN.width()) || Float.isNaN($this$isNaN.height());
    }

    public static final void inset(@NotNull RectF $this$inset, @NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter($this$inset, "<this>");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        $this$inset.left += rectF.left;
        $this$inset.top += rectF.top;
        $this$inset.right -= rectF.right;
        $this$inset.bottom -= rectF.bottom;
    }

    public static final boolean isOutside(@NotNull RectF $this$isOutside, @NotNull RectF other) {
        Intrinsics.checkNotNullParameter($this$isOutside, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return $this$isOutside.left < other.left || $this$isOutside.right > other.right || $this$isOutside.top < other.top || $this$isOutside.bottom > other.bottom;
    }

    @NotNull
    public static final RectF cropRectToMatchAspect(@NotNull RectF inputRect, @NotNull RectF boundingRect) {
        Intrinsics.checkNotNullParameter(inputRect, "inputRect");
        Intrinsics.checkNotNullParameter(boundingRect, "boundingRect");
        float fWidth = boundingRect.width() / boundingRect.height();
        float fWidth2 = inputRect.width() / inputRect.height();
        RectF rectF = new RectF();
        float f = inputRect.left;
        rectF.left = f;
        rectF.top = inputRect.top;
        if (fWidth > fWidth2) {
            rectF.right = f + inputRect.width();
            rectF.bottom = rectF.top + (inputRect.width() / fWidth);
        } else {
            rectF.right = f + (inputRect.height() * fWidth);
            rectF.bottom = rectF.top + inputRect.height();
        }
        return rectF;
    }

    public static final boolean isNullOrEmpty(@Nullable RectF $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
    }

    @NotNull
    public static final RectF expand(@NotNull RectF $this$expand, float paddingLeftRight, float paddingTopBottom) {
        Intrinsics.checkNotNullParameter($this$expand, "<this>");
        $this$expand.set($this$expand.left - paddingLeftRight, $this$expand.top - paddingTopBottom, $this$expand.right + paddingLeftRight, $this$expand.bottom + paddingTopBottom);
        return $this$expand;
    }
}
