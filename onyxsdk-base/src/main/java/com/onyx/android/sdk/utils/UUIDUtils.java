package com.onyx.android.sdk.utils;

import com.fasterxml.uuid.Generators;
import java.util.UUID;

public class UUIDUtils {
   public static String randomUUID() {
      return UUID.randomUUID().toString().replaceAll("-", "");
   }

   public static String randomRawUUID() {
      return UUID.randomUUID().toString();
   }

   public static String timeBasedEpochUUID() {
      return Generators.timeBasedEpochGenerator().generate().toString();
   }

   public static int compareTo(String timeUUID1, String timeUUID2) {
      return UUID.fromString(timeUUID1).compareTo(UUID.fromString(timeUUID2));
   }

   public static int safelyCompareTo(String timeUUID1, String timeUUID2) {
      try {
         return compareTo(timeUUID1, timeUUID2);
      } catch (Exception var2) {
         var2.printStackTrace();
         return 0;
      }
   }
}
