// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.base.extension;

import android.view.ViewParent;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.Nullable;
import android.webkit.WebView;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003" }, d2 = { "safelyDestroy", "", "Landroid/webkit/WebView;", "onyxsdk-base_release" })
public final class WebViewKt
{
    public static final void safelyDestroy(@Nullable final WebView $this$safelyDestroy) {
        if ($this$safelyDestroy == null) {
            return;
        }
        final ViewParent parent;
        if ((parent = $this$safelyDestroy.getParent()) != null) {
            ((ViewGroup)parent).removeView((View)$this$safelyDestroy);
        }
        $this$safelyDestroy.destroy();
    }
}
