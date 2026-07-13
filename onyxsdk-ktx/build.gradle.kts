plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.ktx"
}

dependencies {
    api(project(":onyxsdk-base"))
    api(project(":onyxsdk-pen"))
    api(libs.coroutines.core)
    implementation(libs.coroutines.rx2)

    testImplementation(libs.junit4)
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the Kotlin companion release AAR."
    dependsOn("assembleRelease")
}
