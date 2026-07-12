package com.onyx.android.sdk.data;

public class DrmType {
   public static final int UNKNOWN = 0;
   public static final int NONE = 1;
   public static final int BUILTIN_PASSWORD = 2;

   public static boolean isDrmDocument(int drmType) {
      return drmType == 2;
   }
}
