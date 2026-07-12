package com.onyx.android.sdk.wifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.R;
import com.onyx.android.sdk.rx.RxCallback;
import com.onyx.android.sdk.rx.RxManager;
import com.onyx.android.sdk.rx.RxRequest;
import com.onyx.android.sdk.utils.BroadcastHelper;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import com.onyx.android.sdk.wifi.common.BaseSecurity;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin.class */
public class WifiAdmin {
    private static final String h = "WifiAdmin";
    public static final int SECURITY_NONE = 0;
    public static final int SECURITY_WEP = 1;
    public static final int SECURITY_PSK = 2;
    public static final int SECURITY_EAP = 3;
    private static final String i = "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";
    private static final String j = "android.net.wifi.LINK_CONFIGURATION_CHANGED";
    private static final int[] k = {R.attr.state_encrypted};
    private static final int[] l = new int[0];
    private WifiManager a;
    private List<ScanResult> b;
    private Context c;
    private IntentFilter d;
    Callback e;
    private BroadcastReceiver f;
    private RxManager g;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$Callback.class */
    public interface Callback {
        void onWifiStateChange(boolean z, int i);

        void onScanResultReady(List<AccessPoint> list);

        void onSupplicantStateChanged(NetworkInfo.DetailedState detailedState);

        void onNetworkConnectionChange(NetworkInfo.DetailedState detailedState);

        void onLinkConfigurationChange(List<AccessPoint> list);

        void onConfiguredNetworksChange(List<AccessPoint> list);

