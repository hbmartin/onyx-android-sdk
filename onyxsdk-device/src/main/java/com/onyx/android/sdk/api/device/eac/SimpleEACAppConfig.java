package com.onyx.android.sdk.api.device.eac;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/eac/SimpleEACAppConfig.class */
public class SimpleEACAppConfig {
    private String pkgName;
    private boolean enable = true;

    public String getPkgName() {
        return this.pkgName;
    }

    public SimpleEACAppConfig setPkgName(String pkgName) {
        this.pkgName = pkgName;
        return this;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
