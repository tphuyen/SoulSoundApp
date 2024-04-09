package com.project.soulsoundapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Playlist implements Serializable {
    @SerializedName("playlist_id")
    private String playlistId;

    @SerializedName("playlist_userId")
    private String playlistUserId;

    @SerializedName("playlist_title")
    private String playlistTitle;

    @SerializedName("playlist_description")
    private String playlistDescription;

    @SerializedName("playlist_thumbnail")
    private String playlistThumbnail;

    @SerializedName("playlist_cover")
    private String playlistCover;

    @SerializedName("playlist_songs")
    private List<String> playlistSongs;

    public Playlist(String playlistId, String playlistUserId, String playlistTitle, String playlistDescription, String playlistThumbnail, String playlistCover, List<String> playlistSongs) {
        this.playlistId = playlistId;
        this.playlistUserId = playlistUserId;
        this.playlistTitle = playlistTitle;
        this.playlistDescription = playlistDescription;
        this.playlistThumbnail = playlistThumbnail;
        this.playlistCover = playlistCover;
        this.playlistSongs = playlistSongs;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistUserId() {
        return playlistUserId;
    }

    public void setPlaylistUserId(String playlistUserId) {
        this.playlistUserId = playlistUserId;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public String getPlaylistThumbnail() {
        return playlistThumbnail;
    }

    public void setPlaylistThumbnail(String playlistThumbnail) {
        this.playlistThumbnail = playlistThumbnail;
    }

    public String getPlaylistCover() {
        return playlistCover;
    }

    public void setPlaylistCover(String playlistCover) {
        this.playlistCover = playlistCover;
    }

    public List<String> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<String> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public String toString() {
        return String.format(playlistTitle + " have " + playlistSongs.size() + " songs");
    }
}
