package com.project.soulsoundapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("category_title")
    private String categoryTitle;
    @SerializedName("category_thumbnail")
    private String categoryThumbnail;
    @SerializedName("category_backcolor")
    private String categoryBackColor;
    @SerializedName("category_playlists")
    private List<String> categoryPlaylists;

    public Category(String categoryId, String categoryTitle, String categoryThumbnail, String categoryBackColor, List<String> categoryPlaylists) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryThumbnail = categoryThumbnail;
        this.categoryBackColor = categoryBackColor;
        this.categoryPlaylists = categoryPlaylists;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryThumbnail() {
        return categoryThumbnail;
    }

    public void setCategoryThumbnail(String categoryThumbnail) {
        this.categoryThumbnail = categoryThumbnail;
    }

    public String getCategoryBackColor() {
        return categoryBackColor;
    }

    public void setCategoryBackColor(String categoryBackColor) {
        this.categoryBackColor = categoryBackColor;
    }

    public List<String> getCategoryPlaylists() {
        return categoryPlaylists;
    }

    public void setCategoryPlaylists(List<String> categoryPlaylists) {
        this.categoryPlaylists = categoryPlaylists;
    }
}
