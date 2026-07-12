package com.onyx.android.sdk.data.note.background;

import com.onyx.android.sdk.utils.LocaleUtils;

public class BackgroundLang {
   public static int ALL_LANG;
   public static int CN;
   public static int EN;

   public static boolean filter(int lang) {
      if (lang == ALL_LANG) {
         return true;
      } else {
         return LocaleUtils.isChinese() ? lang == CN : lang == EN;
      }
   }
}
