import org.gradle.api.tasks.Exec

plugins {
    base
    id("com.android.library") version "9.2.1" apply false
}

group = "com.onyx.android.sdk.recovered"
version = "1.0.0"

val nativeCrate = layout.projectDirectory.dir(
    "onyxsdk-pen/native/onyx-pen-touch-reader"
)

tasks.register<Exec>("nativeFormatCheck") {
    group = "verification"
    description = "Checks formatting of the recovered Rust implementation."
    commandLine(
        "cargo", "fmt", "--check", "--manifest-path",
        nativeCrate.file("Cargo.toml").asFile.absolutePath,
    )
}

tasks.register<Exec>("nativeTest") {
    group = "verification"
    description = "Runs the recovered Rust state-machine tests."
    commandLine(
        "cargo", "test", "--locked", "--manifest-path",
        nativeCrate.file("Cargo.toml").asFile.absolutePath,
    )
}

val verifyRecovery = tasks.register<Exec>("verifyRecovery") {
    group = "verification"
    description = "Audits recovered source, binary independence, Rust exports, and AAR contents."
    dependsOn("assembleRecovered")
    commandLine("bash", layout.projectDirectory.file("scripts/verify-recovery.sh").asFile)
}

tasks.named("check") {
    dependsOn(
        subprojects.map { "${it.path}:check" },
        "nativeFormatCheck",
        "nativeTest",
        verifyRecovery,
    )
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds all three source-native AARs and decompilation evidence archives."
    dependsOn(
        ":onyxsdk-base:assembleRecovered",
        ":onyxsdk-device:assembleRecovered",
        ":onyxsdk-pen:assembleRecovered",
    )
}
