package com.onyx.android.sdk.data.note;

public class NoteScene {
   public static final int INVALID_SCENE = -1;
   public static final int SCRIBBLE_SCENE = 0;
   public static final int RICH_TEXT_SCENE = 1;
   public static final int DRAFT_SCENE = 4;
   public static final int PDF_SCRIBBLE_SCENE = 3;
   public static final int MAX_SCENE = 3;
   public static final int SCRIBBLE_MEETING_SCENE = 2;

   public static boolean isValidScene(int scene) {
      return scene > -1 && scene <= 4;
   }

   public static boolean isRichTextScene(int scene) {
      return scene == 1;
   }

   public static boolean isScribbleScene(int scene) {
      return scene == 0 || scene == 3;
   }

   public static boolean isDraft(int scene) {
      return scene == 4;
   }

   public static boolean isMeetingScene(int scene) {
      return scene == 2;
   }

   public static boolean supportContinuousPageScene(int scene) {
      return isScribbleScene(scene);
   }
}
