package com.onyx.android.sdk.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DeviceType {
   public static final int IMX6 = 0;
   public static final int RK = 1;
   public static final int SDM = 2;

   public static int translate(int val) {
      return val;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface DeviceTypeDef {
   }
}
