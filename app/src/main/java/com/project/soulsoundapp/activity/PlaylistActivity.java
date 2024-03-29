package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        songs.add(new Song(1, "Thủy Triều", 378626.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7U00WDE.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7U00WDEx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7U00WDEx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7U00WDE.mp3", "Quang Hùng MasterD"));
        songs.add(new Song(2, "Sau Lời Từ Khước", 291469.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7UUAFUF.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7UUAFUFx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7UUAFUFx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7UUAFUF.mp3", "Phan Mạnh Quỳnh"));
        songs.add(new Song(3, "Thiên Lý Ơi", 136216.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7I6BCCO.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7I6BCCOx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7I6BCCOx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7I6BCCO.mp3", "Jack - J97"));
        songs.add(new Song(4, "Ăn Trông Nồi Ngồi Trông Hướng", 118621.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7IBO90D.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7IBO90Dx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7IBO90Dx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7IBO90D.mp3", "Drum7"));
        songs.add(new Song(5, "Cắt Đôi Nỗi Sầu", 1676676.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z6FWCOO0.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z6FWCOO0x96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z6FWCOO0x240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z6FWCOO0.mp3", "Drum7"));
        return songs;
    }
}