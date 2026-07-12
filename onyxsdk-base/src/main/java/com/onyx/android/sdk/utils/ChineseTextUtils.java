package com.onyx.android.sdk.utils;

public class ChineseTextUtils {
   public static String removeWhiteSpacesBetweenChineseText(String text) {
      if (StringUtils.isBlank(text)) {
         return "";
      }

      text = StringUtils.deleteNewlineSymbol(text);

      while (text.matches(".*[\\u4E00-\\u9FA5][\\s]+[\\u4E00-\\u9FA5].*")) {
         text = text.replaceAll("([\\u4E00-\\u9FA5])[\\s]+([\\u4E00-\\u9FA5])", "$1$2");
      }

      return text;
   }
}
