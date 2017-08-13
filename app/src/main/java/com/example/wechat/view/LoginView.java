package com.example.wechat.view;

/**
 * author:salmonzhang
 * Description:登录功能的V层
 * Date:2017/8/13 0013 20:14
 */

public interface LoginView {
    void onLogin(boolean isSuccess, String username, String pwd, String message);
}
