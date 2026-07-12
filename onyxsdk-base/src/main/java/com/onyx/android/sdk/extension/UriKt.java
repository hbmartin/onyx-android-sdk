// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.UriUtils;
import org.jetbrains.annotations.Nullable;
import org.apache.commons.io.FilenameUtils;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.functions.Function0;
import android.webkit.MimeTypeMap;
import android.database.Cursor;
import android.os.CancellationSignal;
import android.content.Context;
import kotlin.jvm.internal.Intrinsics;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import android.net.Uri;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0002\u001a\f\u0010\u000b\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0002¨\u0006\u000e" }, d2 = { "getDefaultName", "", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "getFileExtension", "getFileName", "getFileSize", "", "isValidURI", "", "isWebsiteOrFile", "toFile", "Ljava/io/File;", "onyxsdk-base_release" })
public final class UriKt
{
    @NotNull
    public static final File toFile(@NotNull final Uri $this$toFile) {
        Intrinsics.checkNotNullParameter((Object)$this$toFile, "<this>");
        if (!Intrinsics.areEqual((Object)$this$toFile.getScheme(), (Object)"file")) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Uri lacks 'file' scheme: ", (Object)$this$toFile).toString());
        }
        final String path;
        if ((path = $this$toFile.getPath()) != null) {
            return new File(path);
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Uri path is null: ", (Object)$this$toFile).toString());
    }
    
    public static final long getFileSize(@NotNull final Uri $this$getFileSize, @NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)$this$getFileSize, "<this>");
        Intrinsics.checkNotNullParameter((Object)context, "context");
        final String scheme;
        if ((scheme = $this$getFileSize.getScheme()) != null) {
            final int hashCode;
            if ((hashCode = scheme.hashCode()) != 3143036) {
                if (hashCode == 951530617) {
                    if (scheme.equals("content")) {
                        long long1 = 0L;
                        final Cursor query;
                        if ((query = context.getContentResolver().query($this$getFileSize, (String[])null, (String)null, (String[])null, (String)null, (CancellationSignal)null)) != null) {
                            final Cursor cursor = query;
                            cursor.moveToFirst();
                            long1 = cursor.getLong(cursor.getColumnIndex("_size"));
                            cursor.close();
                        }
                        return long1;
                    }
                }
            }
            else if (scheme.equals("file")) {
                return toFile($this$getFileSize).length();
            }
        }
        return 0L;
    }
    
    @Nullable
    public static final String getFileExtension(@NotNull final Uri $this$getFileExtension, @NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)$this$getFileExtension, "<this>");
        Intrinsics.checkNotNullParameter((Object)context, "context");
        return AnyKt.getOrElseNull(MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType($this$getFileExtension)), (kotlin.jvm.functions.Function0<? extends String>)new Function0<String>() {
            @Nullable
            public final String invoke() {
                return FilenameUtils.getExtension($this$getFileExtension.getLastPathSegment());
            }
        });
    }
    
    @NotNull
    public static final String getFileName(@NotNull final Uri $this$getFileName, @NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)$this$getFileName, "<this>");
        Intrinsics.checkNotNullParameter((Object)context, "context");
        final String scheme;
        if ((scheme = $this$getFileName.getScheme()) != null) {
            final int hashCode;
            if ((hashCode = scheme.hashCode()) != 3143036) {
                if (hashCode == 951530617) {
                    if (scheme.equals("content")) {
                        String s = null;
                        final Cursor query;
                        if ((query = context.getContentResolver().query($this$getFileName, (String[])null, (String)null, (String[])null, (String)null, (CancellationSignal)null)) != null) {
                            final Cursor cursor = query;
                            cursor.moveToFirst();
                            s = cursor.getString(cursor.getColumnIndex("_display_name"));
                            cursor.close();
                        }
                        if (s == null) {
                            s = getDefaultName($this$getFileName, context);
                        }
                        return s;
                    }
                }
            }
            else if (scheme.equals("file")) {
                final String s2;
                Intrinsics.checkNotNullExpressionValue((Object)(s2 = toFile($this$getFileName).getName()), "{\n            toFile().name\n        }");
                return s2;
            }
        }
        return getDefaultName($this$getFileName, context);
    }
    
    @NotNull
    public static final String getDefaultName(@NotNull final Uri $this$getDefaultName, @NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)$this$getDefaultName, "<this>");
        Intrinsics.checkNotNullParameter((Object)context, "context");
        return new StringBuilder().append(System.currentTimeMillis()).append('.').append((Object)MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType($this$getDefaultName))).append('}').toString();
    }
    
    public static final boolean isValidURI(@NotNull final Uri $this$isValidURI) {
        Intrinsics.checkNotNullParameter((Object)$this$isValidURI, "<this>");
        final String scheme;
        return ((scheme = $this$isValidURI.getScheme()) == null || scheme.length() == 0) ^ true;
    }
    
    public static final boolean isWebsiteOrFile(@Nullable final String $this$isWebsiteOrFile) {
        return UriUtils.isWebsiteUri($this$isWebsiteOrFile) || UriUtils.isFileUri($this$isWebsiteOrFile);
    }
}
