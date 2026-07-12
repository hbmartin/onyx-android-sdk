package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.LocaleUtils;

public class OssConfigInfo {
   private static final String k = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String l = "https://oss-us-west-1.aliyuncs.com";
   private static final String m = "onyx-log-collection";
   private static final String n = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String o = "onyx-log-collection-us";
   private static final String p = "https://oss-us-west-1.aliyuncs.com";
   private static final String q = "onyx-cloud";
   private static final String r = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String s = "onyx-cloud-us";
   private static final String t = "https://oss-us-west-1.aliyuncs.com";
   private static final String u = "onyx-ai";
   private static final String v = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String w = "onyx-ai-us";
   private static final String x = "https://oss-us-west-1.aliyuncs.com";
   private static final String y = "onyx-cloud-test";
   private static final String z = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String A = "onyx-cloud-test";
   private static final String B = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String C = "onyx-note";
   private static final String D = "https://oss-cn-shenzhen.aliyuncs.com";
   private static final String E = "onyx-note-us";
   private static final String F = "https://oss-us-west-1.aliyuncs.com";
   private String a;
   private String b;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;
   private String j;

   public static OssConfigInfo createDefaultOss() {
      return LocaleUtils.isChinese() ? createDefaultChineseOss() : createDefaultAmericanOss();
   }

   public static OssConfigInfo createDefaultChineseOss() {
      OssConfigInfo var10000 = new OssConfigInfo();
      var10000.setOssLogBucket("onyx-log-collection");
      var10000.setOssLogEndpoint("https://oss-cn-shenzhen.aliyuncs.com");
      var10000.setOssNoteBucket("onyx-cloud");
      var10000.setOssNoteEndPoint("https://oss-cn-shenzhen.aliyuncs.com");
      var10000.setOssTestBucket("onyx-cloud-test");
      var10000.setOssTestEndpoint("https://oss-cn-shenzhen.aliyuncs.com");
      var10000.setOssDeprecatedBucket("onyx-note");
      var10000.setOssDeprecatedEndPoint("https://oss-cn-shenzhen.aliyuncs.com");
      var10000.setOssAIBucket("onyx-ai");
      var10000.setOssAIEndPoint("https://oss-cn-shenzhen.aliyuncs.com");
      return var10000;
   }

   public static OssConfigInfo createDefaultAmericanOss() {
      OssConfigInfo var10000 = new OssConfigInfo();
      var10000.setOssLogBucket("onyx-log-collection-us");
      var10000.setOssLogEndpoint("https://oss-us-west-1.aliyuncs.com");
      var10000.setOssNoteBucket("onyx-cloud-us");
      var10000.setOssNoteEndPoint("https://oss-us-west-1.aliyuncs.com");
      var10000.setOssTestBucket("onyx-cloud-test");
      var10000.setOssTestEndpoint("https://oss-cn-shenzhen.aliyuncs.com");
      var10000.setOssDeprecatedBucket("onyx-note-us");
      var10000.setOssDeprecatedEndPoint("https://oss-us-west-1.aliyuncs.com");
      var10000.setOssAIBucket("onyx-ai-us");
      var10000.setOssAIEndPoint("https://oss-us-west-1.aliyuncs.com");
      return var10000;
   }

   public String getOssLogBucket() {
      return this.a;
   }

   public void setOssLogBucket(String ossLogBucket) {
      this.a = ossLogBucket;
   }

   public String getOssLogEndpoint() {
      return this.b;
   }

   public void setOssLogEndpoint(String ossLogEndpoint) {
      this.b = ossLogEndpoint;
   }

   public String getOssNoteBucket() {
      return this.c;
   }

   public void setOssNoteBucket(String ossNoteBucket) {
      this.c = ossNoteBucket;
   }

   public String getOssNoteEndPoint() {
      return this.d;
   }

   public void setOssNoteEndPoint(String ossNoteEndPoint) {
      this.d = ossNoteEndPoint;
   }

   public String getOssAIBucket() {
      return this.e;
   }

   public void setOssAIBucket(String ossAIBucket) {
      this.e = ossAIBucket;
   }

   public String getOssAIEndPoint() {
      return this.f;
   }

   public void setOssAIEndPoint(String ossAIEndPoint) {
      this.f = ossAIEndPoint;
   }

   public String getOssTestBucket() {
      return this.g;
   }

   public void setOssTestBucket(String ossTestBucket) {
      this.g = ossTestBucket;
   }

   public String getOssTestEndpoint() {
      return this.h;
   }

   public void setOssTestEndpoint(String ossTestEndpoint) {
      this.h = ossTestEndpoint;
   }

   public String getOssDeprecatedBucket() {
      return this.i;
   }

   public void setOssDeprecatedBucket(String ossDeprecatedBucket) {
      this.i = ossDeprecatedBucket;
   }

   public String getOssDeprecatedEndPoint() {
      return this.j;
   }

   public void setOssDeprecatedEndPoint(String ossDeprecatedEndPoint) {
      this.j = ossDeprecatedEndPoint;
   }
}
