package com.onyx.android.sdk.utils;

import android.graphics.PointF;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.note.TouchPoint;

public class PointUtils {
   public static boolean isPointInPolygon(PointF[] pts, float x, float y) {
      int var3;
      int var10000 = var3 = pts.length;
      int var4 = 0;
      int var5 = var10000 - 1;
      int var6 = var5;
      var5 = var4;

      while (var5 < var3) {
         if ((pts[var5].y <= y && y < pts[var6].y || pts[var6].y <= y && y < pts[var5].y)
            && x < (pts[var6].x - pts[var5].x) * (y - pts[var5].y) / (pts[var6].y - pts[var5].y) + pts[var5].x) {
            return true;
         }

         var4 = var5 + 1;
         var6 = var5;
         var5 = var4;
      }

      return false;
   }

   public static void scale(PointF point, float scale) {
      if (point != null) {
         point.x *= scale;
         point.y *= scale;
      }
   }

   public static void scale(PointF point, float scaleX, float scaleY) {
      if (point != null) {
         point.x *= scaleX;
         point.y *= scaleY;
      }
   }

   public static boolean isPointInsideCircle(float pointX, float pointY, float centerX, float centerY, float radius) {
      float var10000 = pointX - centerX;
      float var10001 = pointX - centerX;
      pointX = pointY - centerY;
      return (float)Math.sqrt(var10000 * var10001 + pointX * pointX) <= radius;
   }

   public static TouchPoint createTouchPoint(float x, float y) {
      float var10003 = TestUtils.randInt(0, (int)EpdController.MAX_TOUCH_PRESSURE);
      long var2 = System.currentTimeMillis();
      return new TouchPoint(x, y, var10003, 0.0F, var2);
   }
}
