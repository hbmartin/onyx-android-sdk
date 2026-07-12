package com.onyx.android.sdk.data.note;

import android.graphics.Rect;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.annotation.JSONField;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NotePageInfo implements Serializable {
   public Map<String, PageInfo> pageInfoMap = new HashMap<>();
   private CanvasExpandType a = CanvasExpandType.DEFAULT;
   private NoteZoomInfo b;
   private Rect c;
   private String d = "";

   public void addPageInfo(String pageId, PageInfo pageInfo) {
      this.pageInfoMap.put(pageId, pageInfo);
   }

   public void mergePageInfo(String pageId, PageInfo pageInfo) {
      PageInfo var3;
      if ((var3 = this.pageInfoMap.get(pageId)) != null) {
         pageInfo.mergePageInfo(var3);
      }

      this.pageInfoMap.put(pageId, pageInfo);
   }

   public void updatePageInfo(String pageId, PageInfo pageInfo) {
      PageInfo var3;
      if ((var3 = this.pageInfoMap.get(pageId)) != null) {
         var3.updatePageInfo(pageInfo);
      } else {
         this.pageInfoMap.put(pageId, pageInfo);
      }
   }

   public void removePageInfo(String pageId) {
      this.pageInfoMap.remove(pageId);
   }

   public Map<String, PageInfo> getPageInfoMap() {
      return this.pageInfoMap;
   }

   public void setPageInfoMap(Map<String, PageInfo> pageInfoMap) {
      this.pageInfoMap = pageInfoMap;
   }

   public NoteZoomInfo getZoomInfo() {
      return this.b;
   }

   public void setZoomInfo(NoteZoomInfo zoomInfo) {
      this.b = zoomInfo;
   }

   @Nullable
   public PageInfo getPageInfo(String pageId) {
      return this.pageInfoMap.get(pageId);
   }

   public String getCoverPageId() {
      return this.d;
   }

   public void setCoverPageId(String coverPageId) {
      this.d = coverPageId;
   }

   public NotePageInfo setCanvasExpandType(CanvasExpandType canvasExpandType) {
      this.a = canvasExpandType;
      return this;
   }

   @JSONField(serialize = false)
   public void mergePageInfo(NotePageInfo mergePageInfo) {
      if (mergePageInfo != null) {
         Iterator var5 = mergePageInfo.getPageInfoMap().entrySet().iterator();

         while (var5.hasNext()) {
            Entry var2;
            String var3 = (String)(var2 = (Entry)var5.next()).getKey();
            PageInfo var4;
            if ((var4 = this.pageInfoMap.get(var3)) == null) {
               this.pageInfoMap.put(var3, (PageInfo)var2.getValue());
            } else {
               var4.mergePageInfo((PageInfo)var2.getValue());
            }
         }
      }
   }

   public Rect getDefaultPageRect() {
      return this.c;
   }

   public void setDefaultPageRect(Rect defaultPageRect) {
      this.c = defaultPageRect;
   }

   public void setCanvasExpandMenuId(String menuId) {
      this.a = CanvasExpandType.safelyValueOf(menuId);
   }

   public CanvasExpandType getCanvasExpandType() {
      return this.a;
   }

   public String loadCanvasExpandType() {
      return this.a.name();
   }
}
