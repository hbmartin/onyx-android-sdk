package com.onyx.android.sdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSSUtils {
   private static Pattern a = Pattern.compile("(\\\\[0-9a-fA-F]+)");

   public static String unicodeToString(String str) {
      try {
         Matcher matcher = a.matcher(str);
         String result = str;
         while (matcher.find()) {
            String escape = matcher.group();
            result = result.replace(escape, String.valueOf((char) Integer.parseInt(escape.substring(1), 16)));
         }
         return result;
      } catch (Throwable failure) {
         Debug.w(failure);
         return str;
      }
   }
}
