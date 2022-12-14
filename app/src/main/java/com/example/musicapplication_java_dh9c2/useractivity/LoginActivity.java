package com.example.musicapplication_java_dh9c2.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.model.Account;
import com.example.musicapplication_java_dh9c2.NaviActivity;
import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.data.AccountDAO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPasswd;
    TextView textViewSignUp;
    Button btnLogin;
    AccountDAO dao;
    List<Account> dsAccounts;
    public static final String SHARED_PREFS = "user_prefs";
    // key for storing username.
    String user;
    public static final String USERNAME_KEY = "username_key";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(USERNAME_KEY, null);

        dao = new AccountDAO(this);
        dsAccounts = new ArrayList<>();

        onClick();
    }


    private void initView() {
        edtUsername = findViewById(R.id.inputUsername);
        edtPasswd = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
    }

    private void onClick() {
        btnLogin.setOnClickListener((v) -> {
            String u = edtUsername.getText().toString();
            String p = edtPasswd.getText().toString();
            boolean isRealACC = dao.CheckAccount(u, p);
            Log.d("ACC", "Account: " + isRealACC);
            if (isRealACC) {
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // put username to shared preferences.
                editor.putString(USERNAME_KEY, u);

                // to save our data with key and value.
                editor.apply();

                // starting new activity.
                Intent i = new Intent(LoginActivity.this, NaviActivity.class);
                startActivity(i);
                finish();

            }
            else {
                Toast.makeText(this, "Vui l??ng ki???m tra l???i th??ng tin ????ng nh???p", Toast.LENGTH_SHORT).show();

            }
        });

        textViewSignUp.setOnClickListener((view) -> {
            Intent regInt = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(regInt);
        });
    }

}