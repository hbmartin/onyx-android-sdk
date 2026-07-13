import org.gradle.api.tasks.bundling.Jar

plugins {
    id("onyx.android-library")
}

android {
    namespace = "com.onyx.android.sdk"

    buildFeatures {
        dataBinding = true
    }

    lint {
        // The recovered library preserves the original vendor API, resources,
        // hidden-service calls, and minSdk, which lint cannot model. Those
        // pre-existing findings live in the checked-in baseline; new
        // permission, API-level, range, and resource-type mistakes still warn.
        baseline = file("lint-baseline.xml")
    }
}

configurations.configureEach {
    resolutionStrategy.force(libs.androidx.databinding.common.get())
}

dependencies {
    api(project(":onyxsdk-device"))
    api(project(":onyxsdk-base:support:onyxsdk-baselite"))
    api(project(":onyxsdk-base:support:onyxsdk-commons-io"))
    api(libs.bundles.onyx.runtime)

    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR."
    dependsOn("assembleRelease")
}
