# Audit findings review — 2026-07-15

## Scope and decision rule

This review checked the supplied report against commit `f015ab0` and, where compatibility was in
question, against the pre-KTX/recovery implementation at `5bc248e` or the immediately preceding
native implementation. A claim is classified as:

- **Should fix** — reproducible, or a test/documentation gap that could conceal a real defect.
- **Reject** — inaccurate, outdated, or an intentional fixture/ABI behavior rather than a defect.
- **Requires user feature input** — a product choice with no technically dominant answer.

No finding in this report ultimately requires feature input. Compatibility-sensitive choices had a
clear answer in the recovered implementation: preserve lenient legacy calls unless doing so would
hide truncation, corrupt lifecycle ownership, or violate an explicit KTX contract.

## High findings

### 1. `RawInkSession.commit(WaitForPenUp)` swallows its typed timeout

**Decision: Should fix. Confirmed.** `withTimeout` raised `TimeoutCancellationException`, and the
preceding `CancellationException` catch rethrew it. The `OnyxFailure.TimedOut` conversion was
unreachable for an actual timeout.

**Resolution:** use `withTimeoutOrNull`. Its own deadline returns a typed `TimedOut` failure while
parent/job cancellation still propagates normally. This intentionally follows the existing
`awaitInputProbe` pattern and the `Result`-returning API contract.

### 2. Cancelling `RawInkSession.open()` leaks the lease and actor executor

**Decision: Should fix. Confirmed.** Cancellation while awaiting `initialize()` skipped both
`closeAndAwait()` and `ProcessRawInkLease.release`.

**Resolution:** all post-acquisition construction/initialization paths now clean up under
`NonCancellable`; constructor failure closes the dispatcher and releases the lease directly. The
actor loop also has a fatal boundary that releases firmware resources, the process lease, and every
pending request reply. This is lifecycle hardening for the new KTX API, not a legacy behavior
change.

### 3. Hidden-API exemptions are narrower than the reflection surface

**Decision: Should fix. Confirmed, and broader than the report's examples.** The old narrowed list
covered `android.onyx`, the exact descriptor `android.view.View;`, `ServiceManager`, and
`VMRuntime`. It did not cover `View$EINK_MODE`, `SystemProperties`, `DeviceController`,
`ActivityThread`, or `com.android.internal.*`. A repository-wide literal-class audit also found
hidden reflection in these package families:

- `android.app`, including `ActivityManagerNative`, `ActivityThread`, and `StatusBarManager`;
- `android.content`, including hidden package/user APIs;
- `android.database`, including `CursorWindow` internals;
- `android.hardware`, `android.net`, `android.os`, `android.telephony`;
- `android.view` and `android.webkit`;
- `com.android.internal`, `libcore.io`, `dalvik.system`, and vendor `android.onyx`.

**Resolution:** exemptions now use package prefixes for exactly those recovered reflection
families. In particular, `Landroid/view/` covers both `View` and nested descriptors such as
`View$EINK_MODE`; `Landroid/os/` covers both `ServiceManager` and `SystemProperties`.

**Deviation from the original AAR:** the original used the process-wide `"L"` exemption. The
recovered SDK deliberately remains narrower for diagnosability and process security, but the list
is no longer narrower than its audited implementation surface. A unit test pins the critical
prefixes and rejects accidental return to `"L"`.

### 5. `transactionCodes` is read while structurally mutated

**Decision: Should fix. Confirmed.** `info()`, `supports()`, and `requireCode()` read the mutable
`LinkedHashMap` outside the monitor used by discovery/reset.

**Resolution:** `info()` and `requireCode()` now use the initialization monitor, `info()` snapshots
one local Binder reference, and unused `supports()` was removed. The immutable
`FirmwareBackendInfo` copy is constructed while the map is locked.

## Medium findings

### Strictness regressions

1. **Invalid `TouchHelper` stroke widths — Should fix.** Confirmed half-applied state. Width is now
   sanitized to the recovered 3 px default before any renderer or snapshot mutation. This differs
   from the original pass-through behavior, but retains its non-throwing contract and prevents
   NaN/non-positive state.
