package com.onyx.android.sdk.data.exception;

public class AbortException extends RuntimeException {
   private static final long serialVersionUID = 8052253155370901057L;

   public AbortException() {
   }

   public AbortException(String message) {
      super(message);
   }
}
