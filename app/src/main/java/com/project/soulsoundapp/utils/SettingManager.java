package com.project.soulsoundapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingManager {
    private static SettingManager instance;
    private Context context;
    private SharedPreferences prefs;
    private SQLiteDatabase mDatabase;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LANGUAGE = "language";
    private static final String COLUMN_DARK_THEME = "dark_theme";

    public interface LANGUAGE {
        String VI = "vi";
        String EN = "en";
    }

    private DatabaseHelper db;

    private SettingManager(Context context) {
        this.context = context;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        mDatabase = dbHelper.getWritableDatabase();
        db = DatabaseHelper.getInstance(context);
    }

    public static SettingManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingManager.class) {
                if (instance == null) {
                    instance = new SettingManager(context);
                }
            }
        }
        return instance;
    }

    public boolean isDarkTheme() {
        return prefs.getBoolean(context.getResources().getString(R.string.isDarkTheme), true);
    }

    public void setTheme(boolean isDarkTheme) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getResources().getString(R.string.isDarkTheme), isDarkTheme);
        editor.apply();
    }

    public boolean isEnglish() {
        return prefs.getBoolean(context.getResources().getString(R.string.isEnglish), true);
    }

    public void setLanguageSP(boolean isEnglish) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getResources().getString(R.string.isEnglish), isEnglish);
        editor.apply();
    }

    public void updateLanguageConfiguration() {
        Locale locale;
        if (isEnglish()) {
            locale = new Locale(LANGUAGE.EN);
        } else {
            locale = new Locale(LANGUAGE.VI);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public void updateLanguageSQLite(boolean isEnglish) {
        // Update SQLite database
        String language = isEnglish ? LANGUAGE.EN : LANGUAGE.VI;
        String updateQuery = "UPDATE " + TABLE_USERS + " SET " + COLUMN_LANGUAGE + " = '" + language + "'";
        mDatabase.execSQL(updateQuery);

        Log.d("updateLanguageSQLite", "Language updated in SQLite");

        // Update language configuration
        setLanguageSP(isEnglish);
        updateLanguageConfiguration();
    }

    public void updateThemeSQLite(boolean isDarkTheme) {
        // Update SQLite database
        int darkThemeValue = isDarkTheme ? 1 : 0;
        String updateQuery = "UPDATE " + TABLE_USERS + " SET " + COLUMN_DARK_THEME + " = " + darkThemeValue;
        mDatabase.execSQL(updateQuery);

        Log.d("updateThemeSQLite", "Theme updated in SQLite");

        // Update theme configuration
        setTheme(isDarkTheme);
    }

    public void setDatabase() {
        ApiService.apiService.getAllSongs().enqueue(new Callback<ApiService.MultupleResource>() {
            @Override
            public void onResponse(Call<ApiService.MultupleResource> call, Response<ApiService.MultupleResource> response) {
                ApiService.MultupleResource resource = response.body();
                List<Song> songs = new ArrayList<>(resource.getSongs());
                for (Song song : songs) {
//                    db.insertSong(song);
                }
                Toast.makeText(context.getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiService.MultupleResource> call, Throwable throwable) {
                Toast.makeText(context.getApplicationContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
