plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition.ocr.paddlev3"
    defaultConfig { minSdk = 26 }
}

dependencies {
    api(project(":onyxsdk-recognition"))
    implementation(project(":onyxsdk-recognition-ocr-core"))
    testImplementation(libs.junit4)
}
