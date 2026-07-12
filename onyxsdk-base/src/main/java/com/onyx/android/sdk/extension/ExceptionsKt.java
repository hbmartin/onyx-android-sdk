// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import java.nio.file.FileAlreadyExistsException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003" }, d2 = { "isFileAlreadyExistsException", "", "Ljava/io/IOException;", "onyxsdk-base_release" })
public final class ExceptionsKt
{
    public static final boolean isFileAlreadyExistsException(@NotNull final IOException $this$isFileAlreadyExistsException) {
        Intrinsics.checkNotNullParameter((Object)$this$isFileAlreadyExistsException, "<this>");
        return $this$isFileAlreadyExistsException instanceof FileAlreadyExistsException || $this$isFileAlreadyExistsException instanceof kotlin.io.FileAlreadyExistsException;
    }
}
