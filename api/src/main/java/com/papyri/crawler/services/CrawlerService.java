package com.papyri.crawler.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.papyri.AuthApiRequest;
import com.papyri.models.Playlist;
import com.papyri.models.Track;
import org.json.JSONObject;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CrawlerService {

    @Inject
    private AuthApiRequest authApiRequest;

    public List<Object> getPlaylists(String token, int userId) throws InterruptedException, ExecutionException, URISyntaxException {
        var playlist = authApiRequest.get(playlists(userId, 0), token).get().body();
        //var playlist = authApiRequest.get(playlistTracks("7IqhCGwlLdzzoRSA6zXval"), token).get().body();;

        var resultObject = new JSONObject(playlist);
        //todo will return invalid access token if invalid, then look like no items
        var trks = resultObject.getJSONArray("items");

        List<Object> playlists = new ArrayList<>();
        for (int i = 0; i < trks.length(); i++) {
            var name = trks.getJSONObject(i).getString("name");
            playlists.add(Playlist.builder().name(name).build());
        }
        return playlists;
    }


    public List<Object> getSavedTracks(String token) throws InterruptedException, ExecutionException, URISyntaxException {
        var playlist = authApiRequest.get(savedTracks(0), token).get().body();

        var resultObject = new JSONObject(playlist);
        //todo will return invalid access token if invalid, then look like no items
        var trks = resultObject.getJSONArray("items");

        ObjectMapper mapper = new ObjectMapper();
        List<Object> deserializedTracks = new ArrayList<>();
        for (int i = 0; i < trks.length(); i++) {
            var trackObject = trks.getJSONObject(i).getJSONObject("track");
            deserializedTracks.add(Track.builder()
                    .name(trackObject.getString("name"))
                    .artist(trackObject.getJSONArray("artists").getJSONObject(0).getString("name"))
                    .album(trackObject.getJSONObject("album").getString("name"))
                    .build()
            );
            //deserializedTracks.add(mapper.readValue(trackObject.toString(), Track.class));
            //deserializedTracks.add((Track) trackObject));
        }

//        var tracks = json.getJSONArray("tracks").toList().stream()
//                .map(val -> val.toString().startsWith("A"))
//                .collect(Collectors.toList());
        return deserializedTracks;
    }

    private String playlists(int userId, int offset) {
        return "https://api.spotify.com/v1/users/" + userId + "/playlists?limit=50&offset=" + offset;
    }

    private String playlistDetails(String playlistId) {
        return "https://api.spotify.com/v1/playlists/" + playlistId;
    }

    private String savedTracks(int offset) {
        return "https://api.spotify.com/v1/me/tracks?limit=50&offset=" + offset;
    }

    private String playlistTracks(String playlistId) {
        return "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
    }
}
