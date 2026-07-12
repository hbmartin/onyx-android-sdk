package com.onyx.android.sdk.data.note.background;

import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.Size;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.NumberUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

public class BkGroundRes implements Serializable {
   private static final String j = "_";
   public static String DEFAULT_BACKGROUND_RES_ID = a();
   private int a = NoteBackgroundType.defaultBackground();
   private String b;
   private int c;
   private boolean d;
   private String e;
   private String f;
   private String g;
   private boolean h;
   private boolean i = true;
   public float width;
   public float height;
   public String title;

   public BkGroundRes() {
   }

   public BkGroundRes(BkGroundRes res) {
      if (res != null) {
         this.setType(res.a).setValue(res.b).setResIndex(res.c);
      }
   }

   public BkGroundRes(int type) {
      this.setType(type);
   }

   public static BkGroundRes create() {
      return new BkGroundRes();
   }

   public static String getBkGroundResId(BkGroundRes bkGroundRes) {
      if (isPdfBkGroundRes(bkGroundRes)) {
         return getPDFBKGroundUniqueId(bkGroundRes.e);
      } else {
         return isFileBkGroundRes(bkGroundRes) ? bkGroundRes.e : String.valueOf(bkGroundRes.a);
      }
   }

   public static BkGroundRes createEmpty() {
      return new BkGroundRes().setType(NoteBackgroundType.defaultBackground()).setResId(DEFAULT_BACKGROUND_RES_ID);
   }

   public static BkGroundRes createEmpty(boolean isLandscape) {
      int type = isLandscape ? 100 : 0;
      return new BkGroundRes().setType(type).setResId(String.valueOf(type));
   }

   public static BkGroundRes create(int type) {
      return new BkGroundRes(type);
   }

   public static int resIndexMapping(int pageIndex, int resCount) {
      return resCount == 0 ? 0 : pageIndex % resCount;
   }

