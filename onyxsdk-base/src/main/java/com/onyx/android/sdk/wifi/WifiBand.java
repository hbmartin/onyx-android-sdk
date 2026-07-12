package com.onyx.android.sdk.wifi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WifiBand {
   public static final int UNKNOWN = -1;
   public static final int B_G_N_NETWORK = 0;
   public static final int A_H_J_N_AC_NETWORK = 1;

   public static int translate(int val) {
      return val;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface WifiBandDef {
   }
}
