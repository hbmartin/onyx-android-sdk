plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
}

android {
    namespace = "com.onyx.android.sdk.baselite"

    defaultConfig {
        minSdk = 24
    }

}

dependencies {
    api(libs.kotlin.stdlib.jdk8)

    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
}
