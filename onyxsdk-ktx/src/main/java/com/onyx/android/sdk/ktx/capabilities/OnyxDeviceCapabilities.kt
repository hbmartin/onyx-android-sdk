package com.onyx.android.sdk.ktx.capabilities

import android.os.Build
import android.view.SurfaceView
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.api.device.epd.UpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenKind
import com.onyx.android.sdk.utils.ReflectUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class CapabilityProbeMode { PASSIVE, ACTIVE_REVERSIBLE }
enum class CapabilitySupport { SUPPORTED, UNSUPPORTED, UNVERIFIED }
enum class CapabilityEvidenceKind {
    METHOD_RESOLVED,
    BINDER_CODE_RESOLVED,
    BINDER_SERVICE_RESOLVED,
    SAFE_QUERY,
    ROUND_TRIP,
    NATIVE_SELF_TEST,
    ACCESS_DENIED,
    FALLBACK,
}

data class CapabilityEvidence(
    val kind: CapabilityEvidenceKind,
    val detail: String,
)

data class Capability<T>(
    val support: CapabilitySupport,
    val value: T? = null,
    val evidence: List<CapabilityEvidence> = emptyList(),
)

data class FirmwareIdentity(
    val manufacturer: String,
    val model: String,
    val device: String,
    val board: String,
    val sdkInt: Int,
    val fingerprint: String,
    val displayBuild: String,
)

data class InputAxisRange(
    val minimum: Int,
    val maximum: Int,
    val fuzz: Int? = null,
    val flat: Int? = null,
    val resolution: Int? = null,
    val source: String,
)

data class FirmwareTransport(
    val backend: String,
    val serviceName: String,
    val interfaceToken: String,
    val transactionCodes: Map<String, Int>,
)

data class DisplayCapabilities(
    val widthPx: Capability<Int>,
    val heightPx: Capability<Int>,
    val colorType: Capability<Int>,
    val updateModes: Map<EpdUpdateMode, Capability<Unit>>,
    val awaitUpdate: Capability<Unit>,
    val handwritingRepaint: Capability<Unit>,
    val savePenAttachedFramebuffer: Capability<Unit>,
)

data class RawInkCapabilities(
    val pressure: Capability<InputAxisRange>,
    val viewLocalCoordinates: Capability<Unit>,
    val tilt: Capability<Unit>,
    val sideEraser: Capability<Unit>,
    val tailEraser: Capability<Unit>,
    val regionLimits: Capability<Unit>,
)

data class RendererCapability(
    val support: CapabilitySupport,
    val experimental: Boolean,
    val evidence: List<CapabilityEvidence>,
)

data class OnyxDeviceCapabilities(
    val firmware: FirmwareIdentity,
    val transport: Capability<FirmwareTransport>,
    val hiddenApi: Capability<ReflectUtil.HiddenApiAccessStatus>,
    val display: DisplayCapabilities,
    val rawInk: RawInkCapabilities,
    val renderers: Map<PenKind, RendererCapability>,
)

