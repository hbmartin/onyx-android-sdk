import org.gradle.api.tasks.testing.Test

plugins {
    id("com.android.library")
}

android {
    namespace = "com.onyx.recovery.tests"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = false
    }

    lint {
        disable += "GradleDependency"
    }
}

dependencies {
    implementation(project(":onyxsdk-base"))
    implementation(project(":onyxsdk-device"))
    implementation(project(":onyxsdk-pen"))

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("recovery.root", rootProject.projectDir.absolutePath)
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
