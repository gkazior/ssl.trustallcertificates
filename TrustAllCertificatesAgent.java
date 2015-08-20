import javax.net.ssl.*;

import java.security.cert.X509Certificate;
import java.lang.instrument.Instrumentation;

/**
 * The class when run as an agent disables all SSL certificate checks.
 * Use it for hacking only, never in test or production!
 */
public class TrustAllCertificatesAgent {

  public static void premain(String args, Instrumentation inst) {

    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {

      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }
    }};

    try {
      // Install the all-trusting trust manager
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      });
    } catch (Exception e) {
      System.out.println("Unexpected exception: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
