package com.onyx.android.sdk.data.pdf;

import com.onyx.android.sdk.data.richtext.RichTextRun;

public class PdfTextRunCreateArgs {
   public static final int VERTICAL_ROTATION_ANGLE = 90;
   public static final float UNDERLINE_THICKNESS = 20.0F;
   public static final float TEXT_SKEW_X = -0.2F;
   private RichTextRun a;
   private String b;
   private boolean c;
   private float d;
   private float e;
   private float f;
   private float g;
   private float h;

   public static PdfTextRunCreateArgs create(RichTextRun textRun, float scaleX, float scaleY) {
      PdfTextRunCreateArgs var10000 = new PdfTextRunCreateArgs();
      var10000.setRichTextRun(textRun).setScaleX(scaleX).setScaleY(scaleY);
      return var10000;
   }

   public RichTextRun getRichTextRun() {
      return this.a;
   }

   public PdfTextRunCreateArgs setRichTextRun(RichTextRun richTextRun) {
      this.a = richTextRun;
      return this;
   }

   public String getFontPath() {
      return this.b;
   }

   public PdfTextRunCreateArgs setFontPath(String fontPath) {
      this.b = fontPath;
      return this;
   }

   public boolean isVertical() {
      return this.c;
   }

   public PdfTextRunCreateArgs setVertical(boolean vertical) {
      this.c = vertical;
      return this;
   }

   public float getFontSize() {
      return this.d;
   }

   public PdfTextRunCreateArgs setFontSize(float fontSize) {
      this.d = fontSize;
      return this;
   }

   public float getOffsetX() {
      return this.e;
   }

   public PdfTextRunCreateArgs setOffsetX(float offsetX) {
      this.e = offsetX;
      return this;
   }

   public float getOffsetY() {
      return this.f;
   }

   public PdfTextRunCreateArgs setOffsetY(float offsetY) {
      this.f = offsetY;
      return this;
   }

   public float getScaleX() {
      return this.g;
   }

   public PdfTextRunCreateArgs setScaleX(float scaleX) {
      this.g = scaleX;
      return this;
   }

   public float getScaleY() {
      return this.h;
   }

   public PdfTextRunCreateArgs setScaleY(float scaleY) {
      this.h = scaleY;
      return this;
   }
}
