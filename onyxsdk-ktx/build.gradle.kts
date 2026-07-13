plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
}

android {
    namespace = "com.onyx.android.sdk.ktx"

    defaultConfig {
        minSdk = 24
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
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
