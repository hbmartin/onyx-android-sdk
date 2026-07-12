import org.gradle.api.tasks.Exec

plugins {
    base
    id("com.android.library") version "9.2.1" apply false
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

tasks.named("check") {
    dependsOn(
        ":onyxsdk-base:testDebugUnitTest",
        ":onyxsdk-device:testDebugUnitTest",
        ":onyxsdk-pen:testDebugUnitTest",
        ":recovery-tests:testDebugUnitTest",
        "nativeFormatCheck",
        "nativeTest",
        "nativeClippy",
        verifyRecovery,
    )
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds all three source-native release AARs."
    dependsOn(
        ":onyxsdk-base:assembleRecovered",
        ":onyxsdk-device:assembleRecovered",
        ":onyxsdk-pen:assembleRecovered",
    )
}
