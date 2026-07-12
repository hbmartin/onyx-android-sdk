package com.onyx.android.sdk.firmware.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter.Feature;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class a<T> implements Converter<T, RequestBody> {
   private static final MediaType b = MediaType.parse("application/json; charset=UTF-8");
   private Feature[] a;

   a(Feature... features) {
      this.a = features;
   }

   @Override
   public RequestBody convert(T value) throws IOException {
      Feature[] var2;
      byte[] var3;
      if ((var2 = this.a) != null) {
         var3 = JSON.toJSONBytes(value, var2);
      } else {
         var3 = JSON.toJSONBytes(value);
      }

      return RequestBody.create(b, var3);
   }
}
