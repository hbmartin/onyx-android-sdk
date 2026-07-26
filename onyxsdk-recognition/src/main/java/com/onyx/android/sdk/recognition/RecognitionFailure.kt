package com.onyx.android.sdk.recognition

import com.onyx.android.sdk.recognition.model.RecognitionProvenance
import com.onyx.android.sdk.recognition.model.RecognitionRevision
import com.onyx.android.sdk.recognition.provider.ProviderId

/**
 * Typed operational recognition failure returned inside Kotlin [Result].
 *
 * Invalid arguments and API precondition violations use ordinary Kotlin
 * `IllegalArgumentException` and `IllegalStateException` instead.
 */
sealed class RecognitionFailure(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    /** Provider discovery has not completed. */
    class NotReady : RecognitionFailure("Initial recognition provider discovery is still running")

    /** The selected provider is unavailable. */
    class ProviderUnavailable(
        /** Requested provider. */
        val providerId: ProviderId,
        detail: String,
    ) : RecognitionFailure("Provider $providerId is unavailable: $detail")

    /** The request exceeded the bounded HWR/OCR pending queue. */
    class QueueFull : RecognitionFailure("Recognition FIFO already has eight pending requests")

    /** An explicit caller timeout elapsed. */
    class TimedOut(
        /** Operation that exceeded its timeout. */
        val operation: String,
    ) : RecognitionFailure("Recognition operation timed out: $operation")

    /** Valid input produced no acceptable recognition. */
    class NoMatch(
        /** Attempt provenance. */
        val provenance: RecognitionProvenance,
        /** Attempt revision, also available through [provenance]. */
        val revision: RecognitionRevision = provenance.revision,
    ) : RecognitionFailure("Recognition completed without an acceptable match")

    /** A provider violated its SPI contract by throwing unexpectedly. */
    class ProviderBug(
        /** Provider that threw. */
        val providerId: ProviderId,
        cause: Throwable,
    ) : RecognitionFailure(
        "Provider $providerId threw ${cause.javaClass.name}: ${cause.message.orEmpty()}",
        cause,
    )

    /** The requested input, content, language, or platform feature is unsupported. */
    class Unsupported(
        /** Content-safe explanation. */
        val detail: String,
    ) : RecognitionFailure(detail)

    /** Another speech session owns the process speech lane. */
    class SpeechBusy : RecognitionFailure("A speech session is already active")

    /** A provider or resource revision failed validation. */
    class ValidationFailed(
        /** Content-safe validation explanation. */
        val detail: String,
    ) : RecognitionFailure(detail)

    /** A resource operation failed without changing the active revision. */
    class ResourceOperationFailed(
        /** Content-safe failure explanation. */
        val detail: String,
        cause: Throwable? = null,
    ) : RecognitionFailure(detail, cause)

    /** A protected or session-pinned resource pack cannot be removed. */
    class ResourceProtected(
        /** Protected resource slot or revision. */
        val identity: String,
    ) : RecognitionFailure("Resource is protected or pinned: $identity")

    /** An operational runtime initialization step failed. */
    class InitializationFailed(
        /** Content-safe initialization explanation. */
        val detail: String,
        cause: Throwable? = null,
    ) : RecognitionFailure(detail, cause)
}
