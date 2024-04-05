package com.project.soulsoundapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.MediaPlayerService;
import com.squareup.picasso.Picasso;

public class MiniPlayerFragment extends Fragment {
    private ImageView ivMiniPlayerCover;
    private TextView tvMiniPlayerSongName, tvMiniPlayerArtistName;
    private ImageButton ibMiniPlayerPrevious, ibMiniPlayerPlayPause, ibMiniPlayerNext;
    private static MediaPlayerService mediaPlayerService;
    private static MiniPlayerFragment instance;

    public MiniPlayerFragment() {
        instance = this;
        mediaPlayerService = MediaPlayerService.getInstance();
    }

    public static MiniPlayerFragment getInstance() {
        if (instance == null) {
            instance = new MiniPlayerFragment();
        }
        return instance;
    }

    public static MiniPlayerFragment newInstance() {
        return new MiniPlayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_player, container, false);

        mediaPlayerService = MediaPlayerService.getInstance();

        addControl(view);
        addEvent();

        return view;
    }

    private void addControl(View view) {
        ivMiniPlayerCover = view.findViewById(R.id.ivMiniPlayerCover);
        tvMiniPlayerSongName = view.findViewById(R.id.tvMiniPlayerSongName);
        tvMiniPlayerArtistName = view.findViewById(R.id.tvMiniPlayerArtistName);
        ibMiniPlayerPrevious = view.findViewById(R.id.ibMiniPlayerPrevious);
        ibMiniPlayerPlayPause = view.findViewById(R.id.ibMiniPlayerPlayPause);
        ibMiniPlayerNext = view.findViewById(R.id.ibMiniPlayerNext);
    }

    private void addEvent() {
        ibMiniPlayerPrevious.setOnClickListener(v -> {
            mediaPlayerService.previousSong();
            updateSongInfo();
        });

        ibMiniPlayerPlayPause.setOnClickListener(v -> {
            if (mediaPlayerService.isPlaying()) {
                mediaPlayerService.pauseSong();
                ibMiniPlayerPlayPause.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayerService.resumeSong();
                ibMiniPlayerPlayPause.setImageResource(R.drawable.ic_pause);
            }
        });

        ibMiniPlayerNext.setOnClickListener(v -> {
            mediaPlayerService.nextSong();
            updateSongInfo();
        });
    }

    public void showMiniPlayer() {
        updateSongInfo();
        if (getView() != null) {
            getView().setVisibility(View.VISIBLE);
        }
    }

    public void updateSongInfo() {
        if (mediaPlayerService != null) {
            Song currentSong = mediaPlayerService.getCurrentSong();
            if (currentSong != null) {
                tvMiniPlayerSongName.setText(currentSong.getTitle());
                tvMiniPlayerArtistName.setText(currentSong.getArtist());
                Picasso.get().load(currentSong.getThumbnailUrl()).into(ivMiniPlayerCover);
                ibMiniPlayerPlayPause.setImageResource(mediaPlayerService.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play);
            }
        }
    }
}