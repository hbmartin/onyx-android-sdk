// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import com.onyx.android.sdk.device.EnvironmentUtil;
import java.util.Locale;
import androidx.annotation.Nullable;
import java.util.Date;
import android.util.Log;
import java.io.IOException;
import java.io.PrintWriter;
import android.content.Context;
import android.content.pm.PackageInfo;
import java.io.File;
import java.util.List;
import java.text.SimpleDateFormat;

public class DefaultLogEntry
{
    public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HHMMSS;
    private static DefaultLogEntry instance;
    public static final String COMMAND_LOGCAT = "/system/bin/logcat -b system -b main ";
    public static final String COMMAND_LOGCAT_ALL = "/system/bin/logcat -b system -b main -b crash -b events ";
    public static final String COMMAND_LOGCAT_KERNEL = "/system/bin/logcat -d -b kernel ";
    public static final String COMMAND_SU = "su ";
    public static final String COMMAND_CAT = "/system/bin/cat ";
    public static final String COMMAND_DMSG = "/system/bin/dmesg ";
    public static final String LOGCAT_GET_NOTE = "-d ";
    public static final String LOGCAT_CLEAR_NOTE = "-c ";
    public static final String LOGCAT_FORMAT_SET = "-v ";
    public static final String TAG_FORMAT_TAG = "tag ";
    public static final String TAG_FORMAT_TIME = "time ";
    public static final String DIR_PROC = "/proc/";
    public static final String VERSION = "version";
    public static final String SEPARATOR_STAR = "\r\n***********************************\r\n\r\n";
    public static final String BOOT_KERNEL = "boot_kernel";
    public static final String BOOT_LOGCAT = "boot_logcat";
    private static final String b = "-t ";
    private static final int c = 60;
    private static final String d = "/data/local/log/";
    private static final String e = "/data/local/log/last_logcat";
    private static final String f = "/data/local/log/last_dmesg";
    private static final String g = "/sys/fs/pstore";
    protected static final String EBC_VERSION_PATH = "/sys/class/ebc/version";
    protected static final String DUMP_REG_PATH = "/sys/class/ebc/dump_reg";
    public static final String PROGRAM_EXEC_CAT_LINUX_VERSION = "/system/bin/cat /proc/version";
    public static final String PROGRAM_EXEC_CAT_LINUX_DMSG = "/system/bin/dmesg ";
    public static final String PROGRAM_EXEC_CAT_LINUX_KERNEL = "/system/bin/logcat -d -b kernel ";
    public static final String PROGRAM_EXEC_CAT_SHUTDOWN_LOG_LOGCAT = "/system/bin/cat /data/local/log/last_logcat";
    public static final String PROGRAM_EXEC_CAT_SHUTDOWN_LOG_DMESG = "/system/bin/cat /data/local/log/last_dmesg";
    public static final String PROGRAM_EXEC_CAT_CONSOLE_RAMOOPS = "/system/bin/cat /sys/fs/pstore/console-ramoops";
    public static final String PROGRAM_EXEC_CAT_ECB_VERSION = "/system/bin/cat /sys/class/ebc/version";
    public static final String ENCODING_TYPE = "utf-8";
    public static final String OUTPUT_FILE_ZIP_PREFIX = "feedback_";
    public static final String OUTPUT_FILE_LOGCAT_PREFIX = "logcat_";
    public static final String OUTPUT_FILE_KERNEL_PREFIX = "kernel_";
    public static final String OUTPUT_FILE_DUMPSYS_PREFIX = "dumpsys_";
    public static final String OUTPUT_FILE_ANR_PREFIX = "anr_";
    public static final String OUTPUT_FILE_TXT_EXTENSION = ".txt";
    public static final String OUTPUT_FILE_ZIP_EXTENSION = ".zip";
    protected static final String OUTPUT_FILE_APK_INFO_PREFIX = "appsInfo_";
    public static final String OUTPUT_FILE_SHUTDOWN_LOGCAT_PREFIX = "shutdown_logcat_";
    public static final String OUTPUT_FILE_SHUTDOWN_DMESG_PREFIX = "shutdown_dmesg_";
    public static final String OUTPUT_FILE_CONSOLE_RAMOOPS_PREFIX = "console-ramoops_";
    public static final String PROGRAM_EXEC_LOGCAT_GET_NOTE = "/system/bin/logcat -b system -b main -b crash -b events -d -v tag -v time ";
    public static final String PROGRAM_EXEC_LOGCAT_CLEAR_NOTE = "/system/bin/logcat -b system -b main -b crash -b events -c ";
    public static final String ANR_TRACES_PATH = "/data/anr/traces.txt";
    public static final String PROGRAM_EXEC_CAT_ANR = "/system/bin/cat /data/anr/traces.txt";
    public static final String PATH_LOG_DIRECTORY;
    protected String[] commandLogcatSet;
    protected String[] commandKernelSet;
    protected String[] commandClearNoteSet;
    public String[] commandANRSet;
    protected String[] commandShutdownLogLogcat;
    protected String[] commandShutdownLogDmesg;
    protected String[] commandConsoleRamoopsLog;
    
