package com.example.wechat.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.wechat.view.RegistView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * author:salmonzhang
 * Description:P层对象，主要完成注册功能，并将结果返回给V层
 * Date:2017/8/12 0012 19:16
 */

public class RegistPresenterImpl implements RegistPresenter {

    private RegistView mRegistView;
    public RegistPresenterImpl(RegistView registView) {
        this.mRegistView = registView;
    }

    @Override
    public void regist(final String username, final String pwd) {
        /**
         * 1：先注册云数据库平台
         * 2：如果云数据库注册成功，再注册环信平台
         * 3：如果环信平台注册失败，则将云数据库中对应的数据删除（保证数据的一致性）
         */
        // 新建 AVUser 对象实例
        final AVUser avUser = new AVUser();
        avUser.setUsername(username);
        avUser.setPassword(pwd);
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {//云数据库注册成功
                    //环信平台注册
                    try {
                        EMClient.getInstance().createAccount(username, pwd);
                        //将注册结果返回给V层对象
                        mRegistView.onRegist(true,username,pwd,"success");
                    } catch (HyphenateException e1) {
                        e1.printStackTrace();
                        //环信注册失败，则从云数据平台删除数据
                        avUser.deleteInBackground();
                        //将注册结果返回给V层对象
                        mRegistView.onRegist(true,username,pwd,"环信注册失败");
                    }
                } else {//云数据库注册失败
                    //将注册结果返回给V层对象
                    mRegistView.onRegist(true,username,pwd,"云数据库注册失败");
                }
            }
        });
    }
}
