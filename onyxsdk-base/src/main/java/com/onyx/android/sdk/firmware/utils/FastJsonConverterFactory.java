package com.onyx.android.sdk.firmware.utils;

import com.alibaba.fastjson2.JSONReader.Feature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;

public class FastJsonConverterFactory extends Factory {
   private Feature[] a;
   private com.alibaba.fastjson2.JSONWriter.Feature[] b;

   public static FastJsonConverterFactory create() {
      return new FastJsonConverterFactory();
   }

   private FastJsonConverterFactory() {
   }

   public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
      return new b(type, this.a);
   }

   public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
      return new a(this.b);
   }

   public Feature[] getFeatures() {
      return this.a;
   }

   public FastJsonConverterFactory setFeatures(Feature[] features) {
      this.a = features;
      return this;
   }

   public com.alibaba.fastjson2.JSONWriter.Feature[] getSerializerFeatures() {
      return this.b;
   }

   public FastJsonConverterFactory setSerializerFeatures(com.alibaba.fastjson2.JSONWriter.Feature[] serializerFeatures) {
      this.b = serializerFeatures;
      return this;
   }
}
