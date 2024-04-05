package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.utils.DataManager;

public class SplashActivity extends AppCompatActivity {
    DataManager dbManager;
    private Handler handler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dbManager = DataManager.getInstance(getApplicationContext());
        dbManager.setDB();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                boolean isNameExist = sharedPreferences.contains(KEY_NAME);
                boolean isEmaiExist = sharedPreferences.contains(KEY_EMAIL);
                Intent intent;
                if(isNameExist && isEmaiExist) {
                    intent = new Intent(SplashActivity.this, MainActivity2.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity2.class);
//                    intent = new Intent(SplashActivity.this, SignInActivity.class);

                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}