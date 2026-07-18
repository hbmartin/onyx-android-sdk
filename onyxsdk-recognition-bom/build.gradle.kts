plugins {
    id("onyx.java-platform")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api(project(":onyxsdk-recognition"))
        api(project(":onyxsdk-recognition-native-runtime"))
        api(project(":onyxsdk-recognition-hwr-core"))
        api(project(":onyxsdk-recognition-ocr-core"))
        api(project(":onyxsdk-recognition-speech-core"))
        api(project(":onyxsdk-recognition-paddle-v3"))
        api(project(":onyxsdk-recognition-myscript-editor"))
        api(project(":onyxsdk-recognition-myscript-jiix"))
        api(project(":onyxsdk-recognition-digital-ink-preparation"))
        api(project(":onyxsdk-recognition-soda-preparation"))
        api(project(":onyxsdk-recognition-testing"))
    }
}
