package com.project.soulsoundapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "SoulSound.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SONG_TABLE_NAME = "songs";
    private static final String SONG_COLUMN_ID = "_id";
    private static final String SONG_COLUMN_TITLE = "title";
    private static final String SONG_COLUMN_DURATION = "duration";
    private static final String SONG_COLUMN_IMAGE = "image";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + SONG_TABLE_NAME + "(" +
                SONG_COLUMN_ID + "," +
                SONG_COLUMN_TITLE + "," +
                SONG_COLUMN_DURATION + "," +
                SONG_COLUMN_IMAGE +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + SONG_TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
}
