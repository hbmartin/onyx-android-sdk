// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import java.util.Iterator;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import java.util.List;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.extension.AnyKt;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.functions.Function0;
import java.io.File;
import kotlin.text.StringsKt;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u0016\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u001e\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0002J\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u001e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004J\u001c\u0010\u0019\u001a\u00020\u00042\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001b2\u0006\u0010\u0018\u001a\u00020\u0004J\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u001d2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u0016\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001e\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u0018\u0010 \u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u001e\u0010!\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u0012\u0010\"\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0012\u0010\u0013\u001a\u00020\u0006*\u00020\u00042\u0006\u0010#\u001a\u00020\u0004J\u0012\u0010$\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\n\u0010%\u001a\u00020\u0006*\u00020\u0006J\n\u0010&\u001a\u00020\u0006*\u00020\u0006J\u0012\u0010'\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001a\u0010'\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J\u001a\u0010)\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J\u0012\u0010*\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006+" }, d2 = { "Lcom/onyx/android/sdk/path/TemplatePaths;", "", "()V", "TEMPLATE_SUFFIX", "", "appDocPageTemplateJsonPath", "Ljava/nio/file/Path;", "documentId", "pageId", "appDocTemplateJsonDir", "appUserNoteTemplateResDir", "userId", "appUserNoteTemplateResPath", "title", "appUserNoteTemplateThumbnailResDir", "appUserNoteTemplateThumbnailResPath", "docNoteTemplateResDir", "docNoteTemplateResFile", "fileName", "docPageTemplateJsonPath", "dirPath", "docTemplateJsonDir", "docTemplateResDir", "searchTemplatePath", "resRelativePath", "templatePathByDirList", "dirPathList", "", "templatePathDirList", "", "userNoteTemplateResDir", "userNoteTemplateResPath", "userNoteTemplateThumbnailResDir", "userNoteTemplateThumbnailResPath", "commonTemplateResAbsolutePath", "newPageId", "docTemplateResAbsolutePath", "template", "templateRes", "templateResAbsolutePath", "docId", "templateResExportPath", "templateResRelativePath", "onyxsdk-base_release" })
public final class TemplatePaths
{
    @NotNull
    public static final TemplatePaths INSTANCE;
    @NotNull
    private static final String a = "template_json";
    
    private TemplatePaths() {
    }
    
    private final Path b(final String dirPath, final String userId) {
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                CommonPaths.INSTANCE.thumbnail(this.userNoteTemplateResDir(dirPath, userId)));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    private final Path a(final String dirPath, final String documentId) {
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.json(this.template(instance.docDir(dirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    static {
        INSTANCE = new TemplatePaths();
    }
    
    @NotNull
    public final Path template(@NotNull final Path $this$template) {
        Intrinsics.checkNotNullParameter((Object)$this$template, "<this>");
        return Paths.INSTANCE.append($this$template, "template");
    }
    
    @NotNull
    public final Path templateRes(@NotNull final Path $this$templateRes) {
        Intrinsics.checkNotNullParameter((Object)$this$templateRes, "<this>");
        return Paths.INSTANCE.append($this$templateRes, "templateRes");
    }
    
    @NotNull
    public final String templateResRelativePath(@NotNull final String $this$templateResRelativePath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)$this$templateResRelativePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final String string;
        if ($this$templateResRelativePath.startsWith(string = AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString())) {
            final String relativePath = Paths.INSTANCE.relativePath($this$templateResRelativePath, string);
            final String separator = File.separator;
            Intrinsics.checkNotNullExpressionValue((Object)separator, "separator");
            int position = relativePath.indexOf(separator);
            return position < 0 ? relativePath : relativePath.substring(position + separator.length());
        }
        return Paths.INSTANCE.relativePath($this$templateResRelativePath, CommonPaths.INSTANCE.userDir(userId).toAbsolutePath().toString());
    }
    
    @NotNull
    public final String templateResAbsolutePath(@NotNull final String $this$templateResAbsolutePath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)$this$templateResAbsolutePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return Paths.INSTANCE.absolutePath($this$templateResAbsolutePath, CommonPaths.INSTANCE.userDir(userId).toAbsolutePath().toString());
    }
    
    @NotNull
    public final String templateResAbsolutePath(@NotNull final String $this$templateResAbsolutePath, @NotNull final String userId, @NotNull final String docId) {
        Intrinsics.checkNotNullParameter((Object)$this$templateResAbsolutePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)docId, "docId");
        final String filesDirAbsolutePath = AppPaths.INSTANCE.filesDirAbsolutePath($this$templateResAbsolutePath);
        final com.onyx.android.sdk.extension.Files instance;
        if ((instance = com.onyx.android.sdk.extension.Files.INSTANCE).isFileExist(filesDirAbsolutePath)) {
            return filesDirAbsolutePath;
        }
        String docTemplateResAbsolutePath;
        if (!instance.isFileExist(docTemplateResAbsolutePath = this.docTemplateResAbsolutePath($this$templateResAbsolutePath, docId))) {
            docTemplateResAbsolutePath = null;
        }
        return AnyKt.getOrElse(docTemplateResAbsolutePath,
                new TemplatePaths$b($this$templateResAbsolutePath, userId));
    }
    
    @NotNull
    public final String commonTemplateResAbsolutePath(@NotNull final String $this$commonTemplateResAbsolutePath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)$this$commonTemplateResAbsolutePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final String filesDirAbsolutePath = AppPaths.INSTANCE.filesDirAbsolutePath($this$commonTemplateResAbsolutePath);
        if (com.onyx.android.sdk.extension.Files.INSTANCE.isFileExist(filesDirAbsolutePath)) {
            return filesDirAbsolutePath;
        }
        return this.templateResAbsolutePath($this$commonTemplateResAbsolutePath, userId);
    }
    
