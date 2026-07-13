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
}

gradlePlugin {
    plugins {
        register("onyxAndroidLibrary") {
            id = "onyx.android-library"
            implementationClass = "OnyxAndroidLibraryPlugin"
        }
        register("onyxKdoc") {
            id = "onyx.kdoc"
            implementationClass = "OnyxKdocPlugin"
        }
    }
}
