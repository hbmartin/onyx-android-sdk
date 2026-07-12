package com.onyx.android.sdk.common;

import androidx.annotation.NonNull;
import com.onyx.android.sdk.common.base.BaseSearchHistoryHelper;

public class DefaultSearchHistory extends BaseSearchHistoryHelper {
   public static DefaultSearchHistory create(MMKVBuilder mmkvBuilder) {
      return new DefaultSearchHistory(mmkvBuilder);
   }

   private DefaultSearchHistory(@NonNull MMKVBuilder mmkvBuilder) {
      super(mmkvBuilder);
   }
}
