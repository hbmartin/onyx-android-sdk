// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data;

import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006" }, d2 = { "Lcom/onyx/android/sdk/data/ContentStatus;", "", "()V", "INVISIBLE", "", "VISIBLE", "onyxsdk-base_release" })
public final class ContentStatus
{
    @NotNull
    public static final ContentStatus INSTANCE;
    public static final int VISIBLE = 0;
    public static final int INVISIBLE = 1;
    
    private ContentStatus() {
    }
    
    static {
        INSTANCE = new ContentStatus();
    }
}
