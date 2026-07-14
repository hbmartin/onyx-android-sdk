package com.onyx.android.sdk.pennative;

/** Minimal test-only value object matching the installed Notable JNI field contract. */
public final class PenConfig {
    public static final int NEOPEN_PEN_TYPE_BRUSH = 1;
    public static final int NEOPEN_PEN_TYPE_FOUNTAIN = 2;
    public static final int NEOPEN_PEN_TYPE_MARKER = 3;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL = 4;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL_V2 = 5;
    public static final int NEOPEN_PEN_TYPE_FOUNTAIN_V2 = 6;
    public static final int NEOPEN_PEN_TYPE_PENCIL = 7;
    public static final int NEOPEN_PEN_TYPE_BALLPOINT = 8;
    public static final int NEOPEN_PEN_TYPE_SQUARE = 9;
    public static final int NEOPEN_PEN_TYPE_BRUSH_SIGN = 10;

    public static final int NEOPEN_BRUSH_SHAPE_CIRCLE = 0;
    public static final int NEOPEN_BRUSH_SHAPE_ELLIPSE = 1;
    public static final int NEOPEN_BRUSH_SHAPE_RECTANGLE = 2;

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
    public PenConfig setColor(int value) { color = value; return this; }
    public PenConfig setWidth(float value) { width = value; return this; }
    public PenConfig setMinWidth(float value) { minWidth = value; return this; }
    public PenConfig setRotateAngle(int value) { rotateAngle = value; return this; }
    public PenConfig setTiltEnabled(boolean value) { tiltEnabled = value; return this; }
    public PenConfig setTiltScale(float value) { tiltScale = value; return this; }
    public PenConfig setDirectionEnabled(boolean value) { directionEnabled = value; return this; }
    public PenConfig setMaxTouchPressure(float value) { maxTouchPressure = value; return this; }
    public PenConfig setDpi(float value) { dpi = value; return this; }
    public PenConfig setDisplayScaleX(float value) { displayScaleX = value; return this; }
    public PenConfig setDisplayScaleY(float value) { displayScaleY = value; return this; }
    public PenConfig setScalePrecision(float value) { scalePrecision = value; return this; }
    public PenConfig setBrushSpacing(float value) { brushSpacing = value; return this; }
    public PenConfig setBrushShape(int value) { brushShape = value; return this; }
    public PenConfig setBrushRatio(float value) { brushRatio = value; return this; }
    public PenConfig setBrushAngle(float value) { brushAngle = value; return this; }
    public PenConfig setPressureSensitivity(float value) { pressureSensitivity = value; return this; }
    public PenConfig setVelocitySensitivity(float value) { velocitySensitivity = value; return this; }
    public PenConfig setVelocityAmplifier(float value) { velocityAmplifier = value; return this; }
    public PenConfig setVelocityIgnoreThreshold(float value) { velocityIgnoreThreshold = value; return this; }
    public PenConfig setVelocityLowerBound(float value) { velocityLowerBound = value; return this; }
    public PenConfig setVelocityUpperBound(float value) { velocityUpperBound = value; return this; }
    public PenConfig setSmoothLevel(float value) { smoothLevel = value; return this; }
    public PenConfig setStartPointLimit(float value) { startPointLimit = value; return this; }
    public PenConfig setStartLengthLimit(float value) { startLengthLimit = value; return this; }
    public PenConfig setEndVelocitySensitivity(float value) { endVelocitySensitivity = value; return this; }
    public PenConfig setEndThinningRate(float value) { endThinningRate = value; return this; }
    public PenConfig setIgnorePressure(float value) { ignorePressure = value; return this; }
}
