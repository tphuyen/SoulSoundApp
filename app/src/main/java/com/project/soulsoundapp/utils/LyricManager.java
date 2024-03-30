package com.project.soulsoundapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LyricManager {

    private Context context;
    private TextView textView;
    private LyricLoadListener listener;

    public LyricManager(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public interface LyricLoadListener {
        void onLyricsLoaded(String[] lyrics);
    }

    public void setLyricLoadListener(LyricLoadListener listener) {
        this.listener = listener;
    }

    public void loadLyricsFromUrl(String url) {
        new LoadLyricsTask().execute(url);
    }

    private class LoadLyricsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... urls) {
            List<String> lyricsList = new ArrayList<>();
            for (String urlString : urls) {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    lyricsList.add(stringBuilder.toString());
                    urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return lyricsList.toArray(new String[0]);
        }

        @Override
        protected void onPostExecute(String[] lyrics) {
            if (listener != null) {
                listener.onLyricsLoaded(lyrics);
            } else {
                // Nếu không có listener, hiển thị lời bài hát trong TextView
                if (lyrics.length > 0) {
                    textView.setText(lyrics[0]);
                }
            }
        }
    }
}
