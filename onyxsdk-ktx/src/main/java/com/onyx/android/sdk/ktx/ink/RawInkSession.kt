@file:Suppress(
    // Actor-owned mutable state and broad cleanup boundaries are intentional: every mutation
    // happens on one dispatcher, and cleanup must convert arbitrary framework/native failures.
    "AvoidVarsExceptWithDelegate",
    "AvoidMutableCollections",
    "CyclomaticComplexMethod",
    "DontForceCast",
    "DoubleMutabilityForCollection",
    "InstanceOfCheckForException",
    "LargeClass",
    "LongMethod",
    "MagicNumber",
    "NoCallbacksInFunctions",
    "ReturnCount",
    "ThrowsCount",
    "TooGenericExceptionCaught",
    "TooManyFunctions",
)

package com.onyx.android.sdk.ktx.ink

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.onyx.android.sdk.api.device.epd.EpdController
import com.onyx.android.sdk.api.device.epd.UpdateMode
import com.onyx.android.sdk.data.note.TouchPoint
import com.onyx.android.sdk.ktx.capabilities.toLegacyUpdateMode
import com.onyx.android.sdk.ktx.diagnostics.FirmwareBackendKind
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticPhase
import com.onyx.android.sdk.ktx.diagnostics.onyxResult
import com.onyx.android.sdk.ktx.diagnostics.FirmwareDiagnosticStatus
import com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics
import com.onyx.android.sdk.ktx.display.RefreshCompletionEvidence
import com.onyx.android.sdk.ktx.display.awaitEpdIdle
import com.onyx.android.sdk.ktx.model.ActiveStrokePolicy
import com.onyx.android.sdk.ktx.model.CommitOptions
import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkPreview
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.ktx.model.InkTool
import com.onyx.android.sdk.ktx.model.LeasePolicy
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.PenEvent
import com.onyx.android.sdk.ktx.model.PenPhase
import com.onyx.android.sdk.ktx.model.RawInkConfiguration
import com.onyx.android.sdk.ktx.model.RegionMode
import com.onyx.android.sdk.pen.RawDrawingConfigurationPolicy
import com.onyx.android.sdk.pen.RawDrawingConfigurationSnapshot
import com.onyx.android.sdk.pen.RawInputCallback
import com.onyx.android.sdk.pen.RawInputDeviceInfo
import com.onyx.android.sdk.pen.RawInputEventV2
import com.onyx.android.sdk.pen.RawInputListenerV2
import com.onyx.android.sdk.pen.RawInputPhase
import com.onyx.android.sdk.pen.RawInputTool
import com.onyx.android.sdk.pen.TouchHelper
import com.onyx.android.sdk.pen.data.TouchPointList
import java.io.Closeable
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration

private object ProcessRawInkLease {
    private val mutex = Mutex()

    suspend fun acquire(owner: Any, policy: LeasePolicy): Boolean = when (policy) {
        LeasePolicy.FAIL_FAST -> mutex.tryLock(owner)
        LeasePolicy.WAIT -> {
            mutex.lock(owner)
            true
        }
    }

    fun release(owner: Any) {
        if (mutex.holdsLock(owner)) mutex.unlock(owner)
    }
}

/**
 * Exclusive, generation-aware owner of the firmware raw-ink overlay.
 *
 * All lifecycle, input, surface, and commit commands are processed on one private queue. The
 * public suspend functions may be called from any dispatcher.
 */
