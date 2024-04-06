package com.project.soulsoundapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Album implements Serializable {
    @SerializedName("album_id")
    private String albumId;

    @SerializedName("album_title")
    private String albumTitle;

    @SerializedName("album_description")
    private String albumDescription;

    @SerializedName("album_thumbnail")
    private String albumThumbnail;

    @SerializedName("album_cover")
    private String albumCover;

    @SerializedName("album_songs")
    private List<String> albumSongs;

}
