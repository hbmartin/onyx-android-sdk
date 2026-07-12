package com.onyx.android.sdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {
   public static final int INVALID_ID = 0;
   public static final float FLOAT_ONE = 1.0F;
   public static final float FLOAT_HUNDRED = 100.0F;
   private static boolean a;

   private static void a(Throwable throwable) {
      if (a) {
         Debug.e(throwable);
      }
   }

   public static long parseLong(String s) {
      try {
         return Long.parseLong(s);
      } catch (Exception var1) {
         a(var1);
         return 0L;
      }
   }

   public static int parseInt(String s) {
      try {
         return Integer.parseInt(s);
      } catch (Exception var1) {
         a(var1);
         return 0;
      }
   }

   public static int parseInt(String s, int radix) {
      try {
         return Integer.parseInt(s, radix);
      } catch (Exception var2) {
         a(var2);
         return 0;
      }
   }

   public static int parseInt(Integer integer, int defaultValue) {
      if (integer != null) {
         defaultValue = integer;
      }

      return defaultValue;
   }

   public static float parseFloat(String s) {
      try {
         return Float.parseFloat(s);
      } catch (Exception var1) {
         a(var1);
         return 0.0F;
      }
   }

   public static float parseFloat(Float f, float defaultValue) {
      return f != null && !Float.isInfinite(f) && !Float.isNaN(f) ? f : defaultValue;
   }

   public static double parseDouble(String s) {
      try {
         return Double.parseDouble(s);
      } catch (Exception var1) {
         a(var1);
         return 0.0;
      }
   }

   public static String formatFloat(float width) {
      Object[] var1;
      (var1 = new Object[1])[0] = roundFloat(width);
      return String.format("%.2f", var1);
   }

   public static float roundFloat(float width) {
      return roundToNearestOfFive(width);
   }

   public static float roundToNearestOfFive(float width) {
      return roundToNearestMultipleOfFive(Math.round(width * 100.0F)) / 100.0F;
   }

   public static int roundToNearestMultipleOfFive(int num) {
      int var1;
      return (var1 = num % 5) < 3 ? num - var1 : num + (5 - var1);
   }

   public static float extractFloat(String text) {
      Matcher var1;
      return (var1 = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(text)).find() ? parseFloat(var1.group()) : 0.0F;
   }
}
