package com.onyx.android.sdk.api.device.tp;

public class UpgradeConfig {
    public String tpType;
    public boolean force;
    public String path;

    public final String toString() {
        return "tp_type=" + this.tpType + " force=" + (this.force ? 1 : 0) + " path=" + this.path;
    }
}
