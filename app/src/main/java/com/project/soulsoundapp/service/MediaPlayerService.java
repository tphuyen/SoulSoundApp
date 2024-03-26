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

public class MediaPlayerService extends MediaBrowserService {
    public static ArrayList<Song> songs = new ArrayList<Song>();
    private static MediaPlayer player;

    public static ArrayList<Song> getCurrentPlaylist() {
        return songs;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {

    }
}
