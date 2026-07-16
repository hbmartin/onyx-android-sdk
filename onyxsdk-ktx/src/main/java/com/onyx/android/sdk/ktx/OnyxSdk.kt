@file:Suppress(
    // Discovery is isolated from callers and legacy initializers throw undocumented failures.
    "InjectDispatcher",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.ktx

import android.app.Application
import android.view.SurfaceView
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.capabilities.DeviceCapabilityProbe
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.rx.RxBaseAction
import com.onyx.android.sdk.utils.ResManager
import com.onyx.android.sdk.utils.ReflectUtil
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface HiddenApiExemptionProvider {
    /** Enables only the hidden APIs required by the host application. */
    fun enable()
}

data class OnyxInitializationOptions(
    /** Initializes compatibility-only resource and Rx services used by the legacy base artifact. */
    val legacyBaseServices: Boolean = false,
    val hiddenApiExemptionProvider: HiddenApiExemptionProvider? = null,
)

enum class InitializedModule { KTX, LEGACY_BASE_SERVICES }

data class OnyxInitializationReceipt(
    val modules: Set<InitializedModule>,
    val reusedApplication: Boolean,
)

object OnyxSdk {
    private val bootstrapLock = Any()
    // Atomics keep the properties immutable for static analysis; bootstrapLock still owns every
    // compound transition so provider installation and discovery cannot race each other.
    private val exemptionProvider = AtomicReference<HiddenApiExemptionProvider?>()
    private val discoveryStarted = AtomicBoolean()
    private val externalExemptionAttempted = AtomicBoolean()
    private val externalExemptionSucceeded = AtomicBoolean()
    private val initializedApplication = AtomicReference<Application?>()
    private val legacyBaseInitialized = AtomicBoolean()

    val diagnostics: OnyxDiagnostics
        get() = OnyxDiagnostics

    /**
     * Idempotent, explicit SDK bootstrap. KTX itself needs no eager initialization; callers opt in
     * to legacy base services only when they use APIs that depend on ResManager or RxBaseAction.
     */
    fun initialize(
        application: Application,
        options: OnyxInitializationOptions = OnyxInitializationOptions(),
    ): Result<OnyxInitializationReceipt> = synchronized(bootstrapLock) {
        val existing = initializedApplication.get()
        if (existing != null && existing !== application) {
            return@synchronized Result.failure(
                OnyxFailure.InvalidState(
                    "sdk.initialize",
                    null,
                    "OnyxSdk was already initialized with a different Application",
                ),
            )
        }
        options.hiddenApiExemptionProvider?.let { provider ->
            val installed = exemptionProvider.get()
            if (installed == null && !discoveryStarted.get()) {
                exemptionProvider.set(provider)
            } else if (installed !== provider) {
                return@synchronized Result.failure(
                    OnyxFailure.InvalidState(
                        "sdk.initialize",
                        null,
                        "Hidden API provider must be installed once before capability discovery",
                    ),
                )
            }
        }
        if (options.legacyBaseServices && legacyBaseInitialized.compareAndSet(false, true)) {
            try {
                ResManager.init(application)
                RxBaseAction.init(application)
            } catch (failure: Throwable) {
                legacyBaseInitialized.set(false)
                return@synchronized Result.failure(
                    OnyxFailure.InvalidState(
                        "sdk.initialize.legacy-base",
                        null,
                        failure.message ?: "Legacy base service initialization failed",
                    ),
                )
            }
        }
        initializedApplication.compareAndSet(null, application)
        Result.success(
            OnyxInitializationReceipt(
                modules = buildSet {
                    add(InitializedModule.KTX)
                    if (legacyBaseInitialized.get()) add(InitializedModule.LEGACY_BASE_SERVICES)
                },
                reusedApplication = existing != null,
            ),
        )
    }

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
                val initialBackend = EpdController.getFirmwareBackendInfo()
                val hiddenApiStatus = initialBackend.hiddenApiAccessStatus
                val accessCanBeRetried =
                    hiddenApiStatus == ReflectUtil.HiddenApiAccessStatus.NOT_ATTEMPTED ||
                        hiddenApiStatus == ReflectUtil.HiddenApiAccessStatus.FAILED
                val shouldAttemptExternalProvider =
                    !initialBackend.isAvailable &&
                        accessCanBeRetried &&
                        !externalExemptionAttempted.get()
                if (shouldAttemptExternalProvider) {
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
