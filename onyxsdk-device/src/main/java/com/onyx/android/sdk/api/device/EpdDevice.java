package com.onyx.android.sdk.api.device;

import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/EpdDevice.class */
public class EpdDevice {
    public void applyGCUpdate(View view) {
    }

    public void applyDeepGCUpdate(View view) {
    }

    public void applyRegalUpdate(View view) {
    }

    public void applyRegalClearUpdate(View view) {
    }

    public void setUpdateMode(View view, UpdateMode mode) {
        EpdController.setViewDefaultUpdateMode(view, mode);
    }

    public void resetUpdate(View view) {
    }

    public void cleanUpdate(View view) {
    }

    public void enableScreenUpdate(View view, boolean enable) {
        EpdController.enableScreenUpdate(view, enable);
    }

    public void refreshScreen(View view, UpdateMode updateMode) {
        EpdController.refreshScreenRegion(view, 0, 0, 1000, 1000, updateMode);
    }

    public void holdDisplay(boolean hold, UpdateMode updateMode, int ignoreFrame) {
    }

    public void enableRegal() {
        EpdController.enableRegal();
    }

    public void disableRegal() {
        EpdController.disableRegal();
    }

    public void applyGCOnce() {
        EpdController.applyGCOnce();
    }
}
