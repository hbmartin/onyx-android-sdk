// 
// 

package com.onyx.android.sdk.extension;

import android.app.Activity;
import org.jetbrains.annotations.Nullable;
import android.content.Context;
import android.content.ContextWrapper;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0002¨\u0006\u0005" }, d2 = { "isActivity", "", "Landroid/content/Context;", "toActivitySafety", "Landroid/app/Activity;", "onyxsdk-base_release" })
public final class ContextExtensionKt
{
    private ContextExtensionKt() {
    }

    @Nullable
    public static final Activity toActivitySafety(@Nullable Context $this$toActivitySafety) {
        while (!isActivity($this$toActivitySafety)
                && $this$toActivitySafety instanceof ContextWrapper) {
            Context base = ((ContextWrapper)$this$toActivitySafety).getBaseContext();
            if (base == null || base == $this$toActivitySafety) {
                break;
            }
            $this$toActivitySafety = base;
        }
        if (!isActivity($this$toActivitySafety)) {
            return null;
        }
        if ($this$toActivitySafety != null) {
            return (Activity)$this$toActivitySafety;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.app.Activity");
    }
    
    public static final boolean isActivity(@Nullable final Context $this$isActivity) {
        return $this$isActivity instanceof Activity;
    }
}
