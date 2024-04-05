package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.utils.DataManager;

public class SplashActivity extends AppCompatActivity {
    DataManager dbManager;
    private Handler handler;
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
                Intent intent = new Intent(SplashActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}