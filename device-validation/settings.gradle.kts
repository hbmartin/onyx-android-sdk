pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.boox.com/repository/maven-public/")
            content {
                includeGroup("pub.devrel")
                includeGroup("com.tencent")
                includeGroup("com.jakewharton.hugo.fix")
            }
        }
    }
}

rootProject.name = "onyx-device-validation"
include(":app")
