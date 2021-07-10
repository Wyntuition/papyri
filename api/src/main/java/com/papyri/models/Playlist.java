package com.papyri.models;

import lombok.Builder;

import java.util.List;

@Builder
public class Playlist {
    public String name;
    public List<Track> tracks;

    @Override
    public String toString() {
        return String.format("%s\n", name);
    }
}
