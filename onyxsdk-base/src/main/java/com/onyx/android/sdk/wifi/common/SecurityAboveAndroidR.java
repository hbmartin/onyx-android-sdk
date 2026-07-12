package com.onyx.android.sdk.wifi.common;

import android.net.wifi.WifiConfiguration;
import com.onyx.android.sdk.R.string;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.wifi.AccessPoint;

public class SecurityAboveAndroidR extends BaseSecurity {
   public static final int INVALID = -1;
   public static final int DISABLED_ASSOCIATION_REJECTION = 1;
   public static final int DISABLED_AUTHENTICATION_FAILURE = 2;
   public static final int DISABLED_DHCP_FAILURE = 3;
   public static final int DISABLED_NO_INTERNET_TEMPORARY = 4;
   public static final int DISABLED_AUTHENTICATION_NO_CREDENTIALS = 5;
   public static final int DISABLED_NO_INTERNET_PERMANENT = 6;
   public static final int DISABLED_BY_WIFI_MANAGER = 7;
   public static final int DISABLED_BY_WRONG_PASSWORD = 8;
   public static final int DISABLED_AUTHENTICATION_NO_SUBSCRIPTION = 9;
   public static final String[] QUALITY_NETWORK_SELECTION_DISABLE_REASON = new String[]{
      "INVALID",
      "DISABLED_ASSOCIATION_REJECTION",
      "DISABLED_AUTHENTICATION_FAILURE ",
      "DISABLED_DHCP_FAILURE",
      "DISABLED_NO_INTERNET_TEMPORARY",
      "DISABLED_AUTHENTICATION_NO_CREDENTIALS",
      "DISABLED_NO_INTERNET_PERMANENT",
      "DISABLED_BY_WIFI_MANAGER",
      "DISABLED_BY_WRONG_PASSWORD",
      "DISABLED_AUTHENTICATION_NO_SUBSCRIPTION"
   };

   @Override
   protected String disableReason(AccessPoint accessPoint) {
      Debug.i(this.getClass(), "disableReason:" + accessPoint.getDisableReason(), new Object[0]);
      switch (accessPoint.getDisableReason()) {
         case 1:
            return ResManager.getString(string.wifi_disabled_generic);
         case 2:
            return ResManager.getString(string.wifi_disabled_password_failure);
         case 3:
            return ResManager.getString(string.wifi_disabled_network_failure);
         case 4:
            return ResManager.getString(string.wifi_disabled_no_internet_temporary);
         case 5:
         case 7:
         default:
            return "";
         case 6:
            return ResManager.getString(string.wifi_remembered);
         case 8:
            return ResManager.getString(string.wifi_disabled_wrong_password);
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
