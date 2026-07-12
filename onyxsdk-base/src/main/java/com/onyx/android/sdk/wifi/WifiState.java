package com.onyx.android.sdk.wifi;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/WifiState.class */
public enum WifiState {
    DISABLING(0, "disabling"),
    DISABLED(1, "disabled"),
    ENABLING(2, "enabling"),
    ENABLED(3, "enabled"),
    UNKNOWN(4, "unknown");

    public final int state;
    public final String description;

    WifiState(int state, String description) {
        this.state = state;
        this.description = description;
    }

    public static WifiState fromState(int state) {
        for (WifiState wifiState : values()) {
            if (wifiState.state == state) {
                return wifiState;
            }
        }
        return UNKNOWN;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "WifiState{state=" + this.state + ", description='" + this.description + "'}";
    }
}
