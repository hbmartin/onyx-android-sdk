package com.onyx.android.sdk.data.point;

public interface PointDocumentLoader {
   void parse(PointDocument var1) throws Exception;

   void parse(PointDocument var1, int var2, int var3) throws Exception;

   void save(PointDocument var1) throws Exception;
}
