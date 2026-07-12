# Recovery notes

## Source-only policy

Supplied JARs, AARs, and native libraries are analysis references only. They
are untracked, are not Gradle dependencies, and are not copied into release
artifacts. Android Gradle Plugin compiles every class from `src/main`; both pen
libraries are cross-compiled from the checked-in Rust crates.

Raw JADX/CFR/Vineflower output and the earlier recovery notes remain available
on the `backup` branch. Production source and executable validation are the
authority on this branch.

## Production recoveries

The base and device recoveries live directly in their real SDK classes:

- `FileUtils.readableFileSize(long)` follows the verified 1024-based bytecode;
- `RxUtils$d.run()` preserves its caught-`Exception`, escaping-`Error`, and
  exactly-once worker disposal behavior;
- all seven device reflection wrappers use the verified static `Method` field,
  null receiver, empty argument array, and outer `catch (Exception)`.

No recovery-only surrogate helper classes remain.

## Pen Java/Kotlin surface

The production pen module contains all 129 reference class files: 57 from the
legacy 1.5.4 JAR and 72 from `onyxsdk-pen-native-classes.jar`. The build also
includes the required geometry types recovered from the release SDK. A `javap`
descriptor audit proves that all 118 public reference classes have identical
public/protected declarations, fields, constructors, and methods.

The legacy `PenUtils.getPointDoubleArray(TouchPoint, float)` allocation was
corrected from five to seven elements so its existing seven-value JNI record
can execute instead of throwing `ArrayIndexOutOfBoundsException`.

## Pen native surface

- `onyx-pen-touch-reader` exports the 11 `RawInputReader` JNI methods.
- `onyx-neo-pen` exports seven `NeoPenNative` methods and seven legacy
  `NeoPenWrapper` methods.
- Both crates build for four Android ABIs without a shared C++ runtime.
- Export lists in `scripts/native-contracts` are checked against loose binaries
  and the packaged release AAR.

The new pen runtime owns state by 64-bit handle, validates pen types, returns
non-null empty ink objects where the reference does, safely handles stale
handles, produces texture bitmaps, and resets stroke state after pen-up. The
legacy static wrapper has its own lifecycle and offline list renderer.

## Validation evidence

- Host Rust tests cover all nine encodings, lifecycle, prediction isolation,
  raw input states, eraser selection, regions, and pressure normalization.
- Android instrumentation runs every new pen type, exact recovered values for
  types 1–3, texture and polygon encodings, invalid-handle safety, the legacy
  wrapper, and raw-reader JNI configuration/lifecycle calls.
- The device differential runs the original and Rust `libneo_pen.so` in the
  same APK on BOOX hardware.
- `scripts/verify-recovery.sh` audits source-only packaging, class coverage,
  exports, ABI coverage, and native dependencies.
