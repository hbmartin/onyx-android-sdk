// 
// 

package com.onyx.android.sdk.data;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0018\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u0019\u001a\u00020\u00002\b\u0010\u0011\u001a\u0004\u0018\u00010\tJ\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\tJ\u0010\u0010\u001b\u001a\u00020\u00002\b\u0010\u0015\u001a\u0004\u0018\u00010\tJ\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u000eR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\"\u0010\f\u001a\u0004\u0018\u00010\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u001e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\u0004\u0018\u00010\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000bR\u001e\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000bR\"\u0010\u0015\u001a\u0004\u0018\u00010\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000b¨\u0006\u001f" }, d2 = { "Lcom/onyx/android/sdk/data/ShapeDocumentArgs;", "", "()V", "<set-?>", "", "createAt", "getCreateAt", "()J", "debugInfo", "", "getDebugInfo", "()Ljava/lang/String;", "documentId", "getDocumentId", "", "isSaveExportRecord", "()Z", "pageId", "getPageId", "pbDirPath", "getPbDirPath", "revisionId", "getRevisionId", "setCreateAt", "setDocumentId", "setPageId", "setPbDirPath", "setRevisionId", "setSaveExportRecord", "save", "Companion", "onyxsdk-base_release" })
public final class ShapeDocumentArgs
{
    @NotNull
    public static final Companion Companion;
    @Nullable
    private String a;
    @Nullable
    private String b;
    @Nullable
    private String c;
    private long d;
    private boolean e;
    @NotNull
    private String f;
    
    public ShapeDocumentArgs() {
        final String syncDocDirFilePath;
        Intrinsics.checkNotNullExpressionValue((Object)(syncDocDirFilePath = KSyncConstant.syncDocDirFilePath()), "syncDocDirFilePath()");
        this.f = syncDocDirFilePath;
    }
    
    @JvmStatic
    public static final int compare(@Nullable final ShapeDocumentArgs args1, @Nullable final ShapeDocumentArgs args2) {
        return ShapeDocumentArgs.Companion.compare(args1, args2);
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @Nullable
    public final String getDocumentId() {
        return this.a;
    }
    
    @Nullable
    public final String getRevisionId() {
        return this.b;
    }
    
    @Nullable
    public final String getPageId() {
        return this.c;
    }
    
    public final long getCreateAt() {
        return this.d;
    }
    
    public final boolean isSaveExportRecord() {
        return this.e;
    }
    
    @NotNull
    public final String getPbDirPath() {
        return this.f;
    }
    
    @NotNull
    public final ShapeDocumentArgs setDocumentId(@Nullable final String documentId) {
        this.a = documentId;
        return this;
    }
    
    @NotNull
    public final ShapeDocumentArgs setRevisionId(@Nullable final String revisionId) {
        this.b = revisionId;
        return this;
    }
    
    @NotNull
    public final ShapeDocumentArgs setPageId(@Nullable final String pageId) {
        this.c = pageId;
        return this;
    }
    
    @NotNull
    public final ShapeDocumentArgs setCreateAt(final long createAt) {
        this.d = createAt;
        return this;
    }
    
    @NotNull
    public final ShapeDocumentArgs setPbDirPath(@NotNull final String pbDirPath) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        this.f = pbDirPath;
        return this;
    }
    
    @NotNull
    public final ShapeDocumentArgs setSaveExportRecord(final boolean save) {
        this.e = save;
        return this;
    }
    
    @NotNull
    public final String getDebugInfo() {
        return "Args{documentId='" + (Object)this.a + "', revisionId='" + (Object)this.b + "', pageId='" + (Object)this.c + "', createAt=" + this.d + '}';
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J\u001a\u0010\b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¨\u0006\t" }, d2 = { "Lcom/onyx/android/sdk/data/ShapeDocumentArgs$Companion;", "", "()V", "compare", "", "args1", "Lcom/onyx/android/sdk/data/ShapeDocumentArgs;", "args2", "descCompare", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @JvmStatic
        public final int compare(@Nullable final ShapeDocumentArgs args1, @Nullable final ShapeDocumentArgs args2) {
            if (args1 == null && args2 == null) {
                return 0;
            }
            if (args1 == null) {
                return -1;
            }
            if (args2 == null) {
                return 1;
            }
            return Long.compare(args1.getCreateAt(), args2.getCreateAt());
        }
        
        public final int descCompare(@Nullable final ShapeDocumentArgs args1, @Nullable final ShapeDocumentArgs args2) {
            if (args1 == null && args2 == null) {
                return 0;
            }
            if (args1 == null) {
                return 1;
            }
            if (args2 == null) {
                return -1;
            }
            return -Intrinsics.compare(args1.getCreateAt(), args2.getCreateAt());
        }
    }
}
