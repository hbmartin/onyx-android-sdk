package com.onyx.android.sdk.data;

public class EncryptionType {
   public static final int NONE = 0;
   public static final int FINGERPRINT = 1;
   public static final int KEYGUARD = 2;
   public static final int PRIVATE_PASSWORD = 3;
   public static final int GLOBAL_PASSWORD = 4;
   public static final int KEYGUARD_FINGERPRINT = 5;

   public static boolean isEncrypted(int type) {
      return type != 0;
   }

   public static boolean isPrivatePasswordType(int type) {
      return type == 3;
   }

   public static boolean isGlobalPasswordType(int type) {
      return type == 4;
   }
}
