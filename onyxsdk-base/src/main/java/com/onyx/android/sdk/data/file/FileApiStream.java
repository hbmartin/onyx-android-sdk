package com.onyx.android.sdk.data.file;

import com.onyx.android.sdk.utils.FileUtils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileApiStream implements BaseStream {
   @Override
   public FileOutputStream getOutputStream(String filePath, boolean clearFile) throws FileNotFoundException {
      if (clearFile) {
         FileUtils.deleteFile(filePath);
      }

      return new FileOutputStream(filePath);
   }
}
