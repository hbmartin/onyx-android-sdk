# Recovery notes

## Source-only policy

Supplied JARs, AARs, and native libraries are analysis references only. They
are untracked, are not Gradle dependencies, and are not copied into release
artifacts. Android Gradle Plugin compiles every class from `src/main`; both pen
libraries are cross-compiled from the checked-in Rust crates.

Raw generated output and the earlier recovery notes remain available on the
`backup` branch. Production source and executable validation are the authority
on this branch.

## Production recoveries

The base and device recoveries live directly in their real SDK classes:

- `FileUtils.readableFileSize(long)` follows the verified 1024-based bytecode;
- `RxUtils$d.run()` preserves its caught-`Exception`, escaping-`Error`, and
  exactly-once worker disposal behavior;
- all seven device reflection wrappers use the verified static `Method` field,
  null receiver, empty argument array, and outer `catch (Exception)`.

No recovery-only surrogate helper classes remain.

## Base JVM surface repairs

A descriptor-level audit (`device-validation/classify_api_differences.py`)
compared every rebuilt base class — including nested classes — against the
reference bytecode across four independent layers: member (name, descriptor)
sets with access flags, generic `Signature` attributes, `kotlin.Metadata`
annotations, and member annotation sets. The 75 textual "changed signature"
classes reported by the earlier `javap -public` diff decomposed into 42
binary-breaking classes, 35 extra-surface classes, 2 generic-signature
classes, 10 metadata-only classes, and 3 synthetic-accessor artifacts, all of
which were repaired:

- 45 missing Kotlin `$default` bridges (including data-class `copy$default`
  and mask-applied synthetic constructors) re-created with mask bits and
  default values taken from the reference disassembly;
- companion fields renamed `INSTANCE` → `Companion` on five classes, plus the
  missing `RxScroller$Companion` class and two synthetic marker constructors
  restored to `public`;
- 40 file-facade classes given the reference's private constructor instead of
  an implicit public one; lost `final`/visibility flags and `WhenMappings`
  class flags restored; obfuscated members re-narrowed to reference
  visibility; synthetic `access$` accessors re-added;
- wildcard generic signatures corrected (`Collection<? extends Object>` etc.)
  and every diverging `kotlin.Metadata` annotation replaced with the exact
  reference values decoded from the class-file constant pool.

The repaired surface is pinned — without any reference JAR — by
`onyxsdk-base`'s `RecoveredApiSurfaceRegressionTest`, generated from the
pre-repair audit (`device-validation/gen_regression_test.py` with
`device-validation/base-prerepair-classified.json`).

### Accepted javac-inexpressible residuals

Four reference facts are Kotlin-compiler-only shapes that Java source cannot
produce; the audit still reports them and they are excluded from the pinned
surface:

- `ElementCounter.get/remove` keep `Object` parameters (declaring `T` is a
  JLS erasure clash with `HashMap`), so their `(TT;)Ljava/lang/Integer;`
  signatures and the `put(Object,Object)` bridge are absent — harmless at the
  JVM level because resolution falls through to `HashMap`;
- `RxBaseBenchmarkRequest.execute()` returns `kotlin.Unit` instead of the
  reference's `void`-plus-bridge pair; callers linked against the base class's
  `execute()Ljava/lang/Object;` are unaffected;
- `CalendarRemoteServiceConnection.asInterface` and six
  `SimpleBitmapReferenceLruCache` overrides are `public` where the reference
  splits protected-specialized/public-bridge — a binary-compatible widening
  required for javac to emit the reference's public erased bridges.

## Pen Java/Kotlin surface

The production pen module contains all 129 reference class files: 57 from the
legacy 1.5.4 JAR and 72 from `onyxsdk-pen-native-classes.jar`. The build also
includes the required geometry types recovered from the release SDK. A `javap`
descriptor audit proves that all 118 public reference classes have identical
public/protected declarations, fields, constructors, and methods.

The legacy `PenUtils.getPointDoubleArray(TouchPoint, float)` allocation was
corrected from five to seven elements so its existing seven-value JNI record
can execute instead of throwing `ArrayIndexOutOfBoundsException`.

`PenBrushInk` is the one genuine Kotlin source file (its `UByte` accessors
carry JVM-mangled names Java cannot declare), so its `kotlin.Metadata` is
whatever the current Kotlin compiler emits rather than the reference bytes —
a non-breaking recompilation residual the classified audit reports. Every
Java-transcribed `@Metadata` annotation, including `NeoPenRender$Companion`,
matches the reference exactly.

### Recovery extensions

Three classes exist in the rebuilt pen AAR but in neither reference JAR:

- `com.onyx.android.sdk.geometry.data.BoxInfo` and
  `com.onyx.android.sdk.geometry.data.TouchData` are minimal public stand-ins
  for a separate Onyx geometry artifact that was not supplied; the reference
  `NeoPenRender.onTouchData(TouchData)` signature requires them.
- `com.onyx.android.sdk.pen.PenBrushInkAccess` is a package-private reflection
  bridge to Kotlin's JVM-mangled unsigned-byte accessors and is not part of
  the public surface.

### Faithful-by-design behaviors

Several recovered behaviors look like bugs but reproduce the reference SDK
exactly and must not be "fixed":

- `DeviceUtils.detectInputDevicePath()` falls back to `/dev/input/event1`
  whenever `/proc/bus/input/devices` is unreadable (the common non-root case),
  even though the digitizer may live elsewhere (`event5` on a NoteAir4C). The
  reference build returns the same wrong path; the pen module's native reader
  performs its own `event0`–`event15` discovery and is unaffected. Consumers
  needing the real stylus node should resolve it themselves (the
  device-validation harness pins `event5`).
- `RawInputReader.pause()` never force-releases a stroke in progress: the
  native flush fires only for pen states below 2 and the Java surface only
  ever sets state 4, so a stroke silently continues across pause/resume.
- The obfuscated `touch/d` and `touch/f`–`touch/n` classes are orphaned
  synthetic bridges: their original call sites were compiler-generated and are
  unrecoverable, so their callback pairings are conjecture. They are kept for
  class-surface completeness, as is `RawInputReader.B()`, which exists solely
  so `touch/g.java` compiles.

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
- The device differential runs the original and Rust neo-pen libraries in the
  same APK on BOOX hardware.
- `scripts/verify-recovery.sh` audits source-only packaging, class coverage,
  exports, ABI coverage, and native dependencies.
