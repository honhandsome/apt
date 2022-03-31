package com.amazingchs.login;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amazingchs.login.base.ActionHolder;
import com.amazingchs.login.base.ILoginService;
import com.amazingchs.login.base.LoginListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Route(path = "/login/service")
public class LoginServiceImpl implements ILoginService {

    private boolean status;

    private final List<WeakReference<LoginListener>> loginListeners = new ArrayList<>();

    @Override
    public boolean isLogin() {
        return status;
    }

    @Override
    public boolean checkLogin() {
        return checkLogin(null);
    }

    @Override
    public boolean checkLogin(String reportKey) {
        return checkLogin(reportKey, null);
    }

    @Override
    public boolean checkLogin(String reportKey, Map<String, String> reportData) {
        boolean isLogin = isLogin();
        if (!isLogin) {
            gotoLogin();
            if (!TextUtils.isEmpty(reportKey)) {
                // report
            }
        }
        return isLogin;
    }

    @Override
    public ILoginService afterLogin(LifecycleOwner lifecycleOwner, ActionHolder actionHolder) {
        AfterLoginContinue.getInstance().action(lifecycleOwner, actionHolder, "");
        return this;
    }

    @Override
    public void gotoLogin() {
        // goto login page
    }

    @Override
    public <T> void login(T data) {
        status = true;
        for (WeakReference<LoginListener> listenerWRF : loginListeners) {
            if (listenerWRF.get() != null) {
                listenerWRF.get().onLogin();
            }
        }
    }

    @Override
    public void logout(int type) {
        if (!status) return;
        status = false;
        for (WeakReference<LoginListener> listenerWRF : loginListeners) {
            if (listenerWRF.get() != null) {
                listenerWRF.get().onLogout();
            }
        }
    }

    @Override
    public void register(LoginListener listener) {
        unRegister(listener);
        loginListeners.add(new WeakReference<>(listener));
    }

    @Override
    public void unRegister(LoginListener listener) {
        Iterator<WeakReference<LoginListener>> iterator = loginListeners.iterator();
        while (iterator.hasNext()) {
            WeakReference<LoginListener> listenerWRF = iterator.next();
            if (listenerWRF.get() == null || listenerWRF.get() == listener) {
                iterator.remove();
            }
        }
    }

    @Override
    public void init(Context context) {
    }
}