package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.StringUtils;

public class ClusterHost {
   public static String DEFAULT_VIETNAM_HOST;
   public static String DEFAULT_CHINESE_BOOX_HOST;
   public static String DEFAULT_AMERICAN_BOOX_HOST;
   public static String DEFAULT_EUR_BOOX_HOST;
   public static String DEFAULT_CHINESE_COUCH_BASE_WSS_HOST;
   public static String DEFAULT_AMERICAN_COUCH_BASE_WSS_HOST;
   public static String DEFAULT_CHINESE_MESSAGE_CB_WSS_HOST;
   public static String DEFAULT_AMERICAN_MESSAGE_CB_WSS_HOST;
   public static String DEFAULT_EUR_MESSAGE_CB_WSS_HOST;
   public static String DEFAULT_COUCH_BASE_TEST_HOST;
   public static String DEFAULT_TEST_COUCH_BASE_WSS_HOST;
   public static String DEFAULT_CHINESE_MATERIAL_HOST;
   public static String DEFAULT_AMERICAN_MATERIAL_HOST;
   public static String DEFAULT_EUR_MATERIAL_HOST;
   public static String DEFAULT_TEST_HOST;
   public static String DEFAULT_TEST_MATERIAL_HOST;
   public static String DEFAULT_CHINESE_DATA_HOST;
   public static String DEFAULT_AMERICAN_DATA_HOST;
   public static String DEFAULT_EUR_DATA_HOST;
   public static String DEFAULT_CHINESE_LOG_HOST;
   public static String DEFAULT_AMERICAN_LOG_HOST;
   private String a;
   private String b;
   private String c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;

   public static ClusterHost createDefaultChineseHost() {
      ClusterHost var10000 = new ClusterHost();
      var10000.setOnyxHostBaseUrl(DEFAULT_CHINESE_BOOX_HOST);
      var10000.setOnyxCloudDataHostBaseUrl(DEFAULT_CHINESE_DATA_HOST);
      var10000.setOnyxLogHostBaseUrl(DEFAULT_CHINESE_LOG_HOST);
      var10000.setOnyxContentHostBaseUrl(DEFAULT_CHINESE_BOOX_HOST);
      var10000.setOnyxSend2BooxHostBaseUrl(DEFAULT_CHINESE_BOOX_HOST);
      var10000.setShopBookUrl(DEFAULT_CHINESE_BOOX_HOST);
      var10000.setOnyxCouchbaseWSSHost(DEFAULT_CHINESE_COUCH_BASE_WSS_HOST);
      var10000.setOnyxMessageReplicator(DEFAULT_CHINESE_MESSAGE_CB_WSS_HOST);
      var10000.setOnyxMaterialHostBaseUrl(DEFAULT_CHINESE_MATERIAL_HOST);
      return var10000;
   }

   public static ClusterHost createDefaultAmericanHost() {
      ClusterHost var10000 = new ClusterHost();
      var10000.setOnyxHostBaseUrl(DEFAULT_AMERICAN_BOOX_HOST);
      var10000.setOnyxCloudDataHostBaseUrl(DEFAULT_AMERICAN_DATA_HOST);
      var10000.setOnyxLogHostBaseUrl(DEFAULT_AMERICAN_LOG_HOST);
      var10000.setOnyxContentHostBaseUrl(DEFAULT_AMERICAN_BOOX_HOST);
      var10000.setOnyxSend2BooxHostBaseUrl(DEFAULT_AMERICAN_BOOX_HOST);
      var10000.setShopBookUrl(DEFAULT_AMERICAN_BOOX_HOST);
      var10000.setOnyxCouchbaseWSSHost(DEFAULT_AMERICAN_COUCH_BASE_WSS_HOST);
      var10000.setOnyxMessageReplicator(DEFAULT_AMERICAN_MESSAGE_CB_WSS_HOST);
      var10000.setOnyxMaterialHostBaseUrl(DEFAULT_AMERICAN_MATERIAL_HOST);
      return var10000;
   }

   public static ClusterHost createDefaultVietnamHost() {
      return createDefaultAmericanHost().setShopBookUrl(DEFAULT_VIETNAM_HOST);
   }

   public static ClusterHost createDefaultEURHost() {
      ClusterHost var10000 = new ClusterHost();
      var10000.setOnyxHostBaseUrl(DEFAULT_EUR_BOOX_HOST);
      var10000.setOnyxCloudDataHostBaseUrl(DEFAULT_EUR_DATA_HOST);
      var10000.setOnyxLogHostBaseUrl(DEFAULT_EUR_DATA_HOST);
      var10000.setOnyxContentHostBaseUrl(DEFAULT_EUR_BOOX_HOST);
      var10000.setOnyxSend2BooxHostBaseUrl(DEFAULT_EUR_BOOX_HOST);
      var10000.setShopBookUrl(DEFAULT_EUR_BOOX_HOST);
      var10000.setOnyxCouchbaseWSSHost(DEFAULT_EUR_MESSAGE_CB_WSS_HOST);
      var10000.setOnyxMessageReplicator(DEFAULT_EUR_MESSAGE_CB_WSS_HOST);
      var10000.setOnyxMaterialHostBaseUrl(DEFAULT_EUR_MATERIAL_HOST);
      return var10000;
   }

