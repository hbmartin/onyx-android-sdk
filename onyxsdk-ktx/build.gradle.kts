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
    implementation(project(":onyxsdk-base"))
    implementation(project(":onyxsdk-pen"))
    api(libs.coroutines.core)
    implementation(libs.coroutines.android)

    testImplementation(libs.junit4)
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the Kotlin companion release AAR."
    dependsOn("assembleRelease")
}
