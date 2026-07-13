plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.baselite"
}

dependencies {
    api(libs.kotlin.stdlib.jdk8)

    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
}
