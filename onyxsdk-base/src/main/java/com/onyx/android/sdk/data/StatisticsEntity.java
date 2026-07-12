package com.onyx.android.sdk.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatisticsEntity implements Serializable {
   public static final int TYPE_OPEN = 0;
   public static final int TYPE_PAGE_CHANGE = 1;
   public static final int TYPE_ANNOTATION = 2;
   public static final int TYPE_LOOKUP_DIC = 3;
   public static final int TYPE_CLOSE = 5;
   public static final int TYPE_FINISH = 6;
   public static final int TYPE_RESUME = 9;
   public static final int TYPE_PAUSE = 10;
   public static final String ACTION_ADD = "add";
   public static final int INVALID_ID = -1;
   public static final long MAX_PAGE_DURATION_TIME = 600000L;
   long a = -1L;
   private String b;
   private String c;
   private Integer d;
   private String e;
   private String f;
   private String g;
   private String h;
   private Date i;
   private String j;
   private Integer k;
   private float l;
   private String m;
   private String n = "";
   private String o;
   private String p;
   private String q;
   private String r;
   private String s;
   private String t;
   private List<String> u;
   private Integer v;
   private Integer w;
   private Long x;
   private String y;
   private Integer z;
   private boolean A;

   public StatisticsEntity() {
   }

   private StatisticsEntity(String md5, String sid, String docId, Integer type, Date eventTime, String action) {
      this.c = action;
      this.d = type;
      this.f = md5;
      this.i = eventTime;
      this.j = sid;
      this.g = docId;
   }

   public static StatisticsEntity create(String md5, String sid, String docId, Integer type, Date eventTime, String action) {
      return new StatisticsEntity(md5, sid, docId, type, eventTime, action);
   }

   public List<String> getAuthor() {
      return this.u;
   }

   public StatisticsEntity setAuthor(List<String> author) {
      this.u = author;
      return this;
   }

   public Integer getCurrPage() {
      return this.w;
   }

   public StatisticsEntity setCurrPage(Integer currPage) {
      this.w = currPage;
      return this;
   }

   public Long getDurationTime() {
      Long var1 = this.x;
      return this.x != null ? var1 > 600000L ? 600000L : this.x : null;
   }

   public StatisticsEntity setDurationTime(Long durationTime) {
      this.x = durationTime;
      return this;
   }

   public Integer getLastPage() {
      return this.v;
   }

   public StatisticsEntity setLastPage(Integer lastPage) {
      this.v = lastPage;
      return this;
   }

   public String getName() {
      return this.t;
   }

   public StatisticsEntity setName(String name) {
      this.t = name;
      return this;
   }

   public String getNote() {
      return this.q;
   }

   public StatisticsEntity setNote(String note) {
      this.q = note;
      return this;
   }

   public String getOrgText() {
      return this.p;
   }

   public StatisticsEntity setOrgText(String orgText) {
      this.p = orgText;
      return this;
   }

   public String getPath() {
      return this.r;
   }

   public StatisticsEntity setPath(String path) {
      this.r = path;
      return this;
   }

   public String getTitle() {
      return this.s;
   }

   public StatisticsEntity setTitle(String title) {
      this.s = title;
      return this;
   }

   public String getComment() {
      return this.y;
   }

   public StatisticsEntity setComment(String comment) {
      this.y = comment;
      return this;
   }

   public Integer getScore() {
      return this.z;
   }

   public StatisticsEntity setScore(Integer score) {
      this.z = score;
      return this;
   }

   public String getAccountId() {
      return this.o;
   }

   public void setAccountId(String accountId) {
      this.o = accountId;
   }

   public String getChapter() {
      return this.n;
   }

   public StatisticsEntity setChapter(String chapter) {
      this.n = chapter;
      return this;
   }

   public String getPosition() {
      return this.m;
   }

   public StatisticsEntity setPosition(String position) {
      this.m = position;
      return this;
   }

   public boolean isHideRecord() {
      return this.A;
   }

   public void setHideRecord(boolean hideRecord) {
      this.A = hideRecord;
   }

   public float getReadingProgress() {
      return this.l;
   }

   public StatisticsEntity setReadingProgress(float readingProgress) {
      this.l = readingProgress;
      return this;
   }

   public String getDocId() {
      return this.g;
   }

   public StatisticsEntity setDocId(String docId) {
      this.g = docId;
      return this;
   }

   public String getUuid() {
      return this.b;
   }

   public StatisticsEntity setUuid(String uuid) {
      this.b = uuid;
      return this;
   }

   public Integer getType() {
      return this.d;
   }

   public StatisticsEntity setType(Integer type) {
      this.d = type;
      return this;
   }

   public long getId() {
      return this.a;
   }

   public void setId(long id) {
      this.a = id;
   }

   public String getAction() {
      return this.c;
   }

   public void setAction(String action) {
      this.c = action;
   }

   public String getMac() {
      return this.e;
   }

   public void setMac(String mac) {
      this.e = mac;
   }

   public String getMd5() {
      return this.f;
   }

   public StatisticsEntity setMd5(String md5) {
      this.f = md5;
      return this;
   }

   public String getMd5short() {
      return this.h;
   }

   public StatisticsEntity setMd5short(String md5short) {
      this.h = md5short;
      return this;
   }

   public Date getEventTime() {
      return this.i;
   }

   public void setEventTime(Date eventTime) {
      this.i = eventTime;
   }

   public String getSid() {
      return this.j;
   }

   public void setSid(String sid) {
      this.j = sid;
   }

   public Integer getStatus() {
      return this.k;
   }

   public void setStatus(Integer status) {
      this.k = status;
   }
}
