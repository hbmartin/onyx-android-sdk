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
classes. The references are analysis inputs only and are never Gradle inputs,
tracked files, or AAR contents.

## Pen native implementation

`onyxsdk-pen` builds two native libraries entirely from Rust:

- `libonyx_pen_touch_reader.so` implements the 11 `RawInputReader` JNI calls,
  Linux input discovery/polling, pen and eraser states, pressure processing,
  and region filtering;
- `libneo_pen.so` implements the seven newer `NeoPenNative` calls and the
  seven legacy `NeoPenWrapper` calls, including all nine pen types, prediction,
  texture bitmaps, handle lifecycle, and the legacy static API.

Both libraries are built for `armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`.
They do not depend on `libc++_shared.so`.

The BOOX device differential harness executes the supplied reference and the
Rust library through the same Java API. Pen types 1–5 match the recovered
reference snapshot exactly. Types 6–9 use an independent Wacom-style
spline/brush implementation and are checked for the same buffering, prediction,
record encoding, bitmap behavior, and bounded output geometry.

## Build and test

Prerequisites are JDK 17+, Rust stable, Android SDK platform 35, and Android NDK
28.2.13676358. Set `ANDROID_HOME` or `ANDROID_SDK_ROOT` when needed.

```bash
./gradlew clean :check assembleRecovered
```

That gate compiles all production Java/Kotlin, tests the recovery behaviors,
runs Rust tests and Clippy, builds eight native binaries, assembles the three
release AARs, and verifies all JNI exports and packaged native dependencies.

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
