package com.onyx.android.sdk.pen.data;

public enum RotateType {
    FREEDOM(-32768.0f),
    CW_90(90.0f),
    CWW_90(-90.0f);

    private float a;

    RotateType(float angle) {
        this.a = angle;
    }

    public float getAngle() {
        return this.a;
    }
}

