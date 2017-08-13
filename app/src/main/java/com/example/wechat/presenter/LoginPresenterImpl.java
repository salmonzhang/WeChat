package com.example.wechat.presenter;

import com.example.wechat.adapter.MyEMCallBack;
import com.example.wechat.view.LoginView;
import com.hyphenate.chat.EMClient;

/**
 * author:salmonzhang
 * Description:实现登录功能的P层
 * Date:2017/8/13 0013 20:13
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;

    }

    @Override
    public void login(final String username, final String pwd) {
        EMClient.getInstance().login(username, pwd, new MyEMCallBack() {
            @Override
            public void onMainSuccess() {//成功
                //将登录信息返回给V层
                mLoginView.onLogin(true,username,pwd,"success");
            }

            @Override
            public void onMainError(int code, String message) {//失败
                //将登录信息返回给V层
                mLoginView.onLogin(false,username,pwd,message);
            }
        });
    }
}
