package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.SongAdapter;
import com.project.soulsoundapp.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageButton ibReturn;
    private ImageView ivPlaylistImage;
    private RecyclerView rvSongsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        addControl();
        addEvent();
    }


    public void addControl() {
//        Declared component in xml file <activity_playlist>
        tvTitle = findViewById(R.id.tvTitle);
        ibReturn = findViewById(R.id.ibReturn);
        ivPlaylistImage = findViewById(R.id.ivPlaylistImage);
        rvSongsList = findViewById(R.id.rvSongsList);
//        Init adapter & setAdapter for recycler view
        SongAdapter songAdapter = new SongAdapter(this);
        songAdapter.setSongs(getListSongs());
//        Init layout manager & setLayoutManager for recycler
        LinearLayoutManager managerSongs = new LinearLayoutManager(this);
        rvSongsList.setLayoutManager(managerSongs);
        rvSongsList.setAdapter(songAdapter);
//        Function to chagne view
        updateIntentData();
    }

    public void addEvent() {
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void updateIntentData() {
        tvTitle.setText(getIntent().getStringExtra("title"));
//        ivPlaylistImage.setImageResource(getIntent().getIntExtra("image", 0));
        Picasso.get().load("https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z6600ZCOx96.jpg").into(ivPlaylistImage);
    }

    private List<Song> getListSongs() {
        List<Song> songs = new ArrayList<Song>();
        songs.add(new Song(1, R.drawable.ic_launcher_background, "Không sao mà, Em đây rồi", null));
        songs.add(new Song(2, R.drawable.ic_launcher_background, "Ngày đầu tiên", null));
        songs.add(new Song(3, R.drawable.ic_launcher_background, "Yêu người có ước mơ", null));
        songs.add(new Song(4, R.drawable.ic_launcher_background, "Từng là của nhau", null));
        songs.add(new Song(5, R.drawable.ic_launcher_background, "Đưa nhau đi trốn", null));
        songs.add(new Song(6, R.drawable.ic_launcher_background, "Không sao mà, Em đây rồi", null));
        songs.add(new Song(7, R.drawable.ic_launcher_background, "Ngày đầu tiên", null));
        songs.add(new Song(8, R.drawable.ic_launcher_background, "Yêu người có ước mơ", null));
        songs.add(new Song(9, R.drawable.ic_launcher_background, "Từng là của nhau", null));
        songs.add(new Song(10, R.drawable.ic_launcher_background, "Đưa nhau đi trốn", null));
        return songs;
    }
}