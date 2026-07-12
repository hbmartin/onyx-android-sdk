package com.onyx.android.sdk.data;

public class SyncStatus {
   public static final int PENDING = 0;
   public static final int SYNCING = 1;
   public static final int SYNC_SUCCESS = 2;
   public static final int SYNC_ERROR = 3;
   public static final int SYNC_STOP = 4;
   public static final int SYNC_WAITING = 5;
   public static final int SYNC_UPDATING = 6;
   public static final int SYNC_DISABLE = 7;
   public static final int SYNC_NO_DATA = 8;
   public static final int UNCONNECTED = 9;

   public static boolean isFinished(int status) {
      return status == 2 || status == 3 || status == 4;
   }

   public static String toString(int status) {
      String var10000;
      switch (status) {
         case 0:
            var10000 = "PENDING";
            break;
         case 1:
            var10000 = "SYNCING";
            break;
         case 2:
            var10000 = "SYNC_SUCCESS";
            break;
         case 3:
            var10000 = "SYNC_ERROR";
            break;
         case 4:
            var10000 = "SYNC_STOP";
            break;
         case 5:
            var10000 = "SYNC_WAITING";
            break;
         case 6:
            var10000 = "SYNC_UPDATING";
            break;
         case 7:
            var10000 = "SYNC_DISABLE";
            break;
         case 8:
            var10000 = "SYNC_NO_DATA";
            break;
         case 9:
            var10000 = "UNCONNECTED";
            break;
         default:
            var10000 = String.valueOf(status);
      }

      return var10000;
   }
}
