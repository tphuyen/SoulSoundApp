package com.project.soulsoundapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.User;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static DatabaseHelper instance;

    private static final String DATABASE_NAME = "soulsound.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";
//    TABLE
    private static final String TABLE_USER = "users";
    private static final String TABLE_SONG = "Songs";
    private static final String TABLE_PLAYLIST = "Playlists";
    private static final String TABLE_PS = "Playlist_Song";
    private static final String TABLE_FAVORITE = "Favorites";

//    USER KEY
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PASSWORD = "password";

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

//    PLAYLIST_SONG KEY
    private static final String KEY_PS_PLAYLIST_ID = "playlistId";
    private static final String KEY_PS_SONG_ID = "songId";

//    FAVORITE KEY
    private static final String KEY_FAVORITE_USER_EMAIL = "email";
    private static final String KEY_FAVORITE_SONG_ID = "songId";

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (instance == null) {
            Log.v(TAG, "Get instance");
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(TAG, "Contructor DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table if not exists
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ( " +
                KEY_USER_EMAIL + " TEXT PRIMARY KEY, " +
                KEY_USER_NAME + " TEXT NOT NULL, " +
                KEY_USER_PASSWORD + " TEXT NOT NULL" +
                ")";

        // Create songs table if not exists
        String CREATE_SONGS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SONG + " ( " +
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

        // Create playlists table if not exists
        String CREATE_PLAYLISTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYLIST + " ( " +
                KEY_PLAYLIST_ID + " TEXT PRIMARY KEY, " +
                KEY_PLAYLIST_USER_ID + " TEXT NOT NULL, " +
                KEY_PLAYLIST_TITLE + " TEXT NOT NULL, " +
                KEY_PLAYLIST_DESCRIPTION + " TEXT NOT NULL, " +
                KEY_PLAYLIST_THUMBNAIL_URL + " TEXT NOT NULL, " +
                KEY_PLAYLIST_COVER_URL + " TEXT NOT NULL" +
                ")";

        // Create playlist-song table if not exists
        String CREATE_PLAYLIST_SONG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PS + " ( " +
                KEY_PS_PLAYLIST_ID + " TEXT NOT NULL, " +
                KEY_PS_SONG_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + KEY_PS_PLAYLIST_ID + ") REFERENCES " + TABLE_PLAYLIST + "(" + KEY_PLAYLIST_ID + ")," +
                "FOREIGN KEY(" + KEY_PS_SONG_ID + ") REFERENCES " + TABLE_SONG + "(" + KEY_SONG_ID + ")," +
                "PRIMARY KEY (" + KEY_PS_PLAYLIST_ID + ", " + KEY_PS_SONG_ID + ")" +
                ")";

        // Create favorite table if not exists
        String CREATE_FAVORITE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE + " ( " +
                KEY_FAVORITE_USER_EMAIL + " TEXT NOT NULL, " +
                KEY_FAVORITE_SONG_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + KEY_FAVORITE_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + KEY_USER_EMAIL + ")," +
                "FOREIGN KEY(" + KEY_FAVORITE_SONG_ID + ") REFERENCES " + TABLE_SONG + "(" + KEY_SONG_ID + ")," +
                "PRIMARY KEY (" + KEY_FAVORITE_USER_EMAIL + ", " + KEY_FAVORITE_SONG_ID + ")" +
                ")";

        // Execute SQL statements
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_PLAYLISTS_TABLE);
        db.execSQL(CREATE_PLAYLIST_SONG_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);

        Log.v(TAG, "Create tables successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.beginTransaction();
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

                onCreate(db);

                db.setTransactionSuccessful();
                Log.d(TAG, "Database upgraded successfully");
            } catch (Exception e) {
                Log.d(TAG, "Error while upgrading the database" + e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
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
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("name"))
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

//    BEGIN HANDLE SONG
    public void addSong(Song song) {
        if(isSongAvailable(song.getId())) {
            return;
        }
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
                Log.d(TAG, "[Song DB] Create songs successfully");
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get songs from database");
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

    public boolean isSongAvailable(String songId) {
        String SONGS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_SONG, KEY_SONG_ID, songId);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SONGS_SELECT_QUERY, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    public List<Song> getSongsByTitle(String title) {
        List<Song> songs = new ArrayList<>();

        String SONGS_SELECT_QUERY = String.format("SELECT * FROM %s WHERE title LIKE ?", TABLE_SONG);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SONGS_SELECT_QUERY, new String[]{"%" + title + "%"});
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

//    END HANDLE SONG

//    BEGIN HANDLE PLAYLIST
    public void addPlaylist(Playlist playlist) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues valuesOfPlaylist = new ContentValues();
            valuesOfPlaylist.put(KEY_PLAYLIST_ID, playlist.getPlaylistId());
            valuesOfPlaylist.put(KEY_PLAYLIST_USER_ID, playlist.getPlaylistUserId());
            valuesOfPlaylist.put(KEY_PLAYLIST_TITLE, playlist.getPlaylistTitle());
            valuesOfPlaylist.put(KEY_PLAYLIST_DESCRIPTION, playlist.getPlaylistDescription());
            valuesOfPlaylist.put(KEY_PLAYLIST_THUMBNAIL_URL, playlist.getPlaylistThumbnail());
            valuesOfPlaylist.put(KEY_PLAYLIST_COVER_URL, playlist.getPlaylistCover());

            // Check if playlistId already exists
            if (!isPlaylistExists(playlist.getPlaylistId())) {
                db.insert(TABLE_PLAYLIST, null, valuesOfPlaylist);
//                Log.d(TAG, "Add playlist successfully ::" + playlist.getPlaylistId());
            } else {
//                Log.d(TAG, "Playlist already exists, skipping insertion ::" + playlist.getPlaylistId());
            }

            for (String songId : playlist.getPlaylistSongs()) {
                if(isSongAvailable(songId)) {
                    addPlaylistSong(playlist.getPlaylistId(), songId);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add playlist to database" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public boolean isPlaylistExists(String playlistId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_PLAYLIST + " WHERE " + KEY_PLAYLIST_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{playlistId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String PLAYLISTS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_PLAYLIST);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PLAYLISTS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {

                do {
                    Playlist playlist = new Playlist(
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_THUMBNAIL_URL)),
                            cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_COVER_URL)),
                            getSongsOfPlaylist(cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_ID)))
                    );

                    playlists.add(playlist);
                } while(cursor.moveToNext());
            }
            Log.d(TAG, "[Playlist DB] Create playlists successfully");

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get playlist from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return playlists;
    }

