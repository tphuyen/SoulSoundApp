package com.project.soulsoundapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.soulsoundapp.activity.SignInActivity;
import com.project.soulsoundapp.fragment.MiniPlayerFragment;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.utils.DataManager;

import java.util.List;
import java.util.Random;

public class MediaPlayerService extends MediaBrowserService {
    private static final String TAG = "MediaPlayerService";
    private static MediaPlayerService instance;
    private DatabaseHelper db;
    private List<Song> songs;
    private MediaPlayer player;
    private int currentSongIndex = 0;
    private MiniPlayerFragment miniPlayerFragment;
    private boolean isShuffle = false;

//    Declare Shared Preferences
    private SharedPreferences prefs;
    private static final String SHARED_PREF_NAME = "player_music";
    private static final String KEY_SONG_ID = "songId";
    private static final String KEY_SONG_INDEX = "songIndex";
    private static final String KEY_PLAYLIST_ID = "playlistId";
    private static final String KEY_CURRENT_TIME = "currentTime";
    private static final String KEY_IS_PLAYING = "isPlaying";

    private MediaPlayerService(Context context) {
        db = DatabaseHelper.getInstance(context);
        songs = initSongs();
        miniPlayerFragment = MiniPlayerFragment.getInstance(context);

        prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

    }

    public static synchronized MediaPlayerService getInstance(Context context) {
        if (instance == null) {
            instance = new MediaPlayerService(context.getApplicationContext());
        }
        return instance;
    }

    public void updateState() {
        currentSongIndex = prefs.getInt(KEY_SONG_INDEX, 0);
        int currentTime = prefs.getInt(KEY_CURRENT_TIME, 0);

        Log.d(TAG, "Current time : " + currentTime);

        try {
            if(player == null) {
                player = new MediaPlayer();
            }
            String songUrl = songs.get(currentSongIndex).getSongUrl();
            player.setDataSource(songUrl);
            player.prepare();
            player.seekTo(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    private List<Song> initSongs() {
        return db.getAllSongs();
    }

    public List<Song> getCurrentPlaylist() {
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
//            Set prefs
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_SONG_INDEX, currentSongIndex);
            editor.putString(KEY_SONG_ID, songs.get(index).getId());
            editor.putFloat(KEY_CURRENT_TIME, 0);
            editor.putBoolean(KEY_IS_PLAYING, true);
            editor.apply();

            miniPlayerFragment.setMiniPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playRandomSong() {
        playSong(new Random().nextInt(songs.size()));
    }

    public void pauseSong() {
        if (player != null && player.isPlaying()) {
            Log.v(TAG, "Pause in : " + player.getCurrentPosition());

            player.pause();
//            Set prefs
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_CURRENT_TIME, player.getCurrentPosition());
            editor.putBoolean(KEY_IS_PLAYING, false);
            editor.apply();

            miniPlayerFragment.setMiniPlayer();
        }
    }

    public void resumeSong() {
        if (player != null && !player.isPlaying()) {
            player.start();
            //            Set prefs
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_IS_PLAYING, true);
            editor.apply();

            miniPlayerFragment.setMiniPlayer();
        }

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
        return new BrowserRoot("media_root", null);
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
