plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "2.5-recovered-source"

android {
    namespace = "com.onyx.android.sdk.commons.io"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
    }
}
