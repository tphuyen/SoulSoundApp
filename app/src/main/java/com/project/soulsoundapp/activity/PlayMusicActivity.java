package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.MediaPlayerService;
import com.squareup.picasso.Picasso;
import com.project.soulsoundapp.adapter.CommentAdapter;
import com.project.soulsoundapp.model.Comment;
import com.project.soulsoundapp.utils.LyricManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayMusicActivity extends AppCompatActivity {

    ImageView ivBackground, ivSongCover;
    TextView tvStartTime, tvEndTime, tvTitle, tvArtist, tvLyrics;
    ImageButton ibBack, ibPlayPause, ibNext, ibPrevious, ibFavorite, ibShuffle, ibMenu;
    Button btnLyrics, btnCloseLyrics;
    SeekBar sbSongProgress;
    BottomSheetDialog bottomSheetDialog;
    MediaPlayerService mediaPlayerService;
    Song song;
    private boolean isShuffle = false, isFavorite = false;
    private static final int PAUSE_ICON = R.drawable.ic_pause;
    private static final int PLAY_ICON = R.drawable.ic_play;
    private static final int FAVORITE_ICON = R.drawable.ic_favorite;
    private static final int FAVORITE_FILLED_ICON = R.drawable.ic_favorite_filled;
    private static final int SHUFFLE_ICON = R.drawable.ic_shuffle;
    private static final int SHUFFLE_FILLED_ICON = R.drawable.ic_shuffle_filled;

    private List<Comment> comments;
    private LyricManager lyricManager;
    private static final String TAG = "PlayMusicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        mediaPlayerService = new MediaPlayerService();
        song = (Song) getIntent().getSerializableExtra("song");
        mediaPlayerService.playSong(MediaPlayerService.getCurrentPlaylist().indexOf(song));

        addControls();
        addEvents();

        updateUI();

    }

    private void addControls() {
        ibBack = findViewById(R.id.ibBack);
        ivBackground = findViewById(R.id.ivBackground);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        ivSongCover = findViewById(R.id.ivSongCover);
        sbSongProgress = findViewById(R.id.sbSongProgress);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        ibPlayPause = findViewById(R.id.ibPlayPause);
        ibMenu = findViewById(R.id.ibMenu);
        ibShuffle = findViewById(R.id.ibShuffle);
    }

    private void addEvents() {
        setupBackBtn();
        setupSeekBar();
        togglePlayPauseBtn();
        setupNextBtn();
        setupPreviousBtn();
        setupFavoriteBtn();
        setupShuffleBtn();
        setupLyricsBtn();
        setupMenuBtn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayerService != null) {
            updateUI();
        }
    }

    private void updateUI() {
        updateSongInfo();
        updatePlayPauseButton();
        updateSongTime();
    }

    private void setupBackBtn() {
        ibBack.setOnClickListener(v -> onBackPressed());
    }

    public void setupSeekBar() {
        sbSongProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sbSongProgress, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayerService.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar sbSongProgress) {
                mediaPlayerService.pauseSong();
            }

            @Override
            public void onStopTrackingTouch(SeekBar sbSongProgress) {
                mediaPlayerService.resumeSong();
                updatePlayPauseButton();
            }
        });
    }




    private void setupMenuBtn() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(PlayMusicActivity.this, R.style.TransparentDialog);
                @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_menu, null);
                dialog.setContentView(view);
                Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.CENTER);
                dialog.getWindow().setDimAmount(0.4f);
                dialog.show();

                TextView tvComment = dialog.findViewById(R.id.tvComment);
                tvComment.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        showCommentDialog();
                    }
                });

            }
        });
    }


    private void showCommentDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comments);

//        Add Controls
        RecyclerView rvComments = dialog.findViewById(R.id.rvComments);
        ImageButton ivClose = dialog.findViewById(R.id.ivClose);

