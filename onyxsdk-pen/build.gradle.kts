import org.gradle.api.tasks.Exec

plugins {
    id("com.android.library")
}

group = "com.onyx.android.sdk.recovered"
version = "1.5.4-recovered-source"

val buildRustAndroid = tasks.register<Exec>("buildRustAndroid") {
    group = "build"
    description = "Cross-compiles both recovered pen libraries for all four Android ABIs."
    inputs.files(
        fileTree("native/onyx-pen-touch-reader/src"),
        file("native/onyx-pen-touch-reader/Cargo.toml"),
        file("native/onyx-pen-touch-reader/Cargo.lock"),
        fileTree("native/onyx-neo-pen/src"),
        file("native/onyx-neo-pen/Cargo.toml"),
        file("native/onyx-neo-pen/Cargo.lock"),
    )
    outputs.dir("src/main/jniLibs")
    inputs.property("penReferenceNeoSo", providers.gradleProperty("penReferenceNeoSo").orElse(""))
    commandLine("bash", rootProject.layout.projectDirectory.file("scripts/build-rust-android.sh").asFile)
    doLast {
        providers.gradleProperty("penReferenceNeoSo").orNull?.let { referencePath ->
            val reference = file(referencePath)
            require(reference.isFile) { "Reference libneo_pen.so does not exist: $reference" }
            copy {
                from(reference)
                into(file("src/main/jniLibs/arm64-v8a"))
                rename { "libneo_pen.so" }
            }
        }
    }
}

android {
    namespace = "com.onyx.android.sdk.pen"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
    }

    lint {
        disable += "GradleDependency"
    }
}

dependencies {
    api(project(":onyxsdk-base"))
    api("de.ruedigermoeller:fst:2.56")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
}

configurations.configureEach {
    if (name.contains("AndroidTest", ignoreCase = true)) {
        exclude(group = "com.android.support")
    }
}

tasks.named("preBuild") {
    dependsOn(buildRustAndroid)
}

tasks.named<Delete>("clean") {
    delete("src/main/jniLibs")
}

tasks.register("assembleRecovered") {
    group = "build"
    description = "Builds the source-native release AAR and both Rust JNI libraries."
    dependsOn("assembleRelease")
}
