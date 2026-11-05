plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition.hwr.jiix"
    defaultConfig { minSdk = 26 }
}

dependencies {
    api(project(":onyxsdk-recognition-myscript-editor"))
    testImplementation(libs.junit4)
}
