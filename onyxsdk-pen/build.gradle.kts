import java.io.File
import org.gradle.api.tasks.Exec

plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

val buildRustAndroid = tasks.register<Exec>("buildRustAndroid") {
    group = "build"
    description = "Cross-compiles both recovered pen libraries for all four Android ABIs."
    val buildScript = rootProject.layout.projectDirectory.file("scripts/build-rust-android.sh")
    val sharedLibrarySuffix = ".so"
    inputs.files(
        fileTree("native") {
            include("**/*.rs", "**/Cargo.toml", "Cargo.lock")
            exclude("**/target/**", "**/build/**", "**/fuzz/**")
        },
        rootProject.layout.projectDirectory.file("rust-toolchain.toml"),
        buildScript,
    )
    outputs.dir("src/main/jniLibs")
    inputs.property("penReferenceNeoSo", providers.gradleProperty("penReferenceNeoSo").orElse(""))
    // The script picks its toolchain from these; a changed NDK must invalidate native outputs.
    inputs.property("ndkVersion", providers.environmentVariable("ANDROID_NDK_VERSION").orElse(""))
    inputs.property("ndkHome", providers.environmentVariable("ANDROID_NDK_HOME").orElse(""))
    inputs.property("androidApi", providers.environmentVariable("ANDROID_API").orElse(""))
    val androidSdkDirectory = androidComponents.sdkComponents.sdkDirectory
    inputs.property("androidSdkDirectory", androidSdkDirectory.map { it.asFile.absolutePath })
    commandLine("bash", buildScript.asFile)
    doFirst {
        environment("ANDROID_HOME", androidSdkDirectory.get().asFile.absolutePath)
    }
    val referenceSoPath = providers.gradleProperty("penReferenceNeoSo")
    val projectDir = layout.projectDirectory.asFile
    val jniArm64 = layout.projectDirectory.dir("src/main/jniLibs/arm64-v8a").asFile
    doLast {
        referenceSoPath.orNull?.let { referencePath ->
            val candidate = File(referencePath)
            val reference = if (candidate.isAbsolute) candidate else projectDir.resolve(referencePath)
            require(reference.isFile) { "Reference neo-pen library does not exist: $reference" }
            jniArm64.mkdirs()
            reference.copyTo(jniArm64.resolve("libneo_pen$sharedLibrarySuffix"), overwrite = true)
        }
    }
}

android {
    namespace = "com.onyx.android.sdk.pen"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(project(":onyxsdk-base"))
    api(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
}

configurations.configureEach {
    if (name.contains("AndroidTest", ignoreCase = true)) {
        exclude(group = "com.android.support")
    }
}

tasks.named("preBuild") {
    dependsOn(buildRustAndroid)
}

tasks.named<Delete>("clean") {
    delete("src/main/jniLibs")
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR and both Rust JNI libraries."
    dependsOn("assembleRelease")
}
