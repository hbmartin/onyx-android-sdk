package com.onyx.android.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import java.io.File;

public class CommonPdfUtils {
   public static final float PDF_STANDARD_DPI = 72.0F;
   public static final float MM_TO_IN_VALUE = 25.4F;

   public static Bitmap pdfPageBitmap(String param0, int param1, int param2, int param3, @NonNull Config param4) {
      try (ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(
              new File(param0), ParcelFileDescriptor.MODE_READ_ONLY);
           PdfRenderer renderer = new PdfRenderer(descriptor);
           PdfRenderer.Page page = renderer.openPage(param1)) {
         Bitmap bitmap = Bitmap.createBitmap(param2, param3, param4);
         page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
         return bitmap;
      } catch (Exception exception) {
         exception.printStackTrace();
         return null;
      }
   }

   public static int pdfPageCount(String param0) {
      try (ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(
              new File(param0), ParcelFileDescriptor.MODE_READ_ONLY);
           PdfRenderer renderer = new PdfRenderer(descriptor)) {
         return renderer.getPageCount();
      } catch (Exception exception) {
         exception.printStackTrace();
         return 0;
      }
   }

   public static int pdfMMToPx(int mm) {
      return (int)Math.ceil(mm / 25.4F * 72.0F);
   }

   public static int pdfPxToMM(int px) {
      return (int)Math.ceil(px * 25.4F / 72.0F);
   }

   public static int pdfCMToPx(int cm) {
      return pdfMMToPx(cm / 10);
   }
}
