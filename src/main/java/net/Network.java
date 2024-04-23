package net;

import java.net.Socket;
import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class Network {
    public static void main(String[] args){
        
        String address = "http://www.google.com";
        if (args.length > 0)
        {
            address = args[0];
        }

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{ TRUST_MANAGER }, new SecureRandom());
        
            HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

            System.out.println(String.format("GET %s HTTP/1.1", address));

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }
        catch (java.io.IOException e) {
            Network.printStackTrace(e);
        }
        catch (java.lang.InterruptedException e) {
            Network.printStackTrace(e);
        }
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
            System.out.println("SSL certificate");
            System.out.println(chain);
            System.out.println(authType);
            // empty method
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