package com.onyx.android.sdk.utils;

public class BooleanUtils {
   public static String toString(boolean bool, String trueString, String falseString) {
      if (bool) {
         falseString = trueString;
      }

      return falseString;
   }

   public static String toStringTrueFalse(boolean bool) {
      return bool ? "True" : "False";
   }

   public static boolean isChanged(boolean bool1, boolean bool2) {
      return bool1 ^ bool2;
   }
}
