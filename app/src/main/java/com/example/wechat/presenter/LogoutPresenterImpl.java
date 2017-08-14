package com.example.wechat.presenter;

import com.example.wechat.adapter.MyEMCallBack;
import com.example.wechat.view.fragment.LogoutView;
import com.hyphenate.chat.EMClient;

import static com.hyphenate.chat.a.a.a.f;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/14 0014 15:25
 */

public class LogoutPresenterImpl implements LogoutPresenter {

    private LogoutView mLogoutView;
    public LogoutPresenterImpl(LogoutView logoutView) {
        mLogoutView = logoutView;
    }

    @Override
    public void logout() {
        EMClient.getInstance().logout(true, new MyEMCallBack() {
            @Override
            public void onMainSuccess() {
                //退出成功
                mLogoutView.onLogout(true,"success");
            }

            @Override
            public void onMainError(int code, String message) {
                //退出失败
                mLogoutView.onLogout(false,message);
            }
        });
    }
}
