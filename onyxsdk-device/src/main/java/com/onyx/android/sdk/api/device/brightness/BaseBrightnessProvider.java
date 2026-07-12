package com.onyx.android.sdk.api.device.brightness;

import android.content.Context;
import com.onyx.android.sdk.device.Device;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseBrightnessProvider {
    protected Context appContext;

    public BaseBrightnessProvider(Context appContext) {
        this.appContext = appContext;
    }

    public Integer getValue() {
        return Device.currentDevice().getLightValues(getType());
    }

    public boolean setValue(int value) {
        return Device.currentDevice().setLightValue(getType(), value);
    }

    public boolean isLightOn() {
        return Device.currentDevice().isLightOn(this.appContext, getType());
    }

    public boolean open() {
        return Device.currentDevice().openFrontLight(getType());
    }

    public boolean close() {
        return Device.currentDevice().closeFrontLight(getType());
    }

    public void toggle() {
        if (isLightOn()) {
            close();
        } else {
            open();
        }
    }

    public List<Integer> getValues() {
        return new ArrayList();
    }

    public abstract int getType();

    public abstract int getMaxIndex();

    public abstract boolean setIndex(int i);

    public abstract Integer getIndex();

    public abstract Integer getValueByIndex(int i);
}
