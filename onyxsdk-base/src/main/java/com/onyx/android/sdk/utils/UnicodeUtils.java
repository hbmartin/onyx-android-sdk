package com.onyx.android.sdk.utils;

import java.lang.Character.UnicodeBlock;

public class UnicodeUtils {
   public static boolean isWhitespace(Character ch) {
      return Character.isWhitespace(ch);
   }

   public static boolean isCJKCharacter(Character ch) {
      return UnicodeBlock.of(ch) == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
   }

   public static boolean isPunctuation(Character ch) {
      return isEnglishPunctuation(ch) || isChinesePunctuation(ch);
   }

   public static boolean isEnglishPunctuation(Character ch) {
      return ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ':' || ch == ';' || ch == 8217;
   }

   public static boolean isChinesePunctuation(Character ch) {
      return ch == '，'
         || ch == 12290
         || ch == '？'
         || ch == '：'
         || ch == 12289
         || ch == '；'
         || ch == '！'
         || ch == 8220
         || ch == 8221
         || ch == 8216
         || ch == 8217;
   }
}
