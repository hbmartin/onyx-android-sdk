// 
// 

package com.onyx.android.sdk.data.note.background;

import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b" }, d2 = { "Lcom/onyx/android/sdk/data/note/background/BKGroundScaleType;", "", "()V", "FIT_CENTER", "", "FIT_XY", "ORIGIN", "SPLICE", "onyxsdk-base_release" })
public final class BKGroundScaleType
{
    @NotNull
    public static final BKGroundScaleType INSTANCE;
    public static final int FIT_XY = 0;
    public static final int FIT_CENTER = 1;
    public static final int SPLICE = 2;
    public static final int ORIGIN = 3;
    
    private BKGroundScaleType() {
    }
    
    static {
        INSTANCE = new BKGroundScaleType();
    }
}
