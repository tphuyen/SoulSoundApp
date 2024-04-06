package com.project.soulsoundapp.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Category;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.model.User;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {
    private static final String TAG = "DataManager";
    private static DataManager instance;
    private DatabaseHelper db;
    private static List<User> mListUsers;
    private static List<Song> mListSongs;
    private static List<Playlist> mListPlaylists;
    private static List<Category> mListCategories;
    private DataManager(Context context) {
        db = DatabaseHelper.getInstance(context);
        mListUsers = new ArrayList<>();
        mListSongs = new ArrayList<>();
        mListPlaylists = new ArrayList<>();
        mListCategories = new ArrayList<>();
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    public void setDB() {
        setUsersDB();
        setSongsDB();
        setPlaylistsDB();
        setCategoriesDB();
    }
    private void setUsersDB() {
        ApiService.apiService.getAllUsers().enqueue(new Callback<ApiService.ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiService.ApiResponse<List<User>>> call, Response<ApiService.ApiResponse<List<User>>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    mListUsers = response.body().getData();

                    if(mListUsers.size() > 0) {
                        for (User user : mListUsers) {
                            db.addUser(user);
                        }
                    }
                    Log.d(TAG, "[User] Called API & Insert DB successfully");
                }
            }

            @Override
            public void onFailure(Call<ApiService.ApiResponse<List<User>>> call, Throwable throwable) {
                Log.e(TAG, "[User] Called API & Insert DB failed : " + throwable.getMessage());
            }
        });
    }

    private void setSongsDB() {
        ApiService.apiService.getAllSongs().enqueue(new Callback<ApiService.ApiResponse<List<Song>>>() {

            @Override
            public void onResponse(Call<ApiService.ApiResponse<List<Song>>> call, Response<ApiService.ApiResponse<List<Song>>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    mListSongs = response.body().getData();

                    if(mListSongs.size() > 0) {
                        for (Song song : mListSongs) {
                            db.addSong(song);
                        }
                    }
                    Log.d(TAG, "[Song] Called API & Insert DB successfully");
                }
            }

            @Override
            public void onFailure(Call<ApiService.ApiResponse<List<Song>>> call, Throwable throwable) {
                Log.e(TAG, "[Song] Called API & Insert DB failed : " + throwable.getMessage());
            }
        });
    }

    private void setPlaylistsDB() {
        ApiService.apiService.getAllPlaylists().enqueue(new Callback<ApiService.ApiResponse<List<Playlist>>>() {
            @Override
            public void onResponse(Call<ApiService.ApiResponse<List<Playlist>>> call, Response<ApiService.ApiResponse<List<Playlist>>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    mListPlaylists = response.body().getData();

                    if(mListPlaylists.size() > 0) {
                        for (Playlist playlist : mListPlaylists) {
                            db.addPlaylist(playlist);
                        }
                    }
                    Log.d(TAG, "[Playlist] Called API & Insert DB successfully");
                }
            }

            @Override
            public void onFailure(Call<ApiService.ApiResponse<List<Playlist>>> call, Throwable throwable) {
                Log.e(TAG, "[Playlist] Called API & Insert DB failed : " + throwable.getMessage());
            }
        });
    }

    private void setCategoriesDB() {
        ApiService.apiService.getAllCategories().enqueue(new Callback<ApiService.ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ApiService.ApiResponse<List<Category>>> call, Response<ApiService.ApiResponse<List<Category>>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    mListCategories = response.body().getData();

                    if(mListCategories.size() > 0) {
                        for (Category category : mListCategories) {
                            db.addCategory(category);
                        }
                    }
                    Log.d(TAG, "[Category] Called API & Insert DB successfully");
                }
            }

            @Override
            public void onFailure(Call<ApiService.ApiResponse<List<Category>>> call, Throwable throwable) {
                Log.e(TAG, "[Category] Called API & Insert DB failed : " + throwable.getMessage());
            }
        });
    }

    public void setFavorites(String email, List<String> favorites) {
        for (String songId : favorites) {
            db.addFavorite(email, songId);
        }
    }
}
