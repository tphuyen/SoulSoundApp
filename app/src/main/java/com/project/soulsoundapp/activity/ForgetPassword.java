package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.helper.DatabaseHelper;

import java.util.Random;

public class ForgetPassword extends AppCompatActivity {
    TextView tvForgotPw, tvEmailReset, tvBackSignIn;
    EditText etEmailReset;
    Button btnReset;
    int code;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        tvForgotPw = findViewById(R.id.tvForgotPw);
        tvEmailReset = findViewById(R.id.tvEmailReset);
        tvBackSignIn = findViewById(R.id.tvBackSignIn);
        etEmailReset = findViewById(R.id.etEmailReset);
        btnReset = findViewById(R.id.btnReset);
        databaseHelper = new DatabaseHelper(this);

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = etEmailReset.getText().toString();
                boolean checkUserEmail = databaseHelper.checkMail(email);
                if(checkUserEmail){
                    Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgetPassword.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void moveToSignIn(View view){
        Intent i = new Intent( this, SignInActivity.class);
        startActivity(i);
    }
}