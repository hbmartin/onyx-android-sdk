
package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.content.Intent;
import androidx.annotation.RequiresApi;
import android.os.storage.StorageVolume;
import java.util.Collection;
import com.onyx.android.sdk.data.VolumeInfo;
import android.content.Context;
import java.text.DecimalFormat;
import androidx.annotation.Nullable;
import java.math.BigDecimal;
import android.os.Environment;
import android.util.Log;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import android.os.storage.StorageManager;
import com.onyx.android.sdk.device.EnvironmentUtil;
import android.os.StatFs;
import java.util.List;
import com.onyx.android.sdk.device.Device;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.lang.reflect.Method;

public class StorageUtils
{
    public static final String TAG = "StorageUtils";
    private static final Class<?> a;
    private static final Class<?> b;
    private static final Method c;
    private static final Method d;
    private static final Method e;
    private static final Method f;
    private static final Method g;
    private static final Method h;
    private static final Method i;
    private static final Method j;
    private static final Method k;
    private static final Class<?> l;
    private static final Method m;
    private static final Method n;
    private static final Pattern o;
    public static final int STATE_UNMOUNTED = 0;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_EMULATED = 2;
    public static final int ACTUAL_SIZE_FORMAT_BASE = 1024;
    public static final int DISPLAY_SIZE_FORMAT_BASE = 1000;
    public static final long ACTUAL_MB = 1048576L;
    public static final long DISPLAY_MB = 1000000L;
    public static final long DISPLAY_GB = 1000000000L;
    public static final long[] DISPLAY_SIZE_IN_GB;
    private static final String[] p;
    private static final String q = "#";
    
    public static long getTotalStorageAmount() {
        final ArrayList pathList = new ArrayList();
        pathList.add("/system");
        pathList.add("/data");
        pathList.add("/cache");
        pathList.add(Device.currentDevice().getExternalStorageDirectory().getAbsolutePath());
        return getStorageAmountForPartitions(pathList);
    }
    
    public static long getStorageAmountForPartitions(final List<String> pathList) {
        final int size = pathList.size();
        long n = 0L;
        for (int i = 0; i < size; ++i) {
            final StatFs statFs = new StatFs((String)pathList.get(i));
            long n2;
            if (CompatibilityUtil.apiLevelCheck(18)) {
                n2 = statFs.getBlockSizeLong() * statFs.getBlockCountLong();
            }
            else {
                n2 = statFs.getBlockSize() * (long)statFs.getBlockCount();
            }
            n += n2;
        }
        return n;
    }
    
