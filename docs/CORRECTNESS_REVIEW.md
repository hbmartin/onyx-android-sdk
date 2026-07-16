# Correctness review — onyx-android-sdk, `d09becd..HEAD`

Scope: all changes since commit `d09becda036d67de2839cd374abf56def82497a7`
(58 commits, 674 files changed). Goal: correctness and absence of bugs in the SDK.

## How this was reviewed

The diff is large but concentrated. After filtering, **432 of the changed files are pure
decompiler-comment removal** (`// Decompiled by Procyon`, JADX / `$VF` markers) with zero code
change. The real review surface is **~108 shipping-source files (77 with actual logic changes)**
across `onyxsdk-base`, `onyxsdk-pen`, `onyxsdk-device`, the Rust native libraries, `onyxsdk-ktx`,
and `build-logic`.

The review was done holistically (net `d09becd..HEAD` diff, not commit-by-commit) using two
independent passes, each finding adversarially verified by a separate reviewer, plus manual
verification of the highest-risk files:

- **Pass 1** — subsystem reviewers with a *faithfulness-to-original* lens → 0 bugs.
- **Pass 2** — reviewers with a *divergence-from-original + absolute-correctness* lens and a lower
  reporting bar → 24 verified behavioral divergences: **5 material, 1 uncertain, 17 confirmed-safe,
  1 false positive.**
- Manual diff of `BitmapUtils`, `FileUtils`, `StrokeTransport`, both Rust libs, `SDMDevice`,
  `BaseDevice`, and `ReflectUtil` against their originals to corroborate.

## Bottom line

**No crash-class or wrong-result correctness bugs in the SDK's normal operation.** The
reconstruction is high-quality — the decompiler-artifact repairs (dead `new Canvas()` / `new Paint()`
allocations, restored paint flags, corrected canvas targets, try-with-resources) are correct, and the
new code (stroke transport, versioned renderer) is sound. What remains is a set of **deliberate
behavioral divergences** from the pre-existing recovery. One — the charcoal-pen rendering change — is
the only item that could be an unintended regression, and it can only be settled on a physical device.

## Items that warrant attention

### 1. Charcoal pen rendering was materially changed — verify on hardware
`onyxsdk-pen/native/onyx-neo-pen/src/lib.rs:758,953` — **highest attention**

Commit `56a085c` ("Fix pen recovery and stroke transport regressions") rewrote charcoal (pen type 4)
and charcoal-v2 (type 5) stamping:

- Removed the reverse-engineered per-pixel mask textures (`CHARCOAL_*_ROWS`) and now fills each stamp
  as a **solid rectangle at alpha 180** instead of a textured grain at alpha 255.
- Flipped the charcoal-v2 final-stamp anchor from the **stroke origin** (`history.first()`) to the
  **last move point** (`history.last()`).
- **Deleted** the tests that pinned the old behavior (rather than updating them), and removed a code
  comment stating the origin-anchoring matched "reference-device differential output."

Because the prior behavior was reverse-engineered from device output and its tests were removed (not
re-asserted), this reads as a *simplification*, not a validated fix. If a physical BOOX/Onyx device
renders textured charcoal, this is a visual regression; if it renders solid strokes, it is fine.
**Recommendation: validate charcoal / charcoal-v2 strokes against a reference device before shipping.**

### 2. Remote metadata service: listener no longer called on bind failure
`onyxsdk-base/src/main/java/com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.java:57` — medium

`executeRemoteService()` now early-returns when `bindService()` returns `false`. The original ignored
that return and always proceeded (blocking in `waitUntilConnected`, then invoking the listener with a
null service). The new path is cleaner and is part of the intended "reliable cleanup" fix, **but a
caller that assumes its `OnCallRemoteServiceApiListener` is always invoked will now get no callback
when binding fails** (e.g. `com.onyx.kreader` not installed). Confirm intended; if callers depend on
always being called back, invoke the listener with a failure/null on the early-return path.

### 3. `roundCornerBitmap` corners are now anti-aliased
`onyxsdk-base/src/main/java/com/onyx/android/sdk/utils/BitmapUtils.java:968` — low

