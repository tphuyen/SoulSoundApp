package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.soulsoundapp.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etConfirmPw;
    private TextView tvPwError, tvCheckPw;
    private Button btnSignUp;
    private final int counter = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPw = findViewById(R.id.etConfirmPw);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvPwError = findViewById(R.id.tvPwError);
        tvCheckPw = findViewById(R.id.tvCheckPw);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmpw = etConfirmPw.getText().toString();

                if(password.length()<counter && confirmpw.length()<counter){
                    tvPwError.setVisibility(View.VISIBLE);
                    tvPwError.setText("Password must be at least "+ counter+" characters long");
                    return;
                }else {
                    tvPwError.setVisibility(View.GONE);
                }
                // Implement authentication logic here
                if(!password.equals(confirmpw) ){
                    tvCheckPw.setVisibility(View.VISIBLE);
                    tvCheckPw.setText("Password not match");

                }
                else {
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
//                if (email.equals("huyentp@gmail.com") && password.equals("123456")) {
//                    // Successful login
//                    Toast.makeText(SignUpActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                } else {
//                    // Failed login
//                    Toast.makeText(SignUpActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}