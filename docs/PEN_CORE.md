# Minimal pen artifact and ABI packaging

`onyxsdk-pen-core` contains only the modern `com.onyx.android.sdk.pennative`
renderer contract, Kotlin stdlib, and `libneopen_jni.so`. It has no dependency
on `onyxsdk-base`, RxJava, EventBus, Data Binding, MMKV, serialization, archive,
network, or document services.

Use it when an application needs the modern offline/native renderer but does
not need `TouchHelper` or the compatibility pen surface:

```kotlin
dependencies {
    implementation("io.github.hbmartin.onyx:onyxsdk-pen-core:1.0.0")
}
```

`onyxsdk-pen` remains the compatibility artifact and depends on `pen-core`.
It packages `libneo_pen.so` plus `libonyx_pen_touch_reader.so`; `pen-core`
packages `libneopen_jni.so`. This prevents duplicate native libraries when an
application uses `onyxsdk-ktx` or the legacy pen artifact.

Both pen artifacts support `arm64-v8a`, `armeabi-v7a`, `x86`, and `x86_64`.
Android App Bundles automatically deliver only the device ABI. APK builds can
opt into explicit splits:

```kotlin
android {
    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
            isUniversalApk = false
        }
    }
}
```

Do not filter ABIs in a published library. ABI selection belongs to the final
application so downstream consumers are not made incompatible accidentally.
