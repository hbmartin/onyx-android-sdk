// 
// 

package com.onyx.android.sdk.data.note.background;

import com.onyx.android.sdk.utils.DeviceUtils;
import com.onyx.android.sdk.utils.ResManager;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0004R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/data/note/background/BKGroundOrientation;", "", "()V", "LANDSCAPE", "", "PORTRAIT", "defaultOrientation", "onyxsdk-base_release" })
public final class BKGroundOrientation
{
    @NotNull
    public static final BKGroundOrientation INSTANCE;
    @JvmField
    public static int PORTRAIT;
    @JvmField
    public static int LANDSCAPE;
    
    private BKGroundOrientation() {
    }
    
    static {
        INSTANCE = new BKGroundOrientation();
        BKGroundOrientation.LANDSCAPE = 1;
    }
    
    public final int defaultOrientation() {
        return DeviceUtils.isDeviceInLandscapeOrientation(ResManager.getAppContext()) ? BKGroundOrientation.LANDSCAPE : BKGroundOrientation.PORTRAIT;
    }
}
