// 
// Decompiled by Procyon v0.6.0
// 

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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: lstore_0       
        //     2: lconst_0       
        //     3: lstore_2       
        //     4: lconst_0       
        //     5: lstore          4
        //     7: ldc_w           "/proc/partitions"
        //    10: astore          6
        //    12: aconst_null    
        //    13: astore          7
        //    15: iconst_0       
        //    16: istore          8
        //    18: new             Ljava/io/FileReader;
        //    21: dup            
        //    22: astore          9
        //    24: aload           6
        //    26: invokespecial   java/io/FileReader.<init>:(Ljava/lang/String;)V
        //    29: new             Ljava/io/BufferedReader;
        //    32: dup            
        //    33: dup2           
        //    34: astore          6
        //    36: aload           9
        //    38: sipush          8192
        //    41: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;I)V
        //    44: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    47: pop            
        //    48: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    51: pop            
        //    52: aload           6
        //    54: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    57: dup            
        //    58: astore          7
        //    60: ifnull          186
        //    63: aload           7
        //    65: getstatic       com/onyx/android/sdk/utils/StorageUtils.TAG:Ljava/lang/String;
        //    68: aload           7
        //    70: invokestatic    android/util/Log.d:(Ljava/lang/String;Ljava/lang/String;)I
        //    73: pop            
        //    74: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    77: ldc_w           "\\s+"
        //    80: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    83: dup            
        //    84: astore          7
        //    86: arraylength    
        //    87: iconst_4       
        //    88: if_icmpge       94
        //    91: goto            52
        //    94: aload           7
        //    96: iconst_3       
        //    97: aaload         
        //    98: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   101: ldc_w           "sda"
        //   104: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   107: ifeq            113
        //   110: iconst_1       
        //   111: istore          8
        //   113: aload           7
        //   115: iconst_3       
        //   116: aaload         
        //   117: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   120: ldc_w           "mmcblk0"
        //   123: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   126: ifeq            140
        //   129: aload           7
        //   131: iconst_2       
        //   132: aaload         
        //   133: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //   136: lstore_2       
        //   137: goto            52
        //   140: aload           7
        //   142: iconst_3       
        //   143: aaload         
        //   144: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   147: ldc_w           "sda"
        //   150: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   153: ifeq            52
        //   156: lload           4
        //   158: aload           7
        //   160: iconst_2       
        //   161: aaload         
        //   162: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //   165: dup2           
        //   166: lstore          10
        //   168: lcmp           
        //   169: ifge            175
        //   172: goto            179
        //   175: lload           4
        //   177: lstore          10
        //   179: lload           10
        //   181: lstore          4
        //   183: goto            52
        //   186: iload           8
        //   188: ifeq            194
        //   191: goto            197
        //   194: lload_2        
        //   195: lstore          4
        //   197: aload           9
        //   199: getstatic       com/onyx/android/sdk/utils/StorageUtils.TAG:Ljava/lang/String;
        //   202: new             Ljava/lang/StringBuilder;
        //   205: dup            
        //   206: invokespecial   java/lang/StringBuilder.<init>:()V
        //   209: ldc_w           "totalFlashSize = "
        //   212: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   215: lload           4
        //   217: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   220: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   223: invokestatic    android/util/Log.d:(Ljava/lang/String;Ljava/lang/String;)I
        //   226: pop            
        //   227: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   230: goto            264
        //   233: goto            256
        //   236: lload_0        
        //   237: lstore          4
        //   239: goto            256
        //   242: aload           7
        //   244: astore          9
        //   246: goto            271
        //   249: aload           7
        //   251: lload_0        
        //   252: lstore          4
        //   254: astore          9
        //   256: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   259: aload           9
        //   261: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   264: lload           4
        //   266: ldc2_w          1000
        //   269: lmul           
        //   270: lreturn        
        //   271: aload           9
        //   273: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   276: athrow         
        //    StackMapTable: 00 10 FF 00 34 00 07 04 04 04 07 01 A4 00 01 07 01 A1 00 00 FF 00 29 00 07 04 04 04 07 01 A4 07 01 B6 01 07 01 A1 00 00 12 1A FF 00 22 00 07 04 04 04 07 01 A4 00 01 07 01 A1 00 00 FF 00 03 00 09 04 04 00 00 07 01 A4 00 01 07 01 A1 04 00 00 FF 00 06 00 08 00 00 04 04 00 00 01 07 01 A1 00 00 FF 00 07 00 09 00 00 04 00 00 00 00 00 07 01 A1 00 00 FF 00 02 00 09 00 00 00 00 04 00 00 00 07 01 A1 00 00 63 07 01 CE FF 00 02 00 09 04 00 00 00 00 00 00 00 07 01 A1 00 01 07 01 CE FF 00 05 00 08 00 00 00 00 00 00 00 07 01 A1 00 01 07 01 D0 FF 00 06 00 07 04 00 00 00 00 00 07 01 A1 00 01 07 01 CE FF 00 06 00 09 00 00 00 00 04 00 00 00 07 01 A1 00 01 07 01 CE FF 00 07 00 05 00 00 00 00 04 00 00 FF 00 06 00 0A 00 00 00 00 00 00 00 00 00 07 01 A1 00 01 07 01 D0
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  18     21     249    256    Ljava/lang/Exception;
        //  18     21     242    249    Any
        //  24     29     249    256    Ljava/lang/Exception;
        //  24     29     242    249    Any
        //  29     32     236    242    Ljava/lang/Exception;
        //  29     32     271    277    Any
        //  36     47     236    242    Ljava/lang/Exception;
        //  36     47     271    277    Any
        //  48     51     236    242    Ljava/lang/Exception;
        //  48     51     271    277    Any
        //  52     57     236    242    Ljava/lang/Exception;
        //  52     57     271    277    Any
        //  63     73     236    242    Ljava/lang/Exception;
        //  63     73     271    277    Any
        //  74     83     236    242    Ljava/lang/Exception;
        //  74     83     271    277    Any
        //  86     87     236    242    Ljava/lang/Exception;
        //  86     87     271    277    Any
        //  94     107    236    242    Ljava/lang/Exception;
        //  94     107    271    277    Any
        //  113    126    236    242    Ljava/lang/Exception;
        //  113    126    271    277    Any
        //  129    136    236    242    Ljava/lang/Exception;
        //  129    136    271    277    Any
        //  140    153    236    242    Ljava/lang/Exception;
        //  140    153    271    277    Any
        //  156    165    236    242    Ljava/lang/Exception;
        //  156    165    271    277    Any
        //  197    226    233    236    Ljava/lang/Exception;
        //  197    226    271    277    Any
        //  256    259    271    277    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0256 (coming from #0276).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2239)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:203)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
