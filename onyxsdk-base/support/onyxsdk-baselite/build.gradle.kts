plugins {
    id("onyx.android-library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.1.1-recovered-source"

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
