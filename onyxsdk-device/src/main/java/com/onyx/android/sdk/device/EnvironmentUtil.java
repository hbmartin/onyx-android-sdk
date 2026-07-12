// 
// 

package com.onyx.android.sdk.device;

import android.os.StatFs;
import androidx.annotation.NonNull;
import android.os.Environment;
import java.util.Collection;
import com.onyx.android.sdk.api.utils.CollectionUtils;
import android.content.Intent;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import android.util.Log;
import java.util.UUID;
import android.telephony.TelephonyManager;
import android.provider.Settings;
import android.content.Context;
import com.onyx.android.sdk.api.utils.StringUtils;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import java.io.File;

public class EnvironmentUtil
{
    private static final String a = "EnvironmentUtil";
    public static final File EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY;
    public static final File REMOVABLE_SDCARD_ANDROID_DATA_DIRECTORY;
    
    public static File getStorageRootDirectory() {
        return Device.currentDevice.getStorageRootDirectory();
    }
    
    public static File getExternalStorageDirectory() {
        return Device.currentDevice.getExternalStorageDirectory();
    }
    
    public static File getExternalStorageAndroidDataDir() {
        return EnvironmentUtil.EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY;
    }
    
    public static File getExternalStorageAppDataDirectory(final String packageName) {
        return new File(EnvironmentUtil.EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, packageName);
    }
    
    public static File getExternalStorageAppFilesDirectory(final String packageName) {
        return new File(new File(EnvironmentUtil.EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, packageName), "files");
    }
    
    public static File getExternalStorageAppCacheDirectory(final String packageName) {
        return new File(new File(EnvironmentUtil.EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY, packageName), "cache");
    }
    
    @Deprecated
    public static File getRemovableSDCardDirectory() {
        return Device.currentDevice.getRemovableSDCardDirectory();
    }
    
    @Deprecated
    @Nullable
    public static File getValidRemovableSDCardDirectory() {
        final File removableSDCardDirectory;
        if ((removableSDCardDirectory = getRemovableSDCardDirectory()) != null && removableSDCardDirectory.canRead() && removableSDCardDirectory.isDirectory()) {
            return removableSDCardDirectory;
        }
        return null;
    }
    
    public static List<File> getValidRemovableSDCardDirectories() {
        final List<File> removableSDCardDirs = Device.currentDevice().getRemovableSDCardDirs();
        final ArrayList list = new ArrayList();
        if (removableSDCardDirs != null) {
            final Iterator<File> iterator = removableSDCardDirs.iterator();
            while (iterator.hasNext()) {
                final File file;
                if ((file = iterator.next()).canRead() && file.isDirectory()) {
                    list.add(file);
                }
            }
        }
        return list;
    }
    
    public static boolean isFileOnRemovableSDCard(final File file) {
        return Device.currentDevice.isFileOnRemovableSDCard(file);
    }
    
