package com.example.musicapplication_java_dh9c2.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.model.Account;
import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.data.AccountDAO;

public class RegisterActivity extends AppCompatActivity {
    EditText rUsername, rPasswd, rEmail;
    Button btnRegister, btnBack;
    AccountDAO adao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        adao = new AccountDAO(this);

        onClick();
    }

    private void onClick() {
        btnRegister.setOnClickListener((view)-> {
                String u = rUsername.getText().toString();
                String p = rPasswd.getText().toString();
                String e = rEmail.getText().toString();
                Account a = new Account(u, p, e);
                if(!u.equals("") && !p.equals("") && !e.equals("")) {
                    if(adao.ThemACC(a)) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intentAdd = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentAdd);
                        finish();
                    }
                    else {
                        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

        });

        btnBack.setOnClickListener((v)-> {
            finish();
        });
    }

    private void initView() {
        rUsername = findViewById(R.id.reg_inputUsername);
        rPasswd = findViewById(R.id.reg_inputPassword);
        rEmail = findViewById(R.id.reg_inputEmail);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_r_back);

    }
}