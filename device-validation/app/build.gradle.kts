plugins {
    id("com.android.application")
    id("onyx.device-validation")
}

android {
    namespace = "com.onyx.recovery.validation"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.onyx.recovery.validation"
        minSdk = 24
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
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
        disable += setOf("GradleDependency", "ExpiredTargetSdkVersion")
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.fragment:fragment:1.8.8")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.databinding:databinding-common:4.1.3")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.48.android8")
    implementation("org.apache.commons:commons-lang3:3.18.0")
    implementation("io.reactivex.rxjava2:rxjava:2.1.13")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("androidx.dynamicanimation:dynamicanimation:1.1.0-alpha03")
    implementation("org.greenrobot:eventbus:3.0.0")
    implementation("com.tencent:mmkv:1.3.14")
    implementation("net.lingala.zip4j:zip4j:2.11.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    testImplementation("junit:junit:4.13.2")
    // The mockable android.jar stubs org.json (toString() returns null);
    // recorder tests that exercise real record writes need the real library.
    testImplementation("org.json:json:20240303")
}
