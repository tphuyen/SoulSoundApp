package com.project.soulsoundapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlayMusicActivity;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.MediaPlayerService;
import com.squareup.picasso.Picasso;

public class MiniPlayerFragment extends Fragment {
    private static final String TAG = "MiniPlayerFragment";
    private ImageView ivMiniPlayerCover;
    private TextView tvMiniPlayerSongName, tvMiniPlayerArtistName;
    private ImageButton ibMiniPlayerPrevious, ibMiniPlayerPlayPause, ibMiniPlayerNext;
    private static MediaPlayerService mediaPlayerService;
    private static MiniPlayerFragment instance;
    private ViewGroup layout_mini_player_container;
    private SharedPreferences prefs;
    private Song song;
    private static final String SHARED_PREF_NAME = "player_music";
    private static final String KEY_IS_PLAYING = "isPlaying";

//    public MiniPlayerFragment(Context context) {
//        instance = this;
//        mediaPlayerService = MediaPlayerService.getInstance(context.getApplicationContext());
//        prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//    }
//
//    public static MiniPlayerFragment getInstance(Context context) {
//        if (instance == null) {
//            instance = new MiniPlayerFragment(context);
//        }
//        return instance;
//    }

    public static MiniPlayerFragment getInstance(Context context) {
        if (instance == null) {
            instance = new MiniPlayerFragment();
            mediaPlayerService = MediaPlayerService.getInstance(context.getApplicationContext());
            instance.prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        song = MediaPlayerService.getCurrentSong();
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        addEvent();
        setMiniPlayer();
    }

    private void addControl(View view) {
        ivMiniPlayerCover = view.findViewById(R.id.ivMiniPlayerCover);
        tvMiniPlayerSongName = view.findViewById(R.id.tvMiniPlayerSongName);
        tvMiniPlayerArtistName = view.findViewById(R.id.tvMiniPlayerArtistName);
        ibMiniPlayerPrevious = view.findViewById(R.id.ibMiniPlayerPrevious);
        ibMiniPlayerPlayPause = view.findViewById(R.id.ibMiniPlayerPlayPause);
        ibMiniPlayerNext = view.findViewById(R.id.ibMiniPlayerNext);
        layout_mini_player_container = view.findViewById(R.id.layout_mini_player_container);
    }

    private void addEvent() {

        layout_mini_player_container.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PlayMusicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", song);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        ibMiniPlayerPrevious.setOnClickListener(v -> {
            MediaPlayerService.previousSong();
            setMiniPlayer();
        });

        ibMiniPlayerPlayPause.setOnClickListener(v -> {
            if (MediaPlayerService.isPlaying()) {
                MediaPlayerService.pauseSong();
            } else {
                MediaPlayerService.resumeSong();
            }
            setMiniPlayer();
        });

        ibMiniPlayerNext.setOnClickListener(v -> {
            MediaPlayerService.nextSong();
            setMiniPlayer();
        });

        tvMiniPlayerSongName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setMiniPlayer() {
        song = MediaPlayerService.getSongSaved();

        if(song != null) {
            Log.d(TAG, "Song currently playing :: " + song.getTitle());
            layout_mini_player_container.setVisibility(View.VISIBLE);
            updateMiniPlayer(song);
        } else {
            layout_mini_player_container.setVisibility(View.GONE);
        }
    }

    public void updateMiniPlayer(Song song) {
        Picasso.get().load(song.getThumbnailUrl()).into(ivMiniPlayerCover);
        tvMiniPlayerSongName.setText(song.getTitle());
        tvMiniPlayerArtistName.setText(song.getArtist());
        ibMiniPlayerPlayPause.setBackgroundResource(MediaPlayerService.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play);
    }
    @Override
    public void onResume() {
        super.onResume();
        setMiniPlayer(); // Cập nhật lại mini player khi fragment được hoạt động lại
    }

}