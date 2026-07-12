package com.onyx.android.sdk.base.lite.extension;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.onyx.android.sdk.base.utils.Debug;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Bitmap.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/lite/extension/BitmapKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001d\u0010\u0007\u001a\u00020\u0006*\u0004\u0018\u00010\u0001\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000¨\u0006\b"}, d2 = {"createScaledBitmap", "Landroid/graphics/Bitmap;", "newWidth", "", "newHeight", "isWhiteBG", "", "isValid", "sdk-baselite_release"})
public final class BitmapKt {
    public static final boolean isValid(@Nullable Bitmap $this$isValid) {
        return $this$isValid != null && !$this$isValid.isRecycled() && $this$isValid.getWidth() > 0 && $this$isValid.getHeight() > 0;
    }

    @NotNull
    public static final Bitmap createScaledBitmap(@NotNull Bitmap $this$createScaledBitmap, int newWidth, int newHeight) {
        Intrinsics.checkNotNullParameter($this$createScaledBitmap, "<this>");
        return createScaledBitmap($this$createScaledBitmap, newWidth, newHeight, false);
    }

    @NotNull
    public static final Bitmap createScaledBitmap(@NotNull Bitmap $this$createScaledBitmap, int newWidth, int newHeight, boolean isWhiteBG) {
        Intrinsics.checkNotNullParameter($this$createScaledBitmap, "<this>");
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        if (!isValid($this$createScaledBitmap)) {
            Debug.INSTANCE.e(new IllegalArgumentException("invalid bitmap"));
            Intrinsics.checkNotNullExpressionValue(scaledBitmap, "scaledBitmap");
            return scaledBitmap;
        }
        if (isWhiteBG) {
            scaledBitmap.eraseColor(-1);
        }
        float ratioX = newWidth / $this$createScaledBitmap.getWidth();
        float ratioY = newHeight / $this$createScaledBitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap($this$createScaledBitmap, middleX - ($this$createScaledBitmap.getWidth() / 2), middleY - ($this$createScaledBitmap.getHeight() / 2), new Paint(2));
        Intrinsics.checkNotNullExpressionValue(scaledBitmap, "scaledBitmap");
        return scaledBitmap;
    }
}
