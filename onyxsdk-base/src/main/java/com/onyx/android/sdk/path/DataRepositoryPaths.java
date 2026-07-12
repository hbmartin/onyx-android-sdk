// 
// 

package com.onyx.android.sdk.path;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/path/DataRepositoryPaths;", "", "()V", "appCurUserDataRepositoryDirPath", "", "userId", "appCurUserShareResourceDirPath", "onyxsdk-base_release" })
public final class DataRepositoryPaths
{
    @NotNull
    public static final DataRepositoryPaths INSTANCE;
    
    private DataRepositoryPaths() {
    }
    
    static {
        INSTANCE = new DataRepositoryPaths();
    }
    
    @NotNull
    public final String appCurUserDataRepositoryDirPath(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return CommonPaths.INSTANCE.userDataRepository(AppPaths.INSTANCE.kSyncFilesPath(), userId).toAbsolutePath().toString();
    }
    
    @NotNull
    public final String appCurUserShareResourceDirPath(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return CommonPaths.INSTANCE.userShareResourceDir(AppPaths.INSTANCE.kSyncFilesPath(), userId).toAbsolutePath().toString();
    }
}
