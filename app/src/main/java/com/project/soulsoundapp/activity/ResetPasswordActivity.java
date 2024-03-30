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

public class ResetPasswordActivity extends AppCompatActivity {
    TextView tvEmailReset;
    EditText etNewPw, etConfirmNewPw;
    Button btnSave;
    ImageView passwordIcon, ConfirmPwIcon;
    private boolean passwordShowing = false;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        tvEmailReset = findViewById(R.id.tvEmailReset);
        etNewPw = findViewById(R.id.etNewPw);
        etConfirmNewPw = findViewById(R.id.etConfirmNewPw);
        btnSave = findViewById(R.id.btnSave);
        passwordIcon = findViewById(R.id.passwordIcon);
        ConfirmPwIcon = findViewById(R.id.ConfirmPwIcon);
        databaseHelper = new DatabaseHelper(this);


        Intent intent = getIntent();
        tvEmailReset.setText(intent.getStringExtra("email"));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmailReset.getText().toString();
                String password = etNewPw.getText().toString();
                String repassword = etConfirmNewPw.getText().toString();
                if(password.equals("") || repassword.equals("")){
                    Toast.makeText(ResetPasswordActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    if (password.equals(repassword)) {
                        boolean checkpwupdate = databaseHelper.updatePw(email, password);
                        if (checkpwupdate) {
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);
                            Toast.makeText(ResetPasswordActivity.this, "Password Updates Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Password Not Update", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;
                    etNewPw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_show);
                }else {
                    passwordShowing = true;
                    etNewPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_eye_off);
                }
                etNewPw.setSelection(etNewPw.length());
            }
        });

        ConfirmPwIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;
                    etConfirmNewPw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ConfirmPwIcon.setImageResource(R.drawable.ic_show);
                }else {
                    passwordShowing = true;
                    etConfirmNewPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ConfirmPwIcon.setImageResource(R.drawable.ic_eye_off);
                }
                etConfirmNewPw.setSelection(etConfirmNewPw.length());
            }
        });
    }

    public void moveToSignIn(View view){
        Intent i = new Intent( this, SignInActivity.class);
        startActivity(i);
    }
}