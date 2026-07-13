# JNI resolves these names directly from the recovered Rust libraries.
-keepclasseswithmembernames class com.onyx.android.sdk.pen.RawInputReader {
    native <methods>;
}

-keepclassmembers class com.onyx.android.sdk.pen.RawInputReader {
    public void onTouchPointReceived(float, float, int, int, int, boolean, boolean, boolean, int, long);
}

-keepclasseswithmembernames class com.onyx.android.sdk.pen.NeoPenNative {
    native <methods>;
}

-keepclasseswithmembernames class com.onyx.android.sdk.pen.NeoPenWrapper {
    native <methods>;
}

# libneo_pen.so resolves these classes and members by name at runtime
# (FindClass/NewObject/GetFieldID): result construction, render point
# field writes, and NeoPenConfig field reads.
-keep class com.onyx.android.sdk.pen.NeoRenderPoint {
    <init>();
    <fields>;
}

-keep class com.onyx.android.sdk.pen.PenInk {
    <init>(float[], int[], android.graphics.Bitmap[]);
}

-keep class com.onyx.android.sdk.pen.NeoPenResult {
    <init>(com.onyx.android.sdk.pen.PenInk, com.onyx.android.sdk.pen.PenInk);
}

-keep class com.onyx.android.sdk.pen.NeoPenConfig {
    <fields>;
}

# PenBrushResult bridges to Kotlin UByte accessors whose names are part of the
# recovered binary contract.
-keep class com.onyx.android.sdk.pen.PenBrushInk { *; }
