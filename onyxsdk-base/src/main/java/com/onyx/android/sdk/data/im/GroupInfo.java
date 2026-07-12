package com.onyx.android.sdk.data.im;

public class GroupInfo {
   public String groupId;
   public String reason;

   public GroupInfo setGroupId(String groupId) {
      this.groupId = groupId;
      return this;
   }

   public GroupInfo setReason(String reason) {
      this.reason = reason;
      return this;
   }
}
