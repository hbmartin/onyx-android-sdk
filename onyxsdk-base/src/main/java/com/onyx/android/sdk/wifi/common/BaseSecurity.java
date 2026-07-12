package com.onyx.android.sdk.wifi.common;

import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.onyx.android.sdk.R;
import com.onyx.android.sdk.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TTFFont;
import com.onyx.android.sdk.wifi.AccessPoint;

public class BaseSecurity {
    public static final int SECURITY_NONE = 0;
    public static final int SECURITY_WEP = 1;
    public static final int SECURITY_PSK = 2;
    public static final int SECURITY_EAP = 3;
    public static final int SECURITY_OWE = 4;
    public static final int SECURITY_SAE = 5;
    public static final int SECURITY_EAP_SUITE_B = 6;
    public static final int SECURITY_EAP_WPA3_ENTERPRISE = 7;
    public static final int SECURITY_DPP = 8;
    public static final int SECURITY_MAX_VAL = 9;
    public static final int DISABLED_UNKNOWN_REASON = 0;
    public static final int DISABLED_DNS_FAILURE = 1;
    public static final int DISABLED_DHCP_FAILURE = 2;
    public static final int DISABLED_AUTH_FAILURE = 3;
    public static final int DISABLED_ASSOCIATION_REJECT = 4;
    private static final int[] a = {R.attr.state_encrypted};
    private static final int[] b = new int[0];

    public static int getSecurity(ScanResult result) {
        if (!CompatibilityUtil.apiLevelCheck(29)) {
            if (result.capabilities.contains("WEP")) {
                return 1;
            }
            if (result.capabilities.contains("PSK")) {
                return 2;
            }
            return result.capabilities.contains("EAP") ? 3 : 0;
        }
        boolean zContains = result.capabilities.contains("WEP");
        boolean zContains2 = result.capabilities.contains("SAE");
        boolean zContains3 = result.capabilities.contains("PSK");
        boolean zContains4 = result.capabilities.contains("EAP_SUITE_B_192");
        boolean zContains5 = result.capabilities.contains("EAP");
        boolean zContains6 = result.capabilities.contains("OWE");
        boolean zContains7 = result.capabilities.contains("OWE_TRANSITION");
        boolean zContains8 = result.capabilities.contains("DPP");
        if (zContains2 && zContains3) {
            return ((WifiManager) ResManager.getAppContext().getSystemService("wifi")).isWpa3SaeSupported() ? 5 : 2;
        }
        if (zContains7) {
            return ((WifiManager) ResManager.getAppContext().getSystemService("wifi")).isEnhancedOpenSupported() ? 4 : 0;
        }
        if (zContains8) {
            return 8;
        }
        if (zContains) {
            return 1;
        }
        if (zContains2) {
            return 5;
        }
        if (zContains3) {
            return 2;
        }
        if (zContains4) {
            return 6;
        }
        if (zContains5) {
            return 3;
        }
        return zContains6 ? 4 : 0;
    }

    public static int[] getWifiImageState(int security) {
        return isSecurity(security) ? a : b;
    }

    public static boolean isSecurity(int security) {
        return security != 0;
    }

    public static String getSecurityMode(ScanResult result, boolean concise, int security) {
        if (isPskSaeTransitionMode(result)) {
            return ResManager.getString(R.string.wifi_security_wpa3_sae_transition_mode);
        }
        if (isOweTransitionMode(result)) {
            return ResManager.getString(R.string.wifi_security_wpa3_owe_transition_mode);
        }
        switch (security) {
            case 1:
                return concise ? ResManager.getString(R.string.wifi_security_short_wep) : ResManager.getString(R.string.wifi_security_wep);
            case 2:
                int iA = a(result);
                if (iA == 1) {
                    return concise ? ResManager.getString(R.string.wifi_security_short_wpa) : ResManager.getString(R.string.wifi_security_wpa);
                }
                if (iA == 2) {
                    return concise ? ResManager.getString(R.string.wifi_security_short_wpa2) : ResManager.getString(R.string.wifi_security_wpa2);
                }
                if (iA != 3) {
                    return concise ? ResManager.getString(R.string.wifi_security_short_psk_generic) : ResManager.getString(R.string.wifi_security_psk_generic);
                }
                return concise ? ResManager.getString(R.string.wifi_security_short_wpa_wpa2) : ResManager.getString(R.string.wifi_security_wpa_wpa2);
            case 3:
                return concise ? ResManager.getString(R.string.wifi_security_short_eap) : ResManager.getString(R.string.wifi_security_eap);
            case 4:
                return ResManager.getString(R.string.wifi_security_owe);
            case 5:
                return ResManager.getString(R.string.wifi_security_sae);
            case 6:
                return ResManager.getString(R.string.wifi_security_suite_b);
            case 7:
            default:
                return concise ? TTFFont.UNKNOWN_FONT_NAME : ResManager.getString(R.string.wifi_security_none);
            case 8:
                return ResManager.getString(R.string.wifi_security_dpp);
        }
    }

