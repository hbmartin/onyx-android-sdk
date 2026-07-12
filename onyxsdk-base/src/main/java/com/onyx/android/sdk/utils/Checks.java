package com.onyx.android.sdk.utils;

import android.os.Looper;

public class Checks {
   public static void ensureInMainThread(boolean throwException) {
      if (Looper.myLooper() != Looper.getMainLooper()) {
         String var1 = "have to called on main thread";
         if (throwException) {
            throw new RuntimeException(var1);
         }

         Debug.w(var1);
      }
   }

   public static void ensureInWorkingThread() {
      if (Looper.myLooper() == Looper.getMainLooper()) {
         throw new RuntimeException("have to called on working thread");
      }
   }
}
