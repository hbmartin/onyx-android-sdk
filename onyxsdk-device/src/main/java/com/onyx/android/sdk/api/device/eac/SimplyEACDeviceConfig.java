package com.onyx.android.sdk.api.device.eac;

import android.text.TextUtils;
import com.onyx.android.sdk.utils.Debug;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/eac/SimplyEACDeviceConfig.class */
public class SimplyEACDeviceConfig {
    private Map<String, SimpleEACAppConfig> appConfigMap = new ConcurrentHashMap();
    private Set<String> eacWhiteSet = Collections.newSetFromMap(new ConcurrentHashMap());

    public static SimplyEACDeviceConfig createDummyConfig() {
        return createDummyConfig(true);
    }

    public void setAppConfigMap(Map<String, SimpleEACAppConfig> map) {
        this.appConfigMap.clear();
        this.appConfigMap.putAll(map);
    }

    public Map<String, SimpleEACAppConfig> getAppConfigMap() {
        return this.appConfigMap;
    }

    public boolean contains(String pkg) {
        return this.appConfigMap.containsKey(pkg);
    }

    public SimpleEACAppConfig getAppConfig(String pkg) {
        if (!TextUtils.isEmpty(pkg)) {
            return this.appConfigMap.get(pkg);
        }
        Debug.d((Class<?>) SimplyEACDeviceConfig.class, "null pkg is not allowed", new Object[0]);
        return null;
    }

    public void setAppConfig(SimpleEACAppConfig appConfig) {
        if (TextUtils.isEmpty(appConfig.getPkgName())) {
            return;
        }
        this.appConfigMap.put(appConfig.getPkgName(), appConfig);
    }

    public Set<String> getEACWhiteSet() {
        return this.eacWhiteSet;
    }

    public SimplyEACDeviceConfig setEACWhiteSet(Set<String> eacWhiteSet) {
        this.eacWhiteSet.clear();
        this.eacWhiteSet.addAll(eacWhiteSet);
        return this;
    }

    public boolean isEACWhiteApp(String pkg) {
        return this.eacWhiteSet.contains(pkg);
    }

    public boolean isAppEACEnable(String packageName) {
        if (this.appConfigMap.containsKey(packageName)) {
            return this.appConfigMap.get(packageName).isEnable();
        }
        return false;
    }

    public static SimplyEACDeviceConfig createDummyConfig(boolean deviceConstantReady) {
        return new SimplyEACDeviceConfig();
    }
}