   public static boolean isPdfBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && bkGroundRes.getType() == 27;
   }

   public static boolean isImageBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && bkGroundRes.getType() == 26;
   }

   public static boolean isFileBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return isPdfBkGroundRes(bkGroundRes) || isImageBkGroundRes(bkGroundRes);
   }

   public static boolean isLocalFileBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && !bkGroundRes.isCloud() && isFileBkGroundRes(bkGroundRes);
   }

   public static boolean isValidLocalFileBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null
         && !bkGroundRes.isCloud()
         && isFileBkGroundRes(bkGroundRes)
         && StringUtils.isNotBlank(bkGroundRes.getResId())
         && FileUtils.fileExist(bkGroundRes.getValue());
   }

   public static boolean isFixBkGroundRes(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && !isFileBkGroundRes(bkGroundRes);
   }

   public static boolean isBkGroundEmpty(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && bkGroundRes.getType() == NoteBackgroundType.defaultBackground();
   }

   public static boolean isEmptyOrInvisible(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes == null ? false : !bkGroundRes.isVisible() || isBkGroundEmpty(bkGroundRes);
   }

   public static boolean isRichTextBkGroundRes(BkGroundRes bkGroundRes) {
      return bkGroundRes == null ? false : NoteBackgroundType.isRichTextBKGround(bkGroundRes.getType());
   }

   public static boolean isBkGroundResWithLocalResource(@Nullable BkGroundRes bkGroundRes) {
      return bkGroundRes != null && !bkGroundRes.d ? isFileBkGroundRes(bkGroundRes) : false;
   }

   @Nullable
   public static String getPDFBKGroundUniqueId(String resId) {
      if (StringUtils.isNullOrEmpty(resId)) {
         return null;
      }

      int var1;
      return (var1 = resId.lastIndexOf("_")) < 0 ? resId : resId.substring(0, var1);
   }

   public static int getPDFBKGroundPageIndex(String resId) {
      if (StringUtils.isNullOrEmpty(resId)) {
         return 0;
      }

      int var1;
      return (var1 = resId.lastIndexOf("_")) < 0 ? 0 : NumberUtils.parseInt(resId.substring(var1 + 1));
   }

   @Nullable
   public static String getBKGroundUniqueId(BkGroundRes bkGroundRes) {
      return isPdfBkGroundRes(bkGroundRes) ? getPDFBKGroundUniqueId(bkGroundRes.getResId()) : bkGroundRes.getResId();
   }

   @Nullable
   public static String getBKGroundResourceId(BkGroundRes bkGroundRes) {
      if (bkGroundRes == null) {
         return null;
      } else if (isPdfBkGroundRes(bkGroundRes)) {
         return getPDFBKGroundUniqueId(bkGroundRes.getResId());
      } else {
         return isImageBkGroundRes(bkGroundRes) ? bkGroundRes.getResId() : null;
      }
   }

   private static String a() {
      return ResManager.isLandscapeWindows() ? String.valueOf(100) : String.valueOf(0);
   }

   public static boolean isEmptyBKGroundId(String bgId) {
      return StringUtils.safelyEquals(bgId, String.valueOf(100)) || StringUtils.safelyEquals(bgId, String.valueOf(0));
   }

   public static boolean isEmptyBKGroundType(int type) {
      return type == 0 || type == 100;
   }

   public int getType() {
      return this.a;
   }

   public BkGroundRes setType(int type) {
      this.a = type;
      return this;
   }

   public String getValue() {
      return this.b;
   }

   public BkGroundRes setValue(String value) {
      this.b = value;
      return this;
   }

   public BkGroundRes setContent(String content) {
      this.g = content;
      return this;
   }

   public String getContent() {
      return this.g;
   }

   public BkGroundRes setContentPageId(String contentPageId) {
      this.f = contentPageId;
      return this;
   }

   public String getContentPageId() {
      return this.f;
   }

   public boolean isVisible() {
      return this.i;
   }

   public BkGroundRes setVisible(boolean visible) {
      this.i = visible;
      return this;
   }

   public boolean isCloud() {
      return this.d;
   }

   public BkGroundRes setCloud(boolean cloud) {
      this.d = cloud;
      return this;
   }

   public RectF getBkGroundResRectF() {
      float var2 = this.getWidth();
      float var1 = this.getHeight();
      return new RectF(0.0F, 0.0F, var2, var1);
   }

   public int getResIndex() {
      return this.c;
   }

   public BkGroundRes setResIndex(int resIndex) {
      this.c = resIndex;
      return this;
   }

   public String getResId() {
      return this.e;
   }

   public BkGroundRes setResId(String resId) {
      this.e = resId;
      return this;
   }

   public boolean isGlobal() {
      return this.h;
   }

   public void setGlobal(boolean global) {
      this.h = global;
   }

   public BkGroundRes setWidth(float width) {
      this.width = width;
      return this;
   }

   public float getWidth() {
      if (this.width <= 0.0F) {
         this.width = ResManager.getWindowDefaultWidth();
      }

      return this.width;
   }

   public BkGroundRes setHeight(float height) {
      this.height = height;
      return this;
   }

   public float getHeight() {
      if (this.height <= 0.0F) {
         this.height = ResManager.getWindowDefaultHeight();
      }

      return this.height;
   }

   public BkGroundRes setSize(Size size) {
      this.setWidth(size.width);
      this.setHeight(size.height);
      return this;
   }

   public boolean isEqualsNotIndex(@Nullable BkGroundRes res) {
      return res == null ? false : res.d == this.d && res.a == this.a && StringUtils.safelyEquals(res.b, this.b);
   }

   public BkGroundRes bkGroundResClone() {
      BkGroundRes var1;
      BkGroundRes var10000 = var1 = create();
      var1.setCloud(this.d);
      var1.setGlobal(this.h);
      var1.setResId(this.getResId());
      var1.setResIndex(this.c);
      var1.setType(this.a);
      var1.setValue(this.b);
      var1.setWidth(this.width);
      var1.setHeight(this.height);
      var10000.title = this.title;
      return var10000;
   }
}
