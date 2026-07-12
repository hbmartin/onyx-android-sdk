package com.onyx.android.sdk.common;

import android.content.Context;
import com.tencent.mmkv.MMKV;

public class MMKVBuilder {
   private String a;
   private int b;

   private MMKVBuilder(String mmapID, int mmapMode) {
      this.a = mmapID;
      this.b = mmapMode;
   }

   public static MMKVBuilder init(Context context, String id, int mode) {
      MMKV.initialize(context);
      return new MMKVBuilder(id, mode);
   }

   public static MMKVBuilder init(Context context, String id) {
      return init(context, id, 1);
   }

   public MMKV getMMKV() {
      return MMKV.mmkvWithID(this.a, this.b);
   }

   public void putString(String k, String v) {
      this.getMMKV().putString(k, v);
   }

   public void putInt(String k, int v) {
      this.getMMKV().putInt(k, v);
   }

   public void putBoolean(String k, boolean v) {
      this.getMMKV().putBoolean(k, v);
   }

   public void putFloat(String k, float v) {
      this.getMMKV().putFloat(k, v);
   }

   public void putLong(String k, long v) {
      this.getMMKV().putLong(k, v);
   }

   public String getString(String k, String defaul) {
      return this.getMMKV().getString(k, defaul);
   }

   public int getInt(String k, int defaul) {
      return this.getMMKV().getInt(k, defaul);
   }

   public boolean getBoolean(String k, boolean defaul) {
      return this.getMMKV().getBoolean(k, defaul);
   }

   public float getFloat(String k, float defaul) {
      return this.getMMKV().getFloat(k, defaul);
   }

   public long getLong(String k, long defaul) {
      return this.getMMKV().getLong(k, defaul);
   }

   public boolean containsKey(String key) {
      return this.getMMKV().contains(key);
   }

   public void remove(String key) {
      this.getMMKV().remove(key);
   }
}
