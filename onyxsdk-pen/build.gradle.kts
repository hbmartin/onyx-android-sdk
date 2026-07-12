import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.bundling.Jar

plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.5.4-recovered-source"

val buildRustAndroid = tasks.register<Exec>("buildRustAndroid") {
    group = "build"
    description = "Cross-compiles the recovered native reader for all four Android ABIs."
    inputs.files(
        fileTree("native/onyx-pen-touch-reader/src"),
        file("native/onyx-pen-touch-reader/Cargo.toml"),
        file("native/onyx-pen-touch-reader/Cargo.lock"),
    )
    outputs.dir("src/main/jniLibs")
    commandLine("bash", rootProject.layout.projectDirectory.file("scripts/build-rust-android.sh").asFile)
}

android {
    namespace = "com.onyx.android.sdk.pen"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
    }

    lint {
        disable += "GradleDependency"
    }
}

dependencies {
    api(project(":onyxsdk-base"))
    api("de.ruedigermoeller:fst:2.56")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
}

tasks.named("preBuild") {
    dependsOn(buildRustAndroid)
}

tasks.named<Delete>("clean") {
    delete("src/main/jniLibs")
}

val decompiledSourcesJar = tasks.register<Jar>("decompiledSourcesJar") {
    group = "build"
    description = "Archives the raw JADX recovery retained as analysis evidence."
    archiveClassifier.set("decompiled-sources")
    from(rootProject.file("recovery-evidence/decompilers/pen/jadx"))
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR, Rust JNI libraries, and evidence archive."
    dependsOn("assembleRelease", decompiledSourcesJar)
}
