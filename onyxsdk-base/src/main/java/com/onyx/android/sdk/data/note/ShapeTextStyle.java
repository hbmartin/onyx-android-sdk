package com.onyx.android.sdk.data.note;

import android.text.Layout.Alignment;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.Debug;

public class ShapeTextStyle implements Cloneable {
   public static final float DEFAULT_TEXT_SPACING = 1.2F;
   public static final int DEFAULT_TEXT_WIDTH = 300;
   public static final int HORIZONTAL = 0;
   public static final int VERTICAL = 1;
   public static final int DEFAULT_BORDER_WIDTH = 3;
   public static final String DEFAULT_BORDER_COLOR = "#FF0000";
   private float a;
   private float b = 1.2F;
   private boolean c;
   private boolean d;
   private boolean e;
   private NoteChineseConvertType f = NoteChineseConvertType.NONE;
   private boolean g;
   private boolean h;
   private float i = 300.0F;
   private float j = 0.0F;
   private float k = 1.0F;
   private String l;
   private int m = 0;
   private int n = 0;
   private int o;
   private int p;
   private int q = 0;
   private int r = 3;
   private String s = "#FF0000";
   private String t = "";

   public static NoteChineseConvertType getChineseConvertTypeByStyle(ShapeTextStyle style) {
      return style.isTextOriginConverted()
         ? NoteChineseConvertType.NONE
         : (style.isTextSimplifiedConverted() ? NoteChineseConvertType.T_TO_S : NoteChineseConvertType.S_TO_T);
   }

   public String getNightTag() {
      return this.t;
   }

   public void setNightTag(String nightTag) {
      this.t = nightTag;
   }

   public ShapeTextStyle setTextWidth(float textWidth) {
      this.i = textWidth;
      this.ensureTextWidth();
      return this;
   }

   public ShapeTextStyle setTextHeight(float textHeight) {
      this.j = textHeight;
      return this;
   }

   public float getTextSize() {
      return this.a;
   }

   public ShapeTextStyle setTextSize(float textSize) {
      this.a = textSize;
      return this;
   }

   public float getTextSpacing() {
      return this.b;
   }

   public float getTextWidth() {
      this.ensureTextWidth();
      return this.i;
   }

   public float getTextHeight() {
      return this.j;
   }

   public ShapeTextStyle setTextSpacing(float textSpacing) {
      this.b = textSpacing;
      return this;
   }

   public void appendTextWidth(int append) {
      this.i += append;
   }

   public boolean isTextItalic() {
      return this.c;
   }

   public ShapeTextStyle setTextItalic(boolean textItalic) {
      this.c = textItalic;
      return this;
   }

   public int getAlignType() {
      return this.n;
   }

   public ShapeTextStyle setAlignType(int type) {
      this.n = type;
      return this;
   }

   public int getTextBorder() {
      return this.q;
   }

   public int getBorderWidth() {
      return this.r;
   }

   public void setBorderWidth(int borderWidth) {
      this.r = borderWidth;
   }

   public void setBorderColor(String borderColor) {
      this.s = borderColor;
   }

   public String getBorderColor() {
      return this.s;
   }

   public ShapeTextStyle setTextBorder(int textBorder) {
      this.q = textBorder;
      return this;
   }

   @JSONField(serialize = false, deserialize = false)
   public Alignment getAlignment() {
      int var1;
      if ((var1 = this.n) != 0) {
         if (var1 != 1) {
            return var1 != 2 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_CENTER;
         } else {
            return Alignment.ALIGN_OPPOSITE;
         }
      } else {
         return Alignment.ALIGN_NORMAL;
      }
   }

   @Deprecated
   public boolean isTextTraditional() {
      return this.d;
   }

   public ShapeTextStyle setTextTraditional(boolean textTraditional) {
      this.d = textTraditional;
      return this;
   }

   public NoteChineseConvertType getTextConvertType() {
      return this.f;
   }

   public ShapeTextStyle setTextConvertType(NoteChineseConvertType convertType) {
      this.f = convertType;
      return this;
   }

   public boolean isTextConverted() {
      return this.e;
   }

   public ShapeTextStyle setTextConverted(boolean textConverted) {
      this.e = textConverted;
      return this;
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isTextOriginConverted() {
      return this.getTextConvertType().equals(NoteChineseConvertType.NONE);
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isTextSimplifiedConverted() {
      return this.getTextConvertType().equals(NoteChineseConvertType.T_TO_S) && this.isTextConverted();
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isTextTraditionalConverted() {
      return this.getTextConvertType().equals(NoteChineseConvertType.S_TO_T) && this.isTextConverted();
   }

   public boolean isTextBold() {
      return this.g;
   }

   public ShapeTextStyle setTextBold(boolean textBold) {
      this.g = textBold;
      return this;
   }

   public boolean isTextUnderline() {
      return this.h;
   }

   public ShapeTextStyle setTextUnderline(boolean textUnderline) {
      this.h = textUnderline;
      return this;
   }

   public float getPointScale() {
      return this.k;
   }

   public ShapeTextStyle setPointScale(float pointScale) {
      this.k = pointScale;
      return this;
   }

   public String getFontFace() {
      return this.l;
   }

   public ShapeTextStyle setFontFace(String fontFace) {
      this.l = fontFace;
      return this;
   }

   public int getOrientation() {
      return this.m;
   }

   public ShapeTextStyle setOrientation(int orientation) {
      this.m = orientation;
      return this;
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isVertical() {
      return this.m == 1;
   }

   public int getPaddingStart() {
      return this.o;
   }

   public void setPaddingStart(int paddingStart) {
      this.o = paddingStart;
   }

   public int getPaddingEnd() {
      return this.p;
   }

   public void setPaddingEnd(int paddingEnd) {
      this.p = paddingEnd;
   }

   public ShapeTextStyle clone() {
      try {
         return (ShapeTextStyle) super.clone();
      } catch (CloneNotSupportedException exception) {
         throw new AssertionError(exception);
      }
   }

   public void ensureTextWidth() {
      if (this.i <= 0.0F) {
         Debug.w(new IllegalArgumentException("text width = " + this.i));
         this.i = 300.0F;
      }
   }
}