    @NotNull
    public final String templateResExportPath(@NotNull final String $this$templateResExportPath, @NotNull final String dirPath, @NotNull final String docId) {
        Intrinsics.checkNotNullParameter((Object)$this$templateResExportPath, "<this>");
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)docId, "docId");
        return Paths.INSTANCE.append(CommonPaths.INSTANCE.docDir(dirPath, docId), $this$templateResExportPath).toAbsolutePath().toString();
    }
    
    @NotNull
    public final Path docTemplateResDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                this.templateRes(CommonPaths.INSTANCE.note(AppPaths.INSTANCE.appDocDir(documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appUserNoteTemplateResDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.userNoteTemplateResDir(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path docNoteTemplateResFile(@NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.docNoteTemplateResDir(documentId), fileName);
    }
    
    @NotNull
    public final Path docNoteTemplateResDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                this.templateRes(CommonPaths.INSTANCE.note(AppPaths.INSTANCE.appDocDir(documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appUserNoteTemplateThumbnailResDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.b(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path appUserNoteTemplateResPath(@NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.appUserNoteTemplateResDir(userId), title);
    }
    
    @NotNull
    public final Path appUserNoteTemplateThumbnailResPath(@NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.appUserNoteTemplateThumbnailResDir(userId), title);
    }
    
    @NotNull
    public final Path userNoteTemplateThumbnailResPath(@NotNull final String dirPath, @NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.b(dirPath, userId), title);
    }
    
    @NotNull
    public final Path userNoteTemplateResPath(@NotNull final String dirPath, @NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.userNoteTemplateResDir(dirPath, userId), title);
    }
    
    @NotNull
    public final Path userNoteTemplateResDir(@NotNull final String dirPath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                this.templateRes(CommonPaths.INSTANCE.userNoteDir(dirPath, userId)));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appDocTemplateJsonDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return this.a(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId);
    }
    
    @NotNull
    public final Path docPageTemplateJsonPath(@NotNull final String dirPath, @NotNull final String documentId, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        final Paths instance = Paths.INSTANCE;
        return instance.createParentDirectories(instance.append(this.a(dirPath, documentId), Intrinsics.stringPlus(pageId, (Object)".template_json")), (FileAttribute<?>[])new FileAttribute[0]);
    }
    
    @NotNull
    public final Path appDocPageTemplateJsonPath(@NotNull final String documentId, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        return this.docPageTemplateJsonPath(AppPaths.INSTANCE.appDocDir(), documentId, pageId);
    }
    
    @NotNull
    public final String docTemplateResAbsolutePath(@NotNull final String $this$docTemplateResAbsolutePath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)$this$docTemplateResAbsolutePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return Paths.INSTANCE.absolutePath($this$docTemplateResAbsolutePath, AppPaths.INSTANCE.appDocDir(documentId).toAbsolutePath().toString());
    }
    
    @NotNull
    public final Path docPageTemplateJsonPath(@NotNull final String $this$docPageTemplateJsonPath, @NotNull final String newPageId) {
        Intrinsics.checkNotNullParameter((Object)$this$docPageTemplateJsonPath, "<this>");
        Intrinsics.checkNotNullParameter((Object)newPageId, "newPageId");
        final Paths instance = Paths.INSTANCE;
        final String parent = FileUtils.getParent($this$docPageTemplateJsonPath);
        Intrinsics.checkNotNullExpressionValue((Object)parent, "getParent(this)");
        return instance.createParentDirectories(instance.append(instance.toPath(parent), Intrinsics.stringPlus(newPageId, (Object)".template_json")), (FileAttribute<?>[])new FileAttribute[0]);
    }
    
    @NotNull
    public final List<Path> templatePathDirList(@NotNull final String userId, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return CollectionsKt.mutableListOf(new Path[] { AppPaths.INSTANCE.appDocDir(documentId), CommonPaths.INSTANCE.userDir(userId) });
    }
    
    @NotNull
    public final String templatePathByDirList(@NotNull final List<? extends Path> dirPathList, @NotNull final String resRelativePath) {
        Intrinsics.checkNotNullParameter((Object)dirPathList, "dirPathList");
        Intrinsics.checkNotNullParameter((Object)resRelativePath, "resRelativePath");
        final ArrayList list = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)dirPathList, 10));
        final Iterator<? extends Path> iterator = dirPathList.iterator();
        while (iterator.hasNext()) {
            list.add(Paths.INSTANCE.append(iterator.next(), resRelativePath).toAbsolutePath().toString());
        }
        final Iterator iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            final Object next;
            if (com.onyx.android.sdk.extension.Files.INSTANCE.isFileExist((String)(next = iterator2.next()))) {
                return AnyKt.getOrElse((String) next, new TemplatePaths$a(resRelativePath));
            }
        }
        return AppPaths.INSTANCE.filesDirAbsolutePath(resRelativePath);
    }
    
    @NotNull
    public final String searchTemplatePath(@NotNull final String userId, @NotNull final String documentId, @NotNull final String resRelativePath) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)resRelativePath, "resRelativePath");
        return this.templatePathByDirList(this.templatePathDirList(userId, documentId), resRelativePath);
    }
}
