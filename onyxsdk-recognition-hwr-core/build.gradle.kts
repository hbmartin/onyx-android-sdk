plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition.hwr.core"
    defaultConfig {
        minSdk = 26
        ndk {
            abiFilters += "arm64-v8a"
        }
    }
    packaging {
        jniLibs.pickFirsts += "lib/arm64-v8a/libc++_shared.so"
    }
}

dependencies {
    api(project(":onyxsdk-recognition-native-runtime"))
    api(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.junit4)
}
