// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\n\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000b\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f" }, d2 = { "Lcom/onyx/android/sdk/data/ContentConstants;", "", "()V", "TYPE_GEO_LAYOUT", "", "TYPE_IMPORT_PDF", "TYPE_TEMPLATE_PDF", "isImportPDF", "", "contentType", "isPDF", "isTemplatePDF", "onyxsdk-base_release" })
public final class ContentConstants
{
    @NotNull
    public static final ContentConstants INSTANCE;
    @NotNull
    public static final String TYPE_GEO_LAYOUT = "geo_layout";
    @NotNull
    public static final String TYPE_TEMPLATE_PDF = "pdf";
    @NotNull
    public static final String TYPE_IMPORT_PDF = "import_pdf";
    
    private ContentConstants() {
    }
    
    static {
        INSTANCE = new ContentConstants();
    }
    
    public final boolean isPDF(@Nullable final String contentType) {
        return Intrinsics.areEqual((Object)contentType, (Object)"pdf") || this.isImportPDF(contentType);
    }
    
    public final boolean isImportPDF(@Nullable final String contentType) {
        return Intrinsics.areEqual((Object)contentType, (Object)"import_pdf");
    }
    
    public final boolean isTemplatePDF(@Nullable final String contentType) {
        return Intrinsics.areEqual((Object)contentType, (Object)"pdf");
    }
}
