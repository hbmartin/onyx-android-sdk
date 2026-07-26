package com.onyx.android.sdk.recognition.hwr.core

import com.onyx.android.sdk.recognition.nativecore.NativeComponentIdentity

/**
 * Native-isolated runtime identity exposed to the Kotlin facade.
 *
 * Vendor SDK objects never cross this boundary.
 *
 * @property component Repository-owned Rust bridge identity, when present.
 * @property engineName Human-readable vendor engine name.
 * @property engineVersion Exact runtime version.
 */
data class HandwritingCoreIdentity(
    val component: NativeComponentIdentity?,
    val engineName: String,
    val engineVersion: String,
) {
    init {
        require(engineName.isNotBlank()) { "engineName must not be blank" }
        require(engineVersion.isNotBlank()) { "engineVersion must not be blank" }
    }
}

/**
 * Availability result from an isolated handwriting runtime probe.
 */
sealed interface HandwritingCoreAvailability {
    /** Runtime is loadable and ready for resource-specific validation. */
    data class Available(val identity: HandwritingCoreIdentity) : HandwritingCoreAvailability

    /** Runtime cannot be used in the current application package. */
    data class Unavailable(val reason: String) : HandwritingCoreAvailability {
        init {
            require(reason.isNotBlank()) { "reason must not be blank" }
        }
    }
}

/**
 * Minimal probe implemented by each bundled handwriting runtime boundary.
 */
fun interface HandwritingCoreProbe {
    /** Probes runtime linkage without loading recognition resources. */
    fun probe(): HandwritingCoreAvailability
}
