# Recovery notes

## Source-only policy

The repository intentionally excludes every supplied SDK binary:

- no `classes-original.jar` files;
- no original AAR files;
- no original `libonyx_pen_touch_reader.so` files;
- no copied `libc++_shared.so` runtime;
- no binary test fixtures.

Android Gradle Plugin compiles each AAR from checked-in Java and a minimal
manifest. Legacy manifests, resources, and AIDL are source evidence under
`recovery-evidence/android`, not implicit build inputs. The pen module compiles
its JNI implementation from Rust during the build. `scripts/verify-recovery.sh`
fails if removed binary inputs or their former injection patterns return.

## Java organization

Exact-name JADX recovery produced 588 base, 72 device, and 45 pen outer-source
files. Those 705 files are under `recovery-evidence/decompilers/*/jadx` and
include the repaired nine target methods. They remain analysis input because
95 files contain wider invalid type-inference artifacts.

Only reviewed source is promoted into each module's `src/main/java`. The
current compiled recovery contains:

- `ReadableFileSize` and `DisposeFinally` for the two repaired base behaviors;
- `DeviceMethodRecovery` for all seven reflective device mappings;
- the recovered R8-named `com.onyx.android.sdk.device.a` UTF-8 file helper;
- `RawInputReader`, `NativeContract`, and `DefaultArgumentGuard` for the pen
  JNI and failure-path contracts.

This separation keeps build output honest: a class is either source-compiled
and tested, or visibly remains decompilation evidence.

## Nine repaired methods

CFR reconstructed all seven failed device sites as zero-argument static
reflective calls guarded by `catch (Exception)`. Bytecode inspection confirmed
the field mapping, null receiver, empty argument array, and exception table.

The two base sites required direct control-flow inspection:

- `FileUtils.readableFileSize(long)` uses logarithm base 1024, units through
  TB, and `DecimalFormat("#,##0.#")`.
- `RxUtils$d.run()` catches `Exception`, reports it, always disposes its worker,
  and lets non-`Exception` throwables escape after disposal.

The decisions are summarized in
`docs/DECOMPILER_DISAGREEMENTS.md`. Tests inspect the repaired raw sources and
exercise their promoted implementations.

## Native recovery

The Rust crate preserves the 11 JNI method names and Java callback descriptor.
The source contract is declared in `NativeContract` and implemented by the
source-native `RawInputReader` bridge. Native validation compares every ABI's
exports to that checked-in source contract and ensures neither the build nor
the packaged AAR depends on `libc++_shared.so`.

## Test visibility

- `UnsupportedOperationPathTest` exercises the recovered Kotlin default-super
  guard and exact exception message.
- `ObfuscatedCodePathTest` executes the source-compiled R8 class `device.a`.
- `DecompilerDisagreementTest` audits all nine repaired source sites and their
  documented mappings.
- `RecoveredAlgorithmsTest` covers success, caught exception, finally/dispose,
  uncaught `Error`, all seven device mappings, and the Java/JNI surface.
- Rust tests cover state transitions, eraser selection, regions, and pressure
  normalization.
