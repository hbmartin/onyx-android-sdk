
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
