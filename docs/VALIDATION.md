# Validation matrix

| Requirement | Automated evidence |
|---|---|
| No tracked reference SDK binaries | tracked-file scan and removed-directory assertions |
| No bytecode/JAR injection | Gradle-script scan rejects binary inputs and custom AAR assembly |
| Complete pen class surface | 129 expected class entries checked across the transitive pen and pen-core AARs |
| Public JVM compatibility | `verify-pen-api.py` matches 118 public classes by `javap` descriptor across the candidate classpath |
| Kotlin migration ABI guard | `verifyJvmApiContracts` pins visible descriptors, inheritance, flags, signatures, annotations, and Kotlin metadata for all seven production AARs |
| Kotlin companion API | host tests cover immutable values and typed ink contracts; `verifyKtxPublicSurface` rejects legacy Onyx, EventBus, RxJava, Fragment, and Data Binding descriptors |
| Base recoveries | production-class unit tests for formatting and disposal branches |
| Base JVM compatibility | classified descriptor/flags/signature/metadata audit (`device-validation/classify_api_differences.py`) plus `RecoveredApiSurfaceRegressionTest` pinning the repaired surface without reference JARs |
| Seven device recoveries | field-injection unit tests covering the success, failure, and null-method branches |
| Rust quality | locked tests, rustfmt, and Clippy for both crates |
| Four Android ABIs | cross-build and AAR entry audit for both implementations and all three JNI sonames |
| JNI consistency | 11 touch-reader and 14 neo-pen exports checked per ABI |
| No C++ runtime bridge | ELF dependency and AAR scans reject `libc++_shared.so` |
| New pen runtime | Android tests execute all nine pen types and lifecycle/error cases |
| Legacy pen runtime | Android tests execute static wrapper down/move/up/offline rendering |
| Raw reader JNI | Android test executes configuration, pause/resume, and close calls |
| First-class KTX device harness | recovered `sdk-automated` runs active capability evidence, refresh/wait, lease contention, best-effort commit, diagnostics export, and every renderer; `sdk-ink` provides operator raw-input and surface-lifecycle testing |
| Native differential | original and Rust snapshots run through the same BOOX APK harness |
| Exact simple pens | types 1–3 values asserted exactly by instrumentation tests |
| Texture pens | types 4–5 exact only under the hardware differential gate; snapshots compare stamp positions plus bitmap dimensions and pixel digests |
| Complex pen behavior | types 6–9 encoding, prediction, finiteness, and geometry bounds match |
| Versioned fountain behavior | recovered-v1 remains default; reference-compatible v2 dynamics, bounds, batching, and prediction isolation are host-tested |
| Device stroke extensions | additive API is explicitly accepted; reflection helpers reject instance methods intended for null receivers |
| Direct stroke transport | firmware codes are mandatory, framework reflection remains default, rejected starts fall back for the whole stroke, and mid-stroke Binder loss aborts without cross-transport calls |
| Reader lifecycle endurance | 300 start/pause/resume/quit cycles asserting no fd leaks, no stuck reader threads, bounded native- and Java-heap growth |
| Replay delivery health | per-run `replay_health` record: zero dropped callbacks, semantic callbacks observed, delivery latency within bounds; unhealthy runs classify as `recovery_defect` |
| Comparison taxonomy | host unit tests pin `compare_results.py` (one-sided records, asymmetric denials, bool/number equivalence, state grammar) and guided-scenario gates |
| Pinned device evidence | sanitized fixtures under `device-validation/fixtures/`; `tests/test_fixtures.py` re-compares each fixture against its committed counts |
| MMKV file compatibility | a checksum-pinned 1.0.19 data/CRC fixture is read and extended under 1.3.14 by the `mmkv-compat` NoteAir4C suite |
| Physical waveform correctness | operator-confirmed guided protocol (`run-comparison.sh --suite guided`, `docs/GUIDED_VALIDATION.md`): 12 scenarios with per-capture gates plus combined exact replay parity |

Portable source-only gate:

```bash
./gradlew clean :check assembleRecovered
```

Reference API gate (references remain untracked):

```bash
scripts/verify-pen-api.py \
  --old-reference /path/to/legacy/classes.jar \
  --native-reference /path/to/onyxsdk-pen-native-classes.jar \
  --candidate onyxsdk-pen/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar \
  --candidate-dependency onyxsdk-pen-core/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar
```

BOOX hardware gate:

```bash
scripts/device-pen-differential.sh /path/to/reference/neo-pen-library DEVICE_SERIAL
```
