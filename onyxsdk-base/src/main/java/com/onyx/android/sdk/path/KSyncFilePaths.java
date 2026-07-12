// 
// 

package com.onyx.android.sdk.path;

import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.LazyKt;
import kotlin.Lazy;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u001b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\t\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\t\u001a\u0004\b\u000f\u0010\fR\u001b\u0010\u0011\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\t\u001a\u0004\b\u0012\u0010\fR\u001b\u0010\u0014\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\t\u001a\u0004\b\u0015\u0010\fR\u001b\u0010\u0017\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\t\u001a\u0004\b\u0018\u0010\fR!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\t\u001a\u0004\b\u001b\u0010\u0007R\u001b\u0010\u001d\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\t\u001a\u0004\b\u001e\u0010\f¨\u0006 " }, d2 = { "Lcom/onyx/android/sdk/path/KSyncFilePaths;", "", "()V", "booxSyncFilePathList", "", "", "getBooxSyncFilePathList", "()Ljava/util/List;", "booxSyncFilePathList$delegate", "Lkotlin/Lazy;", "kSyncAppFilesPath", "getKSyncAppFilesPath", "()Ljava/lang/String;", "kSyncAppFilesPath$delegate", "kSyncCacheDir", "getKSyncCacheDir", "kSyncCacheDir$delegate", "kSyncDataCacheDir", "getKSyncDataCacheDir", "kSyncDataCacheDir$delegate", "kSyncExternalFilesPath", "getKSyncExternalFilesPath", "kSyncExternalFilesPath$delegate", "kSyncFilesPath", "getKSyncFilesPath", "kSyncFilesPath$delegate", "kSyncRootDirList", "getKSyncRootDirList", "kSyncRootDirList$delegate", "phoneAppExternalFilesPath", "getPhoneAppExternalFilesPath", "phoneAppExternalFilesPath$delegate", "onyxsdk-base_release" })
public final class KSyncFilePaths
{
    @NotNull
    public static final KSyncFilePaths INSTANCE;
    @NotNull
    private static final Lazy a;
    @NotNull
    private static final Lazy b;
    @NotNull
    private static final Lazy c;
    @NotNull
    private static final Lazy d;
    @NotNull
    private static final Lazy e;
    @NotNull
    private static final Lazy f;
    @NotNull
    private static final Lazy g;
    @NotNull
    private static final Lazy h;
    
    private KSyncFilePaths() {
    }
    
    static {
        INSTANCE = new KSyncFilePaths();
        a = LazyKt.lazy((Function0)KSyncFilePaths$b.a);
        b = LazyKt.lazy((Function0)KSyncFilePaths$e.a);
        c = LazyKt.lazy((Function0)KSyncFilePaths$f.a);
        d = LazyKt.lazy((Function0)KSyncFilePaths$g.a);
        e = LazyKt.lazy((Function0)KSyncFilePaths$h.a);
        f = LazyKt.lazy((Function0)KSyncFilePaths$a.a);
        g = LazyKt.lazy((Function0)KSyncFilePaths$c.a);
        h = LazyKt.lazy((Function0)KSyncFilePaths$d.a);
    }
    
    @NotNull
    public final String getKSyncAppFilesPath() {
        return (String)KSyncFilePaths.a.getValue();
    }
    
    @NotNull
    public final String getKSyncExternalFilesPath() {
        return (String)KSyncFilePaths.b.getValue();
    }
    
    @NotNull
    public final String getKSyncFilesPath() {
        return (String)KSyncFilePaths.c.getValue();
    }
    
    @NotNull
    public final List<String> getKSyncRootDirList() {
        return (List)KSyncFilePaths.d.getValue();
    }
    
    @NotNull
    public final String getPhoneAppExternalFilesPath() {
        return (String)KSyncFilePaths.e.getValue();
    }
    
    @NotNull
    public final List<String> getBooxSyncFilePathList() {
        return (List)KSyncFilePaths.f.getValue();
    }
    
    @NotNull
    public final String getKSyncCacheDir() {
        return (String)KSyncFilePaths.g.getValue();
    }
    
    @NotNull
    public final String getKSyncDataCacheDir() {
        return (String)KSyncFilePaths.h.getValue();
    }
}
