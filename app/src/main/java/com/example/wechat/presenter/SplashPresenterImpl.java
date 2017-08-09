package com.example.wechat.presenter;

import com.example.wechat.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * author:salmonzhang
 * Description:SplashPresenter接口的实现类
 * Date:2017/8/10 0010 01:15
 */

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView mSplashView;
    public SplashPresenterImpl(SplashView splashView) {
        this.mSplashView = splashView;
    }

    @Override
    public void CheckLogin() {
        //判断当前是否登录
        //判断逻辑由环信去实现
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            //将结果返回给SplashActivity
            mSplashView.onCheckLogin(true);
        } else {
            mSplashView.onCheckLogin(false);
        }
    }
}
