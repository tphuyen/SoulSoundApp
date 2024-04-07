package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;
import com.project.soulsoundapp.service.MediaPlayerService;
//import com.project.soulsoundapp.utils.CommentManager;
import com.squareup.picasso.Picasso;
import com.project.soulsoundapp.adapter.CommentAdapter;
import com.project.soulsoundapp.model.Comment;
import com.project.soulsoundapp.utils.LyricManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayMusicActivity extends AppCompatActivity {
//    VIEW OF COMMENT DIALOG
    RecyclerView rvComments;
    TextView tvNoComments;
    ImageButton ibSend;

    EditText etComment;

    ImageView ivBackground, ivSongCover;
    TextView tvStartTime, tvEndTime, tvTitle, tvArtist, tvLyrics;
    ImageButton ibBack, ibPlayPause, ibNext, ibPrevious, ibFavorite, ibShuffle, ibMenu, ivClose;
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

    private List<Comment> mListComments;
    private LyricManager lyricManager;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "PlayMusicActivity";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        mListComments = new ArrayList<>();
        mediaPlayerService = MediaPlayerService.getInstance(getApplicationContext());
        song = (Song) getIntent().getSerializableExtra("song");
        mediaPlayerService.playSong(mediaPlayerService.getCurrentPlaylist().indexOf(song));
        mProgressDialog = new ProgressDialog(this);
        db = DatabaseHelper.getInstance(getApplicationContext());
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
        btnLyrics = findViewById(R.id.btnLyrics);

//        commentManager = CommentManager.getInstance(getApplicationContext());
    }

    private void addEvents() {
        btnLyrics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnLyrics.setVisibility(View.GONE);
                setupLyricsBtn();
            }
        });
        setupBackBtn();
        setupSeekBar();
        togglePlayPauseBtn();
        setupNextBtn();
        setupPreviousBtn();
        setupFavoriteBtn();
        setupShuffleBtn();
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
                        getCommentAPI(song.getId());
                    }
                });

            }
        });
    }
//    BEGIN HANDLE COMMENT DIALOG
    private void showCommentDialog() {
//        Init dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comments);

//        Add Controls
        rvComments = dialog.findViewById(R.id.rvComments);
        ivClose = dialog.findViewById(R.id.ivClose);
        tvNoComments = dialog.findViewById(R.id.tvNoComments);
        ibSend = dialog.findViewById(R.id.ibSend);
        etComment = dialog.findViewById(R.id.etComment);

        if(mListComments.size() > 0) {
            CommentAdapter commentAdapter = new CommentAdapter(this);
            commentAdapter.setComments(mListComments);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvComments.setLayoutManager(linearLayoutManager);
            rvComments.setAdapter(commentAdapter);
            rvComments.post(() -> {rvComments.smoothScrollToPosition(mListComments.size() - 1);});
            tvNoComments.setVisibility(View.GONE);
            rvComments.setVisibility(View.VISIBLE);
        } else {
            tvNoComments.setVisibility(View.VISIBLE);
            rvComments.setVisibility(View.GONE);
        }

//        Add Events
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etComment.getText().toString().trim();

                if(content.length() > 0) {
                    Comment comment = new Comment(song.getId(), "thangvb.dev@gmail.com", content);
                    sendCommentAPI(comment);
                }


            }
        });

//        Config and show the layout comments
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.DialogAnimation;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    private void sendCommentAPI(Comment comment) {
        ApiService.apiService.sendCommentByIdSong(song.getId(), comment)
                .enqueue(new Callback<ApiService.ApiResponse<List<Comment>>>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse<List<Comment>>> call, Response<ApiService.ApiResponse<List<Comment>>> response) {
                        mListComments = response.body().getData();
                        if(mListComments.size() > 0) {
                            CommentAdapter commentAdapter = new CommentAdapter(getApplicationContext());
                            commentAdapter.setComments(mListComments);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvComments.setLayoutManager(linearLayoutManager);
                            rvComments.setAdapter(commentAdapter);
                            etComment.setText("");
                            etComment.clearFocus();
                            rvComments.post(() -> {rvComments.smoothScrollToPosition(mListComments.size() - 1);});
                            InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                        }
                        Log.d(TAG, "Post Comment Successfully");
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse<List<Comment>>> call, Throwable throwable) {
                        Log.d(TAG, "Post Comment Failed: " + throwable.getMessage());
                    }
                });
    }

    private void getCommentAPI(String id) {
        mProgressDialog.show();
        ApiService.apiService.getCommentsBySongId(id)
                .enqueue(new Callback<ApiService.ApiResponse<List<Comment>>>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse<List<Comment>>> call, Response<ApiService.ApiResponse<List<Comment>>> response) {
                        mListComments = response.body().getData();
                        mProgressDialog.dismiss();
                        showCommentDialog();
                        Log.d(TAG, "[Comment::" + song.getId() + "] Called API successfully");
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse<List<Comment>>> call, Throwable throwable) {
                        mProgressDialog.dismiss();
                        Log.e(TAG, "[Comment] Called API DB failed : " + throwable.getMessage());
                    }
                });
    }

//    END HANDLE COMMENT DIALOG


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
            if (db.isFavoriteExists("mail", song.getId())) {
                db.removeFavorite("mail", song.getId());
                ibFavorite.setImageResource(FAVORITE_ICON);
            } else {
                db.addFavorite("mail", song.getId());
                ibFavorite.setImageResource(FAVORITE_FILLED_ICON);
            }
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_lyrics);

//        Add controls
        ImageButton ibCloseLyric = dialog.findViewById(R.id.ibCloseLyric);
        TextView tvLyric = dialog.findViewById(R.id.tvLyric);

//        Add events
        ibCloseLyric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                btnLyrics.setVisibility(View.VISIBLE);
            }

        });

//        Set lyric
        lyricManager = new LyricManager(getApplicationContext(), tvLyric, mediaPlayerService);
        lyricManager.loadLyric(song.getLyricUrl());
//        Display lyrics
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.windowAnimations = R.style.DialogAnimation;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(params);
        dialog.show();
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

    private void updateFavoriteButton() {
        if (db.isFavoriteExists("userId", song.getId())) {
            ibFavorite.setImageResource(FAVORITE_FILLED_ICON);
        } else {
            ibFavorite.setImageResource(FAVORITE_ICON);
        }
    }
}