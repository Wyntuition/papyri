package com.papyri.crawler.controllers;

import com.papyri.AuthApiRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@Controller
@Slf4j
public class ApiAuthController {

    @Inject
    private AuthApiRequest authApiRequest;

    @Get(uri="/auth/login", produces=MediaType.TEXT_PLAIN)
    public HttpResponse<?> login() throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException {
        return HttpResponse.seeOther(URI.create(authApiRequest.authorizeUrl()));
    }

    @Get(uri="/auth/callback", produces=MediaType.TEXT_PLAIN)
    @ApiResponse(content = @Content(mediaType =  "text/plain", schema = @Schema(type="string")))
    public HttpResponse<?> authorizeCallback(String code) throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException {
        log.info("Auth Callback wth code: " + code);

        // now exchange code from returned for an access token
        var tokens  =  authApiRequest.token(code);
        System.out.printf("Tokens response: " + tokens);
        var token = new JSONObject(tokens).getString("access_token");
        // todo
        var refreshToken = new JSONObject(tokens).getString("refresh_token");
        //var expiresIn = new JSONObject(tokens).getString("expires_in");

        return HttpResponse.ok(token);
    }

    //todo non-redirect version with client_credentials for non-browser?
    //      NOTE: Only endpoints that do not access user information can be accessed
    //      client_credentials; use auth flow for doing this with UI/users, but determine overall strategy - when backing up/doing background data migration might need access
    @Get(uri="/auth/clientcredential", produces=MediaType.APPLICATION_JSON)
    public HttpResponse<String> clientcredential() throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException {
        var tokens  =  authApiRequest.tokenClientCredential();
        //tokens.contains("error")

        //todo: refresh token
        var token = new JSONObject(tokens).getString("access_token");
        return HttpResponse.ok(token);
    }
}
