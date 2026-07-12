package com.onyx.android.sdk.data;

public class ComplainInfo {
   public int reasonCode;
   public String reason;
   public String prompt;
   public String content;

   public static ComplainInfo create(int reasonCode, String prompt, String content) {
      return new ComplainInfo().setReasonCode(reasonCode).setPrompt(prompt).setContent(content);
   }

   public ComplainInfo setReasonCode(int reasonCode) {
      this.reasonCode = reasonCode;
      return this;
   }

   public ComplainInfo setReason(String reason) {
      this.reason = reason;
      return this;
   }

   public ComplainInfo setPrompt(String prompt) {
      this.prompt = prompt;
      return this;
   }

   public ComplainInfo setContent(String content) {
      this.content = content;
      return this;
   }
}