@OptIn(com.onyx.android.sdk.ktx.model.ExperimentalOnyxRendererApi::class)
internal object DeviceCapabilityProbe {
    suspend fun probe(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
    ): Result<OnyxDeviceCapabilities> = onyxResult(
        operation = "capabilities.probe",
        backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
        phase = FirmwareDiagnosticPhase.VALIDATION,
    ) {
        if (probeMode == CapabilityProbeMode.ACTIVE_REVERSIBLE && surfaceView == null) {
            throw OnyxFailure.InvalidArgument(
                "capabilities.probe",
                "ACTIVE_REVERSIBLE probing requires an owned SurfaceView",
            )
        }
        val backend = EpdController.getFirmwareBackendInfo()
        val codes = backend.transactionCodes
        val hiddenStatus = backend.hiddenApiAccessStatus
        val hiddenSupport = when (hiddenStatus) {
            ReflectUtil.HiddenApiAccessStatus.NOT_REQUIRED,
            ReflectUtil.HiddenApiAccessStatus.EXEMPTIONS_APPLIED,
            -> CapabilitySupport.SUPPORTED
            ReflectUtil.HiddenApiAccessStatus.FAILED -> CapabilitySupport.UNSUPPORTED
            ReflectUtil.HiddenApiAccessStatus.NOT_ATTEMPTED -> CapabilitySupport.UNVERIFIED
        }

        fun codeCapability(name: String): Capability<Unit> = if (codes.containsKey(name)) {
            Capability(
                CapabilitySupport.UNVERIFIED,
                evidence = listOf(
                    CapabilityEvidence(
                        CapabilityEvidenceKind.BINDER_CODE_RESOLVED,
                        "$name=${codes.getValue(name)}; presence is not behavioral verification",
                    ),
                ),
            )
        } else {
            Capability(CapabilitySupport.UNVERIFIED)
        }

        val width = strictQuery("GET_EPD_WIDTH") { EpdController.getStrictEpdWidthOrThrow() }
        val height = strictQuery("GET_EPD_HEIGHT") { EpdController.getStrictEpdHeightOrThrow() }
        val color = strictQuery("GET_COLOR_TYPE") { EpdController.getStrictColorTypeOrThrow() }
        val pressureMaximum = runCatching { EpdController.getStrictMaxTouchPressureOrThrow() }
            .getOrNull()
            ?.takeIf { it > 0 }
        val pressure = if (pressureMaximum != null) {
            Capability(
                CapabilitySupport.SUPPORTED,
                InputAxisRange(0, pressureMaximum, source = "SurfaceFlinger GET_MAX_TOUCH_PRESSURE"),
                listOf(CapabilityEvidence(CapabilityEvidenceKind.SAFE_QUERY, "positive maximum")),
            )
        } else {
            Capability<InputAxisRange>(CapabilitySupport.UNVERIFIED)
        }

        val updateModes = if (probeMode == CapabilityProbeMode.ACTIVE_REVERSIBLE) {
            probeUpdateModes(requireNotNull(surfaceView))
        } else {
            EpdUpdateMode.entries.associateWith {
                Capability(
                    CapabilitySupport.UNVERIFIED,
                    evidence = listOf(
                        CapabilityEvidence(
                            CapabilityEvidenceKind.METHOD_RESOLVED,
                            "Requires ACTIVE_REVERSIBLE round-trip",
                        ),
                    ),
                )
            }
        }

        OnyxDeviceCapabilities(
            firmware = FirmwareIdentity(
                manufacturer = Build.MANUFACTURER.orEmpty(),
                model = Build.MODEL.orEmpty(),
                device = Build.DEVICE.orEmpty(),
                board = Build.BOARD.orEmpty(),
                sdkInt = Build.VERSION.SDK_INT,
                fingerprint = Build.FINGERPRINT.orEmpty(),
                displayBuild = Build.DISPLAY.orEmpty(),
            ),
            transport = Capability(
                support = if (backend.isAvailable) CapabilitySupport.SUPPORTED else CapabilitySupport.UNVERIFIED,
                value = FirmwareTransport(
                    backend = backend.backend,
                    serviceName = backend.serviceName,
                    interfaceToken = backend.interfaceToken,
                    transactionCodes = codes.toMap(),
                ),
                evidence = listOf(
                    CapabilityEvidence(
                        CapabilityEvidenceKind.BINDER_SERVICE_RESOLVED,
                        "${backend.serviceName}/${backend.interfaceToken}; alive=${backend.isAvailable}",
                    ),
                ),
            ),
            hiddenApi = Capability(
                hiddenSupport,
                hiddenStatus,
                listOf(
                    CapabilityEvidence(
                        if (hiddenStatus == ReflectUtil.HiddenApiAccessStatus.FAILED) {
                            CapabilityEvidenceKind.ACCESS_DENIED
                        } else {
                            CapabilityEvidenceKind.METHOD_RESOLVED
                        },
                        backend.failure ?: backend.backend,
                    ),
                ),
            ),
            display = DisplayCapabilities(
                widthPx = width,
                heightPx = height,
                colorType = color,
                updateModes = updateModes,
                awaitUpdate = codeCapability("WAIT_FOR_UPDATE_FINISHED"),
                handwritingRepaint = codeCapability("HANDWRITING_REPAINT"),
                savePenAttachedFramebuffer = codeCapability("SAVE_PEN_ATTACHED_FB").copy(
                    support = CapabilitySupport.UNVERIFIED,
                    value = null,
                    evidence = codeCapability("SAVE_PEN_ATTACHED_FB").evidence +
                        CapabilityEvidence(
                            CapabilityEvidenceKind.FALLBACK,
                            "Experimental until overlay A/B validation passes",
                        ),
                ),
            ),
            rawInk = RawInkCapabilities(
                pressure = pressure,
                viewLocalCoordinates = Capability(
                    CapabilitySupport.UNVERIFIED,
                    evidence = listOf(
                        CapabilityEvidence(
                            CapabilityEvidenceKind.METHOD_RESOLVED,
                            "Host-view transform exists but requires native input round-trip",
                        ),
                    ),
                ),
                tilt = Capability(CapabilitySupport.UNVERIFIED),
                sideEraser = Capability(CapabilitySupport.UNVERIFIED),
                tailEraser = Capability(CapabilitySupport.UNVERIFIED),
                regionLimits = Capability(CapabilitySupport.UNVERIFIED),
            ),
            renderers = PenKind.entries.associateWith { pen ->
                RendererCapability(
                    support = CapabilitySupport.UNVERIFIED,
                    experimental = pen in setOf(PenKind.BRUSH, PenKind.MARKER, PenKind.CHARCOAL_V2),
                    evidence = emptyList(),
                )
            },
        )
    }

