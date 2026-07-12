package com.onyx.android.sdk.data.im;

public class Message {
   public static final String TYPE_TEXT = "text";
   public static final String TYPE_FILE_DOWNLOAD = "file";
   public static final String TYPE_PRODUCT_DOWNLOAD = "product";
   public static final String TYPE_LIBRARY_CLEAR = "library_clear";
   public static final String TYPE_LOGIN = "login";
   public static final String TYPE_SCREEN_SAVER = "screen_saver";
   public static final String TYPE_NOTIFICATION = "notification";
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
   private long k;

   public Message() {
   }

   public Message(String channel, String action, String type, String id, String content) {
      this.c = type;
      this.a = channel;
      this.b = action;
      this.d = id;
      this.f = content;
   }

   public Message(String action, String content) {
      this.b = action;
      this.f = content;
   }

   public Message(String channel, String action, String content, String event) {
      this.a = channel;
      this.b = action;
      this.f = content;
      this.g = event;
   }

   public Message(String action) {
      this.b = action;
   }

   public static Message create(String channel, String action, String type, String id, String content) {
      return new Message(channel, action, type, id, content);
   }

   public static Message create(String channel, String action, String content, String event) {
      return new Message(channel, action, content, event);
   }

   public static Message create(String action, String content) {
      return new Message(action, content);
   }

   public static Message create(String action) {
      return new Message(action);
   }

   public String getChannel() {
      return this.a;
   }

   public Message setChannel(String channel) {
      this.a = channel;
      return this;
   }

   public String getAction() {
      return this.b;
   }

   public Message setAction(String action) {
      this.b = action;
      return this;
   }

   public String getId() {
      return this.d;
   }

   public Message setId(String id) {
      this.d = id;
      return this;
   }

   public String getContent() {
      return this.f;
   }

   public Message setContent(String content) {
      this.f = content;
      return this;
   }

   public String getType() {
      return this.c;
   }

   public Message setType(String type) {
      this.c = type;
      return this;
   }

   public String getEvent() {
      return this.g;
   }

   public Message setEvent(String event) {
      this.g = event;
      return this;
   }

   public String getMac() {
      return this.h;
   }

   public Message setMac(String mac) {
      this.h = mac;
      return this;
   }

   public String getOriginData() {
      return this.i;
   }

   public Message setOriginData(String originData) {
      this.i = originData;
      return this;
   }

   public String getArgs() {
      return this.j;
   }

   public Message setArgs(String args) {
      this.j = args;
      return this;
   }

   public long getTimestamp() {
      return this.k;
   }

   public Message setTimestamp(long timestamp) {
      this.k = timestamp;
      return this;
   }

   public String toSimpleString() {
      return "originData:" + this.i;
   }
}
