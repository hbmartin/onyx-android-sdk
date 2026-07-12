package com.onyx.android.sdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSSUtils {
   private static Pattern a = Pattern.compile("(\\\\[0-9a-fA-F]+)");

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
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
