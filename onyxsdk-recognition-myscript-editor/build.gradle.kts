plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition.hwr.editor"
    defaultConfig { minSdk = 26 }
}

dependencies {
    api(project(":onyxsdk-recognition"))
    implementation(project(":onyxsdk-recognition-hwr-core"))
    testImplementation(libs.junit4)
}
