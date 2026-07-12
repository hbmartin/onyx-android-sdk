/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.onyx.android.sdk.data.note.ShapeCreateArgs
 *  com.onyx.android.sdk.device.Device
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.data.note.ShapeCreateArgs;
import com.onyx.android.sdk.device.Device;

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

    public static void initPenConfig(NeoPenConfigWrapper config, ShapeCreateArgs createArgs) {
        float[] fArray;
        if (createArgs.getTiltConfig() != null) {
            var0.tiltEnabled = fArray.tiltConfig.isTiltEnabled();
            var0.tiltScale = fArray.tiltConfig.getTiltScale();
            return;
        }
        fArray = Device.currentDevice().getStrokeParameters(4);
        if (fArray != null && fArray.length >= 2) {
            boolean bl = fArray[0] != 0.0f;
            var0.tiltEnabled = bl;
            var0.tiltScale = fArray[1];
        }
    }

    public int getType() {
        return this.type;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setType(int type) {
        void var1_1;
        this.type = var1_1;
        return this;
    }

    public int getColor() {
        return this.color;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setColor(int color) {
        void var1_1;
        this.color = var1_1;
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setWidth(float width) {
        void var1_1;
        this.width = var1_1;
        return this;
    }

    public int getRotateAngle() {
        return this.rotateAngle;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setRotateAngle(int rotateAngle) {
        void var1_1;
        this.rotateAngle = var1_1;
        return this;
    }

    public boolean isTiltEnabled() {
        return this.tiltEnabled;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setTiltEnabled(boolean tiltEnabled) {
        void var1_1;
        this.tiltEnabled = var1_1;
        return this;
    }

    public float getTiltScale() {
        return this.tiltScale;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setTiltScale(float tiltScale) {
        void var1_1;
        this.tiltScale = var1_1;
        return this;
    }

    public float getMaxTouchPressure() {
        return this.maxTouchPressure;
    }

    /*
     * WARNING - void declaration
     */
    public NeoPenConfigWrapper setMaxTouchPressure(float maxTouchPressure) {
        void var1_1;
        this.maxTouchPressure = var1_1;
        return this;
    }
}

