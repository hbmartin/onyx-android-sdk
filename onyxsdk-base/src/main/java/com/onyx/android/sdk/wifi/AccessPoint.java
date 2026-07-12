package com.onyx.android.sdk.wifi;

import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.onyx.android.sdk.BR;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.CompatibilityUtil;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.wifi.common.BaseSecurity;
import com.onyx.android.sdk.wifi.common.SecurityAboveAndroidO;
import com.onyx.android.sdk.wifi.common.SecurityAboveAndroidR;
import com.onyx.android.sdk.wifi.common.SecurityAboveAndroidS;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/AccessPoint.class */
public class AccessPoint extends BaseObservable {
    private WifiManager a;
    private ScanResult b;
    private WifiConfiguration c;
    private int d;
    private String e;
    private String f;
    private int g;
    private int[] h;
    private WifiInfo i;
    private String j;
    private NetworkInfo.DetailedState k;
    private int l = -1;
    private BaseSecurity m = createBaseSecurity();

    public AccessPoint(ScanResult result, WifiManager wifiManager) {
        this.b = result;
        this.a = wifiManager;
        d();
        b();
        c();
        e();
        f();
        a();
        updateWifiInfo();
    }

    private void d() {
        WifiConfiguration wifiConfigurationA = a(this.b);
        this.c = wifiConfigurationA;
        if (wifiConfigurationA != null) {
            if (CompatibilityUtil.apiLevelCheck(31) || this.c.status == 1) {
                this.l = this.m.getDisableReason(this.c);
            }
        }
    }

    private void b() {
        this.d = BaseSecurity.getSecurity(this.b);
    }

    private void c() {
        this.f = BaseSecurity.getSecurityMode(this.b, false, this.d);
    }

    private void e() {
        this.e = this.m.getSecurityString(this);
    }

    private void f() {
        this.g = WifiUtil.getWifiSignalLevel(this.b.level);
    }

    private void a() {
        this.h = BaseSecurity.getWifiImageState(this.d);
    }

    public static void logScanResult(String TAG, List<AccessPoint> scanResult) {
        HashSet hashSet = new HashSet();
        for (AccessPoint accessPoint : scanResult) {
            if (accessPoint == null || accessPoint.getScanResult() == null) {
                Log.e(TAG, "null ap found");
            } else {
                ScanResult scanResult2 = accessPoint.getScanResult();
                String utf8Ssid = WifiUtil.getUtf8Ssid(scanResult2);
                Log.e(TAG, "AccessPoint SSID:" + utf8Ssid + " BSSID: " + scanResult2.BSSID);
                if (hashSet.contains(utf8Ssid)) {
                    Log.e(TAG, "Duplicated id found:" + utf8Ssid + " bssid: " + scanResult2.BSSID);
                }
                hashSet.add(utf8Ssid);
            }
        }
        Log.e(TAG, "Result Size:" + scanResult.size());
    }

    protected BaseSecurity createBaseSecurity() {
        if (CompatibilityUtil.apiLevelCheck(31)) {
            this.m = new SecurityAboveAndroidS();
        } else if (CompatibilityUtil.apiLevelCheck(30)) {
            this.m = new SecurityAboveAndroidR();
        } else if (CompatibilityUtil.apiLevelCheck(26)) {
            this.m = new SecurityAboveAndroidO();
        } else {
            this.m = new BaseSecurity();
        }
        return this.m;
    }

    @Bindable
    public ScanResult getScanResult() {
        return this.b;
    }

    public void resetWifiConfiguration() {
        this.c = null;
    }

    @Bindable
    public WifiConfiguration getWifiConfiguration() {
        return this.c;
    }

    public boolean isSecurity() {
        return BaseSecurity.isSecurity(getSecurity());
    }

    @Bindable
    public int getSecurity() {
        return this.d;
    }

    @Bindable
    public String getSecurityMode() {
        return this.f;
    }

    public void setSecurityString(String string) {
        this.e = string;
        notifyPropertyChanged(BR.securityString);
    }

    @Bindable
    public String getSecurityString() {
        return this.e;
    }

    public void setSignalLevel(int level) {
        this.g = level;
    }

    @Bindable
    public int getSignalLevel() {
        return this.g;
    }

    @Bindable
    public int[] getImageState() {
        return this.h;
    }

    public void updateWifiInfo() {
        this.i = b(this.b);
    }

    @Bindable
    public WifiInfo getWifiInfo() {
        return this.i;
    }

    public void setWifiInfo(WifiInfo wifiInfo) {
        this.i = wifiInfo;
    }

    public void setPassword(String pwd) {
        this.j = pwd;
    }

    @Bindable
    public String getPassword() {
        return this.j;
    }

    @Bindable
    public NetworkInfo.DetailedState getDetailedState() {
        return this.k;
    }

    public void setDetailedState(NetworkInfo.DetailedState state) {
        this.k = state;
        e();
        notifyPropertyChanged(BR.detailedState);
    }

    @Bindable
    public int getDisableReason() {
        return this.l;
    }

    public boolean isConnected() {
        return getWifiInfo() != null;
    }

    public String getSsidForDisplay() {
        return WifiUtil.getUtf8Ssid(this.b);
    }

    private WifiInfo b(ScanResult result) {
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

    @Nullable
    private WifiConfiguration a(ScanResult result) {
        List<WifiConfiguration> configuredNetworks = this.a.getConfiguredNetworks();
        if (CollectionUtils.isNullOrEmpty(configuredNetworks)) {
            return null;
        }
        String utf8Ssid = WifiUtil.getUtf8Ssid(result);
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (WifiUtil.isSameSSID(WifiUtil.getUtf8Ssid(wifiConfiguration.SSID), utf8Ssid)) {
                return wifiConfiguration;
            }
        }
        return null;
    }
}
