package com.onyx.android.sdk.firmware.utils;

import com.onyx.android.sdk.firmware.api.OnyxOTAService;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public class ServiceFactory {
   private static ConcurrentHashMap<String, Retrofit> a = new ConcurrentHashMap<>();
   private static ConcurrentHashMap<String, OkHttpClient> b = new ConcurrentHashMap<>();

   private static Retrofit a(String baseUrl) {
      if (!a.containsKey(baseUrl)) {
         Retrofit var1 = getBaseRetrofitBuilder(baseUrl).build();
         a.put(baseUrl, var1);
      }

      return a.get(baseUrl);
   }

   public static final OnyxOTAService getOTAService(String baseUrl) {
      return getSpecifyService(OnyxOTAService.class, baseUrl);
   }

   public static Builder getBaseRetrofitBuilder(String baseUrl) {
      return getRetrofitBuilder(baseUrl, getBaseOkClientBuilder(baseUrl));
   }

   public static Builder getRetrofitBuilder(String baseUrl, OkHttpClient client) {
      return new Builder().baseUrl(baseUrl).client(client).addConverterFactory(FastJsonConverterFactory.create());
   }

   public static OkHttpClient getBaseOkClientBuilder(String baseUrl) {
      if (b.containsKey(baseUrl)) {
         return b.get(baseUrl);
      }

      OkHttpClient var1;
      OkHttpClient var10000 = var1 = new OkHttpClient().newBuilder().build();
      b.put(baseUrl, var1);
      return var10000;
   }

   public static final <T> T getSpecifyService(Class<T> service, String baseUrl) {
      return (T)a(baseUrl).create(service);
   }
}
