package com.onyx.android.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.data.MultiWindowMode;
import com.onyx.android.sdk.device.Device;

public class MultiWindowUtils {
   public static boolean startActivitySafely(Context context, Intent srcIntent) {
      if (!DeviceUtils.isSystemInMultiWindowMode(context)) {
         return ActivityUtil.startActivitySafely(context, srcIntent);
      }

      if (Device.currentDevice().isEnabledStartActivityInMultiWindowMode(context)) {
         return ActivityUtil.startActivitySafely(context, srcIntent);
      }

      if ((srcIntent.getFlags() & 268435456) == 0) {
         return ActivityUtil.startActivitySafely(context, srcIntent);
      }

      BroadcastHelper.sendStartActivityInMultiWindowCompatM(context, srcIntent);
      return true;
   }

   public static void startMultiWindow(Context context, int primaryTaskId, int createMode, Intent primaryTask, Intent secondaryTask, int splitScreenType) {
      BroadcastHelper.sendStartMultiWindowIntent(context, primaryTaskId, createMode, primaryTask, secondaryTask, splitScreenType);
   }

   public static boolean isInterceptFinishInMultiWindowMode(Activity activity) {
      return isInMultiWindowMode(activity) && !Device.currentDevice().isOriginMultiWindow();
   }

   public static boolean isInMultiWindowMode(Activity activity) {
      if (activity == null) {
         return false;
      }

      boolean var10000 = DeviceUtils.isSystemInMultiWindowMode(activity);
      boolean var2 = Device.currentDevice().isInMultiWindowMode(activity);
      String var10001 = "systemInMultiWindowMode = " + var10000 + ", inActivityMultiWindowMode = " + var2;
      Object[] var1 = new Object[0];
      Debug.d(MultiWindowUtils.class, var10001, var1);
      return var10000 || var2;
   }

   public static boolean isSystemInMultiWindowMode(Context context) {
      return DeviceUtils.isSystemInMultiWindowMode(context);
   }

   public static MultiWindowMode getMultiWindowMode(Activity activity) {
      if (!isInMultiWindowMode(activity)) {
         return MultiWindowMode.NONE;
      } else {
         return ResManager.getWindowDefaultWidth(activity) > ResManager.getWindowDefaultHeight(activity)
            ? MultiWindowMode.TOP_BOTTOM
            : MultiWindowMode.LEFT_RIGHT;
      }
   }

   public static boolean isAllowStateLoss(Context context) {
      return !Device.currentDevice().isOriginMultiWindow() || DeviceUtils.isSystemInMultiWindowMode(context);
   }

   public static boolean isLimitedMultiScreenMode(Context context) {
      return Device.currentDevice().isLimitedMultiScreenMode(context);
   }

   public static boolean isFullFunctionMultiScreenMode(Context context) {
      return Device.currentDevice().isFullFunctionMultiScreenMode(context);
   }

   public static void updateFullScreenInMultiWindowMode(Activity activity) {
      if (!CompatibilityUtil.apiLevelCheck(29)) {
         boolean var1;
         if (!Device.currentDevice().isShowStatusBarInMultiWindowMode() && isInMultiWindowMode(activity)) {
            var1 = true;
         } else {
            var1 = false;
         }

         DeviceUtils.setFullScreenOnResume(activity, var1);
      }
   }
}
