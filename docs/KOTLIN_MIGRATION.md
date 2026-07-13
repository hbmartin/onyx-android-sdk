# Kotlin migration guardrails

The production sources remain a mixed Java/Kotlin recovery. Android Gradle
Plugin 9's built-in Kotlin support is disabled with the temporary legacy
opt-out flags so the three Kotlin-bearing Android modules can use the external
Kotlin Android plugin 2.0.21. The build-logic classpath excludes AGP's bundled
Kotlin Gradle plugin artifacts and supplies 2.0.21 from the version catalog;
the Kotlin standard-library metadata is aligned to the same version.

All Android source now compiles for Java 21. The Android and Kotlin JVM
toolchains and Java/Kotlin bytecode targets must remain aligned at 21. The
local resolver may select JBR 21, while GitHub Actions deliberately installs
Temurin 21. `build-logic` continues to use Gradle's embedded `kotlin-dsl`
compiler rather than replacing it with the Android Kotlin compiler.

## Migration order

1. Start with validation code and tests, which have no published consumer ABI.
2. Convert private and package-private leaf implementations in small batches.
3. Convert published classes one at a time only after reviewing the generated
   JVM contract diff. Treat the relocated Commons IO source as vendored code,
   not an automatic conversion target.
4. Leave classes that recreate Kotlin compiler artifacts, copied
   `kotlin.Metadata`, `$default` bridges, file facades, or obfuscated binary
   names until the final, explicitly audited phase.

## Published API rules

A source-equivalent Kotlin declaration is not necessarily JVM- or
Java-compatible. Each conversion must preserve Java-facing class and member
names, descriptors, visibility, inheritance, static/final/abstract flags,
generic signatures, checked `throws` declarations, annotations, overloads,
companion fields, file-facade names, default-argument bridges, and intentional
synthetic members. Use `@JvmName`, `@JvmField`, `@JvmStatic`, `@JvmOverloads`,
`@Throws`, and explicit facade names only when the checked-in contract proves
they are required.

Do not apply package-wide nullability defaults or broad annotation sweeps to
the recovered API. Nullability changes affect Kotlin callers and the
annotation/metadata layers already audited by this repository.

## Kotlin companion API

New idiomatic Kotlin APIs belong in `onyxsdk-ktx` when they do not need to be
members of the recovered binary surface. The companion module currently owns
immutable `PixelSize` conversions and scaling, a fresh mutable-size factory,
RxJava-to-`Flow` conversion, and a lifecycle-aware raw-input event flow. This
keeps coroutine dependencies and Kotlin-first naming out of the recovered
artifacts while allowing the original Java/Kotlin types to interoperate.

## Required gate

`./gradlew check` builds all six production AARs and runs
`verifyJvmApiContracts`. The canonical contracts under
`scripts/jvm-api-contracts/` pin visible classes and members, descriptors,
inheritance, access flags, generic signatures, annotations, and Kotlin metadata
hashes. A difference fails with a unified diff.

`./gradlew detektTypeCheck` runs Detekt 1.23.8's typed `detektMain` and
`detektTest` tasks for every Kotlin-bearing Android module and `build-logic`.
Current findings are captured in source-set/variant baselines; new typed
findings fail the CI gate and produce XML, HTML, and SARIF reports.

For an intentional API change, first test both a Java consumer and a Kotlin
consumer against the old and new AARs. Then run
`./gradlew updateJvmApiContracts`, review every contract change, and commit the
updated contract files with the source change. Regenerating a contract is an explicit
acceptance step, never an automatic build action.
