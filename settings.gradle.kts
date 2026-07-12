pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "booxThirdPartyMirror"
            url = uri("https://repo.boox.com/repository/maven-public/")
            content {
                includeGroup("pub.devrel")
                includeGroup("com.tencent")
                includeGroup("com.jakewharton.hugo.fix")
            }
        }
    }
}

rootProject.name = "onyxsdk-recovered-complete"

include(
    "onyxsdk-base",
    "onyxsdk-base:support:onyxsdk-baselite",
    "onyxsdk-base:support:onyxsdk-commons-io",
    "onyxsdk-device",
    "onyxsdk-pen",
    "recovery-tests",
)
