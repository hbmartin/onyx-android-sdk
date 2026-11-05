package com.onyx.android.sdk.recognition.speech.core

import android.os.Build

/**
 * Platform capabilities needed by the on-device SODA boundary.
 *
 * @property microphoneOnDevice Whether the platform can create an on-device microphone recognizer.
 * @property injectedAudioProbeSupported Whether injected PCM can be capability-probed.
 * @property apiLevel Android API level used for the decision.
 */
data class SpeechPlatformCapabilities(
    val microphoneOnDevice: Boolean,
    val injectedAudioProbeSupported: Boolean,
    val apiLevel: Int,
)

/**
 * Stable Android platform probe for local speech recognition features.
 */
object SpeechCoreProbe {
    /** Returns API-gated capabilities without starting a recognizer or microphone. */
    fun platformCapabilities(): SpeechPlatformCapabilities = SpeechPlatformCapabilities(
        microphoneOnDevice = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
        injectedAudioProbeSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU,
        apiLevel = Build.VERSION.SDK_INT,
    )
}