2. **Destroy/render on an unknown native handle — Should fix.** Confirmed. Double destroy is again
   an idempotent no-op and render calls on an inactive handle return `null`, matching the native
   behavior before renderer hardening.
3. **Null/empty firmware regions clear state — Should fix.** Confirmed for `RawInputReader`.
   Null/empty limit and exclude lists are again no-ops, matching the original AAR. No new public
   `clear*` API was added; explicit clearing remains unavailable through this legacy surface.

### Raw-ink actor lifecycle

1. **Concurrent close can throw from `commands.send` — Should fix.** Confirmed. Public requests and
   close now use non-throwing `trySend` and return `InvalidState` if the command queue is closed.
2. **An unguarded handler can kill the actor — Should fix.** Confirmed. A top-level actor boundary
   performs non-cancellable best-effort firmware cleanup, fails the current and queued replies,
   closes stroke delivery, and releases the lease. Normal close also fails commands that raced
   behind the close command rather than abandoning their deferred replies.

### EPD refresh waiter

1. **One timeout permanently poisons the waiter — Should fix.** Confirmed. The permanent atomic
   poison and executor shutdown were removed.
2. **Concurrent waits consume their timeout in the executor queue — Should fix.** Confirmed. A
   coroutine mutex serializes submissions, so each started wait gets its own full execution
   budget. If a timed-out Binder call ignores interruption, later calls temporarily use estimated
   completion until that one worker returns; precise waits then resume automatically. This avoids
   both process-lifetime poison and unbounded abandoned waiter threads.

### Capabilities and Binder facade

1. **Every capability call retries an external exemption — Should fix.** Confirmed. Provider
   attempt/success is latched, successful external access is reported consistently, and backend
   rediscovery runs only after a successful one-time provider call.
2. **Capability probes block the caller dispatcher — Should fix.** Confirmed. Binder queries,
   native input setup, and renderer self-tests now run on `Dispatchers.Default`; only view update
   mode round trips switch to Main.
3. **Volatile Binder is read twice — Should fix.** Confirmed. Backend info/initialization use one
   local Binder reference under the monitor.

### Other medium findings

1. **Realtime-to-monotonic offset sampled once — Should fix.** Confirmed. When
   `EVIOCSCLOCKID(CLOCK_MONOTONIC)` is denied, the clock-domain offset is re-sampled for each event,
   preventing later wall-clock steps from being applied through a stale offset.
2. **`TouchHelper.observedConfiguration` is process-static — Should fix.** Confirmed. The snapshot
   and lock are now per helper instance, preventing view-relative regions from crossing owners.
3. **Validation suite can launch after `onDestroy` — Should fix.** Confirmed. The stable-surface
   callback is stored and cancelled, queued animation callbacks are removed, and the KTX harness
   cancels its complete coroutine scope.
4. **`buildRustAndroid` omits androidTest output — Should fix.** Confirmed. The task now declares
   both `src/main/jniLibs` and `src/androidTest/jniLibs` as outputs.
5. **Firmware Binder transport lacks behavioral tests — Should fix.** Confirmed. Robolectric Binder
   tests now cover integer and float replies, parcel underflow/invalid dimensions, rejected
   transactions, and invalidation after `RemoteException`.

## Low findings

1. **Renderer state errors are mislabeled native failures — Should fix.** `IllegalStateException`
   from begin/append/end/closed-state checks now maps to `OnyxFailure.InvalidState`.
2. **Degenerate raw pressure prevents Binder fallback — Should fix.** An unverified EVIOCGABS
   pressure result no longer masks a supported SurfaceFlinger pressure maximum.
3. **Provider install/probe TOCTOU — Should fix.** Provider installation and discovery start now
   share one monitor; a provider cannot be installed after a concurrent probe has committed to not
   using it.
4. **JNI callback exceptions remain pending — Should fix.** Native callback results are checked.
   The reader stops issuing JNI calls immediately after a Java callback failure and returns the
   pending exception to Java.
5. **Legacy renderer paths ignore allocation overflow — Should fix.** Legacy interactive and
   offline paths now throw the same `[ALLOCATION_LIMIT]` failure as handle-based V2 instead of
   returning truncated geometry. This is an intentional safety deviation from lenient legacy
   truncation.
