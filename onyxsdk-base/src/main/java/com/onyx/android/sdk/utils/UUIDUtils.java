package com.onyx.android.sdk.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class UUIDUtils {
   private static final TimeBasedEpochGenerator TIME_BASED_EPOCH_GENERATOR = new TimeBasedEpochGenerator();

   public static String randomUUID() {
      return UUID.randomUUID().toString().replaceAll("-", "");
   }

   public static String randomRawUUID() {
      return UUID.randomUUID().toString();
   }

   public static String timeBasedEpochUUID() {
      return TIME_BASED_EPOCH_GENERATOR.generate().toString();
   }

   public static int compareTo(String timeUUID1, String timeUUID2) {
      return UUID.fromString(timeUUID1).compareTo(UUID.fromString(timeUUID2));
   }

   public static int safelyCompareTo(String timeUUID1, String timeUUID2) {
      try {
         return compareTo(timeUUID1, timeUUID2);
      } catch (Exception var2) {
         var2.printStackTrace();
         return 0;
      }
   }

   /** Local UUIDv7 generator adapted from the supplied JUG reference implementation. */
   private static final class TimeBasedEpochGenerator {
      private static final int ENTROPY_BYTE_LENGTH = 10;
      private static final long RIGHT_62_MASK = (1L << 62) - 1;
      private final SecureRandom random = new SecureRandom();
      private final byte[] lastEntropy = new byte[ENTROPY_BYTE_LENGTH];
      private long lastTimestamp = -1L;

      UUID generate() {
         return construct(System.currentTimeMillis());
      }

      private UUID construct(long rawTimestamp) {
         final long mostSigBits;
         final long leastSigBits;
         synchronized (lastEntropy) {
            if (rawTimestamp == lastTimestamp) {
               incrementEntropy();
            } else {
               lastTimestamp = rawTimestamp;
               random.nextBytes(lastEntropy);
               lastEntropy[0] &= 1;
            }
            mostSigBits = (rawTimestamp << 16)
               | (7L << 12)
               | (lastEntropy[0] & 255L) << 10
               | (lastEntropy[1] & 255L) << 2
               | (lastEntropy[2] & 255L) >>> 6;
            leastSigBits = (2L << 62) | (toLong(lastEntropy, 2) & RIGHT_62_MASK);
         }
         return new UUID(mostSigBits, leastSigBits);
      }

      private void incrementEntropy() {
         for (int i = ENTROPY_BYTE_LENGTH - 1; i > 0; i--) {
            lastEntropy[i] = (byte)(lastEntropy[i] + 1);
            if (lastEntropy[i] != 0) {
               return;
            }
         }
         lastEntropy[0] = (byte)(lastEntropy[0] + 1);
         if (lastEntropy[0] >= 4) {
            throw new IllegalStateException("overflow on same millisecond");
         }
      }

      private static long toLong(byte[] bytes, int offset) {
         long value = 0L;
         for (int i = offset; i < offset + 8; i++) {
            value = (value << 8) | (bytes[i] & 255L);
         }
         return value;
      }
   }
}