    public static long getPrimaryStorageSize() {
        final StorageManager storageManager = getStorageManager(ResManager.getAppContext());
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely((Class)storageManager.getClass(), "getPrimaryStorageSize", new Class[0]), (Object)storageManager, new Object[0])) instanceof Long) {
            return roundStorageSize((long)invokeMethodSafely);
        }
        return roundStorageSize(getStorageTotalSpace(EnvironmentUtil.getExternalStorageDirectory()));
    }
    
    public static long getExtsdStorageAmount() {
        final ArrayList pathList = new ArrayList();
        final Iterator iterator = EnvironmentUtil.getValidRemovableSDCardDirectories().iterator();
        while (iterator.hasNext()) {
            pathList.add(((File)iterator.next()).getAbsolutePath());
        }
        return getStorageAmountForPartitions(pathList);
    }
    
    public static long convertBytesToMB(final long bytes) {
        return bytes / 1048576L;
    }
    
    public static long getDisplayGBForUser(final double bytes) {
        return Math.round(bytes / 1000.0 / 1000.0 / 1000.0);
    }
    
    public static long getTotalStorageAmountInGB() {
        return getDisplayGBForUser((double)getTotalStorageAmount());
    }
    
    public static double convertByteToDesireFormat(final double bytes, final SIZE_FORMAT format, final VALUE_USAGE usage) {
        return bytes / Math.pow((usage == VALUE_USAGE.ACTUAL) ? 1024 : 1000, format.ordinal() + 1);
    }
    
    public static long getSDCardFreeBytes() {
        return getFreeBytes(Device.currentDevice.getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static long getExtsdStorageFreeBytes() {
        long n = 0L;
        final Iterator iterator = EnvironmentUtil.getValidRemovableSDCardDirectories().iterator();
        while (iterator.hasNext()) {
            n += getFreeBytes(((File)iterator.next()).getAbsolutePath());
        }
        return n;
    }
    
    public static boolean isSDCardFreeByteEnough(final long allocBytes, final long limitBytes) {
        return getSDCardFreeBytes() - allocBytes > limitBytes;
    }
    
    public static long getFreeBytes(final String path) {
        final long n = 0L;
        if (!new File(path).exists()) {
            return n;
        }
        final StatFs statFs = new StatFs(path);
        long availableBytes;
        if (CompatibilityUtil.apiLevelCheck(18)) {
            availableBytes = statFs.getAvailableBytes();
        }
        else {
            availableBytes = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        }
        final long n2 = availableBytes;
        Log.i(StorageUtils.TAG, "blocks: " + statFs.getAvailableBlocksLong() + " |block size:" + statFs.getBlockSizeLong() + " |amount: " + availableBytes / 1024L / 1024L + "MB");
        return n2;
    }
    
    public static long getTotalBytes(final String path) {
        final long n = 0L;
        if (!new File(path).exists()) {
            return n;
        }
        final StatFs statFs = new StatFs(path);
        long totalBytes;
        if (CompatibilityUtil.apiLevelCheck(18)) {
            totalBytes = statFs.getTotalBytes();
        }
        else {
            totalBytes = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        }
        final long n2 = totalBytes;
        Log.i(StorageUtils.TAG, "blocks: " + statFs.getBlockCountLong() + " |block size:" + statFs.getBlockSizeLong() + " |amount: " + totalBytes / 1024L / 1024L + "MB");
        return n2;
    }
    
    public static long getInternalFreeBytes() {
        return getFreeBytes(Environment.getDataDirectory().getAbsolutePath());
    }
    
    public static BigDecimal getFreeStorageInGB() {
        return new BigDecimal(getSDCardFreeBytes() / 1.073741824E9).setScale(2, 4);
    }
    
    public static boolean isInInternalStorage(final File directory) {
        return directory != null && !StringUtils.isNullOrEmpty(directory.getAbsolutePath()) && directory.getAbsolutePath().startsWith(Device.currentDevice().getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static long roundStorageSize(final long size) {
        return roundStorageSizeInGB(size);
    }
    
    public static long roundStorageSizeInGB(final long size) {
        if (ArraysUtils.isOutOfValue(StorageUtils.DISPLAY_SIZE_IN_GB, size)) {
            return size;
        }
        int n = 0;
        long n2 = Long.MAX_VALUE;
        long[] display_SIZE_IN_GB;
        long abs;
        for (int i = 0; i < (display_SIZE_IN_GB = StorageUtils.DISPLAY_SIZE_IN_GB).length; ++i, n2 = abs) {
            if ((abs = Math.abs(size - display_SIZE_IN_GB[i])) < n2) {
                n = i;
            }
            else {
                abs = n2;
            }
        }
        return display_SIZE_IN_GB[n];
    }
    
    public static String getFormattedStorageSpaceStr(final double bytes, @Nullable String decimalFormatPattern, final boolean forDisplay) {
        if (bytes <= 0.0) {
            return "0";
        }
        if (StringUtils.isNullOrEmpty(decimalFormatPattern)) {
            decimalFormatPattern = "#";
        }
        int n;
        if (forDisplay) {
            n = 1000;
        }
        else {
            n = 1024;
        }
        int n2 = 0;
        double n4;
        for (double n3 = bytes; n3 > (n4 = n); n3 /= n4, ++n2) {}
        final StringBuilder sb = new StringBuilder();
        final DecimalFormat decimalFormat = new java.text.DecimalFormat();
        final DecimalFormat decimalFormat2 = decimalFormat;
        final double a = n4;
        final int n5 = n2;
        new DecimalFormat(decimalFormatPattern);
        return sb.append(decimalFormat.format(bytes / Math.pow(a, n5))).append(StorageUtils.p[n2]).toString();
    }
    
    public static String formatStorage(final long bytes) {
        return getFormattedStorageSpaceStr((double)bytes, "#.#", true);
    }
    
    public static long getStorageTotalSpace(final File storageFile) {
        long a = storageFile.getTotalSpace();
        if (EnvironmentUtil.isFileOnExternalStorage(storageFile.getAbsolutePath())) {
            a = Math.max(a, getFlashTotalSpace());
        }
        return a;
    }
    
    public static long getFlashTotalSpace() {
        long totalBlocks = 0L;
        long emmcBlocks = 0L;
        long sdaBlocks = 0L;
        boolean hasSda = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/partitions"), 8192)) {
            reader.readLine();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, line);
                String[] fields = line.trim().split("\\s+");
                if (fields.length < 4) {
                    continue;
                }
                String deviceName = fields[3].trim();
                if (deviceName.contains("sda")) {
                    hasSda = true;
                }
                if (deviceName.equals("mmcblk0")) {
                    emmcBlocks = Long.parseLong(fields[2]);
                }
                else if (deviceName.contains("sda")) {
                    sdaBlocks = Math.max(sdaBlocks, Long.parseLong(fields[2]));
                }
            }
            totalBlocks = hasSda ? sdaBlocks : emmcBlocks;
            Log.d(TAG, "totalFlashSize = " + totalBlocks);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return totalBlocks * 1000L;
    }
    
    public static StorageManager getStorageManager(final Context context) {
        return (StorageManager)context.getSystemService((Class)StorageManager.class);
    }
    
    public static void mount(final Context context, final String volId) {
        ReflectUtil.invokeMethodSafely(StorageUtils.d, (Object)getStorageManager(context), new Object[] { volId });
    }
    
    public static void unmount(final Context context, final String volId) {
        ReflectUtil.invokeMethodSafely(StorageUtils.c, (Object)getStorageManager(context), new Object[] { volId });
    }
    
    public static void format(final Context context, final String volId) {
        ReflectUtil.invokeMethodSafely(StorageUtils.e, (Object)getStorageManager(context), new Object[] { volId });
    }
    
    public static void partitionPublic(final Context context, final String Id) {
        ReflectUtil.invokeMethodSafely(StorageUtils.f, (Object)getStorageManager(context), new Object[] { Id });
    }
    
    @Nullable
    public static List<VolumeInfo> getVolumes(final Context context) {
        List<VolumeInfo> volumes = null;
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(StorageUtils.g, (Object)getStorageManager(context), new Object[0])) instanceof List) {
            for (Object next : (List)invokeMethodSafely) {
                final File file;
                if ((file = (File)ReflectUtil.invokeMethodSafely(StorageUtils.h, next, new Object[0])) == null) {
                    continue;
                }
                final VolumeInfo volumeInfo = new com.onyx.android.sdk.data.VolumeInfo();
                final VolumeInfo volumeInfo2 = volumeInfo;
                final File file2 = file;
                new VolumeInfo();
                volumeInfo.path = file2.getAbsolutePath();
                volumeInfo.type = (int)ReflectUtil.invokeMethodSafely(StorageUtils.j, next, new Object[0]);
                volumeInfo.state = (int)ReflectUtil.invokeMethodSafely(StorageUtils.i, next, new Object[0]);
                volumes = CollectionUtils.ensureList(volumes);
                CollectionUtils.safeAdd(volumes, volumeInfo2);
                Debug.d(StorageUtils.TAG + " get storage volume:" + next + "\r\n" + volumeInfo2);
            }
        }
        return volumes;
    }
    
    public static boolean isMounted(final int state) {
        return state == 2 || state == 3;
    }
    
    public static boolean isUnmounted(final int state) {
        return state == 0 || state == 6;
    }
    
    @RequiresApi(api = 24)
    public static StorageVolume getStorageVolume(final File file) {
        return getStorageManager(ResManager.getAppContext()).getStorageVolume(file);
    }
    
    public static boolean isPrimaryVolume(final File file) {
        if (CompatibilityUtil.apiLevelCheck(24)) {
            final StorageVolume storageVolume;
            return (storageVolume = getStorageVolume(file)) != null && storageVolume.isPrimary();
        }
        return FileUtils.safelyEqualsIgnoreCase(file.getAbsolutePath(), EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static Intent getViewDocumentUri(final File directory) {
        final Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(getDocumentAndroidDirUri(directory), "vnd.android.document/directory");
        return intent;
    }
    
    @SuppressLint({ "NewApi" })
    public static Uri getDocumentAndroidDirUri(final File directory) {
        String uuid;
        if (isPrimaryVolume(directory)) {
            uuid = "primary";
        }
        else {
            uuid = getStorageVolume(directory).getUuid();
        }
        return new Uri.Builder().scheme("content").authority("com.android.externalstorage.documents").appendPath("document").appendPath(uuid + ":" + StringUtils.replaceFirst(directory.getAbsolutePath(), FileUtils.getRootDirectory(directory).getAbsolutePath() + "/", "")).build();
    }
    
    public static boolean isRestrictedAndroidDir(final File directory) {
        return isRestrictedAndroidDir(FileUtils.getAbsolutePath(directory));
    }
    
    public static boolean isRestrictedAndroidDir(final String path) {
        return CompatibilityUtil.apiLevelCheck(31) && StorageUtils.o.matcher(FileUtils.formatFileAbsolutePath(path, "")).matches();
    }
    
    @Nullable
    private static Object a(final String path) {
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(StorageUtils.g, (Object)getStorageManager(ResManager.getAppContext()), new Object[0])) instanceof List) {
            for (final Object next : (List)invokeMethodSafely) {
                final File file;
                if ((file = (File)ReflectUtil.invokeMethodSafely(StorageUtils.h, next, new Object[0])) == null) {
                    continue;
                }
                if (StringUtils.safelyEquals(path, file.getAbsolutePath())) {
                    return a(next);
                }
            }
        }
        return null;
    }
    
    private static Object a(Object volumeInfo) {
        if ((volumeInfo = ReflectUtil.invokeMethodSafely(StorageUtils.k, volumeInfo, new Object[0])) != null && StorageUtils.l.isInstance(volumeInfo)) {
            return volumeInfo;
        }
        return null;
    }
    
    public static boolean isUsb(final String path) {
        return c(a(path));
    }
    
    private static boolean c(final Object diskInfo) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(StorageUtils.m, diskInfo, new Object[0])) != null && b;
    }
    
    public static boolean isSd(final String path) {
        return b(a(path));
    }
    
    private static boolean b(final Object diskInfo) {
        final Boolean b;
        return (b = (Boolean)ReflectUtil.invokeMethodSafely(StorageUtils.n, diskInfo, new Object[0])) != null && b;
    }
    
    static {
        a = StorageManager.class;
        final Class<?> clazz = b = ReflectUtil.classForName("android.os.storage.VolumeInfo");
        c = ReflectUtil.getMethodSafely((Class)StorageManager.class, "unmount", new Class[] { String.class });
        d = ReflectUtil.getMethodSafely((Class)StorageManager.class, "mount", new Class[] { String.class });
        e = ReflectUtil.getMethodSafely((Class)StorageManager.class, "format", new Class[] { String.class });
        f = ReflectUtil.getMethodSafely((Class)StorageManager.class, "partitionPublic", new Class[] { String.class });
        g = ReflectUtil.getMethodSafely((Class)StorageManager.class, "getVolumes", new Class[0]);
        h = ReflectUtil.getMethodSafely((Class)clazz, "getPath", new Class[0]);
        i = ReflectUtil.getMethodSafely((Class)clazz, "getState", new Class[0]);
        j = ReflectUtil.getMethodSafely((Class)clazz, "getType", new Class[0]);
        k = ReflectUtil.getMethodSafely((Class)clazz, "getDisk", new Class[0]);
        final Class<?> clazz2 = l = ReflectUtil.classForName("android.os.storage.DiskInfo");
        m = ReflectUtil.getMethodSafely((Class)clazz2, "isUsb", new Class[0]);
        n = ReflectUtil.getMethodSafely((Class)clazz2, "isSd", new Class[0]);
        o = Pattern.compile("^/Android/(?:data|obb|sandbox)(?:/.+)?", 2);
        DISPLAY_SIZE_IN_GB = new long[] { 128000000L, 256000000L, 512000000L, 1000000000L, 2000000000L, 4000000000L, 8000000000L, 16000000000L, 32000000000L, 64000000000L, 128000000000L, 256000000000L, 512000000000L };
        p = new String[] { "B", "KB", "MB", "GB", "TB" };
    }
    
    public enum VALUE_USAGE
    {
        ACTUAL, 
        DISPLAY;
    }
    
    public enum SIZE_FORMAT
    {
        KB, 
        MB, 
        GB;
    }
}
