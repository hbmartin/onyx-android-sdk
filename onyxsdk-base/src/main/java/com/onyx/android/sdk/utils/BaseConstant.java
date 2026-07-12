package com.onyx.android.sdk.utils;

public class BaseConstant {
   public static final String NOTE_TEMPLATE_DIR_NAME = "noteTemplate";
   public static final String IMAGE_MIME_TYPE = "image/*";
   public static final String ANDROID_SETTING_PACKAGE_NAME = "com.android.settings";

   public static class PDFConverterErrorCode {
      public static final int NO_ERROR = 0;
      public static final int COMPLETE = 1;
      public static final int CONVERT_ERROR = 2;
      public static final int NOT_SUPPORT_CONVERT_ERROR = 3;
      public static final int INVALID_PATH_ERROR = 4;
   }

   public static class ReaderExportStateCode {
      public static final int NO_ERROR = 0;
      public static final int COMPLETE = 1;
      public static final int EXPORT_ERROR = 2;
      public static final int STATE_PACKING = 3;
   }

   public static class ReaderImportStateCode {
      public static final int NO_ERROR = 0;
      public static final int COMPLETE = 1;
      public static final int IMPORT_ERROR = 2;
   }
}
