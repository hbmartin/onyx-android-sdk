package com.onyx.android.sdk.ktx.api

import android.view.SurfaceView
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.onyx.android.sdk.ktx.OnyxSdk
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.display.RefreshModeController
import com.onyx.android.sdk.ktx.display.RefreshModeToken
import com.onyx.android.sdk.ktx.display.RefreshReceipt
import com.onyx.android.sdk.ktx.display.TransientUpdateMode
import com.onyx.android.sdk.ktx.display.refreshAndAwait
import com.onyx.android.sdk.ktx.ink.PenSession
import com.onyx.android.sdk.ktx.ink.RawInkSession
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.LeasePolicy
import com.onyx.android.sdk.ktx.model.RawInkConfiguration
import com.onyx.android.sdk.ktx.model.RefreshScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun interface CapabilityProvider {
    suspend fun capabilities(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
    ): Result<OnyxDeviceCapabilities>
}

interface DisplayController {
    suspend fun refresh(
        view: View,
        mode: EpdUpdateMode,
        scope: RefreshScope = RefreshScope.FullView,
        timeout: Duration = 2.seconds,
    ): Result<RefreshReceipt>

    suspend fun acquireTransientMode(
        mode: TransientUpdateMode = TransientUpdateMode.ANIMATION_QUALITY,
    ): Result<RefreshModeToken>
}

fun interface PenSessionFactory {
    suspend fun attach(
        surfaceView: SurfaceView,
        lifecycleOwner: LifecycleOwner,
        configuration: RawInkConfiguration,
        leasePolicy: LeasePolicy,
    ): Result<PenSession>
}

/** Ergonomic passive probe while retaining SAM-convertible injectable interfaces. */
suspend fun CapabilityProvider.capabilities(
    surfaceView: SurfaceView? = null,
): Result<OnyxDeviceCapabilities> = capabilities(surfaceView, CapabilityProbeMode.PASSIVE)

/** Attaches with fail-fast process ownership unless the caller requests another lease policy. */
suspend fun PenSessionFactory.attach(
    surfaceView: SurfaceView,
    lifecycleOwner: LifecycleOwner,
    configuration: RawInkConfiguration = RawInkConfiguration(),
): Result<PenSession> = attach(
    surfaceView,
    lifecycleOwner,
    configuration,
    LeasePolicy.FAIL_FAST,
)

object DefaultCapabilityProvider : CapabilityProvider {
    override suspend fun capabilities(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
    ): Result<OnyxDeviceCapabilities> = OnyxSdk.capabilities(surfaceView, probeMode)
}

object DefaultDisplayController : DisplayController {
    override suspend fun refresh(
        view: View,
        mode: EpdUpdateMode,
        scope: RefreshScope,
        timeout: Duration,
    ): Result<RefreshReceipt> = view.refreshAndAwait(mode, scope, timeout)

    override suspend fun acquireTransientMode(
        mode: TransientUpdateMode,
    ): Result<RefreshModeToken> = RefreshModeController.acquire(mode)
}

object DefaultPenSessionFactory : PenSessionFactory {
    override suspend fun attach(
        surfaceView: SurfaceView,
        lifecycleOwner: LifecycleOwner,
        configuration: RawInkConfiguration,
        leasePolicy: LeasePolicy,
    ): Result<PenSession> = RawInkSession.attach(
        surfaceView,
        lifecycleOwner,
        configuration,
        leasePolicy,
    )
}
