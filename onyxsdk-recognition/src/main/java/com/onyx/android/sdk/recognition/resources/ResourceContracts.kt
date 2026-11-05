package com.onyx.android.sdk.recognition.resources

import android.net.Uri
import com.onyx.android.sdk.recognition.provider.CapabilityValidationState
import java.io.File
import java.io.InputStream
import kotlinx.coroutines.flow.StateFlow

/**
 * Stable recognition resource slot.
 */
@JvmInline
value class RecognitionResourceSlot(val value: String) {
    init {
        require(value.matches(Regex("^[a-z][a-z0-9-]{2,95}$"))) {
            "Resource slot must be a lowercase hyphenated ASCII slug"
        }
    }
}

/**
 * Stable resource-pack revision.
 */
@JvmInline
value class ResourcePackRevision(val value: String) {
    init {
        require(value.matches(Regex("^[A-Za-z0-9][A-Za-z0-9._-]{0,127}$"))) {
            "Resource revision contains unsupported characters"
        }
    }
}

/**
 * Typed resource-pack input.
 *
 * All forms are consumed by the SDK. [Stream] is always closed by the SDK.
 */
sealed interface ResourcePackInput {
    /** Pack read from a local file. */
    data class FileInput(val file: File) : ResourcePackInput

    /** Pack read through the application content resolver. */
    data class ContentUri(val uri: Uri) : ResourcePackInput

    /** One-shot stream that the SDK closes after staging. */
    data class Stream(val input: InputStream) : ResourcePackInput
}

/** Required behavior after a pack verifies successfully. */
enum class ResourceInstallPolicy {
    PREPARE_ONLY,
    ACTIVATE_AFTER_VERIFY,
}

/** Resource operation lifecycle phase. */
enum class ResourceOperationPhase {
    QUEUED,
    STAGING,
    VERIFYING,
    PREPARED,
    ACTIVATING,
    ROLLING_BACK,
    COMPLETED,
    CANCELLED,
    FAILED,
}

/**
 * Immutable progress record for a resource operation.
 *
 * @property phase Current lifecycle phase.
 * @property bytesProcessed Bytes processed so far.
 * @property totalBytes Expected bytes when known.
 * @property entriesProcessed ZIP entries processed.
 * @property detail Content-safe progress detail.
 */
data class ResourceOperationProgress(
    val phase: ResourceOperationPhase,
    val bytesProcessed: Long = 0,
    val totalBytes: Long? = null,
    val entriesProcessed: Int = 0,
    val detail: String? = null,
) {
    init {
        require(bytesProcessed >= 0) { "bytesProcessed must be non-negative" }
        require(totalBytes == null || totalBytes >= 0) { "totalBytes must be non-negative" }
        require(entriesProcessed >= 0) { "entriesProcessed must be non-negative" }
    }
}

/**
 * Result of a completed installation.
 */
data class ResourceInstallReceipt(
    val slot: RecognitionResourceSlot,
    val revision: ResourcePackRevision,
    val policy: ResourceInstallPolicy,
    val activated: Boolean,
    val validation: CapabilityValidationState,
)

/**
 * Asynchronous, idempotently awaitable resource operation.
 */
interface ResourceOperationHandle {
    /** Live progress state. */
    val progress: StateFlow<ResourceOperationProgress>

    /**
     * Requests cancellation.
     *
     * Unverified staging is deleted, a verified candidate is preserved, and an
     * already-started activation is allowed to finish or roll back.
     */
    fun cancel()

    /** Waits for the same terminal result on every call. */
    suspend fun await(): Result<ResourceInstallReceipt>
}

/**
 * Immutable slot state.
 *
 * @property slot Resource slot.
 * @property active Active revision.
 * @property previous Rollback revision.
 * @property prepared Inactive verified candidate.
 */
data class RecognitionResourceSlotState(
    val slot: RecognitionResourceSlot,
    val active: ResourcePackRevision?,
    val previous: ResourcePackRevision?,
    val prepared: ResourcePackRevision?,
)

/**
 * Resource-store API supplied to providers and applications.
 *
 * Pack hashes detect corruption only. They do not authenticate origin; the host
 * application remains responsible for trusted sourcing and transport.
 */
interface RecognitionResourceStore {
    /** Observes all known resource slots. */
    val slots: StateFlow<List<RecognitionResourceSlotState>>

    /** Stages, verifies, and optionally activates one resource pack. */
    fun install(
        input: ResourcePackInput,
        policy: ResourceInstallPolicy,
    ): ResourceOperationHandle

    /** Activates an already prepared candidate atomically. */
    suspend fun activate(slot: RecognitionResourceSlot): Result<ResourceInstallReceipt>

    /** Rolls back to the retained previous revision. */
    suspend fun rollback(slot: RecognitionResourceSlot): Result<ResourceInstallReceipt>

    /** Removes an inactive revision unless it is protected or session-pinned. */
    suspend fun remove(
        slot: RecognitionResourceSlot,
        revision: ResourcePackRevision,
    ): Result<Unit>
}

/** HWR resource operations. */
interface HandwritingResources : RecognitionResourceStore

/** OCR resource operations. */
interface OcrResources : RecognitionResourceStore
