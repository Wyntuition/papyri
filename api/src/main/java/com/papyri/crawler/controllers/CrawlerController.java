package com.papyri.crawler.controllers;

import com.papyri.crawler.services.CrawlerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

//todo auth for these URLs
@Controller
@Slf4j
public class CrawlerController {

    @Inject
    CrawlerService crawlerService;

    @Get(uri="/crawler/apis", produces= MediaType.TEXT_PLAIN)
    @Operation(summary = "API List", description = "Returns list of available APIs to grab data from")
    @ApiResponse(content = @Content(mediaType =  "text/plain", schema = @Schema(type="string")))
    @ApiResponse(responseCode = "400", description = "No APIs available.")
    @ApiResponse(responseCode = "404", description = "No APIs found")
    @Tag(name = "crawler")
    public String externalApis() {
        log.info("Calling...");
        return "Spotify\nPocket\nGmail\niDrive";
    }

    @Get(uri="/crawler/api/{api}", produces= MediaType.TEXT_PLAIN)
    @Operation(summary = "Gets API info", description = "API info about a specific API returned")
    @ApiResponse(content = @Content(mediaType =  "text/plain", schema = @Schema(type="string")))
    @ApiResponse(responseCode = "400", description = "No API info available.")
    @ApiResponse(responseCode = "404", description = "API info not found")
    @Tag(name = "crawler")
    public String apiInfo(String api) {
        return api;
    }

    @Get(uri="/mydata/{api}", produces= MediaType.TEXT_PLAIN)
    @Operation(summary = "Gets data from given API", description = "Gets your data for the given service's API")
    @Tag(name = "mydata")
    public String getMyData(String api) throws URISyntaxException, ExecutionException, InterruptedException {
        //todo: data category loop; will add things like list of sources for music playlists, albums, files, articles, videos; tags across these
        return null;
    }

    @Get(uri="/playlists/{userId}", produces=MediaType.APPLICATION_JSON)
    public HttpResponse<?> playlists(@Header("Authorization") String authorization, @Parameter int userId) throws URISyntaxException, ExecutionException, InterruptedException {
        return HttpResponse.ok(crawlerService.getPlaylists(authorization.split("Bearer ")[1], userId));
    }

    @Get(uri="/playlist/{id}", produces=MediaType.APPLICATION_JSON)
    public HttpResponse<?> playlistTracksPerPlaylist(@Header("Authorization") String authorization, @Parameter String id, @Parameter int offset) throws URISyntaxException, ExecutionException, InterruptedException {
        return HttpResponse.ok(crawlerService.getPlaylistTracks(authorization.split("Bearer ")[1], id, offset));
    }

    @Get(uri="/playlist/{id}", produces=MediaType.APPLICATION_JSON)
    public HttpResponse<?> playlistTracks(@Header("Authorization") String authorization, @Parameter String id, @Parameter int offset) throws URISyntaxException, ExecutionException, InterruptedException {
        return HttpResponse.ok(crawlerService.getPlaylistTracks(authorization.split("Bearer ")[1], id, offset));
    }

    @Get(uri="/me/tracks", produces=MediaType.APPLICATION_JSON)
    public HttpResponse<?> myTracks(@Header("Authorization") String authorization, @Parameter int offset) throws URISyntaxException, ExecutionException, InterruptedException {
        return HttpResponse.ok(crawlerService.getSavedTracks(authorization.split("Bearer ")[1], offset));
    }
}
