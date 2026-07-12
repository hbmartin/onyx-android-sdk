package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.StringUtils;

public class PageConstants {
   public static final String SINGLE_PAGE = "singlePage";
   public static final String SINGLE_PAGE_RTL = "singlePageRTL";
   public static final String SINGLE_PAGE_NAVIGATION_LIST = "singlePageNavigationList";
   public static final String DUAL_PAGE_LTR = "dualPage";
   public static final String DUAL_PAGE_RTL = "dualPage_RTL";
   public static final String CONTINUOUS_PAGE = "continuousPage";
   public static final String HORIZONTAL_CONTINUOUS_PAGE = "horizontalContinuousPage";
   public static final String IMAGE_REFLOW_PAGE = "imageReflowPage";
   public static final String FIXED_DOCUMENT_TEXT_REFLOW_PAGE = "fixedDocumentTextReflowPage";
   public static final String TEXT_REFLOW_PAGE = "textReflowPage";
   public static final int SCALE_INVALID = 0;
   public static final int SCALE_TO_PAGE = -1;
   public static final int SCALE_TO_WIDTH = -2;
   public static final int SCALE_TO_HEIGHT = -3;
   public static final int SCALE_TO_PAGE_CONTENT = -4;
   public static final int SCALE_TO_WIDTH_CONTENT = -5;
   public static final int ZOOM_TO_SCAN_REFLOW = -6;
   public static final int ZOOM_TO_REFLOW = -7;
   public static final int ZOOM_TO_COMICE = -8;
   public static final int ZOOM_TO_PAPER = -9;
   public static final float MAX_SCALE = 32.0F;
   public static final float DEFAULT_ACTUAL_SCALE = 1.0F;
   public static double DEFAULT_AUTO_CROP_VALUE;
   public static int DEFAULT_PARAGRAPH_INDENT;
   public static int DEFAULT_LINE_SPACING;
   public static int DEFAULT_CROP_STEP;
   public static int DEFAULT_CROP_MARGIN;

   public static boolean isSpecialScale(int scale) {
      return scale < 0 && scale >= -5;
   }

   public static boolean isScaleToPage(int specialScale) {
      return specialScale == -1;
   }

   public static boolean isScaleToWidth(int specialScale) {
      return specialScale == -2;
   }

   public static boolean isScaleToHeight(int specialScale) {
      return specialScale == -3;
   }

   public static boolean isScaleToPageContent(int specialScale) {
      return specialScale == -4;
   }

   public static boolean isWidthCrop(int specialScale) {
      return specialScale == -5;
   }

   public static boolean isCropPage(int specialScale) {
      return specialScale == -5 || specialScale == -4;
   }

   public static boolean isDualPageLayout(String layoutType) {
      if (StringUtils.isNullOrEmpty(layoutType)) {
         return false;
      }

      layoutType.hashCode();
      return layoutType.equals("dualPage_RTL") || layoutType.equals("dualPage");
   }

   public static boolean isDualPageRTL(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "dualPage_RTL");
   }

   public static boolean isDualPageLTR(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "dualPage");
   }

   public static boolean isSinglePage(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "singlePage") || StringUtils.safelyEquals(layoutType, "singlePageRTL");
   }

   public static boolean isSinglePageRTL(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "singlePageRTL");
   }

   public static boolean isImageReflow(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "imageReflowPage");
   }

   public static boolean isFixedDocumentTextReflow(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "fixedDocumentTextReflowPage");
   }

   public static boolean isTextReflowLayout(String layoutType) {
      if (StringUtils.isNullOrEmpty(layoutType)) {
         return false;
      }

      layoutType.hashCode();
      return layoutType.equals("textReflowPage") || layoutType.equals("fixedDocumentTextReflowPage");
   }

   public static boolean isContinuousPage(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "continuousPage");
   }

   public static boolean isSinglePageNavigation(String layoutType) {
      return StringUtils.safelyEquals(layoutType, "singlePageNavigationList");
   }

   public static boolean isOverMaxScale(float scale) {
      return scale >= 32.0F;
   }
}
