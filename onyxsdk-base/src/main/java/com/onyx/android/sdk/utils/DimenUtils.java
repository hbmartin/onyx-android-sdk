package com.onyx.android.sdk.utils;

import android.content.Context;

public class DimenUtils {
   public static int dip2px(Context context, float dpValue) {
      return (int)(dpValue * context.getResources().getDisplayMetrics().density + 0.5F);
   }

   public static int px2dip(Context context, float pxValue) {
      return (int)(pxValue / context.getResources().getDisplayMetrics().density + 0.5F);
   }

   public static int sp2px(Context context, float spValue) {
      return (int)(spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
   }

   public static int px2sp(Context context, float pxValue) {
      return (int)(pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
   }

   public static int getCssPx(Context context, float pxValue) {
      return (int)(pxValue / context.getResources().getDisplayMetrics().density) * 96 / 160;
   }

   public static float pt2px(Context context, float pt) {
      return pt * context.getResources().getDisplayMetrics().densityDpi / 72.0F;
   }

   public static float px2pt(Context context, float px) {
      return px * 72.0F / context.getResources().getDisplayMetrics().densityDpi;
   }
}
