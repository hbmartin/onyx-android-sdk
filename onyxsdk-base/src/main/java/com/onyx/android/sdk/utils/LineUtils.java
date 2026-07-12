package com.onyx.android.sdk.utils;

import androidx.annotation.FloatRange;

public class LineUtils {
   private static final int a = 30;
   private static final int b = 30;
   private static final int c = 90;

   @FloatRange(from = 0.0, to = 90.0)
   public static float getHorizontalAngle(float startX, float startY, float endX, float endY) {
      return getHorizontalAngle(endX - startX, startY - endY);
   }

   @FloatRange(from = 0.0, to = 90.0)
   public static float getHorizontalAngle(float dx, float dy) {
      return (float)(Math.atan2(Math.abs(dy), Math.abs(dx)) * 180.0 / Math.PI);
   }

   public static boolean isHorizontalLine(float dx, float dy) {
      return getHorizontalAngle(dx, dy) <= 30.0F;
   }

   public static boolean isVerticalLine(float dx, float dy) {
      return 90.0F - getHorizontalAngle(dx, dy) <= 30.0F;
   }
}
