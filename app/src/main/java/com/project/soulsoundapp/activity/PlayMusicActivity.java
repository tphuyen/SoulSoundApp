package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.project.soulsoundapp.R;

import java.util.Objects;

public class PlayMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        setupMenuButton();
        setupLyricsButton();
        handleBackButton();
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
            }
        });
    }

    private void setupLyricsButton() {
        Button btnLyrics = findViewById(R.id.btnLyrics);
        btnLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PlayMusicActivity.this, R.style.TransparentDialog);
                View view = getLayoutInflater().inflate(R.layout.dialog_lyrics, null);
                TextView tvLyrics = view.findViewById(R.id.tvLyrics);
                Button btnCloseLyrics = view.findViewById(R.id.btnCloseLyrics);

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
                btnCloseLyrics.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

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