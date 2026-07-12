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
        dataBinding = true
    }

    lint {
        disable += "GradleDependency"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

configurations.configureEach {
    resolutionStrategy.force("androidx.databinding:databinding-common:4.1.3")
}

dependencies {
    api(project(":onyxsdk-device"))
    api(project(":onyxsdk-base:support:onyxsdk-baselite"))
    api(project(":onyxsdk-base:support:onyxsdk-commons-io"))
    api("androidx.fragment:fragment:1.8.8")
    api("androidx.appcompat:appcompat:1.7.1")
    api("androidx.databinding:databinding-common:4.1.3")
    api("com.squareup.retrofit2:retrofit:2.1.0")
    api("com.squareup.okhttp3:logging-interceptor:4.10.0")
    api("com.alibaba.fastjson2:fastjson2:2.0.48.android8")
    api("pub.devrel:easypermissions:0.2.1")
    api("commons-io:commons-io:2.13.0")
    api("org.apache.commons:commons-text:1.4")
    api("commons-codec:commons-codec:1.13")
    api("io.reactivex.rxjava2:rxjava:2.1.13")
    api("io.reactivex.rxjava2:rxandroid:2.1.0")
    api("androidx.dynamicanimation:dynamicanimation:1.1.0-alpha03")
    api("org.greenrobot:eventbus:3.0.0")
    api("com.tencent:mmkv:1.0.19")
    api("net.lingala.zip4j:zip4j:2.11.5")
    api("de.ruedigermoeller:fst:2.56")
    api("joda-time:joda-time:2.10.14")
    api("com.fasterxml.uuid:java-uuid-generator:4.1.0")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    api("com.jakewharton.hugo.fix:hugo-annotations:1.2.3")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.14.1")
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
