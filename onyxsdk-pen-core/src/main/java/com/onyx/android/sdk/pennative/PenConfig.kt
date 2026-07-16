@file:Suppress(
    // This class intentionally mirrors the mutable, serializable vendor JNI field contract.
    "AvoidVarsExceptWithDelegate",
    "MagicNumber",
    "SerialVersionUIDInSerializableClass",
)

package com.onyx.android.sdk.pennative

import java.io.Serializable

/** Complete configuration contract from `onyxsdk-pennative:1.0.4`. */
open class PenConfig : Serializable {
    /** Renderer type identifier; use one of the `NEOPEN_PEN_TYPE_*` constants. */
    var type: Int = 0

    /** Enables the renderer's lower-cost fast path where the selected pen type supports it. */
    var fastMode: Boolean = false

    /** Stroke color encoded as an Android ARGB integer. */
    var color: Int = 0xff000000.toInt()

    /** Nominal stroke width in output-coordinate pixels. */
    var width: Float = 3f

    /** Smallest pressure- or velocity-adjusted stroke width in output-coordinate pixels. */
    var minWidth: Float = 0f

    /** Additional brush rotation in degrees. */
    var rotateAngle: Int = 0

    /** Whether stylus tilt contributes to variable-width rendering. */
    var tiltEnabled: Boolean = false

    /** Multiplier applied to the normalized tilt contribution. */
    var tiltScale: Float = 0f

    /** Whether stroke direction contributes to shaped-brush width. */
    var directionEnabled: Boolean = false

    /** Maximum raw touch pressure represented by the input source. */
    var maxTouchPressure: Float = 1f

    /** Display density used when calculating bitmap-brush stamp spacing. */
    var dpi: Float = 320f

    /** Horizontal scale applied to every input coordinate before rendering. */
    var displayScaleX: Float = 1f

    /** Vertical scale applied to every input coordinate before rendering. */
    var displayScaleY: Float = 1f

    /** Precision factor used to reduce bitmap-brush stamp spacing. */
    var scalePrecision: Float = 1f

    /** Base distance between bitmap-brush stamps. */
    var brushSpacing: Float = 0.25f

    /** Brush-tip shape identifier; use one of the `NEOPEN_BRUSH_SHAPE_*` constants. */
    var brushShape: Int = NEOPEN_BRUSH_SHAPE_CIRCLE

    /** Major-to-minor axis ratio for directional brush tips. */
    var brushRatio: Float = 5f

    /** Base directional brush angle in degrees. */
    var brushAngle: Float = 0f

    /** Strength of the pressure curve, normalized to the range from zero to one. */
    var pressureSensitivity: Float = 0f

    /** Amount by which velocity narrows a stroke, normalized from zero to one. */
    var velocitySensitivity: Float = 0f

    /** Amount by which velocity widens a stroke, normalized from zero to one. */
    var velocityAmplifier: Float = 0f

    /** Velocity at or below which velocity-dependent width changes are ignored. */
    var velocityIgnoreThreshold: Float = 0f

    /** Lower velocity bound used when normalizing velocity-dependent width changes. */
    var velocityLowerBound: Float = 0f

    /** Upper velocity bound used when normalizing velocity-dependent width changes. */
    var velocityUpperBound: Float = 0f

    /** Strength of width smoothing between adjacent input points, from zero to one. */
    var smoothLevel: Float = 0.2f

    /** Number of initial brush-sign segments to skip before emitting geometry. */
    var startPointLimit: Float = 0f

    /** Minimum traveled distance required before brush-sign geometry is emitted. */
    var startLengthLimit: Float = 0f

    /** Amount of velocity-derived thinning applied to the end of a brush-sign stroke. */
    var endVelocitySensitivity: Float = 0f

    /** Minimum configured thinning applied to the end of a brush-sign stroke. */
    var endThinningRate: Float = 0f

    /** Pressure at or below which brush-sign rendering treats the sample as unpressured. */
    var ignorePressure: Float = 0f

    /** Stable vendor identifiers for renderer types and brush-tip shapes. */
    companion object {
        /** Pressure-sensitive brush renderer. */
        const val NEOPEN_PEN_TYPE_BRUSH = 1

        /** Legacy fountain-pen renderer. */
        const val NEOPEN_PEN_TYPE_FOUNTAIN = 2

        /** Marker renderer. */
        const val NEOPEN_PEN_TYPE_MARKER = 3

        /** Bitmap-stamp charcoal renderer. */
        const val NEOPEN_PEN_TYPE_CHARCOAL = 4

        /** Variable-width charcoal renderer. */
        const val NEOPEN_PEN_TYPE_CHARCOAL_V2 = 5

        /** Smoothed variable-width fountain-pen renderer. */
        const val NEOPEN_PEN_TYPE_FOUNTAIN_V2 = 6

        /** Pencil renderer. */
        const val NEOPEN_PEN_TYPE_PENCIL = 7

        /** Ballpoint renderer. */
        const val NEOPEN_PEN_TYPE_BALLPOINT = 8

        /** Square-tip renderer. */
        const val NEOPEN_PEN_TYPE_SQUARE = 9

        /** Brush-sign polygon renderer. */
        const val NEOPEN_PEN_TYPE_BRUSH_SIGN = 10

        /** Circular brush-tip shape. */
        const val NEOPEN_BRUSH_SHAPE_CIRCLE = 0

        /** Elliptical brush-tip shape. */
        const val NEOPEN_BRUSH_SHAPE_ELLIPSE = 1

        /** Rectangular brush-tip shape. */
        const val NEOPEN_BRUSH_SHAPE_RECTANGLE = 2
    }
}
