package com.onyx.android.sdk.data.note;

public class SelectionType {
   public static final int SMART = 0;
   public static final int FREE = 1;
   public static final int RECTANGLE = 2;

   public static boolean isRectangleSelection(int selectionType) {
      return selectionType == 2;
   }

   public static boolean isSmartSelection(int selectionType) {
      return selectionType == 0;
   }

   public static boolean isDrawSelectionType(int selectionType) {
      return isSmartSelection(selectionType) || isRectangleSelection(selectionType);
   }
}
