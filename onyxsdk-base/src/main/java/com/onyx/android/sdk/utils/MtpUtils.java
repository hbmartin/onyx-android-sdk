package com.onyx.android.sdk.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.onyx.android.sdk.device.Device;
import java.io.File;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/MtpUtils.class */
public class MtpUtils {
    private static final String a = "android.media.IMediaScannerService";
    private static final String b = "com.android.providers.media";
    private static final String c = "com.android.providers.media.MediaScannerService";
    private static final String d = "volume";
    private static final String e = "external";
    private static final String f = "internal";

    public static boolean saveBitmapToFile(Context context, Bitmap bitmap, File file) {
        boolean zSaveBitmapToFile = FileUtils.saveBitmapToFile(bitmap, file, Bitmap.CompressFormat.PNG, 100);
        if (zSaveBitmapToFile) {
            updateMtpDb(context, file);
        }
        return zSaveBitmapToFile;
    }

    public static boolean appendContentToFile(Context context, String content, File fileForSave) {
        boolean zAppendContentToFile;
        try {
            byte[] bytes = content.getBytes(StringUtils.UTF8);
            zAppendContentToFile = FileUtils.appendContentToFile(fileForSave.getAbsolutePath(), bytes, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            zAppendContentToFile = false;
        }
        if (zAppendContentToFile) {
            updateMtpDb(context, fileForSave);
        }
        return zAppendContentToFile;
    }

    public static boolean saveContentToFile(Context context, String content, File fileForSave) {
        boolean zSaveContentToFile = FileUtils.saveContentToFile(content, fileForSave);
        if (zSaveContentToFile) {
            updateMtpDb(context, fileForSave);
        }
        return zSaveContentToFile;
    }

    public static void updateMtpDb(Context context, File file) {
        Device.currentDevice.updateMtpDb(context, file);
    }

    public static boolean deleteFile(Context context, String path) {
        return deleteFile(context, new File(path));
    }

    public static void scanExternalFiles(Context context) {
        a(context, e);
    }

    private static void a(Context context, String volume) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(b, c));
        intent.putExtra(d, volume);
        context.startService(intent);
    }

    public static void updateMtpDb(Context context, String filePath) {
        updateMtpDb(context, new File(filePath));
    }

    public static boolean deleteFile(Context context, File file) {
        boolean zDeleteFile = FileUtils.deleteFile(file);
        if (zDeleteFile) {
            updateMtpDb(context, file);
        }
        return zDeleteFile;
    }

    public static boolean deleteFile(Context context, File file, boolean recursive) {
        boolean zDeleteFile = FileUtils.deleteFile(file, recursive);
        if (zDeleteFile) {
            updateMtpDb(context, file);
        }
        return zDeleteFile;
    }
}
