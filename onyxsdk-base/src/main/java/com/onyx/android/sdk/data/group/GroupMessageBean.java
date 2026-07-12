package com.onyx.android.sdk.data.group;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.StringUtils;

public class GroupMessageBean {
   public static final String TYPE_JOIN_GROUP_APPLY = "apply";
   public static final String TYPE_JOIN_GROUP_REJECTED = "rejected";
   public static final String TYPE_JOIN_GROUP_APPROVED = "approved";
   public static final String TYPE_GROUP_MEMBER_EXIT = "exit";
   public static final String TYPE_GROUP_MEMBER_REMOVED = "removed";
   public static final String TYPE_GROUP_DISBANDED = "disbanded";
   public static final int APPLY_STATUS_PENDING = 0;
   public static final int APPLY_STATUS_APPROVED = 1;
   public static final int APPLY_STATUS_REJECTED = 2;
   public String type;
   @JSONField(name = "user_uid")
   public String userId;
   public String description;
   public String senderUid;
   public String noteGroupId;
   public GroupInfoBean noteGroup;
   public GroupUserBean recipient;
   public GroupUserBean sender;
   public int applyStatus;

   @JSONField(serialize = false)
   public String getGroupName() {
      GroupInfoBean var1;
      return (var1 = this.noteGroup) == null ? "" : var1.name;
   }

   @JSONField(serialize = false)
   public String getSenderName() {
      GroupUserBean var1;
      return (var1 = this.sender) == null ? "" : var1.getDisplayName();
   }

   @JSONField(serialize = false)
   public boolean isApply() {
      return StringUtils.safelyEquals(this.type, "apply");
   }

   @JSONField(serialize = false)
   public boolean applyPending() {
      return this.isApply() && this.applyStatus == 0;
   }

   @JSONField(serialize = false)
   public boolean applyApproved() {
      return this.isApply() && this.applyStatus == 1;
   }

   @JSONField(serialize = false)
   public boolean applyRejected() {
      return this.isApply() && this.applyStatus == 2;
   }
}
