package com.amazingchs.login.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.amazingchs.login.base.LoginListener;
import com.amazingchs.login.base.LoginService;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGotoSexy = findViewById(R.id.btnGotoSexy);
        btnGotoSexy.setOnClickListener(v -> gotoSexy());
        Button btnLogin = findViewById(R.id.btnLogin);
        if (LoginService.getInstance().isLogin()) {
            btnLogin.setText("Sign out");
        } else {
            btnLogin.setText("Sign in");
        }
        btnLogin.setOnClickListener(v -> {
            if (LoginService.getInstance().isLogin()) {
                LoginService.getInstance().logout(0);
            } else {
                LoginService.getInstance().gotoLogin();
            }
        });
        LoginService.getInstance().register(this);
    }

    private void gotoSexy() {
        startActivity(new Intent(this, SexyActivity.class));
    }

    @Override
    public void onLogin() {
        ((Button) findViewById(R.id.btnLogin)).setText("Sign out");
    }

    @Override
    public void onLogout() {
        ((Button) findViewById(R.id.btnLogin)).setText("Sign in");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginService.getInstance().unRegister(this);
    }
}