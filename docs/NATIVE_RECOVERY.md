# Native recovery

`onyxsdk-pen/native/onyx-pen-touch-reader` is a Rust behavioral reconstruction
of the pen input driver. The repository contains no original native binary.

The recovery implements:

- `/dev/input/event0` through `event15` device discovery;
- Hanvon, Wacom, and `onyx_emp` device-name matching;
- Linux input-event polling and `eventfd` wakeup;
- pen, rubber, side-button erase, draw, move, up, pause, and sync states;
- limit and exclusion regions with stroke-width margins;
- sticky region mode and pressure-curve normalization;
- `onTouchPointReceived(FFIIIZZZIJ)V` callback dispatch.

## Source contract

The eleven JNI names are declared in the checked-in Java `NativeContract`,
implemented by Rust exports, and consumed by the source-native
`RawInputReader`. Validation checks that all three layers agree for every ABI.
No comparison or packaging step reads an original `.so`.

## Android builds

`scripts/build-rust-android.sh` uses API 21 NDK clang linkers and emits standard
Android `jniLibs/<abi>/libonyx_pen_touch_reader.so` outputs for:

- `armeabi-v7a`;
- `arm64-v8a`;
- `x86`;
- `x86_64`.

The driver is pure Rust/JNI and does not link to `libc++_shared.so`. The Android
library build packages only the generated Rust library. Host tests validate
the state machine and ABI surface; BOOX hardware remains necessary to validate
real vendor input devices and firmware behavior.
