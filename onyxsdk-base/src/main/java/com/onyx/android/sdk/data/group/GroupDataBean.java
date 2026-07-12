package com.onyx.android.sdk.data.group;

import java.util.List;

public class GroupDataBean {
   public String groupId;
   public String name;
   public String description;
   public int type;
   public List<String> users;

   public String getGroupId() {
      return this.groupId;
   }

   public GroupDataBean setGroupId(String groupId) {
      this.groupId = groupId;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public GroupDataBean setName(String name) {
      this.name = name;
      return this;
   }

   public String getDescription() {
      return this.description;
   }

   public GroupDataBean setDescription(String description) {
      this.description = description;
      return this;
   }

   public int getType() {
      return this.type;
   }

   public GroupDataBean setType(int type) {
      this.type = type;
      return this;
   }

   public List<String> getUsers() {
      return this.users;
   }

   public GroupDataBean setUsers(List<String> users) {
      this.users = users;
      return this;
   }
}
