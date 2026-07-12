package com.onyx.android.sdk.data.note;

import androidx.annotation.NonNull;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.ReaderTextStyle;

public class ShapeCreateArgs implements Cloneable {
    public TiltConfig tiltConfig;
    private PenAttrs b;
    public int tiltX;
    public int tiltY;
    public float pressure;
    private float a = 1.0f;
    public float maxPressure = EpdController.MAX_TOUCH_PRESSURE;
    public float alphaFactor = 1.0f;
    public float smoothLevel = 0.2f;
    public float dpi = 320.0f;
    private int c = 0;
    private Float d = Float.valueOf(1.0f);
    public float newBrushRatio = ReaderTextStyle.FONT_EMBOLDEN_NORMAL;

    public float getDisplayScale() {
        return this.a;
    }

    @JSONField(serialize = false, deserialize = false)
    public float getRevisedDisplayScale() {
        if (Float.compare(this.a, ReaderTextStyle.FONT_EMBOLDEN_NORMAL) == 0) {
            this.a = 1.0f;
        }
        return this.a;
    }

    public ShapeCreateArgs setDisplayScale(float displayScale) {
        this.a = displayScale;
        return this;
    }

    public ShapeCreateArgs setMaxPressure(float maxPressure) {
        this.maxPressure = maxPressure;
        return this;
    }

    public float getMaxPressure() {
        return this.maxPressure;
    }

    public ShapeCreateArgs setPenAttrs(PenAttrs penAttrs) {
        this.b = penAttrs;
        return this;
    }

    public PenAttrs getPenAttrs() {
        return this.b;
    }

    public ShapeCreateArgs setSource(int source) {
        this.c = source;
        return this;
    }

    public int getSource() {
        return this.c;
    }

    public ShapeCreateArgs setTiltConfig(TiltConfig tiltConfig) {
        this.tiltConfig = tiltConfig;
        return this;
    }

    public TiltConfig getTiltConfig() {
        return this.tiltConfig;
    }

    @JSONField(serialize = false, deserialize = false)
    public float getCreateScaleValue(float value) {
        return this.a == ReaderTextStyle.FONT_EMBOLDEN_NORMAL ? value : value / getDisplayScale();
    }

    public Float getPressureSensitivity() {
        return this.d;
    }

    public ShapeCreateArgs setPressureSensitivity(float pressureSensitivity) {
        this.d = Float.valueOf(pressureSensitivity);
        return this;
    }

    public float getNewBrushRatio() {
        return this.newBrushRatio;
    }

    public ShapeCreateArgs setNewBrushRatio(float newBrushRatio) {
        this.newBrushRatio = newBrushRatio;
        return this;
    }

    @NonNull
    public ShapeCreateArgs clone() throws CloneNotSupportedException {
        ShapeCreateArgs shapeCreateArgs = new ShapeCreateArgs();
        shapeCreateArgs.a = this.a;
        shapeCreateArgs.maxPressure = this.maxPressure;
        shapeCreateArgs.tiltConfig = this.tiltConfig;
        shapeCreateArgs.b = this.b;
        shapeCreateArgs.d = this.d;
        shapeCreateArgs.smoothLevel = this.smoothLevel;
        shapeCreateArgs.tiltX = this.tiltX;
        shapeCreateArgs.tiltY = this.tiltY;
        shapeCreateArgs.pressure = this.pressure;
        shapeCreateArgs.newBrushRatio = this.newBrushRatio;
        return shapeCreateArgs;
    }
}
