package com.onyx.android.sdk.utils;

public class OTAUtil {
   private static final String a = "android-info.txt";

   public static boolean checkLocalUpdateZipLegality(String param0) {
      try (java.io.FileInputStream file = new java.io.FileInputStream(param0);
           java.util.zip.ZipInputStream zip = new java.util.zip.ZipInputStream(
                   new java.io.BufferedInputStream(file))) {
         java.util.zip.ZipEntry entry;
         while ((entry = zip.getNextEntry()) != null) {
            if (!a.equals(entry.getName())) continue;
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = zip.read(buffer)) != -1) output.write(buffer, 0, read);
            String[] parts = output.toString().split("=");
            return parts.length >= 2 && parts[1] != null
                    && parts[1].trim().equals(android.os.Build.MODEL);
         }
         return false;
      } catch (java.io.IOException exception) {
         exception.printStackTrace();
         return false;
      }
   }
}
