package com.onyx.android.sdk.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.device.Device;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TaskUtils {
   public static final int SPLIT_SCREEN_CREATE_MODE_TOP_OR_LEFT = 0;
   public static final int SPLIT_SCREEN_CREATE_MODE_BOTTOM_OR_RIGHT = 1;
   public static final int INVALID_TASK_ID = -1;

   public static List<RunningTaskInfo> getTasks(Context context) {
      return getTasks(context, Integer.MAX_VALUE);
   }

   public static List<RunningTaskInfo> getTasks(Context context, int maxNum) {
      ActivityManager var2;
      return (var2 = getActivityManager(context)) == null ? Collections.emptyList() : var2.getRunningTasks(maxNum);
   }

   public static ActivityManager getActivityManager(Context context) {
      return (ActivityManager)context.getSystemService("activity");
   }

   public static boolean isPrimaryWindowingMode(Context context, int taskId) {
      return !isValidTaskId(taskId) ? false : Device.currentDevice().isPrimaryTaskInMultiWindowMode(context, taskId);
   }

   public static boolean isServiceRunning(Context context, String serviceName) {
      ActivityManager var2;
      if ((var2 = getActivityManager(context)) == null) {
         return false;
      }

      Iterator var3 = var2.getRunningServices(Integer.MAX_VALUE).iterator();

      while (var3.hasNext()) {
         if (serviceName.equals(((RunningServiceInfo)var3.next()).service.getClassName())) {
            return true;
         }
      }

      return false;
   }

   public static boolean isActivityRunning(Context context, String activity) {
      return getTaskInfo(context, activity) != null;
   }

   @Nullable
   public static RunningTaskInfo getTaskInfo(Context context, String activity) {
      ActivityManager var3;
      if ((var3 = getActivityManager(context)) == null) {
         return null;
      }

      Iterator var4 = var3.getRunningTasks(Integer.MAX_VALUE).iterator();

      while (var4.hasNext()) {
         RunningTaskInfo var2;
         if (activity.equalsIgnoreCase((var2 = (RunningTaskInfo)var4.next()).topActivity.getClassName())) {
            return var2;
         }
      }

      return null;
   }

   public static RunningTaskInfo getTopTask(Context context) {
      List var1;
      return CollectionUtils.isNullOrEmpty(var1 = getTasks(context, 1)) ? null : (RunningTaskInfo)var1.get(0);
   }

   public static boolean isValidTaskId(int taskId) {
      return taskId != -1;
   }

   public static Rect getTaskBounds(Context context, int taskId) {
      return Device.currentDevice().getTaskBounds(context, taskId);
   }

   public static int getTaskSplitScreenCreateMode(Context context, int taskId) {
      return getTaskSplitScreenCreateMode(getTaskBounds(context, taskId));
   }

   public static int getTaskSplitScreenCreateMode(Rect taskBounds) {
      if (RectUtils.isNullOrEmpty(taskBounds)) {
         return 0;
      } else {
         return taskBounds.left == 0 ? 0 : 1;
      }
   }
}
