package com.onyx.android.sdk.wifi;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.R;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.BroadcastHelper;
import com.onyx.android.sdk.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiUtil.class */
public class WifiUtil {
    public static final String ACTION_WIFI_ENABLE = "android.intent.action.WIFI_ENABLE";
    private static final String SSID_PATTERN = "^\"(.*)\"$";
    private static final String b = "$1";
    private static Method c;
    private static Method d;
    private static Method e;
    private static final String f = "/system/etc/security/cacerts";
    public static final String USE_SYSTEM_CA = "use_system_ca";
    public static final String DO_NOT_VALIDATE_CA = "do_not_validate_ca";

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiUtil$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[WifiCertType.values().length];
            a = iArr;
            try {
                iArr[WifiCertType.CA_CERT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[WifiCertType.USER_CERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static int convertFrequencyToBand(int freq) {
        if (freq < 2412 || freq > 2484) {
            return (freq < 5170 || freq > 5825) ? -1 : 1;
        }
        return 0;
    }

    public static String getBandString(Context context, int frequnecy) {
        int iConvertFrequencyToBand = convertFrequencyToBand(frequnecy);
        if (iConvertFrequencyToBand != 0) {
            return iConvertFrequencyToBand != 1 ? context.getString(R.string.unknown_network) : context.getString(R.string.ac_network);
        }
        return context.getString(R.string.bgn_net_work);
    }

    public static boolean isSameSSID(String ssid1, String ssid2) {
        return ssid1.replaceAll(SSID_PATTERN, b).equals(ssid2.replaceAll(SSID_PATTERN, b));
    }

    @TargetApi(18)
    private static void a() {
        if (c == null || d == null) {
            c = ReflectUtil.getMethodSafely(WifiEnterpriseConfig.class, "setCaCertificateAlias", new Class[]{String.class});
            d = ReflectUtil.getMethodSafely(WifiEnterpriseConfig.class, "setClientCertificateAlias", new Class[]{String.class});
            e = ReflectUtil.getMethodSafely(WifiEnterpriseConfig.class, "setCaPath", new Class[]{String.class});
        }
    }

    @TargetApi(18)
    public static WifiConfiguration buildWifiEAPConfig(String ssid, int eapMethod, int phase2Type, String eapCaCert, String eapUserCert, String identity, String anonymous, String passWord) {
        a();
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        wifiConfiguration.allowedKeyManagement.set(2);
        wifiConfiguration.allowedKeyManagement.set(3);
        WifiEnterpriseConfig wifiEnterpriseConfig = new WifiEnterpriseConfig();
        wifiConfiguration.enterpriseConfig = wifiEnterpriseConfig;
        wifiEnterpriseConfig.setEapMethod(eapMethod);
        wifiConfiguration.enterpriseConfig.setPhase2Method(phase2Type);
        ReflectUtil.invokeMethodSafely(c, wifiConfiguration.enterpriseConfig, new Object[]{null});
        ReflectUtil.invokeMethodSafely(e, wifiConfiguration.enterpriseConfig, new Object[]{null});
        if (!TextUtils.isEmpty(eapCaCert) && !eapCaCert.equalsIgnoreCase(DO_NOT_VALIDATE_CA)) {
            if (eapCaCert.equalsIgnoreCase(USE_SYSTEM_CA)) {
                ReflectUtil.invokeMethodSafely(e, wifiConfiguration.enterpriseConfig, new Object[]{f});
            } else {
                ReflectUtil.invokeMethodSafely(c, wifiConfiguration.enterpriseConfig, new Object[]{eapCaCert});
            }
        }
        ReflectUtil.invokeMethodSafely(d, wifiConfiguration.enterpriseConfig, new Object[]{eapUserCert});
        wifiConfiguration.enterpriseConfig.setIdentity(identity);
        wifiConfiguration.enterpriseConfig.setAnonymousIdentity(anonymous);
        wifiConfiguration.enterpriseConfig.setPassword(passWord);
        return wifiConfiguration;
    }

    public static List<String> loadCACertificates(String defaultValue) {
        return a(WifiCertType.CA_CERT, Collections.singleton(defaultValue));
    }

    public static List<String> loadUserCertificates(String defaultValue) {
        return a(WifiCertType.USER_CERT, Collections.singleton(defaultValue));
    }

    public static boolean isValidPassword(int securityMode, String password) {
        if (StringUtils.isNullOrEmpty(password)) {
            return false;
        }
        if (securityMode == 1) {
            return password.length() >= 5;
        }
        if (securityMode != 2) {
            return password.length() >= 5;
        }
        return password.length() >= 8;
    }

    public static void reevaluateNetwork(Context context) {
        ConnectivityManager connectivityManager;
        if (CompatibilityUtil.apiLevelCheck(23) && (connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")) != null) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                connectivityManager.reportNetworkConnectivity(activeNetwork, true);
            } else {
                Debug.i("No Active Network, abandon network reevaluate");
            }
        }
    }

    public static int getWifiSignalLevel(int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

    public static boolean checkAccessPointLegality(WifiManager wifiManager, HashMap<String, AccessPoint> map, AccessPoint point) {
        AccessPoint accessPoint;
        if (!map.containsKey(point.getSsidForDisplay()) || (accessPoint = map.get(point.getSsidForDisplay())) == null) {
            return true;
        }
        if (isAccessPointCurrentConnected(wifiManager, accessPoint)) {
            return false;
        }
        if (isAccessPointCurrentConnected(wifiManager, point)) {
            return true;
        }
        return accessPoint.getSecurity() != point.getSecurity() && accessPoint.getSignalLevel() < point.getSignalLevel();
    }

    public static boolean isAccessPointCurrentConnected(WifiManager wifiManager, AccessPoint accessPoint) {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        return (connectionInfo == null || accessPoint.getWifiConfiguration() == null || accessPoint.getScanResult() == null || accessPoint.getWifiConfiguration().networkId != connectionInfo.getNetworkId() || !StringUtils.safelyEquals(connectionInfo.getBSSID(), accessPoint.getScanResult().BSSID)) ? false : true;
    }

    public static WifiConfiguration isExistConfiguration(WifiManager wifiManager, String ssid) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return null;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public static WifiConfiguration createWifiConfiguration(AccessPoint accessPoint) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + accessPoint.getSsidForDisplay() + "\"";
        String password = accessPoint.getPassword();
        switch (accessPoint.getSecurity()) {
            case 0:
                wifiConfiguration.allowedKeyManagement.set(0);
                break;
            case 1:
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                int length = password.length();
                if ((length == 10 || length == 26 || length == 58) && password.matches("[0-9A-Fa-f]*")) {
                    wifiConfiguration.wepKeys[0] = password;
                } else {
                    wifiConfiguration.wepKeys[0] = '\"' + password + '\"';
                }
                break;
            case 2:
                wifiConfiguration.allowedKeyManagement.set(1);
                if (!password.matches("[0-9A-Fa-f]{64}")) {
                    wifiConfiguration.preSharedKey = '\"' + password + '\"';
                } else {
                    wifiConfiguration.preSharedKey = password;
                }
                break;
            case 3:
                break;
            default:
                return null;
        }
        return wifiConfiguration;
    }

    public static void openWifiSettings(Context context) {
        context.startActivity(new Intent(BroadcastHelper.Settings.SETTINGS_CONFIGURE_NEW_WIFI_ACTION));
    }

    public static String getUtf8Ssid(String ssid) {
        return !CompatibilityUtil.apiLevelCheck(32) ? ssid : a(b(ssid));
    }

    private static String b(String quotedStr) {
        int length = quotedStr.length();
        if (length >= 2 && quotedStr.charAt(0) == '\"') {
            int i = length - 1;
            if (quotedStr.charAt(i) == '\"') {
                return quotedStr.substring(1, i);
            }
        }
        return quotedStr;
    }

    public static List<String> loadCACertificates(Collection<String> defaultValues) {
        return a(WifiCertType.CA_CERT, defaultValues);
    }

    public static String getUtf8Ssid(ScanResult scanResult) {
        if (CompatibilityUtil.apiLevelCheck(32) && StringUtils.safelyEquals("<unknown ssid>", scanResult.SSID)) {
            Object objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely(ScanResult.class, "getWifiSsid", new Class[0]), scanResult, new Object[0]);
            return objInvokeMethodSafely != null ? getUtf8Ssid(objInvokeMethodSafely.toString()) : scanResult.SSID;
        }
        return scanResult.SSID;
    }

    private static List<String> a(WifiCertType certType, Collection<String> defaultValues) {
        List<String> list;
        ArrayList<String> arrayList = new ArrayList<>(defaultValues);
        String[] strArrLoadCACertificate = null;
        int i = a.a[certType.ordinal()];
        if (i == 1) {
            strArrLoadCACertificate = Device.currentDevice().loadCACertificate();
        } else if (i == 2) {
            strArrLoadCACertificate = Device.currentDevice().loadUserCertificate();
        }
        if (strArrLoadCACertificate != null && strArrLoadCACertificate.length != 0) {
            List<String> listAsList = Arrays.asList(strArrLoadCACertificate);
            list = listAsList;
            listAsList.addAll(defaultValues);
        } else {
            list = arrayList;
        }
        return list;
    }

    private static String a(String ssid) {
        if (!TextUtils.isEmpty(ssid) && ssid.charAt(0) != '\"') {
            if (StringUtils.isAllDigit(ssid)) {
                return ssid;
            }
            try {
                byte[] bArrA = a(ssid.toCharArray(), false);
                Charset charset = StandardCharsets.UTF_8;
                if (charset != null) {
                    String strA = a(bArrA, charset);
                    if (!TextUtils.isEmpty(strA)) {
                        return strA;
                    }
                }
                Charset charsetForName = Charset.forName(TTFFont.CHARSET_GBK);
                if (charsetForName != null) {
                    String strA2 = a(bArrA, charsetForName);
                    if (!TextUtils.isEmpty(strA2)) {
                        return strA2;
                    }
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        return b(ssid);
    }

    private static String a(@NonNull byte[] ssidBytes, @NonNull Charset charset) {
        CharsetDecoder charsetDecoderOnUnmappableCharacter = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        CharBuffer charBufferAllocate = CharBuffer.allocate(32);
        CoderResult coderResultDecode = charsetDecoderOnUnmappableCharacter.decode(ByteBuffer.wrap(ssidBytes), charBufferAllocate, true);
        charBufferAllocate.flip();
        if (coderResultDecode.isError()) {
            return null;
        }
        return charBufferAllocate.toString();
    }

    private static byte[] a(char[] encoded, boolean allowSingleChar) throws IllegalArgumentException {
        int length = encoded.length;
        byte[] bArr = new byte[(length + 1) / 2];
        int i = 0;
        int i2 = 0;
        if (allowSingleChar) {
            if (length % 2 != 0) {
                i2 = 1;
                bArr[0] = (byte) a(encoded, 0);
                i = 1;
            }
        } else if (length % 2 != 0) {
            throw new IllegalArgumentException("Invalid input length: " + length);
        }
        while (i2 < length) {
            int i3 = i;
            i++;
            bArr[i3] = (byte) ((a(encoded, i2) << 4) | a(encoded, i2 + 1));
            i2 += 2;
        }
        return bArr;
    }

    private static int a(char[] str, int offset) throws IllegalArgumentException {
        char c2 = str[offset];
        if ('0' <= c2 && c2 <= '9') {
            return c2 - '0';
        }
        if ('a' <= c2 && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        if ('A' > c2 || c2 > 'F') {
            throw new IllegalArgumentException("Illegal char: " + str[offset] + " at offset " + offset);
        }
        return (c2 - 'A') + 10;
    }
}