class RawInkSession private constructor(
    private val surfaceView: SurfaceView,
    private val configuration: RawInkConfiguration,
    private val actorDispatcher: CoroutineDispatcher,
    private val releaseLease: () -> Unit,
) : PenSession {
    private val scope = CoroutineScope(SupervisorJob() + actorDispatcher)
    private val commands = Channel<Command>(Channel.UNLIMITED)
    private val eventStream = CollectorBoundEventStream<PenEvent>(
        "RawInkSession.events supports one collector at a time",
    )
    private val strokeChannel = Channel<InkStroke>(Channel.UNLIMITED)
    private val strokeConsumer = AtomicBoolean()
    private val closeRequested = AtomicBoolean()
    private val closeCommand = Command.Close()
    private val mutableState = MutableStateFlow<RawInkSessionState>(RawInkSessionState.Opening)
    private val mutablePreview = MutableStateFlow<InkPreview?>(null)
    private val mutableStrokeActive = MutableStateFlow(false)
    private val mutableInputProbe = MutableStateFlow<RawInputProbe?>(null)
    // Erased deliberately so Kotlin's public synthetic coroutine accessors do not leak legacy
    // SDK descriptors from the KTX AAR. Casts remain confined to implementation code.
    private var helper: Any? = null
    private var priorConfiguration: Any? = null
    private var surfaceGeneration = 0L
    private var currentStroke: MutableList<InkPoint>? = null
    private var currentTool: InkTool? = null
    private var manuallyPaused = false
    private var lifecyclePaused = false
    private var lifecycleBinding: LifecycleBinding? = null
    @Volatile
    private var closed = false

    override val state: StateFlow<RawInkSessionState> = mutableState.asStateFlow()

    /** Lossless and ordered while collected; events are not retained without a collector. */
    override val events: Flow<PenEvent> = eventStream.flow

    /** Latest-wins preview. Sequence numbers make any coalesced intermediate updates explicit. */
    override val preview: StateFlow<InkPreview?> = mutablePreview.asStateFlow()

    /** Lossless channel with an enforced single collector. */
    override val completedStrokes: Flow<InkStroke> = flow {
        check(strokeConsumer.compareAndSet(false, true)) {
            "RawInkSession.completedStrokes supports one collector at a time"
        }
        try {
            for (stroke in strokeChannel) emit(stroke)
        } finally {
            strokeConsumer.set(false)
        }
    }

    private val rawListener = object : RawInputListenerV2 {
        override fun onRawInputEvent(event: RawInputEventV2) {
            commands.trySend(
                Command.RawEvent(
                    point = InkPoint(
                        xPx = event.x,
                        yPx = event.y,
                        rawPressure = event.rawPressure,
                        normalizedPressure = event.normalizedPressure,
                        maxPressure = event.pressureMaximum,
                        tiltX = event.tiltX,
                        tiltY = event.tiltY,
                        eventTimeNanos = event.timestampNanos,
                        sequence = event.sequence,
                        tool = when (event.tool) {
                            RawInputTool.PEN -> InkTool.PEN
                            RawInputTool.SIDE_ERASER -> InkTool.SIDE_ERASER
                            RawInputTool.TAIL_ERASER -> InkTool.TAIL_ERASER
                        },
                    ),
                    phase = when (event.phase) {
                        RawInputPhase.DOWN -> PenPhase.DOWN
                        RawInputPhase.MOVE -> PenPhase.MOVE
                        RawInputPhase.UP -> PenPhase.UP
                        RawInputPhase.PROXIMITY -> PenPhase.PROXIMITY
                    },
                    outsideLimitRegion = event.isOutsideLimitRegion,
                    forced = event.isForced,
                ),
            )
        }

        override fun onRawInputDeviceInfo(deviceInfo: RawInputDeviceInfo) {
            val started = System.nanoTime()
            fun axis(value: com.onyx.android.sdk.pen.RawInputAxisRange?): RawInputAxisProbe? =
                value?.let {
                    RawInputAxisProbe(
                        minimum = it.minimum,
                        maximum = it.maximum,
                        fuzz = it.fuzz,
                        flat = it.flat,
                        resolution = it.resolution,
                    )
                }
            mutableInputProbe.value = RawInputProbe(
                x = axis(deviceInfo.x),
                y = axis(deviceInfo.y),
                pressure = axis(deviceInfo.pressure),
                tiltX = axis(deviceInfo.tiltX),
                tiltY = axis(deviceInfo.tiltY),
                kernelMonotonicClock = deviceInfo.isMonotonicClock,
            )
            OnyxDiagnostics.record(
                "raw.device-info",
                FirmwareBackendKind.NATIVE_INPUT,
                FirmwareDiagnosticPhase.RESOLUTION,
                FirmwareDiagnosticStatus.SUCCEEDED,
                started,
            )
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            commands.trySend(Command.SurfaceCreated)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            commands.trySend(Command.SurfaceDestroyed)
        }
    }

    init {
        scope.launch {
            for (command in commands) {
                try {
                    process(command)
                } catch (failure: Throwable) {
                    abortActor(command, failure)
                    break
                }
            }
        }
    }

    override suspend fun commit(
        bitmap: Bitmap,
        destination: Rect,
        dirtyRegion: Rect,
        options: CommitOptions,
    ): Result<CommitReceipt> {
        if (bitmap.isRecycled) {
            return Result.failure(OnyxFailure.InvalidArgument("raw.commit", "bitmap is recycled"))
        }
        if (destination.isEmpty || dirtyRegion.isEmpty) {
            return Result.failure(
                OnyxFailure.InvalidArgument("raw.commit", "destination and dirtyRegion must not be empty"),
            )
        }
        when (val policy = options.activeStrokePolicy) {
            ActiveStrokePolicy.Reject -> if (mutableStrokeActive.value) {
                return Result.failure(
                    OnyxFailure.InvalidState("raw.commit", null, "A stroke is active"),
                )
            }
            is ActiveStrokePolicy.WaitForPenUp -> if (mutableStrokeActive.value) {
                val penReleased = withTimeoutOrNull(policy.timeout) {
                    if (mutableStrokeActive.value) {
                        mutableStrokeActive.filter { active -> !active }.first()
                    }
                    true
                } ?: false
                if (!penReleased) {
                    return Result.failure(
                        OnyxFailure.TimedOut(
                            "raw.commit.wait-for-pen-up",
                            null,
                            "Pen remained down for ${policy.timeout}",
                        ),
                    )
                }
            }
        }
        return request(Command.Commit(bitmap, Rect(destination), Rect(dirtyRegion), options))
    }

    override suspend fun closeAndAwait(): Result<Unit> {
        if (closed) return closeCommand.reply.await()
        if (closeRequested.compareAndSet(false, true)) {
            if (commands.trySend(closeCommand).isFailure) {
                return if (closed) {
                    closeCommand.reply.await()
                } else {
                    Result.failure(
                        OnyxFailure.InvalidState("raw.close", null, "Session command queue is closed"),
                    )
                }
            }
        }
        return closeCommand.reply.await()
    }

    override suspend fun pause(): Result<Unit> {
        if (closed) {
            val failure = (mutableState.value as? RawInkSessionState.Failed)?.failure
            return failure?.let { Result.failure(it) } ?: Result.success(Unit)
        }
        return request(Command.SetPaused(SuspensionReason.REQUESTED, true))
    }

    override suspend fun resume(): Result<Unit> {
        if (closed) {
            return Result.failure(OnyxFailure.InvalidState("raw.resume", null, "Session is closed"))
        }
        return request(Command.SetPaused(SuspensionReason.REQUESTED, false))
    }

    override fun close() {
        if (closed) return
        if (closeRequested.compareAndSet(false, true)) {
            commands.trySend(closeCommand)
        }
    }

    private suspend fun initialize(): Result<Unit> = request(Command.Open())

    private suspend fun bindLifecycle(owner: LifecycleOwner): Result<Unit> =
        request(Command.BindLifecycle(owner))

    internal suspend fun awaitInputProbe(timeout: Duration): Result<RawInputProbe> {
        mutableInputProbe.value?.let { return Result.success(it) }
        val probe = withTimeoutOrNull(timeout) {
            mutableInputProbe.filter { it != null }.first()
        }
        return if (probe != null) {
            Result.success(probe)
        } else {
            Result.failure(
                OnyxFailure.TimedOut(
                    "raw.input-probe",
                    null,
                    "Native input metadata was not reported within $timeout",
                ),
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> request(command: RequestCommand<T>): Result<T> {
        if (closed && command !is Command.Close) {
            return Result.failure(OnyxFailure.InvalidState(command.operation, null, "Session is closed"))
        }
        if (commands.trySend(command).isFailure) {
            return Result.failure(
                OnyxFailure.InvalidState(command.operation, null, "Session command queue is closed"),
            )
        }
        return command.reply.await()
    }

    private suspend fun process(command: Command) {
        when (command) {
            is Command.RawEvent -> handleRawEvent(command)
            Command.SurfaceCreated -> handleSurfaceCreated()
            Command.SurfaceDestroyed -> handleSurfaceDestroyed()
            is RequestCommand<*> -> processRequest(command)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun processRequest(command: RequestCommand<*>) {
        val result: Result<Any?> = try {
            when (command) {
                is Command.Open -> openOnActor()
                is Command.Commit -> commitOnActor(command)
                is Command.SetPaused -> setPausedOnActor(command.reason, command.paused)
                is Command.BindLifecycle -> bindLifecycleOnActor(command.owner)
                is Command.Close -> closeOnActor()
            } as Result<Any?>
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: Throwable) {
            Result.failure(failure)
        }
        (command.reply as CompletableDeferred<Result<Any?>>).complete(result)
        if (command is Command.Close) {
            commands.close()
            failQueuedRequests(
                OnyxFailure.InvalidState("raw.session", null, "Session closed before command execution"),
            )
            (actorDispatcher as? Closeable)?.close()
        }
    }

    private suspend fun abortActor(command: Command, failure: Throwable) {
        val typed = terminateFailedSession(
            failure as? OnyxFailure ?: OnyxFailure.FirmwareRejected(
                "raw.actor",
                null,
                failure.message ?: "Raw ink actor failed",
                failure,
            ),
        )
        completeFailure(command, typed)
    }

    @Suppress("UNCHECKED_CAST")
    private fun completeFailure(command: Command, failure: Throwable) {
        if (command is RequestCommand<*>) {
            (command.reply as CompletableDeferred<Result<Any?>>).complete(Result.failure(failure))
        }
    }

    private fun failQueuedRequests(failure: Throwable) {
        while (true) {
            val pending = commands.tryReceive().getOrNull() ?: return
            completeFailure(pending, failure)
        }
    }

    private suspend fun openOnActor(): Result<Unit> = runFirmwareOperation("raw.open") {
        withContext(Dispatchers.Main.immediate) {
            val callback = noOpLegacyCallback()
            val current = TouchHelper.create(surfaceView, callback).setRawInputListenerV2(rawListener)
            helper = current
            priorConfiguration = current.captureRawDrawingConfiguration()
            surfaceView.holder.addCallback(surfaceCallback)
        }
        if (surfaceView.holder.surface.isValid) {
            surfaceGeneration++
            startOrResumeForSurface()
        } else {
            mutableState.value = RawInkSessionState.Suspended(
                surfaceGeneration,
                SuspensionReason.SURFACE_UNAVAILABLE,
            )
        }
    }

    private suspend fun handleSurfaceCreated() {
        if (closed) return
        surfaceGeneration++
        val result = runFirmwareOperation("raw.surface-created") {
            if (effectiveSuspensionReason() == null) {
                startOrResumeForSurface()
            } else {
                updateSuspendedState(requireNotNull(effectiveSuspensionReason()))
            }
        }
        result.exceptionOrNull()?.let { terminateFailedSession(it) }
    }

    private suspend fun startOrResumeForSurface() {
        effectiveSuspensionReason()?.let {
            updateSuspendedState(it)
            return
        }
        withContext(Dispatchers.Main.immediate) {
            val current = requireNotNull(helper as? TouchHelper)
            if (!current.isRawDrawingCreated) {
                current.openRawDrawing()
            }
            applyConfiguration(current)
            current.setRawDrawingRenderEnabled(true)
            current.setRawInputReaderEnable(true)
        }
        mutableState.value = RawInkSessionState.Active(surfaceGeneration)
    }

    private suspend fun handleSurfaceDestroyed() {
        if (closed) return
        surfaceGeneration++
        withContext(Dispatchers.Main.immediate) {
            (helper as? TouchHelper)?.setRawInputReaderEnable(false)
            (helper as? TouchHelper)?.setRawDrawingRenderEnabled(false)
        }
        clearActiveStroke()
        updateSuspendedState(effectiveSuspensionReason() ?: SuspensionReason.SURFACE_DESTROYED)
    }

    private suspend fun handleRawEvent(event: Command.RawEvent) {
        if (mutableState.value !is RawInkSessionState.Active) return
        val point = event.point
        eventStream.tryEmit(
            PenEvent(
                phase = event.phase,
                point = point,
                outsideLimitRegion = event.outsideLimitRegion,
                forced = event.forced,
            ),
        )
        if (event.phase == PenPhase.PROXIMITY) return
        val previewPhase = when (event.phase) {
            PenPhase.DOWN -> InkPreview.Phase.DOWN
            PenPhase.MOVE -> InkPreview.Phase.MOVE
            PenPhase.UP -> InkPreview.Phase.UP
            PenPhase.PROXIMITY -> return
        }
        mutablePreview.value = InkPreview(point, previewPhase)
        when (event.phase) {
            PenPhase.DOWN -> {
                currentStroke = arrayListOf(point)
                currentTool = point.tool
                mutableStrokeActive.value = true
            }
            PenPhase.MOVE -> {
                val stroke = currentStroke ?: arrayListOf<InkPoint>().also {
                    currentStroke = it
                    currentTool = point.tool
                    mutableStrokeActive.value = true
                }
                stroke += point
                if (stroke.size > configuration.maxPointsPerStroke) {
                    val diagnostic = OnyxDiagnostics.record(
                        "raw.stroke",
                        FirmwareBackendKind.NATIVE_INPUT,
                        FirmwareDiagnosticPhase.VALIDATION,
                        FirmwareDiagnosticStatus.FAILED,
                        System.nanoTime(),
                    )
                    val failure = OnyxFailure.EventOverflow(
                        "raw.stroke",
                        diagnostic.id,
                        "Stroke exceeded ${configuration.maxPointsPerStroke} points; input paused",
                    )
                    withContext(Dispatchers.Main.immediate) {
                        (helper as? TouchHelper)?.setRawInputReaderEnable(false)
                    }
                    currentStroke = null
                    currentTool = null
                    mutableStrokeActive.value = false
                    terminateFailedSession(failure)
                }
            }
            PenPhase.UP -> {
                val points = currentStroke?.also { it += point } ?: listOf(point)
                val tool = currentTool ?: point.tool
                currentStroke = null
                currentTool = null
                mutableStrokeActive.value = false
                strokeChannel.send(
                    InkStroke(
                        tool = tool,
                        points = points.toList(),
                        endedOutsideLimitRegion = event.outsideLimitRegion,
                        bounds = points.bounds(),
                    ),
                )
            }
            PenPhase.PROXIMITY -> Unit
        }
    }

    private suspend fun setPausedOnActor(
        reason: SuspensionReason,
        paused: Boolean,
    ): Result<Unit> = runFirmwareOperation("raw.${if (paused) "pause" else "resume"}") {
        when (reason) {
            SuspensionReason.REQUESTED -> manuallyPaused = paused
            SuspensionReason.LIFECYCLE_STOPPED -> lifecyclePaused = paused
            SuspensionReason.SURFACE_UNAVAILABLE,
            SuspensionReason.SURFACE_DESTROYED,
            -> throw OnyxFailure.InvalidArgument("raw.pause", "$reason is not caller-controlled")
        }
        val activeSuspension = effectiveSuspensionReason()
        if (activeSuspension != null) {
            withContext(Dispatchers.Main.immediate) {
                (helper as? TouchHelper)?.setRawInputReaderEnable(false)
                (helper as? TouchHelper)?.setRawDrawingRenderEnabled(false)
            }
            clearActiveStroke()
            updateSuspendedState(activeSuspension)
        } else if (surfaceView.holder.surface.isValid) {
            startOrResumeForSurface()
        } else {
            updateSuspendedState(SuspensionReason.SURFACE_UNAVAILABLE)
        }
    }

    private suspend fun bindLifecycleOnActor(owner: LifecycleOwner): Result<Unit> =
        runFirmwareOperation("raw.lifecycle-bind") {
            withContext(Dispatchers.Main.immediate) {
                check(owner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    "Cannot attach a pen session to a destroyed lifecycle"
                }
                lifecycleBinding?.remove()
                val observer = object : DefaultLifecycleObserver {
                    override fun onStart(owner: LifecycleOwner) {
                        commands.trySend(
                            Command.SetPaused(SuspensionReason.LIFECYCLE_STOPPED, false),
                        )
                    }

                    override fun onStop(owner: LifecycleOwner) {
                        commands.trySend(
                            Command.SetPaused(SuspensionReason.LIFECYCLE_STOPPED, true),
                        )
                    }

                    override fun onDestroy(owner: LifecycleOwner) {
                        close()
                    }
                }
                lifecycleBinding = LifecycleBinding(owner.lifecycle, observer).also { it.add() }
                lifecyclePaused = !owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
            }
            if (lifecyclePaused) {
                setPausedOnActor(SuspensionReason.LIFECYCLE_STOPPED, true).getOrThrow()
            }
        }

    private fun effectiveSuspensionReason(): SuspensionReason? = when {
        manuallyPaused -> SuspensionReason.REQUESTED
        lifecyclePaused -> SuspensionReason.LIFECYCLE_STOPPED
        else -> null
    }

    private fun updateSuspendedState(reason: SuspensionReason) {
        mutableState.value = RawInkSessionState.Suspended(surfaceGeneration, reason)
    }

    private fun clearActiveStroke() {
        currentStroke = null
        currentTool = null
        mutableStrokeActive.value = false
        mutablePreview.value = null
    }

    private suspend fun commitOnActor(command: Command.Commit): Result<CommitReceipt> {
        if (currentStroke != null) {
            return Result.failure(
                OnyxFailure.InvalidState("raw.commit", null, "A stroke started before commit serialization"),
            )
        }
        val activeState = mutableState.value as? RawInkSessionState.Active
            ?: return Result.failure(
                OnyxFailure.SurfaceUnavailable(
                    "raw.commit",
                    null,
                    "Session has no active owned surface: ${mutableState.value}",
                ),
            )
        val generation = activeState.surfaceGeneration
        mutableState.value = RawInkSessionState.Committing(generation)
        var cleanupFailure: Throwable? = null
        var previousUpdateMode: UpdateMode? = null
        var viewModeApplied = false
        var viewModeRestored = false
        val warnings = mutableSetOf(CommitWarning.BEST_EFFORT_NOT_HARDWARE_ATOMIC)
        val outcome = try {
            withContext(Dispatchers.Main.immediate) {
                (helper as? TouchHelper)?.setRawInputReaderEnable(false)
            }
            if (generation != surfaceGeneration || !surfaceView.holder.surface.isValid) {
                throw surfaceChanged(generation)
            }
            previousUpdateMode = withContext(Dispatchers.Main.immediate) {
                EpdController.getViewDefaultUpdateMode(surfaceView)
            }
            withContext(Dispatchers.Main.immediate) {
                val modeResult = onyxResult(
                    operation = "raw.commit.view-mode",
                    backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
                    phase = FirmwareDiagnosticPhase.VALIDATION,
                ) {
                    EpdController.setViewDefaultUpdateModeOrThrow(
                        surfaceView,
                        UpdateMode.HAND_WRITING_REPAINT_MODE,
                    )
                }
                if (modeResult.isSuccess) {
                    viewModeApplied = true
                } else if (modeResult.exceptionOrNull() is OnyxFailure.FirmwareRejected) {
                    // Some firmware exposes HAND_WRITING_REPAINT_MODE but maps it back to GC.
                    // The dedicated repaint transaction below remains the authoritative overlay
                    // synchronization step, so retain honest best-effort semantics here.
                    warnings += CommitWarning.HANDWRITING_REPAINT_VIEW_MODE_REJECTED
                } else {
                    throw requireNotNull(modeResult.exceptionOrNull())
                }
                val canvas = surfaceView.holder.lockCanvas(command.dirtyRegion)
                    ?: throw OnyxFailure.SurfaceUnavailable(
                        "raw.commit.surface-post",
                        null,
                        "SurfaceHolder.lockCanvas returned null",
                    )
                try {
                    canvas.drawBitmap(command.bitmap, null, command.destination, Paint(Paint.FILTER_BITMAP_FLAG))
                } finally {
                    surfaceView.holder.unlockCanvasAndPost(canvas)
                }
                if (generation != surfaceGeneration || !surfaceView.holder.surface.isValid) {
                    throw surfaceChanged(generation)
                }
                onyxResult(
                    operation = "raw.commit.overlay-reconciliation",
                    backend = FirmwareBackendKind.SURFACE_FLINGER_BINDER,
                ) {
                    EpdController.handwritingRepaintOrThrow(
                        surfaceView,
                        command.dirtyRegion.left,
                        command.dirtyRegion.top,
                        command.dirtyRegion.right,
                        command.dirtyRegion.bottom,
                    )
                }.getOrThrow()
                EpdController.refreshScreenRegion(
                    surfaceView,
                    command.dirtyRegion.left,
                    command.dirtyRegion.top,
                    command.dirtyRegion.width(),
                    command.dirtyRegion.height(),
                    command.options.updateMode.toLegacyUpdateMode() as UpdateMode,
                )
                previousUpdateMode?.takeIf { viewModeApplied }?.let {
                    EpdController.setViewDefaultUpdateModeOrThrow(surfaceView, it)
                    viewModeRestored = true
                }
            }
            val refreshResult = awaitEpdIdle(command.options.refreshTimeout)
            if (refreshResult.isFailure) throw requireNotNull(refreshResult.exceptionOrNull())
            val refresh = refreshResult.getOrThrow()
            Result.success(
                CommitReceipt(
                    surfaceGeneration = generation,
                    surfacePost = SurfacePostEvidence.POSTED_AND_GENERATION_VERIFIED,
                    overlaySynchronization = OverlaySynchronizationEvidence.HANDWRITING_REPAINT,
                    refresh = refresh,
                    configurationRestoration =
                        ConfigurationRestorationEvidence.REAPPLIED_SESSION_CONFIGURATION,
                    warnings = warnings.apply {
                        if (refresh.evidence == RefreshCompletionEvidence.ESTIMATED_DELAY) {
                            add(CommitWarning.ESTIMATED_REFRESH_COMPLETION)
                        }
                    }.toSet(),
                ),
            )
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: Throwable) {
            Result.failure(
                if (failure is OnyxFailure) {
                    failure
                } else {
                    requireNotNull(
                        onyxResult<Unit>(
                            operation = "raw.commit",
                            backend = FirmwareBackendKind.FRAMEWORK_REFLECTION,
                        ) { throw failure }.exceptionOrNull(),
                    )
                },
            )
        } finally {
            withContext(NonCancellable + Dispatchers.Main.immediate) {
                try {
                    if (generation == surfaceGeneration && surfaceView.holder.surface.isValid) {
                        previousUpdateMode?.takeIf { viewModeApplied && !viewModeRestored }?.let {
                            EpdController.setViewDefaultUpdateModeOrThrow(surfaceView, it)
                            viewModeRestored = true
                        }
                        helper?.let(::applyConfiguration)
                        (helper as? TouchHelper)?.setRawDrawingRenderEnabled(true)
                        (helper as? TouchHelper)?.setRawInputReaderEnable(true)
                        if (mutableState.value is RawInkSessionState.Committing) {
                            mutableState.value = RawInkSessionState.Active(generation)
                        }
                    } else {
                        mutableState.value = RawInkSessionState.Suspended(
                            surfaceGeneration,
                            SuspensionReason.SURFACE_DESTROYED,
                        )
                    }
                } catch (failure: Throwable) {
                    cleanupFailure = failure
                }
            }
        }
        val cleanup = cleanupFailure?.let {
            OnyxFailure.FirmwareRejected(
                "raw.commit.cleanup",
                null,
                it.message ?: "Commit cleanup failed",
                it,
            )
        }
        if (cleanup != null) {
            terminateFailedSession(cleanup)
            return Result.failure(cleanup)
        }
        return outcome
    }

    private suspend fun closeOnActor(): Result<Unit> {
        if (closed) return Result.success(Unit)
        mutableState.value = RawInkSessionState.Closing
        var closeFailure: OnyxFailure? = null
        return try {
            releaseFirmwareResources()
            mutableState.value = RawInkSessionState.Closed
            Result.success(Unit)
        } catch (failure: Throwable) {
            val typed = OnyxFailure.FirmwareRejected(
                "raw.close",
                null,
                failure.message ?: "Raw ink close failed",
                failure,
            )
            closeFailure = typed
            mutableState.value = RawInkSessionState.Failed(typed)
            Result.failure(typed)
        } finally {
            closed = true
            helper = null
            priorConfiguration = null
            currentStroke = null
            currentTool = null
            mutableStrokeActive.value = false
            mutablePreview.value = null
            strokeChannel.close(closeFailure)
            eventStream.close(closeFailure)
            releaseLease()
        }
    }

    private suspend fun terminateFailedSession(failure: Throwable): OnyxFailure {
        val typed = failure as? OnyxFailure ?: OnyxFailure.InvalidState(
            "raw.session",
            null,
            failure.message ?: failure.javaClass.simpleName,
        )
        if (closed) {
            mutableState.value = RawInkSessionState.Failed(typed)
            return typed
        }
        closeRequested.set(true)
        closed = true
        mutableState.value = RawInkSessionState.Failed(typed)
        try {
            releaseFirmwareResources()
        } catch (cleanupFailure: Throwable) {
            typed.addSuppressed(cleanupFailure)
        } finally {
            helper = null
            priorConfiguration = null
            currentStroke = null
            currentTool = null
            mutableStrokeActive.value = false
            mutablePreview.value = null
            releaseLease()
            strokeChannel.close(typed)
            eventStream.close(typed)
            completeFailure(closeCommand, typed)
            commands.close(typed)
            failQueuedRequests(typed)
            mutableState.value = RawInkSessionState.Failed(typed)
            (actorDispatcher as? Closeable)?.close()
        }
        return typed
    }

    private suspend fun releaseFirmwareResources() {
        withContext(NonCancellable + Dispatchers.Main.immediate) {
            val activeHelper = helper as? TouchHelper
            var firstFailure: Throwable? = null
            fun attempt(action: () -> Unit) {
                try {
                    action()
                } catch (failure: Throwable) {
                    if (firstFailure == null) {
                        firstFailure = failure
                    } else if (firstFailure !== failure) {
                        firstFailure?.addSuppressed(failure)
                    }
                }
            }

            attempt { surfaceView.holder.removeCallback(surfaceCallback) }
            attempt { lifecycleBinding?.remove() }
            lifecycleBinding = null
            attempt { activeHelper?.setRawInputListenerV2(null) }
            attempt { activeHelper?.setRawInputReaderEnable(false) }
            attempt { activeHelper?.setRawDrawingRenderEnabled(false) }
            attempt {
                (priorConfiguration as? RawDrawingConfigurationSnapshot)?.let { configuration ->
                    activeHelper?.applyRawDrawingConfiguration(configuration)
                }
            }
            attempt {
                if (activeHelper?.isRawDrawingCreated == true) activeHelper.closeRawDrawing()
            }
            firstFailure?.let { throw it }
        }
    }

    private fun applyConfiguration(currentValue: Any) {
        val current = currentValue as TouchHelper
        current
            .setStrokeStyle(configuration.brush.style.firmwareValue)
            .setStrokeColor(configuration.brush.color)
            .setStrokeWidth(configuration.brush.widthPx)
            .setBrushRawDrawingEnabled(true)
            .setEraserRawDrawingEnabled(false, configuration.eraser.style.firmwareValue)
            .enableSideBtnErase(configuration.eraser.sideButtonEnabled)
        when (configuration.regionMode) {
            RegionMode.MULTI_REGION -> current.setMultiRegionMode()
            RegionMode.SINGLE_REGION -> current.setSingleRegionMode()
        }
        if (configuration.limitRegions.isNotEmpty()) {
            current.setLimitRect(configuration.limitRegions.map(::Rect))
        }
        if (configuration.excludeRegions.isNotEmpty()) {
            current.setExcludeRect(configuration.excludeRegions.map(::Rect))
        }
        configuration.brush.dashPattern?.let { dash ->
            EpdController.setStrokeParameters(
                configuration.brush.style.firmwareValue,
                floatArrayOf(dash.onLengthPx, dash.offLengthPx, dash.phasePx),
            )
        }
        current.setRawDrawingEnabled(true, RawDrawingConfigurationPolicy.PRESERVE_CURRENT)
    }

    private fun surfaceChanged(expected: Long) = OnyxFailure.SurfaceChanged(
        "raw.commit",
        null,
        "Surface generation changed from $expected to $surfaceGeneration",
    )

    private suspend fun <T> runFirmwareOperation(operation: String, block: suspend () -> T): Result<T> {
        val started = System.nanoTime()
        return try {
            val result = block()
            OnyxDiagnostics.record(
                operation,
                FirmwareBackendKind.NATIVE_INPUT,
                FirmwareDiagnosticPhase.DISPATCH,
                FirmwareDiagnosticStatus.SUCCEEDED,
                started,
            )
            Result.success(result)
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (failure: Throwable) {
            val diagnostic = OnyxDiagnostics.record(
                operation,
                FirmwareBackendKind.NATIVE_INPUT,
                FirmwareDiagnosticPhase.DISPATCH,
                FirmwareDiagnosticStatus.FAILED,
                started,
                failure,
            )
            Result.failure(
                failure as? OnyxFailure ?: OnyxFailure.FirmwareRejected(
                    operation,
                    diagnostic.id,
                    failure.message ?: "Firmware operation failed",
                    failure,
                ),
            )
        }
    }

    private sealed interface Command {
        data object SurfaceCreated : Command
        data object SurfaceDestroyed : Command
        data class RawEvent(
            val point: InkPoint,
            val phase: PenPhase,
            val outsideLimitRegion: Boolean,
            val forced: Boolean,
        ) : Command

        class Open : RequestCommand<Unit>("raw.open")
        data class SetPaused(
            val reason: SuspensionReason,
            val paused: Boolean,
        ) : RequestCommand<Unit>("raw.${if (paused) "pause" else "resume"}")
        data class BindLifecycle(val owner: LifecycleOwner) :
            RequestCommand<Unit>("raw.lifecycle-bind")
        data class Commit(
            val bitmap: Bitmap,
            val destination: Rect,
            val dirtyRegion: Rect,
            val options: CommitOptions,
        ) : RequestCommand<CommitReceipt>("raw.commit")
        class Close : RequestCommand<Unit>("raw.close")
    }

    private sealed class RequestCommand<T>(val operation: String) : Command {
        val reply = CompletableDeferred<Result<T>>()
    }

    companion object {
        suspend fun open(
            surfaceView: SurfaceView,
            configuration: RawInkConfiguration = RawInkConfiguration(),
            leasePolicy: LeasePolicy = LeasePolicy.FAIL_FAST,
        ): Result<RawInkSession> {
            val owner = Any()
            if (!ProcessRawInkLease.acquire(owner, leasePolicy)) {
                return Result.failure(
                    OnyxFailure.LeaseUnavailable(
                        "raw.open",
                        null,
                        "Another RawInkSession owns the process-wide firmware overlay",
                    ),
                )
            }
            val dispatcher = Executors.newSingleThreadExecutor { runnable ->
                Thread(runnable, "onyx-raw-ink").apply { isDaemon = true }
            }.asCoroutineDispatcher()
            val session = try {
                RawInkSession(surfaceView, configuration, dispatcher) {
                    ProcessRawInkLease.release(owner)
                }
            } catch (failure: Throwable) {
                dispatcher.close()
                ProcessRawInkLease.release(owner)
                if (failure is CancellationException) throw failure
                return Result.failure(failure)
            }
            return try {
                val initialized = session.initialize()
                if (initialized.isFailure) {
                    withContext(NonCancellable) { session.closeAndAwait() }
                    Result.failure(requireNotNull(initialized.exceptionOrNull()))
                } else {
                    Result.success(session)
                }
            } catch (cancelled: CancellationException) {
                withContext(NonCancellable) { session.closeAndAwait() }
                throw cancelled
            } catch (failure: Throwable) {
                withContext(NonCancellable) { session.closeAndAwait() }
                Result.failure(failure)
            }
        }

        /** Opens a session and binds pause/resume/close to the supplied lifecycle. */
        suspend fun attach(
            surfaceView: SurfaceView,
            lifecycleOwner: LifecycleOwner,
            configuration: RawInkConfiguration = RawInkConfiguration(),
            leasePolicy: LeasePolicy = LeasePolicy.FAIL_FAST,
        ): Result<RawInkSession> {
            val session = open(surfaceView, configuration, leasePolicy).getOrElse {
                return Result.failure(it)
            }
            val bound = session.bindLifecycle(lifecycleOwner)
            if (bound.isFailure) {
                withContext(NonCancellable) { session.closeAndAwait() }
                return Result.failure(requireNotNull(bound.exceptionOrNull()))
            }
            return Result.success(session)
        }
    }

    private data class LifecycleBinding(
        val lifecycle: Lifecycle,
        val observer: DefaultLifecycleObserver,
    ) {
        fun add() = lifecycle.addObserver(observer)
        fun remove() = lifecycle.removeObserver(observer)
    }
}

internal suspend fun probeRawInput(
    surfaceView: SurfaceView,
    timeout: Duration,
): Result<RawInputProbe> {
    val session = RawInkSession.open(
        surfaceView = surfaceView,
        leasePolicy = LeasePolicy.FAIL_FAST,
    ).getOrElse { return Result.failure(it) }
    return try {
        session.awaitInputProbe(timeout)
    } finally {
        withContext(NonCancellable) { session.closeAndAwait() }
    }
}

suspend fun <T> withRawInkSession(
    surfaceView: SurfaceView,
    configuration: RawInkConfiguration = RawInkConfiguration(),
    leasePolicy: LeasePolicy = LeasePolicy.FAIL_FAST,
    block: suspend RawInkSession.() -> T,
): Result<T> {
    val session = RawInkSession.open(surfaceView, configuration, leasePolicy).getOrElse {
        return Result.failure(it)
    }
    var outcome: Result<T>? = null
    try {
        outcome = Result.success(session.block())
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (failure: Throwable) {
        outcome = Result.failure(failure)
    } finally {
        val closed = withContext(NonCancellable) { session.closeAndAwait() }
        if (outcome?.isSuccess == true && closed.isFailure) {
            outcome = Result.failure(requireNotNull(closed.exceptionOrNull()))
        }
    }
    return requireNotNull(outcome)
}

private fun List<InkPoint>.bounds(): RectF {
    val first = first()
    val bounds = RectF(first.xPx, first.yPx, first.xPx, first.yPx)
    drop(1).forEach { bounds.union(it.xPx, it.yPx) }
    return bounds
}

private fun noOpLegacyCallback() = object : RawInputCallback() {
    override fun onBeginRawDrawing(shortcut: Boolean, point: TouchPoint) = Unit
    override fun onEndRawDrawing(outLimitRegion: Boolean, point: TouchPoint) = Unit
    override fun onRawDrawingTouchPointMoveReceived(point: TouchPoint) = Unit
    override fun onRawDrawingTouchPointListReceived(pointList: TouchPointList) = Unit
    override fun onBeginRawErasing(shortcut: Boolean, point: TouchPoint) = Unit
    override fun onEndRawErasing(outLimitRegion: Boolean, point: TouchPoint) = Unit
    override fun onRawErasingTouchPointMoveReceived(point: TouchPoint) = Unit
    override fun onRawErasingTouchPointListReceived(pointList: TouchPointList) = Unit
}
