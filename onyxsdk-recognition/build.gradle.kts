plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition"
    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    api(project(":onyxsdk-ktx"))
    implementation(project(":onyxsdk-recognition-hwr-core"))
    implementation(project(":onyxsdk-recognition-ocr-core"))
    implementation(project(":onyxsdk-recognition-speech-core"))
    api(libs.coroutines.core)
    implementation(libs.coroutines.android)

    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
}
