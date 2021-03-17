package com.papyri.crawler.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

@Controller("/crawler")

public class CrawlerController {
    @Get(uri = "/apis")
    public String externalApis() {
        return "Spotify!";
    }
}