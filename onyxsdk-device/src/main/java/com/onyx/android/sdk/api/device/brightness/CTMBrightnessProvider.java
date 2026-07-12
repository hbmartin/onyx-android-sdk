package com.onyx.android.sdk.api.device.brightness;

import android.content.Context;
import com.onyx.android.sdk.device.Device;

public class CTMBrightnessProvider extends BaseBrightnessProvider {
    public CTMBrightnessProvider(Context appContext) {
        super(appContext);
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public int getType() {
        return 7;
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public Integer getValueByIndex(int index) {
        return Integer.valueOf(index);
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public Integer getIndex() {
        return Device.currentDevice().getLightValues(getType());
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public boolean setIndex(int index) {
        return Device.currentDevice().setLightValue(getType(), index);
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public int getMaxIndex() {
        return Device.currentDevice().getMaxLightValues(getType()).intValue();
    }
}
