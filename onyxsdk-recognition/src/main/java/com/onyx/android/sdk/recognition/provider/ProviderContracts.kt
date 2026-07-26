@file:Suppress("MagicNumber")

package com.onyx.android.sdk.recognition.provider

import android.content.Context
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnostics
import com.onyx.android.sdk.recognition.hwr.HandwritingProvider
import com.onyx.android.sdk.recognition.ocr.OcrProvider
import com.onyx.android.sdk.recognition.resources.RecognitionResourceStore
import com.onyx.android.sdk.recognition.speech.SpeechProvider
import java.util.Locale

/**
 * Stable, versioned identity of a recognition provider.
 *
 * IDs are lowercase hyphenated ASCII slugs between 3 and 96 characters. They begin
 * with a letter and contain a `vN` generation segment.
 */
@JvmInline
value class ProviderId(val value: String) {
    init {
        require(value.length in 3..96) { "Provider ID length must be in 3..96" }
        require(value.matches(Regex("^[a-z][a-z0-9]*(?:-[a-z0-9]+)*$"))) {
            "Provider ID must be a lowercase hyphenated ASCII slug"
        }
        require(value.split('-').any { it.matches(Regex("^v[1-9][0-9]*$")) }) {
            "Provider ID must contain a v<positive integer> generation segment"
        }
    }

    /** Returns the slug form. */
    override fun toString(): String = value
}

/** Canonical provider IDs shipped or recognized by the SDK. */
object BuiltInProviderIds {
    /** MyScript iink v4 handwriting provider. */
    val MYSCRIPT_IINK_V4: ProviderId = ProviderId("myscript-iink-v4")

    /** Google ML Kit Digital Ink v1 handwriting provider. */
    val GOOGLE_MLKIT_DIGITAL_INK_V1: ProviderId =
        ProviderId("google-mlkit-digital-ink-v1")

    /** Google ML Kit Text Recognition v2 OCR provider. */
    val GOOGLE_MLKIT_TEXT_RECOGNITION_V2: ProviderId =
        ProviderId("google-mlkit-text-recognition-v2")

    /** Legacy 2020 Paddle OCR provider. */
    val PADDLE_OCR_V1_2020: ProviderId = ProviderId("paddleocr-v1-2020")

    /** PP-OCRv3 provider. */
    val PADDLE_OCR_V3: ProviderId = ProviderId("paddleocr-v3")

    /** Android on-device SODA speech provider. */
    val GOOGLE_SODA_V1: ProviderId = ProviderId("google-soda-v1")

    /** IDs registered automatically unless disabled by the host. */
    val all: Set<ProviderId> = setOf(
        MYSCRIPT_IINK_V4,
        GOOGLE_MLKIT_DIGITAL_INK_V1,
        GOOGLE_MLKIT_TEXT_RECOGNITION_V2,
        PADDLE_OCR_V1_2020,
        GOOGLE_SODA_V1,
    )
}

/**
 * BCP 47 language tag retained as an immutable value.
 */
@JvmInline
value class LanguageTag(val value: String) {
    init {
        require(value.isNotBlank()) { "Language tag must not be blank" }
        require(Locale.forLanguageTag(value).toLanguageTag() != "und" || value.equals("und", true)) {
            "Language tag is not a recognized BCP 47 tag: $value"
        }
    }

    /** Returns this tag in the platform's canonical spelling. */
    fun canonical(): String = Locale.forLanguageTag(value).toLanguageTag()
}

/** Recognition pipeline implemented by a provider. */
enum class RecognitionPipeline { HANDWRITING, OCR, SPEECH }

/** Actual compute backend selected for an attempt. */
enum class ComputeBackend { CPU, GPU, NNAPI, DSP, NPU, UNKNOWN }

/** Input ownership modes understood by a provider. */
enum class ProviderInputOwnership {
    COPIED,
    SNAPSHOT,
    BORROW_UNTIL_COMPLETION,
    TRANSFER,
}

