import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.gradle.plugin) {
        // AGP 9 bundles its built-in Kotlin implementation. This build uses
        // AGP's legacy opt-out so the requested external Kotlin plugin owns
        // Kotlin compilation instead.
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-gradle-plugin")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-gradle-plugin-api")
    }
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    testImplementation(gradleTestKit())
    testImplementation(libs.junit4)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

detekt {
    toolVersion = libs.versions.detekt.get()
    basePath = rootProject.projectDir.parentFile.absolutePath
}

val detektReportDirectory = layout.buildDirectory.dir("reports/detekt")
tasks.withType<Detekt>().configureEach {
    jvmTarget = "21"
    reports {
        xml.required.set(true)
        xml.outputLocation.set(detektReportDirectory.map { it.file("$name.xml") })
        html.required.set(true)
        html.outputLocation.set(detektReportDirectory.map { it.file("$name.html") })
        sarif.required.set(true)
        sarif.outputLocation.set(detektReportDirectory.map { it.file("$name.sarif") })
        md.required.set(false)
    }
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "21"
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
