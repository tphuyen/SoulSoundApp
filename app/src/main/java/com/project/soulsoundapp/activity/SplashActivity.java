package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    DatabaseHelper db;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        db = DatabaseHelper.getInstance(this.getApplication());
        ApiService.apiService.getAllSongs().enqueue(new Callback<ApiService.MultupleResource>() {
            @Override
            public void onResponse(Call<ApiService.MultupleResource> call, Response<ApiService.MultupleResource> response) {
                ApiService.MultupleResource resource = response.body();
                List<Song> songs = resource.getSongs();
                for (Song song : songs) {
                    db.addSong(song);
                }
                Toast.makeText(getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiService.MultupleResource> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}