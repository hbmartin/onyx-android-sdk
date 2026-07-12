package com.onyx.android.sdk.wifi;

public enum WifiSignalLevel {
    NO_SIGNAL(0, "no signal"),
    POOR(1, "poor"),
    FAIR(2, "fair"),
    GOOD(3, "good"),
    EXCELLENT(4, "excellent"),
    PERFECT(5, "perfect");

    public final int level;
    public final String description;

    WifiSignalLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public static int getMaxLevel() {
        return PERFECT.level;
    }

    public static WifiSignalLevel fromLevel(int level) {
        for (WifiSignalLevel wifiSignalLevel : values()) {
            if (wifiSignalLevel.level == level) {
                return wifiSignalLevel;
            }
        }
        return NO_SIGNAL;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "WifiSignalLevel{level=" + this.level + ", description='" + this.description + "'}";
    }
}
