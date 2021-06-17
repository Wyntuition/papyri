package com.papyri;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class ApiRequest {

    public String postRequest(String url, String body) {
        HttpResponse response = null;
        try {
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers("Content-Type", "text/plain;charset=UTF-8")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(body))
                    .build();

            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Techniques
        // call 2 services, thenCombine - CompleteableFuture; stream of asynchromously processed data - RxJava

        return response != null ? response.body().toString() : null;
    }

    private List<CompletableFuture<String>> multipleHttpRequest() throws URISyntaxException {
        List<URI> targets = Arrays.asList(
                new URI("https://postman-echo.com/get?foo1=bar1"),
                new URI("https://postman-echo.com/get?foo2=bar2"));
        HttpClient client = HttpClient.newHttpClient();
        return targets.stream()
                .map(target -> client
                        .sendAsync(
                                java.net.http.HttpRequest.newBuilder(target).GET().build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body))
                .collect(Collectors.toList());
    }
}
