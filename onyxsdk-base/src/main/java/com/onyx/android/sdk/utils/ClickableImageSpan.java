package com.onyx.android.sdk.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.onyx.android.sdk.data.span.FixVerticalImageSpan;

public abstract class ClickableImageSpan extends FixVerticalImageSpan {
   public ClickableImageSpan(Drawable b) {
      super(b);
   }

   public abstract void onClick(View var1);
}
