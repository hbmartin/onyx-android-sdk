plugins {
    id("onyx.android-library")
    id("onyx.kdoc")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.onyx.android.sdk.recognition.nativecore"
    ndkVersion = "28.2.13676358"
    defaultConfig {
        minSdk = 26
        ndk {
            abiFilters += "arm64-v8a"
        }
    }
    sourceSets {
        getByName("main").jniLibs.srcDir(
            layout.buildDirectory.dir("generated/pinnedNdkRuntime").get().asFile,
        )
    }
    packaging {
        jniLibs.keepDebugSymbols += "**/libc++_shared.so"
    }
}

dependencies {
    api(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.junit4)
}

val preparePinnedNdkRuntime by tasks.registering(Sync::class) {
    val prebuiltRoot = android.sdkDirectory.resolve(
        "ndk/28.2.13676358/toolchains/llvm/prebuilt",
    )
    from(
        fileTree(prebuiltRoot) {
            include("*/sysroot/usr/lib/aarch64-linux-android/libc++_shared.so")
        },
    )
    into(layout.buildDirectory.dir("generated/pinnedNdkRuntime/arm64-v8a"))
    includeEmptyDirs = false
    eachFile {
        path = name
    }
}

tasks.named("preBuild") {
    dependsOn(preparePinnedNdkRuntime)
}
