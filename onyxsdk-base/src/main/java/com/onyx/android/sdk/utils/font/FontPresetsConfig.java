// 
// 

package com.onyx.android.sdk.utils.font;

import kotlin.collections.CollectionsKt;
import java.util.List;
import android.annotation.SuppressLint;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\b¨\u0006\u0010" }, d2 = { "Lcom/onyx/android/sdk/utils/font/FontPresetsConfig;", "", "()V", "FONT_DIR", "", "NOTE_FONT_PATHS", "", "getNOTE_FONT_PATHS", "()Ljava/util/List;", "NotoSansCJK", "NotoSansSymbols", "NotoSerif", "NotoSerifCJK", "OnyxCustomFont", "READER_FONT_PATHS", "getREADER_FONT_PATHS", "onyxsdk-base_release" })
public final class FontPresetsConfig
{
    @NotNull
    public static final FontPresetsConfig INSTANCE;
    @SuppressLint({ "SdCardPath" })
    @NotNull
    public static final String FONT_DIR = "/sdcard/fonts/";
    @NotNull
    private static final String a = "/system/fonts/NotoSerif-Regular.ttf";
    @NotNull
    private static final String b = "/system/fonts/OnyxCustomFont-Regular.otf";
    @NotNull
    private static final String c = "/system/fonts/NotoSansCJK-Regular.ttc";
    @NotNull
    private static final String d = "/system/fonts/NotoSerifCJK-Regular.ttc";
    @NotNull
    private static final String e = "/system/fonts/NotoSansSymbols-Regular-Subsetted.ttf";
    @NotNull
    private static final List<String> f;
    @NotNull
    private static final List<String> g;
    
    private FontPresetsConfig() {
    }
    
    static {
        INSTANCE = new FontPresetsConfig();
        f = CollectionsKt.mutableListOf(new String[] { "/system/fonts/NotoSerif-Regular.ttf", "/system/fonts/OnyxCustomFont-Regular.otf", "/system/fonts/NotoSansCJK-Regular.ttc", "/system/fonts/NotoSerifCJK-Regular.ttc", "/system/fonts/NotoSansSymbols-Regular-Subsetted.ttf" });
        g = CollectionsKt.mutableListOf(new String[] { "/system/fonts/NotoSerif-Regular.ttf", "/system/fonts/NotoSansCJK-Regular.ttc", "/system/fonts/NotoSansSymbols-Regular-Subsetted.ttf", "/system/fonts/NotoSerifCJK-Regular.ttc" });
    }
    
    @NotNull
    public final List<String> getREADER_FONT_PATHS() {
        return FontPresetsConfig.f;
    }
    
    @NotNull
    public final List<String> getNOTE_FONT_PATHS() {
        return FontPresetsConfig.g;
    }
}
