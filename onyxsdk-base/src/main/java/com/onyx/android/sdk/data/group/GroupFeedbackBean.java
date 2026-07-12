package com.onyx.android.sdk.data.group;

import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.List;

public class GroupFeedbackBean {
   public String groupId;
   public String type;
   public String note;
   public List<String> evidences;
   public List<String> localPaths;

   public GroupFeedbackBean setGroupId(String groupId) {
      this.groupId = groupId;
      return this;
   }

   public GroupFeedbackBean setType(String type) {
      this.type = type;
      return this;
   }

   public GroupFeedbackBean setNote(String note) {
      this.note = note;
      return this;
   }

   public GroupFeedbackBean setLocalPaths(List<String> localPaths) {
      this.localPaths = localPaths;
      return this;
   }

   public GroupFeedbackBean addEvidence(String evidence) {
      List var2;
      List var10001 = var2 = CollectionUtils.ensureList(this.evidences);
      this.evidences = var2;
      var10001.add(evidence);
      return this;
   }
}
