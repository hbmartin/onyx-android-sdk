# Onyx SDK Kotlin façade

`onyxsdk-ktx` is the recommended application-facing API for display refresh,
firmware capability discovery, raw stylus ownership, and native ink rendering.
It is one artifact with package boundaries (`capabilities`, `diagnostics`,
`display`, `ink`, `model`, and `render`) that can become modules later without
changing the concepts exposed to applications.

The façade returns Kotlin `Result<T>`. Failures are `OnyxFailure` subclasses
and contain the operation plus a diagnostic ID when firmware dispatch began.
Cancellation is never converted to `Result.failure`: suspend functions rethrow
`CancellationException`.

## Setup

```kotlin
dependencies {
    implementation("io.github.hbmartin.onyx:onyxsdk-ktx:1.0.0")
}
```

Coroutines Core remains an API dependency because flows are public.
Coroutines Android is internal. The recovered base and pen libraries are
implementation dependencies: no EventBus, RxJava, Fragment, Data Binding, or
legacy Onyx type is part of a KTX public signature.

KTX rendering calls only the modern `com.onyx.android.sdk.pennative` facade and
`libneopen_jni.so`. The separately packaged `libneo_pen.so` is a compatibility
alias for applications that still ship an older Onyx Java facade; KTX never
loads or references that facade.

## Capabilities and diagnostics

```kotlin
val capabilities = OnyxSdk.capabilities(
    surfaceView = drawingSurface,
    probeMode = CapabilityProbeMode.ACTIVE_REVERSIBLE,
).getOrElse { failure ->
    val onyx = failure as? OnyxFailure
    logger.error("${onyx?.operation}: ${failure.message}")
    return
}

val awaitSupport = capabilities.display.awaitUpdate
when (awaitSupport.support) {
    CapabilitySupport.SUPPORTED -> Unit
    CapabilitySupport.UNSUPPORTED -> disableWaitUi()
    CapabilitySupport.UNVERIFIED -> showUnverifiedWarning(awaitSupport.evidence)
}
```

`PASSIVE` performs safe queries and resolution only. It never refreshes or
changes a view. `ACTIVE_REVERSIBLE` requires an owned `SurfaceView`; it
round-trips view-local settings and restores them. A method or transaction
constant being present is only `UNVERIFIED`. `SUPPORTED` requires safe-query,
round-trip, Binder, native self-test, or guided evidence.

`OnyxSdk.diagnostics.events` is a bounded live stream. `snapshot()` returns the
last 256 records. Failures are emitted by default; set
`OnyxDiagnostics.level = DiagnosticsLevel.VERBOSE` while probing to observe
successful calls too. Records include backend, phase, elapsed time, thread,
status, exception summary, and firmware identity. They never contain the ADB
serial.

The SDK first attempts its narrow VMRuntime exemptions for the Onyx, View,
ServiceManager, and VMRuntime members it uses. It deliberately has no hard
HiddenApiBypass dependency. An application that already owns another strategy
may install it once, before discovery:

```kotlin
OnyxSdk.installHiddenApiExemptionProvider {
    applicationHiddenApiBootstrap.enableOnlyRequiredPrefixes()
}
```

Failure is reported as `HiddenApiStatus.ACCESS_DENIED` and capability evidence;
it is not converted to a fabricated `false` or `null` result.

## Refresh and thread guarantees

```kotlin
val receipt = view.refreshAndAwait(
    mode = EpdUpdateMode.GU,
    scope = RefreshScope.Region(Rect(0, 0, view.width, view.height)),
    timeout = 2.seconds,
).getOrThrow()
```

Suspend APIs are callable from any dispatcher. View and surface dispatch moves
to `Dispatchers.Main.immediate`; the blocking firmware wait uses one dedicated
worker. A timed-out wait trips a process circuit breaker, stops that backend,
and uses a waveform-dependent delay for later requests. Receipts distinguish
`FIRMWARE_WAIT_RETURNED`, `FIRMWARE_DISPATCHED`, and `ESTIMATED_DELAY`.
Synchronous strict Java operations enforce their `@MainThread` or
`@WorkerThread` contract at runtime. Legacy Java methods retain their original
behavior.

## Raw ink session

```kotlin
val opened = RawInkSession.open(
    surfaceView = drawingSurface,
    configuration = RawInkConfiguration(
        brush = BrushConfiguration(style = StrokeStyle.FOUNTAIN, widthPx = 3f),
        eraser = EraserConfiguration(widthPx = 24f),
        regionMode = RegionMode.MULTI_REGION,
    ),
    leasePolicy = LeasePolicy.FAIL_FAST,
)

val session = opened.getOrElse { return }
try {
    session.completedStrokes.collect { stroke -> persist(stroke) }
} finally {
    session.closeAndAwait().getOrThrow()
}
```

Only one session may own the firmware overlay in a process. `FAIL_FAST`
returns `LeaseUnavailable`; `WAIT` is cancellable. There is no forced takeover.
Open, input callbacks, pause/resume, surface events, commits, and close use one
serialized queue. A destroyed surface pauses the session. A new
`SurfaceHolder` generation is fully configured before input resumes.