        void onConnectedNetworkRSSIChange(int i);
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$DefaultCallback.class */
    public static class DefaultCallback implements Callback {
        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onWifiStateChange(boolean isWifiEnable, int wifiExtraState) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onScanResultReady(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onSupplicantStateChanged(NetworkInfo.DetailedState state) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onNetworkConnectionChange(NetworkInfo.DetailedState state) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onLinkConfigurationChange(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onConfiguredNetworksChange(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.WifiAdmin.Callback
        public void onConnectedNetworkRSSIChange(int newRSSI) {
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$a.class */
    class a extends BroadcastReceiver {
        final WifiAdmin owner = WifiAdmin.this;

        a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.net.wifi.WIFI_STATE_CHANGED":
                    WifiAdmin.this.e.onWifiStateChange(WifiAdmin.this.a.isWifiEnabled(), intent.getIntExtra("wifi_state", 4));
                    if (WifiAdmin.this.a.isWifiEnabled()) {
                        WifiAdmin.this.triggerWifiScan();
                        break;
                    }
                    break;
                case "android.net.wifi.RSSI_CHANGED":
                    WifiAdmin.this.e.onConnectedNetworkRSSIChange(intent.getIntExtra("newRssi", -200));
                    break;
                case "android.net.wifi.STATE_CHANGE":
                    WifiAdmin.this.e.onNetworkConnectionChange(((NetworkInfo) intent.getParcelableExtra("networkInfo")).getDetailedState());
                    break;
                case "android.net.wifi.supplicant.STATE_CHANGE":
                    WifiAdmin.this.e.onSupplicantStateChanged(WifiInfo.getDetailedStateOf((SupplicantState) intent.getParcelableExtra("newState")));
                    break;
                case "android.net.wifi.LINK_CONFIGURATION_CHANGED":
                    WifiAdmin.this.a(new WifiAdmin$a$b(this));
                    break;
                case "android.net.wifi.CONFIGURED_NETWORKS_CHANGE":
                    WifiAdmin.this.a(new WifiAdmin$a$c(this));
                    break;
                case "android.net.wifi.SCAN_RESULTS":
                    WifiAdmin.this.a(new WifiAdmin$a$a(this));
                    break;
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$b.class */
    class b extends RxCallback<RxRequest> {
        final /* synthetic */ RxCallback a;
        final /* synthetic */ d b;

        b(RxCallback rxCallback, d dVar) {
            this.a = rxCallback;
            this.b = dVar;
        }

        @Override // com.onyx.android.sdk.rx.RxCallback
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onNext(@NonNull RxRequest rxRequest) {
            this.a.onNext(this.b.a());
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$c.class */
    class c implements Comparator<AccessPoint> {
        c() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(AccessPoint a1, AccessPoint a2) {
            return a2.getSignalLevel() - a1.getSignalLevel();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiAdmin$d.class */
    private class d extends RxRequest {
        private List<AccessPoint> c;

        /* synthetic */ d(WifiAdmin x0, a x1) {
            this();
        }

        public List<AccessPoint> a() {
            return this.c;
        }

        @Override // com.onyx.android.sdk.rx.RxRequest
        public void execute() throws Exception {
            WifiAdmin wifiAdmin = WifiAdmin.this;
            List listA = wifiAdmin.a(wifiAdmin.a.getScanResults());
            if (CollectionUtils.isNonBlank(listA)) {
                this.c.addAll(listA);
            }
        }

        private d() {
            this.c = new ArrayList();
        }
    }

    public WifiAdmin(Context context) {
        this.c = context;
        this.a = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        a();
    }

    public WifiAdmin setCallback(Callback callback) {
        this.e = callback;
        return this;
    }

    public RxManager getRxManager() {
        if (this.g == null) {
            this.g = RxManager.Builder.sharedSingleThreadManager();
        }
        return this.g;
    }

    @SuppressLint({"NewApi"})
    public boolean registerReceiver() {
        Context context = this.c;
        if (context == null) {
            return false;
        }
        context.registerReceiver(this.f, this.d, BroadcastHelper.ReceiverFlags.RECEIVER_EXPORTED);
        return true;
    }

    public boolean unregisterReceiver() {
        Context context = this.c;
        if (context == null) {
            return false;
        }
        context.unregisterReceiver(this.f);
        return true;
    }

    public void toggleWifi() {
        WifiManager wifiManager = this.a;
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
        }
    }

    public boolean isWifiEnabled() {
        WifiManager wifiManager = this.a;
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    public int getWifiState() {
        WifiManager wifiManager = this.a;
        if (wifiManager == null) {
            return 4;
        }
        return wifiManager.getWifiState();
    }

    public void setWifiEnabled(boolean isWifiEnabled) {
        WifiManager wifiManager = this.a;
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(isWifiEnabled);
        }
    }

    public void triggerWifiScan() {
        WifiManager wifiManager = this.a;
        if (wifiManager != null) {
            wifiManager.startScan();
        }
    }

    public WifiInfo getCurrentConnectionInfo() {
        return this.a.getConnectionInfo();
    }

    public int checkWifiState() {
        return this.a.getWifiState();
    }

    @Nullable
    public WifiConfiguration getWifiConfiguration(ScanResult result) {
        List<WifiConfiguration> configuredNetworks = this.a.getConfiguredNetworks();
        if (CollectionUtils.isNullOrEmpty(configuredNetworks)) {
            return null;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (WifiUtil.isSameSSID(WifiUtil.getUtf8Ssid(wifiConfiguration.SSID), WifiUtil.getUtf8Ssid(result))) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public int getSecurity(ScanResult result) {
        return BaseSecurity.getSecurity(result);
    }

    public String getSecurityString(AccessPoint accessPoint) {
        return new BaseSecurity().getSecurityString(accessPoint);
    }

    public String getSecurityMode(ScanResult result, boolean concise, int security) {
        if (security == 1) {
            return concise ? this.c.getString(R.string.wifi_security_short_wep) : this.c.getString(R.string.wifi_security_wep);
        }
        if (security != 2) {
            if (security != 3) {
                return concise ? TTFFont.UNKNOWN_FONT_NAME : this.c.getString(R.string.wifi_security_none);
            }
            return concise ? this.c.getString(R.string.wifi_security_short_eap) : this.c.getString(R.string.wifi_security_eap);
        }
        int iA = a(result);
        if (iA == 1) {
            return concise ? this.c.getString(R.string.wifi_security_short_wpa) : this.c.getString(R.string.wifi_security_wpa);
        }
        if (iA == 2) {
            return concise ? this.c.getString(R.string.wifi_security_short_wpa2) : this.c.getString(R.string.wifi_security_wpa2);
        }
        if (iA != 3) {
            return concise ? this.c.getString(R.string.wifi_security_short_psk_generic) : this.c.getString(R.string.wifi_security_psk_generic);
        }
        return concise ? this.c.getString(R.string.wifi_security_short_wpa_wpa2) : this.c.getString(R.string.wifi_security_wpa_wpa2);
    }

    public WifiInfo getWifiInfo(ScanResult result) {
        WifiInfo connectionInfo = this.a.getConnectionInfo();
        if (connectionInfo == null) {
            return null;
        }
        String utf8Ssid = WifiUtil.getUtf8Ssid(connectionInfo.getSSID());
        if (!StringUtils.isNullOrEmpty(utf8Ssid) && WifiUtil.isSameSSID(utf8Ssid, WifiUtil.getUtf8Ssid(result))) {
            return connectionInfo;
        }
        return null;
    }

    public int getWifiSignalLevel(ScanResult result) {
        return getWifiSignalLevel(result.level);
    }

    public int[] getWifiImageState(int security) {
        return security != 0 ? k : l;
    }

    public void connectWifi(AccessPoint accessPoint) {
        int iAddNetwork;
        WifiConfiguration wifiConfiguration = accessPoint.getWifiConfiguration();
        if (wifiConfiguration != null) {
            iAddNetwork = wifiConfiguration.networkId;
        } else {
            iAddNetwork = this.a.addNetwork(createWifiConfiguration(accessPoint));
        }
        if (this.a.enableNetwork(iAddNetwork, true)) {
            this.a.saveConfiguration();
        }
    }

    public WifiConfiguration createWifiConfiguration(AccessPoint accessPoint) {
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

    public String getSignalString(int signal) {
        return this.c.getResources().getStringArray(R.array.wifi_signal)[signal];
    }

    public boolean forget(AccessPoint accessPoint) {
        if (accessPoint == null || accessPoint.getWifiConfiguration() == null) {
            return false;
        }
        this.a.removeNetwork(accessPoint.getWifiConfiguration().networkId);
        return this.a.saveConfiguration();
    }

    public String getLocalIPAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                    return inetAddressNextElement.getHostAddress();
                }
            }
        }
        return TTFFont.UNKNOWN_FONT_NAME;
    }

    public int getPosition(List<AccessPoint> wifiList, NetworkInfo.DetailedState state, WifiInfo connectionInfo) {
        if (wifiList == null) {
            return -1;
        }
        int i2 = 0;
        while (i2 < wifiList.size()) {
            WifiConfiguration wifiConfiguration = wifiList.get(i2).getWifiConfiguration();
            if (wifiConfiguration != null) {
                int i3 = wifiConfiguration.networkId;
                if (connectionInfo != null && i3 != -1 && i3 == connectionInfo.getNetworkId()) {
                    return i2;
                }
            }
            i2++;
        }
        i2 = -1;
        return i2;
    }

    public int getDisableReason(WifiConfiguration config) {
        return CompatibilityUtil.apiLevelCheck(26) ? ReflectUtil.getStaticInnerClassDeclareIntFieldSafely(config.getClass(), "NetworkSelectionStatus", "mNetworkSelectionDisableReason") : ReflectUtil.getDeclareIntFieldSafely(config.getClass(), config, "disableReason");
    }

    public void addNetwork(WifiConfiguration wcg) {
        this.a.enableNetwork(this.a.addNetwork(wcg), true);
    }

    private void b(AccessPoint point) {
        point.updateWifiInfo();
        if (getCurrentConnectionInfo().getIpAddress() != 0) {
            point.setDetailedState(NetworkInfo.DetailedState.CONNECTED);
            point.setSecurityString(this.c.getString(R.string.wifi_connected));
        }
    }

    public int getWifiSignalLevel(int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

    public void addNetwork(ArrayList<WifiConfiguration> wcgList) {
        if (wcgList == null || wcgList.isEmpty()) {
            return;
        }
        Iterator<WifiConfiguration> it = wcgList.iterator();
        while (it.hasNext()) {
            this.a.enableNetwork(this.a.addNetwork(it.next()), true);
        }
    }

    private void a() {
        IntentFilter intentFilter = new IntentFilter();
        this.d = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.d.addAction("android.net.wifi.SCAN_RESULTS");
        this.d.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        this.d.addAction("android.net.wifi.STATE_CHANGE");
        this.d.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        this.d.addAction("android.net.wifi.LINK_CONFIGURATION_CHANGED");
        this.d.addAction("android.net.wifi.RSSI_CHANGED");
        this.f = new a();
    }

    public WifiAdmin(Context context, Callback callback) {
        this(context);
        setCallback(callback);
    }

    public void addNetwork(String ssid, String password, int securityType) {
        addNetwork(createWifiConfiguration(ssid, password, securityType));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(RxCallback<List<AccessPoint>> callback) {
        d dVar = new d();
        getRxManager().enqueue(dVar, new b(callback, dVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AccessPoint> a(Collection<ScanResult> scanResults) {
        HashMap<String, AccessPoint> map = new HashMap<>();
        AccessPoint accessPoint = null;
        for (ScanResult scanResult : scanResults) {
            if (!StringUtils.isNullOrEmpty(WifiUtil.getUtf8Ssid(scanResult))) {
                AccessPoint accessPoint2 = new AccessPoint(scanResult, this.a);
                if (accessPoint2.getWifiConfiguration() != null && Debug.getDebug()) {
                    Log.e(h, accessPoint2.getWifiConfiguration().SSID + "(networkID):" + accessPoint2.getWifiConfiguration().networkId);
                }
                if (a(accessPoint2)) {
                    b(accessPoint2);
                    accessPoint = accessPoint2;
                }
                if (a(accessPoint2, map)) {
                    map.put(accessPoint2.getSsidForDisplay(), accessPoint2);
                }
            }
        }
        AccessPoint accessPoint3 = accessPoint;
        LinkedList linkedList = new LinkedList(map.values());
        Collections.sort(linkedList, new c());
        if (accessPoint3 != null) {
            linkedList.remove(accessPoint);
            linkedList.add(0, accessPoint);
        }
        return linkedList;
    }

    public WifiConfiguration createWifiConfiguration(String ssid, String password, int type) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        WifiConfiguration wifiConfigurationA = a(ssid);
        if (wifiConfigurationA != null) {
            this.a.removeNetwork(wifiConfigurationA.networkId);
        }
        if (type == 0) {
            wifiConfiguration.wepKeys[0] = TTFFont.UNKNOWN_FONT_NAME;
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (type == 1) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + password + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (type == 2) {
            wifiConfiguration.preSharedKey = "\"" + password + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.status = 2;
        }
        return wifiConfiguration;
    }

    private boolean a(AccessPoint accessPoint) {
        WifiInfo currentConnectionInfo = getCurrentConnectionInfo();
        return currentConnectionInfo != null && accessPoint.getWifiConfiguration() != null && accessPoint.getWifiConfiguration().networkId == currentConnectionInfo.getNetworkId() && currentConnectionInfo.getBSSID().equals(accessPoint.getScanResult().BSSID);
    }

    private boolean a(AccessPoint point, HashMap<String, AccessPoint> map) {
        AccessPoint accessPoint;
        if (!map.containsKey(point.getSsidForDisplay()) || (accessPoint = map.get(point.getSsidForDisplay())) == null) {
            return true;
        }
        if (a(accessPoint)) {
            return false;
        }
        if (a(point)) {
            return true;
        }
        return accessPoint.getSecurity() != point.getSecurity() && accessPoint.getSignalLevel() < point.getSignalLevel();
    }

    private int a(ScanResult result) {
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
        Log.w(h, "Received abnormal flag string: " + result.capabilities);
        return 0;
    }

    private WifiConfiguration a(String ssid) {
        List<WifiConfiguration> configuredNetworks = this.a.getConfiguredNetworks();
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
}

final class WifiAdmin$a$a extends RxCallback<List<AccessPoint>> {
    private final WifiAdmin.a receiver;

    WifiAdmin$a$a(WifiAdmin.a receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onNext(@NonNull List<AccessPoint> accessPoints) {
        receiver.owner.e.onScanResultReady(accessPoints);
    }
}

final class WifiAdmin$a$b extends RxCallback<List<AccessPoint>> {
    private final WifiAdmin.a receiver;

    WifiAdmin$a$b(WifiAdmin.a receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onNext(@NonNull List<AccessPoint> accessPoints) {
        receiver.owner.e.onLinkConfigurationChange(accessPoints);
    }
}

final class WifiAdmin$a$c extends RxCallback<List<AccessPoint>> {
    private final WifiAdmin.a receiver;

    WifiAdmin$a$c(WifiAdmin.a receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onNext(@NonNull List<AccessPoint> accessPoints) {
        receiver.owner.e.onConfiguredNetworksChange(accessPoints);
    }
}
