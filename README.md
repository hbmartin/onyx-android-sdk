# Onyx SDK recovered source

This repository is a source-only reconstruction of three Onyx/BOOX SDK
packages. It contains no tracked original SDK JAR, AAR, or native library.

| Module | Recovered version | Production result |
|---|---:|---|
| `onyxsdk-base` | 1.8.5 | source-built Android library |
| `onyxsdk-device` | 1.3.5 | source-built Android library |
| `onyxsdk-pen` | 1.5.4 | complete 129-class pen surface plus two Rust JNI libraries |

The pen module includes both generations of the Java API: the 57 classes from
the 1.5.4 SDK and the 72 classes supplied with the newer native pen API. Its
public/protected JVM signatures are checked against all 118 public reference
classes. The base module's surface is additionally verified by a classified
descriptor/flags/signature/metadata audit against its reference JAR and
pinned by an in-tree regression test (see RECOVERY_NOTES.md). The references
are analysis inputs only and are never Gradle inputs, tracked files, or AAR
contents.

## Pen native implementation

`onyxsdk-pen` builds two native libraries entirely from Rust:

- `libonyx_pen_touch_reader.so` implements the 11 `RawInputReader` JNI calls,
  Linux input discovery/polling, pen and eraser states, pressure processing,
  and region filtering;
- `libneo_pen.so` exports the seven `NeoPenNative` functions — five are
  declared `native` methods on the recovered class; `nativeSetBitmapColor` and
  `nativeSetLogLevel` are exported only to match the reference library's
  surface — and the seven legacy `NeoPenWrapper` calls, including all nine pen
  types, prediction, texture bitmaps, handle lifecycle, and the legacy static
  API.

Both libraries are built for `armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`.
They do not depend on `libc++_shared.so`.

The BOOX device differential harness executes the supplied reference and the
Rust library through the same Java API. Instrumentation tests assert exact
recovered values for pen types 1–3; exactness for the texture types 4–5 is
checked only by the optional on-device differential gate, whose snapshot
comparison covers stamp positions plus bitmap dimensions and a pixel-content
digest. Types 6–9 use an independent Wacom-style spline/brush implementation
and are checked for the same buffering, prediction, record encoding, bitmap
behavior, and bounded output geometry.

## Build and test

Prerequisites are JDK 17+, Rust stable, Android SDK platform 35, and Android NDK
28.2.13676358. Set `ANDROID_HOME` or `ANDROID_SDK_ROOT` when needed.

```bash
./gradlew clean :check assembleRecovered
```

That gate compiles all production Java/Kotlin, tests the recovery behaviors,
runs Rust tests and Clippy, builds eight native binaries, assembles the three
release AARs, and verifies all JNI exports and packaged native dependencies.
It deliberately runs each module's unit tests in a single variant (the Java
bytecode is variant-identical) and leaves instrumentation and differential
checks to the hardware gates below.

With a connected BOOX device and the untracked analysis reference, run the
additional differential gate:

```bash
scripts/device-pen-differential.sh /path/to/reference/libneo_pen.so DEVICE_SERIAL
```

To repeat the JVM API comparison against the two untracked reference JARs:

```bash
scripts/verify-pen-api.py \
  --old-reference /path/to/onyxsdk-pen-1.5.4/classes.jar \
  --native-reference /path/to/onyxsdk-pen-native-classes.jar \
  --candidate onyxsdk-pen/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar
```

See [RECOVERY_NOTES.md](RECOVERY_NOTES.md),
[docs/VALIDATION.md](docs/VALIDATION.md), and
[docs/NATIVE_RECOVERY.md](docs/NATIVE_RECOVERY.md).
