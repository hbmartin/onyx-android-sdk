import org.gradle.api.tasks.bundling.Jar

plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.8.5-recovered-source"

android {
    namespace = "com.onyx.android.sdk"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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

val decompiledSourcesJar = tasks.register<Jar>("decompiledSourcesJar") {
    group = "build"
    description = "Archives the raw JADX recovery retained as analysis evidence."
    archiveClassifier.set("decompiled-sources")
    from(rootProject.file("recovery-evidence/decompilers/base/jadx"))
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR and decompilation evidence archive."
    dependsOn("assembleRelease", decompiledSourcesJar)
}
