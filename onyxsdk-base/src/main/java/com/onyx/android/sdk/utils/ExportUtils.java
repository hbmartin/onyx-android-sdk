package com.onyx.android.sdk.utils;

import android.os.Environment;
import android.os.Build.VERSION;
import com.onyx.android.sdk.device.EnvironmentUtil;
import com.onyx.android.sdk.path.CommonPaths;
import com.onyx.android.sdk.path.NoteCommonPaths;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ExportUtils {
   public static final String NOTE = "note";

   private static String a(String documentPath) throws IOException {
      documentPath = FileUtils.getParent(documentPath);
      if (FileUtils.mkdirs(documentPath = documentPath + "/" + FileUtils.getBaseName(documentPath).trim())) {
         return documentPath;
      } else {
         throw new IOException(documentPath);
      }
   }

   public static String getExportPdfPath(String documentPath) throws IOException {
      String var1 = FileUtils.getBaseName(documentPath);
      String var2 = FileUtils.getFileExtension(documentPath);
      return new File(a(documentPath), var1 + "-Exported." + var2).getAbsolutePath();
   }

   public static String getExportAnnotationPath(String documentPath) throws IOException {
      String var1 = FileUtils.getBaseName(documentPath);
      return new File(a(documentPath), var1 + "-annotation.txt").getAbsolutePath();
   }

   public static String getExportScribblePath(String documentPath, String page) throws IOException {
      String var2 = FileUtils.getBaseName(documentPath);
      return new File(a(documentPath), var2 + "-scribble-" + page + ".png").getAbsolutePath();
   }

   public static String getExportNotePath(String document, String page) throws IOException {
      document = NoteCommonPaths.INSTANCE.getNoteExportPath() + document;
      return new File(a(document), page + ".png").getAbsolutePath();
   }

   public static String getExportPicPath(String document) {
      String var1 = a();
      return new File(var1, document + ".png").getAbsolutePath();
   }

   public static String getExportNameWithTime(String name) {
      return name + "_" + DateTimeUtil.getDateFormatYYYYMMDD_HH_MM_SS().format(new Date());
   }

   private static String a() {
      return VERSION.SDK_INT < 23
         ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Screenshots"
         : EnvironmentUtil.getExternalStorageDirectory().getPath() + File.separator + "Screenshots";
   }

   public static String getNoteDir() {
      return CommonPaths.INSTANCE.getExternalStorageDir() + "note";
   }
}
