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
import org.json.JSONObject;

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
    public HttpResponse<?> authorizeCallback(String code) throws URISyntaxException, ExecutionException, InterruptedException, UnsupportedEncodingException, JsonProcessingException {
        log.info("Auth Callback: token...");
        var tokens  =  authApiRequest.token(code);
        var token = new JSONObject(tokens).getString("access_token");

        //? redriect back
        var playlist = authApiRequest.get(savedTracks(), token).get().body();;
        //var playlist = authApiRequest.get(playlists(127855684), token).get().body();;
        //var playlist = authApiRequest.get(playlistTracks("7IqhCGwlLdzzoRSA6zXval"), token).get().body();;

        return HttpResponse.ok(playlist);
    }

    private String playlists(int userId) {
        return "https://api.spotify.com/v1/users/" + userId + "/playlists";
    }

    private String playlistDetails(String playlistId) {
        return "https://api.spotify.com/v1/playlists/" + playlistId;
    }

    private String savedTracks() {
        return "https://api.spotify.com/v1/me/tracks?limit=50";
    }

    private String playlistTracks(String playlistId) {
        return "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
    }

}
