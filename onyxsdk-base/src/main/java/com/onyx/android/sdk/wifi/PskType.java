package com.onyx.android.sdk.wifi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PskType {
   public static final int UNKNOWN = 0;
   public static final int WPA = 1;
   public static final int WPA2 = 2;
   public static final int WPA_WPA2 = 3;

   public static int translate(int val) {
      return val;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface PskTypeDef {
   }
}
