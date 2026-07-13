import org.gradle.api.tasks.Exec

plugins {
    base
}

group = "com.onyx.android.sdk.recovered"
version = "1.0.0"

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

val productionAarTasks = listOf(
    ":onyxsdk-base:assembleRelease",
    ":onyxsdk-base:support:onyxsdk-baselite:assembleRelease",
    ":onyxsdk-base:support:onyxsdk-commons-io:assembleRelease",
    ":onyxsdk-device:assembleRelease",
    ":onyxsdk-ktx:assembleRelease",
    ":onyxsdk-pen:assembleRelease",
)

val verifyJvmApiContracts = tasks.register<Exec>("verifyJvmApiContracts") {
    group = "verification"
    description = "Checks the public/protected JVM contracts of every production AAR."
    dependsOn(productionAarTasks)
    commandLine(
        "python3",
        layout.projectDirectory.file("scripts/jvm_api_contracts.py").asFile,
        "--root",
        layout.projectDirectory.asFile,
    )
}

tasks.register<Exec>("updateJvmApiContracts") {
    group = "build setup"
    description = "Explicitly replaces the checked-in JVM API contracts."
    dependsOn(productionAarTasks)
    commandLine(
        "python3",
        layout.projectDirectory.file("scripts/jvm_api_contracts.py").asFile,
        "--root",
        layout.projectDirectory.asFile,
        "--update",
    )
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
        verifyJvmApiContracts,
    )
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the recovered SDK and Kotlin companion release AARs."
    dependsOn(
        ":onyxsdk-base:assembleRecovered",
        ":onyxsdk-device:assembleRecovered",
        ":onyxsdk-ktx:assembleRecovered",
        ":onyxsdk-pen:assembleRecovered",
    )
}
