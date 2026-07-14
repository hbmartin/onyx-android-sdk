package com.onyx.android.sdk.ktx

import android.view.SurfaceView
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.capabilities.DeviceCapabilityProbe
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.utils.ReflectUtil
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.atomic.AtomicBoolean

fun interface HiddenApiExemptionProvider {
    /** Enables only the hidden APIs required by the host application. */
    fun enable()
}

object OnyxSdk {
    private val exemptionProvider = AtomicReference<HiddenApiExemptionProvider?>()
    private val discoveryStarted = AtomicBoolean()

    val diagnostics: OnyxDiagnostics
        get() = OnyxDiagnostics

    /** Installs the optional provider once, before the first capability probe. */
    fun installHiddenApiExemptionProvider(provider: HiddenApiExemptionProvider): Boolean =
        !discoveryStarted.get() && exemptionProvider.compareAndSet(null, provider)

    suspend fun capabilities(
        surfaceView: SurfaceView? = null,
        probeMode: CapabilityProbeMode = CapabilityProbeMode.PASSIVE,
    ): Result<OnyxDeviceCapabilities> {
        discoveryStarted.set(true)
        if (ReflectUtil.getHiddenApiAccessStatus() == ReflectUtil.HiddenApiAccessStatus.FAILED) {
            exemptionProvider.get()?.let { provider ->
                runCatching(provider::enable)
                EpdController.retryFirmwareBackendDiscovery()
            }
        }
        return DeviceCapabilityProbe.probe(surfaceView, probeMode)
    }
}
