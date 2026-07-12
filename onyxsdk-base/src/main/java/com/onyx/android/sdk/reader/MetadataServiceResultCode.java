package com.onyx.android.sdk.reader;

public class MetadataServiceResultCode {
   public static final int RESULT_CODE_UNKNOWN_ERROR = 0;
   public static final int RESULT_CODE_SUCCESS = 1;
   public static final int RESULT_CODE_PASSWORD_REQUIRED = 2;
   public static final int RESULT_CODE_PASSWORD_ERROR = 3;

   public static boolean isSuccess(int resultCode) {
      return resultCode == 1;
   }
}
