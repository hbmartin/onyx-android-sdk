package com.onyx.android.sdk.data;

import com.onyx.android.sdk.device.EnvironmentUtil;

public class AppsConstant {
   public static final String DOWNLOAD_ONYX_DIR;
   public static final String ONYX_CUSTOM_ICON_CACHE_DIR;
   public static final String ICON_SUFFIX = ".png";
   public static final String ONYX_TS_CALIBRATION_PACKAGE_NAME = "com.onyx.tscalibration";
   public static final String ONYX_TS_CALIBRATION_CLASS_NAME = "com.onyx.tscalibration.MainActivity";

   static {
      String var0;
      DOWNLOAD_ONYX_DIR = var0 = EnvironmentUtil.getExternalStorageDownloadDirectory() + "/Onyx";
      ONYX_CUSTOM_ICON_CACHE_DIR = var0 + "/icon";
   }
}
