package com.onyx.android.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class RotationUtils {
   public static final String ACTION_ROTATION = "com.onyx.action.ROTATION";
   public static final String ARGS_ROTATION = "rotation";
   public static final String ARGS_ROTATE_BY = "args_rotate_by";
   public static final int UNKNOWN_ROTATION = 0;
   public static final int ROTATE_BY_USER = 1;
   public static final int ROTATE_BY_APP = 2;
   private static final int a = -1;

   public static void setRequestedOrientation(Activity activity, int orientation, boolean isScreenRotationFollowingSystem, int rotateBy) {
      if (isScreenRotationFollowingSystem) {
         setRequestedOrientationFollowingSystem(activity, orientation, rotateBy);
      } else {
         activity.setRequestedOrientation(orientation);
      }
   }

   public static void setRequestedOrientationFollowingSystem(Context context, int orientation, int rotateBy) {
      int var3 = convertOrientationToRotation(orientation);
      BroadcastHelper.sendRotationIntent(context.getApplicationContext(), var3, rotateBy);
   }

   public static int convertOrientationToRotation(int orientation) {
      if (orientation != 0) {
         if (orientation != 1) {
            if (orientation != 8) {
               return orientation != 9 ? -1 : 2;
            } else {
               return 3;
            }
         } else {
            return 0;
         }
      } else {
         return 1;
      }
   }

   public static int convertRotationToOrientation(int rotation) {
      if (rotation != 1) {
         if (rotation != 2) {
            return rotation != 3 ? 1 : 8;
         } else {
            return 9;
         }
      } else {
         return 0;
      }
   }

   public static int convertOrientationToDegrees(int orientation) {
      if (orientation != 0) {
         if (orientation != 8) {
            return orientation != 9 ? 0 : 180;
         } else {
            return 270;
         }
      } else {
         return 90;
      }
   }

   public static int computeNewRotation(Activity activity, int newOrientation) {
      int var2;
      int var3;
      int var10000 = var2 = computeNewRotation(var3 = activity.getRequestedOrientation(), convertOrientationToDegrees(newOrientation));
      String var10002 = "currentOrientation:" + var3 + ",orientation:" + var2 + ",newOrientation:" + newOrientation;
      Object[] var4 = new Object[0];
      Debug.i(RotationUtils.class, var10002, var4);
      return var10000 == var3 ? -1 : var2;
   }

   public static int computeNewRotation(int currentOrientation, int rotationOperation) {
      if (rotationOperation != 0) {
         if (rotationOperation != 90) {
            if (rotationOperation != 180) {
               if (rotationOperation != 270) {
                  return currentOrientation;
               } else if (currentOrientation == 1) {
                  return 8;
               } else if (currentOrientation == 0) {
                  return 1;
               } else if (currentOrientation == 9) {
                  return 0;
               } else {
                  return currentOrientation == 8 ? 9 : 8;
               }
            } else if (currentOrientation == 1) {
               return 9;
            } else if (currentOrientation == 0) {
               return 8;
            } else if (currentOrientation == 9) {
               return 1;
            } else {
               return currentOrientation == 8 ? 0 : 9;
            }
         } else if (currentOrientation == 1) {
            return 0;
         } else if (currentOrientation == 0) {
            return 9;
         } else if (currentOrientation == 9) {
            return 8;
         } else {
            return currentOrientation == 8 ? 1 : 0;
         }
      } else {
         return currentOrientation;
      }
   }

   public static int getDisplayRotation(Context context) {
      return context == null ? 0 : ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getRotation();
   }

   public static boolean isValidRotation(int rotation) {
      return rotation != -1;
   }

   public static boolean isRotateByUser(int rotateBy) {
      return rotateBy == 1;
   }
}