    public static boolean isPskSaeTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains("SAE");
    }

    public static boolean isOweTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("OWE_TRANSITION");
    }

    private static int a(ScanResult result) {
        boolean zContains = result.capabilities.contains("WPA-PSK");
        boolean zContains2 = result.capabilities.contains("WPA2-PSK");
        if (zContains2 && zContains) {
            return 3;
        }
        if (zContains2) {
            return 2;
        }
        if (zContains) {
            return 1;
        }
        Log.w(BaseSecurity.class.getSimpleName(), "Received abnormal flag string: " + result.capabilities);
        return 0;
    }

    public String getSecurityString(AccessPoint accessPoint) {
        accessPoint.getScanResult();
        NetworkInfo.DetailedState detailedState = accessPoint.getDetailedState();
        WifiConfiguration wifiConfiguration = accessPoint.getWifiConfiguration();
        if (detailedState != null) {
            return getDetailedState(detailedState, accessPoint.getSsidForDisplay());
        }
        return (wifiConfiguration == null || wifiConfiguration.status != 1) ? getAccessSummary(accessPoint) : disableReason(accessPoint);
    }

    public String getDetailedState(NetworkInfo.DetailedState state, String ssid) {
        String[] stringArray = ResManager.getStringArray(ssid == null ? R.array.wifi_status : R.array.wifi_status_with_ssid);
        int iOrdinal = state.ordinal();
        if (iOrdinal >= stringArray.length || stringArray[iOrdinal].length() == 0) {
            return null;
        }
        return String.format(stringArray[iOrdinal], ssid);
    }

    protected String disableReason(AccessPoint accessPoint) {
        switch (accessPoint.getDisableReason()) {
            case 0:
            case 4:
                return ResManager.getString((!CompatibilityUtil.apiLevelCheck(27) || accessPoint.isConnected()) ? R.string.wifi_disabled_generic : R.string.wifi_disabled_wrong_password);
            case 1:
            case 2:
                return ResManager.getString(R.string.wifi_disabled_network_failure);
            case 3:
                return ResManager.getString(R.string.wifi_disabled_password_failure);
            default:
                return TTFFont.UNKNOWN_FONT_NAME;
        }
    }

    public int getDisableReason(WifiConfiguration config) {
        return ReflectUtil.getDeclareIntFieldSafely(config.getClass(), config, "disableReason");
    }

    protected String getAccessSummary(AccessPoint accessPoint) {
        ScanResult scanResult = accessPoint.getScanResult();
        WifiConfiguration wifiConfiguration = accessPoint.getWifiConfiguration();
        int security = accessPoint.getSecurity();
        String securityMode = accessPoint.getSecurityMode();
        boolean z = security != 3 && scanResult.capabilities.contains("WPS");
        StringBuilder sb = new StringBuilder();
        if (wifiConfiguration != null) {
            sb.append(ResManager.getString(R.string.wifi_remembered));
        }
        if (security != 0) {
            sb.append(String.format(sb.length() == 0 ? ResManager.getString(R.string.wifi_secured_first_item) : ResManager.getString(R.string.wifi_secured_second_item), securityMode));
        }
        if (wifiConfiguration == null && z) {
            if (sb.length() == 0) {
                sb.append(ResManager.getString(R.string.wifi_wps_available_first_item));
            } else {
                sb.append(ResManager.getString(R.string.wifi_wps_available_second_item));
            }
        }
        return sb.toString();
    }
}
