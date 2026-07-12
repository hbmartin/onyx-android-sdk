// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.note;

import android.content.pm.PackageManager;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.Benchmark;
import com.onyx.android.sdk.utils.BitmapUtils;
import com.onyx.android.sdk.utils.StringUtils;
import android.graphics.Bitmap;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.ResManager;
import java.io.File;
import android.content.Context;
import com.onyx.android.sdk.path.NoteCommonPaths;

public class NoteThumbnailUtils
{
    public static String THUMBNAIL_FILE_EXTENSION = ".png";
    
    public static String thumbnailBasePath() {
        return NoteCommonPaths.INSTANCE.getNoteThumbnailDir();
    }
    
    public static String thumbnailPath(final ThumbnailRes thumbnailRes, final String id) {
        if (thumbnailRes != null && thumbnailRes.isCoverThumbnail()) {
            return thumbnailRes.value;
        }
        return thumbnailPath((Context)null, id);
    }
    
    public static String thumbnailPath(final Context context, final String id) {
        return thumbnailBasePath() + File.separator + id + NoteThumbnailUtils.THUMBNAIL_FILE_EXTENSION;
    }
    
    public static boolean hasThumbnailFile(final String documentId) {
        return FileUtils.fileExist(thumbnailPath(ResManager.getAppContext(), documentId));
    }
    
    public static boolean saveThumbnailWithSize(final Context context, final String documentUniqueId, final Bitmap src) {
        if (StringUtils.isNullOrEmpty(documentUniqueId) || src == null) {
            return false;
        }
        if (!BitmapUtils.isValid(src)) {
            return false;
        }
        final String thumbnailPath = thumbnailPath(context, documentUniqueId);
        final Benchmark benchmark = new Benchmark();
        final boolean saveBitmap = BitmapUtils.saveBitmap(src, thumbnailPath);
        if (benchmark.duration() > 1000L) {
            Debug.e((Class)NoteThumbnailUtils.class, "save thumbnail use long time: " + benchmark.duration() + "ms", new Object[0]);
        }
        return saveBitmap;
    }
    
    public static Bitmap loadThumbnail(final Context context, final String documentUniqueId) {
        return BitmapUtils.loadBitmapFromFile(thumbnailPath(context, documentUniqueId));
    }
    
    public static String oldThumbnailPath(final Context context, final String id) {
        return oldThumbnailBasePath(context) + "/" + id + ".png";
    }
    
    public static boolean hasThumbnail(final Context context, final String documentUniqueId) {
        return FileUtils.fileExist(thumbnailPath(context, documentUniqueId));
    }
    
    public static boolean saveThumbnail(final Context context, final String documentUniqueId, final Bitmap bitmap) {
        return !StringUtils.isNullOrEmpty(documentUniqueId) && bitmap != null && BitmapUtils.saveBitmap(bitmap, thumbnailPath(context, documentUniqueId));
    }
    
    public static void removeAllThumbnails(final Context context) {
        FileUtils.purgeDirectory(new File(thumbnailBasePath()));
    }
    
    public static String oldThumbnailBasePath(Context context) {
        String dataDir = context.getApplicationInfo().dataDir;
        try {
            dataDir = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        String path = dataDir + "/thumbnails";
        if (!FileUtils.fileExist(path)) {
            FileUtils.mkdirs(path);
        }
        return path;
    }
}
