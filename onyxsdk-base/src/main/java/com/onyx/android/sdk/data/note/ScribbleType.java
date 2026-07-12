package com.onyx.android.sdk.data.note;

public class ScribbleType {
   public static final int FIXED_DOCUMENT_SCRIBBLE = 0;
   public static final int SIDE_NOTE = 1;
   public static final int TEXT_REFLOW_DOCUMENT_SCRIBBLE = 2;
   public static final int IMAGE_REFLOW_DOCUMENT_SCRIBBLE = 3;
   public static final int FIXED_DOCUMENT_TEXT_REFLOW_SCRIBBLE = 4;

   public static boolean isFixedPage(int scribbleType) {
      return scribbleType != 2;
   }

   public static boolean isSideNote(int scribbleType) {
      return scribbleType == 1;
   }

   public static boolean isDocumentNote(int scribbleType) {
      return isSideNote(scribbleType) ^ true;
   }
}
