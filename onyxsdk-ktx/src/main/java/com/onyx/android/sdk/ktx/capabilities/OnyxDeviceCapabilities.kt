@file:Suppress(
    // The probe is intentionally a single reversible transaction; splitting it would make
    // restoration and evidence ordering harder to audit. Legacy return values stay erased.
    "CyclomaticComplexMethod",
    "DontForceCast",
    "LongMethod",
    "MagicNumber",
    "MaxLineLength",
    "NoCallbacksInFunctions",
)

package com.onyx.android.sdk.ktx.capabilities

import android.os.Build
import android.view.SurfaceView
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.api.device.epd.UpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.ink.RawInputAxisProbe
import com.onyx.android.sdk.ktx.ink.probeRawInput
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkTool
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenKind
import com.onyx.android.sdk.ktx.render.InkRenderer
import com.onyx.android.sdk.utils.ReflectUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

enum class CapabilityProbeMode { PASSIVE, ACTIVE_REVERSIBLE }
enum class CapabilitySupport { SUPPORTED, UNSUPPORTED, UNVERIFIED }
enum class HiddenApiStatus { NOT_ATTEMPTED, NOT_REQUIRED, EXEMPTIONS_APPLIED, ACCESS_DENIED }
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
    val x: Capability<InputAxisRange>,
    val y: Capability<InputAxisRange>,
    val pressure: Capability<InputAxisRange>,
    val tiltX: Capability<InputAxisRange>,
    val tiltY: Capability<InputAxisRange>,
    val viewLocalCoordinates: Capability<Unit>,
    val monotonicTimestamps: Capability<Unit>,
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
    val hiddenApi: Capability<HiddenApiStatus>,
    val display: DisplayCapabilities,
    val rawInk: RawInkCapabilities,
    val renderers: Map<PenKind, RendererCapability>,
)

@OptIn(com.onyx.android.sdk.ktx.model.ExperimentalOnyxRendererApi::class)
internal object DeviceCapabilityProbe {
    suspend fun probe(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
    ): Result<OnyxDeviceCapabilities> = probe(surfaceView, probeMode, false)

