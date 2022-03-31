package com.amazingchs.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazingchs.login.base.LoginService;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            LoginService.getInstance().login(null);
            finish();
        });
    }
}