package com.onyx.android.sdk.data;

public class DatabaseInfo {
   private String a;
   private int b;
   private String c;

   public DatabaseInfo(String name, int version, String dbPath) {
      this.a = name;
      this.b = version;
      this.c = dbPath;
   }

   public DatabaseInfo(String dbPath) {
      this.c = dbPath;
   }

   public static DatabaseInfo create(String name, int version, String dbPath) {
      return new DatabaseInfo(name, version, dbPath);
   }

   public static DatabaseInfo create(String dbPath) {
      return new DatabaseInfo(dbPath);
   }

   public String getName() {
      return this.a;
   }

   public void setName(String name) {
      this.a = name;
   }

   public int getVersion() {
      return this.b;
   }

   public void setVersion(int version) {
      this.b = version;
   }

   public String getDbPath() {
      return this.c;
   }

   public void setDbPath(String dbPath) {
      this.c = dbPath;
   }
}
