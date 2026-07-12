package com.onyx.android.sdk.common.event;

public class SystemUIChangedEvent {
   public String type;
   public boolean open;

   public SystemUIChangedEvent(String type, boolean open) {
      this.type = type;
      this.open = open;
   }
}
