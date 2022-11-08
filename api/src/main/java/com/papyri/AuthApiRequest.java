package com.papyri;

import com.papyri.data.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthApiRequest extends ApiRequest {

    // OVERALL WORKFLOW - SPOTIFY
        // STEP 1: Auth flow - sign in, allow user to auth. Receive token back to use in API calls.
        // STEP 2: fetch user's playlists
        // STEP 3: save data into datastore
        // STEP 4: browse and refresh that data

    // AUTHORIZATION CODE FLOW - STEPS
    //        Handling the user login request
    //        Specifying the scopes for which authorization is sought
    //        Performing the exchange of the authorization code for an access token
    //        Calling the Web API endpoint, callback, code for tokensx

    // STEP 1: Auth flow
    //      Call 1: Sign In - RESPONSE: 302 redirects back to your site w/code on url of redirect
    //        The first call is a GET to the service ‘/authorize’ endpoint, passing to it the client ID, scopes,
    //        and redirect URI. This is the call that starts the process of authenticating to user and gets the user’s authorization to access data.
    //      Call 2:
    //        The second call is a POST to the Spotify Accounts Service ‘/api/token’ endpoint, passing it the authorization code returned by the
    //        first call and the client secret key. This call returns an access token and also a refresh token.
    //     Call 3: in the code managing requests to ‘/refresh_token’, a refresh token is sent to ‘/api/token’. This will generate a new access token that we can issue when the previous has expired.

    // login form > /authorize(id, scope, redirectUri) > redirect to client(code) > /token(code, secret, redirectUri) > redirect to client(tokens)

    @Inject
    private Repository repository;

    public String   authorizeUrl() throws UnsupportedEncodingException {
        String clientId = repository.getClientId();
        String redirectUri = repository.getRedirectUrl();

        return String.format
                ("https://accounts.spotify.com/authorize?response_type=code&client_id=%s&scope=%s&redirect_uri=%s&state=%s",
                        clientId, repository.getScopes(), encodeValue(redirectUri), "abcd1234"); //todo state
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String base64EncodeValue(String value) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    /*
    SECOND CALL - AuthorizationCode from first call
    RETURNS access & refresh tokens
     */
    public String token(String authorizationCode) throws UnsupportedEncodingException {
        var values = new HashMap<String, String>() {{
            put("grant_type", "authorization_code");
            put ("redirect_uri", repository.getRedirectUrl());
            put("code", authorizationCode);
            put("client_id", repository.getClientId());
            put("client_secret", repository.getClientSecret());
        }};

        return post(
                "https://accounts.spotify.com/api/token",
                "application/x-www-form-urlencoded",
                getDataString(values),
                "Basic " + base64EncodeValue(repository.getClientId() + ":" + repository.getClientSecret()));
    }

    public String tokenClientCredential() throws UnsupportedEncodingException {
        var values = new HashMap<String, String>() {{
            put("grant_type", "client_credentials");
            put("scope", repository.getScopes());
        }};

        return post(
                "https://accounts.spotify.com/api/token",
                "application/x-www-form-urlencoded",
                getDataString(values),
                "Basic " + base64EncodeValue(repository.getClientId() + ":" + repository.getClientSecret()));
    }

    private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
