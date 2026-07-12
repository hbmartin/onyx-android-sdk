package com.onyx.android.sdk.data.pdf;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import androidx.annotation.NonNull;

public interface PDFProvider {
   Bitmap renderPageBitmap(String var1, int var2, int var3, int var4, @NonNull Config var5);
}
