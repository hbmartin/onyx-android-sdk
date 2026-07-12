package com.onyx.android.sdk.utils;

import android.os.Build.VERSION;

public class CompatibilityUtil {
   public static final int VERSION_CODE_P = 28;
   public static final int VERSION_CODE_Q = 29;
   public static final int VERSION_CODE_R = 30;
   public static final int VERSION_CODE_S = 31;
   public static final int VERSION_CODE_T = 32;

   public static boolean apiLevelCheck(int requireAPILevel) {
      return VERSION.SDK_INT >= requireAPILevel;
   }

   public static boolean isRequireApiLevelT() {
      return VERSION.SDK_INT >= 32;
   }
}
