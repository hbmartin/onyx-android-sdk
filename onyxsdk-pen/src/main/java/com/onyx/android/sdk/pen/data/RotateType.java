package com.onyx.android.sdk.pen.data;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/data/RotateType.class */
public enum RotateType {
    FREEDOM(-32768.0f),
    CW_90(90.0f),
    CWW_90(-90.0f);

    private float a;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    RotateType(float angle) {
        this.a = angle;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public float getAngle() {
        return this.a;
    }
}

