package com.project.soulsoundapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.model.User;

import java.util.ArrayList;
import java.util.List;

import com.project.soulsoundapp.activity.SignUpActivity;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static DatabaseHelper instance;
  
    private static final String DATABASE_NAME = "soulsound.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";
//    TABLE
    private static final String TABLE_SONG = "Songs";
    private static final String TABLE_PLAYLIST = "Playlists";
    private static final String TABLE_PLAYLIST_SONG = "Playlist-Song";

//    SONG KEY
    private static final String KEY_SONG_ID = "id";
    private static final String KEY_SONG_TITLE = "title";
    private static final String KEY_SONG_DURATION = "duration";
    private static final String KEY_SONG_LIKE = "likes";
    private static final String KEY_SONG_ARTIST = "artist";
    private static final String KEY_SONG_STREAM_URL = "songUrl";
    private static final String KEY_SONG_THUMBNAIL_URL = "thumbnailUrl";
    private static final String KEY_SONG_COVER_URL = "coverUrl";
    private static final String KEY_SONG_LYRIC_URL = "lyricUrl";

//    PLAYLIST KEY
    private static final String KEY_PLAYLIST_ID = "playlistId";
    private static final String KEY_PLAYLIST_USER_ID = "playlistUserId";
    private static final String KEY_PLAYLIST_TITLE = "playlistTitle";
    private static final String KEY_PLAYLIST_DESCRIPTION = "playlistDescription";
    private static final String KEY_PLAYLIST_THUMBNAIL_URL = "playlistThumbnail";
    private static final String KEY_PLAYLIST_COVER_URL = "playlistCover";

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(TAG, "START DB");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT primary key, name TEXT,  password TEXT)");

//        Create songs table
        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONG + " ( " +
            KEY_SONG_ID + " TEXT PRIMARY KEY, " +
            KEY_SONG_TITLE + " TEXT NOT NULL, " +
            KEY_SONG_DURATION + " INTEGER NOT NULL, " +
            KEY_SONG_ARTIST + " TEXT NOT NULL, " +
            KEY_SONG_LIKE + " INTEGER NOT NULL, " +
            KEY_SONG_STREAM_URL + " TEXT NOT NULL, " +
            KEY_SONG_THUMBNAIL_URL + " TEXT NOT NULL, " +
            KEY_SONG_COVER_URL + " TEXT NOT NULL, " +
            KEY_SONG_LYRIC_URL + " TEXT NOT NULL " +
        ")";
        db.execSQL(CREATE_SONGS_TABLE);
        Log.v(TAG, "Create table songs successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
          db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
          db.execSQL("DROP TABLE IF EXISTS users");
          onCreate(db); 
        }
    }

    public boolean insertatata(Context context, String email,String name,  String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        long result = myDB.insert("users",null, contentValues);
        if (result==-1)return false;
        else {
            return true;
        }
    }

    public Boolean checkMail(String mail){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users where email = ?", new String[]{mail});
        if(cursor.getCount()>0)
            return true;
        else return false;
    }

    public User checkUser(String mail, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users where email = ? and password=?", new String[]{mail, password});
        User u;
        Log.v(TAG, "Start check");

        if(cursor.moveToFirst()) {
            do {
                u = new User(
                        "1",
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("password"))
                );
            } while (cursor.moveToNext());
            Log.v(TAG, "End check true");
            return u;
        }
        else {
            Log.v(TAG, "End check true");

            return null;
        }
    }

    public boolean updatePw(String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = myDB.update("users", contentValues, "email = ?", new String[]{email});
        if (result==-1)return false;
        else {
            return true;
        }
    }

    public  void addSong(Song song) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_SONG_ID, song.getId());
            values.put(KEY_SONG_TITLE, song.getTitle());
            values.put(KEY_SONG_DURATION, song.getDuration());
            values.put(KEY_SONG_ARTIST, song.getArtist());
            values.put(KEY_SONG_LIKE, song.getLikes());
            values.put(KEY_SONG_STREAM_URL, song.getSongUrl());
            values.put(KEY_SONG_THUMBNAIL_URL, song.getThumbnailUrl());
            values.put(KEY_SONG_COVER_URL, song.getCoverUrl());
            values.put(KEY_SONG_LYRIC_URL, song.getLyricUrl());
            Log.i(TAG, values.toString());
            db.insert(TABLE_SONG, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();

        String SONGS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_SONG);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SONGS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Song song = new Song(
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_TITLE)),
                            cursor.getInt(cursor.getColumnIndex(KEY_SONG_DURATION)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_ARTIST)),
                            cursor.getInt(cursor.getColumnIndex(KEY_SONG_LIKE)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_STREAM_URL)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_THUMBNAIL_URL)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_COVER_URL)),
                            cursor.getString(cursor.getColumnIndex(KEY_SONG_LYRIC_URL))
                    );

                    songs.add(song);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return songs;
    }

    public List<Song> getSongByIds(List<String> songIds) {
        List<Song> songs = new ArrayList<>();
        for (String id : songIds) {
            String SONGS_SELECT_BY_IDS_QUERY =
                    String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_SONG, KEY_SONG_ID, id);
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(SONGS_SELECT_BY_IDS_QUERY, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Song song = new Song(
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_ID)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_TITLE)),
                                cursor.getInt(cursor.getColumnIndex(KEY_SONG_DURATION)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_ARTIST)),
                                cursor.getInt(cursor.getColumnIndex(KEY_SONG_LIKE)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_STREAM_URL)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_THUMBNAIL_URL)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_COVER_URL)),
                                cursor.getString(cursor.getColumnIndex(KEY_SONG_LYRIC_URL))
                        );

                        songs.add(song);
                    } while(cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to get posts from database");
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }

        return songs;
    }

}