`state` and `preview` are state flows. Preview is latest-wins and each point's
sequence exposes coalescing gaps. `completedStrokes` is a lossless,
single-consumer flow. If a stroke exceeds `maxPointsPerStroke`, input pauses
and the session enters `Failed(EventOverflow)`; completed strokes are never
silently discarded.

`InkPoint` coordinates are view-local physical pixels. It contains raw pressure,
normalized pressure, the axis maximum used for normalization, raw tilt axes,
a monotonic nanosecond timestamp derived from the kernel event timestamp, a
sequence number, and an explicit pen/side-eraser/tail-eraser tool. The raw Java
v2 callback exposes the same immutable event. Legacy callbacks are driven from
that event for compatibility.

The session uses `PRESERVE_CURRENT` toggles and reapplies its full brush,
eraser, region, and side-button configuration after every restart. The Java
`captureRawDrawingConfiguration` / `applyRawDrawingConfiguration` primitives
are process-local SDK-observed snapshots because current firmware exposes no
complete configuration query. Their evidence never claims a firmware
round-trip.

Prefer structured ownership when the result itself should include cleanup:

```kotlin
val outcome = withRawInkSession(drawingSurface) {
    // draw or collect strokes
    "finished"
}
```

## Best-effort commit

```kotlin
val receipt = session.commit(
    bitmap = pageBitmap,
    destination = Rect(0, 0, drawingSurface.width, drawingSurface.height),
    dirtyRegion = changedBounds,
    options = CommitOptions(
        activeStrokePolicy = ActiveStrokePolicy.WaitForPenUp(2.seconds),
        updateMode = EpdUpdateMode.HANDWRITING_REPAINT,
    ),
).getOrThrow()
```

Commit disables input, drains the serialized callback queue, snapshots the
surface generation, posts the application bitmap under handwriting-repaint
mode, reconciles the firmware overlay, awaits EPD completion where verified,
restores the prior view mode, reapplies session configuration, then enables
rendering before input. Cleanup runs non-cancellably. Surface loss suspends the
session; an unrecoverable cleanup failure fails it.

This is not a hardware transaction. `CommitReceipt` reports the surface post,
overlay synchronization, refresh evidence, configuration restoration, and
warnings, and always includes `BEST_EFFORT_NOT_HARDWARE_ATOMIC`. The strict
`savePenAttachedFramebufferOrThrow` Java primitive is experimental and is not
selected automatically. A firmware profile must pass at least 30 repeated
draw/erase/commit A/B cycles before it can be considered for promotion.

## Renderers

```kotlin
InkRenderer.create(PenKind.FOUNTAIN_V2, BrushConfiguration(widthPx = 4f))
    .getOrThrow()
    .use { renderer ->
        renderer.begin(points.first()).getOrThrow().draw(canvas)
        renderer.append(points.drop(1).dropLast(1), prediction).getOrThrow().draw(canvas)
        renderer.end(points.last()).getOrThrow().draw(canvas)
    }
```

`render(points)` performs the same down/move/up state machine on an isolated
native handle. Input points are immutable and already pressure-normalized.
`RenderFrame` keeps committed and predicted layers separate, reports copied
bounds, and draws on a Canvas without exposing legacy pen classes.

`BRUSH`, `MARKER`, `CHARCOAL_V2`, and `BRUSH_SIGN` require
`@OptIn(ExperimentalOnyxRendererApi::class)`. Capability results for every
renderer still distinguish verified from unverified. Native input is checked
for finite values, record arity, bounded batches/allocations, and valid handle
state. `BrushConfiguration` maps the full modern pressure, velocity, tilt,
brush-shape, scale, start-limit, and end-thinning configuration to native code.
Rust JNI builds with unwind support; renderer exports catch panics and
turn them into typed native-renderer failures. Always close a renderer.

## Firmware inspection

The inspection command is read-only and deliberately cannot scan transaction
codes:

```bash
python3 scripts/inspect_onyx_firmware.py \
  --serial DEVICE_SERIAL \
  --output build/firmware-inspection/my-device
```

It records an allowlisted property set, pulls only `framework.jar` and
`services.jar` into ignored output, hashes them, decompiles only
`android.onyx.ViewUpdateHelper`, and writes its declared integer symbols and
public static methods to `manifest.json`. It performs no Binder transaction,
does not record the device serial, and changes no setting or partition. Never
commit the pulled jars, APKs, native libraries, or an unsanitized manifest.

## Migration

Replace legacy boolean-returning EPD calls with `refreshAndAwait` and inspect
receipt evidence. Replace raw callback ownership with one `RawInkSession`; use
the preview flow for UI and the completed-stroke flow for durable work. Replace
direct `NeoPen` instances with `InkRenderer` and close them deterministically.
Keep legacy APIs only where Java ABI compatibility is required.

The recovered validation flavor includes `sdk-automated` (capabilities,
refresh, lease contention, commit, diagnostics, and all renderers) and
`sdk-ink` (operator drawing plus surface lifecycle). The reference flavor uses
the same harness contract with a legacy adapter.
