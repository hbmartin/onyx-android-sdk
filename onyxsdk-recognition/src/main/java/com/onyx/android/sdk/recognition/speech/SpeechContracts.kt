@file:Suppress("LongParameterList")

package com.onyx.android.sdk.recognition.speech

import android.os.ParcelFileDescriptor
import com.onyx.android.sdk.recognition.model.RecognitionConfidence
import com.onyx.android.sdk.recognition.model.RecognitionContentType
import com.onyx.android.sdk.recognition.model.RecognitionProvenance
import com.onyx.android.sdk.recognition.model.RecognitionRevision
import com.onyx.android.sdk.recognition.provider.BuiltInProviderIds
import com.onyx.android.sdk.recognition.provider.LanguageTag
import com.onyx.android.sdk.recognition.provider.ProviderId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

/** Speech content identity used in revisions. */
enum class SpeechContentType(
    override val revisionName: String,
) : RecognitionContentType {
    TRANSCRIPT("speech:transcript"),
}

/**
 * Backpressured signed 16-bit PCM frame.
 *
 * @param samples Interleaved PCM samples copied at construction.
 */
class PcmFrame(
    samples: ShortArray,
    /** Sample rate in hertz. */
    val sampleRateHz: Int,
    /** Interleaved channel count. */
    val channelCount: Int,
    /** Monotonic start time, when supplied by the host. */
    val startTimeNanos: Long? = null,
) {
    private val values = samples.copyOf()

    init {
        require(values.isNotEmpty()) { "PCM frame must not be empty" }
        require(sampleRateHz > 0) { "sampleRateHz must be positive" }
        require(channelCount > 0) { "channelCount must be positive" }
        require(values.size % channelCount == 0) { "PCM samples must contain complete frames" }
        require(startTimeNanos == null || startTimeNanos >= 0) {
            "startTimeNanos must be non-negative"
        }
    }

    /** Frame count per channel. */
    val frameCount: Int = values.size / channelCount

    /** Returns a defensive copy of interleaved samples. */
    fun copySamples(): ShortArray = values.copyOf()
}

/** Ownership behavior for a PCM file descriptor. */
enum class ParcelFileDescriptorOwnership {
    /** SDK duplicates the descriptor and leaves the caller descriptor open. */
    DUPLICATE,

    /** SDK takes ownership of the supplied descriptor. */
    TRANSFER,
}

/** Typed local speech audio source. */
sealed interface SpeechAudioSource {
    /** Platform microphone owned for the lifetime of the speech session. */
    data object Microphone : SpeechAudioSource

    /**
     * Backpressured PCM frames. The provider collects only while the session is active.
     */
    data class PcmStream(val frames: Flow<PcmFrame>) : SpeechAudioSource

    /**
     * PCM bytes read from a descriptor with explicit ownership.
     */
    data class PcmFileDescriptor(
        val descriptor: ParcelFileDescriptor,
        val ownership: ParcelFileDescriptorOwnership,
        val sampleRateHz: Int,
        val channelCount: Int,
    ) : SpeechAudioSource {
        init {
            require(sampleRateHz > 0) { "sampleRateHz must be positive" }
            require(channelCount > 0) { "channelCount must be positive" }
        }
    }
}

/**
 * Speech request pinned when a session opens.
 *
 * @property source Local microphone or typed PCM input.
 * @property providerId Explicit provider; defaults to on-device SODA.
 * @property languages Preferred languages. Empty resolves application locales.
 * @property partialResults Whether partial transcript events are requested.
 * @property timeout Optional session timeout; the SDK defines no default.
 */
data class SpeechRequest(
    val source: SpeechAudioSource,
    val providerId: ProviderId = BuiltInProviderIds.GOOGLE_SODA_V1,
    val languages: List<LanguageTag> = emptyList(),
    val partialResults: Boolean = true,
    val timeout: Duration? = null,
)

/**
 * Time range within a speech input.
 */
data class SpeechTimeRange(
    val start: Duration,
    val endExclusive: Duration,
) {
    init {
        require(start >= Duration.ZERO && endExclusive >= start) {
            "Speech time range must be non-negative and ordered"
        }
    }
}

/**
 * Immutable recognized speech token.
 */
data class SpeechToken(
    val text: String,
    val time: SpeechTimeRange? = null,
    val confidence: RecognitionConfidence? = null,
    val speakerLabel: String? = null,
)

/**
 * Immutable recognized speech segment.
 */
data class SpeechSegment(
    val text: String,
    val tokens: List<SpeechToken> = emptyList(),
    val time: SpeechTimeRange? = null,
    val confidence: RecognitionConfidence? = null,
    val speakerLabel: String? = null,
)

/**
 * Alternative transcript hypothesis.
 */
data class SpeechTranscriptAlternative(
    val text: String,
    val confidence: RecognitionConfidence? = null,
)

/**
 * Immutable speech transcript separate from the spatial HWR/OCR document.
 */
data class SpeechTranscript(
    val text: String,
    val segments: List<SpeechSegment>,
    val alternatives: List<SpeechTranscriptAlternative>,
    val provenance: RecognitionProvenance,
) {
    /** Output-affecting recognition revision. */
    val revision: RecognitionRevision get() = provenance.revision
}

/** Streaming speech-session event. */
sealed interface SpeechSessionEvent {
    /** Session is ready to receive or capture audio. */
    data object Ready : SpeechSessionEvent

    /** Partial transcript; segmentation is provider-native and unstable. */
    data class Partial(val transcript: SpeechTranscript) : SpeechSessionEvent

    /** Final immutable transcript. */
    data class Final(val transcript: SpeechTranscript) : SpeechSessionEvent

    /** Input audio level in provider-native units. */
    data class AudioLevel(val value: Float) : SpeechSessionEvent
}

/** Public speech-session lifecycle. */
enum class SpeechSessionState { OPENING, LISTENING, CLOSING, CLOSED, FAILED }

/** Provider-owned active speech session. */
interface SpeechProviderSession : AutoCloseable {
    /** Provider event stream. */
    val events: Flow<SpeechSessionEvent>

    /** Current provider session state. */
    val state: StateFlow<SpeechSessionState>

    /** Requests idempotent non-blocking cleanup. */
    override fun close()

    /** Waits for idempotent cleanup completion. */
    suspend fun closeAndAwait(): Result<Unit>
}

/** Provider runtime created during asynchronous speech discovery. */
interface SpeechProvider : AutoCloseable {
    /** Opens and starts one local speech session. */
    suspend fun openSession(request: SpeechRequest): Result<SpeechProviderSession>

    /** Requests idempotent non-blocking provider cleanup. */
    override fun close()
}

/** Application-facing active speech session. */
interface SpeechSession : AutoCloseable {
    /** Ordered speech events. */
    val events: Flow<SpeechSessionEvent>

    /** Current session state. */
    val state: StateFlow<SpeechSessionState>

    /** Requests idempotent non-blocking cleanup. */
    override fun close()

    /** Waits for idempotent cleanup completion. */
    suspend fun closeAndAwait(): Result<Unit>
}

/** Idiomatic local-only speech facade. */
interface SpeechRecognizer {
    /** Opens the process-exclusive speech lane without cloud fallback. */
    suspend fun openSession(request: SpeechRequest): Result<SpeechSession>
}
