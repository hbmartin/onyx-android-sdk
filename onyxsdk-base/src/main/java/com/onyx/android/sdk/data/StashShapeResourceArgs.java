// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data;

import org.jetbrains.annotations.Nullable;
import java.util.ListIterator;
import java.util.List;
import com.onyx.android.sdk.utils.NumberUtils;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.text.Regex;
import java.io.File;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\b2\b\b\u0002\u0010\u0013\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\bJ\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\bJ\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\bR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u001e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u001e\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000b¨\u0006\u001b" }, d2 = { "Lcom/onyx/android/sdk/data/StashShapeResourceArgs;", "", "()V", "<set-?>", "", "createAt", "getCreateAt", "()J", "", "resourceId", "getResourceId", "()Ljava/lang/String;", "resourcePath", "getResourcePath", "revisionId", "getRevisionId", "shapeId", "getShapeId", "getStashResourceFileName", "suffix", "setCreateAt", "setResourceId", "setResourcePath", "path", "setRevisionId", "setShapeId", "Companion", "onyxsdk-base_release" })
public final class StashShapeResourceArgs
{
    @NotNull
    public static final Companion Companion;
    @NotNull
    public static final String RESOURCE_FILE_SPLIT_CHAR = "#";
    private static final int f = 4;
    @NotNull
    private String a;
    @NotNull
    private String b;
    @NotNull
    private String c;
    private long d;
    @NotNull
    private String e;
    
    public StashShapeResourceArgs() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.e = "";
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final String getShapeId() {
        return this.a;
    }
    
    @NotNull
    public final String getResourceId() {
        return this.b;
    }
    
    @NotNull
    public final String getRevisionId() {
        return this.c;
    }
    
    public final long getCreateAt() {
        return this.d;
    }
    
    @NotNull
    public final String getResourcePath() {
        return this.e;
    }
    
    @NotNull
    public final StashShapeResourceArgs setShapeId(@NotNull final String shapeId) {
        Intrinsics.checkNotNullParameter((Object)shapeId, "shapeId");
        this.a = shapeId;
        return this;
    }
    
    @NotNull
    public final StashShapeResourceArgs setResourceId(@NotNull final String resourceId) {
        Intrinsics.checkNotNullParameter((Object)resourceId, "resourceId");
        this.b = resourceId;
        return this;
    }
    
    @NotNull
    public final StashShapeResourceArgs setRevisionId(@NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        this.c = revisionId;
        return this;
    }
    
    @NotNull
    public final StashShapeResourceArgs setCreateAt(final long createAt) {
        this.d = createAt;
        return this;
    }
    
    @NotNull
    public final StashShapeResourceArgs setResourcePath(@NotNull final String path) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        this.e = path;
        return this;
    }
    
    @NotNull
    public final String getStashResourceFileName(@NotNull final String suffix) {
        Intrinsics.checkNotNullParameter((Object)suffix, "suffix");
        return this.a + '#' + this.b + '#' + this.c + '#' + this.d + suffix;
    }

    public static /* synthetic */ String getStashResourceFileName$default(final StashShapeResourceArgs stashShapeResourceArgs, String suffix, final int i, final Object obj) {
        if ((i & 1) != 0) {
            suffix = "";
        }
        return stashShapeResourceArgs.getStashResourceFileName(suffix);
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/data/StashShapeResourceArgs$Companion;", "", "()V", "RESOURCE_FILE_NAME_SEGMENT_COUNT", "", "RESOURCE_FILE_SPLIT_CHAR", "", "descCompare", "args1", "Lcom/onyx/android/sdk/data/StashShapeResourceArgs;", "args2", "parseArgs", "resourceFile", "Ljava/io/File;", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @Nullable
        public final StashShapeResourceArgs parseArgs(@NotNull final File resourceFile) {
            Intrinsics.checkNotNullParameter((Object)resourceFile, "resourceFile");
            List list2 = null;
            Label_0101: {
                final List split;
                if (!(split = new Regex("#").split((CharSequence)FilesKt.getNameWithoutExtension(resourceFile), 0)).isEmpty()) {
                    final List list = split;
                    final ListIterator listIterator = list.listIterator(list.size());
                    while (listIterator.hasPrevious()) {
                        if (((String)listIterator.previous()).length() != 0) {
                            list2 = CollectionsKt.take((Iterable)split, listIterator.nextIndex() + 1);
                            break Label_0101;
                        }
                    }
                }
                list2 = CollectionsKt.emptyList();
            }
            final Object[] array;
            if ((array = list2.toArray(new String[0])) == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            final String[] array2;
            if ((array2 = (String[])array).length != 4) {
                return null;
            }
            final StashShapeResourceArgs setCreate = new StashShapeResourceArgs().setShapeId(array2[0]).setResourceId(array2[1]).setRevisionId(array2[2]).setCreateAt(NumberUtils.parseLong(array2[3]));
            final String absolutePath = resourceFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue((Object)absolutePath, "resourceFile.absolutePath");
            return setCreate.setResourcePath(absolutePath);
        }
        
        public final int descCompare(@Nullable final StashShapeResourceArgs args1, @Nullable final StashShapeResourceArgs args2) {
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
