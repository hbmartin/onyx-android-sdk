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
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:174)
      //
      // Bytecode:
      // 00: aconst_null
      // 01: astore 5
      // 03: new java/io/File
      // 06: dup
      // 07: aload 0
      // 08: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 0b: ldc 268435456
      // 0d: invokestatic android/os/ParcelFileDescriptor.open (Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
      // 10: astore 0
      // 11: new android/graphics/pdf/PdfRenderer
      // 14: dup
      // 15: astore 6
      // 17: iload 1
      // 18: aload 6
      // 1a: aload 0
      // 1b: invokespecial android/graphics/pdf/PdfRenderer.<init> (Landroid/os/ParcelFileDescriptor;)V
      // 1e: invokevirtual android/graphics/pdf/PdfRenderer.openPage (I)Landroid/graphics/pdf/PdfRenderer$Page;
      // 21: dup
      // 22: dup
      // 23: astore 1
      // 24: iload 2
      // 25: iload 3
      // 26: aload 4
      // 28: invokestatic android/graphics/Bitmap.createBitmap (IILandroid/graphics/Bitmap.Config;)Landroid/graphics/Bitmap;
      // 2b: dup
      // 2c: astore 5
      // 2e: aconst_null
      // 2f: aconst_null
      // 30: bipush 1
      // 31: invokevirtual android/graphics/pdf/PdfRenderer$Page.render (Landroid/graphics/Bitmap;Landroid/graphics/Rect;Landroid/graphics/Matrix;I)V
      // 34: ifnull 3b
      // 37: aload 1
      // 38: invokevirtual android/graphics/pdf/PdfRenderer$Page.close ()V
      // 3b: aload 0
      // 3c: aload 6
      // 3e: invokevirtual android/graphics/pdf/PdfRenderer.close ()V
      // 41: ifnull 8d
      // 44: aload 0
      // 45: invokevirtual android/os/ParcelFileDescriptor.close ()V
      // 48: goto 8d
      // 4b: astore 2
      // 4c: aload 2
      // 4d: athrow
      // 4e: astore 3
      // 4f: aload 1
      // 50: ifnull 5f
      // 53: aload 1
      // 54: invokevirtual android/graphics/pdf/PdfRenderer$Page.close ()V
      // 57: goto 5f
      // 5a: aload 2
      // 5b: swap
      // 5c: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 5f: aload 3
      // 60: athrow
      // 61: astore 1
      // 62: aload 1
      // 63: athrow
      // 64: astore 2
      // 65: aload 6
      // 67: invokevirtual android/graphics/pdf/PdfRenderer.close ()V
      // 6a: goto 72
      // 6d: aload 1
      // 6e: swap
      // 6f: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 72: aload 2
      // 73: athrow
      // 74: astore 1
      // 75: aload 1
      // 76: athrow
      // 77: astore 2
      // 78: aload 0
      // 79: ifnull 88
      // 7c: aload 0
      // 7d: invokevirtual android/os/ParcelFileDescriptor.close ()V
      // 80: goto 88
      // 83: aload 1
      // 84: swap
      // 85: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 88: aload 2
      // 89: athrow
      // 8a: invokevirtual java/lang/Exception.printStackTrace ()V
      // 8d: aload 5
      // 8f: areturn
      // try (2 -> 8): 80 java/lang/Exception
      // try (9 -> 10): 66 null
      // try (12 -> 16): 66 null
      // try (16 -> 17): 54 null
      // try (20 -> 24): 40 null
      // try (26 -> 30): 40 null
      // try (31 -> 33): 54 null
      // try (33 -> 36): 66 null
      // try (37 -> 40): 80 java/lang/Exception
      // try (41 -> 43): 43 null
      // try (46 -> 49): 49 null
      // try (49 -> 54): 54 null
      // try (55 -> 57): 57 null
      // try (58 -> 61): 61 null
      // try (61 -> 66): 66 null
      // try (67 -> 69): 69 null
      // try (72 -> 75): 75 null
      // try (75 -> 80): 80 java/lang/Exception
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
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:174)
      //
      // Bytecode:
      // 00: bipush 0
      // 01: istore 1
      // 02: new java/io/File
      // 05: dup
      // 06: aload 0
      // 07: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 0a: ldc 268435456
      // 0c: invokestatic android/os/ParcelFileDescriptor.open (Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
      // 0f: dup
      // 10: astore 0
      // 11: new android/graphics/pdf/PdfRenderer
      // 14: dup
      // 15: dup2
      // 16: astore 2
      // 17: aload 0
      // 18: invokespecial android/graphics/pdf/PdfRenderer.<init> (Landroid/os/ParcelFileDescriptor;)V
      // 1b: invokevirtual android/graphics/pdf/PdfRenderer.getPageCount ()I
      // 1e: istore 1
      // 1f: invokevirtual android/graphics/pdf/PdfRenderer.close ()V
      // 22: ifnull 59
      // 25: aload 0
      // 26: invokevirtual android/os/ParcelFileDescriptor.close ()V
      // 29: goto 59
      // 2c: dup
      // 2d: astore 3
      // 2e: athrow
      // 2f: astore 4
      // 31: aload 2
      // 32: invokevirtual android/graphics/pdf/PdfRenderer.close ()V
      // 35: goto 3d
      // 38: aload 3
      // 39: swap
      // 3a: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 3d: aload 4
      // 3f: athrow
      // 40: astore 2
      // 41: aload 2
      // 42: athrow
      // 43: astore 3
      // 44: aload 0
      // 45: ifnull 54
      // 48: aload 0
      // 49: invokevirtual android/os/ParcelFileDescriptor.close ()V
      // 4c: goto 54
      // 4f: aload 2
      // 50: swap
      // 51: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 54: aload 3
      // 55: athrow
      // 56: invokevirtual java/lang/Exception.printStackTrace ()V
      // 59: iload 1
      // 5a: ireturn
      // try (2 -> 8): 49 java/lang/Exception
      // try (10 -> 11): 35 null
      // try (14 -> 16): 35 null
      // try (16 -> 17): 23 null
      // try (18 -> 19): 35 null
      // try (20 -> 23): 49 java/lang/Exception
      // try (25 -> 26): 26 null
      // try (27 -> 30): 30 null
      // try (30 -> 35): 35 null
      // try (36 -> 38): 38 null
      // try (41 -> 44): 44 null
      // try (44 -> 49): 49 java/lang/Exception
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
