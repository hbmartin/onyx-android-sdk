plugins {
    id("com.android.application")
    id("onyx.device-validation")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.onyx.recovery.validation"

    defaultConfig {
        applicationId = "com.onyx.recovery.validation"
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"
    }

    flavorDimensions += "sdk"
    productFlavors {
        create("reference") {
            dimension = "sdk"
            buildConfigField("String", "SDK_VARIANT", "\"reference\"")
        }
        create("recovered") {
            dimension = "sdk"
            buildConfigField("String", "SDK_VARIANT", "\"recovered\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
        resources {
            excludes += setOf("META-INF/DEPENDENCIES", "META-INF/LICENSE*", "META-INF/NOTICE*")
        }
    }

    lint {
        // Target 29 intentionally matches the supplied SDK manifests so the
        // two variants receive the same legacy platform compatibility rules.
        disable += "ExpiredTargetSdkVersion"
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(libs.bundles.onyx.runtime)
    implementation(libs.coroutines.android)

    testImplementation(libs.junit4)
    // The mockable android.jar stubs org.json (toString() returns null);
    // recorder tests that exercise real record writes need the real library.
    testImplementation(libs.json.java)
}

// AGP 9 supplies Kotlin 2.2.10 to this standalone build. Keep its packaged runtime at the
// compiler level even though the recovered production catalog intentionally remains older.
configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.name in setOf(
                "kotlin-stdlib",
                "kotlin-stdlib-common",
                "kotlin-stdlib-jdk7",
                "kotlin-stdlib-jdk8",
            )
        ) {
            useVersion("2.2.10")
            because("the standalone AGP 9 validation compiler emits Kotlin 2.2 coroutine bytecode")
        }
    }
}
