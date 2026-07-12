package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import androidx.annotation.RequiresPermission;

public class BuildUtils {
   @SuppressLint("MissingPermission")
   @RequiresPermission("android.permission.READ_PHONE_STATE")
   public static String getSerial() {
      return VERSION.SDK_INT >= 28 ? Build.getSerial() : Build.SERIAL;
   }
}
