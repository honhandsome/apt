package com.amazingchs.login.base;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Map;

public class LoginService implements ILoginService {

    private final ILoginService wrappedLoginService;

    public static LoginService getInstance() {
        return Inner.sInstance;
    }

    private LoginService() {
        this.wrappedLoginService = (ILoginService) ARouter.getInstance().navigation(ILoginService.class);
    }

    @Override
    public boolean isLogin() {
        return this.wrappedLoginService != null && this.wrappedLoginService.isLogin();
    }

    @Override
    public boolean checkLogin() {
        return this.wrappedLoginService != null && this.wrappedLoginService.checkLogin();
    }

    @Override
    public boolean checkLogin(String reportKey) {
        return this.wrappedLoginService != null && this.wrappedLoginService.checkLogin(reportKey);
    }

    @Override
    public boolean checkLogin(String reportKey, Map<String, String> reportData) {
        return this.wrappedLoginService != null && this.wrappedLoginService.checkLogin(reportKey, reportData);
    }

    @Override
    public ILoginService afterLogin(LifecycleOwner lifecycleOwner, ActionHolder actionHolder) {
        return this.wrappedLoginService != null ? this.wrappedLoginService.afterLogin(lifecycleOwner, actionHolder) : this;
    }

    @Override
    public void gotoLogin() {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.gotoLogin();
        }
    }

    @Override
    public <T> void login(T data) {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.login(data);
        }
    }

    @Override
    public void logout(int type) {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.logout(type);
        }
    }

    @Override
    public void register(LoginListener listener) {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.register(listener);
        }
    }

    @Override
    public void unRegister(LoginListener listener) {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.unRegister(listener);
        }
    }

    @Override
    public void init(Context context) {
        if (this.wrappedLoginService != null) {
            this.wrappedLoginService.init(context);
        }
    }

    private static class Inner {
        private static final LoginService sInstance = new LoginService();

        private Inner() {
        }
    }
}