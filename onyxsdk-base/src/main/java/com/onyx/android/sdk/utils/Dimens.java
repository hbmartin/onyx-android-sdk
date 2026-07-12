// 
// 

package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.base.utils.ResManager;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006" }, d2 = { "Lcom/onyx/android/sdk/utils/Dimens;", "", "()V", "dp2PxFloat", "", "dp", "onyxsdk-base_release" })
public final class Dimens
{
    @NotNull
    public static final Dimens INSTANCE;
    
    private Dimens() {
    }
    
    static {
        INSTANCE = new Dimens();
    }
    
    public final float dp2PxFloat(final float dp) {
        return dp * ResManager.INSTANCE.getAppContext().getResources().getDisplayMetrics().density + 0.5f;
    }
}
