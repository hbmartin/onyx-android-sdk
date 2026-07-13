import org.gradle.api.tasks.testing.Test

plugins {
    id("onyx.android-library")
}

android {
    namespace = "com.onyx.recovery.tests"
}

dependencies {
    implementation(project(":onyxsdk-base"))
    implementation(project(":onyxsdk-device"))
    implementation(project(":onyxsdk-pen"))

    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.junit5.jupiter)
    testRuntimeOnly(libs.junit5.platform.launcher)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("recovery.root", rootProject.projectDir.absolutePath)
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
