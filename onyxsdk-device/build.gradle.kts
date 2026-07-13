plugins {
    id("onyx.android-library")
}

android {
    namespace = "com.onyx.android.sdk.device"

    defaultConfig {
        minSdk = 19
    }

    lint {
        // This SDK intentionally mirrors vendor/system APIs and its original
        // minSdk/manifest; those pre-existing findings live in the checked-in
        // baseline. New permission, API-level, range, and resource-type
        // mistakes still warn.
        baseline = file("lint-baseline.xml")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(libs.fastjson)
    api(libs.androidx.annotation)

    testImplementation(libs.junit4)
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR."
    dependsOn("assembleRelease")
}
