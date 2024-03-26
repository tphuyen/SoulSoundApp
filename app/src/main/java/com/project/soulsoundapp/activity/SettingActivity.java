package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.soulsoundapp.R;

public class SettingActivity extends AppCompatActivity {
    EditText etName, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        handleBackButton();
        fillData();
        handleSaveButton();
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
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);

        etEmail.setText("example@gmail.com");
        etName.setText("John Doe");
    }

    private void handleSaveButton() {
        Button ibSave = findViewById(R.id.btnSave);
        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName = findViewById(R.id.etName);
                etEmail = findViewById(R.id.etEmail);

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();

                // Save the data to the database...
            }
        });
    }
}