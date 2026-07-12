package com.onyx.android.sdk.utils;

import android.content.IntentFilter;

public class IntentFilterFactory {
   public static final String ACTION_MEDIA_SCANNED = "com.onyx.android.intent.action.ACTION_MEDIA_SCANNED";
   public static final String ACTION_OPEN_FRONT_LIGHT = "OPEN_FRONT_LIGHT";
   public static final String ACTION_CLOSE_FRONT_LIGHT = "CLOSE_FRONT_LIGHT";
   public static final String INTENT_FRONT_LIGHT_VALUE = "FRONT_LIGHT_VALUE";
   public static final String ACTION_GET_APPLICATION_PREFERENCE = "com.onyx.android.sdk.data.IntentFactory.ACTION_GET_APPLICATION_PREFERENCE";
   public static final String ACTION_EXTRACT_METADATA = "com.onyx.android.sdk.data.IntentFactory.ACTION_EXTRACT_METADATA";
   public static final String ACTION_GET_TOC = "com.onyx.android.sdk.data.IntentFactory.ACTION_GET_TOC";
   private static final IntentFilter a;
   private static final IntentFilter b;
   private static final IntentFilter c;
   private static final IntentFilter d;
   private static final IntentFilter e;
   private static IntentFilter f;

   public static IntentFilter getSDCardUnmountedFilter() {
      return a;
   }

   public static IntentFilter getMediaMountedFilter() {
      return b;
   }

   public static IntentFilter getMediaScannedFilter() {
      return c;
   }

   public static IntentFilter getBootCompletedFilter() {
      return d;
   }

   public static IntentFilter getIntentFilterFrontPreferredApplications() {
      (f = new IntentFilter()).addAction("com.onyx.android.sdk.data.IntentFactory.ACTION_GET_APPLICATION_PREFERENCE");
      return f;
   }

   public static IntentFilter getOpenAndCloseFrontLightFilter() {
      return e;
   }

   static {
      IntentFilter var10000 = a = new IntentFilter();
      var10000.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
      var10000.addAction("android.intent.action.MEDIA_REMOVED");
      var10000.addAction("android.intent.action.MEDIA_SHARED");
      var10000.addAction("android.intent.action.MEDIA_UNMOUNTABLE");
      var10000.addAction("android.intent.action.MEDIA_UNMOUNTED");
      var10000.addDataScheme("file");
      var10000 = b = new IntentFilter();
      var10000.addAction("android.intent.action.MEDIA_MOUNTED");
      var10000.addDataScheme("file");
      (c = new IntentFilter()).addAction("com.onyx.android.intent.action.ACTION_MEDIA_SCANNED");
      (d = new IntentFilter()).addAction("android.intent.action.BOOT_COMPLETED");
      var10000 = e = new IntentFilter();
      var10000.addAction("OPEN_FRONT_LIGHT");
      var10000.addAction("CLOSE_FRONT_LIGHT");
   }
}
