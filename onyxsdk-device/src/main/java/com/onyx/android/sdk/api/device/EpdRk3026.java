package com.onyx.android.sdk.api.device;

import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/EpdRk3026.class */
public class EpdRk3026 extends EpdDevice {
    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyGCUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.GC);
        view.invalidate();
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void setUpdateMode(View view, UpdateMode mode) {
        EpdController.setViewDefaultUpdateMode(view, mode);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyRegalUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.REGAL);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void applyRegalClearUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.REGAL);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void resetUpdate(View view) {
        EpdController.setViewDefaultUpdateMode(view, UpdateMode.GU);
    }

    @Override // com.onyx.android.sdk.api.device.EpdDevice
    public void holdDisplay(boolean hold, UpdateMode mode, int ignoreFrame) {
        EpdController.holdDisplay(hold, mode, ignoreFrame);
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
