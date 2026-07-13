import org.gradle.api.tasks.Exec

plugins {
    base
    id("onyx.root")
    id("onyx.kdoc")
}

version = "0.0.2"

tasks.register<Exec>("nativeFormatCheck") {
    group = "verification"
    description = "Checks formatting of the recovered Rust implementation."
    commandLine("bash", layout.projectDirectory.file("scripts/check-rust.sh").asFile, "fmt")
}

tasks.register<Exec>("nativeTest") {
    group = "verification"
    description = "Runs the recovered Rust state-machine tests."
    commandLine("bash", layout.projectDirectory.file("scripts/check-rust.sh").asFile, "test")
}

tasks.register<Exec>("nativeClippy") {
    group = "verification"
    description = "Runs Clippy over both recovered Rust libraries."
    commandLine("bash", layout.projectDirectory.file("scripts/check-rust.sh").asFile, "clippy")
}

val verifyRecovery = tasks.register<Exec>("verifyRecovery") {
    group = "verification"
    description = "Audits recovered source, binary independence, Rust exports, and AAR contents."
    dependsOn("assembleRecovered")
    commandLine("bash", layout.projectDirectory.file("scripts/verify-recovery.sh").asFile)
}

val buildLogicTest = tasks.register("buildLogicTest") {
    group = "verification"
    description = "Runs unit tests for the registry-driven Gradle build logic."
    dependsOn(gradle.includedBuild("build-logic").task(":test"))
}

tasks.named("check") {
    dependsOn(
        ":onyxsdk-base:testDebugUnitTest",
        ":onyxsdk-base:support:onyxsdk-baselite:testDebugUnitTest",
        ":onyxsdk-device:testDebugUnitTest",
        ":onyxsdk-ktx:testDebugUnitTest",
        ":onyxsdk-pen:testDebugUnitTest",
        ":recovery-tests:testDebugUnitTest",
        // Lint runs against the checked-in per-module baselines; only new
        // findings fail the gate.
        ":onyxsdk-base:lintRelease",
        ":onyxsdk-base:support:onyxsdk-baselite:lintRelease",
        ":onyxsdk-device:lintRelease",
        ":onyxsdk-ktx:lintRelease",
        ":onyxsdk-pen:lintRelease",
        "nativeFormatCheck",
        "nativeTest",
        "nativeClippy",
        verifyRecovery,
        "verifyJvmApiContracts",
        "verifyModuleBoundaries",
        "verifyPublicationMetadata",
        "pythonValidationTest",
        buildLogicTest,
    )
}
