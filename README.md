# Onyx SDK recovered source

This repository is a source-only recovery workspace for three Onyx/BOOX SDK
packages. It contains no original SDK JAR, AAR, or native library.

| Module | Recovered version | Raw Java evidence | Source-native artifact |
|---|---:|---:|---|
| `onyxsdk-base` | 1.8.5 | 588 files | `onyxsdk-base-release.aar` |
| `onyxsdk-device` | 1.3.5 | 72 files | `onyxsdk-device-release.aar` |
| `onyxsdk-pen` | 1.5.4 | 45 files plus Rust JNI | `onyxsdk-pen-release.aar` |

Each module follows a normal Android library layout:

- `src/main/java` contains only reviewed, compilable recovered source;
- `src/main/AndroidManifest.xml` is the minimal source-native Android manifest;
- `recovery-evidence/decompilers/<module>/jadx` contains the complete raw
  decompilation for continued recovery work;
- `recovery-evidence/android/<module>` contains the recovered legacy
  manifests, resources, and AIDL until individual pieces are reviewed;
- `onyxsdk-pen/native/onyx-pen-touch-reader` contains the Rust driver.

There is no `binary-reference`, no fixture JAR, no `classes.jar` injection,
and no preserved `libonyx_pen_touch_reader.so` or `libc++_shared.so`.

## Build and test

Prerequisites are JDK 17+, Rust stable, Android SDK platform 35, and Android NDK
28.2.13676358. Set `ANDROID_HOME` or `ANDROID_SDK_ROOT` when the SDK is not in a
standard location.

```bash
./gradlew clean check assembleRecovered
```

The build uses Android Gradle Plugin 9.2.1 to compile the Java and Android
resources directly. It also:

1. runs the recovered Java tests, including obfuscated,
   `UnsupportedOperationException`, and decompiler-disagreement paths;
2. runs the Rust state-machine tests;
3. cross-compiles Rust for `armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`;
4. verifies all 11 JNI exports against the source-declared contract;
5. rejects tracked SDK binaries, legacy injection code, `libc++_shared.so`, or
   unexpected native dependencies;
6. creates source-native AARs under each module's `build/outputs/aar`.

## Recovery boundary

The AARs contain the reviewed source-native recovery, not the original complete
SDK API. The 705-file raw decompilation still has wider decompiler diagnostics
and is retained as source evidence rather than silently compiled or replaced
with binary bytecode. Expanding the source-native API now means repairing and
promoting individual classes from evidence into a module's `src/main/java`.

See [RECOVERY_NOTES.md](RECOVERY_NOTES.md),
[docs/DECOMPILER_DISAGREEMENTS.md](docs/DECOMPILER_DISAGREEMENTS.md), and
[docs/NATIVE_RECOVERY.md](docs/NATIVE_RECOVERY.md).
