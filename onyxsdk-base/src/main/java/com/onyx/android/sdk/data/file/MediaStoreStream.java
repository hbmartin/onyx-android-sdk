package com.onyx.android.sdk.data.file;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import androidx.annotation.RequiresApi;
import com.onyx.android.sdk.device.EnvironmentUtil;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TTFFont;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/file/MediaStoreStream.class */
public class MediaStoreStream implements BaseStream {
    private static final String a = EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    @SuppressLint({"SdCardPath"})
    private static final String b = "/mnt/sdcard/";

    @RequiresApi(api = 29)
    private Uri a(String filePath) {
        File file = new File(filePath);
        String name = file.getName();
        String absolutePath = file.getAbsolutePath();
        String strReplace = absolutePath;
        String str = a;
        if (absolutePath.startsWith(str)) {
            strReplace = strReplace.replace(str, TTFFont.UNKNOWN_FONT_NAME);
        } else if (strReplace.startsWith(b)) {
            strReplace = strReplace.replace(b, TTFFont.UNKNOWN_FONT_NAME);
        }
        if (strReplace.endsWith(name)) {
            String str2 = strReplace;
            strReplace = str2.substring(0, str2.lastIndexOf(name));
        }
        ContentValues contentValues = new ContentValues();
        Uri contentUri = MediaStore.Files.getContentUri("external_primary");
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtils.getFileExtension(file));
        Debug.d(getClass(), "mimeType: " + mimeTypeFromExtension + ",relativePath: " + strReplace + ",name: " + name, new Object[0]);
        contentValues.put("_display_name", name);
        contentValues.put("mime_type", mimeTypeFromExtension);
        contentValues.put("relative_path", strReplace);
        contentValues.put("date_added", Long.valueOf(System.currentTimeMillis()));
        return ResManager.getAppContext().getContentResolver().insert(contentUri, contentValues);
    }

    @Override // com.onyx.android.sdk.data.file.BaseStream
    @RequiresApi(api = 29)
    public FileOutputStream getOutputStream(String filePath, boolean clearFile) throws FileNotFoundException {
        if (clearFile) {
            FileUtils.deleteFile(filePath);
        }
        return new FileOutputStream(ResManager.getAppContext().getContentResolver().openFileDescriptor(a(filePath), "rw").getFileDescriptor());
    }
}
