// 
// 

package com.onyx.android.sdk.utils.broadcast;

import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005" }, d2 = { "Lcom/onyx/android/sdk/utils/broadcast/LibraryBroadcastUtils;", "", "()V", "ACTION_DUMP_LIBRARY_TREE", "", "onyxsdk-base_release" })
public final class LibraryBroadcastUtils
{
    @NotNull
    public static final LibraryBroadcastUtils INSTANCE;
    @NotNull
    public static final String ACTION_DUMP_LIBRARY_TREE = "com.onyx.action.library.dump_library_tree";
    
    private LibraryBroadcastUtils() {
    }
    
    static {
        INSTANCE = new LibraryBroadcastUtils();
    }
}
