package com.onyx.android.sdk.data.note;

import androidx.annotation.NonNull;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.JSONUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

public class ThumbnailRes implements Serializable {
   public static final int TYPE_PAGE = 0;
   public static final int TYPE_COVER = 1;
   public int type = 0;
   public String value;

   public static ThumbnailRes create(int type, String value) {
      ThumbnailRes var10000 = new ThumbnailRes();
      var10000.type = type;
      var10000.value = value;
      return var10000;
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isPageThumbnail() {
      return this.type == 0 && StringUtils.isNotBlank(this.value);
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isCoverThumbnail() {
      return this.type == 1 && StringUtils.isNotBlank(this.value);
   }

   @NonNull
   @Override
   public String toString() {
      return JSONUtils.toJson(this);
   }
}
