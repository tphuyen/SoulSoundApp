package com.project.soulsoundapp.service;

import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;

import java.util.List;
import java.util.Random;

public class MediaPlayerService extends MediaBrowserService {
    public static DatabaseHelper db;
    public static List<Song> songs;
    private static MediaPlayer player;
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private static MediaPlayerService instance;

    public MediaPlayerService() {
        instance = this;
        db = DatabaseHelper.getInstance(this);
        songs = initSongs();
    }

    public static MediaPlayerService getInstance() {
        return instance;
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    private static List<Song> initSongs() {
        return db.getAllSongs();
    }

    public static List<Song> getCurrentPlaylist() {
        return songs;
    }

    public Song getCurrentSong() {
        return songs.get(currentSongIndex);
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public void playSong(int index) {
        if (player == null) player = new MediaPlayer();
        else player.reset();

        try {
            player.setDataSource(songs.get(index).getSongUrl());
            player.prepare();
            player.start();
            currentSongIndex = index;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playRandomSong() {
        playSong(new Random().nextInt(songs.size()));
    }

    public void pauseSong() {
        if (player != null && player.isPlaying()) player.pause();
    }

    public void resumeSong() {
        if (player != null && !player.isPlaying()) player.start();
    }

    public void nextSong() {
        if (currentSongIndex < songs.size() - 1) playSong(currentSongIndex + 1);
    }

    public void previousSong() {
        if (currentSongIndex > 0) playSong(currentSongIndex - 1);
    }

    public void seekTo(int position) {
        if (player != null) player.seekTo(position);
    }

    public int getCurrentPosition() {
        return player != null ? player.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return player != null ? player.getDuration() : 0;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

}
