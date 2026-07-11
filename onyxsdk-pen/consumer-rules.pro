# JNI resolves these names directly from the recovered Rust library.
-keepclasseswithmembernames class com.onyx.android.sdk.pen.RawInputReader {
    native <methods>;
}

-keepclassmembers class com.onyx.android.sdk.pen.RawInputReader {
    public void onTouchPointReceived(float, float, int, int, int, boolean, boolean, boolean, int, long);
}
