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
