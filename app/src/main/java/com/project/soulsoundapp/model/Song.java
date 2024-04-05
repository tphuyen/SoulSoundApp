package com.project.soulsoundapp.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Song implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("duration")
    private int duration;

    @SerializedName("song_artist_names")
    private String artist;

    @SerializedName("likes")
    private int likes;

    @SerializedName("song_url")
    private String songUrl;

    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("lyric_url")
    private String lyricUrl;

    public Song(String id, String title, int duration, String artist, int likes, String songUrl, String thumbnailUrl, String coverUrl, String lyricUrl) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.likes = likes;
        this.songUrl = songUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.coverUrl = coverUrl;
        this.lyricUrl = lyricUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getLyricUrl() {
        return lyricUrl;
    }

    public void setLyricUrl(String lyricUrl) {
        this.lyricUrl = lyricUrl;
    }
  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return id.equals(song.id);
    }
}
