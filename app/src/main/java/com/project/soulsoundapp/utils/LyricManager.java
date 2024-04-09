package com.project.soulsoundapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.project.soulsoundapp.model.Lyric;
import com.project.soulsoundapp.service.MediaPlayerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LyricManager {

    private static final int MSG_LOAD_LYRIC_SUCCESS = 1;
    private static final int MSG_LOAD_LYRIC_FAILED = 2;
    private static final String TAG = "LyricManager";

    private Context context;
    private TextView textView;
    private String lyricUrl;
    private List<Lyric> lyrics;
    private Handler handler;
    private MediaPlayerService mps;

    public LyricManager(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
        lyrics = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_LOAD_LYRIC_SUCCESS:
                        showLyrics();
                        break;
                    case MSG_LOAD_LYRIC_FAILED:
                        textView.setText("Lỗi tải lời bài hát!");
                        break;
                }
            }
        };
    }

    public void loadLyric(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        lyricUrl = url;
        new LyricDownloadTask().execute(url);
    }

    private void showLyrics() {
        if (lyrics.isEmpty()) {
            return;
        }
        StringBuilder lyricContent = new StringBuilder();
        for (Lyric lyric : lyrics) {
            lyricContent.append("\n").append(lyric.getText());
        }
        textView.setText(lyricContent);
        textView.setTextSize(22);
        textView.setLineSpacing(20, 1);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        }, 0, 100);
    }

    private long parseTimestamp(String timestamp) {
        return Long.parseLong(timestamp.replaceAll("[^0-9]", ""));
    }


    private class LyricDownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                // Tạo kết nối mạng
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Đọc nội dung file .lrc
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                reader.close();

                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.v(TAG, "onPostExecute");

                lyrics = parseLrc(result);
                Log.v(TAG, "" +lyrics.size());

                for(Lyric l : lyrics) {
                    Log.v(TAG, l.getText());
                }
                handler.sendEmptyMessage(MSG_LOAD_LYRIC_SUCCESS);
            } else {
                handler.sendEmptyMessage(MSG_LOAD_LYRIC_FAILED);
            }
        }
    }

    private List<Lyric> parseLrc(String content) {
        List<Lyric> lyrics = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(\\d+:\\d+\\.\\d+)\\]\\s*(.*)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String timestamp = matcher.group(1);
            String text = matcher.group(2).trim();
            lyrics.add(new Lyric(timestamp, text));
        }

        return lyrics;
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int time) {
        int minutes = time / 60000;
        int seconds = (time % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
