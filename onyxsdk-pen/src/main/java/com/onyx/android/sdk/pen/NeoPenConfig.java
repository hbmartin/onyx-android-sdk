package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPenConfig.class */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b#\u0018\u0000 92\u00020\u0001:\u00019B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010+\u001a\u00020\fJ\u0006\u0010,\u001a\u00020\u0004J\u0006\u0010-\u001a\u00020\fJ\u0006\u0010.\u001a\u00020\u0004J\u0006\u0010/\u001a\u00020\fJ\u0006\u00100\u001a\u00020\u0004J\u0006\u00101\u001a\u00020\u0017J\u000e\u00102\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\fJ\u000e\u00103\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0004J\u000e\u00104\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\fJ\u000e\u00105\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020\u0017J\u000e\u00106\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0004J\u000e\u00107\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\fJ\u000e\u00108\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\"\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001b\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001c\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001f\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010 \u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\"\u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010$\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010%\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010&\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010(\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010)\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010*\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenConfig;", "Ljava/io/Serializable;", "()V", "alphaFactor", "", "getAlphaFactor", "()F", "setAlphaFactor", "(F)V", "brushAngle", "brushRatio", "brushShape", "", "brushShapes", "", "Landroid/graphics/Bitmap;", "getBrushShapes", "()Ljava/util/List;", "setBrushShapes", "(Ljava/util/List;)V", "brushSpacing", "color", "directionEnabled", "", "displayScaleX", "displayScaleY", "dpi", "fastMode", "maxTouchPressure", "minWidth", "pressureSensitivity", "rotateAngle", "scalePrecision", "smoothLevel", "tiltEnabled", "tiltScale", "type", "velocityAmplifier", "velocityIgnoreThreshold", "velocityLowerBound", "velocitySensitivity", "velocityUpperBound", "width", "getColor", "getMaxTouchPressure", "getRotateAngle", "getTiltScale", "getType", "getWidth", "isTiltEnabled", "setColor", "setMaxTouchPressure", "setRotateAngle", "setTiltEnabled", "setTiltScale", "setType", "setWidth", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoPenConfig implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int NEOPEN_BRUSH_SHAPE_CIRCLE = 0;
    public static final int NEOPEN_BRUSH_SHAPE_ELLIPSE = 1;
    public static final int NEOPEN_BRUSH_SHAPE_RECTANGLE = 2;
    public static final int NEOPEN_PEN_TYPE_BALLPOINT = 8;
    public static final int NEOPEN_PEN_TYPE_BRUSH = 1;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL = 4;
    public static final int NEOPEN_PEN_TYPE_CHARCOAL_V2 = 5;
    public static final int NEOPEN_PEN_TYPE_FOUNTAIN = 2;
    public static final int NEOPEN_PEN_TYPE_FOUNTAIN_V2 = 6;
    public static final int NEOPEN_PEN_TYPE_MARKER = 3;
    public static final int NEOPEN_PEN_TYPE_PENCIL = 7;
    public static final int NEOPEN_PEN_TYPE_SQUARE = 9;
    public static final float TILT_SCALE_VALUE = 5.0f;
    /** Preserves the recovered renderer's established output. */
    public static final int RENDERER_RECOVERED_V1 = 1;
    /** Enables the configurable smoothing/velocity/tilt renderer. */
    public static final int RENDERER_REFERENCE_COMPAT = 2;

    @Nullable
    private List<Bitmap> b;

    @JvmField
    public float brushAngle;

    @JvmField
    public int brushShape;

    @JvmField
    public boolean directionEnabled;

    @JvmField
    public boolean fastMode;

    /**
     * Selects the native rendering algorithm. The default deliberately keeps
     * the behavior shipped by the recovered SDK; callers must opt in to the
     * reference-compatible renderer.
     */
    @JvmField
    public int rendererVersion = RENDERER_RECOVERED_V1;

    @JvmField
    public int rotateAngle;

    @JvmField
    public boolean tiltEnabled;

    @JvmField
    public float velocityAmplifier;

    @JvmField
    public float velocityIgnoreThreshold;

    @JvmField
    public float velocityLowerBound;

    @JvmField
    public float velocityUpperBound;

    @JvmField
    public int type = 1;

    @JvmField
    public int color = -16777216;

    @JvmField
    public float width = 3.0f;

    @JvmField
    public float minWidth = 0.001f;

    @JvmField
    public float tiltScale = 3.0f;

    @JvmField
    public float maxTouchPressure = 1.0f;

    @JvmField
    public float dpi = 320.0f;

    @JvmField
    public float displayScaleX = 1.0f;

    @JvmField
    public float displayScaleY = 1.0f;

    @JvmField
    public float scalePrecision = 1.0f;

    @JvmField
    public float brushSpacing = 0.25f;

    @JvmField
    public float brushRatio = 5.0f;

    @JvmField
    public float pressureSensitivity = 0.3f;

    @JvmField
    public float velocitySensitivity = 0.5f;

    @JvmField
    public float smoothLevel = 0.2f;
    private float a = 1.0f;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPenConfig$Companion.class */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenConfig$Companion;", "", "()V", "NEOPEN_BRUSH_SHAPE_CIRCLE", "", "NEOPEN_BRUSH_SHAPE_ELLIPSE", "NEOPEN_BRUSH_SHAPE_RECTANGLE", "NEOPEN_PEN_TYPE_BALLPOINT", "NEOPEN_PEN_TYPE_BRUSH", "NEOPEN_PEN_TYPE_CHARCOAL", "NEOPEN_PEN_TYPE_CHARCOAL_V2", "NEOPEN_PEN_TYPE_FOUNTAIN", "NEOPEN_PEN_TYPE_FOUNTAIN_V2", "NEOPEN_PEN_TYPE_MARKER", "NEOPEN_PEN_TYPE_PENCIL", "NEOPEN_PEN_TYPE_SQUARE", "TILT_SCALE_VALUE", "", "getPrecision", "scale", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.NeoPenConfig.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getPrecision(float scale) {
            if (scale < 2.0f) {
                return 1.0f;
            }
            return scale < 8.0f ? 4.0f : 8.0f;
        }
    }

    public final float getAlphaFactor() {
        return this.a;
    }

    @Nullable
    public final List<Bitmap> getBrushShapes() {
        return this.b;
    }

    public final int getColor() {
        return this.color;
    }

    public final float getMaxTouchPressure() {
        return this.maxTouchPressure;
    }

    public final int getRotateAngle() {
        return this.rotateAngle;
    }

    public final int getRendererVersion() {
        return this.rendererVersion;
    }

    public final float getTiltScale() {
        return this.tiltScale;
    }

    public final int getType() {
        return this.type;
    }

    public final float getWidth() {
        return this.width;
    }

    public final boolean isTiltEnabled() {
        return this.tiltEnabled;
    }

    public final void setAlphaFactor(float f) {
        this.a = f;
    }

    public final void setBrushShapes(@Nullable List<Bitmap> list) {
        this.b = list;
    }

    @NotNull
    public final NeoPenConfig setColor(int color) {
        this.color = color;
        return this;
    }

    @NotNull
    public final NeoPenConfig setMaxTouchPressure(float maxTouchPressure) {
        this.maxTouchPressure = maxTouchPressure;
        return this;
    }

    @NotNull
    public final NeoPenConfig setRotateAngle(int rotateAngle) {
        this.rotateAngle = rotateAngle;
        return this;
    }

    @NotNull
    public final NeoPenConfig setRendererVersion(int rendererVersion) {
        if (rendererVersion != RENDERER_RECOVERED_V1
                && rendererVersion != RENDERER_REFERENCE_COMPAT) {
            throw new IllegalArgumentException("Unknown renderer version: " + rendererVersion);
        }
        this.rendererVersion = rendererVersion;
        return this;
    }

    @NotNull
    public final NeoPenConfig setTiltEnabled(boolean tiltEnabled) {
        this.tiltEnabled = tiltEnabled;
        return this;
    }

    @NotNull
    public final NeoPenConfig setTiltScale(float tiltScale) {
        this.tiltScale = tiltScale;
        return this;
    }

    @NotNull
    public final NeoPenConfig setType(int type) {
        this.type = type;
        return this;
    }

    @NotNull
    public final NeoPenConfig setWidth(float width) {
        this.width = width;
        return this;
    }
}

