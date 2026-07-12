// 
// 

package com.onyx.android.sdk.path;

import kotlin.jvm.JvmStatic;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import java.io.File;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/path/KReaderPathCommonUtils;", "", "()V", "isLocalKSyncBookPath", "", "path", "", "onyxsdk-base_release" })
public final class KReaderPathCommonUtils
{
    @NotNull
    public static final KReaderPathCommonUtils INSTANCE;
    
    private KReaderPathCommonUtils() {
    }
    
    @JvmStatic
    public static final boolean isLocalKSyncBookPath(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        if (path.length() == 0) {
            return false;
        }
        final String separator;
        final String stringPlus;
        if (!path.startsWith(stringPlus = Intrinsics.stringPlus(KSyncFilePaths.INSTANCE.getKSyncFilesPath(), (Object)(separator = File.separator)))) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue((Object)(path = path.substring(stringPlus.length())), "this as java.lang.String).substring(startIndex)");
        final String s = path;
        final String s2 = separator;
        Intrinsics.checkNotNullExpressionValue((Object)s2, "separator");
        final List split = new Regex(s2).split((CharSequence)s, 0);
        final String s3 = (String)CollectionsKt.getOrNull(split, 0);
        path = (String)CollectionsKt.getOrNull(split, 1);
        final String s4 = (String)CollectionsKt.getOrNull(split, 2);
        return Intrinsics.areEqual((Object)"reader", (Object)path);
    }
    
    static {
        INSTANCE = new KReaderPathCommonUtils();
    }
}
