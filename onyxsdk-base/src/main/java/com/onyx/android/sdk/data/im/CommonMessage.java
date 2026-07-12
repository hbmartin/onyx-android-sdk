package com.onyx.android.sdk.data.im;

import java.util.Date;

public class CommonMessage {
   private String a;
   private String b;
   private String c;
   private String d;
   private boolean e;
   private Date f;

   public String getMsgSeq() {
      return this.a;
   }

   public void setMsgSeq(String msgSeq) {
      this.a = msgSeq;
   }

   public String getJson() {
      return this.b;
   }

   public void setJson(String json) {
      this.b = json;
   }

   public String getSender() {
      return this.c;
   }

   public void setSender(String sender) {
      this.c = sender;
   }

   public String getReceiver() {
      return this.d;
   }

   public void setReceiver(String receiver) {
      this.d = receiver;
   }

   public boolean isSelf() {
      return this.e;
   }

   public void setSelf(boolean self) {
      this.e = self;
   }

   public Date getTime() {
      return this.f;
   }

   public void setTime(Date time) {
      this.f = time;
   }
}
