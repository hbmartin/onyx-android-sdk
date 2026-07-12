package com.onyx.android.sdk.api.device.brightness;

import android.content.Context;
import com.onyx.android.sdk.api.utils.CollectionUtils;
import com.onyx.android.sdk.device.Device;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarmBrightnessProvider extends BaseBrightnessProvider {
    private ArrayList<Integer> values;

    public WarmBrightnessProvider(Context appContext) {
        super(appContext);
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public int getType() {
        return 2;
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public List<Integer> getValues() {
        if (CollectionUtils.isNullOrEmpty(this.values)) {
            this.values = new ArrayList<>(Arrays.asList(Device.currentDevice().getWarmLightValues(this.appContext)));
        }
        return this.values;
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public Integer getValueByIndex(int index) {
        return getValues().get(index);
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public Integer getIndex() {
        return Integer.valueOf(CollectionUtils.isNullOrEmpty(getValues()) ? -1 : getValues().indexOf(getValue()));
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public boolean setIndex(int index) {
        if (CollectionUtils.isNullOrEmpty(getValues()) || index < 0) {
            return false;
        }
        return Device.currentDevice().setLightValue(getType(), getValues().get(index).intValue());
    }

    @Override // com.onyx.android.sdk.api.device.brightness.BaseBrightnessProvider
    public int getMaxIndex() {
        return getValues().size() - 1;
    }
}
