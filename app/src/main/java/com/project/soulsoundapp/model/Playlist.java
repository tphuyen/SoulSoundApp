package com.project.soulsoundapp.model;

import java.util.List;

public class Playlist {
    private String name;
    private int image;
    private int songCount;
    private List<Song> songs;

    public Playlist(String name, int image, int songCount) {
        this.name = name;
        this.image = image;
        this.songCount = songCount;
    }

    public Playlist(String name, int image, int songCount, List<Song> songs) {
        this.name = name;
        this.image = image;
        this.songCount = songCount;
        this.songs = songs;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
