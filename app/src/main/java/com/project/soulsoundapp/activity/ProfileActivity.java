package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.soulsoundapp.R;

public class ProfileActivity extends AppCompatActivity {
    TextView tvName, tvEmail;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        handleBackButton();
        fillData();
//        handleSaveButton();
    }

    private void handleBackButton() {
        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fillData() {
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String fullname = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if(fullname!=null||email!=null){
            tvName.setText(fullname);
            tvEmail.setText(email);
        }
    }

//    private void handleSaveButton() {
//        Button ibSave = findViewById(R.id.btnSave);
//        ibSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvName = findViewById(R.id.tvName);
//                tvEmail = findViewById(R.id.etEmail);
//
//                String name = tvName.getText().toString();
//                String email = tvEmail.getText().toString();
//
//                // Save the data to the database...
//            }
//        });
//    }
}