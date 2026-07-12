plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.3.5-recovered-source"

android {
    namespace = "com.onyx.android.sdk.device"
    compileSdk = 35

    defaultConfig {
        minSdk = 19
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
    }

    lint {
        // This SDK intentionally mirrors vendor/system APIs and its original
        // minSdk/manifest. These checks flag Onyx service-name strings,
        // privileged permissions, and model-gated newer APIs even though the
        // consuming app/firmware supplies the relevant capability.
        disable += setOf(
            "GradleDependency",
            "LongLogTag",
            "MissingPermission",
            "NewApi",
            "ProtectedPermissions",
            "WrongConstant",
        )
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api("com.alibaba.fastjson2:fastjson2:2.0.48.android8")
    api("androidx.annotation:annotation:1.0.0")

    testImplementation("junit:junit:4.13.2")
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR."
    dependsOn("assembleRelease")
}
