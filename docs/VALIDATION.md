# Validation matrix

| Requirement | Automated evidence |
|---|---|
| No original SDK binaries | tracked-file scan and removed-directory assertions |
| No bytecode injection | Gradle-script scan rejects binary inputs and custom ZIP assembly |
| Standard Android builds | AGP compiles three release AARs from `src/main` |
| 705 raw Java sources retained | exact per-module source counts |
| Seven device methods filled | mapping tests and repaired-source scan |
| Two base methods rebuilt | formatting and finally/error branch tests |
| Obfuscated behavior visible | `ObfuscatedCodePathTest` executes source-compiled `device.a` |
| Unsupported-operation behavior | source-compiled guard and exact-message test |
| Decompiler disagreements visible | documentation and `DecompilerDisagreementTest` |
| Rust behavior | `cargo test --locked` |
| Four Android ABI builds | NDK cross-build plus AAR entry audit |
| JNI consistency | 11 exports compared with Java `NativeContract` for every ABI |
| No C++ runtime bridge | dependency and AAR scans reject `libc++_shared.so` |
| Repeatable CI | `.github/workflows/build.yml` |

Run the complete gate with:

```bash
./gradlew clean check assembleRecovered
```

The resulting AARs are source-native recovery artifacts. They do not claim the
complete original SDK API until additional raw classes are repaired, reviewed,
and promoted from recovery evidence.
