package com.onyx.android.sdk.api.device;

import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/EpdRk32xx.class */
public class EpdRk32xx extends EpdDevice {
    private void useFastScheme() {
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyGCUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.GC);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyRegalUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.REGAL);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyRegalClearUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.REGAL_D);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void setUpdateMode(View view, UpdateMode mode) {
        EpdController.setViewDefaultUpdateMode(view, mode);
        useFastScheme();
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void resetUpdate(View view) {
        EpdController.resetUpdateMode(view);
        useFastScheme();
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void cleanUpdate(View view) {
        resetUpdate(view);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void enableRegal() {
        EpdController.enableRegal();
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void disableRegal() {
        EpdController.disableRegal();
    }
}
