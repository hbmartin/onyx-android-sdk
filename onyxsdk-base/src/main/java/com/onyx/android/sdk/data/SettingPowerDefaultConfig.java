package com.onyx.android.sdk.data;

public class SettingPowerDefaultConfig {
   private int a = -1;
   private int b = -1;
   private int c = -1;
   private int d = -1;
   private boolean e;
   private boolean f;

   public int getScreenTimeout() {
      return this.a;
   }

   public void setScreenTimeout(int screenTimeout) {
      this.a = screenTimeout;
   }

   public int getPowerOffTimeout() {
      return this.b;
   }

   public void setPowerOffTimeout(int powerOffTimeout) {
      this.b = powerOffTimeout;
   }

   public int getWifiInactivityTimeout() {
      return this.c;
   }

   public void setWifiInactivityTimeout(int wifiInactivityTimeout) {
      this.c = wifiInactivityTimeout;
   }

   public boolean isOpenFrontLight() {
      return this.f;
   }

   public void setOpenFrontLight(boolean openFrontLight) {
      this.f = openFrontLight;
   }

   public boolean isEnablePowerSavedMode() {
      return this.e;
   }

   public void setEnablePowerSavedMode(boolean enablePowerSavedMode) {
      this.e = enablePowerSavedMode;
   }

   public int getLowPowerWorkTimeout() {
      return this.d;
   }

   public void setLowPowerWorkTimeout(int lowPowerWorkTimeout) {
      this.d = lowPowerWorkTimeout;
   }
}
