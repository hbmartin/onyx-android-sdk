package com.onyx.android.sdk.recognition.ocr.core

import com.onyx.android.sdk.recognition.nativecore.NativeComponentIdentity

/**
 * Runtime identity for an OCR engine isolated behind Java/JNI or Rust JNI.
 *
 * @property component Repository-owned Rust component, when the path uses one.
 * @property engineName Human-readable vendor engine.
 * @property engineVersion Exact vendor runtime version.
 * @property usesOfficialPaddleJavaJni Whether Paddle is invoked through its official Java/JNI API.
 * @property usesOpenCvJavaJni Whether OpenCV is invoked through its Java/JNI API.
 */
data class OcrCoreIdentity(
    val component: NativeComponentIdentity?,
    val engineName: String,
    val engineVersion: String,
    val usesOfficialPaddleJavaJni: Boolean,
    val usesOpenCvJavaJni: Boolean,
) {
    init {
        require(engineName.isNotBlank()) { "engineName must not be blank" }
        require(engineVersion.isNotBlank()) { "engineVersion must not be blank" }
    }
}

/**
 * Availability result from an isolated OCR runtime probe.
 */
sealed interface OcrCoreAvailability {
    /** Runtime is linked and can proceed to model validation. */
    data class Available(val identity: OcrCoreIdentity) : OcrCoreAvailability

    /** Runtime or a required vendor dependency is absent or incompatible. */
    data class Unavailable(val reason: String) : OcrCoreAvailability {
        init {
            require(reason.isNotBlank()) { "reason must not be blank" }
        }
    }
}

/**
 * Minimal probe implemented by each bundled OCR runtime boundary.
 */
fun interface OcrCoreProbe {
    /** Probes Java/JNI and native linkage without loading model files. */
    fun probe(): OcrCoreAvailability
}
