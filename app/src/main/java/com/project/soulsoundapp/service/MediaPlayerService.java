package com.project.soulsoundapp.service;

import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.soulsoundapp.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediaPlayerService extends MediaBrowserService {
    public static ArrayList<Song> songs;
    private static MediaPlayer player;
    private int currentSongIndex = 0;
    private boolean isShuffle = false;

    static {
        songs = initSongs();
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    private static ArrayList<Song> initSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song(1, "Thủy Triều", 378626.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7U00WDE.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7U00WDEx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7U00WDEx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7U00WDE.mp3", "Quang Hùng MasterD"));
        songs.add(new Song(2, "Sau Lời Từ Khước", 291469.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7UUAFUF.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7UUAFUFx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7UUAFUFx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7UUAFUF.mp3", "Phan Mạnh Quỳnh"));
        songs.add(new Song(3, "Thiên Lý Ơi", 136216.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7I6BCCO.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7I6BCCOx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7I6BCCOx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7I6BCCO.mp3", "Jack - J97"));
        songs.add(new Song(4, "Ăn Trông Nồi Ngồi Trông Hướng", 118621.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z7IBO90D.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7IBO90Dx96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7IBO90Dx240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z7IBO90D.mp3", "Drum7"));
        songs.add(new Song(5, "Cắt Đôi Nỗi Sầu", 1676676.0, "https://music-player.sgp1.digitaloceanspaces.com/song_lyric/Z6FWCOO0.lrc", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z6FWCOO0x96.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z6FWCOO0x240.jpg", "https://music-player.sgp1.digitaloceanspaces.com/song_stream/Z6FWCOO0.mp3", "Drum7"));
        return new ArrayList<>(songs);
    }

    public static ArrayList<Song> getCurrentPlaylist() {
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
            player.setDataSource(songs.get(index).getStreamUrl());
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
