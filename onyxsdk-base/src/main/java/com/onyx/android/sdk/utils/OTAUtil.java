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
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:174)
      //
      // Bytecode:
      // 00: aconst_null
      // 01: astore 1
      // 02: aconst_null
      // 03: astore 2
      // 04: aconst_null
      // 05: astore 3
      // 06: new java/io/FileInputStream
      // 09: dup
      // 0a: astore 4
      // 0c: aload 0
      // 0d: invokespecial java/io/FileInputStream.<init> (Ljava/lang/String;)V
      // 10: new java/util/zip/ZipInputStream
      // 13: dup
      // 14: astore 0
      // 15: new java/io/BufferedInputStream
      // 18: dup
      // 19: aload 4
      // 1b: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;)V
      // 1e: invokespecial java/util/zip/ZipInputStream.<init> (Ljava/io/InputStream;)V
      // 21: aload 0
      // 22: invokevirtual java/util/zip/ZipInputStream.getNextEntry ()Ljava/util/zip/ZipEntry;
      // 25: dup
      // 26: astore 1
      // 27: ifnull 98
      // 2a: aload 1
      // 2b: invokevirtual java/util/zip/ZipEntry.getName ()Ljava/lang/String;
      // 2e: ldc "android-info.txt"
      // 30: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 33: ifeq 21
      // 36: new java/io/ByteArrayOutputStream
      // 39: dup
      // 3a: astore 1
      // 3b: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 3e: sipush 1024
      // 41: newarray 8
      // 43: astore 2
      // 44: aload 0
      // 45: aload 2
      // 46: invokevirtual java/util/zip/ZipInputStream.read ([B)I
      // 49: dup
      // 4a: istore 3
      // 4b: bipush -1
      // 4c: if_icmpeq 59
      // 4f: aload 1
      // 50: aload 2
      // 51: bipush 0
      // 52: iload 3
      // 53: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
      // 56: goto 44
      // 59: aload 1
      // 5a: invokevirtual java/io/ByteArrayOutputStream.toString ()Ljava/lang/String;
      // 5d: ldc "="
      // 5f: invokevirtual java/lang/String.split (Ljava/lang/String;)[Ljava/lang/String;
      // 62: dup
      // 63: astore 2
      // 64: arraylength
      // 65: bipush 2
      // 66: if_icmplt 82
      // 69: aload 2
      // 6a: bipush 1
      // 6b: aaload
      // 6c: ifnull 82
      // 6f: aload 2
      // 70: bipush 1
      // 71: aaload
      // 72: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 75: getstatic android/os/Build.MODEL Ljava/lang/String;
      // 78: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 7b: ifeq 82
      // 7e: bipush 1
      // 7f: goto 83
      // 82: bipush 0
      // 83: aload 0
      // 84: aload 4
      // 86: aload 1
      // 87: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // 8a: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // 8d: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // 90: ireturn
      // 91: goto c9
      // 94: pop
      // 95: goto df
      // 98: aload 0
      // 99: aload 4
      // 9b: aload 3
      // 9c: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // 9f: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // a2: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // a5: goto ec
      // a8: aload 3
      // a9: astore 1
      // aa: goto c9
      // ad: pop
      // ae: aload 3
      // af: astore 1
      // b0: goto df
      // b3: aload 3
      // b4: aload 2
      // b5: astore 0
      // b6: astore 1
      // b7: goto c9
      // ba: pop
      // bb: aload 3
      // bc: aload 2
      // bd: astore 0
      // be: astore 1
      // bf: goto df
      // c2: aload 3
      // c3: aload 1
      // c4: aload 2
      // c5: astore 0
      // c6: astore 4
      // c8: astore 1
      // c9: aload 0
      // ca: aload 4
      // cc: aload 1
      // cd: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // d0: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // d3: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // d6: athrow
      // d7: pop
      // d8: aload 3
      // d9: aload 1
      // da: aload 2
      // db: astore 0
      // dc: astore 4
      // de: astore 1
      // df: aload 0
      // e0: aload 4
      // e2: aload 1
      // e3: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // e6: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // e9: invokestatic com/onyx/android/sdk/utils/FileUtils.closeQuietly (Ljava/io/Closeable;)V
      // ec: bipush 0
      // ed: ireturn
      // try (6 -> 7): 120 java/io/IOException
      // try (6 -> 7): 107 null
      // try (9 -> 11): 120 java/io/IOException
      // try (9 -> 11): 107 null
      // try (11 -> 12): 101 java/io/IOException
      // try (11 -> 12): 96 null
      // try (14 -> 19): 101 java/io/IOException
      // try (14 -> 19): 96 null
      // try (19 -> 21): 92 java/io/IOException
      // try (19 -> 21): 89 null
      // try (24 -> 28): 92 java/io/IOException
      // try (24 -> 28): 89 null
      // try (29 -> 30): 92 java/io/IOException
      // try (29 -> 30): 89 null
      // try (32 -> 33): 92 java/io/IOException
      // try (32 -> 33): 89 null
      // try (33 -> 35): 80 java/io/IOException
      // try (33 -> 35): 79 null
      // try (36 -> 39): 80 java/io/IOException
      // try (36 -> 39): 79 null
      // try (43 -> 53): 80 java/io/IOException
      // try (43 -> 53): 79 null
      // try (55 -> 56): 80 java/io/IOException
      // try (55 -> 56): 79 null
      // try (58 -> 61): 80 java/io/IOException
      // try (58 -> 61): 79 null
      // try (62 -> 68): 80 java/io/IOException
      // try (62 -> 68): 79 null
   }
}
