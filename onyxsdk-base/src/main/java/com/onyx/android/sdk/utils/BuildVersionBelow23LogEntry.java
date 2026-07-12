package com.onyx.android.sdk.utils;

public class BuildVersionBelow23LogEntry extends DefaultLogEntry {
   private static BuildVersionBelow23LogEntry h;
   public static final String PROGRAM_EXEC_LOGCAT_GET_NOTE = "/system/bin/logcat -b system -b main -d -v tag -v time ";
   public static final String PROGRAM_EXEC_LOGCAT_CLEAR_NOTE = "/system/bin/logcat -b system -b main -c ";
   public static final String ANR_TRACES_PATH = "/data/anr/traces.txt";
   public static final String PROGRAM_EXEC_CAT_ANR = "/system/bin/cat /data/anr/traces.txt";

   public static BuildVersionBelow23LogEntry createLog() {
      if (h == null) {
         h = new BuildVersionBelow23LogEntry();
      }

      return h;
   }

   private BuildVersionBelow23LogEntry() {
      String[] var1;
      String[] var10002 = var1 = new String[2];
      var10002[0] = "/system/bin/logcat -b system -b main -d -v tag -v time ";
      var10002[1] = "/system/bin/cat /proc/version";
      super.commandLogcatSet = var1;
      String[] var2;
      (var2 = new String[1])[0] = "/system/bin/cat /data/anr/traces.txt";
      super.commandANRSet = var2;
   }
}
