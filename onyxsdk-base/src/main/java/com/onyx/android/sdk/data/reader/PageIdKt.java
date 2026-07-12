// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.reader;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003" }, d2 = { "isValidId", "", "Lcom/onyx/android/sdk/data/reader/PageId;", "onyxsdk-base_release" })
public final class PageIdKt
{
    private PageIdKt() {
    }

    public static final boolean isValidId(@Nullable final PageId $this$isValidId) {
        return $this$isValidId != null && ((StringsKt.isBlank((CharSequence)$this$isValidId.getPageReferenceId()) ^ true) || (StringsKt.isBlank((CharSequence)$this$isValidId.getPageUUID()) ^ true));
    }
}
