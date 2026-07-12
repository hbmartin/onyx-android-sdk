package com.onyx.android.sdk.utils;

import java.lang.reflect.Method;

public class OnyxSystemProperties {
   private static final String a = "android.os.SystemProperties";
   private static Class<?> b;
   private static Method c;
   private static Method d;
   private static Method e;

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static void a() {
      Class var10000;
      try {
         var10000 = b;
      } catch (Exception var10) {
         var10.printStackTrace();
         return;
      }

      if (var10000 == null) {
         try {
            var10000 = b = Class.forName("android.os.SystemProperties");
         } catch (Exception var9) {
            var9.printStackTrace();
            return;
         }

         String var10001 = "get";

         Class[] var10002;
         try {
            var10002 = new Class[1];
         } catch (Exception var8) {
            var8.printStackTrace();
            return;
         }

         Class[] var10003 = var10002;
         byte var10004 = 0;

         try {
            var10003[var10004] = String.class;
            c = var10000.getDeclaredMethod(var10001, var10002);
            var10000 = b;
         } catch (Exception var7) {
            var7.printStackTrace();
            return;
         }

         var10001 = "getInt";

         try {
            var10002 = new Class[2];
         } catch (Exception var6) {
            var6.printStackTrace();
            return;
         }

         var10003 = var10002;
         Class[] var20 = var10002;
         byte var10005 = 0;

         try {
            var20[var10005] = String.class;
         } catch (Exception var5) {
            var5.printStackTrace();
            return;
         }

         var10004 = 1;

         try {
            var10003[var10004] = int.class;
            d = var10000.getDeclaredMethod(var10001, var10002);
            var10000 = b;
         } catch (Exception var4) {
            var4.printStackTrace();
            return;
         }

         var10001 = "set";

         try {
            var10002 = new Class[2];
         } catch (Exception var3) {
            var3.printStackTrace();
            return;
         }

         var10003 = var10002;
         Class[] var22 = var10002;
         var10005 = 0;

         try {
            var22[var10005] = String.class;
         } catch (Exception var2) {
            var2.printStackTrace();
            return;
         }

         var10004 = 1;

         try {
            var10003[var10004] = String.class;
            e = var10000.getDeclaredMethod(var10001, var10002);
         } catch (Exception var1) {
            var1.printStackTrace();
         }
      }
   }

   public static boolean set(String key, String value) {
      a();

      try {
         e.invoke(b, key, value);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return false;
   }

   public static String get(String key, String defaultValue) {
      a();

      String var10000;
      try {
         var10000 = (String)c.invoke(b, key);
      } catch (Exception var2) {
         var2.printStackTrace();
         return defaultValue;
      }

      return var10000;
   }
}
