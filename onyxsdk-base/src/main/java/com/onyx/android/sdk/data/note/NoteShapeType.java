package com.onyx.android.sdk.data.note;

public class NoteShapeType {
   public static final int SHAPE_SELECTOR = -3;
   public static final int SHAPE_ERASER = -2;
   public static final int SHAPE_INVALID = -1;
   public static final int SHAPE_CIRCLE = 0;
   public static final int SHAPE_RECTANGLE = 1;
   public static final int SHAPE_PENCIL_SCRIBBLE = 2;
   public static final int SHAPE_OILY_PEN_SCRIBBLE = 3;
   public static final int SHAPE_FOUNTAIN_PEN_SCRIBBLE = 4;
   public static final int SHAPE_BRUSH_SCRIBBLE = 5;
   public static final int SHAPE_TEXT = 6;
   public static final int SHAPE_LINE = 7;
   public static final int SHAPE_TRIANGLE = 8;
   public static final int SHAPE_ANNOTATION = 9;
   public static final int SHAPE_TRIANGLE_45 = 10;
   public static final int SHAPE_TRIANGLE_60 = 11;
   public static final int SHAPE_TRIANGLE_90 = 12;
   public static final int SHAPE_FORM_TEXT = 13;
   public static final int SHAPE_ERASE_OVERLAY = 14;
   public static final int SHAPE_MARKER_SCRIBBLE = 15;
   public static final int SHAPE_EDIT_TEXT_SHAPE = 16;
   public static final int SHAPE_POLYGON = 17;
   public static final int SHAPE_RHOMBUS = 18;
   public static final int SHAPE_IMAGE = 19;
   public static final int SHAPE_AREA_ERASE = 20;
   public static final int SHAPE_NEO_BRUSH = 21;
   public static final int SHAPE_CHARCOAL_SCRIBBLE = 22;
   public static final int SHAPE_AUDIO = 23;
   public static final int SHAPE_REGULAR_POLYGON = 24;
   public static final int SHAPE_WAVE_LINE = 25;
   public static final int SHAPE_TRAPEZOID = 26;
   public static final int SHAPE_REGULAR_HEXAGON = 27;
   public static final int SHAPE_ARROW_LINE = 28;
   public static final int SHAPE_RICH_TEXT = 29;
   public static final int SHAPE_RESOURCE = 30;
   public static final int SHAPE_POLYLINE = 31;
   public static final int SHAPE_STAMP = 32;
   public static final int SHAPE_LINK = 33;
   public static final int SHAPE_ATTACHMENT = 34;
   public static final int SHAPE_SVG = 35;
   public static final int SHAPE_FREE = 36;
   public static final int SHAPE_FILL = 37;
   public static final int SHAPE_TIMESTAMP = 38;
   public static final int SHAPE_CURVE = 39;
   public static final int SHAPE_UNIVERSAL = 40;
   public static final int SHAPE_SQUARE_PEN = 47;
   public static final int SHAPE_LATIN_CALLIGRAPHY_PEN_SCRIBBLE = 60;
   public static final int SHAPE_ASIA_CALLIGRAPHY_PEN_SCRIBBLE = 61;
   public static final int SHAPE_MEETING_AUDIO = 200;
   public static final int SHAPE_BOOKMARK = 210;
   public static final int SELECTION_LINK_MODEL = 100;
   public static final int ERASER_STROKE = 0;
   public static final int ERASER_MOVE = 1;
   public static final int ERASER_AREA = 2;
   public static final int SHAPE_REFERENCE = 2000;

   public static boolean isBitmapRenderShape(int shape) {
      return shape == 19 || shape == 32 || shape == 37 || shape == 22;
   }

   public static boolean isSupportSerializeData(int type) {
      return type != 23 && type != 34 && type != 40;
   }
}
