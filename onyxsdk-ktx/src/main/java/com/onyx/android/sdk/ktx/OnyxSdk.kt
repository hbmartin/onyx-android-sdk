package com.onyx.android.sdk.ktx

import android.view.SurfaceView
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.capabilities.DeviceCapabilityProbe
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.utils.ReflectUtil
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface HiddenApiExemptionProvider {
    /** Enables only the hidden APIs required by the host application. */
    fun enable()
}

object OnyxSdk {
    private val bootstrapLock = Any()
    // Atomics keep the properties immutable for static analysis; bootstrapLock still owns every
    // compound transition so provider installation and discovery cannot race each other.
    private val exemptionProvider = AtomicReference<HiddenApiExemptionProvider?>()
    private val discoveryStarted = AtomicBoolean()
    private val externalExemptionAttempted = AtomicBoolean()
    private val externalExemptionSucceeded = AtomicBoolean()

    val diagnostics: OnyxDiagnostics
        get() = OnyxDiagnostics

    /** Installs the optional provider once, before the first capability probe. */
    fun installHiddenApiExemptionProvider(provider: HiddenApiExemptionProvider): Boolean =
        synchronized(bootstrapLock) {
            if (discoveryStarted.get() || exemptionProvider.get() != null) {
                false
            } else {
                exemptionProvider.set(provider)
                true
            }
        }

    suspend fun capabilities(
        surfaceView: SurfaceView? = null,
        probeMode: CapabilityProbeMode = CapabilityProbeMode.PASSIVE,
    ): Result<OnyxDeviceCapabilities> {
        val externalHiddenApiAccess = withContext(Dispatchers.Default) {
            synchronized(bootstrapLock) {
                discoveryStarted.set(true)
                if (
                    ReflectUtil.getHiddenApiAccessStatus() == ReflectUtil.HiddenApiAccessStatus.FAILED &&
                    !externalExemptionAttempted.get()
                ) {
                    exemptionProvider.get()?.let { provider ->
                        externalExemptionAttempted.set(true)
                        externalExemptionSucceeded.set(runCatching(provider::enable).isSuccess)
                        if (externalExemptionSucceeded.get()) {
                            EpdController.retryFirmwareBackendDiscovery()
                        }
                    }
                }
                externalExemptionSucceeded.get()
            }
        }
        return DeviceCapabilityProbe.probe(surfaceView, probeMode, externalHiddenApiAccess)
    }
}
