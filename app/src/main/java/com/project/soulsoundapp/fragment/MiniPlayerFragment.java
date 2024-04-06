package com.project.soulsoundapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private ImageView ivMiniPlayerCover;
    private TextView tvMiniPlayerSongName, tvMiniPlayerArtistName;
    private ImageButton ibMiniPlayerPrevious, ibMiniPlayerPlayPause, ibMiniPlayerNext;
    private static MediaPlayerService mediaPlayerService;
    private static MiniPlayerFragment instance;
    private ViewGroup layout_mini_player_container;

    public MiniPlayerFragment(Context context) {
        instance = this;
        mediaPlayerService = MediaPlayerService.getInstance(context.getApplicationContext());
    }

    public static MiniPlayerFragment getInstance(Context context) {
        if (instance == null) {
            instance = new MiniPlayerFragment(context);
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_player, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mediaPlayerService = MediaPlayerService.getInstance(getContext());
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
        ibMiniPlayerPrevious.setOnClickListener(v -> {
            mediaPlayerService.previousSong();
            setMiniPlayer();
        });

        ibMiniPlayerPlayPause.setOnClickListener(v -> {
            if (mediaPlayerService.isPlaying()) {
                mediaPlayerService.pauseSong();
                ibMiniPlayerPlayPause.setBackgroundResource(R.drawable.ic_play);
            } else {
                mediaPlayerService.resumeSong();
                ibMiniPlayerPlayPause.setBackgroundResource(R.drawable.ic_pause);
            }
        });

        ibMiniPlayerNext.setOnClickListener(v -> {
            mediaPlayerService.nextSong();
            setMiniPlayer();
        });
    }

    public void setMiniPlayer() {
        if(mediaPlayerService.getCurrentSong() != null) {
            Song currentSong = mediaPlayerService.getCurrentSong();
            Picasso.get().load(currentSong.getThumbnailUrl()).into(ivMiniPlayerCover);
            tvMiniPlayerSongName.setText(currentSong.getTitle());
            tvMiniPlayerArtistName.setText(currentSong.getArtist());
            ibMiniPlayerPlayPause.setBackgroundResource(mediaPlayerService.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play);
        }
    }
}