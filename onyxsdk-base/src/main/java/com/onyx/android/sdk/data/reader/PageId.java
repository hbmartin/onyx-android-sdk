// 
// 

package com.onyx.android.sdk.data.reader;

import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00c6\u0003J'\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000f¨\u0006\u001c" }, d2 = { "Lcom/onyx/android/sdk/data/reader/PageId;", "", "pageUUID", "", "pageReferenceId", "pageIndex", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getPageIndex", "()I", "setPageIndex", "(I)V", "getPageReferenceId", "()Ljava/lang/String;", "setPageReferenceId", "(Ljava/lang/String;)V", "getPageUUID", "setPageUUID", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "onyxsdk-base_release" })
public final class PageId
{
    @NotNull
    public static final Companion Companion;
    public static final boolean isEnabledUUID = false;
    @NotNull
    private String a;
    @NotNull
    private String b;
    private int c;
    
    public PageId(@NotNull final String pageUUID, @NotNull final String pageReferenceId, final int pageIndex) {
        Intrinsics.checkNotNullParameter((Object)pageUUID, "pageUUID");
        Intrinsics.checkNotNullParameter((Object)pageReferenceId, "pageReferenceId");
        this.a = pageUUID;
        this.b = pageReferenceId;
        this.c = pageIndex;
    }
    
    public PageId() {
        this("", "", 0);
    }

    public PageId(String pageUUID, String pageReferenceId, int pageIndex, int mask,
                  DefaultConstructorMarker marker) {
        this((mask & 1) != 0 ? "" : pageUUID,
                (mask & 2) != 0 ? "" : pageReferenceId,
                (mask & 4) != 0 ? 0 : pageIndex);
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final String getPageUUID() {
        return this.a;
    }
    
    public final void setPageUUID(@NotNull final String value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.a = value;
    }
    
    @NotNull
    public final String getPageReferenceId() {
        return this.b;
    }
    
    public final void setPageReferenceId(@NotNull final String value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.b = value;
    }
    
    public final int getPageIndex() {
        return this.c;
    }
    
    public final void setPageIndex(final int value) {
        this.c = value;
    }
    
    @NotNull
    public final String component1() {
        return this.a;
    }
    
    @NotNull
    public final String component2() {
        return this.b;
    }
    
    public final int component3() {
        return this.c;
    }
    
    @NotNull
    public final PageId copy(@NotNull final String pageUUID, @NotNull final String pageReferenceId, final int pageIndex) {
        Intrinsics.checkNotNullParameter((Object)pageUUID, "pageUUID");
        Intrinsics.checkNotNullParameter((Object)pageReferenceId, "pageReferenceId");
        return new PageId(pageUUID, pageReferenceId, pageIndex);
    }

    public static /* synthetic */ PageId copy$default(final PageId pageId, String pageUUID, String pageReferenceId, int pageIndex, final int i, final Object obj) {
        if ((i & 1) != 0) {
            pageUUID = pageId.a;
        }
        if ((i & 2) != 0) {
            pageReferenceId = pageId.b;
        }
        if ((i & 4) != 0) {
            pageIndex = pageId.c;
        }
        return pageId.copy(pageUUID, pageReferenceId, pageIndex);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "PageId(pageUUID=" + this.a + ", pageReferenceId=" + this.b + ", pageIndex=" + this.c + ')';
    }
    
    @Override
    public int hashCode() {
        return (this.a.hashCode() * 31 + this.b.hashCode()) * 31 + Integer.hashCode(this.c);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PageId)) {
            return false;
        }
        final PageId pageId = (PageId)other;
        return Intrinsics.areEqual((Object)this.a, (Object)pageId.a) && Intrinsics.areEqual((Object)this.b, (Object)pageId.b) && this.c == pageId.c;
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b" }, d2 = { "Lcom/onyx/android/sdk/data/reader/PageId$Companion;", "", "()V", "isEnabledUUID", "", "generateUUID", "", "kotlin.jvm.PlatformType", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        public final String generateUUID() {
            return "";
        }
    }
}
