package com.project.soulsoundapp.model;

import java.io.Serializable;
import java.util.List;

public class Song implements Serializable {
    private int id;
    private int image;
    private String name;
    private List<Artist> artists;

    public Song(int id, int image, String name, List<Artist> artists) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.artists = artists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
