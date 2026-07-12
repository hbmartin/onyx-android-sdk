package com.onyx.android.sdk.data;

public class WebQueryThirdSiteBean {
   private String a;
   private String b;

   public WebQueryThirdSiteBean() {
   }

   public WebQueryThirdSiteBean(String name, String link) {
      this.a = name;
      this.b = link;
   }

   public String getName() {
      return this.a;
   }

   public void setName(String name) {
      this.a = name;
   }

   public String getLink() {
      return this.b;
   }

   public void setLink(String link) {
      this.b = link;
   }
}
