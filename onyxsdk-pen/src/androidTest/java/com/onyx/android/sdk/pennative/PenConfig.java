package com.onyx.android.sdk.pennative;

/** Minimal test-only value object matching the installed Notable JNI field contract. */
public final class PenConfig {
    private int type;
    private boolean fastMode;
    private int color = 0xff000000;
    private float width = 3f;
    private float minWidth;
    private int rotateAngle;
    private boolean tiltEnabled;
    private float tiltScale;
    private boolean directionEnabled;
    private float maxTouchPressure = 1f;
    private float dpi = 320f;
    private float displayScaleX = 1f;
    private float displayScaleY = 1f;
    private float scalePrecision = 1f;
    private float brushSpacing = 0.25f;
    private int brushShape;
    private float brushRatio = 5f;
    private float brushAngle;
    private float pressureSensitivity;
    private float velocitySensitivity;
    private float velocityAmplifier;
    private float velocityIgnoreThreshold;
    private float velocityLowerBound;
    private float velocityUpperBound;
    private float smoothLevel = 0.2f;
    private float startPointLimit;
    private float startLengthLimit;
    private float endVelocitySensitivity;
    private float endThinningRate;
    private float ignorePressure;

    public PenConfig setType(int value) { type = value; return this; }
    public PenConfig setFastMode(boolean value) { fastMode = value; return this; }
}
