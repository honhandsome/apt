package com.amazingchs.login.base;

import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.Map;

public interface ILoginService extends IProvider {

    boolean isLogin();

    boolean checkLogin();

    boolean checkLogin(String reportKey);

    boolean checkLogin(String reportKey, Map<String, String> reportData);

    ILoginService afterLogin(LifecycleOwner lifecycleOwner, ActionHolder actionHolder);

    void gotoLogin();

    <T> void login(T data);

    void logout(int type);

    void register(LoginListener listener);

    void unRegister(LoginListener listener);
}