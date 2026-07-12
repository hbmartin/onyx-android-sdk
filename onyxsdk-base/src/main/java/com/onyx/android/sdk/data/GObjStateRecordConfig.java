package com.onyx.android.sdk.data;

import androidx.core.util.Consumer;

public class GObjStateRecordConfig {
   private boolean a = false;
   private final String b;

   public GObjStateRecordConfig(String key, boolean isSupportRecordLocal) {
      this.b = key;
      this.a = isSupportRecordLocal;
   }

   public String getKey() {
      return this.b;
   }

   public boolean isSupportRecordLocal() {
      return this.a;
   }

   public void shouldRecordState(Consumer<String> consumer) {
      if (consumer != null && this.isSupportRecordLocal()) {
         consumer.accept(this.getKey());
      }
   }
}
