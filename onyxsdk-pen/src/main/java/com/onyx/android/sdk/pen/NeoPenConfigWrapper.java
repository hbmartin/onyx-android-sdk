package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.data.note.ShapeCreateArgs;
import com.onyx.android.sdk.device.Device;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/NeoPenConfigWrapper.class */
public class NeoPenConfigWrapper {
    public static final int NEOPEN_PEN_TYPE_BRUSH = 1;
    public static final int NEOPEN_PEN_TYPE_FOUNTAIN = 2;
    public static final int NEOPEN_PEN_TYPE_MARKER = 3;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL = 4;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL_V2 = 5;
    public static final float TILT_SCALE_VALUE = 5.0f;
    public int type = 1;
    public int color = -16777216;
    public float width = 3.0f;
    public int rotateAngle = 0;
    public boolean tiltEnabled = false;
    public float tiltScale = 3.0f;
    public float maxTouchPressure = 4095.0f;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void initPenConfig(NeoPenConfigWrapper config, ShapeCreateArgs createArgs) {
        if (createArgs.getTiltConfig() != null) {
            config.tiltEnabled = createArgs.tiltConfig.isTiltEnabled();
            config.tiltScale = createArgs.tiltConfig.getTiltScale();
            return;
        }
        float[] strokeParameters = Device.currentDevice().getStrokeParameters(4);
        if (strokeParameters == null || strokeParameters.length < 2) {
            return;
        }
        config.tiltEnabled = strokeParameters[0] != com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY;
        config.tiltScale = strokeParameters[1];
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getType() {
        return this.type;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setType(int type) {
        this.type = type;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getColor() {
        return this.color;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setColor(int color) {
        this.color = color;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public float getWidth() {
        return this.width;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setWidth(float width) {
        this.width = width;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getRotateAngle() {
        return this.rotateAngle;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setRotateAngle(int rotateAngle) {
        this.rotateAngle = rotateAngle;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isTiltEnabled() {
        return this.tiltEnabled;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setTiltEnabled(boolean tiltEnabled) {
        this.tiltEnabled = tiltEnabled;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public float getTiltScale() {
        return this.tiltScale;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setTiltScale(float tiltScale) {
        this.tiltScale = tiltScale;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public float getMaxTouchPressure() {
        return this.maxTouchPressure;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public NeoPenConfigWrapper setMaxTouchPressure(float maxTouchPressure) {
        this.maxTouchPressure = maxTouchPressure;
        return this;
    }
}

