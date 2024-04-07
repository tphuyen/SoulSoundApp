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

import com.project.soulsoundapp.activity.MainActivity;
import com.project.soulsoundapp.fragment.MiniPlayerFragment;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MediaPlayerService extends MediaBrowserService {
    private static final String TAG = "MediaPlayerService";
    private static MediaPlayerService instance;
    private static DatabaseHelper db;
    private static MediaPlayer player;
//    Declare variables
    private static int currentIndex = -1;
    private static List<Song> currentPlaylist;
//    Using update mini player
    private static MiniPlayerFragment miniPlayerFragment;

//    Declare Shared Preferences
    private static SharedPreferences prefs;
    private static final String SHARED_PREF_NAME = "player_music";
    private static final String KEY_SONG_ID = "songId";
    private static final String KEY_SONG_INDEX = "songIndex";
    private static final String KEY_PLAYLIST = "playlist";
    private static final String KEY_CURRENT_TIME = "currentTime";
    private static final String KEY_IS_PLAYING = "isPlaying";
    private static int SONG_STATE = 0;
//    0 = lần lượt
//    1 = ngẫu nhiên

    private MediaPlayerService(Context context) {
        db = DatabaseHelper.getInstance(context);
        miniPlayerFragment = MiniPlayerFragment.getInstance(context);
        currentPlaylist = new ArrayList<>();
        prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized MediaPlayerService getInstance(Context context) {
        if (instance == null) {
            instance = new MediaPlayerService(context.getApplicationContext());
        }
        return instance;
    }

    public static void setPlaylist(List<Song> songs, int index) {
        currentIndex = index;
        if(songs == null) {
            currentPlaylist = db.getAllSongs();
        } else {
            currentPlaylist = songs;
        }
        SharedPreferences.Editor editor = prefs.edit();
        StringBuilder stringBuilder = new StringBuilder();
//        Log
        String msg = "[ ";
        for(Song song : currentPlaylist) {
            msg += song.getTitle() + ", ";
            stringBuilder.append(song.getId()).append(",");
        }
        editor.putString(KEY_PLAYLIST, stringBuilder.toString());
        msg += " ]";
        editor.apply();
        Log.v(TAG, "Playlist : " + msg);
        Log.v(TAG, "Index : " + currentIndex);
    }
    public static void setPlaylist(List<Song> songs) {
        setPlaylist(songs, 0);
    }

    public static void setPlaylist() {
        setPlaylist(null, -1);
    }

    public static void setPlaylist(Song song) {
        List<Song> songs = new ArrayList<>();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PLAYLIST, null);
        editor.apply();
        songs.add(song);
        setPlaylist(songs, 0);
    }

    public static Song getSongSaved() {
        if(prefs.contains(KEY_IS_PLAYING)) {
            if(player == null) {
                player = new MediaPlayer();
                Log.d(TAG, "Status playing :: " + Boolean.toString(prefs.getBoolean(KEY_IS_PLAYING, true)));
                Log.d(TAG, "Index playing :: " + Integer.toString(prefs.getInt(KEY_SONG_INDEX, -1)));
                Log.d(TAG, "Index current time :: " + Integer.toString(prefs.getInt(KEY_CURRENT_TIME, 0)));
                Log.d(TAG, "Index playlist id :: " + prefs.getString(KEY_PLAYLIST, null));
                Log.d(TAG, "Index song id :: " + prefs.getString(KEY_SONG_ID, null));
                currentIndex = prefs.getInt(KEY_SONG_INDEX, 0);
                String playlistSaved = prefs.getString(KEY_PLAYLIST, null);
                if(playlistSaved == null) {
                    String saveSongId = prefs.getString(KEY_SONG_ID, null);
                    if(saveSongId != null)  {
                        List<String> setupPlaylist = new ArrayList<>();
                        setupPlaylist.add(saveSongId);
                        currentPlaylist = db.getSongByIds(setupPlaylist);
                    }
                } else {
                    String[] songStrings = playlistSaved.split(",");
                    List<String> songsFromPlaylistSaved = new ArrayList<>();
                    Collections.addAll(songsFromPlaylistSaved, songStrings);
                    currentPlaylist = db.getSongByIds(songsFromPlaylistSaved);
                }
                try {
                    player.setDataSource(currentPlaylist.get(currentIndex).getSongUrl());
                    player.prepare();
                    player.seekTo(prefs.getInt(KEY_CURRENT_TIME, 0));
                } catch (Exception e) {
                    Log.v(TAG, "Not init player::" + e.getMessage());
                }

            }
            return getCurrentSong();
        }
        return null;
    }

    public static void playSong() {
        if (player == null) player = new MediaPlayer();
        else player.reset();

        try {
            if (currentIndex != -1 && currentPlaylist.size() > 0) {
                player.setDataSource(currentPlaylist.get(currentIndex).getSongUrl());
                player.prepare();
                player.start();

//                Update prefs
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(KEY_SONG_INDEX, currentIndex);
                editor.putString(KEY_SONG_ID, currentPlaylist.get(currentIndex).getId());
                editor.putBoolean(KEY_IS_PLAYING, true);
                editor.apply();

//                Update mini player
                miniPlayerFragment.setMiniPlayer();
            } else {
                Log.v(TAG, "Không có bài hát nào trong playlist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pauseSong() {
        try {
            player.pause();

//            Update prefs
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_CURRENT_TIME, player.getCurrentPosition());
            editor.putBoolean(KEY_IS_PLAYING, false);
            editor.apply();

//            Update mini player
            miniPlayerFragment.setMiniPlayer();

//            Test Log
            Log.d(TAG, "Status playing :: " + Boolean.toString(prefs.getBoolean(KEY_IS_PLAYING, true)));
            Log.d(TAG, "Status playing :: " + Integer.toString(prefs.getInt(KEY_CURRENT_TIME, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resumeSong() {
        if (player != null && !player.isPlaying()) {
            player.start();

//            Set prefs
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_IS_PLAYING, true);
            editor.apply();

//            Update mini player
            miniPlayerFragment.setMiniPlayer();
        }

    }

    public static List<Song> getCurrentPlaylist() {
        return currentPlaylist;
    }

    public static Song getCurrentSong() {
        if(currentPlaylist != null) {
            if(currentPlaylist.size() > 0 && currentIndex != -1) {
                return currentPlaylist.get(currentIndex);
            }
        }
        return null;
    }

    public static boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public static void playRandomSong() {
        SONG_STATE = 1;
    }

    public static void nextSong() {
        if (SONG_STATE == 0) {
            if (currentIndex < currentPlaylist.size() - 1) currentIndex += 1;
            else currentIndex = 0;
        } else if (SONG_STATE == 1) {
            currentIndex = new Random().nextInt(currentPlaylist.size());
        }
        playSong();
    }

    public static void previousSong() {
        if (SONG_STATE == 0) {
            if (currentIndex > 0) currentIndex -= 1;
            else currentIndex = currentPlaylist.size() - 1;
        } else if (SONG_STATE == 1) {
            currentIndex = new Random().nextInt(currentPlaylist.size());
        }
        playSong();
    }

    public static void seekTo(int position) {
        if (player != null) player.seekTo(position);
    }

    public static int getCurrentPosition() {
        return player != null ? player.getCurrentPosition() : 0;
    }

    public static int getDuration() {
        return player != null ? player.getDuration() : 0;
    }

    public static void addPlaylistId() {

    }

    public static void removePlaylistId() {

    }

//    public static void updateState() {
//        currentIndex = prefs.getInt(KEY_SONG_INDEX, 0);
//        int currentTime = prefs.getInt(KEY_CURRENT_TIME, 0);
//
//        Log.d(TAG, "Current time : " + currentTime);
//
//        try {
//            if(player == null) {
//                player = new MediaPlayer();
//            }
//            String songUrl = .get(currentSongIndex).getSongUrl();
//            player.setDataSource(songUrl);
//            player.prepare();
//            player.seekTo(currentTime);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setShuffle(boolean isShuffle) {
//        this.isShuffle = isShuffle;
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
