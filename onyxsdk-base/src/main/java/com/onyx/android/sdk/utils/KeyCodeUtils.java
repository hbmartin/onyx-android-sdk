package com.onyx.android.sdk.utils;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class KeyCodeUtils {
   public static boolean isDigitalKey(int keyCode) {
      return keyCode >= 7 && keyCode <= 16;
   }

   public static boolean isConfirmKey(int keyCode) {
      return keyCode == 23 || keyCode == 62 || keyCode == 66 || keyCode == 160;
   }

   public static boolean isBackKey(int keyCode) {
      return keyCode == 4;
   }

   public static boolean isEscapeKey(int keyCode) {
      return keyCode == 111;
   }

   public static boolean isSpaceKey(KeyEvent keyEvent) {
      return keyEvent.getKeyCode() == 62;
   }

   public static boolean isDpadDirectionKey(int keyCode) {
      switch (keyCode) {
         default:
            switch (keyCode) {
               case 268:
               case 269:
               case 270:
               case 271:
                  break;
               default:
                  return false;
            }
         case 19:
         case 20:
         case 21:
         case 22:
            return true;
      }
   }

   public static boolean isKeyDownAction(KeyEvent event) {
      return event.getAction() == 0;
   }

   public static boolean isKeyUpAction(KeyEvent event) {
      return event.getAction() == 1;
   }

   public static boolean isSendByKeyboard(KeyEvent event) {
      return ResManager.getResources().getConfiguration().keyboard != 1 && event != null && event.getDevice() != null && event.getDevice().supportsSource(257);
   }

   public static boolean isHardVolumeKey(KeyEvent event) {
      return event != null && (event.getKeyCode() == 25 || event.getKeyCode() == 24) && isSendByKeyboard(event);
   }

   public static class OnLongKeyListener implements OnKeyListener {
      private boolean a = false;

      public boolean onKey(View v, int keyCode, KeyEvent event) {
         int var4;
         if ((var4 = event.getAction()) != 0) {
            if (var4 == 1) {
               boolean var5 = this.a;
               this.onKeyClicked(v, keyCode, event, var5);
               this.a = false;
            }
         } else {
            this.a = this.a | event.isLongPress();
         }

         return true;
      }

      protected void onKeyClicked(View v, int keyCode, KeyEvent event, boolean longPress) {
      }
   }
}
