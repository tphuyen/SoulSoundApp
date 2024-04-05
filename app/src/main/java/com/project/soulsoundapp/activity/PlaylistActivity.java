package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.SongAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageButton ibReturn;
    private ImageView ivPlaylistImage;
    private RecyclerView rvSongsList;
    private List<Song> songs;
    private Playlist playlist;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        addControls();
        addEvents();
    }

    public void addControls() {
        playlist = (Playlist) getIntent().getSerializableExtra("playlist");
        db = DatabaseHelper.getInstance(getApplicationContext());
        songs = new ArrayList<>();

        tvTitle = findViewById(R.id.tvTitle);
        ibReturn = findViewById(R.id.ibReturn);
        ivPlaylistImage = findViewById(R.id.ivPlaylistImage);
        rvSongsList = findViewById(R.id.rvSongsList);

//        Set layout RecyclerView
        LinearLayoutManager managerSongs = new LinearLayoutManager(this);
        rvSongsList.setLayoutManager(managerSongs);
        getListSongs();
    }

    public void addEvents() {
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void updateIntentData() {
        tvTitle.setText(playlist.getPlaylistTitle());
        Picasso.get().load(playlist.getPlaylistCover()).into(ivPlaylistImage);
    }

    private void setSongsList(List<Song> songs) {
        SongAdapter songAdapter = new SongAdapter(this);
        songAdapter.setSongs(songs);
        rvSongsList.setAdapter(songAdapter);
    }

    private void getListSongs() {
        if (db != null) {
            songs = db.getSongByIds(playlist.getPlaylistSongs());
            setSongsList(songs);
            updateIntentData();
        } else {
            Toast.makeText(getApplicationContext(), "Database is not initialized", Toast.LENGTH_SHORT).show();
        }
    }
}
