# Native recovery

The pen AAR contains two source-built Rust libraries and no supplied native
binary.

## Pen touch reader

The raw reader implements all 11 `RawInputReader` JNI entry points:

- `/dev/input/event0` through `event15` discovery for Hanvon, Wacom, and
  `onyx_emp` devices;
- Linux `input_event` polling with an `eventfd` wakeup;
- draw, erase, side-button erase, move, up, pause, and sync states;
- limit/exclusion regions, sticky region mode, and stroke-width margins;
- pressure normalization and
  `onTouchPointReceived(FFIIIZZZIJ)V` callback dispatch.

## Neo-pen renderer

The neo renderer implements both Java generations:

- seven exported `NeoPenNative` functions for handle creation/destruction,
  down/move/up rendering, logging, and bitmap setup (five are declared
  `native` methods on the recovered Java class; `nativeSetBitmapColor` and
  `nativeSetLogLevel` exist to match the reference export surface);
- seven exported `NeoPenWrapper` functions for the legacy static lifecycle,
  point rendering, offline list rendering, and texture retrieval.

All nine configured pen types are implemented. Point pens return three-value
records, pencil returns five-value records, path pens return 12-value polygon
records, and texture pens return two-value positions with Android bitmaps.
Prediction data is separate from committed data and never advances stroke
state.

## Builds and contracts

`scripts/build-rust-android.sh` uses NDK API 21 linkers to build both crates for
`armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`. The checked-in export contracts
under `scripts/native-contracts` contain 11 touch-reader names and 14 neo-pen
names. `scripts/verify-recovery.sh` compares those contracts with every loose
and packaged library and rejects shared C++ runtime dependencies.

## Device differential

`scripts/device-pen-differential.sh` temporarily builds an instrumentation APK
with the untracked reference arm64 library, captures all nine pen behaviors,
rebuilds the Rust candidate, captures it through the same Java calls, compares
the results, and finally runs the full candidate instrumentation suite. A trap
always rebuilds the Rust candidate so the reference cannot remain in the
working output.

Instrumentation tests pin exact recovered values for types 1–3; the texture
types 4–5 are exact only under this optional hardware differential, whose
snapshot format records each bitmap's dimensions plus a pixel-content digest.
The independently implemented Wacom-style spline/brush pipeline for
types 6–9 is validated for matching record counts, buffering/prediction
semantics, bitmap behavior, finite values, and bounded geometry.
