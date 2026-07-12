package com.onyx.android.sdk.data.richtext.v2;

import com.onyx.android.sdk.data.richtext.RichTextResource;
import com.onyx.android.sdk.data.richtext.RichTextResourceLoader;
import com.onyx.android.sdk.extension.Files;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.FileUtils;
import java.io.File;

public class RichTextResourceLoaderV2 implements RichTextResourceLoader {
   @Override
   public void parse(RichTextResource resource) throws Exception {
      if (!FileUtils.fileExist(resource.getFilePath())) {
         Debug.e(this.getClass(), "rich text resource file not exist: " + resource.getFilePath(), new Object[0]);
      } else {
         resource.setContent(FileUtils.readContentFromFile(resource.getFilePath()));
      }
   }

   @Override
   public boolean save(RichTextResource resource) throws Exception {
      return Files.INSTANCE.saveContentWithDiskSync(resource.getContent(), new File(resource.getFilePath()));
   }
}
