package com.onyx.android.sdk.utils;

import android.graphics.PointF;
import android.text.TextUtils;

public class MathUtils {
   public static final float ANGLE_180 = 180.0F;

   public static double distance(float x1, float y1, float x2, float y2) {
      return Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
   }

   public static float calculateAngle(PointF center, PointF current, PointF previous) {
      float var10001 = current.x - center.x;
      float var3;
      float var10002 = var3 = current.x - center.x;
      float var10 = current.y - center.y;
      double var4 = Math.sqrt(var10001 * var10002 + var10 * var10);
      float var11 = (float)(var3 / var4);
      var3 = (float)(var10 / var4);
      float var10000 = previous.x - center.x;
      float var13;
      var10001 = var13 = previous.x - center.x;
      float var7 = previous.y - center.y;
      double var5 = Math.sqrt(var10000 * var10001 + var7 * var7);
      float var8 = (float)(var13 / var5);
      float var9;
      if (!((var9 = (float)Math.toDegrees(Math.atan2((float)(var7 / var5), var8) - Math.atan2(var3, var11))) > 0.0F)) {
         var9 += 360.0F;
      }

      return var9;
   }

   public static float calculateAngle(double startX, double startY, double endX, double endY) {
      return -radianToAngle((float)Math.atan2(endY - startY, endX - startX));
   }

   public static PointF calculateMiddlePointFromTwoPoint(double p1X, double p1Y, double p2X, double p2Y) {
      float var8 = (float)((p1X + p2X) / 2.0);
      return new PointF(var8, (float)(p1Y + p2Y) / 2.0F);
   }

   public static PointF calculateTriangleCentroidPoint(PointF p1, PointF p2, PointF p3) {
      return new PointF((p1.x + p2.x + p3.x) / 3.0F, (p1.y + p2.y + p3.y) / 3.0F);
   }

   public static long parseLong(String str) {
      try {
         return Long.parseLong(str);
      } catch (Exception var1) {
         return 0L;
      }
   }

   public static int parseInt(String str) {
      try {
         return Integer.parseInt(str);
      } catch (Exception var1) {
         return 0;
      }
   }

   public static float parseFloat(String string) {
      String var10000 = string;
      float var5 = 0.0F;

      try {
         return Float.valueOf(var10000);
      } catch (Exception var3) {
         return var5;
      } finally {
         return var5;
      }
   }

   public static boolean floatCompare(float f1, float f2) {
      return Float.compare(f1, f2) == 0;
   }

   public static boolean withinRange(int number, int start, int end) {
      int var3 = Math.min(start, end);
      end = Math.max(start, end);
      return number >= var3 && number < end;
   }

   public static boolean withinRange(float number, float start, float end) {
      float var3 = Math.min(start, end);
      end = Math.max(start, end);
      return number >= var3 && number < end;
   }

   public static float clampFloat(float target, float min, float max) {
      return Math.max(min, Math.min(max, target));
   }

   public static float radianToAngle(float radian) {
      return (float)Math.toDegrees(radian);
   }

   public static float angleToRadian(float angle) {
      return (float)Math.toRadians(angle);
   }

   public static int getLimitedValue(int value, int increaseDiff, int maxLimit) {
      if ((value = value + increaseDiff) >= maxLimit) {
         value = maxLimit;
      }

      return value;
   }

   public static float sin(float radius, float angle) {
      return (float)(Math.sin(angleToRadian(angle)) * radius);
   }

   public static float cos(float radius, float angle) {
      return (float)(Math.cos(angleToRadian(angle)) * radius);
   }

   public static int max(int... numbers) {
      int var1 = numbers[0];
      int var2 = numbers.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1 = Math.max(var1, numbers[var3]);
      }

      return var1;
   }

   public static long max(long... numbers) {
      long var1 = numbers[0];
      int var3 = numbers.length;

      for (int var4 = 0; var4 < var3; var4++) {
         var1 = Math.max(var1, numbers[var4]);
      }

      return var1;
   }

   public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
      if (max.compareTo(value) > 0) {
         max = value;
      }

      if (min.compareTo(max) > 0) {
         max = min;
      }

      return max;
   }

   public static int calculateRow(int size, int col) {
      if (col <= 0) {
         return 0;
      }

      size /= col;
      if (size % col != 0) {
         size++;
      }

      return size;
   }

   public static float calculatePercent(Number used, Number total) {
      return total.floatValue() <= 0.0F ? 0.0F : used.floatValue() * 100.0F / total.floatValue();
   }

   public static int tryParseInt(String value, int defValue) {
      if (TextUtils.isEmpty(value)) {
         return defValue;
      }

      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var2) {
         return defValue;
      }
   }

   public static boolean withinRangeIncluded(int value, int min, int max) {
      int var3 = Math.min(min, max);
      max = Math.max(min, max);
      return value >= var3 && value <= max;
   }

   public static boolean isZero(float value) {
      return Math.abs(value) < 1.0E-6;
   }
}
