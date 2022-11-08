package com.papyri.crawler;

public class Constants {

    public static final String SAVED_TRACKS_URL(int offset) {
        return String.format("https://api.spotify.com/v1/me/tracks?limit=50&offset=%s", offset);
    }

    public static final String PLAYLIST_TRACKS_URL(String playlistId, int offset) {
      return String.format("https://api.spotify.com/v1/playlists/%s/tracks?offset=%s", playlistId, offset);
    }

    public static final String PLAYLISTS_URL(int userId, int offset) {
        return String.format("https://api.spotify.com/v1/users/%s/playlists?limit=50&offset=%s", userId, offset);
    }

}
