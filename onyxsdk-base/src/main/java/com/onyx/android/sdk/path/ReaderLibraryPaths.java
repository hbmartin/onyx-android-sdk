// 
// 

package com.onyx.android.sdk.path;

import kotlin.jvm.internal.Intrinsics;
import java.io.File;
import com.onyx.android.sdk.device.EnvironmentUtil;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b" }, d2 = { "Lcom/onyx/android/sdk/path/ReaderLibraryPaths;", "", "()V", "PATH_RECYCLE_BIN", "", "getPATH_RECYCLE_BIN", "()Ljava/lang/String;", "RECYCLE_BIN_DIR_NAME", "getRecycleBinFile", "Ljava/io/File;", "fileName", "onyxsdk-base_release" })
public final class ReaderLibraryPaths
{
    @NotNull
    public static final ReaderLibraryPaths INSTANCE;
    @NotNull
    private static final String a = ".LibraryRecycleBin";
    @NotNull
    private static final String b;
    
    private ReaderLibraryPaths() {
    }
    
    static {
        INSTANCE = new ReaderLibraryPaths();
        b = EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath() + (Object)File.separator + ".LibraryRecycleBin";
    }
    
    @NotNull
    public final String getPATH_RECYCLE_BIN() {
        return ReaderLibraryPaths.b;
    }
    
    @NotNull
    public final File getRecycleBinFile(@NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return new File(ReaderLibraryPaths.b, fileName);
    }
}
