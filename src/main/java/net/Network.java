package net;

import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;

public class Network {
    public static void main(String[] args){
        HttpClient httpClient = HttpClient.newHttpClient();
    
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://www.google.com/"))
          .build();
        
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }
        catch (java.io.IOException e) {
            Network.printStackTrace(e);
        }
        catch (java.lang.InterruptedException e) {
            Network.printStackTrace(e);
        }
    }
    private static void printStackTrace(Exception e){
        e.printStackTrace();
    }
}