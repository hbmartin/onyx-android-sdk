package com.onyx.android.sdk.data.dict;

import com.onyx.android.sdk.data.GObject;
import java.util.List;

public class DictionaryInfo implements Cloneable {
   public static final int SOURCE_LOCAL = 0;
   public static final int SOURCE_CLOUD = 1;
   public String md5;
   public String version;
   public String type;
   public String name;
   public String description;
   public String copyright;
   public String author;
   public String email;
   public GObject features;
   public List<String> files;
   public long articleCount;
   public long wordCount;
   public String sourceLanguage;
   public String targetLanguage;
   public String dictPath;
   public String styleSheet;
   public String[] styles;
   public DictVoiceInfo dictVoiceInfo;
   public int dictSource = 0;

   public boolean isCloudSource() {
      return this.dictSource == 1;
   }

   public DictionaryInfo clone() throws CloneNotSupportedException {
      try {
         return (DictionaryInfo) super.clone();
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
