package com.onyx.android.sdk.utils;

import android.content.Context;
import android.hardware.input.InputManager;
import com.onyx.android.sdk.device.Device;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DeviceFeatureUtil {
   private static final boolean a = true;
   private static final boolean b = true;
   private static final List<String> c = Arrays.asList("onyx_emp", "Wacom I2C Digitizer", "hanvon_tp");

   public static boolean hasAudio(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.audio.output");
   }

   public static boolean hasWifi(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.wifi");
   }

   public static boolean hasBluetooth(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
   }

   public static boolean hasTouch(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.touchscreen");
   }

   public static boolean hasFLBrightness(Context context) {
      return Device.currentDevice().hasFLBrightness(context);
   }

   public static boolean hasCTMBrightness(Context context) {
      return Device.currentDevice().hasCTMBrightness(context);
   }

   public static boolean hasFrontLight(Context context) {
      return hasFLBrightness(context) || hasCTMBrightness(context);
   }

   public static boolean hasCamera(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.camera.any");
   }

   public static boolean hasStylus(Context context) {
      if (CompatibilityUtil.apiLevelCheck(16)) {
         InputManager var4;
         if ((var4 = (InputManager)context.getSystemService("input")) != null) {
            int[] var1;
            int var2 = (var1 = var4.getInputDeviceIds()).length;

            for (int var3 = 0; var3 < var2; var3++) {
               if (a(var4.getInputDevice(var1[var3]).getName())) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean a(String deviceName) {
      Iterator var1 = c.iterator();

      while (var1.hasNext()) {
         if (deviceName.contains((String)var1.next())) {
            return true;
         }
      }

      return false;
   }

   public static boolean supportExternalSD(Context context) {
      return Device.currentDevice().supportExternalSD(context);
   }

   public static boolean hasFingerprint(Context context) {
      return CompatibilityUtil.apiLevelCheck(23) ? context.getPackageManager().hasSystemFeature("android.hardware.fingerprint") : false;
   }

   public static boolean useSystemWifiSettingDialog() {
      return true;
   }

   public static boolean supportEAC() {
      return CompatibilityUtil.apiLevelCheck(23);
   }

   public static boolean isLowRamDevice(Context context) {
      return context.getPackageManager().hasSystemFeature("android.hardware.ram.low");
   }
}
