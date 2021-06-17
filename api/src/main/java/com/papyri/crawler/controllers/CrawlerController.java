package com.papyri.crawler.controllers;

import com.papyri.ApiRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Controller
@Slf4j
public class CrawlerController {

    @Inject
    private ApiRequest apiRequest;

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
        // STEP 1: log into spotify with client id/secret, allow user to auth. Receive token back.
        // STEP 2: fetch user's playlists
        // STEP 3: save data into datastore
        // STEP 4: browse and refresh that data

        // API call
        // Controller > SpotifyApi > http request > auth

        return apiRequest.postRequest("https://postman-echo.com/post", "Sample request body from: " + api);
    }

    @Post(uri="/mydata/save/{api}", produces= MediaType.TEXT_PLAIN)
    @Operation(summary = "Gets API info", description = "API info about a specific API returned")
    @Tag(name = "mydata")
    public String saveMyData(String api) {
        return api;
    }

    @Get(uri="/mydata/{api}", produces= MediaType.TEXT_PLAIN)
    @Operation(summary = "Gets API info", description = "Gets your data for the given service's API")
    @Tag(name = "mydata")
    public String getMyData(String api) {
        return api;
    }
}