/** Additive provider SPI feature negotiated from a descriptor. */
enum class ProviderFeature {
    RESOURCE_REVISIONS,
    PROVIDER_PREPROCESSING,
    STREAMING_EVENTS,
    INJECTED_AUDIO,
    EDITING,
}

/**
 * Inclusive host-minor range for one provider SPI major.
 *
 * @property major Required SPI major.
 * @property minimumHostMinor Oldest supported host minor.
 * @property maximumHostMinor Newest supported host minor.
 */
data class ProviderSpiCompatibility(
    val major: Int,
    val minimumHostMinor: Int,
    val maximumHostMinor: Int,
) {
    init {
        require(major > 0) { "major must be positive" }
        require(minimumHostMinor >= 0) { "minimumHostMinor must be non-negative" }
        require(maximumHostMinor >= minimumHostMinor) {
            "maximumHostMinor must not precede minimumHostMinor"
        }
    }

    /** Returns whether this range accepts the supplied host SPI. */
    fun supports(hostMajor: Int, hostMinor: Int): Boolean =
        major == hostMajor && hostMinor in minimumHostMinor..maximumHostMinor
}

/**
 * Hard limits declared by a provider.
 *
 * Zero means the input form is unsupported.
 *
 * @property maximumPoints Maximum HWR points per request.
 * @property maximumPixels Maximum OCR pixels per request.
 * @property maximumAudioFrames Maximum frames in one non-streaming speech request.
 * @property maximumSessions Maximum simultaneously open provider sessions.
 */
data class ProviderLimits(
    val maximumPoints: Long = 0,
    val maximumPixels: Long = 0,
    val maximumAudioFrames: Long = 0,
    val maximumSessions: Int = 1,
) {
    init {
        require(maximumPoints >= 0) { "maximumPoints must be non-negative" }
        require(maximumPixels >= 0) { "maximumPixels must be non-negative" }
        require(maximumAudioFrames >= 0) { "maximumAudioFrames must be non-negative" }
        require(maximumSessions > 0) { "maximumSessions must be positive" }
    }
}

/**
 * Resource prerequisite declared by a provider.
 *
 * @property slot Stable resource slot.
 * @property required Whether recognition is unavailable when the slot is empty.
 */
data class ProviderResourceRequirement(
    val slot: String,
    val required: Boolean,
) {
    init {
        require(slot.isNotBlank()) { "slot must not be blank" }
    }
}

/**
 * Immutable typed provider description.
 *
 * Collection properties are copied at construction.
 */
class RecognitionProviderDescriptor(
    /** Stable provider ID. */
    val id: ProviderId,
    /** Recognition pipeline implemented by the provider. */
    val pipeline: RecognitionPipeline,
    /** Human-readable engine name. */
    val engineName: String,
    /** Exact engine/runtime version. */
    val engineVersion: String,
    supportedLanguages: Collection<LanguageTag> = emptyList(),
    computeBackends: Collection<ComputeBackend> = listOf(ComputeBackend.CPU),
    ownershipModes: Collection<ProviderInputOwnership> = listOf(ProviderInputOwnership.SNAPSHOT),
    features: Collection<ProviderFeature> = emptyList(),
    resourceRequirements: Collection<ProviderResourceRequirement> = emptyList(),
    /** Provider hard limits. */
    val limits: ProviderLimits = ProviderLimits(),
) {
    /** Supported languages; empty means the provider resolves support dynamically. */
    val supportedLanguages: List<LanguageTag> = supportedLanguages.toList()

    /** Compute backends that the provider may select. */
    val computeBackends: Set<ComputeBackend> = computeBackends.toSet()

    /** Supported input retention modes. */
    val ownershipModes: Set<ProviderInputOwnership> = ownershipModes.toSet()

    /** Negotiated additive features. */
    val features: Set<ProviderFeature> = features.toSet()

    /** Resource prerequisites. */
    val resourceRequirements: List<ProviderResourceRequirement> = resourceRequirements.toList()

    init {
        require(engineName.isNotBlank()) { "engineName must not be blank" }
        require(engineVersion.isNotBlank()) { "engineVersion must not be blank" }
        require(computeBackends.isNotEmpty()) { "computeBackends must not be empty" }
        require(ownershipModes.isNotEmpty()) { "ownershipModes must not be empty" }
    }
}