//        Add Events
        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);

        Log.d("CommentAdapter", rvComments.toString());
        CommentAdapter commentAdapter = new CommentAdapter(this);
        comments = new ArrayList<Comment>();
        comments.add(new Comment("1", "1", "Hay quá má ơi 01"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 02"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 03"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 04"));
        comments.add(new Comment("1", "1", "Hay quá má ơi 01"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 02"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 03"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 04"));
        comments.add(new Comment("1", "1", "Hay quá má ơi 01"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 02"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 03"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 04"));
        comments.add(new Comment("1", "1", "Hay quá má ơi 01"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 02"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 03"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 04"));
        comments.add(new Comment("1", "1", "Hay quá má ơi 01"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 02"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 03"));
        comments.add(new Comment("2", "2", "Chán quá má ơi 04"));

        commentAdapter.setComments(comments);

        rvComments.setAdapter(commentAdapter);

//        Config and show the layout comments
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.DialogAnimation;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public void togglePlayPauseBtn() {
        ibPlayPause.setOnClickListener(v -> {
            if (mediaPlayerService.isPlaying()) {
                mediaPlayerService.pauseSong();
            } else {
                mediaPlayerService.resumeSong();
            }
            updateUI();
        });
    }

    public void setupNextBtn() {
        ibNext = findViewById(R.id.ibNext);
        ibNext.setOnClickListener(v -> {
            if (isShuffle) {
                mediaPlayerService.playRandomSong();
            } else {
                mediaPlayerService.nextSong();
            }
            updateUI();
        });
    }

    public void setupPreviousBtn() {
        ibPrevious = findViewById(R.id.ibPrevious);
        ibPrevious.setOnClickListener(v -> {
            mediaPlayerService.previousSong();
            updateUI();
        });
    }

    private void setupFavoriteBtn() {
        ibFavorite = findViewById(R.id.ibFavorite);
        ibFavorite.setOnClickListener(v -> {
            if (!isFavorite) {
                ibFavorite.setImageResource(FAVORITE_FILLED_ICON);
            } else {
                ibFavorite.setImageResource(FAVORITE_ICON);
            }
            isFavorite = !isFavorite;
        });
    }

    public void setupShuffleBtn() {
        ibShuffle.setOnClickListener(v -> {
            isShuffle = !isShuffle;
            mediaPlayerService.setShuffle(!isShuffle);
            if (isShuffle) {
                ibShuffle.setImageResource(SHUFFLE_FILLED_ICON);
            } else {
                ibShuffle.setImageResource(SHUFFLE_ICON);
            }
        });
    }

    private void setupLyricsBtn() {
        btnLyrics = findViewById(R.id.btnLyrics);
        btnLyrics.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(PlayMusicActivity.this, R.style.TransparentDialog);
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_lyrics, null);
            tvLyrics = view.findViewById(R.id.tvLyrics);
            btnCloseLyrics = view.findViewById(R.id.btnCloseLyrics);

            // Your lyrics data...
            String[] lyrics = {
                    "This is the first line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    "This is the second line of the song",
                    // ... more lyrics ...
            };

            // Join the lyrics with a newline character
            String lyricsText = String.join("\n", lyrics);

            tvLyrics.setText(lyricsText);

            // Set the dialog to be cancelable
            bottomSheetDialog.setCancelable(true);

            // Set an OnClickListener for the close button
            btnCloseLyrics.setOnClickListener(v1 -> bottomSheetDialog.dismiss());

            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();
        });
    }

    private void updateSongInfo() {
        song = mediaPlayerService.getCurrentSong();
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());
        Picasso.get().load(song.getCoverUrl()).into(ivSongCover);
        Picasso.get().load(song.getCoverUrl()).into(ivBackground);
    }

    public void updatePlayPauseButton() {
        int icon = mediaPlayerService.isPlaying() ? PAUSE_ICON : PLAY_ICON;
        ibPlayPause.setBackgroundResource(icon);
    }

    private void updateSongTime() {
        int currentTime = mediaPlayerService.getCurrentPosition();
        int duration = mediaPlayerService.getDuration();

        if (currentTime >= duration) {
            mediaPlayerService.nextSong();
            updateSongInfo();
        } else {
            tvStartTime.setText(formatTime(currentTime));
            tvEndTime.setText(formatTime(duration));

            sbSongProgress.setMax(duration);
            sbSongProgress.setProgress(currentTime);

            new Handler().postDelayed(this::updateSongTime, 1000);
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int time) {
        int minutes = time / 60000;
        int seconds = (time % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }
}