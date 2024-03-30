package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etConfirmPw;
    private TextView tvPwError, tvCheckPw, tvEmailError;
    private ImageView passwordIcon, ConfirmPwIcon;
    private Button btnSignUp;
    private final int counter = 6;
    private boolean passwordShowing = false;

    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        tvEmailError = findViewById(R.id.tvEmailError);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPw = findViewById(R.id.etConfirmPw);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvPwError = findViewById(R.id.tvPwError);
        tvCheckPw = findViewById(R.id.tvCheckPw);
        passwordIcon = findViewById(R.id.passwordIcon);
        ConfirmPwIcon = findViewById(R.id.ConfirmPwIcon);
        databaseHelper = new DatabaseHelper(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmpw = etConfirmPw.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$!%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/\\\\]).{8,24}$";
                if (!email.matches(emailPattern)) {
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvEmailError.setText("Invalid email format");
                    return;
                }else {
                    tvEmailError.setVisibility(View.GONE);
                }
                if(name.equals("") || email.equals("") || password.equals("") || confirmpw.equals("")){
                    Toast.makeText(SignUpActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    if(!password.matches(passwordPattern)){
                        tvPwError.setVisibility(View.VISIBLE);
                        tvPwError.setText("Password must be at least 8 characters long \nSpecial characters \nUppercase & lowercase characters");
                        return;
                    }else {
                        tvPwError.setVisibility(View.GONE);
                    }
//                    if(password.length()<counter && confirmpw.length()<counter){
//
//                    }
                    if (password.equals(confirmpw)) {
                        boolean checkUserEmail = databaseHelper.checkMail(email);
                        if (checkUserEmail == false) {
                            boolean insert = databaseHelper.insertatata(view.getContext(), email, name, password);

                            if (insert) {
                                Toast.makeText(SignUpActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        tvCheckPw.setVisibility(View.VISIBLE);
                        tvCheckPw.setText("Password not match");
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

        ConfirmPwIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;
                    etConfirmPw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ConfirmPwIcon.setImageResource(R.drawable.ic_show);
                }else {
                    passwordShowing = true;
                    etConfirmPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ConfirmPwIcon.setImageResource(R.drawable.ic_eye_off);
                }
                etConfirmPw.setSelection(etConfirmPw.length());
            }
        });
    }
    public void moveToSignIn(View view){
        Intent i = new Intent( this, SignInActivity.class);
        startActivity(i);
    }
}