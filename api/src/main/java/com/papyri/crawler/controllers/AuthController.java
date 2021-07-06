package com.papyri.crawler.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.papyri.AuthApiRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@Controller
@Slf4j
public class AuthController {
    @Inject
    private AuthApiRequest authApiRequest;

    @Get(uri="/auth/login", produces= MediaType.TEXT_PLAIN)
    public HttpResponse<?> login() throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException {
        var url = authApiRequest.authorizeUrl();
        return HttpResponse.seeOther(URI.create(url));
    }

    @Get(uri="/auth/callback", produces= MediaType.TEXT_PLAIN)
    @ApiResponse(content = @Content(mediaType =  "text/plain", schema = @Schema(type="string")))
    public String authorizeCallback(String code) throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException, JsonProcessingException {
        log.info("Auth Callback: token...");
        return authApiRequest.token(code);
    }

}