    public static boolean isFileOnExternalStorage(final String path) {
        return !StringUtils.isNullOrEmpty(path) && path.contains(getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static File getRemovableSDCARDAppCacheDirectory(final String packageName) {
        return new File(new File(EnvironmentUtil.REMOVABLE_SDCARD_ANDROID_DATA_DIRECTORY, packageName), "cache");
    }
    
    public static String getDeviceSerial(final Context context) {
        final String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        UUID randomUUID;
        try {
            String deviceId;
            randomUUID = ("9774d56d682e549c".equals(string) ? (((deviceId = ((TelephonyManager)context.getSystemService("phone")).getDeviceId()) != null) ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID()) : UUID.nameUUIDFromBytes(string.getBytes("utf8")));
        }
        catch (final UnsupportedEncodingException ex) {
            Log.w("EnvironmentUtil", "exception", (Throwable)ex);
            randomUUID = UUID.randomUUID();
        }
        return randomUUID.toString();
    }
    
    public static String getRemovableSDCardCid() {
        String deviceDirectory = null;
        final String template = "/sys/block/mmcblk0/device/type";
        for (final String block : new String[] { "mmcblk0", "mmcblk1", "mmcblk2" }) {
            deviceDirectory = getStorageDevice(template.replaceFirst("mmcblk0", block), "sd");
            if (!TextUtils.isEmpty((CharSequence)deviceDirectory) && !TextUtils.isEmpty((CharSequence)deviceDirectory.trim())) {
                break;
            }
        }
        if (TextUtils.isEmpty((CharSequence)deviceDirectory)) {
            return null;
        }
        try (final BufferedReader reader = new BufferedReader(new FileReader(deviceDirectory + "cid"))) {
            final String cid = reader.readLine();
            Log.i("EnvironmentUtil", "SDCard cid:" + cid);
            return cid;
        }
        catch (final Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    public static String getRemovableSDCardCid(final Context context, final String filePath) {
        if (CompatibilityUtil.isApiLevelSatisfied(24)) {
            return Device.currentDevice().getVolumeUUID(context, filePath);
        }
        return getRemovableSDCardCid();
    }
    
    public static List<String> getRemovableSDCardCids(final Context context) {
        final ArrayList list = new ArrayList();
        if (CompatibilityUtil.isApiLevelSatisfied(24)) {
            final Iterator<File> iterator = getValidRemovableSDCardDirectories().iterator();
            while (iterator.hasNext()) {
                list.add(getRemovableSDCardCid(context, iterator.next().getAbsolutePath()));
            }
        }
        else {
            final String removableSDCardCid;
            if (StringUtils.isNotBlank(removableSDCardCid = getRemovableSDCardCid())) {
                list.add(removableSDCardCid);
            }
        }
        return list;
    }
    
    public static String getStorageDevice(final String devicePath, final String deviceType) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(devicePath))) {
            final String type = reader.readLine();
            if (type != null && type.toLowerCase().contentEquals(deviceType)) {
                final String replaceAll = devicePath.replaceAll("type", "");
                reader.close();
                return replaceAll;
            }
        }
        catch (final Exception error) {
            error.printStackTrace();
        }
        return null;
    }
    
    public static boolean isExternalStorageDirectory(final String path) {
        return path.equals(getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static boolean isExternalStorageDirectory(final Context context, final Intent intent) {
        return isExternalStorageDirectory(com.onyx.android.sdk.device.a.b(context, intent.getData()));
    }
    
    public static boolean isRemovableSDDirectory(final String path) {
        final List<File> validRemovableSDCardDirectories;
        if (!CollectionUtils.isNullOrEmpty(validRemovableSDCardDirectories = getValidRemovableSDCardDirectories())) {
            final Iterator<File> iterator = validRemovableSDCardDirectories.iterator();
            while (iterator.hasNext()) {
                if (StringUtils.safelyEquals(path, iterator.next().getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isRemovableSDDirectory(final Context context, final Intent intent) {
        return isRemovableSDDirectory(com.onyx.android.sdk.device.a.b(context, intent.getData()));
    }
    
    public static File getExternalStorageDownloadDirectory() {
        return new File(getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
    }
    
    public static File getExternalStorageBooksDirectory() {
        return new File(getExternalStorageDirectory(), "Books");
    }
    
    public static File getExternalStorageDocumentDirectory() {
        return new File(getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS);
    }
    
    public static boolean isStorageRootDirectory(final String path) {
        return getStorageRootDirectory().getAbsolutePath().contains(path);
    }
    
    public static boolean isStorageSDCardDirectory(@NonNull final String path) {
        return isExternalStorageDirectory(path) || isRemovableSDDirectory(path);
    }
    
    private static File a(final File storageRootDir) {
        return new File(new File(storageRootDir, "Android"), "data");
    }
    
    public static long getTotalInternalMemorySize() {
        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getBlockCount() * (long)statFs.getBlockSize();
    }
    
    static {
        EXTERNAL_STORAGE_ANDROID_DATA_DIRECTORY = a(getExternalStorageDirectory());
        REMOVABLE_SDCARD_ANDROID_DATA_DIRECTORY = a(getRemovableSDCardDirectory());
    }
}