/**
 * Shared contract implemented by every typed provider factory.
 */
interface RecognitionProviderFactory {
    /** Static provider description available before discovery. */
    val descriptor: RecognitionProviderDescriptor

    /** SPI versions accepted by this factory. */
    val compatibility: ProviderSpiCompatibility
}

/**
 * Context supplied when creating a handwriting provider.
 */
data class HandwritingProviderContext(
    val applicationContext: Context,
    val resources: RecognitionResourceStore,
    val diagnostics: RecognitionDiagnostics,
)

/**
 * Context supplied when creating an OCR provider.
 */
data class OcrProviderContext(
    val applicationContext: Context,
    val resources: RecognitionResourceStore,
    val diagnostics: RecognitionDiagnostics,
)

/**
 * Context supplied when creating a speech provider.
 */
data class SpeechProviderContext(
    val applicationContext: Context,
    val diagnostics: RecognitionDiagnostics,
)

/** Factory for one handwriting provider. */
interface HandwritingProviderFactory : RecognitionProviderFactory {
    /** Creates and probes the provider during asynchronous discovery. */
    fun create(context: HandwritingProviderContext): Result<HandwritingProvider>
}

/** Factory for one OCR provider. */
interface OcrProviderFactory : RecognitionProviderFactory {
    /** Creates and probes the provider during asynchronous discovery. */
    fun create(context: OcrProviderContext): Result<OcrProvider>
}

/** Factory for one on-device speech provider. */
interface SpeechProviderFactory : RecognitionProviderFactory {
    /** Creates and probes the provider during asynchronous discovery. */
    fun create(context: SpeechProviderContext): Result<SpeechProvider>
}

/** Validation state used when a capability was selected. */
enum class CapabilityValidationState { VALIDATED, UNVERIFIED, UNAVAILABLE }

/** Lifecycle state of initial provider discovery. */
enum class CapabilityDiscoveryState { DISCOVERING, COMPLETE }

/** Typed reason why a provider is not currently usable. */
enum class CapabilityUnavailableReason {
    DISABLED_BY_HOST,
    DEPENDENCY_MISSING,
    RESOURCE_MISSING,
    INCOMPATIBLE_RUNTIME,
    INITIALIZATION_FAILED,
    PLATFORM_UNSUPPORTED,
}

/**
 * Capability state for one registered provider.
 *
 * @property descriptor Static provider description.
 * @property validation Validation evidence state.
 * @property unavailableReason Reason for `UNAVAILABLE`.
 * @property detail Content-safe diagnostic detail.
 */
data class ProviderCapability(
    val descriptor: RecognitionProviderDescriptor,
    val validation: CapabilityValidationState,
    val unavailableReason: CapabilityUnavailableReason? = null,
    val detail: String? = null,
) {
    init {
        require(
            (validation == CapabilityValidationState.UNAVAILABLE) == (unavailableReason != null),
        ) { "Unavailable capabilities require a reason, and usable capabilities must not have one" }
    }
}

/**
 * Point-in-time process capability snapshot.
 *
 * Collection properties are copied at construction.
 */
class RecognitionCapabilitySnapshot(
    /** Discovery lifecycle state. */
    val discovery: CapabilityDiscoveryState,
    providers: Collection<ProviderCapability>,
) {
    /** Provider capabilities in deterministic registration order. */
    val providers: List<ProviderCapability> = providers.toList()

    /** Finds a provider capability by stable ID. */
    fun capability(id: ProviderId): ProviderCapability? =
        providers.firstOrNull { it.descriptor.id == id }
}
