import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.tasks.Exec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
    base
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.versions)
    id("onyx.root")
    id("onyx.kdoc")
}

version = "0.0.2"

subprojects {
    pluginManager.withPlugin("org.jetbrains.kotlin.android") {
        extensions.configure<KotlinAndroidProjectExtension> {
            jvmToolchain(21)
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }

    pluginManager.withPlugin("io.gitlab.arturbosch.detekt") {
        extensions.configure<DetektExtension> {
            toolVersion = libs.versions.detekt.get()
            basePath = rootProject.projectDir.absolutePath
        }

        val detektReportDirectory = layout.buildDirectory.dir("reports/detekt")
        tasks.withType<Detekt>().configureEach {
            jvmTarget = "21"
            reports {
                xml.required.set(true)
                xml.outputLocation.set(detektReportDirectory.map { it.file("$name.xml") })
                html.required.set(true)
                html.outputLocation.set(detektReportDirectory.map { it.file("$name.html") })
                sarif.required.set(true)
                sarif.outputLocation.set(detektReportDirectory.map { it.file("$name.sarif") })
                md.required.set(false)
            }
        }
        tasks.withType<DetektCreateBaselineTask>().configureEach {
            jvmTarget = "21"
        }
    }
}

tasks.withType<DependencyUpdatesTask> {
    checkForGradleUpdate = true
    rejectVersionIf {
        listOf("-RC", "-Beta", "-alpha", "-rc", "-beta", "-dev").any { word ->
            candidate.version.contains(word)
        }
    }
}

tasks.register("detektTypeCheck") {
    group = "verification"
    description = "Runs typed Detekt analysis for production and test Kotlin source sets."
    dependsOn(
        ":onyxsdk-base:support:onyxsdk-baselite:detektMain",
        ":onyxsdk-base:support:onyxsdk-baselite:detektTest",
        ":onyxsdk-ktx:detektMain",
        ":onyxsdk-ktx:detektTest",
        ":onyxsdk-pen:detektMain",
        ":onyxsdk-pen:detektTest",
        gradle.includedBuild("build-logic").task(":detektMain"),
        gradle.includedBuild("build-logic").task(":detektTest"),
    )
}

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
