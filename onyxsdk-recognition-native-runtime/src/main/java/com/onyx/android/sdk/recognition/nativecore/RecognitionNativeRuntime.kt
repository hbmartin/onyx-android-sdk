package com.onyx.android.sdk.recognition.nativecore

/**
 * Build-time identity of the native runtime shared by recognition engines.
 *
 * This artifact is the sole Onyx recognition artifact allowed to package
 * `libc++_shared.so`. Vendor AARs must exclude their copy and use this one.
 */
object RecognitionNativeRuntime {
    /** Android NDK revision from which the C++ shared runtime is copied. */
    const val NDK_VERSION: String = "28.2.13676358"

    /** Only ABI supported by recognition artifacts. */
    const val SUPPORTED_ABI: String = "arm64-v8a"

    /** Canonical shared-library soname owned by this artifact. */
    const val CXX_SHARED_SONAME: String = "libc++_shared.so"
}

/**
 * Identifies a repository-owned native recognition component.
 *
 * @property name Stable component name.
 * @property rustCrateVersion Version of its Rust implementation.
 * @property requiredRuntimeNdk Required [RecognitionNativeRuntime] NDK identity.
 */
data class NativeComponentIdentity(
    val name: String,
    val rustCrateVersion: String,
    val requiredRuntimeNdk: String = RecognitionNativeRuntime.NDK_VERSION,
) {
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(rustCrateVersion.isNotBlank()) { "rustCrateVersion must not be blank" }
        require(requiredRuntimeNdk.isNotBlank()) { "requiredRuntimeNdk must not be blank" }
    }
}
