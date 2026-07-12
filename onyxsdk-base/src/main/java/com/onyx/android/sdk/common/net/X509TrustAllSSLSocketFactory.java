package com.onyx.android.sdk.common.net;

import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class X509TrustAllSSLSocketFactory {
   private X509TrustManager a;
   private SSLSocketFactory b;

   public X509TrustAllSSLSocketFactory() {
      try {
         this.a = new X509TrustAllCertsManager();
         SSLContext context = SSLContext.getInstance("TLS");
         context.init(null, new X509TrustManager[]{this.a}, new SecureRandom());
         this.b = context.getSocketFactory();
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public X509TrustManager getTrustManager() {
      return this.a;
   }

   public SSLSocketFactory getSslSocketFactory() {
      return this.b;
   }

   public boolean isValid() {
      return this.a != null && this.b != null;
   }
}
