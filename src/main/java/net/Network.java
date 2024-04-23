package net;

import java.net.Socket;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class Network {
    public static void main(String[] args){
        
        String address = "https://www.google.com";
        if (args.length > 0)
        {
            address = args[0];
        }

        try {

            URL url = new URL(address);
            HttpsURLConnection client = (HttpsURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.setRequestProperty("User-Agent", "java/1.7.0_80");

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[] { TRUST_MANAGER }, new SecureRandom());
            client.setSSLSocketFactory(sslContext.getSocketFactory());

            // send HTTP request

            System.out.println(String.format("GET %s HTTP/1.1", address));

            int responseCode = client.getResponseCode();
            System.out.println(responseCode);
        }
        catch (java.io.IOException e) {
            Network.printStackTrace(e);
        }
        // catch (java.lang.InterruptedException e) {
        //     Network.printStackTrace(e);
        // }
        catch (java.security.KeyManagementException e) {
            Network.printStackTrace(e);
        }
        catch (java.security.NoSuchAlgorithmException e) {
            Network.printStackTrace(e);
        }
    }
    private static void printStackTrace(Exception e){
        e.printStackTrace();
    }

    private static final TrustManager TRUST_MANAGER = new X509ExtendedTrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
 
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            System.out.println(authType);
            for (int index = 0; index < chain.length; index++) {
                System.out.println(chain[index]);
            }
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
            checkServerTrusted(chain, authType);
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
            checkServerTrusted(chain, authType);
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // empty method
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
            // empty method
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
            // empty method
        }
    };
}