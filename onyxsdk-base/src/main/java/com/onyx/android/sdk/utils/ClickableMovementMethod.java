package com.onyx.android.sdk.utils;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class ClickableMovementMethod extends LinkMovementMethod {
   private static ClickableMovementMethod b;
   private boolean a = true;

   public static ClickableMovementMethod getInstance() {
      if (b == null) {
         b = new ClickableMovementMethod();
      }

      return b;
   }

   public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
      int var4;
      if ((var4 = event.getAction()) == 1 || var4 == 0) {
         int var8 = (int)event.getX();
         int var16 = (int)event.getY();
         int var9 = var8 - widget.getTotalPaddingLeft();
         var16 -= widget.getTotalPaddingTop();
         int var10 = var9 + widget.getScrollX();
         int var5 = var16 + widget.getScrollY();
         Layout var10002 = widget.getLayout();
         int var11 = var10002.getLineForVertical(var5);
         int var12;
         int var15 = var12 = var10002.getOffsetForHorizontal(var11, var10);
         ClickableSpan[] var14 = (ClickableSpan[])buffer.getSpans(var15, var15, ClickableSpan.class);
         ClickableImageSpan[] var13 = (ClickableImageSpan[])buffer.getSpans(var12, var12, ClickableImageSpan.class);
         if (var14.length != 0) {
            if (var4 == 1) {
               var14[0].onClick(widget);
            } else if (var4 == 0 && this.a) {
               int var7 = buffer.getSpanStart(var14[0]);
               Selection.setSelection(buffer, var7, buffer.getSpanEnd(var14[0]));
            }

            return true;
         }

         if (var13.length != 0) {
            if (var4 == 1) {
               var13[0].onClick(widget);
            } else if (var4 == 0 && this.a) {
               int var6 = buffer.getSpanStart(var13[0]);
               Selection.setSelection(buffer, var6, buffer.getSpanEnd(var13[0]));
            }

            return true;
         }

         Selection.removeSelection(buffer);
      }

      return false;
   }

   public ClickableMovementMethod setSelectionWhenClick(boolean selection) {
      this.a = selection;
      return this;
   }
}
