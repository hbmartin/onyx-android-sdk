// 
// 

package com.onyx.android.sdk.path;

import kotlin.jvm.internal.Intrinsics;
import java.io.File;
import com.onyx.android.sdk.device.EnvironmentUtil;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b/\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u00101\u001a\u00020\u00042\u0006\u00102\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b0\u0010\u0006¨\u00063" }, d2 = { "Lcom/onyx/android/sdk/path/NoteCommonPaths;", "", "()V", "backupLocalSaveDir", "", "getBackupLocalSaveDir", "()Ljava/lang/String;", "copyPointsDir", "getCopyPointsDir", "customBackgroundDir", "getCustomBackgroundDir", "customCloudBackgroundDir", "getCustomCloudBackgroundDir", "draftPageThumbnailDir", "getDraftPageThumbnailDir", "imageCachePath", "getImageCachePath", "imageSavePath", "getImageSavePath", "jsonCachePath", "getJsonCachePath", "linkDataDir", "getLinkDataDir", "noteCacheDir", "getNoteCacheDir", "noteCloudBackupDir", "getNoteCloudBackupDir", "noteCloudBackupTempDir", "getNoteCloudBackupTempDir", "noteExportPath", "getNoteExportPath", "noteImageCacheDir", "getNoteImageCacheDir", "noteMergeExportPath", "getNoteMergeExportPath", "notePageSnapshotDataDir", "getNotePageSnapshotDataDir", "notePageThumbnailDir", "getNotePageThumbnailDir", "noteThumbnailDir", "getNoteThumbnailDir", "privateClipboardDataDir", "getPrivateClipboardDataDir", "renderCacheDir", "getRenderCacheDir", "shapeThumbnailDir", "getShapeThumbnailDir", "zoomThumbnailDir", "getZoomThumbnailDir", "zoomThumbnailPath", "documentId", "onyxsdk-base_release" })
public final class NoteCommonPaths
{
    @NotNull
    public static final NoteCommonPaths INSTANCE;
    
    private NoteCommonPaths() {
    }
    
    static {
        INSTANCE = new NoteCommonPaths();
    }
    
    @NotNull
    public final String getNoteCacheDir() {
        return EnvironmentUtil.getExternalStorageDirectory().getPath() + (Object)File.separator + ".noteCache";
    }
    
    @NotNull
    public final String getNoteThumbnailDir() {
        return this.getNoteCacheDir() + (Object)File.separator + "thumbnail";
    }
    
    @NotNull
    public final String getNotePageThumbnailDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("page").toString();
    }
    
    @NotNull
    public final String getDraftPageThumbnailDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("draftPage").toString();
    }
    
    @NotNull
    public final String getNoteImageCacheDir() {
        return this.getNoteCacheDir() + (Object)File.separator + "images";
    }
    
    @NotNull
    public final String getShapeThumbnailDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("shape").toString();
    }
    
    @NotNull
    public final String getZoomThumbnailDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("zoom").toString();
    }
    
    @NotNull
    public final String getCopyPointsDir() {
        return this.getNoteCacheDir() + (Object)File.separator + "CopyPoints";
    }
    
    @NotNull
    public final String getLinkDataDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("LINK_DATA").append((Object)separator).toString();
    }
    
    @NotNull
    public final String getPrivateClipboardDataDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("PRIVATE_CLIPBOARD_DATA").append((Object)separator).toString();
    }
    
    @NotNull
    public final String getNotePageSnapshotDataDir() {
        final StringBuilder append = new StringBuilder().append(this.getNoteCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append("note_page_snapshot_data_dir").append((Object)separator).toString();
    }
    
    @NotNull
    public final String getBackupLocalSaveDir() {
        return Intrinsics.stringPlus(EnvironmentUtil.getExternalStorageDirectory().getPath(), (Object)"/note/backup/local/");
    }
    
    @NotNull
    public final String getRenderCacheDir() {
        return Intrinsics.stringPlus(this.getNoteCacheDir(), (Object)"/textRender/");
    }
    
    @NotNull
    public final String getCustomBackgroundDir() {
        return EnvironmentUtil.getExternalStorageDirectory().getPath() + (Object)File.separator + "noteTemplate";
    }
    
    @NotNull
    public final String getCustomCloudBackgroundDir() {
        final StringBuilder append = new StringBuilder().append(EnvironmentUtil.getExternalStorageDirectory().getPath());
        final String separator = File.separator;
        return append.append((Object)separator).append("noteTemplate").append((Object)separator).append("cloud").toString();
    }
    
    @NotNull
    public final String getNoteCloudBackupDir() {
        return this.getNoteCacheDir() + (Object)File.separator + "cloud_backup";
    }
    
    @NotNull
    public final String getNoteCloudBackupTempDir() {
        return this.getNoteCacheDir() + (Object)File.separator + "cloud_backup_temp";
    }
    
    @NotNull
    public final String getNoteExportPath() {
        final StringBuilder append = new StringBuilder().append(EnvironmentUtil.getExternalStorageDirectory().getPath());
        final String separator = File.separator;
        return append.append((Object)separator).append("note").append((Object)separator).toString();
    }
    
    @NotNull
    public final String getNoteMergeExportPath() {
        return this.getNoteExportPath() + "mergeExport" + (Object)File.separator;
    }
    
    @NotNull
    public final String getJsonCachePath() {
        final StringBuilder append = new StringBuilder().append(KSyncFilePaths.INSTANCE.getKSyncCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append(".onyx").append((Object)separator).append("json").toString();
    }
    
    @NotNull
    public final String getImageCachePath() {
        final StringBuilder append = new StringBuilder().append(KSyncFilePaths.INSTANCE.getKSyncCacheDir());
        final String separator = File.separator;
        return append.append((Object)separator).append(".onyx").append((Object)separator).append("image").toString();
    }
    
    @NotNull
    public final String getImageSavePath() {
        final String separator;
        return EnvironmentUtil.getExternalStorageDirectory().getPath() + (Object)(separator = File.separator) + "Pictures" + (Object)separator + "NeoReader" + (Object)separator;
    }
    
    @NotNull
    public final String zoomThumbnailPath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return this.getZoomThumbnailDir() + (Object)File.separator + documentId;
    }
}
