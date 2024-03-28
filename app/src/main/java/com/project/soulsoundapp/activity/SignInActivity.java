package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;

public class SignInActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ImageView passwordIcon;
    private TextView tvPwError;
    private Button btnSignIn;
    private final int counter = 6;
    private boolean passwordShowing = false;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvPwError = findViewById(R.id.tvPwError);
        passwordIcon = findViewById(R.id.passwordIcon);


        // Set a click listener for the login button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                boolean isSignedIn = databaseHelper.checkUser(etEmail.getText().toString(), etPassword.getText().toString());
//                if(password.length()<counter){
//                    tvPwError.setVisibility(View.VISIBLE);
//                    tvPwError.setText("Wrong password");
//                    return;
//                }else {
//                    tvPwError.setVisibility(View.GONE);
//                }
                // Implement authentication logic here
                if(email.equals("") || password.equals("")){
                    Toast.makeText(SignInActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if (isSignedIn) {
                        // Successful login
                        Toast.makeText(SignInActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity2.class);
                        startActivity(intent);
                    } else {
                        // Failed login
                        Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_show);
                }else {
                    passwordShowing = true;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_eye_off);
                }
                etPassword.setSelection(etPassword.length());
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