package com.papyri.data;

import io.micronaut.context.annotation.Property;

import javax.inject.Singleton;

@Singleton
public class Repository {

    @Property(name = "spotify.client-id")
    private String clientId;
    public String getClientId() {
        return clientId;
    }

    @Property(name = "spotify.client-secret")
    private String clientSecret;
    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUrl() {
        return "http://localhost:8080/auth/callback";
    }

    public String getScopes() {
        return "user-read-private,user-library-read,playlist-read-private";
    }
}
