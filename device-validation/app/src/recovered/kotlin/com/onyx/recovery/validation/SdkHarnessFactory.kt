package com.onyx.recovery.validation

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.view.SurfaceView
import android.widget.TextView
import com.onyx.android.sdk.ktx.OnyxSdk
import com.onyx.android.sdk.ktx.capabilities.CapabilityProbeMode
import com.onyx.android.sdk.ktx.display.refreshAndAwait
import com.onyx.android.sdk.ktx.ink.RawInkSession
import com.onyx.android.sdk.ktx.ink.RawInkSessionState
import com.onyx.android.sdk.ktx.model.BrushConfiguration
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.ExperimentalOnyxRendererApi
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkTool
import com.onyx.android.sdk.ktx.model.LeasePolicy
import com.onyx.android.sdk.ktx.model.PenKind
import com.onyx.android.sdk.ktx.render.InkRenderer
import java.io.File
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

internal object SdkHarnessFactory {
    @JvmStatic
    fun create(activity: ValidationActivity, recorder: ResultRecorder): SdkHarness =
        RecoveredKtxHarness(activity, recorder)
}

@OptIn(ExperimentalOnyxRendererApi::class)
private class RecoveredKtxHarness(
    private val activity: ValidationActivity,
    private val recorder: ResultRecorder,
) : SdkHarness {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var activeSession: RawInkSession? = null
    private var activeJob: Job? = null

    override fun runAutomated(surface: SurfaceView, status: TextView, finished: Runnable) {
        activeJob = scope.launch {
            try {
                status.text = "KTX: capabilities, refresh, lease, commit, renderers"
                recordCapabilities(surface)
                recordRefresh(surface)
                recordSessionAndCommit(surface)
                recordRenderers()
                exportDiagnostics()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: Throwable) {
                recorder.failure("sdk-facade", "automated", failure)
            } finally {
                finishIfActive(finished)
            }
        }
    }

    override fun startInk(surface: SurfaceView, status: TextView, durationMs: Long, finished: Runnable) {
        activeJob = scope.launch {
            try {
                val session = RawInkSession.open(surface, leasePolicy = LeasePolicy.FAIL_FAST).getOrThrow()
                activeSession = session
                awaitActive(session, surface)
                status.text = "Draw/erase on the lower surface; rotate or background to test suspension"
                recorder.value("sdk-facade", "ink_open", null, session.state.value.toString())
                delay(durationMs)
                recorder.value("sdk-facade", "ink_final_state", null, session.state.value.toString())
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: Throwable) {
                recorder.failure("sdk-facade", "ink", failure)
            } finally {
                withContext(NonCancellable) { activeSession?.closeAndAwait() }
                activeSession = null
                finishIfActive(finished)
            }
        }
    }

    private suspend fun recordCapabilities(surface: SurfaceView) {
        val passive = OnyxSdk.capabilities(probeMode = CapabilityProbeMode.PASSIVE).getOrThrow()
        val active = OnyxSdk.capabilities(surface, CapabilityProbeMode.ACTIVE_REVERSIBLE).getOrThrow()
        recorder.value(
            "sdk-facade",
            "capabilities",
            null,
            ResultRecorder.map(
                "firmware", passive.firmware.fingerprint,
                "transport", passive.transport.toString(),
                "hiddenApi", passive.hiddenApi.toString(),
                "passiveDisplay", passive.display.toString(),
                "activeModes", active.display.updateModes.toString(),
                "activeRawInput", active.rawInk.toString(),
                "activeRenderers", active.renderers.toString(),
            ),
        )
    }

    private suspend fun recordRefresh(surface: SurfaceView) {
        val receipt = surface.refreshAndAwait(EpdUpdateMode.DU).getOrThrow()
        recorder.value("sdk-facade", "refresh_receipt", null, receipt.toString())
    }

    private suspend fun recordSessionAndCommit(surface: SurfaceView) {
        val first = RawInkSession.open(surface, leasePolicy = LeasePolicy.FAIL_FAST).getOrThrow()
        activeSession = first
        try {
            awaitActive(first, surface)
            val contention = RawInkSession.open(surface, leasePolicy = LeasePolicy.FAIL_FAST)
            recorder.value(
                "sdk-facade",
                "lease_contention",
                null,
                ResultRecorder.map(
                    "rejected", contention.isFailure,
                    "failure", contention.exceptionOrNull()?.javaClass?.simpleName,
                ),
            )
            contention.getOrNull()?.let { unexpected ->
                withContext(NonCancellable) { unexpected.closeAndAwait() }
            }
            val width = surface.width.coerceAtLeast(1)
            val height = surface.height.coerceAtLeast(1)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                eraseColor(Color.WHITE)
            }
            try {
                val receipt = first.commit(bitmap, Rect(0, 0, width, height)).getOrThrow()
                recorder.value("sdk-facade", "commit_receipt", null, receipt.toString())
            } finally {
                bitmap.recycle()
            }
        } finally {
            withContext(NonCancellable) { first.closeAndAwait().getOrThrow() }
            activeSession = null
        }
    }

    private suspend fun recordRenderers() {
        val results = withContext(Dispatchers.Default) {
            val points = listOf(
                point(20f, 20f, 0.2f, 1L),
                point(60f, 80f, 0.7f, 2L),
                point(120f, 100f, 0.5f, 3L),
            )
            PenKind.entries.associateWith { kind ->
                InkRenderer.create(kind, BrushConfiguration(widthPx = 4f)).fold(
                    onSuccess = { renderer ->
                        renderer.use { it.render(points).getOrThrow().bounds.toString() }
                    },
                    onFailure = { "${it.javaClass.simpleName}: ${it.message}" },
                )
            }
        }
        withContext(Dispatchers.IO) {
            results.forEach { (kind, result) ->
                recorder.value("sdk-facade", "renderer_${kind.name.lowercase()}", null, result)
            }
        }
    }

    private suspend fun exportDiagnostics() {
        withContext(Dispatchers.IO) {
            val diagnostics = OnyxSdk.diagnostics.snapshot()
            val output = File(activity.filesDir, "onyx-diagnostics.txt")
            output.writeText(diagnostics.joinToString("\n"))
            recorder.value(
                "sdk-facade",
                "diagnostics_export",
                null,
                ResultRecorder.map("events", diagnostics.size, "file", output.name),
            )
        }
    }

    private suspend fun awaitActive(session: RawInkSession, surface: SurfaceView) {
        if (session.state.value is RawInkSessionState.Active) return
        val active = withTimeoutOrNull(5_000) {
            session.state.filterIsInstance<RawInkSessionState.Active>().first()
        }
        check(active != null) {
            "Raw surface did not activate: state=${session.state.value}, " +
                "valid=${surface.holder.surface.isValid}, attached=${surface.isAttachedToWindow}, " +
                "size=${surface.width}x${surface.height}"
        }
    }

    override fun close() {
        activeJob?.cancel()
        activeSession?.close()
        activeSession = null
        scope.cancel()
    }

    private fun finishIfActive(finished: Runnable) {
        if (!activity.isDestroyed && !activity.isFinishing) finished.run()
    }

    private fun point(x: Float, y: Float, pressure: Float, sequence: Long) = InkPoint(
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
}
