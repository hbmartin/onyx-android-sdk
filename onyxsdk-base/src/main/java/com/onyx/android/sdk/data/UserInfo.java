package com.onyx.android.sdk.data;

import java.io.Serializable;

public class UserInfo implements Serializable {
   public int userId;
   public int serialNumber;
   public String name;

   public UserInfo() {
   }

   public UserInfo(int userId, String name, int serialNumber) {
      this.userId = userId;
      this.name = name;
      this.serialNumber = serialNumber;
   }
}
