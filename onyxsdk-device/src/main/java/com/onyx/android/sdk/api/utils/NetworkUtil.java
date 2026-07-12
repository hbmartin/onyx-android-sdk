package com.onyx.android.sdk.api.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.api.data.model.NetWorkBean;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.Debug;
import java.net.NetworkInterface;
import java.util.Collections;

public class NetworkUtil {
    private static final String TAG = "NetworkUtil";
    public static final String MAC_ADDRESS_KEY = "mac_address";
    private static final String MAC_UNKNOWN = "unknown";
    private static volatile String macCache;
    public static final int DEFAULT_WIFI_SIGNAL_WEAK = 20;
    public static final String[] INVALID_MAC_ADDRESS = {"00:00:00:00:00:00", "20:00:00:00:00:00"};
    public static final String schemeHttp = "http://";
    public static final String schemeHttps = "https://";
    public static final String[] schemeList = {schemeHttp, schemeHttps};

    public static boolean isNetworkConnected(Context context) {
        return isWiFiConnected(context);
    }

    @Deprecated
    public static boolean isWiFiConnected(Context context) {
        return isWiFiConnected(((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo());
    }

    public static void toggleWiFi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
    }

    public static void enableWiFi(Context context, boolean enabled) {
        ((WifiManager) context.getSystemService("wifi")).setWifiEnabled(enabled);
    }

    public static boolean isConnectingOrConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private static String getMacAddressFromCacheFile(Context context) {
        if (isStringValidMacAddress(macCache)) {
            Debug.i(TAG, "read memory cache mac = " + macCache, new Object[0]);
            return macCache;
        }
        String systemConfig = Device.currentDevice().readSystemConfig(context, MAC_ADDRESS_KEY);
        if (!isStringValidMacAddress(systemConfig)) {
            return "";
        }
        macCache = systemConfig;
        Debug.i(TAG, "read onyxconfig mac = " + systemConfig, new Object[0]);
        return systemConfig;
    }

    private static boolean isMacAddressInInvalidList(String val) {
        for (String str : INVALID_MAC_ADDRESS) {
            if (str.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringValidMacAddress(String val) {
        return (StringUtils.isNullOrEmpty(val) || !val.matches(Device.currentDevice().getValidMacAddressRegex()) || isMacAddressInInvalidList(val)) ? false : true;
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    public static String getMacAddressFromSystem(Context context) {
        for (int i = 0; i < 5; i++) {
            String macAddressFromSystemImpl = getMacAddressFromSystemImpl(context);
            if (isStringValidMacAddress(macAddressFromSystemImpl)) {
                return macAddressFromSystemImpl.toLowerCase();
            }
        }
        Log.e(TAG, "No mac address acquired");
        return "";
    }

    private static String getMacAddressFromSystemImpl(Context context) {
        String macAddressFromWifiManager;
        boolean z = false;
        if (!isWifiEnabled(context)) {
            enableWiFi(context, true);
            try {
                Thread.sleep(600L);
            } catch (Exception unused) {
            }
            z = true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            String fixedWifiMacAddress = Device.currentDevice().getFixedWifiMacAddress(context);
            macAddressFromWifiManager = fixedWifiMacAddress;
            if (TextUtils.isEmpty(fixedWifiMacAddress)) {
                macAddressFromWifiManager = getMacAddressFromNetworkInterface();
            }
        } else {
            macAddressFromWifiManager = getMacAddressFromWifiManager(context);
        }
        if (z) {
            enableWiFi(context, false);
        }
        return macAddressFromWifiManager;
    }

    private static String getMacAddressFromNetworkInterface() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(b & 255);
                        sb.append(String.format("%02X:", objArr));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString().toLowerCase();
                }
            }
            return "";
        } catch (Exception e) {
            if (!Debug.getDebug()) {
                return "";
            }
            e.printStackTrace();
            return "";
        }
    }

    private static String getMacAddressFromWifiManager(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        return wifiManager != null ? wifiManager.getConnectionInfo().getMacAddress() : "";
    }

    public static void saveMacAddressToCache(Context context, String macAddress) {
        macCache = macAddress;
        Debug.i(TAG, "update memory cache mac = " + macCache, new Object[0]);
        Device.currentDevice().saveSystemConfig(context, MAC_ADDRESS_KEY, macAddress);
    }

    @Nullable
    public static String getMacAddress(Context context) {
        if (context == null || !context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
            return null;
        }
        String macAddressFromCacheFile = getMacAddressFromCacheFile(context);
        if (isStringValidMacAddress(macAddressFromCacheFile)) {
            return macAddressFromCacheFile;
        }
        String str = TAG;
        Debug.e(str, "get empty/invalid cache mac address:" + macAddressFromCacheFile, new Object[0]);
        String macAddressFromSystem = getMacAddressFromSystem(context);
        Debug.e(str, "getMacAddressFromSystem:" + macAddressFromSystem, new Object[0]);
        if (!isStringValidMacAddress(macAddressFromSystem)) {
            return "";
        }
        saveMacAddressToCache(context, macAddressFromSystem);
        return macAddressFromSystem;
    }

    @NonNull
    public static String getMacAddressWithoutSymbol(Context context) {
        String macAddress = getMacAddress(context);
        return TextUtils.isEmpty(macAddress) ? MAC_UNKNOWN : macAddress.replaceAll(":", "");
    }

    public static String getCurrentWifiSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        return wifiManager != null ? wifiManager.getConnectionInfo().getSSID() : "";
    }

    public static String getCurrentWifiBSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        return wifiManager != null ? wifiManager.getConnectionInfo().getBSSID() : "";
    }

    public static boolean enableWifiOpenAndDetect(Context context) {
        if (isWiFiConnected(context)) {
            return false;
        }
        Device.currentDevice().enableWifiDetect(context);
        enableWiFi(context, true);
        return true;
    }

    public static void clearCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
    }

    @NonNull
    public static String getMacCache() {
        return TextUtils.isEmpty(macCache) ? MAC_UNKNOWN : macCache;
    }

    @NonNull
    public static String getMacCacheWithoutSymbol() {
        return getMacCache().replaceAll(":", "");
    }

    public static void initMacCache(Context context) {
        macCache = getMacAddress(context.getApplicationContext());
    }

    public static NetWorkBean getNetWorkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        NetWorkBean netWorkBean = new NetWorkBean();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            netWorkBean.setConnect(false);
        } else {
            netWorkBean.setConnect(true);
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            boolean zHasCapability = networkCapabilities.hasCapability(12);
            boolean zHasCapability2 = networkCapabilities.hasCapability(16);
            if (zHasCapability && zHasCapability2) {
                netWorkBean.setAvailable(true);
            } else {
                netWorkBean.setAvailable(false);
            }
        }
        return netWorkBean;
    }

    public static boolean isMobileDataEnabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (Build.VERSION.SDK_INT >= 23) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return networkCapabilities != null && networkCapabilities.hasTransport(0);
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiNetworkTypeConnected(Context context) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null || (networkInfo = connectivityManager.getNetworkInfo(1)) == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public static int getWifiSignalLevel(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            return WifiManager.calculateSignalLevel(wifiManager.getConnectionInfo().getRssi(), 100);
        }
        return 0;
    }

    public static boolean isWifiSignalStrengthPass(Context context) {
        return isWifiNetworkTypeConnected(context) && getWifiSignalLevel(context) > 20;
    }

    public static boolean isWiFiConnected(NetworkInfo info) {
        return info != null && info.isConnected();
    }
}
