// 
// 

package com.onyx.android.sdk.utils;

import androidx.core.graphics.ColorUtils;
import org.jetbrains.annotations.Nullable;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Bitmap;
import androidx.annotation.ColorInt;
import android.graphics.Color;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007J\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0007J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0007J\n\u0010\u0017\u001a\u00020\u0013*\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0018" }, d2 = { "Lcom/onyx/android/sdk/utils/Colors;", "", "()V", "DARK_COLOR_LUMINANCE_LIMIT", "", "DARK_COLOR_RATIO", "alpha", "", "getAlpha", "(I)I", "calculateLuminance", "foregroundColor", "backgroundColor", "(ILjava/lang/Integer;)D", "convertToGrayscaleWithAlpha", "color", "invertColor", "colorInt", "isColorDark", "", "bitmap", "Landroid/graphics/Bitmap;", "isColorOpaque", "isTransparent", "onyxsdk-base_release" })
public final class Colors
{
    @NotNull
    public static final Colors INSTANCE;
    private static double a;
    private static double b;
    
    private Colors() {
    }
    
    static {
        INSTANCE = new Colors();
        Colors.a = 0.3;
        Colors.b = 0.7;
    }
    
    public final int invertColor(int colorInt) {
        final int red = Color.red(colorInt);
        colorInt = Color.green(colorInt);
        return Color.rgb(255 - red, 255 - colorInt, 255 - Color.blue(colorInt));
    }
    
    public final int getAlpha(@ColorInt final int $this$alpha) {
        return $this$alpha >> 24 & 0xFF;
    }
    
    public final boolean isColorOpaque(final int color) {
        return Color.alpha(color) == 255;
    }
    
    public final int convertToGrayscaleWithAlpha(int color) {
        final int alpha = Color.alpha(color);
        final int n = color;
        final int red = Color.red(n);
        color = Color.green(n);
        final int n2 = (int)(red * 0.299 + color * 0.587 + Color.blue(n) * 0.114);
        return Color.argb(alpha, n2, n2, n2);
    }
    
    public final boolean isTransparent(final int $this$isTransparent) {
        return Color.alpha($this$isTransparent) == 0;
    }
    
    public final boolean isColorDark(@NotNull final Bitmap bitmap) {
        Intrinsics.checkNotNullParameter((Object)bitmap, "bitmap");
        int n = 0;
        int n2 = 0;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final IntProgression step = RangesKt.step((IntProgression)RangesKt.until(0, width), 10);
        int first = step.getFirst();
        final int last = step.getLast();
        final int step2;
        if (((step2 = step.getStep()) > 0 && first <= last) || (step2 < 0 && last <= first)) {
            while (true) {
                final int n3 = first + step2;
                final IntProgression step3 = RangesKt.step((IntProgression)RangesKt.until(0, height), 10);
                int first2 = step3.getFirst();
                final int last2 = step3.getLast();
                final int step4;
                if (((step4 = step3.getStep()) > 0 && first2 <= last2) || (step4 < 0 && last2 <= first2)) {
                    while (true) {
                        final int n4 = first;
                        final int n5 = first2 + step4;
                        ++n2;
                        final int pixel;
                        if (!this.isTransparent(pixel = bitmap.getPixel(n4, first2))) {
                            Integer value;
                            if (this.isColorOpaque(pixel)) {
                                value = null;
                            }
                            else {
                                value = -1;
                            }
                            if (this.calculateLuminance(pixel, value) < Colors.a) {
                                ++n;
                            }
                        }
                        if (first2 == last2) {
                            break;
                        }
                        first2 = n5;
                    }
                }
                if (first == last) {
                    break;
                }
                first = n3;
            }
        }
        return n / (double)n2 > Colors.b;
    }
    
    public final double calculateLuminance(int foregroundColor, @Nullable final Integer backgroundColor) {
        if (backgroundColor != null) {
            foregroundColor = ColorUtils.compositeColors(foregroundColor, (int)backgroundColor);
        }
        return ColorUtils.calculateLuminance(foregroundColor);
    }

    public static /* synthetic */ double calculateLuminance$default(final Colors colors, final int foregroundColor, Integer backgroundColor, final int i, final Object obj) {
        if ((i & 2) != 0) {
            backgroundColor = -1;
        }
        return colors.calculateLuminance(foregroundColor, backgroundColor);
    }
}
