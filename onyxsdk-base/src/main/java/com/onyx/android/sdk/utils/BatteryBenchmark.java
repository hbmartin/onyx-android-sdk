package com.onyx.android.sdk.utils;

import android.content.Context;

public class BatteryBenchmark {
   private double a = PowerUtil.getBatteryCapacity(this.a());
   private int b;
   private int c;

   private Context a() {
      return ResManager.getAppContext();
   }

   private int b() {
      return DeviceUtils.getBatteryPercentLevel(this.a());
   }

   public void restart() {
      this.b = this.b();
   }

   public double usage() {
      int var1;
      int var10000 = var1 = this.b();
      this.c = var1;
      return (var10000 - this.b) * this.a;
   }

   public void report(String msg) {
      boolean var2 = PowerUtil.isBatteryCharging(this.a());
      double var3 = this.usage();
      Debug.w(
         this.getClass(),
         msg
            + ", batteryCharging = "
            + var2
            + ", usage = "
            + var3
            + ", startPercent = "
            + this.b
            + ", endPercent = "
            + this.c
            + ", batteryCapacity = "
            + this.a,
         new Object[0]
      );
   }
}
