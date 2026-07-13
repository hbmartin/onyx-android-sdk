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
