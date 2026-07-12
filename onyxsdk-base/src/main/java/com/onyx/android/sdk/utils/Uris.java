// 
// 

package com.onyx.android.sdk.utils;

import kotlin.jvm.internal.Intrinsics;
import android.net.Uri;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t" }, d2 = { "Lcom/onyx/android/sdk/utils/Uris;", "", "()V", "FILE_ASSET_URI", "", "PATH_ASSET_URI_PREFIX", "assetFileUri", "Landroid/net/Uri;", "fileName", "onyxsdk-base_release" })
public final class Uris
{
    @NotNull
    public static final Uris INSTANCE;
    @NotNull
    public static final String PATH_ASSET_URI_PREFIX = "/android_asset/";
    @NotNull
    public static final String FILE_ASSET_URI = "file:///android_asset/";
    
    private Uris() {
    }
    
    static {
        INSTANCE = new Uris();
    }
    
    @NotNull
    public final Uri assetFileUri(@NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        final Uri parse = Uri.parse(Intrinsics.stringPlus("file:///android_asset/", (Object)fileName));
        Intrinsics.checkNotNullExpressionValue((Object)parse, "parse(\"$FILE_ASSET_URI$fileName\")");
        return parse;
    }
}
