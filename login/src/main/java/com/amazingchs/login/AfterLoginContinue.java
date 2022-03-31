package com.amazingchs.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.amazingchs.login.base.ActionHolder;
import com.amazingchs.login.base.LoginService;

/**
 * 登录后继续之前操作
 */
public enum AfterLoginContinue {
    INSTANCE;

    public static AfterLoginContinue getInstance() {
        return INSTANCE;
    }

    /**
     * 同一时间只会有一个登录后执行的操作
     */
    private ActionExecutor mActionExecutor;

    /**
     * 执行一个需要登录的操作
     *
     * @param lifecycleOwner 所处界面
     * @param actionHolder   操作
     * @param actionTag      操作tag
     */
    public void action(@NonNull LifecycleOwner lifecycleOwner,
                       @NonNull ActionHolder actionHolder,
                       String actionTag) {
        if (LoginService.getInstance().isLogin()) {
            actionHolder.onContinueAction();
        } else {
            cancelAction();
            mActionExecutor = new ActionExecutor(lifecycleOwner, actionHolder, actionTag);
        }
    }

    private void cancelAction() {
        if (mActionExecutor != null) {
            mActionExecutor.cancelAction();
            mActionExecutor = null;
        }
    }

    private static class ActionExecutor implements LifecycleObserver {
        private final LifecycleOwner mLifecycleOwner;
        private final ActionHolder mActionHolder;
        private final String mTag;
        private boolean mSkipFirstOnResume = true;

        ActionExecutor(LifecycleOwner lifecycleOwner,
                       ActionHolder actionHolder,
                       String tag) {
            this.mLifecycleOwner = lifecycleOwner;
            this.mActionHolder = actionHolder;
            this.mTag = tag;
            mLifecycleOwner.getLifecycle().addObserver(this);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume(LifecycleOwner source) {
            //需要屏蔽第一次注册时立即回调的onResume
            if (mSkipFirstOnResume) {
                //log("Skip First OnResume");
                mSkipFirstOnResume = false;
                return;
            }
            //当从登录界面返回时要及时处理掉此次触发登录的操作
            if (LoginService.getInstance().isLogin()) {
                mActionHolder.onContinueAction();
                //log("onResume 已登录，继续之前操作");
            } else {
                //log("onResume 未登录，取消操作");
                mActionHolder.onCancelAction();
            }
            AfterLoginContinue.getInstance().cancelAction();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestroy(LifecycleOwner source) {
            //log("onDestroy 界面销毁，取消操作");
            AfterLoginContinue.getInstance().cancelAction();
        }

        private void cancelAction() {
            mLifecycleOwner.getLifecycle().removeObserver(this);
        }
    }
}