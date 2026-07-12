package com.onyx.android.sdk.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import com.onyx.android.sdk.common.provider.OnyxFileProviderUtil;
import java.io.File;

public class UriUtils {
   private static final String a = "primary";

   public static String getRealFilePath(Context context, Uri uri) {
      if (uri == null) {
         return null;
      }

      String var2;
      String var10000 = var2 = uri.getScheme();
      String var3 = null;
      if (var10000 == null) {
         var3 = uri.getPath();
      } else if ("file".equals(var2)) {
         var3 = uri.getPath();
      } else if ("content".equals(var2)) {
         ContentResolver var7 = context.getContentResolver();
         String[] var5;
         (var5 = new String[1])[0] = "_data";
         Cursor var6;
         if ((var6 = var7.query(uri, var5, null, null, null)) != null) {
            int var4;
            if (var6.moveToFirst() && (var4 = var6.getColumnIndex("_data")) > -1) {
               var3 = var6.getString(var4);
            }

            var6.close();
         }

         if (var3 == null) {
            var3 = getImageAbsolutePath(context, uri);
         }
      }

      return var3;
   }

   public static Uri getUri(String filePath) {
      return Uri.fromFile(new File(filePath));
   }

   @TargetApi(19)
   public static String getImageAbsolutePath(Context context, Uri imageUri) {
      if (context != null && imageUri != null) {
         if (VERSION.SDK_INT < 19 || !DocumentsContract.isDocumentUri(context, imageUri)) {
            if ("content".equalsIgnoreCase(imageUri.getScheme())) {
               if (isGooglePhotosUri(imageUri)) {
                  return imageUri.getLastPathSegment();
               }

               return getDataColumn(context, imageUri, null, null);
            }

            if ("file".equalsIgnoreCase(imageUri.getScheme())) {
               return imageUri.getPath();
            }
         } else if (isExternalStorageDocument(imageUri)) {
            String[] var4;
            if ("primary".equalsIgnoreCase((var4 = DocumentsContract.getDocumentId(imageUri).split(":"))[0])) {
               return Environment.getExternalStorageDirectory() + "/" + var4[1];
            }
         } else {
            if (isDownloadsDocument(imageUri)) {
               return getDataColumn(
                  context,
                  ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(imageUri))),
                  null,
                  null
               );
            }

            if (isMediaDocument(imageUri)) {
               String[] var6;
               String var2 = (var6 = DocumentsContract.getDocumentId(imageUri).split(":"))[0];
               Uri var3 = null;
               if ("image".equals(var2)) {
                  var3 = Media.EXTERNAL_CONTENT_URI;
               } else if ("video".equals(var2)) {
                  var3 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
               } else if ("audio".equals(var2)) {
                  var3 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
               }

               String[] var5;
               (var5 = new String[1])[0] = var6[1];
               return getDataColumn(context, var3, "_id=?", var5);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static String getFileNameFromUri(Context context, Uri uri) {
      String var2;
      if (StringUtils.isNullOrEmpty(var2 = FileUtils.getDisplayNameFromUri(context, uri))) {
         String var10000;
         try {
            var10000 = FileUtils.getFileName(StringUtils.safelyGetStr(getImageAbsolutePath(context, uri)));
         } catch (Exception var3) {
            var3.printStackTrace();
            return var2;
         }

         var2 = var10000;
      }

      return var2;
   }

   public static String getDataColumn(Context param0, Uri param1, String param2, String[] param3) {
      try (Cursor cursor = param0.getContentResolver().query(
              param1, new String[]{"_data"}, param2, param3, null)) {
         if (cursor != null && cursor.moveToFirst()) {
            int column = cursor.getColumnIndexOrThrow("_data");
            return cursor.getString(column);
         }
         return null;
      }
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:361)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:504)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1058)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:351)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:98)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:185)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aconst_null
      // 02: astore 0
      // 03: ldc "_data"
      // 05: astore 4
      // 07: bipush 1
      // 08: anewarray 59
      // 0b: dup
      // 0c: astore 5
      // 0e: bipush 0
      // 0f: aload 4
      // 11: aastore
      // 12: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 15: aload 1
      // 16: aload 5
      // 18: aload 2
      // 19: aload 3
      // 1a: aconst_null
      // 1b: invokevirtual android/content/ContentResolver.query (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      // 1e: dup
      // 1f: astore 0
      // 20: ifnull 47
      // 23: aload 0
      // 24: invokeinterface android/database/Cursor.moveToFirst ()Z 1
      // 29: ifeq 47
      // 2c: aload 0
      // 2d: dup
      // 2e: dup
      // 2f: aload 4
      // 31: invokeinterface android/database/Cursor.getColumnIndexOrThrow (Ljava/lang/String;)I 2
      // 36: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 3b: astore 1
      // 3c: ifnull 45
      // 3f: aload 0
      // 40: invokeinterface android/database/Cursor.close ()V 1
      // 45: aload 1
      // 46: areturn
      // 47: aload 0
      // 48: ifnull 51
      // 4b: aload 0
      // 4c: invokeinterface android/database/Cursor.close ()V 1
      // 51: aconst_null
      // 52: areturn
      // 53: astore 1
      // 54: aload 0
      // 55: ifnull 5e
      // 58: aload 0
      // 59: invokeinterface android/database/Cursor.close ()V 1
      // 5e: aload 1
      // 5f: athrow
      // try (12 -> 19): 43 null
      // try (22 -> 24): 43 null
      // try (25 -> 31): 43 null
   }

   public static boolean isExternalStorageDocument(Uri uri) {
      return "com.android.externalstorage.documents".equals(uri.getAuthority());
   }

   public static boolean isDownloadsDocument(Uri uri) {
      return "com.android.providers.downloads.documents".equals(uri.getAuthority());
   }

   public static boolean isMediaDocument(Uri uri) {
      return "com.android.providers.media.documents".equals(uri.getAuthority());
   }

   public static boolean isGooglePhotosUri(Uri uri) {
      return "com.google.android.apps.photos.content".equals(uri.getAuthority());
   }

   public static boolean isWebsiteUri(String uri) {
      return StringUtils.startsWithIgnoreCase(uri, "http://") || StringUtils.startsWithIgnoreCase(uri, "https://");
   }

   public static boolean isFileUri(String uri) {
      return StringUtils.startsWithIgnoreCase(uri, "file:///");
   }

   public static long length(Context context, Uri uri) {
      String var2;
      String var10000 = var2 = uri.getScheme();
      var10000.hashCode();
      if (!var10000.equals("file")) {
         if (!var2.equals("content")) {
            return 0L;
         }

         try {
            return context.getContentResolver().openFileDescriptor(uri, "r").getStatSize();
         } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
         }
      } else {
         return new File(uri.getPath()).length();
      }
   }

   public static Uri getTakePhotoTempOutputUri(Context context) {
      return OnyxFileProviderUtil.getUriForFile(context, new File(context.getExternalCacheDir(), "take_photo_temp.jpg"));
   }
}
