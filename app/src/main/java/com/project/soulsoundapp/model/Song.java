package com.project.soulsoundapp.model;

import java.io.Serializable;
import java.util.List;

public class Song implements Serializable {
    private final int id;
    private final String title;
    private double likes;
    private final String lyricUrl;
    private final String thumbnailUrl;
    private final String coverUrl;
    private final String streamUrl;
    private final String artist;

    public Song(int id, String title, double likes, String lyricUrl, String thumbnailUrl, String coverUrl, String streamUrl, String artist) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.lyricUrl = lyricUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.coverUrl = coverUrl;
        this.streamUrl = streamUrl;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getLikes() {
        return likes;
    }

    public void setLikes(double likes) {
        this.likes = likes;
    }

    public String getLyricUrl() {
        return lyricUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return id == song.id;
    }
}
