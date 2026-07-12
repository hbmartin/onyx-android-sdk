package com.onyx.android.sdk.utils;

import android.os.Looper;

public class LooperUtils {
   public static void looperPrepare() {
      if (Looper.getMainLooper() == null) {
         Looper.prepare();
      }
   }
}
