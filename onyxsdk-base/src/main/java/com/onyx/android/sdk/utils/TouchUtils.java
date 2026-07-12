package com.onyx.android.sdk.utils;

import android.view.MotionEvent;

public class TouchUtils {
   public static boolean isPenTouchType(MotionEvent event) {
      return a(event, 2) || a(event, 4);
   }

   public static boolean isStylusTouchType(MotionEvent event) {
      return a(event, 2);
   }

   public static boolean isEraserTouchType(MotionEvent event) {
      return a(event, 4);
   }

   private static boolean a(MotionEvent event, int touchType) {
      return event.getToolType(event.getActionIndex()) == touchType;
   }

   public static boolean isFingerTouch(MotionEvent event) {
      int var1;
      return (var1 = event.getToolType(event.getActionIndex())) == 1 || var1 == 0;
   }

   public static boolean isMultiTouch(MotionEvent event) {
      return event.getPointerCount() > 1;
   }

   public static boolean isTouchFinish(MotionEvent event) {
      return event.getAction() == 1 || event.getAction() == 3;
   }
}
