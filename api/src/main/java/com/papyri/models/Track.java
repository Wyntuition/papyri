package com.papyri.models;

import lombok.Builder;

@Builder
public class Track {
    public String name;
    public String album;
    public String artist;

    @Override
    public String toString() {
        return String.format("%s, %s (%s)\n",
                name, artist, album);
    }
}