6. **Two native exports are dead and bitmap return type is wrong — Reject.** The report conflates
   the test-only Notable 0.2.3 ABI (`com.onyx.android.sdk.pennative`) with the recovered SDK ABI
   (`com.onyx.android.sdk.pen`). The recovered Java contract intentionally has no declarations for
   these two symbols, while the original native export surface contains them and the recovery gate
   pins them. Removing or repurposing them would reduce native compatibility; no change was made.
7. **V2 listener is silent on application renderers — Should fix.** `AppTouchRender` now emits
   immutable V2 down/move/up events and device metadata from MotionEvent's monotonic clock. When a
   native `SFTouchRender` is also present, TouchHelper selects it exclusively to avoid duplicates.
   This is an additive improvement to the recovered V2 API; the original AAR had no V2 listener.
8. **`getActivitySafety` silently became nullable — Should fix documentation.** Runtime behavior is
   intentionally safer and internal callers already handle it. The source-contract deviation is
   now recorded in `docs/API_INCOMPATIBILITIES.md`.
9. **Recovered SDK harness performs heavy work on Main — Should fix.** Capability probing now
   offloads internally; native renderer batches and diagnostics file writes explicitly run on
   Default/IO.
10. **Lease probe leaks an unexpected successful second session — Should fix.** The unexpected
    session is now closed under `NonCancellable` after recording the observation.
11. **Reference `sdk-ink` adapter draws nothing — Should fix.** It now opens the reference
    `TouchHelper` raw-drawing overlay for the capture window and closes it during completion or
    harness teardown.
12. **Advanced snapshot validator raises `KeyError` — Should fix.** Advanced validation now skips
    pen/fast-mode pairs absent from a legacy-only reference. A host regression covers empty
    advanced coverage.
13. **Notable corpus writer has no assertions — Reject as a defect claim.** The named method is an
    oracle fixture generator and intentionally serializes exceptions. The same class separately
    asserts nonzero handles and non-null down/move/up results for supported pen types. Treating a
    corpus capture as a pass/fail conformance test would destroy diagnostic evidence.
14. **Activity location test compares `[0,0]` with `[0,0]` — Should fix.** The test now uses a view
    with explicit nonzero screen coordinates and verifies the exact fallback result.

## Nits and omitted gate

1. **Unused `FirmwareBinderBackend.supports()` — Should fix.** Removed.
2. **`SAVE_PEN_ATTACHED_FB` capability computed three times — Should fix.** Computed once and
   copied with fallback evidence.
3. **`InkContractsTest` misses invariants/boundaries — Should fix.** Added `maxPressure == 0`
   rejection and accepted normalized-pressure 0/1 boundary tests.
4. **`verifyKtxPublicSurface` not wired into `check` — Should fix.** Although the numbered finding
   was absent from the supplied body, the suggested-order section was correct. Root `check` now
   depends on the existing public-surface task. The JVM contract baseline was reviewed and updated
   for the additive application-renderer listener plus KTX internal implementation artifacts; the
   pre-existing two-argument capability-probe JVM entry point remains present for binary
   compatibility.

## Intentional deviations from the original implementation

- Invalid stroke widths are non-throwing but normalized to 3 px rather than propagated unchanged.
- Hidden-API access uses audited package prefixes rather than the original global `"L"` exemption.
- Legacy native renderer overflow is rejected rather than silently truncated.
- `ActivityUtil.getActivitySafety` returns nullable instead of relying on an eventual cast failure.
- V2 events are now available on application rendering paths, with MotionEvent-derived monotonic
  timestamps and source-thread callback semantics.
- KTX raw-ink ownership, capability discovery, typed failures, and EPD wait receipts are new APIs;
  their lifecycle/concurrency guarantees intentionally improve on the legacy SDK's lack of an
  equivalent contract.

All other compatibility-sensitive fixes above restore the observed legacy behavior (idempotent
destroy, inactive-handle null results, and null/empty region no-ops) or change only tests/build
metadata.
