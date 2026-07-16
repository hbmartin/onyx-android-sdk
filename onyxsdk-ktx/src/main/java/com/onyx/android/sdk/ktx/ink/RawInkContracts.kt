package com.onyx.android.sdk.ktx.ink

import android.graphics.Bitmap
import android.graphics.Rect
import com.onyx.android.sdk.ktx.display.RefreshReceipt
import com.onyx.android.sdk.ktx.model.CommitOptions
import com.onyx.android.sdk.ktx.model.InkPreview
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenEvent
import java.io.Closeable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/** Injectable application-facing pen-session contract. */
interface PenSession : Closeable {
    /** Current lifecycle state of the session. */
    val state: StateFlow<RawInkSessionState>

    /** Ordered raw pen events emitted while the stream is collected. */
    val events: Flow<PenEvent>

    /** Latest preview snapshot, or `null` before a stroke begins. */
    val preview: StateFlow<InkPreview?>

    /** Completed strokes in input order. */
    val completedStrokes: Flow<InkStroke>

    /** Suspends firmware input and overlay rendering for this session. */
    suspend fun pause(): Result<Unit>

    /** Resumes firmware input and overlay rendering when a surface is available. */
    suspend fun resume(): Result<Unit>

    /** Posts [bitmap], refreshes [dirtyRegion], and restores the session configuration. */
    suspend fun commit(
        bitmap: Bitmap,
        destination: Rect,
        dirtyRegion: Rect = destination,
        options: CommitOptions = CommitOptions(),
    ): Result<CommitReceipt>

    /** Closes the session and waits for firmware resources and its process lease to be released. */
    suspend fun closeAndAwait(): Result<Unit>
}

/** Lifecycle state of a raw-ink session. */
sealed interface RawInkSessionState {
    /** The session is acquiring firmware resources. */
    data object Opening : RawInkSessionState

    /**
     * The session is accepting input.
     *
     * @property surfaceGeneration Generation of the active rendering surface.
     */
    data class Active(val surfaceGeneration: Long) : RawInkSessionState

    /**
     * The session is paused.
     *
     * @property surfaceGeneration Most recently observed rendering-surface generation.
     * @property reason Cause of the suspension.
     */
    data class Suspended(val surfaceGeneration: Long, val reason: SuspensionReason) : RawInkSessionState

    /**
     * A bitmap is being committed.
     *
     * @property surfaceGeneration Generation of the surface receiving the bitmap.
     */
    data class Committing(val surfaceGeneration: Long) : RawInkSessionState

    /** Cleanup has begun. */
    data object Closing : RawInkSessionState

    /** Cleanup completed successfully. */
    data object Closed : RawInkSessionState

    /**
     * The session terminated unsuccessfully.
     *
     * @property failure Typed cause of termination.
     */
    data class Failed(val failure: OnyxFailure) : RawInkSessionState
}

/** Reason a raw-ink session is not currently accepting input. */
enum class SuspensionReason {
    SURFACE_UNAVAILABLE,
    SURFACE_DESTROYED,
    REQUESTED,
    LIFECYCLE_STOPPED,
}

/** Evidence that a committed bitmap reached the expected surface generation. */
enum class SurfacePostEvidence { POSTED_AND_GENERATION_VERIFIED }

/** Mechanism used to synchronize firmware handwriting overlay contents. */
enum class OverlaySynchronizationEvidence { HANDWRITING_REPAINT }

/** Evidence that raw-ink configuration was restored after a commit. */
enum class ConfigurationRestorationEvidence { REAPPLIED_SESSION_CONFIGURATION }

/** Recoverable limitation encountered during a bitmap commit. */
enum class CommitWarning {
    BEST_EFFORT_NOT_HARDWARE_ATOMIC,
    ESTIMATED_REFRESH_COMPLETION,
    HANDWRITING_REPAINT_VIEW_MODE_REJECTED,
}

/**
 * Evidence collected while committing content beneath the raw-ink overlay.
 *
 * @property surfaceGeneration Surface generation that received the bitmap.
 * @property surfacePost Evidence for the bitmap post.
 * @property overlaySynchronization Mechanism used to synchronize the handwriting overlay.
 * @property refresh Result of the corresponding E Ink refresh.
 * @property configurationRestoration Evidence that the session configuration was restored.
 * @property warnings Recoverable limitations encountered during the commit.
 */
data class CommitReceipt(
    val surfaceGeneration: Long,
    val surfacePost: SurfacePostEvidence,
    val overlaySynchronization: OverlaySynchronizationEvidence,
    val refresh: RefreshReceipt,
    val configurationRestoration: ConfigurationRestorationEvidence,
    val warnings: Set<CommitWarning>,
)

internal data class RawInputAxisProbe(
    val minimum: Int,
    val maximum: Int,
    val fuzz: Int,
    val flat: Int,
    val resolution: Int,
)

internal data class RawInputProbe(
    val x: RawInputAxisProbe?,
    val y: RawInputAxisProbe?,
    val pressure: RawInputAxisProbe?,
    val tiltX: RawInputAxisProbe?,
    val tiltY: RawInputAxisProbe?,
    val kernelMonotonicClock: Boolean,
)