The reconstruction gave this method's `Paint` an `ANTI_ALIAS_FLAG` the original did not have (the
original bytecode constructed a flag-less `Paint`; sibling methods like `buildBitmapFromText` correctly
preserved their `new Paint(1)`). Purely cosmetic — smoother rounded corners — but technically unfaithful
and inconsistent with the sibling reconstructions. Restore `new Paint()` for byte-faithful output;
otherwise it is a harmless improvement.

### 4. `BaseDevice` region setters now NPE on a null array
`onyxsdk-device/src/main/java/com/onyx/android/sdk/device/BaseDevice.java:515,523` — low

`setScreenHandWritingRegionLimit/Exclude(View, Rect[])` were empty null-safe no-ops at base; they now
call `Objects.requireNonNull(convertRectArrayToIntArray(regions))`, which throws NPE if `regions` is
null. This only affects the `BaseDevice` / `IMX7Device` path (the 5 SoC subclasses already NPE'd on
null). It is a net improvement (base silently ignored valid `Rect[]` input entirely), but the null case
regressed from no-op to crash. Add an early `if (regions == null) return;`.

### 5. (Uncertain) device-validation reference JNI libs may not bundle
`build-logic/src/main/kotlin/OnyxDeviceValidationPlugin.kt:76` — build-tooling only

The `reference` variant's native dirs are registered by mutating `jniLibs.directories` inside
`afterEvaluate`. On AGP 9.2.x that collection can be a read-only / already-snapshotted view, so the
reference variant may assemble **without** the reference `.so` files. This is net-new build tooling (no
original to diverge from) and affects only the internal validation app, not the shipped SDK. Confirm
the reference variant actually contains the native libs; prefer `jniLibs.srcDirs(...)` registered during
`apply()`.

## Verified safe (the other 18 divergences)

All real behavior changes below were confirmed to be intended fixes, hardening, or exact equivalents —
no action needed:

- **Resource-safety hardening**: `UriUtils` / `UriKt` now close cursors and `ParcelFileDescriptor`s and
  guard `moveToFirst()` / column indices; `NeoPenUtils.computeStrokePoints` destroys the native pen in
  `finally` (fixes a leak); `TTFFont.parse` uses try-with-resources.
- **Android-version correctness**: `RxAlarm` adds `FLAG_IMMUTABLE` (required on API 31+);
  `RxBroadcastChangeObservable` / `ExceptionsKt` gate newer APIs behind `SDK_INT` checks.
- **Faithful-recovery quirks**: `FileUtils.readContentOfFile(String)` now joins lines with **no
  separator**, restoring the true original single-append behavior (the base commit had temporarily
  stubbed it to delegate to the `File` overload). Side effect: the `String` and `File` overloads now
  legitimately behave differently (no-separator vs `line.separator`) — surprising, but matches the
  original SDK.
- **Intended API removals**: `FirmwareUpdateRequest.execute()` throws `UnsupportedOperationException`
  (networking deliberately removed; documented in `docs/API_INCOMPATIBILITIES.md`).
- **Math/format**: `MathUtils.normalizeAngleTo36` true-modulo wrapping, `MathUtils.isEmptyMatrix` using
  `Matrix.isIdentity`, `Size.ratioPageSize` float division with a `maxOf(1.0f, …)` floor — all
  improvements over the originals for in-range inputs.
- **Rust**: touch timestamps changed µs→ms **consistently** end-to-end (native `now_ms()` ↔ Java
  `onTouchPointReceived(...J)`); config sanitization and the JNI 0.22 migration are correct.

One false positive was correctly rejected: the `getMethodSafely → getStaticMethodSafely` change across
all SoC devices is valid hardening (those framework methods are invoked with a `null` receiver, i.e.
statically), and `getStaticMethodSafely` simply validates that assumption.

## Confidence

Two orthogonal review lenses plus manual verification converged: the shipping SDK code is correct. The
only genuinely open question is **item 1 (charcoal rendering)**, a fidelity decision requiring a physical
device to settle. Everything else is either an intended improvement or a trivial hardening opportunity
(item 4).
