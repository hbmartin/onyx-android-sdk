# JNI resolves these names directly from the recovered Rust library.
-keepclasseswithmembernames class com.onyx.android.sdk.pen.RawInputReader {
    native <methods>;
}

-keepclassmembers class com.onyx.android.sdk.pen.RawInputReader {
    public void onTouchPointReceived(float, float, int, int, int, boolean, boolean, boolean, int, long);
}

# PenBrushResult bridges to Kotlin UByte accessors whose names are part of the
# recovered binary contract.
-keep class com.onyx.android.sdk.pen.PenBrushInk { *; }