   public static ClusterHost createTestHost() {
      ClusterHost var10000 = new ClusterHost();
      var10000.setOnyxHostBaseUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxCloudDataHostBaseUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxLogHostBaseUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxContentHostBaseUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxSend2BooxHostBaseUrl(DEFAULT_TEST_HOST);
      var10000.setShopBookUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxCouchbaseWSSHost(DEFAULT_CHINESE_COUCH_BASE_WSS_HOST);
      var10000.setOnyxMessageReplicator(DEFAULT_CHINESE_MESSAGE_CB_WSS_HOST);
      var10000.setOnyxMaterialHostBaseUrl(DEFAULT_TEST_MATERIAL_HOST);
      return var10000;
   }

   public static ClusterHost createCouchbaseTestHost() {
      ClusterHost var10000 = new ClusterHost();
      var10000.setOnyxHostBaseUrl(DEFAULT_COUCH_BASE_TEST_HOST);
      var10000.setOnyxCloudDataHostBaseUrl(DEFAULT_COUCH_BASE_TEST_HOST);
      var10000.setOnyxLogHostBaseUrl(DEFAULT_COUCH_BASE_TEST_HOST);
      var10000.setOnyxContentHostBaseUrl(DEFAULT_COUCH_BASE_TEST_HOST);
      var10000.setOnyxSend2BooxHostBaseUrl(DEFAULT_COUCH_BASE_TEST_HOST);
      var10000.setShopBookUrl(DEFAULT_TEST_HOST);
      var10000.setOnyxCouchbaseWSSHost(DEFAULT_CHINESE_COUCH_BASE_WSS_HOST);
      var10000.setOnyxMessageReplicator(DEFAULT_CHINESE_MESSAGE_CB_WSS_HOST);
      return var10000;
   }

   public boolean validCheck() {
      return StringUtils.isNotBlank(this.a)
         && StringUtils.isNotBlank(this.b)
         && StringUtils.isNotBlank(this.c)
         && StringUtils.isNotBlank(this.d)
         && StringUtils.isNotBlank(this.e)
         && StringUtils.isNotBlank(this.f)
         && StringUtils.isNotBlank(this.h);
   }

   public String getOnyxHostBaseUrl() {
      return this.a;
   }

   public void setOnyxHostBaseUrl(String onyxHostBaseUrl) {
      this.a = onyxHostBaseUrl;
   }

   public String getOnyxCloudDataHostBaseUrl() {
      return this.b;
   }

   public void setOnyxCloudDataHostBaseUrl(String onyxCloudDataHostBaseUrl) {
      this.b = onyxCloudDataHostBaseUrl;
   }

   public String getOnyxLogHostBaseUrl() {
      return this.c;
   }

   public void setOnyxLogHostBaseUrl(String onyxLogHostBaseUrl) {
      this.c = onyxLogHostBaseUrl;
   }

   public String getOnyxContentHostBaseUrl() {
      return this.d;
   }

   public void setOnyxContentHostBaseUrl(String onyxContentHostBaseUrl) {
      this.d = onyxContentHostBaseUrl;
   }

   public String getShopBookUrl() {
      return this.h;
   }

   public ClusterHost setShopBookUrl(String shopBookUrl) {
      this.h = shopBookUrl;
      return this;
   }

   public String getOnyxMaterialHostBaseUrl() {
      return this.i;
   }

   public void setOnyxMaterialHostBaseUrl(String onyxMaterialHostBaseUrl) {
      this.i = onyxMaterialHostBaseUrl;
   }

   public String getOnyxSend2BooxHostBaseUrl() {
      return this.e;
   }

   public void setOnyxSend2BooxHostBaseUrl(String onyxSend2BooxHostBaseUrl) {
      this.e = onyxSend2BooxHostBaseUrl;
   }

   public String getOnyxCouchbaseWSSHost() {
      return this.f;
   }

   public void setOnyxCouchbaseWSSHost(String onyxCouchbaseWSSHost) {
      this.f = onyxCouchbaseWSSHost;
   }

   public void setOnyxMessageReplicator(String onyxMessageReplicator) {
      this.g = onyxMessageReplicator;
   }

   public String getOnyxMessageReplicator() {
      return this.g;
   }
}
