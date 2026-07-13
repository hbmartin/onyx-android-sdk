plugins {
    id("com.android.application")
    id("onyx.device-validation")
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

    testImplementation(libs.junit4)
    // The mockable android.jar stubs org.json (toString() returns null);
    // recorder tests that exercise real record writes need the real library.
    testImplementation(libs.json.java)
}
