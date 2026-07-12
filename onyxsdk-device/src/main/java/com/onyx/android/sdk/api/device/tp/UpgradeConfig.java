package com.onyx.android.sdk.api.device.tp;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/tp/UpgradeConfig.class */
public class UpgradeConfig {
    public String tpType;
    public boolean force;
    public String path;

    public final String toString() {
        return "tp_type=" + this.tpType + " force=" + (this.force ? 1 : 0) + " path=" + this.path;
    }
}
