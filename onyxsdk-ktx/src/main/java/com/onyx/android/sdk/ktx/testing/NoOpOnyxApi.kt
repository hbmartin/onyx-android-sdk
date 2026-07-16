package com.onyx.android.sdk.ktx.testing

import android.view.SurfaceView
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.onyx.android.sdk.ktx.api.CapabilityProvider
import com.onyx.android.sdk.ktx.api.DisplayController
import com.onyx.android.sdk.ktx.api.PenSessionFactory
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.capabilities.OnyxDeviceCapabilities
import com.onyx.android.sdk.ktx.display.RefreshModeToken
import com.onyx.android.sdk.ktx.display.RefreshReceipt
import com.onyx.android.sdk.ktx.display.TransientUpdateMode
import com.onyx.android.sdk.ktx.ink.PenSession
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.LeasePolicy
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.RawInkConfiguration
import com.onyx.android.sdk.ktx.model.RefreshScope
import kotlin.time.Duration

/** Explicit non-Onyx fallback that never pretends a firmware operation succeeded. */
object NoOpOnyxApi : CapabilityProvider, DisplayController, PenSessionFactory {
    /** Returns an unsupported-capability failure without probing firmware. */
    override suspend fun capabilities(
        surfaceView: SurfaceView?,
        probeMode: CapabilityProbeMode,
    ): Result<OnyxDeviceCapabilities> = unsupported("capabilities")

    /** Returns an unsupported-capability failure without dispatching a refresh. */
    override suspend fun refresh(
        view: View,
        mode: EpdUpdateMode,
        scope: RefreshScope,
        timeout: Duration,
    ): Result<RefreshReceipt> = unsupported("refresh")

    /** Returns an unsupported-capability failure without acquiring firmware state. */
    override suspend fun acquireTransientMode(
        mode: TransientUpdateMode,
    ): Result<RefreshModeToken> = unsupported("transient-mode")

    /** Returns an unsupported-capability failure without opening a pen session. */
    override suspend fun attach(
        surfaceView: SurfaceView,
        lifecycleOwner: LifecycleOwner,
        configuration: RawInkConfiguration,
        leasePolicy: LeasePolicy,
    ): Result<PenSession> = unsupported("pen-session")

    private fun <T> unsupported(operation: String): Result<T> = Result.failure(
        OnyxFailure.UnsupportedCapability(
            "noop.$operation",
            null,
            "Onyx firmware is unavailable; use software rendering for this operation",
        ),
    )
}
