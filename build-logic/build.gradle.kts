plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${libs.versions.dokka.get()}")
    testImplementation(gradleTestKit())
    testImplementation("junit:junit:4.13.2")
}

gradlePlugin {
    plugins {
        register("onyxModuleSettings") {
            id = "onyx.module-settings"
            implementationClass = "OnyxModuleSettingsPlugin"
        }
        register("onyxRoot") {
            id = "onyx.root"
            implementationClass = "OnyxRootPlugin"
        }
        register("onyxAndroidLibrary") {
            id = "onyx.android-library"
            implementationClass = "OnyxAndroidLibraryPlugin"
        }
        register("onyxDeviceValidation") {
            id = "onyx.device-validation"
            implementationClass = "OnyxDeviceValidationPlugin"
        }
        register("onyxKdoc") {
            id = "onyx.kdoc"
            implementationClass = "OnyxKdocPlugin"
        }
    }
}
