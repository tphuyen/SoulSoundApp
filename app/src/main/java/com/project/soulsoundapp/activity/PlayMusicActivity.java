package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.CommentAdapter;
import com.project.soulsoundapp.model.Comment;
import com.project.soulsoundapp.utils.LyricManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayMusicActivity extends AppCompatActivity {
    private List<Comment> comments;
    private Button btnLyrics;
    private LyricManager lyricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        addControls();
        addEvents();
        setupMenuButton();
        setupLyricsButton();
        handleBackButton();
    }

    private void addEvents() {
//        tvComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog();
//            }
//        });
    }

    private void addControls() {
//        tvComment = findViewById(R.id.tvComment);
    }

    private void setupMenuButton() {
        ImageButton ibMenu = findViewById(R.id.ibMenu);
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

    private void setupLyricsButton() {
        btnLyrics = findViewById(R.id.btnLyrics);
        btnLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PlayMusicActivity.this, R.style.TransparentDialog);
                View view = getLayoutInflater().inflate(R.layout.dialog_lyrics, null);
                TextView tvLyrics = view.findViewById(R.id.tvLyrics);
                Button btnCloseLyrics = view.findViewById(R.id.btnCloseLyrics);

                lyricManager = new LyricManager(PlayMusicActivity.this, tvLyrics);

                lyricManager.setLyricLoadListener(new LyricManager.LyricLoadListener() {
                    @Override
                    public void onLyricsLoaded(String[] lyrics) {
                        String lyricsText = String.join("\n", lyrics);
                        tvLyrics.setText(lyricsText);
                    }
                });

                lyricManager.loadLyricsFromUrl("https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7UUAFUF.lrc");

                btnCloseLyrics.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                // Hiển thị dialog
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
            }
        });
    }

    private void handleBackButton() {
        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}