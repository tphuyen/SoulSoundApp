package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.soulsoundapp.R;

public class SignInActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private TextView tvPwError;
    private Button btnSignIn;
    private final int counter = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvPwError = findViewById(R.id.tvPwError);

        // Set a click listener for the login button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

//                if(password.length()<counter){
//                    tvPwError.setVisibility(View.VISIBLE);
//                    tvPwError.setText("Wrong password");
//                    return;
//                }else {
//                    tvPwError.setVisibility(View.GONE);
//                }
                // Implement authentication logic here
                if (true) {
                    // Successful login
                    Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Failed login
                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void moveToSignUp(View view){
        Intent i = new Intent( this, SignUpActivity.class);
        startActivity(i);
    }

    public void moveToForgotPw(View view){
        Intent i = new Intent( this, ForgetPassword.class);
        startActivity(i);
    }
}