package com.onyx.android.sdk.utils;

import kotlin.jvm.functions.Function0;

final class BitmapLruCacheConfig$a implements Function0<Float> {
    public static final BitmapLruCacheConfig$a a = new BitmapLruCacheConfig$a();
    BitmapLruCacheConfig$a() {}
    public final Float a() { return ResManager.getScreenWidth() * ResManager.getScreenHeight() * 4.0f; }
    @Override public Float invoke() { return a(); }
}
