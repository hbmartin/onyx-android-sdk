package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader.Feature;
import java.util.Iterator;
import java.util.Locale;

public class ConfigLoader {
   private static final String a = "ConfigLoader";
   private static final Context b = ApplicationUtil.getApplicationContext();
   public static final int MODEL_CONFIG_FULL_REPLACE_MODE = 0;
   public static final int MODEL_CONFIG_PARTIAL_REPLACE_MODE = 1;

   private static String a(String name) {
      Context var1 = b;
      if (b == null) {
         Debug.e(a, "getConfigJSONByName with empty context", new Object[0]);
         return "";
      } else {
         Resources var10001 = var1.getResources();
         name = name.toLowerCase(Locale.US);
         String var3 = var1.getPackageName();
         return RawResourceUtil.contentOfRawResource(var1, var10001.getIdentifier(name, "raw", var3));
      }
   }

   @Nullable
   private static <T> T a(Class<T> cls, String name) {
      String var2;
      if (TextUtils.isEmpty(var2 = a(name))) {
         Debug.e(a, "loadObjectByName to" + cls.getSimpleName() + "failed, " + name + "file not found", new Object[0]);
         return null;
      } else {
         return JSONUtils.parseObject(var2, cls);
      }
   }

   @Nullable
   private static <T> T b(Class<T> cls, String targetResName, String baseResName) {
      targetResName = a(targetResName);
      String var3 = a(baseResName);
      if (TextUtils.isEmpty(targetResName)) {
         return a(cls, baseResName);
      }

      if (TextUtils.isEmpty(var3)) {
         Debug.e(a, "loadObjectByPartialReplace failed, base config is missing", new Object[0]);
         return null;
      }

      JSONObject var6 = JSONUtils.parseObject(targetResName);
      JSONObject var7 = JSONObject.parseObject(var3);
      Iterator var8 = var6.keySet().iterator();

      while (var8.hasNext()) {
         String var11 = (String) var8.next();
         var7.put(var11, var6.get(var11));
      }

      return (T)var7.toJavaObject(cls, new Feature[0]);
   }

   @Nullable
   private static <T> T a(Class<T> cls, String targetResName, String fallbackResName) {
      Object var3;
      return (T)((var3 = a(cls, targetResName)) != null ? var3 : a(cls, fallbackResName));
   }

   @Nullable
   public static <T> T load(int mode, Class<T> cls, String targetResName, String fallbackResName) {
      return mode != 1 ? a(cls, targetResName, fallbackResName) : b(cls, targetResName, fallbackResName);
   }
}
