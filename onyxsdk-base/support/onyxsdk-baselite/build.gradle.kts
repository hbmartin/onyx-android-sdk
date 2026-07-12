plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.1.1-recovered-source"

android {
    namespace = "com.onyx.android.sdk.baselite"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
}
