package com.amazingchs.login.base;

/**
 * 通用的持有操作处理类
 */
public abstract class ActionHolder {

    /**
     * 继续之前的操作
     */
    public abstract void onContinueAction();

    /**
     * 未登录成功操作被取消
     */
    public void onCancelAction() {
    }
}