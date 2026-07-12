package com.onyx.android.sdk.data;

import java.io.Serializable;

public class LunarData implements Serializable {
   public String lunarStr;

   public LunarData setLunarStr(String lunarStr) {
      this.lunarStr = lunarStr;
      return this;
   }
}
