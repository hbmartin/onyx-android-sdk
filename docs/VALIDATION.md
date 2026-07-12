# Validation matrix

| Requirement | Automated evidence |
|---|---|
| No tracked reference SDK binaries | tracked-file scan and removed-directory assertions |
| No bytecode/JAR injection | Gradle-script scan rejects binary inputs and custom AAR assembly |
| Complete pen class surface | 129 expected class entries checked in the release AAR |
| Public JVM compatibility | `verify-pen-api.py` matches 118 public classes by `javap` descriptor |
| Base recoveries | production-class unit tests for formatting and disposal branches |
| Seven device recoveries | field-injection tests plus compiled-bytecode inspection |
| Rust quality | locked tests, rustfmt, and Clippy for both crates |
| Four Android ABIs | cross-build and AAR entry audit for both native libraries |
| JNI consistency | 11 touch-reader and 14 neo-pen exports checked per ABI |
| No C++ runtime bridge | ELF dependency and AAR scans reject `libc++_shared.so` |
| New pen runtime | Android tests execute all nine pen types and lifecycle/error cases |
| Legacy pen runtime | Android tests execute static wrapper down/move/up/offline rendering |
| Raw reader JNI | Android test executes configuration, pause/resume, and close calls |
| Native differential | original and Rust snapshots run through the same BOOX APK harness |
| Exact simple pens | types 1–5 snapshot values match the reference exactly |
| Complex pen behavior | types 6–9 encoding, prediction, finiteness, and geometry bounds match |

Portable source-only gate:

```bash
./gradlew clean :check assembleRecovered
```

Reference API gate (references remain untracked):

```bash
scripts/verify-pen-api.py \
  --old-reference /path/to/legacy/classes.jar \
  --native-reference /path/to/onyxsdk-pen-native-classes.jar \
  --candidate onyxsdk-pen/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar
```

BOOX hardware gate:

```bash
scripts/device-pen-differential.sh /path/to/libneo_pen.so DEVICE_SERIAL
```
