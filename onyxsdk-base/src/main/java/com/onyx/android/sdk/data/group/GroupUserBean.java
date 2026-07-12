package com.onyx.android.sdk.data.group;

import com.onyx.android.sdk.utils.StringUtils;

public class GroupUserBean {
   public String id;
   public String avatarUrl;
   public String nickname;
   public String avatar;
   public String uid;
   public String name;
   public String phone;
   public String area_code;
   public String email;
   public String sex;
   public GroupRoleBean role;

   public String getDisplayName() {
      if (StringUtils.isNotBlank(this.nickname)) {
         return this.nickname;
      } else if (StringUtils.isNotBlank(this.name)) {
         return this.name;
      } else {
         return StringUtils.isNotBlank(this.phone) ? StringUtils.hidePartPhone(this.phone) : this.email;
      }
   }
}
