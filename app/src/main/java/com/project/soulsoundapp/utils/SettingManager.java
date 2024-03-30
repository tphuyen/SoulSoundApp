package com.project.soulsoundapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingManager {
    private static SettingManager instance;
    private Context context;
    DatabaseHelper db = DatabaseHelper.getInstance(context);

    private SettingManager(Context context) {
        this.context = context;
    }

    public static SettingManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingManager.class) {
                if (instance == null) {
                    instance = new SettingManager(context);
                }
            }
        }
        return instance;
    }

    public void setDatabase() {
        ApiService.apiService.getAllSongs().enqueue(new Callback<ApiService.MultupleResource>() {
            @Override
            public void onResponse(Call<ApiService.MultupleResource> call, Response<ApiService.MultupleResource> response) {
                ApiService.MultupleResource resource = response.body();
                List<Song> songs = new ArrayList<>(resource.getSongs());
                for (Song song : songs) {
//                    db.insertSong(song);
                }
                Toast.makeText(context.getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiService.MultupleResource> call, Throwable throwable) {
                Toast.makeText(context.getApplicationContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
