package com.papyri.crawler.services;

import com.papyri.AuthApiRequest;
import com.papyri.crawler.Constants;
import com.papyri.models.Playlist;
import com.papyri.models.Track;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrawlerService {

    @Inject
    private AuthApiRequest authApiRequest;

    public List<Object> getPlaylists(String token, int userId) throws InterruptedException, ExecutionException, URISyntaxException {
        var playlist = authApiRequest.get(playlists(userId, 0), token).get().body();
        //var playlist = authApiRequest.get(playlistTracks("7IqhCGwlLdzzoRSA6zXval"), token).get().body();;
        var responseJson = getJsonFromResponse(playlist);
        var playlistsJson = responseJson.getJSONArray("items");

        return IntStream.range(0, playlistsJson.length())
                .mapToObj(i -> playlistsJson.getJSONObject(i))
                .map(m -> Playlist.builder()
                        .id(m.getString("id"))
                        .name(m.getString("name")).build())
                .collect(Collectors.toList());
    }

    public List<Track> getPlaylistTracks(String token, String playlistId, int offset) throws URISyntaxException, ExecutionException, InterruptedException {
        var response = authApiRequest.get(Constants.PLAYLIST_TRACKS_URL(playlistId, offset), token).get().body();
        var responseJson = getJsonFromResponse(response);
        var tracks = responseJson.getJSONArray("items");

        return extractTracksJson(tracks);
    }

    public List<Track> getSavedTracks(String token, int offset) throws InterruptedException, ExecutionException, URISyntaxException {
        var playlist = authApiRequest.get(savedTracksRequest(offset), token).get().body();
        var responseJson = getJsonFromResponse(playlist);
        var tracks = responseJson.getJSONArray("items");

        return extractTracksJson(tracks);
    }

    private static List<Track> extractTracksJson(JSONArray tracks) {
        return IntStream.range(0, tracks.length())
                .mapToObj(i -> tracks.getJSONObject(i))
                .map(m -> m.getJSONObject("track"))
                .map(t -> Track.builder()
                        .name(t.getString("name"))
                        .artist(t.getJSONArray("artists").getJSONObject(0).getString("name"))
                        .album(t.getJSONObject("album").getString("name")).build())
                .collect(Collectors.toList());
    }

    private String playlists(int userId, int offset) {
        return Constants.PLAYLISTS_URL(userId, offset);
    }

    private String playlistDetails(String playlistId) {
        return "https://api.spotify.com/v1/playlists/" + playlistId;
    }

    private String savedTracksRequest(int offset) {
        return Constants.SAVED_TRACKS_URL(offset);
    }

    @NotNull
    private static JSONObject getJsonFromResponse(String response) {
        var responseJson = new JSONObject(response);
        if (responseJson.has("error"))
            throw new RuntimeException(responseJson.get("error").toString());
        return responseJson;
    }
}
