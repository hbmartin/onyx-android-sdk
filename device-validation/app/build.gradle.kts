plugins {
    id("com.android.application")
}

val artifactsRoot = providers.gradleProperty("OnyxArtifactsRoot")
    .orNull
    ?: error("Pass -POnyxArtifactsRoot=/absolute/path/to/reference-artifacts")
val recoveryRoot = rootProject.layout.projectDirectory.dir("..").asFile

fun recoveredAar(path: String) = file("${recoveryRoot.absolutePath}/$path")
fun original(path: String) = file("$artifactsRoot/$path")

configurations.configureEach {
    // The legacy easypermissions artifact still advertises support-compat 24.x.
    // The recovered SDK is AndroidX-based and the validation app never calls that
    // legacy surface, so keep the old support classes out of the APK.
    exclude(group = "com.android.support")
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

    sourceSets.getByName("reference").jniLibs.srcDir(
        original("onyxsdk-pen-1.5.4/jni")
    )

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
    implementation(files(
        recoveredAar("onyxsdk-base/support/onyxsdk-baselite/build/outputs/aar/onyxsdk-baselite-release.aar"),
        recoveredAar("onyxsdk-base/support/onyxsdk-commons-io/build/outputs/aar/onyxsdk-commons-io-release.aar"),
    ))

    add("referenceImplementation", files(
        original("onyxsdk-base-1.8.5/classes.jar"),
        original("onyxsdk-device-1.3.5/classes.jar"),
        original("onyxsdk-pen-1.5.4/classes.jar"),
    ))

    add("recoveredImplementation", files(
        recoveredAar("onyxsdk-base/build/outputs/aar/onyxsdk-base-release.aar"),
        recoveredAar("onyxsdk-device/build/outputs/aar/onyxsdk-device-release.aar"),
        recoveredAar("onyxsdk-pen/build/outputs/aar/onyxsdk-pen-release.aar"),
    ))

    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.fragment:fragment:1.8.8")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.databinding:databinding-common:4.1.3")
    implementation("com.squareup.retrofit2:retrofit:2.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.48.android8")
    implementation("pub.devrel:easypermissions:0.2.1")
    implementation("commons-io:commons-io:2.13.0")
    implementation("org.apache.commons:commons-text:1.4")
    implementation("commons-codec:commons-codec:1.13")
    implementation("io.reactivex.rxjava2:rxjava:2.1.13")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("androidx.dynamicanimation:dynamicanimation:1.1.0-alpha03")
    implementation("org.greenrobot:eventbus:3.0.0")
    implementation("com.tencent:mmkv:1.0.19")
    implementation("net.lingala.zip4j:zip4j:2.11.5")
    implementation("de.ruedigermoeller:fst:2.56")
    implementation("joda-time:joda-time:2.10.14")
    implementation("com.fasterxml.uuid:java-uuid-generator:4.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("com.jakewharton.hugo.fix:hugo-annotations:1.2.3")

    testImplementation("junit:junit:4.13.2")
    // The mockable android.jar stubs org.json (toString() returns null);
    // recorder tests that exercise real record writes need the real library.
    testImplementation("org.json:json:20240303")
}

tasks.register("verifyInputs") {
    doLast {
        val required = listOf(
            original("onyxsdk-base-1.8.5/classes.jar"),
            original("onyxsdk-device-1.3.5/classes.jar"),
            original("onyxsdk-pen-1.5.4/classes.jar"),
            recoveredAar("onyxsdk-base/build/outputs/aar/onyxsdk-base-release.aar"),
            recoveredAar("onyxsdk-device/build/outputs/aar/onyxsdk-device-release.aar"),
            recoveredAar("onyxsdk-pen/build/outputs/aar/onyxsdk-pen-release.aar"),
        )
        val missing = required.filterNot { it.isFile }
        check(missing.isEmpty()) {
            "Missing validation inputs:\n${missing.joinToString("\n")}. Build assembleRecovered with the repository Gradle wrapper first."
        }
    }
}

tasks.matching { it.name == "preBuild" }.configureEach {
    dependsOn("verifyInputs")
}
