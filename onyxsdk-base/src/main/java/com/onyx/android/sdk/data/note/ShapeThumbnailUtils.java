package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.path.NoteCommonPaths;
import java.io.File;

public class ShapeThumbnailUtils {
   public static final String KSYNC_SHAPE_THUMBNAIL_FILE_EXTENSION = ".png";

   public static String getShapeThumbnailPath(String documentId, String shapeId) {
      StringBuilder var10000 = new StringBuilder().append(NoteCommonPaths.INSTANCE.getShapeThumbnailDir());
      String var2 = File.separator;
      return var10000.append(File.separator).append(documentId).append(var2).append(shapeId).append(".png").toString();
   }
}
