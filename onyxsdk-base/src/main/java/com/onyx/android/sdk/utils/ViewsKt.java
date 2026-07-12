// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.Rect;
import org.jetbrains.annotations.NotNull;
import android.view.View;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000:\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0007\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\f\u001a\u00020\t*\u00020\r2\u0006\u0010\n\u001a\u00020\u000b\u001a\u001e\u0010\u000e\u001a\u00020\u0003*\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013" }, d2 = { "LIMIT_IMAGE_BITMAP_SIZE", "", "isViewCenterInRect", "", "Landroid/view/View;", "rect", "Landroid/graphics/Rect;", "isViewIntersectingRect", "scaleSize", "", "scale", "", "scaleTextSize", "Landroid/widget/TextView;", "setImageLimitBitmap", "Landroid/widget/ImageView;", "bitmap", "Landroid/graphics/Bitmap;", "limitBitmapSize", "onyxsdk-base_release" })
public final class ViewsKt
{
    private ViewsKt() {
    }

    public static final long LIMIT_IMAGE_BITMAP_SIZE = 5242880L;
    
    public static final boolean isViewIntersectingRect(@NotNull final View $this$isViewIntersectingRect, @NotNull final Rect rect) {
        Intrinsics.checkNotNullParameter((Object)$this$isViewIntersectingRect, "<this>");
        Intrinsics.checkNotNullParameter((Object)rect, "rect");
        final Rect rect2;
        $this$isViewIntersectingRect.getGlobalVisibleRect(rect2 = new Rect());
        return Rect.intersects(rect, rect2);
    }
    
    public static final boolean isViewCenterInRect(@NotNull final View $this$isViewCenterInRect, @NotNull final Rect rect) {
        Intrinsics.checkNotNullParameter((Object)$this$isViewCenterInRect, "<this>");
        Intrinsics.checkNotNullParameter((Object)rect, "rect");
        final int[] array = new int[2];
        $this$isViewCenterInRect.getLocationOnScreen(array);
        return rect.contains(array[0] + $this$isViewCenterInRect.getWidth() / 2, array[1] + $this$isViewCenterInRect.getHeight() / 2);
    }
    
    public static final void scaleTextSize(@NotNull final TextView $this$scaleTextSize, final float scale) {
        Intrinsics.checkNotNullParameter((Object)$this$scaleTextSize, "<this>");
        $this$scaleTextSize.setTextSize(0, $this$scaleTextSize.getTextSize() * scale);
    }
    
    public static final void scaleSize(@NotNull final View $this$scaleSize, final float scale) {
        Intrinsics.checkNotNullParameter((Object)$this$scaleSize, "<this>");
        final LayoutParams layoutParams = $this$scaleSize.getLayoutParams();
        layoutParams.width *= (int)scale;
        layoutParams.height *= (int)scale;
        $this$scaleSize.setLayoutParams(layoutParams);
    }
    
    public static final boolean setImageLimitBitmap(@NotNull final ImageView $this$setImageLimitBitmap, @Nullable final Bitmap bitmap, final long limitBitmapSize) {
        Intrinsics.checkNotNullParameter((Object)$this$setImageLimitBitmap, "<this>");
        if (BitmapUtils.getSizeInBytes(bitmap) > limitBitmapSize) {
            $this$setImageLimitBitmap.setImageBitmap((Bitmap)null);
            return false;
        }
        $this$setImageLimitBitmap.setImageBitmap(bitmap);
        return true;
    }

    public static /* synthetic */ boolean setImageLimitBitmap$default(final ImageView imageView, final Bitmap bitmap, long limitBitmapSize, final int i, final Object obj) {
        if ((i & 2) != 0) {
            limitBitmapSize = 5242880L;
        }
        return setImageLimitBitmap(imageView, bitmap, limitBitmapSize);
    }
}
