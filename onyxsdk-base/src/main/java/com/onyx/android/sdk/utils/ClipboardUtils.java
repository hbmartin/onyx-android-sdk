package com.onyx.android.sdk.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ClipData.Item;
import android.net.Uri;
import android.os.Looper;
import android.os.Build.VERSION;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.common.provider.OnyxFileProviderUtil;
import java.io.File;
import java.util.Optional;

public class ClipboardUtils {
   @MainThread
   public static void init(@NonNull Context context) {
      if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
         context.getApplicationContext().getSystemService("clipboard");
      } else {
         throw new IllegalArgumentException("ClipboardManager must in main thread");
      }
   }

   public static boolean copyToClipboard(@NonNull Context context, String text) {
      ClipboardManager var2;
      if ((var2 = (ClipboardManager)context.getApplicationContext().getSystemService("clipboard")) != null) {
         var2.setPrimaryClip(ClipData.newPlainText(null, text));
         return true;
      } else {
         return false;
      }
   }

   public static boolean copyImgToClipboard(@NonNull Context context, File imgFile) {
      ClipboardManager var2;
      ClipboardManager var10000 = var2 = (ClipboardManager)context.getApplicationContext().getSystemService("clipboard");
      ContentResolver var3 = context.getContentResolver();
      if (var10000 != null && var3 != null) {
         Uri var4 = OnyxFileProviderUtil.getUriForFile(context, imgFile);
         var2.setPrimaryClip(ClipData.newUri(var3, FileUtils.getBaseName(imgFile), var4));
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public static String getClipPrimaryContent(@NonNull Context context) {
      LooperUtils.looperPrepare();
      return getClipPrimaryContent((ClipboardManager)context.getApplicationContext().getSystemService("clipboard"));
   }

   public static Boolean hasClipPrimaryContent(@NonNull Context context) {
      return StringUtils.isNotBlank(getClipPrimaryContent(context));
   }

   public static long getClipTimestamp(@NonNull Context context) {
      ClipData var1;
      if ((var1 = getClipPrimaryData((ClipboardManager)context.getApplicationContext().getSystemService("clipboard"))) == null) {
         return 0L;
      } else {
         return VERSION.SDK_INT >= 26 ? var1.getDescription().getTimestamp() : 0L;
      }
   }

   @Nullable
   public static String getClipPrimaryRichContent(@NonNull Context context) {
      LooperUtils.looperPrepare();
      return getClipPrimaryRichContent((ClipboardManager)context.getApplicationContext().getSystemService("clipboard"));
   }

   @Nullable
   public static String getClipPrimaryContent(ClipboardManager manager) {
      ClipData var1;
      if ((var1 = getClipPrimaryData(manager)) == null) {
         return null;
      }

      Item var2;
      return (var2 = var1.getItemAt(0)) != null && var2.getText() != null ? var2.getText().toString() : null;
   }

   @Nullable
   public static String getClipPrimaryRichContent(ClipboardManager manager) {
      ClipData var2;
      if ((var2 = getClipPrimaryData(manager)) == null) {
         return null;
      }

      Item var3;
      if ((var3 = var2.getItemAt(0)) != null && var3.getText() != null) {
         String var1;
         if (StringUtils.isBlank(var1 = var3.getHtmlText())) {
            var1 = var3.getText().toString();
         }

         return var1;
      } else {
         return null;
      }
   }

   @Nullable
   public static ClipData getClipPrimaryData(@NonNull Context context) {
      return getClipPrimaryData((ClipboardManager)context.getApplicationContext().getSystemService("clipboard"));
   }

   @Nullable
   public static ClipData getClipPrimaryData(@Nullable ClipboardManager manager) {
      if (manager == null) {
         return null;
      }

      ClipData var1;
      return (var1 = manager.getPrimaryClip()) != null && var1.getItemCount() > 0 ? var1 : null;
   }

   public static void clearClipboard(@NonNull Context context) {
      Optional.ofNullable((ClipboardManager)context.getApplicationContext().getSystemService("clipboard")).ifPresent(clipboardManager -> {
         if (VERSION.SDK_INT >= 28) {
            clipboardManager.clearPrimaryClip();
         } else {
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, null));
         }
      });
   }
}
