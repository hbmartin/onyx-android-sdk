# Kotlin migration guardrails

The production sources remain a mixed Java/Kotlin recovery. Android Gradle
Plugin 9 supplies built-in Kotlin support to Android modules, so this project
must not add `org.jetbrains.kotlin.android` or a separate Kotlin Android plugin.
The current Java 8 targets and explicit Kotlin standard-library API dependencies
remain unchanged until a migration change demonstrates why they should move.

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

## Required gate

`./gradlew check` builds all five production AARs and runs
`verifyJvmApiContracts`. The canonical contracts under
`scripts/jvm-api-contracts/` pin visible classes and members, descriptors,
inheritance, access flags, generic signatures, annotations, and Kotlin metadata
hashes. A difference fails with a unified diff.

For an intentional API change, first test both a Java consumer and a Kotlin
consumer against the old and new AARs. Then run
`./gradlew updateJvmApiContracts`, review every contract change, and commit the
updated contract files with the source change. Regenerating a contract is an explicit
acceptance step, never an automatic build action.
