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
    var type: Int = 0
    var fastMode: Boolean = false
    var color: Int = 0xff000000.toInt()
    var width: Float = 3f
    var minWidth: Float = 0f
    var rotateAngle: Int = 0
    var tiltEnabled: Boolean = false
    var tiltScale: Float = 0f
    var directionEnabled: Boolean = false
    var maxTouchPressure: Float = 1f
    var dpi: Float = 320f
    var displayScaleX: Float = 1f
    var displayScaleY: Float = 1f
    var scalePrecision: Float = 1f
    var brushSpacing: Float = 0.25f
    var brushShape: Int = NEOPEN_BRUSH_SHAPE_CIRCLE
    var brushRatio: Float = 5f
    var brushAngle: Float = 0f
    var pressureSensitivity: Float = 0f
    var velocitySensitivity: Float = 0f
    var velocityAmplifier: Float = 0f
    var velocityIgnoreThreshold: Float = 0f
    var velocityLowerBound: Float = 0f
    var velocityUpperBound: Float = 0f
    var smoothLevel: Float = 0.2f
    var startPointLimit: Float = 0f
    var startLengthLimit: Float = 0f
    var endVelocitySensitivity: Float = 0f
    var endThinningRate: Float = 0f
    var ignorePressure: Float = 0f

    companion object {
        const val NEOPEN_PEN_TYPE_BRUSH = 1
        const val NEOPEN_PEN_TYPE_FOUNTAIN = 2
        const val NEOPEN_PEN_TYPE_MARKER = 3
        const val NEOPEN_PEN_TYPE_CHARCOAL = 4
        const val NEOPEN_PEN_TYPE_CHARCOAL_V2 = 5
        const val NEOPEN_PEN_TYPE_FOUNTAIN_V2 = 6
        const val NEOPEN_PEN_TYPE_PENCIL = 7
        const val NEOPEN_PEN_TYPE_BALLPOINT = 8
        const val NEOPEN_PEN_TYPE_SQUARE = 9
        const val NEOPEN_PEN_TYPE_BRUSH_SIGN = 10

        const val NEOPEN_BRUSH_SHAPE_CIRCLE = 0
        const val NEOPEN_BRUSH_SHAPE_ELLIPSE = 1
        const val NEOPEN_BRUSH_SHAPE_RECTANGLE = 2
    }
}
