package com.onyx.android.sdk.data;

import java.util.List;

public class DictionaryQuery {
   public static final int DICT_STATE_ERROR = -2;
   public static final int DICT_STATE_PARAM_ERROR = -1;
   public static final int DICT_STATE_QUERY_SUCCESSFUL = 0;
   public static final int DICT_STATE_QUERY_FAILED = 1;
   public static final int DICT_STATE_LOADING = 2;
   public static final int DICT_STATE_NO_DATA = 3;
   private int a;
   private List<DictionaryQuery.Dictionary> b;

   public DictionaryQuery(int state) {
      this.a = state;
   }

   public DictionaryQuery.Dictionary createDictionary(int state, String dictName, String keyword, String explanation) {
      return new DictionaryQuery.Dictionary(state, dictName, keyword, explanation);
   }

   public int getState() {
      return this.a;
   }

   public void setState(int state) {
      this.a = state;
   }

   public List<DictionaryQuery.Dictionary> getList() {
      return this.b;
   }

   public void setList(List<DictionaryQuery.Dictionary> list) {
      this.b = list;
   }

   public static class Dictionary {
      private int a;
      private String b;
      private String c;
      private String d;

      public Dictionary(int state, String dictName, String keyword, String explanation) {
         this.a = state;
         this.b = dictName;
         this.c = keyword;
         this.d = explanation;
      }

      public int getState() {
         return this.a;
      }

      public void setState(int state) {
         this.a = state;
      }

      public String getDictName() {
         return this.b;
      }

      public void setDictName(String dictName) {
         this.b = dictName;
      }

      public String getExplanation() {
         return this.d;
      }

      public void setExplanation(String explanation) {
         this.d = explanation;
      }

      public String getKeyword() {
         return this.c;
      }

      public void setKeyword(String keyword) {
         this.c = keyword;
      }
   }
}
