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

The existing fountain renderer remains the default. An additive, opt-in
reference-compatible renderer consumes smoothing, velocity, pressure, tilt,
direction, and brush configuration while preserving both Java API generations
and their JNI/record contracts. The device module also exposes advanced stroke
configuration, capability checks, and a firmware-configured Binder transport
with stroke-scoped fallback and failure handling. See
[`docs/STROKE_COMPATIBILITY.md`](docs/STROKE_COMPATIBILITY.md).

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
scripts/device-pen-differential.sh /path/to/reference/neo-pen-library DEVICE_SERIAL
```

To repeat the JVM API comparison against the two untracked reference JARs:

```bash
scripts/verify-pen-api.py \
  --old-reference /path/to/onyxsdk-pen-1.5.4/classes.jar \
  --native-reference /path/to/reference-artifacts/onyxsdk-pen-native-classes.jar \
  --candidate onyxsdk-pen/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar
```

See [RECOVERY_NOTES.md](RECOVERY_NOTES.md),
[docs/VALIDATION.md](docs/VALIDATION.md), and
[docs/NATIVE_RECOVERY.md](docs/NATIVE_RECOVERY.md). The constraints and gates
for the planned Java-to-Kotlin conversion are documented in
[docs/KOTLIN_MIGRATION.md](docs/KOTLIN_MIGRATION.md).

## Demo and test app

`device-validation/app` is an installable comparison and demonstration app for
a BOOX device. It has `reference` and `recovered` flavors with the same
application ID, so the runner installs them sequentially and exercises the
same firmware paths. Keep the original analysis inputs in a separate directory
and pass that directory explicitly.

The reference directory must contain these inputs:

```text
reference-artifacts/
├── onyxsdk-base-1.8.5/classes.jar
├── onyxsdk-device-1.3.5/classes.jar
├── onyxsdk-pen-1.5.4/
│   ├── classes.jar
│   └── jni/                 # original ABI-specific native libraries
└── onyxsdk-pen-native-classes.jar
```

Set `ANDROID_HOME` or `ANDROID_SDK_ROOT`, authorize the device with `adb`, and
run a guided drawing demo from the repository root:

```bash
device-validation/run-comparison.sh \
  --artifacts-root /path/to/reference-artifacts \
  --serial DEVICE_SERIAL \
  --suite pen-live \
  --duration-ms 30000
```

The screen shows a drawing canvas plus limit, exclude, single-region, pause,
clear, and finish controls. The runner builds both flavors, installs each one,
captures events, and writes its report under `device-validation/results/`.
Use `--suite automated`, `base`, `device`, `mmkv-compat`, `pen-replay`,
`neo-pen`, or `guided` for the other test modes. The `automated` and `neo-pen`
suites also require `--neo-pen-reference /path/to/reference/neo-pen-library`.

To install only the recovered flavor for manual exploration:

```bash
./gradlew assembleRecovered
./gradlew -p device-validation \
  -POnyxArtifactsRoot=/path/to/reference-artifacts \
  :app:assembleRecoveredDebug
adb install -r device-validation/app/build/outputs/apk/recovered/debug/app-recovered-debug.apk
adb shell am start -n com.onyx.recovery.validation/.ValidationActivity \
  --es suite pen-live --el durationMs 30000
```

Host-only validation for the app's comparison code does not need a device:

```bash
python3 -m unittest discover device-validation/tests
```

## Dependency catalog (non-Cargo)

### Vendored

- Onyx Baselite 1.1.1 recovered source in
  `onyxsdk-base/support/onyxsdk-baselite`.
- Apache Commons IO 2.5 recovered and package-relocated source in
  `onyxsdk-base/support/onyxsdk-commons-io`.
- Gradle Wrapper 9.4.1 bootstrap files in `gradle/wrapper`.

### Gradle

The build scripts directly declare the following external Gradle dependencies;
Gradle resolves their transitive dependencies:

- Build plugin: `com.android.application` / `com.android.library` 9.2.1.
- AndroidX: `annotation` 1.0.0 and 1.9.1, `appcompat` 1.7.1,
  `databinding-common` 4.1.3, `dynamicanimation` 1.1.0-alpha03,
  `fragment` 1.8.8, Test JUnit extension 1.2.1, and Test Runner 1.6.2.
- Data, networking, and storage: Fastjson2 2.0.48.android8, Retrofit 2.1.0,
  OkHttp logging interceptor 4.10.0, MMKV 1.3.14, Zip4j 2.11.5,
  Java UUID Generator 4.1.0, and FST 2.56.
- Utilities: Commons Codec 1.13, Commons IO 2.13.0, Commons Text 1.4,
  EventBus 3.0.0, Joda-Time 2.10.14, Kotlin standard library JDK 8 1.6.10,
  Hugo annotations 1.2.1,
  RxJava 2.1.13, and RxAndroid 2.1.0.
- Tests: JUnit 4.13.2, JUnit BOM 5.11.4 with JUnit Jupiter and the JUnit
  Platform Launcher, Robolectric 4.14.1, and JSON-java 20240303.

EasyPermissions was previously exposed accidentally through `onyxsdk-base`'s
Gradle `api` configuration even though no production source used it. Its
removal changes that transitive dependency surface but removes no Onyx SDK
class or method. MMKV compatibility with files written by 1.0.19 is covered by
the `mmkv-compat` device suite described in
[`device-validation/README.md`](device-validation/README.md).
