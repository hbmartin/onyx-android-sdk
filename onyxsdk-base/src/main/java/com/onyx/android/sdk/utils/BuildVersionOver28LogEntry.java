package com.onyx.android.sdk.utils;

import android.content.Context;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BuildVersionOver28LogEntry.class */
public class BuildVersionOver28LogEntry extends DefaultLogEntry {
    private static BuildVersionOver28LogEntry h;
    public static final String PROGRAM_EXEC_LOGCAT_GET_NOTE = "/system/bin/logcat -b system -b main -b crash -b events -d -v tag -v time ";
    public static final String PROGRAM_EXEC_LOGCAT_CLEAR_NOTE = "/system/bin/logcat -b system -b main -b crash -b events -c ";
    public static final String ANR_TRACES_DIR = "/data/anr/";
    public static final String ANR_TRACES_FILE_PREFIX = "anr_";
    public static final String ANR_TRACES_PATH = "/data/anr/anr_*";
    public static final String PROGRAM_EXEC_CAT_ANR = "/system/bin/cat /data/anr/anr_*";

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BuildVersionOver28LogEntry$a.class */
    class a implements FilenameFilter {
        a() {
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File dir, String name) {
            return name.startsWith("anr_");
        }
    }

    public static BuildVersionOver28LogEntry createLog() {
        if (h == null) {
            h = new BuildVersionOver28LogEntry();
        }
        return h;
    }

    private BuildVersionOver28LogEntry() {
        this.commandLogcatSet = new String[]{"/system/bin/logcat -b system -b main -b crash -b events -d -v tag -v time ", DefaultLogEntry.PROGRAM_EXEC_CAT_LINUX_VERSION};
        this.commandANRSet = new String[]{PROGRAM_EXEC_CAT_ANR};
        this.commandKernelSet = new String[]{DefaultLogEntry.PROGRAM_EXEC_CAT_LINUX_VERSION, "/system/bin/logcat -d -b kernel "};
    }

    private File[] a() {
        return new File(ANR_TRACES_DIR).listFiles(new a());
    }

    @Override // com.onyx.android.sdk.utils.DefaultLogEntry
    protected File generateRegDumpFile(Context context) throws IOException {
        return DefaultLogEntry.generateExceptionFile(context, new String[]{"/system/bin/cat /sys/class/ebc/dump_reg"}, new File(context.getFilesDir(), "dump_reg.txt"));
    }

    @Override // com.onyx.android.sdk.utils.DefaultLogEntry
    protected File[] generateEBCVersionFiles(Context context) throws IOException {
        if (FileUtils.fileExist("/sys/class/ebc/version")) {
            return new File[]{DefaultLogEntry.generateExceptionFile(context, new String[]{DefaultLogEntry.PROGRAM_EXEC_CAT_ECB_VERSION}, new File(context.getFilesDir(), "EBC_Version.txt"))};
        }
        return null;
    }

    @Override // com.onyx.android.sdk.utils.DefaultLogEntry
    protected File[] generateAnrFile(Context context) throws IOException {
        File[] fileArrA = a();
        ArrayList arrayList = new ArrayList();
        if (fileArrA != null && fileArrA.length > 0) {
            for (File file : fileArrA) {
                File fileA = a(context, file);
                if (FileUtils.fileExist(fileA) && fileA.length() > 0) {
                    arrayList.add(fileA);
                }
            }
        }
        return (File[]) arrayList.toArray(new File[0]);
    }

    private File a(Context context, File file) throws IOException {
        return DefaultLogEntry.generateExceptionFile(context, new String[]{"/system/bin/cat /data/anr/" + file.getName()}, new File(context.getFilesDir(), file.getName() + DefaultLogEntry.OUTPUT_FILE_TXT_EXTENSION));
    }
}
