package com.onyx.android.sdk.ktx.ink

import com.onyx.android.sdk.ktx.display.RefreshReceipt
import com.onyx.android.sdk.ktx.model.OnyxFailure

sealed interface RawInkSessionState {
    data object Opening : RawInkSessionState
    data class Active(val surfaceGeneration: Long) : RawInkSessionState
    data class Suspended(val surfaceGeneration: Long, val reason: SuspensionReason) : RawInkSessionState
    data class Committing(val surfaceGeneration: Long) : RawInkSessionState
    data object Closing : RawInkSessionState
    data object Closed : RawInkSessionState
    data class Failed(val failure: OnyxFailure) : RawInkSessionState
}

enum class SuspensionReason { SURFACE_UNAVAILABLE, SURFACE_DESTROYED }

enum class SurfacePostEvidence { POSTED_AND_GENERATION_VERIFIED }
enum class OverlaySynchronizationEvidence { HANDWRITING_REPAINT }
enum class ConfigurationRestorationEvidence { REAPPLIED_SESSION_CONFIGURATION }
enum class CommitWarning {
    BEST_EFFORT_NOT_HARDWARE_ATOMIC,
    ESTIMATED_REFRESH_COMPLETION,
    HANDWRITING_REPAINT_VIEW_MODE_REJECTED,
}

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
