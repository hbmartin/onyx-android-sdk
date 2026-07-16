plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.pen.core"

    packaging {
        jniLibs {
            excludes += setOf("**/libneo_pen.so", "**/libonyx_pen_touch_reader.so")
        }
    }
}

dependencies {
    api(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.junit4)
}

tasks.named("preBuild") {
    dependsOn(":onyxsdk-pen:buildRustAndroid")
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the dependency-minimal modern pen renderer AAR."
    dependsOn("assembleRelease")
}

tasks.named<Delete>("clean") {
    delete("src/main/jniLibs")
}
