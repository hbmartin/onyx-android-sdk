package com.onyx.android.sdk.data.point;

import androidx.annotation.Nullable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class PointDocumentUtils {
   private static final int a = 3;

   public static String getPointFileName(String pageUniqueId, String revisionId) {
      return pageUniqueId + "#" + revisionId + "#" + "points";
   }

   public static String parseRevisionId(String fileName) {
      String[] var1;
      return (var1 = fileName.split("#")).length != 3 ? null : var1[1];
   }

   @Nullable
   public static Entry<String, String> parseRevisionPageId(String fileName) {
      String[] var1;
      if ((var1 = fileName.split("#")).length != 3) {
         return null;
      }

      fileName = var1[1];
      return new SimpleEntry<>(fileName, var1[0]);
   }

   public static int getPointCountBreakValue(int pointCount) {
      return Math.max(1, Math.round(pointCount * 1.0F / 20000.0F));
   }
}
