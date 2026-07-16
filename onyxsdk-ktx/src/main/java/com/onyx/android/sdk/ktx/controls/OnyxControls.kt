@file:Suppress("NoCallbacksInFunctions")

package com.onyx.android.sdk.ktx.controls

import android.view.View
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.ktx.capabilities.Capability
import com.onyx.android.sdk.ktx.capabilities.CapabilitySupport
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.model.DashPattern
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenState
import com.onyx.android.sdk.ktx.model.StrokeStyle
import com.onyx.android.sdk.ktx.model.TurboMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Firmware behavior assigned to the pen's side button. */
enum class SideButtonMode(internal val firmwareEnabled: Boolean) {
    /** Ignore presses of the side button. */
    DISABLED(false),

    /** Treat the side button as an eraser modifier. */
    ERASER(true),
}

/** Evidence that a control operation reached its synchronous completion boundary. */
enum class ControlDispatchEvidence { FIRMWARE_CALL_RETURNED }

/**
 * Confirmation that a firmware control call returned without throwing.
 *
 * @property operation Stable name of the dispatched operation.
 * @property evidence Completion boundary observed by the wrapper.
 */
data class ControlReceipt(
    val operation: String,
    val evidence: ControlDispatchEvidence = ControlDispatchEvidence.FIRMWARE_CALL_RETURNED,
)

/** Stable typed entry points for firmware controls that historically required reflection. */
object OnyxControls {
    /** Enables or disables firmware turbo rendering. */
    suspend fun setTurboMode(
        mode: TurboMode,
        capability: Capability<Unit>? = null,
    ): Result<ControlReceipt> = dispatch("controls.turbo", capability) {
        EpdController.setEpdTurbo(mode.firmwareValue)
    }

    /** Assigns [mode] to the pen's side button. */
    suspend fun setSideButtonMode(
        mode: SideButtonMode,
        capability: Capability<Unit>? = null,
    ): Result<ControlReceipt> = dispatch("controls.side-button", capability) {
        EpdController.setEnablePenSideButton(mode.firmwareEnabled)
    }

    /** Updates the handwriting pen [state] associated with [view]. */
    suspend fun setPenState(view: View, state: PenState): Result<ControlReceipt> =
        dispatch("controls.pen-state", null) {
            EpdController.setScreenHandWritingPenState(view, state.firmwareValue)
        }

    /** Configures the firmware dash stroke lengths and phase. */
    suspend fun setDashPattern(
        pattern: DashPattern,
    ): Result<ControlReceipt> = dispatch("controls.dash-pattern", null) {
        EpdController.setStrokeParameters(
            StrokeStyle.DASH.firmwareValue,
            floatArrayOf(pattern.onLengthPx, pattern.offLengthPx, pattern.phasePx),
        )
    }

    private suspend fun dispatch(
        operation: String,
        capability: Capability<Unit>?,
        block: () -> Unit,
    ): Result<ControlReceipt> {
        if (capability?.support == CapabilitySupport.UNSUPPORTED) {
            return Result.failure(
                OnyxFailure.UnsupportedCapability(
                    operation,
                    null,
                    capability.evidence.joinToString { it.detail }.ifBlank {
                        "$operation is unsupported on this firmware"
                    },
                ),
            )
        }
        return withContext(Dispatchers.Main.immediate) {
            onyxResult(
                operation = operation,
                backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
            ) {
                block()
                ControlReceipt(operation)
            }
        }
    }
}