    suspend fun probe(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
        externalHiddenApiAccess: Boolean,
    ): Result<OnyxDeviceCapabilities> = withContext(Dispatchers.Default) {
        onyxResult(
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
        val legacyHiddenStatus = backend.hiddenApiAccessStatus
        val hiddenStatus = if (
            externalHiddenApiAccess &&
            legacyHiddenStatus == ReflectUtil.HiddenApiAccessStatus.FAILED
        ) {
            HiddenApiStatus.EXEMPTIONS_APPLIED
        } else when (legacyHiddenStatus) {
            ReflectUtil.HiddenApiAccessStatus.NOT_ATTEMPTED -> HiddenApiStatus.NOT_ATTEMPTED
            ReflectUtil.HiddenApiAccessStatus.NOT_REQUIRED -> HiddenApiStatus.NOT_REQUIRED
            ReflectUtil.HiddenApiAccessStatus.EXEMPTIONS_APPLIED -> HiddenApiStatus.EXEMPTIONS_APPLIED
            ReflectUtil.HiddenApiAccessStatus.FAILED -> HiddenApiStatus.ACCESS_DENIED
        }
        val hiddenSupport = when (hiddenStatus) {
            HiddenApiStatus.NOT_REQUIRED,
            HiddenApiStatus.EXEMPTIONS_APPLIED,
            -> CapabilitySupport.SUPPORTED
            HiddenApiStatus.ACCESS_DENIED -> CapabilitySupport.UNSUPPORTED
            HiddenApiStatus.NOT_ATTEMPTED -> CapabilitySupport.UNVERIFIED
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
        val rawProbe = if (probeMode == CapabilityProbeMode.ACTIVE_REVERSIBLE) {
            probeRawInput(requireNotNull(surfaceView), 2.seconds).getOrNull()
        } else {
            null
        }
        val rawEvidence = listOf(
            CapabilityEvidence(
                CapabilityEvidenceKind.NATIVE_SELF_TEST,
                "EVIOCGABS metadata reported by the opened input device",
            ),
        )
        fun axisCapability(axis: RawInputAxisProbe?, name: String): Capability<InputAxisRange> =
            if (axis != null && axis.maximum > axis.minimum) {
                Capability(
                    CapabilitySupport.SUPPORTED,
                    InputAxisRange(
                        minimum = axis.minimum,
                        maximum = axis.maximum,
                        fuzz = axis.fuzz,
                        flat = axis.flat,
                        resolution = axis.resolution,
                        source = "EVIOCGABS $name",
                    ),
                    rawEvidence,
                )
            } else {
                Capability(CapabilitySupport.UNVERIFIED)
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
                        if (hiddenStatus == HiddenApiStatus.ACCESS_DENIED) {
                            CapabilityEvidenceKind.ACCESS_DENIED
                        } else {
                            CapabilityEvidenceKind.METHOD_RESOLVED
                        },
                        if (externalHiddenApiAccess) {
                            "Host exemption provider succeeded; ${backend.failure ?: backend.backend}"
                        } else {
                            backend.failure ?: backend.backend
                        },
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
                savePenAttachedFramebuffer = codeCapability("SAVE_PEN_ATTACHED_FB").let { capability ->
                    capability.copy(
                        support = CapabilitySupport.UNVERIFIED,
                        value = null,
                        evidence = capability.evidence +
                            CapabilityEvidence(
                                CapabilityEvidenceKind.FALLBACK,
                                "Experimental until overlay A/B validation passes",
                            ),
                    )
                },
            ),
            rawInk = RawInkCapabilities(
                x = axisCapability(rawProbe?.x, "ABS_X"),
                y = axisCapability(rawProbe?.y, "ABS_Y"),
                pressure = rawProbe?.pressure
                    ?.let { axisCapability(it, "ABS_PRESSURE") }
                    ?.takeIf { it.support == CapabilitySupport.SUPPORTED }
                    ?: pressure,
                tiltX = axisCapability(rawProbe?.tiltX, "ABS_TILT_X"),
                tiltY = axisCapability(rawProbe?.tiltY, "ABS_TILT_Y"),
                viewLocalCoordinates = Capability(
                    CapabilitySupport.UNVERIFIED,
                    evidence = listOf(
                        CapabilityEvidence(
                            CapabilityEvidenceKind.METHOD_RESOLVED,
                            "Host-view transform exists but requires native input round-trip",
                        ),
                    ),
                ),
                monotonicTimestamps = if (rawProbe != null) {
                    Capability(
                        CapabilitySupport.SUPPORTED,
                        Unit,
                        listOf(
                            CapabilityEvidence(
                                if (rawProbe.kernelMonotonicClock) {
                                    CapabilityEvidenceKind.NATIVE_SELF_TEST
                                } else {
                                    CapabilityEvidenceKind.FALLBACK
                                },
                                if (rawProbe.kernelMonotonicClock) {
                                    "EVIOCSCLOCKID(CLOCK_MONOTONIC) accepted"
                                } else {
                                    "Kernel realtime events translated to the monotonic domain"
                                },
                            ),
                        ),
                    )
                } else {
                    Capability(CapabilitySupport.UNVERIFIED)
                },
                tilt = if (rawProbe?.tiltX != null && rawProbe.tiltY != null) {
                    Capability(CapabilitySupport.SUPPORTED, Unit, rawEvidence)
                } else {
                    Capability(CapabilitySupport.UNVERIFIED)
                },
                sideEraser = Capability(CapabilitySupport.UNVERIFIED),
                tailEraser = Capability(CapabilitySupport.UNVERIFIED),
                regionLimits = Capability(CapabilitySupport.UNVERIFIED),
            ),
            renderers = if (probeMode == CapabilityProbeMode.ACTIVE_REVERSIBLE) {
                probeRenderers()
            } else {
                PenKind.entries.associateWith { pen ->
                    RendererCapability(
                        support = CapabilitySupport.UNVERIFIED,
                        experimental = pen.isExperimental,
                        evidence = emptyList(),
                    )
                }
            },
        )
        }
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
                    val legacy = mode.toLegacyUpdateMode() as UpdateMode
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

    private fun probeRenderers(): Map<PenKind, RendererCapability> {
        val points = listOf(
            rendererPoint(8f, 8f, 0.2f, 1),
            rendererPoint(24f, 32f, 0.7f, 2),
            rendererPoint(48f, 40f, 0.4f, 3),
        )
        return PenKind.entries.associateWith { pen ->
            val selfTest = InkRenderer.create(pen).fold(
                onSuccess = { renderer ->
                    renderer.use { it.render(points) }
                },
                onFailure = { Result.failure(it) },
            )
            selfTest.fold(
                onSuccess = { frame ->
                    val meaningfulGeometry = frame.bounds.run {
                        left.isFinite() && top.isFinite() && right.isFinite() && bottom.isFinite() &&
                            width() > 0f && height() > 0f
                    }
                    RendererCapability(
                        support = if (meaningfulGeometry) {
                            CapabilitySupport.SUPPORTED
                        } else {
                            CapabilitySupport.UNSUPPORTED
                        },
                        experimental = pen.isExperimental,
                        evidence = listOf(
                            CapabilityEvidence(
                                CapabilityEvidenceKind.NATIVE_SELF_TEST,
                                if (meaningfulGeometry) {
                                    "down/move/up completed; bounds=${frame.bounds}"
                                } else {
                                    "down/move/up returned degenerate bounds=${frame.bounds}"
                                },
                            ),
                        ),
                    )
                },
                onFailure = { failure ->
                    RendererCapability(
                        support = CapabilitySupport.UNSUPPORTED,
                        experimental = pen.isExperimental,
                        evidence = listOf(
                            CapabilityEvidence(
                                CapabilityEvidenceKind.NATIVE_SELF_TEST,
                                failure.message ?: failure.javaClass.simpleName,
                            ),
                        ),
                    )
                },
            )
        }
    }

    private fun rendererPoint(x: Float, y: Float, pressure: Float, sequence: Long) = InkPoint(
        xPx = x,
        yPx = y,
        rawPressure = (pressure * 4096).toInt(),
        normalizedPressure = pressure,
        maxPressure = 4096,
        tiltX = 0,
        tiltY = 0,
        eventTimeNanos = sequence * 1_000_000,
        sequence = sequence,
        tool = InkTool.PEN,
    )

    private val PenKind.isExperimental: Boolean
        get() = this in setOf(
            PenKind.BRUSH,
            PenKind.MARKER,
            PenKind.CHARCOAL_V2,
            PenKind.BRUSH_SIGN,
        )
}

/** Internal bridge deliberately erases the legacy type from the KTX JVM descriptor. */
internal fun EpdUpdateMode.toLegacyUpdateMode(): Any = when (this) {
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
