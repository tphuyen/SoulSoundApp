package com.project.soulsoundapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.project.soulsoundapp.activity.SignUpActivity;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "soulsound.db";
    private static final int DATABASE_VERSION = 1;

//    private static final String SONG_TABLE_NAME = "songs";
//    private static final String SONG_COLUMN_ID = "_id";
//    private static final String SONG_COLUMN_TITLE = "title";
//    private static final String SONG_COLUMN_DURATION = "duration";
//    private static final String SONG_COLUMN_IMAGE = "image";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT primary key, name TEXT,  password TEXT)");
//        String query = "CREATE TABLE " + SONG_TABLE_NAME + "(" +
//                SONG_COLUMN_ID + "," +
//                SONG_COLUMN_TITLE + "," +
//                SONG_COLUMN_DURATION + "," +
//                SONG_COLUMN_IMAGE +
//                ")";
//        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String query = "DROP TABLE IF EXISTS " + SONG_TABLE_NAME;
//        db.execSQL(query);
//        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS users");
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

    public boolean checkMail(String mail){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users where email = ?", new String[]{mail});
        if(cursor.getCount()>0)
            return true;
        else return false;
    }

    public boolean checkUser(String mail, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users where email = ? and password=?", new String[]{mail, password});
        if(cursor.getCount()>0)
            return true;
        else return false;
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
}