    private fun strictQuery(name: String, block: () -> Int): Capability<Int> =
        runCatching(block).fold(
            onSuccess = { value ->
                if (value > 0) {
                    Capability(
                        CapabilitySupport.SUPPORTED,
                        value,
                        listOf(CapabilityEvidence(CapabilityEvidenceKind.SAFE_QUERY, name)),
                    )
                } else {
                    Capability(CapabilitySupport.UNVERIFIED)
                }
            },
            onFailure = { Capability(CapabilitySupport.UNVERIFIED) },
        )

    private suspend fun probeUpdateModes(
        surfaceView: SurfaceView,
    ): Map<EpdUpdateMode, Capability<Unit>> = withContext(Dispatchers.Main.immediate) {
        val original = EpdController.getViewDefaultUpdateMode(surfaceView)
        try {
            EpdUpdateMode.entries.associateWith { mode ->
                runCatching {
                    val legacy = mode.toLegacyUpdateMode()
                    EpdController.setViewDefaultUpdateModeOrThrow(surfaceView, legacy)
                    check(EpdController.getViewDefaultUpdateMode(surfaceView) == legacy)
                }.fold(
                    onSuccess = {
                        Capability(
                            CapabilitySupport.SUPPORTED,
                            Unit,
                            listOf(CapabilityEvidence(CapabilityEvidenceKind.ROUND_TRIP, mode.name)),
                        )
                    },
                    onFailure = {
                        Capability(
                            CapabilitySupport.UNSUPPORTED,
                            evidence = listOf(CapabilityEvidence(CapabilityEvidenceKind.ROUND_TRIP, it.message.orEmpty())),
                        )
                    },
                )
            }
        } finally {
            runCatching { EpdController.setViewDefaultUpdateMode(surfaceView, original) }
        }
    }
}

internal fun EpdUpdateMode.toLegacyUpdateMode(): UpdateMode = when (this) {
    EpdUpdateMode.AUTO -> UpdateMode.None
    EpdUpdateMode.DU -> UpdateMode.DU
    EpdUpdateMode.DU4 -> UpdateMode.DU4
    EpdUpdateMode.GU -> UpdateMode.GU
    EpdUpdateMode.GU_FAST -> UpdateMode.GU_FAST
    EpdUpdateMode.GC4 -> UpdateMode.GC4
    EpdUpdateMode.GC16 -> UpdateMode.GC
    EpdUpdateMode.DEEP_GC -> UpdateMode.DEEP_GC
    EpdUpdateMode.REGAL -> UpdateMode.REGAL
    EpdUpdateMode.REGAL_D -> UpdateMode.REGAL_D
    EpdUpdateMode.REGAL_PLUS -> UpdateMode.REGAL_PLUS
    EpdUpdateMode.A2 -> UpdateMode.ANIMATION_MONO
    EpdUpdateMode.HANDWRITING_REPAINT -> UpdateMode.HAND_WRITING_REPAINT_MODE
}
