package com.onyx.android.sdk.wifi.common;

import android.net.wifi.WifiConfiguration;
import com.onyx.android.sdk.R.string;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.wifi.AccessPoint;

public class SecurityAboveAndroidO extends BaseSecurity {
   public static final int INVALID = -1;
   public static final int NETWORK_SELECTION_ENABLE = 0;
   public static final int NETWORK_SELECTION_DISABLED_STARTING_INDEX = 1;
   public static final int DISABLED_BAD_LINK = 1;
   public static final int DISABLED_ASSOCIATION_REJECTION = 2;
   public static final int DISABLED_AUTHENTICATION_FAILURE = 3;
   public static final int DISABLED_DHCP_FAILURE = 4;
   public static final int DISABLED_DNS_FAILURE = 5;
   public static final int DISABLED_NO_INTERNET_TEMPORARY = 6;
   public static final int DISABLED_WPS_START = 7;
   public static final int DISABLED_TLS_VERSION_MISMATCH = 8;
   public static final int DISABLED_AUTHENTICATION_NO_CREDENTIALS = 9;
   public static final int DISABLED_NO_INTERNET_PERMANENT = 10;
   public static final int DISABLED_BY_WIFI_MANAGER = 11;
   public static final int DISABLED_DUE_TO_USER_SWITCH = 12;
   public static final int DISABLED_BY_WRONG_PASSWORD = 13;
   public static final int NETWORK_SELECTION_DISABLED_MAX = 14;
   public static final String[] QUALITY_NETWORK_SELECTION_DISABLE_REASON = new String[]{
      "NETWORK_SELECTION_ENABLE",
      "NETWORK_SELECTION_DISABLED_BAD_LINK",
      "NETWORK_SELECTION_DISABLED_ASSOCIATION_REJECTION ",
      "NETWORK_SELECTION_DISABLED_AUTHENTICATION_FAILURE",
      "NETWORK_SELECTION_DISABLED_DHCP_FAILURE",
      "NETWORK_SELECTION_DISABLED_DNS_FAILURE",
      "NETWORK_SELECTION_DISABLED_NO_INTERNET_TEMPORARY",
      "NETWORK_SELECTION_DISABLED_WPS_START",
      "NETWORK_SELECTION_DISABLED_TLS_VERSION",
      "NETWORK_SELECTION_DISABLED_AUTHENTICATION_NO_CREDENTIALS",
      "NETWORK_SELECTION_DISABLED_NO_INTERNET_PERMANENT",
      "NETWORK_SELECTION_DISABLED_BY_WIFI_MANAGER",
      "NETWORK_SELECTION_DISABLED_BY_USER_SWITCH",
      "NETWORK_SELECTION_DISABLED_BY_WRONG_PASSWORD"
   };

   @Override
   protected String disableReason(AccessPoint accessPoint) {
      Debug.i(this.getClass(), "disableReason:" + accessPoint.getDisableReason(), new Object[0]);
      int var2;
      if ((var2 = accessPoint.getDisableReason()) != 10) {
         if (var2 != 13) {
            switch (var2) {
               case 2:
                  return ResManager.getString(string.wifi_disabled_generic);
               case 3:
                  return ResManager.getString(string.wifi_disabled_password_failure);
               case 4:
               case 5:
                  return ResManager.getString(string.wifi_disabled_network_failure);
               default:
                  return "";
            }
         } else {
            return ResManager.getString(string.wifi_disabled_wrong_password);
         }
      } else {
         return ResManager.getString(string.wifi_remembered);
      }
   }

   @Override
   public int getDisableReason(WifiConfiguration config) {
      Object var2;
      return (var2 = ReflectUtil.getDeclaredFieldSafely(config.getClass(), config, "mNetworkSelectionStatus")) == null
         ? -1
         : (Integer)ReflectUtil.getDeclaredFieldSafely(var2.getClass(), var2, "mNetworkSelectionDisableReason");
   }
}
