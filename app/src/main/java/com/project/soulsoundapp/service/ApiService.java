package com.project.soulsoundapp.service;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.project.soulsoundapp.model.Album;
import com.project.soulsoundapp.model.Comment;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    class ApiResponse<T> {
        private final String message;
        private final int status;
        @SerializedName("metadata")
        private final T data;

        public ApiResponse(String message, int status, T data) {
            this.message = message;
            this.status = status;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }
    }
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
//    http://146.190.90.159:8000/api/v2/songs
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://146.190.90.159:8000/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

//    Routes
    @GET("users")
    Call<ApiResponse<List<User>>> getAllUsers();

    @POST("users/login")
    Call<ApiResponse<String>> loginApi(@Body String username, @Body String password);

    @POST("users/register")
    Call<ApiResponse<User>> registerApi(@Body User user, @Body String password);
    @GET("users/{email}/favorite")
    Call<ApiResponse<Playlist>> getFavoriteApi(@Path("email") String email);

    @GET("songs")
    Call<ApiResponse<List<Song>>> getAllSongs();
    @GET("songs/{id}/comments")
    Call<ApiResponse<List<Comment>>> getCommentsBySongId(@Path("id") String id);

    @POST("songs/{id}/comments")
    Call<ApiResponse<List<Comment>>> sendCommentByIdSong(@Path("id") String id, @Body Comment comment);

    @GET("playlists")
    Call<ApiResponse<List<Playlist>>> getAllPlaylists();

    @GET("albums")
    Call<ApiResponse<List<Album>>> getAllAlbums();

}
