package com.onyx.android.sdk.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DetectInputDeviceUtil {
   private static final String a = "/proc/bus/input/devices";
   private static final String b = "B:";
   private static final String c = "Handlers";
   private static final List<String> d = new ArrayList<>(Arrays.asList("hanvon_tp", "Wacom"));

   public static String detectInputDevicePath() {
      String var0 = null;
      if (!FileUtils.fileExist("/proc/bus/input/devices")) {
         return var0;
      }

      String var1;
      String[] var3;
      if (StringUtils.isNotBlank(var1 = FileUtils.readContentOfFile(new File("/proc/bus/input/devices")))
         && StringUtils.isNotBlank(var1 = a(d, a(var1, "B:")))
         && (var3 = a(var1, " ")) != null
         && StringUtils.isNotBlank(var1 = a(var3, "Handlers"))) {
         var0 = var1.substring(var1.length() - 1);
      }

      return var0;
   }

   private static String[] a(String content, String regex) {
      return !StringUtils.isNullOrEmpty(content) && regex != null ? content.split(regex) : null;
   }

   private static String a(String[] content, String str) {
      if (content != null && !StringUtils.isNullOrEmpty(str)) {
         Object var2 = null;
         int var3 = content.length;
         int var4 = 0;

         String var5;
         while (true) {
            if (var4 >= var3) {
               var5 = (String)var2;
               break;
            }

            if ((var5 = content[var4]).contains(str)) {
               break;
            }

            var4++;
         }

         return var5;
      } else {
         return null;
      }
   }

   private static String a(List<String> tpList, String[] content) {
      String var2 = null;
      if (tpList != null && content != null) {
         Iterator var3 = tpList.iterator();

         while (var3.hasNext() && !StringUtils.isNotBlank(var2 = a(content, (String)var3.next()))) {
         }
      }

      return var2;
   }
}
