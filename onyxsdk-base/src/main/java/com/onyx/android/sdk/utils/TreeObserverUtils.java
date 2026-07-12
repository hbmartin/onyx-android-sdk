package com.onyx.android.sdk.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class TreeObserverUtils {
   public static void removeLayoutListenerPre16(ViewTreeObserver observer, OnGlobalLayoutListener listener) {
      observer.removeGlobalOnLayoutListener(listener);
   }

   @TargetApi(16)
   public static void removeLayoutListenerPost16(ViewTreeObserver observer, OnGlobalLayoutListener listener) {
      observer.removeOnGlobalLayoutListener(listener);
   }

   public static void removeGlobalOnLayoutListener(ViewTreeObserver observer, OnGlobalLayoutListener listener) {
      if (VERSION.SDK_INT < 16) {
         removeLayoutListenerPre16(observer, listener);
      } else {
         removeLayoutListenerPost16(observer, listener);
      }
   }
}
