package com.onyx.android.sdk.firmware.utils;

import com.alibaba.fastjson2.JSONReader.Feature;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class b<T> implements Converter<ResponseBody, T> {
   private static final Feature[] c = new Feature[0];
   private Type a;
   private Feature[] b;

   b(Type type, Feature... features) {
      this.a = type;
      this.b = features;
   }

   @Override
   public T convert(ResponseBody response) throws IOException {
      try {
         Feature[] features = this.b != null ? this.b : c;
         return (T) com.alibaba.fastjson2.JSON.parseObject(response.string(), this.a, features);
      } finally {
         response.close();
      }
   }
}
