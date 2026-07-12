// 
// 

package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.ResManager;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\u001a\n\u0010\u0002\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0004" }, d2 = { "MM_OF_ONE_INCH", "", "mmToPx", "pxToMM", "onyxsdk-base_release" })
public final class DimensKt
{
    private DimensKt() {
    }

    public static final float MM_OF_ONE_INCH = 25.4f;
    
    public static final float mmToPx(final float $this$mmToPx) {
        return $this$mmToPx * ResManager.getAppContext().getResources().getDisplayMetrics().densityDpi / 25.4f;
    }
    
    public static final float pxToMM(final float $this$pxToMM) {
        return $this$pxToMM / ResManager.getAppContext().getResources().getDisplayMetrics().densityDpi * 25.4f;
    }
}
