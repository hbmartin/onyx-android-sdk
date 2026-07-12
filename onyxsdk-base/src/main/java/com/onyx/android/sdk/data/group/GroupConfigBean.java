package com.onyx.android.sdk.data.group;

public class GroupConfigBean {
   private static final int a = 3;
   private static final int b = 3;
   public int maxGroupCount;
   public int maxMemberCount;

   public static GroupConfigBean defaultConfig() {
      GroupConfigBean var10000 = new GroupConfigBean();
      var10000.maxGroupCount = 3;
      var10000.maxMemberCount = 3;
      return var10000;
   }
}
