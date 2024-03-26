package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.soulsoundapp.R;

public class SettingActivity extends AppCompatActivity {
    EditText etName, etEmail, etPhone, etPassword, etConfirmPassword;

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
        etPhone = findViewById(R.id.etPhone);

        etEmail.setText("example@gmail.com");
        etName.setText("John Doe");
        etPhone.setText("1234567890");
    }

    private void handleSaveButton() {
        Button ibSave = findViewById(R.id.btnSave);
        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName = findViewById(R.id.etName);
                etEmail = findViewById(R.id.etEmail);
                etPhone = findViewById(R.id.etPhone);
                etPassword = findViewById(R.id.etPassword);
                etConfirmPassword = findViewById(R.id.etConfirmPassword);

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // Save the data to the database...
            }
        });
    }
}