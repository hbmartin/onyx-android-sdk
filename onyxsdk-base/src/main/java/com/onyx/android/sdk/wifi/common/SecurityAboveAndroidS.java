package com.onyx.android.sdk.wifi.common;

import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import com.onyx.android.sdk.R.string;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.wifi.AccessPoint;

public class SecurityAboveAndroidS extends BaseSecurity {
   public static final int INVALID = -1;
   public static final int DISABLED_NONE = 0;
   public static final int DISABLED_ASSOCIATION_REJECTION = 1;
   public static final int DISABLED_AUTHENTICATION_FAILURE = 2;
   public static final int DISABLED_DHCP_FAILURE = 3;
   public static final int DISABLED_NO_INTERNET_TEMPORARY = 4;
   public static final int DISABLED_AUTHENTICATION_NO_CREDENTIALS = 5;
   public static final int DISABLED_NO_INTERNET_PERMANENT = 6;
   public static final int DISABLED_BY_WIFI_MANAGER = 7;
   public static final int DISABLED_BY_WRONG_PASSWORD = 8;
   public static final int DISABLED_AUTHENTICATION_NO_SUBSCRIPTION = 9;
   public static final int DISABLED_AUTHENTICATION_PRIVATE_EAP_ERROR = 10;
   public static final int DISABLED_NETWORK_NOT_FOUND = 11;
   public static final int DISABLED_CONSECUTIVE_FAILURES = 12;
   public static final String[] QUALITY_NETWORK_SELECTION_DISABLE_REASON = new String[]{
      "INVALID",
      "DISABLED_NONE",
      "DISABLED_ASSOCIATION_REJECTION",
      "DISABLED_AUTHENTICATION_FAILURE ",
      "DISABLED_DHCP_FAILURE",
      "DISABLED_NO_INTERNET_TEMPORARY",
      "DISABLED_AUTHENTICATION_NO_CREDENTIALS",
      "DISABLED_NO_INTERNET_PERMANENT",
      "DISABLED_BY_WIFI_MANAGER",
      "DISABLED_BY_WRONG_PASSWORD",
      "DISABLED_AUTHENTICATION_NO_SUBSCRIPTION",
      "DISABLED_AUTHENTICATION_PRIVATE_EAP_ERROR",
      "DISABLED_NETWORK_NOT_FOUND",
      "DISABLED_CONSECUTIVE_FAILURES"
   };

   @Override
   public String getSecurityString(AccessPoint accessPoint) {
      accessPoint.getScanResult();
      DetailedState var2;
      DetailedState var10000 = var2 = accessPoint.getDetailedState();
      WifiConfiguration var3 = accessPoint.getWifiConfiguration();
      if (var10000 != null) {
         return this.getDetailedState(var2, accessPoint.getSsidForDisplay());
      } else {
         return var3 != null && accessPoint.getDisableReason() != -1 ? this.disableReason(accessPoint) : this.getAccessSummary(accessPoint);
      }
   }

   @Override
   protected String disableReason(AccessPoint accessPoint) {
      Debug.i(this.getClass(), "disableReason:" + accessPoint.getDisableReason(), new Object[0]);
      int var2;
      if ((var2 = accessPoint.getDisableReason()) != 8) {
         switch (var2) {
            case 1:
               return ResManager.getString(string.wifi_disabled_generic);
            case 2:
               return ResManager.getString(string.wifi_disabled_password_failure);
            case 3:
               return ResManager.getString(string.wifi_disabled_network_failure);
            case 4:
               return ResManager.getString(string.wifi_disabled_no_internet_temporary);
            default:
               return ResManager.getString(string.wifi_remembered);
         }
      } else {
         return ResManager.getString(string.wifi_disabled_wrong_password);
      }
   }

   @Override
   public int getDisableReason(WifiConfiguration config) {
      Object var2;
      if ((var2 = ReflectUtil.getDeclaredFieldSafely(config.getClass(), config, "mNetworkSelectionStatus")) == null) {
         return -1;
      }

      int var3;
      return (var3 = (Integer)ReflectUtil.getDeclaredFieldSafely(var2.getClass(), var2, "mNetworkSelectionDisableReason")) == 0 ? -1 : var3;
   }
}
