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
}

gradlePlugin {
    plugins {
        register("onyxAndroidLibrary") {
            id = "onyx.android-library"
            implementationClass = "OnyxAndroidLibraryPlugin"
        }
    }
}
