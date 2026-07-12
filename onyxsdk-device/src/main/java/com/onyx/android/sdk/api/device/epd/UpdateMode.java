package com.onyx.android.sdk.api.device.epd;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/epd/UpdateMode.class */
public enum UpdateMode {
    None,
    DU,
    DU4,
    GU,
    GU_FAST,
    GC,
    GCC,
    DEEP_GC,
    ANIMATION,
    ANIMATION_QUALITY,
    ANIMATION_MONO,
    ANIMATION_X,
    GC4,
    REGAL,
    REGAL_D,
    REGAL_PLUS,
    DU_QUALITY,
    HAND_WRITING_REPAINT_MODE;

    public static UpdateMode getTypeByName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException unused) {
            return None;
        }
    }
}