//    END HANDLE PLAYLIST

//    BEGIN HANDLE FAVORITE PLAYLIST
    public void addFavorite(String email, String songId) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_FAVORITE_USER_EMAIL, email);
            values.put(KEY_FAVORITE_SONG_ID, songId);

            if (!isFavoriteExists(email, songId)) {
                db.insert(TABLE_FAVORITE, null, values);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add favorite to database: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public boolean isFavoriteExists(String mail, String songId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_FAVORITE + " WHERE " + KEY_FAVORITE_USER_EMAIL + " LIKE ? AND " + KEY_FAVORITE_SONG_ID + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{mail, songId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<String> getFavoriteSongs(String userId) {
        List<String> songs = new ArrayList<>();

        String FAVORITE_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_FAVORITE, KEY_FAVORITE_USER_EMAIL, userId);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(FAVORITE_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String song = cursor.getString(cursor.getColumnIndex(KEY_FAVORITE_SONG_ID));
                    songs.add(song);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get favorite from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return songs;
    }

    public void removeFavorite(String mail, String songId) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_FAVORITE, KEY_FAVORITE_USER_EMAIL + " = ? AND " + KEY_FAVORITE_SONG_ID + " = ?", new String[]{mail, songId});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to remove favorite from database: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
//    END HANDLE FAVORITE PLAYLIST

//    BEGIN HANDLE PLAYLIST_SONG
    public List<String> getSongsOfPlaylist(String playlistId) {
        List<String> songs = new ArrayList<>();

        String PS_SONGS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_PS, KEY_PS_PLAYLIST_ID, playlistId);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PS_SONGS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String song = cursor.getString(cursor.getColumnIndex(KEY_PS_SONG_ID));
                    songs.add(song);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get playlist from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return songs;
    }

    public boolean isPlaylistSongExists(String playlistId, String songId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_PS + " WHERE " + KEY_PS_PLAYLIST_ID + " = ? AND " + KEY_PS_SONG_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{playlistId, songId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addPlaylistSong(String playlistId, String songId) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PS_PLAYLIST_ID, playlistId);
            values.put(KEY_PS_SONG_ID, songId);

            if (!isPlaylistSongExists(playlistId, songId)) {
                db.insert(TABLE_PS, null, values);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add playlist-song pair to database: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

//    END HANDLE PLAYLIST_SONG


//    BEGIN HANDLE USER
    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_EMAIL, user.getEmail());
            values.put(KEY_USER_NAME, user.getFullName());
            values.put(KEY_USER_PASSWORD, "User@123");
            db.insert(TABLE_USER, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add user to database" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

//    END HANDLE USER
}
