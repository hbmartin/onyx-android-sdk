package com.onyx.android.sdk.data;

public class PenConstant {
   public static final int DEFAULT_PEN_UP_REFRESH_TIME_MS = 500;
   public static final int MIN_PEN_UP_REFRESH_TIME_MS = 400;
   public static final int MAX_PEN_UP_REFRESH_TIME_MS = 2000;
   public static final int PEN_UP_REFRESH_STEP = 100;
   public static float DEFAULT_STROKE_WIDTH;
   public static float BRUSH_PEN_DEFAULT_STROKE_WIDTH;
   public static float MARKER_PEN_DEFAULT_STROKE_WIDTH;
   public static float CHARCOAL_PEN_DEFAULT_STROKE_WIDTH;
   public static float PENCIL_PEN_DEFAULT_STROKE_WIDTH;
   public static final float DEFAULT_STROKE_WIDTH_MM = 0.5F;
   public static final float BRUSH_PEN_DEFAULT_STROKE_WIDTH_MM = 1.0F;
   public static final float MARKER_PEN_DEFAULT_STROKE_WIDTH_MM = 5.0F;
   public static final float CHARCOAL_PEN_DEFAULT_STROKE_WIDTH_MM = 0.3F;
   public static final float PENCIL_PEN_DEFAULT_STROKE_WIDTH_MM = 0.5F;
   public static final float MAX_RENDER_STROKE_WIDTH = 80.0F;
   public static final float MIN_NORMAL_STROKE_WIDTH = 0.1F;
   public static final float MAX_NORMAL_STROKE_WIDTH = 2.0F;
   public static final float MIN_MARKER_STROKE_WIDTH = 0.5F;
   public static final float MAX_MARKER_STROKE_WIDTH = 8.0F;
   public static final float NORMAL_STROKE_WIDTH_GAP = 0.05F;
   public static final float NORMAL_STROKE_WIDTH_MAX_GAP = 1.0F;
   public static final float NORMAL_STROKE_WIDTH_MIN_GAP = 0.25F;
   public static final float NORMAL_STROKE_WIDTH_DIVIDER = 2.0F;
   public static final float MARKER_STROKE_WIDTH_GAP = 0.05F;
   public static final int PEN_DEACTIVATE_TIME_INTERVAL_MS = 100;
   public static final float CHARCOAL_SHAPE_DRAW_NORMAL_SCALE_WIDTH_THRESHOLD = 80.0F;
   public static final float FILTER_REPEAT_MOVE_POINT_SPEED_LIMIT_VALUE = 0.005F;
   public static final float FILTER_REPEAT_MOVE_POINT_PRESSURE_LIMIT_VALUE = 2.0F;
   public static final int SHAPE_LIMIT_RENDER_TOUCH_POINT_COUNT = 20000;
   public static final float WAVY_LENGTH = 24.0F;
   public static final float WAVY_PEAK = 12.0F;
   public static final float AREA_ERASER_STROKE_WIDTH = 0.1F;
   public static final float DASH_STROKE_WIDTH = 5.0F;
   public static final float SELECTION_DASH_STROKE_WIDTH = 3.0F;
   public static final float DEFAULT_FINGER_TOUCH_PRESSURE = 1500.0F;
   public static final float MARKER_PEN_DEFAULT_OPACITY = 0.5F;
   public static final float LATIN_CALLIGRAPHY_PEN_BRUSH_ANGLE = 45.0F;
   public static final float ASIA_CALLIGRAPHY_PEN_BRUSH_ANGLE = -45.0F;
   public static final int NEO_SQUARE_PEN_RENDER_STROKE_WIDTH_FACTOR = 2;
   public static final boolean ENABLE_CONFIG_PEN_PRESSURE_SENSITIVITY = true;
   public static final float DEFAULT_PRESSURE_SENSITIVITY = 0.375F;
   public static final float DEFAULT_VELOCITY_SENSITIVITY = 1.0F;
   public static final float DEFAULT_ALPHA_FACTOR = 1.0F;
   public static final float DEFAULT_DPI = 320.0F;
   public static final float NEW_PENCIL_COMPENSATE_SCALE = 2.2F;
   public static final float DEFAULT_SMOOTH_LEVEL = 0.2F;

   public static float checkPenWidth(float width) {
      return !(width > 80.0F) && !(width <= 0.0F) ? width : DEFAULT_STROKE_WIDTH;
   }
}
