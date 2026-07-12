package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.utils.StringUtils;

public enum CanvasExpandType {
    DEFAULT,
    HEIGHT_EXPAND,
    WIDTH_EXPAND,
    WIDTH_HEIGHT_EXPAND,
    CUSTOM,
    CLIP;

    public static int getMaxExpandMultiple() {
        return 2;
    }

    public static CanvasExpandType safelyValueOf(String menuId) {
        if (StringUtils.isNullOrEmpty(menuId)) {
            return DEFAULT;
        }
        menuId.hashCode();
        switch (menuId) {
            case "WIDTH_HEIGHT_EXPAND":
                return WIDTH_HEIGHT_EXPAND;
            case "WIDTH_EXPAND":
                return WIDTH_EXPAND;
            case "HEIGHT_EXPAND":
                return HEIGHT_EXPAND;
            case "EXPAND_CUSTOM":
                return CUSTOM;
            case "EXPAND_CLIP":
                return CLIP;
            default:
                return DEFAULT;
        }
    }

    public boolean isCustomCanvasExpandType() {
        return this == CUSTOM;
    }

    public boolean isDefaultCanvasExpandType() {
        return this == DEFAULT;
    }
}
