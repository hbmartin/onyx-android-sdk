package com.onyx.android.sdk.utils;

import android.os.Build.VERSION;

public class LogUtils {
   public static final String ATTRIBUTE_SEPARATOR = ",";
   public static final String ITEM_SEPARATOR = "|";

   public static DefaultLogEntry getLogger() {
      int var0 = VERSION.SDK_INT;
      if (VERSION.SDK_INT >= 28) {
         return BuildVersionOver28LogEntry.createLog();
      } else {
         return var0 < 23 ? BuildVersionBelow23LogEntry.createLog() : DefaultLogEntry.createLog();
      }
   }
}
