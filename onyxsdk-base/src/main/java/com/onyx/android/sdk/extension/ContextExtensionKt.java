// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import android.view.ContextThemeWrapper;
import android.app.Activity;
import org.jetbrains.annotations.Nullable;
import android.content.Context;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0002¨\u0006\u0005" }, d2 = { "isActivity", "", "Landroid/content/Context;", "toActivitySafety", "Landroid/app/Activity;", "onyxsdk-base_release" })
public final class ContextExtensionKt
{
    @Nullable
    public static final Activity toActivitySafety(@Nullable Context $this$toActivitySafety) {
        while (!isActivity($this$toActivitySafety)) {
            if ($this$toActivitySafety instanceof ContextThemeWrapper) {
                $this$toActivitySafety = ((ContextThemeWrapper)$this$toActivitySafety).getBaseContext();
            }
            else {
                if (!($this$toActivitySafety instanceof androidx.appcompat.view.ContextThemeWrapper)) {
                    break;
                }
                $this$toActivitySafety = ((androidx.appcompat.view.ContextThemeWrapper)$this$toActivitySafety).getBaseContext();
            }
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
