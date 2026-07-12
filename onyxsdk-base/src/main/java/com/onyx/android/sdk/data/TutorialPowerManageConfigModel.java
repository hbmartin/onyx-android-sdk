package com.onyx.android.sdk.data;

import java.util.List;

public class TutorialPowerManageConfigModel {
   private List<String> a;
   private String b;
   private String c;
   private String d;
   private boolean e;
   private String f;
   private List<Integer> g;
   private List<Integer> h;
   private List<Integer> i;

   public boolean isPowerSaveMode() {
      return this.e;
   }

   public void setPowerSaveMode(boolean powerSaveMode) {
      this.e = powerSaveMode;
   }

   public List<String> getTutorialSettingSequence() {
      return this.a;
   }

   public void setTutorialSettingSequence(List<String> tutorialSettingSequence) {
      this.a = tutorialSettingSequence;
   }

   public String getSystemScreenOffKey() {
      return this.b;
   }

   public void setSystemScreenOffKey(String systemScreenOffKey) {
      this.b = systemScreenOffKey;
   }

   public String getSystemPowerOffKey() {
      return this.c;
   }

   public void setSystemPowerOffKey(String systemPowerOffKey) {
      this.c = systemPowerOffKey;
   }

   public String getSystemWakeUpFrontLightKey() {
      return this.d;
   }

   public void setSystemWakeUpFrontLightKey(String systemWakeUpFrontLightKey) {
      this.d = systemWakeUpFrontLightKey;
   }

   public String getSystemWifiInactivityKey() {
      return this.f;
   }

   public void setSystemWifiInactivityKey(String systemWifiInactivityKey) {
      this.f = systemWifiInactivityKey;
   }

   public List<Integer> getScreenScreenOffValues() {
      return this.g;
   }

   public void setScreenScreenOffValues(List<Integer> screenScreenOffValues) {
      this.g = screenScreenOffValues;
   }

   public List<Integer> getPowerOffTimeoutValues() {
      return this.h;
   }

   public void setPowerOffTimeoutValues(List<Integer> powerOffTimeoutValues) {
      this.h = powerOffTimeoutValues;
   }

   public List<Integer> getNetworkInactivityTimeoutValues() {
      return this.i;
   }

   public void setNetworkInactivityTimeoutValues(List<Integer> networkInactivityTimeoutValues) {
      this.i = networkInactivityTimeoutValues;
   }
}
