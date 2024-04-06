package com.project.soulsoundapp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
    private String email;
    @SerializedName("name")
    private String fullName;
    @SerializedName("playlists")
    private List<String> favorites;
    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
        this.favorites = null;
    }

    public User(String email, String fullName, List<String> favorites) {
        this.email = email;
        this.fullName = fullName;
        this.favorites = favorites;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public String toString() {
        String ps = "";
        for (String p : getFavorites()) {
            ps = ps + p + " ";
        }
        return "User{" +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", playlists=" + ps +
                '}';
    }
}

