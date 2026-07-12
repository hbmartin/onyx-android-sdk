package com.onyx.android.sdk.api;

import android.graphics.Bitmap;
import android.graphics.RectF;

public interface ReaderImage {
   RectF getRectangle();

   Bitmap getBitmap();

   int getPositionInt();

   ReaderPosition getPosition();
}
