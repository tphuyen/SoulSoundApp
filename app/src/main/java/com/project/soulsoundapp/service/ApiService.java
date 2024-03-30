package com.project.soulsoundapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {

    class MultupleResource {
        String message;
        int status;
        @SerializedName("metadata")
        List<Song> songs;

        public MultupleResource(String message, int status, List<Song> songs) {
            this.message = message;
            this.status = status;
            this.songs = songs;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<Song> getSongs() {
            return songs;
        }

        public void setSongs(List<Song> songs) {
            this.songs = songs;
        }
    }

    class PlaylistResponse {
        String message;
        int status;
        @SerializedName("metadata")
        List<Playlist> playlists;

        public PlaylistResponse(String message, int status, List<Playlist> playlists) {
            this.message = message;
            this.status = status;
            this.playlists = playlists;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<Playlist> getPlaylists() {
            return playlists;
        }

        public void setPlaylists(List<Playlist> playlists) {
            this.playlists = playlists;
        }
    }
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
//    http://146.190.90.159:8000/api/v2/songs
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://146.190.90.159:8000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("/api/v2/songs")
    Call<MultupleResource> getAllSongs();

    @GET("/api/v2/playlists")
    Call<PlaylistResponse> getAllPlaylists();
}
