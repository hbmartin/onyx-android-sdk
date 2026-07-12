package com.onyx.android.sdk.data;

import android.view.KeyEvent;

public class CustomBindKeyBean {
   private String a;
   private String b;

   public CustomBindKeyBean() {
   }

   public CustomBindKeyBean(String args, String action) {
      this.a = action;
      this.b = args;
   }

   public static CustomBindKeyBean createKeyBean(String args, String action) {
      return new CustomBindKeyBean(args, action);
   }

   public static CustomBindKeyBean createKeyBean(String args, int keyCode) {
      return new CustomBindKeyBean(args, KeyEvent.keyCodeToString(keyCode));
   }

   public String getAction() {
      return this.a;
   }

   public void setAction(String action) {
      this.a = action;
   }

   public String getArgs() {
      return this.b;
   }

   public void setArgs(String args) {
      this.b = args;
   }
}
