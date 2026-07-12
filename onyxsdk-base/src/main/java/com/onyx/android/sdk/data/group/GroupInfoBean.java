package com.onyx.android.sdk.data.group;

import com.alibaba.fastjson2.annotation.JSONField;
import java.util.Date;
import java.util.List;

public class GroupInfoBean {
   @JSONField(name = "_id")
   public String groupId;
   public String name;
   public String description;
   public String parentUniqueId;
   public String creater;
   public String status;
   public int type;
   public Date createdAt;
   public Date updatedAt;
   public List<GroupUserBean> userlist;
}
