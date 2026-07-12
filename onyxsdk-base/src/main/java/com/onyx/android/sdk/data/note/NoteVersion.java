package com.onyx.android.sdk.data.note;

public class NoteVersion {
   public static final int V0 = 0;
   public static final int V1 = 1;

   public static int latestVersion() {
      return 1;
   }

   public static boolean isLatestVersion(int v) {
      return v == latestVersion();
   }
}
