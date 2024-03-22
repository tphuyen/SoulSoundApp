package com.project.soulsoundapp.model;

import java.util.Date;

public class Album {
    private String id;
    private String title;
    private Date release_date;
    private String description;
    private int thumbnail;

    public Album(String id, String title, Date release_date, String description, int thumbnail) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.description = description;
        this.thumbnail = thumbnail;
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

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
