package com.onyx.android.sdk.data.note.background;

import com.onyx.android.sdk.utils.CollectionUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteBackground implements Serializable {
   public Map<String, BkGroundRes> pageBKGroundMap = new HashMap<>();
   public BkGroundRes docBKGround = BkGroundRes.create();
   public boolean useDocBKGround;
   private BKGroundConfig a = new BKGroundConfig();

   public void setPageBKGroundMap(Map<String, BkGroundRes> pageBKGroundMap) {
      this.pageBKGroundMap = pageBKGroundMap;
   }

   public Map<String, BkGroundRes> getPageBKGroundMap() {
      return this.pageBKGroundMap;
   }

   public BkGroundRes getDocBKGround() {
      return this.docBKGround;
   }

   public void setDocBKGround(BkGroundRes docBKGround) {
      this.docBKGround = docBKGround;
   }

   public boolean isUseDocBKGround() {
      return this.useDocBKGround;
   }

   public void setUseDocBKGround(boolean useDocBKGround) {
      this.useDocBKGround = useDocBKGround;
      this.docBKGround.setGlobal(useDocBKGround);
   }

   public BkGroundRes getBkGroundRes(String pageId) {
      BkGroundRes var2;
      if ((var2 = this.pageBKGroundMap.get(pageId)) == null) {
         var2 = this.docBKGround;
      }

      return var2;
   }

   public BKGroundConfig getBkGroundConfig() {
      return this.a;
   }

   public void setBkGroundConfig(BKGroundConfig config) {
      this.a = config;
   }

   public void merge(NoteBackground background) {
      if (background != null) {
         if (background.isUseDocBKGround()) {
            this.setDocBKGround(background.docBKGround);
            this.setUseDocBKGround(background.isUseDocBKGround());
         }

         this.pageBKGroundMap.putAll(background.pageBKGroundMap);
      }
   }

   public void mergePageBKGround(NoteBackground background) {
      if (background != null && !CollectionUtils.isNullOrEmpty(background.pageBKGroundMap)) {
         this.pageBKGroundMap.putAll(background.pageBKGroundMap);
      }
   }

   public void mergeDocumentBKGround(List<String> pageList, BkGroundRes bkGroundRes) {
      if (!CollectionUtils.isNullOrEmpty(pageList)) {
         for (String var3 : pageList) {
            this.pageBKGroundMap.put(var3, bkGroundRes);
         }
      }
   }
}