    public DefaultLogEntry() {
        final String[] commandLogcatSet;
        final String[] array = commandLogcatSet = new String[2];
        array[0] = "/system/bin/logcat -b system -b main -b crash -b events -d -v tag -v time ";
        array[1] = "/system/bin/cat /proc/version";
        this.commandLogcatSet = commandLogcatSet;
        final String[] commandKernelSet;
        final String[] array2 = commandKernelSet = new String[2];
        array2[0] = "/system/bin/cat /proc/version";
        array2[1] = "/system/bin/dmesg ";
        this.commandKernelSet = commandKernelSet;
        this.commandClearNoteSet = new String[] { "/system/bin/logcat -b system -b main -b crash -b events -c " };
        this.commandANRSet = new String[] { "/system/bin/cat /data/anr/traces.txt" };
        this.commandShutdownLogLogcat = new String[] { "/system/bin/cat /data/local/log/last_logcat" };
        this.commandShutdownLogDmesg = new String[] { "/system/bin/cat /data/local/log/last_dmesg" };
        this.commandConsoleRamoopsLog = new String[] { "/system/bin/cat /sys/fs/pstore/console-ramoops" };
    }
    
    private void a(final List<File> list, final File[] files) {
        if (files != null && files.length > 0) {
            for (int length = files.length, i = 0; i < length; ++i) {
                final File file;
                if ((file = files[i]) != null && file.exists()) {
                    list.add(file);
                }
            }
        }
    }
    
    private void a(final List<File> list, final File file) {
        if (list != null && file != null) {
            list.add(file);
        }
    }
    
    public static File generateExceptionFile(final Context context, final String[] commandSet, final String filePrefix) throws IOException {
        return generateExceptionFile(context, commandSet, new File(context.getFilesDir(), getFileNameBasedOnDate(filePrefix, ".txt")));
    }
    
    public static File generateExceptionFile(final Context context, final String[] commandSet, final File targetFile) throws IOException {
        for (int length = commandSet.length, i = 0; i < length; ++i) {
            ShellUtils.execCommand(commandSet[i] + " >> " + targetFile.getAbsolutePath(), false, true);
            FileUtils.appendContentToFile("\r\n***********************************\r\n\r\n", targetFile);
        }
        Log.i("success:" + targetFile.getName(), "" + targetFile.length());
        return targetFile;
    }
    
    protected static String getFileNameBasedOnDate(final String prefix, final String suffix) {
        return prefix + DefaultLogEntry.DATE_FORMAT_YYYY_MM_DD_HHMMSS.format(new Date()) + suffix;
    }
    
