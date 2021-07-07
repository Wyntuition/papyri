package com.papyri;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpRequest.newBuilder;

@Singleton
public class ApiRequest {

    public CompletableFuture<HttpResponse<String>> get(String url, String token) throws URISyntaxException {
        var response = HttpClient.newHttpClient().sendAsync(
                newBuilder(new URI(url))
                        .headers("Authorization", "Bearer " + token)
                        .GET().build()
                ,HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public String post(String url, String contentType, String body, String token) {
        HttpResponse response = null;
        try {
            java.net.http.HttpRequest request = newBuilder()
                    .uri(new URI(url))
                    //.headers("Content-Type", contentType, "Authorization", "Bearer" + token)
                    .headers("Content-Type", contentType)
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(body))
                    .build();

            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response != null ? response.body().toString() : null;
    }

}
