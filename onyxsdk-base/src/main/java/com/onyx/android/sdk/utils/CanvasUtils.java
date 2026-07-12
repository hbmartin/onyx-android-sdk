package com.onyx.android.sdk.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.Nullable;

public class CanvasUtils {
   public static Canvas lockHardwareCanvas(SurfaceHolder holder) {
      return lockHardwareCanvas(holder, null);
   }

   public static Canvas lockHardwareCanvas(SurfaceHolder holder, @Nullable Rect dirty) {
      if (isUseHardwareCanvas()) {
         return holder.lockHardwareCanvas();
      } else {
         return dirty == null ? holder.lockCanvas() : holder.lockCanvas(dirty);
      }
   }

   public static void clipRect(Canvas canvas, Rect rect) {
      if (rect != null && canvas != null && !canvas.isHardwareAccelerated()) {
         canvas.clipRect(rect);
      }
   }

   public static boolean isUseHardwareCanvas() {
      return com.onyx.android.sdk.api.utils.CompatibilityUtil.isApiLevelAbove(29);
   }

   public static void unlockCanvasAndPost(SurfaceView surfaceView, Canvas canvas) {
      if (!surfaceView.getHolder().getSurface().isValid()) {
         Object[] var3 = new Object[0];
         Debug.e(CanvasUtils.class, "surfaceView is invalid", var3);
      } else {
         if (!surfaceView.isAttachedToWindow()) {
            Object[] var2 = new Object[0];
            Debug.e(CanvasUtils.class, "surfaceView is not attach", var2);
         }

         surfaceView.getHolder().unlockCanvasAndPost(canvas);
      }
   }
}