    protected static File generateAppsInfoFile(final Context context, final String fileDirPath, final String filePrefix) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aconst_null    
        //     2: astore_3       
        //     3: aconst_null    
        //     4: astore          4
        //     6: new             Ljava/io/File;
        //     9: dup            
        //    10: astore          5
        //    12: aload_1        
        //    13: aload_2        
        //    14: ldc             ".txt"
        //    16: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.getFileNameBasedOnDate:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    19: invokespecial   java/io/File.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    22: new             Ljava/io/PrintWriter;
        //    25: dup            
        //    26: astore_1       
        //    27: aload           5
        //    29: invokespecial   java/io/PrintWriter.<init>:(Ljava/io/File;)V
        //    32: invokevirtual   android/content/Context.getPackageManager:()Landroid/content/pm/PackageManager;
        //    35: iconst_0       
        //    36: invokevirtual   android/content/pm/PackageManager.getInstalledPackages:(I)Ljava/util/List;
        //    39: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    44: astore_2       
        //    45: aload_2        
        //    46: invokeinterface java/util/Iterator.hasNext:()Z
        //    51: ifeq            187
        //    54: aload_1        
        //    55: dup            
        //    56: dup            
        //    57: dup2           
        //    58: aload_2        
        //    59: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    64: checkcast       Landroid/content/pm/PackageInfo;
        //    67: astore_3       
        //    68: new             Ljava/lang/StringBuilder;
        //    71: dup            
        //    72: invokespecial   java/lang/StringBuilder.<init>:()V
        //    75: ldc_w           "appName:"
        //    78: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    81: aload_3        
        //    82: getfield        android/content/pm/PackageInfo.applicationInfo:Landroid/content/pm/ApplicationInfo;
        //    85: aload_0        
        //    86: invokevirtual   android/content/Context.getPackageManager:()Landroid/content/pm/PackageManager;
        //    89: invokevirtual   android/content/pm/ApplicationInfo.loadLabel:(Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
        //    92: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    95: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    98: invokevirtual   java/io/PrintWriter.println:(Ljava/lang/String;)V
        //   101: new             Ljava/lang/StringBuilder;
        //   104: dup            
        //   105: invokespecial   java/lang/StringBuilder.<init>:()V
        //   108: ldc_w           "packageName:"
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: aload_3        
        //   115: getfield        android/content/pm/PackageInfo.packageName:Ljava/lang/String;
        //   118: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   121: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   124: invokevirtual   java/io/PrintWriter.println:(Ljava/lang/String;)V
        //   127: new             Ljava/lang/StringBuilder;
        //   130: dup            
        //   131: invokespecial   java/lang/StringBuilder.<init>:()V
        //   134: ldc_w           "versionCode:"
        //   137: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   140: aload_3        
        //   141: getfield        android/content/pm/PackageInfo.versionCode:I
        //   144: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   147: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   150: invokevirtual   java/io/PrintWriter.println:(Ljava/lang/String;)V
        //   153: new             Ljava/lang/StringBuilder;
        //   156: dup            
        //   157: invokespecial   java/lang/StringBuilder.<init>:()V
        //   160: ldc_w           "versionName:"
        //   163: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   166: aload_3        
        //   167: getfield        android/content/pm/PackageInfo.versionName:Ljava/lang/String;
        //   170: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   173: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   176: invokevirtual   java/io/PrintWriter.println:(Ljava/lang/String;)V
        //   179: ldc             "\r\n***********************************\r\n\r\n"
        //   181: invokevirtual   java/io/PrintWriter.println:(Ljava/lang/String;)V
        //   184: goto            45
        //   187: aload_1        
        //   188: dup            
        //   189: invokevirtual   java/io/PrintWriter.flush:()V
        //   192: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   195: goto            231
        //   198: goto            215
        //   201: pop            
        //   202: goto            227
        //   205: pop            
        //   206: aload           4
        //   208: astore_1       
        //   209: goto            227
        //   212: aload           4
        //   214: astore_1       
        //   215: aload_1        
        //   216: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   219: athrow         
        //   220: pop            
        //   221: aload           4
        //   223: aload_3        
        //   224: astore          5
        //   226: astore_1       
        //   227: aload_1        
        //   228: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   231: aload           5
        //   233: areturn        
        //    Exceptions:
        //  throws java.io.IOException
        //    StackMapTable: 00 0A FF 00 2D 00 06 07 00 D8 07 01 2E 07 01 41 00 00 07 00 C2 00 00 FF 00 8D 00 06 00 07 01 2E 00 00 00 07 00 C2 00 00 FF 00 0A 00 02 00 07 01 2E 00 01 07 01 78 FF 00 02 00 06 00 07 01 2E 00 00 00 07 00 C2 00 01 07 01 7A FF 00 03 00 06 00 00 00 00 07 01 2E 07 00 C2 00 01 07 01 7A FF 00 06 00 05 00 00 00 00 07 01 2E 00 01 07 01 78 FF 00 02 00 02 00 07 01 2E 00 01 07 01 78 FF 00 04 00 05 00 00 00 07 00 C2 07 01 2E 00 01 07 01 7A FF 00 06 00 06 00 07 01 2E 00 00 00 07 00 C2 00 00 FF 00 03 00 06 00 00 00 00 00 07 00 C2 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  6      9      220    227    Ljava/lang/Exception;
        //  6      9      212    215    Any
        //  12     22     220    227    Ljava/lang/Exception;
        //  12     22     212    215    Any
        //  22     25     205    212    Ljava/lang/Exception;
        //  22     25     212    215    Any
        //  27     32     205    212    Ljava/lang/Exception;
        //  27     32     212    215    Any
        //  32     44     201    205    Ljava/lang/Exception;
        //  32     44     198    201    Any
        //  45     51     201    205    Ljava/lang/Exception;
        //  45     51     198    201    Any
        //  54     67     201    205    Ljava/lang/Exception;
        //  54     67     198    201    Any
        //  68     192    201    205    Ljava/lang/Exception;
        //  68     192    198    201    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0227 (coming from #0226).
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
        File target = new File(fileDirPath, getFileNameBasedOnDate(filePrefix, ".txt"));
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(target);
            for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(0)) {
                writer.println("appName:" + packageInfo.applicationInfo.loadLabel(context.getPackageManager()));
                writer.println("packageName:" + packageInfo.packageName);
                writer.println("versionCode:" + packageInfo.versionCode);
                writer.println("versionName:" + packageInfo.versionName);
                writer.println(SEPARATOR_STAR);
            }
            writer.flush();
        }
        catch (Exception ignored) {
        }
        finally {
            FileUtils.closeQuietly(writer);
        }
        return target;
    }
    
    @Nullable
    public static String getECBVersion() {
        final ShellUtils.CommandResult execCommand;
        if ((execCommand = ShellUtils.execCommand("/system/bin/cat /sys/class/ebc/version", false, true)).isSuccessful()) {
            return StringUtils.fullTrim(execCommand.successMsg);
        }
        Debug.i((Class)DefaultLogEntry.class, "can't read ecb version:" + execCommand.errorMsg, new Object[0]);
        return null;
    }
    
    public static void deleteFiles(final File[] files) {
        if (files != null && files.length > 0) {
            for (int length = files.length, i = 0; i < length; ++i) {
                final File file;
                if ((file = files[i]) != null && file.exists()) {
                    FileUtils.deleteFile(file, true);
                }
            }
        }
    }
    
    public static DefaultLogEntry createLog() {
        if (DefaultLogEntry.instance == null) {
            DefaultLogEntry.instance = new DefaultLogEntry();
        }
        return DefaultLogEntry.instance;
    }
    
    static {
        DATE_FORMAT_YYYY_MM_DD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
        PATH_LOG_DIRECTORY = EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath() + "/.log/";
    }
    
    public File generateFeedbackUploadZip(final Context context, final boolean collectLog, final File... argFiles) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aconst_null    
        //     2: astore_2       
        //     3: new             Ljava/util/ArrayList;
        //     6: dup            
        //     7: astore          4
        //     9: invokespecial   java/util/ArrayList.<init>:()V
        //    12: new             Ljava/util/ArrayList;
        //    15: dup            
        //    16: astore          5
        //    18: invokespecial   java/util/ArrayList.<init>:()V
        //    21: ifeq            203
        //    24: aload           4
        //    26: aload           5
        //    28: aload_0        
        //    29: aload           5
        //    31: aload_0        
        //    32: aload           5
        //    34: aload_0        
        //    35: aload           5
        //    37: aload_0        
        //    38: aload           5
        //    40: aload_0        
        //    41: aload           5
        //    43: aload_0        
        //    44: aload           5
        //    46: aload_0        
        //    47: aload           5
        //    49: aload_0        
        //    50: aload           5
        //    52: aload_0        
        //    53: aload           5
        //    55: aload_0        
        //    56: aload           5
        //    58: aload_0        
        //    59: aload           5
        //    61: aload_0        
        //    62: aload_1        
        //    63: aload_0        
        //    64: dup            
        //    65: aload_1        
        //    66: aload_0        
        //    67: aload_1        
        //    68: aload_0        
        //    69: aload_1        
        //    70: aload_0        
        //    71: aload_1        
        //    72: dup            
        //    73: dup            
        //    74: aload_0        
        //    75: aload_1        
        //    76: aload_0        
        //    77: aload_1        
        //    78: aload_0        
        //    79: aload_1        
        //    80: aload_0        
        //    81: aload_1        
        //    82: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateLogcatFile:(Landroid/content/Context;)Ljava/io/File;
        //    85: astore          6
        //    87: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateKernelFile:(Landroid/content/Context;)Ljava/io/File;
        //    90: astore          7
        //    92: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateDumpsysFile:(Landroid/content/Context;)Ljava/io/File;
        //    95: astore          8
        //    97: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateAnrFile:(Landroid/content/Context;)[Ljava/io/File;
        //   100: astore          9
        //   102: invokevirtual   android/content/Context.getFilesDir:()Ljava/io/File;
        //   105: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   108: ldc             "appsInfo_"
        //   110: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.generateAppsInfoFile:(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
        //   113: astore          10
        //   115: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateShutdownLogcatFile:(Landroid/content/Context;)Ljava/io/File;
        //   118: astore          11
        //   120: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateShutdownDmesgFile:(Landroid/content/Context;)Ljava/io/File;
        //   123: astore          12
        //   125: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generatePStoreFiles:(Landroid/content/Context;)[Ljava/io/File;
        //   128: astore          13
        //   130: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateEBCVersionFiles:(Landroid/content/Context;)[Ljava/io/File;
        //   133: astore          14
        //   135: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateBootFiles:()[Ljava/io/File;
        //   138: astore          15
        //   140: invokevirtual   com/onyx/android/sdk/utils/DefaultLogEntry.generateRegDumpFile:(Landroid/content/Context;)Ljava/io/File;
        //   143: astore          16
        //   145: aload           6
        //   147: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   150: aload           7
        //   152: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   155: aload           8
        //   157: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   160: aload           9
        //   162: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;[Ljava/io/File;)V
        //   165: aload           10
        //   167: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   170: aload           11
        //   172: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   175: aload           12
        //   177: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   180: aload           13
        //   182: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;[Ljava/io/File;)V
        //   185: aload           14
        //   187: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;[Ljava/io/File;)V
        //   190: aload           15
        //   192: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;[Ljava/io/File;)V
        //   195: aload           16
        //   197: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;Ljava/io/File;)V
        //   200: invokestatic    com/onyx/android/sdk/utils/CollectionUtils.safeAddAll:(Ljava/util/Collection;Ljava/util/Collection;)V
        //   203: aload           4
        //   205: aload_0        
        //   206: aload           4
        //   208: aload_3        
        //   209: invokespecial   com/onyx/android/sdk/utils/DefaultLogEntry.a:(Ljava/util/List;[Ljava/io/File;)V
        //   212: invokestatic    com/onyx/android/sdk/utils/CollectionUtils.isNullOrEmpty:(Ljava/util/Collection;)Z
        //   215: ifeq            237
        //   218: aload           5
        //   220: iconst_0       
        //   221: anewarray       Ljava/io/File;
        //   224: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   229: checkcast       [Ljava/io/File;
        //   232: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.deleteFiles:([Ljava/io/File;)V
        //   235: aconst_null    
        //   236: areturn        
        //   237: aload           4
        //   239: invokeinterface java/util/List.size:()I
        //   244: anewarray       Ljava/io/File;
        //   247: dup            
        //   248: aload           4
        //   250: swap           
        //   251: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   256: pop            
        //   257: new             Ljava/io/File;
        //   260: dup            
        //   261: dup            
        //   262: astore_0       
        //   263: aload_1        
        //   264: invokevirtual   android/content/Context.getFilesDir:()Ljava/io/File;
        //   267: ldc             "feedback_"
        //   269: ldc             ".zip"
        //   271: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.getFileNameBasedOnDate:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   274: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //   277: invokestatic    com/onyx/android/sdk/utils/ZipUtils.compress:([Ljava/io/File;Ljava/io/File;)Z
        //   280: ifne            302
        //   283: aload           5
        //   285: iconst_0       
        //   286: anewarray       Ljava/io/File;
        //   289: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   294: checkcast       [Ljava/io/File;
        //   297: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.deleteFiles:([Ljava/io/File;)V
        //   300: aconst_null    
        //   301: areturn        
        //   302: aload           5
        //   304: iconst_0       
        //   305: anewarray       Ljava/io/File;
        //   308: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   313: checkcast       [Ljava/io/File;
        //   316: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.deleteFiles:([Ljava/io/File;)V
        //   319: goto            350
        //   322: goto            330
        //   325: goto            352
        //   328: aload_2        
        //   329: astore_0       
        //   330: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   333: aload           5
        //   335: iconst_0       
        //   336: anewarray       Ljava/io/File;
        //   339: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   344: checkcast       [Ljava/io/File;
        //   347: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.deleteFiles:([Ljava/io/File;)V
        //   350: aload_0        
        //   351: areturn        
        //   352: aload           5
        //   354: iconst_0       
        //   355: anewarray       Ljava/io/File;
        //   358: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   363: checkcast       [Ljava/io/File;
        //   366: invokestatic    com/onyx/android/sdk/utils/DefaultLogEntry.deleteFiles:([Ljava/io/File;)V
        //   369: athrow         
        //    StackMapTable: 00 09 FF 00 CB 00 06 07 00 05 07 00 D8 07 00 C2 07 00 C0 07 01 B7 07 01 B7 00 00 FF 00 21 00 06 00 07 00 D8 07 00 C2 00 07 01 B7 07 01 B7 00 00 FF 00 40 00 06 07 00 C2 00 00 00 00 07 01 B7 00 00 53 07 01 7A FF 00 02 00 06 00 00 00 00 00 07 01 B7 00 01 07 01 78 FF 00 02 00 06 00 00 07 00 C2 00 00 07 01 B7 00 01 07 01 7A FF 00 01 00 06 07 00 C2 00 00 00 00 07 01 B7 00 01 07 01 7A FF 00 13 00 01 07 00 C2 00 00 FF 00 01 00 06 00 00 00 00 00 07 01 B7 00 01 07 01 78
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  24     85     328    330    Ljava/lang/Exception;
        //  24     85     325    328    Any
        //  87     90     328    330    Ljava/lang/Exception;
        //  87     90     325    328    Any
        //  92     95     328    330    Ljava/lang/Exception;
        //  92     95     325    328    Any
        //  97     100    328    330    Ljava/lang/Exception;
        //  97     100    325    328    Any
        //  102    113    328    330    Ljava/lang/Exception;
        //  102    113    325    328    Any
        //  115    118    328    330    Ljava/lang/Exception;
        //  115    118    325    328    Any
        //  120    123    328    330    Ljava/lang/Exception;
        //  120    123    325    328    Any
        //  125    128    328    330    Ljava/lang/Exception;
        //  125    128    325    328    Any
        //  130    133    328    330    Ljava/lang/Exception;
        //  130    133    325    328    Any
        //  135    138    328    330    Ljava/lang/Exception;
        //  135    138    325    328    Any
        //  140    143    328    330    Ljava/lang/Exception;
        //  140    143    325    328    Any
        //  145    215    328    330    Ljava/lang/Exception;
        //  145    215    325    328    Any
        //  237    247    328    330    Ljava/lang/Exception;
        //  237    247    325    328    Any
        //  248    256    328    330    Ljava/lang/Exception;
        //  248    256    325    328    Any
        //  257    260    328    330    Ljava/lang/Exception;
        //  257    260    325    328    Any
        //  263    277    328    330    Ljava/lang/Exception;
        //  263    277    325    328    Any
        //  277    280    322    325    Ljava/lang/Exception;
        //  277    280    325    328    Any
        //  330    333    325    328    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0350 (coming from #0325).
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
        File result = null;
        final ArrayList<File> files = new ArrayList<>();
        final ArrayList<File> generatedFiles = new ArrayList<>();
        try {
            if (collectLog) {
                a(generatedFiles, generateLogcatFile(context));
                a(generatedFiles, generateKernelFile(context));
                a(generatedFiles, generateDumpsysFile(context));
                a(generatedFiles, generateAnrFile(context));
                a(generatedFiles, generateAppsInfoFile(context, context.getFilesDir().getAbsolutePath(), OUTPUT_FILE_APK_INFO_PREFIX));
                a(generatedFiles, generateShutdownLogcatFile(context));
                a(generatedFiles, generateShutdownDmesgFile(context));
                a(generatedFiles, generateConsoleRampoosFile(context));
                a(generatedFiles, generateEBCVersionFiles(context));
                a(generatedFiles, generatePStoreFiles(context));
                a(generatedFiles, generateBootFiles());
                a(generatedFiles, generateRegDumpFile(context));
                CollectionUtils.safeAddAll(files, generatedFiles);
            }
            a(files, argFiles);
            if (CollectionUtils.isNullOrEmpty(files)) {
                return null;
            }
            File[] sourceFiles = files.toArray(new File[files.size()]);
            result = new File(context.getFilesDir(), getFileNameBasedOnDate(OUTPUT_FILE_ZIP_PREFIX, OUTPUT_FILE_ZIP_EXTENSION));
            if (!ZipUtils.compress(sourceFiles, result)) {
                return null;
            }
            return result;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return result;
        }
        finally {
            deleteFiles(generatedFiles.toArray(new File[0]));
        }
    }
    
    protected File generateLogcatFile(final Context context) throws IOException {
        return generateExceptionFile(context, this.commandLogcatSet, "logcat_");
    }
    
    protected File generateKernelFile(final Context context) throws IOException {
        return generateExceptionFile(context, this.commandKernelSet, "kernel_");
    }
    
    protected File generateDumpsysFile(final Context context) throws IOException {
        return generateExceptionFile(context, DefaultLogEntry.a.D, "dumpsys_");
    }
    
    protected File[] generateAnrFile(final Context context) throws IOException {
        return new File[] { generateExceptionFile(context, this.commandANRSet, "anr_") };
    }
    
    protected File generateShutdownLogcatFile(final Context context) throws IOException {
        return generateExceptionFile(context, this.commandShutdownLogLogcat, "shutdown_logcat_");
    }
    
    protected File generateShutdownDmesgFile(final Context context) throws IOException {
        return generateExceptionFile(context, this.commandShutdownLogDmesg, "shutdown_dmesg_");
    }
    
    protected File generateConsoleRampoosFile(final Context context) throws IOException {
        return generateExceptionFile(context, this.commandConsoleRamoopsLog, "console-ramoops_");
    }
    
    protected File[] generateBootFiles() {
        final ArrayList<File> list = new ArrayList<>();
        final ArrayList<String> fileList = new ArrayList<>();
        FileUtils.collectFiles(DefaultLogEntry.PATH_LOG_DIRECTORY, null, false, fileList, false);
        final Iterator<String> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final String pathname;
            final String safelyGetStr;
            if ((safelyGetStr = StringUtils.safelyGetStr(FileUtils.getFileName(pathname = iterator.next()))).startsWith("boot_kernel") || safelyGetStr.startsWith("boot_logcat")) {
                list.add(new File(pathname));
            }
        }
        return (File[])list.toArray(new File[0]);
    }
    
    protected File[] generateEBCVersionFiles(final Context context) throws IOException {
        return null;
    }
    
    protected File generateRegDumpFile(final Context context) throws IOException {
        return null;
    }
    
    protected File[] generatePStoreFiles(final Context context) throws IOException {
        final ArrayList<File> list = new ArrayList<>();
        final ArrayList<String> fileList = new ArrayList<>();
        FileUtils.collectFiles("/sys/fs/pstore", null, false, fileList, false);
        final Iterator<String> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            final String str = iterator.next();
            list.add(generateExceptionFile(context, new String[] { "/system/bin/cat " + str },
                    StringUtils.safelyGetStr(FileUtils.getFileName(str)) + "_"));
        }
        return (File[])list.toArray(new File[0]);
    }
    
    private static class a
    {
        private static final String a = "/system/bin/dumpsys ";
        private static final String b = "/system/bin/dumpsys SurfaceFlinger";
        private static final String c = "/system/bin/dumpsys activity";
        private static final String d = "/system/bin/dumpsys activity exit-info";
        private static final String e = "/system/bin/dumpsys alarm";
        private static final String f = "/system/bin/dumpsys battery";
        private static final String g = "/system/bin/dumpsys biometric";
        private static final String h = "/system/bin/dumpsys bluetooth_manager";
        private static final String i = "/system/bin/dumpsys dreams";
        private static final String j = "/system/bin/dumpsys fingerprint";
        private static final String k = "/system/bin/dumpsys font";
        private static final String l = "/system/bin/dumpsys input";
        private static final String m = "/system/bin/dumpsys input_method";
        private static final String n = "/system/bin/dumpsys inputflinger";
        private static final String o = "/system/bin/dumpsys package";
        private static final String p = "/system/bin/dumpsys power";
        private static final String q = "/system/bin/dumpsys settings";
        private static final String r = "/system/bin/dumpsys wifi";
        private static final String s = "/system/bin/dumpsys window";
        private static final String t = "/system/bin/dumpsys webviewupdate";
        private static final String u = "/system/bin/dumpsys usagestats";
        private static final String v = "/system/bin/dumpsys batterystats";
        private static final String w = "/system/bin/dumpsys audio";
        private static final String x = "/system/bin/dumpsys media.audio_flinger";
        private static final String y = "/system/bin/dumpsys media.metrics";
        private static final String z = "/system/bin/dumpsys media_session";
        private static final String A = "/system/bin/dumpsys oec_service";
        private static final String B = "/system/bin/dumpsys activity service com.android.quickstep.TouchInteractionService";
        private static final String C = "/system/bin/dumpsys sensorservice";
        public static final String[] D;
        
        static {
            D = new String[] { "/system/bin/dumpsys SurfaceFlinger", "/system/bin/dumpsys activity", "/system/bin/dumpsys activity exit-info", "/system/bin/dumpsys alarm", "/system/bin/dumpsys battery", "/system/bin/dumpsys biometric", "/system/bin/dumpsys bluetooth_manager", "/system/bin/dumpsys dreams", "/system/bin/dumpsys fingerprint", "/system/bin/dumpsys font", "/system/bin/dumpsys input", "/system/bin/dumpsys input_method", "/system/bin/dumpsys inputflinger", "/system/bin/dumpsys package", "/system/bin/dumpsys power", "/system/bin/dumpsys settings", "/system/bin/dumpsys wifi", "/system/bin/dumpsys window", "/system/bin/dumpsys webviewupdate", "/system/bin/dumpsys usagestats", "/system/bin/dumpsys batterystats", "/system/bin/dumpsys audio", "/system/bin/dumpsys media.audio_flinger", "/system/bin/dumpsys media.metrics", "/system/bin/dumpsys media_session", "/system/bin/dumpsys oec_service", "/system/bin/dumpsys activity service com.android.quickstep.TouchInteractionService", "/system/bin/dumpsys sensorservice" };
        }
    }
}
