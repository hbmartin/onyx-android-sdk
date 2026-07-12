package com.onyx.android.sdk.utils;

import android.net.Uri;
import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.data.sync.ResourcePathUtils;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SharePathDecoder {
   public static final String TEMP_DIR_NAME = "temp_share";

   @WorkerThread
   public static String decodePath(Uri uri) {
      if (uri != null && !StringUtils.isNullOrEmpty(uri.toString())) {
         String var1;
         return StringUtils.isNullOrEmpty(var1 = a(uri)) ? null : a(uri, var1);
      } else {
         return null;
      }
   }

   private static String a(Uri srcUri, String targetFileName) {
      return a(srcUri, targetFileName, false);
   }

   private static String a(Uri srcUri, String targetFileName, boolean decodeUriText) {
      String targetPath = ResourcePathUtils.getResourceDataDirPath(TEMP_DIR_NAME)
              .concat(targetFileName);
      try (FileOutputStream output = new FileOutputStream(targetPath);
           InputStream input = decodeUriText
                   ? new ByteArrayInputStream(srcUri.toString().getBytes(StandardCharsets.UTF_8))
                   : ResManager.getAppContext().getContentResolver().openInputStream(srcUri)) {
         return input != null && FileUtils.copy(input, output, null) > 0L ? targetPath : null;
      } catch (Exception exception) {
         exception.printStackTrace();
         return null;
      }
   }

   private static String a(Uri uri) {
      int var1;
      return (var1 = uri.toString().lastIndexOf("/")) <= 0 ? null : uri.toString().substring(var1);
   }
}
