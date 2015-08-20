# trust all certificates

   Simple java agent that disables certificate validation.

   When java establishes the ssl connection to the host it has to verify the certificate of the connected host.
   Connection is refused when the certificate is not trusted or valid.

   In some hacking situations (see the background) you may want to disable the verification.

   Remember about the risks. Never use it in production.

## Usage

   Suppose you are running application which connnects to the host:

        java TestApp https://10.132.24.6:8443/

   and it ends with the javax.net.ssl.SSLHandshakeException exception.
   Then add the javaagent argument which disables the hostname verification.

        java -javaagent:trustallcertificates.jar TestApp https://10.132.24.6:8443/

   Usually it is enough to set JAVA_OPTS before running the java application, since many tools picks up the JAVA_OPTS variable:

        export JAVA_OPTS="-javaagent:/tmp/trustallcertificates.jar"
        java $JAVA_OPTS -jar yourApp.jar

## Documentation

   The java agent will disable certificate verification for test purposes.
   Never use it in production!

   See the code for details.

   May be usefull when you have:

   Unexpected exception: java.security.cert.CertificateException: No name matching XXX found
   Exception in thread "main" javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No name matching XXX found
        at sun.security.ssl.Alerts.getSSLException(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.fatal(Unknown Source)
        at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
        at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
        at sun.security.ssl.ClientHandshaker.serverCertificate(Unknown Source)
        at sun.security.ssl.ClientHandshaker.processMessage(Unknown Source)
        at sun.security.ssl.Handshaker.processLoop(Unknown Source)
        at sun.security.ssl.Handshaker.process_record(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.readRecord(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.performInitialHandshake(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
        at sun.net.www.protocol.https.HttpsClient.afterConnect(Unknown Source)
        at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(Unknown Source)
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(Unknown Source)
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream(Unknown Source)
        at sun.net.www.protocol.https.HttpsURLConnectionImpl.getInputStream(Unknown Source)
        at TestHelper.main(TestHelper.java:23)
   Caused by: java.security.cert.CertificateException: No name matching XXX found
        at sun.security.util.HostnameChecker.matchDNS(Unknown Source)
        at sun.security.util.HostnameChecker.match(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkIdentity(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkIdentity(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkTrusted(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(Unknown Source)
        ... 14 more

   OR

   Unexpected exception: java.security.cert.CertificateException: No subject alternative names present
   Exception in thread "main" javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No subject alternative names present
        at sun.security.ssl.Alerts.getSSLException(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.fatal(Unknown Source)
        at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
        at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
        at sun.security.ssl.ClientHandshaker.serverCertificate(Unknown Source)
        at sun.security.ssl.ClientHandshaker.processMessage(Unknown Source)
        at sun.security.ssl.Handshaker.processLoop(Unknown Source)
        at sun.security.ssl.Handshaker.process_record(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.readRecord(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.performInitialHandshake(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
        at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
        at sun.net.www.protocol.https.HttpsClient.afterConnect(Unknown Source)
        at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(Unknown Source)
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(Unknown Source)
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream(Unknown Source)
        at sun.net.www.protocol.https.HttpsURLConnectionImpl.getInputStream(Unknown Source)
        at TestHelper.main(TestHelper.java:23)
   Caused by: java.security.cert.CertificateException: No subject alternative names present
        at sun.security.util.HostnameChecker.matchIP(Unknown Source)
        at sun.security.util.HostnameChecker.match(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkIdentity(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkIdentity(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkTrusted(Unknown Source)
        at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(Unknown Source)
        ... 14 more

## Caution

   Replacing TrustManager or HostnameVerifier can be very dangerous, because a man-in-the-middle could intercept your traffic.

## Compilation

        javac TrustAllCertificatesAgent.java
        jar cmf manifest.txt trustallcertificates.jar *.class
        javac TestApp.java

## Some reading

   * http://www.crsr.net/Notes/SSL.html
   * http://apetec.com/support/GenerateSAN-CSR.htm

##  Usefull options

        -Djavax.net.debug=all
        -Djavax.net.ssl.trustStore=/tmp/app.truststore
        -Djavax.net.ssl.trustStorePassword=changeit  # this is standard password for jks keystore/truststore
        -Dssl.debug=true
        -Dsun.security.ssl.allowUnsafeRenegotiation=true

